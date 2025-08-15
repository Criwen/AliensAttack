package com.aliensattack.core.interfaces;

import com.aliensattack.core.model.Position;
import com.aliensattack.actions.interfaces.IAction;
import com.aliensattack.core.model.GameContext;

import java.util.List;
import java.util.Optional;

/**
 * Core interface for character control systems
 * Defines the contract that all brain implementations must implement
 * This abstraction allows for different control methods (Human, AI, etc.)
 */
public interface IBrain {
    
    /**
     * Get the unique identifier of this brain
     */
    String getBrainId();
    
    /**
     * Get the type of this brain
     */
    BrainType getBrainType();
    
    /**
     * Get the unit this brain controls
     */
    IUnit getControlledUnit();
    
    /**
     * Check if this brain can control the unit
     */
    boolean canControl(IUnit unit);
    
    /**
     * Take control of a unit
     */
    void takeControl(IUnit unit);
    
    /**
     * Release control of the current unit
     */
    void releaseControl();
    
    /**
     * Check if the brain is currently controlling a unit
     */
    boolean isControlling();
    
    /**
     * Get available actions for the current turn
     */
    List<IAction> getAvailableActions();
    
    /**
     * Select an action to perform
     */
    Optional<IAction> selectAction(GameContext context);
    
    /**
     * Execute the selected action
     */
    boolean executeAction(IAction action);
    
    /**
     * Get the brain's decision-making priority
     */
    int getPriority();
    
    /**
     * Update the brain's state based on game context
     */
    void update(GameContext context);
    
    /**
     * Check if the brain is ready to make decisions
     */
    boolean isReady();
    
    /**
     * Get the brain's current state
     */
    BrainState getState();
    
    /**
     * Reset the brain's state for a new turn
     */
    void resetForNewTurn();
    
    /**
     * Get the brain's performance metrics
     */
    BrainMetrics getMetrics();
    
    /**
     * Brain types enumeration
     */
    enum BrainType {
        HUMAN,      // Human player control
        AI,         // Artificial Intelligence
        REMOTE,     // Remote/Network player
        AUTONOMOUS  // Autonomous behavior
    }
    
    /**
     * Brain states enumeration
     */
    enum BrainState {
        IDLE,           // Not controlling any unit
        THINKING,       // Analyzing situation
        DECIDING,       // Making decision
        EXECUTING,      // Performing action
        WAITING,        // Waiting for input/response
        ERROR           // Error state
    }
    
    /**
     * Brain metrics for performance tracking
     */
    interface BrainMetrics {
        int getDecisionsMade();
        int getActionsExecuted();
        double getAverageDecisionTime();
        double getSuccessRate();
        int getErrors();
    }
}

package com.aliensattack.actions.interfaces;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;

/**
 * Core interface for all actions in the game
 * Defines the contract that all action types must implement
 */
public interface IAction {
    
    /**
     * Get the type of this action
     */
    String getActionType();
    
    /**
     * Get the unit performing this action
     */
    IUnit getPerformer();
    
    /**
     * Get the target unit (if applicable)
     */
    IUnit getTarget();
    
    /**
     * Get the target position (if applicable)
     */
    Position getTargetPosition();
    
    /**
     * Check if this action can be performed
     */
    boolean canPerform();
    
    /**
     * Execute this action
     */
    void execute();
    
    /**
     * Get the cost in action points
     */
    int getActionPointCost();
    
    /**
     * Get the actual cost (may differ from base cost)
     */
    int getActualActionPointCost();
    
    /**
     * Check if the action was successful
     */
    boolean isSuccessful();
    
    /**
     * Get the result message
     */
    String getResult();
    
    /**
     * Get the damage dealt (if applicable)
     */
    int getDamage();
}

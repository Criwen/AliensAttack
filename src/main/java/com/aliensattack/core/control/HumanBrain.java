package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.actions.interfaces.IAction;
import com.aliensattack.core.model.GameContext;
import com.aliensattack.actions.ActionManager;
import com.aliensattack.actions.ActionFactory;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Human brain implementation for player-controlled units
 * Handles human input and decision making
 */
@Log4j2
public class HumanBrain extends AbstractBrain {
    
    // Human-specific properties
    private final String playerId;
    private final List<String> preferredActions;
    private boolean enableAutoActions;
    private int reactionTime;
    
    // Input handling
    private IAction pendingAction;
    private boolean waitingForInput;
    private long inputTimeout;
    
    public HumanBrain(String playerId, String brainId, int priority) {
        super(brainId, BrainType.HUMAN, priority);
        this.playerId = playerId;
        this.preferredActions = new ArrayList<>();
        this.enableAutoActions = false;
        this.reactionTime = 1000; // 1 second default reaction time
        this.pendingAction = null;
        this.waitingForInput = false;
        this.inputTimeout = 0;
    }
    
    public HumanBrain(String playerId, String brainId, int priority, 
                     List<String> preferredActions, boolean enableAutoActions, int reactionTime) {
        super(brainId, BrainType.HUMAN, priority);
        this.playerId = playerId;
        this.preferredActions = new ArrayList<>(preferredActions);
        this.enableAutoActions = enableAutoActions;
        this.reactionTime = reactionTime;
        this.pendingAction = null;
        this.waitingForInput = false;
        this.inputTimeout = 0;
    }
    
    @Override
    protected Optional<IAction> decideAction(GameContext context) {
        // Human brain waits for player input
        if (waitingForInput) {
            // Check if input timeout has occurred
            if (System.currentTimeMillis() > inputTimeout) {
                log.warn("Input timeout for player {}, using auto-action", playerId);
                return selectAutoAction(context);
            }
            return Optional.empty();
        }
        
        // If we have a pending action from player input, use it
        if (pendingAction != null) {
            IAction action = pendingAction;
            pendingAction = null;
            return Optional.of(action);
        }
        
        // Check if auto-actions are enabled
        if (enableAutoActions) {
            return selectAutoAction(context);
        }
        
        // Wait for player input
        startWaitingForInput();
        return Optional.empty();
    }
    
    @Override
    protected List<IAction> calculateAvailableActions() {
        if (getControlledUnit() == null) {
            return new ArrayList<>();
        }
        
        List<IAction> actions = new ArrayList<>();
        IUnit unit = getControlledUnit();
        
        // Add movement actions
        if (unit.canMove() && hasActionPoints(1.0)) {
            // Create basic move action to current position (will be updated with target)
            actions.add(ActionFactory.createMoveAction(unit, unit.getPosition()));
        }
        
        // Add attack actions
        if (unit.canAttack() && hasActionPoints(1.0)) {
            // Create basic attack action (will be updated with target)
            actions.add(ActionFactory.createAttackAction(unit, null));
        }
        
        // Add defensive actions
        if (hasActionPoints(1.0)) {
            actions.add(ActionFactory.createDefendAction(unit));
            actions.add(ActionFactory.createOverwatchAction(unit));
        }
        
        // Add utility actions
        if (hasActionPoints(0.5)) {
            actions.add(ActionFactory.createReloadAction(unit));
        }
        
        // Filter by preferred actions if specified
        if (!preferredActions.isEmpty()) {
            actions = actions.stream()
                    .filter(action -> preferredActions.contains(action.getActionType()))
                    .collect(Collectors.toList());
        }
        
        return actions;
    }
    
    @Override
    protected void onUpdate(GameContext context) {
        // Update input timeout
        if (waitingForInput && System.currentTimeMillis() > inputTimeout) {
            log.debug("Input timeout occurred for player {}", playerId);
            waitingForInput = false;
        }
        
        // Check for auto-action opportunities
        if (enableAutoActions && !waitingForInput && pendingAction == null) {
            checkForAutoActionOpportunities(context);
        }
    }
    
    @Override
    protected void onNewTurn() {
        // Reset input state for new turn
        waitingForInput = false;
        pendingAction = null;
        inputTimeout = 0;
        
        log.debug("Human brain {} ready for new turn", getBrainId());
    }
    
    @Override
    public BrainState getState() {
        return getCurrentState();
    }
    
    // Human-specific methods
    
    /**
     * Set a pending action from player input
     */
    public void setPendingAction(IAction action) {
        if (action != null && getAvailableActions().contains(action)) {
            this.pendingAction = action;
            this.waitingForInput = false;
            log.debug("Player {} set pending action: {}", playerId, action.getActionType());
        } else {
            log.warn("Player {} tried to set invalid action: {}", playerId, 
                    action != null ? action.getActionType() : "null");
        }
    }
    
    /**
     * Get the current pending action
     */
    public Optional<IAction> getPendingAction() {
        return Optional.ofNullable(pendingAction);
    }
    
    /**
     * Check if the brain is waiting for player input
     */
    public boolean isWaitingForInput() {
        return waitingForInput;
    }
    
    /**
     * Get the player ID
     */
    public String getPlayerId() {
        return playerId;
    }
    
    /**
     * Add a preferred action type
     */
    public void addPreferredAction(String actionType) {
        if (!preferredActions.contains(actionType)) {
            preferredActions.add(actionType);
            log.debug("Added preferred action {} for player {}", actionType, playerId);
        }
    }
    
    /**
     * Remove a preferred action type
     */
    public void removePreferredAction(String actionType) {
        if (preferredActions.remove(actionType)) {
            log.debug("Removed preferred action {} for player {}", actionType, playerId);
        }
    }
    
    /**
     * Enable or disable auto-actions
     */
    public void setAutoActionsEnabled(boolean enabled) {
        if (this.enableAutoActions != enabled) {
            this.enableAutoActions = enabled;
            log.info("Auto-actions {} for player {}", enabled ? "enabled" : "disabled", playerId);
        }
    }
    
    /**
     * Set the reaction time for this player
     */
    public void setReactionTime(int reactionTime) {
        this.reactionTime = Math.max(100, reactionTime); // Minimum 100ms
        log.debug("Set reaction time to {}ms for player {}", this.reactionTime, playerId);
    }
    
    // Private helper methods
    
    private void startWaitingForInput() {
        this.waitingForInput = true;
        this.inputTimeout = System.currentTimeMillis() + reactionTime;
        log.debug("Waiting for input from player {} (timeout: {}ms)", playerId, reactionTime);
    }
    
    private Optional<IAction> selectAutoAction(GameContext context) {
        List<IAction> actions = getAvailableActions();
        
        if (actions.isEmpty()) {
            return Optional.empty();
        }
        
        // Simple auto-action logic - can be enhanced
        // Priority: Defend > Move to cover > Attack > Utility
        
        // Look for defensive actions first
        Optional<IAction> defensiveAction = actions.stream()
                .filter(action -> action.getActionType().contains("DEFEND") || 
                                action.getActionType().contains("OVERWATCH"))
                .findFirst();
        
        if (defensiveAction.isPresent()) {
            return defensiveAction;
        }
        
        // Look for movement to cover
        Optional<IAction> coverAction = actions.stream()
                .filter(action -> action.getActionType().contains("MOVE") && 
                                context.getCoverValue(action.getTargetPosition().toString()) > 0)
                .findFirst();
        
        if (coverAction.isPresent()) {
            return coverAction;
        }
        
        // Look for attack actions
        Optional<IAction> attackAction = actions.stream()
                .filter(action -> action.getActionType().contains("ATTACK"))
                .findFirst();
        
        if (attackAction.isPresent()) {
            return attackAction;
        }
        
        // Return first available action
        return Optional.of(actions.get(0));
    }
    
    private void checkForAutoActionOpportunities(GameContext context) {
        // Check if unit is in immediate danger
        if (getControlledUnit() != null && 
            getControlledUnit().getCurrentHealth() < getControlledUnit().getMaxHealth() * 0.3) {
            
            log.debug("Unit {} in danger, considering auto-action", getControlledUnit().getId());
            
            // Force defensive action
            Optional<IAction> defensiveAction = getAvailableActions().stream()
                    .filter(action -> action.getActionType().contains("DEFEND"))
                    .findFirst();
            
            if (defensiveAction.isPresent()) {
                setPendingAction(defensiveAction.get());
                log.info("Auto-selected defensive action for endangered unit");
            }
        }
    }
}

package com.aliensattack.actions;

import com.aliensattack.actions.interfaces.IAction;
import com.aliensattack.core.GameLogManager;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;

import lombok.Getter;
import lombok.Setter;

/**
 * Base implementation of IAction interface
 * Contains core functionality shared by all action types
 */
@Getter
@Setter
public abstract class BaseAction implements IAction {
    
    protected String actionType;
    protected IUnit performer;
    protected IUnit target;
    protected Position targetPosition;
    protected String description;
    protected boolean successful;
    protected int damage;
    protected String result;
    protected int actionPointCost;
    protected int actualActionPointCost;
    
    public BaseAction(String actionType, IUnit performer, int actionPointCost) {
        this.actionType = actionType;
        this.performer = performer;
        this.actionPointCost = actionPointCost;
        this.actualActionPointCost = actionPointCost;
        this.successful = false;
        this.damage = 0;
        
        // Log action creation with enhanced details
        String targetInfo = target != null ? target.getName() : 
                          (targetPosition != null ? targetPosition.toString() : "none");
        GameLogManager.logActionStart(actionType, 
            performer != null ? performer.getName() : "unknown", targetInfo);
        
        // Log unit action details
        if (performer != null) {
            GameLogManager.logUnitAction(performer.getName(), "Action Created", 
                String.format("Type: %s, Cost: %d AP, Target: %s", actionType, actionPointCost, targetInfo));
        }
    }
    
    public BaseAction(String actionType, IUnit performer, IUnit target, int actionPointCost) {
        this(actionType, performer, actionPointCost);
        this.target = target;
    }
    
    public BaseAction(String actionType, IUnit performer, Position targetPosition, int actionPointCost) {
        this(actionType, performer, actionPointCost);
        this.targetPosition = targetPosition;
    }
    
    // IAction interface implementation
    @Override
    public String getActionType() { return actionType; }
    
    @Override
    public IUnit getPerformer() { return performer; }
    
    @Override
    public IUnit getTarget() { return target; }
    
    @Override
    public Position getTargetPosition() { return targetPosition; }
    
    @Override
    public int getActionPointCost() { return actionPointCost; }
    
    @Override
    public int getActualActionPointCost() { return actualActionPointCost; }
    
    @Override
    public boolean isSuccessful() { return successful; }
    
    @Override
    public String getResult() { return result; }
    
    @Override
    public int getDamage() { return damage; }
    
    // Core validation logic
    @Override
    public boolean canPerform() {
        if (performer == null || !performer.isAlive()) {
            String reason = performer == null ? "Performer is null" : "Performer is not alive";
            GameLogManager.logActionValidation(actionType, 
                performer != null ? performer.getName() : "null", false, reason);
            
            // Log unit status for debugging
            if (performer != null) {
                GameLogManager.logUnitAction(performer.getName(), "Validation Failed", 
                    String.format("Alive: %s, Health: %d/%d", performer.isAlive(), 
                        performer.getCurrentHealth(), performer.getMaxHealth()));
            }
            return false;
        }
        
        if (performer.getActionPoints() < actualActionPointCost) {
            String reason = String.format("Insufficient action points: %.1f < %d", 
                performer.getActionPoints(), actualActionPointCost);
            GameLogManager.logActionValidation(actionType, performer.getName(), false, reason);
            
            // Log action point status
            GameLogManager.logActionPointSpending(performer.getName(), 0, (int)performer.getActionPoints());
            return false;
        }
        
        boolean canPerform = validateActionSpecificRequirements();
        String reason = canPerform ? "All requirements met" : "Action-specific requirements not met";
        GameLogManager.logActionValidation(actionType, performer.getName(), canPerform, reason);
        
        // Log validation success with unit details
        if (canPerform && performer != null) {
            GameLogManager.logUnitAction(performer.getName(), "Validation Success", 
                String.format("Health: %d/%d, AP: %.1f, Can perform: %s", 
                    performer.getCurrentHealth(), performer.getMaxHealth(), 
                    performer.getActionPoints(), actionType));
        }
        
        return canPerform;
    }
    
    // Template method for action-specific validation
    protected abstract boolean validateActionSpecificRequirements();
    
    // Core execution logic
    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();
        
        if (!canPerform()) {
            successful = false;
            result = "Действие не может быть выполнено";
            GameLogManager.logActionComplete(actionType, 
                performer != null ? performer.getName() : "unknown", false, result);
            return;
        }
        
        // Log action execution start with enhanced details
        String executionDetails = String.format("Starting execution, cost: %d AP", actualActionPointCost);
        GameLogManager.logActionExecution(actionType, performer.getName(), executionDetails);
        
        // Log unit state before action
        if (performer != null) {
            GameLogManager.logUnitAction(performer.getName(), "Pre-Action State", 
                String.format("Health: %d/%d, AP: %.1f, Position: %s", 
                    performer.getCurrentHealth(), performer.getMaxHealth(), 
                    performer.getActionPoints(), performer.getPosition()));
        }
        
        // Spend action points
        double previousAP = performer.getActionPoints();
        performer.spendActionPoints(actualActionPointCost);
        double remainingAP = performer.getActionPoints();
        
        // Log action point spending
        GameLogManager.logActionPointSpending(performer.getName(), actualActionPointCost, (int)remainingAP);
        
        // Log resource management
        GameLogManager.logResourceManagement("Action Points", (int)previousAP, (int)remainingAP, 
            String.format("Spent on %s action", actionType));
        
        // Execute action-specific logic
        executeActionLogic();
        
        // Log unit state after action
        if (performer != null) {
            GameLogManager.logUnitAction(performer.getName(), "Post-Action State", 
                String.format("Health: %d/%d, AP: %.1f, Position: %s", 
                    performer.getCurrentHealth(), performer.getMaxHealth(), 
                    performer.getActionPoints(), performer.getPosition()));
        }
        
        // Log action completion
        GameLogManager.logActionComplete(actionType, performer.getName(), successful, result);
        
        // Log performance
        long duration = System.currentTimeMillis() - startTime;
        GameLogManager.logPerformance(actionType + " execution", duration);
        
        // Log turn progression if this is a significant action
        if (successful && damage > 0) {
            GameLogManager.logTurnProgression(0, "Action Execution", performer.getName(), 
                String.format("Completed %s with %d damage", actionType, damage));
        }
    }
    
    // Template method for action-specific execution
    protected abstract void executeActionLogic();
    
    // Utility methods
    protected void setSuccess(boolean success, String message) {
        this.successful = success;
        this.result = message;
    }
    
    protected void setDamage(int damage) {
        this.damage = damage;
        
        // Log damage application if this is a combat action
        if (damage > 0 && target != null) {
            int oldHealth = target.getCurrentHealth();
            int newHealth = target.getCurrentHealth(); // Will be updated by target.takeDamage()
            GameLogManager.logUnitHealthChange(target.getName(), oldHealth, newHealth, -damage, 
                String.format("%s action by %s", actionType, performer.getName()));
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s[type=%s, performer=%s, target=%s, position=%s, success=%s]",
                getClass().getSimpleName(), actionType, 
                performer != null ? performer.getName() : "null",
                target != null ? target.getName() : "null",
                targetPosition, successful);
    }
}

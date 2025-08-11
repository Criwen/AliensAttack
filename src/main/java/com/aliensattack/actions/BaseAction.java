package com.aliensattack.actions;

import com.aliensattack.actions.interfaces.IAction;
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
            return false;
        }
        
        if (performer.getActionPoints() < actualActionPointCost) {
            return false;
        }
        
        return validateActionSpecificRequirements();
    }
    
    // Template method for action-specific validation
    protected abstract boolean validateActionSpecificRequirements();
    
    // Core execution logic
    @Override
    public void execute() {
        if (!canPerform()) {
            successful = false;
            result = "Действие не может быть выполнено";
            return;
        }
        
        // Spend action points
        performer.spendActionPoints(actualActionPointCost);
        
        // Execute action-specific logic
        executeActionLogic();
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

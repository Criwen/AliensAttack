package com.aliensattack.actions;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;

/**
 * Action for dashing to a position
 */
public class DashAction extends BaseAction {
    
    private static final int BASE_DASH_COST = 2;
    
    public DashAction(IUnit performer, Position targetPosition) {
        super("DASH", performer, targetPosition, BASE_DASH_COST);
        calculateActualCost();
    }
    
    @Override
    protected boolean validateActionSpecificRequirements() {
        return targetPosition != null && performer.canMove();
    }
    
    @Override
    protected void executeActionLogic() {
        if (targetPosition != null) {
            Position oldPosition = performer.getPosition();
            performer.setPosition(targetPosition);
            setSuccess(true, String.format("%s совершает рывок из %s в позицию %s (стоимость: %d AP)",
                    performer.getName(), oldPosition, targetPosition, actualActionPointCost));
        } else {
            setSuccess(false, "Ошибка: целевая позиция не указана");
        }
    }
    
    private void calculateActualCost() {
        if (targetPosition == null || performer == null) {
            actualActionPointCost = actionPointCost;
            return;
        }
        
        Position currentPos = performer.getPosition();
        int distance = Math.abs(currentPos.getX() - targetPosition.getX()) + 
                      Math.abs(currentPos.getY() - targetPosition.getY());
        
        // Dash costs more than regular move but allows longer distance
        int calculatedCost = actionPointCost;
        if (distance > 2) {
            calculatedCost += (distance - 2) * 2; // Higher cost for longer dashes
        }
        
        // Don't exceed available action points
        actualActionPointCost = Math.min(calculatedCost, (int)performer.getActionPoints());
    }
}

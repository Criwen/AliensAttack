package com.aliensattack.actions;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;

/**
 * Action for moving a unit to a new position
 */
public class MoveAction extends BaseAction {
    
    private static final int BASE_MOVE_COST = 1;
    
    public MoveAction(IUnit performer, Position targetPosition) {
        super("MOVE", performer, targetPosition, BASE_MOVE_COST);
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
            setSuccess(true, String.format("%s переместился из %s в позицию %s (стоимость: %d AP)",
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
        
        // Base cost + additional cost for distance
        int calculatedCost = actionPointCost;
        if (distance > 1) {
            calculatedCost += (distance - 1);
        }
        
        // Don't exceed available action points
        actualActionPointCost = Math.min(calculatedCost, (int)performer.getActionPoints());
    }
}

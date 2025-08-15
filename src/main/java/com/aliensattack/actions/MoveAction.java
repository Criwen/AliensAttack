package com.aliensattack.actions;

import com.aliensattack.core.GameLogManager;
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
        if (targetPosition == null) {
            GameLogManager.logActionValidation("MOVE", performer.getName(), false, "Target position is null");
            return false;
        }
        if (!performer.canMove()) {
            GameLogManager.logActionValidation("MOVE", performer.getName(), false, "Unit cannot move");
            return false;
        }
        
        // Log movement validation details
        Position currentPos = performer.getPosition();
        int distance = Math.abs(currentPos.getX() - targetPosition.getX()) + 
                      Math.abs(currentPos.getY() - targetPosition.getY());
        
        GameLogManager.logActionValidation("MOVE", performer.getName(), true, 
            String.format("Movement validated - Distance: %d tiles, Current: %s, Target: %s", 
                distance, currentPos, targetPosition));
        
        return true;
    }
    
    @Override
    protected void executeActionLogic() {
        if (targetPosition != null) {
            Position oldPosition = performer.getPosition();
            
            // Log movement attempt
            GameLogManager.logActionExecution("MOVE", performer.getName(), 
                String.format("Attempting movement from %s to %s (Cost: %d AP)", 
                    oldPosition, targetPosition, actualActionPointCost));
            
            // Log terrain interaction if moving to different elevation
            if (oldPosition.getHeight() != targetPosition.getHeight()) {
                String terrainType = targetPosition.getHeight() > oldPosition.getHeight() ? "Elevation Gain" : "Elevation Loss";
                GameLogManager.logTerrainInteraction(performer.getName(), terrainType, "Movement", 
                    String.format("Moving from Height=%d to Height=%d", oldPosition.getHeight(), targetPosition.getHeight()));
            }
            
            // Execute movement
            performer.setPosition(targetPosition);
            
            // Log successful movement
            setSuccess(true, String.format("%s переместился из %s в позицию %s (стоимость: %d AP)",
                    performer.getName(), oldPosition, targetPosition, actualActionPointCost));
            
            // Log unit movement for tracking
            GameLogManager.logUnitMovement(performer.getName(), oldPosition.toString(), 
                targetPosition.toString(), actualActionPointCost);
            
            // Log turn progression for significant movement
            int distance = Math.abs(oldPosition.getX() - targetPosition.getX()) + 
                          Math.abs(oldPosition.getY() - targetPosition.getY());
            if (distance > 2) {
                GameLogManager.logTurnProgression(0, "Movement", performer.getName(), 
                    String.format("Long-distance movement completed (%d tiles)", distance));
            }
            
        } else {
            setSuccess(false, "Ошибка: целевая позиция не указана");
            GameLogManager.logActionExecution("MOVE", performer.getName(), "Movement failed - No target position");
        }
    }
    
    private void calculateActualCost() {
        if (targetPosition == null || performer == null) {
            actualActionPointCost = actionPointCost;
            GameLogManager.logDebug("MOVE", "Cost calculation skipped - Missing target or performer");
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
        
        // Log cost calculation
        GameLogManager.logDebug("MOVE", String.format("Cost calculation: Base(%d) + Distance(%d) = %d AP", 
            actionPointCost, distance > 1 ? distance - 1 : 0, calculatedCost));
        GameLogManager.logDebug("MOVE", String.format("Final cost: %d AP (limited by available AP: %.1f)", 
            actualActionPointCost, performer.getActionPoints()));
    }
}

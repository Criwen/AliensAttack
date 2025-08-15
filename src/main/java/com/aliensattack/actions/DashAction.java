package com.aliensattack.actions;

import com.aliensattack.core.GameLogManager;
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
        if (targetPosition == null) {
            GameLogManager.logActionValidation("DASH", performer.getName(), false, "Target position is null");
            return false;
        }
        if (!performer.canMove()) {
            GameLogManager.logActionValidation("DASH", performer.getName(), false, "Unit cannot move");
            return false;
        }
        
        // Log dash validation details
        Position currentPos = performer.getPosition();
        int distance = Math.abs(currentPos.getX() - targetPosition.getX()) + 
                      Math.abs(currentPos.getY() - targetPosition.getY());
        
        GameLogManager.logActionValidation("DASH", performer.getName(), true, 
            String.format("Dash validated - Distance: %d tiles, Current: %s, Target: %s", 
                distance, currentPos, targetPosition));
        
        return true;
    }
    
    @Override
    protected void executeActionLogic() {
        if (targetPosition != null) {
            Position oldPosition = performer.getPosition();
            
            // Log dash attempt
            GameLogManager.logActionExecution("DASH", performer.getName(), 
                String.format("Attempting dash from %s to %s (Cost: %d AP)", 
                    oldPosition, targetPosition, actualActionPointCost));
            
            // Log terrain interaction for dash effects
            if (oldPosition.getHeight() != targetPosition.getHeight()) {
                String terrainType = targetPosition.getHeight() > oldPosition.getHeight() ? "Elevation Gain" : "Elevation Loss";
                GameLogManager.logTerrainInteraction(performer.getName(), terrainType, "Dash", 
                    String.format("Dashing from Height=%d to Height=%d", oldPosition.getHeight(), targetPosition.getHeight()));
            }
            
            // Execute dash movement
            performer.setPosition(targetPosition);
            
            // Log successful dash
            setSuccess(true, String.format("%s совершает рывок из %s в позицию %s (стоимость: %d AP)",
                    performer.getName(), oldPosition, targetPosition, actualActionPointCost));
            
            // Log unit movement for tracking
            GameLogManager.logUnitMovement(performer.getName(), oldPosition.toString(), 
                targetPosition.toString(), actualActionPointCost);
            
            // Log turn progression for dash actions
            int distance = Math.abs(oldPosition.getX() - targetPosition.getX()) + 
                          Math.abs(oldPosition.getY() - targetPosition.getY());
            GameLogManager.logTurnProgression(0, "Dash", performer.getName(), 
                String.format("Dash completed (%d tiles)", distance));
            
            // Log combat event for tactical movement
            GameLogManager.logCombatEvent("Dash Completed", 
                String.format("%s dashed %d tiles to %s", performer.getName(), distance, targetPosition));
            
        } else {
            setSuccess(false, "Ошибка: целевая позиция не указана");
            GameLogManager.logActionExecution("DASH", performer.getName(), "Dash failed - No target position");
        }
    }
    
    private void calculateActualCost() {
        if (targetPosition == null || performer == null) {
            actualActionPointCost = actionPointCost;
            GameLogManager.logDebug("DASH", "Cost calculation skipped - Missing target or performer");
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
        
        // Log cost calculation
        GameLogManager.logDebug("DASH", String.format("Cost calculation: Base(%d) + Distance Penalty(%d) = %d AP", 
            actionPointCost, distance > 2 ? (distance - 2) * 2 : 0, calculatedCost));
        GameLogManager.logDebug("DASH", String.format("Final cost: %d AP (limited by available AP: %.1f)", 
            actualActionPointCost, performer.getActionPoints()));
    }
}

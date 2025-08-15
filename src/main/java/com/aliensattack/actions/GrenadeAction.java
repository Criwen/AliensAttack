package com.aliensattack.actions;

import com.aliensattack.core.GameLogManager;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;

/**
 * Action for throwing a grenade
 */
public class GrenadeAction extends BaseAction {
    
    private static final int BASE_GRENADE_COST = 1;
    private static final int GRENADE_DAMAGE = 30;
    
    public GrenadeAction(IUnit performer, Position targetPosition) {
        super("GRENADE", performer, targetPosition, BASE_GRENADE_COST);
    }
    
    @Override
    protected boolean validateActionSpecificRequirements() {
        if (targetPosition == null) {
            GameLogManager.logActionValidation("GRENADE", performer.getName(), false, "Target position is null");
            return false;
        }
        if (!performer.canTakeActions()) {
            GameLogManager.logActionValidation("GRENADE", performer.getName(), false, "Unit cannot take actions");
            return false;
        }
        
        // Log grenade validation details
        Position currentPos = performer.getPosition();
        int distance = Math.abs(currentPos.getX() - targetPosition.getX()) + 
                      Math.abs(currentPos.getY() - targetPosition.getY());
        
        GameLogManager.logActionValidation("GRENADE", performer.getName(), true, 
            String.format("Grenade throw validated - Distance: %d tiles, Current: %s, Target: %s", 
                distance, currentPos, targetPosition));
        
        return true;
    }
    
    @Override
    protected void executeActionLogic() {
        if (targetPosition == null) {
            setSuccess(false, "Целевая позиция для гранаты не указана");
            GameLogManager.logActionExecution("GRENADE", performer.getName(), "Grenade throw failed - No target position");
            return;
        }
        
        // Log grenade throw attempt
        GameLogManager.logActionExecution("GRENADE", performer.getName(), 
            String.format("Throwing grenade to %s (Damage: %d, Cost: %d AP)", 
                targetPosition, GRENADE_DAMAGE, actualActionPointCost));
        
        // Log combat event
        GameLogManager.logCombatEvent("Grenade Throw Initiated", 
            String.format("%s throwing grenade to %s", performer.getName(), targetPosition));
        
        // Log terrain interaction for explosive effects
        GameLogManager.logTerrainInteraction(performer.getName(), "Explosive", "Grenade Impact", 
            String.format("Grenade will explode at %s", targetPosition));
        
        // Set damage for area effect
        setDamage(GRENADE_DAMAGE);
        setSuccess(true, String.format("%s бросает гранату в позицию %s и наносит %d урона по области (стоимость: %d AP)",
                performer.getName(), targetPosition, GRENADE_DAMAGE, actualActionPointCost));
        
        // Log successful grenade throw
        GameLogManager.logActionExecution("GRENADE", performer.getName(), 
            String.format("Grenade thrown successfully to %s - Area damage: %d", targetPosition, GRENADE_DAMAGE));
        
        // Log combat event
        GameLogManager.logCombatEvent("Grenade Thrown", 
            String.format("%s successfully threw grenade to %s", performer.getName(), targetPosition));
        
        // Log turn progression for explosive actions
        GameLogManager.logTurnProgression(0, "Explosive", performer.getName(), 
            String.format("Grenade thrown to %s", targetPosition));
        
        // Log inventory operation (assuming grenade is consumed)
        GameLogManager.logInventoryOperation(performer.getName(), "USED", "Grenade", 1);
    }
}

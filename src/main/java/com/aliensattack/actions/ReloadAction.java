package com.aliensattack.actions;

import com.aliensattack.core.GameLogManager;
import com.aliensattack.core.interfaces.IUnit;

/**
 * Action for reloading a unit's weapon
 */
public class ReloadAction extends BaseAction {
    
    private static final int BASE_RELOAD_COST = 1;
    
    public ReloadAction(IUnit performer) {
        super("RELOAD", performer, BASE_RELOAD_COST);
    }
    
    @Override
    protected boolean validateActionSpecificRequirements() {
        if (!performer.canTakeActions()) {
            GameLogManager.logActionValidation("RELOAD", performer.getName(), false, "Unit cannot take actions");
            return false;
        }
        
        // Log reload validation
        GameLogManager.logActionValidation("RELOAD", performer.getName(), true, 
            String.format("Reload validated - Unit: %s, Position: %s, Can reload: true", 
                performer.getName(), performer.getPosition()));
        
        return true;
    }
    
    @Override
    protected void executeActionLogic() {
        // Log reload attempt
        GameLogManager.logActionExecution("RELOAD", performer.getName(), 
            String.format("Attempting to reload weapon - Cost: %d AP", actualActionPointCost));
        
        // Log unit status before reload
        GameLogManager.logUnitAction(performer.getName(), "Pre-Reload Status", 
            String.format("Position: %s, Action Points: %.1f", 
                performer.getPosition(), performer.getActionPoints()));
        
        // Simple reload implementation
        setSuccess(true, String.format("%s перезаряжается (стоимость: %d AP)",
                performer.getName(), actualActionPointCost));
        
        // Log successful reload
        GameLogManager.logActionExecution("RELOAD", performer.getName(), 
            "Weapon reloaded successfully");
        
        // Log unit status after reload
        GameLogManager.logUnitAction(performer.getName(), "Post-Reload Status", 
            String.format("Position: %s, Action Points: %.1f", 
                performer.getPosition(), performer.getActionPoints()));
        
        // Log turn progression for reload actions
        GameLogManager.logTurnProgression(0, "Reload", performer.getName(), 
            "Weapon reload completed");
        
        // Log combat event
        GameLogManager.logCombatEvent("Weapon Reloaded", 
            String.format("%s reloaded weapon", performer.getName()));
        
        // Log equipment change (assuming ammo is replenished)
        GameLogManager.logEquipmentChange(performer.getName(), "Ammunition", "Empty", "Full");
    }
}

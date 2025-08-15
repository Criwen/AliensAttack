package com.aliensattack.actions;

import com.aliensattack.core.GameLogManager;
import com.aliensattack.core.interfaces.IUnit;

/**
 * Action for setting up overwatch (reaction fire)
 */
public class OverwatchAction extends BaseAction {
    
    public OverwatchAction(IUnit performer) {
        super("OVERWATCH", performer, 0); // Cost will be calculated dynamically
        calculateActualCost();
    }
    
    @Override
    protected boolean validateActionSpecificRequirements() {
        if (!performer.canTakeActions()) {
            GameLogManager.logActionValidation("OVERWATCH", performer.getName(), false, "Unit cannot take actions");
            return false;
        }
        
        // Log overwatch validation
        GameLogManager.logActionValidation("OVERWATCH", performer.getName(), true, 
            String.format("Overwatch validated - Unit: %s, Position: %s, Available AP: %.1f", 
                performer.getName(), performer.getPosition(), performer.getActionPoints()));
        
        return true;
    }
    
    @Override
    protected void executeActionLogic() {
        // Log overwatch attempt
        GameLogManager.logActionExecution("OVERWATCH", performer.getName(), 
            String.format("Setting up overwatch - Cost: %d AP, Remaining AP: %.1f", 
                actualActionPointCost, performer.getActionPoints()));
        
        // Log unit status before overwatch
        GameLogManager.logUnitAction(performer.getName(), "Pre-Overwatch Status", 
            String.format("Position: %s, Overwatching: %s, Overwatch Chance: %d%%", 
                performer.getPosition(), performer.isOverwatching(), performer.getOverwatchChance()));
        
        // Set overwatch state
        performer.setOverwatching(true);
        
        // Calculate overwatch chance based on unit stats
        int overwatchChance = calculateOverwatchChance();
        performer.setOverwatchChance(overwatchChance);
        
        // Log successful overwatch
        setSuccess(true, String.format("%s ведет наблюдение (AP spent: %d, Overwatch: %d%%)",
                performer.getName(), actualActionPointCost, overwatchChance));
        
        // Log unit status after overwatch
        GameLogManager.logUnitAction(performer.getName(), "Post-Overwatch Status", 
            String.format("Position: %s, Overwatching: %s, Overwatch Chance: %d%%", 
                performer.getPosition(), performer.isOverwatching(), performer.getOverwatchChance()));
        
        // Log turn progression for overwatch actions
        GameLogManager.logTurnProgression(0, "Overwatch", performer.getName(), 
            String.format("Overwatch established with %d%% chance", overwatchChance));
        
        // Log combat event
        GameLogManager.logCombatEvent("Overwatch Established", 
            String.format("%s established overwatch with %d%% chance", performer.getName(), overwatchChance));
        
        // Log unit status effect
        GameLogManager.logUnitStatusEffect(performer.getName(), "OVERWATCH", 
            String.format("Overwatching with %d%% chance", overwatchChance), true);
    }
    
    private void calculateActualCost() {
        // Overwatch costs all remaining action points
        actualActionPointCost = (int)performer.getActionPoints();
        
        // Log cost calculation
        GameLogManager.logDebug("OVERWATCH", String.format("Cost calculation: All remaining AP: %.1f", 
            performer.getActionPoints()));
    }
    
    private int calculateOverwatchChance() {
        int baseChance = 70; // Base overwatch chance
        int accuracyBonus = performer.getAccuracy() / 10; // Accuracy contributes to overwatch
        
        int finalChance = Math.max(50, Math.min(95, baseChance + accuracyBonus));
        
        // Log overwatch chance calculation
        GameLogManager.logDebug("OVERWATCH", String.format("Chance calculation: Base(%d) + Accuracy Bonus(%d) = %d%%", 
            baseChance, accuracyBonus, finalChance));
        
        return finalChance;
    }
}

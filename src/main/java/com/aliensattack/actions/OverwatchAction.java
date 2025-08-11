package com.aliensattack.actions;

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
        return performer.canTakeActions();
    }
    
    @Override
    protected void executeActionLogic() {
        // Set overwatch state
        performer.setOverwatching(true);
        
        // Calculate overwatch chance based on unit stats
        int overwatchChance = calculateOverwatchChance();
        performer.setOverwatchChance(overwatchChance);
        
        setSuccess(true, String.format("%s ведет наблюдение (AP spent: %d, Overwatch: %d%%)",
                performer.getName(), actualActionPointCost, overwatchChance));
    }
    
    private void calculateActualCost() {
        // Overwatch costs all remaining action points
        actualActionPointCost = (int)performer.getActionPoints();
    }
    
    private int calculateOverwatchChance() {
        int baseChance = 70; // Base overwatch chance
        int accuracyBonus = performer.getAccuracy() / 10; // Accuracy contributes to overwatch
        
        return Math.max(50, Math.min(95, baseChance + accuracyBonus));
    }
}

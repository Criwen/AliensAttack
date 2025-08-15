package com.aliensattack.actions;

import com.aliensattack.core.GameLogManager;
import com.aliensattack.core.interfaces.IUnit;

/**
 * Action for healing a target unit
 */
public class HealAction extends BaseAction {
    
    private static final int BASE_HEAL_COST = 1;
    private static final int HEAL_AMOUNT = 25;
    
    public HealAction(IUnit performer, IUnit target) {
        super("HEAL", performer, target, BASE_HEAL_COST);
    }
    
    @Override
    protected boolean validateActionSpecificRequirements() {
        if (target == null) {
            GameLogManager.logActionValidation("HEAL", performer.getName(), false, "Target is null");
            return false;
        }
        if (!target.isAlive()) {
            GameLogManager.logActionValidation("HEAL", performer.getName(), false, "Target is not alive");
            return false;
        }
        if (!performer.canTakeActions()) {
            GameLogManager.logActionValidation("HEAL", performer.getName(), false, "Performer cannot take actions");
            return false;
        }
        
        // Log healing validation details
        GameLogManager.logActionValidation("HEAL", performer.getName(), true, 
            String.format("Healing validated - Target: %s, Current HP: %d/%d, Heal Amount: %d", 
                target.getName(), target.getCurrentHealth(), target.getMaxHealth(), HEAL_AMOUNT));
        
        return true;
    }
    
    @Override
    protected void executeActionLogic() {
        if (target == null) {
            setSuccess(false, "Цель для лечения не указана");
            GameLogManager.logActionExecution("HEAL", performer.getName(), "Healing failed - No target");
            return;
        }
        
        // Log healing attempt
        int targetHealthBefore = target.getCurrentHealth();
        GameLogManager.logActionExecution("HEAL", performer.getName(), 
            String.format("Attempting to heal %s - Current HP: %d/%d, Heal Amount: %d", 
                target.getName(), targetHealthBefore, target.getMaxHealth(), HEAL_AMOUNT));
        
        // Log unit status before healing
        GameLogManager.logUnitAction(target.getName(), "Pre-Healing Status", 
            String.format("Health: %d/%d, Position: %s", 
                target.getCurrentHealth(), target.getMaxHealth(), target.getPosition()));
        
        // Perform healing
        target.heal(HEAL_AMOUNT);
        int targetHealthAfter = target.getCurrentHealth();
        int actualHealAmount = targetHealthAfter - targetHealthBefore;
        
        // Log successful healing
        setSuccess(true, String.format("%s лечит %s на %d HP (стоимость: %d AP)",
                performer.getName(), target.getName(), actualHealAmount, actualActionPointCost));
        
        // Log unit health change
        GameLogManager.logUnitHealthChange(target.getName(), targetHealthBefore, targetHealthAfter, 
            actualHealAmount, "Healing by " + performer.getName());
        
        // Log unit status after healing
        GameLogManager.logUnitAction(target.getName(), "Post-Healing Status", 
            String.format("Health: %d/%d, Healed: %d HP", 
                target.getCurrentHealth(), target.getMaxHealth(), actualHealAmount));
        
        // Log turn progression for healing actions
        GameLogManager.logTurnProgression(0, "Healing", performer.getName(), 
            String.format("Healed %s for %d HP", target.getName(), actualHealAmount));
        
        // Log mission objective progress if healing critical units
        if (targetHealthBefore < target.getMaxHealth() * 0.3 && targetHealthAfter > target.getMaxHealth() * 0.5) {
            GameLogManager.logMissionObjective("Unit Recovery", "IN_PROGRESS", 
                String.format("%s recovered from critical health", target.getName()));
        }
        
        // Log inventory operation (assuming medikit is consumed)
        GameLogManager.logInventoryOperation(performer.getName(), "USED", "Medikit", 1);
    }
}

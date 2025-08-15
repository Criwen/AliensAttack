package com.aliensattack.actions;

import com.aliensattack.core.GameLogManager;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.data.StatusEffectData;

/**
 * Action for taking a defensive position
 */
public class DefendAction extends BaseAction {
    
    private static final int BASE_DEFEND_COST = 1;
    private static final int DEFENSE_BONUS = 20;
    private static final int DEFENSE_DURATION = 2;
    
    public DefendAction(IUnit performer) {
        super("DEFEND", performer, BASE_DEFEND_COST);
    }
    
    @Override
    protected boolean validateActionSpecificRequirements() {
        if (!performer.canTakeActions()) {
            GameLogManager.logActionValidation("DEFEND", performer.getName(), false, "Unit cannot take actions");
            return false;
        }
        
        // Log defensive position validation
        GameLogManager.logActionValidation("DEFEND", performer.getName(), true, 
            String.format("Defensive position validated - Current Defense: %d, Bonus: +%d%%, Duration: %d turns", 
                performer.getDefense(), DEFENSE_BONUS, DEFENSE_DURATION));
        
        return true;
    }
    
    @Override
    protected void executeActionLogic() {
        // Log defense attempt
        GameLogManager.logActionExecution("DEFEND", performer.getName(), 
            String.format("Taking defensive position - Current Defense: %d, Will gain: +%d%%", 
                performer.getDefense(), DEFENSE_BONUS));
        
        // Log unit status before defense
        GameLogManager.logUnitAction(performer.getName(), "Pre-Defense Status", 
            String.format("Defense: %d, Position: %s, Status Effects: %d", 
                performer.getDefense(), performer.getPosition(), performer.getStatusEffects().size()));
        
        // Add defense status effect
        StatusEffectData defenseEffect = new StatusEffectData(
            StatusEffect.PROTECTED, 
            DEFENSE_DURATION, 
            DEFENSE_BONUS
        );
        performer.addStatusEffect(defenseEffect);
        
        // Log status effect application
        GameLogManager.logUnitStatusEffect(performer.getName(), "DEFENSE", 
            String.format("Protected (+%d%% defense for %d turns)", DEFENSE_BONUS, DEFENSE_DURATION), true);
        
        // Log successful defense
        setSuccess(true, String.format("%s занимает оборонительную позицию (+%d%% защиты на %d хода, стоимость: %d AP)",
                performer.getName(), DEFENSE_BONUS, DEFENSE_DURATION, actualActionPointCost));
        
        // Log unit status after defense
        GameLogManager.logUnitAction(performer.getName(), "Post-Defense Status", 
            String.format("Defense: %d, Status Effects: %d", 
                performer.getDefense(), performer.getStatusEffects().size()));
        
        // Log turn progression for defensive actions
        GameLogManager.logTurnProgression(0, "Defense", performer.getName(), 
            "Defensive position established");
        
        // Log combat event
        GameLogManager.logCombatEvent("Defense Established", 
            String.format("%s established defensive position (+%d%% defense)", performer.getName(), DEFENSE_BONUS));
    }
}

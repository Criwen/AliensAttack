package com.aliensattack.actions;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.model.StatusEffectData;

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
        return performer.canTakeActions();
    }
    
    @Override
    protected void executeActionLogic() {
        // Add defense status effect
        StatusEffectData defenseEffect = new StatusEffectData(
            StatusEffect.PROTECTED, 
            DEFENSE_DURATION, 
            DEFENSE_BONUS
        );
        performer.addStatusEffect(defenseEffect);
        
        setSuccess(true, String.format("%s занимает оборонительную позицию (+%d%% защиты на %d хода, стоимость: %d AP)",
                performer.getName(), DEFENSE_BONUS, DEFENSE_DURATION, actualActionPointCost));
    }
}

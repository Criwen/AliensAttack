package com.aliensattack.actions;

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
        return target != null && target.isAlive() && performer.canTakeActions();
    }
    
    @Override
    protected void executeActionLogic() {
        if (target == null) {
            setSuccess(false, "Цель для лечения не указана");
            return;
        }
        
        // Perform healing
        target.heal(HEAL_AMOUNT);
        setSuccess(true, String.format("%s лечит %s на %d HP (стоимость: %d AP)",
                performer.getName(), target.getName(), HEAL_AMOUNT, actualActionPointCost));
    }
}

package com.aliensattack.actions;

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
        return performer.canTakeActions();
    }
    
    @Override
    protected void executeActionLogic() {
        // Simple reload implementation
        setSuccess(true, String.format("%s перезаряжается (стоимость: %d AP)",
                performer.getName(), actualActionPointCost));
    }
}

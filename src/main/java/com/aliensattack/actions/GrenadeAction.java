package com.aliensattack.actions;

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
        return targetPosition != null && performer.canTakeActions();
    }
    
    @Override
    protected void executeActionLogic() {
        if (targetPosition == null) {
            setSuccess(false, "Целевая позиция для гранаты не указана");
            return;
        }
        
        // Set damage for area effect
        setDamage(GRENADE_DAMAGE);
        setSuccess(true, String.format("%s бросает гранату в позицию %s и наносит %d урона по области (стоимость: %d AP)",
                performer.getName(), targetPosition, GRENADE_DAMAGE, actualActionPointCost));
    }
}

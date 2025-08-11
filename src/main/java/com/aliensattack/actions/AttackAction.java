package com.aliensattack.actions;

import com.aliensattack.core.interfaces.IUnit;

/**
 * Action for attacking a target unit
 */
public class AttackAction extends BaseAction {
    
    private static final int BASE_ATTACK_COST = 1;
    
    public AttackAction(IUnit performer, IUnit target) {
        super("ATTACK", performer, target, BASE_ATTACK_COST);
    }
    
    @Override
    protected boolean validateActionSpecificRequirements() {
        return target != null && target.isAlive() && performer.canAttack();
    }
    
    @Override
    protected void executeActionLogic() {
        if (target == null) {
            setSuccess(false, "Цель не указана");
            return;
        }
        
        // Simple attack calculation
        int hitChance = calculateHitChance();
        boolean hit = Math.random() * 100 < hitChance;
        
        if (hit) {
            int calculatedDamage = calculateDamage();
            boolean killed = target.takeDamage(calculatedDamage);
            setDamage(calculatedDamage);
            setSuccess(true, String.format("%s атакует %s и наносит %d урона%s",
                    performer.getName(), target.getName(), calculatedDamage,
                    killed ? " (убит!)" : ""));
        } else {
            setSuccess(false, String.format("%s промахивается по %s", 
                    performer.getName(), target.getName()));
        }
    }
    
    private int calculateHitChance() {
        int baseChance = 70; // Base hit chance
        int accuracyBonus = performer.getAccuracy();
        int defensePenalty = target.getDefense();
        
        return Math.max(5, Math.min(95, baseChance + accuracyBonus - defensePenalty));
    }
    
    private int calculateDamage() {
        int baseDamage = performer.getAttackDamage();
        // Could add critical hit logic here
        return baseDamage;
    }
}

package com.aliensattack.actions;

import com.aliensattack.core.GameLogManager;
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
        if (target == null) {
            GameLogManager.logActionValidation("ATTACK", performer.getName(), false, "Target is null");
            return false;
        }
        if (!target.isAlive()) {
            GameLogManager.logActionValidation("ATTACK", performer.getName(), false, "Target is not alive");
            return false;
        }
        if (!performer.canAttack()) {
            GameLogManager.logActionValidation("ATTACK", performer.getName(), false, "Performer cannot attack");
            return false;
        }
        
        // Log target status for validation
        GameLogManager.logUnitAction(target.getName(), "Attack Target Validation", 
            String.format("Alive: %s, Health: %d/%d, Position: %s", 
                target.isAlive(), target.getCurrentHealth(), target.getMaxHealth(), target.getPosition()));
        
        GameLogManager.logActionValidation("ATTACK", performer.getName(), true, "All attack requirements met");
        return true;
    }
    
    @Override
    protected void executeActionLogic() {
        if (target == null) {
            setSuccess(false, "Цель не указана");
            return;
        }
        
        // Log attack attempt details
        GameLogManager.logActionExecution("ATTACK", performer.getName(), 
            String.format("Attempting attack on %s (HP: %d/%d)", 
                target.getName(), target.getCurrentHealth(), target.getMaxHealth()));
        
        // Log combat event
        GameLogManager.logCombatEvent("Attack Initiated", 
            String.format("%s attacking %s", performer.getName(), target.getName()));
        
        // Simple attack calculation
        int hitChance = calculateHitChance();
        boolean hit = Math.random() * 100 < hitChance;
        
        // Log hit calculation
        GameLogManager.logDebug("ATTACK", String.format("Hit chance: %d%%, Roll result: %s", 
            hitChance, hit ? "HIT" : "MISS"));
        
        if (hit) {
            int calculatedDamage = calculateDamage();
            int targetHealthBefore = target.getCurrentHealth();
            boolean killed = target.takeDamage(calculatedDamage);
            int targetHealthAfter = target.getCurrentHealth();
            
            setDamage(calculatedDamage);
            setSuccess(true, String.format("%s атакует %s и наносит %d урона%s",
                    performer.getName(), target.getName(), calculatedDamage,
                    killed ? " (убит!)" : ""));
            
            // Log successful attack
            GameLogManager.logActionExecution("ATTACK", performer.getName(), 
                String.format("Hit successful! Damage: %d, Target HP: %d -> %d", 
                    calculatedDamage, targetHealthBefore, targetHealthAfter));
            
            // Log unit health change
            GameLogManager.logUnitHealthChange(target.getName(), targetHealthBefore, targetHealthAfter, 
                -(targetHealthBefore - targetHealthAfter), "Attack by " + performer.getName());
            
            // Log unit destruction if killed
            if (killed) {
                GameLogManager.logUnitDestruction(target.getName(), "Combat Death", 
                    String.format("Killed by %s's attack (Damage: %d)", performer.getName(), calculatedDamage));
            }
            
            // Log combat event
            GameLogManager.logCombatEvent("Attack Successful", 
                String.format("%s hit %s for %d damage", performer.getName(), target.getName(), calculatedDamage));
            
        } else {
            setSuccess(false, String.format("%s промахивается по %s", 
                    performer.getName(), target.getName()));
            
            // Log missed attack
            GameLogManager.logActionExecution("ATTACK", performer.getName(), 
                String.format("Attack missed target %s", target.getName()));
            
            // Log combat event
            GameLogManager.logCombatEvent("Attack Missed", 
                String.format("%s missed %s", performer.getName(), target.getName()));
        }
        
        // Log turn progression for combat actions
        GameLogManager.logTurnProgression(0, "Combat Resolution", performer.getName(), 
            String.format("Attack %s - %s", hit ? "hit" : "missed", target.getName()));
    }
    
    private int calculateHitChance() {
        int baseChance = 70; // Base hit chance
        int accuracyBonus = performer.getAccuracy();
        int defensePenalty = target.getDefense();
        
        int finalChance = Math.max(5, Math.min(95, baseChance + accuracyBonus - defensePenalty));
        
        // Log hit chance calculation
        GameLogManager.logDebug("ATTACK", String.format("Hit chance calculation: Base(%d) + Accuracy(%d) - Defense(%d) = %d%%", 
            baseChance, accuracyBonus, defensePenalty, finalChance));
        
        // Log unit stats for debugging
        GameLogManager.logUnitAction(performer.getName(), "Attack Stats", 
            String.format("Accuracy: %d, Base Damage: %d", performer.getAccuracy(), performer.getAttackDamage()));
        GameLogManager.logUnitAction(target.getName(), "Defense Stats", 
            String.format("Defense: %d, Current Health: %d/%d", target.getDefense(), 
                target.getCurrentHealth(), target.getMaxHealth()));
        
        return finalChance;
    }
    
    private int calculateDamage() {
        int baseDamage = performer.getAttackDamage();
        // Could add critical hit logic here
        return baseDamage;
    }
}

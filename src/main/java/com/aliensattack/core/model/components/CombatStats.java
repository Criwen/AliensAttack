package com.aliensattack.core.model.components;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Component for unit combat statistics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombatStats {
    private int maxHealth;
    private int currentHealth;
    private int movementRange;
    private int attackRange;
    private int attackDamage;
    private int viewRange;
    private int height;
    private int initiative;
    private int criticalChance;
    private int criticalDamageMultiplier;
    private int overwatchChance;
    
    public boolean isAlive() {
        return currentHealth > 0;
    }
    
    public boolean hasActionPoints(double actionPoints) {
        return actionPoints > 0;
    }
    
    public boolean canMove(double actionPoints) {
        return actionPoints >= 0.5;
    }
    
    public boolean canAttack(double actionPoints) {
        return actionPoints >= 1.0;
    }
}

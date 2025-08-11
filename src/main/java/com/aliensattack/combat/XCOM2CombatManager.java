package com.aliensattack.combat;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * XCOM 2 specific combat manager
 * Implements XCOM 2 mechanics like flanking, cover, and tactical positioning
 */
public class XCOM2CombatManager extends BaseCombatManager {
    
    public XCOM2CombatManager(ITacticalField field) {
        super(field);
    }
    
    @Override
    protected int getUnitInitiative(IUnit unit) {
        // XCOM 2 initiative: base initiative + unit type bonus
        int baseInitiative = GameConfig.getInt("unit.initiative.base", 50);
        int unitTypeBonus = getUnitTypeInitiativeBonus(unit);
        return baseInitiative + unitTypeBonus;
    }
    
    @Override
    protected void resetUnitActionPoints() {
        for (IUnit unit : units) {
            if (unit.isAlive()) {
                unit.resetActionPoints();
            }
        }
    }
    
    @Override
    protected void processStatusEffects() {
        for (IUnit unit : units) {
            if (unit.isAlive()) {
                unit.processStatusEffects();
            }
        }
    }
    
    @Override
    protected void checkCombatEndConditions() {
        // Check for mission objectives, unit survival, etc.
        if (isCombatFinished()) {
            combatActive = false;
        }
    }
    
    /**
     * Get initiative bonus for unit type
     */
    private int getUnitTypeInitiativeBonus(IUnit unit) {
        return switch (unit.getUnitType().name()) {
            case "SOLDIER" -> 10;
            case "ALIEN" -> 5;
            case "ALIEN_RULER" -> 20;
            case "CHOSEN" -> 15;
            default -> 0;
        };
    }
    
    /**
     * Check if attacker is flanking target
     */
    public boolean isFlanking(IUnit attacker, IUnit target) {
        if (attacker == null || target == null) return false;
        
        // Simple flanking check - if target has cover, attacker might be flanking
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        
        // Check if attacker is positioned to the side or behind target
        int dx = Math.abs(attackerPos.getX() - targetPos.getX());
        int dy = Math.abs(attackerPos.getY() - targetPos.getY());
        
        return dx > 0 || dy > 0; // Not directly in front
    }
    
    /**
     * Get flanking bonus damage
     */
    public int getFlankingBonusDamage(int baseDamage) {
        return (int)(baseDamage * 0.2); // 20% bonus for flanking
    }
    
    /**
     * Process overwatch reactions
     */
    public void processOverwatchReactions(IUnit movingUnit, Position newPosition) {
        List<IUnit> overwatchingUnits = units.stream()
                .filter(IUnit::isOverwatching)
                .filter(unit -> canSeeUnit(unit, movingUnit))
                .toList();
        
        for (IUnit overwatcher : overwatchingUnits) {
            if (Math.random() * 100 < overwatcher.getOverwatchChance()) {
                // Trigger overwatch shot
                triggerOverwatchShot(overwatcher, movingUnit);
            }
        }
    }
    
    private void triggerOverwatchShot(IUnit overwatcher, IUnit target) {
        // Simple overwatch shot implementation
        int damage = overwatcher.getAttackDamage();
        boolean hit = target.takeDamage(damage);
        
        // Reset overwatch state
        overwatcher.setOverwatching(false);
        
        // Log the overwatch shot
        System.out.printf("%s overwatch shot at %s: %s (damage: %d)%n",
                overwatcher.getName(), target.getName(),
                hit ? "HIT" : "MISS", damage);
    }
} 
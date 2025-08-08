package com.aliensattack.combat;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Ultimate combat manager implementing iteration 1 XCOM 2 mechanics:
 * 1. Overwatch Ambush
 * 2. Height Advantage
 * 3. Grenade Launcher
 * 4. Medikit System
 * 5. Ammo Types
 */
@Getter
@Setter
public class UltimateXCOM2CombatManager extends AdvancedXCOM2CombatManager {
    
    private Random random;
    
    public UltimateXCOM2CombatManager(ITacticalField field) {
        super(field);
        this.random = ThreadLocalRandom.current();
    }
    
    // =============================================================================
    // 1. OVERWATCH AMBUSH
    // =============================================================================
    
    /**
     * Set up overwatch ambush with multiple units
     */
    public boolean setupOverwatchAmbush(List<Unit> ambushers) {
        if (ambushers.size() < 2) {
            return false; // Need at least 2 units for ambush
        }
        
        for (Unit unit : ambushers) {
            if (!unit.canTakeActions() || unit.getActionPoints() < 1) {
                return false;
            }
        }
        
        // Set all units to overwatch ambush mode
        for (Unit unit : ambushers) {
            unit.setOverwatchAmbush(true);
            unit.spendActionPoint();
        }
        
        return true;
    }
    
    /**
     * Execute overwatch ambush attack
     */
    public List<CombatResult> executeOverwatchAmbush(Unit target) {
        List<CombatResult> results = new ArrayList<>();
        List<Unit> ambushers = getAllUnits().stream()
            .filter(unit -> unit.isInOverwatchAmbush())
            .collect(java.util.stream.Collectors.toList());
        
        for (Unit ambusher : ambushers) {
            if (canAttack(ambusher, target)) {
                Weapon weapon = ambusher.getWeapon();
                if (weapon != null) {
                    // Apply ambush bonuses
                    int ambushAccuracy = weapon.getAccuracy() + ambusher.getOverwatchAmbushAccuracyBonus();
                    int ambushDamage = weapon.getBaseDamage() + ambusher.getOverwatchAmbushDamageBonus();
                    
                    CombatResult result = performAttackWithBonuses(ambusher, target, ambushAccuracy, ambushDamage);
                    results.add(result);
                }
            }
        }
        
        return results;
    }
    
    // =============================================================================
    // 2. HEIGHT ADVANTAGE
    // =============================================================================
    
    /**
     * Attack with height advantage
     */
    public CombatResult heightAdvantageAttack(Unit attacker, Unit target) {
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        
        if (!attackerPos.hasHeightAdvantageOver(targetPos)) {
            return new CombatResult(false, 0, "No height advantage");
        }
        
        Weapon weapon = attacker.getWeapon();
        if (weapon == null) {
            return new CombatResult(false, 0, "No weapon equipped");
        }
        
        // Apply height advantage bonuses
        int heightBonus = attackerPos.getHeightAdvantageBonus();
        int totalAccuracy = weapon.getAccuracy() + heightBonus;
        int totalDamage = weapon.getBaseDamage() + (heightBonus / 10); // +1 damage per 10% height bonus
        
        return performAttackWithBonuses(attacker, target, totalAccuracy, totalDamage);
    }
    
    /**
     * Check if unit has height advantage over target
     */
    public boolean hasHeightAdvantage(Unit attacker, Unit target) {
        return attacker.getPosition().hasHeightAdvantageOver(target.getPosition());
    }
    
    // =============================================================================
    // 3. GRENADE LAUNCHER
    // =============================================================================
    
    /**
     * Attack with grenade launcher
     */
    public List<CombatResult> grenadeLauncherAttack(Unit attacker, Position targetPos) {
        Weapon weapon = attacker.getWeapon();
        if (weapon == null || !weapon.isGrenadeLauncher()) {
            return new ArrayList<>();
        }
        
        List<CombatResult> results = new ArrayList<>();
        int areaRadius = weapon.getGrenadeLauncherAreaRadius();
        
        // Find all units in grenade launcher area
        List<Unit> unitsInArea = getAllUnits().stream()
            .filter(unit -> unit.isAlive() && targetPos.getDistanceTo(unit.getPosition()) <= areaRadius)
            .collect(java.util.stream.Collectors.toList());
        
        for (Unit target : unitsInArea) {
            if (!target.equals(attacker)) {
                CombatResult result = performAttack(attacker, target);
                results.add(result);
            }
        }
        
        attacker.spendActionPoint();
        weapon.useAmmo();
        
        return results;
    }
    
    // =============================================================================
    // 4. MEDIKIT SYSTEM
    // =============================================================================
    
    /**
     * Use medikit on target
     */
    public CombatResult useMedikit(Unit medic, Unit target, Medikit medikit) {
        if (!medic.canTakeActions() || medic.getActionPoints() < 1) {
            return new CombatResult(false, 0, "Medic cannot take actions");
        }
        
        if (!medikit.canUse()) {
            return new CombatResult(false, 0, "Medikit has no uses left");
        }
        
        int distance = medic.getPosition().getDistanceTo(target.getPosition());
        if (distance > medikit.getRange()) {
            return new CombatResult(false, 0, "Target out of range");
        }
        
        int healingAmount = medikit.use(target);
        if (healingAmount > 0) {
            medic.spendActionPoint();
            return new CombatResult(true, healingAmount, "Medikit used successfully!");
        }
        
        return new CombatResult(false, 0, "Failed to use medikit");
    }
    
    /**
     * Cure status effects with medikit
     */
    public CombatResult cureStatusEffects(Unit medic, Unit target, Medikit medikit) {
        if (!medic.canTakeActions() || medic.getActionPoints() < 1) {
            return new CombatResult(false, 0, "Medic cannot take actions");
        }
        
        if (!medikit.canUse()) {
            return new CombatResult(false, 0, "Medikit has no uses left");
        }
        
        if (medikit.cureStatusEffects(target)) {
            medic.spendActionPoint();
            return new CombatResult(true, 0, "Status effects cured!");
        }
        
        return new CombatResult(false, 0, "No status effects to cure");
    }
    
    // =============================================================================
    // 5. AMMO TYPES
    // =============================================================================
    
    /**
     * Attack with specific ammo type
     */
    public CombatResult ammoTypeAttack(Unit attacker, Unit target, AmmoType ammoType) {
        Weapon weapon = attacker.getWeapon();
        if (weapon == null) {
            return new CombatResult(false, 0, "No weapon equipped");
        }
        
        // Set ammo type
        weapon.setAmmoType(ammoType);
        
        // Get ammo type bonuses
        int totalAccuracy = weapon.getTotalAccuracy();
        int totalDamage = weapon.getTotalDamage();
        
        // Perform attack
        CombatResult result = performAttackWithBonuses(attacker, target, totalAccuracy, totalDamage);
        
        // Apply ammo type status effect
        StatusEffect statusEffect = weapon.getAmmoTypeStatusEffect();
        if (statusEffect != StatusEffect.NONE && result.isSuccess()) {
            target.addStatusEffect(new StatusEffectData(statusEffect, 2, 5));
        }
        
        return result;
    }
    
    /**
     * Get ammo type effectiveness against target
     */
    public int getAmmoTypeEffectiveness(AmmoType ammoType, Unit target) {
        switch (ammoType) {
            case ARMOR_PIERCING:
                return target.getArmor() != null && target.getArmor().getEffectiveDamageReduction() > 0 ? 20 : 0; // +20% vs armored targets
            case INCENDIARY:
                return target.hasStatusEffect(StatusEffect.BURNING) ? 0 : 15; // +15% vs non-burning
            case ACID:
                return target.hasStatusEffect(StatusEffect.ACID_BURN) ? 0 : 15; // +15% vs non-acid
            case STUN:
                return target.hasStatusEffect(StatusEffect.STUNNED) ? 0 : 25; // +25% vs non-stunned
            default:
                return 0;
        }
    }
    
    // =============================================================================
    // HELPER METHODS
    // =============================================================================
    
    /**
     * Perform attack with custom accuracy and damage
     */
    private CombatResult performAttackWithBonuses(Unit attacker, Unit target, int accuracy, int damage) {
        // Check hit
        int roll = random.nextInt(100) + 1;
        if (roll > accuracy) {
            return new CombatResult(false, 0, "Attack missed!");
        }
        
        // Apply damage
        boolean killed = target.takeDamageWithArmor(damage);
        
        String message = killed ? "Target killed!" : "Target hit!";
        return new CombatResult(true, damage, message);
    }
    
    // =============================================================================
    // ENHANCED TURN PROCESSING
    // =============================================================================
    
    @Override
    public void endTurn() {
        super.endTurn();
        
        // Process height advantage effects
        for (Unit unit : getAllUnits()) {
            if (unit.isAlive()) {
                // Apply height-based bonuses
                int heightBonus = unit.getPosition().getHeightAdvantageBonus();
                if (heightBonus > 0) {
                    // Units on high ground get defensive bonuses
                    // Height advantage provides natural defense
                }
            }
        }
    }
} 
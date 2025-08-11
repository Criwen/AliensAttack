package com.aliensattack.combat;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Final combat manager implementing additional XCOM 2 mechanics:
 * 1. Bladestorm
 * 2. Bluescreen Protocol
 * 3. Volatile Mix
 * 4. Rapid Fire
 * 5. Deep Cover
 */
@Getter
@Setter
public class FinalXCOM2CombatManager extends UltimateXCOM2CombatManager {
    private static final Logger log = LogManager.getLogger(FinalXCOM2CombatManager.class);
    
    private Random random;
    
    public FinalXCOM2CombatManager(ITacticalField field) {
        super(field);
        this.random = ThreadLocalRandom.current();
    }
    
    // =============================================================================
    // 1. BLADESTORM
    // =============================================================================
    
    /**
     * Trigger Bladestorm attack when enemy moves nearby
     */
    public List<CombatResult> triggerBladestorm(Unit bladestormUnit, Unit movingEnemy) {
        List<CombatResult> results = new ArrayList<>();
        
        if (!bladestormUnit.hasBladestorm()) {
            return results;
        }
        
        // Check if enemy moved within Bladestorm range
        int distance = bladestormUnit.getPosition().getDistanceTo(movingEnemy.getPosition());
        if (distance <= 1) { // Adjacent tiles
            int bladestormDamage = bladestormUnit.getBladestormDamage();
            int bladestormAccuracy = bladestormUnit.getBladestormAccuracy();
            
            CombatResult result = performAttackWithBonuses(bladestormUnit, movingEnemy, bladestormAccuracy, bladestormDamage);
            results.add(result);
            
            // Trigger the Bladestorm ability
            bladestormUnit.triggerBladestorm(movingEnemy);
        }
        
        return results;
    }
    
    /**
     * Check if unit can trigger Bladestorm
     */
    public boolean canTriggerBladestorm(Unit unit) {
        return unit.hasBladestorm();
    }
    
    // =============================================================================
    // 2. BLUESCREEN PROTOCOL
    // =============================================================================
    
    /**
     * Attack with Bluescreen weapon
     */
    public CombatResult bluescreenAttack(Unit attacker, Unit target) {
        Weapon weapon = attacker.getWeapon();
        if (weapon == null || !weapon.isBluescreen()) {
            return new CombatResult(false, 0, "No Bluescreen weapon equipped");
        }
        
        // Check if target is robotic
        if (target.getUnitType() != UnitType.ALIEN) {
            return new CombatResult(false, 0, "Target is not robotic");
        }
        
        // Apply Bluescreen bonuses
        int totalDamage = weapon.getTotalBluescreenDamage();
        int totalAccuracy = weapon.getTotalBluescreenAccuracy();
        
        return performAttackWithBonuses(attacker, target, totalAccuracy, totalDamage);
    }
    
    /**
     * Check if weapon is effective against robotic units
     */
    public boolean isEffectiveVsRobotics(Weapon weapon) {
        return weapon != null && weapon.isBluescreen();
    }
    
    // =============================================================================
    // 3. VOLATILE MIX
    // =============================================================================
    
    /**
     * Detonate Volatile Mix explosive
     */
    public List<CombatResult> detonateVolatileMix(Unit detonator, Explosive explosive, Position targetPos) {
        List<CombatResult> results = new ArrayList<>();
        
        if (!explosive.isVolatileMix()) {
            return results;
        }
        
        // Trigger chain reaction
        explosive.triggerChainReaction();
        
        // Find all units in chain reaction area
        int chainRadius = explosive.getVolatileMixChainRadius();
        List<Unit> allUnits = getAllUnits();
        if (allUnits == null) {
            return results;
        }
        List<Unit> unitsInArea = allUnits.stream()
            .filter(unit -> unit.isAlive() && targetPos.getDistanceTo(unit.getPosition()) <= chainRadius)
            .collect(java.util.stream.Collectors.toList());
        
        for (Unit target : unitsInArea) {
            if (!target.equals(detonator)) {
                int chainDamage = explosive.getVolatileMixChainDamage();
                CombatResult result = new CombatResult(true, chainDamage, "Volatile Mix chain reaction!");
                results.add(result);
                
                target.takeDamage(chainDamage);
            }
        }
        
        detonator.spendActionPoint();
        explosive.detonate();
        
        return results;
    }
    
    /**
     * Check if explosive can trigger chain reaction
     */
    public boolean canTriggerChainReaction(Explosive explosive) {
        return explosive != null && explosive.canTriggerChainReaction();
    }

    /**
     * Throw a grenade with radial damage falloff: maximum damage at center, minimum at edge
     */
    public List<CombatResult> throwGrenade(Unit thrower, Explosive explosive, Position targetPos) {
        List<CombatResult> results = new ArrayList<>();
        if (thrower == null || explosive == null || targetPos == null) {
            return results;
        }

        if (!thrower.canTakeActions() || thrower.getActionPoints() < 1) {
            return results;
        }

        List<Explosive> throwerExplosives = thrower.getExplosives();
        if (throwerExplosives == null || !throwerExplosives.contains(explosive)) {
            return results;
        }

        int radius = Math.max(0, explosive.getRadius());
        int baseDamage = Math.max(0, explosive.getDamage());
        log.info("Grenade thrown by {}: '{}' at {} (radius={}, baseDmg={})",
                thrower.getName(), explosive.getName(), targetPos, radius, baseDamage);

        List<Unit> allUnits = getAllUnits();
        if (allUnits == null) {
            return results;
        }
        
        for (Unit unit : allUnits) {
            if (unit == null || !unit.isAlive() || unit.equals(thrower) || unit.getPosition() == null) {
                continue;
            }
            int distance = targetPos.getDistanceTo(unit.getPosition());
            if (distance <= radius) {
                double factor = 1.0 - ((double) distance / Math.max(1, radius));
                factor = Math.max(0.25, Math.min(1.0, factor));
                int damage = (int) Math.round(baseDamage * factor);
                boolean killed = damage > 0 && unit.takeDamage(damage);
                String msg = (killed ? "Unit killed by grenade!" : (damage > 0 ? "Grenade hit!" : "Grenade effect"))
                        + " (dist=" + distance + ", dmg=" + damage + ")";
                results.add(new CombatResult(true, damage, msg));
                if (damage > 0) {
                    log.debug("Grenade affected {} at {}: dist={}, dmg={}, killed={}",
                            unit.getName(), unit.getPosition(), distance, damage, killed);
                } else {
                    log.trace("Grenade peripheral effect at {} (dist={})", unit.getPosition(), distance);
                }
            }
        }

        thrower.removeExplosive(explosive);
        thrower.spendActionPoint();
        explosive.detonate();
        log.info("Grenade resolved: {} results", results.size());
        return results;
    }
    
    // =============================================================================
    // 4. RAPID FIRE
    // =============================================================================
    
    /**
     * Attack with Rapid Fire
     */
    public List<CombatResult> rapidFireAttack(Unit attacker, Unit target) {
        List<CombatResult> results = new ArrayList<>();
        Weapon weapon = attacker.getWeapon();
        
        if (weapon == null || !weapon.canUseRapidFire()) {
            return results;
        }
        
        if (attacker.getActionPoints() < weapon.getRapidFireActionPointCost()) {
            return results;
        }
        
        int shotCount = weapon.getRapidFireShotCount();
        int rapidFireAccuracy = weapon.getRapidFireAccuracy();
        
        for (int i = 0; i < shotCount; i++) {
            CombatResult result = performAttackWithBonuses(attacker, target, rapidFireAccuracy, weapon.getBaseDamage());
            results.add(result);
        }
        
        attacker.spendActionPoint();
        weapon.useAmmo();
        
        return results;
    }
    
    /**
     * Check if unit can use Rapid Fire
     */
    public boolean canUseRapidFire(Unit unit) {
        Weapon weapon = unit.getWeapon();
        return weapon != null && weapon.canUseRapidFire() && unit.getActionPoints() >= weapon.getRapidFireActionPointCost();
    }
    
    // =============================================================================
    // 5. DEEP COVER
    // =============================================================================
    
    /**
     * Apply Deep Cover bonuses to unit
     */
    public void applyDeepCoverBonuses(Unit unit, CoverObject cover) {
        if (cover != null && cover.providesDeepCover()) {
            // Apply deep cover bonuses
            int deepCoverDefenseBonus = cover.getDeepCoverDefenseBonus();
            int deepCoverDodgeBonus = cover.getDeepCoverDodgeBonus();
            
            // Apply defense bonus to armor if available
            if (unit.hasArmor()) {
                Armor armor = unit.getArmor();
                // Increase armor's effective damage reduction
                int currentReduction = armor.getEffectiveDamageReduction();
                armor.setDamageReduction(currentReduction + deepCoverDefenseBonus);
            }
            
            // Apply dodge bonus to unit's dodge chance
            int currentDodge = unit.getDodgeChance();
            unit.setDodgeChance(currentDodge + deepCoverDodgeBonus);
            
            System.out.println(unit.getName() + " gained Deep Cover bonuses: +" + deepCoverDefenseBonus + " defense, +" + deepCoverDodgeBonus + " dodge");
        }
    }
    
    /**
     * Attack unit in Deep Cover
     */
    public CombatResult attackDeepCoverUnit(Unit attacker, Unit target) {
        // Find cover object protecting target
        CoverObject cover = findCoverForUnit(target);
        
        if (cover != null && cover.providesDeepCover()) {
            // Apply deep cover penalties to attacker
            int deepCoverPenalty = cover.getDeepCoverDodgeBonus();
            int modifiedAccuracy = attacker.getWeapon().getAccuracy() - deepCoverPenalty;
            
            return performAttackWithBonuses(attacker, target, modifiedAccuracy, attacker.getWeapon().getBaseDamage());
        }
        
        return performAttack(attacker, target);
    }
    
    /**
     * Check if unit is in Deep Cover
     */
    public boolean isInDeepCover(Unit unit) {
        CoverObject cover = findCoverForUnit(unit);
        return cover != null && cover.providesDeepCover();
    }
    
    // =============================================================================
    // HELPER METHODS
    // =============================================================================
    
    /**
     * Find cover object protecting a unit
     */
    private CoverObject findCoverForUnit(Unit unit) {
        // This would need to be implemented based on your cover system
        // For now, return null as placeholder
        return null;
    }
    
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
        
        // Process reactive ability cooldowns
        List<Unit> allUnits = getAllUnits();
        if (allUnits != null) {
            for (Unit unit : allUnits) {
                unit.processReactiveAbilityCooldowns();
            }
        }
    }
} 
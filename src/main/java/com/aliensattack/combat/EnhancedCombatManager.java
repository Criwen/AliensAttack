package com.aliensattack.combat;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.OptimizedTacticalField;

import lombok.Getter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Enhanced combat manager with XCOM 2 mechanics:
 * - Critical hits
 * - Overwatch system
 * - Height advantages
 * - Status effects
 * - Area of effect attacks
 */
@Getter
public class EnhancedCombatManager extends OptimizedCombatManager {
    private final Map<String, List<Unit>> overwatchTargets; // Units that can be overwatched
    private final Random random;
    
    public EnhancedCombatManager(OptimizedTacticalField field) {
        super(field);
        this.overwatchTargets = new ConcurrentHashMap<>();
        this.random = new Random();
    }
    
    /**
     * Enhanced attack with critical hits and status effects
     */
    public CombatResult attackWithCritical(Unit attacker, Unit target) {
        if (!attacker.canAttack() || !attacker.canTakeActions()) {
            return new CombatResult(false, 0, "Cannot attack");
        }
        
        if (!target.isAlive()) {
            return new CombatResult(false, 0, "Target is already dead");
        }
        
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        
        int distance = Math.abs(attackerPos.getX() - targetPos.getX()) + 
                      Math.abs(attackerPos.getY() - targetPos.getY());
        
        if (distance > attacker.getAttackRange()) {
            return new CombatResult(false, 0, "Target out of range");
        }
        
        // Calculate hit chance with height and cover bonuses
        int baseHitChance = attacker.getWeapon() != null ? attacker.getWeapon().getAccuracy() : 80;
        int heightBonus = calculateHeightBonus(attacker, target);
        CoverType targetCover = ((OptimizedTacticalField)getField()).getCoverTypeAt(targetPos.getX(), targetPos.getY());
        int coverBonus = calculateCoverBonus(targetCover);
        int finalHitChance = baseHitChance + heightBonus - coverBonus;
        
        // Apply status effect modifiers
        finalHitChance = applyStatusEffectModifiers(attacker, finalHitChance);
        
        // Roll for hit
        int roll = random.nextInt(100) + 1;
        boolean hit = roll <= finalHitChance;
        
        CombatResult result;
        if (hit) {
            // Check for critical hit
            boolean isCritical = checkCriticalHit(attacker);
            int damage = calculateDamage(attacker, isCritical);
            
            // Apply status effects
            applyStatusEffects(attacker, target);
            
            boolean killed = target.takeDamage(damage);
            attacker.spendActionPoint();
            
            String message = isCritical ? "Critical hit! " : "";
            message += killed ? "Target killed!" : "Target hit!";
            result = new CombatResult(true, damage, message);
        } else {
            result = new CombatResult(false, 0, "Attack missed!");
        }
        
        return result;
    }
    
    /**
     * Area of effect attack (grenades, rockets)
     */
    public List<CombatResult> areaOfEffectAttack(Unit attacker, Position targetPos, Weapon weapon) {
        if (!attacker.canAttack() || !attacker.canTakeActions()) {
            return List.of(new CombatResult(false, 0, "Cannot attack"));
        }
        
        if (!weapon.isAreaOfEffect()) {
            return List.of(new CombatResult(false, 0, "Weapon is not area of effect"));
        }
        
        List<CombatResult> results = new ArrayList<>();
        int areaRadius = weapon.getAreaRadius();
        
        // Get all units in area
        List<Unit> unitsInArea = ((OptimizedTacticalField)getField()).getUnitsInRangeOptimized(targetPos, areaRadius);
        
        for (Unit target : unitsInArea) {
            if (target.isAlive()) {
                int damage = weapon.getBaseDamage();
                boolean killed = target.takeDamage(damage);
                
                String message = killed ? "Target killed by AoE!" : "Target hit by AoE!";
                results.add(new CombatResult(true, damage, message));
            }
        }
        
        attacker.spendActionPoint();
        weapon.useAmmo();
        
        return results;
    }
    
    /**
     * Set unit to overwatch mode
     */
    public boolean setOverwatch(Unit unit) {
        if (!unit.canAttack() || !unit.canTakeActions()) {
            return false;
        }
        
        unit.setOverwatching(true);
        unit.spendActionPoint();
        return true;
    }
    
    /**
     * Process overwatch shots when enemy moves
     */
    public List<CombatResult> processOverwatch(Unit movingUnit) {
        List<CombatResult> results = new ArrayList<>();
        
        for (Unit overwatcher : getPlayerUnits()) {
            if (overwatcher.isOverwatching() && overwatcher.isAlive()) {
                // Check if moving unit is visible to overwatcher
                if (isUnitVisible(overwatcher, movingUnit)) {
                    int roll = random.nextInt(100) + 1;
                    if (roll <= overwatcher.getOverwatchChance()) {
                        CombatResult result = attackWithCritical(overwatcher, movingUnit);
                        results.add(result);
                    }
                }
            }
        }
        
        return results;
    }
    
    /**
     * Apply status effects to units
     */
    public void applyStatusEffects(Unit source, Unit target) {
        // Apply bleeding effect (10% chance)
        if (random.nextInt(100) < 10) {
            target.addStatusEffect(new StatusEffectData(StatusEffect.BLEEDING, 3, 2));
        }
        
        // Apply stun effect (5% chance)
        if (random.nextInt(100) < 5) {
            target.addStatusEffect(new StatusEffectData(StatusEffect.STUNNED, 1, 0));
        }
    }
    
    /**
     * Process status effects for all units
     */
    public void processAllStatusEffects() {
        for (Unit unit : getAllUnits()) {
            if (unit.isAlive()) {
                unit.processStatusEffects();
                
                // Apply damage from status effects
                for (StatusEffectData effect : unit.getStatusEffects()) {
                    if (effect.isActive()) {
                        switch (effect.getEffect()) {
                            case BLEEDING:
                            case BURNING:
                            case POISONED:
                            case ACID_BURN:
                            case ELECTROCUTED:
                            case RADIATION:
                            case CORROSION:
                            case FROSTBITE:
                            case BOUND:
                            case FREEZING:
                                unit.takeDamage(effect.getIntensity());
                                break;
                            case STUNNED:
                            case FROZEN:
                            case GRAPPLED:
                            case KNOCKED_DOWN:
                            case KNOCKED_BACK:
                            case GRENADED:
                            case FLASHBANGED:
                            case SMOKED:
                            case HIDDEN:
                            case REVEALED:
                            case PANICKED:
                            case HEALING:
                            case PROTECTED:
                            case EXPOSED:
                            case VULNERABLE:
                            case RESISTANT:
                            case IMMUNE:
                            case WOUNDED:
                            case CAPTURED:
                            case EXTRACTED:
                            case DEAD:
                            case MUTATION_RISK:
                            case ARMOR_DEGRADATION:
                            case WEAPON_MALFUNCTION:
                            case MARKED:
                            case OVERWATCH:
                            case SUPPRESSED:
                            case CONTROLLED:
                            case MIND_SHIELD:
                            case DOMINATED:
                            case PSYCHIC_SHIELD:
                            case MIND_MERGED:
                            case NONE:
                                // These effects don't cause damage over time
                                break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Calculate height advantage bonus
     */
    private int calculateHeightBonus(Unit attacker, Unit target) {
        int heightDiff = attacker.getHeight() - target.getHeight();
        if (heightDiff > 0) {
            return heightDiff * 10; // +10% per height level
        }
        return 0;
    }
    
    /**
     * Check for critical hit
     */
    private boolean checkCriticalHit(Unit attacker) {
        int totalCritChance = attacker.getTotalCriticalChance();
        int roll = random.nextInt(100) + 1;
        return roll <= totalCritChance;
    }
    
    /**
     * Calculate damage with critical multiplier
     */
    private int calculateDamage(Unit attacker, boolean isCritical) {
        int baseDamage = attacker.getWeapon() != null ? 
            attacker.getWeapon().getBaseDamage() : attacker.getAttackDamage();
        
        if (isCritical) {
            return baseDamage * attacker.getCriticalDamageMultiplier();
        }
        return baseDamage;
    }
    
    /**
     * Apply status effect modifiers to hit chance
     */
    private int applyStatusEffectModifiers(Unit unit, int baseChance) {
        int modifiedChance = baseChance;
        
        if (unit.hasStatusEffect(StatusEffect.POISONED)) {
            modifiedChance -= 20; // Poisoned units have reduced accuracy
        }
        
        if (unit.hasStatusEffect(StatusEffect.MARKED)) {
            modifiedChance += 20; // Marked units are easier to hit
        }
        
        return Math.max(0, Math.min(100, modifiedChance));
    }
    
    /**
     * Calculate cover bonus
     */
    private int calculateCoverBonus(CoverType coverType) {
        return switch (coverType) {
            case FULL_COVER -> 40;
            case HALF_COVER -> 20;
            case LOW_COVER -> 10;
            default -> 0;
        };
    }
    
    /**
     * Check if unit is visible to observer
     */
    private boolean isUnitVisible(Unit observer, Unit target) {
        Object field = getField();
        if (field instanceof OptimizedTacticalField) {
            return ((OptimizedTacticalField) field).isPositionVisible(observer.getPosition(), target.getPosition(), observer.getViewRange());
        }
        return false;
    }
    
    @Override
    public void endTurnOptimized() {
        // Process status effects before ending turn
        processAllStatusEffects();
        
        // Clear overwatch for all units
        for (Unit unit : getAllUnits()) {
            unit.setOverwatching(false);
        }
        
        super.endTurnOptimized();
    }
} 
package com.aliensattack.combat;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.EnvironmentObject;
import com.aliensattack.core.model.SoldierAbility;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.core.enums.*;
import com.aliensattack.core.data.StatusEffectData;
import com.aliensattack.field.TacticalField;
import com.aliensattack.core.config.GameConfig;

import lombok.Getter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Advanced combat manager with additional XCOM 2 mechanics:
 * - Soldier classes and abilities
 * - Shot types and firing modes
 * - Destructible environment
 * - Gravity and fall damage
 * - Visibility and detection system
 */
@Getter
public class CombatManagerExtended extends EnhancedCombatManager {
    private final Map<String, EnvironmentObject> environmentObjects;
    private final Map<String, VisibilityType> unitVisibility;
    private final Random random;
    
    public CombatManagerExtended(TacticalField field) {
        super(field);
        this.environmentObjects = new ConcurrentHashMap<>();
        this.unitVisibility = new ConcurrentHashMap<>();
        this.random = new Random();
    }
    
    /**
     * Attack with specific shot type
     */
    public CombatResult attackWithShotType(Unit attacker, Unit target, ShotType shotType) {
        if (!attacker.canAttack() || !attacker.canTakeActions()) {
            return new CombatResult(false, 0, "Cannot attack");
        }
        
        if (!target.isAlive()) {
            return new CombatResult(false, 0, "Target is already dead");
        }
        
        // Check visibility
        if (!isTargetVisible(attacker, target)) {
            return new CombatResult(false, 0, "Target not visible");
        }
        
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        
        int distance = Math.abs(attackerPos.getX() - targetPos.getX()) + 
                      Math.abs(attackerPos.getY() - targetPos.getY());
        
        if (distance > attacker.getAttackRange()) {
            return new CombatResult(false, 0, "Target out of range");
        }
        
        // Calculate hit chance based on shot type
        int baseHitChance = calculateBaseHitChance(attacker, shotType);
        int heightBonus = calculateHeightBonus(attacker, target);
        CoverType targetCover = ((TacticalField)getField()).getCoverTypeAt(targetPos.getX(), targetPos.getY());
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
            int damage = calculateDamage(attacker, isCritical, shotType);
            
            // Apply status effects
            applyStatusEffects(attacker, target);
            
            // Check for environment destruction
            checkEnvironmentDestruction(targetPos, damage);
            
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
     * Suppressive fire - reduces target's accuracy
     */
    public boolean performSuppression(Unit attacker, Unit target) {
        if (!attacker.canAttack() || !attacker.canTakeActions()) {
            return false;
        }
        
        // Apply suppression effect
        target.addStatusEffect(new StatusEffectData(StatusEffect.MARKED, 2, 0));
        attacker.spendActionPoint();
        
        return true;
    }
    
    /**
     * Move unit with fall damage check
     */
    public boolean moveUnitWithFallCheck(Unit unit, int newX, int newY) {
        int currentHeight = unit.getHeight();
        
        // Check if movement involves height change
        EnvironmentObject targetObject = getEnvironmentObjectAt(newX, newY);
        int newHeight = targetObject != null ? targetObject.getHeight() : 0;
        
        if (currentHeight > newHeight && (currentHeight - newHeight) > 1) {
            // Unit is falling
            unit.applyFallDamage(currentHeight - newHeight);
        }
        
        boolean moved = moveUnitQuick(unit, newX, newY);
        if (moved) {
            unit.setHeight(newHeight);
        }
        
        return moved;
    }
    
    /**
     * Add environment object
     */
    public void addEnvironmentObject(EnvironmentObject object, int x, int y) {
        String key = x + "_" + y;
        environmentObjects.put(key, object);
    }
    
    /**
     * Get environment object at position
     */
    public EnvironmentObject getEnvironmentObjectAt(int x, int y) {
        String key = x + "_" + y;
        return environmentObjects.get(key);
    }
    
    /**
     * Check environment destruction
     */
    private void checkEnvironmentDestruction(Position position, int damage) {
        EnvironmentObject object = getEnvironmentObjectAt(position.getX(), position.getY());
        if (object != null && object.isDestructible()) {
            object.takeDamage(damage);
        }
    }
    
    /**
     * Calculate base hit chance based on shot type
     */
    private int calculateBaseHitChance(Unit attacker, ShotType shotType) {
        int baseChance = attacker.getWeapon() != null ? attacker.getWeapon().getAccuracy() : 80;
        
        return switch (shotType) {
            case STANDARD_SHOT -> baseChance + GameConfig.getShotAccuracyModifier("standard");
            case AIMED_SHOT -> baseChance + GameConfig.getShotAccuracyModifier("aimed");
            case RAPID_FIRE -> baseChance + GameConfig.getShotAccuracyModifier("rapid");
            case PISTOL_SHOT -> baseChance + GameConfig.getShotAccuracyModifier("pistol");
            case OVERWATCH -> baseChance + GameConfig.getShotAccuracyModifier("overwatch");
            case SUPPRESSION -> baseChance + GameConfig.getShotAccuracyModifier("suppression");
            default -> baseChance;
        };
    }
    
    /**
     * Calculate damage based on shot type
     */
    private int calculateDamage(Unit attacker, boolean isCritical, ShotType shotType) {
        int baseDamage = attacker.getWeapon() != null ? 
            attacker.getWeapon().getBaseDamage() : attacker.getAttackDamage();
        
        int finalDamage = baseDamage;
        
        // Apply shot type modifiers
        switch (shotType) {
            case STANDARD_SHOT -> finalDamage = (int)(baseDamage * GameConfig.getShotDamageModifier("standard"));
            case AIMED_SHOT -> finalDamage = (int)(baseDamage * GameConfig.getShotDamageModifier("aimed"));
            case RAPID_FIRE -> finalDamage = (int)(baseDamage * GameConfig.getShotDamageModifier("rapid"));
            case PISTOL_SHOT -> finalDamage = (int)(baseDamage * GameConfig.getShotDamageModifier("pistol"));
            case OVERWATCH -> finalDamage = (int)(baseDamage * GameConfig.getShotDamageModifier("overwatch"));
            case SUPPRESSION -> finalDamage = (int)(baseDamage * GameConfig.getShotDamageModifier("suppression"));
            default -> finalDamage = baseDamage;
        }
        
        if (isCritical) {
            finalDamage *= attacker.getCriticalDamageMultiplier();
        }
        
        return finalDamage;
    }
    
    /**
     * Calculate height advantage bonus
     */
    private int calculateHeightBonus(Unit attacker, Unit target) {
        int heightDiff = attacker.getHeight() - target.getHeight();
        if (heightDiff > 0) {
            return heightDiff * GameConfig.getHeightBonusPerLevel();
        }
        return 0;
    }
    
    /**
     * Calculate cover bonus
     */
    private int calculateCoverBonus(CoverType coverType) {
        return switch (coverType) {
            case FULL_COVER -> GameConfig.getCoverModifier("full");
            case HALF_COVER -> GameConfig.getCoverModifier("heavy");
            case LOW_COVER -> GameConfig.getCoverModifier("light");
            default -> GameConfig.getCoverModifier("none");
        };
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
     * Check for critical hit
     */
    private boolean checkCriticalHit(Unit attacker) {
        int totalCritChance = attacker.getTotalCriticalChance();
        int roll = random.nextInt(100) + 1;
        return roll <= totalCritChance;
    }
    
    /**
     * Check if target is visible to attacker
     */
    private boolean isTargetVisible(Unit attacker, Unit target) {
        // Check if target is concealed
        if (target.isConcealed()) {
            return false;
        }
        
        // Check visibility type
        VisibilityType targetVisibility = target.getVisibility();
        if (targetVisibility == VisibilityType.HIDDEN) {
            return false;
        }
        
        // Check line of sight
        Object field = getField();
        if (field instanceof TacticalField) {
            return ((TacticalField) field).isPositionVisible(attacker.getPosition(), target.getPosition(), attacker.getViewRange());
        }
        return false;
    }
    
    /**
     * Process soldier abilities
     */
    public void processSoldierAbilities() {
        for (Unit unit : getAllUnits()) {
            if (unit.isAlive() && unit.getSoldierClass() != null) {
                unit.processAbilityCooldowns();
            }
        }
    }
    
    /**
     * Use soldier ability
     */
    public boolean useSoldierAbility(Unit unit, String abilityName) {
        if (unit.getSoldierClass() == null) {
            return false;
        }
        
        for (SoldierAbility ability : unit.getAbilities()) {
            if (ability.getName().equals(abilityName) && ability.canUse()) {
                if (unit.getActionPoints() >= ability.getActionPointCost()) {
                    ability.use();
                    unit.spendActionPoint();
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public void endTurnRound() {
        // Process soldier abilities
        processSoldierAbilities();
        
        // Process fall damage recovery
        for (Unit unit : getAllUnits()) {
            if (unit.hasFallen()) {
                unit.setHasFallen(false); // Recover from fall
            }
        }
        
        super.endTurnCore();
    }
} 
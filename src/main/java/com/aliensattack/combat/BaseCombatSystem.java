package com.aliensattack.combat;

import com.aliensattack.combat.interfaces.ICombatSystem;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.core.enums.CoverType;
import com.aliensattack.core.enums.TerrainType;

import java.util.List;
import java.util.Random;

/**
 * Base implementation of the combat system
 * Provides core combat functionality that can be extended by specialized implementations
 */
public abstract class BaseCombatSystem implements ICombatSystem {
    
    protected Random random;
    protected boolean combatActive;
    protected String missionType;
    
    // Combat statistics
    protected int totalDamageDealt;
    protected int totalDamageTaken;
    protected int totalHits;
    protected int totalMisses;
    protected int totalCriticalHits;
    
    public BaseCombatSystem() {
        this.totalDamageDealt = 0;
        this.totalDamageTaken = 0;
        this.totalHits = 0;
        this.totalMisses = 0;
        this.totalCriticalHits = 0;
        
        // TODO: Implement comprehensive combat system initialization
        // - Load combat configuration
        // - Initialize damage calculation systems
        // - Set up critical hit systems
        // - Initialize status effect systems
        // - Set up combat modifiers
    }
    
    // Core combat resolution methods
    @Override
    public CombatResult resolveCombat(IUnit attacker, IUnit target, Position targetPosition) {
        if (!canUnitAttack(attacker) || !isUnitValidForCombat(target)) {
            return new CombatResult(false, 0, "Invalid combat action");
        }
        
        // Determine attack type and resolve accordingly
        if (isInRange(attacker, target, targetPosition)) {
            if (calculateDistance(attacker.getPosition(), targetPosition) <= 1) {
                return resolveMeleeAttack(attacker, target, targetPosition);
            } else {
                return resolveRangedAttack(attacker, target, targetPosition);
            }
        }
        
        return new CombatResult(false, 0, "Target out of range");
    }
    
    @Override
    public CombatResult resolveRangedAttack(IUnit attacker, IUnit target, Position targetPosition) {
        int hitChance = calculateHitChance(attacker, target, targetPosition);
        boolean hit = isHit(hitChance);
        
        if (!hit) {
            totalMisses++;
            return new CombatResult(false, 0, "Attack missed");
        }
        
        int criticalChance = calculateCriticalChance(attacker, target, targetPosition);
        boolean critical = isCritical(criticalChance);
        
        int damage = calculateDamage(attacker, target, critical);
        if (critical) {
            totalCriticalHits++;
        }
        
        totalHits++;
        totalDamageDealt += damage;
        
        // Apply damage to target
        boolean targetSurvived = target.takeDamage(damage);
        if (!targetSurvived) {
            totalDamageTaken += target.getMaxHealth();
        }
        
        String message = critical ? "Critical hit for " + damage + " damage!" : "Hit for " + damage + " damage";
        return new CombatResult(true, damage, message);
    }
    
    @Override
    public CombatResult resolveMeleeAttack(IUnit attacker, IUnit target, Position targetPosition) {
        // Melee attacks have higher accuracy but lower damage
        int hitChance = calculateHitChance(attacker, target, targetPosition) + 20;
        boolean hit = isHit(hitChance);
        
        if (!hit) {
            totalMisses++;
            return new CombatResult(false, 0, "Melee attack missed");
        }
        
        int damage = calculateDamage(attacker, target, false) - 2; // Melee does less damage
        totalHits++;
        totalDamageDealt += damage;
        
        boolean targetSurvived = target.takeDamage(damage);
        if (!targetSurvived) {
            totalDamageTaken += target.getMaxHealth();
        }
        
        return new CombatResult(true, damage, "Melee hit for " + damage + " damage");
    }
    
    @Override
    public CombatResult resolveOverwatchShot(IUnit attacker, IUnit target, Position targetPosition) {
        // Overwatch shots have reduced accuracy
        int hitChance = calculateHitChance(attacker, target, targetPosition) - 15;
        boolean hit = isHit(hitChance);
        
        if (!hit) {
            totalMisses++;
            return new CombatResult(false, 0, "Overwatch shot missed");
        }
        
        int damage = calculateDamage(attacker, target, false);
        totalHits++;
        totalDamageDealt += damage;
        
        boolean targetSurvived = target.takeDamage(damage);
        if (!targetSurvived) {
            totalDamageTaken += target.getMaxHealth();
        }
        
        return new CombatResult(true, damage, "Overwatch hit for " + damage + " damage");
    }
    
    // Accuracy and hit calculation
    @Override
    public int calculateHitChance(IUnit attacker, IUnit target, Position targetPosition) {
        int baseAccuracy = attacker.getAccuracy();
        int targetDefense = target.getDefense();
        int targetDodge = target.getDodgeChance();
        
        // Apply cover bonuses
        CoverType cover = getCoverAtPosition(targetPosition);
        int coverBonus = getCoverBonus(cover);
        
        // Apply height bonuses
        int heightBonus = getHeightBonus(getHeightDifference(attacker, target));
        
        // Apply flanking bonus
        int flankingBonus = isFlanking(attacker, target, targetPosition) ? 25 : 0;
        
        int finalAccuracy = baseAccuracy - targetDefense - targetDodge + coverBonus + heightBonus + flankingBonus;
        return Math.max(5, Math.min(95, finalAccuracy)); // Clamp between 5% and 95%
    }
    
    @Override
    public int calculateCriticalChance(IUnit attacker, IUnit target, Position targetPosition) {
        int baseCritical = attacker.getTotalCriticalChance();
        
        // Flanking increases critical chance
        if (isFlanking(attacker, target, targetPosition)) {
            baseCritical += 25;
        }
        
        // Height advantage increases critical chance
        if (hasHeightAdvantage(attacker, target)) {
            baseCritical += 15;
        }
        
        return Math.min(100, baseCritical);
    }
    
    @Override
    public boolean isHit(int hitChance) {
        return random.nextInt(100) < hitChance;
    }
    
    @Override
    public boolean isCritical(int criticalChance) {
        return random.nextInt(100) < criticalChance;
    }
    
    // Damage calculation
    @Override
    public int calculateDamage(IUnit attacker, IUnit target, boolean isCritical) {
        int baseDamage = attacker.getAttackDamage();
        
        if (isCritical) {
            baseDamage *= attacker.getCriticalDamageMultiplier();
        }
        
        // Apply terrain effects
        TerrainType terrain = getTerrainAtPosition(target.getPosition());
        int terrainBonus = getTerrainBonus(terrain);
        baseDamage += terrainBonus;
        
        return Math.max(1, baseDamage); // Minimum 1 damage
    }
    
    @Override
    public int calculateDamageWithCover(IUnit attacker, IUnit target, Position targetPosition, CoverType cover) {
        int baseDamage = calculateDamage(attacker, target, false);
        int coverReduction = getCoverBonus(cover);
        return Math.max(1, baseDamage - coverReduction);
    }
    
    @Override
    public int calculateDamageWithTerrain(IUnit attacker, IUnit target, Position targetPosition, TerrainType terrain) {
        int baseDamage = calculateDamage(attacker, target, false);
        int terrainBonus = getTerrainBonus(terrain);
        return Math.max(1, baseDamage + terrainBonus);
    }
    
    // Utility methods
    @Override
    public int calculateDistance(Position pos1, Position pos2) {
        if (pos1 == null || pos2 == null) return Integer.MAX_VALUE;
        
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return (int) Math.sqrt(dx * dx + dy * dy);
    }
    
    @Override
    public boolean isInRange(IUnit attacker, IUnit target, Position targetPosition) {
        int distance = calculateDistance(attacker.getPosition(), targetPosition);
        return distance <= attacker.getTotalAttackRange();
    }
    
    @Override
    public boolean hasLineOfSight(IUnit attacker, Position targetPosition) {
        // Simplified line of sight - can be enhanced with actual terrain checking
        return true;
    }
    
    @Override
    public boolean isFlanking(IUnit attacker, IUnit target, Position targetPosition) {
        // Simplified flanking calculation
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        
        if (attackerPos == null || targetPos == null) return false;
        
        // Check if attacker is on opposite side of target
        int dx = Math.abs(attackerPos.getX() - targetPos.getX());
        int dy = Math.abs(attackerPos.getY() - targetPos.getY());
        
        return dx <= 1 && dy <= 1; // Adjacent positions
    }
    
    @Override
    public int getHeightDifference(IUnit attacker, IUnit target) {
        return attacker.getHeight() - target.getHeight();
    }
    
    @Override
    public int getHeightBonus(int heightDifference) {
        if (heightDifference > 0) return 15; // Height advantage
        if (heightDifference < 0) return -10; // Height disadvantage
        return 0;
    }
    
    @Override
    public boolean hasHeightAdvantage(IUnit attacker, IUnit target) {
        return getHeightDifference(attacker, target) > 0;
    }
    
    // Combat state management
    @Override
    public void startCombat() {
        this.combatActive = true;
        resetCombatStatistics();
    }
    
    @Override
    public void endCombat() {
        this.combatActive = false;
    }
    
    @Override
    public boolean isCombatActive() {
        return combatActive;
    }
    
    @Override
    public void processCombatTurn() {
        // Base implementation - can be overridden by subclasses
    }
    
    // Unit validation
    @Override
    public boolean canUnitAttack(IUnit unit) {
        return unit != null && unit.canAttack() && unit.isAlive();
    }
    
    @Override
    public boolean canUnitMove(IUnit unit) {
        return unit != null && unit.canMove() && unit.isAlive();
    }
    
    @Override
    public boolean canUnitUseAbility(IUnit unit, String abilityName) {
        return unit != null && unit.canPerformSpecialAbility() && unit.isAlive();
    }
    
    @Override
    public boolean isUnitValidForCombat(IUnit unit) {
        return unit != null && unit.isAlive();
    }
    
    // Combat statistics
    @Override
    public int getTotalDamageDealt() { return totalDamageDealt; }
    
    @Override
    public int getTotalDamageTaken() { return totalDamageTaken; }
    
    @Override
    public int getTotalHits() { return totalHits; }
    
    @Override
    public int getTotalMisses() { return totalMisses; }
    
    @Override
    public int getTotalCriticalHits() { return totalCriticalHits; }
    
    @Override
    public void resetCombatStatistics() {
        totalDamageDealt = 0;
        totalDamageTaken = 0;
        totalHits = 0;
        totalMisses = 0;
        totalCriticalHits = 0;
    }
    
    // Abstract methods that must be implemented by subclasses
    @Override
    public abstract CoverType getCoverAtPosition(Position position);
    
    @Override
    public abstract TerrainType getTerrainAtPosition(Position position);
    
    @Override
    public abstract int getCoverBonus(CoverType cover);
    
    @Override
    public abstract int getTerrainBonus(TerrainType terrain);
    
    // Default implementations for optional methods
    @Override
    public void applyStatusEffect(IUnit target, String effectType, int duration, int intensity) {
        // Default implementation - can be overridden
    }
    
    @Override
    public void removeStatusEffect(IUnit target, String effectType) {
        // Default implementation - can be overridden
    }
    
    @Override
    public boolean hasStatusEffect(IUnit target, String effectType) {
        return false; // Default implementation - can be overridden
    }
    
    @Override
    public void applySuppression(IUnit target, int duration) {
        target.applySuppression(duration);
    }
    
    @Override
    public void removeSuppression(IUnit target) {
        target.removeSuppression();
    }
    
    @Override
    public boolean canTriggerOverwatch(IUnit attacker, IUnit target, Position targetPosition) {
        return attacker.isOverwatching() && isInRange(attacker, target, targetPosition);
    }
    
    @Override
    public void setMissionType(String missionType) {
        this.missionType = missionType;
    }
    
    @Override
    public String getMissionType() {
        return missionType;
    }
}

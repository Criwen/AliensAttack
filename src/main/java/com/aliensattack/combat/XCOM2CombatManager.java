package com.aliensattack.combat;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Comprehensive combat manager implementing XCOM 2 mechanics:
 * 1. Concealment/Stealth System
 * 2. Flanking Mechanics  
 * 3. Suppression Fire
 * 4. Destructible Environment
 * 5. Squad Cohesion
 */
@Getter
@Setter
public class XCOM2CombatManager implements ICombatManager {
    
    private ITacticalField field;
    private List<Unit> units;
    private List<CoverObject> coverObjects;
    private List<EnvironmentObject> environmentObjects;
    private Random random;
    
    public XCOM2CombatManager(ITacticalField field) {
        this.field = field;
        this.units = new ArrayList<>();
        this.coverObjects = new ArrayList<>();
        this.environmentObjects = new ArrayList<>();
        this.random = ThreadLocalRandom.current();
    }
    
    // =============================================================================
    // 1. CONCEALMENT/STEALTH SYSTEM
    // =============================================================================
    
    /**
     * Conceal a unit (stealth mode)
     */
    public boolean concealUnit(Unit unit) {
        if (unit.conceal()) {
            // Check if any enemies can detect the unit
            checkConcealmentDetection(unit);
            return true;
        }
        return false;
    }
    
    /**
     * Check if concealed unit is detected by enemies
     */
    private void checkConcealmentDetection(Unit concealedUnit) {
        for (Unit enemy : getEnemyUnits(concealedUnit)) {
            if (concealedUnit.isDetectedBy(enemy)) {
                concealedUnit.reveal();
                break;
            }
        }
    }
    
    /**
     * Attack with stealth bonus
     */
    public CombatResult stealthAttack(Unit attacker, Unit target, Weapon weapon) {
        if (!attacker.isConcealed()) {
            return new CombatResult(false, 0, "Unit is not concealed");
        }
        
        // Reveal unit after attack
        attacker.reveal();
        
        // Apply stealth bonus
        int stealthBonus = attacker.getStealthAttackBonus();
        int baseAccuracy = weapon.getAccuracy();
        int totalAccuracy = baseAccuracy + stealthBonus;
        
        // Perform attack with enhanced accuracy
        return performAttack(attacker, target, weapon, totalAccuracy);
    }
    
    // =============================================================================
    // 2. FLANKING MECHANICS
    // =============================================================================
    
    /**
     * Check if unit is flanked by attacker
     */
    public boolean isUnitFlanked(Unit target, Unit attacker) {
        // Find cover object protecting target
        CoverObject cover = findCoverForUnit(target);
        if (cover == null) {
            return false; // No cover to flank
        }
        
        // Check if attacker is behind cover
        return !cover.providesCoverFrom(attacker.getPosition());
    }
    
    /**
     * Attack with flanking bonus
     */
    public CombatResult flankingAttack(Unit attacker, Unit target, Weapon weapon) {
        if (!isUnitFlanked(target, attacker)) {
            return new CombatResult(false, 0, "Target is not flanked");
        }
        
        // Apply flanking bonus (+50% damage, +25% accuracy)
        int flankingDamageBonus = weapon.getBaseDamage() / 2;
        int flankingAccuracyBonus = 25;
        
        int totalDamage = weapon.getBaseDamage() + flankingDamageBonus;
        int totalAccuracy = weapon.getAccuracy() + flankingAccuracyBonus;
        
        return performAttack(attacker, target, weapon, totalAccuracy, totalDamage);
    }
    
    /**
     * Find cover object protecting a unit
     */
    private CoverObject findCoverForUnit(Unit unit) {
        for (CoverObject cover : coverObjects) {
            if (cover.getPosition().equals(unit.getPosition())) {
                return cover;
            }
        }
        return null;
    }
    
    // =============================================================================
    // 3. SUPPRESSION FIRE
    // =============================================================================
    
    /**
     * Apply suppression fire to target area
     */
    public List<CombatResult> suppressionFire(Unit attacker, Position targetPos, Weapon weapon, int turns) {
        List<CombatResult> results = new ArrayList<>();
        
        // Find units in suppression area
        List<Unit> unitsInArea = getUnitsInRange(targetPos, weapon.getRange());
        
        for (Unit target : unitsInArea) {
            if (target.isAlive() && !target.equals(attacker)) {
                // Apply suppression effect
                target.applySuppression(turns);
                
                // Attempt suppression shot (reduced accuracy)
                int suppressionAccuracy = weapon.getAccuracy() - 30;
                CombatResult result = performAttack(attacker, target, weapon, suppressionAccuracy);
                results.add(result);
            }
        }
        
        attacker.spendActionPoint();
        weapon.useAmmo();
        
        return results;
    }
    
    /**
     * Check if unit can move while suppressed
     */
    public boolean canMoveWhileSuppressed(Unit unit) {
        return unit.canMoveWhileSuppressed();
    }
    
    // =============================================================================
    // 4. DESTRUCTIBLE ENVIRONMENT
    // =============================================================================
    
    /**
     * Attack environment object
     */
    public CombatResult attackEnvironment(Unit attacker, EnvironmentObject target, Weapon weapon) {
        if (target.isDestroyed()) {
            return new CombatResult(false, 0, "Target is already destroyed");
        }
        
        int damage = weapon.getBaseDamage();
        boolean destroyed = target.takeDamage(damage);
        
        if (destroyed) {
            // Handle explosion if object has explosion damage
            if (target.getExplosionDamage() > 0) {
                handleEnvironmentExplosion(target);
            }
        }
        
        attacker.spendActionPoint();
        weapon.useAmmo();
        
        String message = destroyed ? "Environment object destroyed!" : "Environment object damaged!";
        return new CombatResult(true, damage, message);
    }
    
    /**
     * Handle explosion when environment object is destroyed
     */
    private void handleEnvironmentExplosion(EnvironmentObject destroyedObject) {
        int explosionDamage = destroyedObject.getExplosionDamage();
        int explosionRadius = destroyedObject.getExplosionRadius();
        
        List<Unit> unitsInExplosion = getUnitsInRange(destroyedObject.getPosition(), explosionRadius);
        
        for (Unit unit : unitsInExplosion) {
            if (unit.isAlive()) {
                unit.takeDamage(explosionDamage);
            }
        }
    }
    
    // =============================================================================
    // 5. SQUAD COHESION
    // =============================================================================
    
    /**
     * Activate squad tactic
     */
    public boolean activateSquadTactic(Unit leader, SquadTactic tactic) {
        if (!tactic.canActivate()) {
            return false;
        }
        
        tactic.activate();
        
        // Apply tactic to all nearby allies
        List<Unit> nearbyAllies = getUnitsInRange(leader.getPosition(), tactic.getRange());
        
        for (Unit ally : nearbyAllies) {
            if (isAlly(leader, ally)) {
                ally.setActiveSquadTactic(tactic);
            }
        }
        
        return true;
    }
    
    /**
     * Get squad cohesion bonus for unit
     */
    public int getSquadCohesionBonus(Unit unit, String statType) {
        SquadTactic tactic = unit.getActiveSquadTactic();
        if (tactic != null && tactic.isActive()) {
            return tactic.getTotalBonus(statType);
        }
        return 0;
    }
    
    /**
     * Process squad tactic cooldowns
     */
    public void processSquadTacticCooldowns() {
        for (Unit unit : units) {
            SquadTactic tactic = unit.getActiveSquadTactic();
            if (tactic != null) {
                tactic.processCooldown();
            }
        }
    }
    
    // =============================================================================
    // HELPER METHODS
    // =============================================================================
    
    /**
     * Perform standard attack
     */
    private CombatResult performAttack(Unit attacker, Unit target, Weapon weapon, int accuracy) {
        return performAttack(attacker, target, weapon, accuracy, weapon.getBaseDamage());
    }
    
    /**
     * Perform attack with custom damage
     */
    private CombatResult performAttack(Unit attacker, Unit target, Weapon weapon, int accuracy, int damage) {
        // Apply squad cohesion bonuses
        int cohesionAccuracyBonus = getSquadCohesionBonus(attacker, "accuracy");
        int cohesionDamageBonus = getSquadCohesionBonus(attacker, "damage");
        
        int totalAccuracy = accuracy + cohesionAccuracyBonus;
        int totalDamage = damage + cohesionDamageBonus;
        
        // Check hit
        int roll = random.nextInt(100) + 1;
        if (roll > totalAccuracy) {
            return new CombatResult(false, 0, "Attack missed!");
        }
        
        // Apply damage
        boolean killed = target.takeDamageWithArmor(totalDamage);
        
        String message = killed ? "Target killed!" : "Target hit!";
        return new CombatResult(true, totalDamage, message);
    }
    
    /**
     * Get enemy units for a given unit
     */
    private List<Unit> getEnemyUnits(Unit unit) {
        List<Unit> enemies = new ArrayList<>();
        for (Unit other : units) {
            if (!other.equals(unit) && other.getUnitType() != unit.getUnitType()) {
                enemies.add(other);
            }
        }
        return enemies;
    }
    
    /**
     * Check if two units are allies
     */
    private boolean isAlly(Unit unit1, Unit unit2) {
        return unit1.getUnitType() == unit2.getUnitType();
    }
    
    /**
     * Get units in range of a position
     */
    private List<Unit> getUnitsInRange(Position center, int range) {
        List<Unit> unitsInRange = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.isAlive()) {
                int distance = center.getDistanceTo(unit.getPosition());
                if (distance <= range) {
                    unitsInRange.add(unit);
                }
            }
        }
        return unitsInRange;
    }
    
    // =============================================================================
    // ICombatManager Implementation
    // =============================================================================
    
    public void setField(ITacticalField field) {
        this.field = field;
    }
    
    @Override
    public ITacticalField getField() {
        return field;
    }
    
    public void addUnit(Unit unit) {
        units.add(unit);
    }
    
    @Override
    public void removeUnit(Unit unit) {
        units.remove(unit);
    }
    
    @Override
    public List<Unit> getAllUnits() {
        return new ArrayList<>(units);
    }
    
    public void addCoverObject(CoverObject cover) {
        coverObjects.add(cover);
    }
    
    public void addEnvironmentObject(EnvironmentObject envObject) {
        environmentObjects.add(envObject);
    }
    
    // =============================================================================
    // ICombatManager Implementation - Missing Methods
    // =============================================================================
    
    @Override
    public void startTurn() {
        // Reset action points for all units
        for (Unit unit : units) {
            unit.resetActionPoints();
        }
    }
    
    @Override
    public void endTurn() {
        // Process status effects and cooldowns
        for (Unit unit : units) {
            unit.processStatusEffects();
        }
        processSquadTacticCooldowns();
    }
    
    @Override
    public boolean isPlayerTurn() {
        // Simple implementation - could be enhanced with turn tracking
        return true;
    }
    
    @Override
    public int getCurrentTurn() {
        // Simple implementation - could be enhanced with turn counter
        return 1;
    }
    
    @Override
    public void addPlayerUnit(Unit unit) {
        units.add(unit);
    }
    
    @Override
    public void addEnemyUnit(Unit unit) {
        units.add(unit);
    }
    
    @Override
    public List<Unit> getPlayerUnits() {
        List<Unit> playerUnits = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getUnitType() == UnitType.SOLDIER) {
                playerUnits.add(unit);
            }
        }
        return playerUnits;
    }
    
    @Override
    public List<Unit> getEnemyUnits() {
        List<Unit> enemyUnits = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getUnitType() == UnitType.ALIEN) {
                enemyUnits.add(unit);
            }
        }
        return enemyUnits;
    }
    
    @Override
    public CombatResult performAttack(Unit attacker, Unit target) {
        Weapon weapon = attacker.getWeapon();
        if (weapon == null) {
            return new CombatResult(false, 0, "No weapon equipped");
        }
        return performAttack(attacker, target, weapon, weapon.getAccuracy());
    }
    
    @Override
    public boolean canAttack(Unit attacker, Unit target) {
        if (!attacker.isAlive() || !target.isAlive()) {
            return false;
        }
        
        Weapon weapon = attacker.getWeapon();
        if (weapon == null) {
            return false;
        }
        
        int distance = attacker.getPosition().getDistanceTo(target.getPosition());
        return distance <= weapon.getRange();
    }
    
    @Override
    public List<Unit> getValidTargets(Unit attacker) {
        List<Unit> validTargets = new ArrayList<>();
        for (Unit target : units) {
            if (canAttack(attacker, target)) {
                validTargets.add(target);
            }
        }
        return validTargets;
    }
    
    @Override
    public boolean canMoveTo(Unit unit, Position target) {
        if (!unit.isAlive() || !unit.canMove()) {
            return false;
        }
        
        int distance = unit.getPosition().getDistanceTo(target);
        return distance <= unit.getMovementRange();
    }
    
    @Override
    public boolean moveUnit(Unit unit, Position target) {
        if (canMoveTo(unit, target)) {
            unit.setPosition(target);
            unit.spendActionPoint();
            return true;
        }
        return false;
    }
    
    @Override
    public List<Position> getValidMovePositions(Unit unit) {
        List<Position> validPositions = new ArrayList<>();
        Position currentPos = unit.getPosition();
        int range = unit.getMovementRange();
        
        for (int x = currentPos.getX() - range; x <= currentPos.getX() + range; x++) {
            for (int y = currentPos.getY() - range; y <= currentPos.getY() + range; y++) {
                Position pos = new Position(x, y);
                if (canMoveTo(unit, pos)) {
                    validPositions.add(pos);
                }
            }
        }
        return validPositions;
    }
    
    @Override
    public boolean isGameOver() {
        return hasPlayerWon() || hasEnemyWon();
    }
    
    @Override
    public boolean hasPlayerWon() {
        return getEnemyUnits().isEmpty();
    }
    
    @Override
    public boolean hasEnemyWon() {
        return getPlayerUnits().isEmpty();
    }
    
    @Override
    public boolean allPlayerUnitsOutOfActionPoints() {
        for (Unit unit : getPlayerUnits()) {
            if (unit.getActionPoints() > 0) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Map<String, Unit> getPlayerUnitsMap() {
        Map<String, Unit> playerUnitsMap = new HashMap<>();
        for (Unit unit : getPlayerUnits()) {
            playerUnitsMap.put(unit.getName(), unit);
        }
        return playerUnitsMap;
    }
    
    @Override
    public Map<String, Unit> getEnemyUnitsMap() {
        Map<String, Unit> enemyUnitsMap = new HashMap<>();
        for (Unit unit : getEnemyUnits()) {
            enemyUnitsMap.put(unit.getName(), unit);
        }
        return enemyUnitsMap;
    }
    
    @Override
    public List<Unit> getPlayerUnitsOptimized() {
        return getPlayerUnits();
    }
    
    @Override
    public List<Unit> getEnemyUnitsOptimized() {
        return getEnemyUnits();
    }
    
    @Override
    public boolean isUnitAlive(Unit unit) {
        return unit.isAlive();
    }
    
    @Override
    public void resetUnitActionPoints(Unit unit) {
        unit.resetActionPoints();
    }
} 
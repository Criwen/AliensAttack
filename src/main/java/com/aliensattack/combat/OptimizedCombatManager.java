package com.aliensattack.combat;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Explosive;
import com.aliensattack.core.enums.CoverType;
import com.aliensattack.field.OptimizedTacticalField;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.ICombatManager;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.combat.CombatResult;

import lombok.Getter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Optimized combat manager with enhanced performance and caching
 * Uses concurrent collections and optimized algorithms for better scalability
 * Оптимизированная реализация менеджера боя
 */
@Getter
public class OptimizedCombatManager implements ICombatManager {
    private final OptimizedTacticalField field;
    private final Map<String, Unit> playerUnits;
    private final Map<String, Unit> enemyUnits;
    private final Map<String, CombatResult> resultCache;
    private boolean isPlayerTurn;
    private final Random random;
    
    public OptimizedCombatManager(OptimizedTacticalField field) {
        this.field = field;
        this.playerUnits = new ConcurrentHashMap<>();
        this.enemyUnits = new ConcurrentHashMap<>();
        this.resultCache = new ConcurrentHashMap<>();
        this.isPlayerTurn = true;
        this.random = new Random();
    }
    

    
    public boolean moveUnitOptimized(Unit unit, int newX, int newY) {
        if (!unit.canMove()) return false;
        
        Position currentPos = unit.getPosition();
        int distance = Math.abs(currentPos.getX() - newX) + Math.abs(currentPos.getY() - newY);
        
        if (distance > unit.getMovementRange()) return false;
        
        if (field.moveUnitOptimized(unit, newX, newY)) {
            unit.spendActionPoint();
            return true;
        }
        
        return false;
    }
    
    public CombatResult attackOptimized(Unit attacker, Unit target) {
        // Check cache first
        String cacheKey = attacker.getName() + "_" + target.getName() + "_" + 
                         attacker.getPosition() + "_" + target.getPosition();
        
        CombatResult cached = resultCache.get(cacheKey);
        if (cached != null) {
            return cached;
        }
        
        if (!attacker.canAttack()) {
            return new CombatResult(false, 0, "No action points remaining");
        }
        
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        
        int distance = Math.abs(attackerPos.getX() - targetPos.getX()) + 
                      Math.abs(attackerPos.getY() - targetPos.getY());
        
        if (distance > attacker.getAttackRange()) {
            return new CombatResult(false, 0, "Target out of range");
        }
        
        // Calculate hit chance with optimized cover calculation
        int baseHitChance = 80;
        CoverType targetCover = field.getCoverTypeAt(targetPos.getX(), targetPos.getY());
        int coverBonus = getCoverBonusOptimized(targetCover);
        int finalHitChance = baseHitChance - coverBonus;
        
        // Roll for hit
        int roll = random.nextInt(100) + 1;
        boolean hit = roll <= finalHitChance;
        
        CombatResult result;
        if (hit) {
            int damage = attacker.getAttackDamage();
            boolean killed = target.takeDamage(damage);
            attacker.spendActionPoint();
            
            result = new CombatResult(true, damage, killed ? "Target killed!" : "Target hit!");
        } else {
            result = new CombatResult(false, 0, "Attack missed!");
        }
        
        // Cache the result
        resultCache.put(cacheKey, result);
        return result;
    }
    
    private int getCoverBonusOptimized(CoverType coverType) {
        return switch (coverType) {
            case FULL_COVER -> 40;
            case HALF_COVER -> 20;
            case LOW_COVER -> 10;
            default -> 0;
        };
    }
    
    public void endTurnOptimized() {
        // Reset action points for all units efficiently
        playerUnits.values().stream()
            .filter(Unit::isAlive)
            .forEach(Unit::resetActionPoints);
            
        enemyUnits.values().stream()
            .filter(Unit::isAlive)
            .forEach(Unit::resetActionPoints);
        
        isPlayerTurn = !isPlayerTurn;
        resultCache.clear(); // Clear cache on turn end
    }
    
    public boolean isCombatOverOptimized() {
        boolean playerAlive = playerUnits.values().stream().anyMatch(Unit::isAlive);
        boolean enemyAlive = enemyUnits.values().stream().anyMatch(Unit::isAlive);
        
        return !playerAlive || !enemyAlive;
    }
    
    public String getWinnerOptimized() {
        boolean playerAlive = playerUnits.values().stream().anyMatch(Unit::isAlive);
        boolean enemyAlive = enemyUnits.values().stream().anyMatch(Unit::isAlive);
        
        if (!playerAlive) return "Enemy";
        if (!enemyAlive) return "Player";
        return null;
    }
    
    public List<Unit> getPlayerUnits() {
        return new ArrayList<>(playerUnits.values());
    }
    
    public List<Unit> getEnemyUnits() {
        return new ArrayList<>(enemyUnits.values());
    }
    
    public List<Unit> getAllUnits() {
        List<Unit> allUnits = new ArrayList<>();
        allUnits.addAll(playerUnits.values());
        allUnits.addAll(enemyUnits.values());
        return allUnits;
    }
    
    public CombatResult performAttack(Unit attacker, Unit target) {
        return attackOptimized(attacker, target);
    }
    
    public boolean canAttack(Unit attacker, Unit target) {
        if (!attacker.isAlive() || !target.isAlive()) return false;
        
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        int distance = Math.abs(attackerPos.getX() - targetPos.getX()) + 
                      Math.abs(attackerPos.getY() - targetPos.getY());
        
        return distance <= attacker.getAttackRange();
    }
    
    public List<Unit> getValidTargets(Unit attacker) {
        List<Unit> validTargets = new ArrayList<>();
        for (Unit target : enemyUnits.values()) {
            if (canAttack(attacker, target)) {
                validTargets.add(target);
            }
        }
        return validTargets;
    }
    
    public boolean canMoveTo(Unit unit, Position target) {
        if (!unit.isAlive()) return false;
        
        Position currentPos = unit.getPosition();
        int distance = Math.abs(currentPos.getX() - target.getX()) + 
                      Math.abs(currentPos.getY() - target.getY());
        
        return distance <= unit.getMovementRange() && field.isValidPosition(target.getX(), target.getY());
    }
    
    public boolean moveUnit(Unit unit, Position target) {
        if (canMoveTo(unit, target)) {
            return field.moveUnitOptimized(unit, target.getX(), target.getY());
        }
        return false;
    }
    
    public List<Position> getValidMovePositions(Unit unit) {
        return field.getValidMovesOptimized(unit, unit.getMovementRange());
    }
    
    public boolean isGameOver() {
        return hasPlayerWon() || hasEnemyWon();
    }
    
    public boolean hasPlayerWon() {
        return enemyUnits.values().stream().noneMatch(Unit::isAlive);
    }
    
    public boolean hasEnemyWon() {
        return playerUnits.values().stream().noneMatch(Unit::isAlive);
    }
    
    public boolean allPlayerUnitsOutOfActionPoints() {
        return playerUnits.values().stream()
            .filter(Unit::isAlive)
            .allMatch(unit -> unit.getActionPoints() <= 0);
    }
    
    public Map<String, Unit> getPlayerUnitsMap() {
        return new HashMap<>(playerUnits);
    }
    
    public Map<String, Unit> getEnemyUnitsMap() {
        return new HashMap<>(enemyUnits);
    }
    
    public List<Unit> getPlayerUnitsOptimized() {
        return new ArrayList<>(playerUnits.values());
    }
    
    public List<Unit> getEnemyUnitsOptimized() {
        return new ArrayList<>(enemyUnits.values());
    }
    
    public boolean isUnitAlive(Unit unit) {
        return unit.isAlive();
    }
    
    public void resetUnitActionPoints(Unit unit) {
        unit.setActionPoints(2);
    }
    
    public void startTurn() {
        isPlayerTurn = true;
        for (Unit unit : playerUnits.values()) {
            unit.setActionPoints(2);
        }
    }
    
    public void endTurn() {
        isPlayerTurn = false;
        // AI turn logic would go here
    }
    
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }
    
    public int getCurrentTurn() {
        return 1; // Simplified for now
    }
    
    public void addPlayerUnit(Unit unit) {
        playerUnits.put(unit.getName(), unit);
    }
    
    public void addEnemyUnit(Unit unit) {
        enemyUnits.put(unit.getName(), unit);
    }
    
    public void removeUnit(Unit unit) {
        playerUnits.remove(unit.getName());
        enemyUnits.remove(unit.getName());
    }
    

    
    public void clearCache() {
        resultCache.clear();
    }
    
    public int getCacheSize() {
        return resultCache.size();
    }
    
    public void removeDeadUnits() {
        playerUnits.entrySet().removeIf(entry -> !entry.getValue().isAlive());
        enemyUnits.entrySet().removeIf(entry -> !entry.getValue().isAlive());
    }
    
    // Additional utility methods (not part of ICombatManager interface)
    public void initialize() {
        // Initialize the combat system
        isPlayerTurn = true;
        clearCache();
    }
    
    public void processInitiative() {
        // Process unit initiative order (not implemented in this optimized version)
    }
    
    public List<IUnit> getUnitsInInitiativeOrder() {
        List<IUnit> allUnits = new ArrayList<>();
        allUnits.addAll(playerUnits.values());
        allUnits.addAll(enemyUnits.values());
        return allUnits;
    }
    
    public boolean isCombatFinished() {
        return isCombatOverOptimized();
    }
    
    public String getCombatState() {
        return String.format("Player Turn: %s, Player Units: %d, Enemy Units: %d", 
                           isPlayerTurn, playerUnits.size(), enemyUnits.size());
    }
    
    public List<IUnit> getUnitsAt(Position position) {
        List<IUnit> unitsAtPosition = new ArrayList<>();
        for (Unit unit : playerUnits.values()) {
            if (unit.getPosition().equals(position)) {
                unitsAtPosition.add(unit);
            }
        }
        for (Unit unit : enemyUnits.values()) {
            if (unit.getPosition().equals(position)) {
                unitsAtPosition.add(unit);
            }
        }
        return unitsAtPosition;
    }
    
    public List<IUnit> getUnitsInRange(Position center, int range) {
        List<IUnit> unitsInRange = new ArrayList<>();
        for (Unit unit : playerUnits.values()) {
            if (center.getDistanceTo(unit.getPosition()) <= range) {
                unitsInRange.add(unit);
            }
        }
        for (Unit unit : enemyUnits.values()) {
            if (center.getDistanceTo(unit.getPosition()) <= range) {
                unitsInRange.add(unit);
            }
        }
        return unitsInRange;
    }
    
    public boolean isValidMovePosition(IUnit unit, Position position) {
        if (unit instanceof Unit) {
            Unit u = (Unit) unit;
            return canMoveTo(u, position);
        }
        return false;
    }
    
    public boolean canSeeUnit(IUnit observer, IUnit target) {
        if (observer instanceof Unit && target instanceof Unit) {
            Unit obs = (Unit) observer;
            Unit tgt = (Unit) target;
            return obs.getPosition().getDistanceTo(tgt.getPosition()) <= obs.getAttackRange();
        }
        return false;
    }
    
    @Override
    public ITacticalField getField() {
        return field;
    }
    
    /**
     * Throw a grenade at a target position
     */
    public List<CombatResult> throwGrenade(Unit thrower, Explosive explosive, Position targetPos) {
        List<CombatResult> results = new ArrayList<>();
        
        if (!thrower.canAttack()) {
            results.add(new CombatResult(false, 0, "No action points remaining"));
            return results;
        }
        
        // Check if target position is in range
        int distance = thrower.getPosition().getDistanceTo(targetPos);
        if (distance > explosive.getRadius()) {
            results.add(new CombatResult(false, 0, "Target out of range"));
            return results;
        }
        
        // Simulate grenade explosion
        int damage = explosive.getDamage();
        boolean hit = random.nextInt(100) < 80; // 80% hit chance for grenades
        
        if (hit) {
            thrower.spendActionPoint();
            results.add(new CombatResult(true, damage, "Grenade hit target!"));
        } else {
            results.add(new CombatResult(false, 0, "Grenade missed!"));
        }
        
        return results;
    }
} 

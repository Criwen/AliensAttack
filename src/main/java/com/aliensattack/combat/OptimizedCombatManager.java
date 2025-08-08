package com.aliensattack.combat;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.enums.CoverType;
import com.aliensattack.field.OptimizedTacticalField;
import com.aliensattack.field.ITacticalField;

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
    
    @Override
    public List<Unit> getPlayerUnits() {
        return new ArrayList<>(playerUnits.values());
    }
    
    @Override
    public List<Unit> getEnemyUnits() {
        return new ArrayList<>(enemyUnits.values());
    }
    
    @Override
    public List<Unit> getAllUnits() {
        List<Unit> allUnits = new ArrayList<>();
        allUnits.addAll(playerUnits.values());
        allUnits.addAll(enemyUnits.values());
        return allUnits;
    }
    
    @Override
    public CombatResult performAttack(Unit attacker, Unit target) {
        return attackOptimized(attacker, target);
    }
    
    @Override
    public boolean canAttack(Unit attacker, Unit target) {
        if (!attacker.isAlive() || !target.isAlive()) return false;
        
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        int distance = Math.abs(attackerPos.getX() - targetPos.getX()) + 
                      Math.abs(attackerPos.getY() - targetPos.getY());
        
        return distance <= attacker.getAttackRange();
    }
    
    @Override
    public List<Unit> getValidTargets(Unit attacker) {
        List<Unit> validTargets = new ArrayList<>();
        for (Unit target : enemyUnits.values()) {
            if (canAttack(attacker, target)) {
                validTargets.add(target);
            }
        }
        return validTargets;
    }
    
    @Override
    public boolean canMoveTo(Unit unit, Position target) {
        if (!unit.isAlive()) return false;
        
        Position currentPos = unit.getPosition();
        int distance = Math.abs(currentPos.getX() - target.getX()) + 
                      Math.abs(currentPos.getY() - target.getY());
        
        return distance <= unit.getMovementRange() && field.isValidPosition(target.getX(), target.getY());
    }
    
    @Override
    public boolean moveUnit(Unit unit, Position target) {
        if (canMoveTo(unit, target)) {
            return field.moveUnitOptimized(unit, target.getX(), target.getY());
        }
        return false;
    }
    
    @Override
    public List<Position> getValidMovePositions(Unit unit) {
        return field.getValidMovesOptimized(unit, unit.getMovementRange());
    }
    
    @Override
    public boolean isGameOver() {
        return hasPlayerWon() || hasEnemyWon();
    }
    
    @Override
    public boolean hasPlayerWon() {
        return enemyUnits.values().stream().noneMatch(Unit::isAlive);
    }
    
    @Override
    public boolean hasEnemyWon() {
        return playerUnits.values().stream().noneMatch(Unit::isAlive);
    }
    
    @Override
    public boolean allPlayerUnitsOutOfActionPoints() {
        return playerUnits.values().stream()
            .filter(Unit::isAlive)
            .allMatch(unit -> unit.getActionPoints() <= 0);
    }
    
    @Override
    public Map<String, Unit> getPlayerUnitsMap() {
        return new HashMap<>(playerUnits);
    }
    
    @Override
    public Map<String, Unit> getEnemyUnitsMap() {
        return new HashMap<>(enemyUnits);
    }
    
    @Override
    public List<Unit> getPlayerUnitsOptimized() {
        return new ArrayList<>(playerUnits.values());
    }
    
    @Override
    public List<Unit> getEnemyUnitsOptimized() {
        return new ArrayList<>(enemyUnits.values());
    }
    
    @Override
    public boolean isUnitAlive(Unit unit) {
        return unit.isAlive();
    }
    
    @Override
    public void resetUnitActionPoints(Unit unit) {
        unit.setActionPoints(2);
    }
    
    @Override
    public void startTurn() {
        isPlayerTurn = true;
        for (Unit unit : playerUnits.values()) {
            unit.setActionPoints(2);
        }
    }
    
    @Override
    public void endTurn() {
        isPlayerTurn = false;
        // AI turn logic would go here
    }
    
    @Override
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }
    
    @Override
    public int getCurrentTurn() {
        return 1; // Simplified for now
    }
    
    @Override
    public void addPlayerUnit(Unit unit) {
        playerUnits.put(unit.getName(), unit);
    }
    
    @Override
    public void addEnemyUnit(Unit unit) {
        enemyUnits.put(unit.getName(), unit);
    }
    
    @Override
    public void removeUnit(Unit unit) {
        playerUnits.remove(unit.getName());
        enemyUnits.remove(unit.getName());
    }
    
    @Override
    public ITacticalField getField() {
        return field;
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
    
} 
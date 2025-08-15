package com.aliensattack.combat;

import com.aliensattack.combat.interfaces.ICombatStrategy;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.core.events.EventBus;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.events.AttackEvent;
import com.aliensattack.core.events.MoveEvent;
import com.aliensattack.core.pool.ObjectPool;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

/**
 * Unified combat manager using strategy pattern
 * Replaces multiple combat manager classes with a single, maintainable solution
 */
@Log4j2
public class CombatManager {
    
    private final ICombatStrategy combatStrategy;
    private final ITacticalField tacticalField;
    private final EventBus eventBus;
    private final ObjectPool<Position> positionPool;
    
    // Unit management
    private final Map<String, Unit> playerUnits = new ConcurrentHashMap<>();
    private final Map<String, Unit> enemyUnits = new ConcurrentHashMap<>();
    
    // Turn management
    private boolean isPlayerTurn = true;
    private int currentTurn = 1;
    
    public CombatManager(ICombatStrategy combatStrategy, ITacticalField tacticalField) {
        this(combatStrategy, tacticalField, EventBus.getInstance());
    }

    /**
     * Preferred constructor allowing dependency injection of {@link EventBus}
     */
    public CombatManager(ICombatStrategy combatStrategy, ITacticalField tacticalField, EventBus eventBus) {
        this.combatStrategy = combatStrategy;
        this.tacticalField = tacticalField;
        this.eventBus = eventBus;
        this.positionPool = com.aliensattack.core.pool.PoolFactory.createPositionPool();
        log.info("CombatManager initialized with strategy: {}", combatStrategy.getStrategyType());
    }
    
    // Combat operations
    public CombatResult performAttack(Unit attacker, Unit target) {
        log.info("Attack initiated: {} -> {}", attacker.getName(), target.getName());
        
        CombatResult result = combatStrategy.executeAttack(attacker, target);
        
        // Publish attack event
        if (result.isSuccess()) {
            boolean isCriticalHit = detectCriticalHit(attacker, result);
            AttackEvent attackEvent = new AttackEvent(
                attacker.getId(), 
                target.getId(), 
                result.getDamage(), 
                isCriticalHit,
                true, 
                attacker.getWeapon() != null ? attacker.getWeapon().getType().toString() : "Unknown"
            );
            eventBus.publish(attackEvent);
        }
        
        return result;
    }
    
    public boolean canAttack(Unit attacker, Unit target) {
        return combatStrategy.canAttack(attacker, target);
    }
    
    public List<Unit> getValidTargets(Unit attacker) {
        return combatStrategy.getValidTargets(attacker);
    }
    
    // Movement operations
    public boolean canMoveTo(Unit unit, Position target) {
        return combatStrategy.canMoveTo(unit, target);
    }
    
    public boolean moveUnit(Unit unit, Position target) {
        Position from = unit.getPosition();
        boolean moved = combatStrategy.moveUnit(unit, target);
        if (moved && from != null && target != null) {
            MoveEvent moveEvent = new MoveEvent(unit.getId(), from.getX(), from.getY(), target.getX(), target.getY());
            eventBus.publish(moveEvent);
        }
        return moved;
    }
    
    public List<Position> getValidMovePositions(Unit unit) {
        return combatStrategy.getValidMovePositions(unit);
    }
    
    // Unit management
    public void addPlayerUnit(Unit unit) {
        playerUnits.put(unit.getId(), unit);
        log.info("Player unit added: {}", unit.getName());
    }
    
    public void addEnemyUnit(Unit unit) {
        enemyUnits.put(unit.getId(), unit);
        log.info("Enemy unit added: {}", unit.getName());
    }
    
    public void removeUnit(Unit unit) {
        playerUnits.remove(unit.getId());
        enemyUnits.remove(unit.getId());
        log.info("Unit removed: {}", unit.getName());
    }
    
    public List<Unit> getPlayerUnits() {
        return List.copyOf(playerUnits.values());
    }
    
    public List<Unit> getEnemyUnits() {
        return List.copyOf(enemyUnits.values());
    }
    
    public List<Unit> getAllUnits() {
        List<Unit> allUnits = new ArrayList<>();
        allUnits.addAll(playerUnits.values());
        allUnits.addAll(enemyUnits.values());
        return allUnits;
    }
    
    // Turn management
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }
    
    public int getCurrentTurn() {
        return currentTurn;
    }
    
    public void endPlayerTurn() {
        isPlayerTurn = false;
        currentTurn++;
        log.info("Player turn ended, starting turn {}", currentTurn);
    }
    
    public void endEnemyTurn() {
        isPlayerTurn = true;
        log.info("Enemy turn ended, starting player turn");
    }
    
    // Game state
    public boolean isGameOver() {
        boolean anyPlayers = !getPlayerUnits().isEmpty();
        boolean anyEnemies = !getEnemyUnits().isEmpty();
        boolean allPlayersDead = anyPlayers && getPlayerUnits().stream().allMatch(u -> u.getCurrentHealth() <= 0);
        boolean allEnemiesDead = anyEnemies && getEnemyUnits().stream().allMatch(u -> u.getCurrentHealth() <= 0);
        return allPlayersDead || allEnemiesDead;
    }
    
    public boolean hasPlayerWon() {
        boolean anyEnemies = !getEnemyUnits().isEmpty();
        return anyEnemies && getEnemyUnits().stream().allMatch(u -> u.getCurrentHealth() <= 0);
    }
    
    public boolean hasEnemyWon() {
        boolean anyPlayers = !getPlayerUnits().isEmpty();
        return anyPlayers && getPlayerUnits().stream().allMatch(u -> u.getCurrentHealth() <= 0);
    }
    
    public boolean allPlayerUnitsOutOfActionPoints() {
        List<Unit> players = getPlayerUnits();
        return !players.isEmpty() && players.stream().allMatch(unit -> unit.getActionPoints() <= 0);
    }
    
    // Utility methods
    public ITacticalField getField() {
        return tacticalField;
    }
    
    public Map<String, Unit> getPlayerUnitsMap() {
        return new ConcurrentHashMap<>(playerUnits);
    }
    
    public Map<String, Unit> getEnemyUnitsMap() {
        return new ConcurrentHashMap<>(enemyUnits);
    }
    
    // Compatibility: interface now exposes standard getters
    
    public boolean isUnitAlive(Unit unit) {
        return unit != null && unit.getCurrentHealth() > 0;
    }
    
    public void resetUnitActionPoints(Unit unit) {
        if (unit != null) {
            int defaultActionPoints = GameConfig.getDefaultActionPoints();
            unit.setActionPoints(defaultActionPoints);
            log.debug("Reset action points for unit: {} -> {}", unit.getName(), defaultActionPoints);
        }
    }
    
    /**
     * Get position from pool for reuse
     */
    public Position acquirePosition(int x, int y) {
        Position pos = positionPool.acquire();
        pos.setX(x);
        pos.setY(y);
        return pos;
    }
    
    /**
     * Return position to pool
     */
    public void releasePosition(Position position) {
        if (position != null) {
            positionPool.release(position);
        }
    }
    
    /**
     * Get pool statistics
     */
    public String getPoolStats() {
        return String.format("PositionPool: %d/%d", 
            positionPool.getPoolSize(), positionPool.getMaxSize());
    }
    
    /**
     * Detect if an attack was a critical hit
     */
    private boolean detectCriticalHit(Unit attacker, CombatResult result) {
        if (attacker == null || result == null) {
            return false;
        }

        int baseDamage = attacker.getAttackDamage();
        int actualDamage = result.getDamage();
        int criticalMultiplier = Math.max(1, GameConfig.getDefaultCriticalDamageMultiplier());

        return actualDamage >= baseDamage * criticalMultiplier;
    }
}

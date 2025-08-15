package com.aliensattack.core.pool;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.events.CombatEvent;
import com.aliensattack.core.events.AttackEvent;
import com.aliensattack.core.events.MoveEvent;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.BaseUnit;
import com.aliensattack.core.model.Alien;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.actions.BaseAction;
import com.aliensattack.actions.AttackAction;
import com.aliensattack.core.config.GameConfig;
import lombok.extern.log4j.Log4j2;

import java.util.function.Consumer;

/**
 * Factory for creating performance-optimized object pools
 */
@Log4j2
public class PerformancePoolFactory {
    
    // Pool sizes optimized for different object types
    private static final int POSITION_POOL_SIZE = 1000;
    private static final int EVENT_POOL_SIZE = 500;
    private static final int UNIT_POOL_SIZE = 200;
    private static final int ACTION_POOL_SIZE = 300;
    private static final int COMBAT_RESULT_POOL_SIZE = 400;
    
    /**
     * Create position object pool with reset functionality
     */
    public static PerformanceObjectPool<Position> createPositionPool() {
        Consumer<Position> resetter = pos -> {
            if (pos != null) {
                pos.setX(0);
                pos.setY(0);
            }
        };
        
        return new PerformanceObjectPool<>(
            () -> new Position(0, 0),
            resetter,
            POSITION_POOL_SIZE,
            "PositionPool"
        );
    }
    
    /**
     * Create combat event pool with reset functionality
     */
    public static PerformanceObjectPool<CombatEvent> createCombatEventPool() {
        Consumer<CombatEvent> resetter = event -> {
            if (event instanceof AttackEvent) {
                AttackEvent attackEvent = (AttackEvent) event;
                // Reset attack event fields
                attackEvent.setDamage(0);
                attackEvent.setCritical(false);
                attackEvent.setHit(false);
                attackEvent.setWeaponType("");
            }
        };
        
        return new PerformanceObjectPool<>(
            () -> new AttackEvent("", "", 0, false, false, ""),
            resetter,
            EVENT_POOL_SIZE,
            "CombatEventPool"
        );
    }
    
    /**
     * Create move event pool
     */
    public static PerformanceObjectPool<MoveEvent> createMoveEventPool() {
        Consumer<MoveEvent> resetter = event -> {
            if (event != null) {
                // Reset move event fields
                event.setFromX(0);
                event.setToX(0);
                event.setFromY(0);
                event.setToY(0);
            }
        };
        
        return new PerformanceObjectPool<>(
            () -> new MoveEvent("", 0, 0, 0, 0),
            resetter,
            EVENT_POOL_SIZE,
            "MoveEventPool"
        );
    }
    
    /**
     * Create action pool for frequently used actions
     */
    public static PerformanceObjectPool<BaseAction> createActionPool() {
        Consumer<BaseAction> resetter = action -> {
            if (action != null) {
                // Reset action fields - only accessible ones
                action.setSuccessful(false);
                action.setResult("");
                action.setActualActionPointCost(action.getActionPointCost());
            }
        };
        
        return new PerformanceObjectPool<>(
            () -> new AttackAction(null, null),
            resetter,
            ACTION_POOL_SIZE,
            "ActionPool"
        );
    }
    
    /**
     * Create unit pool for AI units
     */
    public static PerformanceObjectPool<BaseUnit> createUnitPool() {
        Consumer<BaseUnit> resetter = unit -> {
            if (unit != null) {
                // Reset unit to pool state
                unit.setCurrentHealth(unit.getMaxHealth());
                unit.setActionPoints((int)GameConfig.getDouble("game.default.action.points", 2.0));
                unit.setPosition(new Position(0, 0));
                unit.setOverwatching(false);
                // Note: BaseUnit doesn't have setConcealed, setSuppressed, setSuppressionTurns
                // These are specific to Unit class
            }
        };
        
        return new PerformanceObjectPool<BaseUnit>(
            () -> new Alien("PoolAlien", 100, 3, 5, 25, AlienType.ADVENT_TROOPER),
            resetter,
            UNIT_POOL_SIZE,
            "UnitPool"
        );
    }
    
    /**
     * Create generic performance pool with custom settings
     */
    public static <T> PerformanceObjectPool<T> createPerformancePool(
            java.util.function.Supplier<T> factory, 
            Consumer<T> resetter, 
            int maxSize, 
            String name) {
        return new PerformanceObjectPool<T>(factory, resetter, maxSize, name);
    }
    
    /**
     * Pre-populate all pools for optimal performance
     */
    public static void prePopulateAllPools() {
        log.info("Pre-populating all performance pools...");
        
        try {
            createPositionPool().prePopulate(POSITION_POOL_SIZE / 2);
            createCombatEventPool().prePopulate(EVENT_POOL_SIZE / 2);
            createMoveEventPool().prePopulate(EVENT_POOL_SIZE / 2);
            createActionPool().prePopulate(ACTION_POOL_SIZE / 2);
            createUnitPool().prePopulate(UNIT_POOL_SIZE / 2);
            
            log.info("All performance pools pre-populated successfully");
        } catch (Exception e) {
            log.error("Error pre-populating pools", e);
        }
    }
    
    /**
     * Get pool statistics for monitoring
     */
    public static void logPoolStatistics() {
        log.info("=== Performance Pool Statistics ===");
        
        PerformanceObjectPool<Position> posPool = createPositionPool();
        PerformanceObjectPool<CombatEvent> combatPool = createCombatEventPool();
        PerformanceObjectPool<MoveEvent> movePool = createMoveEventPool();
        PerformanceObjectPool<BaseAction> actionPool = createActionPool();
        PerformanceObjectPool<BaseUnit> unitPool = createUnitPool();
        
        log.info("Position Pool: {}", posPool.getStatistics());
        log.info("Combat Event Pool: {}", combatPool.getStatistics());
        log.info("Move Event Pool: {}", movePool.getStatistics());
        log.info("Action Pool: {}", actionPool.getStatistics());
        log.info("Unit Pool: {}", unitPool.getStatistics());
    }
}

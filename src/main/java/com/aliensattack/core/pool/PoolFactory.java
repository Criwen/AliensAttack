package com.aliensattack.core.pool;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.events.CombatEvent;
import com.aliensattack.core.events.AttackEvent;

/**
 * Factory for creating object pools
 */
public class PoolFactory {
    
    private static final int DEFAULT_POOL_SIZE = 100;
    private static final int POSITION_POOL_SIZE = 500;
    private static final int EVENT_POOL_SIZE = 200;
    
    /**
     * Create position object pool
     */
    public static ObjectPool<Position> createPositionPool() {
        return new ObjectPool<>(
            () -> new Position(0, 0),
            POSITION_POOL_SIZE,
            "PositionPool"
        );
    }
    
    /**
     * Create combat event pool
     */
    public static ObjectPool<CombatEvent> createCombatEventPool() {
        return new ObjectPool<>(
            () -> new AttackEvent("", "", 0, false, false, ""),
            EVENT_POOL_SIZE,
            "CombatEventPool"
        );
    }
    
    /**
     * Create generic pool with custom settings
     */
    public static <T> ObjectPool<T> createPool(java.util.function.Supplier<T> factory, int maxSize, String name) {
        return new ObjectPool<>(factory, maxSize, name);
    }
    
    /**
     * Create default-sized pool
     */
    public static <T> ObjectPool<T> createDefaultPool(java.util.function.Supplier<T> factory, String name) {
        return new ObjectPool<>(factory, DEFAULT_POOL_SIZE, name);
    }
}

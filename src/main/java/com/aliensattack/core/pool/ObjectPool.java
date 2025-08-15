package com.aliensattack.core.pool;

import lombok.extern.log4j.Log4j2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

/**
 * Generic object pool for frequently created/destroyed objects
 */
@Log4j2
public class ObjectPool<T> {
    
    private final Queue<T> pool;
    private final Supplier<T> factory;
    private final int maxSize;
    private final String poolName;
    
    public ObjectPool(Supplier<T> factory, int maxSize, String poolName) {
        this.pool = new ConcurrentLinkedQueue<>();
        this.factory = factory;
        this.maxSize = maxSize;
        this.poolName = poolName;
        
        log.debug("ObjectPool '{}' created with max size: {}", poolName, maxSize);
    }
    
    /**
     * Acquire object from pool or create new one
     */
    public T acquire() {
        T obj = pool.poll();
        if (obj != null) {
            log.debug("Object acquired from pool '{}', remaining: {}", poolName, pool.size());
            return obj;
        }
        
        log.debug("Creating new object for pool '{}'", poolName);
        return factory.get();
    }
    
    /**
     * Return object to pool
     */
    public void release(T obj) {
        if (obj == null) return;
        
        if (pool.size() < maxSize) {
            pool.offer(obj);
            log.debug("Object returned to pool '{}', current size: {}", poolName, pool.size());
        } else {
            log.debug("Pool '{}' is full, discarding object", poolName);
        }
    }
    
    /**
     * Get current pool size
     */
    public int getPoolSize() {
        return pool.size();
    }
    
    /**
     * Get maximum pool size
     */
    public int getMaxSize() {
        return maxSize;
    }
    
    /**
     * Clear pool
     */
    public void clear() {
        int size = pool.size();
        pool.clear();
        log.debug("Pool '{}' cleared, removed {} objects", poolName, size);
    }
}

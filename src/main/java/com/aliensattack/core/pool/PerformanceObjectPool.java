package com.aliensattack.core.pool;

import lombok.extern.log4j.Log4j2;
import lombok.Data;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.function.Consumer;

/**
 * Enhanced object pool with performance monitoring and smart management
 */
@Log4j2
public class PerformanceObjectPool<T> {
    
    private final Queue<T> pool;
    private final Supplier<T> factory;
    private final Consumer<T> resetter;
    private final int maxSize;
    private final String poolName;
    
    // Performance metrics
    private final AtomicLong totalAcquisitions = new AtomicLong(0);
    private final AtomicLong totalReleases = new AtomicLong(0);
    private final AtomicLong totalCreations = new AtomicLong(0);
    private final AtomicLong totalDiscards = new AtomicLong(0);
    private final AtomicLong totalResetTime = new AtomicLong(0);
    
    public PerformanceObjectPool(Supplier<T> factory, Consumer<T> resetter, int maxSize, String poolName) {
        this.pool = new ConcurrentLinkedQueue<>();
        this.factory = factory;
        this.resetter = resetter;
        this.maxSize = maxSize;
        this.poolName = poolName;
        
        log.debug("PerformanceObjectPool '{}' created with max size: {}", poolName, maxSize);
    }
    
    /**
     * Acquire object from pool or create new one
     */
    public T acquire() {
        T obj = pool.poll();
        if (obj != null) {
            totalAcquisitions.incrementAndGet();
            log.debug("Object acquired from pool '{}', remaining: {}", poolName, pool.size());
            return obj;
        }
        
        totalCreations.incrementAndGet();
        totalAcquisitions.incrementAndGet();
        log.debug("Creating new object for pool '{}'", poolName);
        return factory.get();
    }
    
    /**
     * Return object to pool with reset
     */
    public void release(T obj) {
        if (obj == null) return;
        
        if (pool.size() < maxSize) {
            long startTime = System.nanoTime();
            resetter.accept(obj);
            long resetTime = System.nanoTime() - startTime;
            totalResetTime.addAndGet(resetTime);
            
            pool.offer(obj);
            totalReleases.incrementAndGet();
            log.debug("Object returned to pool '{}', current size: {}", poolName, pool.size());
        } else {
            totalDiscards.incrementAndGet();
            log.debug("Pool '{}' is full, discarding object", poolName);
        }
    }
    
    /**
     * Get performance statistics
     */
    public PoolStatistics getStatistics() {
        return new PoolStatistics(
            poolName,
            pool.size(),
            maxSize,
            totalAcquisitions.get(),
            totalReleases.get(),
            totalCreations.get(),
            totalDiscards.get(),
            totalResetTime.get()
        );
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
    
    /**
     * Pre-populate pool with objects
     */
    public void prePopulate(int count) {
        int toCreate = Math.min(count, maxSize - pool.size());
        for (int i = 0; i < toCreate; i++) {
            T obj = factory.get();
            resetter.accept(obj);
            pool.offer(obj);
        }
        log.debug("Pre-populated pool '{}' with {} objects", poolName, toCreate);
    }
    
    @Data
    public static class PoolStatistics {
        private final String poolName;
        private final int currentSize;
        private final int maxSize;
        private final long totalAcquisitions;
        private final long totalReleases;
        private final long totalCreations;
        private final long totalDiscards;
        private final long totalResetTimeNanos;
        
        public double getHitRate() {
            return totalAcquisitions > 0 ? 
                (double)(totalAcquisitions - totalCreations) / totalAcquisitions : 0.0;
        }
        
        public double getEfficiency() {
            return totalReleases > 0 ? 
                (double)(totalReleases - totalDiscards) / totalReleases : 0.0;
        }
        
        public double getAverageResetTimeMs() {
            return totalReleases > 0 ? 
                (double)totalResetTimeNanos / totalReleases / 1_000_000 : 0.0;
        }
    }
}

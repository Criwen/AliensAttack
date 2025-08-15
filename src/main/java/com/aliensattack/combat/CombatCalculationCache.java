package com.aliensattack.combat;

import lombok.extern.log4j.Log4j2;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.TimeUnit;

/**
 * Cache system for combat calculations to improve performance
 */
@Log4j2
public class CombatCalculationCache {
    
    private static final int DEFAULT_CACHE_SIZE = 1000;
    private static final long DEFAULT_TTL_MS = TimeUnit.MINUTES.toMillis(5);
    
    private final Map<String, CachedCalculation> cache;
    private final int maxSize;
    private final long ttlMs;
    
    // Performance metrics
    private final AtomicLong cacheHits = new AtomicLong(0);
    private final AtomicLong cacheMisses = new AtomicLong(0);
    private final AtomicLong cacheEvictions = new AtomicLong(0);
    
    public CombatCalculationCache() {
        this(DEFAULT_CACHE_SIZE, DEFAULT_TTL_MS);
    }
    
    public CombatCalculationCache(int maxSize, long ttlMs) {
        this.cache = new ConcurrentHashMap<>();
        this.maxSize = maxSize;
        this.ttlMs = ttlMs;
        
        log.info("CombatCalculationCache initialized with max size: {}, TTL: {}ms", maxSize, ttlMs);
    }
    
    /**
     * Get cached calculation result
     */
    public <T> T get(String key, Class<T> resultType) {
        CachedCalculation cached = cache.get(key);
        
        if (cached != null && !cached.isExpired()) {
            cacheHits.incrementAndGet();
            log.debug("Cache hit for key: {}", key);
            return resultType.cast(cached.getResult());
        }
        
        if (cached != null && cached.isExpired()) {
            cache.remove(key);
            cacheEvictions.incrementAndGet();
        }
        
        cacheMisses.incrementAndGet();
        log.debug("Cache miss for key: {}", key);
        return null;
    }
    
    /**
     * Store calculation result in cache
     */
    public void put(String key, Object result) {
        if (cache.size() >= maxSize) {
            evictExpiredEntries();
            
            if (cache.size() >= maxSize) {
                evictOldestEntry();
            }
        }
        
        CachedCalculation cached = new CachedCalculation(result, System.currentTimeMillis() + ttlMs);
        cache.put(key, cached);
        log.debug("Cached calculation for key: {}", key);
    }
    
    /**
     * Generate cache key for combat calculation
     */
    public static String generateKey(String calculationType, Object... params) {
        StringBuilder keyBuilder = new StringBuilder(calculationType);
        for (Object param : params) {
            keyBuilder.append("_").append(param != null ? param.hashCode() : "null");
        }
        return keyBuilder.toString();
    }
    
    /**
     * Clear expired entries from cache
     */
    private void evictExpiredEntries() {
        long currentTime = System.currentTimeMillis();
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired(currentTime));
    }
    
    /**
     * Evict oldest entry when cache is full
     */
    private void evictOldestEntry() {
        long oldestTime = Long.MAX_VALUE;
        String oldestKey = null;
        
        for (Map.Entry<String, CachedCalculation> entry : cache.entrySet()) {
            if (entry.getValue().getExpirationTime() < oldestTime) {
                oldestTime = entry.getValue().getExpirationTime();
                oldestKey = entry.getKey();
            }
        }
        
        if (oldestKey != null) {
            cache.remove(oldestKey);
            cacheEvictions.incrementAndGet();
            log.debug("Evicted oldest entry: {}", oldestKey);
        }
    }
    
    /**
     * Clear entire cache
     */
    public void clear() {
        int size = cache.size();
        cache.clear();
        log.info("Cache cleared, removed {} entries", size);
    }
    
    /**
     * Get cache statistics
     */
    public CacheStatistics getStatistics() {
        evictExpiredEntries();
        
        return new CacheStatistics(
            cache.size(),
            maxSize,
            cacheHits.get(),
            cacheMisses.get(),
            cacheEvictions.get(),
            calculateHitRate()
        );
    }
    
    /**
     * Calculate cache hit rate
     */
    private double calculateHitRate() {
        long total = cacheHits.get() + cacheMisses.get();
        return total > 0 ? (double) cacheHits.get() / total : 0.0;
    }
    
    /**
     * Get cache size
     */
    public int getSize() {
        return cache.size();
    }
    
    /**
     * Check if cache is empty
     */
    public boolean isEmpty() {
        return cache.isEmpty();
    }
    
    @Data
    public static class CacheStatistics {
        private final int currentSize;
        private final int maxSize;
        private final long hits;
        private final long misses;
        private final long evictions;
        private final double hitRate;
        
        public double getEfficiency() {
            return (double) currentSize / maxSize;
        }
    }
    
    @Data
    private static class CachedCalculation {
        private final Object result;
        private final long expirationTime;
        
        public boolean isExpired() {
            return isExpired(System.currentTimeMillis());
        }
        
        public boolean isExpired(long currentTime) {
            return currentTime > expirationTime;
        }
    }
}

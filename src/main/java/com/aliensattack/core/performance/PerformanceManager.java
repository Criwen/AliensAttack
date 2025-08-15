package com.aliensattack.core.performance;

import com.aliensattack.core.pool.PerformancePoolFactory;
import com.aliensattack.combat.CombatCalculationCache;
import com.aliensattack.core.loading.LazyResourceLoader;
import com.aliensattack.core.control.MultithreadedAIManager;
import com.aliensattack.core.config.GameConfig;
import lombok.extern.log4j.Log4j2;
import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Central performance manager integrating all optimization systems
 */
@Log4j2
public class PerformanceManager {
    
    private static PerformanceManager instance;
    private final AtomicBoolean initialized = new AtomicBoolean(false);
    
    // Performance systems
    private PerformancePoolFactory poolFactory;
    private CombatCalculationCache combatCache;
    private LazyResourceLoader resourceLoader;
    private MultithreadedAIManager aiManager;
    
    // Monitoring
    private ScheduledExecutorService monitoringExecutor;
    private PerformanceMetrics metrics;
    
    private PerformanceManager() {
        // Private constructor for singleton
    }
    
    public static PerformanceManager getInstance() {
        if (instance == null) {
            instance = new PerformanceManager();
        }
        return instance;
    }
    
    /**
     * Initialize performance optimization systems
     */
    public void initialize() {
        if (initialized.compareAndSet(false, true)) {
            log.info("Initializing Performance Manager...");
            
            try {
                // Initialize object pooling
                initializeObjectPools();
                
                // Initialize combat caching
                initializeCombatCaching();
                
                // Initialize lazy loading
                initializeLazyLoading();
                
                // Initialize multithreaded AI
                initializeMultithreadedAI();
                
                // Initialize performance monitoring
                initializeMonitoring();
                
                log.info("Performance Manager initialized successfully");
                
            } catch (Exception e) {
                log.error("Failed to initialize Performance Manager", e);
                initialized.set(false);
                throw new RuntimeException("Performance Manager initialization failed", e);
            }
        }
    }
    
    /**
     * Initialize object pooling system
     */
    private void initializeObjectPools() {
        if (GameConfig.getBoolean("performance.pooling.enabled", true)) {
            log.info("Initializing Object Pooling System...");
            
            // Pre-populate pools if configured
            if (GameConfig.getBoolean("performance.pooling.pre_populate", true)) {
                PerformancePoolFactory.prePopulateAllPools();
            }
            
            log.info("Object Pooling System initialized");
        }
    }
    
    /**
     * Initialize combat calculation caching
     */
    private void initializeCombatCaching() {
        if (GameConfig.getBoolean("performance.caching.enabled", true)) {
            log.info("Initializing Combat Calculation Cache...");
            
            int maxSize = GameConfig.getInt("performance.caching.combat.max_size", 1000);
            int ttlMinutes = GameConfig.getInt("performance.caching.combat.ttl_minutes", 5);
            
            combatCache = new CombatCalculationCache(maxSize, TimeUnit.MINUTES.toMillis(ttlMinutes));
            
            log.info("Combat Calculation Cache initialized with max size: {}, TTL: {} minutes", maxSize, ttlMinutes);
        }
    }
    
    /**
     * Initialize lazy loading system
     */
    private void initializeLazyLoading() {
        if (GameConfig.getBoolean("performance.lazy_loading.enabled", true)) {
            log.info("Initializing Lazy Resource Loader...");
            
            int threadPoolSize = GameConfig.getInt("performance.lazy_loading.thread_pool_size", 4);
            int maxConcurrentLoads = GameConfig.getInt("performance.lazy_loading.max_concurrent_loads", 10);
            
            resourceLoader = new LazyResourceLoader(threadPoolSize, maxConcurrentLoads);
            
            log.info("Lazy Resource Loader initialized with {} threads, max concurrent loads: {}", 
                    threadPoolSize, maxConcurrentLoads);
        }
    }
    
    /**
     * Initialize multithreaded AI system
     */
    private void initializeMultithreadedAI() {
        if (GameConfig.getBoolean("performance.multithreading.ai.enabled", true)) {
            log.info("Initializing Multithreaded AI Manager...");
            
            int threadPoolSize = GameConfig.getInt("performance.multithreading.ai.thread_pool_size", 4);
            int maxConcurrent = GameConfig.getInt("performance.multithreading.ai.max_concurrent", 8);
            long timeoutMs = GameConfig.getInt("performance.multithreading.ai.timeout_ms", 5000);
            
            aiManager = new MultithreadedAIManager(threadPoolSize, maxConcurrent, timeoutMs);
            
            log.info("Multithreaded AI Manager initialized with {} threads, max concurrent: {}, timeout: {}ms", 
                    threadPoolSize, maxConcurrent, timeoutMs);
        }
    }
    
    /**
     * Initialize performance monitoring
     */
    private void initializeMonitoring() {
        if (GameConfig.getBoolean("performance.monitoring.enabled", true)) {
            log.info("Initializing Performance Monitoring...");
            
            int logInterval = GameConfig.getInt("performance.monitoring.log_interval_seconds", 60);
            
            monitoringExecutor = Executors.newSingleThreadScheduledExecutor();
            metrics = new PerformanceMetrics();
            
            // Schedule periodic monitoring
            monitoringExecutor.scheduleAtFixedRate(
                this::logPerformanceMetrics,
                logInterval,
                logInterval,
                TimeUnit.SECONDS
            );
            
            log.info("Performance Monitoring initialized with {} second interval", logInterval);
        }
    }
    
    /**
     * Log performance metrics
     */
    private void logPerformanceMetrics() {
        try {
            log.info("=== Performance Metrics Report ===");
            
            // Log pool statistics
            if (GameConfig.getBoolean("performance.pooling.enabled", true)) {
                PerformancePoolFactory.logPoolStatistics();
            }
            
            // Log cache statistics
            if (combatCache != null) {
                log.info("Combat Cache: {}", combatCache.getStatistics());
            }
            
            // Log resource loader statistics
            if (resourceLoader != null) {
                log.info("Resource Loader: {}", resourceLoader.getStatistics());
            }
            
            // Log AI manager statistics
            if (aiManager != null) {
                log.info("AI Manager: {}", aiManager.getStatistics());
            }
            
            // Log memory usage
            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            
            log.info("Memory Usage - Total: {}MB, Used: {}MB, Free: {}MB", 
                    totalMemory / 1024 / 1024,
                    usedMemory / 1024 / 1024,
                    freeMemory / 1024 / 1024);
            
        } catch (Exception e) {
            log.error("Error logging performance metrics", e);
        }
    }
    
    /**
     * Get combat calculation cache
     */
    public CombatCalculationCache getCombatCache() {
        return combatCache;
    }
    
    /**
     * Get lazy resource loader
     */
    public LazyResourceLoader getResourceLoader() {
        return resourceLoader;
    }
    
    /**
     * Get multithreaded AI manager
     */
    public MultithreadedAIManager getAIManager() {
        return aiManager;
    }
    
    /**
     * Get performance metrics
     */
    public PerformanceMetrics getMetrics() {
        return metrics;
    }
    
    /**
     * Shutdown performance manager
     */
    public void shutdown() {
        log.info("Shutting down Performance Manager...");
        
        if (monitoringExecutor != null) {
            monitoringExecutor.shutdown();
        }
        
        if (aiManager != null) {
            aiManager.shutdown();
        }
        
        if (resourceLoader != null) {
            resourceLoader.shutdown();
        }
        
        log.info("Performance Manager shutdown complete");
    }
    
    @Data
    public static class PerformanceMetrics {
        private long startTime = System.currentTimeMillis();
        private int totalOperations = 0;
        private int successfulOperations = 0;
        private int failedOperations = 0;
        
        public void recordOperation(boolean success) {
            totalOperations++;
            if (success) {
                successfulOperations++;
            } else {
                failedOperations++;
            }
        }
        
        public double getSuccessRate() {
            return totalOperations > 0 ? (double) successfulOperations / totalOperations : 0.0;
        }
        
        public long getUptime() {
            return System.currentTimeMillis() - startTime;
        }
    }
}

package com.aliensattack.core.performance;

import com.aliensattack.core.pool.PerformanceObjectPool;
import com.aliensattack.combat.CombatCalculationCache;
import com.aliensattack.core.loading.LazyResourceLoader;
import com.aliensattack.core.control.MultithreadedAIManager;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.events.AttackEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.function.Consumer;

/**
 * Test class for performance optimization systems
 */
public class PerformanceOptimizationTest {
    
    private PerformanceObjectPool<Position> positionPool;
    private CombatCalculationCache combatCache;
    private LazyResourceLoader resourceLoader;
    private MultithreadedAIManager aiManager;
    
    @BeforeEach
    void setUp() {
        // Initialize performance systems
        positionPool = new PerformanceObjectPool<>(
            () -> new Position(0, 0),
            pos -> {
                if (pos != null) {
                    pos.setX(0);
                    pos.setY(0);
                }
            },
            100,
            "TestPositionPool"
        );
        
        combatCache = new CombatCalculationCache(100, TimeUnit.MINUTES.toMillis(1));
        resourceLoader = new LazyResourceLoader(2, 5);
        aiManager = new MultithreadedAIManager(2, 4, 1000);
    }
    
    @AfterEach
    void tearDown() {
        if (aiManager != null) {
            aiManager.shutdown();
        }
        if (resourceLoader != null) {
            resourceLoader.shutdown();
        }
    }
    
    @Test
    void testObjectPoolingPerformance() {
        // Test object pooling efficiency
        long startTime = System.nanoTime();
        
        for (int i = 0; i < 1000; i++) {
            Position pos = positionPool.acquire();
            pos.setX(i);
            pos.setY(i * 2);
            positionPool.release(pos);
        }
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        // Verify pool statistics
        var stats = positionPool.getStatistics();
        assertTrue(stats.getHitRate() > 0.8, "Pool hit rate should be high");
        assertTrue(stats.getEfficiency() > 0.9, "Pool efficiency should be high");
        
        System.out.println("Object Pooling Performance: " + duration / 1_000_000 + "ms");
        System.out.println("Pool Statistics: " + stats);
    }
    
    @Test
    void testCombatCachingPerformance() {
        // Test combat calculation caching
        String cacheKey = CombatCalculationCache.generateKey("damage_calc", "attacker_1", "target_1", "weapon_rifle");
        
        // First calculation (cache miss)
        long startTime = System.nanoTime();
        Integer result1 = combatCache.get(cacheKey, Integer.class);
        long firstCall = System.nanoTime() - startTime;
        
        // Simulate expensive calculation
        if (result1 == null) {
            try {
                Thread.sleep(10); // Simulate expensive computation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            combatCache.put(cacheKey, 25);
        }
        
        // Second calculation (cache hit)
        startTime = System.nanoTime();
        Integer result2 = combatCache.get(cacheKey, Integer.class);
        long secondCall = System.nanoTime() - startTime;
        
        // Verify caching works
        assertNotNull(result2, "Cached result should be available");
        assertEquals(25, result2, "Cached result should match stored value");
        assertTrue(secondCall < firstCall, "Cached call should be faster");
        
        // Verify cache statistics
        var stats = combatCache.getStatistics();
        assertTrue(stats.getHitRate() > 0.0, "Cache should have hits");
        
        System.out.println("Combat Caching Performance - First call: " + firstCall / 1_000_000 + "ms, Second call: " + secondCall / 1_000_000 + "ms");
        System.out.println("Cache Statistics: " + stats);
    }
    
    @Test
    void testLazyLoadingPerformance() {
        // Test lazy resource loading
        String resourceId = "test_resource";
        
        // Register a resource that simulates expensive loading
        resourceLoader.registerResource(resourceId, () -> {
            try {
                Thread.sleep(50); // Simulate expensive loading
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Loaded Resource";
        }, LazyResourceLoader.ResourceType.CONFIGURATION);
        
        // Test synchronous loading
        long startTime = System.nanoTime();
        String resource = resourceLoader.getResource(resourceId, String.class);
        long syncLoadTime = System.nanoTime() - startTime;
        
        assertNotNull(resource, "Resource should be loaded");
        assertEquals("Loaded Resource", resource, "Resource content should match");
        
        // Test asynchronous preloading
        startTime = System.nanoTime();
        CompletableFuture<String> preloadFuture = resourceLoader.preloadResource(resourceId, String.class);
        long asyncLoadTime = System.nanoTime() - startTime;
        
        assertTrue(asyncLoadTime < syncLoadTime, "Async loading should be faster than sync");
        
        // Verify resource is now loaded
        assertTrue(resourceLoader.isResourceLoaded(resourceId), "Resource should be marked as loaded");
        
        // Verify loading statistics
        var stats = resourceLoader.getStatistics();
        assertTrue(stats.getLoadingProgress() > 0.0, "Loading progress should be positive");
        
        System.out.println("Lazy Loading Performance - Sync: " + syncLoadTime / 1_000_000 + "ms, Async: " + asyncLoadTime / 1_000_000 + "ms");
        System.out.println("Loading Statistics: " + stats);
    }
    
    @Test
    void testMultithreadedAIPerformance() {
        // Test multithreaded AI processing
        // Note: This is a simplified test since we don't have actual AI brains
        
        // Verify AI manager is initialized
        assertNotNull(aiManager, "AI Manager should be initialized");
        
        // Test statistics
        var stats = aiManager.getStatistics();
        assertEquals(0, stats.getTotalDecisions(), "Initial total decisions should be 0");
        assertEquals(0, stats.getSuccessfulDecisions(), "Initial successful decisions should be 0");
        
        System.out.println("Multithreaded AI Manager initialized successfully");
        System.out.println("AI Statistics: " + stats);
    }
    
    @Test
    void testPerformanceManagerIntegration() {
        // Test performance manager integration
        PerformanceManager manager = PerformanceManager.getInstance();
        
        // Initialize performance systems
        manager.initialize();
        
        // Verify systems are available
        assertNotNull(manager.getCombatCache(), "Combat cache should be available");
        assertNotNull(manager.getResourceLoader(), "Resource loader should be available");
        assertNotNull(manager.getAIManager(), "AI manager should be available");
        assertNotNull(manager.getMetrics(), "Performance metrics should be available");
        
        // Test metrics recording
        var metrics = manager.getMetrics();
        metrics.recordOperation(true);
        metrics.recordOperation(false);
        metrics.recordOperation(true);
        
        assertEquals(3, metrics.getTotalOperations(), "Total operations should be 3");
        assertEquals(2, metrics.getSuccessfulOperations(), "Successful operations should be 2");
        assertEquals(1, metrics.getFailedOperations(), "Failed operations should be 1");
        assertEquals(2.0/3.0, metrics.getSuccessRate(), 0.01, "Success rate should be 2/3");
        
        System.out.println("Performance Manager Integration Test - Success Rate: " + metrics.getSuccessRate());
        
        // Cleanup
        manager.shutdown();
    }
    
    @Test
    void testConcurrentOperations() throws InterruptedException {
        // Test concurrent operations for thread safety
        
        // Test concurrent object pool operations
        int numThreads = 10;
        int operationsPerThread = 100;
        
        Thread[] threads = new Thread[numThreads];
        
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    Position pos = positionPool.acquire();
                    pos.setX(threadId);
                    pos.setY(j);
                    positionPool.release(pos);
                }
            });
        }
        
        // Start all threads
        long startTime = System.nanoTime();
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        long endTime = System.nanoTime();
        
        long duration = endTime - startTime;
        System.out.println("Concurrent Operations Performance: " + duration / 1_000_000 + "ms");
        
        // Verify pool integrity
        var stats = positionPool.getStatistics();
        assertTrue(stats.getCurrentSize() <= stats.getMaxSize(), "Pool size should not exceed maximum");
        
        System.out.println("Concurrent Operations Test - Final Pool Statistics: " + stats);
    }
}

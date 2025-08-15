package com.aliensattack.core.monitoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for the Performance Monitoring System
 * Tests all three components: PerformanceMetrics, GameLoopProfiler, and MemoryMonitor
 */
@DisplayName("Performance Monitoring System Tests")
class PerformanceMonitoringSystemTest {
    
    private PerformanceMonitoringManager monitoringManager;
    private PerformanceMetrics performanceMetrics;
    private GameLoopProfiler gameLoopProfiler;
    private MemoryMonitor memoryMonitor;
    
    @BeforeEach
    void setUp() {
        // Reset all singleton instances for clean testing
        monitoringManager = PerformanceMonitoringManager.getInstance();
        performanceMetrics = PerformanceMetrics.getInstance();
        gameLoopProfiler = GameLoopProfiler.getInstance();
        memoryMonitor = MemoryMonitor.getInstance();
        
        // Reset all systems
        performanceMetrics.resetAll();
        gameLoopProfiler.reset();
    }
    
    @AfterEach
    void tearDown() {
        // Clean shutdown
        monitoringManager.shutdown();
    }
    
    @Test
    @DisplayName("Performance Metrics System - Basic Functionality")
    void testPerformanceMetricsBasicFunctionality() {
        // Test metric registration
        performanceMetrics.registerMetric("test.metric", "Test metric description");
        
        // Test timing recording
        performanceMetrics.recordTiming("test.metric", 150);
        performanceMetrics.recordTiming("test.metric", 250);
        performanceMetrics.recordTiming("test.metric", 350);
        
        // Test counter increment
        performanceMetrics.incrementCounter("game.frames_rendered");
        performanceMetrics.incrementCounter("game.frames_rendered");
        
        // Test gauge setting
        performanceMetrics.setGauge("game.fps", 60.0);
        
        // Verify data
        Map<String, PerformanceMetrics.MetricStatistics> stats = performanceMetrics.getStatistics();
        Map<String, Long> counters = performanceMetrics.getCounters();
        Map<String, Double> gauges = performanceMetrics.getGauges();
        
        assertTrue(stats.containsKey("test.metric"));
        assertEquals(3, stats.get("test.metric").getCount());
        assertEquals(250.0, stats.get("test.metric").getAverage(), 0.1);
        assertEquals(150.0, stats.get("test.metric").getMin(), 0.1);
        assertEquals(350.0, stats.get("test.metric").getMax(), 0.1);
        
        assertEquals(2L, counters.get("game.frames_rendered"));
        assertEquals(60.0, gauges.get("game.fps"), 0.1);
    }
    
    @Test
    @DisplayName("Game Loop Profiler - Frame and Phase Profiling")
    void testGameLoopProfilerFunctionality() {
        // Test frame profiling
        gameLoopProfiler.startFrame();
        
        // Simulate some work
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Test phase profiling
        gameLoopProfiler.startPhase(GameLoopProfiler.PHASE_RENDER);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        gameLoopProfiler.endPhase(GameLoopProfiler.PHASE_RENDER);
        
        gameLoopProfiler.endFrame();
        
        // Verify frame statistics
        GameLoopProfiler.FrameStatistics frameStats = gameLoopProfiler.getCurrentFrameStatistics();
        assertNotNull(frameStats);
        assertEquals(1, frameStats.getFrameNumber());
        assertTrue(frameStats.getTotalPhaseTime() > 0);
        
        // Verify overall statistics
        GameLoopProfiler.OverallStatistics overallStats = gameLoopProfiler.getOverallStatistics();
        assertEquals(1, overallStats.getTotalFrames());
        assertTrue(overallStats.getAverageFrameTime() > 0);
        assertTrue(overallStats.getAverageFPS() > 0);
        
        // Verify phase statistics
        Map<String, GameLoopProfiler.PhaseStatistics> phaseStats = overallStats.getPhaseStatistics();
        assertTrue(phaseStats.containsKey(GameLoopProfiler.PHASE_RENDER));
        assertEquals(1, phaseStats.get(GameLoopProfiler.PHASE_RENDER).getCallCount());
    }
    
    @Test
    @DisplayName("Game Loop Profiler - Operation Profiling")
    void testGameLoopProfilerOperationProfiling() {
        gameLoopProfiler.startFrame();
        
        // Test automatic operation profiling
        gameLoopProfiler.profileOperation(GameLoopProfiler.PHASE_UPDATE, () -> {
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        gameLoopProfiler.endFrame();
        
        // Verify operation was profiled
        GameLoopProfiler.OverallStatistics stats = gameLoopProfiler.getOverallStatistics();
        Map<String, GameLoopProfiler.PhaseStatistics> phaseStats = stats.getPhaseStatistics();
        
        assertTrue(phaseStats.containsKey(GameLoopProfiler.PHASE_UPDATE));
        assertEquals(1, phaseStats.get(GameLoopProfiler.PHASE_UPDATE).getCallCount());
        assertTrue(phaseStats.get(GameLoopProfiler.PHASE_UPDATE).getTotalTime() > 0);
    }
    
    @Test
    @DisplayName("Memory Monitor - Basic Functionality")
    void testMemoryMonitorBasicFunctionality() {
        // Test memory snapshot capture
        MemoryMonitor.MemorySnapshot snapshot = memoryMonitor.captureMemorySnapshot();
        assertNotNull(snapshot);
        assertTrue(snapshot.getHeapUsed() > 0);
        assertTrue(snapshot.getHeapMax() > 0);
        assertTrue(snapshot.getHeapUsagePercent() >= 0.0);
        assertTrue(snapshot.getHeapUsagePercent() <= 1.0);
        assertTrue(snapshot.getActiveThreadCount() > 0);
        
        // Test memory statistics
        MemoryMonitor.MemoryStatistics stats = memoryMonitor.getMemoryStatistics();
        assertNotNull(stats);
        assertEquals(0, stats.getTotalGCCount()); // No GC yet
        assertEquals(0, stats.getTotalGCTime());
        assertNull(stats.getLastGC());
    }
    
    @Test
    @DisplayName("Memory Monitor - Garbage Collection")
    void testMemoryMonitorGarbageCollection() {
        // Test forced garbage collection
        MemoryMonitor.GCSnapshot gcSnapshot = memoryMonitor.forceGarbageCollection();
        assertNotNull(gcSnapshot);
        assertTrue(gcSnapshot.getDuration() >= 0);
        assertTrue(gcSnapshot.getTimestamp().isAfter(Instant.now().minus(Duration.ofMinutes(1))));
        
        // Verify GC statistics updated
        MemoryMonitor.MemoryStatistics stats = memoryMonitor.getMemoryStatistics();
        assertEquals(1, stats.getTotalGCCount());
        assertTrue(stats.getTotalGCTime() > 0);
        assertNotNull(stats.getLastGC());
    }
    
    @Test
    @DisplayName("Performance Monitoring Manager - Integration")
    void testPerformanceMonitoringManagerIntegration() {
        // Test game session management
        monitoringManager.startGameSession();
        
        // Simulate some game activity
        gameLoopProfiler.startFrame();
        gameLoopProfiler.startPhase(GameLoopProfiler.PHASE_RENDER);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        gameLoopProfiler.endPhase(GameLoopProfiler.PHASE_RENDER);
        gameLoopProfiler.endFrame();
        
        // Test quick status
        PerformanceMonitoringManager.PerformanceStatus status = monitoringManager.getQuickStatus();
        assertNotNull(status);
        assertEquals(1, status.getTotalFrames());
        assertTrue(status.getAverageFPS() > 0);
        assertTrue(status.getHeapUsagePercent() >= 0.0);
        assertTrue(status.getHeapUsagePercent() <= 1.0);
        
        // Test comprehensive report generation
        String report = monitoringManager.generateComprehensiveReport();
        assertNotNull(report);
        assertTrue(report.contains("PERFORMANCE METRICS"));
        assertTrue(report.contains("GAME LOOP PROFILING"));
        assertTrue(report.contains("MEMORY MONITORING"));
        assertTrue(report.contains("PERFORMANCE SUMMARY"));
        
        // Test game session ending
        monitoringManager.endGameSession();
    }
    
    @Test
    @DisplayName("Performance Monitoring Manager - Periodic Reporting")
    void testPerformanceMonitoringManagerPeriodicReporting() {
        // Start monitoring
        monitoringManager.startGameSession();
        
        // Simulate some activity
        for (int i = 0; i < 3; i++) {
            gameLoopProfiler.startFrame();
            gameLoopProfiler.profileOperation(GameLoopProfiler.PHASE_UPDATE, () -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            gameLoopProfiler.endFrame();
        }
        
        // Wait a bit for any background processing
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verify data was collected
        GameLoopProfiler.OverallStatistics stats = gameLoopProfiler.getOverallStatistics();
        assertEquals(3, stats.getTotalFrames());
        assertTrue(stats.getAverageFPS() > 0);
        
        monitoringManager.endGameSession();
    }
    
    @Test
    @DisplayName("Performance Metrics - Threshold Warnings")
    void testPerformanceMetricsThresholdWarnings() {
        // Test warning threshold
        performanceMetrics.recordTiming("game.loop.frame_time", 120); // Above 100ms warning
        
        // Test critical threshold
        performanceMetrics.recordTiming("game.loop.frame_time", 600); // Above 500ms critical
        
        // Verify metrics were recorded
        Map<String, PerformanceMetrics.MetricStatistics> stats = performanceMetrics.getStatistics();
        assertTrue(stats.containsKey("game.loop.frame_time"));
        assertEquals(2, stats.get("game.loop.frame_time").getCount());
    }
    
    @Test
    @DisplayName("Game Loop Profiler - Performance Thresholds")
    void testGameLoopProfilerPerformanceThresholds() {
        gameLoopProfiler.startFrame();
        
        // Test phase that exceeds warning threshold
        gameLoopProfiler.startPhase(GameLoopProfiler.PHASE_RENDER);
        try {
            Thread.sleep(10); // 10ms > 8ms warning threshold
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        gameLoopProfiler.endPhase(GameLoopProfiler.PHASE_RENDER);
        
        // Test phase that exceeds critical threshold
        gameLoopProfiler.startPhase(GameLoopProfiler.PHASE_UPDATE);
        try {
            Thread.sleep(20); // 20ms > 16ms critical threshold
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        gameLoopProfiler.endPhase(GameLoopProfiler.PHASE_UPDATE);
        
        gameLoopProfiler.endFrame();
        
        // Verify phases were profiled
        GameLoopProfiler.OverallStatistics stats = gameLoopProfiler.getOverallStatistics();
        Map<String, GameLoopProfiler.PhaseStatistics> phaseStats = stats.getPhaseStatistics();
        
        assertTrue(phaseStats.containsKey(GameLoopProfiler.PHASE_RENDER));
        assertTrue(phaseStats.containsKey(GameLoopProfiler.PHASE_UPDATE));
        assertEquals(1, phaseStats.get(GameLoopProfiler.PHASE_RENDER).getCallCount());
        assertEquals(1, phaseStats.get(GameLoopProfiler.PHASE_UPDATE).getCallCount());
    }
    
    @Test
    @DisplayName("Memory Monitor - Memory Health Checks")
    void testMemoryMonitorMemoryHealthChecks() {
        // Test memory snapshot with various usage levels
        MemoryMonitor.MemorySnapshot snapshot = memoryMonitor.captureMemorySnapshot();
        
        // Verify snapshot data integrity
        assertTrue(snapshot.getHeapUsed() >= 0);
        assertTrue(snapshot.getHeapMax() > 0);
        assertTrue(snapshot.getHeapUsagePercent() >= 0.0);
        assertTrue(snapshot.getHeapUsagePercent() <= 1.0);
        assertTrue(snapshot.getActiveThreadCount() > 0);
        assertTrue(snapshot.getPeakThreadCount() >= snapshot.getActiveThreadCount());
        
        // Test memory statistics
        MemoryMonitor.MemoryStatistics stats = memoryMonitor.getMemoryStatistics();
        assertNotNull(stats);
        assertNotNull(stats.getCurrentSnapshot());
        assertEquals(0, stats.getTotalGCCount());
        assertEquals(0, stats.getTotalGCTime());
    }
    
    @Test
    @DisplayName("System Integration - Complete Workflow")
    void testSystemIntegrationCompleteWorkflow() {
        // Start monitoring
        monitoringManager.startGameSession();
        
        // Simulate complete game loop
        for (int frame = 0; frame < 5; frame++) {
            gameLoopProfiler.startFrame();
            
            // Input processing
            gameLoopProfiler.profileOperation(GameLoopProfiler.PHASE_INPUT, () -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            
            // Game update
            gameLoopProfiler.profileOperation(GameLoopProfiler.PHASE_UPDATE, () -> {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            
            // Rendering
            gameLoopProfiler.profileOperation(GameLoopProfiler.PHASE_RENDER, () -> {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            
            gameLoopProfiler.endFrame();
            
            // Record some performance metrics
            performanceMetrics.recordTiming("combat.calculation_time", 15 + frame);
            performanceMetrics.incrementCounter("game.actions_performed");
        }
        
        // Verify comprehensive data collection
        GameLoopProfiler.OverallStatistics gameLoopStats = gameLoopProfiler.getOverallStatistics();
        assertEquals(5, gameLoopStats.getTotalFrames());
        assertTrue(gameLoopStats.getAverageFPS() > 0);
        
        Map<String, PerformanceMetrics.MetricStatistics> metricsStats = performanceMetrics.getStatistics();
        assertTrue(metricsStats.containsKey("combat.calculation_time"));
        assertEquals(5, metricsStats.get("combat.calculation_time").getCount());
        
        Map<String, Long> counters = performanceMetrics.getCounters();
        assertEquals(5L, counters.get("game.actions_performed"));
        
        // End session and generate report
        monitoringManager.endGameSession();
        
        // Verify final report
        String finalReport = monitoringManager.generateComprehensiveReport();
        assertNotNull(finalReport);
        assertTrue(finalReport.contains("Total Frames: 5"));
        assertTrue(finalReport.contains("combat.calculation_time"));
        assertTrue(finalReport.contains("game.actions_performed"));
    }
}

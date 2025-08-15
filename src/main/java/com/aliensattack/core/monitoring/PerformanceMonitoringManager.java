package com.aliensattack.core.monitoring;

import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main performance monitoring manager that integrates all monitoring systems
 * Provides unified interface for performance monitoring and reporting
 */
@Log4j2
public class PerformanceMonitoringManager {
    
    // Singleton instance
    private static volatile PerformanceMonitoringManager instance;
    private static final Object lock = new Object();
    
    // Integrated monitoring systems
    private final PerformanceMetrics performanceMetrics;
    private final GameLoopProfiler gameLoopProfiler;
    private final MemoryMonitor memoryMonitor;
    
    // Reporting and monitoring
    private final ScheduledExecutorService reportingExecutor;
    private static final long REPORTING_INTERVAL_SECONDS = 60; // 1 minute
    
    private PerformanceMonitoringManager() {
        this.performanceMetrics = PerformanceMetrics.getInstance();
        this.gameLoopProfiler = GameLoopProfiler.getInstance();
        this.memoryMonitor = MemoryMonitor.getInstance();
        
        this.reportingExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "PerformanceReporter");
            t.setDaemon(true);
            return t;
        });
        
        initializeReporting();
        log.info("PerformanceMonitoringManager initialized with all monitoring systems");
    }
    
    public static PerformanceMonitoringManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new PerformanceMonitoringManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize periodic performance reporting
     */
    private void initializeReporting() {
        reportingExecutor.scheduleAtFixedRate(
            this::generatePeriodicReport,
            REPORTING_INTERVAL_SECONDS,
            REPORTING_INTERVAL_SECONDS,
            TimeUnit.SECONDS
        );
        
        log.debug("Performance reporting scheduled every {} seconds", REPORTING_INTERVAL_SECONDS);
    }
    
    /**
     * Generate periodic performance report
     */
    private void generatePeriodicReport() {
        try {
            log.info("=== PERIODIC PERFORMANCE REPORT ===");
            
            // Performance metrics summary
            var metricsStats = performanceMetrics.getStatistics();
            log.info("Performance Metrics - {} active metrics", metricsStats.size());
            
            // Game loop profiling summary
            var gameLoopStats = gameLoopProfiler.getOverallStatistics();
            log.info("Game Loop - Frames: {}, Avg FPS: {:.2f}, Avg Frame Time: {:.2f}ms", 
                gameLoopStats.getTotalFrames(),
                gameLoopStats.getAverageFPS(),
                gameLoopStats.getAverageFrameTime());
            
            // Memory monitoring summary
            var memoryStats = memoryMonitor.getMemoryStatistics();
            var currentMemory = memoryStats.getCurrentSnapshot();
            log.info("Memory - Heap: {:.1f}% used, Threads: {}, GC Count: {}", 
                currentMemory.getHeapUsagePercent() * 100,
                currentMemory.getActiveThreadCount(),
                memoryStats.getTotalGCCount());
            
        } catch (Exception e) {
            log.error("Error generating periodic performance report", e);
        }
    }
    
    /**
     * Start monitoring a new game session
     */
    public void startGameSession() {
        log.info("Starting new game session monitoring");
        
        // Reset all monitoring systems
        performanceMetrics.resetAll();
        gameLoopProfiler.reset();
        
        // Start memory monitoring
        memoryMonitor.captureMemorySnapshot();
        
        log.info("Game session monitoring started");
    }
    
    /**
     * End current game session and generate final report
     */
    public void endGameSession() {
        log.info("Ending game session monitoring");
        
        // Generate comprehensive final report
        String finalReport = generateComprehensiveReport();
        log.info("Final Performance Report:\n{}", finalReport);
        
        log.info("Game session monitoring ended");
    }
    
    /**
     * Generate comprehensive performance report
     */
    public String generateComprehensiveReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== COMPREHENSIVE PERFORMANCE REPORT ===\n");
        report.append("Generated at: ").append(Instant.now()).append("\n\n");
        
        // Performance Metrics Report
        report.append("PERFORMANCE METRICS:\n");
        report.append(performanceMetrics.generateReport()).append("\n");
        
        // Game Loop Profiling Report
        report.append("GAME LOOP PROFILING:\n");
        report.append(gameLoopProfiler.generateReport()).append("\n");
        
        // Memory Monitoring Report
        report.append("MEMORY MONITORING:\n");
        report.append(memoryMonitor.generateReport()).append("\n");
        
        // Summary and Recommendations
        report.append("PERFORMANCE SUMMARY:\n");
        report.append(generatePerformanceSummary()).append("\n");
        
        return report.toString();
    }
    
    /**
     * Generate performance summary with recommendations
     */
    private String generatePerformanceSummary() {
        StringBuilder summary = new StringBuilder();
        
        // Get current statistics
        var gameLoopStats = gameLoopProfiler.getOverallStatistics();
        var memoryStats = memoryMonitor.getMemoryStatistics();
        var currentMemory = memoryStats.getCurrentSnapshot();
        
        // Performance assessment
        if (gameLoopStats.getTotalFrames() > 0) {
            double avgFPS = gameLoopStats.getAverageFPS();
            if (avgFPS >= 55) {
                summary.append("✓ Excellent performance: ").append(String.format("%.1f", avgFPS)).append(" FPS\n");
            } else if (avgFPS >= 45) {
                summary.append("⚠ Good performance: ").append(String.format("%.1f", avgFPS)).append(" FPS\n");
            } else if (avgFPS >= 30) {
                summary.append("⚠ Acceptable performance: ").append(String.format("%.1f", avgFPS)).append(" FPS\n");
            } else {
                summary.append("✗ Poor performance: ").append(String.format("%.1f", avgFPS)).append(" FPS\n");
            }
        }
        
        // Memory assessment
        double heapUsage = currentMemory.getHeapUsagePercent();
        if (heapUsage < 0.6) {
            summary.append("✓ Healthy memory usage: ").append(String.format("%.1f", heapUsage * 100)).append("%\n");
        } else if (heapUsage < 0.8) {
            summary.append("⚠ Moderate memory usage: ").append(String.format("%.1f", heapUsage * 100)).append("%\n");
        } else if (heapUsage < 0.9) {
            summary.append("⚠ High memory usage: ").append(String.format("%.1f", heapUsage * 100)).append("%\n");
        } else {
            summary.append("✗ Critical memory usage: ").append(String.format("%.1f", heapUsage * 100)).append("%\n");
        }
        
        // Recommendations
        summary.append("\nRECOMMENDATIONS:\n");
        
        if (gameLoopStats.getTotalFrames() > 0 && gameLoopStats.getAverageFPS() < 50) {
            summary.append("- Consider reducing visual effects or complexity\n");
            summary.append("- Review game loop optimization opportunities\n");
            summary.append("- Monitor frame time distribution across phases\n");
        }
        
        if (heapUsage > 0.8) {
            summary.append("- Monitor for memory leaks\n");
            summary.append("- Consider reducing cache sizes\n");
            summary.append("- Review object pooling strategies\n");
        }
        
        if (memoryStats.getTotalGCCount() > 10) {
            summary.append("- High GC frequency detected\n");
            summary.append("- Review object creation patterns\n");
            summary.append("- Consider tuning JVM memory parameters\n");
        }
        
        return summary.toString();
    }
    
    /**
     * Get quick performance status
     */
    public PerformanceStatus getQuickStatus() {
        var gameLoopStats = gameLoopProfiler.getOverallStatistics();
        var memoryStats = memoryMonitor.getMemoryStatistics();
        var currentMemory = memoryStats.getCurrentSnapshot();
        
        return new PerformanceStatus(
            gameLoopStats.getTotalFrames(),
            gameLoopStats.getAverageFPS(),
            currentMemory.getHeapUsagePercent(),
            memoryStats.getTotalGCCount(),
            Instant.now()
        );
    }
    
    /**
     * Force garbage collection and update metrics
     */
    public void forceGarbageCollection() {
        log.info("Forcing garbage collection via monitoring manager");
        memoryMonitor.forceGarbageCollection();
    }
    
    /**
     * Shutdown all monitoring systems
     */
    public void shutdown() {
        log.info("Shutting down PerformanceMonitoringManager");
        
        // Shutdown reporting executor
        if (reportingExecutor != null && !reportingExecutor.isShutdown()) {
            reportingExecutor.shutdown();
            try {
                if (!reportingExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                    reportingExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                reportingExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        // Shutdown memory monitor
        memoryMonitor.shutdown();
        
        log.info("PerformanceMonitoringManager shutdown completed");
    }
    
    /**
     * Performance status data transfer object
     */
    public static class PerformanceStatus {
        private final long totalFrames;
        private final double averageFPS;
        private final double heapUsagePercent;
        private final long totalGCCount;
        private final Instant timestamp;
        
        public PerformanceStatus(long totalFrames, double averageFPS, double heapUsagePercent, 
                               long totalGCCount, Instant timestamp) {
            this.totalFrames = totalFrames;
            this.averageFPS = averageFPS;
            this.heapUsagePercent = heapUsagePercent;
            this.totalGCCount = totalGCCount;
            this.timestamp = timestamp;
        }
        
        // Getters
        public long getTotalFrames() { return totalFrames; }
        public double getAverageFPS() { return averageFPS; }
        public double getHeapUsagePercent() { return heapUsagePercent; }
        public long getTotalGCCount() { return totalGCCount; }
        public Instant getTimestamp() { return timestamp; }
        
        @Override
        public String toString() {
            return String.format("PerformanceStatus{frames=%d, fps=%.1f, heap=%.1f%%, gc=%d, time=%s}",
                totalFrames, averageFPS, heapUsagePercent * 100, totalGCCount, timestamp);
        }
    }
}

package com.aliensattack.core.monitoring;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;

/**
 * Comprehensive memory and garbage collection monitoring system
 * Tracks heap usage, GC performance, and detects potential memory leaks
 */
@Log4j2
public class MemoryMonitor {
    
    // Singleton instance
    private static volatile MemoryMonitor instance;
    private static final Object lock = new Object();
    
    // Monitoring data storage
    private final Map<String, MemorySnapshot> memoryHistory = new ConcurrentHashMap<>();
    private final Map<String, GCSnapshot> gcHistory = new ConcurrentHashMap<>();
    private final AtomicLong totalGCCount = new AtomicLong(0);
    private final AtomicLong totalGCTime = new AtomicLong(0);
    private final AtomicReference<Instant> lastGC = new AtomicReference<>();
    
    // Memory thresholds
    private static final double HEAP_WARNING_THRESHOLD = 0.75; // 75%
    private static final double HEAP_CRITICAL_THRESHOLD = 0.90; // 90%
    private static final double HEAP_EMERGENCY_THRESHOLD = 0.95; // 95%
    private static final long MEMORY_LEAK_THRESHOLD_KB = 1024; // 1MB per hour
    private static final int MEMORY_HISTORY_SIZE = 100; // Keep last 100 snapshots
    
    // Monitoring interval
    private static final long MONITORING_INTERVAL_MS = 5000; // 5 seconds
    private final ScheduledExecutorService monitoringExecutor;
    
    // JMX beans
    private final MemoryMXBean memoryBean;
    private final List<GarbageCollectorMXBean> gcBeans;
    private final ThreadMXBean threadBean;
    
    private MemoryMonitor() {
        this.memoryBean = ManagementFactory.getMemoryMXBean();
        this.gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        this.threadBean = ManagementFactory.getThreadMXBean();
        
        this.monitoringExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "MemoryMonitor");
            t.setDaemon(true);
            return t;
        });
        
        initializeMonitoring();
        log.info("MemoryMonitor system initialized");
    }
    
    public static MemoryMonitor getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new MemoryMonitor();
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize continuous monitoring
     */
    private void initializeMonitoring() {
        monitoringExecutor.scheduleAtFixedRate(
            this::performMemoryCheck,
            MONITORING_INTERVAL_MS,
            MONITORING_INTERVAL_MS,
            TimeUnit.MILLISECONDS
        );
        
        log.debug("Memory monitoring scheduled every {}ms", MONITORING_INTERVAL_MS);
    }
    
    /**
     * Perform comprehensive memory check
     */
    private void performMemoryCheck() {
        try {
            // Capture memory snapshot
            MemorySnapshot memorySnapshot = captureMemorySnapshot();
            String timestamp = memorySnapshot.getTimestamp().toString();
            
            // Store in history
            memoryHistory.put(timestamp, memorySnapshot);
            
            // Clean up old history
            if (memoryHistory.size() > MEMORY_HISTORY_SIZE) {
                String oldestKey = memoryHistory.keySet().stream()
                    .min(String::compareTo)
                    .orElse(null);
                if (oldestKey != null) {
                    memoryHistory.remove(oldestKey);
                }
            }
            
            // Check for memory issues
            checkMemoryHealth(memorySnapshot);
            
            // Update performance metrics
            updatePerformanceMetrics(memorySnapshot);
            
            log.debug("Memory check completed: heap usage {}%", 
                String.format("%.1f", memorySnapshot.getHeapUsagePercent() * 100));
                
        } catch (Exception e) {
            log.error("Error during memory check", e);
        }
    }
    
    /**
     * Capture current memory state
     */
    public MemorySnapshot captureMemorySnapshot() {
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        
        long heapUsed = heapUsage.getUsed();
        long heapMax = heapUsage.getMax();
        double heapUsagePercent = heapMax > 0 ? (double) heapUsed / heapMax : 0.0;
        
        long nonHeapUsed = nonHeapUsage.getUsed();
        long nonHeapMax = nonHeapUsage.getMax();
        double nonHeapUsagePercent = nonHeapMax > 0 ? (double) nonHeapUsed / nonHeapMax : 0.0;
        
        // Calculate available memory
        long availableMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        
        return new MemorySnapshot(
            Instant.now(),
            heapUsed,
            heapMax,
            heapUsagePercent,
            nonHeapUsed,
            nonHeapMax,
            nonHeapUsagePercent,
            availableMemory,
            totalMemory,
            threadBean.getThreadCount(),
            threadBean.getPeakThreadCount()
        );
    }
    
    /**
     * Check memory health and log warnings
     */
    private void checkMemoryHealth(MemorySnapshot snapshot) {
        double heapUsage = snapshot.getHeapUsagePercent();
        
        if (heapUsage > HEAP_EMERGENCY_THRESHOLD) {
            log.error("EMERGENCY: Heap usage at {}% - immediate action required!", 
                String.format("%.1f", heapUsage * 100));
            suggestMemoryActions(snapshot);
        } else if (heapUsage > HEAP_CRITICAL_THRESHOLD) {
            log.error("CRITICAL: Heap usage at {}% - memory pressure detected", 
                String.format("%.1f", heapUsage * 100));
            suggestMemoryActions(snapshot);
        } else if (heapUsage > HEAP_WARNING_THRESHOLD) {
            log.warn("WARNING: Heap usage at {}% - monitor closely", 
                String.format("%.1f", heapUsage * 100));
        }
        
        // Check for potential memory leaks
        checkForMemoryLeaks();
    }
    
    /**
     * Check for potential memory leaks
     */
    private void checkForMemoryLeaks() {
        if (memoryHistory.size() < 2) return;
        
        // Get last two snapshots
        String[] timestamps = memoryHistory.keySet().stream()
            .sorted()
            .toArray(String[]::new);
        
        if (timestamps.length < 2) return;
        
        MemorySnapshot older = memoryHistory.get(timestamps[timestamps.length - 2]);
        MemorySnapshot newer = memoryHistory.get(timestamps[timestamps.length - 1]);
        
        if (older != null && newer != null) {
            long timeDiff = Duration.between(older.getTimestamp(), newer.getTimestamp()).toSeconds();
            long memoryDiff = newer.getHeapUsed() - older.getHeapUsed();
            
            if (timeDiff > 0 && memoryDiff > 0) {
                double memoryGrowthRateKB = (double) memoryDiff / 1024 / timeDiff;
                
                if (memoryGrowthRateKB > MEMORY_LEAK_THRESHOLD_KB) {
                    log.warn("Potential memory leak detected: {} KB/s growth rate", 
                        String.format("%.2f", memoryGrowthRateKB));
                }
            }
        }
    }
    
    /**
     * Suggest memory management actions
     */
    private void suggestMemoryActions(MemorySnapshot snapshot) {
        log.info("Memory management suggestions:");
        log.info("  - Current heap usage: {} MB / {} MB ({}%)", 
            snapshot.getHeapUsed() / 1024 / 1024,
            snapshot.getHeapMax() / 1024 / 1024,
            String.format("%.1f", snapshot.getHeapUsagePercent() * 100));
        log.info("  - Available memory: {} MB", 
            snapshot.getAvailableMemory() / 1024 / 1024);
        log.info("  - Active threads: {}", snapshot.getActiveThreadCount());
        
        if (snapshot.getHeapUsagePercent() > HEAP_CRITICAL_THRESHOLD) {
            log.info("  - Consider: Reducing object cache sizes");
            log.info("  - Consider: Forcing garbage collection");
            log.info("  - Consider: Reducing concurrent operations");
        }
    }
    
    /**
     * Update performance metrics with memory data
     */
    private void updatePerformanceMetrics(MemorySnapshot snapshot) {
        PerformanceMetrics metrics = PerformanceMetrics.getInstance();
        
        metrics.recordValue("memory.heap_used", snapshot.getHeapUsed() / 1024.0 / 1024.0); // MB
        metrics.recordValue("memory.heap_max", snapshot.getHeapMax() / 1024.0 / 1024.0); // MB
        metrics.recordValue("memory.heap_usage_percent", snapshot.getHeapUsagePercent() * 100);
        metrics.setGauge("system.thread_count", snapshot.getActiveThreadCount());
    }
    
    /**
     * Force garbage collection and measure performance
     */
    public GCSnapshot forceGarbageCollection() {
        log.info("Forcing garbage collection...");
        
        long startTime = System.currentTimeMillis();
        long startMemory = memoryBean.getHeapMemoryUsage().getUsed();
        
        // Force GC
        System.gc();
        
        long endTime = System.currentTimeMillis();
        long endMemory = memoryBean.getHeapMemoryUsage().getUsed();
        
        long gcTime = endTime - startTime;
        long freedMemory = startMemory - endMemory;
        
        GCSnapshot snapshot = new GCSnapshot(
            Instant.now(),
            "Manual",
            gcTime,
            freedMemory,
            startMemory,
            endMemory
        );
        
        // Update counters
        totalGCCount.incrementAndGet();
        totalGCTime.addAndGet(gcTime);
        lastGC.set(snapshot.getTimestamp());
        
        // Store in history
        String timestamp = snapshot.getTimestamp().toString();
        gcHistory.put(timestamp, snapshot);
        
        // Clean up old GC history
        if (gcHistory.size() > MEMORY_HISTORY_SIZE) {
            String oldestKey = gcHistory.keySet().stream()
                .min(String::compareTo)
                .orElse(null);
            if (oldestKey != null) {
                gcHistory.remove(oldestKey);
            }
        }
        
        log.info("Garbage collection completed in {}ms, freed {} MB", 
            gcTime, freedMemory / 1024 / 1024);
        
        return snapshot;
    }
    
    /**
     * Get current memory statistics
     */
    public MemoryStatistics getMemoryStatistics() {
        MemorySnapshot current = captureMemorySnapshot();
        
        return new MemoryStatistics(
            current,
            totalGCCount.get(),
            totalGCTime.get(),
            lastGC.get(),
            memoryHistory.size(),
            gcHistory.size()
        );
    }
    
    /**
     * Generate memory monitoring report
     */
    public String generateReport() {
        MemoryStatistics stats = getMemoryStatistics();
        StringBuilder report = new StringBuilder();
        
        report.append("=== MEMORY MONITORING REPORT ===\n");
        report.append("Generated at: ").append(Instant.now()).append("\n\n");
        
        MemorySnapshot current = stats.getCurrentSnapshot();
        report.append("CURRENT MEMORY STATUS:\n");
        report.append("  Heap Used: ").append(current.getHeapUsed() / 1024 / 1024).append(" MB\n");
        report.append("  Heap Max: ").append(current.getHeapMax() / 1024 / 1024).append(" MB\n");
        report.append("  Heap Usage: ").append(String.format("%.1f", current.getHeapUsagePercent() * 100)).append("%\n");
        report.append("  Non-Heap Used: ").append(current.getNonHeapUsed() / 1024 / 1024).append(" MB\n");
        report.append("  Available Memory: ").append(current.getAvailableMemory() / 1024 / 1024).append(" MB\n");
        report.append("  Active Threads: ").append(current.getActiveThreadCount()).append("\n");
        report.append("  Peak Threads: ").append(current.getPeakThreadCount()).append("\n\n");
        
        report.append("GARBAGE COLLECTION:\n");
        report.append("  Total GC Count: ").append(stats.getTotalGCCount()).append("\n");
        report.append("  Total GC Time: ").append(stats.getTotalGCTime()).append("ms\n");
        report.append("  Last GC: ").append(stats.getLastGC() != null ? stats.getLastGC() : "Never").append("\n");
        report.append("  GC History Size: ").append(stats.getGcHistorySize()).append("\n\n");
        
        report.append("MONITORING:\n");
        report.append("  Memory History Size: ").append(stats.getMemoryHistorySize()).append("\n");
        report.append("  Monitoring Interval: ").append(MONITORING_INTERVAL_MS).append("ms\n");
        
        return report.toString();
    }
    
    /**
     * Shutdown monitoring system
     */
    public void shutdown() {
        if (monitoringExecutor != null && !monitoringExecutor.isShutdown()) {
            monitoringExecutor.shutdown();
            try {
                if (!monitoringExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                    monitoringExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                monitoringExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        log.info("MemoryMonitor system shutdown");
    }
    
    /**
     * Memory snapshot data transfer object
     */
    @Data
    public static class MemorySnapshot {
        private final Instant timestamp;
        private final long heapUsed;
        private final long heapMax;
        private final double heapUsagePercent;
        private final long nonHeapUsed;
        private final long nonHeapMax;
        private final double nonHeapUsagePercent;
        private final long availableMemory;
        private final long totalMemory;
        private final int activeThreadCount;
        private final int peakThreadCount;
    }
    
    /**
     * Garbage collection snapshot data transfer object
     */
    @Data
    public static class GCSnapshot {
        private final Instant timestamp;
        private final String gcType;
        private final long duration;
        private final long freedMemory;
        private final long startMemory;
        private final long endMemory;
    }
    
    /**
     * Memory statistics data transfer object
     */
    @Data
    public static class MemoryStatistics {
        private final MemorySnapshot currentSnapshot;
        private final long totalGCCount;
        private final long totalGCTime;
        private final Instant lastGC;
        private final int memoryHistorySize;
        private final int gcHistorySize;
    }
}

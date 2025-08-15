package com.aliensattack.core.monitoring;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.time.Instant;
import java.time.Duration;

/**
 * Comprehensive performance metrics collection system
 * Tracks various performance indicators throughout the game
 */
@Log4j2
public class PerformanceMetrics {
    
    // Singleton instance
    private static volatile PerformanceMetrics instance;
    private static final Object lock = new Object();
    
    // Core metrics storage
    private final Map<String, MetricData> metrics = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> counters = new ConcurrentHashMap<>();
    private final Map<String, AtomicReference<Double>> gauges = new ConcurrentHashMap<>();
    
    // Performance thresholds
    private static final long WARNING_THRESHOLD_MS = 100;
    private static final long CRITICAL_THRESHOLD_MS = 500;
    private static final double MEMORY_WARNING_THRESHOLD = 0.8; // 80%
    private static final double MEMORY_CRITICAL_THRESHOLD = 0.95; // 95%
    
    private PerformanceMetrics() {
        initializeDefaultMetrics();
        log.info("PerformanceMetrics system initialized");
    }
    
    public static PerformanceMetrics getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new PerformanceMetrics();
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize default metrics that are always tracked
     */
    private void initializeDefaultMetrics() {
        // Game loop metrics
        registerMetric("game.loop.frame_time", "Frame rendering time in milliseconds");
        registerMetric("game.loop.update_time", "Game state update time in milliseconds");
        registerMetric("game.loop.total_time", "Total game loop time in milliseconds");
        
        // Combat metrics
        registerMetric("combat.calculation_time", "Combat calculation time in milliseconds");
        registerMetric("combat.pathfinding_time", "Pathfinding calculation time in milliseconds");
        registerMetric("combat.visibility_time", "Visibility calculation time in milliseconds");
        
        // Memory metrics
        registerMetric("memory.heap_used", "Heap memory usage in MB");
        registerMetric("memory.heap_max", "Maximum heap size in MB");
        registerMetric("memory.heap_usage_percent", "Heap usage percentage");
        
        // System metrics
        registerMetric("system.cpu_usage", "CPU usage percentage");
        registerMetric("system.thread_count", "Active thread count");
        registerMetric("system.gc_time", "Garbage collection time in milliseconds");
        
        // Initialize counters
        counters.put("game.frames_rendered", new AtomicLong(0));
        counters.put("game.actions_performed", new AtomicLong(0));
        counters.put("game.combat_rounds", new AtomicLong(0));
        counters.put("memory.gc_cycles", new AtomicLong(0));
        
        // Initialize gauges
        gauges.put("game.fps", new AtomicReference<>(0.0));
        gauges.put("game.active_units", new AtomicReference<>(0.0));
        gauges.put("game.field_size", new AtomicReference<>(0.0));
    }
    
    /**
     * Register a new metric for tracking
     */
    public void registerMetric(String name, String description) {
        metrics.put(name, new MetricData(description));
        log.debug("Registered metric: {} - {}", name, description);
    }
    
    /**
     * Record a timing measurement
     */
    public void recordTiming(String metricName, long durationMs) {
        MetricData metric = metrics.get(metricName);
        if (metric == null) {
            log.warn("Attempted to record timing for unregistered metric: {}", metricName);
            return;
        }
        
        metric.recordValue(durationMs);
        
        // Check thresholds and log warnings
        if (durationMs > CRITICAL_THRESHOLD_MS) {
            log.error("CRITICAL performance issue: {} took {}ms", metricName, durationMs);
        } else if (durationMs > WARNING_THRESHOLD_MS) {
            log.warn("Performance warning: {} took {}ms", metricName, durationMs);
        }
        
        log.debug("Recorded timing for {}: {}ms", metricName, durationMs);
    }
    
    /**
     * Record a value measurement
     */
    public void recordValue(String metricName, double value) {
        MetricData metric = metrics.get(metricName);
        if (metric == null) {
            log.warn("Attempted to record value for unregistered metric: {}", metricName);
            return;
        }
        
        metric.recordValue(value);
        log.debug("Recorded value for {}: {}", metricName, value);
    }
    
    /**
     * Increment a counter
     */
    public void incrementCounter(String counterName) {
        AtomicLong counter = counters.get(counterName);
        if (counter != null) {
            counter.incrementAndGet();
        } else {
            log.warn("Attempted to increment unregistered counter: {}", counterName);
        }
    }
    
    /**
     * Set a gauge value
     */
    public void setGauge(String gaugeName, double value) {
        AtomicReference<Double> gauge = gauges.get(gaugeName);
        if (gauge != null) {
            gauge.set(value);
        } else {
            log.warn("Attempted to set unregistered gauge: {}", gaugeName);
        }
    }
    
    /**
     * Get current metric statistics
     */
    public Map<String, MetricStatistics> getStatistics() {
        Map<String, MetricStatistics> stats = new ConcurrentHashMap<>();
        
        metrics.forEach((name, metric) -> {
            stats.put(name, metric.getStatistics());
        });
        
        return stats;
    }
    
    /**
     * Get all current counter values
     */
    public Map<String, Long> getCounters() {
        Map<String, Long> result = new ConcurrentHashMap<>();
        counters.forEach((name, counter) -> {
            result.put(name, counter.get());
        });
        return result;
    }
    
    /**
     * Get all current gauge values
     */
    public Map<String, Double> getGauges() {
        Map<String, Double> result = new ConcurrentHashMap<>();
        gauges.forEach((name, gauge) -> {
            result.put(name, gauge.get());
        });
        return result;
    }
    
    /**
     * Reset all metrics (useful for testing or new game sessions)
     */
    public void resetAll() {
        metrics.values().forEach(MetricData::reset);
        counters.values().forEach(counter -> counter.set(0));
        log.info("All performance metrics have been reset");
    }
    
    /**
     * Generate performance report
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== PERFORMANCE METRICS REPORT ===\n");
        report.append("Generated at: ").append(Instant.now()).append("\n\n");
        
        // Timing metrics
        report.append("TIMING METRICS:\n");
        metrics.forEach((name, metric) -> {
            MetricStatistics stats = metric.getStatistics();
            report.append(String.format("  %s: avg=%.2fms, min=%.2fms, max=%.2fms, count=%d\n",
                name, stats.getAverage(), stats.getMin(), stats.getMax(), stats.getCount()));
        });
        
        // Counters
        report.append("\nCOUNTERS:\n");
        counters.forEach((name, counter) -> {
            report.append(String.format("  %s: %d\n", name, counter.get()));
        });
        
        // Gauges
        report.append("\nGAUGES:\n");
        gauges.forEach((name, gauge) -> {
            report.append(String.format("  %s: %.2f\n", name, gauge.get()));
        });
        
        return report.toString();
    }
    
    /**
     * Internal metric data storage
     */
    @Data
    private static class MetricData {
        private final String description;
        private final AtomicLong count = new AtomicLong(0);
        private final AtomicReference<Double> sum = new AtomicReference<>(0.0);
        private final AtomicReference<Double> min = new AtomicReference<>(Double.MAX_VALUE);
        private final AtomicReference<Double> max = new AtomicReference<>(Double.MIN_VALUE);
        private volatile double lastValue = 0.0;
        private volatile Instant lastUpdate = Instant.now();
        
        public MetricData(String description) {
            this.description = description;
        }
        
        public void recordValue(double value) {
            count.incrementAndGet();
            sum.updateAndGet(current -> current + value);
            
            double currentMin = min.get();
            while (value < currentMin && !min.compareAndSet(currentMin, value)) {
                currentMin = min.get();
            }
            
            double currentMax = max.get();
            while (value > currentMax && !max.compareAndSet(currentMax, value)) {
                currentMax = max.get();
            }
            
            lastValue = value;
            lastUpdate = Instant.now();
        }
        
        public void reset() {
            count.set(0);
            sum.set(0.0);
            min.set(Double.MAX_VALUE);
            max.set(Double.MIN_VALUE);
            lastValue = 0.0;
            lastUpdate = Instant.now();
        }
        
        public MetricStatistics getStatistics() {
            long currentCount = count.get();
            if (currentCount == 0) {
                return new MetricStatistics(0.0, 0.0, 0.0, 0, lastUpdate);
            }
            
            double average = sum.get() / currentCount;
            return new MetricStatistics(average, min.get(), max.get(), currentCount, lastUpdate);
        }
    }
    
    /**
     * Metric statistics data transfer object
     */
    @Data
    public static class MetricStatistics {
        private final double average;
        private final double min;
        private final double max;
        private final long count;
        private final Instant lastUpdate;
    }
}

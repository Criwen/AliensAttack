package com.aliensattack.core.monitoring;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Game loop profiling system for tracking performance of different game phases
 * Provides detailed timing analysis and performance insights
 */
@Log4j2
public class GameLoopProfiler {
    
    // Singleton instance
    private static volatile GameLoopProfiler instance;
    private static final Object lock = new Object();
    
    // Profiling data storage
    private final Map<String, PhaseProfile> phases = new ConcurrentHashMap<>();
    private final AtomicLong totalFrames = new AtomicLong(0);
    private final AtomicLong totalGameTime = new AtomicLong(0);
    private final AtomicReference<Instant> lastFrameStart = new AtomicReference<>();
    
    // Performance thresholds
    private static final long FRAME_TIME_WARNING_MS = 16; // 60 FPS target
    private static final long FRAME_TIME_CRITICAL_MS = 33; // 30 FPS minimum
    private static final long PHASE_WARNING_MS = 8; // Half frame time
    private static final long PHASE_CRITICAL_MS = 16; // Full frame time
    
    // Phase names
    public static final String PHASE_INPUT = "input_processing";
    public static final String PHASE_UPDATE = "game_update";
    public static final String PHASE_COMBAT = "combat_calculation";
    public static final String PHASE_AI = "ai_processing";
    public static final String PHASE_RENDER = "rendering";
    public static final String PHASE_SYNC = "synchronization";
    
    private GameLoopProfiler() {
        initializePhases();
        log.info("GameLoopProfiler system initialized");
    }
    
    public static GameLoopProfiler getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new GameLoopProfiler();
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize default game loop phases
     */
    private void initializePhases() {
        phases.put(PHASE_INPUT, new PhaseProfile("Input Processing"));
        phases.put(PHASE_UPDATE, new PhaseProfile("Game Update"));
        phases.put(PHASE_COMBAT, new PhaseProfile("Combat Calculation"));
        phases.put(PHASE_AI, new PhaseProfile("AI Processing"));
        phases.put(PHASE_RENDER, new PhaseProfile("Rendering"));
        phases.put(PHASE_SYNC, new PhaseProfile("Synchronization"));
        
        log.debug("Initialized {} game loop phases", phases.size());
    }
    
    /**
     * Start profiling a new frame
     */
    public void startFrame() {
        lastFrameStart.set(Instant.now());
        totalFrames.incrementAndGet();
        
        // Reset phase timings for new frame
        phases.values().forEach(PhaseProfile::resetFrame);
        
        log.debug("Started profiling frame #{}", totalFrames.get());
    }
    
    /**
     * End profiling the current frame
     */
    public void endFrame() {
        Instant frameStart = lastFrameStart.get();
        if (frameStart == null) {
            log.warn("Attempted to end frame without starting one");
            return;
        }
        
        long frameTime = Duration.between(frameStart, Instant.now()).toMillis();
        totalGameTime.addAndGet(frameTime);
        
        // Check frame time performance
        if (frameTime > FRAME_TIME_CRITICAL_MS) {
            log.error("CRITICAL: Frame took {}ms (target: 16ms for 60 FPS)", frameTime);
        } else if (frameTime > FRAME_TIME_WARNING_MS) {
            log.warn("WARNING: Frame took {}ms (target: 16ms for 60 FPS)", frameTime);
        }
        
        // Update performance metrics
        PerformanceMetrics.getInstance().recordTiming("game.loop.total_time", frameTime);
        PerformanceMetrics.getInstance().setGauge("game.fps", 1000.0 / frameTime);
        
        log.debug("Frame #{} completed in {}ms", totalFrames.get(), frameTime);
    }
    
    /**
     * Start profiling a specific phase
     */
    public void startPhase(String phaseName) {
        PhaseProfile phase = phases.get(phaseName);
        if (phase == null) {
            log.warn("Attempted to profile unknown phase: {}", phaseName);
            return;
        }
        
        phase.startPhase();
        log.debug("Started profiling phase: {}", phaseName);
    }
    
    /**
     * End profiling a specific phase
     */
    public void endPhase(String phaseName) {
        PhaseProfile phase = phases.get(phaseName);
        if (phase == null) {
            log.warn("Attempted to end profiling for unknown phase: {}", phaseName);
            return;
        }
        
        long phaseTime = phase.endPhase();
        
        // Check phase time performance
        if (phaseTime > PHASE_CRITICAL_MS) {
            log.error("CRITICAL: Phase '{}' took {}ms (exceeds frame budget)", phaseName, phaseTime);
        } else if (phaseTime > PHASE_WARNING_MS) {
            log.warn("WARNING: Phase '{}' took {}ms (approaching frame budget)", phaseName, phaseTime);
        }
        
        // Update performance metrics
        PerformanceMetrics.getInstance().recordTiming("game.loop." + phaseName + "_time", phaseTime);
        
        log.debug("Phase '{}' completed in {}ms", phaseName, phaseTime);
    }
    
    /**
     * Profile a specific operation with automatic start/stop
     */
    public void profileOperation(String phaseName, Runnable operation) {
        startPhase(phaseName);
        try {
            operation.run();
        } finally {
            endPhase(phaseName);
        }
    }
    
    /**
     * Get current frame statistics
     */
    public FrameStatistics getCurrentFrameStatistics() {
        long totalPhaseTime = phases.values().stream()
            .mapToLong(PhaseProfile::getCurrentFrameTime)
            .sum();
        
        return new FrameStatistics(
            totalFrames.get(),
            totalPhaseTime,
            phases
        );
    }
    
    /**
     * Get overall performance statistics
     */
    public OverallStatistics getOverallStatistics() {
        long totalFramesCount = totalFrames.get();
        if (totalFramesCount == 0) {
            return new OverallStatistics(0, 0.0, 0.0, 0.0, Map.of());
        }
        
        double averageFrameTime = (double) totalGameTime.get() / totalFramesCount;
        double averageFPS = 1000.0 / averageFrameTime;
        
        Map<String, PhaseStatistics> phaseStats = new ConcurrentHashMap<>();
        phases.forEach((name, phase) -> {
            phaseStats.put(name, phase.getOverallStatistics());
        });
        
        return new OverallStatistics(
            totalFramesCount,
            averageFrameTime,
            averageFPS,
            (double) totalGameTime.get() / 1000.0, // Total game time in seconds
            phaseStats
        );
    }
    
    /**
     * Reset all profiling data
     */
    public void reset() {
        totalFrames.set(0);
        totalGameTime.set(0);
        lastFrameStart.set(null);
        phases.values().forEach(PhaseProfile::resetAll);
        
        log.info("GameLoopProfiler data reset");
    }
    
    /**
     * Generate detailed profiling report
     */
    public String generateReport() {
        OverallStatistics stats = getOverallStatistics();
        StringBuilder report = new StringBuilder();
        
        report.append("=== GAME LOOP PROFILING REPORT ===\n");
        report.append("Total Frames: ").append(stats.getTotalFrames()).append("\n");
        report.append("Average Frame Time: ").append(String.format("%.2f", stats.getAverageFrameTime())).append("ms\n");
        report.append("Average FPS: ").append(String.format("%.2f", stats.getAverageFPS())).append("\n");
        report.append("Total Game Time: ").append(String.format("%.2f", stats.getTotalGameTimeSeconds())).append("s\n\n");
        
        report.append("PHASE BREAKDOWN:\n");
        stats.getPhaseStatistics().forEach((phaseName, phaseStats) -> {
            report.append(String.format("  %s:\n", phaseName));
            report.append(String.format("    Total Time: %.2fms\n", phaseStats.getTotalTime()));
            report.append(String.format("    Average Time: %.2fms\n", phaseStats.getAverageTime()));
            report.append(String.format("    Min Time: %.2fms\n", phaseStats.getMinTime()));
            report.append(String.format("    Max Time: %.2fms\n", phaseStats.getMaxTime()));
            report.append(String.format("    Call Count: %d\n", phaseStats.getCallCount()));
            report.append("\n");
        });
        
        return report.toString();
    }
    
    /**
     * Internal phase profile data
     */
    @Data
    private static class PhaseProfile {
        private final String description;
        private final AtomicLong totalTime = new AtomicLong(0);
        private final AtomicLong callCount = new AtomicLong(0);
        private final AtomicLong minTime = new AtomicLong(Long.MAX_VALUE);
        private final AtomicLong maxTime = new AtomicLong(0);
        private volatile long currentFrameTime = 0;
        private volatile Instant phaseStart = null;
        
        public PhaseProfile(String description) {
            this.description = description;
        }
        
        public void startPhase() {
            phaseStart = Instant.now();
        }
        
        public long endPhase() {
            if (phaseStart == null) {
                log.warn("Attempted to end phase without starting it");
                return 0;
            }
            
            long duration = Duration.between(phaseStart, Instant.now()).toMillis();
            
            // Update overall statistics
            totalTime.addAndGet(duration);
            callCount.incrementAndGet();
            
            // Update min/max
            long currentMin = minTime.get();
            while (duration < currentMin && !minTime.compareAndSet(currentMin, duration)) {
                currentMin = minTime.get();
            }
            
            long currentMax = maxTime.get();
            while (duration > currentMax && !maxTime.compareAndSet(currentMax, duration)) {
                currentMax = maxTime.get();
            }
            
            // Update current frame time
            currentFrameTime = duration;
            phaseStart = null;
            
            return duration;
        }
        
        public void resetFrame() {
            currentFrameTime = 0;
        }
        
        public void resetAll() {
            totalTime.set(0);
            callCount.set(0);
            minTime.set(Long.MAX_VALUE);
            maxTime.set(0);
            currentFrameTime = 0;
            phaseStart = null;
        }
        
        public long getCurrentFrameTime() {
            return currentFrameTime;
        }
        
        public PhaseStatistics getOverallStatistics() {
            long calls = callCount.get();
            if (calls == 0) {
                return new PhaseStatistics(0.0, 0.0, 0.0, 0.0, 0);
            }
            
            double total = totalTime.get();
            double average = total / calls;
            double min = minTime.get() == Long.MAX_VALUE ? 0.0 : minTime.get();
            double max = maxTime.get();
            
            return new PhaseStatistics(total, average, min, max, calls);
        }
    }
    
    /**
     * Frame statistics data transfer object
     */
    @Data
    public static class FrameStatistics {
        private final long frameNumber;
        private final long totalPhaseTime;
        private final Map<String, PhaseProfile> phases;
    }
    
    /**
     * Overall statistics data transfer object
     */
    @Data
    public static class OverallStatistics {
        private final long totalFrames;
        private final double averageFrameTime;
        private final double averageFPS;
        private final double totalGameTimeSeconds;
        private final Map<String, PhaseStatistics> phaseStatistics;
    }
    
    /**
     * Phase statistics data transfer object
     */
    @Data
    public static class PhaseStatistics {
        private final double totalTime;
        private final double averageTime;
        private final double minTime;
        private final double maxTime;
        private final long callCount;
    }
}

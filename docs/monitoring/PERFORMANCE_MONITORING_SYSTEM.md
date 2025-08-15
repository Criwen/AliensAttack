# Performance Monitoring System

## Overview

The AliensAttack Performance Monitoring System is a comprehensive solution for tracking, analyzing, and optimizing game performance. It consists of three main components that work together to provide complete visibility into the game's performance characteristics.

## Architecture

```
PerformanceMonitoringManager (Main Controller)
├── PerformanceMetrics (Metrics Collection)
├── GameLoopProfiler (Game Loop Analysis)
└── MemoryMonitor (Memory & GC Tracking)
```

## Components

### 1. Performance Metrics System

The Performance Metrics system collects and tracks various performance indicators throughout the game.

#### Features
- **Timing Metrics**: Track execution time of operations
- **Counters**: Increment counters for events
- **Gauges**: Set current values for metrics
- **Threshold Monitoring**: Automatic warnings for performance issues
- **Statistical Analysis**: Min, max, average, and count tracking

#### Usage Example

```java
// Get the metrics instance
PerformanceMetrics metrics = PerformanceMetrics.getInstance();

// Record timing measurements
metrics.recordTiming("combat.calculation_time", 45);
metrics.recordTiming("pathfinding.time", 12);

// Increment counters
metrics.incrementCounter("game.actions_performed");
metrics.incrementCounter("combat.rounds");

// Set gauge values
metrics.setGauge("game.fps", 60.0);
metrics.setGauge("active.units", 8.0);

// Get statistics
Map<String, MetricStatistics> stats = metrics.getStatistics();
Map<String, Long> counters = metrics.getCounters();
Map<String, Double> gauges = metrics.getGauges();
```

#### Default Metrics

The system automatically tracks these metrics:

**Game Loop Metrics**
- `game.loop.frame_time` - Frame rendering time
- `game.loop.update_time` - Game state update time
- `game.loop.total_time` - Total game loop time

**Combat Metrics**
- `combat.calculation_time` - Combat calculation time
- `combat.pathfinding_time` - Pathfinding calculation time
- `combat.visibility_time` - Visibility calculation time

**Memory Metrics**
- `memory.heap_used` - Heap memory usage in MB
- `memory.heap_max` - Maximum heap size in MB
- `memory.heap_usage_percent` - Heap usage percentage

**System Metrics**
- `system.cpu_usage` - CPU usage percentage
- `system.thread_count` - Active thread count
- `system.gc_time` - Garbage collection time

### 2. Game Loop Profiler

The Game Loop Profiler tracks performance of different game loop phases and provides detailed timing analysis.

#### Features
- **Frame Profiling**: Track complete frame timing
- **Phase Profiling**: Monitor individual game loop phases
- **Performance Thresholds**: Automatic warnings for slow phases
- **Statistical Analysis**: Comprehensive timing statistics
- **Automatic Operation Profiling**: Profile operations with lambda support

#### Game Loop Phases

- `input_processing` - User input handling
- `game_update` - Game state updates
- `combat_calculation` - Combat system calculations
- `ai_processing` - AI decision making
- `rendering` - Visual rendering
- `synchronization` - Frame synchronization

#### Usage Example

```java
// Get the profiler instance
GameLoopProfiler profiler = GameLoopProfiler.getInstance();

// Profile a complete frame
profiler.startFrame();

// Profile individual phases
profiler.startPhase(GameLoopProfiler.PHASE_RENDER);
// ... rendering code ...
profiler.endPhase(GameLoopProfiler.PHASE_RENDER);

profiler.startPhase(GameLoopProfiler.PHASE_UPDATE);
// ... update code ...
profiler.endPhase(GameLoopProfiler.PHASE_UPDATE);

profiler.endFrame();

// Profile operations automatically
profiler.profileOperation(GameLoopProfiler.PHASE_COMBAT, () -> {
    // Combat calculation code
    performCombatCalculations();
});

// Get statistics
GameLoopProfiler.OverallStatistics stats = profiler.getOverallStatistics();
GameLoopProfiler.FrameStatistics frameStats = profiler.getCurrentFrameStatistics();
```

#### Performance Thresholds

- **Frame Time Warning**: 16ms (60 FPS target)
- **Frame Time Critical**: 33ms (30 FPS minimum)
- **Phase Warning**: 8ms (half frame budget)
- **Phase Critical**: 16ms (full frame budget)

### 3. Memory Monitor

The Memory Monitor tracks heap usage, garbage collection performance, and detects potential memory leaks.

#### Features
- **Memory Snapshots**: Capture current memory state
- **GC Monitoring**: Track garbage collection performance
- **Memory Leak Detection**: Identify potential memory issues
- **Threshold Monitoring**: Automatic warnings for memory pressure
- **Historical Tracking**: Maintain memory usage history

#### Usage Example

```java
// Get the memory monitor instance
MemoryMonitor memoryMonitor = MemoryMonitor.getInstance();

// Capture current memory state
MemoryMonitor.MemorySnapshot snapshot = memoryMonitor.captureMemorySnapshot();

// Force garbage collection and measure performance
MemoryMonitor.GCSnapshot gcSnapshot = memoryMonitor.forceGarbageCollection();

// Get memory statistics
MemoryMonitor.MemoryStatistics stats = memoryMonitor.getMemoryStatistics();

// Check memory health
double heapUsage = snapshot.getHeapUsagePercent();
if (heapUsage > 0.8) {
    log.warn("High memory usage: {}%", heapUsage * 100);
}
```

#### Memory Thresholds

- **Heap Warning**: 75% usage
- **Heap Critical**: 90% usage
- **Heap Emergency**: 95% usage
- **Memory Leak**: 1MB/hour growth rate

### 4. Performance Monitoring Manager

The Performance Monitoring Manager integrates all three systems and provides a unified interface.

#### Features
- **Unified Management**: Single point of control for all monitoring
- **Game Session Management**: Start/stop monitoring for game sessions
- **Periodic Reporting**: Automatic performance reports
- **Comprehensive Reports**: Detailed performance analysis
- **Performance Recommendations**: Actionable optimization suggestions

#### Usage Example

```java
// Get the monitoring manager
PerformanceMonitoringManager manager = PerformanceMonitoringManager.getInstance();

// Start monitoring a new game session
manager.startGameSession();

// ... game runs ...

// Get quick performance status
PerformanceMonitoringManager.PerformanceStatus status = manager.getQuickStatus();
log.info("Current FPS: {}, Heap Usage: {}%", 
    status.getAverageFPS(), 
    status.getHeapUsagePercent() * 100);

// Generate comprehensive report
String report = manager.generateComprehensiveReport();
log.info("Performance Report:\n{}", report);

// End game session
manager.endGameSession();

// Shutdown monitoring (call on application shutdown)
manager.shutdown();
```

## Configuration

The monitoring system is configured through `src/main/resources/performance.properties`:

```properties
# Performance Metrics System
performance.metrics.enabled=true
performance.metrics.warning_threshold_ms=100
performance.metrics.critical_threshold_ms=500

# Game Loop Profiling
performance.profiling.enabled=true
performance.profiling.frame_time_warning_ms=16
performance.profiling.frame_time_critical_ms=33

# Memory and GC Monitoring
performance.memory.monitoring.enabled=true
performance.memory.monitoring.interval_ms=5000
performance.memory.monitoring.heap_warning_threshold=0.75
```

## Integration with Game Systems

### Game Loop Integration

```java
public class GameLoop {
    private final GameLoopProfiler profiler = GameLoopProfiler.getInstance();
    private final PerformanceMetrics metrics = PerformanceMetrics.getInstance();
    
    public void runGameLoop() {
        while (gameRunning) {
            profiler.startFrame();
            
            // Input processing
            profiler.startPhase(GameLoopProfiler.PHASE_INPUT);
            processInput();
            profiler.endPhase(GameLoopProfiler.PHASE_INPUT);
            
            // Game update
            profiler.startPhase(GameLoopProfiler.PHASE_UPDATE);
            updateGameState();
            profiler.endPhase(GameLoopProfiler.PHASE_UPDATE);
            
            // Rendering
            profiler.startPhase(GameLoopProfiler.PHASE_RENDER);
            renderFrame();
            profiler.endPhase(GameLoopProfiler.PHASE_RENDER);
            
            profiler.endFrame();
            
            // Update metrics
            metrics.incrementCounter("game.frames_rendered");
            metrics.setGauge("game.fps", calculateCurrentFPS());
        }
    }
}
```

### Combat System Integration

```java
public class CombatManager {
    private final PerformanceMetrics metrics = PerformanceMetrics.getInstance();
    
    public CombatResult performCombat(Unit attacker, Unit target) {
        long startTime = System.currentTimeMillis();
        
        try {
            CombatResult result = calculateCombat(attacker, target);
            return result;
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            metrics.recordTiming("combat.calculation_time", duration);
            metrics.incrementCounter("combat.rounds");
        }
    }
}
```

### Memory Management Integration

```java
public class ResourceManager {
    private final MemoryMonitor memoryMonitor = MemoryMonitor.getInstance();
    
    public void loadResources() {
        // Check memory before loading
        MemoryMonitor.MemorySnapshot snapshot = memoryMonitor.captureMemorySnapshot();
        
        if (snapshot.getHeapUsagePercent() > 0.8) {
            log.warn("High memory usage, forcing GC before resource loading");
            memoryMonitor.forceGarbageCollection();
        }
        
        // Load resources...
    }
}
```

## Performance Reports

### Periodic Reports

The system automatically generates performance reports every 60 seconds:

```
=== PERIODIC PERFORMANCE REPORT ===
Performance Metrics - 15 active metrics
Game Loop - Frames: 3600, Avg FPS: 58.2, Avg Frame Time: 17.2ms
Memory - Heap: 67.3% used, Threads: 12, GC Count: 3
```

### Comprehensive Reports

Generate detailed reports for analysis:

```
=== COMPREHENSIVE PERFORMANCE REPORT ===
Generated at: 2024-01-15T10:30:00Z

PERFORMANCE METRICS:
  game.loop.frame_time: avg=17.2ms, min=12.1ms, max=45.3ms, count=3600
  combat.calculation_time: avg=23.4ms, min=15.2ms, max=67.8ms, count=180
  memory.heap_usage_percent: avg=67.3, min=45.2, max=89.1, count=3600

GAME LOOP PROFILING:
Total Frames: 3600
Average Frame Time: 17.2ms
Average FPS: 58.2
Total Game Time: 61.9s

PHASE BREAKDOWN:
  input_processing:
    Total Time: 2.1s
    Average Time: 0.6ms
    Min Time: 0.3ms
    Max Time: 2.1ms
    Call Count: 3600

MEMORY MONITORING:
CURRENT MEMORY STATUS:
  Heap Used: 1024 MB
  Heap Max: 2048 MB
  Heap Usage: 67.3%
  Active Threads: 12
  Peak Threads: 15

PERFORMANCE SUMMARY:
✓ Excellent performance: 58.2 FPS
✓ Healthy memory usage: 67.3%

RECOMMENDATIONS:
- Performance is within acceptable ranges
- Memory usage is healthy
- Continue monitoring for any degradation
```

## Best Practices

### 1. Metric Naming

Use consistent naming conventions for metrics:
- Use dot notation: `system.cpu.usage`
- Group related metrics: `combat.attack.time`, `combat.defense.time`
- Use descriptive names: `game.loop.frame_time` not `gl.ft`

### 2. Performance Thresholds

Set appropriate thresholds based on your game's requirements:
- Frame time: 16ms for 60 FPS, 33ms for 30 FPS
- Memory usage: 75% warning, 90% critical
- Operation time: Based on user experience requirements

### 3. Monitoring Frequency

- **Real-time**: Critical metrics (FPS, memory usage)
- **Periodic**: Detailed analysis (every 60 seconds)
- **On-demand**: Comprehensive reports (end of sessions)

### 4. Error Handling

Always handle monitoring errors gracefully:
```java
try {
    metrics.recordTiming("operation.time", duration);
} catch (Exception e) {
    log.warn("Failed to record performance metric", e);
    // Don't let monitoring errors affect game performance
}
```

### 5. Resource Management

Properly shutdown monitoring systems:
```java
// In application shutdown
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    PerformanceMonitoringManager.getInstance().shutdown();
}));
```

## Troubleshooting

### Common Issues

1. **High Memory Usage**
   - Check for memory leaks
   - Review object pooling strategies
   - Consider reducing cache sizes

2. **Low FPS**
   - Profile game loop phases
   - Identify slow operations
   - Review rendering optimizations

3. **High GC Frequency**
   - Review object creation patterns
   - Check for unnecessary allocations
   - Consider JVM tuning

### Debug Mode

Enable debug logging for detailed monitoring information:
```properties
# In log4j2.xml
<Logger name="com.aliensattack.core.monitoring" level="DEBUG"/>
```

### Performance Impact

The monitoring system is designed to have minimal performance impact:
- **Metrics Collection**: < 1ms per operation
- **Memory Monitoring**: < 5ms per check
- **Game Loop Profiling**: < 0.1ms per phase
- **Overall Impact**: < 2% of frame budget

## Future Enhancements

### Planned Features

1. **Real-time Dashboard**: Web-based performance monitoring
2. **Alert System**: Email/SMS notifications for critical issues
3. **Performance Regression Detection**: Automatic detection of performance degradation
4. **Machine Learning**: Predictive performance analysis
5. **Integration with External Tools**: JProfiler, VisualVM integration

### Extensibility

The system is designed to be easily extensible:
- Add new metric types
- Implement custom profilers
- Create specialized monitors
- Integrate with external monitoring systems

## Conclusion

The Performance Monitoring System provides comprehensive visibility into game performance, enabling developers to identify and resolve performance issues quickly. By following the best practices and integrating the monitoring into your game systems, you can maintain optimal performance and provide the best gaming experience for your players.

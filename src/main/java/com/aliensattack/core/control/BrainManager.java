package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.GameContext;
import com.aliensattack.actions.interfaces.IAction;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Central manager for all brain systems
 * Handles brain assignment, control switching, and coordination
 */
@Log4j2
public class BrainManager {
    
    // Brain management
    private final Map<String, IBrain> registeredBrains;
    private final Map<String, String> unitToBrainMapping; // unitId -> brainId
    private final Map<String, String> brainToUnitMapping; // brainId -> unitId
    
    // Control coordination
    private final Queue<String> brainExecutionQueue;
    private final Set<String> activeBrains;
    private final Map<String, Long> brainLastActionTime;
    
    // Configuration
    private final int maxConcurrentBrains;
    private final long brainExecutionTimeout;
    private final boolean enableBrainSwitching;
    
    public BrainManager() {
        this(10, 5000, true); // Default: 10 max brains, 5s timeout, switching enabled
    }
    
    public BrainManager(int maxConcurrentBrains, long brainExecutionTimeout, boolean enableBrainSwitching) {
        this.maxConcurrentBrains = maxConcurrentBrains;
        this.brainExecutionTimeout = brainExecutionTimeout;
        this.enableBrainSwitching = enableBrainSwitching;
        
        this.registeredBrains = new ConcurrentHashMap<>();
        this.unitToBrainMapping = new ConcurrentHashMap<>();
        this.brainToUnitMapping = new ConcurrentHashMap<>();
        this.brainExecutionQueue = new LinkedList<>();
        this.activeBrains = ConcurrentHashMap.newKeySet();
        this.brainLastActionTime = new ConcurrentHashMap<>();
        
        log.info("Brain Manager initialized with max {} concurrent brains", maxConcurrentBrains);
    }
    
    // Brain registration and management
    
    /**
     * Register a brain with the manager
     */
    public boolean registerBrain(IBrain brain) {
        if (brain == null) {
            log.warn("Cannot register null brain");
            return false;
        }
        
        String brainId = brain.getBrainId();
        if (registeredBrains.containsKey(brainId)) {
            log.warn("Brain {} already registered", brainId);
            return false;
        }
        
        registeredBrains.put(brainId, brain);
        log.info("Brain {} registered successfully", brainId);
        return true;
    }
    
    /**
     * Unregister a brain from the manager
     */
    public boolean unregisterBrain(String brainId) {
        IBrain brain = registeredBrains.get(brainId);
        if (brain == null) {
            log.warn("Brain {} not found for unregistration", brainId);
            return false;
        }
        
        // Release control if brain is controlling a unit
        if (brain.isControlling()) {
            releaseUnitControl(brainId);
        }
        
        registeredBrains.remove(brainId);
        brainExecutionQueue.remove(brainId);
        activeBrains.remove(brainId);
        brainLastActionTime.remove(brainId);
        
        log.info("Brain {} unregistered successfully", brainId);
        return true;
    }
    
    /**
     * Get a registered brain by ID
     */
    public Optional<IBrain> getBrain(String brainId) {
        return Optional.ofNullable(registeredBrains.get(brainId));
    }
    
    /**
     * Get all registered brains
     */
    public List<IBrain> getAllBrains() {
        return new ArrayList<>(registeredBrains.values());
    }
    
    /**
     * Get brains by type
     */
    public List<IBrain> getBrainsByType(IBrain.BrainType type) {
        return registeredBrains.values().stream()
                .filter(brain -> brain.getBrainType() == type)
                .collect(Collectors.toList());
    }
    
    // Unit control management
    
    /**
     * Assign a brain to control a unit
     */
    public boolean assignBrainToUnit(String brainId, String unitId) {
        IBrain brain = registeredBrains.get(brainId);
        if (brain == null) {
            log.warn("Cannot assign non-existent brain {} to unit {}", brainId, unitId);
            return false;
        }
        
        // Check if unit is already controlled by another brain
        String currentBrainId = unitToBrainMapping.get(unitId);
        if (currentBrainId != null && !currentBrainId.equals(brainId)) {
            if (!enableBrainSwitching) {
                log.warn("Brain switching disabled, unit {} already controlled by {}", unitId, currentBrainId);
                return false;
            }
            
            // Release current brain's control
            releaseUnitControl(currentBrainId);
        }
        
        // Check if brain is already controlling another unit
        String currentUnitId = brainToUnitMapping.get(brainId);
        if (currentUnitId != null && !currentUnitId.equals(unitId)) {
            log.warn("Brain {} already controlling unit {}, cannot assign to {}", brainId, currentUnitId, unitId);
            return false;
        }
        
        // Create unit reference (in real implementation, get from game context)
        // For now, we'll assume the brain can handle this
        try {
            // Update mappings
            unitToBrainMapping.put(unitId, brainId);
            brainToUnitMapping.put(brainId, unitId);
            
            // Add to execution queue
            if (!brainExecutionQueue.contains(brainId)) {
                brainExecutionQueue.offer(brainId);
            }
            
            log.info("Brain {} assigned to unit {}", brainId, unitId);
            return true;
            
        } catch (Exception e) {
            log.error("Error assigning brain {} to unit {}", brainId, unitId, e);
            return false;
        }
    }
    
    /**
     * Release a brain's control of a unit
     */
    public boolean releaseUnitControl(String brainId) {
        String unitId = brainToUnitMapping.get(brainId);
        if (unitId == null) {
            log.warn("Brain {} not controlling any unit", brainId);
            return false;
        }
        
        IBrain brain = registeredBrains.get(brainId);
        if (brain != null) {
            brain.releaseControl();
        }
        
        // Remove mappings
        unitToBrainMapping.remove(unitId);
        brainToUnitMapping.remove(brainId);
        brainExecutionQueue.remove(brainId);
        activeBrains.remove(brainId);
        brainLastActionTime.remove(brainId);
        
        log.info("Brain {} released control of unit {}", brainId, unitId);
        return true;
    }
    
    /**
     * Get the brain controlling a specific unit
     */
    public Optional<IBrain> getBrainControllingUnit(String unitId) {
        String brainId = unitToBrainMapping.get(unitId);
        return Optional.ofNullable(registeredBrains.get(brainId));
    }
    
    /**
     * Get the unit controlled by a specific brain
     */
    public Optional<String> getUnitControlledByBrain(String brainId) {
        return Optional.ofNullable(brainToUnitMapping.get(brainId));
    }
    
    // Brain execution management
    
    /**
     * Execute brains for the current game context
     */
    public void executeBrains(GameContext context) {
        if (context == null) {
            log.warn("Cannot execute brains with null context");
            return;
        }
        
        // Update all brains with current context
        registeredBrains.values().forEach(brain -> {
            if (brain.isControlling()) {
                brain.update(context);
            }
        });
        
        // Execute brains in priority order
        List<IBrain> brainsToExecute = getBrainsByPriority();
        
        for (IBrain brain : brainsToExecute) {
            if (!brain.isReady()) {
                continue;
            }
            
            if (activeBrains.size() >= maxConcurrentBrains) {
                log.debug("Maximum concurrent brains reached, skipping {}", brain.getBrainId());
                continue;
            }
            
            executeBrain(brain, context);
        }
        
        // Clean up inactive brains
        cleanupInactiveBrains();
    }
    
    /**
     * Execute a specific brain
     */
    private void executeBrain(IBrain brain, GameContext context) {
        String brainId = brain.getBrainId();
        
        try {
            // Mark brain as active
            activeBrains.add(brainId);
            brainLastActionTime.put(brainId, System.currentTimeMillis());
            
            // Select and execute action
            Optional<IAction> action = brain.selectAction(context);
            
            if (action.isPresent()) {
                boolean success = brain.executeAction(action.get());
                
                if (success) {
                    log.debug("Brain {} successfully executed action {}", brainId, action.get().getActionType());
                } else {
                    log.warn("Brain {} failed to execute action {}", brainId, action.get().getActionType());
                }
            } else {
                log.debug("Brain {} no action selected", brainId);
            }
            
        } catch (Exception e) {
            log.error("Error executing brain {}", brainId, e);
        } finally {
            // Mark brain as inactive
            activeBrains.remove(brainId);
        }
    }
    
    /**
     * Get brains ordered by priority
     */
    private List<IBrain> getBrainsByPriority() {
        return registeredBrains.values().stream()
                .filter(IBrain::isControlling)
                .sorted(Comparator.comparingInt(IBrain::getPriority).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * Clean up inactive brains
     */
    private void cleanupInactiveBrains() {
        long currentTime = System.currentTimeMillis();
        
        brainLastActionTime.entrySet().removeIf(entry -> {
            String brainId = entry.getKey();
            long lastActionTime = entry.getValue();
            
            if (currentTime - lastActionTime > brainExecutionTimeout) {
                log.warn("Brain {} timed out, removing from active list", brainId);
                activeBrains.remove(brainId);
                return true;
            }
            
            return false;
        });
    }
    
    // Brain coordination
    
    /**
     * Coordinate brains for team actions
     */
    public void coordinateBrains(List<String> brainIds, String coordinationType) {
        if (brainIds == null || brainIds.isEmpty()) {
            return;
        }
        
        log.info("Coordinating {} brains for {}", brainIds.size(), coordinationType);
        
        // Implement coordination logic based on type
        switch (coordinationType.toUpperCase()) {
            case "FLANKING":
                coordinateFlanking(brainIds);
                break;
            case "DEFENSIVE":
                coordinateDefensive(brainIds);
                break;
            case "SUPPORT":
                coordinateSupport(brainIds);
                break;
            default:
                log.warn("Unknown coordination type: {}", coordinationType);
        }
    }
    
    private void coordinateFlanking(List<String> brainIds) {
        // Implement flanking coordination
        log.debug("Coordinating flanking maneuver for {} brains", brainIds.size());
    }
    
    private void coordinateDefensive(List<String> brainIds) {
        // Implement defensive coordination
        log.debug("Coordinating defensive formation for {} brains", brainIds.size());
    }
    
    private void coordinateSupport(List<String> brainIds) {
        // Implement support coordination
        log.debug("Coordinating support actions for {} brains", brainIds.size());
    }
    
    // Utility methods
    
    /**
     * Get brain statistics
     */
    public Map<String, Object> getBrainStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalBrains", registeredBrains.size());
        stats.put("activeBrains", activeBrains.size());
        stats.put("controlledUnits", unitToBrainMapping.size());
        stats.put("brainTypes", getBrainTypeDistribution());
        
        return stats;
    }
    
    private Map<String, Integer> getBrainTypeDistribution() {
        return registeredBrains.values().stream()
                .collect(Collectors.groupingBy(
                    brain -> brain.getBrainType().name(),
                    Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }
    
    /**
     * Reset all brains for new turn
     */
    public void resetAllBrainsForNewTurn() {
        registeredBrains.values().forEach(IBrain::resetForNewTurn);
        log.info("All brains reset for new turn");
    }
    
    /**
     * Get brain performance metrics
     */
    public Map<String, IBrain.BrainMetrics> getAllBrainMetrics() {
        Map<String, IBrain.BrainMetrics> metrics = new HashMap<>();
        
        registeredBrains.forEach((brainId, brain) -> {
            metrics.put(brainId, brain.getMetrics());
        });
        
        return metrics;
    }
}

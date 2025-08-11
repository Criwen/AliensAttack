package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Alien Evolution System - XCOM 2 Tactical Combat
 * Implements dynamic alien adaptation and evolution based on player tactics 
 * and mission outcomes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedAlienEvolutionSystem {
    
    private String evolutionId;
    private Map<String, AlienUnit> alienUnits;
    private Map<String, EvolutionTrigger> evolutionTriggers;
    private Map<String, EvolutionType> evolutionTypes;
    private Map<String, EvolutionTracker> evolutionTrackers;
    private Map<String, EvolutionCounter> evolutionCounters;
    private Map<String, EvolutionConsequence> evolutionConsequences;
    private List<EvolutionEvent> evolutionEvents;
    private Map<String, Integer> evolutionLevels;
    private Map<String, Boolean> evolutionStates;
    private int totalEvolutionPoints;
    private int maxEvolutionLevel;
    private boolean isEvolutionActive;
    private int evolutionRate;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlienUnit {
        private String unitId;
        private String unitName;
        private AlienType alienType;
        private int currentHealth;
        private int maxHealth;
        private int currentEvolutionLevel;
        private int maxEvolutionLevel;
        private Map<String, Integer> evolutionBonuses;
        private List<String> evolvedAbilities;
        private boolean isEvolved;
        private String evolutionPath;
        private int evolutionPoints;
        
        public enum AlienType {
            ADVENT_TROOPER,    // Basic ADVENT trooper
            ADVENT_OFFICER,    // ADVENT officer
            ADVENT_STUN_LANCER, // ADVENT stun lancer
            ADVENT_MEC,        // ADVENT MEC
            ADVENT_PRIEST,     // ADVENT priest
            ADVENT_TURRET,     // ADVENT turret
            SECTOID,           // Sectoid
            VIPER,            // Viper
            MUTON,            // Muton
            BERSERKER,        // Berserker
            ARCHON,           // Archon
            ANDROMEDON,       // Andromedon
            CHRYSALID,        // Chrysalid
            CODEX,            // Codex
            GATEKEEPER,       // Gatekeeper
            AVATAR            // Avatar
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvolutionTrigger {
        private String triggerId;
        private String triggerName;
        private TriggerType triggerType;
        private String alienUnitId;
        private Map<String, Integer> triggerConditions;
        private Map<String, Integer> triggerEffects;
        private boolean isTriggered;
        private int triggerCount;
        private String description;
        
        public enum TriggerType {
            PLAYER_TACTIC,     // Player uses specific tactic
            MISSION_OUTCOME,   // Mission success/failure
            UNIT_SURVIVAL,     // Unit survives multiple encounters
            DAMAGE_TAKEN,      // Unit takes significant damage
            ABILITY_USAGE,     // Unit uses specific abilities
            ENVIRONMENT,       // Environmental factors
            TIME_PRESSURE,     // Time-based triggers
            RESOURCE_GAIN      // Resource acquisition triggers
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvolutionType {
        private String evolutionId;
        private String evolutionName;
        private EvolutionCategory evolutionCategory;
        private Map<String, Integer> evolutionBonuses;
        private List<String> newAbilities;
        private int evolutionCost;
        private boolean isAvailable;
        private String requirement;
        
        public enum EvolutionCategory {
            OFFENSIVE,        // Offensive evolution
            DEFENSIVE,        // Defensive evolution
            TACTICAL,         // Tactical evolution
            ADAPTIVE,         // Adaptive evolution
            SPECIALIZED,      // Specialized evolution
            HYBRID,           // Hybrid evolution
            COUNTER,          // Counter-evolution
            ESCALATION        // Escalation evolution
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvolutionTracker {
        private String trackerId;
        private String alienUnitId;
        private Map<String, Integer> evolutionProgress;
        private Map<String, Integer> evolutionHistory;
        private List<String> completedEvolutions;
        private int totalEvolutionPoints;
        private int evolutionRate;
        private boolean isTracking;
        
        public void trackEvolution(String evolutionType, int points) {
            evolutionProgress.put(evolutionType, 
                evolutionProgress.getOrDefault(evolutionType, 0) + points);
            evolutionHistory.put(evolutionType, 
                evolutionHistory.getOrDefault(evolutionType, 0) + 1);
            totalEvolutionPoints += points;
        }
        
        public boolean canEvolve(String evolutionType) {
            return evolutionProgress.getOrDefault(evolutionType, 0) >= 100;
        }
        
        public void completeEvolution(String evolutionType) {
            completedEvolutions.add(evolutionType);
            evolutionProgress.put(evolutionType, 0);
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvolutionCounter {
        private String counterId;
        private String counterName;
        private CounterType counterType;
        private String targetAlienId;
        private Map<String, Integer> counterEffects;
        private boolean isActive;
        private int duration;
        private String description;
        
        public enum CounterType {
            EVOLUTION_BLOCK,      // Block evolution temporarily
            EVOLUTION_SLOW,       // Slow evolution rate
            EVOLUTION_REVERSE,    // Reverse evolution effects
            EVOLUTION_RESET,      // Reset evolution progress
            EVOLUTION_DISABLE,    // Disable evolution abilities
            EVOLUTION_WEAKEN,     // Weaken evolved abilities
            EVOLUTION_PREVENT,    // Prevent new evolutions
            EVOLUTION_DEGRADE     // Degrade existing evolutions
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvolutionConsequence {
        private String consequenceId;
        private String consequenceName;
        private ConsequenceType consequenceType;
        private String alienUnitId;
        private Map<String, Integer> consequenceEffects;
        private boolean isTriggered;
        private int severity;
        private String description;
        
        public enum ConsequenceType {
            ABILITY_UNLOCK,       // Unlock new abilities
            STAT_BOOST,           // Increase stats
            RESISTANCE_GAIN,      // Gain resistances
            WEAKNESS_LOSS,        // Lose weaknesses
            ADAPTATION_GAIN,      // Gain adaptations
            COUNTER_DEVELOPMENT,  // Develop counters
            ESCALATION_TRIGGER,   // Trigger escalation
            MUTATION_ACTIVATION   // Activate mutations
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvolutionEvent {
        private String eventId;
        private String eventName;
        private EventType eventType;
        private String alienUnitId;
        private Map<String, Integer> eventEffects;
        private boolean isTriggered;
        private String description;
        
        public enum EventType {
            EVOLUTION_START,      // Evolution begins
            EVOLUTION_PROGRESS,   // Evolution progresses
            EVOLUTION_COMPLETE,   // Evolution completes
            EVOLUTION_FAILURE,    // Evolution fails
            EVOLUTION_REVERSE,    // Evolution reverses
            EVOLUTION_MUTATE,     // Evolution mutates
            EVOLUTION_ACCELERATE, // Evolution accelerates
            EVOLUTION_STAGNATE    // Evolution stagnates
        }
    }
    

    
    /**
     * Initialize alien evolution system with default values
     */
    public void initializeSystem() {
        this.evolutionId = "ALIEN_EVOLUTION_" + System.currentTimeMillis();
        this.alienUnits = new HashMap<>();
        this.evolutionTriggers = new HashMap<>();
        this.evolutionTypes = new HashMap<>();
        this.evolutionTrackers = new HashMap<>();
        this.evolutionCounters = new HashMap<>();
        this.evolutionConsequences = new HashMap<>();
        this.evolutionEvents = new ArrayList<>();
        this.evolutionLevels = new HashMap<>();
        this.evolutionStates = new HashMap<>();
        this.totalEvolutionPoints = 0;
        this.maxEvolutionLevel = 10;
        this.isEvolutionActive = false;
        this.evolutionRate = 1;
        
        // ToDo: Реализовать полную систему эволюции пришельцев
        // - Alien research and technology system
        // - Alien base system with facilities
        // - Alien infestation system
        // - Alien autopsy system for research
        // - Alien technology reverse engineering
        // - Alien evolution tree with multiple paths
        // - Alien adaptation to player tactics
        // - Alien mutation system
    }
    
    /**
     * Add alien unit
     */
    public void addAlienUnit(AlienUnit unit) {
        alienUnits.put(unit.getUnitId(), unit);
        evolutionLevels.put(unit.getUnitId(), unit.getCurrentEvolutionLevel());
        evolutionStates.put(unit.getUnitId(), unit.isEvolved());
        
        // Create evolution tracker for unit
        EvolutionTracker tracker = EvolutionTracker.builder()
                .trackerId(UUID.randomUUID().toString())
                .alienUnitId(unit.getUnitId())
                .evolutionProgress(new HashMap<>())
                .evolutionHistory(new HashMap<>())
                .completedEvolutions(new ArrayList<>())
                .totalEvolutionPoints(0)
                .evolutionRate(1)
                .isTracking(true)
                .build();
        
        evolutionTrackers.put(tracker.getTrackerId(), tracker);
    }
    
    /**
     * Trigger evolution
     */
    public boolean triggerEvolution(String triggerId, String alienUnitId) {
        EvolutionTrigger trigger = evolutionTriggers.get(triggerId);
        AlienUnit unit = alienUnits.get(alienUnitId);
        
        if (trigger != null && unit != null && canTriggerEvolution(trigger, unit)) {
            applyEvolutionTrigger(trigger, unit);
            trigger.setTriggered(true);
            trigger.setTriggerCount(trigger.getTriggerCount() + 1);
            return true;
        }
        return false;
    }
    
    /**
     * Evolve alien unit
     */
    public boolean evolveAlienUnit(String alienUnitId, String evolutionTypeId) {
        AlienUnit unit = alienUnits.get(alienUnitId);
        EvolutionType evolutionType = evolutionTypes.get(evolutionTypeId);
        
        if (unit != null && evolutionType != null && canEvolve(unit, evolutionType)) {
            applyEvolution(unit, evolutionType);
            return true;
        }
        return false;
    }
    
    /**
     * Apply evolution counter
     */
    public boolean applyEvolutionCounter(EvolutionCounter counter) {
        if (canApplyCounter(counter)) {
            evolutionCounters.put(counter.getCounterId(), counter);
            counter.setActive(true);
            applyCounterEffects(counter);
            return true;
        }
        return false;
    }
    
    /**
     * Track evolution progress
     */
    public void trackEvolutionProgress(String alienUnitId, String evolutionType, int points) {
        EvolutionTracker tracker = findTrackerForUnit(alienUnitId);
        if (tracker != null) {
            tracker.trackEvolution(evolutionType, points);
            totalEvolutionPoints += points;
        }
    }
    
    /**
     * Add evolution trigger
     */
    public void addEvolutionTrigger(EvolutionTrigger trigger) {
        evolutionTriggers.put(trigger.getTriggerId(), trigger);
    }
    
    /**
     * Add evolution type
     */
    public void addEvolutionType(EvolutionType evolutionType) {
        evolutionTypes.put(evolutionType.getEvolutionId(), evolutionType);
    }
    
    /**
     * Add evolution consequence
     */
    public void addEvolutionConsequence(EvolutionConsequence consequence) {
        evolutionConsequences.put(consequence.getConsequenceId(), consequence);
    }
    
    /**
     * Add evolution event
     */
    public void addEvolutionEvent(EvolutionEvent event) {
        evolutionEvents.add(event);
    }
    
    /**
     * Find tracker for unit
     */
    private EvolutionTracker findTrackerForUnit(String alienUnitId) {
        return evolutionTrackers.values().stream()
                .filter(tracker -> tracker.getAlienUnitId().equals(alienUnitId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Check if evolution can be triggered
     */
    private boolean canTriggerEvolution(EvolutionTrigger trigger, AlienUnit unit) {
        if (trigger.isTriggered() && trigger.getTriggerCount() >= 3) {
            return false; // Limit triggers per unit
        }
        
        // Check trigger conditions
        for (Map.Entry<String, Integer> condition : trigger.getTriggerConditions().entrySet()) {
            if (!checkTriggerCondition(unit, condition.getKey(), condition.getValue())) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Check if unit can evolve
     */
    private boolean canEvolve(AlienUnit unit, EvolutionType evolutionType) {
        if (!evolutionType.isAvailable()) {
            return false;
        }
        
        if (unit.getCurrentEvolutionLevel() >= unit.getMaxEvolutionLevel()) {
            return false;
        }
        
        if (unit.getEvolutionPoints() < evolutionType.getEvolutionCost()) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Check if counter can be applied
     */
    private boolean canApplyCounter(EvolutionCounter counter) {
        AlienUnit unit = alienUnits.get(counter.getTargetAlienId());
        return unit != null && unit.isEvolved();
    }
    
    /**
     * Check trigger condition
     */
    private boolean checkTriggerCondition(AlienUnit unit, String condition, int value) {
        switch (condition) {
            case "survival_count":
                return unit.getEvolutionPoints() >= value;
            case "damage_taken":
                return unit.getCurrentHealth() <= value;
            case "ability_usage":
                return unit.getEvolvedAbilities().size() >= value;
            case "evolution_level":
                return unit.getCurrentEvolutionLevel() >= value;
            default:
                return true;
        }
    }
    
    /**
     * Apply evolution trigger
     */
    private void applyEvolutionTrigger(EvolutionTrigger trigger, AlienUnit unit) {
        for (Map.Entry<String, Integer> effect : trigger.getTriggerEffects().entrySet()) {
            applyTriggerEffectToUnit(unit, effect.getKey(), effect.getValue());
        }
        
        // Add evolution points
        unit.setEvolutionPoints(unit.getEvolutionPoints() + 10);
    }
    
    /**
     * Apply evolution
     */
    private void applyEvolution(AlienUnit unit, EvolutionType evolutionType) {
        // Apply evolution bonuses
        for (Map.Entry<String, Integer> bonus : evolutionType.getEvolutionBonuses().entrySet()) {
            applyEvolutionBonusToUnit(unit, bonus.getKey(), bonus.getValue());
        }
        
        // Add new abilities
        unit.getEvolvedAbilities().addAll(evolutionType.getNewAbilities());
        
        // Increase evolution level
        unit.setCurrentEvolutionLevel(unit.getCurrentEvolutionLevel() + 1);
        unit.setEvolved(true);
        
        // Spend evolution points
        unit.setEvolutionPoints(unit.getEvolutionPoints() - evolutionType.getEvolutionCost());
        
        // Update tracking
        evolutionLevels.put(unit.getUnitId(), unit.getCurrentEvolutionLevel());
        evolutionStates.put(unit.getUnitId(), unit.isEvolved());
        
        // Track completion
        EvolutionTracker tracker = findTrackerForUnit(unit.getUnitId());
        if (tracker != null) {
            tracker.completeEvolution(evolutionType.getEvolutionId());
        }
    }
    
    /**
     * Apply counter effects
     */
    private void applyCounterEffects(EvolutionCounter counter) {
        AlienUnit unit = alienUnits.get(counter.getTargetAlienId());
        if (unit != null) {
            for (Map.Entry<String, Integer> effect : counter.getCounterEffects().entrySet()) {
                applyCounterEffectToUnit(unit, effect.getKey(), effect.getValue());
            }
        }
    }
    
    /**
     * Apply trigger effect to unit
     */
    private void applyTriggerEffectToUnit(AlienUnit unit, String effectType, int effectValue) {
        switch (effectType) {
            case "evolution_points":
                unit.setEvolutionPoints(unit.getEvolutionPoints() + effectValue);
                break;
            case "evolution_rate":
                // Increase evolution rate
                break;
            case "ability_unlock":
                // Unlock abilities
                break;
            case "stat_boost":
                // Boost stats
                break;
        }
    }
    
    /**
     * Apply evolution bonus to unit
     */
    private void applyEvolutionBonusToUnit(AlienUnit unit, String bonusType, int bonusValue) {
        switch (bonusType) {
            case "health":
                unit.setMaxHealth(unit.getMaxHealth() + bonusValue);
                break;
            case "damage":
                // Increase damage
                break;
            case "accuracy":
                // Increase accuracy
                break;
            case "defense":
                // Increase defense
                break;
            case "movement":
                // Increase movement
                break;
            case "abilities":
                // Add abilities
                break;
        }
    }
    
    /**
     * Apply counter effect to unit
     */
    private void applyCounterEffectToUnit(AlienUnit unit, String effectType, int effectValue) {
        switch (effectType) {
            case "evolution_block":
                // Block evolution temporarily
                break;
            case "evolution_slow":
                // Slow evolution rate
                break;
            case "evolution_reverse":
                // Reverse evolution effects
                break;
            case "evolution_reset":
                // Reset evolution progress
                break;
            case "evolution_disable":
                // Disable evolution abilities
                break;
            case "evolution_weaken":
                // Weaken evolved abilities
                break;
            case "evolution_prevent":
                // Prevent new evolutions
                break;
            case "evolution_degrade":
                // Degrade existing evolutions
                break;
        }
    }
    
    /**
     * Get evolved alien units
     */
    public List<AlienUnit> getEvolvedAlienUnits() {
        return alienUnits.values().stream()
                .filter(AlienUnit::isEvolved)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get evolution level for unit
     */
    public int getEvolutionLevel(String alienUnitId) {
        return evolutionLevels.getOrDefault(alienUnitId, 0);
    }
    
    /**
     * Check if unit is evolved
     */
    public boolean isUnitEvolved(String alienUnitId) {
        return evolutionStates.getOrDefault(alienUnitId, false);
    }
    
    /**
     * Get total evolution points
     */
    public int getTotalEvolutionPoints() {
        return totalEvolutionPoints;
    }
    
    /**
     * Get evolution rate
     */
    public int getEvolutionRate() {
        return evolutionRate;
    }
    
    /**
     * Set evolution rate
     */
    public void setEvolutionRate(int rate) {
        this.evolutionRate = Math.max(1, Math.min(5, rate));
    }
    
    /**
     * Get active evolution counters
     */
    public List<EvolutionCounter> getActiveEvolutionCounters() {
        return evolutionCounters.values().stream()
                .filter(EvolutionCounter::isActive)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get available evolution types
     */
    public List<EvolutionType> getAvailableEvolutionTypes() {
        return evolutionTypes.values().stream()
                .filter(EvolutionType::isAvailable)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Get evolution tracker for unit
     */
    public EvolutionTracker getEvolutionTracker(String alienUnitId) {
        return findTrackerForUnit(alienUnitId);
    }
}

package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Alien Evolution System for alien progression.
 * Implements alien evolution, adaptation, and progression mechanics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienEvolutionSystem {
    
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
        private boolean triggered;
        private int triggerCount;
        private String description;
        private boolean isActive;
        
        public enum TriggerType {
            PLAYER_TACTIC,     // Player uses specific tactic
            MISSION_OUTCOME,   // Mission success/failure
            UNIT_SURVIVAL,     // Unit survives multiple encounters
            DAMAGE_TAKEN,      // Unit takes significant damage
            ABILITY_USAGE,     // Unit uses specific abilities
            ENVIRONMENT,       // Environmental factors
            TIME_PRESSURE,     // Time-based triggers
            RESOURCE_GAIN,     // Resource acquisition triggers
            COMBAT_PERFORMANCE, // Combat performance triggers
            ENVIRONMENTAL_ADAPTATION, // Environmental adaptation
            PSIONIC_RESISTANCE, // Psionic resistance
            TECHNOLOGY_COUNTER, // Technology counter
            STRATEGIC_ADAPTATION // Strategic adaptation
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
        private List<String> counterAbilities;
        private boolean isActive;
        private int duration;
        private String description;
        private int counterCost;
        
        public enum CounterType {
            EVOLUTION_BLOCK,      // Block evolution temporarily
            EVOLUTION_SLOW,       // Slow evolution rate
            EVOLUTION_REVERSE,    // Reverse evolution effects
            EVOLUTION_RESET,      // Reset evolution progress
            EVOLUTION_DISABLE,    // Disable evolution abilities
            EVOLUTION_WEAKEN,     // Weaken evolved abilities
            EVOLUTION_PREVENT,    // Prevent new evolutions
            EVOLUTION_DEGRADE,    // Degrade existing evolutions
            PSIONIC_COUNTER,      // Psionic counter
            TECHNOLOGY_COUNTER,   // Technology counter
            ENVIRONMENTAL_COUNTER, // Environmental counter
            COMBAT_COUNTER,       // Combat counter
            STRATEGIC_COUNTER,    // Strategic counter
            TACTICAL_COUNTER,     // Tactical counter
            PSIONIC_WARFARE_COUNTER, // Psionic warfare counter
            TECHNOLOGY_WARFARE_COUNTER // Technology warfare counter
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
        private List<String> consequenceAbilities;
        private boolean isTriggered;
        private int severity;
        private String description;
        private boolean isActive;
        private int consequenceCost;
        
        public enum ConsequenceType {
            ABILITY_UNLOCK,       // Unlock new abilities
            STAT_BOOST,           // Increase stats
            RESISTANCE_GAIN,      // Gain resistances
            WEAKNESS_LOSS,        // Lose weaknesses
            ADAPTATION_GAIN,      // Gain adaptations
            COUNTER_DEVELOPMENT,  // Develop counters
            ESCALATION_TRIGGER,   // Trigger escalation
            MUTATION_ACTIVATION,  // Activate mutations
            POWER_INCREASE,       // Power increase
            THREAT_ESCALATION,    // Threat escalation
            ADAPTATION,           // Adaptation
            MUTATION,            // Mutation
            RESISTANCE_DEVELOPMENT, // Resistance development
            INTELLIGENCE_INCREASE, // Intelligence increase
            COORDINATION_ENHANCEMENT, // Coordination enhancement
            ULTIMATE_EVOLUTION    // Ultimate evolution
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
    
    public enum EvolutionStage {
        BASIC, INTERMEDIATE, ADVANCED, MASTER
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvolutionPath {
        private String pathId;
        private String pathName;
        private String pathType;
        private AlienUnit.AlienType targetAlienType;
        private int evolutionCost;
        private int evolutionTime;
        private int currentProgress;
        private int maxProgress;
        private double successRate;
        private double failureRate;
        private String evolutionBonus;
        private String evolutionPenalty;
        private boolean isActive;
        private boolean isCompleted;
        private boolean isFailed;
        private EvolutionStage evolutionStage;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvolutionMutation {
        private String mutationId;
        private String mutationName;
        private String mutationType;
        private String mutationEffect;
        private int mutationDuration;
        private int currentDuration;
        private boolean isActive;
        private boolean isPermanent;
    }
    

    
    /**
     * Initialize the system
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
        
        initializeEvolutionTriggers();
        initializeEvolutionTypes();
        initializeEvolutionCounters();
        initializeEvolutionConsequences();
    }
    
    /**
     * Initialize evolution triggers
     */
    private void initializeEvolutionTriggers() {
        // Player Tactic Trigger
        EvolutionTrigger playerTacticTrigger = EvolutionTrigger.builder()
            .triggerId("PLAYER_TACTIC_TRIGGER")
            .triggerName("Player Tactic Adaptation")
            .triggerType(EvolutionTrigger.TriggerType.PLAYER_TACTIC)
            .triggerConditions(Map.of("player_victory", 3, "tactic_usage", 5))
            .triggerEffects(Map.of("evolution_points", 15, "adaptation_bonus", 20))
            .triggered(false)
            .triggerCount(0)
            .isActive(true)
            .build();
        evolutionTriggers.put(playerTacticTrigger.getTriggerId(), playerTacticTrigger);
        
        // Mission Outcome Trigger
        EvolutionTrigger missionOutcomeTrigger = EvolutionTrigger.builder()
            .triggerId("MISSION_OUTCOME_TRIGGER")
            .triggerName("Mission Outcome Response")
            .triggerType(EvolutionTrigger.TriggerType.MISSION_OUTCOME)
            .triggerConditions(Map.of("alien_defeat", 2, "mission_failure", 3))
            .triggerEffects(Map.of("evolution_points", 20, "defense_bonus", 25))
            .triggered(false)
            .triggerCount(0)
            .isActive(true)
            .build();
        evolutionTriggers.put(missionOutcomeTrigger.getTriggerId(), missionOutcomeTrigger);
        
        // Unit Survival Trigger
        EvolutionTrigger unitSurvivalTrigger = EvolutionTrigger.builder()
            .triggerId("UNIT_SURVIVAL_TRIGGER")
            .triggerName("Unit Survival Adaptation")
            .triggerType(EvolutionTrigger.TriggerType.UNIT_SURVIVAL)
            .triggerConditions(Map.of("survival_rate", 80, "combat_experience", 10))
            .triggerEffects(Map.of("evolution_points", 10, "survival_bonus", 15))
            .triggered(false)
            .triggerCount(0)
            .isActive(true)
            .build();
        evolutionTriggers.put(unitSurvivalTrigger.getTriggerId(), unitSurvivalTrigger);
        
        // Combat Performance Trigger
        EvolutionTrigger combatPerformanceTrigger = EvolutionTrigger.builder()
            .triggerId("COMBAT_PERFORMANCE_TRIGGER")
            .triggerName("Combat Performance Enhancement")
            .triggerType(EvolutionTrigger.TriggerType.COMBAT_PERFORMANCE)
            .triggerConditions(Map.of("damage_dealt", 1000, "units_defeated", 5))
            .triggerEffects(Map.of("evolution_points", 25, "combat_bonus", 30))
            .triggered(false)
            .triggerCount(0)
            .isActive(true)
            .build();
        evolutionTriggers.put(combatPerformanceTrigger.getTriggerId(), combatPerformanceTrigger);
        
        // Environmental Adaptation Trigger
        EvolutionTrigger environmentalTrigger = EvolutionTrigger.builder()
            .triggerId("ENVIRONMENTAL_ADAPTATION_TRIGGER")
            .triggerName("Environmental Adaptation")
            .triggerType(EvolutionTrigger.TriggerType.ENVIRONMENTAL_ADAPTATION)
            .triggerConditions(Map.of("environment_exposure", 15, "hazard_survival", 8))
            .triggerEffects(Map.of("evolution_points", 12, "environmental_resistance", 20))
            .triggered(false)
            .triggerCount(0)
            .isActive(true)
            .build();
        evolutionTriggers.put(environmentalTrigger.getTriggerId(), environmentalTrigger);
        
        // Psionic Resistance Trigger
        EvolutionTrigger psionicTrigger = EvolutionTrigger.builder()
            .triggerId("PSIONIC_RESISTANCE_TRIGGER")
            .triggerName("Psionic Resistance Development")
            .triggerType(EvolutionTrigger.TriggerType.PSIONIC_RESISTANCE)
            .triggerConditions(Map.of("psionic_attacks", 20, "resistance_success", 15))
            .triggerEffects(Map.of("evolution_points", 18, "psionic_resistance", 25))
            .triggered(false)
            .triggerCount(0)
            .isActive(true)
            .build();
        evolutionTriggers.put(psionicTrigger.getTriggerId(), psionicTrigger);
        
        // Technology Counter Trigger
        EvolutionTrigger technologyTrigger = EvolutionTrigger.builder()
            .triggerId("TECHNOLOGY_COUNTER_TRIGGER")
            .triggerName("Technology Counter Development")
            .triggerType(EvolutionTrigger.TriggerType.TECHNOLOGY_COUNTER)
            .triggerConditions(Map.of("hacking_attempts", 25, "counter_success", 12))
            .triggerEffects(Map.of("evolution_points", 22, "technology_resistance", 30))
            .triggered(false)
            .triggerCount(0)
            .isActive(true)
            .build();
        evolutionTriggers.put(technologyTrigger.getTriggerId(), technologyTrigger);
        
        // Strategic Adaptation Trigger
        EvolutionTrigger strategicTrigger = EvolutionTrigger.builder()
            .triggerId("STRATEGIC_ADAPTATION_TRIGGER")
            .triggerName("Strategic Adaptation")
            .triggerType(EvolutionTrigger.TriggerType.STRATEGIC_ADAPTATION)
            .triggerConditions(Map.of("strategic_defeats", 3, "adaptation_success", 8))
            .triggerEffects(Map.of("evolution_points", 30, "strategic_bonus", 35))
            .triggered(false)
            .triggerCount(0)
            .isActive(true)
            .build();
        evolutionTriggers.put(strategicTrigger.getTriggerId(), strategicTrigger);
    }
    
    /**
     * Initialize evolution types
     */
    private void initializeEvolutionTypes() {
        // Offensive Evolution
        EvolutionType offensiveEvolution = EvolutionType.builder()
            .evolutionId("OFFENSIVE_EVOLUTION")
            .evolutionName("Offensive Enhancement")
            .evolutionCategory(EvolutionType.EvolutionCategory.OFFENSIVE)
            .evolutionBonuses(Map.of("damage", 25, "accuracy", 20, "critical_chance", 15))
            .newAbilities(Arrays.asList("ENHANCED_ATTACK", "CRITICAL_STRIKE", "OVERWHELMING_FORCE"))
            .evolutionCost(50)
            .isAvailable(true)
            .requirement("Combat experience")
            .build();
        evolutionTypes.put(offensiveEvolution.getEvolutionId(), offensiveEvolution);
        
        // Defensive Evolution
        EvolutionType defensiveEvolution = EvolutionType.builder()
            .evolutionId("DEFENSIVE_EVOLUTION")
            .evolutionName("Defensive Enhancement")
            .evolutionCategory(EvolutionType.EvolutionCategory.DEFENSIVE)
            .evolutionBonuses(Map.of("health", 30, "armor", 25, "resistance", 20))
            .newAbilities(Arrays.asList("ENHANCED_DEFENSE", "DAMAGE_ABSORPTION", "REGENERATION"))
            .evolutionCost(50)
            .isAvailable(true)
            .requirement("Survival experience")
            .build();
        evolutionTypes.put(defensiveEvolution.getEvolutionId(), defensiveEvolution);
        
        // Tactical Evolution
        EvolutionType tacticalEvolution = EvolutionType.builder()
            .evolutionId("TACTICAL_EVOLUTION")
            .evolutionName("Tactical Enhancement")
            .evolutionCategory(EvolutionType.EvolutionCategory.TACTICAL)
            .evolutionBonuses(Map.of("movement", 20, "initiative", 25, "coordination", 15))
            .newAbilities(Arrays.asList("TACTICAL_SUPERIORITY", "COORDINATED_ATTACK", "FLANKING_BONUS"))
            .evolutionCost(50)
            .isAvailable(true)
            .requirement("Tactical experience")
            .build();
        evolutionTypes.put(tacticalEvolution.getEvolutionId(), tacticalEvolution);
        
        // Adaptive Evolution
        EvolutionType adaptiveEvolution = EvolutionType.builder()
            .evolutionId("ADAPTIVE_EVOLUTION")
            .evolutionName("Adaptive Enhancement")
            .evolutionCategory(EvolutionType.EvolutionCategory.ADAPTIVE)
            .evolutionBonuses(Map.of("adaptation", 30, "learning", 25, "counter_ability", 20))
            .newAbilities(Arrays.asList("RAPID_ADAPTATION", "LEARNING_BONUS", "COUNTER_MEASURE"))
            .evolutionCost(60)
            .isAvailable(true)
            .requirement("Adaptation experience")
            .build();
        evolutionTypes.put(adaptiveEvolution.getEvolutionId(), adaptiveEvolution);
        
        // Specialized Evolution
        EvolutionType specializedEvolution = EvolutionType.builder()
            .evolutionId("SPECIALIZED_EVOLUTION")
            .evolutionName("Specialized Enhancement")
            .evolutionCategory(EvolutionType.EvolutionCategory.SPECIALIZED)
            .evolutionBonuses(Map.of("specialization", 35, "unique_ability", 30, "mastery", 25))
            .newAbilities(Arrays.asList("SPECIALIZATION_MASTERY", "UNIQUE_ABILITY", "EXPERT_SKILL"))
            .evolutionCost(70)
            .isAvailable(true)
            .requirement("Specialization experience")
            .build();
        evolutionTypes.put(specializedEvolution.getEvolutionId(), specializedEvolution);
        
        // Hybrid Evolution
        EvolutionType hybridEvolution = EvolutionType.builder()
            .evolutionId("HYBRID_EVOLUTION")
            .evolutionName("Hybrid Enhancement")
            .evolutionCategory(EvolutionType.EvolutionCategory.HYBRID)
            .evolutionBonuses(Map.of("hybrid_power", 40, "versatility", 30, "synergy", 25))
            .newAbilities(Arrays.asList("HYBRID_POWER", "VERSATILITY_BONUS", "SYNERGY_EFFECT"))
            .evolutionCost(80)
            .isAvailable(true)
            .requirement("Multiple evolution paths")
            .build();
        evolutionTypes.put(hybridEvolution.getEvolutionId(), hybridEvolution);
        
        // Counter Evolution
        EvolutionType counterEvolution = EvolutionType.builder()
            .evolutionId("COUNTER_EVOLUTION")
            .evolutionName("Counter Enhancement")
            .evolutionCategory(EvolutionType.EvolutionCategory.COUNTER)
            .evolutionBonuses(Map.of("counter_ability", 35, "resistance", 30, "immunity", 20))
            .newAbilities(Arrays.asList("COUNTER_MEASURE", "RESISTANCE_BONUS", "IMMUNITY_DEVELOPMENT"))
            .evolutionCost(75)
            .isAvailable(true)
            .requirement("Counter experience")
            .build();
        evolutionTypes.put(counterEvolution.getEvolutionId(), counterEvolution);
        
        // Escalation Evolution
        EvolutionType escalationEvolution = EvolutionType.builder()
            .evolutionId("ESCALATION_EVOLUTION")
            .evolutionName("Escalation Enhancement")
            .evolutionCategory(EvolutionType.EvolutionCategory.ESCALATION)
            .evolutionBonuses(Map.of("escalation_power", 45, "threat_level", 40, "dominance", 35))
            .newAbilities(Arrays.asList("ESCALATION_POWER", "THREAT_LEVEL", "DOMINANCE_AURA"))
            .evolutionCost(100)
            .isAvailable(true)
            .requirement("Maximum evolution level")
            .build();
        evolutionTypes.put(escalationEvolution.getEvolutionId(), escalationEvolution);
    }
    
    /**
     * Initialize evolution counters
     */
    private void initializeEvolutionCounters() {
        // Psionic Counter
        EvolutionCounter psionicCounter = EvolutionCounter.builder()
            .counterId("PSIONIC_COUNTER")
            .counterName("Psionic Counter")
            .counterType(EvolutionCounter.CounterType.PSIONIC_COUNTER)
            .counterEffects(Map.of("psionic_resistance", 30, "mind_shield", 25))
            .counterAbilities(Arrays.asList("PSIONIC_RESISTANCE", "MIND_SHIELD", "PSIONIC_IMMUNITY"))
            .isActive(false)
            .counterCost(40)
            .build();
        evolutionCounters.put(psionicCounter.getCounterId(), psionicCounter);
        
        // Technology Counter
        EvolutionCounter technologyCounter = EvolutionCounter.builder()
            .counterId("TECHNOLOGY_COUNTER")
            .counterName("Technology Counter")
            .counterType(EvolutionCounter.CounterType.TECHNOLOGY_COUNTER)
            .counterEffects(Map.of("hacking_resistance", 35, "system_immunity", 30))
            .counterAbilities(Arrays.asList("HACKING_RESISTANCE", "SYSTEM_IMMUNITY", "TECHNOLOGY_IMMUNITY"))
            .isActive(false)
            .counterCost(45)
            .build();
        evolutionCounters.put(technologyCounter.getCounterId(), technologyCounter);
        
        // Environmental Counter
        EvolutionCounter environmentalCounter = EvolutionCounter.builder()
            .counterId("ENVIRONMENTAL_COUNTER")
            .counterName("Environmental Counter")
            .counterType(EvolutionCounter.CounterType.ENVIRONMENTAL_COUNTER)
            .counterEffects(Map.of("environmental_resistance", 25, "hazard_immunity", 20))
            .counterAbilities(Arrays.asList("ENVIRONMENTAL_RESISTANCE", "HAZARD_IMMUNITY", "CLIMATE_ADAPTATION"))
            .isActive(false)
            .counterCost(35)
            .build();
        evolutionCounters.put(environmentalCounter.getCounterId(), environmentalCounter);
        
        // Combat Counter
        EvolutionCounter combatCounter = EvolutionCounter.builder()
            .counterId("COMBAT_COUNTER")
            .counterName("Combat Counter")
            .counterType(EvolutionCounter.CounterType.COMBAT_COUNTER)
            .counterEffects(Map.of("combat_resistance", 30, "damage_reduction", 25))
            .counterAbilities(Arrays.asList("COMBAT_RESISTANCE", "DAMAGE_REDUCTION", "COUNTER_ATTACK"))
            .isActive(false)
            .counterCost(40)
            .build();
        evolutionCounters.put(combatCounter.getCounterId(), combatCounter);
        
        // Strategic Counter
        EvolutionCounter strategicCounter = EvolutionCounter.builder()
            .counterId("STRATEGIC_COUNTER")
            .counterName("Strategic Counter")
            .counterType(EvolutionCounter.CounterType.STRATEGIC_COUNTER)
            .counterEffects(Map.of("strategic_resistance", 35, "planning_immunity", 30))
            .counterAbilities(Arrays.asList("STRATEGIC_RESISTANCE", "PLANNING_IMMUNITY", "COUNTER_STRATEGY"))
            .isActive(false)
            .counterCost(50)
            .build();
        evolutionCounters.put(strategicCounter.getCounterId(), strategicCounter);
        
        // Tactical Counter
        EvolutionCounter tacticalCounter = EvolutionCounter.builder()
            .counterId("TACTICAL_COUNTER")
            .counterName("Tactical Counter")
            .counterType(EvolutionCounter.CounterType.TACTICAL_COUNTER)
            .counterEffects(Map.of("tactical_resistance", 25, "maneuver_immunity", 20))
            .counterAbilities(Arrays.asList("TACTICAL_RESISTANCE", "MANEUVER_IMMUNITY", "COUNTER_TACTIC"))
            .isActive(false)
            .counterCost(35)
            .build();
        evolutionCounters.put(tacticalCounter.getCounterId(), tacticalCounter);
        
        // Psionic Warfare Counter
        EvolutionCounter psionicWarfareCounter = EvolutionCounter.builder()
            .counterId("PSIONIC_WARFARE_COUNTER")
            .counterName("Psionic Warfare Counter")
            .counterType(EvolutionCounter.CounterType.PSIONIC_WARFARE_COUNTER)
            .counterEffects(Map.of("psionic_warfare_resistance", 40, "psi_immunity", 35))
            .counterAbilities(Arrays.asList("PSIONIC_WARFARE_RESISTANCE", "PSI_IMMUNITY", "PSIONIC_DOMINANCE"))
            .isActive(false)
            .counterCost(60)
            .build();
        evolutionCounters.put(psionicWarfareCounter.getCounterId(), psionicWarfareCounter);
        
        // Technology Warfare Counter
        EvolutionCounter technologyWarfareCounter = EvolutionCounter.builder()
            .counterId("TECHNOLOGY_WARFARE_COUNTER")
            .counterName("Technology Warfare Counter")
            .counterType(EvolutionCounter.CounterType.TECHNOLOGY_WARFARE_COUNTER)
            .counterEffects(Map.of("technology_warfare_resistance", 45, "tech_immunity", 40))
            .counterAbilities(Arrays.asList("TECHNOLOGY_WARFARE_RESISTANCE", "TECH_IMMUNITY", "TECHNOLOGY_DOMINANCE"))
            .isActive(false)
            .counterCost(70)
            .build();
        evolutionCounters.put(technologyWarfareCounter.getCounterId(), technologyWarfareCounter);
    }
    
    /**
     * Initialize evolution consequences
     */
    private void initializeEvolutionConsequences() {
        // Power Increase Consequence
        EvolutionConsequence powerIncrease = EvolutionConsequence.builder()
            .consequenceId("POWER_INCREASE_CONSEQUENCE")
            .consequenceName("Power Increase")
            .consequenceType(EvolutionConsequence.ConsequenceType.POWER_INCREASE)
            .consequenceEffects(Map.of("power_bonus", 30, "threat_level", 25))
            .consequenceAbilities(Arrays.asList("POWER_BONUS", "THREAT_LEVEL", "DOMINANCE_AURA"))
            .isActive(false)
            .consequenceCost(0)
            .build();
        evolutionConsequences.put(powerIncrease.getConsequenceId(), powerIncrease);
        
        // Threat Escalation Consequence
        EvolutionConsequence threatEscalation = EvolutionConsequence.builder()
            .consequenceId("THREAT_ESCALATION_CONSEQUENCE")
            .consequenceName("Threat Escalation")
            .consequenceType(EvolutionConsequence.ConsequenceType.THREAT_ESCALATION)
            .consequenceEffects(Map.of("threat_bonus", 35, "escalation_power", 30))
            .consequenceAbilities(Arrays.asList("THREAT_BONUS", "ESCALATION_POWER", "THREAT_AURA"))
            .isActive(false)
            .consequenceCost(0)
            .build();
        evolutionConsequences.put(threatEscalation.getConsequenceId(), threatEscalation);
        
        // Adaptation Consequence
        EvolutionConsequence adaptation = EvolutionConsequence.builder()
            .consequenceId("ADAPTATION_CONSEQUENCE")
            .consequenceName("Adaptation")
            .consequenceType(EvolutionConsequence.ConsequenceType.ADAPTATION)
            .consequenceEffects(Map.of("adaptation_bonus", 25, "learning_rate", 20))
            .consequenceAbilities(Arrays.asList("ADAPTATION_BONUS", "LEARNING_RATE", "RAPID_ADAPTATION"))
            .isActive(false)
            .consequenceCost(0)
            .build();
        evolutionConsequences.put(adaptation.getConsequenceId(), adaptation);
        
        // Mutation Consequence
        EvolutionConsequence mutation = EvolutionConsequence.builder()
            .consequenceId("MUTATION_CONSEQUENCE")
            .consequenceName("Mutation")
            .consequenceType(EvolutionConsequence.ConsequenceType.MUTATION)
            .consequenceEffects(Map.of("mutation_bonus", 30, "unique_ability", 25))
            .consequenceAbilities(Arrays.asList("MUTATION_BONUS", "UNIQUE_ABILITY", "MUTATION_POWER"))
            .isActive(false)
            .consequenceCost(0)
            .build();
        evolutionConsequences.put(mutation.getConsequenceId(), mutation);
        
        // Resistance Development Consequence
        EvolutionConsequence resistanceDevelopment = EvolutionConsequence.builder()
            .consequenceId("RESISTANCE_DEVELOPMENT_CONSEQUENCE")
            .consequenceName("Resistance Development")
            .consequenceType(EvolutionConsequence.ConsequenceType.RESISTANCE_DEVELOPMENT)
            .consequenceEffects(Map.of("resistance_bonus", 35, "immunity_chance", 30))
            .consequenceAbilities(Arrays.asList("RESISTANCE_BONUS", "IMMUNITY_CHANCE", "RESISTANCE_AURA"))
            .isActive(false)
            .consequenceCost(0)
            .build();
        evolutionConsequences.put(resistanceDevelopment.getConsequenceId(), resistanceDevelopment);
        
        // Intelligence Increase Consequence
        EvolutionConsequence intelligenceIncrease = EvolutionConsequence.builder()
            .consequenceId("INTELLIGENCE_INCREASE_CONSEQUENCE")
            .consequenceName("Intelligence Increase")
            .consequenceType(EvolutionConsequence.ConsequenceType.INTELLIGENCE_INCREASE)
            .consequenceEffects(Map.of("intelligence_bonus", 40, "planning_bonus", 35))
            .consequenceAbilities(Arrays.asList("INTELLIGENCE_BONUS", "PLANNING_BONUS", "STRATEGIC_MASTERY"))
            .isActive(false)
            .consequenceCost(0)
            .build();
        evolutionConsequences.put(intelligenceIncrease.getConsequenceId(), intelligenceIncrease);
        
        // Coordination Enhancement Consequence
        EvolutionConsequence coordinationEnhancement = EvolutionConsequence.builder()
            .consequenceId("COORDINATION_ENHANCEMENT_CONSEQUENCE")
            .consequenceName("Coordination Enhancement")
            .consequenceType(EvolutionConsequence.ConsequenceType.COORDINATION_ENHANCEMENT)
            .consequenceEffects(Map.of("coordination_bonus", 30, "teamwork_bonus", 25))
            .consequenceAbilities(Arrays.asList("COORDINATION_BONUS", "TEAMWORK_BONUS", "COORDINATED_ATTACK"))
            .isActive(false)
            .consequenceCost(0)
            .build();
        evolutionConsequences.put(coordinationEnhancement.getConsequenceId(), coordinationEnhancement);
        
        // Ultimate Evolution Consequence
        EvolutionConsequence ultimateEvolution = EvolutionConsequence.builder()
            .consequenceId("ULTIMATE_EVOLUTION_CONSEQUENCE")
            .consequenceName("Ultimate Evolution")
            .consequenceType(EvolutionConsequence.ConsequenceType.ULTIMATE_EVOLUTION)
            .consequenceEffects(Map.of("ultimate_power", 50, "evolution_mastery", 45))
            .consequenceAbilities(Arrays.asList("ULTIMATE_POWER", "EVOLUTION_MASTERY", "EVOLUTION_DOMINANCE"))
            .isActive(false)
            .consequenceCost(0)
            .build();
        evolutionConsequences.put(ultimateEvolution.getConsequenceId(), ultimateEvolution);
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

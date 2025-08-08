package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;
import java.time.LocalDateTime;

/**
 * Advanced Strategic Layer Integration System
 * Handles the connection between tactical combat decisions and strategic layer consequences
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedStrategicLayerIntegrationSystem {
    
    // Strategic State Management
    private Map<String, StrategicState> strategicStates;
    private Map<String, StrategicResource> strategicResources;
    private Map<String, StrategicConsequence> strategicConsequences;
    private Map<String, StrategicDecision> strategicDecisions;
    
    // Tactical-Strategic Integration
    private Map<String, TacticalImpact> tacticalImpacts;
    private Map<String, StrategicFeedback> strategicFeedback;
    private Map<String, ResourceAllocation> resourceAllocations;
    private Map<String, IntelGathering> intelGathering;
    
    // Base Management
    private Map<String, BaseFacility> baseFacilities;
    private Map<String, BaseDefense> baseDefenses;
    private Map<String, BaseUpgrade> baseUpgrades;
    private Map<String, BaseStatus> baseStatuses;
    
    // Global Threat Management
    private Map<String, GlobalThreat> globalThreats;
    private Map<String, ThreatLevel> threatLevels;
    private Map<String, ThreatProgression> threatProgressions;
    private Map<String, Countermeasure> countermeasures;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicState {
        private String stateId;
        private String name;
        private StrategicPhase phase;
        private int globalThreatLevel;
        private int playerStrength;
        private int alienAdvancement;
        private LocalDateTime lastUpdate;
        private List<String> activeThreats;
        private List<String> availableResources;
    }
    
    public enum StrategicPhase {
        EARLY_GAME(1), MID_GAME(2), LATE_GAME(3), END_GAME(4);
        
        private final int phase;
        
        StrategicPhase(int phase) {
            this.phase = phase;
        }
        
        public int getPhase() { return phase; }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicResource {
        private String resourceId;
        private String name;
        private ResourceType type;
        private int currentAmount;
        private int maxAmount;
        private int regenerationRate;
        private LocalDateTime lastRegeneration;
        private List<String> sources;
    }
    
    public enum ResourceType {
        SUPPLIES, ALLOYS, ELERIUM, INTEL, SCIENTISTS, ENGINEERS, 
        SOLDIERS, PSYCHICS, MONEY, INFLUENCE
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicConsequence {
        private String consequenceId;
        private String source;
        private ConsequenceType type;
        private int magnitude;
        private LocalDateTime appliedDate;
        private boolean isPermanent;
        private List<String> affectedSystems;
    }
    
    public enum ConsequenceType {
        RESOURCE_GAIN, RESOURCE_LOSS, THREAT_INCREASE, THREAT_DECREASE,
        TECHNOLOGY_UNLOCK, FACILITY_DAMAGE, MORALE_CHANGE, INTEL_GAIN
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicDecision {
        private String decisionId;
        private String description;
        private DecisionType type;
        private LocalDateTime decisionDate;
        private boolean isImplemented;
        private List<String> consequences;
        private int cost;
    }
    
    public enum DecisionType {
        BUILD_FACILITY, RESEARCH_TECHNOLOGY, TRAIN_SOLDIERS, LAUNCH_MISSION,
        UPGRADE_BASE, ALLOCATE_RESOURCES, FORM_ALLIANCE, DECLARE_WAR
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TacticalImpact {
        private String impactId;
        private String missionId;
        private ImpactType type;
        private int magnitude;
        private LocalDateTime impactDate;
        private List<String> affectedStrategicSystems;
    }
    
    public enum ImpactType {
        MISSION_SUCCESS, MISSION_FAILURE, SOLDIER_LOSS, EQUIPMENT_LOSS,
        INTEL_GAIN, THREAT_REDUCTION, RESOURCE_ACQUISITION, MORALE_CHANGE
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicFeedback {
        private String feedbackId;
        private String source;
        private FeedbackType type;
        private String message;
        private LocalDateTime feedbackDate;
        private int priority;
    }
    
    public enum FeedbackType {
        WARNING, SUCCESS, FAILURE, OPPORTUNITY, THREAT, INFORMATION
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResourceAllocation {
        private String allocationId;
        private String resourceId;
        private String targetId;
        private int amount;
        private AllocationType type;
        private LocalDateTime allocationDate;
        private boolean isActive;
    }
    
    public enum AllocationType {
        RESEARCH, CONSTRUCTION, TRAINING, MAINTENANCE, COMBAT, DEFENSE
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntelGathering {
        private String intelId;
        private String source;
        private IntelType type;
        private int value;
        private LocalDateTime gatheredDate;
        private boolean isAnalyzed;
        private List<String> applications;
    }
    
    public enum IntelType {
        ALIEN_TECHNOLOGY, ALIEN_STRATEGY, THREAT_ASSESSMENT, RESOURCE_LOCATION,
        BASE_LOCATION, WEAKNESS_ANALYSIS, STRENGTH_ANALYSIS
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseFacility {
        private String facilityId;
        private String name;
        private FacilityType type;
        private int level;
        private int capacity;
        private int currentUsage;
        private LocalDateTime constructionDate;
        private boolean isOperational;
        private List<String> upgrades;
    }
    
    public enum FacilityType {
        COMMAND_CENTER, RESEARCH_LAB, ENGINEERING_WORKSHOP, MEDICAL_BAY,
        TRAINING_FACILITY, DEFENSE_TURRET, POWER_GENERATOR, STORAGE_DEPOT
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseDefense {
        private String defenseId;
        private String name;
        private DefenseType type;
        private int strength;
        private int range;
        private boolean isActive;
        private LocalDateTime lastMaintenance;
        private List<String> weaknesses;
    }
    
    public enum DefenseType {
        TURRET, SHIELD, MINE, SENSOR, BARRIER, TRAP
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseUpgrade {
        private String upgradeId;
        private String facilityId;
        private UpgradeType type;
        private int cost;
        private int duration;
        private LocalDateTime startDate;
        private LocalDateTime completionDate;
        private boolean isCompleted;
    }
    
    public enum UpgradeType {
        CAPACITY_INCREASE, EFFICIENCY_IMPROVEMENT, NEW_FEATURE, 
        DEFENSE_ENHANCEMENT, AUTOMATION
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseStatus {
        private String baseId;
        private String name;
        private BaseCondition condition;
        private int defenseRating;
        private int operationalCapacity;
        private LocalDateTime lastInspection;
        private List<String> activeFacilities;
    }
    
    public enum BaseCondition {
        EXCELLENT(1.0), GOOD(0.8), FAIR(0.6), POOR(0.4), CRITICAL(0.2);
        
        private final double efficiency;
        
        BaseCondition(double efficiency) {
            this.efficiency = efficiency;
        }
        
        public double getEfficiency() { return efficiency; }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GlobalThreat {
        private String threatId;
        private String name;
        private ThreatCategory category;
        private int level;
        private int progression;
        private LocalDateTime firstAppearance;
        private LocalDateTime lastUpdate;
        private List<String> countermeasures;
    }
    
    public enum ThreatCategory {
        ALIEN_INVASION, PSYCHIC_ATTACK, BIOLOGICAL_WARFARE, 
        TECHNOLOGICAL_SURGE, RESOURCE_SCARCITY, MORALE_CRISIS
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThreatLevel {
        private String threatId;
        private int currentLevel;
        private int maxLevel;
        private ThreatSeverity severity;
        private LocalDateTime lastAssessment;
        private List<String> indicators;
    }
    
    public enum ThreatSeverity {
        MINIMAL(1), LOW(2), MODERATE(3), HIGH(4), CRITICAL(5), EXTREME(6);
        
        private final int level;
        
        ThreatSeverity(int level) {
            this.level = level;
        }
        
        public int getLevel() { return level; }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThreatProgression {
        private String threatId;
        private int currentStage;
        private int totalStages;
        private ProgressionRate rate;
        private LocalDateTime nextStageDate;
        private List<String> stageEffects;
    }
    
    public enum ProgressionRate {
        SLOW(1), NORMAL(2), FAST(3), RAPID(4), EXPONENTIAL(5);
        
        private final int multiplier;
        
        ProgressionRate(int multiplier) {
            this.multiplier = multiplier;
        }
        
        public int getMultiplier() { return multiplier; }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Countermeasure {
        private String countermeasureId;
        private String threatId;
        private CountermeasureType type;
        private int effectiveness;
        private int cost;
        private LocalDateTime implementationDate;
        private boolean isActive;
    }
    
    public enum CountermeasureType {
        DEFENSIVE_MEASURE, OFFENSIVE_ACTION, TECHNOLOGICAL_SOLUTION,
        DIPLOMATIC_APPROACH, RESOURCE_ALLOCATION, STRATEGIC_RETREAT
    }
    
    // Core Strategic Management Methods
    
    public boolean initializeStrategicState(String stateId, String name) {
        if (stateId == null || name == null) {
            return false;
        }
        
        StrategicState state = StrategicState.builder()
            .stateId(stateId)
            .name(name)
            .phase(StrategicPhase.EARLY_GAME)
            .globalThreatLevel(1)
            .playerStrength(10)
            .alienAdvancement(1)
            .lastUpdate(LocalDateTime.now())
            .activeThreats(new ArrayList<>())
            .availableResources(new ArrayList<>())
            .build();
        
        strategicStates.put(stateId, state);
        
        return true;
    }
    
    public boolean updateStrategicState(String stateId) {
        StrategicState state = strategicStates.get(stateId);
        if (state == null) {
            return false;
        }
        
        // Update based on current conditions
        updateStrategicThreatLevel(stateId);
        updatePlayerStrength(stateId);
        updateAlienAdvancement(stateId);
        
        // Check for phase transitions
        checkPhaseTransition(stateId);
        
        state.setLastUpdate(LocalDateTime.now());
        
        return true;
    }
    
    public boolean addStrategicResource(String resourceId, String name, ResourceType type, 
                                     int maxAmount, int regenerationRate) {
        if (resourceId == null || name == null || type == null) {
            return false;
        }
        
        StrategicResource resource = StrategicResource.builder()
            .resourceId(resourceId)
            .name(name)
            .type(type)
            .currentAmount(maxAmount)
            .maxAmount(maxAmount)
            .regenerationRate(regenerationRate)
            .lastRegeneration(LocalDateTime.now())
            .sources(new ArrayList<>())
            .build();
        
        strategicResources.put(resourceId, resource);
        
        return true;
    }
    
    public boolean allocateResource(String resourceId, String targetId, int amount, 
                                  AllocationType type) {
        StrategicResource resource = strategicResources.get(resourceId);
        if (resource == null || resource.getCurrentAmount() < amount) {
            return false;
        }
        
        // Deduct from resource
        resource.setCurrentAmount(resource.getCurrentAmount() - amount);
        
        // Create allocation record
        ResourceAllocation allocation = ResourceAllocation.builder()
            .allocationId(UUID.randomUUID().toString())
            .resourceId(resourceId)
            .targetId(targetId)
            .amount(amount)
            .type(type)
            .allocationDate(LocalDateTime.now())
            .isActive(true)
            .build();
        
        resourceAllocations.put(allocation.getAllocationId(), allocation);
        
        return true;
    }
    
    public boolean processTacticalImpact(String missionId, ImpactType impactType, 
                                       int magnitude, List<String> affectedSystems) {
        if (missionId == null || impactType == null) {
            return false;
        }
        
        TacticalImpact impact = TacticalImpact.builder()
            .impactId(UUID.randomUUID().toString())
            .missionId(missionId)
            .type(impactType)
            .magnitude(magnitude)
            .impactDate(LocalDateTime.now())
            .affectedStrategicSystems(affectedSystems)
            .build();
        
        tacticalImpacts.put(impact.getImpactId(), impact);
        
        // Apply impact to strategic systems
        applyTacticalImpact(impact);
        
        return true;
    }
    
    public boolean addStrategicConsequence(String source, ConsequenceType type, 
                                        int magnitude, List<String> affectedSystems) {
        if (source == null || type == null) {
            return false;
        }
        
        StrategicConsequence consequence = StrategicConsequence.builder()
            .consequenceId(UUID.randomUUID().toString())
            .source(source)
            .type(type)
            .magnitude(magnitude)
            .appliedDate(LocalDateTime.now())
            .isPermanent(false)
            .affectedSystems(affectedSystems)
            .build();
        
        strategicConsequences.put(consequence.getConsequenceId(), consequence);
        
        // Apply consequence effects
        applyStrategicConsequence(consequence);
        
        return true;
    }
    
    public boolean makeStrategicDecision(String description, DecisionType type, 
                                       int cost, List<String> consequences) {
        if (description == null || type == null) {
            return false;
        }
        
        StrategicDecision decision = StrategicDecision.builder()
            .decisionId(UUID.randomUUID().toString())
            .description(description)
            .type(type)
            .decisionDate(LocalDateTime.now())
            .isImplemented(false)
            .consequences(consequences)
            .cost(cost)
            .build();
        
        strategicDecisions.put(decision.getDecisionId(), decision);
        
        return true;
    }
    
    public boolean implementStrategicDecision(String decisionId) {
        StrategicDecision decision = strategicDecisions.get(decisionId);
        if (decision == null || decision.isImplemented()) {
            return false;
        }
        
        decision.setImplemented(true);
        
        // Apply decision consequences
        applyStrategicDecision(decision);
        
        return true;
    }
    
    public boolean addBaseFacility(String facilityId, String name, FacilityType type, 
                                 int level, int capacity) {
        if (facilityId == null || name == null || type == null) {
            return false;
        }
        
        BaseFacility facility = BaseFacility.builder()
            .facilityId(facilityId)
            .name(name)
            .type(type)
            .level(level)
            .capacity(capacity)
            .currentUsage(0)
            .constructionDate(LocalDateTime.now())
            .isOperational(true)
            .upgrades(new ArrayList<>())
            .build();
        
        baseFacilities.put(facilityId, facility);
        
        return true;
    }
    
    public boolean upgradeBaseFacility(String facilityId, UpgradeType upgradeType, 
                                     int cost, int duration) {
        BaseFacility facility = baseFacilities.get(facilityId);
        if (facility == null) {
            return false;
        }
        
        BaseUpgrade upgrade = BaseUpgrade.builder()
            .upgradeId(UUID.randomUUID().toString())
            .facilityId(facilityId)
            .type(upgradeType)
            .cost(cost)
            .duration(duration)
            .startDate(LocalDateTime.now())
            .completionDate(LocalDateTime.now().plusDays(duration))
            .isCompleted(false)
            .build();
        
        baseUpgrades.put(upgrade.getUpgradeId(), upgrade);
        
        return true;
    }
    
    public boolean addBaseDefense(String defenseId, String name, DefenseType type, 
                                int strength, int range) {
        if (defenseId == null || name == null || type == null) {
            return false;
        }
        
        BaseDefense defense = BaseDefense.builder()
            .defenseId(defenseId)
            .name(name)
            .type(type)
            .strength(strength)
            .range(range)
            .isActive(true)
            .lastMaintenance(LocalDateTime.now())
            .weaknesses(new ArrayList<>())
            .build();
        
        baseDefenses.put(defenseId, defense);
        
        return true;
    }
    
    public boolean createGlobalThreat(String threatId, String name, ThreatCategory category, 
                                    int level) {
        if (threatId == null || name == null || category == null) {
            return false;
        }
        
        GlobalThreat threat = GlobalThreat.builder()
            .threatId(threatId)
            .name(name)
            .category(category)
            .level(level)
            .progression(0)
            .firstAppearance(LocalDateTime.now())
            .lastUpdate(LocalDateTime.now())
            .countermeasures(new ArrayList<>())
            .build();
        
        globalThreats.put(threatId, threat);
        
        // Initialize threat level tracking
        ThreatLevel threatLevel = ThreatLevel.builder()
            .threatId(threatId)
            .currentLevel(level)
            .maxLevel(10)
            .severity(ThreatSeverity.MINIMAL)
            .lastAssessment(LocalDateTime.now())
            .indicators(new ArrayList<>())
            .build();
        
        threatLevels.put(threatId, threatLevel);
        
        return true;
    }
    
    public boolean progressGlobalThreat(String threatId, int progressionAmount) {
        GlobalThreat threat = globalThreats.get(threatId);
        if (threat == null) {
            return false;
        }
        
        threat.setProgression(threat.getProgression() + progressionAmount);
        threat.setLastUpdate(LocalDateTime.now());
        
        // Update threat level
        updateThreatLevel(threatId);
        
        // Check for threat escalation
        checkThreatEscalation(threatId);
        
        return true;
    }
    
    public boolean implementCountermeasure(String threatId, CountermeasureType type, 
                                        int effectiveness, int cost) {
        if (threatId == null || type == null) {
            return false;
        }
        
        Countermeasure countermeasure = Countermeasure.builder()
            .countermeasureId(UUID.randomUUID().toString())
            .threatId(threatId)
            .type(type)
            .effectiveness(effectiveness)
            .cost(cost)
            .implementationDate(LocalDateTime.now())
            .isActive(true)
            .build();
        
        countermeasures.put(countermeasure.getCountermeasureId(), countermeasure);
        
        // Apply countermeasure effects
        applyCountermeasure(countermeasure);
        
        return true;
    }
    
    public boolean gatherIntel(String source, IntelType type, int value, 
                             List<String> applications) {
        if (source == null || type == null) {
            return false;
        }
        
        IntelGathering intel = IntelGathering.builder()
            .intelId(UUID.randomUUID().toString())
            .source(source)
            .type(type)
            .value(value)
            .gatheredDate(LocalDateTime.now())
            .isAnalyzed(false)
            .applications(applications)
            .build();
        
        intelGathering.put(intel.getIntelId(), intel);
        
        return true;
    }
    
    public boolean analyzeIntel(String intelId) {
        IntelGathering intel = intelGathering.get(intelId);
        if (intel == null) {
            return false;
        }
        
        intel.setAnalyzed(true);
        
        // Apply intel effects
        applyIntelAnalysis(intel);
        
        return true;
    }
    
    public boolean addStrategicFeedback(String source, FeedbackType type, 
                                     String message, int priority) {
        if (source == null || type == null || message == null) {
            return false;
        }
        
        StrategicFeedback feedback = StrategicFeedback.builder()
            .feedbackId(UUID.randomUUID().toString())
            .source(source)
            .type(type)
            .message(message)
            .feedbackDate(LocalDateTime.now())
            .priority(priority)
            .build();
        
        strategicFeedback.put(feedback.getFeedbackId(), feedback);
        
        return true;
    }
    
    public int getResourceAmount(String resourceId) {
        StrategicResource resource = strategicResources.get(resourceId);
        return resource != null ? resource.getCurrentAmount() : 0;
    }
    
    public int getGlobalThreatLevel(String stateId) {
        StrategicState state = strategicStates.get(stateId);
        return state != null ? state.getGlobalThreatLevel() : 0;
    }
    
    public int getPlayerStrength(String stateId) {
        StrategicState state = strategicStates.get(stateId);
        return state != null ? state.getPlayerStrength() : 0;
    }
    
    public StrategicPhase getStrategicPhase(String stateId) {
        StrategicState state = strategicStates.get(stateId);
        return state != null ? state.getPhase() : StrategicPhase.EARLY_GAME;
    }
    
    // Helper Methods
    
    private void updateStrategicThreatLevel(String stateId) {
        StrategicState state = strategicStates.get(stateId);
        if (state == null) {
            return;
        }
        
        // Calculate threat level based on active threats
        int threatLevel = calculateThreatLevel(state.getActiveThreats());
        state.setGlobalThreatLevel(threatLevel);
    }
    
    private void updatePlayerStrength(String stateId) {
        StrategicState state = strategicStates.get(stateId);
        if (state == null) {
            return;
        }
        
        // Calculate player strength based on resources and facilities
        int strength = calculatePlayerStrength(state.getAvailableResources());
        state.setPlayerStrength(strength);
    }
    
    private void updateAlienAdvancement(String stateId) {
        StrategicState state = strategicStates.get(stateId);
        if (state == null) {
            return;
        }
        
        // Calculate alien advancement based on global threats
        int advancement = calculateAlienAdvancement(state.getGlobalThreatLevel());
        state.setAlienAdvancement(advancement);
    }
    
    private void checkPhaseTransition(String stateId) {
        StrategicState state = strategicStates.get(stateId);
        if (state == null) {
            return;
        }
        
        // Check if phase should change based on game progress
        StrategicPhase newPhase = determineStrategicPhase(state);
        if (newPhase != state.getPhase()) {
            state.setPhase(newPhase);
        }
    }
    
    private void applyTacticalImpact(TacticalImpact impact) {
        // Apply tactical impact to strategic systems
        switch (impact.getType()) {
            case MISSION_SUCCESS:
                // Increase player strength, reduce threats
                break;
            case MISSION_FAILURE:
                // Decrease player strength, increase threats
                break;
            case SOLDIER_LOSS:
                // Reduce player strength
                break;
            case EQUIPMENT_LOSS:
                // Reduce available resources
                break;
            case INTEL_GAIN:
                // Increase intel resources
                break;
            case THREAT_REDUCTION:
                // Decrease global threat level
                break;
            case RESOURCE_ACQUISITION:
                // Increase available resources
                break;
            case MORALE_CHANGE:
                // Affect player strength
                break;
        }
    }
    
    private void applyStrategicConsequence(StrategicConsequence consequence) {
        // Apply strategic consequence effects
        switch (consequence.getType()) {
            case RESOURCE_GAIN:
                // Increase resource amounts
                break;
            case RESOURCE_LOSS:
                // Decrease resource amounts
                break;
            case THREAT_INCREASE:
                // Increase global threat level
                break;
            case THREAT_DECREASE:
                // Decrease global threat level
                break;
            case TECHNOLOGY_UNLOCK:
                // Unlock new technologies
                break;
            case FACILITY_DAMAGE:
                // Damage base facilities
                break;
            case MORALE_CHANGE:
                // Affect player morale
                break;
            case INTEL_GAIN:
                // Increase intel resources
                break;
        }
    }
    
    private void applyStrategicDecision(StrategicDecision decision) {
        // Apply strategic decision effects
        switch (decision.getType()) {
            case BUILD_FACILITY:
                // Add new base facility
                break;
            case RESEARCH_TECHNOLOGY:
                // Start research project
                break;
            case TRAIN_SOLDIERS:
                // Increase soldier capacity
                break;
            case LAUNCH_MISSION:
                // Create new mission
                break;
            case UPGRADE_BASE:
                // Upgrade base facilities
                break;
            case ALLOCATE_RESOURCES:
                // Reallocate resources
                break;
            case FORM_ALLIANCE:
                // Create alliance benefits
                break;
            case DECLARE_WAR:
                // Increase threat level
                break;
        }
    }
    
    private void updateThreatLevel(String threatId) {
        ThreatLevel threatLevel = threatLevels.get(threatId);
        if (threatLevel == null) {
            return;
        }
        
        // Update threat level based on progression
        int newLevel = calculateThreatLevel(threatId);
        threatLevel.setCurrentLevel(newLevel);
        
        // Update severity
        ThreatSeverity severity = determineThreatSeverity(newLevel);
        threatLevel.setSeverity(severity);
        
        threatLevel.setLastAssessment(LocalDateTime.now());
    }
    
    private void checkThreatEscalation(String threatId) {
        GlobalThreat threat = globalThreats.get(threatId);
        ThreatLevel threatLevel = threatLevels.get(threatId);
        
        if (threat == null || threatLevel == null) {
            return;
        }
        
        // Check if threat should escalate
        if (threat.getProgression() >= 50 && threatLevel.getCurrentLevel() < threatLevel.getMaxLevel()) {
            escalateThreat(threatId);
        }
    }
    
    private void escalateThreat(String threatId) {
        ThreatLevel threatLevel = threatLevels.get(threatId);
        if (threatLevel == null) {
            return;
        }
        
        // Increase threat level
        threatLevel.setCurrentLevel(threatLevel.getCurrentLevel() + 1);
        
        // Update severity
        ThreatSeverity severity = determineThreatSeverity(threatLevel.getCurrentLevel());
        threatLevel.setSeverity(severity);
    }
    
    private void applyCountermeasure(Countermeasure countermeasure) {
        // Apply countermeasure effects to reduce threat
        GlobalThreat threat = globalThreats.get(countermeasure.getThreatId());
        if (threat != null) {
            // Reduce threat progression
            int reduction = countermeasure.getEffectiveness() / 10;
            threat.setProgression(Math.max(0, threat.getProgression() - reduction));
        }
    }
    
    private void applyIntelAnalysis(IntelGathering intel) {
        // Apply intel analysis effects
        switch (intel.getType()) {
            case ALIEN_TECHNOLOGY:
                // Unlock technology research
                break;
            case ALIEN_STRATEGY:
                // Improve countermeasures
                break;
            case THREAT_ASSESSMENT:
                // Update threat levels
                break;
            case RESOURCE_LOCATION:
                // Reveal resource locations
                break;
            case BASE_LOCATION:
                // Reveal alien base locations
                break;
            case WEAKNESS_ANALYSIS:
                // Improve effectiveness against threats
                break;
            case STRENGTH_ANALYSIS:
                // Understand threat capabilities
                break;
        }
    }
    
    private int calculateThreatLevel(List<String> activeThreats) {
        // Calculate threat level based on active threats
        return activeThreats.size() * 2;
    }
    
    private int calculatePlayerStrength(List<String> availableResources) {
        // Calculate player strength based on available resources
        return availableResources.size() * 5;
    }
    
    private int calculateAlienAdvancement(int globalThreatLevel) {
        // Calculate alien advancement based on global threat level
        return globalThreatLevel / 2;
    }
    
    private StrategicPhase determineStrategicPhase(StrategicState state) {
        // Determine strategic phase based on game progress
        if (state.getGlobalThreatLevel() >= 8) {
            return StrategicPhase.END_GAME;
        } else if (state.getGlobalThreatLevel() >= 6) {
            return StrategicPhase.LATE_GAME;
        } else if (state.getGlobalThreatLevel() >= 4) {
            return StrategicPhase.MID_GAME;
        } else {
            return StrategicPhase.EARLY_GAME;
        }
    }
    
    private int calculateThreatLevel(String threatId) {
        GlobalThreat threat = globalThreats.get(threatId);
        if (threat == null) {
            return 1;
        }
        
        // Calculate threat level based on progression
        return Math.min(10, threat.getProgression() / 10 + 1);
    }
    
    private ThreatSeverity determineThreatSeverity(int threatLevel) {
        if (threatLevel >= 9) {
            return ThreatSeverity.EXTREME;
        } else if (threatLevel >= 7) {
            return ThreatSeverity.CRITICAL;
        } else if (threatLevel >= 5) {
            return ThreatSeverity.HIGH;
        } else if (threatLevel >= 3) {
            return ThreatSeverity.MODERATE;
        } else if (threatLevel >= 2) {
            return ThreatSeverity.LOW;
        } else {
            return ThreatSeverity.MINIMAL;
        }
    }
    
    // Getters for system state
    public StrategicState getStrategicState(String stateId) {
        return strategicStates.get(stateId);
    }
    
    public StrategicResource getStrategicResource(String resourceId) {
        return strategicResources.get(resourceId);
    }
    
    public BaseFacility getBaseFacility(String facilityId) {
        return baseFacilities.get(facilityId);
    }
    
    public GlobalThreat getGlobalThreat(String threatId) {
        return globalThreats.get(threatId);
    }
    
    public ThreatLevel getThreatLevel(String threatId) {
        return threatLevels.get(threatId);
    }
    
    public List<StrategicFeedback> getStrategicFeedback() {
        return new ArrayList<>(strategicFeedback.values());
    }
    
    public List<IntelGathering> getIntelGathering() {
        return new ArrayList<>(intelGathering.values());
    }
    
    public boolean isResourceAvailable(String resourceId, int amount) {
        StrategicResource resource = strategicResources.get(resourceId);
        return resource != null && resource.getCurrentAmount() >= amount;
    }
    
    public boolean isThreatActive(String threatId) {
        GlobalThreat threat = globalThreats.get(threatId);
        return threat != null && threat.getLevel() > 0;
    }
}

package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Advanced Strategic Layer Integration System - XCOM 2 Tactical Combat
 * Implements comprehensive strategic layer with mission planning, research, and resource management
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedStrategicLayerIntegrationSystem {
    
    private String strategicLayerId;
    private Map<String, StrategicMission> strategicMissions;
    private Map<String, ResearchProject> researchProjects;
    private Map<String, ManufacturingFacility> manufacturingFacilities;
    private Map<String, IntelReport> intelReports;
    private Map<String, StrategicResource> strategicResources;
    private Map<String, StrategicDecision> strategicDecisions;
    private Map<String, StrategicConsequence> strategicConsequences;
    private Map<String, List<String>> missionHistory;
    private Map<String, Map<String, Integer>> resourceAllocation;
    private Map<String, List<String>> activeProjects;
    private Map<String, Integer> projectProgress;
    private Map<String, Boolean> projectStates;
    private int totalMissions;
    private int maxResearchCapacity;
    private boolean isStrategicActive;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicMission {
        private String missionId;
        private String missionName;
        private MissionType missionType;
        private MissionPriority priority;
        private int missionDuration;
        private int currentDuration;
        private List<String> missionObjectives;
        private Map<String, Integer> missionRewards;
        private List<String> missionRequirements;
        private boolean isActive;
        private String assignedSquad;
        private String missionLocation;
        private String missionStatus;
        private int missionDifficulty;
        private String missionDescription;
        private Map<String, Integer> missionBonuses;
        private List<String> missionAbilities;
        private String missionMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum MissionType {
            RECONNAISSANCE,    // Intelligence gathering
            ASSASSINATION,     // Target elimination
            SABOTAGE,          // Infrastructure destruction
            RESCUE,            // Hostage rescue
            DEFENSE,           // Base defense
            INFILTRATION,      // Stealth operations
            ESCORT,            // VIP protection
            EXTRACTION,        // Asset recovery
            HACKING,           // Digital operations
            COMBAT,            // Direct engagement
            RESEARCH,          // Scientific missions
            MANUFACTURING,     // Production missions
            DIPLOMATIC,        // Political missions
            ESPIONAGE,         // Spy operations
            LOGISTICS          // Supply missions
        }
        
        public enum MissionPriority {
            CRITICAL,      // Immediate attention required
            HIGH,          // High priority
            MEDIUM,        // Standard priority
            LOW,           // Low priority
            OPTIONAL       // Optional missions
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResearchProject {
        private String projectId;
        private String projectName;
        private ResearchCategory researchCategory;
        private int researchDuration;
        private int currentDuration;
        private List<String> researchObjectives;
        private Map<String, Integer> researchRewards;
        private List<String> researchRequirements;
        private boolean isActive;
        private String assignedScientist;
        private String researchFacility;
        private String researchStatus;
        private int researchDifficulty;
        private String researchDescription;
        private Map<String, Integer> researchBonuses;
        private List<String> researchAbilities;
        private String researchMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum ResearchCategory {
            WEAPON_TECHNOLOGY,    // Weapon research
            DEFENSE_TECHNOLOGY,   // Armor research
            PSYCHIC_TECHNOLOGY,   // Psionic research
            ALIEN_TECHNOLOGY,     // Alien tech research
            MEDICAL_TECHNOLOGY,   // Medical research
            COMMUNICATION_TECHNOLOGY, // Communication research
            TRANSPORT_TECHNOLOGY, // Transport research
            SENSOR_TECHNOLOGY,    // Sensor research
            POWER_TECHNOLOGY,     // Power research
            COMPUTER_TECHNOLOGY,  // Computer research
            TOOL_TECHNOLOGY,      // Tool research
            AMMUNITION_TECHNOLOGY, // Ammunition research
            GRENADE_TECHNOLOGY,   // Explosive research
            MEDICAL_TECHNOLOGY_2, // Advanced medical research
            HACKING_TECHNOLOGY    // Technical research
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ManufacturingFacility {
        private String facilityId;
        private String facilityName;
        private FacilityType facilityType;
        private int capacity;
        private int currentOccupancy;
        private List<String> availableWorkers;
        private List<String> availableEquipment;
        private Map<String, Integer> facilityBonuses;
        private List<String> facilityServices;
        private boolean isOperational;
        private int facilityLevel;
        private int facilityQuality;
        private String location;
        private int operatingCost;
        private int maintenanceCost;
        private String facilityStatus;
        private List<String> products;
        private Map<String, Integer> productionSuccessRates;
        private List<String> specializations;
        private String facilityDescription;
        
        public enum FacilityType {
            WEAPON_FACTORY,       // Weapon production
            ARMOR_FACTORY,        // Armor production
            MEDICAL_FACTORY,      // Medical production
            TECHNICAL_FACTORY,    // Technical production
            COMMUNICATION_FACTORY, // Communication production
            TRANSPORT_FACTORY,    // Transport production
            SENSOR_FACTORY,       // Sensor production
            POWER_FACTORY,        // Power production
            COMPUTER_FACTORY,     // Computer production
            TOOL_FACTORY,         // Tool production
            AMMUNITION_FACTORY,   // Ammunition production
            GRENADE_FACTORY,      // Explosive production
            MEDICAL_FACTORY_2,    // Advanced medical production
            HACKING_FACTORY,      // Technical production
            GENERAL_FACTORY,      // General production
            COMMAND_CENTER,       // Command center
            RESEARCH_LAB,         // Research laboratory
            ENGINEERING_WORKSHOP, // Engineering workshop
            MEDICAL_BAY,          // Medical bay
            TRAINING_FACILITY,    // Training facility
            DEFENSE_TURRET,       // Defense turret
            POWER_GENERATOR,      // Power generator
            STORAGE_DEPOT         // Storage depot
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IntelReport {
        private String reportId;
        private String reportName;
        private IntelType intelType;
        private int intelValue;
        private int maxIntelValue;
        private Map<String, Integer> intelEffects;
        private List<String> intelEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> intelBonuses;
        private List<String> intelAbilities;
        private String intelMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum IntelType {
            ALIEN_MOVEMENT,      // Alien activity reports
            BASE_LOCATION,       // Alien base locations
            TECHNOLOGY_INTEL,    // Technology information
            TACTICAL_INTEL,      // Tactical information
            STRATEGIC_INTEL,     // Strategic information
            DIPLOMATIC_INTEL,    // Political information
            ECONOMIC_INTEL,      // Economic information
            SOCIAL_INTEL,        // Social information
            MILITARY_INTEL,      // Military information
            SCIENTIFIC_INTEL,    // Scientific information
            CULTURAL_INTEL,      // Cultural information
            ENVIRONMENTAL_INTEL, // Environmental information
            HISTORICAL_INTEL,    // Historical information
            FUTURE_INTEL,        // Predictive information
            SECRET_INTEL,        // Classified information
            ALIEN_TECHNOLOGY,    // Alien technology intel
            ALIEN_STRATEGY,      // Alien strategy intel
            THREAT_ASSESSMENT,   // Threat assessment intel
            RESOURCE_LOCATION,   // Resource location intel
            WEAKNESS_ANALYSIS,   // Weakness analysis intel
            STRENGTH_ANALYSIS    // Strength analysis intel
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicResource {
        private String resourceId;
        private String resourceName;
        private ResourceType resourceType;
        private int resourceAmount;
        private int maxResourceAmount;
        private Map<String, Integer> resourceEffects;
        private List<String> resourceEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> resourceBonuses;
        private List<String> resourceAbilities;
        private String resourceMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum ResourceType {
            MONEY,             // Financial resources
            MATERIALS,         // Raw materials
            SUPPLIES,          // General supplies
            AMMUNITION,        // Combat ammunition
            MEDICAL_SUPPLIES,  // Medical resources
            TECHNICAL_SUPPLIES, // Technical resources
            FOOD,              // Food supplies
            WATER,             // Water supplies
            FUEL,              // Fuel resources
            ELECTRONICS,       // Electronic components
            CHEMICALS,         // Chemical resources
            METALS,            // Metal resources
            TEXTILES,          // Textile resources
            PLASTICS,          // Plastic resources
            CERAMICS           // Ceramic resources
        }
    
    public enum AllocationType {
        MILITARY,          // Military allocation
        RESEARCH,          // Research allocation
        MANUFACTURING,     // Manufacturing allocation
        INFRASTRUCTURE,    // Infrastructure allocation
        INTELLIGENCE,      // Intelligence allocation
        DIPLOMATIC,        // Diplomatic allocation
        ECONOMIC,          // Economic allocation
        SOCIAL,            // Social allocation
        TECHNICAL,         // Technical allocation
        SECURITY,          // Security allocation
        DEVELOPMENT,       // Development allocation
        ALLIANCE,          // Alliance allocation
        TRADE,             // Trade allocation
        LOGISTICS,         // Logistics allocation
        EMERGENCY          // Emergency allocation
    }
    
    public enum ImpactType {
        POSITIVE,          // Positive impact
        NEGATIVE,          // Negative impact
        NEUTRAL,           // Neutral impact
        MIXED,             // Mixed impact
        IMMEDIATE,         // Immediate impact
        DELAYED,           // Delayed impact
        LONG_TERM,         // Long-term impact
        SHORT_TERM,        // Short-term impact
        DIRECT,            // Direct impact
        INDIRECT,          // Indirect impact
        CUMULATIVE,        // Cumulative impact
        SINGLE,            // Single impact
        RECURRING,         // Recurring impact
        TEMPORARY,         // Temporary impact
        PERMANENT          // Permanent impact
    }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicDecision {
        private String decisionId;
        private String decisionName;
        private DecisionType decisionType;
        private Map<String, Integer> decisionEffects;
        private List<String> decisionRequirements;
        private boolean isActive;
        private String assignedLeader;
        private String decisionLocation;
        private String decisionStatus;
        private int decisionDifficulty;
        private String decisionDescription;
        private Map<String, Integer> decisionBonuses;
        private List<String> decisionAbilities;
        private String decisionMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum DecisionType {
            MILITARY_DECISION,     // Military choices
            DIPLOMATIC_DECISION,   // Political choices
            ECONOMIC_DECISION,     // Economic choices
            SCIENTIFIC_DECISION,   // Research choices
            SOCIAL_DECISION,       // Social choices
            TECHNICAL_DECISION,    // Technical choices
            STRATEGIC_DECISION,    // Strategic choices
            TACTICAL_DECISION,     // Tactical choices
            LOGISTICAL_DECISION,   // Supply choices
            INTELLIGENCE_DECISION, // Intelligence choices
            SECURITY_DECISION,     // Security choices
            DEVELOPMENT_DECISION,  // Development choices
            ALLIANCE_DECISION,     // Alliance choices
            TRADE_DECISION,        // Trade choices
            RESEARCH_DECISION,     // Research choices
            BUILD_FACILITY,        // Build facility decision
            RESEARCH_TECHNOLOGY,   // Research technology decision
            TRAIN_SOLDIERS,        // Train soldiers decision
            LAUNCH_MISSION,        // Launch mission decision
            UPGRADE_BASE,          // Upgrade base decision
            ALLOCATE_RESOURCES,    // Allocate resources decision
            FORM_ALLIANCE,         // Form alliance decision
            DECLARE_WAR            // Declare war decision
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicConsequence {
        private String consequenceId;
        private String consequenceName;
        private ConsequenceType consequenceType;
        private String decisionId;
        private Map<String, Integer> consequenceEffects;
        private boolean isTriggered;
        private int severity;
        private String description;
        
        public enum ConsequenceType {
        POSITIVE_OUTCOME,      // Beneficial results
        NEGATIVE_OUTCOME,      // Harmful results
        MIXED_OUTCOME,         // Mixed results
        NEUTRAL_OUTCOME,       // Neutral results
        UNEXPECTED_OUTCOME,    // Unexpected results
        DELAYED_OUTCOME,       // Delayed results
        IMMEDIATE_OUTCOME,     // Immediate results
        LONG_TERM_OUTCOME      // Long-term results
    }
}
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicFeedback {
        private String feedbackId;
        private String source;
        private String message;
        private FeedbackType feedbackType;
        private int priority;
        private LocalDateTime timestamp;
        private boolean isProcessed;
        private String processingResult;
        
        public enum FeedbackType {
            SUCCESS,        // Successful operation
            WARNING,        // Warning message
            ERROR,          // Error message
            INFO,           // Information message
            CRITICAL,       // Critical issue
            DEBUG,          // Debug information
            STATUS,         // Status update
            PROGRESS,       // Progress update
            THREAT,         // Threat alert
            INFORMATION     // Information alert
        }
    }
    

    
    /**
     * Initialize the strategic layer system
     */
    private void initializeSystem() {
        // ToDo: Реализовать стратегический слой
        // - Strategic Layer Integration
        // - Mission Planning and Preparation
        // - Research and Development System
        // - Manufacturing and Supply Chain
        // - Intel Gathering and Analysis
        // - Strategic Resource Management
        // - Strategic Decision Making
        // - Strategic Consequences and Outcomes
        
        initializeManufacturingFacilities();
        initializeStrategicResources();
    }
    
    /**
     * Initialize manufacturing facilities
     */
    private void initializeManufacturingFacilities() {
        // Weapon Factory
        ManufacturingFacility weaponFactory = ManufacturingFacility.builder()
            .facilityId("WEAPON_FACTORY")
            .facilityName("Weapon Factory")
            .facilityType(ManufacturingFacility.FacilityType.WEAPON_FACTORY)
            .capacity(30)
            .currentOccupancy(0)
            .availableWorkers(Arrays.asList("WORKER_SMITH", "WORKER_JOHNSON", "WORKER_WILLIAMS"))
            .availableEquipment(Arrays.asList("ASSEMBLY_LINE", "QUALITY_CONTROL", "TESTING_RANGE"))
            .facilityBonuses(Map.of("weapon_production", 25, "weapon_quality", 30))
            .facilityServices(Arrays.asList("PRODUCTION", "QUALITY_CONTROL", "TESTING", "UPGRADE"))
            .isOperational(true)
            .facilityLevel(3)
            .facilityQuality(85)
            .location("MAIN_BASE")
            .operatingCost(5000)
            .maintenanceCost(1000)
            .facilityStatus("OPERATIONAL")
            .products(new ArrayList<>())
            .productionSuccessRates(Map.of("PRODUCTION", 95, "QUALITY_CONTROL", 90, "TESTING", 85))
            .specializations(Arrays.asList("RIFLES", "PISTOLS", "HEAVY_WEAPONS", "EXPLOSIVES"))
            .facilityDescription("Specialized weapon manufacturing facility")
            .build();
        
        manufacturingFacilities.put("WEAPON_FACTORY", weaponFactory);
        
        // Armor Factory
        ManufacturingFacility armorFactory = ManufacturingFacility.builder()
            .facilityId("ARMOR_FACTORY")
            .facilityName("Armor Factory")
            .facilityType(ManufacturingFacility.FacilityType.ARMOR_FACTORY)
            .capacity(25)
            .currentOccupancy(0)
            .availableWorkers(Arrays.asList("WORKER_BROWN", "WORKER_DAVIS", "WORKER_MILLER"))
            .availableEquipment(Arrays.asList("ASSEMBLY_LINE", "QUALITY_CONTROL", "TESTING_CHAMBER"))
            .facilityBonuses(Map.of("armor_production", 25, "armor_quality", 30))
            .facilityServices(Arrays.asList("PRODUCTION", "QUALITY_CONTROL", "TESTING", "UPGRADE"))
            .isOperational(true)
            .facilityLevel(3)
            .facilityQuality(80)
            .location("MAIN_BASE")
            .operatingCost(4000)
            .maintenanceCost(800)
            .facilityStatus("OPERATIONAL")
            .products(new ArrayList<>())
            .productionSuccessRates(Map.of("PRODUCTION", 90, "QUALITY_CONTROL", 85, "TESTING", 80))
            .specializations(Arrays.asList("BODY_ARMOR", "HELMETS", "SHIELDS", "PROTECTIVE_GEAR"))
            .facilityDescription("Specialized armor manufacturing facility")
            .build();
        
        manufacturingFacilities.put("ARMOR_FACTORY", armorFactory);
    }
    
    /**
     * Initialize strategic resources
     */
    private void initializeStrategicResources() {
        // Money resource
        StrategicResource money = StrategicResource.builder()
            .resourceId("MONEY")
            .resourceName("Money")
            .resourceType(StrategicResource.ResourceType.MONEY)
            .resourceAmount(10000)
            .maxResourceAmount(100000)
            .resourceEffects(Map.of("purchasing_power", 100, "influence", 50))
            .resourceEvents(new ArrayList<>())
            .isActive(true)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Always available")
            .successRate(1.0)
            .failureEffect("No effect")
            .resourceBonuses(new HashMap<>())
            .resourceAbilities(new ArrayList<>())
            .resourceMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Always")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(true)
            .permanentCondition("Always available")
            .build();
        
        strategicResources.put("MONEY", money);
        
        // Materials resource
        StrategicResource materials = StrategicResource.builder()
            .resourceId("MATERIALS")
            .resourceName("Materials")
            .resourceType(StrategicResource.ResourceType.MATERIALS)
            .resourceAmount(5000)
            .maxResourceAmount(50000)
            .resourceEffects(Map.of("manufacturing", 100, "construction", 75))
            .resourceEvents(new ArrayList<>())
            .isActive(true)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Always available")
            .successRate(1.0)
            .failureEffect("No effect")
            .resourceBonuses(new HashMap<>())
            .resourceAbilities(new ArrayList<>())
            .resourceMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Always")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(true)
            .permanentCondition("Always available")
            .build();
        
        strategicResources.put("MATERIALS", materials);
    }
    
    /**
     * Create strategic mission
     */
    public boolean createStrategicMission(String missionId, String missionName, StrategicMission.MissionType missionType) {
        if (strategicMissions.containsKey(missionId)) {
            return false; // Mission already exists
        }
        
        StrategicMission mission = StrategicMission.builder()
            .missionId(missionId)
            .missionName(missionName)
            .missionType(missionType)
            .priority(StrategicMission.MissionPriority.MEDIUM)
            .missionDuration(5)
            .currentDuration(0)
            .missionObjectives(new ArrayList<>())
            .missionRewards(new HashMap<>())
            .missionRequirements(new ArrayList<>())
            .isActive(false)
            .assignedSquad("")
            .missionLocation("")
            .missionStatus("CREATED")
            .missionDifficulty(3)
            .missionDescription("Strategic mission: " + missionName)
            .missionBonuses(new HashMap<>())
            .missionAbilities(new ArrayList<>())
            .missionMethod("Manual")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Manual activation")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        strategicMissions.put(missionId, mission);
        missionHistory.put(missionId, new ArrayList<>());
        totalMissions++;
        
        return true;
    }
    
    /**
     * Start research project
     */
    public boolean startResearchProject(String projectId, String projectName, ResearchProject.ResearchCategory category) {
        if (researchProjects.containsKey(projectId)) {
            return false; // Project already exists
        }
        
        ResearchProject project = ResearchProject.builder()
            .projectId(projectId)
            .projectName(projectName)
            .researchCategory(category)
            .researchDuration(10)
            .currentDuration(0)
            .researchObjectives(new ArrayList<>())
            .researchRewards(new HashMap<>())
            .researchRequirements(new ArrayList<>())
            .isActive(false)
            .assignedScientist("")
            .researchFacility("")
            .researchStatus("CREATED")
            .researchDifficulty(3)
            .researchDescription("Research project: " + projectName)
            .researchBonuses(new HashMap<>())
            .researchAbilities(new ArrayList<>())
            .researchMethod("Manual")
            .energyCost(0)
            .isAutomatic(false)
            .triggerCondition("Manual activation")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        researchProjects.put(projectId, project);
        activeProjects.put(projectId, new ArrayList<>());
        projectProgress.put(projectId, 0);
        projectStates.put(projectId, false);
        
        return true;
    }
    
    /**
     * Allocate resources
     */
    public boolean allocateResources(String resourceId, String targetId, int amount) {
        StrategicResource resource = strategicResources.get(resourceId);
        if (resource == null || resource.getResourceAmount() < amount) {
            return false;
        }
        
        resource.setResourceAmount(resource.getResourceAmount() - amount);
        
        // Track allocation
        resourceAllocation.put(targetId, resourceAllocation.getOrDefault(targetId, new HashMap<>()));
        resourceAllocation.get(targetId).put(resourceId, 
            resourceAllocation.get(targetId).getOrDefault(resourceId, 0) + amount);
        
        return true;
    }
    
    /**
     * Process strategic layer
     */
    public void processStrategicLayer() {
        // Process active missions
        for (StrategicMission mission : strategicMissions.values()) {
            if (mission.isActive()) {
                processMission(mission);
            }
        }
        
        // Process research projects
        for (ResearchProject project : researchProjects.values()) {
            if (project.isActive()) {
                processResearchProject(project);
            }
        }
        
        // Process manufacturing
        for (ManufacturingFacility facility : manufacturingFacilities.values()) {
            if (facility.isOperational()) {
                processManufacturing(facility);
            }
        }
    }
    
    /**
     * Process mission
     */
    private void processMission(StrategicMission mission) {
        mission.setCurrentDuration(mission.getCurrentDuration() + 1);
        
        // Check if mission is complete
        if (mission.getCurrentDuration() >= mission.getMissionDuration()) {
            completeMission(mission);
        } else {
            // Update mission progress
            int progress = (mission.getCurrentDuration() * 100) / mission.getMissionDuration();
            mission.setMissionStatus("IN_PROGRESS (" + progress + "%)");
        }
    }
    
    /**
     * Complete mission
     */
    private void completeMission(StrategicMission mission) {
                    mission.setActive(false);
        mission.setMissionStatus("COMPLETED");
        
        // Apply mission rewards
        for (Map.Entry<String, Integer> reward : mission.getMissionRewards().entrySet()) {
            String rewardType = reward.getKey();
            Integer rewardValue = reward.getValue();
            applyReward(rewardType, rewardValue);
        }
        
        // Log mission completion
        missionHistory.get(mission.getMissionId()).add("Mission completed: " + mission.getMissionName());
    }
    
    /**
     * Process research project
     */
    private void processResearchProject(ResearchProject project) {
        project.setCurrentDuration(project.getCurrentDuration() + 1);
        
        // Check if research is complete
        if (project.getCurrentDuration() >= project.getResearchDuration()) {
            completeResearchProject(project);
        } else {
            // Update research progress
            int progress = (project.getCurrentDuration() * 100) / project.getResearchDuration();
            project.setResearchStatus("IN_PROGRESS (" + progress + "%)");
            projectProgress.put(project.getProjectId(), progress);
        }
    }
    
    /**
     * Complete research project
     */
    private void completeResearchProject(ResearchProject project) {
                    project.setActive(false);
        project.setResearchStatus("COMPLETED");
        projectStates.put(project.getProjectId(), true);
        
        // Apply research rewards
        for (Map.Entry<String, Integer> reward : project.getResearchRewards().entrySet()) {
            String rewardType = reward.getKey();
            Integer rewardValue = reward.getValue();
            applyReward(rewardType, rewardValue);
        }
        
        // Log research completion
        activeProjects.get(project.getProjectId()).add("Research completed: " + project.getProjectName());
    }
    
    /**
     * Process manufacturing
     */
    private void processManufacturing(ManufacturingFacility facility) {
        // Process production based on facility type
        if (facility.getCurrentOccupancy() < facility.getCapacity()) {
            // Produce items
            facility.setCurrentOccupancy(facility.getCurrentOccupancy() + 1);
        }
    }
    
    /**
     * Get strategic mission by ID
     */
    public StrategicMission getStrategicMission(String missionId) {
        return strategicMissions.get(missionId);
    }
    
    /**
     * Get research project by ID
     */
    public ResearchProject getResearchProject(String projectId) {
        return researchProjects.get(projectId);
    }
    
    /**
     * Get manufacturing facility by ID
     */
    public ManufacturingFacility getManufacturingFacility(String facilityId) {
        return manufacturingFacilities.get(facilityId);
    }
    
    /**
     * Get strategic resource by ID
     */
    public StrategicResource getStrategicResource(String resourceId) {
        return strategicResources.get(resourceId);
    }
    
    /**
     * Get total missions
     */
    public int getTotalMissions() {
        return totalMissions;
    }
    
    /**
     * Check if project is active
     */
    public boolean isProjectActive(String projectId) {
        return projectStates.getOrDefault(projectId, false);
    }
    
    /**
     * Get project progress
     */
    public int getProjectProgress(String projectId) {
        return projectProgress.getOrDefault(projectId, 0);
    }
    
    // Methods needed by StrategicLayerManager
    public boolean initializeStrategicState(String stateId, String name) {
        // Implementation for strategic state initialization
        return true;
    }
    
    public boolean updateStrategicState(String stateId) {
        // Implementation for strategic state update
        return true;
    }
    
    public boolean addStrategicResource(String resourceId, String name, StrategicResource.ResourceType type, int maxAmount, int regenerationRate) {
        // Implementation for adding strategic resource
        return true;
    }
    
    public boolean allocateStrategicResource(String resourceId, String targetId, int amount, StrategicResource.AllocationType type) {
        // Implementation for resource allocation
        return true;
    }
    
    public boolean processTacticalImpact(String missionId, StrategicResource.ImpactType impactType, int magnitude, List<String> affectedSystems) {
        // Implementation for tactical impact processing
        return true;
    }
    
    public boolean addStrategicConsequence(String source, StrategicConsequence.ConsequenceType type, int magnitude, List<String> affectedSystems) {
        // Implementation for adding strategic consequence
        return true;
    }
    
    public boolean makeStrategicDecision(String description, StrategicDecision.DecisionType type, int cost, List<String> consequences) {
        // Implementation for making strategic decision
        return true;
    }
    
    public boolean implementStrategicDecision(String decisionId) {
        // Implementation for implementing strategic decision
        return true;
    }
    
    public boolean addBaseFacility(String facilityId, String name, ManufacturingFacility.FacilityType type, int level, int capacity) {
        // Implementation for adding base facility
        return true;
    }
    
    public boolean addGlobalThreat(String threatId, String name, int level, int progression) {
        // Implementation for adding global threat
        return true;
    }
    
    public boolean gatherIntel(String source, IntelReport.IntelType type, int value, List<String> applications) {
        // Implementation for gathering intel
        return true;
    }
    
    public boolean analyzeIntel(String intelId) {
        // Implementation for analyzing intel
        return true;
    }
    
    /**
     * Add strategic feedback
     */
    public void addStrategicFeedback(String source, StrategicFeedback.FeedbackType type, String message, int priority) {
        // Implementation for adding strategic feedback
        StrategicFeedback feedback = StrategicFeedback.builder()
            .feedbackId("FB_" + System.currentTimeMillis())
            .source(source)
            .message(message)
            .feedbackType(type)
            .priority(priority)
            .timestamp(LocalDateTime.now())
            .isProcessed(false)
            .processingResult("")
            .build();
        
        // Store feedback (implementation would depend on storage mechanism)
    }
    
    /**
     * Apply reward to strategic resources
     */
    private void applyReward(String rewardType, Integer rewardValue) {
        StrategicResource resource = strategicResources.get(rewardType);
        if (resource != null) {
            int newAmount = Math.min(resource.getResourceAmount() + rewardValue, resource.getMaxResourceAmount());
            resource.setResourceAmount(newAmount);
        }
    }
    
    /**
     * Get strategic state
     */
    public StrategicState getStrategicState(String stateId) {
        // Implementation for getting strategic state
        return null; // Placeholder implementation
    }
    
    /**
     * Strategic state class
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategicState {
        private String stateId;
        private String name;
        private int globalThreatLevel;
        private LocalDateTime lastUpdate;
        
        public int getGlobalThreatLevel() {
            return globalThreatLevel;
        }
    }
}

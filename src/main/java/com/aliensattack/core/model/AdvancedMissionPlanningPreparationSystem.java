package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Mission Planning and Preparation System
 * Implements pre-mission planning with equipment selection, soldier loadouts, and mission briefing mechanics
 */
@Data
@Builder
@AllArgsConstructor
public class AdvancedMissionPlanningPreparationSystem {
    
    private Map<String, MissionBriefing> missionBriefings;
    private Map<String, EquipmentLoadout> equipmentLoadouts;
    private Map<String, SoldierSelection> soldierSelections;
    private Map<String, EquipmentMaintenance> equipmentMaintenance;
    private Map<String, MissionPreparationBonus> preparationBonuses;
    private Map<String, MissionPlan> missionPlans;
    private Map<String, PreparationEvent> preparationEvents;
    private Map<String, List<String>> selectedSoldiers;
    private Map<String, List<String>> selectedEquipment;
    private Map<String, Integer> preparationProgress;
    private Map<String, Integer> equipmentCondition;
    private Map<String, Boolean> preparationComplete;
    private Map<String, List<String>> missionObjectives;
    private Map<String, Map<String, Integer>> soldierLoadouts;
    
    /**
     * Default constructor that initializes the mission planning and preparation system
     */
    public AdvancedMissionPlanningPreparationSystem() {
        // Initialize maps
        missionBriefings = new HashMap<>();
        equipmentLoadouts = new HashMap<>();
        soldierSelections = new HashMap<>();
        equipmentMaintenance = new HashMap<>();
        preparationBonuses = new HashMap<>();
        missionPlans = new HashMap<>();
        preparationEvents = new HashMap<>();
        selectedSoldiers = new HashMap<>();
        selectedEquipment = new HashMap<>();
        preparationProgress = new HashMap<>();
        equipmentCondition = new HashMap<>();
        preparationComplete = new HashMap<>();
        missionObjectives = new HashMap<>();
        soldierLoadouts = new HashMap<>();
        
        // Initialize the system
        initializeSystem();
    }
    
    /**
     * Mission briefing with detailed information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionBriefing {
        private String briefingId;
        private String missionId;
        private String missionName;
        private String missionType;
        private String description;
        private List<String> objectives;
        private Map<String, Integer> missionParameters;
        private List<String> enemyTypes;
        private List<String> environmentalConditions;
        private int estimatedDuration;
        private String difficulty;
        private Map<String, Integer> rewards;
        private List<String> specialRequirements;
        private String briefingOfficer;
        private int briefingTime;
        private boolean isClassified;
        private String classificationLevel;
        private Map<String, Integer> missionBonuses;
        private List<String> missionHazards;
        private String missionLocation;
        private int missionPriority;
    }
    
    /**
     * Equipment loadout system
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipmentLoadout {
        private String loadoutId;
        private String soldierId;
        private String loadoutName;
        private String loadoutType;
        private Map<String, String> equipmentSlots;
        private List<String> primaryWeapons;
        private List<String> secondaryWeapons;
        private List<String> armor;
        private List<String> utilities;
        private List<String> consumables;
        private Map<String, Integer> loadoutBonuses;
        private boolean isActive;
        private String loadoutStatus;
        private int totalWeight;
        private int maxWeight;
        private Map<String, Integer> slotLimits;
        private List<String> loadoutRequirements;
        private Map<String, Integer> classBonuses;
        private int loadoutCost;
        private String loadoutQuality;
        private boolean isMaintained;
        private int maintenanceLevel;
    }
    
    /**
     * Soldier selection for missions
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SoldierSelection {
        private String selectionId;
        private String missionId;
        private String soldierId;
        private String soldierClass;
        private int soldierLevel;
        private Map<String, Integer> soldierStats;
        private List<String> soldierAbilities;
        private String soldierStatus;
        private boolean isSelected;
        private String selectionReason;
        private Map<String, Integer> missionBonuses;
        private List<String> missionSpecializations;
        private int experience;
        private int missionCount;
        private double successRate;
        private String availability;
        private Map<String, Integer> classBonuses;
        private List<String> equipmentCompatibility;
        private boolean isVeteran;
        private String veteranStatus;
    }
    
    /**
     * Equipment maintenance system
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipmentMaintenance {
        private String maintenanceId;
        private String equipmentId;
        private String equipmentType;
        private int currentCondition;
        private int maxCondition;
        private int maintenanceCost;
        private String maintenanceStatus;
        private int maintenanceTime;
        private int currentMaintenanceTime;
        private String maintenanceType;
        private Map<String, Integer> maintenanceBonuses;
        private List<String> maintenanceRequirements;
        private boolean isMaintained;
        private String maintenanceQuality;
        private Map<String, Integer> efficiency;
        private boolean isCritical;
        private String criticalStatus;
        private int repairCost;
        private String repairMethod;
    }
    
    /**
     * Mission preparation bonus system
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPreparationBonus {
        private String bonusId;
        private String bonusName;
        private String description;
        private String bonusType;
        private Map<String, Integer> bonusEffects;
        private List<String> affectedMissions;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> bonusBonuses;
        private List<String> bonusAbilities;
        private String bonusMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private int progressRequired;
        private int currentProgress;
    }
    
    /**
     * Mission plan with tactical details
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPlan {
        private String planId;
        private String missionId;
        private String planName;
        private String planType;
        private List<String> tacticalObjectives;
        private Map<String, Integer> planParameters;
        private List<String> squadComposition;
        private List<String> equipmentRequirements;
        private String approachMethod;
        private String extractionMethod;
        private Map<String, Integer> planBonuses;
        private List<String> planAbilities;
        private boolean isApproved;
        private String approvalStatus;
        private String planCreator;
        private int planCost;
        private String planQuality;
        private Map<String, Integer> riskFactors;
        private List<String> contingencyPlans;
        private int estimatedSuccess;
        private String planStatus;
    }
    
    /**
     * Preparation event tracking
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreparationEvent {
        private String eventId;
        private String eventType;
        private String sourceId;
        private String targetId;
        private int timestamp;
        private Map<String, Integer> eventEffects;
        private List<String> affectedUnits;
        private boolean isSuccessful;
        private String result;
        private int energyCost;
        private int energyGained;
        private String eventDescription;
        private Map<String, Integer> bonuses;
        private List<String> consequences;
        private String eventLocation;
        private int duration;
        private boolean isPermanent;
        private String preparationStatus;
        private int preparationLevel;
    }
    

    
    /**
     * Initialize the mission planning and preparation system
     */
    private void initializeSystem() {
        // Initialize mission briefings
        initializeMissionBriefings();
        
        // Initialize equipment loadouts
        initializeEquipmentLoadouts();
        
        // Initialize soldier selections
        initializeSoldierSelections();
        
        // Initialize equipment maintenance
        initializeEquipmentMaintenance();
        
        // Initialize preparation bonuses
        initializePreparationBonuses();
    }
    
    /**
     * Initialize mission briefings
     */
    private void initializeMissionBriefings() {
        // Basic mission briefing
        MissionBriefing basicBriefing = MissionBriefing.builder()
            .briefingId("basic_mission_briefing")
            .missionId("mission_001")
            .missionName("Reconnaissance Mission")
            .missionType("Reconnaissance")
            .description("Scout enemy positions and gather intelligence")
            .objectives(Arrays.asList("Reach observation point", "Gather intel", "Extract safely"))
            .missionParameters(Map.of("Duration", 8, "Difficulty", 3, "Reward", 100))
            .enemyTypes(Arrays.asList("Sectoid", "Trooper"))
            .environmentalConditions(Arrays.asList("Urban", "Night"))
            .estimatedDuration(8)
            .difficulty("Moderate")
            .rewards(Map.of("Experience", 50, "Intel", 25))
            .specialRequirements(Arrays.asList("Stealth equipment", "Reconnaissance training"))
            .briefingOfficer("Commander")
            .briefingTime(5)
            .isClassified(false)
            .classificationLevel("Unclassified")
            .missionBonuses(Map.of("Stealth", 10, "Reconnaissance", 15))
            .missionHazards(Arrays.asList("Enemy patrols", "Limited visibility"))
            .missionLocation("City outskirts")
            .missionPriority(2)
            .build();
        
        missionBriefings.put("basic_mission_briefing", basicBriefing);
        
        // Advanced mission briefing
        MissionBriefing advancedBriefing = MissionBriefing.builder()
            .briefingId("advanced_mission_briefing")
            .missionId("mission_002")
            .missionName("Assassination Mission")
            .missionType("Assassination")
            .description("Eliminate high-value target and extract")
            .objectives(Arrays.asList("Locate target", "Eliminate target", "Extract safely"))
            .missionParameters(Map.of("Duration", 12, "Difficulty", 5, "Reward", 200))
            .enemyTypes(Arrays.asList("Sectoid", "Trooper", "Elite Guard"))
            .environmentalConditions(Arrays.asList("Indoor", "High security"))
            .estimatedDuration(12)
            .difficulty("Hard")
            .rewards(Map.of("Experience", 100, "Intel", 50, "Equipment", 25))
            .specialRequirements(Arrays.asList("Combat training", "Stealth equipment", "High-level weapons"))
            .briefingOfficer("Commander")
            .briefingTime(10)
            .isClassified(true)
            .classificationLevel("Top Secret")
            .missionBonuses(Map.of("Combat", 20, "Stealth", 15, "Extraction", 10))
            .missionHazards(Arrays.asList("Heavy security", "Time pressure", "High-value target"))
            .missionLocation("Enemy facility")
            .missionPriority(1)
            .build();
        
        missionBriefings.put("advanced_mission_briefing", advancedBriefing);
    }
    
    /**
     * Initialize equipment loadouts
     */
    private void initializeEquipmentLoadouts() {
        // Ranger loadout
        EquipmentLoadout rangerLoadout = EquipmentLoadout.builder()
            .loadoutId("ranger_loadout")
            .soldierId("ranger_001")
            .loadoutName("Ranger Stealth Loadout")
            .loadoutType("Stealth")
            .equipmentSlots(Map.of("Primary", "Shotgun", "Secondary", "Sword", "Armor", "Stealth Suit"))
            .primaryWeapons(Arrays.asList("Shotgun"))
            .secondaryWeapons(Arrays.asList("Sword"))
            .armor(Arrays.asList("Stealth Suit"))
            .utilities(Arrays.asList("Smoke Grenade", "Flashbang"))
            .consumables(Arrays.asList("Medikit", "Ammo"))
            .loadoutBonuses(Map.of("Stealth", 20, "Close Combat", 15))
            .isActive(true)
            .loadoutStatus("Ready")
            .totalWeight(25)
            .maxWeight(30)
            .slotLimits(Map.of("Primary", 1, "Secondary", 1, "Armor", 1))
            .loadoutRequirements(Arrays.asList("Ranger class"))
            .classBonuses(Map.of("Ranger", 15))
            .loadoutCost(150)
            .loadoutQuality("Standard")
            .isMaintained(true)
            .maintenanceLevel(100)
            .build();
        
        equipmentLoadouts.put("ranger_loadout", rangerLoadout);
        
        // Specialist loadout
        EquipmentLoadout specialistLoadout = EquipmentLoadout.builder()
            .loadoutId("specialist_loadout")
            .soldierId("specialist_001")
            .loadoutName("Specialist Technical Loadout")
            .loadoutType("Technical")
            .equipmentSlots(Map.of("Primary", "Assault Rifle", "Secondary", "Gremlin", "Armor", "Light Armor"))
            .primaryWeapons(Arrays.asList("Assault Rifle"))
            .secondaryWeapons(Arrays.asList("Gremlin"))
            .armor(Arrays.asList("Light Armor"))
            .utilities(Arrays.asList("Hacking Device", "Medical Kit"))
            .consumables(Arrays.asList("Medikit", "Ammo", "Hacking Tools"))
            .loadoutBonuses(Map.of("Technical", 20, "Medical", 15))
            .isActive(true)
            .loadoutStatus("Ready")
            .totalWeight(20)
            .maxWeight(25)
            .slotLimits(Map.of("Primary", 1, "Secondary", 1, "Armor", 1))
            .loadoutRequirements(Arrays.asList("Specialist class"))
            .classBonuses(Map.of("Specialist", 15))
            .loadoutCost(120)
            .loadoutQuality("Standard")
            .isMaintained(true)
            .maintenanceLevel(100)
            .build();
        
        equipmentLoadouts.put("specialist_loadout", specialistLoadout);
    }
    
    /**
     * Initialize soldier selections
     */
    private void initializeSoldierSelections() {
        // Ranger selection
        SoldierSelection rangerSelection = SoldierSelection.builder()
            .selectionId("ranger_selection")
            .missionId("mission_001")
            .soldierId("ranger_001")
            .soldierClass("Ranger")
            .soldierLevel(3)
            .soldierStats(Map.of("Health", 5, "Armor", 1, "Mobility", 15, "Aim", 70))
            .soldierAbilities(Arrays.asList("Phantom", "Bladestorm", "Shadowstep"))
            .soldierStatus("Available")
            .isSelected(true)
            .selectionReason("Stealth specialist needed")
            .missionBonuses(Map.of("Stealth", 15, "Reconnaissance", 10))
            .missionSpecializations(Arrays.asList("Stealth", "Close Combat"))
            .experience(150)
            .missionCount(5)
            .successRate(0.8)
            .availability("Available")
            .classBonuses(Map.of("Ranger", 10))
            .equipmentCompatibility(Arrays.asList("Stealth Suit", "Shotgun", "Sword"))
            .isVeteran(false)
            .veteranStatus("Regular")
            .build();
        
        soldierSelections.put("ranger_selection", rangerSelection);
        
        // Specialist selection
        SoldierSelection specialistSelection = SoldierSelection.builder()
            .selectionId("specialist_selection")
            .missionId("mission_001")
            .soldierId("specialist_001")
            .soldierClass("Specialist")
            .soldierLevel(2)
            .soldierStats(Map.of("Health", 4, "Armor", 1, "Mobility", 12, "Aim", 75))
            .soldierAbilities(Arrays.asList("Medical Protocol", "Combat Protocol"))
            .soldierStatus("Available")
            .isSelected(true)
            .selectionReason("Technical support needed")
            .missionBonuses(Map.of("Technical", 15, "Medical", 10))
            .missionSpecializations(Arrays.asList("Technical", "Medical"))
            .experience(100)
            .missionCount(3)
            .successRate(0.7)
            .availability("Available")
            .classBonuses(Map.of("Specialist", 10))
            .equipmentCompatibility(Arrays.asList("Light Armor", "Assault Rifle", "Gremlin"))
            .isVeteran(false)
            .veteranStatus("Regular")
            .build();
        
        soldierSelections.put("specialist_selection", specialistSelection);
    }
    
    /**
     * Initialize equipment maintenance
     */
    private void initializeEquipmentMaintenance() {
        // Weapon maintenance
        EquipmentMaintenance weaponMaintenance = EquipmentMaintenance.builder()
            .maintenanceId("weapon_maintenance")
            .equipmentId("shotgun_001")
            .equipmentType("Weapon")
            .currentCondition(85)
            .maxCondition(100)
            .maintenanceCost(25)
            .maintenanceStatus("Good")
            .maintenanceTime(2)
            .currentMaintenanceTime(0)
            .maintenanceType("Preventive")
            .maintenanceBonuses(Map.of("Accuracy", 5, "Reliability", 10))
            .maintenanceRequirements(Arrays.asList("Weapon parts", "Cleaning kit"))
            .isMaintained(true)
            .maintenanceQuality("Good")
            .efficiency(Map.of("Accuracy", 85, "Reliability", 90))
            .isCritical(false)
            .criticalStatus("Operational")
            .repairCost(50)
            .repairMethod("Standard")
            .build();
        
        equipmentMaintenance.put("weapon_maintenance", weaponMaintenance);
        
        // Armor maintenance
        EquipmentMaintenance armorMaintenance = EquipmentMaintenance.builder()
            .maintenanceId("armor_maintenance")
            .equipmentId("stealth_suit_001")
            .equipmentType("Armor")
            .currentCondition(90)
            .maxCondition(100)
            .maintenanceCost(30)
            .maintenanceStatus("Excellent")
            .maintenanceTime(3)
            .currentMaintenanceTime(0)
            .maintenanceType("Preventive")
            .maintenanceBonuses(Map.of("Protection", 5, "Stealth", 10))
            .maintenanceRequirements(Arrays.asList("Armor parts", "Stealth materials"))
            .isMaintained(true)
            .maintenanceQuality("Excellent")
            .efficiency(Map.of("Protection", 90, "Stealth", 95))
            .isCritical(false)
            .criticalStatus("Operational")
            .repairCost(75)
            .repairMethod("Specialized")
            .build();
        
        equipmentMaintenance.put("armor_maintenance", armorMaintenance);
    }
    
    /**
     * Initialize preparation bonuses
     */
    private void initializePreparationBonuses() {
        // Thorough preparation bonus
        MissionPreparationBonus thoroughPreparation = MissionPreparationBonus.builder()
            .bonusId("thorough_preparation")
            .bonusName("Thorough Preparation")
            .description("Bonus for thorough mission preparation")
            .bonusType("Preparation")
            .bonusEffects(Map.of("Success Rate", 10, "Experience", 25))
            .affectedMissions(Arrays.asList("mission_001", "mission_002"))
            .isActive(false)
            .duration(-1)
            .currentDuration(0)
            .activationCondition("100% preparation complete")
            .successRate(1.0)
            .failureEffect("No bonus")
            .bonusBonuses(Map.of("Preparation", 10, "Success", 5))
            .bonusAbilities(Arrays.asList("Enhanced Planning", "Better Equipment"))
            .bonusMethod("Passive")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Preparation Complete")
            .progressRequired(100)
            .currentProgress(0)
            .build();
        
        preparationBonuses.put("thorough_preparation", thoroughPreparation);
        
        // Equipment maintenance bonus
        MissionPreparationBonus equipmentMaintenanceBonus = MissionPreparationBonus.builder()
            .bonusId("equipment_maintenance_bonus")
            .bonusName("Equipment Maintenance")
            .description("Bonus for maintaining equipment in top condition")
            .bonusType("Maintenance")
            .bonusEffects(Map.of("Equipment Efficiency", 15, "Reliability", 10))
            .affectedMissions(Arrays.asList("mission_001", "mission_002"))
            .isActive(false)
            .duration(-1)
            .currentDuration(0)
            .activationCondition("All equipment at 90%+ condition")
            .successRate(1.0)
            .failureEffect("No bonus")
            .bonusBonuses(Map.of("Equipment", 15, "Reliability", 10))
            .bonusAbilities(Arrays.asList("Enhanced Equipment", "Better Performance"))
            .bonusMethod("Passive")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Equipment Maintained")
            .progressRequired(90)
            .currentProgress(0)
            .build();
        
        preparationBonuses.put("equipment_maintenance_bonus", equipmentMaintenanceBonus);
    }
    
    /**
     * Create mission briefing
     */
    public boolean createMissionBriefing(String missionId, String missionName, String missionType) {
        MissionBriefing briefing = MissionBriefing.builder()
            .briefingId(missionId + "_briefing")
            .missionId(missionId)
            .missionName(missionName)
            .missionType(missionType)
            .description("Mission briefing for " + missionName)
            .objectives(new ArrayList<>())
            .missionParameters(new HashMap<>())
            .enemyTypes(new ArrayList<>())
            .environmentalConditions(new ArrayList<>())
            .estimatedDuration(8)
            .difficulty("Moderate")
            .rewards(new HashMap<>())
            .specialRequirements(new ArrayList<>())
            .briefingOfficer("Commander")
            .briefingTime(5)
            .isClassified(false)
            .classificationLevel("Unclassified")
            .missionBonuses(new HashMap<>())
            .missionHazards(new ArrayList<>())
            .missionLocation("Unknown")
            .missionPriority(3)
            .build();
        
        missionBriefings.put(briefing.getBriefingId(), briefing);
        return true;
    }
    
    /**
     * Create equipment loadout
     */
    public boolean createEquipmentLoadout(String soldierId, String loadoutName, String loadoutType) {
        EquipmentLoadout loadout = EquipmentLoadout.builder()
            .loadoutId(soldierId + "_loadout")
            .soldierId(soldierId)
            .loadoutName(loadoutName)
            .loadoutType(loadoutType)
            .equipmentSlots(new HashMap<>())
            .primaryWeapons(new ArrayList<>())
            .secondaryWeapons(new ArrayList<>())
            .armor(new ArrayList<>())
            .utilities(new ArrayList<>())
            .consumables(new ArrayList<>())
            .loadoutBonuses(new HashMap<>())
            .isActive(false)
            .loadoutStatus("Incomplete")
            .totalWeight(0)
            .maxWeight(30)
            .slotLimits(new HashMap<>())
            .loadoutRequirements(new ArrayList<>())
            .classBonuses(new HashMap<>())
            .loadoutCost(0)
            .loadoutQuality("Basic")
            .isMaintained(false)
            .maintenanceLevel(0)
            .build();
        
        equipmentLoadouts.put(loadout.getLoadoutId(), loadout);
        return true;
    }
    
    /**
     * Select soldier for mission
     */
    public boolean selectSoldierForMission(String missionId, String soldierId, String soldierClass) {
        SoldierSelection selection = SoldierSelection.builder()
            .selectionId(missionId + "_" + soldierId)
            .missionId(missionId)
            .soldierId(soldierId)
            .soldierClass(soldierClass)
            .soldierLevel(1)
            .soldierStats(new HashMap<>())
            .soldierAbilities(new ArrayList<>())
            .soldierStatus("Available")
            .isSelected(true)
            .selectionReason("Selected for mission")
            .missionBonuses(new HashMap<>())
            .missionSpecializations(new ArrayList<>())
            .experience(0)
            .missionCount(0)
            .successRate(0.0)
            .availability("Available")
            .classBonuses(new HashMap<>())
            .equipmentCompatibility(new ArrayList<>())
            .isVeteran(false)
            .veteranStatus("Regular")
            .build();
        
        soldierSelections.put(selection.getSelectionId(), selection);
        
        // Add to selected soldiers list
        List<String> selected = selectedSoldiers.get(missionId);
        if (selected == null) {
            selected = new ArrayList<>();
            selectedSoldiers.put(missionId, selected);
        }
        selected.add(soldierId);
        
        return true;
    }
    
    /**
     * Add equipment to loadout
     */
    public boolean addEquipmentToLoadout(String loadoutId, String equipmentId, String slot) {
        EquipmentLoadout loadout = equipmentLoadouts.get(loadoutId);
        if (loadout == null) {
            return false;
        }
        
        // Add equipment to slot
        Map<String, String> slots = loadout.getEquipmentSlots();
        slots.put(slot, equipmentId);
        
        // Add to appropriate category
        if (slot.equals("Primary") || slot.equals("Secondary")) {
            if (slot.equals("Primary")) {
                loadout.getPrimaryWeapons().add(equipmentId);
            } else {
                loadout.getSecondaryWeapons().add(equipmentId);
            }
        } else if (slot.equals("Armor")) {
            loadout.getArmor().add(equipmentId);
        } else {
            loadout.getUtilities().add(equipmentId);
        }
        
        // Update loadout status
        if (!loadout.getEquipmentSlots().isEmpty()) {
            loadout.setLoadoutStatus("Ready");
            loadout.setActive(true);
        }
        
        return true;
    }
    
    /**
     * Maintain equipment
     */
    public boolean maintainEquipment(String equipmentId, String maintenanceType) {
        EquipmentMaintenance maintenance = equipmentMaintenance.get(equipmentId);
        if (maintenance == null) {
            maintenance = EquipmentMaintenance.builder()
                .maintenanceId(equipmentId + "_maintenance")
                .equipmentId(equipmentId)
                .equipmentType("Unknown")
                .currentCondition(100)
                .maxCondition(100)
                .maintenanceCost(25)
                .maintenanceStatus("Excellent")
                .maintenanceTime(2)
                .currentMaintenanceTime(0)
                .maintenanceType(maintenanceType)
                .maintenanceBonuses(new HashMap<>())
                .maintenanceRequirements(new ArrayList<>())
                .isMaintained(true)
                .maintenanceQuality("Good")
                .efficiency(new HashMap<>())
                .isCritical(false)
                .criticalStatus("Operational")
                .repairCost(50)
                .repairMethod("Standard")
                .build();
            
            equipmentMaintenance.put(maintenance.getMaintenanceId(), maintenance);
        }
        
        // Perform maintenance
        maintenance.setCurrentCondition(Math.min(100, maintenance.getCurrentCondition() + 15));
        maintenance.setMaintenanceStatus("Maintained");
        maintenance.setMaintained(true);
        
        // Update equipment condition
        equipmentCondition.put(equipmentId, maintenance.getCurrentCondition());
        
        return true;
    }
    
    /**
     * Update preparation progress
     */
    public boolean updatePreparationProgress(String missionId, int progress) {
        int currentProgress = preparationProgress.getOrDefault(missionId, 0);
        preparationProgress.put(missionId, currentProgress + progress);
        
        // Check for preparation bonuses
        for (MissionPreparationBonus bonus : preparationBonuses.values()) {
            if (currentProgress + progress >= bonus.getProgressRequired() && !bonus.isActive()) {
                bonus.setActive(true);
                bonus.setCurrentProgress(currentProgress + progress);
            }
        }
        
        // Mark as complete if 100%
        if (currentProgress + progress >= 100) {
            preparationComplete.put(missionId, true);
        }
        
        return true;
    }
    
    /**
     * Create mission plan
     */
    public boolean createMissionPlan(String missionId, String planName, String planType) {
        MissionPlan plan = MissionPlan.builder()
            .planId(missionId + "_plan")
            .missionId(missionId)
            .planName(planName)
            .planType(planType)
            .tacticalObjectives(new ArrayList<>())
            .planParameters(new HashMap<>())
            .squadComposition(new ArrayList<>())
            .equipmentRequirements(new ArrayList<>())
            .approachMethod("Standard")
            .extractionMethod("Standard")
            .planBonuses(new HashMap<>())
            .planAbilities(new ArrayList<>())
            .isApproved(false)
            .approvalStatus("Pending")
            .planCreator("Commander")
            .planCost(0)
            .planQuality("Basic")
            .riskFactors(new HashMap<>())
            .contingencyPlans(new ArrayList<>())
            .estimatedSuccess(70)
            .planStatus("Draft")
            .build();
        
        missionPlans.put(plan.getPlanId(), plan);
        return true;
    }
    
    /**
     * Get mission briefing
     */
    public MissionBriefing getMissionBriefing(String briefingId) {
        return missionBriefings.get(briefingId);
    }
    
    /**
     * Get equipment loadout
     */
    public EquipmentLoadout getEquipmentLoadout(String loadoutId) {
        return equipmentLoadouts.get(loadoutId);
    }
    
    /**
     * Get soldier selection
     */
    public SoldierSelection getSoldierSelection(String selectionId) {
        return soldierSelections.get(selectionId);
    }
    
    /**
     * Get equipment maintenance
     */
    public EquipmentMaintenance getEquipmentMaintenance(String maintenanceId) {
        return equipmentMaintenance.get(maintenanceId);
    }
    
    /**
     * Get selected soldiers for mission
     */
    public List<String> getSelectedSoldiers(String missionId) {
        return selectedSoldiers.getOrDefault(missionId, new ArrayList<>());
    }
    
    /**
     * Get selected equipment for mission
     */
    public List<String> getSelectedEquipment(String missionId) {
        return selectedEquipment.getOrDefault(missionId, new ArrayList<>());
    }
    
    /**
     * Get preparation progress for mission
     */
    public int getPreparationProgress(String missionId) {
        return preparationProgress.getOrDefault(missionId, 0);
    }
    
    /**
     * Get equipment condition
     */
    public int getEquipmentCondition(String equipmentId) {
        return equipmentCondition.getOrDefault(equipmentId, 100);
    }
    
    /**
     * Get preparation complete status
     */
    public boolean getPreparationComplete(String missionId) {
        return preparationComplete.getOrDefault(missionId, false);
    }
    
    /**
     * Get mission objectives
     */
    public List<String> getMissionObjectives(String missionId) {
        return missionObjectives.getOrDefault(missionId, new ArrayList<>());
    }
    
    /**
     * Get soldier loadout
     */
    public Map<String, Integer> getSoldierLoadout(String soldierId) {
        return soldierLoadouts.getOrDefault(soldierId, new HashMap<>());
    }
    
    /**
     * Get total mission briefings
     */
    public int getTotalMissionBriefings() {
        return missionBriefings.size();
    }
    
    /**
     * Get total equipment loadouts
     */
    public int getTotalEquipmentLoadouts() {
        return equipmentLoadouts.size();
    }
    
    /**
     * Get total soldier selections
     */
    public int getTotalSoldierSelections() {
        return soldierSelections.size();
    }
    
    /**
     * Get total equipment maintenance
     */
    public int getTotalEquipmentMaintenance() {
        return equipmentMaintenance.size();
    }
    
    /**
     * Get total preparation bonuses
     */
    public int getTotalPreparationBonuses() {
        return preparationBonuses.size();
    }
    
    /**
     * Get total mission plans
     */
    public int getTotalMissionPlans() {
        return missionPlans.size();
    }
    
    /**
     * Get total preparation events
     */
    public int getTotalPreparationEvents() {
        return preparationEvents.size();
    }
    
    /**
     * Get total preparation progress
     */
    public int getTotalPreparationProgress() {
        return preparationProgress.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get total equipment condition
     */
    public int getTotalEquipmentCondition() {
        return equipmentCondition.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get total preparation complete
     */
    public long getTotalPreparationComplete() {
        return preparationComplete.values().stream().filter(complete -> complete).count();
    }
}


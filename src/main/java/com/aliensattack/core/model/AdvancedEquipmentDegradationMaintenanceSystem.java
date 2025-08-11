package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Equipment Degradation Maintenance System - XCOM 2 Tactical Combat
 * Implements comprehensive equipment degradation, maintenance, and repair mechanics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedEquipmentDegradationMaintenanceSystem {
    
    private String degradationSystemId;
    private Map<String, Equipment> equipmentMap;
    private Map<String, EquipmentType> equipmentTypes;
    private Map<String, MaintenanceFacility> maintenanceFacilities;
    private Map<String, MaintenancePlan> maintenancePlans;
    private Map<String, RepairOrder> repairOrders;
    private Map<String, EquipmentQuality> equipmentQualities;
    private Map<String, List<String>> equipmentHistory;
    private Map<String, Map<String, Integer>> degradationLevels;
    private Map<String, List<String>> damagedEquipment;
    private Map<String, Integer> repairTimes;
    private Map<String, Boolean> repairStates;
    private int totalDamagedEquipment;
    private int maxMaintenanceCapacity;
    private boolean isMaintenanceActive;
    
    public enum MaintenanceType {
        PREVENTIVE_MAINTENANCE, // Regular maintenance
        CORRECTIVE_MAINTENANCE, // Fix specific issues
        EMERGENCY_MAINTENANCE,  // Urgent repairs
        PREDICTIVE_MAINTENANCE, // Based on condition
        CONDITION_BASED_MAINTENANCE, // Based on monitoring
        RELIABILITY_CENTERED_MAINTENANCE, // Optimized approach
        TOTAL_PRODUCTIVE_MAINTENANCE, // Comprehensive approach
        ROUTINE_MAINTENANCE,    // Standard procedures
        UPGRADE_MAINTENANCE,    // Equipment upgrade
        CALIBRATION_MAINTENANCE, // Precision calibration
        SOFTWARE_UPDATE          // Software updates
    }
    
    public enum EquipmentStatus {
        OPERATIONAL,     // Equipment is working
        DAMAGED,         // Equipment is damaged
        BROKEN,          // Equipment is broken
        UNDER_MAINTENANCE, // Equipment is being maintained
        UNDER_REPAIR,    // Equipment is being repaired
        RETIRED,         // Equipment is retired
        LOST,            // Equipment is lost
        DESTROYED        // Equipment is destroyed
    }
    
    public enum EquipmentCondition {
        EXCELLENT,      // Perfect condition
        GOOD,           // Good condition
        FAIR,           // Fair condition
        POOR,           // Poor condition
        DAMAGED,        // Damaged condition
        BROKEN,         // Broken condition
        BEYOND_REPAIR,  // Cannot be repaired
        SCRAP           // Only useful for parts
    }
    
    public enum EquipmentIssue {
        WEAR_AND_TEAR,      // Normal wear
        CORROSION,          // Rust and corrosion
        ELECTRICAL_FAULT,   // Electrical problems
        MECHANICAL_FAULT,   // Mechanical problems
        SOFTWARE_BUG,       // Software issues
        CALIBRATION_ERROR,  // Calibration problems
        POWER_ISSUE,        // Power problems
        SENSOR_FAULT,       // Sensor problems
        COMMUNICATION_ERROR, // Communication problems
        STRUCTURAL_DAMAGE,  // Physical damage
        HEAT_DAMAGE,        // Heat-related damage
        WATER_DAMAGE,       // Water damage
        IMPACT_DAMAGE,      // Impact damage
        AGE_RELATED,        // Age-related issues
        MANUFACTURING_DEFECT // Manufacturing defects
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Equipment {
        private String equipmentId;
        private String equipmentName;
        private EquipmentType equipmentType;
        private int currentDurability;
        private int maxDurability;
        private int degradationRate;
        private int currentDegradation;
        private boolean isDamaged;
        private boolean isBroken;
        private boolean isRepairable;
        private String equipmentLocation;
        private String assignedUnit;
        private String assignedFacility;
        private String assignedTechnician;
        private boolean isUnderMaintenance;
        private boolean isUnderRepair;
        private String maintenanceStatus;
        private int maintenanceCost;
        private int repairCost;
        private String equipmentQuality;
        private String equipmentCondition;
        private String lastMaintenanceDate;
        private String nextMaintenanceDate;
        private String warrantyStatus;
        private boolean isWarrantied;
        private String warrantyExpiryDate;
        private String equipmentNotes;
        private Map<String, Integer> equipmentStats;
        private List<String> equipmentAbilities;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipmentType {
        private String equipmentTypeId;
        private String equipmentTypeName;
        private EquipmentCategory equipmentCategory;
        private int baseDurability;
        private int baseDegradationRate;
        private int baseMaintenanceCost;
        private int baseRepairCost;
        private Map<String, Integer> equipmentEffects;
        private List<String> possibleFailures;
        private String maintenanceMethod;
        private int maintenanceSuccessRate;
        private String preventionMethod;
        private boolean isMaintainable;
        private boolean isRepairable;
        private boolean isReplaceable;
        private String description;
        private int complexityLevel;
        private String riskFactors;
        private String failureModes;
        private String maintenanceSchedule;
        private String lifespan;
        
        public enum EquipmentCategory {
            WEAPON,             // Combat weapons
            ARMOR,              // Protective armor
            MEDICAL,            // Medical equipment
            TECHNICAL,          // Technical equipment
            COMMUNICATIONS,     // Communication devices
            TRANSPORT,          // Transportation equipment
            SENSORS,            // Sensor equipment
            POWER,              // Power equipment
            COMPUTER,           // Computer systems
            TOOL,               // Tools and instruments
            CONSUMABLE,         // Consumable items
            AMMUNITION,         // Ammunition and explosives
            GRENADE,            // Grenades and explosives
            MEDIKIT,            // Medical kits
            HACKING_DEVICE      // Hacking and technical devices
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaintenanceFacility {
        private String facilityId;
        private String facilityName;
        private FacilityType facilityType;
        private int capacity;
        private int currentOccupancy;
        private List<String> availableTechnicians;
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
        private List<String> equipment;
        private Map<String, Integer> repairSuccessRates;
        private List<String> specializations;
        private String facilityDescription;
        
        public enum FacilityType {
            WEAPON_WORKSHOP,    // Weapon maintenance
            ARMOR_WORKSHOP,     // Armor maintenance
            MEDICAL_LAB,        // Medical equipment
            TECHNICAL_LAB,      // Technical equipment
            COMMUNICATIONS_CENTER, // Communication equipment
            TRANSPORT_GARAGE,   // Vehicle maintenance
            SENSOR_LAB,         // Sensor equipment
            POWER_PLANT,        // Power equipment
            COMPUTER_CENTER,    // Computer systems
            TOOL_WORKSHOP,      // Tool maintenance
            AMMUNITION_FACTORY, // Ammunition production
            GRENADE_FACTORY,    // Explosive production
            MEDICAL_FACILITY,   // Medical equipment
            HACKING_LAB,        // Technical devices
            GENERAL_WORKSHOP    // General maintenance
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaintenancePlan {
        private String planId;
        private String equipmentId;
        private MaintenanceType maintenanceType;
        private int maintenanceDuration;
        private int currentDuration;
        private List<String> maintenanceTasks;
        private Map<String, Integer> maintenanceGoals;
        private List<String> maintenanceMilestones;
        private boolean isActive;
        private String assignedTechnician;
        private String assignedFacility;
        private int maintenanceProgress;
        private boolean isCompleted;
        private String completionDate;
        private String maintenanceStatus;
        private String maintenanceNotes;
        private Map<String, Integer> maintenanceBonuses;
        private List<String> maintenanceRequirements;
        private int maintenanceCost;
        private String maintenanceDescription;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RepairOrder {
        private String repairOrderId;
        private String equipmentId;
        private RepairType repairType;
        private int repairDuration;
        private int currentDuration;
        private List<String> repairTasks;
        private Map<String, Integer> repairGoals;
        private List<String> repairMilestones;
        private boolean isActive;
        private String assignedTechnician;
        private String assignedFacility;
        private int repairProgress;
        private boolean isCompleted;
        private String completionDate;
        private String repairStatus;
        private String repairNotes;
        private Map<String, Integer> repairBonuses;
        private List<String> repairRequirements;
        private int repairCost;
        private String repairDescription;
        private String priority;
        private boolean isUrgent;
        private String estimatedCompletion;
        private String actualCompletion;
        private String qualityCheck;
        private String warrantyCoverage;
        
        public enum RepairType {
            MINOR_REPAIR,       // Simple fixes
            MAJOR_REPAIR,       // Complex repairs
            EMERGENCY_REPAIR,   // Urgent fixes
            OVERHAUL,           // Complete rebuild
            UPGRADE,            // Equipment upgrade
            MODIFICATION,       // Equipment modification
            CALIBRATION,        // Precision adjustment
            REPLACEMENT         // Part replacement
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipmentQuality {
        private String qualityId;
        private String equipmentId;
        private QualityLevel qualityLevel;
        private int qualityScore;
        private Map<String, Integer> qualityMetrics;
        private List<String> qualityIssues;
        private boolean isInspected;
        private String inspectionDate;
        private String inspector;
        private String qualityStatus;
        private String qualityNotes;
        private Map<String, Integer> qualityBonuses;
        private List<String> qualityRequirements;
        private int qualityCost;
        private String qualityDescription;
        
        public enum QualityLevel {
            EXCELLENT,      // Perfect condition
            GOOD,           // Good condition
            FAIR,           // Fair condition
            POOR,           // Poor condition
            DAMAGED,        // Damaged condition
            BROKEN,         // Broken condition
            BEYOND_REPAIR,  // Cannot be repaired
            SCRAP           // Only useful for parts
        }
    }
    

    
    /**
     * Initialize the equipment degradation system
     */
    public void initializeSystem() {
        // ToDo: Реализовать систему деградации оборудования
        // - Equipment Degradation System
        // - Maintenance System
        // - Weapon Progression (полная реализация)
        // - Armor degradation
        // - Equipment repair mechanics
        // - Maintenance facilities
        // - Equipment quality system
        
        initializeEquipmentTypes();
        initializeMaintenanceFacilities();
    }
    
    /**
     * Initialize equipment types
     */
    private void initializeEquipmentTypes() {
        // Weapon type
        EquipmentType weapon = EquipmentType.builder()
            .equipmentTypeId("WEAPON")
            .equipmentTypeName("Combat Weapon")
            .equipmentCategory(EquipmentType.EquipmentCategory.WEAPON)
            .baseDurability(100)
            .baseDegradationRate(5)
            .baseMaintenanceCost(1000)
            .baseRepairCost(2000)
            .equipmentEffects(Map.of("damage", 25, "accuracy", 15, "range", 10))
            .possibleFailures(Arrays.asList("JAM", "MISFIRE", "BREAKAGE", "ACCURACY_LOSS"))
            .maintenanceMethod("CLEANING_CALIBRATION")
            .maintenanceSuccessRate(90)
            .preventionMethod("REGULAR_CLEANING")
            .isMaintainable(true)
            .isRepairable(true)
            .isReplaceable(true)
            .description("Combat weapon requiring regular maintenance")
            .complexityLevel(3)
            .riskFactors("Combat use, environmental exposure")
            .failureModes("Jamming, misfiring, barrel wear")
            .maintenanceSchedule("After each mission")
            .lifespan("1000 rounds")
            .build();
        
        equipmentTypes.put("WEAPON", weapon);
        
        // Armor type
        EquipmentType armor = EquipmentType.builder()
            .equipmentTypeId("ARMOR")
            .equipmentTypeName("Combat Armor")
            .equipmentCategory(EquipmentType.EquipmentCategory.ARMOR)
            .baseDurability(150)
            .baseDegradationRate(3)
            .baseMaintenanceCost(1500)
            .baseRepairCost(3000)
            .equipmentEffects(Map.of("defense", 30, "protection", 25, "mobility", -5))
            .possibleFailures(Arrays.asList("DAMAGE", "BREACH", "WEAR", "INTEGRITY_LOSS"))
            .maintenanceMethod("INSPECTION_REPAIR")
            .maintenanceSuccessRate(85)
            .preventionMethod("REGULAR_INSPECTION")
            .isMaintainable(true)
            .isRepairable(true)
            .isReplaceable(true)
            .description("Protective armor requiring regular inspection")
            .complexityLevel(4)
            .riskFactors("Combat damage, environmental wear")
            .failureModes("Breaching, material degradation")
            .maintenanceSchedule("After each mission")
            .lifespan("50 missions")
            .build();
        
        equipmentTypes.put("ARMOR", armor);
        
        // Medical equipment type
        EquipmentType medical = EquipmentType.builder()
            .equipmentTypeId("MEDICAL")
            .equipmentTypeName("Medical Equipment")
            .equipmentCategory(EquipmentType.EquipmentCategory.MEDICAL)
            .baseDurability(80)
            .baseDegradationRate(2)
            .baseMaintenanceCost(800)
            .baseRepairCost(1500)
            .equipmentEffects(Map.of("healing", 20, "stabilization", 15, "diagnosis", 10))
            .possibleFailures(Arrays.asList("CONTAMINATION", "CALIBRATION_LOSS", "BATTERY_FAILURE"))
            .maintenanceMethod("STERILIZATION_CALIBRATION")
            .maintenanceSuccessRate(95)
            .preventionMethod("REGULAR_STERILIZATION")
            .isMaintainable(true)
            .isRepairable(true)
            .isReplaceable(true)
            .description("Medical equipment requiring sterilization")
            .complexityLevel(2)
            .riskFactors("Contamination, battery life")
            .failureModes("Contamination, calibration drift")
            .maintenanceSchedule("Before each use")
            .lifespan("100 uses")
            .build();
        
        equipmentTypes.put("MEDICAL", medical);
    }
    
    /**
     * Initialize maintenance facilities
     */
    private void initializeMaintenanceFacilities() {
        // Weapon Workshop
        MaintenanceFacility weaponWorkshop = MaintenanceFacility.builder()
            .facilityId("WEAPON_WORKSHOP")
            .facilityName("Weapon Workshop")
            .facilityType(MaintenanceFacility.FacilityType.WEAPON_WORKSHOP)
            .capacity(20)
            .currentOccupancy(0)
            .availableTechnicians(Arrays.asList("TECH_SMITH", "TECH_JOHNSON", "TECH_WILLIAMS"))
            .availableEquipment(Arrays.asList("CLEANING_KIT", "CALIBRATION_TOOL", "TESTING_RANGE"))
            .facilityBonuses(Map.of("weapon_maintenance", 25, "weapon_repair", 30))
            .facilityServices(Arrays.asList("CLEANING", "CALIBRATION", "REPAIR", "UPGRADE"))
            .isOperational(true)
            .facilityLevel(3)
            .facilityQuality(85)
            .location("MAIN_BASE")
            .operatingCost(3000)
            .maintenanceCost(500)
            .facilityStatus("OPERATIONAL")
            .equipment(new ArrayList<>())
            .repairSuccessRates(Map.of("CLEANING", 95, "CALIBRATION", 90, "REPAIR", 85))
            .specializations(Arrays.asList("RIFLES", "PISTOLS", "HEAVY_WEAPONS", "EXPLOSIVES"))
            .facilityDescription("Specialized weapon maintenance facility")
            .build();
        
        maintenanceFacilities.put("WEAPON_WORKSHOP", weaponWorkshop);
        
        // Armor Workshop
        MaintenanceFacility armorWorkshop = MaintenanceFacility.builder()
            .facilityId("ARMOR_WORKSHOP")
            .facilityName("Armor Workshop")
            .facilityType(MaintenanceFacility.FacilityType.ARMOR_WORKSHOP)
            .capacity(15)
            .currentOccupancy(0)
            .availableTechnicians(Arrays.asList("TECH_BROWN", "TECH_DAVIS", "TECH_MILLER"))
            .availableEquipment(Arrays.asList("INSPECTION_TOOL", "REPAIR_KIT", "MATERIALS"))
            .facilityBonuses(Map.of("armor_maintenance", 25, "armor_repair", 30))
            .facilityServices(Arrays.asList("INSPECTION", "REPAIR", "REINFORCEMENT", "UPGRADE"))
            .isOperational(true)
            .facilityLevel(3)
            .facilityQuality(80)
            .location("MAIN_BASE")
            .operatingCost(2500)
            .maintenanceCost(400)
            .facilityStatus("OPERATIONAL")
            .equipment(new ArrayList<>())
            .repairSuccessRates(Map.of("INSPECTION", 90, "REPAIR", 85, "REINFORCEMENT", 80))
            .specializations(Arrays.asList("BODY_ARMOR", "HELMETS", "SHIELDS", "PROTECTIVE_GEAR"))
            .facilityDescription("Specialized armor maintenance facility")
            .build();
        
        maintenanceFacilities.put("ARMOR_WORKSHOP", armorWorkshop);
    }
    
    /**
     * Register equipment
     */
    public boolean registerEquipment(String equipmentId, String equipmentTypeId, String assignedUnit) {
        EquipmentType equipmentType = equipmentTypes.get(equipmentTypeId);
        if (equipmentType == null) {
            return false;
        }
        
        Equipment equipment = Equipment.builder()
            .equipmentId(equipmentId)
            .equipmentName(equipmentType.getEquipmentTypeName())
            .equipmentType(equipmentType)
            .currentDurability(equipmentType.getBaseDurability())
            .maxDurability(equipmentType.getBaseDurability())
            .degradationRate(equipmentType.getBaseDegradationRate())
            .currentDegradation(0)
            .isDamaged(false)
            .isBroken(false)
            .isRepairable(equipmentType.isRepairable())
            .equipmentLocation("INVENTORY")
            .assignedUnit(assignedUnit)
            .assignedFacility("")
            .assignedTechnician("")
            .isUnderMaintenance(false)
            .isUnderRepair(false)
            .maintenanceStatus("OPERATIONAL")
            .maintenanceCost(equipmentType.getBaseMaintenanceCost())
            .repairCost(equipmentType.getBaseRepairCost())
            .equipmentQuality("EXCELLENT")
            .equipmentCondition("NEW")
            .lastMaintenanceDate("")
            .nextMaintenanceDate("")
            .warrantyStatus("ACTIVE")
            .isWarrantied(true)
            .warrantyExpiryDate("")
            .equipmentNotes("")
            .equipmentStats(new HashMap<>(equipmentType.getEquipmentEffects()))
            .equipmentAbilities(new ArrayList<>())
            .manufacturer("XCOM")
            .modelNumber("MODEL_001")
            .serialNumber("SN_" + equipmentId)
            .purchaseDate(new Date().toString())
            .purchaseCost(equipmentType.getBaseMaintenanceCost())
            .supplier("XCOM_SUPPLY")
            .location("MAIN_BASE")
            .status("ACTIVE")
            .build();
        
        equipmentMap.put(equipmentId, equipment);
        equipmentHistory.put(equipmentId, new ArrayList<>());
        degradationLevels.put(equipmentId, new HashMap<>());
        degradationLevels.get(equipmentId).put(equipmentTypeId, 0);
        
        // Create equipment quality
        createEquipmentQuality(equipmentId);
        
        return true;
    }
    
    /**
     * Create equipment quality
     */
    private void createEquipmentQuality(String equipmentId) {
        String qualityId = "QUALITY_" + equipmentId;
        
        EquipmentQuality quality = EquipmentQuality.builder()
            .qualityId(qualityId)
            .equipmentId(equipmentId)
            .qualityLevel(EquipmentQuality.QualityLevel.EXCELLENT)
            .qualityScore(100)
            .qualityMetrics(new HashMap<>())
            .qualityIssues(new ArrayList<>())
            .isInspected(false)
            .inspectionDate("")
            .inspector("")
            .qualityStatus("EXCELLENT")
            .qualityNotes("")
            .qualityBonuses(new HashMap<>())
            .qualityRequirements(new ArrayList<>())
            .qualityCost(0)
            .qualityDescription("New equipment in excellent condition")
            .build();
        
        equipmentQualities.put(qualityId, quality);
    }
    
    /**
     * Apply degradation to equipment
     */
    public boolean applyDegradation(String equipmentId, int degradationAmount) {
        Equipment equipment = this.equipmentMap.get(equipmentId);
        if (equipment == null) {
            return false;
        }
        
        equipment.setCurrentDegradation(equipment.getCurrentDegradation() + degradationAmount);
        equipment.setCurrentDurability(Math.max(0, equipment.getCurrentDurability() - degradationAmount));
        
        // Check if equipment is damaged
        if (equipment.getCurrentDurability() <= equipment.getMaxDurability() * 0.5) {
            equipment.setDamaged(true);
        }
        
        // Check if equipment is broken
        if (equipment.getCurrentDurability() <= 0) {
            equipment.setBroken(true);
            equipment.setDamaged(true);
            totalDamagedEquipment++;
        }
        
        // Update equipment quality
        updateEquipmentQuality(equipmentId);
        
        return true;
    }
    
    /**
     * Update equipment quality
     */
    private void updateEquipmentQuality(String equipmentId) {
        Equipment equipment = this.equipmentMap.get(equipmentId);
        if (equipment == null) {
            return;
        }
        
        EquipmentQuality quality = equipmentQualities.get("QUALITY_" + equipmentId);
        if (quality == null) {
            return;
        }
        
        int durabilityPercentage = (equipment.getCurrentDurability() * 100) / equipment.getMaxDurability();
        
        if (durabilityPercentage >= 90) {
            quality.setQualityLevel(EquipmentQuality.QualityLevel.EXCELLENT);
            quality.setQualityStatus("EXCELLENT");
        } else if (durabilityPercentage >= 75) {
            quality.setQualityLevel(EquipmentQuality.QualityLevel.GOOD);
            quality.setQualityStatus("GOOD");
        } else if (durabilityPercentage >= 50) {
            quality.setQualityLevel(EquipmentQuality.QualityLevel.FAIR);
            quality.setQualityStatus("FAIR");
        } else if (durabilityPercentage >= 25) {
            quality.setQualityLevel(EquipmentQuality.QualityLevel.POOR);
            quality.setQualityStatus("POOR");
        } else if (durabilityPercentage >= 10) {
            quality.setQualityLevel(EquipmentQuality.QualityLevel.DAMAGED);
            quality.setQualityStatus("DAMAGED");
        } else if (durabilityPercentage > 0) {
            quality.setQualityLevel(EquipmentQuality.QualityLevel.BROKEN);
            quality.setQualityStatus("BROKEN");
        } else {
            quality.setQualityLevel(EquipmentQuality.QualityLevel.BEYOND_REPAIR);
            quality.setQualityStatus("BEYOND_REPAIR");
        }
        
        quality.setQualityScore(durabilityPercentage);
    }
    
    /**
     * Start maintenance on equipment
     */
    public boolean startMaintenance(String equipmentId, String facilityId, String technicianId) {
        Equipment equipment = this.equipmentMap.get(equipmentId);
        if (equipment == null) {
            return false;
        }
        
        equipment.setAssignedFacility(facilityId);
        equipment.setAssignedTechnician(technicianId);
        equipment.setUnderMaintenance(true); // Fixed method name
        equipment.setMaintenanceStatus("UNDER_MAINTENANCE");

        // Create maintenance plan
        MaintenancePlan plan = MaintenancePlan.builder()
            .planId("MAINTENANCE_" + equipmentId)
            .equipmentId(equipmentId)
                            .maintenanceType(MaintenanceType.PREVENTIVE_MAINTENANCE)
            .maintenanceDuration(3)
            .currentDuration(0)
            .maintenanceTasks(new ArrayList<>())
            .maintenanceGoals(new HashMap<>())
            .maintenanceMilestones(new ArrayList<>())
            .isActive(true)
            .assignedTechnician(technicianId)
            .assignedFacility(facilityId)
            .maintenanceProgress(0)
            .isCompleted(false)
            .completionDate("")
            .maintenanceStatus("ACTIVE")
            .maintenanceNotes("")
            .maintenanceBonuses(new HashMap<>())
            .maintenanceRequirements(new ArrayList<>())
            .maintenanceCost(equipment.getMaintenanceCost())
            .maintenanceDescription("Preventive maintenance")
            .build();
        
        maintenancePlans.put(plan.getPlanId(), plan);
        
        return true;
    }
    
    /**
     * Process maintenance for all equipment
     */
    public void processMaintenance() {
        for (MaintenancePlan plan : maintenancePlans.values()) {
            if (plan.isActive() && !plan.isCompleted()) {
                processMaintenancePlan(plan);
            }
        }
    }
    
    /**
     * Process maintenance plan
     */
    private void processMaintenancePlan(MaintenancePlan plan) {
        plan.setCurrentDuration(plan.getCurrentDuration() + 1);
        
        // Check if maintenance is complete
        if (plan.getCurrentDuration() >= plan.getMaintenanceDuration()) {
            completeMaintenance(plan);
        } else {
            // Update maintenance progress
            int progress = (plan.getCurrentDuration() * 100) / plan.getMaintenanceDuration();
            plan.setMaintenanceProgress(progress);
            plan.setMaintenanceStatus("IN_PROGRESS (" + progress + "%)");
        }
    }
    
    /**
     * Complete maintenance
     */
    private void completeMaintenance(MaintenancePlan plan) {
        Equipment equipment = this.equipmentMap.get(plan.getEquipmentId());
        if (equipment == null) {
            return;
        }
        
        // Restore durability
        int restorationAmount = equipment.getMaxDurability() * 20 / 100; // Restore 20%
        equipment.setCurrentDurability(Math.min(equipment.getMaxDurability(), 
            equipment.getCurrentDurability() + restorationAmount));
        
        // Reset degradation
        equipment.setCurrentDegradation(Math.max(0, equipment.getCurrentDegradation() - 10));
        
        // Update status
        equipment.setUnderMaintenance(false);
        equipment.setMaintenanceStatus("MAINTENANCE_COMPLETED");
        equipment.setLastMaintenanceDate(new Date().toString());
        
        // Update quality
        updateEquipmentQuality(plan.getEquipmentId());
        
        // Complete plan
        plan.setActive(false);
        plan.setCompleted(true);
        plan.setCompletionDate(new Date().toString());
        plan.setMaintenanceStatus("COMPLETED");
    }
    
    /**
     * Get equipment by ID
     */
    public Equipment getEquipment(String equipmentId) {
        return equipmentMap.get(equipmentId);
    }
    
    /**
     * Get equipment quality
     */
    public EquipmentQuality getEquipmentQuality(String equipmentId) {
        return equipmentQualities.get("QUALITY_" + equipmentId);
    }
    
    /**
     * Get maintenance facility by ID
     */
    public MaintenanceFacility getMaintenanceFacility(String facilityId) {
        return maintenanceFacilities.get(facilityId);
    }
    
    /**
     * Get maintenance plan by ID
     */
    public MaintenancePlan getMaintenancePlan(String planId) {
        return maintenancePlans.get(planId);
    }
    
    /**
     * Get total damaged equipment
     */
    public int getTotalDamagedEquipment() {
        return totalDamagedEquipment;
    }
    
    /**
     * Check if equipment is damaged
     */
    public boolean isEquipmentDamaged(String equipmentId) {
        Equipment equipment = this.equipmentMap.get(equipmentId);
        return equipment != null && equipment.isDamaged();
    }
    
    /**
     * Check if equipment is broken
     */
    public boolean isEquipmentBroken(String equipmentId) {
        Equipment equipment = this.equipmentMap.get(equipmentId);
        return equipment != null && equipment.isBroken();
    }
}

package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;
import java.time.LocalDateTime;

/**
 * Advanced Equipment Degradation and Maintenance System
 * Handles equipment wear, maintenance requirements, and performance degradation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedEquipmentDegradationMaintenanceSystem {
    
    // Equipment Management
    private Map<String, Equipment> equipmentRegistry;
    private Map<String, EquipmentStatus> equipmentStatuses;
    private Map<String, MaintenanceSchedule> maintenanceSchedules;
    private Map<String, List<MaintenanceRecord>> maintenanceHistory;
    
    // Degradation Tracking
    private Map<String, Integer> equipmentDurability;
    private Map<String, Double> performanceMultipliers;
    private Map<String, LocalDateTime> lastMaintenanceDates;
    private Map<String, LocalDateTime> nextMaintenanceDates;
    
    // Maintenance Facilities
    private List<MaintenanceFacility> maintenanceFacilities;
    private Map<String, MaintenanceFacility> assignedFacilities;
    private int totalMaintenanceCapacity;
    private int availableMaintenanceCapacity;
    
    // Equipment Types
    private List<EquipmentType> equipmentTypes;
    private Map<String, EquipmentSpecification> equipmentSpecs;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Equipment {
        private String equipmentId;
        private String name;
        private EquipmentType type;
        private EquipmentCategory category;
        private int maxDurability;
        private int currentDurability;
        private double performanceMultiplier;
        private LocalDateTime manufactureDate;
        private LocalDateTime lastMaintenanceDate;
        private LocalDateTime nextMaintenanceDate;
        private MaintenanceLevel maintenanceLevel;
        private List<EquipmentModification> modifications;
        private EquipmentCondition condition;
    }
    
    public enum EquipmentType {
        WEAPON, ARMOR, MEDICAL_EQUIPMENT, PSYCHIC_AMPLIFIER, GRENADE_LAUNCHER,
        HACKING_DEVICE, STEALTH_EQUIPMENT, ENVIRONMENTAL_SUIT, COMMUNICATION_DEVICE,
        SENSOR_EQUIPMENT, TRANSPORT_EQUIPMENT, EXPERIMENTAL_EQUIPMENT
    }
    
    public enum EquipmentCategory {
        COMBAT_EQUIPMENT, SUPPORT_EQUIPMENT, SPECIALIZED_EQUIPMENT, 
        EXPERIMENTAL_EQUIPMENT, ALIEN_TECHNOLOGY
    }
    
    public enum MaintenanceLevel {
        NONE(0, 1.0), BASIC(1, 0.9), STANDARD(2, 0.8), ADVANCED(3, 0.7), 
        EXPERT(4, 0.6), EXPERIMENTAL(5, 0.5);
        
        private final int level;
        private final double performancePenalty;
        
        MaintenanceLevel(int level, double performancePenalty) {
            this.level = level;
            this.performancePenalty = performancePenalty;
        }
        
        public int getLevel() { return level; }
        public double getPerformancePenalty() { return performancePenalty; }
    }
    
    public enum EquipmentCondition {
        EXCELLENT(1.0), GOOD(0.9), FAIR(0.8), POOR(0.7), CRITICAL(0.6), BROKEN(0.0);
        
        private final double performanceMultiplier;
        
        EquipmentCondition(double performanceMultiplier) {
            this.performanceMultiplier = performanceMultiplier;
        }
        
        public double getPerformanceMultiplier() { return performanceMultiplier; }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipmentStatus {
        private String equipmentId;
        private EquipmentCondition condition;
        private double performanceMultiplier;
        private boolean isOperational;
        private boolean needsMaintenance;
        private boolean isBroken;
        private List<EquipmentIssue> issues;
        private LocalDateTime lastInspectionDate;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipmentIssue {
        private String issueId;
        private IssueType type;
        private int severity;
        private String description;
        private LocalDateTime detectedDate;
        private boolean isResolved;
    }
    
    public enum IssueType {
        WEAR_AND_TEAR, DAMAGE, CORROSION, ELECTRICAL_FAULT, MECHANICAL_FAILURE,
        SOFTWARE_BUG, CALIBRATION_ERROR, POWER_ISSUE, SENSOR_MALFUNCTION
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaintenanceSchedule {
        private String equipmentId;
        private MaintenanceType type;
        private int intervalDays;
        private LocalDateTime lastMaintenanceDate;
        private LocalDateTime nextMaintenanceDate;
        private boolean isActive;
        private int priority;
    }
    
    public enum MaintenanceType {
        PREVENTIVE_MAINTENANCE, CORRECTIVE_MAINTENANCE, EMERGENCY_MAINTENANCE,
        UPGRADE_MAINTENANCE, CALIBRATION_MAINTENANCE, SOFTWARE_UPDATE
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaintenanceRecord {
        private String recordId;
        private String equipmentId;
        private MaintenanceType type;
        private LocalDateTime maintenanceDate;
        private int cost;
        private String description;
        private boolean wasSuccessful;
        private List<String> partsReplaced;
        private int technicianSkill;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MaintenanceFacility {
        private String facilityId;
        private String name;
        private FacilityType type;
        private int capacity;
        private int currentWorkload;
        private List<MaintenanceType> availableServices;
        private int effectiveness;
        private int cost;
        private int technicianSkill;
    }
    
    public enum FacilityType {
        BASIC_WORKSHOP, ADVANCED_WORKSHOP, SPECIALIZED_LAB, EXPERIMENTAL_LAB,
        ALIEN_TECHNOLOGY_LAB, EMERGENCY_REPAIR_STATION, CALIBRATION_CENTER
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipmentSpecification {
        private String specId;
        private EquipmentType type;
        private int baseDurability;
        private int maintenanceInterval;
        private double degradationRate;
        private List<MaintenanceType> requiredMaintenance;
        private int complexity;
        private int cost;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquipmentModification {
        private String modificationId;
        private String name;
        private ModificationType type;
        private int cost;
        private boolean isInstalled;
        private LocalDateTime installationDate;
        private List<String> effects;
    }
    
    public enum ModificationType {
        PERFORMANCE_UPGRADE, DURABILITY_UPGRADE, MAINTENANCE_REDUCTION,
        SPECIALIZED_FUNCTION, EXPERIMENTAL_FEATURE
    }
    
    // Core Equipment Management Methods
    
    public boolean registerEquipment(String equipmentId, String name, EquipmentType type, 
                                   EquipmentCategory category, int maxDurability) {
        if (equipmentId == null || name == null || type == null) {
            return false;
        }
        
        Equipment equipment = Equipment.builder()
            .equipmentId(equipmentId)
            .name(name)
            .type(type)
            .category(category)
            .maxDurability(maxDurability)
            .currentDurability(maxDurability)
            .performanceMultiplier(1.0)
            .manufactureDate(LocalDateTime.now())
            .maintenanceLevel(MaintenanceLevel.NONE)
            .modifications(new ArrayList<>())
            .condition(EquipmentCondition.EXCELLENT)
            .build();
        
        equipmentRegistry.put(equipmentId, equipment);
        
        // Initialize status
        EquipmentStatus status = EquipmentStatus.builder()
            .equipmentId(equipmentId)
            .condition(EquipmentCondition.EXCELLENT)
            .performanceMultiplier(1.0)
            .isOperational(true)
            .needsMaintenance(false)
            .isBroken(false)
            .issues(new ArrayList<>())
            .build();
        
        equipmentStatuses.put(equipmentId, status);
        
        // Initialize durability tracking
        equipmentDurability.put(equipmentId, maxDurability);
        performanceMultipliers.put(equipmentId, 1.0);
        
        return true;
    }
    
    public boolean useEquipment(String equipmentId, int usageIntensity) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null || !isEquipmentOperational(equipmentId)) {
            return false;
        }
        
        // Calculate degradation
        int degradation = calculateDegradation(equipment, usageIntensity);
        equipment.setCurrentDurability(equipment.getCurrentDurability() - degradation);
        
        // Update performance multiplier
        updatePerformanceMultiplier(equipmentId);
        
        // Check for issues
        checkForIssues(equipmentId, usageIntensity);
        
        // Update maintenance schedule
        updateMaintenanceSchedule(equipmentId);
        
        return true;
    }
    
    public boolean performMaintenance(String equipmentId, MaintenanceType maintenanceType) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null) {
            return false;
        }
        
        // Calculate maintenance effectiveness
        int effectiveness = calculateMaintenanceEffectiveness(equipmentId, maintenanceType);
        
        // Apply maintenance
        boolean success = applyMaintenance(equipmentId, maintenanceType, effectiveness);
        
        // Record maintenance
        if (success) {
            recordMaintenance(equipmentId, maintenanceType, effectiveness);
        }
        
        return success;
    }
    
    public boolean assignMaintenanceFacility(String equipmentId, String facilityId) {
        MaintenanceFacility facility = findMaintenanceFacility(facilityId);
        if (facility == null || facility.getCurrentWorkload() >= facility.getCapacity()) {
            return false;
        }
        
        assignedFacilities.put(equipmentId, facility);
        facility.setCurrentWorkload(facility.getCurrentWorkload() + 1);
        
        return true;
    }
    
    public boolean scheduleMaintenance(String equipmentId, MaintenanceType maintenanceType, 
                                     int intervalDays) {
        if (equipmentId == null || maintenanceType == null) {
            return false;
        }
        
        MaintenanceSchedule schedule = MaintenanceSchedule.builder()
            .equipmentId(equipmentId)
            .type(maintenanceType)
            .intervalDays(intervalDays)
            .lastMaintenanceDate(LocalDateTime.now())
            .nextMaintenanceDate(LocalDateTime.now().plusDays(intervalDays))
            .isActive(true)
            .priority(calculateMaintenancePriority(equipmentId))
            .build();
        
        maintenanceSchedules.put(equipmentId, schedule);
        
        return true;
    }
    
    public boolean upgradeEquipment(String equipmentId, String modificationId) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null) {
            return false;
        }
        
        EquipmentModification modification = findEquipmentModification(modificationId);
        if (modification == null) {
            return false;
        }
        
        // Check if modification is compatible
        if (!isModificationCompatible(equipment, modification)) {
            return false;
        }
        
        // Install modification
        modification.setInstalled(true);
        modification.setInstallationDate(LocalDateTime.now());
        equipment.getModifications().add(modification);
        
        // Apply modification effects
        applyModificationEffects(equipmentId, modification);
        
        return true;
    }
    
    public boolean repairEquipment(String equipmentId, int repairAmount) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null) {
            return false;
        }
        
        int newDurability = Math.min(equipment.getMaxDurability(), 
                                   equipment.getCurrentDurability() + repairAmount);
        equipment.setCurrentDurability(newDurability);
        
        // Update condition
        updateEquipmentCondition(equipmentId);
        
        // Update performance multiplier
        updatePerformanceMultiplier(equipmentId);
        
        return true;
    }
    
    public boolean checkEquipmentStatus(String equipmentId) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null) {
            return false;
        }
        
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        if (status == null) {
            return false;
        }
        
        // Update condition based on durability
        updateEquipmentCondition(equipmentId);
        
        // Check for maintenance needs
        checkMaintenanceNeeds(equipmentId);
        
        // Check for critical issues
        checkCriticalIssues(equipmentId);
        
        return true;
    }
    
    public boolean isEquipmentOperational(String equipmentId) {
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        return status != null && status.isOperational() && !status.isBroken();
    }
    
    public boolean needsMaintenance(String equipmentId) {
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        return status != null && status.isNeedsMaintenance();
    }
    
    public double getPerformanceMultiplier(String equipmentId) {
        return performanceMultipliers.getOrDefault(equipmentId, 1.0);
    }
    
    public int getDurability(String equipmentId) {
        return equipmentDurability.getOrDefault(equipmentId, 0);
    }
    
    public EquipmentCondition getEquipmentCondition(String equipmentId) {
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        return status != null ? status.getCondition() : EquipmentCondition.BROKEN;
    }
    
    // Helper Methods
    
    private int calculateDegradation(Equipment equipment, int usageIntensity) {
        int baseDegradation = usageIntensity / 10;
        
        // Modify based on equipment type
        switch (equipment.getType()) {
            case WEAPON:
                return baseDegradation * 2; // Weapons degrade faster
            case ARMOR:
                return baseDegradation * 1; // Armor degrades moderately
            case MEDICAL_EQUIPMENT:
                return baseDegradation * 1; // Medical equipment degrades moderately
            case PSYCHIC_AMPLIFIER:
                return baseDegradation * 3; // Psychic equipment degrades very fast
            case GRENADE_LAUNCHER:
                return baseDegradation * 2; // Explosive equipment degrades fast
            case HACKING_DEVICE:
                return baseDegradation * 1; // Electronic equipment degrades moderately
            case STEALTH_EQUIPMENT:
                return baseDegradation * 2; // Stealth equipment degrades fast
            case ENVIRONMENTAL_SUIT:
                return baseDegradation * 1; // Environmental equipment degrades moderately
            case COMMUNICATION_DEVICE:
                return baseDegradation * 1; // Communication equipment degrades moderately
            case SENSOR_EQUIPMENT:
                return baseDegradation * 1; // Sensor equipment degrades moderately
            case TRANSPORT_EQUIPMENT:
                return baseDegradation * 2; // Transport equipment degrades fast
            case EXPERIMENTAL_EQUIPMENT:
                return baseDegradation * 4; // Experimental equipment degrades very fast
            default:
                return baseDegradation;
        }
    }
    
    private void updatePerformanceMultiplier(String equipmentId) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null) {
            return;
        }
        
        double durabilityRatio = (double) equipment.getCurrentDurability() / equipment.getMaxDurability();
        double conditionMultiplier = equipment.getCondition().getPerformanceMultiplier();
        double maintenanceMultiplier = 1.0 - equipment.getMaintenanceLevel().getPerformancePenalty();
        
        double performanceMultiplier = durabilityRatio * conditionMultiplier * maintenanceMultiplier;
        equipment.setPerformanceMultiplier(performanceMultiplier);
        performanceMultipliers.put(equipmentId, performanceMultiplier);
        
        // Update status
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        if (status != null) {
            status.setPerformanceMultiplier(performanceMultiplier);
        }
    }
    
    private void checkForIssues(String equipmentId, int usageIntensity) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null) {
            return;
        }
        
        double issueChance = calculateIssueChance(equipment, usageIntensity);
        if (Math.random() < issueChance) {
            createEquipmentIssue(equipmentId);
        }
    }
    
    private double calculateIssueChance(Equipment equipment, int usageIntensity) {
        double baseChance = usageIntensity / 100.0;
        double durabilityFactor = 1.0 - ((double) equipment.getCurrentDurability() / equipment.getMaxDurability());
        double maintenanceFactor = equipment.getMaintenanceLevel().getLevel() * 0.1;
        
        return baseChance * (1.0 + durabilityFactor + maintenanceFactor);
    }
    
    private void createEquipmentIssue(String equipmentId) {
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        if (status == null) {
            return;
        }
        
        IssueType[] issueTypes = IssueType.values();
        IssueType randomIssue = issueTypes[(int) (Math.random() * issueTypes.length)];
        
        EquipmentIssue issue = EquipmentIssue.builder()
            .issueId(UUID.randomUUID().toString())
            .type(randomIssue)
            .severity((int) (Math.random() * 5) + 1)
            .description("Equipment issue: " + randomIssue.name())
            .detectedDate(LocalDateTime.now())
            .isResolved(false)
            .build();
        
        status.getIssues().add(issue);
        
        // Update operational status
        updateOperationalStatus(equipmentId);
    }
    
    private void updateOperationalStatus(String equipmentId) {
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        if (status == null) {
            return;
        }
        
        // Check if equipment is broken
        boolean isBroken = status.getIssues().stream()
            .anyMatch(issue -> issue.getSeverity() >= 5 && !issue.isResolved());
        
        status.setBroken(isBroken);
        status.setOperational(!isBroken);
    }
    
    private void updateMaintenanceSchedule(String equipmentId) {
        MaintenanceSchedule schedule = maintenanceSchedules.get(equipmentId);
        if (schedule == null) {
            return;
        }
        
        // Check if maintenance is due
        if (LocalDateTime.now().isAfter(schedule.getNextMaintenanceDate())) {
            EquipmentStatus status = equipmentStatuses.get(equipmentId);
            if (status != null) {
                status.setNeedsMaintenance(true);
            }
        }
    }
    
    private int calculateMaintenanceEffectiveness(String equipmentId, MaintenanceType maintenanceType) {
        MaintenanceFacility facility = assignedFacilities.get(equipmentId);
        if (facility == null) {
            return 50; // Default effectiveness
        }
        
        int baseEffectiveness = facility.getEffectiveness();
        int technicianSkill = facility.getTechnicianSkill();
        
        return Math.min(100, baseEffectiveness + technicianSkill);
    }
    
    private boolean applyMaintenance(String equipmentId, MaintenanceType maintenanceType, int effectiveness) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null) {
            return false;
        }
        
        // Calculate repair amount based on effectiveness
        int maxRepair = equipment.getMaxDurability() - equipment.getCurrentDurability();
        int repairAmount = (int) (maxRepair * (effectiveness / 100.0));
        
        // Apply repair
        equipment.setCurrentDurability(Math.min(equipment.getMaxDurability(), 
                                              equipment.getCurrentDurability() + repairAmount));
        
        // Update condition
        updateEquipmentCondition(equipmentId);
        
        // Update performance multiplier
        updatePerformanceMultiplier(equipmentId);
        
        // Resolve issues
        resolveIssues(equipmentId, effectiveness);
        
        return true;
    }
    
    private void recordMaintenance(String equipmentId, MaintenanceType maintenanceType, int effectiveness) {
        MaintenanceRecord record = MaintenanceRecord.builder()
            .recordId(UUID.randomUUID().toString())
            .equipmentId(equipmentId)
            .type(maintenanceType)
            .maintenanceDate(LocalDateTime.now())
            .cost(calculateMaintenanceCost(maintenanceType))
            .description("Maintenance performed: " + maintenanceType.name())
            .wasSuccessful(effectiveness >= 70)
            .partsReplaced(new ArrayList<>())
            .technicianSkill(effectiveness)
            .build();
        
        maintenanceHistory.computeIfAbsent(equipmentId, k -> new ArrayList<>()).add(record);
    }
    
    private int calculateMaintenanceCost(MaintenanceType maintenanceType) {
        switch (maintenanceType) {
            case PREVENTIVE_MAINTENANCE: return 50;
            case CORRECTIVE_MAINTENANCE: return 100;
            case EMERGENCY_MAINTENANCE: return 200;
            case UPGRADE_MAINTENANCE: return 150;
            case CALIBRATION_MAINTENANCE: return 75;
            case SOFTWARE_UPDATE: return 25;
            default: return 100;
        }
    }
    
    private int calculateMaintenancePriority(String equipmentId) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null) {
            return 1;
        }
        
        // Higher priority for critical equipment
        switch (equipment.getType()) {
            case WEAPON:
            case ARMOR:
            case MEDICAL_EQUIPMENT:
                return 3;
            case PSYCHIC_AMPLIFIER:
            case HACKING_DEVICE:
                return 2;
            default:
                return 1;
        }
    }
    
    private EquipmentModification findEquipmentModification(String modificationId) {
        // Find equipment modification
        return null; // Placeholder
    }
    
    private boolean isModificationCompatible(Equipment equipment, EquipmentModification modification) {
        // Check if modification is compatible with equipment
        return true; // Placeholder
    }
    
    private void applyModificationEffects(String equipmentId, EquipmentModification modification) {
        // Apply modification effects to equipment
        // This would modify equipment performance or capabilities
    }
    
    private void updateEquipmentCondition(String equipmentId) {
        Equipment equipment = equipmentRegistry.get(equipmentId);
        if (equipment == null) {
            return;
        }
        
        double durabilityRatio = (double) equipment.getCurrentDurability() / equipment.getMaxDurability();
        
        EquipmentCondition newCondition;
        if (durabilityRatio >= 0.9) {
            newCondition = EquipmentCondition.EXCELLENT;
        } else if (durabilityRatio >= 0.7) {
            newCondition = EquipmentCondition.GOOD;
        } else if (durabilityRatio >= 0.5) {
            newCondition = EquipmentCondition.FAIR;
        } else if (durabilityRatio >= 0.3) {
            newCondition = EquipmentCondition.POOR;
        } else if (durabilityRatio >= 0.1) {
            newCondition = EquipmentCondition.CRITICAL;
        } else {
            newCondition = EquipmentCondition.BROKEN;
        }
        
        equipment.setCondition(newCondition);
        
        // Update status
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        if (status != null) {
            status.setCondition(newCondition);
        }
    }
    
    private void checkMaintenanceNeeds(String equipmentId) {
        MaintenanceSchedule schedule = maintenanceSchedules.get(equipmentId);
        if (schedule == null) {
            return;
        }
        
        if (LocalDateTime.now().isAfter(schedule.getNextMaintenanceDate())) {
            EquipmentStatus status = equipmentStatuses.get(equipmentId);
            if (status != null) {
                status.setNeedsMaintenance(true);
            }
        }
    }
    
    private void checkCriticalIssues(String equipmentId) {
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        if (status == null) {
            return;
        }
        
        boolean hasCriticalIssues = status.getIssues().stream()
            .anyMatch(issue -> issue.getSeverity() >= 4 && !issue.isResolved());
        
        if (hasCriticalIssues) {
            status.setOperational(false);
        }
    }
    
    private void resolveIssues(String equipmentId, int effectiveness) {
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        if (status == null) {
            return;
        }
        
        // Resolve issues based on effectiveness
        for (EquipmentIssue issue : status.getIssues()) {
            if (!issue.isResolved() && Math.random() < (effectiveness / 100.0)) {
                issue.setResolved(true);
            }
        }
        
        // Update operational status
        updateOperationalStatus(equipmentId);
    }
    
    private MaintenanceFacility findMaintenanceFacility(String facilityId) {
        return maintenanceFacilities.stream()
            .filter(facility -> facility.getFacilityId().equals(facilityId))
            .findFirst()
            .orElse(null);
    }
    
    // Getters for system state
    public Equipment getEquipment(String equipmentId) {
        return equipmentRegistry.get(equipmentId);
    }
    
    public EquipmentStatus getEquipmentStatus(String equipmentId) {
        return equipmentStatuses.get(equipmentId);
    }
    
    public List<MaintenanceRecord> getMaintenanceHistory(String equipmentId) {
        return maintenanceHistory.getOrDefault(equipmentId, new ArrayList<>());
    }
    
    public MaintenanceSchedule getMaintenanceSchedule(String equipmentId) {
        return maintenanceSchedules.get(equipmentId);
    }
    
    public int getAvailableMaintenanceCapacity() {
        return availableMaintenanceCapacity;
    }
    
    public boolean isEquipmentBroken(String equipmentId) {
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        return status != null && status.isBroken();
    }
    
    public List<EquipmentIssue> getEquipmentIssues(String equipmentId) {
        EquipmentStatus status = equipmentStatuses.get(equipmentId);
        return status != null ? status.getIssues() : new ArrayList<>();
    }
}

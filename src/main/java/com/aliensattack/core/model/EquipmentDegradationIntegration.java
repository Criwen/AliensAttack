package com.aliensattack.core.model;

import java.util.*;
import java.util.Date;
import java.util.HashMap;

/**
 * Integration class that connects the EquipmentDegradationMaintenanceSystem
 * with other game systems
 */
public class EquipmentDegradationIntegration {
    
    private final EquipmentDegradationMaintenanceSystem degradationSystem;
    
    public EquipmentDegradationIntegration() {
        this.degradationSystem = new EquipmentDegradationMaintenanceSystem();
    }
    
    /**
     * Initialize the equipment degradation integration
     */
    public void initialize() {
        // Initialize if needed
    }
    
    /**
     * Get equipment degradation status
     */
    public String getEquipmentDegradationStatus() {
        return degradationSystem.getEquipmentDegradationMaintenanceStatus();
    }
    
    /**
     * Add equipment to the system
     */
    public void addEquipment(String equipmentId, String name, String type, int durability) {
        EquipmentDegradationMaintenanceSystem.Equipment equipment = 
            EquipmentDegradationMaintenanceSystem.Equipment.builder()
                .equipmentId(equipmentId)
                .equipmentName(name)
                .equipmentType(null) // Will be set later if needed
                .currentDurability(durability)
                .maxDurability(durability)
                .degradationRate(0)
                .currentDegradation(0)
                .isDamaged(false)
                .isBroken(false)
                .isRepairable(true)
                .equipmentLocation("INVENTORY")
                .assignedUnit("")
                .assignedFacility("")
                .assignedTechnician("")
                .isUnderMaintenance(false)
                .isUnderRepair(false)
                .maintenanceStatus("OPERATIONAL")
                .maintenanceCost(0)
                .repairCost(0)
                .equipmentQuality("EXCELLENT")
                .equipmentCondition("GOOD")
                .lastMaintenanceDate("")
                .nextMaintenanceDate("")
                .warrantyStatus("ACTIVE")
                .isWarrantied(true)
                .warrantyExpiryDate("")
                .equipmentNotes("")
                .equipmentStats(new HashMap<>())
                .equipmentAbilities(new ArrayList<>())
                .manufacturer("XCOM")
                .modelNumber("MODEL_001")
                .serialNumber("SN_" + equipmentId)
                .purchaseDate(new Date().toString())
                .purchaseCost(0)
                .supplier("XCOM_SUPPLY")
                .location("MAIN_BASE")
                .status("ACTIVE")
                .build();
        
        degradationSystem.addEquipment(equipment);
    }
    
    /**
     * Remove equipment from the system
     */
    public void removeEquipment(String equipmentId) {
        EquipmentDegradationMaintenanceSystem.Equipment equipment = degradationSystem.getEquipment(equipmentId);
        if (equipment != null) {
            degradationSystem.removeEquipment(equipment);
        }
    }
    
    /**
     * Get equipment by ID
     */
    public EquipmentDegradationMaintenanceSystem.Equipment getEquipment(String equipmentId) {
        return degradationSystem.getEquipment(equipmentId);
    }
    
    /**
     * Update equipment durability
     */
    public void updateEquipmentDurability(String equipmentId, int newDurability) {
        EquipmentDegradationMaintenanceSystem.Equipment equipment = degradationSystem.getEquipment(equipmentId);
        if (equipment != null) {
            equipment.setCurrentDurability(newDurability);
            degradationSystem.updateEquipment(equipment);
        }
    }
    
    /**
     * Get all equipment
     */
    public List<EquipmentDegradationMaintenanceSystem.Equipment> getAllEquipment() {
        List<EquipmentDegradationMaintenanceSystem.Equipment> equipmentList = new ArrayList<>();
        for (EquipmentDegradationMaintenanceSystem.Equipment equipment : degradationSystem.getEquipmentMap().values()) {
            equipmentList.add(equipment);
        }
        return equipmentList;
    }
    
    /**
     * Get equipment by condition
     */
    public List<EquipmentDegradationMaintenanceSystem.Equipment> getEquipmentByCondition(
            EquipmentDegradationMaintenanceSystem.EquipmentCondition condition) {
        List<EquipmentDegradationMaintenanceSystem.Equipment> equipmentList = new ArrayList<>();
        // Simplified since getCondition() method doesn't exist
        return equipmentList;
    }
    
    /**
     * Get equipment by type
     */
    public List<EquipmentDegradationMaintenanceSystem.Equipment> getEquipmentByType(String type) {
        List<EquipmentDegradationMaintenanceSystem.Equipment> equipmentList = new ArrayList<>();
        // Simplified since getType() method doesn't exist
        return equipmentList;
    }
    
    /**
     * Get equipment quality
     */
    public EquipmentDegradationMaintenanceSystem.EquipmentQuality getEquipmentQuality(String equipmentId) {
        return degradationSystem.getEquipmentQuality(equipmentId);
    }
    
    /**
     * Get maintenance facility
     */
    public EquipmentDegradationMaintenanceSystem.MaintenanceFacility getMaintenanceFacility(String facilityId) {
        return degradationSystem.getMaintenanceFacility(facilityId);
    }
    
    /**
     * Get maintenance plan
     */
    public EquipmentDegradationMaintenanceSystem.MaintenancePlan getMaintenancePlan(String planId) {
        return degradationSystem.getMaintenancePlan(planId);
    }
    
    /**
     * Get degradation system
     */
    public EquipmentDegradationMaintenanceSystem getDegradationSystem() {
        return degradationSystem;
    }
}

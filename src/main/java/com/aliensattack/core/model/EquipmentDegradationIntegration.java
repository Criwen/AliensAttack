package com.aliensattack.core.model;

import com.aliensattack.core.enums.WeaponType;
import com.aliensattack.core.enums.ArmorType;
import java.util.*;

/**
 * Integration class that connects the AdvancedEquipmentDegradationMaintenanceSystem 
 * with existing Weapon and Armor classes
 */
public class EquipmentDegradationIntegration {
    
    private final AdvancedEquipmentDegradationMaintenanceSystem degradationSystem;
    
    public EquipmentDegradationIntegration() {
        this.degradationSystem = new AdvancedEquipmentDegradationMaintenanceSystem();
        initializeSystem();
    }
    
    private void initializeSystem() {
        // Initialize the degradation system using its built-in method
        degradationSystem.initializeSystem();
    }
    
    /**
     * Register a weapon in the degradation system
     */
    public boolean registerWeapon(Weapon weapon) {
        if (weapon == null) {
            return false;
        }
        
        String equipmentId = "WEAPON_" + weapon.getName().replaceAll("\\s+", "_").toUpperCase();
        
        return degradationSystem.registerEquipment(
            equipmentId,
            "WEAPON",
            "UNASSIGNED"
        );
    }
    
    /**
     * Register armor in the degradation system
     */
    public boolean registerArmor(Armor armor) {
        if (armor == null) {
            return false;
        }
        
        String equipmentId = "ARMOR_" + armor.getName().replaceAll("\\s+", "_").toUpperCase();
        
        return degradationSystem.registerEquipment(
            equipmentId,
            "ARMOR",
            "UNASSIGNED"
        );
    }
    
    /**
     * Use weapon and apply degradation
     */
    public boolean useWeapon(Weapon weapon, int usageIntensity) {
        if (weapon == null) {
            return false;
        }
        
        String equipmentId = getWeaponEquipmentId(weapon);
        
        // Apply degradation to equipment
        boolean success = degradationSystem.applyDegradation(equipmentId, usageIntensity);
        
        if (success) {
            // Apply degradation effects to weapon
            applyDegradationToWeapon(weapon, equipmentId);
        }
        
        return success;
    }
    
    /**
     * Use armor and apply degradation
     */
    public boolean useArmor(Armor armor, int damageTaken) {
        if (armor == null) {
            return false;
        }
        
        String equipmentId = getArmorEquipmentId(armor);
        
        // Apply degradation to equipment
        boolean success = degradationSystem.applyDegradation(equipmentId, damageTaken);
        
        if (success) {
            // Apply degradation effects to armor
            applyDegradationToArmor(armor, equipmentId);
        }
        
        return success;
    }
    
    /**
     * Perform maintenance on weapon
     */
    public boolean maintainWeapon(Weapon weapon, String facilityId, String technicianId) {
        if (weapon == null) {
            return false;
        }
        
        String equipmentId = getWeaponEquipmentId(weapon);
        
        boolean success = degradationSystem.startMaintenance(equipmentId, facilityId, technicianId);
        
        if (success) {
            // Apply maintenance benefits to weapon
            applyMaintenanceToWeapon(weapon, equipmentId);
        }
        
        return success;
    }
    
    /**
     * Perform maintenance on armor
     */
    public boolean maintainArmor(Armor armor, String facilityId, String technicianId) {
        if (armor == null) {
            return false;
        }
        
        String equipmentId = getArmorEquipmentId(armor);
        
        boolean success = degradationSystem.startMaintenance(equipmentId, facilityId, technicianId);
        
        if (success) {
            // Apply maintenance benefits to armor
            applyMaintenanceToArmor(armor, equipmentId);
        }
        
        return success;
    }
    
    /**
     * Check if weapon is operational
     */
    public boolean isWeaponOperational(Weapon weapon) {
        if (weapon == null) {
            return false;
        }
        
        String equipmentId = getWeaponEquipmentId(weapon);
        return !degradationSystem.isEquipmentBroken(equipmentId);
    }
    
    /**
     * Check if armor is operational
     */
    public boolean isArmorOperational(Armor armor) {
        if (armor == null) {
            return false;
        }
        
        String equipmentId = getArmorEquipmentId(armor);
        return !degradationSystem.isEquipmentBroken(equipmentId);
    }
    
    /**
     * Get weapon performance multiplier based on durability
     */
    public double getWeaponPerformanceMultiplier(Weapon weapon) {
        if (weapon == null) {
            return 1.0;
        }
        
        String equipmentId = getWeaponEquipmentId(weapon);
        AdvancedEquipmentDegradationMaintenanceSystem.Equipment equipment = degradationSystem.getEquipment(equipmentId);
        
        if (equipment == null) {
            return 1.0;
        }
        
        double durabilityRatio = (double) equipment.getCurrentDurability() / equipment.getMaxDurability();
        return Math.max(0.1, durabilityRatio); // Minimum 10% performance
    }
    
    /**
     * Get armor performance multiplier based on durability
     */
    public double getArmorPerformanceMultiplier(Armor armor) {
        if (armor == null) {
            return 1.0;
        }
        
        String equipmentId = getArmorEquipmentId(armor);
        AdvancedEquipmentDegradationMaintenanceSystem.Equipment equipment = degradationSystem.getEquipment(equipmentId);
        
        if (equipment == null) {
            return 1.0;
        }
        
        double durabilityRatio = (double) equipment.getCurrentDurability() / equipment.getMaxDurability();
        return Math.max(0.1, durabilityRatio); // Minimum 10% performance
    }
    
    /**
     * Get equipment that needs maintenance
     */
    public List<String> getEquipmentNeedingMaintenance() {
        List<String> equipmentNeedingMaintenance = new ArrayList<>();
        
        for (AdvancedEquipmentDegradationMaintenanceSystem.Equipment equipment : degradationSystem.getEquipmentMap().values()) {
            if (equipment.isDamaged() && !equipment.isBroken()) {
                equipmentNeedingMaintenance.add(equipment.getEquipmentId());
            }
        }
        
        return equipmentNeedingMaintenance;
    }
    
    /**
     * Get broken equipment
     */
    public List<String> getBrokenEquipment() {
        List<String> brokenEquipment = new ArrayList<>();
        
        for (AdvancedEquipmentDegradationMaintenanceSystem.Equipment equipment : degradationSystem.getEquipmentMap().values()) {
            if (equipment.isBroken()) {
                brokenEquipment.add(equipment.getEquipmentId());
            }
        }
        
        return brokenEquipment;
    }
    
    // Helper methods
    
    private void applyDegradationToWeapon(Weapon weapon, String equipmentId) {
        double performanceMultiplier = getWeaponPerformanceMultiplier(weapon);
        
        // Apply performance degradation to weapon stats
        weapon.setBaseDamage((int) (weapon.getBaseDamage() * performanceMultiplier));
        weapon.setAccuracy((int) (weapon.getAccuracy() * performanceMultiplier));
        weapon.setRange((int) (weapon.getRange() * performanceMultiplier));
        
        // Check if weapon is broken
        if (degradationSystem.isEquipmentBroken(equipmentId)) {
            weapon.setBaseDamage(0);
            weapon.setAccuracy(0);
            weapon.setRange(0);
        }
    }
    
    private void applyDegradationToArmor(Armor armor, String equipmentId) {
        double performanceMultiplier = getArmorPerformanceMultiplier(armor);
        
        // Apply performance degradation to armor stats
        armor.setDamageReduction((int) (armor.getDamageReduction() * performanceMultiplier));
        
        // Check if armor is broken
        if (degradationSystem.isEquipmentBroken(equipmentId)) {
            armor.setDamageReduction(0);
        }
    }
    
    private void applyMaintenanceToWeapon(Weapon weapon, String equipmentId) {
        // Maintenance will be handled by the degradation system
        // This method can be used for additional weapon-specific maintenance effects
    }
    
    private void applyMaintenanceToArmor(Armor armor, String equipmentId) {
        // Maintenance will be handled by the degradation system
        // This method can be used for additional armor-specific maintenance effects
    }
    
    /**
     * Get degradation system instance
     */
    public AdvancedEquipmentDegradationMaintenanceSystem getDegradationSystem() {
        return degradationSystem;
    }
    
    /**
     * Get equipment status
     */
    public AdvancedEquipmentDegradationMaintenanceSystem.Equipment getEquipment(String equipmentId) {
        return degradationSystem.getEquipment(equipmentId);
    }
    
    /**
     * Get equipment quality
     */
    public AdvancedEquipmentDegradationMaintenanceSystem.EquipmentQuality getEquipmentQuality(String equipmentId) {
        return degradationSystem.getEquipmentQuality(equipmentId);
    }
    
    /**
     * Get maintenance facility by ID
     */
    public AdvancedEquipmentDegradationMaintenanceSystem.MaintenanceFacility getMaintenanceFacility(String facilityId) {
        return degradationSystem.getMaintenanceFacility(facilityId);
    }
    
    /**
     * Get maintenance plan by ID
     */
    public AdvancedEquipmentDegradationMaintenanceSystem.MaintenancePlan getMaintenancePlan(String planId) {
        return degradationSystem.getMaintenancePlan(planId);
    }
    
    /**
     * Check if equipment is damaged
     */
    public boolean isEquipmentDamaged(String equipmentId) {
        return degradationSystem.isEquipmentDamaged(equipmentId);
    }
    
    /**
     * Check if equipment is broken
     */
    public boolean isEquipmentBroken(String equipmentId) {
        return degradationSystem.isEquipmentBroken(equipmentId);
    }
    
    /**
     * Get total damaged equipment count
     */
    public int getTotalDamagedEquipment() {
        return degradationSystem.getTotalDamagedEquipment();
    }
    
    // Helper methods for generating equipment IDs
    
    private String getWeaponEquipmentId(Weapon weapon) {
        return "WEAPON_" + weapon.getName().replaceAll("\\s+", "_").toUpperCase();
    }
    
    private String getArmorEquipmentId(Armor armor) {
        return "ARMOR_" + armor.getName().replaceAll("\\s+", "_").toUpperCase();
    }
}

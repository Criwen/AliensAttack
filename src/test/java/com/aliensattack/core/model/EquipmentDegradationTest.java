package com.aliensattack.core.model;

import com.aliensattack.core.enums.WeaponType;
import com.aliensattack.core.enums.ArmorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Equipment Degradation System functionality
 */
public class EquipmentDegradationTest {
    
    private EquipmentDegradationIntegration integration;
    private Weapon testWeapon;
    private Armor testArmor;
    
    @BeforeEach
    void setUp() {
        integration = new EquipmentDegradationIntegration();
        
        // Create test weapon
        testWeapon = new Weapon("Test Rifle", WeaponType.RIFLE, 25, 50, 15, 75, 8);
        
        // Create test armor
        testArmor = new Armor("Test Armor", ArmorType.MEDIUM_ARMOR, 3, 100);
    }
    
    @Test
    void testRegisterWeapon() {
        // Register weapon in degradation system
        boolean success = integration.registerWeapon(testWeapon);
        
        assertTrue(success);
        
        // Check if weapon is operational
        assertTrue(integration.isWeaponOperational(testWeapon));
        
        // Check performance multiplier
        double multiplier = integration.getWeaponPerformanceMultiplier(testWeapon);
        assertEquals(1.0, multiplier, 0.01);
    }
    
    @Test
    void testRegisterArmor() {
        // Register armor in degradation system
        boolean success = integration.registerArmor(testArmor);
        
        assertTrue(success);
        
        // Check if armor is operational
        assertTrue(integration.isArmorOperational(testArmor));
        
        // Check performance multiplier
        double multiplier = integration.getArmorPerformanceMultiplier(testArmor);
        assertEquals(1.0, multiplier, 0.01);
    }
    
    @Test
    void testWeaponDegradation() {
        // Register weapon
        integration.registerWeapon(testWeapon);
        
        int initialDamage = testWeapon.getBaseDamage();
        int initialAccuracy = testWeapon.getAccuracy();
        int initialRange = testWeapon.getRange();
        
        // Use weapon multiple times to cause degradation
        for (int i = 0; i < 10; i++) {
            integration.useWeapon(testWeapon, 50);
        }
        
        // Check that weapon stats have degraded
        assertTrue(testWeapon.getBaseDamage() < initialDamage);
        assertTrue(testWeapon.getAccuracy() < initialAccuracy);
        assertTrue(testWeapon.getRange() < initialRange);
        
        // Check performance multiplier has decreased
        double multiplier = integration.getWeaponPerformanceMultiplier(testWeapon);
        assertTrue(multiplier < 1.0);
    }
    
    @Test
    void testArmorDegradation() {
        // Register armor
        integration.registerArmor(testArmor);
        
        int initialDamageReduction = testArmor.getDamageReduction();
        
        // Use armor multiple times to cause degradation
        for (int i = 0; i < 10; i++) {
            integration.useArmor(testArmor, 20);
        }
        
        // Check that armor stats have degraded
        assertTrue(testArmor.getDamageReduction() < initialDamageReduction);
        
        // Check performance multiplier has decreased
        double multiplier = integration.getArmorPerformanceMultiplier(testArmor);
        assertTrue(multiplier < 1.0);
    }
    
    @Test
    void testWeaponMaintenance() {
        // Register weapon
        integration.registerWeapon(testWeapon);
        
        // Degrade weapon
        for (int i = 0; i < 10; i++) {
            integration.useWeapon(testWeapon, 50);
        }
        
        int degradedDamage = testWeapon.getBaseDamage();
        double degradedMultiplier = integration.getWeaponPerformanceMultiplier(testWeapon);
        
        // Perform maintenance
        boolean success = integration.maintainWeapon(
            testWeapon, 
            "MAINTENANCE_FACILITY_001",
            "TECHNICIAN_001"
        );
        
        assertTrue(success);
        
        // Check that weapon has been repaired
        assertTrue(testWeapon.getBaseDamage() > degradedDamage);
        
        double newMultiplier = integration.getWeaponPerformanceMultiplier(testWeapon);
        assertTrue(newMultiplier > degradedMultiplier);
    }
    
    @Test
    void testArmorMaintenance() {
        // Register armor
        integration.registerArmor(testArmor);
        
        // Degrade armor
        for (int i = 0; i < 10; i++) {
            integration.useArmor(testArmor, 20);
        }
        
        int degradedDamageReduction = testArmor.getDamageReduction();
        double degradedMultiplier = integration.getArmorPerformanceMultiplier(testArmor);
        
        // Perform maintenance
        boolean success = integration.maintainArmor(
            testArmor, 
            "MAINTENANCE_FACILITY_001",
            "TECHNICIAN_001"
        );
        
        assertTrue(success);
        
        // Check that armor has been repaired
        assertTrue(testArmor.getDamageReduction() > degradedDamageReduction);
        
        double newMultiplier = integration.getArmorPerformanceMultiplier(testArmor);
        assertTrue(newMultiplier > degradedMultiplier);
    }
    

    
    @Test
    void testEquipmentNeedingMaintenance() {
        // Register equipment
        integration.registerWeapon(testWeapon);
        integration.registerArmor(testArmor);
        
        // Use equipment to cause degradation
        for (int i = 0; i < 15; i++) {
            integration.useWeapon(testWeapon, 50);
            integration.useArmor(testArmor, 20);
        }
        
        // Check for equipment needing maintenance
        var equipmentNeedingMaintenance = integration.getEquipmentNeedingMaintenance();
        assertFalse(equipmentNeedingMaintenance.isEmpty());
    }
    
    @Test
    void testBrokenEquipment() {
        // Register equipment
        integration.registerWeapon(testWeapon);
        integration.registerArmor(testArmor);
        
        // Use equipment extensively to break it
        for (int i = 0; i < 50; i++) {
            integration.useWeapon(testWeapon, 100);
            integration.useArmor(testArmor, 50);
        }
        
        // Check for broken equipment
        var brokenEquipment = integration.getBrokenEquipment();
        assertFalse(brokenEquipment.isEmpty());
        
        // Check that equipment is no longer operational
        assertFalse(integration.isWeaponOperational(testWeapon));
        assertFalse(integration.isArmorOperational(testArmor));
    }
    
    @Test
    void testDifferentWeaponTypes() {
        // Test different weapon types
        Weapon pistol = new Weapon("Test Pistol", WeaponType.PISTOL, 15, 30, 10, 80, 4);
        Weapon sniper = new Weapon("Test Sniper", WeaponType.SNIPER_RIFLE, 40, 80, 25, 90, 12);
        Weapon plasma = new Weapon("Test Plasma", WeaponType.PLASMA_WEAPON, 35, 70, 20, 85, 10);
        
        // Register weapons
        integration.registerWeapon(pistol);
        integration.registerWeapon(sniper);
        integration.registerWeapon(plasma);
        
        // Use weapons
        integration.useWeapon(pistol, 30);
        integration.useWeapon(sniper, 40);
        integration.useWeapon(plasma, 50);
        
        // Check that all weapons are operational
        assertTrue(integration.isWeaponOperational(pistol));
        assertTrue(integration.isWeaponOperational(sniper));
        assertTrue(integration.isWeaponOperational(plasma));
    }
    
    @Test
    void testDifferentArmorTypes() {
        // Test different armor types
        Armor lightArmor = new Armor("Light Armor", ArmorType.LIGHT_ARMOR, 2, 80);
        Armor heavyArmor = new Armor("Heavy Armor", ArmorType.HEAVY_ARMOR, 5, 150);
        Armor poweredArmor = new Armor("Powered Armor", ArmorType.POWERED_ARMOR, 8, 200);
        
        // Register armor
        integration.registerArmor(lightArmor);
        integration.registerArmor(heavyArmor);
        integration.registerArmor(poweredArmor);
        
        // Use armor
        integration.useArmor(lightArmor, 15);
        integration.useArmor(heavyArmor, 25);
        integration.useArmor(poweredArmor, 35);
        
        // Check that all armor is operational
        assertTrue(integration.isArmorOperational(lightArmor));
        assertTrue(integration.isArmorOperational(heavyArmor));
        assertTrue(integration.isArmorOperational(poweredArmor));
    }
}

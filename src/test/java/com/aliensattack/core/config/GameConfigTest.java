package com.aliensattack.core.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for GameConfig class
 */
public class GameConfigTest {
    
    @BeforeEach
    void setUp() {
        // Reset configuration before each test
        GameConfig.initialize();
    }
    
    @Test
    void testUnitConfiguration() {
        // Test default unit values
        assertEquals(2, GameConfig.getDefaultActionPoints());
        assertEquals(70, GameConfig.getDefaultOverwatchChance());
        assertEquals(10, GameConfig.getDefaultCriticalChance());
        assertEquals(2, GameConfig.getDefaultCriticalDamageMultiplier());
        assertEquals(10, GameConfig.getDefaultInitiative());
        
        // Test view ranges
        assertEquals(8, GameConfig.getViewRange("soldier"));
        assertEquals(6, GameConfig.getViewRange("alien"));
        assertEquals(4, GameConfig.getViewRange("civilian"));
        assertEquals(10, GameConfig.getViewRange("vehicle"));
    }
    
    @Test
    void testWeaponConfiguration() {
        // Test rifle configuration
        assertEquals("Assault Rifle", GameConfig.getWeaponName("rifle"));
        assertEquals(10, GameConfig.getWeaponDamage("rifle"));
        assertEquals(8, GameConfig.getWeaponRange("rifle"));
        assertEquals(85, GameConfig.getWeaponAccuracy("rifle"));
        assertEquals(12, GameConfig.getWeaponAmmo("rifle"));
        
        // Test shotgun configuration
        assertEquals("Shotgun", GameConfig.getWeaponName("shotgun"));
        assertEquals(15, GameConfig.getWeaponDamage("shotgun"));
        assertEquals(12, GameConfig.getWeaponRange("shotgun"));
        assertEquals(70, GameConfig.getWeaponAccuracy("shotgun"));
        assertEquals(18, GameConfig.getWeaponAmmo("shotgun"));
        
        // Test sniper configuration
        assertEquals("Sniper Rifle", GameConfig.getWeaponName("sniper"));
        assertEquals(25, GameConfig.getWeaponDamage("sniper"));
        assertEquals(10, GameConfig.getWeaponRange("sniper"));
        assertEquals(95, GameConfig.getWeaponAccuracy("sniper"));
        assertEquals(20, GameConfig.getWeaponAmmo("sniper"));
    }
    
    @Test
    void testArmorConfiguration() {
        // Test light armor
        assertEquals("Light Armor", GameConfig.getArmorName("light"));
        assertEquals(1, GameConfig.getArmorDamageReduction("light"));
        assertEquals(20, GameConfig.getArmorHealthBonus("light"));
        
        // Test heavy armor
        assertEquals("Heavy Armor", GameConfig.getArmorName("heavy"));
        assertEquals(5, GameConfig.getArmorDamageReduction("heavy"));
        assertEquals(40, GameConfig.getArmorHealthBonus("heavy"));
        
        // Test powered armor
        assertEquals("Powered Armor", GameConfig.getArmorName("powered"));
        assertEquals(9, GameConfig.getArmorDamageReduction("powered"));
        assertEquals(60, GameConfig.getArmorHealthBonus("powered"));
    }
    
    @Test
    void testExplosiveConfiguration() {
        // Test grenade
        assertEquals("Fragmentation Grenade", GameConfig.getExplosiveName("grenade"));
        assertEquals(25, GameConfig.getExplosiveDamage("grenade"));
        assertEquals(3, GameConfig.getExplosiveRange("grenade"));
        assertEquals(0, GameConfig.getExplosiveDuration("grenade"));
        
        // Test flashbang
        assertEquals("Flashbang", GameConfig.getExplosiveName("flashbang"));
        assertEquals(5, GameConfig.getExplosiveDamage("flashbang"));
        assertEquals(2, GameConfig.getExplosiveRange("flashbang"));
        assertEquals(0, GameConfig.getExplosiveDuration("flashbang"));
        
        // Test timed charge
        assertEquals("Timed Charge", GameConfig.getExplosiveName("timed"));
        assertEquals(35, GameConfig.getExplosiveDamage("timed"));
        assertEquals(3, GameConfig.getExplosiveRange("timed"));
        assertEquals(2, GameConfig.getExplosiveDuration("timed"));
    }
    
    @Test
    void testCombatConfiguration() {
        // Test shot accuracy modifiers
        assertEquals(0, GameConfig.getShotAccuracyModifier("standard"));
        assertEquals(15, GameConfig.getShotAccuracyModifier("aimed"));
        assertEquals(-10, GameConfig.getShotAccuracyModifier("rapid"));
        assertEquals(-5, GameConfig.getShotAccuracyModifier("pistol"));
        assertEquals(-20, GameConfig.getShotAccuracyModifier("overwatch"));
        assertEquals(-30, GameConfig.getShotAccuracyModifier("suppression"));
        
        // Test shot damage modifiers
        assertEquals(1.0, GameConfig.getShotDamageModifier("standard"), 0.001);
        assertEquals(1.5, GameConfig.getShotDamageModifier("aimed"), 0.001);
        assertEquals(0.7, GameConfig.getShotDamageModifier("rapid"), 0.001);
        assertEquals(0.8, GameConfig.getShotDamageModifier("pistol"), 0.001);
        assertEquals(0.6, GameConfig.getShotDamageModifier("overwatch"), 0.001);
        assertEquals(0.3, GameConfig.getShotDamageModifier("suppression"), 0.001);
        
        // Test height and cover bonuses
        assertEquals(10, GameConfig.getHeightBonusPerLevel());
        assertEquals(0, GameConfig.getCoverModifier("none"));
        assertEquals(20, GameConfig.getCoverModifier("light"));
        assertEquals(40, GameConfig.getCoverModifier("heavy"));
        assertEquals(60, GameConfig.getCoverModifier("full"));
    }
    
    @Test
    void testMissionConfiguration() {
        // Test mission turn limits
        assertEquals(20, GameConfig.getMissionTurnLimit("elimination"));
        assertEquals(15, GameConfig.getMissionTurnLimit("extraction"));
        assertEquals(12, GameConfig.getMissionTurnLimit("defense"));
        assertEquals(18, GameConfig.getMissionTurnLimit("sabotage"));
        assertEquals(25, GameConfig.getMissionTurnLimit("reconnaissance"));
        assertEquals(20, GameConfig.getMissionTurnLimit("escort"));
        assertEquals(10, GameConfig.getMissionTurnLimit("timed.assault"));
    }
    
    @Test
    void testGameMechanicsConfiguration() {
        // Test fall damage
        assertEquals(10, GameConfig.getFallDamagePerLevel());
        assertEquals(50, GameConfig.getMaxFallDamage());
        
        // Test suppression
        assertEquals(30, GameConfig.getSuppressionAccuracyPenalty());
        assertEquals(50, GameConfig.getSuppressionMovementPenalty());
        
        // Test medical
        assertEquals(20, GameConfig.getMedicalHealAmount());
        assertEquals(10, GameConfig.getMedicalStabilizeAmount());
        
        // Test psionic
        assertEquals(10, GameConfig.getPsiBaseStrength());
        assertEquals(5, GameConfig.getPsiBaseResistance());
        assertEquals(3, GameConfig.getPsiAbilityCooldown());
    }
    
    @Test
    void testDefaultValues() {
        // Test that non-existent properties return default values
        assertEquals("unknown", GameConfig.getString("nonexistent.property", "unknown"));
        assertEquals(42, GameConfig.getInt("nonexistent.property", 42));
        assertEquals(3.14, GameConfig.getDouble("nonexistent.property", 3.14), 0.001);
        assertFalse(GameConfig.getBoolean("nonexistent.property", false));
        assertTrue(GameConfig.getBoolean("nonexistent.property", true));
    }
    
    @Test
    void testWeaponExplosiveProperties() {
        // Test explosive weapons
        assertTrue(GameConfig.isWeaponExplosive("grenade"));
        assertTrue(GameConfig.isWeaponExplosive("rocket"));
        assertFalse(GameConfig.isWeaponExplosive("rifle"));
        assertFalse(GameConfig.isWeaponExplosive("pistol"));
        
        // Test explosion radius
        assertEquals(5, GameConfig.getWeaponExplosionRadius("grenade"));
        assertEquals(8, GameConfig.getWeaponExplosionRadius("rocket"));
        assertEquals(3, GameConfig.getWeaponExplosionRadius("rifle")); // default
    }
} 
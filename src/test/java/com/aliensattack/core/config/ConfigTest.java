package com.aliensattack.core.config;

/**
 * Simple test class to verify configuration loading
 */
public class ConfigTest {
    
    public static void main(String[] args) {
        System.out.println("Testing GameConfig...");
        
        // Test unit configuration
        System.out.println("Default action points: " + GameConfig.getDefaultActionPoints());
        System.out.println("Default overwatch chance: " + GameConfig.getDefaultOverwatchChance());
        System.out.println("Default critical chance: " + GameConfig.getDefaultCriticalChance());
        System.out.println("Soldier view range: " + GameConfig.getViewRange("soldier"));
        
        // Test weapon configuration
        System.out.println("Rifle damage: " + GameConfig.getWeaponDamage("rifle"));
        System.out.println("Rifle accuracy: " + GameConfig.getWeaponAccuracy("rifle"));
        System.out.println("Rifle range: " + GameConfig.getWeaponRange("rifle"));
        
        // Test armor configuration
        System.out.println("Light armor damage reduction: " + GameConfig.getArmorDamageReduction("light"));
        System.out.println("Heavy armor health bonus: " + GameConfig.getArmorHealthBonus("heavy"));
        
        // Test explosive configuration
        System.out.println("Grenade damage: " + GameConfig.getExplosiveDamage("grenade"));
        System.out.println("Flashbang range: " + GameConfig.getExplosiveRange("flashbang"));
        
        // Test combat configuration
        System.out.println("Aimed shot accuracy modifier: " + GameConfig.getShotAccuracyModifier("aimed"));
        System.out.println("Standard shot damage modifier: " + GameConfig.getShotDamageModifier("standard"));
        System.out.println("Height bonus per level: " + GameConfig.getHeightBonusPerLevel());
        System.out.println("Full cover modifier: " + GameConfig.getCoverModifier("full"));
        
        // Test mission configuration
        System.out.println("Elimination turn limit: " + GameConfig.getMissionTurnLimit("elimination"));
        System.out.println("Defense turn limit: " + GameConfig.getMissionTurnLimit("defense"));
        
        // Test game mechanics
        System.out.println("Fall damage per level: " + GameConfig.getFallDamagePerLevel());
        System.out.println("Max fall damage: " + GameConfig.getMaxFallDamage());
        System.out.println("Suppression accuracy penalty: " + GameConfig.getSuppressionAccuracyPenalty());
        System.out.println("Medical heal amount: " + GameConfig.getMedicalHealAmount());
        
        System.out.println("Configuration test completed successfully!");
    }
} 
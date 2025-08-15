package com.aliensattack.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Configuration utility for loading game parameters from multiple properties files
 */
public class GameConfig {
    private static final Logger log = LogManager.getLogger(GameConfig.class);
    private static final Properties properties = new Properties();
    private static boolean initialized = false;
    
    /**
     * Initialize configuration by loading all properties files
     */
    public static void initialize() {
        if (initialized) {
            return;
        }
        
        // Load all configuration files
        String[] configFiles = {
            "application.properties",
            "unit.properties", 
            "weapon.properties",
            "armor.properties",
            "explosive.properties",
            "combat.properties",
            "mission.properties",
            "game.properties"
        };
        
        for (String configFile : configFiles) {
            try (InputStream input = GameConfig.class.getClassLoader()
                    .getResourceAsStream(configFile)) {
                if (input != null) {
                    Properties fileProps = new Properties();
                    fileProps.load(input);
                    // Merge into main properties, but don't override existing values
                    for (String key : fileProps.stringPropertyNames()) {
                        if (!properties.containsKey(key)) {
                            String value = fileProps.getProperty(key);
                            properties.setProperty(key, value);
                        }
                    }
                } else {
                    log.warn("{} not found on classpath, skipping", configFile);
                }
            } catch (IOException e) {
                log.error("Error loading {}: {}", configFile, e.getMessage());
            }
        }
        
        initialized = true;
        log.debug("GameConfig initialized with {} properties", properties.size());
    }
    
    /**
     * Get string property with default value
     */
    public static String getString(String key, String defaultValue) {
        initialize();
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get integer property with default value
     */
    public static int getInt(String key, int defaultValue) {
        initialize();
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                log.warn("Invalid integer value for {}: {}", key, value);
            }
        }
        return defaultValue;
    }
    
    /**
     * Get double property with default value
     */
    public static double getDouble(String key, double defaultValue) {
        initialize();
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                log.warn("Invalid double value for {}: {}", key, value);
            }
        }
        return defaultValue;
    }
    
    /**
     * Get boolean property with default value
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        initialize();
        String value = properties.getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return defaultValue;
    }
    
    // Unit configuration methods
    public static int getDefaultActionPoints() {
        return getInt("unit.default.action.points", 2);
    }
    
    public static int getDefaultOverwatchChance() {
        return getInt("unit.default.overwatch.chance", 70);
    }
    
    public static int getDefaultCriticalChance() {
        return getInt("unit.default.critical.chance", 10);
    }
    
    public static int getDefaultCriticalDamageMultiplier() {
        return getInt("unit.default.critical.damage.multiplier", 2);
    }
    
    public static int getDefaultInitiative() {
        return getInt("unit.default.initiative", 10);
    }
    
    public static int getViewRange(String unitType) {
        return getInt("unit.view.range." + unitType.toLowerCase(), 6);
    }
    
    // Weapon configuration methods
    public static String getWeaponName(String weaponType) {
        return getString("weapon." + weaponType + ".name", weaponType);
    }
    
    public static int getWeaponDamage(String weaponType) {
        return getInt("weapon." + weaponType + ".damage", 10);
    }
    
    public static int getWeaponRange(String weaponType) {
        return getInt("weapon." + weaponType + ".range", 8);
    }
    
    public static int getWeaponAccuracy(String weaponType) {
        return getInt("weapon." + weaponType + ".accuracy", 80);
    }
    
    public static int getWeaponAmmo(String weaponType) {
        return getInt("weapon." + weaponType + ".ammo", 12);
    }
    
    public static boolean isWeaponExplosive(String weaponType) {
        return getBoolean("weapon." + weaponType + ".explosive", false);
    }
    
    public static int getWeaponExplosionRadius(String weaponType) {
        return getInt("weapon." + weaponType + ".explosion.radius", 3);
    }
    
    // Armor configuration methods
    public static String getArmorName(String armorType) {
        return getString("armor." + armorType + ".name", armorType);
    }
    
    public static int getArmorDamageReduction(String armorType) {
        return getInt("armor." + armorType + ".damage.reduction", 0);
    }
    
    public static int getArmorHealthBonus(String armorType) {
        return getInt("armor." + armorType + ".health.bonus", 0);
    }
    
    // Explosive configuration methods
    public static String getExplosiveName(String explosiveType) {
        return getString("explosive." + explosiveType + ".name", explosiveType);
    }
    
    public static int getExplosiveDamage(String explosiveType) {
        return getInt("explosive." + explosiveType + ".damage", 10);
    }
    
    public static int getExplosiveRange(String explosiveType) {
        return getInt("explosive." + explosiveType + ".range", 3);
    }
    
    public static int getExplosiveDuration(String explosiveType) {
        return getInt("explosive." + explosiveType + ".duration", 0);
    }
    
    // Combat configuration methods
    public static int getShotAccuracyModifier(String shotType) {
        return getInt("combat.shot." + shotType + ".accuracy.modifier", 0);
    }
    
    public static double getShotDamageModifier(String shotType) {
        return getDouble("combat.shot." + shotType + ".damage.modifier", 1.0);
    }
    
    public static int getHeightBonusPerLevel() {
        return getInt("combat.height.bonus.per.level", 10);
    }
    
    public static int getCoverModifier(String coverType) {
        return getInt("combat.cover." + coverType + ".modifier", 0);
    }
    
    public static int getCriticalBaseChance() {
        return getInt("combat.critical.base.chance", 10);
    }
    
    public static int getCriticalHeightBonus() {
        return getInt("combat.critical.height.bonus", 5);
    }
    
    // Mission configuration methods
    public static int getMissionTurnLimit(String missionType) {
        return getInt("mission." + missionType + ".turn.limit", 20);
    }
    
    // Game mechanics methods
    public static int getFallDamagePerLevel() {
        return getInt("game.fall.damage.per.level", 10);
    }
    
    public static int getMaxFallDamage() {
        return getInt("game.fall.max.damage", 50);
    }
    
    public static int getSuppressionAccuracyPenalty() {
        return getInt("game.suppression.accuracy.penalty", 30);
    }
    
    public static int getSuppressionMovementPenalty() {
        return getInt("game.suppression.movement.penalty", 50);
    }
    
    public static int getMedicalHealAmount() {
        return getInt("game.medical.heal.amount", 20);
    }
    
    public static int getMedicalStabilizeAmount() {
        return getInt("game.medical.stabilize.amount", 10);
    }
    
    public static int getPsiBaseStrength() {
        return getInt("game.psi.base.strength", 10);
    }
    
    public static int getPsiBaseResistance() {
        return getInt("game.psi.base.resistance", 5);
    }
    
    public static int getPsiAbilityCooldown() {
        return getInt("game.psi.ability.cooldown", 3);
    }
}
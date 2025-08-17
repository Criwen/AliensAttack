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
            "game.properties",
            "actions.properties",
            "ai.properties"
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
    
    // Action configuration methods
    public static int getActionCost(String actionType) {
        return getInt("action.cost." + actionType.toLowerCase(), 1);
    }
    
    public static int getDefendBonus() {
        return getInt("action.defend.bonus", 20);
    }
    
    public static int getDefendDuration() {
        return getInt("action.defend.duration", 2);
    }
    
    public static int getHealAmount() {
        return getInt("action.heal.amount", 25);
    }
    
    public static int getGrenadeDamage() {
        return getInt("action.grenade.damage", 30);
    }
    
    public static int getOverwatchRange() {
        return getInt("action.overwatch.range", 8);
    }
    
    public static int getOverwatchChance() {
        return getInt("action.overwatch.chance", 70);
    }
    
    public static int getActionBaseHitChance() {
        return getInt("action.base.hit.chance", 70);
    }
    
    // Base combat configuration methods
    public static int getBaseHitChance() {
        return getInt("combat.base.hit.chance", 70);
    }
    
    public static int getBaseOverwatchChance() {
        return getInt("combat.base.overwatch.chance", 70);
    }
    
    public static int getBaseDefenseBonus() {
        return getInt("combat.base.defense.bonus", 20);
    }
    
    public static int getBaseDefenseDuration() {
        return getInt("combat.base.defense.duration", 2);
    }
    
    public static int getBaseHealAmount() {
        return getInt("combat.base.heal.amount", 25);
    }
    
    public static int getBaseGrenadeDamage() {
        return getInt("combat.base.grenade.damage", 30);
    }
    
    public static int getBaseOverwatchRange() {
        return getInt("combat.base.overwatch.range", 8);
    }
    
    // Status effect configuration methods
    public static int getPoisonAccuracyPenalty() {
        return getInt("combat.status.poison.accuracy.penalty", 20);
    }
    
    public static int getMarkedAccuracyBonus() {
        return getInt("combat.status.marked.accuracy.bonus", 20);
    }
    
    // Combat system limits
    public static int getMaxTurns() {
        return getInt("combat.max.turns", 100);
    }
    
    public static int getMinAliveUnits() {
        return getInt("combat.min.alive.units", 1);
    }
    
    public static int getMaxEvents() {
        return getInt("combat.max.events", 10);
    }
    
    // Game field and UI configuration
    public static int getDefaultFieldWidth() {
        return getInt("game.field.default.width", 10);
    }
    
    public static int getDefaultFieldHeight() {
        return getInt("game.field.default.height", 10);
    }
    
    public static int getTurnStart() {
        return getInt("game.turn.start", 1);
    }
    
    public static int getGrenadePreviewRadius() {
        return getInt("game.grenade.preview.radius", 0);
    }
    
    public static int getDefaultMissionTurns() {
        return getInt("game.mission.default.turns", 30);
    }
    
    public static int getMaxMissionTurns() {
        return getInt("game.mission.max.turns", 100);
    }
    
    // Evolution system configuration
    public static int getEvolutionBasePoints() {
        return getInt("game.evolution.base.points", 100);
    }
    
    public static int getEvolutionMutationRate() {
        return getInt("game.evolution.mutation.rate", 10);
    }
    
    public static int getEvolutionCompletionBonus() {
        return getInt("game.evolution.completion.bonus", 50);
    }
    
    public static int getEvolutionPathsRequired() {
        return getInt("game.evolution.paths.required", 3);
    }
    
    // Weapon specialization configuration
    public static int getWeaponSpecializationMaxLevel() {
        return getInt("game.weapon.specialization.max.level", 100);
    }
    
    public static int getWeaponSpecializationMaxDurability() {
        return getInt("game.weapon.specialization.max.durability", 100);
    }
    
    public static int getWeaponSpecializationDurabilityLoss() {
        return getInt("game.weapon.specialization.durability.loss", 1);
    }
    
    // Combat cache configuration
    public static int getCombatCacheDefaultSize() {
        return getInt("game.combat.cache.default.size", 1000);
    }
    
    // AI configuration methods
    public static boolean isAIEnabled() {
        return getBoolean("ai.enemy.enabled", true);
    }
    
    public static int getAIDifficultyLevel() {
        return getInt("ai.enemy.difficulty.level", 5);
    }
    
    public static int getAIMaxDecisionTime() {
        return getInt("ai.enemy.max.decision.time", 1000);
    }
    
    public static boolean isAILearningEnabled() {
        return getBoolean("ai.enemy.learning.enabled", true);
    }
    
    public static double getAIAdaptationRate() {
        return getDouble("ai.enemy.adaptation.rate", 0.1);
    }
    
    // Enhanced AI attributes (merged from AlienAI)
    public static int getAIIntelligenceLevel() {
        return getInt("ai.enemy.intelligence.level", 50);
    }
    
    public static int getAITacticalAwareness() {
        return getInt("ai.enemy.tactical.awareness", 60);
    }
    
    public static int getAIAggressionLevel() {
        return getInt("ai.enemy.aggression.level", 70);
    }
    
    // AI Learning and Adaptation
    public static boolean isAIMemoryLearningEnabled() {
        return getBoolean("ai.memory.learning.enabled", true);
    }
    
    public static double getAIMemoryAdaptationRate() {
        return getDouble("ai.memory.adaptation.rate", 0.1);
    }
    
    // Advanced Terrain Analysis Configuration
    // Elevation System
    public static int getTerrainElevationScale() {
        return getInt("terrain.elevation.scale", 10);
    }
    
    public static int getTerrainElevationMin() {
        return getInt("terrain.elevation.min", -20);
    }
    
    public static int getTerrainElevationMax() {
        return getInt("terrain.elevation.max", 50);
    }
    
    public static double getTerrainHillFrequency() {
        return getDouble("terrain.hill.frequency", 0.1);
    }
    
    public static double getTerrainValleyFrequency() {
        return getDouble("terrain.valley.frequency", 0.15);
    }
    
    // Terrain Object Generation
    public static double getTerrainObjectDensity() {
        return getDouble("terrain.object.density", 0.3);
    }
    
    public static int getTerrainObjectDefaultHealth() {
        return getInt("terrain.object.default.health", 100);
    }
    
    // Depression and Slope Detection
    public static int getTerrainDepressionDetectionRange() {
        return getInt("terrain.depression.detection.range", 3);
    }
    
    public static double getTerrainDepressionThreshold() {
        return getDouble("terrain.depression.threshold", 0.6);
    }
    
    public static int getTerrainSlopeDetectionRange() {
        return getInt("terrain.slope.detection.range", 2);
    }
    
    public static double getTerrainSlopeThreshold() {
        return getDouble("terrain.slope.threshold", 5.0);
    }
    
    // Hazard Detection
    public static int getTerrainHazardLowElevation() {
        return getInt("terrain.hazard.low.elevation", -5);
    }
    
    public static int getTerrainHazardHighElevation() {
        return getInt("terrain.hazard.high.elevation", 30);
    }
    
    // Cover System Enhancement
    public static int getCoverUnitDetectionRange() {
        return getInt("cover.unit.detection.range", 3);
    }
    
    public static int getCoverUnitMaxDistance() {
        return getInt("cover.unit.max.distance", 2);
    }
    
    public static int getCoverUnitCloseDistance() {
        return getInt("cover.unit.close.distance", 1);
    }
    
    public static double getCoverMinimumValue() {
        return getDouble("cover.minimum.value", 0.3);
    }
    
    public static double getCoverElevationBonus() {
        return getDouble("cover.elevation.bonus", 0.02);
    }
    
    public static double getCoverObjectBonus() {
        return getDouble("cover.object.bonus", 0.1);
    }
    
    public static double getCoverHazardBonus() {
        return getDouble("cover.hazard.bonus", 0.05);
    }
    
    public static double getCoverElevationWeight() {
        return getDouble("cover.elevation.weight", 0.3);
    }
    
    // Visibility System
    public static double getVisibilityElevationPenalty() {
        return getDouble("visibility.elevation.penalty", 0.01);
    }
    
    public static double getVisibilityHazardModifier(String hazardType) {
        String key = "visibility.hazard.modifier." + hazardType.toLowerCase();
        switch (hazardType.toUpperCase()) {
            case "SMOKE": return getDouble(key, 0.5);
            case "FOG": return getDouble(key, 0.6);
            case "FIRE": return getDouble(key, 0.7);
            case "ACID": return getDouble(key, 0.8);
            default: return 1.0;
        }
    }
    
    // Tactical Analysis Weights
    public static double getTacticalCoverWeight() {
        return getDouble("tactical.cover.weight", 0.4);
    }
    
    public static double getTacticalElevationWeight() {
        return getDouble("tactical.elevation.weight", 0.3);
    }
    
    public static double getTacticalVisibilityWeight() {
        return getDouble("tactical.visibility.weight", 0.2);
    }
    
    public static double getTacticalHazardPenalty() {
        return getDouble("tactical.hazard.penalty", 0.1);
    }
    
    // Squad Coordination System Configuration
    // Squad Formation
    public static int getSquadFormationRange() {
        return getInt("squad.formation.range", 5);
    }
    
    public static int getSquadFormationSpacing() {
        return getInt("squad.formation.spacing", 2);
    }
    
    public static int getSquadMinSize() {
        return getInt("squad.min.size", 3);
    }
    
    public static int getSquadMaxSize() {
        return getInt("squad.max.size", 8);
    }
    
    public static int getSquadMediumSize() {
        return getInt("squad.medium.size", 5);
    }
    
    public static int getSquadLargeSize() {
        return getInt("squad.large.size", 6);
    }
    
    // Squad Coordination
    public static double getSquadBaseCoordinationScore() {
        return getDouble("squad.base.coordination.score", 0.3);
    }
    
    public static double getSquadBaseMorale() {
        return getDouble("squad.base.morale", 0.7);
    }
    
    public static int getSquadUpdateInterval() {
        return getInt("squad.update.interval", 2000);
    }
    
    public static int getSquadMaxTactics() {
        return getInt("squad.max.tactics", 3);
    }
    
    // Squad Advantages
    public static double getSquadMemberAdvantage() {
        return getDouble("squad.member.advantage", 0.1);
    }
    
    public static double getSquadCoverAdvantage() {
        return getDouble("squad.cover.advantage", 0.15);
    }
    
    public static double getSquadElevationAdvantage() {
        return getDouble("squad.elevation.advantage", 0.02);
    }
    
    // Formation Advantages
    public static double getFormationWedgeAdvantage() {
        return getDouble("formation.wedge.advantage", 0.2);
    }
    
    public static double getFormationDiamondAdvantage() {
        return getDouble("formation.diamond.advantage", 0.15);
    }
    
    public static double getFormationLineAdvantage() {
        return getDouble("formation.line.advantage", 0.1);
    }
    
    public static double getFormationCircleAdvantage() {
        return getDouble("formation.circle.advantage", 0.12);
    }
    
    // Tactics Configuration
    public static int getTacticDefaultCooldown() {
        return getInt("tactic.default.cooldown", 5);
    }
    
    // AI behavior thresholds
    public static double getAIAggressiveThreshold() {
        return getDouble("ai.behavior.aggressive.threshold", 0.7);
    }
    
    public static double getAIDefensiveThreshold() {
        return getDouble("ai.behavior.defensive.threshold", 0.3);
    }
    
    public static double getAIStealthThreshold() {
        return getDouble("ai.behavior.stealth.threshold", 0.5);
    }
    
    public static double getAISupportThreshold() {
        return getDouble("ai.behavior.support.threshold", 0.6);
    }
    
    public static double getAIBalancedThreshold() {
        return getDouble("ai.behavior.balanced.threshold", 0.5);
    }
    
    // AI decision chances
    public static double getAIAttackChance() {
        return getDouble("ai.decision.attack.chance", 0.8);
    }
    
    public static double getAIMoveChance() {
        return getDouble("ai.decision.move.chance", 0.6);
    }
    
    public static double getAISpecialAbilityChance() {
        return getDouble("ai.decision.special.ability.chance", 0.3);
    }
    
    public static double getAIDefendChance() {
        return getDouble("ai.decision.defend.chance", 0.4);
    }
    
    public static double getAIRetreatChance() {
        return getDouble("ai.decision.retreat.chance", 0.2);
    }
    
    // AI tactical bonuses
    public static int getAICoverBonus() {
        return getInt("ai.tactical.cover.bonus", 15);
    }
    
    public static int getAIFlankingBonus() {
        return getInt("ai.tactical.flanking.bonus", 25);
    }
    
    public static int getAIHeightBonus() {
        return getInt("ai.tactical.height.bonus", 10);
    }
    
    public static int getAIRangePenalty() {
        return getInt("ai.tactical.range.penalty", -5);
    }
    
    // AI difficulty scaling
    public static double getAIDifficultyIntelligenceMultiplier() {
        return getDouble("ai.difficulty.intelligence.multiplier", 1.2);
    }
    
    public static double getAIDifficultyTacticalMultiplier() {
        return getDouble("ai.difficulty.tactical.multiplier", 1.15);
    }
    
    public static double getAIDifficultyAggressionMultiplier() {
        return getDouble("ai.difficulty.aggression.multiplier", 1.1);
    }
    
    public static double getAIDifficultyAdaptationMultiplier() {
        return getDouble("ai.difficulty.adaptation.multiplier", 1.25);
    }
    
    // AI memory and learning
    public static int getAIMemoryMaxPositions() {
        return getInt("ai.memory.max.positions", 50);
    }
    
    public static double getAIMemoryLearningRate() {
        return getDouble("ai.memory.learning.rate", 0.05);
    }
    
    public static double getAIMemoryForgettingRate() {
        return getDouble("ai.memory.forgetting.rate", 0.02);
    }
    
    public static double getAIMemoryPatternRecognitionThreshold() {
        return getDouble("ai.memory.pattern.recognition.threshold", 0.7);
    }
    
    // AI performance settings
    public static int getAIPerformanceMaxCalculationTime() {
        return getInt("ai.performance.max.calculation.time", 500);
    }
    
    public static int getAIPerformanceBehaviorUpdateInterval() {
        return getInt("ai.performance.behavior.update.interval", 3);
    }
    
    public static int getAIPerformanceDecisionCacheSize() {
        return getInt("ai.performance.decision.cache.size", 100);
    }
    
    public static int getAIPerformancePathfindingMaxIterations() {
        return getInt("ai.performance.pathfinding.max.iterations", 1000);
    }
    
    // AI special abilities
    public static int getAISpecialAbilityEnergyThreshold() {
        return getInt("ai.special.ability.energy.threshold", 20);
    }
    
    public static double getAISpecialAbilityCooldownMultiplier() {
        return getDouble("ai.special.ability.cooldown.multiplier", 1.5);
    }
    
    public static double getAISpecialAbilitySuccessRateBoost() {
        return getDouble("ai.special.ability.success.rate.boost", 0.1);
    }
    
    public static double getAISpecialAbilityTargetPriorityBoost() {
        return getDouble("ai.special.ability.target.priority.boost", 0.2);
    }
    
    // AI coordination
    public static int getAICoordinationPodSizeLimit() {
        return getInt("ai.coordination.pod.size.limit", 6);
    }
    
    public static int getAICoordinationCommunicationRange() {
        return getInt("ai.coordination.communication.range", 8);
    }
    
    public static int getAICoordinationSupportRange() {
        return getInt("ai.coordination.support.range", 5);
    }
    
    public static double getAICoordinationRetreatSignalThreshold() {
        return getDouble("ai.coordination.retreat.signal.threshold", 0.3);
    }
    
    // AI environmental awareness
    public static double getAIEnvironmentHazardAvoidance() {
        return getDouble("ai.environment.hazard.avoidance", 0.8);
    }
    
    public static double getAIEnvironmentCoverPreference() {
        return getDouble("ai.environment.cover.preference", 0.7);
    }
    
    public static double getAIEnvironmentHeightAdvantage() {
        return getDouble("ai.environment.height.advantage", 0.6);
    }
    
    public static double getAIEnvironmentVisibilityImportance() {
        return getDouble("ai.environment.visibility.importance", 0.9);
    }
}
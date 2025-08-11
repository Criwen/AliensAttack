package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Combat Environment and Weather Effects System
 * Implements dynamic environmental conditions that affect combat mechanics and tactical decisions
 */
public class AdvancedCombatEnvironmentWeatherEffectsSystem {
    
    private Map<String, WeatherCondition> weatherConditions;
    private Map<String, EnvironmentalHazard> environmentalHazards;
    private Map<String, WeatherBasedTactic> weatherBasedTactics;
    private Map<String, EnvironmentalInteraction> environmentalInteractions;
    private Map<String, WeatherProgression> weatherProgressions;
    private Map<String, WeatherEvent> weatherEvents;
    private Map<String, Integer> weatherIntensity;
    private Map<String, Integer> hazardLevels;
    private Map<String, Boolean> weatherActive;
    private Map<String, List<String>> activeWeatherEffects;
    private Map<String, List<String>> affectedUnits;
    private Map<String, Integer> weatherDuration;
    private Map<String, Integer> weatherBonuses;
    private Map<String, Map<String, Integer>> tacticalModifiers;
    

    
    /**
     * Weather condition with effects
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherCondition {
        private String conditionId;
        private String conditionName;
        private String conditionType;
        private int intensity;
        private int maxIntensity;
        private Map<String, Integer> weatherEffects;
        private List<String> affectedAbilities;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> conditionBonuses;
        private List<String> conditionAbilities;
        private String conditionMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> visibilityModifiers;
        private List<String> movementPenalties;
        private boolean isDynamic;
        private String progressionType;
    }
    
    /**
     * Environmental hazard system
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentalHazard {
        private String hazardId;
        private String hazardName;
        private String hazardType;
        private int hazardLevel;
        private int maxHazardLevel;
        private Map<String, Integer> hazardEffects;
        private List<String> affectedUnits;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> hazardBonuses;
        private List<String> hazardAbilities;
        private String hazardMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
    }
    
    /**
     * Weather-based tactical advantages
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherBasedTactic {
        private String tacticId;
        private String tacticName;
        private String description;
        private String weatherType;
        private Map<String, Integer> tacticBonuses;
        private List<String> affectedUnits;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> tacticEffects;
        private List<String> tacticAbilities;
        private String tacticMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> weatherRequirements;
        private List<String> tacticLimitations;
        private boolean isAdvanced;
        private String advancedType;
    }
    
    /**
     * Environmental interaction system
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentalInteraction {
        private String interactionId;
        private String interactionName;
        private String description;
        private String interactionType;
        private Map<String, Integer> interactionEffects;
        private List<String> affectedUnits;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> interactionBonuses;
        private List<String> interactionAbilities;
        private String interactionMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> environmentalRequirements;
        private List<String> interactionLimitations;
        private boolean isTactical;
        private String tacticalType;
    }
    
    /**
     * Weather progression system
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherProgression {
        private String progressionId;
        private String weatherId;
        private String progressionType;
        private int currentStage;
        private int maxStages;
        private Map<String, Integer> stageEffects;
        private List<String> progressionEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> progressionBonuses;
        private List<String> progressionAbilities;
        private String progressionMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> stageRequirements;
        private List<String> stageLimitations;
        private boolean isPredictable;
        private String predictionType;
    }
    
    /**
     * Weather event tracking
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherEvent {
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
        private String weatherStatus;
        private int weatherLevel;
    }
    

    
    /**
     * Initialize the combat environment and weather effects system
     */
    private void initializeSystem() {
        // Initialize weather conditions
        initializeWeatherConditions();
        
        // Initialize environmental hazards
        initializeEnvironmentalHazards();
        
        // Initialize weather-based tactics
        initializeWeatherBasedTactics();
        
        // Initialize environmental interactions
        initializeEnvironmentalInteractions();
        
        // Initialize weather progressions
        initializeWeatherProgressions();
    }
    
    /**
     * Initialize weather conditions
     */
    private void initializeWeatherConditions() {
        // Rain weather condition
        WeatherCondition rain = WeatherCondition.builder()
            .conditionId("rain_weather")
            .conditionName("Rain")
            .conditionType("Precipitation")
            .intensity(50)
            .maxIntensity(100)
            .weatherEffects(Map.of("Visibility", -20, "Movement", -10, "Accuracy", -15))
            .affectedAbilities(Arrays.asList("Sniper", "Long Range", "Overwatch"))
            .isActive(false)
            .duration(5)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.8)
            .failureEffect("No weather change")
            .conditionBonuses(Map.of("Stealth", 10, "Concealment", 15))
            .conditionAbilities(Arrays.asList("Reduced Visibility", "Movement Penalty"))
            .conditionMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather System")
            .visibilityModifiers(Map.of("Visual", -20, "Thermal", -10))
            .movementPenalties(Arrays.asList("Reduced movement speed", "Slippery terrain"))
            .isDynamic(true)
            .progressionType("Gradual")
            .build();
        
        weatherConditions.put("rain_weather", rain);
        
        // Fog weather condition
        WeatherCondition fog = WeatherCondition.builder()
            .conditionId("fog_weather")
            .conditionName("Fog")
            .conditionType("Visibility")
            .intensity(70)
            .maxIntensity(100)
            .weatherEffects(Map.of("Visibility", -40, "Range", -30, "Detection", -25))
            .affectedAbilities(Arrays.asList("Sniper", "Long Range", "Detection"))
            .isActive(false)
            .duration(8)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.9)
            .failureEffect("No weather change")
            .conditionBonuses(Map.of("Stealth", 25, "Concealment", 30))
            .conditionAbilities(Arrays.asList("Heavy Visibility Reduction", "Range Penalty"))
            .conditionMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather System")
            .visibilityModifiers(Map.of("Visual", -40, "Thermal", -20))
            .movementPenalties(Arrays.asList("Reduced visibility", "Navigation difficulty"))
            .isDynamic(true)
            .progressionType("Gradual")
            .build();
        
        weatherConditions.put("fog_weather", fog);
        
        // Storm weather condition
        WeatherCondition storm = WeatherCondition.builder()
            .conditionId("storm_weather")
            .conditionName("Storm")
            .conditionType("Severe")
            .intensity(90)
            .maxIntensity(100)
            .weatherEffects(Map.of("Visibility", -60, "Movement", -30, "Accuracy", -40))
            .affectedAbilities(Arrays.asList("All ranged abilities", "Movement", "Detection"))
            .isActive(false)
            .duration(3)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.7)
            .failureEffect("No weather change")
            .conditionBonuses(Map.of("Stealth", 40, "Concealment", 50))
            .conditionAbilities(Arrays.asList("Severe Visibility Reduction", "Movement Penalty", "Accuracy Penalty"))
            .conditionMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather System")
            .visibilityModifiers(Map.of("Visual", -60, "Thermal", -40))
            .movementPenalties(Arrays.asList("Severe movement penalty", "Navigation impossible"))
            .isDynamic(true)
            .progressionType("Rapid")
            .build();
        
        weatherConditions.put("storm_weather", storm);
    }
    
    /**
     * Initialize environmental hazards
     */
    private void initializeEnvironmentalHazards() {
        // Acid rain hazard
        EnvironmentalHazard acidRain = EnvironmentalHazard.builder()
            .hazardId("acid_rain_hazard")
            .hazardName("Acid Rain")
            .hazardType("Chemical")
            .hazardLevel(60)
            .maxHazardLevel(100)
            .hazardEffects(Map.of("Damage", 10, "Armor Degradation", 20, "Equipment Damage", 15))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(4)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.8)
            .failureEffect("No hazard")
            .hazardBonuses(Map.of("Chemical Damage", 20, "Equipment Degradation", 15))
            .hazardAbilities(Arrays.asList("Continuous Damage", "Equipment Degradation"))
            .hazardMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather System")
            .damageModifiers(Map.of("Chemical", 20, "Equipment", 15))
            .resistanceTypes(Arrays.asList("Chemical Protection", "Equipment Shielding"))
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        environmentalHazards.put("acid_rain_hazard", acidRain);
        
        // Radiation hazard
        EnvironmentalHazard radiation = EnvironmentalHazard.builder()
            .hazardId("radiation_hazard")
            .hazardName("Radiation")
            .hazardType("Radiation")
            .hazardLevel(80)
            .maxHazardLevel(100)
            .hazardEffects(Map.of("Damage", 15, "Health Degradation", 25, "Equipment Damage", 20))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(6)
            .currentDuration(0)
            .activationCondition("Environmental change")
            .successRate(0.9)
            .failureEffect("No hazard")
            .hazardBonuses(Map.of("Radiation Damage", 25, "Health Degradation", 20))
            .hazardAbilities(Arrays.asList("Continuous Damage", "Health Degradation", "Equipment Damage"))
            .hazardMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Environmental System")
            .damageModifiers(Map.of("Radiation", 25, "Health", 20))
            .resistanceTypes(Arrays.asList("Radiation Protection", "Health Shielding"))
            .isPermanent(true)
            .permanentCondition("High radiation area")
            .build();
        
        environmentalHazards.put("radiation_hazard", radiation);
    }
    
    /**
     * Initialize weather-based tactics
     */
    private void initializeWeatherBasedTactics() {
        // Rain tactics
        WeatherBasedTactic rainTactics = WeatherBasedTactic.builder()
            .tacticId("rain_tactics")
            .tacticName("Rain Tactics")
            .description("Tactical advantages in rainy conditions")
            .weatherType("Rain")
            .tacticBonuses(Map.of("Stealth", 15, "Concealment", 20, "Close Combat", 10))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(5)
            .currentDuration(0)
            .activationCondition("Rain weather active")
            .successRate(0.9)
            .failureEffect("No tactical bonus")
            .tacticEffects(Map.of("Stealth", 15, "Concealment", 20))
            .tacticAbilities(Arrays.asList("Enhanced Stealth", "Improved Concealment"))
            .tacticMethod("Passive")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Rain Weather")
            .weatherRequirements(Map.of("Rain", 30))
            .tacticLimitations(Arrays.asList("Reduced visibility", "Movement penalty"))
            .isAdvanced(false)
            .advancedType("Basic")
            .build();
        
        weatherBasedTactics.put("rain_tactics", rainTactics);
        
        // Fog tactics
        WeatherBasedTactic fogTactics = WeatherBasedTactic.builder()
            .tacticId("fog_tactics")
            .tacticName("Fog Tactics")
            .description("Tactical advantages in foggy conditions")
            .weatherType("Fog")
            .tacticBonuses(Map.of("Stealth", 25, "Concealment", 30, "Ambush", 20))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(8)
            .currentDuration(0)
            .activationCondition("Fog weather active")
            .successRate(0.9)
            .failureEffect("No tactical bonus")
            .tacticEffects(Map.of("Stealth", 25, "Concealment", 30))
            .tacticAbilities(Arrays.asList("Enhanced Stealth", "Improved Concealment", "Ambush Bonus"))
            .tacticMethod("Passive")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Fog Weather")
            .weatherRequirements(Map.of("Fog", 50))
            .tacticLimitations(Arrays.asList("Severe visibility reduction", "Range penalty"))
            .isAdvanced(true)
            .advancedType("Stealth")
            .build();
        
        weatherBasedTactics.put("fog_tactics", fogTactics);
    }
    
    /**
     * Initialize environmental interactions
     */
    private void initializeEnvironmentalInteractions() {
        // Water interaction
        EnvironmentalInteraction waterInteraction = EnvironmentalInteraction.builder()
            .interactionId("water_interaction")
            .interactionName("Water Interaction")
            .description("Interactions with water environments")
            .interactionType("Water")
            .interactionEffects(Map.of("Movement", -20, "Stealth", 15, "Detection", -10))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(3)
            .currentDuration(0)
            .activationCondition("Unit in water")
            .successRate(0.8)
            .failureEffect("No interaction")
            .interactionBonuses(Map.of("Stealth", 15, "Concealment", 10))
            .interactionAbilities(Arrays.asList("Water Movement", "Stealth Bonus"))
            .interactionMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Water Contact")
            .environmentalRequirements(Map.of("Water", 1))
            .interactionLimitations(Arrays.asList("Movement penalty", "Equipment damage risk"))
            .isTactical(true)
            .tacticalType("Movement")
            .build();
        
        environmentalInteractions.put("water_interaction", waterInteraction);
        
        // High ground interaction
        EnvironmentalInteraction highGroundInteraction = EnvironmentalInteraction.builder()
            .interactionId("high_ground_interaction")
            .interactionName("High Ground Interaction")
            .description("Interactions with elevated positions")
            .interactionType("Elevation")
            .interactionEffects(Map.of("Accuracy", 15, "Range", 10, "Detection", 20))
            .affectedUnits(new ArrayList<>())
            .isActive(false)
            .duration(-1)
            .currentDuration(0)
            .activationCondition("Unit on high ground")
            .successRate(1.0)
            .failureEffect("No interaction")
            .interactionBonuses(Map.of("Accuracy", 15, "Range", 10))
            .interactionAbilities(Arrays.asList("Height Advantage", "Improved Accuracy"))
            .interactionMethod("Passive")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Elevation")
            .environmentalRequirements(Map.of("Elevation", 2))
            .interactionLimitations(Arrays.asList("Exposed position", "Limited cover"))
            .isTactical(true)
            .tacticalType("Positioning")
            .build();
        
        environmentalInteractions.put("high_ground_interaction", highGroundInteraction);
    }
    
    /**
     * Initialize weather progressions
     */
    private void initializeWeatherProgressions() {
        // Rain progression
        WeatherProgression rainProgression = WeatherProgression.builder()
            .progressionId("rain_progression")
            .weatherId("rain_weather")
            .progressionType("Gradual")
            .currentStage(1)
            .maxStages(3)
            .stageEffects(Map.of("Light Rain", 20, "Moderate Rain", 50, "Heavy Rain", 80))
            .progressionEvents(Arrays.asList("Light drizzle", "Steady rain", "Heavy downpour"))
            .isActive(false)
            .duration(5)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.8)
            .failureEffect("No progression")
            .progressionBonuses(Map.of("Stealth", 10, "Concealment", 15))
            .progressionAbilities(Arrays.asList("Weather Progression", "Intensity Increase"))
            .progressionMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather System")
            .stageRequirements(Map.of("Stage 1", 20, "Stage 2", 50, "Stage 3", 80))
            .stageLimitations(Arrays.asList("Gradual intensity", "Predictable pattern"))
            .isPredictable(true)
            .predictionType("Weather Forecast")
            .build();
        
        weatherProgressions.put("rain_progression", rainProgression);
        
        // Storm progression
        WeatherProgression stormProgression = WeatherProgression.builder()
            .progressionId("storm_progression")
            .weatherId("storm_weather")
            .progressionType("Rapid")
            .currentStage(1)
            .maxStages(2)
            .stageEffects(Map.of("Storm Warning", 40, "Full Storm", 90))
            .progressionEvents(Arrays.asList("Storm warning", "Full storm"))
            .isActive(false)
            .duration(3)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.7)
            .failureEffect("No progression")
            .progressionBonuses(Map.of("Stealth", 40, "Concealment", 50))
            .progressionAbilities(Arrays.asList("Storm Progression", "Rapid Intensity"))
            .progressionMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather System")
            .stageRequirements(Map.of("Stage 1", 40, "Stage 2", 90))
            .stageLimitations(Arrays.asList("Rapid intensity", "Unpredictable pattern"))
            .isPredictable(false)
            .predictionType("Storm Warning")
            .build();
        
        weatherProgressions.put("storm_progression", stormProgression);
    }
    
    /**
     * Activate weather condition
     */
    public boolean activateWeatherCondition(String conditionId, int intensity) {
        WeatherCondition condition = weatherConditions.get(conditionId);
        if (condition == null) {
            return false;
        }
        
        // Activate weather condition
        condition.setActive(true);
        condition.setIntensity(Math.min(intensity, condition.getMaxIntensity()));
        condition.setCurrentDuration(condition.getDuration());
        
        // Update weather intensity
        weatherIntensity.put(conditionId, condition.getIntensity());
        weatherActive.put(conditionId, true);
        weatherDuration.put(conditionId, condition.getDuration());
        
        // Apply weather effects
        Map<String, Integer> effects = condition.getWeatherEffects();
        for (Map.Entry<String, Integer> effect : effects.entrySet()) {
            tacticalModifiers.put(effect.getKey(), Map.of(conditionId, effect.getValue()));
        }
        
        return true;
    }
    
    /**
     * Activate environmental hazard
     */
    public boolean activateEnvironmentalHazard(String hazardId, int level) {
        EnvironmentalHazard hazard = environmentalHazards.get(hazardId);
        if (hazard == null) {
            return false;
        }
        
        // Activate environmental hazard
        hazard.setActive(true);
        hazard.setHazardLevel(Math.min(level, hazard.getMaxHazardLevel()));
        hazard.setCurrentDuration(hazard.getDuration());
        
        // Update hazard levels
        hazardLevels.put(hazardId, hazard.getHazardLevel());
        
        return true;
    }
    
    /**
     * Apply weather-based tactics
     */
    public boolean applyWeatherBasedTactics(String tacticId, String unitId) {
        WeatherBasedTactic tactic = weatherBasedTactics.get(tacticId);
        if (tactic == null) {
            return false;
        }
        
        // Check if weather requirements are met
        String weatherType = tactic.getWeatherType();
        boolean weatherActive = this.weatherActive.getOrDefault(weatherType.toLowerCase() + "_weather", false);
        
        if (!weatherActive) {
            return false;
        }
        
        // Apply tactic
        tactic.setActive(true);
        tactic.setCurrentDuration(tactic.getDuration());
        tactic.getAffectedUnits().add(unitId);
        
        // Apply tactical bonuses
        Map<String, Integer> bonuses = tactic.getTacticBonuses();
        for (Map.Entry<String, Integer> bonus : bonuses.entrySet()) {
            weatherBonuses.put(unitId + "_" + bonus.getKey(), bonus.getValue());
        }
        
        return true;
    }
    
    /**
     * Trigger environmental interaction
     */
    public boolean triggerEnvironmentalInteraction(String interactionId, String unitId) {
        EnvironmentalInteraction interaction = environmentalInteractions.get(interactionId);
        if (interaction == null) {
            return false;
        }
        
        // Trigger interaction
        interaction.setActive(true);
        interaction.setCurrentDuration(interaction.getDuration());
        interaction.getAffectedUnits().add(unitId);
        
        // Apply interaction effects
        Map<String, Integer> effects = interaction.getInteractionEffects();
        for (Map.Entry<String, Integer> effect : effects.entrySet()) {
            tacticalModifiers.put(effect.getKey(), Map.of(interactionId, effect.getValue()));
        }
        
        return true;
    }
    
    /**
     * Progress weather condition
     */
    public boolean progressWeatherCondition(String weatherId, int newStage) {
        WeatherProgression progression = weatherProgressions.get(weatherId + "_progression");
        if (progression == null) {
            return false;
        }
        
        // Progress weather
        progression.setCurrentStage(Math.min(newStage, progression.getMaxStages()));
        progression.setActive(true);
        progression.setCurrentDuration(progression.getDuration());
        
        // Update weather intensity based on stage
        Map<String, Integer> stageEffects = progression.getStageEffects();
        String stageName = "Stage " + progression.getCurrentStage();
        if (stageEffects.containsKey(stageName)) {
            weatherIntensity.put(weatherId, stageEffects.get(stageName));
        }
        
        return true;
    }
    
    /**
     * Get weather condition
     */
    public WeatherCondition getWeatherCondition(String conditionId) {
        return weatherConditions.get(conditionId);
    }
    
    /**
     * Get environmental hazard
     */
    public EnvironmentalHazard getEnvironmentalHazard(String hazardId) {
        return environmentalHazards.get(hazardId);
    }
    
    /**
     * Get weather-based tactic
     */
    public WeatherBasedTactic getWeatherBasedTactic(String tacticId) {
        return weatherBasedTactics.get(tacticId);
    }
    
    /**
     * Get environmental interaction
     */
    public EnvironmentalInteraction getEnvironmentalInteraction(String interactionId) {
        return environmentalInteractions.get(interactionId);
    }
    
    /**
     * Get weather intensity
     */
    public int getWeatherIntensity(String weatherId) {
        return weatherIntensity.getOrDefault(weatherId, 0);
    }
    
    /**
     * Get hazard level
     */
    public int getHazardLevel(String hazardId) {
        return hazardLevels.getOrDefault(hazardId, 0);
    }
    
    /**
     * Get weather active status
     */
    public boolean getWeatherActive(String weatherId) {
        return weatherActive.getOrDefault(weatherId, false);
    }
    
    /**
     * Get active weather effects
     */
    public List<String> getActiveWeatherEffects(String weatherId) {
        return activeWeatherEffects.getOrDefault(weatherId, new ArrayList<>());
    }
    
    /**
     * Get affected units
     */
    public List<String> getAffectedUnits(String effectId) {
        return affectedUnits.getOrDefault(effectId, new ArrayList<>());
    }
    
    /**
     * Get weather duration
     */
    public int getWeatherDuration(String weatherId) {
        return weatherDuration.getOrDefault(weatherId, 0);
    }
    
    /**
     * Get weather bonus
     */
    public int getWeatherBonus(String unitId, String bonusType) {
        return weatherBonuses.getOrDefault(unitId + "_" + bonusType, 0);
    }
    
    /**
     * Get tactical modifier
     */
    public int getTacticalModifier(String modifierType, String sourceId) {
        Map<String, Integer> modifiers = tacticalModifiers.get(modifierType);
        if (modifiers != null) {
            return modifiers.getOrDefault(sourceId, 0);
        }
        return 0;
    }
    
    /**
     * Get total weather intensity
     */
    public int getTotalWeatherIntensity() {
        return weatherIntensity.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get total hazard levels
     */
    public int getTotalHazardLevels() {
        return hazardLevels.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Get total weather active
     */
    public long getTotalWeatherActive() {
        return weatherActive.values().stream().filter(active -> active).count();
    }
    
    /**
     * Get weather condition count
     */
    public int getWeatherConditionCount() {
        return weatherConditions.size();
    }
    
    /**
     * Get environmental hazard count
     */
    public int getEnvironmentalHazardCount() {
        return environmentalHazards.size();
    }
    
    /**
     * Get weather-based tactic count
     */
    public int getWeatherBasedTacticCount() {
        return weatherBasedTactics.size();
    }
    
    /**
     * Get environmental interaction count
     */
    public int getEnvironmentalInteractionCount() {
        return environmentalInteractions.size();
    }
    
    /**
     * Get weather progression count
     */
    public int getWeatherProgressionCount() {
        return weatherProgressions.size();
    }
    
    /**
     * Get weather event count
     */
    public int getWeatherEventCount() {
        return weatherEvents.size();
    }
}


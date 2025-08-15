package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Weather System for dynamic environmental effects.
 * Implements weather patterns and their impact on combat.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherSystem {
    
    private String weatherSystemId;
    private Map<String, WeatherPattern> weatherPatterns;
    private Map<String, WeatherForecast> weatherForecasts;
    private Map<String, WeatherEffect> weatherEffects;
    private Map<String, WeatherModifier> weatherModifiers;
    private Map<String, WeatherEvent> weatherEvents;
    private Map<String, WeatherCycle> weatherCycles;
    private Map<String, List<String>> weatherHistory;
    private Map<String, Map<String, Integer>> weatherIntensities;
    private Map<String, List<String>> activeWeather;
    private Map<String, Integer> weatherDurations;
    private Map<String, Boolean> weatherStates;
    private int totalWeatherPatterns;
    private int maxWeatherIntensity;
    private boolean isWeatherActive;
    

    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherPattern {
        private String patternId;
        private String patternName;
        private WeatherType weatherType;
        private int baseIntensity;
        private int maxIntensity;
        private Map<String, Integer> weatherEffects;
        private List<String> weatherEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> weatherBonuses;
        private List<String> weatherAbilities;
        private String weatherMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum WeatherType {
            CLEAR,              // Clear weather
            CLOUDY,             // Cloudy weather
            RAIN,               // Rain weather
            STORM,              // Storm weather
            SNOW,               // Snow weather
            FOG,                // Fog weather
            WIND,               // Windy weather
            DUST_STORM,         // Dust storm
            ACID_RAIN,          // Acid rain
            RADIATION_STORM,    // Radiation storm
            PLASMA_STORM,       // Plasma storm
            ELECTRICAL_STORM,   // Electrical storm
            TOXIC_FOG,          // Toxic fog
            ICE_STORM,          // Ice storm
            METEOR_SHOWER       // Meteor shower
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherForecast {
        private String forecastId;
        private String forecastName;
        private ForecastType forecastType;
        private int forecastAccuracy;
        private int maxForecastAccuracy;
        private Map<String, Integer> forecastEffects;
        private List<String> forecastEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> forecastBonuses;
        private List<String> forecastAbilities;
        private String forecastMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum ForecastType {
            SHORT_TERM,         // Short-term forecast
            MEDIUM_TERM,        // Medium-term forecast
            LONG_TERM,          // Long-term forecast
            SEASONAL,           // Seasonal forecast
            ANNUAL,             // Annual forecast
            CLIMATE,            // Climate forecast
            EXTREME,            // Extreme weather forecast
            EMERGENCY           // Emergency weather forecast
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherEffect {
        private String effectId;
        private String effectName;
        private EffectType effectType;
        private int effectIntensity;
        private int maxEffectIntensity;
        private Map<String, Integer> effectModifiers;
        private List<String> effectEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> effectBonuses;
        private List<String> effectAbilities;
        private String effectMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum EffectType {
            VISIBILITY_MODIFIER,    // Visibility changes
            MOVEMENT_MODIFIER,      // Movement changes
            ACCURACY_MODIFIER,      // Accuracy changes
            DAMAGE_MODIFIER,        // Damage changes
            ARMOR_MODIFIER,         // Armor changes
            EQUIPMENT_MODIFIER,     // Equipment changes
            PSIONIC_MODIFIER,       // Psionic changes
            MUTATION_RISK,          // Mutation risk
            FATIGUE_MODIFIER,       // Fatigue changes
            MORALE_MODIFIER,        // Morale changes
            COVER_MODIFIER,         // Cover changes
            RANGE_MODIFIER,         // Range changes
            STEALTH_MODIFIER,       // Stealth changes
            HEALING_MODIFIER,       // Healing changes
            REPAIR_MODIFIER         // Repair changes
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherModifier {
        private String modifierId;
        private String modifierName;
        private ModifierType modifierType;
        private int modifierValue;
        private int maxModifierValue;
        private Map<String, Integer> modifierEffects;
        private List<String> modifierEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> modifierBonuses;
        private List<String> modifierAbilities;
        private String modifierMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum ModifierType {
            POSITIVE_MODIFIER,      // Positive effects
            NEGATIVE_MODIFIER,      // Negative effects
            NEUTRAL_MODIFIER,       // Neutral effects
            EXTREME_MODIFIER,       // Extreme effects
            MILD_MODIFIER,          // Mild effects
            SEVERE_MODIFIER,        // Severe effects
            TEMPORARY_MODIFIER,     // Temporary effects
            PERMANENT_MODIFIER      // Permanent effects
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherEvent {
        private String eventId;
        private String eventName;
        private EventType eventType;
        private int eventIntensity;
        private int maxEventIntensity;
        private Map<String, Integer> eventEffects;
        private List<String> eventTriggers;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> eventBonuses;
        private List<String> eventAbilities;
        private String eventMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum EventType {
            LIGHTNING_STRIKE,       // Lightning strike
            TORNADO,                // Tornado
            HURRICANE,              // Hurricane
            EARTHQUAKE,             // Earthquake
            VOLCANIC_ERUPTION,      // Volcanic eruption
            METEOR_IMPACT,          // Meteor impact
            ALIEN_INTERFERENCE,     // Alien interference
            TECHNOLOGICAL_FAILURE,  // Technological failure
            MAGNETIC_STORM,         // Magnetic storm
            GRAVITY_ANOMALY,        // Gravity anomaly
            TIME_DISTORTION,        // Time distortion
            DIMENSIONAL_RIFT,       // Dimensional rift
            PSYCHIC_STORM,          // Psychic storm
            QUANTUM_FLUX,           // Quantum flux
            REALITY_SHIFT           // Reality shift
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherCycle {
        private String cycleId;
        private String cycleName;
        private CycleType cycleType;
        private int cycleDuration;
        private int currentDuration;
        private List<String> cyclePhases;
        private Map<String, Integer> cycleEffects;
        private List<String> cycleEvents;
        private boolean isActive;
        private String assignedPattern;
        private String cycleStatus;
        private int cycleProgress;
        private boolean isCompleted;
        private String completionDate;
        private String cycleNotes;
        private Map<String, Integer> cycleBonuses;
        private List<String> cycleRequirements;
        private int cycleCost;
        private String cycleDescription;
        
        public enum CycleType {
            DAILY_CYCLE,            // Daily weather cycle
            WEEKLY_CYCLE,           // Weekly weather cycle
            MONTHLY_CYCLE,          // Monthly weather cycle
            SEASONAL_CYCLE,         // Seasonal weather cycle
            ANNUAL_CYCLE,           // Annual weather cycle
            CLIMATIC_CYCLE,         // Climatic weather cycle
            EXTREME_CYCLE,          // Extreme weather cycle
            EMERGENCY_CYCLE         // Emergency weather cycle
        }
    }
    

    
    /**
     * Initialize the weather system
     */
    private void initializeSystem() {
        // ToDo: Реализовать систему погоды
        // - Weather Effects System (полная реализация)
        // - Environmental Interaction System
        // - Terrain Destruction System
        // - Weather patterns and cycles
        // - Weather forecasting system
        // - Weather impact on combat
        // - Weather-based mission modifiers
        
        initializeWeatherPatterns();
        initializeWeatherEffects();
    }
    
    /**
     * Initialize weather patterns
     */
    private void initializeWeatherPatterns() {
        // Clear weather
        WeatherPattern clear = WeatherPattern.builder()
            .patternId("CLEAR")
            .patternName("Clear Weather")
            .weatherType(WeatherPattern.WeatherType.CLEAR)
            .baseIntensity(10)
            .maxIntensity(20)
            .weatherEffects(Map.of("visibility", 100, "movement", 100, "accuracy", 100))
            .weatherEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Default weather")
            .successRate(1.0)
            .failureEffect("No effect")
            .weatherBonuses(new HashMap<>())
            .weatherAbilities(new ArrayList<>())
            .weatherMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Always")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(true)
            .permanentCondition("Default")
            .build();
        
        weatherPatterns.put("CLEAR", clear);
        
        // Rain weather
        WeatherPattern rain = WeatherPattern.builder()
            .patternId("RAIN")
            .patternName("Rain Weather")
            .weatherType(WeatherPattern.WeatherType.RAIN)
            .baseIntensity(30)
            .maxIntensity(50)
            .weatherEffects(Map.of("visibility", 80, "movement", 90, "accuracy", 85))
            .weatherEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.8)
            .failureEffect("No rain")
            .weatherBonuses(new HashMap<>())
            .weatherAbilities(new ArrayList<>())
            .weatherMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather system")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        weatherPatterns.put("RAIN", rain);
        
        // Storm weather
        WeatherPattern storm = WeatherPattern.builder()
            .patternId("STORM")
            .patternName("Storm Weather")
            .weatherType(WeatherPattern.WeatherType.STORM)
            .baseIntensity(60)
            .maxIntensity(80)
            .weatherEffects(Map.of("visibility", 60, "movement", 70, "accuracy", 70))
            .weatherEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.6)
            .failureEffect("No storm")
            .weatherBonuses(new HashMap<>())
            .weatherAbilities(new ArrayList<>())
            .weatherMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather system")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        weatherPatterns.put("STORM", storm);
    }
    
    /**
     * Initialize weather effects
     */
    private void initializeWeatherEffects() {
        // Visibility modifier
        WeatherEffect visibility = WeatherEffect.builder()
            .effectId("VISIBILITY_MODIFIER")
            .effectName("Visibility Modifier")
            .effectType(WeatherEffect.EffectType.VISIBILITY_MODIFIER)
            .effectIntensity(20)
            .maxEffectIntensity(50)
            .effectModifiers(Map.of("view_range", -20, "detection", -15))
            .effectEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.9)
            .failureEffect("No effect")
            .effectBonuses(new HashMap<>())
            .effectAbilities(new ArrayList<>())
            .effectMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather system")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        weatherEffects.put("VISIBILITY_MODIFIER", visibility);
        
        // Movement modifier
        WeatherEffect movement = WeatherEffect.builder()
            .effectId("MOVEMENT_MODIFIER")
            .effectName("Movement Modifier")
            .effectType(WeatherEffect.EffectType.MOVEMENT_MODIFIER)
            .effectIntensity(15)
            .maxEffectIntensity(40)
            .effectModifiers(Map.of("movement_range", -15, "mobility", -10))
            .effectEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.9)
            .failureEffect("No effect")
            .effectBonuses(new HashMap<>())
            .effectAbilities(new ArrayList<>())
            .effectMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather system")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        weatherEffects.put("MOVEMENT_MODIFIER", movement);
        
        // Accuracy modifier
        WeatherEffect accuracy = WeatherEffect.builder()
            .effectId("ACCURACY_MODIFIER")
            .effectName("Accuracy Modifier")
            .effectType(WeatherEffect.EffectType.ACCURACY_MODIFIER)
            .effectIntensity(25)
            .maxEffectIntensity(60)
            .effectModifiers(Map.of("accuracy", -25, "critical_chance", -10))
            .effectEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.9)
            .failureEffect("No effect")
            .effectBonuses(new HashMap<>())
            .effectAbilities(new ArrayList<>())
            .effectMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather system")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        weatherEffects.put("ACCURACY_MODIFIER", accuracy);
    }
    
    /**
     * Create weather pattern
     */
    public boolean createWeatherPattern(String patternId, String patternName, WeatherPattern.WeatherType weatherType) {
        // Initialize system if not already done
        if (weatherPatterns.isEmpty()) {
            initializeSystem();
        }
        
        if (weatherPatterns.containsKey(patternId)) {
            return false; // Pattern already exists
        }
        
        WeatherPattern pattern = WeatherPattern.builder()
            .patternId(patternId)
            .patternName(patternName)
            .weatherType(weatherType)
            .baseIntensity(30)
            .maxIntensity(70)
            .weatherEffects(new HashMap<>())
            .weatherEvents(new ArrayList<>())
            .isActive(false)
            .duration(0)
            .currentDuration(0)
            .activationCondition("Weather change")
            .successRate(0.8)
            .failureEffect("No weather change")
            .weatherBonuses(new HashMap<>())
            .weatherAbilities(new ArrayList<>())
            .weatherMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Weather system")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        weatherPatterns.put(patternId, pattern);
        weatherHistory.put(patternId, new ArrayList<>());
        weatherIntensities.put(patternId, new HashMap<>());
        totalWeatherPatterns++;
        
        return true;
    }
    
    /**
     * Activate weather pattern
     */
    public boolean activateWeatherPattern(String patternId, int intensity) {
        WeatherPattern pattern = weatherPatterns.get(patternId);
        if (pattern == null) {
            return false;
        }
        
        pattern.setActive(true);
        pattern.setBaseIntensity(Math.min(intensity, pattern.getMaxIntensity()));
        pattern.setCurrentDuration(0);
        pattern.setWeatherMethod("Active");
        
        // Apply weather effects
        applyWeatherEffects(pattern);
        
        // Track active weather
        activeWeather.put(patternId, new ArrayList<>());
        weatherDurations.put(patternId, pattern.getDuration());
        weatherStates.put(patternId, true);
        
        return true;
    }
    
    /**
     * Apply weather effects
     */
    private void applyWeatherEffects(WeatherPattern pattern) {
        // Apply visibility effects
        if (pattern.getWeatherType() == WeatherPattern.WeatherType.RAIN || 
            pattern.getWeatherType() == WeatherPattern.WeatherType.STORM) {
            WeatherEffect visibility = weatherEffects.get("VISIBILITY_MODIFIER");
            if (visibility != null) {
                visibility.setActive(true);
                visibility.setEffectIntensity(pattern.getBaseIntensity() / 2);
            }
        }
        
        // Apply movement effects
        if (pattern.getWeatherType() == WeatherPattern.WeatherType.STORM) {
            WeatherEffect movement = weatherEffects.get("MOVEMENT_MODIFIER");
            if (movement != null) {
                movement.setActive(true);
                movement.setEffectIntensity(pattern.getBaseIntensity() / 3);
            }
        }
        
        // Apply accuracy effects
        if (pattern.getWeatherType() == WeatherPattern.WeatherType.RAIN || 
            pattern.getWeatherType() == WeatherPattern.WeatherType.STORM) {
            WeatherEffect accuracy = weatherEffects.get("ACCURACY_MODIFIER");
            if (accuracy != null) {
                accuracy.setActive(true);
                accuracy.setEffectIntensity(pattern.getBaseIntensity() / 2);
            }
        }
    }
    
    /**
     * Process weather system
     */
    public void processWeatherSystem() {
        // Process active weather patterns
        for (WeatherPattern pattern : weatherPatterns.values()) {
            if (pattern.isActive()) {
                processWeatherPattern(pattern);
            }
        }
        
        // Process weather effects
        for (WeatherEffect effect : weatherEffects.values()) {
            if (effect.isActive()) {
                processWeatherEffect(effect);
            }
        }
        
        // Update weather states
        updateWeatherStates();
    }
    
    /**
     * Process weather pattern
     */
    private void processWeatherPattern(WeatherPattern pattern) {
        pattern.setCurrentDuration(pattern.getCurrentDuration() + 1);
        
        // Check if weather should change
        if (pattern.getCurrentDuration() >= pattern.getDuration() && pattern.getDuration() > 0) {
            deactivateWeatherPattern(pattern);
        } else {
            // Update weather intensity
            int progress = (pattern.getCurrentDuration() * 100) / Math.max(pattern.getDuration(), 1);
            pattern.setWeatherMethod("Active (" + progress + "%)");
        }
    }
    
    /**
     * Process weather effect
     */
    private void processWeatherEffect(WeatherEffect effect) {
        effect.setCurrentDuration(effect.getCurrentDuration() + 1);
        
        // Check if effect should expire
        if (effect.getCurrentDuration() >= effect.getDuration() && effect.getDuration() > 0) {
            effect.setActive(false);
            effect.setCurrentDuration(0);
        }
    }
    
    /**
     * Deactivate weather pattern
     */
    private void deactivateWeatherPattern(WeatherPattern pattern) {
        pattern.setActive(false);
        pattern.setCurrentDuration(0);
        pattern.setWeatherMethod("Inactive");
        
        // Deactivate associated effects
        for (WeatherEffect effect : weatherEffects.values()) {
            if (effect.isActive()) {
                effect.setActive(false);
                effect.setCurrentDuration(0);
            }
        }
        
        // Remove from active weather
        activeWeather.remove(pattern.getPatternId());
        weatherStates.put(pattern.getPatternId(), false);
    }
    
    /**
     * Update weather states
     */
    private void updateWeatherStates() {
        boolean hasActiveWeather = false;
        
        for (WeatherPattern pattern : weatherPatterns.values()) {
            if (pattern.isActive()) {
                hasActiveWeather = true;
                break;
            }
        }
        
        isWeatherActive = hasActiveWeather;
    }
    
    /**
     * Get weather pattern by ID
     */
    public WeatherPattern getWeatherPattern(String patternId) {
        return weatherPatterns.get(patternId);
    }
    
    /**
     * Get weather effect by ID
     */
    public WeatherEffect getWeatherEffect(String effectId) {
        return weatherEffects.get(effectId);
    }
    
    /**
     * Get active weather patterns
     */
    public List<String> getActiveWeatherPatterns() {
        List<String> activePatterns = new ArrayList<>();
        for (WeatherPattern pattern : weatherPatterns.values()) {
            if (pattern.isActive()) {
                activePatterns.add(pattern.getPatternId());
            }
        }
        return activePatterns;
    }
    
    /**
     * Get weather intensity
     */
    public int getWeatherIntensity(String patternId) {
        WeatherPattern pattern = weatherPatterns.get(patternId);
        return pattern != null ? pattern.getBaseIntensity() : 0;
    }
    
    /**
     * Check if weather is active
     */
    public boolean isWeatherActive() {
        return isWeatherActive;
    }
    
    /**
     * Get total weather patterns
     */
    public int getTotalWeatherPatterns() {
        return totalWeatherPatterns;
    }
    
    /**
     * Get weather duration
     */
    public int getWeatherDuration(String patternId) {
        return weatherDurations.getOrDefault(patternId, 0);
    }
    
    /**
     * Check if weather pattern is active
     */
    public boolean isWeatherPatternActive(String patternId) {
        return weatherStates.getOrDefault(patternId, false);
    }
}

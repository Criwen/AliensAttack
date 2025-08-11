package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.model.AdvancedCombatEnvironmentWeatherEffectsSystem.WeatherEvent;
import com.aliensattack.core.model.AdvancedEnvironmentalInteractionSystem.EnvironmentalHazard;
import com.aliensattack.core.model.AdvancedEnvironmentalInteractionSystem.DestructibleObject;
import com.aliensattack.core.model.AdvancedEnvironmentalInteractionSystem.EnvironmentalManipulation;

import java.util.*;

/**
 * Comprehensive Weather System Manager for XCOM 2 Tactical Combat
 * Integrates weather effects, environmental interactions, and terrain destruction
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherSystemManager {
    
    private WeatherSystem weatherSystem;
    private AdvancedEnvironmentalInteractionSystem environmentalSystem;
    private AdvancedTerrainInteractionSystem terrainSystem;
    private List<Unit> affectedUnits;
    private Map<String, WeatherEvent> weatherEvents;
    private Map<String, EnvironmentalHazard> activeHazards;
    private Map<String, DestructibleObject> destructibleObjects;
    private boolean isWeatherActive;
    private int weatherUpdateFrequency;
    private Random random;
    

    
    /**
     * Initialize the weather system manager
     */
    public void initialize() {
        if (weatherSystem == null) {
            weatherSystem = new WeatherSystem();
        }
        if (environmentalSystem == null) {
            environmentalSystem = new AdvancedEnvironmentalInteractionSystem();
        }
        if (terrainSystem == null) {
            terrainSystem = new AdvancedTerrainInteractionSystem("default", "Default Terrain", com.aliensattack.core.enums.TerrainType.GROUND);
        }
        if (affectedUnits == null) {
            affectedUnits = new ArrayList<>();
        }
        if (weatherEvents == null) {
            weatherEvents = new HashMap<>();
        }
        if (activeHazards == null) {
            activeHazards = new HashMap<>();
        }
        if (destructibleObjects == null) {
            destructibleObjects = new HashMap<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        isWeatherActive = false;
        weatherUpdateFrequency = 5; // Update every 5 turns
        
        initializeWeatherSystem();
    }
    
    /**
     * Initialize the weather system
     */
    private void initializeWeatherSystem() {
        // Set initial weather to clear
        weatherSystem.setWeather(WeatherSystem.WeatherType.CLEAR, WeatherSystem.IntensityLevel.LIGHT, 0);
        weatherSystem.setDynamic(true);
        weatherSystem.setChangeFrequency(10); // 10% chance per turn
    }
    
    /**
     * Update weather system
     */
    public void updateWeatherSystem() {
        // Update weather
        weatherSystem.updateWeather();
        
        // Apply weather effects to all affected units
        for (Unit unit : affectedUnits) {
            weatherSystem.applyWeatherEffectsToUnit(unit);
        }
        
        // Update environmental hazards
        updateEnvironmentalHazards();
        
        // Update terrain effects
        updateTerrainEffects();
        
        // Check for weather events
        checkWeatherEvents();
        
        // Update weather status
        isWeatherActive = weatherSystem.isWeatherActive();
    }
    
    /**
     * Update environmental hazards
     */
    private void updateEnvironmentalHazards() {
        Iterator<Map.Entry<String, EnvironmentalHazard>> iterator = activeHazards.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, EnvironmentalHazard> entry = iterator.next();
            EnvironmentalHazard hazard = entry.getValue();
            
            if (hazard.getDuration() > 0) {
                hazard.setDuration(hazard.getDuration() - 1);
                
                // Apply hazard effects to units in range
                applyHazardEffectsToUnits(hazard);
            } else {
                // Remove expired hazard
                iterator.remove();
            }
        }
    }
    
    /**
     * Update terrain effects
     */
    private void updateTerrainEffects() {
        // Update terrain transformation progress
        terrainSystem.updateTransformationProgress();
        
        // Update hazard durations on terrain
        terrainSystem.updateHazardDurations();
        
        // Update interaction cooldowns
        terrainSystem.updateInteractionCooldowns();
    }
    
    /**
     * Check for weather events
     */
    private void checkWeatherEvents() {
        if (weatherSystem.isWeatherActive() && random.nextInt(100) < 5) {
            // 5% chance to trigger weather event
            triggerWeatherEvent();
        }
    }
    
    /**
     * Trigger weather event
     */
    private void triggerWeatherEvent() {
        WeatherSystem.WeatherType currentWeather = weatherSystem.getCurrentWeather();
        WeatherSystem.IntensityLevel intensity = weatherSystem.getIntensity();
        
        WeatherEvent event = createWeatherEvent(currentWeather, intensity);
        weatherEvents.put(event.getEventId(), event);
        
        // Apply event effects
        applyWeatherEventEffects(event);
    }
    
    /**
     * Create weather event
     */
    private WeatherEvent createWeatherEvent(WeatherSystem.WeatherType weatherType, WeatherSystem.IntensityLevel intensity) {
        String eventId = UUID.randomUUID().toString();
        
        Map<String, Integer> eventEffects = new HashMap<>();
        switch (weatherType) {
            case STORM:
                eventEffects.put("lightning_strike", 50);
                eventEffects.put("wind_gust", 30);
                break;
            case FIRE_STORM:
                eventEffects.put("fire_burst", 75);
                eventEffects.put("heat_wave", 40);
                break;
            case ICE_STORM:
                eventEffects.put("ice_burst", 60);
                eventEffects.put("freeze_blast", 35);
                break;
            case ELECTRICAL_STORM:
                eventEffects.put("electric_arc", 70);
                eventEffects.put("power_surge", 45);
                break;
            case PLASMA_STORM:
                eventEffects.put("plasma_burst", 80);
                eventEffects.put("energy_surge", 50);
                break;
            default:
                eventEffects.put("weather_effect", 25);
                break;
        }
        
        return WeatherEvent.builder()
                .eventId(eventId)
                .eventType("weather_event")
                .sourceId("weather_system")
                .targetId("environment")
                .timestamp((int) (System.currentTimeMillis() / 1000))
                .eventEffects(eventEffects)
                .affectedUnits(new ArrayList<>())
                .isSuccessful(true)
                .result("Weather event triggered")
                .energyCost(0)
                .energyGained(0)
                .eventDescription("Weather event: " + weatherType.name() + " with " + intensity.name() + " intensity")
                .bonuses(new HashMap<>())
                .consequences(new ArrayList<>())
                .eventLocation("global")
                .duration(3)
                .isPermanent(false)
                .weatherStatus(weatherType.name())
                .weatherLevel(intensity.ordinal())
                .build();
    }
    
    /**
     * Apply weather event effects
     */
    private void applyWeatherEventEffects(WeatherEvent event) {
        for (Map.Entry<String, Integer> effect : event.getEventEffects().entrySet()) {
            String effectType = effect.getKey();
            int effectValue = effect.getValue();
            
            switch (effectType) {
                case "lightning_strike":
                    applyLightningStrike(effectValue);
                    break;
                case "fire_burst":
                    applyFireBurst(effectValue);
                    break;
                case "ice_burst":
                    applyIceBurst(effectValue);
                    break;
                case "electric_arc":
                    applyElectricArc(effectValue);
                    break;
                case "plasma_burst":
                    applyPlasmaBurst(effectValue);
                    break;
                default:
                    applyGenericWeatherEffect(effectValue);
                    break;
            }
        }
    }
    
    /**
     * Apply lightning strike effect
     */
    private void applyLightningStrike(int effectValue) {
        // Find random unit to strike
        if (!affectedUnits.isEmpty()) {
            Unit target = affectedUnits.get(random.nextInt(affectedUnits.size()));
            target.takeDamage(effectValue);
            
            StatusEffectData stunnedEffect = new StatusEffectData(StatusEffect.STUNNED, 2, effectValue);
            target.addStatusEffect(stunnedEffect);
        }
    }
    
    /**
     * Apply fire burst effect
     */
    private void applyFireBurst(int effectValue) {
        // Create fire hazard
        EnvironmentalHazard fireHazard = EnvironmentalHazard.builder()
                .hazardId(UUID.randomUUID().toString())
                .hazardName("Fire Burst")
                .hazardType(EnvironmentalHazard.HazardType.FIRE)
                .position(new Position(random.nextInt(20), random.nextInt(20), 0))
                .hazardRadius(3)
                .hazardEffects(Map.of("damage", effectValue))
                .duration(3)
                .isActive(true)
                .isPermanent(false)
                .hazardSource("weather_event")
                .build();
        
        activeHazards.put(fireHazard.getHazardId(), fireHazard);
    }
    
    /**
     * Apply ice burst effect
     */
    private void applyIceBurst(int effectValue) {
        // Apply freezing effect to all units
        for (Unit unit : affectedUnits) {
            StatusEffectData freezingEffect = new StatusEffectData(StatusEffect.FREEZING, 2, effectValue);
            unit.addStatusEffect(freezingEffect);
        }
    }
    
    /**
     * Apply electric arc effect
     */
    private void applyElectricArc(int effectValue) {
        // Apply electrical damage to random unit
        if (!affectedUnits.isEmpty()) {
            Unit target = affectedUnits.get(random.nextInt(affectedUnits.size()));
            target.takeDamage(effectValue);
            
            StatusEffectData electrocutedEffect = new StatusEffectData(StatusEffect.ELECTROCUTED, 2, effectValue);
            target.addStatusEffect(electrocutedEffect);
        }
    }
    
    /**
     * Apply plasma burst effect
     */
    private void applyPlasmaBurst(int effectValue) {
        // Apply plasma damage to all units
        for (Unit unit : affectedUnits) {
            unit.takeDamage(effectValue);
            
            StatusEffectData burningEffect = new StatusEffectData(StatusEffect.BURNING, 3, effectValue);
            unit.addStatusEffect(burningEffect);
        }
    }
    
    /**
     * Apply generic weather effect
     */
    private void applyGenericWeatherEffect(int effectValue) {
        // Apply minor damage to random unit
        if (!affectedUnits.isEmpty()) {
            Unit target = affectedUnits.get(random.nextInt(affectedUnits.size()));
            target.takeDamage(effectValue / 2);
        }
    }
    
    /**
     * Apply hazard effects to units
     */
    private void applyHazardEffectsToUnits(EnvironmentalHazard hazard) {
        for (Unit unit : affectedUnits) {
            double distance = calculateDistance(unit.getPosition(), hazard.getPosition());
            if (distance <= hazard.getHazardRadius()) {
                applyHazardEffectToUnit(unit, hazard);
            }
        }
    }
    
    /**
     * Apply hazard effect to unit
     */
    private void applyHazardEffectToUnit(Unit unit, EnvironmentalHazard hazard) {
        for (Map.Entry<String, Integer> effect : hazard.getHazardEffects().entrySet()) {
            int effectValue = effect.getValue();
            
            switch (hazard.getHazardType()) {
                case FIRE:
                    unit.takeDamage(effectValue);
                    StatusEffectData burningEffect = new StatusEffectData(StatusEffect.BURNING, 3, effectValue);
                    unit.addStatusEffect(burningEffect);
                    break;
                case POISON_GAS:
                    unit.takeDamage(effectValue / 2);
                    StatusEffectData poisonedEffect = new StatusEffectData(StatusEffect.POISONED, 4, effectValue);
                    unit.addStatusEffect(poisonedEffect);
                    break;
                case ELECTRIC_FIELD:
                    unit.takeDamage(effectValue);
                    StatusEffectData stunnedEffect = new StatusEffectData(StatusEffect.STUNNED, 2, effectValue);
                    unit.addStatusEffect(stunnedEffect);
                    break;
                case RADIATION:
                    unit.takeDamage(effectValue / 3);
                    StatusEffectData radiationEffect = new StatusEffectData(StatusEffect.RADIATION, 5, effectValue);
                    unit.addStatusEffect(radiationEffect);
                    break;
                case ACID_POOL:
                    unit.takeDamage(effectValue);
                    StatusEffectData acidEffect = new StatusEffectData(StatusEffect.ACID_BURN, 3, effectValue);
                    unit.addStatusEffect(acidEffect);
                    break;
                case EXPLOSIVE:
                    unit.takeDamage(effectValue * 2);
                    StatusEffectData knockedDownEffect = new StatusEffectData(StatusEffect.KNOCKED_DOWN, 1, effectValue);
                    unit.addStatusEffect(knockedDownEffect);
                    break;
                case TOXIC_WASTE:
                    unit.takeDamage(effectValue / 2);
                    StatusEffectData toxicEffect = new StatusEffectData(StatusEffect.POISONED, 4, effectValue);
                    unit.addStatusEffect(toxicEffect);
                    break;
                case PLASMA_FIELD:
                    unit.takeDamage(effectValue);
                    StatusEffectData plasmaEffect = new StatusEffectData(StatusEffect.BURNING, 2, effectValue);
                    unit.addStatusEffect(plasmaEffect);
                    break;
            }
        }
    }
    
    /**
     * Add unit to weather system
     */
    public void addUnit(Unit unit) {
        if (!affectedUnits.contains(unit)) {
            affectedUnits.add(unit);
            weatherSystem.affectUnit(unit);
        }
    }
    
    /**
     * Remove unit from weather system
     */
    public void removeUnit(Unit unit) {
        affectedUnits.remove(unit);
        weatherSystem.removeUnit(unit);
    }
    
    /**
     * Set weather
     */
    public void setWeather(WeatherSystem.WeatherType weatherType, WeatherSystem.IntensityLevel intensity, int duration) {
        weatherSystem.setWeather(weatherType, intensity, duration);
        isWeatherActive = weatherSystem.isWeatherActive();
    }
    
    /**
     * Create environmental hazard
     */
    public void createHazard(EnvironmentalHazard hazard) {
        activeHazards.put(hazard.getHazardId(), hazard);
    }
    
    /**
     * Destroy object
     */
    public boolean destroyObject(String objectId, Unit destroyingUnit, String weaponType) {
        return environmentalSystem.destroyObject(objectId, destroyingUnit, weaponType);
    }
    
    /**
     * Interact with environment
     */
    public boolean interactWithEnvironment(String objectId, Unit interactingUnit) {
        return environmentalSystem.interactWithObject(objectId, interactingUnit);
    }
    
    /**
     * Manipulate environment
     */
    public boolean manipulateEnvironment(EnvironmentalManipulation manipulation) {
        return environmentalSystem.manipulateEnvironment(manipulation);
    }
    
    /**
     * Get weather description
     */
    public String getWeatherDescription() {
        return weatherSystem.getWeatherDescription();
    }
    
    /**
     * Get weather status
     */
    public boolean isWeatherActive() {
        return isWeatherActive;
    }
    
    /**
     * Get active hazards count
     */
    public int getActiveHazardsCount() {
        return activeHazards.size();
    }
    
    /**
     * Get weather events count
     */
    public int getWeatherEventsCount() {
        return weatherEvents.size();
    }
    
    /**
     * Calculate distance between positions
     */
    private double calculateDistance(Position pos1, Position pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Get comprehensive weather status
     */
    public String getComprehensiveWeatherStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Weather System Status:\n");
        status.append("- Current Weather: ").append(weatherSystem.getCurrentWeather()).append("\n");
        status.append("- Intensity: ").append(weatherSystem.getIntensity()).append("\n");
        status.append("- Duration: ").append(weatherSystem.getDuration()).append(" turns\n");
        status.append("- Active Hazards: ").append(getActiveHazardsCount()).append("\n");
        status.append("- Weather Events: ").append(getWeatherEventsCount()).append("\n");
        status.append("- Affected Units: ").append(affectedUnits.size()).append("\n");
        status.append("- Weather Active: ").append(isWeatherActive).append("\n");
        
        return status.toString();
    }
}

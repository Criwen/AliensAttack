package com.aliensattack.core.systems;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.data.StatusEffectData;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.systems.CombatEnvironmentWeatherEffectsSystem.WeatherEvent;
import com.aliensattack.core.systems.EnvironmentalInteractionSystem.EnvironmentalHazard;
import com.aliensattack.core.systems.EnvironmentalInteractionSystem.DestructibleObject;
import com.aliensattack.core.systems.EnvironmentalInteractionSystem.EnvironmentalManipulation;

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
    private EnvironmentalInteractionSystem environmentalSystem;
    private TerrainInteractionSystem terrainSystem;
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
            environmentalSystem = new EnvironmentalInteractionSystem();
        }
        if (terrainSystem == null) {
            terrainSystem = new TerrainInteractionSystem("default", "Default Terrain", com.aliensattack.core.enums.TerrainType.GROUND);
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
        initializeWeatherEvents();
        initializeEnvironmentalHazards();
        initializeDestructibleObjects();
    }
    
    /**
     * Initialize the weather system
     */
    private void initializeWeatherSystem() {
        // Set initial weather to clear
        weatherSystem.setWeatherStates(new HashMap<>());
        weatherSystem.getWeatherStates().put("CLEAR", true);
        isWeatherActive = true;
    }
    
    /**
     * Update weather system
     */
    public void updateWeatherSystem() {
        // Update weather patterns
        if (weatherSystem.getWeatherPatterns() != null) {
            for (WeatherSystem.WeatherPattern pattern : weatherSystem.getWeatherPatterns().values()) {
                if (pattern.isActive()) {
                    pattern.setCurrentDuration(pattern.getCurrentDuration() + 1);
                    if (pattern.getCurrentDuration() >= pattern.getDuration()) {
                        pattern.setActive(false);
                    }
                }
            }
        }
        
        // Apply weather effects to all affected units
        for (Unit unit : affectedUnits) {
            applyWeatherEffectsToUnit(unit);
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
     * Apply weather effects to unit
     */
    private void applyWeatherEffectsToUnit(Unit unit) {
        // Apply weather effects based on current weather patterns
        if (weatherSystem.getWeatherPatterns() != null) {
            for (WeatherSystem.WeatherPattern pattern : weatherSystem.getWeatherPatterns().values()) {
                if (pattern.isActive() && pattern.getWeatherEffects() != null) {
                    for (Map.Entry<String, Integer> effect : pattern.getWeatherEffects().entrySet()) {
                        String effectType = effect.getKey();
                        int effectValue = effect.getValue();
                        
                        switch (effectType) {
                            case "visibility":
                                // Reduce unit visibility
                                break;
                            case "movement":
                                // Reduce unit movement
                                break;
                            case "accuracy":
                                // Reduce unit accuracy
                                break;
                            case "stealth":
                                // Increase unit stealth
                                break;
                            case "concealment":
                                // Increase unit concealment
                                break;
                            default:
                                // Generic weather effect
                                break;
                        }
                    }
                }
            }
        }
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
        String currentWeather = "CLEAR"; // Default weather
        int intensity = 50; // Default intensity
        
        WeatherEvent event = createWeatherEvent(currentWeather, intensity);
        weatherEvents.put(event.getEventId(), event);
        
        // Apply event effects
        applyWeatherEventEffects(event);
    }
    
    /**
     * Create weather event
     */
    private WeatherEvent createWeatherEvent(String weatherType, int intensity) {
        String eventId = UUID.randomUUID().toString();
        
        Map<String, Integer> eventEffects = new HashMap<>();
        switch (weatherType) {
            case "STORM":
                eventEffects.put("lightning_strike", 50);
                eventEffects.put("wind_gust", 30);
                break;
            case "FIRE_STORM":
                eventEffects.put("fire_burst", 75);
                eventEffects.put("heat_wave", 40);
                break;
            case "ICE_STORM":
                eventEffects.put("ice_burst", 60);
                eventEffects.put("freeze_blast", 35);
                break;
            case "ELECTRICAL_STORM":
                eventEffects.put("electric_arc", 70);
                eventEffects.put("power_surge", 45);
                break;
            case "PLASMA_STORM":
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
                .eventDescription("Weather event: " + weatherType + " with intensity " + intensity)
                .bonuses(new HashMap<>())
                .consequences(new ArrayList<>())
                .eventLocation("global")
                .duration(3)
                .isPermanent(false)
                .weatherStatus(weatherType)
                .weatherLevel(intensity)
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
            // Weather system will automatically affect units
        }
    }
    
    /**
     * Remove unit from weather system
     */
    public void removeUnit(Unit unit) {
        affectedUnits.remove(unit);
        // Weather system will automatically remove units
    }
    
    /**
     * Set weather
     */
    public void setWeather(String weatherType, int intensity, int duration) {
        // Set weather state
        weatherSystem.getWeatherStates().clear();
        weatherSystem.getWeatherStates().put(weatherType, true);
        isWeatherActive = true;
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
        return "Weather System Active: " + isWeatherActive + 
               ", Active Patterns: " + (weatherSystem.getWeatherPatterns() != null ? weatherSystem.getWeatherPatterns().size() : 0);
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
     * Initialize weather events
     */
    private void initializeWeatherEvents() {
        // Create some default weather events
        WeatherEvent rainEvent = WeatherEvent.builder()
            .eventId("RAIN_EVENT_1")
            .eventType("PRECIPITATION")
            .sourceId("weather_system")
            .targetId("environment")
            .timestamp((int) (System.currentTimeMillis() / 1000))
            .eventEffects(Map.of("visibility", -30, "movement", -15, "accuracy", -20))
            .affectedUnits(Arrays.asList("Sniper", "Long Range", "Overwatch"))
            .isSuccessful(true)
            .result("Rain weather activated")
            .energyCost(0)
            .energyGained(0)
            .eventDescription("Heavy rain with reduced visibility and movement")
            .bonuses(Map.of("stealth", 15, "concealment", 20))
            .consequences(Arrays.asList("Reduced Visibility", "Movement Penalty", "Stealth Bonus"))
            .eventLocation("global")
            .duration(5)
            .isPermanent(false)
            .weatherStatus("RAIN")
            .weatherLevel(75)
            .build();
        
        weatherEvents.put(rainEvent.getEventId(), rainEvent);
        
        WeatherEvent stormEvent = WeatherEvent.builder()
            .eventId("STORM_EVENT_1")
            .eventType("ELECTRICAL")
            .sourceId("weather_system")
            .targetId("environment")
            .timestamp((int) (System.currentTimeMillis() / 1000))
            .eventEffects(Map.of("visibility", -50, "movement", -25, "equipment", -30))
            .affectedUnits(Arrays.asList("All Electronic", "Hacking", "Technical"))
            .isSuccessful(true)
            .result("Electrical storm activated")
            .energyCost(0)
            .energyGained(0)
            .eventDescription("Electrical storm with severe visibility reduction and equipment damage")
            .bonuses(Map.of("stealth", 25, "concealment", 30))
            .consequences(Arrays.asList("Severe Visibility Reduction", "Equipment Damage", "Stealth Bonus"))
            .eventLocation("global")
            .duration(3)
            .isPermanent(false)
            .weatherStatus("ELECTRICAL_STORM")
            .weatherLevel(90)
            .build();
        
        weatherEvents.put(stormEvent.getEventId(), stormEvent);
    }
    
    /**
     * Initialize environmental hazards
     */
    private void initializeEnvironmentalHazards() {
        // Create some default environmental hazards
        EnvironmentalHazard fireHazard = new EnvironmentalHazard();
        fireHazard.setHazardId("FIRE_HAZARD_1");
        fireHazard.setHazardName("Fire Hazard");
        fireHazard.setHazardType(EnvironmentalHazard.HazardType.FIRE_HAZARD);
        fireHazard.setHazardIntensity(80);
        fireHazard.setHazardDuration(4);
        fireHazard.setHazardEffects(Map.of("damage", 25, "burning", 20, "visibility", -15));
        fireHazard.setAffectedAbilities(Arrays.asList("Stealth", "Concealment", "Movement"));
        fireHazard.setActive(false);
        fireHazard.setDuration(4);
        fireHazard.setCurrentDuration(0);
        fireHazard.setActivationCondition("Environmental trigger");
        fireHazard.setSuccessRate(0.9);
        fireHazard.setFailureEffect("No fire effect");
        fireHazard.setHazardBonuses(Map.of("damage", 25, "burning", 20));
        fireHazard.setHazardAbilities(Arrays.asList("Fire Damage", "Burning Effect", "Visibility Reduction"));
        fireHazard.setHazardMethod("Automatic");
        fireHazard.setEnergyCost(0);
        fireHazard.setAutomatic(true);
        fireHazard.setTriggerCondition("Environmental");
        fireHazard.setDamageModifiers(Map.of("fire", 25, "burning", 20));
        fireHazard.setResistanceTypes(Arrays.asList("Fire Resistance", "Heat Protection"));
        fireHazard.setPermanent(false);
        fireHazard.setPermanentCondition("");
        
        activeHazards.put(fireHazard.getHazardId(), fireHazard);
        
        EnvironmentalHazard toxicHazard = new EnvironmentalHazard();
        toxicHazard.setHazardId("TOXIC_HAZARD_1");
        toxicHazard.setHazardName("Toxic Hazard");
        toxicHazard.setHazardType(EnvironmentalHazard.HazardType.TOXIC_HAZARD);
        toxicHazard.setHazardIntensity(70);
        toxicHazard.setHazardDuration(5);
        toxicHazard.setHazardEffects(Map.of("damage", 20, "poisoned", 15, "movement", -10));
        toxicHazard.setAffectedAbilities(Arrays.asList("Movement", "Stealth", "Combat"));
        toxicHazard.setActive(false);
        toxicHazard.setDuration(5);
        toxicHazard.setCurrentDuration(0);
        toxicHazard.setActivationCondition("Environmental trigger");
        toxicHazard.setSuccessRate(0.85);
        toxicHazard.setFailureEffect("No toxic effect");
        toxicHazard.setHazardBonuses(Map.of("damage", 20, "poisoned", 15));
        toxicHazard.setHazardAbilities(Arrays.asList("Toxic Damage", "Poisoned Effect", "Movement Reduction"));
        toxicHazard.setHazardMethod("Automatic");
        toxicHazard.setEnergyCost(0);
        toxicHazard.setAutomatic(true);
        toxicHazard.setTriggerCondition("Environmental");
        toxicHazard.setDamageModifiers(Map.of("toxic", 20, "poisoned", 15));
        toxicHazard.setResistanceTypes(Arrays.asList("Toxic Resistance", "Poison Protection"));
        toxicHazard.setPermanent(false);
        toxicHazard.setPermanentCondition("");
        
        activeHazards.put(toxicHazard.getHazardId(), toxicHazard);
    }
    
    /**
     * Initialize destructible objects
     */
    private void initializeDestructibleObjects() {
        // Create some default destructible objects
        DestructibleObject wall = DestructibleObject.builder()
                .objectId("WALL_1")
                .objectName("Concrete Wall")
                .objectType("STRUCTURE")
                .objectHealth(100)
                .maxObjectHealth(100)
                .objectDestruction(0)
                .maxObjectDestruction(100)
                .isDestroyed(false)
                .isDamaged(false)
                .isRepairable(true)
                .objectLocation("Position A1")
                .assignedUnit("")
                .assignedFacility("")
                .assignedTechnician("")
                .underMaintenance(false)
                .underRepair(false)
                .objectStatus("INTACT")
                .maintenanceCost(500)
                .repairCost(1000)
                .objectQuality("GOOD")
                .objectCondition("NEW")
                .lastMaintenanceDate("")
                .nextMaintenanceDate("")
                .warrantyStatus("ACTIVE")
                .warrantied(true)
                .warrantyExpiryDate("")
                .objectNotes("Standard concrete wall")
                .objectStats(Map.of("defense", 50, "cover", 75))
                .objectAbilities(Arrays.asList("Provide Cover", "Block Movement", "Absorb Damage"))
                .manufacturer("XCOM_CONSTRUCTION")
                .modelNumber("WALL_001")
                .serialNumber("SN_WALL_001")
                .purchaseDate(new Date().toString())
                .purchaseCost(2000)
                .supplier("XCOM_SUPPLY")
                .location("MAIN_BASE")
                .status("ACTIVE")
                .build();
        
        destructibleObjects.put(wall.getObjectId(), wall);
        
        DestructibleObject barrier = DestructibleObject.builder()
                .objectId("BARRIER_1")
                .objectName("Metal Barrier")
                .objectType("OBSTACLE")
                .objectHealth(75)
                .maxObjectHealth(75)
                .objectDestruction(0)
                .maxObjectDestruction(75)
                .isDestroyed(false)
                .isDamaged(false)
                .isRepairable(true)
                .objectLocation("Position B2")
                .assignedUnit("")
                .assignedFacility("")
                .assignedTechnician("")
                .underMaintenance(false)
                .underRepair(false)
                .objectStatus("INTACT")
                .maintenanceCost(300)
                .repairCost(600)
                .objectQuality("GOOD")
                .objectCondition("NEW")
                .lastMaintenanceDate("")
                .nextMaintenanceDate("")
                .warrantyStatus("ACTIVE")
                .warrantied(true)
                .warrantyExpiryDate("")
                .objectNotes("Metal barrier for cover")
                .objectStats(Map.of("defense", 30, "cover", 60))
                .objectAbilities(Arrays.asList("Provide Cover", "Block Movement", "Light Defense"))
                .manufacturer("XCOM_CONSTRUCTION")
                .modelNumber("BARRIER_001")
                .serialNumber("SN_BARRIER_001")
                .purchaseDate(new Date().toString())
                .purchaseCost(1500)
                .supplier("XCOM_SUPPLY")
                .location("MAIN_BASE")
                .status("ACTIVE")
                .build();
        
        destructibleObjects.put(barrier.getObjectId(), barrier);
    }
    
    /**
     * Get comprehensive weather status
     */
    public String getComprehensiveWeatherStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Weather System Status:\n");
        status.append("- Current Weather: CLEAR\n");
        status.append("- Intensity: 50\n");
        status.append("- Duration: 5 turns\n");
        status.append("- Active Hazards: ").append(getActiveHazardsCount()).append("\n");
        status.append("- Weather Events: ").append(getWeatherEventsCount()).append("\n");
        status.append("- Affected Units: ").append(affectedUnits.size()).append("\n");
        status.append("- Weather Active: ").append(isWeatherActive).append("\n");
        
        return status.toString();
    }
}

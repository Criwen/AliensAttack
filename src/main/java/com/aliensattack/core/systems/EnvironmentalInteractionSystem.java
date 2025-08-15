package com.aliensattack.core.systems;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.data.StatusEffectData;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.StatusEffect;

import java.util.*;

/**
 * Environmental Interaction System for environmental mechanics.
 * Implements environmental interactions, hazards, and environmental effects.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalInteractionSystem {
    
    private String environmentId;
    private List<InteractiveObject> interactiveObjects;
    private List<EnvironmentalTrigger> environmentalTriggers;
    private List<EnvironmentalHazard> environmentalHazards;
    private Map<String, EnvironmentalEffect> activeEffects;
    private Map<String, DestructibleObject> destructibleObjects;
    private Map<String, EnvironmentalManipulation> activeManipulations;
    private Map<String, EnvironmentalStealth> stealthElements;
    private List<ChainReaction> chainReactions;
    private Map<String, Integer> environmentalDamage;
    private boolean isEnvironmentDestructible;
    private int environmentHealth;
    private int maxEnvironmentHealth;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InteractiveObject {
        private String objectId;
        private String objectName;
        private InteractiveType interactiveType;
        private Position position;
        private int interactionRange;
        private Map<String, Integer> interactionEffects;
        private boolean isActive;
        private int cooldown;
        private boolean isDestroyed;
        private String requiredAbility;
        
        public enum InteractiveType {
            DOOR,           // Doors that can be opened/closed
            COMPUTER,       // Computers for hacking
            SWITCH,         // Switches for environmental control
            TERMINAL,       // Terminals for data access
            GENERATOR,      // Power generators
            VENT,           // Ventilation systems
            ELEVATOR,       // Elevator controls
            SECURITY_PANEL, // Security system panels
            MEDICAL_STATION, // Medical equipment
            AMMO_CRATE      // Ammunition containers
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentalTrigger {
        private String triggerId;
        private String triggerName;
        private TriggerType triggerType;
        private Position position;
        private int triggerRange;
        private Map<String, Integer> triggerEffects;
        private boolean isActivated;
        private boolean isReusable;
        private int activationCount;
        private String activationCondition;
        
        public enum TriggerType {
            PRESSURE_PLATE,     // Pressure-sensitive plates
            MOTION_SENSOR,      // Motion detection sensors
            SOUND_SENSOR,       // Sound detection sensors
            LIGHT_SENSOR,       // Light detection sensors
            PROXIMITY_SENSOR,   // Proximity detection sensors
            ALARM_TRIGGER,      // Alarm system triggers
            TRAP_TRIGGER,       // Trap activation triggers
            SECURITY_TRIGGER    // Security system triggers
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentalHazard {
        private String hazardId;
        private String hazardName;
        private HazardType hazardType;
        private Position position;
        private int hazardRadius;
        private Map<String, Integer> hazardEffects;
        private int duration;
        private boolean isActive;
        private boolean isPermanent;
        private String hazardSource;
        private int hazardIntensity;
        private int hazardDuration;
        private List<String> affectedAbilities;
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
        private String permanentCondition;
        
        public enum HazardType {
            FIRE,           // Fire hazards
            POISON_GAS,     // Poisonous gas
            ELECTRIC_FIELD, // Electric fields
            RADIATION,      // Radiation zones
            ACID_POOL,      // Acid pools
            EXPLOSIVE,      // Explosive hazards
            TOXIC_WASTE,    // Toxic waste
            PLASMA_FIELD,   // Plasma fields
            FIRE_HAZARD,    // Fire hazard type
            TOXIC_HAZARD    // Toxic hazard type
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DestructibleObject {
        private String objectId;
        private String objectName;
        private DestructibleType destructibleType;
        private Position position;
        private int maxHealth;
        private int currentHealth;
        private boolean isDestroyed;
        private Map<String, Integer> destructionEffects;
        private List<String> requiredWeapons;
        private boolean isExplosive;
        private int explosionRadius;
        private String objectType;
        private int objectHealth;
        private int maxObjectHealth;
        private int objectDestruction;
        private int maxObjectDestruction;
        private boolean isDamaged;
        private boolean isRepairable;
        private String objectLocation;
        private String assignedUnit;
        private String assignedFacility;
        private String assignedTechnician;
        private boolean underMaintenance;
        private boolean underRepair;
        private String objectStatus;
        private int maintenanceCost;
        private int repairCost;
        private String objectQuality;
        private String objectCondition;
        private String lastMaintenanceDate;
        private String nextMaintenanceDate;
        private String warrantyStatus;
        private boolean warrantied;
        private String warrantyExpiryDate;
        private String objectNotes;
        private Map<String, Integer> objectStats;
        private List<String> objectAbilities;
        private String manufacturer;
        private String modelNumber;
        private String serialNumber;
        private String purchaseDate;
        private int purchaseCost;
        private String supplier;
        private String location;
        private String status;
        
        public enum DestructibleType {
            WALL,           // Destructible walls
            COVER,          // Destructible cover
            BARRIER,        // Barriers and obstacles
            CONTAINER,      // Containers and crates
            MACHINERY,      // Industrial machinery
            VEHICLE,        // Vehicles and transports
            STRUCTURE,      // Buildings and structures
            EQUIPMENT       // Equipment and devices
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentalManipulation {
        private String manipulationId;
        private String manipulationName;
        private ManipulationType manipulationType;
        private Position targetPosition;
        private Unit manipulatingUnit;
        private Map<String, Integer> manipulationEffects;
        private int duration;
        private boolean isActive;
        private String requiredAbility;
        
        public enum ManipulationType {
            CREATE_COVER,       // Create new cover
            DESTROY_COVER,      // Destroy existing cover
            CREATE_HAZARD,      // Create environmental hazard
            CLEAR_HAZARD,       // Remove environmental hazard
            BLOCK_PATH,         // Block movement path
            CLEAR_PATH,         // Clear blocked path
            CREATE_ELEVATION,   // Create elevated position
            DESTROY_ELEVATION   // Remove elevated position
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentalStealth {
        private String stealthId;
        private String stealthName;
        private StealthType stealthType;
        private Position position;
        private int stealthRadius;
        private Map<String, Integer> stealthEffects;
        private boolean isActive;
        private int duration;
        private String activationCondition;
        
        public enum StealthType {
            SHADOW_COVER,       // Shadow areas for concealment
            SMOKE_SCREEN,       // Smoke for concealment
            SOUND_MASKING,      // Sound masking areas
            VISUAL_BLOCKING,    // Visual blocking elements
            THERMAL_MASKING,    // Thermal signature masking
            ELECTRONIC_JAMMING, // Electronic signature masking
            CAMOUFLAGE_AREA,    // Natural camouflage areas
            CONCEALMENT_ZONE    // General concealment zones
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChainReaction {
        private String reactionId;
        private String reactionName;
        private ReactionType reactionType;
        private List<String> affectedObjects;
        private Map<String, Integer> reactionEffects;
        private int propagationDelay;
        private boolean isActive;
        private int maxPropagationDistance;
        
        public enum ReactionType {
            EXPLOSIVE_CHAIN,    // Explosive chain reactions
            FIRE_SPREAD,        // Fire spreading
            ELECTRICAL_ARC,     // Electrical arcing
            CHEMICAL_REACTION,  // Chemical reactions
            STRUCTURAL_COLLAPSE, // Structural collapse
            RADIATION_SPREAD,   // Radiation spreading
            TOXIC_SPREAD,       // Toxic substance spreading
            PLASMA_CASCADE      // Plasma cascade effects
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentalEffect {
        private String effectId;
        private String effectName;
        private EffectType effectType;
        private Position position;
        private int effectRadius;
        private Map<String, Integer> effectValues;
        private int duration;
        private boolean isActive;
        private String sourceObject;
        
        public enum EffectType {
            DAMAGE_OVER_TIME,   // Continuous damage
            HEALING_OVER_TIME,  // Continuous healing
            MOVEMENT_PENALTY,   // Movement speed reduction
            ACCURACY_BONUS,     // Accuracy improvement
            DEFENSE_BONUS,      // Defense improvement
            VISIBILITY_MODIFIER, // Visibility changes
            SOUND_MODIFIER,     // Sound level changes
            LIGHTING_MODIFIER   // Lighting changes
        }
    }
    

    
    /**
     * Add interactive object
     */
    public void addInteractiveObject(InteractiveObject object) {
        interactiveObjects.add(object);
    }
    
    /**
     * Interact with object
     */
    public boolean interactWithObject(String objectId, Unit interactingUnit) {
        InteractiveObject object = findInteractiveObject(objectId);
        if (object != null && canInteractWithObject(object, interactingUnit)) {
            applyInteractionEffects(object, interactingUnit);
            object.setActive(false);
            return true;
        }
        return false;
    }
    
    /**
     * Activate environmental trigger
     */
    public boolean activateTrigger(String triggerId, Unit activatingUnit) {
        EnvironmentalTrigger trigger = findEnvironmentalTrigger(triggerId);
        if (trigger != null && canActivateTrigger(trigger, activatingUnit)) {
            applyTriggerEffects(trigger, activatingUnit);
            trigger.setActivated(true);
            trigger.setActivationCount(trigger.getActivationCount() + 1);
            return true;
        }
        return false;
    }
    
    /**
     * Create environmental hazard
     */
    public boolean createHazard(EnvironmentalHazard hazard) {
        environmentalHazards.add(hazard);
        applyHazardEffects(hazard);
        return true;
    }
    
    /**
     * Destroy destructible object
     */
    public boolean destroyObject(String objectId, Unit destroyingUnit, String weaponType) {
        DestructibleObject object = destructibleObjects.get(objectId);
        if (object != null && canDestroyObject(object, destroyingUnit, weaponType)) {
            applyDestructionEffects(object, destroyingUnit);
            object.setDestroyed(true);
            object.setCurrentHealth(0);
            return true;
        }
        return false;
    }
    
    /**
     * Manipulate environment
     */
    public boolean manipulateEnvironment(EnvironmentalManipulation manipulation) {
        if (canPerformManipulation(manipulation)) {
            activeManipulations.put(manipulation.getManipulationId(), manipulation);
            applyManipulationEffects(manipulation);
            return true;
        }
        return false;
    }
    
    /**
     * Activate stealth element
     */
    public boolean activateStealthElement(String stealthId, Unit activatingUnit) {
        EnvironmentalStealth stealth = stealthElements.get(stealthId);
        if (stealth != null && canActivateStealth(stealth, activatingUnit)) {
            applyStealthEffects(stealth, activatingUnit);
            stealth.setActive(true);
            return true;
        }
        return false;
    }
    
    /**
     * Trigger chain reaction
     */
    public boolean triggerChainReaction(String reactionId, Unit triggeringUnit) {
        ChainReaction reaction = findChainReaction(reactionId);
        if (reaction != null && canTriggerReaction(reaction, triggeringUnit)) {
            applyChainReactionEffects(reaction, triggeringUnit);
            reaction.setActive(true);
            return true;
        }
        return false;
    }
    
    /**
     * Apply environmental damage
     */
    public void applyEnvironmentalDamage(String damageType, int damageAmount, Position position) {
        environmentalDamage.put(damageType, environmentalDamage.getOrDefault(damageType, 0) + damageAmount);
        environmentHealth = Math.max(0, environmentHealth - damageAmount);
        
        if (environmentHealth <= 0) {
            triggerEnvironmentDestruction();
        }
    }
    
    /**
     * Find interactive object
     */
    private InteractiveObject findInteractiveObject(String objectId) {
        return interactiveObjects.stream()
                .filter(obj -> obj.getObjectId().equals(objectId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Find environmental trigger
     */
    private EnvironmentalTrigger findEnvironmentalTrigger(String triggerId) {
        return environmentalTriggers.stream()
                .filter(trigger -> trigger.getTriggerId().equals(triggerId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Find chain reaction
     */
    private ChainReaction findChainReaction(String reactionId) {
        return chainReactions.stream()
                .filter(reaction -> reaction.getReactionId().equals(reactionId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Check if unit can interact with object
     */
    private boolean canInteractWithObject(InteractiveObject object, Unit unit) {
        if (!object.isActive() || object.isDestroyed()) {
            return false;
        }
        
        double distance = calculateDistance(unit.getPosition(), object.getPosition());
        if (distance > object.getInteractionRange()) {
            return false;
        }
        
        if (object.getRequiredAbility() != null) {
            return unit.getAbilities().stream()
                    .anyMatch(ability -> ability.getName().equals(object.getRequiredAbility()));
        }
        
        return true;
    }
    
    /**
     * Check if unit can activate trigger
     */
    private boolean canActivateTrigger(EnvironmentalTrigger trigger, Unit unit) {
        if (trigger.isActivated() && !trigger.isReusable()) {
            return false;
        }
        
        double distance = calculateDistance(unit.getPosition(), trigger.getPosition());
        return distance <= trigger.getTriggerRange();
    }
    
    /**
     * Check if unit can destroy object
     */
    private boolean canDestroyObject(DestructibleObject object, Unit unit, String weaponType) {
        if (object.isDestroyed()) {
            return false;
        }
        
        if (!object.getRequiredWeapons().isEmpty() && !object.getRequiredWeapons().contains(weaponType)) {
            return false;
        }
        
        double distance = calculateDistance(unit.getPosition(), object.getPosition());
        return distance <= 3.0; // Close range for destruction
    }
    
    /**
     * Check if manipulation can be performed
     */
    private boolean canPerformManipulation(EnvironmentalManipulation manipulation) {
        if (manipulation.getRequiredAbility() != null) {
            return manipulation.getManipulatingUnit().getAbilities().stream()
                    .anyMatch(ability -> ability.getName().equals(manipulation.getRequiredAbility()));
        }
        return true;
    }
    
    /**
     * Check if stealth can be activated
     */
    private boolean canActivateStealth(EnvironmentalStealth stealth, Unit unit) {
        if (stealth.isActive()) {
            return false;
        }
        
        double distance = calculateDistance(unit.getPosition(), stealth.getPosition());
        return distance <= stealth.getStealthRadius();
    }
    
    /**
     * Check if reaction can be triggered
     */
    private boolean canTriggerReaction(ChainReaction reaction, Unit unit) {
        if (reaction.isActive()) {
            return false;
        }
        
        // Check if unit is near any affected object
        return reaction.getAffectedObjects().stream()
                .anyMatch(objectId -> {
                    // Check if unit is near the object
                    return true; // Simplified check
                });
    }
    
    /**
     * Apply interaction effects
     */
    private void applyInteractionEffects(InteractiveObject object, Unit unit) {
        for (Map.Entry<String, Integer> effect : object.getInteractionEffects().entrySet()) {
            applyEffectToUnit(unit, effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Apply trigger effects
     */
    private void applyTriggerEffects(EnvironmentalTrigger trigger, Unit unit) {
        for (Map.Entry<String, Integer> effect : trigger.getTriggerEffects().entrySet()) {
            applyEffectToUnit(unit, effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Apply hazard effects
     */
    private void applyHazardEffects(EnvironmentalHazard hazard) {
        // Apply hazard effects to all units in range
        // Note: This would need to be connected to a unit manager to get all units
        // For now, we'll apply effects to the manipulating unit if available
        if (hazard.getHazardSource() != null) {
            // Find the source unit and apply effects
            // This is a simplified implementation
        }
        
        // Apply specific hazard effects based on hazard type
        applySpecificHazardEffects(hazard);
    }
    
    /**
     * Apply specific hazard effects based on hazard type
     */
    private void applySpecificHazardEffects(EnvironmentalHazard hazard) {
        // Apply effects based on hazard type
        switch (hazard.getHazardType()) {
            case FIRE:
                // Fire effects are handled by applyFireHazardEffect
                // Note: Would need a unit parameter to apply effects
                break;
            case POISON_GAS:
                // Poison effects are handled by applyPoisonHazardEffect
                // Note: Would need a unit parameter to apply effects
                break;
            case ELECTRIC_FIELD:
                // Electric effects are handled by applyElectricHazardEffect
                // Note: Would need a unit parameter to apply effects
                break;
            case RADIATION:
                // Radiation effects are handled by applyRadiationHazardEffect
                // Note: Would need a unit parameter to apply effects
                break;
            case ACID_POOL:
                // Acid effects are handled by applyAcidHazardEffect
                // Note: Would need a unit parameter to apply effects
                break;
            case EXPLOSIVE:
                // Explosive effects are handled by applyExplosiveHazardEffect
                // Note: Would need a unit parameter to apply effects
                break;
            case TOXIC_WASTE:
                // Toxic effects are handled by applyToxicHazardEffect
                // Note: Would need a unit parameter to apply effects
                break;
            case PLASMA_FIELD:
                // Plasma effects are handled by applyPlasmaHazardEffect
                // Note: Would need a unit parameter to apply effects
                break;
        }
    }
    
    /**
     * Apply hazard effects to a specific unit
     */
    public void applyHazardEffectsToUnit(EnvironmentalHazard hazard, Unit unit) {
        if (unit == null || hazard == null) {
            return;
        }
        
        // Check if unit is within hazard radius
        double distance = calculateDistance(unit.getPosition(), hazard.getPosition());
        if (distance > hazard.getHazardRadius()) {
            return;
        }
        
        // Get the primary effect value from hazard effects
        int effectValue = hazard.getHazardEffects().getOrDefault("damage", 10);
        
        // Apply effects based on hazard type
        switch (hazard.getHazardType()) {
            case FIRE:
                applyFireHazardEffect(unit, effectValue);
                break;
            case POISON_GAS:
                applyPoisonHazardEffect(unit, effectValue);
                break;
            case ELECTRIC_FIELD:
                applyElectricHazardEffect(unit, effectValue);
                break;
            case RADIATION:
                applyRadiationHazardEffect(unit, effectValue);
                break;
            case ACID_POOL:
                applyAcidHazardEffect(unit, effectValue);
                break;
            case EXPLOSIVE:
                applyExplosiveHazardEffect(unit, effectValue);
                break;
            case TOXIC_WASTE:
                applyToxicHazardEffect(unit, effectValue);
                break;
            case PLASMA_FIELD:
                applyPlasmaHazardEffect(unit, effectValue);
                break;
        }
    }
    

    
    private void applyFireHazardEffect(Unit unit, int effectValue) {
        unit.takeDamage(effectValue);
        StatusEffectData burningEffect = new StatusEffectData(StatusEffect.BURNING, 3, effectValue);
        unit.addStatusEffect(burningEffect);
    }
    
    private void applyPoisonHazardEffect(Unit unit, int effectValue) {
        unit.takeDamage(effectValue / 2);
        StatusEffectData poisonedEffect = new StatusEffectData(StatusEffect.POISONED, 4, effectValue);
        unit.addStatusEffect(poisonedEffect);
    }
    
    private void applyElectricHazardEffect(Unit unit, int effectValue) {
        unit.takeDamage(effectValue);
        StatusEffectData stunnedEffect = new StatusEffectData(StatusEffect.STUNNED, 2, effectValue);
        unit.addStatusEffect(stunnedEffect);
    }
    
    private void applyRadiationHazardEffect(Unit unit, int effectValue) {
        unit.takeDamage(effectValue / 3);
        StatusEffectData radiationEffect = new StatusEffectData(StatusEffect.RADIATION, 5, effectValue);
        unit.addStatusEffect(radiationEffect);
    }
    
    private void applyAcidHazardEffect(Unit unit, int effectValue) {
        unit.takeDamage(effectValue);
        StatusEffectData acidEffect = new StatusEffectData(StatusEffect.ACID_BURN, 3, effectValue);
        unit.addStatusEffect(acidEffect);
    }
    
    private void applyExplosiveHazardEffect(Unit unit, int effectValue) {
        unit.takeDamage(effectValue * 2);
        StatusEffectData knockedDownEffect = new StatusEffectData(StatusEffect.KNOCKED_DOWN, 1, effectValue);
        unit.addStatusEffect(knockedDownEffect);
    }
    
    private void applyToxicHazardEffect(Unit unit, int effectValue) {
        unit.takeDamage(effectValue / 2);
        StatusEffectData toxicEffect = new StatusEffectData(StatusEffect.POISONED, 4, effectValue);
        unit.addStatusEffect(toxicEffect);
    }
    
    private void applyPlasmaHazardEffect(Unit unit, int effectValue) {
        unit.takeDamage(effectValue);
        StatusEffectData plasmaEffect = new StatusEffectData(StatusEffect.BURNING, 2, effectValue);
        unit.addStatusEffect(plasmaEffect);
    }
    
    /**
     * Apply destruction effects
     */
    private void applyDestructionEffects(DestructibleObject object, Unit unit) {
        for (Map.Entry<String, Integer> effect : object.getDestructionEffects().entrySet()) {
            applyEffectToUnit(unit, effect.getKey(), effect.getValue());
        }
        
        if (object.isExplosive()) {
            // Trigger explosion
            triggerExplosion(object);
        }
    }
    
    /**
     * Apply manipulation effects
     */
    private void applyManipulationEffects(EnvironmentalManipulation manipulation) {
        switch (manipulation.getManipulationType()) {
            case CREATE_COVER:
                createCoverAtPosition(manipulation.getTargetPosition());
                break;
            case DESTROY_COVER:
                destroyCoverAtPosition(manipulation.getTargetPosition());
                break;
            case CREATE_HAZARD:
                createHazardAtPosition(manipulation.getTargetPosition(), manipulation.getManipulationEffects());
                break;
            case CLEAR_HAZARD:
                clearHazardAtPosition(manipulation.getTargetPosition());
                break;
            case BLOCK_PATH:
                blockPathAtPosition(manipulation.getTargetPosition());
                break;
            case CLEAR_PATH:
                clearPathAtPosition(manipulation.getTargetPosition());
                break;
            case CREATE_ELEVATION:
                createElevationAtPosition(manipulation.getTargetPosition());
                break;
            case DESTROY_ELEVATION:
                destroyElevationAtPosition(manipulation.getTargetPosition());
                break;
        }
    }
    
    private void createCoverAtPosition(Position position) {
        DestructibleObject cover = DestructibleObject.builder()
                .objectId(UUID.randomUUID().toString())
                .objectName("Created Cover")
                .destructibleType(DestructibleObject.DestructibleType.COVER)
                .position(position)
                .maxHealth(50)
                .currentHealth(50)
                .isDestroyed(false)
                .destructionEffects(Map.of("damage", 10))
                .requiredWeapons(new ArrayList<>())
                .isExplosive(false)
                .explosionRadius(0)
                .build();
        
        destructibleObjects.put(cover.getObjectId(), cover);
    }
    
    private void destroyCoverAtPosition(Position position) {
        // Find and destroy cover at position
        destructibleObjects.values().removeIf(obj -> 
            obj.getPosition().equals(position) && 
            obj.getDestructibleType() == DestructibleObject.DestructibleType.COVER);
    }
    
    private void createHazardAtPosition(Position position, Map<String, Integer> effects) {
        EnvironmentalHazard hazard = EnvironmentalHazard.builder()
                .hazardId(UUID.randomUUID().toString())
                .hazardName("Created Hazard")
                .hazardType(EnvironmentalHazard.HazardType.FIRE)
                .position(position)
                .hazardRadius(3)
                .hazardEffects(effects)
                .duration(5)
                .isActive(true)
                .isPermanent(false)
                .hazardSource("manipulation")
                .build();
        
        environmentalHazards.add(hazard);
    }
    
    private void clearHazardAtPosition(Position position) {
        environmentalHazards.removeIf(hazard -> 
            hazard.getPosition().equals(position));
    }
    
    private void blockPathAtPosition(Position position) {
        DestructibleObject barrier = DestructibleObject.builder()
                .objectId(UUID.randomUUID().toString())
                .objectName("Path Block")
                .destructibleType(DestructibleObject.DestructibleType.BARRIER)
                .position(position)
                .maxHealth(100)
                .currentHealth(100)
                .isDestroyed(false)
                .destructionEffects(Map.of("damage", 20))
                .requiredWeapons(new ArrayList<>())
                .isExplosive(false)
                .explosionRadius(0)
                .build();
        
        destructibleObjects.put(barrier.getObjectId(), barrier);
    }
    
    private void clearPathAtPosition(Position position) {
        destructibleObjects.values().removeIf(obj -> 
            obj.getPosition().equals(position) && 
            obj.getDestructibleType() == DestructibleObject.DestructibleType.BARRIER);
    }
    
    private void createElevationAtPosition(Position position) {
        // Create elevated terrain
        // This would modify the terrain system
    }
    
    private void destroyElevationAtPosition(Position position) {
        // Remove elevated terrain
        // This would modify the terrain system
    }
    
    /**
     * Apply stealth effects
     */
    private void applyStealthEffects(EnvironmentalStealth stealth, Unit unit) {
        for (Map.Entry<String, Integer> effect : stealth.getStealthEffects().entrySet()) {
            applyEffectToUnit(unit, effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Apply chain reaction effects
     */
    private void applyChainReactionEffects(ChainReaction reaction, Unit unit) {
        for (Map.Entry<String, Integer> effect : reaction.getReactionEffects().entrySet()) {
            applyEffectToUnit(unit, effect.getKey(), effect.getValue());
        }
    }
    
    /**
     * Apply effect to unit
     */
    private void applyEffectToUnit(Unit unit, String effectType, int effectValue) {
        switch (effectType) {
            case "damage":
                unit.takeDamage(effectValue);
                break;
            case "heal":
                unit.heal(effectValue);
                break;
            case "accuracy":
                unit.setAccuracy(unit.getAccuracy() + effectValue);
                break;
            case "defense":
                unit.setDefense(unit.getDefense() + effectValue);
                break;
            case "movement":
                unit.setMovementPoints(unit.getMovementPoints() + effectValue);
                break;
        }
    }
    
    /**
     * Trigger explosion
     */
    private void triggerExplosion(DestructibleObject object) {
        // Create explosion effect
        EnvironmentalHazard explosion = EnvironmentalHazard.builder()
                .hazardId(UUID.randomUUID().toString())
                .hazardName("Explosion")
                .hazardType(EnvironmentalHazard.HazardType.EXPLOSIVE)
                .position(object.getPosition())
                .hazardRadius(object.getExplosionRadius())
                .hazardEffects(Map.of("damage", 50))
                .duration(1)
                .isActive(true)
                .isPermanent(false)
                .hazardSource(object.getObjectId())
                .build();
        
        createHazard(explosion);
    }
    
    /**
     * Trigger environment destruction
     */
    private void triggerEnvironmentDestruction() {
        // Trigger complete environment destruction
        // Implementation would destroy all objects and create hazards
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
     * Get environment health percentage
     */
    public double getEnvironmentHealthPercentage() {
        return (double) environmentHealth / maxEnvironmentHealth * 100.0;
    }
    
    /**
     * Check if environment is destroyed
     */
    public boolean isEnvironmentDestroyed() {
        return environmentHealth <= 0;
    }
    
    /**
     * Get active hazards count
     */
    public int getActiveHazardsCount() {
        return (int) environmentalHazards.stream().filter(EnvironmentalHazard::isActive).count();
    }
    
    /**
     * Get active manipulations count
     */
    public int getActiveManipulationsCount() {
        return activeManipulations.size();
    }
}

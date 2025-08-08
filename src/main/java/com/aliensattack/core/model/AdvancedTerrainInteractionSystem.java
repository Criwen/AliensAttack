package com.aliensattack.core.model;

import com.aliensattack.core.enums.TerrainType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Advanced Terrain Interaction System - XCOM 2 Mechanic
 * Implements the system for complex terrain interactions and environmental effects
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdvancedTerrainInteractionSystem {
    
    private String terrainId;
    private String terrainName;
    private TerrainType terrainType;
    private int maxHealth;
    private int currentHealth;
    private boolean isDestroyed;
    private boolean isInteractable;
    private List<String> interactionAbilities;
    private Map<String, Integer> terrainBonuses;
    private Map<String, Integer> terrainPenalties;
    private List<String> activeHazards;
    private Map<String, Integer> hazardIntensities;
    private Map<String, Integer> hazardDurations;
    private boolean isDestructible;
    private int destructionThreshold;
    private List<String> destructionTriggers;
    private Map<String, Integer> resistanceModifiers;
    private boolean isIndestructible;
    private int height;
    private int maxHeight;
    private List<Position> adjacentPositions;
    private Map<String, Integer> movementCosts;
    private List<String> terrainEffects;
    private Map<String, Integer> effectDurations;
    private boolean isTransforming;
    private TerrainType targetTransformation;
    private int transformationProgress;
    private int maxTransformationTime;
    private List<String> transformationTriggers;
    private Map<String, Integer> interactionCooldowns;
    private boolean isBeingInteracted;
    private int interactionProgress;
    private int maxInteractionTime;
    
    public AdvancedTerrainInteractionSystem(String terrainId, String terrainName, TerrainType terrainType) {
        this.terrainId = terrainId;
        this.terrainName = terrainName;
        this.terrainType = terrainType;
        this.maxHealth = getMaxHealthForType(terrainType);
        this.currentHealth = maxHealth;
        this.isDestroyed = false;
        this.isInteractable = true;
        this.interactionAbilities = new ArrayList<>();
        this.terrainBonuses = new HashMap<>();
        this.terrainPenalties = new HashMap<>();
        this.activeHazards = new ArrayList<>();
        this.hazardIntensities = new HashMap<>();
        this.hazardDurations = new HashMap<>();
        this.isDestructible = true;
        this.destructionThreshold = 0;
        this.destructionTriggers = new ArrayList<>();
        this.resistanceModifiers = new HashMap<>();
        this.isIndestructible = false;
        this.height = getHeightForType(terrainType);
        this.maxHeight = height;
        this.adjacentPositions = new ArrayList<>();
        this.movementCosts = new HashMap<>();
        this.terrainEffects = new ArrayList<>();
        this.effectDurations = new HashMap<>();
        this.isTransforming = false;
        this.targetTransformation = null;
        this.transformationProgress = 0;
        this.maxTransformationTime = 5;
        this.transformationTriggers = new ArrayList<>();
        this.interactionCooldowns = new HashMap<>();
        this.isBeingInteracted = false;
        this.interactionProgress = 0;
        this.maxInteractionTime = 3;
        
        initializeTerrain();
    }
    
    /**
     * Initialize terrain with type-specific properties
     */
    private void initializeTerrain() {
        // Set terrain bonuses based on type
        switch (terrainType) {
            case URBAN:
                terrainBonuses.put("cover", 20);
                terrainBonuses.put("defense", 15);
                terrainPenalties.put("movement", 10);
                break;
            case FOREST:
                terrainBonuses.put("stealth", 25);
                terrainBonuses.put("cover", 15);
                terrainPenalties.put("visibility", 20);
                break;
            case MOUNTAIN:
                terrainBonuses.put("height_advantage", 30);
                terrainBonuses.put("defense", 25);
                terrainPenalties.put("movement", 25);
                break;
            case WATER:
                terrainBonuses.put("cooling", 20);
                terrainPenalties.put("movement", 50);
                terrainPenalties.put("accuracy", 15);
                break;
            case DESERT:
                terrainPenalties.put("visibility", 10);
                terrainPenalties.put("movement", 15);
                break;
            case INDUSTRIAL:
                terrainBonuses.put("cover", 30);
                terrainBonuses.put("destructible", 40);
                terrainPenalties.put("movement", 5);
                break;
            case SWAMP:
                terrainPenalties.put("movement", 40);
                terrainPenalties.put("accuracy", 20);
                break;
            case SNOW:
                terrainPenalties.put("movement", 30);
                terrainPenalties.put("visibility", 15);
                break;
            case VOLCANIC:
                terrainBonuses.put("damage", 25);
                terrainPenalties.put("movement", 35);
                break;
            case UNDERGROUND:
                terrainBonuses.put("stealth", 30);
                terrainPenalties.put("visibility", 25);
                break;
            default:
                // Default terrain properties for unhandled types
                terrainBonuses.put("neutral", 0);
                terrainPenalties.put("neutral", 0);
                break;
        }
        
        // Set resistance modifiers
        resistanceModifiers.put("explosive", 30);
        resistanceModifiers.put("fire", 20);
        resistanceModifiers.put("acid", 15);
        resistanceModifiers.put("psionic", 5);
        
        // Set movement costs
        movementCosts.put("normal", 1);
        movementCosts.put("difficult", 2);
        movementCosts.put("very_difficult", 3);
        
        // Add interaction abilities
        interactionAbilities.add("explore");
        interactionAbilities.add("harvest");
        interactionAbilities.add("modify");
        interactionAbilities.add("destroy");
    }
    
    /**
     * Take damage to terrain
     */
    public boolean takeDamage(int damage, String damageType) {
        if (isDestroyed || isIndestructible) {
            return false;
        }
        
        // Apply resistance modifiers
        int resistance = resistanceModifiers.getOrDefault(damageType, 0);
        int actualDamage = Math.max(1, damage - resistance);
        
        // Apply damage
        currentHealth -= actualDamage;
        
        // Check for destruction
        if (currentHealth <= 0) {
            isDestroyed = true;
            return true;
        }
        
        // Check for transformation triggers
        checkTransformationTriggers(damageType, actualDamage);
        
        return false;
    }
    
    /**
     * Check for transformation triggers
     */
    private void checkTransformationTriggers(String damageType, int damage) {
        if (transformationTriggers.contains(damageType) && !isTransforming) {
            startTransformation(getTransformationType(damageType));
        }
    }
    
    /**
     * Get transformation type based on damage
     */
    private TerrainType getTransformationType(String damageType) {
        switch (damageType) {
            case "fire":
                return TerrainType.VOLCANIC;
            case "acid":
                return TerrainType.SWAMP;
            case "explosive":
                return TerrainType.URBAN;
            case "water":
                return TerrainType.WATER;
            default:
                return terrainType;
        }
    }
    
    /**
     * Start terrain transformation
     */
    public boolean startTransformation(TerrainType newType) {
        if (isTransforming || isDestroyed) {
            return false;
        }
        
        isTransforming = true;
        targetTransformation = newType;
        transformationProgress = 0;
        
        return true;
    }
    
    /**
     * Update transformation progress
     */
    public void updateTransformationProgress() {
        if (isTransforming) {
            transformationProgress++;
            
            if (transformationProgress >= maxTransformationTime) {
                completeTransformation();
            }
        }
    }
    
    /**
     * Complete terrain transformation
     */
    private void completeTransformation() {
        isTransforming = false;
        terrainType = targetTransformation;
        targetTransformation = null;
        transformationProgress = 0;
        
        // Reinitialize terrain properties
        maxHealth = getMaxHealthForType(terrainType);
        currentHealth = Math.min(currentHealth, maxHealth);
        height = getHeightForType(terrainType);
        maxHeight = height;
        
        // Reinitialize bonuses and penalties
        terrainBonuses.clear();
        terrainPenalties.clear();
        initializeTerrain();
    }
    
    /**
     * Add hazard to terrain
     */
    public void addHazard(String hazardType, int intensity, int duration) {
        if (!activeHazards.contains(hazardType)) {
            activeHazards.add(hazardType);
        }
        
        hazardIntensities.put(hazardType, intensity);
        hazardDurations.put(hazardType, duration);
    }
    
    /**
     * Remove hazard from terrain
     */
    public void removeHazard(String hazardType) {
        activeHazards.remove(hazardType);
        hazardIntensities.remove(hazardType);
        hazardDurations.remove(hazardType);
    }
    
    /**
     * Update hazard durations
     */
    public void updateHazardDurations() {
        for (String hazard : new ArrayList<>(hazardDurations.keySet())) {
            int currentDuration = hazardDurations.get(hazard);
            if (currentDuration > 0) {
                hazardDurations.put(hazard, currentDuration - 1);
            } else {
                removeHazard(hazard);
            }
        }
    }
    
    /**
     * Get active hazards
     */
    public List<String> getActiveHazards() {
        return new ArrayList<>(activeHazards);
    }
    
    /**
     * Get hazard intensity
     */
    public int getHazardIntensity(String hazardType) {
        return hazardIntensities.getOrDefault(hazardType, 0);
    }
    
    /**
     * Get terrain bonus for specific attribute
     */
    public int getTerrainBonus(String attribute) {
        if (isDestroyed) {
            return 0;
        }
        
        int baseBonus = terrainBonuses.getOrDefault(attribute, 0);
        
        // Apply hazard penalties
        for (String hazard : activeHazards) {
            baseBonus = applyHazardEffect(baseBonus, hazard);
        }
        
        return baseBonus;
    }
    
    /**
     * Get terrain penalty for specific attribute
     */
    public int getTerrainPenalty(String attribute) {
        int basePenalty = terrainPenalties.getOrDefault(attribute, 0);
        
        // Apply hazard penalties
        for (String hazard : activeHazards) {
            basePenalty = applyHazardEffect(basePenalty, hazard);
        }
        
        return basePenalty;
    }
    
    /**
     * Apply hazard effect to value
     */
    private int applyHazardEffect(int value, String hazard) {
        int intensity = getHazardIntensity(hazard);
        
        switch (hazard) {
            case "fire":
                return value + intensity * 5;
            case "acid":
                return value + intensity * 3;
            case "poison":
                return value + intensity * 2;
            case "radiation":
                return value + intensity * 4;
            default:
                return value;
        }
    }
    
    /**
     * Get movement cost for terrain
     */
    public int getMovementCost(String movementType) {
        int baseCost = movementCosts.getOrDefault(movementType, 1);
        
        // Apply terrain penalties
        baseCost += getTerrainPenalty("movement") / 10;
        
        // Apply hazard effects
        for (String hazard : activeHazards) {
            baseCost += getHazardIntensity(hazard);
        }
        
        return Math.max(1, baseCost);
    }
    
    /**
     * Interact with terrain
     */
    public boolean interactWithTerrain(String interactionType, Unit interactingUnit) {
        if (!isInteractable || isBeingInteracted) {
            return false;
        }
        
        if (interactionAbilities.contains(interactionType)) {
            startInteraction(interactionType, interactingUnit);
            return true;
        }
        
        return false;
    }
    
    /**
     * Start terrain interaction
     */
    private void startInteraction(String interactionType, Unit interactingUnit) {
        isBeingInteracted = true;
        interactionProgress = 0;
        
        // Apply interaction effects
        applyInteractionEffects(interactionType, interactingUnit);
    }
    
    /**
     * Apply interaction effects
     */
    private void applyInteractionEffects(String interactionType, Unit interactingUnit) {
        switch (interactionType) {
            case "explore":
                // Grant exploration bonuses
                break;
            case "harvest":
                // Grant resources
                break;
            case "modify":
                // Modify terrain properties
                break;
            case "destroy":
                // Destroy terrain
                takeDamage(50, "interaction");
                break;
        }
    }
    
    /**
     * Update interaction progress
     */
    public void updateInteractionProgress() {
        if (isBeingInteracted) {
            interactionProgress++;
            
            if (interactionProgress >= maxInteractionTime) {
                completeInteraction();
            }
        }
    }
    
    /**
     * Complete terrain interaction
     */
    private void completeInteraction() {
        isBeingInteracted = false;
        interactionProgress = 0;
        
        // Set interaction cooldown
        interactionCooldowns.put("last_interaction", 3);
    }
    
    /**
     * Check if terrain is interactable
     */
    public boolean isInteractable() {
        return isInteractable && !isDestroyed;
    }
    
    /**
     * Get terrain interaction abilities
     */
    public List<String> getInteractionAbilities() {
        return new ArrayList<>(interactionAbilities);
    }
    
    /**
     * Add interaction ability
     */
    public void addInteractionAbility(String ability) {
        if (!interactionAbilities.contains(ability)) {
            interactionAbilities.add(ability);
        }
    }
    
    /**
     * Remove interaction ability
     */
    public void removeInteractionAbility(String ability) {
        interactionAbilities.remove(ability);
    }
    
    /**
     * Check if terrain provides cover
     */
    public boolean providesCover() {
        return getTerrainBonus("cover") > 0;
    }
    
    /**
     * Check if terrain provides stealth
     */
    public boolean providesStealth() {
        return getTerrainBonus("stealth") > 0;
    }
    
    /**
     * Repair terrain
     */
    public boolean repairTerrain(int repairAmount) {
        if (isDestroyed || !isDestructible) {
            return false;
        }
        
        currentHealth = Math.min(currentHealth + repairAmount, maxHealth);
        
        // Remove hazards if fully repaired
        if (currentHealth >= maxHealth) {
            activeHazards.clear();
            hazardIntensities.clear();
            hazardDurations.clear();
        }
        
        return true;
    }
    
    /**
     * Transform terrain to new type
     */
    public boolean transformTerrain(TerrainType newType) {
        if (isDestroyed) {
            return false;
        }
        
        terrainType = newType;
        maxHealth = getMaxHealthForType(newType);
        currentHealth = Math.min(currentHealth, maxHealth);
        height = getHeightForType(newType);
        maxHeight = height;
        
        // Reinitialize properties
        terrainBonuses.clear();
        terrainPenalties.clear();
        initializeTerrain();
        
        return true;
    }
    
    /**
     * Get max health for terrain type
     */
    private int getMaxHealthForType(TerrainType type) {
        switch (type) {
            case URBAN:
                return 100;
            case FOREST:
                return 80;
            case MOUNTAIN:
                return 150;
            case WATER:
                return 50;
            case DESERT:
                return 60;
            case INDUSTRIAL:
                return 120;
            case SWAMP:
                return 70;
            case SNOW:
                return 90;
            case VOLCANIC:
                return 200;
            case UNDERGROUND:
                return 180;
            default:
                return 100;
        }
    }
    
    /**
     * Get height for terrain type
     */
    private int getHeightForType(TerrainType type) {
        switch (type) {
            case URBAN:
                return 2;
            case FOREST:
                return 1;
            case MOUNTAIN:
                return 4;
            case WATER:
                return 0;
            case DESERT:
                return 1;
            case INDUSTRIAL:
                return 3;
            case SWAMP:
                return 0;
            case SNOW:
                return 1;
            case VOLCANIC:
                return 3;
            case UNDERGROUND:
                return 2;
            default:
                return 1;
        }
    }
    
    /**
     * Get terrain effectiveness percentage
     */
    public double getTerrainEffectiveness() {
        if (isDestroyed) {
            return 0.0;
        }
        
        return (double) currentHealth / maxHealth;
    }
    
    /**
     * Get destruction percentage
     */
    public double getDestructionPercentage() {
        return 1.0 - getTerrainEffectiveness();
    }
    
    /**
     * Check if terrain is vulnerable to specific damage type
     */
    public boolean isVulnerableTo(String damageType) {
        int resistance = resistanceModifiers.getOrDefault(damageType, 0);
        return resistance < 30;
    }
    
    /**
     * Get resistance to damage type
     */
    public int getResistance(String damageType) {
        return resistanceModifiers.getOrDefault(damageType, 0);
    }
    
    /**
     * Add destruction trigger
     */
    public void addDestructionTrigger(String trigger) {
        if (!destructionTriggers.contains(trigger)) {
            destructionTriggers.add(trigger);
        }
    }
    
    /**
     * Check if destruction threshold is met
     */
    public boolean isDestructionThresholdMet() {
        return currentHealth <= destructionThreshold;
    }
    
    /**
     * Get terrain status description
     */
    public String getTerrainStatus() {
        if (isDestroyed) {
            return "DESTROYED";
        } else if (isTransforming) {
            return "TRANSFORMING";
        } else if (isBeingInteracted) {
            return "BEING_INTERACTED";
        } else if (!activeHazards.isEmpty()) {
            return "HAZARDOUS";
        } else {
            return "NORMAL";
        }
    }
    
    /**
     * Get terrain description
     */
    public String getTerrainDescription() {
        return terrainName + " (" + terrainType + ") - " + getTerrainStatus() + 
               " - Health: " + currentHealth + "/" + maxHealth + 
               " - Height: " + height + " - Hazards: " + activeHazards.size();
    }
    
    /**
     * Update interaction cooldowns
     */
    public void updateInteractionCooldowns() {
        for (String interaction : new ArrayList<>(interactionCooldowns.keySet())) {
            int currentCooldown = interactionCooldowns.get(interaction);
            if (currentCooldown > 0) {
                interactionCooldowns.put(interaction, currentCooldown - 1);
            }
        }
    }
    
    /**
     * Check if interaction is on cooldown
     */
    public boolean isInteractionOnCooldown(String interactionType) {
        return interactionCooldowns.getOrDefault(interactionType, 0) > 0;
    }
    
    /**
     * Get adjacent positions
     */
    public List<Position> getAdjacentPositions() {
        return new ArrayList<>(adjacentPositions);
    }
    
    /**
     * Add adjacent position
     */
    public void addAdjacentPosition(Position position) {
        if (!adjacentPositions.contains(position)) {
            adjacentPositions.add(position);
        }
    }
    
    /**
     * Get terrain height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Get max height
     */
    public int getMaxHeight() {
        return maxHeight;
    }
}

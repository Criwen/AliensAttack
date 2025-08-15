package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Terrain System for tactical field management.
 * Implements terrain types, movement costs, and environmental effects.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerrainSystem {
    
    private String terrainId;
    private TerrainType terrainType;
    private Position position;
    private int terrainHealth;
    private int maxTerrainHealth;
    private boolean isDestroyed;
    private List<TerrainHazard> hazards;
    private Map<String, Integer> movementPenalties;
    private Map<String, Integer> terrainBonuses;
    private boolean isInteractable;
    private List<String> interactionAbilities;
    
    public TerrainSystem(String terrainId, TerrainType terrainType, Position position) {
        this.terrainId = terrainId;
        this.terrainType = terrainType;
        this.position = position;
        this.terrainHealth = 100;
        this.maxTerrainHealth = 100;
        this.isDestroyed = false;
        this.hazards = new ArrayList<>();
        this.movementPenalties = new HashMap<>();
        this.terrainBonuses = new HashMap<>();
        this.isInteractable = false;
        this.interactionAbilities = new ArrayList<>();
        
        initializeTerrainProperties();
    }
    
    /**
     * Initialize terrain properties based on type
     */
    private void initializeTerrainProperties() {
        switch (terrainType) {
            case GRASS:
                movementPenalties.put("movement_cost", 1);
                terrainBonuses.put("stealth", 10);
                break;
            case WATER:
                movementPenalties.put("movement_cost", 2);
                hazards.add(new TerrainHazard("Drowning", 5, 3));
                break;
            case RUBBLE:
                movementPenalties.put("movement_cost", 2);
                terrainBonuses.put("cover", 20);
                break;
            case FIRE:
                movementPenalties.put("movement_cost", 3);
                hazards.add(new TerrainHazard("Burning", 10, 5));
                break;
            case ACID:
                movementPenalties.put("movement_cost", 2);
                hazards.add(new TerrainHazard("Acid Damage", 15, 4));
                break;
            case ICE:
                movementPenalties.put("movement_cost", 1);
                hazards.add(new TerrainHazard("Slipping", 2, 2));
                break;
            case ELECTRIC:
                movementPenalties.put("movement_cost", 2);
                hazards.add(new TerrainHazard("Electric Shock", 8, 3));
                break;
            case POISON:
                movementPenalties.put("movement_cost", 1);
                hazards.add(new TerrainHazard("Poison", 3, 6));
                break;
        }
    }
    
    /**
     * Take damage from explosions or attacks
     */
    public boolean takeDamage(int damage) {
        if (isDestroyed) {
            return false;
        }
        
        terrainHealth -= damage;
        
        if (terrainHealth <= 0) {
            isDestroyed = true;
            hazards.clear();
            movementPenalties.clear();
            terrainBonuses.clear();
            return true; // Terrain destroyed
        }
        
        // Reduce bonuses based on damage taken
        double damagePercentage = (double) damage / maxTerrainHealth;
        reduceBonuses(damagePercentage);
        
        return false; // Terrain still standing
    }
    
    /**
     * Reduce terrain bonuses based on damage taken
     */
    private void reduceBonuses(double damagePercentage) {
        for (Map.Entry<String, Integer> entry : terrainBonuses.entrySet()) {
            int currentBonus = entry.getValue();
            int reducedBonus = (int) (currentBonus * (1 - damagePercentage * 0.5));
            terrainBonuses.put(entry.getKey(), Math.max(0, reducedBonus));
        }
    }
    
    /**
     * Get movement penalty for this terrain
     */
    public int getMovementPenalty(String penaltyType) {
        if (isDestroyed) {
            return 0; // No penalty if destroyed
        }
        
        return movementPenalties.getOrDefault(penaltyType, 0);
    }
    
    /**
     * Get terrain bonus for specific attribute
     */
    public int getTerrainBonus(String attribute) {
        if (isDestroyed) {
            return 0;
        }
        
        return terrainBonuses.getOrDefault(attribute, 0);
    }
    
    /**
     * Apply terrain hazards to unit
     */
    public List<TerrainHazard> getActiveHazards() {
        if (isDestroyed) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(hazards);
    }
    
    /**
     * Add new hazard to terrain
     */
    public void addHazard(TerrainHazard hazard) {
        if (!isDestroyed) {
            hazards.add(hazard);
        }
    }
    
    /**
     * Remove hazard from terrain
     */
    public boolean removeHazard(String hazardName) {
        return hazards.removeIf(hazard -> hazard.getName().equals(hazardName));
    }
    
    /**
     * Check if terrain is interactable
     */
    public boolean isInteractable() {
        return isInteractable && !isDestroyed;
    }
    
    /**
     * Add interaction ability
     */
    public void addInteractionAbility(String ability) {
        if (!interactionAbilities.contains(ability)) {
            interactionAbilities.add(ability);
            isInteractable = true;
        }
    }
    
    /**
     * Get all interaction abilities
     */
    public List<String> getInteractionAbilities() {
        return new ArrayList<>(interactionAbilities);
    }
    
    /**
     * Check if terrain provides cover
     */
    public boolean providesCover() {
        return terrainBonuses.containsKey("cover") && !isDestroyed;
    }
    
    /**
     * Check if terrain provides stealth bonus
     */
    public boolean providesStealth() {
        return terrainBonuses.containsKey("stealth") && !isDestroyed;
    }
    
    /**
     * Get terrain status summary
     */
    public String getTerrainStatus() {
        if (isDestroyed) {
            return "Destroyed";
        }
        
        double healthPercentage = (terrainHealth / (double) maxTerrainHealth) * 100;
        return String.format("%s Terrain - %.1f%% Health", terrainType, healthPercentage);
    }
    
    /**
     * Repair terrain (restore health and bonuses)
     */
    public void repairTerrain(int repairAmount) {
        if (isDestroyed) {
            return;
        }
        
        terrainHealth = Math.min(maxTerrainHealth, terrainHealth + repairAmount);
        initializeTerrainProperties(); // Restore original properties
    }
    
    /**
     * Transform terrain to different type
     */
    public boolean transformTerrain(TerrainType newType) {
        if (isDestroyed) {
            return false;
        }
        
        this.terrainType = newType;
        this.hazards.clear();
        initializeTerrainProperties();
        return true;
    }
    
    /**
     * Terrain Hazard inner class
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TerrainHazard {
        private String name;
        private int damage;
        private int duration;
        private String effect;
        
        public TerrainHazard(String name, int damage, int duration) {
            this.name = name;
            this.damage = damage;
            this.duration = duration;
            this.effect = "Damage over time";
        }
    }
    
    /**
     * Terrain Type enum
     */
    public enum TerrainType {
        GRASS,      // Normal grass terrain
        WATER,      // Water terrain with drowning hazard
        RUBBLE,     // Rubble with cover bonus
        FIRE,       // Fire terrain with burning hazard
        ACID,       // Acid terrain with acid damage
        ICE,        // Ice terrain with slipping hazard
        ELECTRIC,   // Electric terrain with shock hazard
        POISON      // Poison terrain with poison hazard
    }
}

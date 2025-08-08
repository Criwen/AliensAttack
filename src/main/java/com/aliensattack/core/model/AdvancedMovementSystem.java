package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Movement System implementing XCOM 2 movement mechanics:
 * - Movement point costs for different actions
 * - Terrain-based movement penalties
 * - Movement interruption mechanics
 * - Movement prediction and pathfinding
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedMovementSystem {
    
    private String unitId;
    private int maxMovementPoints;
    private int currentMovementPoints;
    private Map<String, Integer> actionCosts;
    private Map<String, Integer> terrainPenalties;
    private List<MovementAbility> movementAbilities;
    private boolean isMovementInterrupted;
    private Position lastPosition;
    private List<Position> movementPath;
    private Map<String, Integer> movementPredictions;
    
    public AdvancedMovementSystem(String unitId, int maxMovementPoints) {
        this.unitId = unitId;
        this.maxMovementPoints = maxMovementPoints;
        this.currentMovementPoints = maxMovementPoints;
        this.actionCosts = new HashMap<>();
        this.terrainPenalties = new HashMap<>();
        this.movementAbilities = new ArrayList<>();
        this.isMovementInterrupted = false;
        this.lastPosition = null;
        this.movementPath = new ArrayList<>();
        this.movementPredictions = new HashMap<>();
        
        initializeActionCosts();
        initializeTerrainPenalties();
    }
    
    /**
     * Initialize action costs
     */
    private void initializeActionCosts() {
        actionCosts.put("move", 1);
        actionCosts.put("dash", 2);
        actionCosts.put("climb", 2);
        actionCosts.put("jump", 3);
        actionCosts.put("crawl", 1);
        actionCosts.put("sprint", 4);
        actionCosts.put("overwatch", 1);
        actionCosts.put("reload", 1);
        actionCosts.put("hack", 2);
        actionCosts.put("heal", 2);
    }
    
    /**
     * Initialize terrain penalties
     */
    private void initializeTerrainPenalties() {
        terrainPenalties.put("grass", 0);
        terrainPenalties.put("water", 2);
        terrainPenalties.put("rubble", 2);
        terrainPenalties.put("fire", 3);
        terrainPenalties.put("acid", 2);
        terrainPenalties.put("ice", 1);
        terrainPenalties.put("electric", 2);
        terrainPenalties.put("poison", 1);
    }
    
    /**
     * Move unit to new position
     */
    public boolean moveToPosition(Position newPosition, String terrainType) {
        int moveCost = calculateMoveCost(newPosition, terrainType);
        
        if (currentMovementPoints < moveCost) {
            return false; // Not enough movement points
        }
        
        // Check for movement interruption
        if (isMovementInterrupted) {
            return false;
        }
        
        // Update position and movement points
        lastPosition = newPosition;
        currentMovementPoints -= moveCost;
        movementPath.add(newPosition);
        
        return true;
    }
    
    /**
     * Calculate move cost for position
     */
    private int calculateMoveCost(Position newPosition, String terrainType) {
        int baseCost = actionCosts.getOrDefault("move", 1);
        int terrainPenalty = terrainPenalties.getOrDefault(terrainType, 0);
        
        // Add height difference penalty
        if (lastPosition != null) {
            int heightDifference = Math.abs(newPosition.getHeight() - lastPosition.getHeight());
            if (heightDifference > 0) {
                baseCost += heightDifference;
            }
        }
        
        return baseCost + terrainPenalty;
    }
    
    /**
     * Use movement ability
     */
    public boolean useMovementAbility(String abilityName, Position targetPosition) {
        MovementAbility ability = findMovementAbility(abilityName);
        if (ability == null || currentMovementPoints < ability.getCost()) {
            return false;
        }
        
        if (ability.canUseAbility(targetPosition)) {
            currentMovementPoints -= ability.getCost();
            ability.useAbility(targetPosition);
            return true;
        }
        
        return false;
    }
    
    /**
     * Find movement ability by name
     */
    private MovementAbility findMovementAbility(String abilityName) {
        return movementAbilities.stream()
                .filter(ability -> ability.getName().equals(abilityName))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Add movement ability
     */
    public void addMovementAbility(MovementAbility ability) {
        if (!movementAbilities.contains(ability)) {
            movementAbilities.add(ability);
        }
    }
    
    /**
     * Remove movement ability
     */
    public void removeMovementAbility(String abilityName) {
        movementAbilities.removeIf(ability -> ability.getName().equals(abilityName));
    }
    
    /**
     * Interrupt movement
     */
    public void interruptMovement() {
        isMovementInterrupted = true;
        movementPath.clear();
    }
    
    /**
     * Resume movement
     */
    public void resumeMovement() {
        isMovementInterrupted = false;
    }
    
    /**
     * Predict movement cost to position
     */
    public int predictMovementCost(Position targetPosition, String terrainType) {
        int baseCost = actionCosts.getOrDefault("move", 1);
        int terrainPenalty = terrainPenalties.getOrDefault(terrainType, 0);
        
        if (lastPosition != null) {
            int distance = lastPosition.getDistanceTo(targetPosition);
            int heightDifference = Math.abs(targetPosition.getHeight() - lastPosition.getHeight());
            
            return (baseCost * distance) + terrainPenalty + heightDifference;
        }
        
        return baseCost + terrainPenalty;
    }
    
    /**
     * Get available movement range
     */
    public List<Position> getAvailableMovementRange(Position currentPosition, String terrainType) {
        List<Position> availablePositions = new ArrayList<>();
        int maxRange = currentMovementPoints;
        
        // Simple range calculation (can be enhanced with pathfinding)
        for (int x = -maxRange; x <= maxRange; x++) {
            for (int y = -maxRange; y <= maxRange; y++) {
                Position testPosition = new Position(
                    currentPosition.getX() + x,
                    currentPosition.getY() + y,
                    currentPosition.getHeight()
                );
                
                int cost = predictMovementCost(testPosition, terrainType);
                if (cost <= currentMovementPoints) {
                    availablePositions.add(testPosition);
                }
            }
        }
        
        return availablePositions;
    }
    
    /**
     * Reset movement points
     */
    public void resetMovementPoints() {
        currentMovementPoints = maxMovementPoints;
        isMovementInterrupted = false;
        movementPath.clear();
    }
    
    /**
     * Get movement summary
     */
    public String getMovementSummary() {
        return String.format("Movement Points: %d/%d, Abilities: %d, Interrupted: %s",
            currentMovementPoints, maxMovementPoints, movementAbilities.size(), isMovementInterrupted);
    }
    
    /**
     * Movement Ability inner class
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MovementAbility {
        private String name;
        private int cost;
        private int range;
        private boolean requiresLineOfSight;
        private String description;
        
        public MovementAbility(String name, int cost, int range) {
            this.name = name;
            this.cost = cost;
            this.range = range;
            this.requiresLineOfSight = false;
            this.description = "Movement ability";
        }
        
        /**
         * Check if ability can be used to reach target position
         */
        public boolean canUseAbility(Position targetPosition) {
            // Basic range check - simplified for static context
            return range > 0; // Basic range check
        }
        
        /**
         * Use the movement ability
         */
        public void useAbility(Position targetPosition) {
            // Ability-specific logic would go here
            // For now, just mark as used
        }
    }
}

package com.aliensattack.core.model;

import com.aliensattack.core.enums.CoverType;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Cover System implementing XCOM 2 cover mechanics:
 * - Partial cover (50% cover)
 * - Cover destruction by explosions
 * - Cover movement between positions
 * - Height-based cover bonuses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedCoverSystem {
    
    private String coverId;
    private CoverType coverType;
    private int coverHeight;
    private int coverHealth;
    private int maxCoverHealth;
    private boolean isDestroyed;
    private Position position;
    private List<Position> adjacentPositions;
    private Map<String, Integer> coverBonuses;
    private boolean isPartialCover;
    private double partialCoverChance;
    
    public AdvancedCoverSystem(String coverId, CoverType coverType, Position position) {
        this.coverId = coverId;
        this.coverType = coverType;
        this.position = position;
        this.coverHeight = 1;
        this.coverHealth = 100;
        this.maxCoverHealth = 100;
        this.isDestroyed = false;
        this.adjacentPositions = new ArrayList<>();
        this.coverBonuses = new HashMap<>();
        this.isPartialCover = false;
        this.partialCoverChance = 0.5;
        
        initializeCoverBonuses();
    }
    
    /**
     * Initialize cover bonuses based on type and height
     */
    private void initializeCoverBonuses() {
        switch (coverType) {
            case NONE:
                // No cover bonuses
                break;
            case LIGHT:
                coverBonuses.put("defense", 20);
                coverBonuses.put("dodge", 5);
                isPartialCover = true;
                break;
            case HEAVY:
                coverBonuses.put("defense", 40);
                coverBonuses.put("dodge", 10);
                break;
            case FULL:
                coverBonuses.put("defense", 60);
                coverBonuses.put("dodge", 15);
                break;
            case FLANKED:
                // No cover bonuses when flanked
                break;
            case FULL_COVER:
                coverBonuses.put("defense", 40);
                coverBonuses.put("dodge", 20);
                break;
            case HALF_COVER:
                coverBonuses.put("defense", 20);
                coverBonuses.put("dodge", 10);
                isPartialCover = true;
                break;
            case LOW_COVER:
                coverBonuses.put("defense", 10);
                coverBonuses.put("dodge", 5);
                isPartialCover = true;
                break;
            case DEEP_COVER:
                coverBonuses.put("defense", 80);
                coverBonuses.put("dodge", 20);
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
        
        coverHealth -= damage;
        
        if (coverHealth <= 0) {
            isDestroyed = true;
            coverBonuses.clear();
            return true; // Cover destroyed
        }
        
        // Reduce bonuses based on damage taken
        double damagePercentage = (double) damage / maxCoverHealth;
        reduceBonuses(damagePercentage);
        
        return false; // Cover still standing
    }
    
    /**
     * Reduce cover bonuses based on damage taken
     */
    private void reduceBonuses(double damagePercentage) {
        for (Map.Entry<String, Integer> entry : coverBonuses.entrySet()) {
            int currentBonus = entry.getValue();
            int reducedBonus = (int) (currentBonus * (1 - damagePercentage * 0.5));
            coverBonuses.put(entry.getKey(), Math.max(0, reducedBonus));
        }
    }
    
    /**
     * Check if unit can move to this cover position
     */
    public boolean canMoveToCover(Position unitPosition) {
        if (isDestroyed) {
            return false;
        }
        
        int distance = position.getDistanceTo(unitPosition);
        return distance <= 1; // Adjacent positions only
    }
    
    /**
     * Get cover bonus for specific attribute
     */
    public int getCoverBonus(String attribute) {
        if (isDestroyed) {
            return 0;
        }
        
        return coverBonuses.getOrDefault(attribute, 0);
    }
    
    /**
     * Check if cover provides partial cover protection
     */
    public boolean providesPartialCover() {
        return isPartialCover && !isDestroyed;
    }
    
    /**
     * Calculate partial cover chance
     */
    public double getPartialCoverChance() {
        if (!providesPartialCover()) {
            return 0.0;
        }
        
        return partialCoverChance * (coverHealth / (double) maxCoverHealth);
    }
    
    /**
     * Add adjacent position for cover movement
     */
    public void addAdjacentPosition(Position position) {
        if (!adjacentPositions.contains(position)) {
            adjacentPositions.add(position);
        }
    }
    
    /**
     * Get all adjacent cover positions
     */
    public List<Position> getAdjacentPositions() {
        return new ArrayList<>(adjacentPositions);
    }
    
    /**
     * Check if cover is flanked from given position
     */
    public boolean isFlankedFrom(Position attackerPosition) {
        if (isDestroyed) {
            return true;
        }
        
        // Calculate angle between attacker and cover
        double angle = calculateAngle(attackerPosition, position);
        
        // Check if angle indicates flanking
        return angle > 45 && angle < 135;
    }
    
    /**
     * Calculate angle between two positions
     */
    private double calculateAngle(Position pos1, Position pos2) {
        int deltaX = pos2.getX() - pos1.getX();
        int deltaY = pos2.getY() - pos1.getY();
        
        return Math.toDegrees(Math.atan2(deltaY, deltaX));
    }
    
    /**
     * Repair cover (restore health and bonuses)
     */
    public void repairCover(int repairAmount) {
        if (isDestroyed) {
            return;
        }
        
        coverHealth = Math.min(maxCoverHealth, coverHealth + repairAmount);
        initializeCoverBonuses(); // Restore original bonuses
    }
    
    /**
     * Upgrade cover to higher type
     */
    public boolean upgradeCover(CoverType newType) {
        if (isDestroyed) {
            return false;
        }
        
        this.coverType = newType;
        initializeCoverBonuses();
        return true;
    }
    
    /**
     * Get cover status summary
     */
    public String getCoverStatus() {
        if (isDestroyed) {
            return "Destroyed";
        }
        
        double healthPercentage = (coverHealth / (double) maxCoverHealth) * 100;
        return String.format("%s Cover - %.1f%% Health", coverType, healthPercentage);
    }
}

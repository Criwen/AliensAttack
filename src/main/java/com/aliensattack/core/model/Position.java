package com.aliensattack.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents a position on the tactical field
 * Enhanced with height mechanics for elevation-based bonuses
 */
@Getter
@Setter
public class Position {
    private int x;
    private int y;
    private int height; // Height level (0 = ground, 1 = elevated, 2 = high ground, etc.)
    private boolean isElevated; // Whether position is on elevated terrain
    private boolean hasHeightAdvantage; // Whether position provides height advantage
    private int heightAdvantageBonus; // Bonus from height advantage
    private boolean isRooftop; // Whether position is on rooftop
    private boolean isBalcony; // Whether position is on balcony
    private boolean isStairs; // Whether position is on stairs
    private boolean isLadder; // Whether position is on ladder
    private boolean isElevator; // Whether position is on elevator
    private boolean isBridge; // Whether position is on bridge
    private boolean isUnderground; // Whether position is underground
    private boolean isWaterLevel; // Whether position is at water level
    private boolean isAcidLevel; // Whether position is in acid
    private boolean isFireLevel; // Whether position is on fire
    private boolean isRadiationLevel; // Whether position is in radiation
    private boolean isFrostLevel; // Whether position is in frost
    private boolean isElectrocutedLevel; // Whether position is electrocuted
    private boolean isCorrosionLevel; // Whether position is in corrosion
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.height = 0;
        this.isElevated = false;
        this.hasHeightAdvantage = false;
        this.heightAdvantageBonus = 0;
        this.isRooftop = false;
        this.isBalcony = false;
        this.isStairs = false;
        this.isLadder = false;
        this.isElevator = false;
        this.isBridge = false;
        this.isUnderground = false;
        this.isWaterLevel = false;
        this.isAcidLevel = false;
        this.isFireLevel = false;
        this.isRadiationLevel = false;
        this.isFrostLevel = false;
        this.isElectrocutedLevel = false;
        this.isCorrosionLevel = false;
    }
    
    public Position(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.isElevated = height > 0;
        this.hasHeightAdvantage = height >= 2;
        this.heightAdvantageBonus = calculateHeightAdvantageBonus(height);
        this.isRooftop = false;
        this.isBalcony = false;
        this.isStairs = false;
        this.isLadder = false;
        this.isElevator = false;
        this.isBridge = false;
        this.isUnderground = false;
        this.isWaterLevel = false;
        this.isAcidLevel = false;
        this.isFireLevel = false;
        this.isRadiationLevel = false;
        this.isFrostLevel = false;
        this.isElectrocutedLevel = false;
        this.isCorrosionLevel = false;
    }
    
    /**
     * Calculate height advantage bonus based on height level
     */
    private int calculateHeightAdvantageBonus(int height) {
        if (height <= 0) {
            return 0;
        }
        
        // +10% accuracy per height level, +1 damage per 10% accuracy bonus
        int accuracyBonus = height * 10;
        int damageBonus = accuracyBonus / 10;
        
        return accuracyBonus + damageBonus;
    }
    
    /**
     * Set height and update related properties
     */
    public void setHeight(int height) {
        this.height = height;
        this.isElevated = height > 0;
        this.hasHeightAdvantage = height >= 2;
        this.heightAdvantageBonus = calculateHeightAdvantageBonus(height);
    }
    
    /**
     * Get height advantage accuracy bonus
     */
    public int getHeightAccuracyBonus() {
        if (!hasHeightAdvantage) {
            return 0;
        }
        return height * 10; // +10% per height level
    }
    
    /**
     * Get height advantage damage bonus
     */
    public int getHeightDamageBonus() {
        if (!hasHeightAdvantage) {
            return 0;
        }
        return height; // +1 damage per height level
    }
    
    /**
     * Get total height advantage bonus
     */
    public int getTotalHeightAdvantageBonus() {
        return getHeightAccuracyBonus() + getHeightDamageBonus();
    }
    
    /**
     * Check if position provides natural cover due to height
     */
    public boolean providesNaturalCover() {
        return isElevated || isRooftop || isBalcony || isBridge;
    }
    
    /**
     * Get natural cover bonus from height
     */
    public int getNaturalCoverBonus() {
        if (!providesNaturalCover()) {
            return 0;
        }
        
        if (isRooftop) return 30;
        if (isBalcony) return 25;
        if (isBridge) return 20;
        if (isElevated) return 15;
        
        return 0;
    }
    
    /**
     * Check if position blocks line of sight due to height
     */
    public boolean blocksLineOfSight() {
        return isElevated && height >= 2;
    }
    
    /**
     * Check if position blocks movement due to height
     */
    public boolean blocksMovement() {
        return isUnderground || isWaterLevel || isAcidLevel || isFireLevel;
    }
    
    /**
     * Check if position is hazardous
     */
    public boolean isHazardous() {
        return isAcidLevel || isFireLevel || isRadiationLevel || 
               isFrostLevel || isElectrocutedLevel || isCorrosionLevel;
    }
    
    /**
     * Get hazard damage
     */
    public int getHazardDamage() {
        if (isAcidLevel) return 5;
        if (isFireLevel) return 3;
        if (isRadiationLevel) return 4;
        if (isFrostLevel) return 2;
        if (isElectrocutedLevel) return 6;
        if (isCorrosionLevel) return 4;
        return 0;
    }
    
    /**
     * Check if position is accessible
     */
    public boolean isAccessible() {
        return !blocksMovement() && !isHazardous();
    }
    
    /**
     * Calculate distance to another position
     */
    public int getDistanceTo(Position other) {
        int dx = Math.abs(this.x - other.x);
        int dy = Math.abs(this.y - other.y);
        int dz = Math.abs(this.height - other.height);
        
        // Use 3D distance calculation
        return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    /**
     * Calculate 2D distance to another position (ignoring height)
     */
    public int getDistance2D(Position other) {
        int dx = Math.abs(this.x - other.x);
        int dy = Math.abs(this.y - other.y);
        return (int) Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Check if position is adjacent to another position
     */
    public boolean isAdjacent(Position other) {
        int distance = getDistance2D(other);
        return distance == 1;
    }
    
    /**
     * Check if position is within range of another position
     */
    public boolean isWithinRange(Position other, int range) {
        int distance = getDistance2D(other);
        return distance <= range;
    }
    
    /**
     * Check if position has line of sight to another position
     */
    public boolean hasLineOfSight(Position other) {
        // Basic line of sight check - can be enhanced with Bresenham algorithm
        if (this.equals(other)) {
            return true;
        }
        
        // Check if either position blocks line of sight
        if (this.blocksLineOfSight() || other.blocksLineOfSight()) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Get elevation difference with another position
     */
    public int getElevationDifference(Position other) {
        return Math.abs(this.height - other.height);
    }
    
    /**
     * Check if this position is higher than another position
     */
    public boolean isHigherThan(Position other) {
        return this.height > other.height;
    }
    
    /**
     * Check if this position is lower than another position
     */
    public boolean isLowerThan(Position other) {
        return this.height < other.height;
    }
    
    /**
     * Check if positions are at same elevation
     */
    public boolean isSameElevation(Position other) {
        return this.height == other.height;
    }
    
    /**
     * Get movement cost to reach this position
     */
    public int getMovementCost() {
        int baseCost = 1;
        
        if (isStairs) baseCost += 1;
        if (isLadder) baseCost += 2;
        if (isElevator) baseCost += 1;
        if (isWaterLevel) baseCost += 2;
        if (isAcidLevel) baseCost += 3;
        if (isFireLevel) baseCost += 2;
        if (isRadiationLevel) baseCost += 2;
        if (isFrostLevel) baseCost += 1;
        if (isElectrocutedLevel) baseCost += 3;
        if (isCorrosionLevel) baseCost += 2;
        
        return baseCost;
    }
    
    /**
     * Get position description
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append("(").append(x).append(", ").append(y).append(")");
        
        if (height > 0) {
            desc.append(" Height: ").append(height);
        }
        
        if (isElevated) desc.append(" [Elevated]");
        if (hasHeightAdvantage) desc.append(" [Height Advantage]");
        if (isRooftop) desc.append(" [Rooftop]");
        if (isBalcony) desc.append(" [Balcony]");
        if (isStairs) desc.append(" [Stairs]");
        if (isLadder) desc.append(" [Ladder]");
        if (isElevator) desc.append(" [Elevator]");
        if (isBridge) desc.append(" [Bridge]");
        if (isUnderground) desc.append(" [Underground]");
        if (isWaterLevel) desc.append(" [Water]");
        if (isAcidLevel) desc.append(" [Acid]");
        if (isFireLevel) desc.append(" [Fire]");
        if (isRadiationLevel) desc.append(" [Radiation]");
        if (isFrostLevel) desc.append(" [Frost]");
        if (isElectrocutedLevel) desc.append(" [Electrocuted]");
        if (isCorrosionLevel) desc.append(" [Corrosion]");
        
        return desc.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y && height == position.height;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y, height);
    }
    
    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", height=" + height +
                '}';
    }
    
    // Additional methods for compatibility
    public boolean hasHeightAdvantage() {
        return hasHeightAdvantage;
    }
    
    public boolean hasHeightAdvantageOver(Position other) {
        return this.height > other.height;
    }
} 
package com.aliensattack.core.model;

import com.aliensattack.core.enums.CoverType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a cover object on the tactical field
 */
@Getter
@Setter
public class CoverObject {
    private Position position;
    private CoverType coverType;
    private int durability; // How much damage cover can take
    private boolean isDestructible;
    private boolean isDestroyed;
    
    public CoverObject(Position position, CoverType coverType, int durability) {
        this.position = position;
        this.coverType = coverType;
        this.durability = durability;
        this.isDestructible = durability > 0;
        this.isDestroyed = false;
    }
    
    /**
     * Check if this cover provides protection from a specific direction
     */
    public boolean providesCoverFrom(Position attackerPos) {
        if (isDestroyed) {
            return false;
        }
        
        // Simple directional check - cover protects from front
        int dx = position.getX() - attackerPos.getX();
        int dy = position.getY() - attackerPos.getY();
        
        // Cover provides protection if attacker is in front
        return Math.abs(dx) > Math.abs(dy) || Math.abs(dy) > Math.abs(dx);
    }
    
    /**
     * Take damage to cover
     */
    public boolean takeDamage(int damage) {
        if (!isDestructible || isDestroyed) {
            return false;
        }
        
        durability -= damage;
        if (durability <= 0) {
            isDestroyed = true;
            coverType = CoverType.NONE;
            return true; // Cover destroyed
        }
        return false;
    }
    
    /**
     * Get defense bonus provided by this cover
     */
    public int getDefenseBonus() {
        if (isDestroyed) {
            return 0;
        }
        
        switch (coverType) {
            case LIGHT: return 20;
            case HEAVY: return 40;
            case FULL: return 60;
            default: return 0;
        }
    }
    
    /**
     * Check if cover provides Deep Cover
     */
    public boolean providesDeepCover() {
        return coverType == CoverType.DEEP_COVER;
    }
    
    /**
     * Get Deep Cover defense bonus
     */
    public int getDeepCoverDefenseBonus() {
        if (providesDeepCover()) {
            return 80; // +80% defense for deep cover
        }
        return getDefenseBonus();
    }
    
    /**
     * Get Deep Cover dodge bonus
     */
    public int getDeepCoverDodgeBonus() {
        if (providesDeepCover()) {
            return 20; // +20% dodge for deep cover
        }
        return 0;
    }
    
    /**
     * Get total Deep Cover bonus
     */
    public int getTotalDeepCoverBonus() {
        return getDeepCoverDefenseBonus() + getDeepCoverDodgeBonus();
    }
    
    /**
     * Get the name of the cover object
     */
    public String getName() {
        return switch (coverType) {
            case LIGHT -> "Light Cover";
            case HEAVY -> "Heavy Cover";
            case FULL -> "Full Cover";
            case FULL_COVER -> "Full Cover";
            case HALF_COVER -> "Half Cover";
            case LOW_COVER -> "Low Cover";
            case DEEP_COVER -> "Deep Cover";
            case FLANKED -> "Flanked";
            default -> "No Cover";
        };
    }
    
    /**
     * Set position using x and y coordinates
     */
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }
} 
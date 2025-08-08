package com.aliensattack.core.model;

import com.aliensattack.core.enums.TerrainType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents destructible environment objects in the tactical field
 * Enhanced with health system and destruction mechanics
 */
@Getter
@Setter
public class EnvironmentObject {
    private String name;
    private Position position;
    private TerrainType terrainType;
    private int maxHealth;
    private int currentHealth;
    private boolean isDestructible;
    private boolean isDestroyed;
    private int destructionDamage; // Damage dealt to units when destroyed
    private int coverBonus; // Cover bonus provided when intact
    private boolean providesCover;
    private boolean blocksLineOfSight;
    private boolean blocksMovement;
    private int height; // Height level of the object
    private boolean isFlammable;
    private boolean isExplosive;
    private int explosionRadius;
    private int explosionDamage;
    
    public EnvironmentObject(String name, Position position, TerrainType terrainType, int maxHealth) {
        this.name = name;
        this.position = position;
        this.terrainType = terrainType;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.isDestructible = maxHealth > 0;
        this.isDestroyed = false;
        this.destructionDamage = 0;
        this.coverBonus = 0;
        this.providesCover = false;
        this.blocksLineOfSight = false;
        this.blocksMovement = false;
        this.height = 0;
        this.isFlammable = false;
        this.isExplosive = false;
        this.explosionRadius = 0;
        this.explosionDamage = 0;
        
        // Set properties based on terrain type
        setPropertiesByTerrainType(terrainType);
    }
    
    private void setPropertiesByTerrainType(TerrainType terrainType) {
        switch (terrainType) {
            case WALL:
                providesCover = true;
                coverBonus = 40;
                blocksLineOfSight = true;
                blocksMovement = true;
                height = 2;
                isDestructible = true;
                break;
            case COVER:
                providesCover = true;
                coverBonus = 20;
                blocksLineOfSight = false;
                blocksMovement = false;
                height = 1;
                isDestructible = true;
                break;
            case BARRIER:
                providesCover = true;
                coverBonus = 30;
                blocksLineOfSight = true;
                blocksMovement = false;
                height = 1;
                isDestructible = true;
                break;
            case EXPLOSIVE_BARREL:
                providesCover = false;
                coverBonus = 0;
                blocksLineOfSight = false;
                blocksMovement = false;
                height = 1;
                isDestructible = true;
                isExplosive = true;
                explosionRadius = 3;
                explosionDamage = 15;
                break;
            case FUEL_TANK:
                providesCover = false;
                coverBonus = 0;
                blocksLineOfSight = false;
                blocksMovement = false;
                height = 1;
                isDestructible = true;
                isFlammable = true;
                isExplosive = true;
                explosionRadius = 4;
                explosionDamage = 20;
                break;
            case VEHICLE:
                providesCover = true;
                coverBonus = 50;
                blocksLineOfSight = true;
                blocksMovement = true;
                height = 2;
                isDestructible = true;
                isExplosive = true;
                explosionRadius = 2;
                explosionDamage = 10;
                break;
            case DEBRIS:
                providesCover = true;
                coverBonus = 15;
                blocksLineOfSight = false;
                blocksMovement = false;
                height = 0;
                isDestructible = true;
                break;
            case RUBBLE:
                providesCover = false;
                coverBonus = 0;
                blocksLineOfSight = false;
                blocksMovement = true;
                height = 0;
                isDestructible = false;
                break;
            case WATER:
                providesCover = false;
                coverBonus = 0;
                blocksLineOfSight = false;
                blocksMovement = false;
                height = 0;
                isDestructible = false;
                break;
            case ACID:
                providesCover = false;
                coverBonus = 0;
                blocksLineOfSight = false;
                blocksMovement = false;
                height = 0;
                isDestructible = false;
                break;
            case FIRE:
                providesCover = false;
                coverBonus = 0;
                blocksLineOfSight = false;
                blocksMovement = false;
                height = 0;
                isDestructible = false;
                break;
            default:
                providesCover = false;
                coverBonus = 0;
                blocksLineOfSight = false;
                blocksMovement = false;
                height = 0;
                isDestructible = false;
                break;
        }
    }
    
    /**
     * Take damage to the environment object
     */
    public boolean takeDamage(int damage) {
        if (!isDestructible || isDestroyed) {
            return false;
        }
        
        currentHealth -= damage;
        
        if (currentHealth <= 0) {
            destroy();
            return true;
        }
        
        return false;
    }
    
    /**
     * Destroy the environment object
     */
    public void destroy() {
        if (isDestroyed) {
            return;
        }
        
        isDestroyed = true;
        currentHealth = 0;
        providesCover = false;
        coverBonus = 0;
        blocksLineOfSight = false;
        blocksMovement = false;
        
        // Change terrain type to rubble when destroyed
        terrainType = TerrainType.RUBBLE;
    }
    
    /**
     * Check if object provides cover
     */
    public boolean providesCover() {
        return providesCover && !isDestroyed;
    }
    
    /**
     * Get current cover bonus
     */
    public int getCurrentCoverBonus() {
        if (isDestroyed) {
            return 0;
        }
        
        // Reduce cover bonus based on damage taken
        double healthPercentage = (double) currentHealth / maxHealth;
        return (int) (coverBonus * healthPercentage);
    }
    
    /**
     * Check if object blocks line of sight
     */
    public boolean blocksLineOfSight() {
        return blocksLineOfSight && !isDestroyed;
    }
    
    /**
     * Check if object blocks movement
     */
    public boolean blocksMovement() {
        return blocksMovement && !isDestroyed;
    }
    
    /**
     * Get health percentage
     */
    public double getHealthPercentage() {
        return (double) currentHealth / maxHealth;
    }
    
    /**
     * Check if object is intact
     */
    public boolean isIntact() {
        return !isDestroyed && currentHealth > 0;
    }
    
    /**
     * Check if object is damaged
     */
    public boolean isDamaged() {
        return !isDestroyed && currentHealth < maxHealth && currentHealth > 0;
    }
    
    /**
     * Check if object is heavily damaged
     */
    public boolean isHeavilyDamaged() {
        return !isDestroyed && currentHealth < maxHealth * 0.5 && currentHealth > 0;
    }
    
    /**
     * Get destruction damage
     */
    public int getDestructionDamage() {
        if (isDestroyed) {
            return destructionDamage;
        }
        return 0;
    }
    
    /**
     * Check if object can explode
     */
    public boolean canExplode() {
        return isExplosive && !isDestroyed;
    }
    
    /**
     * Check if object is flammable
     */
    public boolean isFlammable() {
        return isFlammable && !isDestroyed;
    }
    
    /**
     * Get explosion radius
     */
    public int getExplosionRadius() {
        if (canExplode()) {
            return explosionRadius;
        }
        return 0;
    }
    
    /**
     * Get explosion damage
     */
    public int getExplosionDamage() {
        if (canExplode()) {
            return explosionDamage;
        }
        return 0;
    }
    
    /**
     * Trigger explosion
     */
    public void triggerExplosion() {
        if (canExplode()) {
            destroy();
        }
    }
    
    /**
     * Set position
     */
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }
    
    /**
     * Get position
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * Set position
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    
    // Additional method for compatibility
    public boolean providedCover() {
        return providesCover;
    }
} 
package com.aliensattack.core.model;

import com.aliensattack.core.enums.ExplosiveType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an explosive device
 */
@Getter
@Setter
public class Explosive {
    private String name;
    private ExplosiveType type;
    private int damage;
    private int radius;
    private int timer; // Turns until explosion (0 = immediate)
    private int currentTimer;
    private boolean isArmed;
    private boolean isDetonated;
    
    public Explosive(String name, ExplosiveType type, int damage, int radius, int timer) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.radius = radius;
        this.timer = timer;
        this.currentTimer = timer;
        this.isArmed = false;
        this.isDetonated = false;
    }
    
    public void arm() {
        isArmed = true;
        currentTimer = timer;
    }
    
    public void detonate() {
        isDetonated = true;
        isArmed = false;
    }
    
    public void decrementTimer() {
        if (isArmed && currentTimer > 0) {
            currentTimer--;
            if (currentTimer <= 0) {
                detonate();
            }
        }
    }
    
    public boolean isReadyToExplode() {
        return isArmed && currentTimer <= 0;
    }
    
    public boolean isProximityTriggered() {
        return type == ExplosiveType.PROXIMITY_MINE;
    }
    
    public boolean isRemoteTriggered() {
        return type == ExplosiveType.REMOTE_CHARGE;
    }
    
    /**
     * Check if explosive is Volatile Mix
     */
    public boolean isVolatileMix() {
        return type == ExplosiveType.VOLATILE_MIX;
    }
    
    /**
     * Get Volatile Mix chain reaction damage
     */
    public int getVolatileMixChainDamage() {
        if (isVolatileMix()) {
            return damage * 2; // Double damage for chain reactions
        }
        return damage;
    }
    
    /**
     * Get Volatile Mix chain reaction radius
     */
    public int getVolatileMixChainRadius() {
        if (isVolatileMix()) {
            return radius + 2; // +2 radius for chain reactions
        }
        return radius;
    }
    
    /**
     * Check if Volatile Mix can trigger chain reaction
     */
    public boolean canTriggerChainReaction() {
        return isVolatileMix() && !isDetonated;
    }
    
    /**
     * Trigger Volatile Mix chain reaction
     */
    public void triggerChainReaction() {
        if (isVolatileMix()) {
            damage = getVolatileMixChainDamage();
            radius = getVolatileMixChainRadius();
        }
    }
    
    // Additional getter methods for compatibility
    public int getRadius() {
        return radius;
    }
} 
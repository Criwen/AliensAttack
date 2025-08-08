package com.aliensattack.core.model;

import com.aliensattack.core.enums.ArmorType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents armor with protection characteristics
 */
@Getter
@Setter
public class Armor {
    private String name;
    private ArmorType type;
    private int damageReduction;
    private int maxHealth;
    private int currentHealth;
    private boolean isDestroyed;
    
    public Armor(String name, ArmorType type, int damageReduction, int maxHealth) {
        this.name = name;
        this.type = type;
        this.damageReduction = damageReduction;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.isDestroyed = false;
    }
    
    /**
     * Apply damage to armor and return remaining damage
     */
    public int takeDamage(int damage) {
        if (isDestroyed) {
            return damage; // No protection if destroyed
        }
        
        currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            isDestroyed = true;
            damageReduction = 0; // No protection when destroyed
        }
        
        return Math.max(0, damage - damageReduction);
    }
    
    /**
     * Get effective damage reduction
     */
    public int getEffectiveDamageReduction() {
        return isDestroyed ? 0 : damageReduction;
    }
    
    /**
     * Get armor health percentage
     */
    public double getHealthPercentage() {
        return (double) currentHealth / maxHealth;
    }
    
    /**
     * Repair armor
     */
    public void repair(int amount) {
        if (!isDestroyed) {
            currentHealth = Math.min(currentHealth + amount, maxHealth);
        }
    }
} 
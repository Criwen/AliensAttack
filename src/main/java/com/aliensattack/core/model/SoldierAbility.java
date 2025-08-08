package com.aliensattack.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a soldier ability that can be used in combat
 */
@Getter
@Setter
public class SoldierAbility {
    private String name;
    private String description;
    private int actionPointCost;
    private int cooldown;
    private int currentCooldown;
    private boolean isActive;
    private boolean isHackingAbility;
    private boolean isTechnicalAbility;
    private int hackingPower;
    private int technicalEffect;
    
    public SoldierAbility(String name, String description, int actionPointCost, int cooldown) {
        this.name = name;
        this.description = description;
        this.actionPointCost = actionPointCost;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
        this.isActive = false;
        this.isHackingAbility = false;
        this.isTechnicalAbility = false;
        this.hackingPower = 0;
        this.technicalEffect = 0;
    }
    
    /**
     * Use the ability
     */
    public boolean useAbility() {
        if (currentCooldown > 0) {
            return false; // Still on cooldown
        }
        
        isActive = true;
        currentCooldown = cooldown;
        return true;
    }
    
    /**
     * Process cooldown reduction
     */
    public void processCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }
    
    /**
     * Check if ability can be used
     */
    public boolean canUse() {
        return currentCooldown <= 0;
    }
    
    /**
     * Set as hacking ability
     */
    public void setAsHackingAbility(int hackingPower) {
        this.isHackingAbility = true;
        this.hackingPower = hackingPower;
    }
    
    /**
     * Set as technical ability
     */
    public void setAsTechnicalAbility(int technicalEffect) {
        this.isTechnicalAbility = true;
        this.technicalEffect = technicalEffect;
    }
    
    /**
     * Get hacking success chance
     */
    public int getHackingSuccessChance() {
        if (!isHackingAbility) {
            return 0;
        }
        return Math.min(hackingPower * 10, 95); // Max 95% success chance
    }
    
    /**
     * Get technical effect power
     */
    public int getTechnicalEffectPower() {
        if (!isTechnicalAbility) {
            return 0;
        }
        return technicalEffect;
    }
    
    /**
     * Use the ability (alias for useAbility)
     */
    public boolean use() {
        return useAbility();
    }
} 
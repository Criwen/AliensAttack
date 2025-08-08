package com.aliensattack.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a medikit for healing units
 * Implements XCOM 2 medical system mechanics
 */
@Getter
@Setter
public class Medikit {
    private String name;
    private int maxUses;
    private int currentUses;
    private int healingAmount;
    private boolean canStabilize;
    private boolean canRevive;
    private boolean canRemoveStatusEffects;
    private int stabilizationChance;
    private int revivalChance;
    private int statusRemovalChance;
    private boolean isAdvanced;
    private boolean isExperimental;
    private boolean isNanotech;
    private boolean isPsionic;
    private boolean isRobotic;
    private boolean isAlien;
    private boolean isHuman;
    private boolean isHybrid;
    
    public Medikit(String name, int maxUses, int healingAmount) {
        this.name = name;
        this.maxUses = maxUses;
        this.currentUses = maxUses;
        this.healingAmount = healingAmount;
        this.canStabilize = false;
        this.canRevive = false;
        this.canRemoveStatusEffects = false;
        this.stabilizationChance = 0;
        this.revivalChance = 0;
        this.statusRemovalChance = 0;
        this.isAdvanced = false;
        this.isExperimental = false;
        this.isNanotech = false;
        this.isPsionic = false;
        this.isRobotic = false;
        this.isAlien = false;
        this.isHuman = true;
        this.isHybrid = false;
    }
    
    /**
     * Use the medikit
     */
    public int use() {
        if (!canUse()) {
            return 0;
        }
        
        currentUses--;
        return healingAmount;
    }
    
    /**
     * Use medikit on specific unit
     */
    public int use(Unit target) {
        if (!canUse()) {
            return 0;
        }
        
        int healingApplied = healingAmount;
        
        // Apply healing
            target.heal(healingAmount);
        
        // Try to stabilize if unit is critically wounded
        if (canStabilize && target.getHealthPercentage() <= 0.25) {
            if (Math.random() * 100 < stabilizationChance) {
                target.stabilize();
                healingApplied += 5; // Bonus healing for stabilization
            }
        }
        
        // Try to revive if unit is dead
        if (canRevive && !target.isAlive()) {
            if (Math.random() * 100 < revivalChance) {
                target.revive();
                healingApplied += 10; // Bonus healing for revival
            }
        }
        
        // Try to remove status effects
        if (canRemoveStatusEffects && target.hasStatusEffects()) {
            if (Math.random() * 100 < statusRemovalChance) {
            target.clearStatusEffects();
                healingApplied += 3; // Bonus healing for status removal
            }
        }
        
        currentUses--;
        return healingApplied;
    }
    
    /**
     * Check if medikit can be used
     */
    public boolean canUse() {
        return currentUses > 0;
    }
    
    /**
     * Get remaining uses
     */
    public int getRemainingUses() {
        return currentUses;
    }
    
    /**
     * Get usage percentage
     */
    public double getUsagePercentage() {
        return (double) currentUses / maxUses;
    }
    
    /**
     * Refill medikit
     */
    public void refill() {
        currentUses = maxUses;
    }
    
    /**
     * Refill medikit partially
     */
    public void refill(int uses) {
        currentUses = Math.min(currentUses + uses, maxUses);
    }
    
    /**
     * Upgrade medikit
     */
    public void upgrade() {
        if (!isAdvanced) {
            isAdvanced = true;
            healingAmount += 5;
            maxUses += 2;
            currentUses += 2;
        }
    }
    
    /**
     * Make experimental
     */
    public void makeExperimental() {
        isExperimental = true;
        healingAmount += 10;
        canStabilize = true;
        stabilizationChance = 75;
    }
    
    /**
     * Make nanotech
     */
    public void makeNanotech() {
        isNanotech = true;
        healingAmount += 15;
        canRevive = true;
        revivalChance = 50;
        canRemoveStatusEffects = true;
        statusRemovalChance = 80;
    }
    
    /**
     * Make psionic
     */
    public void makePsionic() {
        isPsionic = true;
        healingAmount += 8;
        canRemoveStatusEffects = true;
        statusRemovalChance = 90;
    }
    
    /**
     * Make robotic
     */
    public void makeRobotic() {
        isRobotic = true;
        healingAmount += 12;
        canStabilize = true;
        stabilizationChance = 90;
    }
    
    /**
     * Make alien
     */
    public void makeAlien() {
        isAlien = true;
        healingAmount += 20;
        canRevive = true;
        revivalChance = 75;
        canRemoveStatusEffects = true;
        statusRemovalChance = 95;
    }
    
    /**
     * Make hybrid
     */
    public void makeHybrid() {
        isHybrid = true;
        healingAmount += 25;
        canStabilize = true;
        canRevive = true;
        canRemoveStatusEffects = true;
        stabilizationChance = 95;
        revivalChance = 85;
        statusRemovalChance = 100;
    }
    
    /**
     * Get medikit description
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append("\n");
        desc.append("Uses: ").append(currentUses).append("/").append(maxUses).append("\n");
        desc.append("Healing: ").append(healingAmount).append(" HP\n");
        
        if (canStabilize) desc.append("Can Stabilize (").append(stabilizationChance).append("%)\n");
        if (canRevive) desc.append("Can Revive (").append(revivalChance).append("%)\n");
        if (canRemoveStatusEffects) desc.append("Can Remove Status Effects (").append(statusRemovalChance).append("%)\n");
        
        if (isAdvanced) desc.append("Advanced\n");
        if (isExperimental) desc.append("Experimental\n");
        if (isNanotech) desc.append("Nanotech\n");
        if (isPsionic) desc.append("Psionic\n");
        if (isRobotic) desc.append("Robotic\n");
        if (isAlien) desc.append("Alien\n");
        if (isHybrid) desc.append("Hybrid\n");
        
        return desc.toString();
    }
    
    /**
     * Check if medikit is empty
     */
    public boolean isEmpty() {
        return currentUses <= 0;
    }
    
    /**
     * Check if medikit is full
     */
    public boolean isFull() {
        return currentUses >= maxUses;
    }
    
    /**
     * Get efficiency rating
     */
    public int getEfficiencyRating() {
        int rating = 0;
        rating += healingAmount;
        if (canStabilize) rating += 10;
        if (canRevive) rating += 20;
        if (canRemoveStatusEffects) rating += 15;
        if (isAdvanced) rating += 5;
        if (isExperimental) rating += 10;
        if (isNanotech) rating += 15;
        if (isPsionic) rating += 12;
        if (isRobotic) rating += 8;
        if (isAlien) rating += 25;
        if (isHybrid) rating += 30;
        return rating;
    }
    
    // Additional methods for compatibility
    public int getRange() {
        return 1; // Default range for medikit
    }
    
    public boolean cureStatusEffects(Unit target) {
        if (canRemoveStatusEffects && target.hasStatusEffects()) {
            if (Math.random() * 100 < statusRemovalChance) {
                target.clearStatusEffects();
                return true;
            }
        }
        return false;
    }
} 
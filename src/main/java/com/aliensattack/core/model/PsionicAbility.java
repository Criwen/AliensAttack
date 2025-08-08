package com.aliensattack.core.model;

import com.aliensattack.core.enums.PsionicType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a psionic ability that can be used by units
 */
@Getter
@Setter
public class PsionicAbility {
    private String name;
    private PsionicType psionicType;
    private int psiCost; // Psi energy cost
    private int range; // Range of the ability
    private int cooldown; // Cooldown in turns
    private int currentCooldown;
    private int duration; // Duration of effect
    private int intensity; // Power of the ability
    private boolean isActive;
    
    public PsionicAbility(String name, PsionicType psionicType, int psiCost, int range) {
        this.name = name;
        this.psionicType = psionicType;
        this.psiCost = psiCost;
        this.range = range;
        this.cooldown = 3; // Default 3 turn cooldown
        this.currentCooldown = 0;
        this.duration = 2; // Default 2 turn duration
        this.intensity = 10; // Default intensity
        this.isActive = false;
    }
    
    /**
     * Use the psionic ability
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
     * Get psionic effect based on type
     */
    public int getPsionicEffect() {
        switch (psionicType) {
            case MIND_CONTROL:
                return intensity * 2; // High effect for mind control
            case PSYCHIC_BLAST:
                return intensity * 3; // High damage for blast
            case PSYCHIC_SCREAM:
                return intensity; // Stun effect
            case TELEPATHY:
                return intensity / 2; // Low effect for information
            default:
                return intensity;
        }
    }
    
    /**
     * Get range modifier based on psionic type
     */
    public int getRangeModifier() {
        switch (psionicType) {
            case TELEPATHY:
                return range * 2; // Extended range for telepathy
            case PSYCHIC_BLAST:
                return range; // Standard range for blast
            case MIND_CONTROL:
                return range / 2; // Limited range for mind control
            default:
                return range;
        }
    }
    
    /**
     * Get the psionic type
     */
    public PsionicType getType() {
        return psionicType;
    }
    
    /**
     * Use the psionic ability (alias for useAbility)
     */
    public boolean use() {
        return useAbility();
    }
} 
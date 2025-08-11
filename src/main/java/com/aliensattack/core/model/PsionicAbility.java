package com.aliensattack.core.model;

import com.aliensattack.core.enums.PsionicType;
import com.aliensattack.core.enums.PsionicSchool;
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
    private PsionicSchool preferredSchool; // Preferred school for this ability
    private int resistancePenetration; // Ability to bypass resistance
    private boolean isAreaEffect; // Whether ability affects multiple targets
    private int areaRadius; // Radius of area effect if applicable
    
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
        this.preferredSchool = PsionicSchool.NONE;
        this.resistancePenetration = 0;
        this.isAreaEffect = false;
        this.areaRadius = 0;
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
     * Get psionic effect based on type with school bonuses
     */
    public int getPsionicEffect() {
        return getPsionicEffect(PsionicSchool.NONE);
    }
    
    /**
     * Get psionic effect with school bonus
     */
    public int getPsionicEffect(PsionicSchool userSchool) {
        int baseEffect = getBaseEffect();
        int schoolBonus = getSchoolBonus(userSchool);
        return baseEffect + schoolBonus;
    }
    
    /**
     * Get base effect without school bonuses
     */
    private int getBaseEffect() {
        return switch (psionicType) {
            case MIND_CONTROL -> intensity * 2; // High effect for mind control
            case PSYCHIC_BLAST -> intensity * 3; // High damage for blast
            case PSYCHIC_SCREAM -> intensity; // Stun effect
            case TELEPATHY -> intensity / 2; // Low effect for information
            case TELEPORT -> intensity * 4; // Very high effect for teleportation
            case PSYCHIC_DOMINANCE -> intensity * 3; // High effect for robotic control
            case MIND_MERGE -> intensity * 2; // Moderate effect for consciousness sharing
            case PSYCHIC_BARRIER -> intensity * 3; // High effect for protection
            case MIND_SCORCH -> intensity * 2; // High damage for scorch
            case DOMINATION -> intensity * 2; // High effect for domination
            case PSYCHIC_SHIELD -> intensity * 2; // Moderate effect for shield
            case MIND_SHIELD -> intensity * 2; // Moderate effect for mind shield
            default -> intensity;
        };
    }
    
    /**
     * Get school bonus for this ability
     */
    private int getSchoolBonus(PsionicSchool userSchool) {
        if (userSchool == PsionicSchool.NONE || preferredSchool == PsionicSchool.NONE) {
            return 0;
        }
        
        if (userSchool == preferredSchool) {
            return intensity / 2; // 50% bonus for matching school
        }
        
        // Check for complementary schools
        if (isComplementarySchool(userSchool, preferredSchool)) {
            return intensity / 4; // 25% bonus for complementary schools
        }
        
        return 0;
    }
    
    /**
     * Check if two schools are complementary
     */
    private boolean isComplementarySchool(PsionicSchool school1, PsionicSchool school2) {
        return (school1 == PsionicSchool.TELEPATHY && school2 == PsionicSchool.MIND_CONTROL) ||
               (school1 == PsionicSchool.MIND_CONTROL && school2 == PsionicSchool.TELEPATHY) ||
               (school1 == PsionicSchool.PSYCHIC_WARFARE && school2 == PsionicSchool.PSYCHIC_DEFENSE) ||
               (school1 == PsionicSchool.PSYCHIC_DEFENSE && school2 == PsionicSchool.PSYCHIC_WARFARE) ||
               (school1 == PsionicSchool.TELEPORTATION && school2 == PsionicSchool.MIND_MERGE) ||
               (school1 == PsionicSchool.MIND_MERGE && school2 == PsionicSchool.TELEPORTATION);
    }
    
    /**
     * Get range modifier based on psionic type and school
     */
    public int getRangeModifier() {
        return getRangeModifier(PsionicSchool.NONE);
    }
    
    /**
     * Get range modifier with school bonus
     */
    public int getRangeModifier(PsionicSchool userSchool) {
        int baseRange = getBaseRangeModifier();
        int schoolBonus = getRangeSchoolBonus(userSchool);
        return baseRange + schoolBonus;
    }
    
    /**
     * Get base range modifier without school bonuses
     */
    private int getBaseRangeModifier() {
        return switch (psionicType) {
            case TELEPATHY -> range * 2; // Extended range for telepathy
            case PSYCHIC_BLAST -> range; // Standard range for blast
            case MIND_CONTROL -> range / 2; // Limited range for mind control
            case TELEPORT -> range * 3; // Very long range for teleportation
            case PSYCHIC_DOMINANCE -> (int)(range * 1.5); // Extended range for robotic control
            case MIND_MERGE -> range; // Standard range for consciousness sharing
            case PSYCHIC_BARRIER -> range / 2; // Limited range for barrier
            case PSYCHIC_SHIELD -> range; // Standard range for shield
            case MIND_SHIELD -> range; // Standard range for mind shield
            default -> range;
        };
    }
    
    /**
     * Get range bonus from school
     */
    private int getRangeSchoolBonus(PsionicSchool userSchool) {
        return switch (userSchool) {
            case TELEPATHY -> 2; // +2 range for telepathy school
            case TELEPORTATION -> 3; // +3 range for teleportation school
            case PSYCHIC_WARFARE -> 1; // +1 range for psychic warfare school
            default -> 0;
        };
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
    
    /**
     * Get ability description based on type
     */
    public String getDescription() {
        return switch (psionicType) {
            case MIND_CONTROL -> "Take control of an enemy unit temporarily";
            case TELEPATHY -> "Read enemy thoughts and intentions";
            case PSYCHIC_BLAST -> "Deal area damage psionic attack";
            case MIND_SHIELD -> "Protect against psionic attacks";
            case TELEPORT -> "Move instantly to another location";
            case PSYCHIC_SCREAM -> "Stun and disorient enemies";
            case MIND_MERGE -> "Share consciousness with ally";
            case PSYCHIC_DOMINANCE -> "Take control of robotic units";
            case MIND_SCORCH -> "Direct damage psionic attack";
            case DOMINATION -> "Control enemy unit";
            case PSYCHIC_SHIELD -> "Protect against psionic attacks";
            case PSYCHIC_BARRIER -> "Create protective barrier";
        };
    }
    
    /**
     * Check if ability affects robotic units
     */
    public boolean affectsRobots() {
        return psionicType == PsionicType.PSYCHIC_DOMINANCE;
    }
    
    /**
     * Check if ability is defensive
     */
    public boolean isDefensive() {
        return switch (psionicType) {
            case PSYCHIC_SHIELD, PSYCHIC_BARRIER, MIND_SHIELD -> true;
            default -> false;
        };
    }
    
    /**
     * Check if ability is movement-based
     */
    public boolean isMovementAbility() {
        return psionicType == PsionicType.TELEPORT;
    }
    
    /**
     * Check if ability is offensive
     */
    public boolean isOffensive() {
        return switch (psionicType) {
            case PSYCHIC_BLAST, MIND_SCORCH, PSYCHIC_SCREAM -> true;
            default -> false;
        };
    }
    
    /**
     * Check if ability is control-based
     */
    public boolean isControlAbility() {
        return switch (psionicType) {
            case MIND_CONTROL, PSYCHIC_DOMINANCE, DOMINATION -> true;
            default -> false;
        };
    }
    
    /**
     * Get resistance penetration value
     */
    public int getResistancePenetration() {
        return resistancePenetration;
    }
    
    /**
     * Set resistance penetration
     */
    public void setResistancePenetration(int penetration) {
        this.resistancePenetration = Math.max(0, penetration);
    }
    
    /**
     * Check if ability has area effect
     */
    public boolean hasAreaEffect() {
        return isAreaEffect;
    }
    
    /**
     * Get area effect radius
     */
    public int getAreaRadius() {
        return areaRadius;
    }
    
    /**
     * Set area effect properties
     */
    public void setAreaEffect(boolean isAreaEffect, int radius) {
        this.isAreaEffect = isAreaEffect;
        this.areaRadius = Math.max(0, radius);
    }
    
    /**
     * Get preferred school for this ability
     */
    public PsionicSchool getPreferredSchool() {
        return preferredSchool;
    }
    
    /**
     * Set preferred school for this ability
     */
    public void setPreferredSchool(PsionicSchool school) {
        this.preferredSchool = school;
    }
    
    /**
     * Calculate effective psi cost with school discount
     */
    public int getEffectivePsiCost(PsionicSchool userSchool) {
        if (userSchool == preferredSchool) {
            return Math.max(1, psiCost - 2); // 2 point discount for matching school
        }
        return psiCost;
    }
    
    /**
     * Get ability tier based on intensity and cost
     */
    public int getAbilityTier() {
        int totalPower = intensity + psiCost;
        if (totalPower <= 15) return 1; // Basic abilities
        if (totalPower <= 25) return 2; // Intermediate abilities
        if (totalPower <= 35) return 3; // Advanced abilities
        return 4; // Master abilities
    }
} 
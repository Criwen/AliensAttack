package com.aliensattack.core.model;

import com.aliensattack.core.enums.ReactiveAbilityType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents reactive abilities that trigger automatically under certain conditions
 * Enhanced with comprehensive XCOM 2 reactive ability mechanics
 */
@Getter
@Setter
public class ReactiveAbility {
    private String name;
    private ReactiveAbilityType type;
    private int cooldown;
    private int currentCooldown;
    private boolean canTrigger;
    private int triggerRange; // Range within which ability can trigger
    private int damage; // Damage dealt by reactive ability
    private int accuracy; // Accuracy of reactive ability
    private int criticalChance; // Critical hit chance
    private int criticalDamageMultiplier; // Critical damage multiplier
    private boolean isActive; // Whether ability is currently active
    private int duration; // Duration of active effect
    private int currentDuration; // Current duration remaining
    private boolean requiresLineOfSight; // Whether ability requires line of sight
    private boolean requiresAdjacent; // Whether ability requires adjacent position
    private boolean requiresMovement; // Whether ability requires enemy movement
    private boolean requiresAttack; // Whether ability requires enemy attack
    private boolean requiresDamage; // Whether ability requires taking damage
    private boolean requiresOverwatch; // Whether ability requires overwatch
    private boolean requiresConcealment; // Whether ability requires concealment
    private boolean requiresHeightAdvantage; // Whether ability requires height advantage
    private boolean requiresFlanking; // Whether ability requires flanking
    private boolean requiresSuppression; // Whether ability requires suppression
    private boolean requiresPsionic; // Whether ability requires psionic power
    private boolean requiresHacking; // Whether ability requires hacking
    private boolean requiresExplosive; // Whether ability requires explosive
    private boolean requiresChainReaction; // Whether ability requires chain reaction
    private boolean requiresDeepCover; // Whether ability requires deep cover
    private boolean requiresSquadSight; // Whether ability requires squad sight
    private boolean requiresBluescreen; // Whether ability requires bluescreen weapon
    private boolean requiresVolatileMix; // Whether ability requires volatile mix
    private boolean requiresRapidFire; // Whether ability requires rapid fire
    
    public ReactiveAbility(String name, ReactiveAbilityType type, int cooldown) {
        this.name = name;
        this.type = type;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
        this.canTrigger = true;
        this.triggerRange = 1;
        this.damage = 0;
        this.accuracy = 0;
        this.criticalChance = 0;
        this.criticalDamageMultiplier = 1;
        this.isActive = false;
        this.duration = 0;
        this.currentDuration = 0;
        this.requiresLineOfSight = false;
        this.requiresAdjacent = false;
        this.requiresMovement = false;
        this.requiresAttack = false;
        this.requiresDamage = false;
        this.requiresOverwatch = false;
        this.requiresConcealment = false;
        this.requiresHeightAdvantage = false;
        this.requiresFlanking = false;
        this.requiresSuppression = false;
        this.requiresPsionic = false;
        this.requiresHacking = false;
        this.requiresExplosive = false;
        this.requiresChainReaction = false;
        this.requiresDeepCover = false;
        this.requiresSquadSight = false;
        this.requiresBluescreen = false;
        this.requiresVolatileMix = false;
        this.requiresRapidFire = false;
        
        // Set properties based on ability type
        setPropertiesByAbilityType(type);
    }
    
    private void setPropertiesByAbilityType(ReactiveAbilityType type) {
        switch (type) {
            case BLADESTORM:
                triggerRange = 1;
                damage = 8;
                accuracy = 85;
                criticalChance = 15;
                criticalDamageMultiplier = 2;
                requiresAdjacent = true;
                requiresMovement = true;
                break;
            case LIGHTNING_REFLEXES:
                triggerRange = 2;
                damage = 0;
                accuracy = 0;
                criticalChance = 0;
                criticalDamageMultiplier = 1;
                requiresAttack = true;
                duration = 1;
                break;
            case RETURN_FIRE:
                triggerRange = 3;
                damage = 6;
                accuracy = 70;
                criticalChance = 10;
                criticalDamageMultiplier = 1;
                requiresAttack = true;
                break;
            case COUNTER_ATTACK:
                triggerRange = 1;
                damage = 10;
                accuracy = 80;
                criticalChance = 20;
                criticalDamageMultiplier = 2;
                requiresAdjacent = true;
                requiresAttack = true;
                break;
            case REACTIVE_ARMOR:
                triggerRange = 0;
                damage = 0;
                accuracy = 0;
                criticalChance = 0;
                criticalDamageMultiplier = 1;
                requiresDamage = true;
                duration = 2;
                break;
            case OVERWATCH_AMBUSH:
                triggerRange = 4;
                damage = 12;
                accuracy = 90;
                criticalChance = 25;
                criticalDamageMultiplier = 2;
                requiresOverwatch = true;
                requiresConcealment = true;
                break;
            case HEIGHT_ADVANTAGE:
                triggerRange = 5;
                damage = 5;
                accuracy = 75;
                criticalChance = 10;
                criticalDamageMultiplier = 1;
                requiresHeightAdvantage = true;
                break;
            case FLANKING_BONUS:
                triggerRange = 2;
                damage = 8;
                accuracy = 85;
                criticalChance = 20;
                criticalDamageMultiplier = 2;
                requiresFlanking = true;
                break;
            case SUPPRESSION_RESPONSE:
                triggerRange = 3;
                damage = 4;
                accuracy = 60;
                criticalChance = 5;
                criticalDamageMultiplier = 1;
                requiresSuppression = true;
                break;
            case PSIONIC_SHIELD:
                triggerRange = 0;
                damage = 0;
                accuracy = 0;
                criticalChance = 0;
                criticalDamageMultiplier = 1;
                requiresPsionic = true;
                requiresDamage = true;
                duration = 3;
                break;
            case HACKING_COUNTER:
                triggerRange = 2;
                damage = 3;
                accuracy = 65;
                criticalChance = 10;
                criticalDamageMultiplier = 1;
                requiresHacking = true;
                break;
            case EXPLOSIVE_REACTION:
                triggerRange = 2;
                damage = 15;
                accuracy = 80;
                criticalChance = 15;
                criticalDamageMultiplier = 2;
                requiresExplosive = true;
                break;
            case CHAIN_REACTION:
                triggerRange = 3;
                damage = 20;
                accuracy = 70;
                criticalChance = 20;
                criticalDamageMultiplier = 3;
                requiresChainReaction = true;
                break;
            case DEEP_COVER_RESPONSE:
                triggerRange = 2;
                damage = 6;
                accuracy = 75;
                criticalChance = 10;
                criticalDamageMultiplier = 1;
                requiresDeepCover = true;
                break;
            case SQUAD_SIGHT_SNIPE:
                triggerRange = 8;
                damage = 15;
                accuracy = 85;
                criticalChance = 30;
                criticalDamageMultiplier = 2;
                requiresSquadSight = true;
                break;
            case BLUESCREEN_COUNTER:
                triggerRange = 3;
                damage = 12;
                accuracy = 80;
                criticalChance = 15;
                criticalDamageMultiplier = 2;
                requiresBluescreen = true;
                break;
            case VOLATILE_MIX_REACTION:
                triggerRange = 4;
                damage = 25;
                accuracy = 75;
                criticalChance = 25;
                criticalDamageMultiplier = 3;
                requiresVolatileMix = true;
                break;
            case RAPID_FIRE_RESPONSE:
                triggerRange = 2;
                damage = 8;
                accuracy = 70;
                criticalChance = 15;
                criticalDamageMultiplier = 1;
                requiresRapidFire = true;
                break;
            default:
                triggerRange = 1;
                damage = 0;
                accuracy = 0;
                criticalChance = 0;
                criticalDamageMultiplier = 1;
                break;
        }
    }
    
    /**
     * Check if ability can trigger
     */
    public boolean canTrigger() {
        return canTrigger && currentCooldown <= 0 && !isActive;
    }
    
    /**
     * Trigger the reactive ability
     */
    public boolean trigger() {
        if (!canTrigger()) {
            return false;
        }
        
        isActive = true;
        currentDuration = duration;
        currentCooldown = cooldown;
        canTrigger = false;
        
        return true;
    }
    
    /**
     * Process cooldown reduction
     */
    public void processCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
        
        if (currentDuration > 0) {
            currentDuration--;
            if (currentDuration <= 0) {
                isActive = false;
            }
        }
        
        // Reset trigger ability when cooldown is complete
        if (currentCooldown <= 0 && !isActive) {
            canTrigger = true;
        }
    }
    
    /**
     * Get bladestorm damage
     */
    public int getBladestormDamage() {
        if (type == ReactiveAbilityType.BLADESTORM) {
            return damage;
        }
        return 0;
    }
    
    /**
     * Get bladestorm accuracy
     */
    public int getBladestormAccuracy() {
        if (type == ReactiveAbilityType.BLADESTORM) {
            return accuracy;
        }
        return 0;
    }
    
    /**
     * Check if ability requires specific conditions
     */
    public boolean requiresCondition(String condition) {
        return switch (condition.toLowerCase()) {
            case "line_of_sight" -> requiresLineOfSight;
            case "adjacent" -> requiresAdjacent;
            case "movement" -> requiresMovement;
            case "attack" -> requiresAttack;
            case "damage" -> requiresDamage;
            case "overwatch" -> requiresOverwatch;
            case "concealment" -> requiresConcealment;
            case "height_advantage" -> requiresHeightAdvantage;
            case "flanking" -> requiresFlanking;
            case "suppression" -> requiresSuppression;
            case "psionic" -> requiresPsionic;
            case "hacking" -> requiresHacking;
            case "explosive" -> requiresExplosive;
            case "chain_reaction" -> requiresChainReaction;
            case "deep_cover" -> requiresDeepCover;
            case "squad_sight" -> requiresSquadSight;
            case "bluescreen" -> requiresBluescreen;
            case "volatile_mix" -> requiresVolatileMix;
            case "rapid_fire" -> requiresRapidFire;
            default -> false;
        };
    }
    
    /**
     * Get total damage including critical hits
     */
    public int getTotalDamage() {
        if (criticalChance > 0) {
            // Simulate critical hit chance
            int totalDamage = damage;
            if (Math.random() * 100 < criticalChance) {
                totalDamage *= criticalDamageMultiplier;
            }
            return totalDamage;
        }
        return damage;
    }
    
    /**
     * Get ability description
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append(" (").append(type).append(")\n");
        desc.append("Cooldown: ").append(cooldown).append(" turns\n");
        desc.append("Range: ").append(triggerRange).append(" tiles\n");
        
        if (damage > 0) desc.append("Damage: ").append(damage).append("\n");
        if (accuracy > 0) desc.append("Accuracy: ").append(accuracy).append("%\n");
        if (criticalChance > 0) desc.append("Critical: ").append(criticalChance).append("%\n");
        if (criticalDamageMultiplier > 1) desc.append("Critical Multiplier: x").append(criticalDamageMultiplier).append("\n");
        if (duration > 0) desc.append("Duration: ").append(duration).append(" turns\n");
        
        if (requiresLineOfSight) desc.append("Requires: Line of Sight\n");
        if (requiresAdjacent) desc.append("Requires: Adjacent Position\n");
        if (requiresMovement) desc.append("Requires: Enemy Movement\n");
        if (requiresAttack) desc.append("Requires: Enemy Attack\n");
        if (requiresDamage) desc.append("Requires: Taking Damage\n");
        if (requiresOverwatch) desc.append("Requires: Overwatch\n");
        if (requiresConcealment) desc.append("Requires: Concealment\n");
        if (requiresHeightAdvantage) desc.append("Requires: Height Advantage\n");
        if (requiresFlanking) desc.append("Requires: Flanking\n");
        if (requiresSuppression) desc.append("Requires: Suppression\n");
        if (requiresPsionic) desc.append("Requires: Psionic Power\n");
        if (requiresHacking) desc.append("Requires: Hacking\n");
        if (requiresExplosive) desc.append("Requires: Explosive\n");
        if (requiresChainReaction) desc.append("Requires: Chain Reaction\n");
        if (requiresDeepCover) desc.append("Requires: Deep Cover\n");
        if (requiresSquadSight) desc.append("Requires: Squad Sight\n");
        if (requiresBluescreen) desc.append("Requires: Bluescreen Weapon\n");
        if (requiresVolatileMix) desc.append("Requires: Volatile Mix\n");
        if (requiresRapidFire) desc.append("Requires: Rapid Fire\n");
        
        return desc.toString();
    }
    
    /**
     * Check if ability is on cooldown
     */
    public boolean isOnCooldown() {
        return currentCooldown > 0;
    }
    
    /**
     * Get remaining cooldown
     */
    public int getRemainingCooldown() {
        return currentCooldown;
    }
    
    /**
     * Get remaining duration
     */
    public int getRemainingDuration() {
        return currentDuration;
    }
    
    /**
     * Reset ability
     */
    public void reset() {
        currentCooldown = 0;
        currentDuration = 0;
        isActive = false;
        canTrigger = true;
    }
    
    /**
     * Force trigger ability (for testing)
     */
    public void forceTrigger() {
        trigger();
    }
    
    /**
     * Check if ability is active
     */
    public boolean isActive() {
        return isActive && currentDuration > 0;
    }
    
    /**
     * Get ability type
     */
    public ReactiveAbilityType getType() {
        return type;
    }
    
    /**
     * Get ability name
     */
    public String getName() {
        return name;
    }
} 
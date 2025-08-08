package com.aliensattack.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an objective target for sabotage and destruction missions
 * Implements XCOM 2 objective target mechanics
 */
@Getter
@Setter
public class ObjectiveTarget {
    private String name;
    private String description;
    private Position position;
    private int maxHealth;
    private int currentHealth;
    private boolean isDestroyed;
    private boolean isDamaged;
    private boolean isRepaired;
    private boolean isProtected;
    private boolean isExposed;
    private boolean isHidden;
    private boolean isRevealed;
    private boolean isAccessible;
    private boolean isBlocked;
    private boolean isGuarded;
    private boolean isAlarmed;
    private boolean isCompromised;
    private boolean isSecure;
    private int damageLevel;
    private int repairLevel;
    private int protectionLevel;
    private int exposureLevel;
    private int guardLevel;
    private int alarmLevel;
    private int compromiseLevel;
    private int securityLevel;
    private int destructionValue;
    private int damageValue;
    private int repairValue;
    private int protectionValue;
    private int exposureValue;
    private int guardValue;
    private int alarmValue;
    private int compromiseValue;
    private int securityValue;
    private boolean isPrimary;
    private boolean isSecondary;
    private boolean isOptional;
    private boolean isRequired;
    private boolean isBonus;
    private boolean isPenalty;
    private int bonusValue;
    private int penaltyValue;
    private boolean isTimeSensitive;
    private int timeLimit;
    private int currentTime;
    private boolean isTimeExpired;
    private boolean isTimeCritical;
    private boolean isTimeBonus;
    private boolean isTimePenalty;
    private int timeBonusValue;
    private int timePenaltyValue;
    
    public ObjectiveTarget(String name, String description, Position position, int maxHealth) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.isDestroyed = false;
        this.isDamaged = false;
        this.isRepaired = false;
        this.isProtected = false;
        this.isExposed = false;
        this.isHidden = false;
        this.isRevealed = false;
        this.isAccessible = true;
        this.isBlocked = false;
        this.isGuarded = false;
        this.isAlarmed = false;
        this.isCompromised = false;
        this.isSecure = false;
        this.damageLevel = 0;
        this.repairLevel = 0;
        this.protectionLevel = 0;
        this.exposureLevel = 0;
        this.guardLevel = 0;
        this.alarmLevel = 0;
        this.compromiseLevel = 0;
        this.securityLevel = 0;
        this.destructionValue = 100;
        this.damageValue = 50;
        this.repairValue = 25;
        this.protectionValue = 30;
        this.exposureValue = 20;
        this.guardValue = 40;
        this.alarmValue = 60;
        this.compromiseValue = 80;
        this.securityValue = 90;
        this.isPrimary = false;
        this.isSecondary = false;
        this.isOptional = false;
        this.isRequired = true;
        this.isBonus = false;
        this.isPenalty = false;
        this.bonusValue = 0;
        this.penaltyValue = 0;
        this.isTimeSensitive = false;
        this.timeLimit = 0;
        this.currentTime = 0;
        this.isTimeExpired = false;
        this.isTimeCritical = false;
        this.isTimeBonus = false;
        this.isTimePenalty = false;
        this.timeBonusValue = 0;
        this.timePenaltyValue = 0;
    }
    
    /**
     * Take damage to the objective
     */
    public boolean takeDamage(int damage) {
        if (isDestroyed || !isAccessible) {
            return false;
        }
        
        currentHealth -= damage;
        damageLevel++;
        isDamaged = true;
        isRepaired = false;
        
        if (currentHealth <= 0) {
            destroy();
            return true;
        }
        
        return false;
    }
    
    /**
     * Destroy the objective
     */
    public boolean destroy() {
        if (isDestroyed) {
            return false;
        }
        
        isDestroyed = true;
        currentHealth = 0;
        damageLevel = maxHealth;
        return true;
    }
    
    /**
     * Repair the objective
     */
    public boolean repair() {
        if (!isDamaged || isDestroyed) {
            return false;
        }
        
        damageLevel = Math.max(0, damageLevel - 1);
        repairLevel++;
        
        if (damageLevel == 0) {
            isDamaged = false;
            isRepaired = true;
        }
        
        return true;
    }
    
    /**
     * Protect the objective
     */
    public boolean protect() {
        if (isProtected || isDestroyed) {
            return false;
        }
        
        isProtected = true;
        protectionLevel++;
        return true;
    }
    
    /**
     * Expose the objective
     */
    public boolean expose() {
        if (isExposed || isDestroyed) {
            return false;
        }
        
        isExposed = true;
        exposureLevel++;
        return true;
    }
    
    /**
     * Hide the objective
     */
    public boolean hide() {
        if (isHidden || isDestroyed) {
            return false;
        }
        
        isHidden = true;
        isRevealed = false;
        return true;
    }
    
    /**
     * Reveal the objective
     */
    public boolean reveal() {
        if (!isHidden) {
            return false;
        }
        
        isHidden = false;
        isRevealed = true;
        return true;
    }
    
    /**
     * Block the objective
     */
    public boolean block() {
        if (isBlocked || isDestroyed) {
            return false;
        }
        
        isBlocked = true;
        isAccessible = false;
        return true;
    }
    
    /**
     * Unblock the objective
     */
    public boolean unblock() {
        if (!isBlocked) {
            return false;
        }
        
        isBlocked = false;
        isAccessible = true;
        return true;
    }
    
    /**
     * Guard the objective
     */
    public boolean guard() {
        if (isGuarded || isDestroyed) {
            return false;
        }
        
        isGuarded = true;
        guardLevel++;
        return true;
    }
    
    /**
     * Remove guard from objective
     */
    public boolean removeGuard() {
        if (!isGuarded) {
            return false;
        }
        
        isGuarded = false;
        guardLevel = Math.max(0, guardLevel - 1);
        return true;
    }
    
    /**
     * Alarm the objective
     */
    public boolean alarm() {
        if (isAlarmed || isDestroyed) {
            return false;
        }
        
        isAlarmed = true;
        alarmLevel++;
        return true;
    }
    
    /**
     * Disarm the objective
     */
    public boolean disarm() {
        if (!isAlarmed) {
            return false;
        }
        
        isAlarmed = false;
        alarmLevel = Math.max(0, alarmLevel - 1);
        return true;
    }
    
    /**
     * Compromise the objective
     */
    public boolean compromise() {
        if (isCompromised || isDestroyed) {
            return false;
        }
        
        isCompromised = true;
        compromiseLevel++;
        return true;
    }
    
    /**
     * Secure the objective
     */
    public boolean secure() {
        if (isSecure || isDestroyed) {
            return false;
        }
        
        isSecure = true;
        securityLevel++;
        return true;
    }
    
    /**
     * Process time-sensitive objectives
     */
    public void processTime() {
        if (!isTimeSensitive) {
            return;
        }
        
        currentTime++;
        
        if (currentTime >= timeLimit) {
            isTimeExpired = true;
        }
        
        if (currentTime >= timeLimit * 0.8) {
            isTimeCritical = true;
        }
    }
    
    /**
     * Check if objective is functional
     */
    public boolean isFunctional() {
        return !isDestroyed && isAccessible && !isBlocked;
    }
    
    /**
     * Check if objective is vulnerable
     */
    public boolean isVulnerable() {
        return isFunctional() && (isExposed || isCompromised || !isProtected);
    }
    
    /**
     * Check if objective is secure
     */
    public boolean isSecure() {
        return isFunctional() && isProtected && !isCompromised && !isExposed;
    }
    
    /**
     * Get objective status
     */
    public String getObjectiveStatus() {
        if (isDestroyed) return "DESTROYED";
        if (!isAccessible) return "INACCESSIBLE";
        if (isBlocked) return "BLOCKED";
        if (isTimeExpired) return "TIME_EXPIRED";
        if (isTimeCritical) return "TIME_CRITICAL";
        if (isCompromised) return "COMPROMISED";
        if (isAlarmed) return "ALARMED";
        if (isGuarded) return "GUARDED";
        if (isProtected) return "PROTECTED";
        if (isExposed) return "EXPOSED";
        if (isDamaged) return "DAMAGED";
        if (isHidden) return "HIDDEN";
        if (isRevealed) return "REVEALED";
        return "NORMAL";
    }
    
    /**
     * Get objective value
     */
    public int getObjectiveValue() {
        int value = 0;
        
        if (isDestroyed) {
            value += destructionValue;
        }
        
        if (isDamaged) {
            value += damageValue;
        }
        
        if (isRepaired) {
            value += repairValue;
        }
        
        if (isProtected) {
            value += protectionValue;
        }
        
        if (isExposed) {
            value += exposureValue;
        }
        
        if (isGuarded) {
            value += guardValue;
        }
        
        if (isAlarmed) {
            value += alarmValue;
        }
        
        if (isCompromised) {
            value += compromiseValue;
        }
        
        if (isSecure) {
            value += securityValue;
        }
        
        if (isBonus) {
            value += bonusValue;
        }
        
        if (isPenalty) {
            value += penaltyValue;
        }
        
        if (isTimeBonus) {
            value += timeBonusValue;
        }
        
        if (isTimePenalty) {
            value += timePenaltyValue;
        }
        
        return value;
    }
    
    /**
     * Get objective description
     */
    public String getObjectiveDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append("\n");
        desc.append("Description: ").append(description).append("\n");
        desc.append("Status: ").append(getObjectiveStatus()).append("\n");
        desc.append("Position: ").append(position).append("\n");
        desc.append("Health: ").append(currentHealth).append("/").append(maxHealth).append("\n");
        desc.append("Value: ").append(getObjectiveValue()).append("\n");
        
        if (isTimeSensitive) {
            desc.append("Time Limit: ").append(timeLimit).append(" turns\n");
            desc.append("Current Time: ").append(currentTime).append(" turns\n");
            desc.append("Time Remaining: ").append(Math.max(0, timeLimit - currentTime)).append(" turns\n");
        }
        
        if (isDamaged) {
            desc.append("Damage Level: ").append(damageLevel).append("\n");
        }
        
        if (isRepaired) {
            desc.append("Repair Level: ").append(repairLevel).append("\n");
        }
        
        if (isProtected) {
            desc.append("Protection Level: ").append(protectionLevel).append("\n");
        }
        
        if (isExposed) {
            desc.append("Exposure Level: ").append(exposureLevel).append("\n");
        }
        
        if (isGuarded) {
            desc.append("Guard Level: ").append(guardLevel).append("\n");
        }
        
        if (isAlarmed) {
            desc.append("Alarm Level: ").append(alarmLevel).append("\n");
        }
        
        if (isCompromised) {
            desc.append("Compromise Level: ").append(compromiseLevel).append("\n");
        }
        
        if (isSecure) {
            desc.append("Security Level: ").append(securityLevel).append("\n");
        }
        
        if (isPrimary) desc.append("Primary Objective\n");
        if (isSecondary) desc.append("Secondary Objective\n");
        if (isOptional) desc.append("Optional Objective\n");
        if (isRequired) desc.append("Required Objective\n");
        if (isBonus) desc.append("Bonus Objective\n");
        if (isPenalty) desc.append("Penalty Objective\n");
        if (isTimeSensitive) desc.append("Time Sensitive\n");
        if (isTimeCritical) desc.append("Time Critical\n");
        if (isTimeExpired) desc.append("Time Expired\n");
        
        return desc.toString();
    }
} 
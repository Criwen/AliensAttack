package com.aliensattack.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a defense position for defense missions
 * Implements XCOM 2 defense position mechanics
 */
@Getter
@Setter
public class DefensePosition {
    private String name;
    private String description;
    private Position position;
    private int radius;
    private boolean isActive;
    private boolean isSecure;
    private boolean isCompromised;
    private boolean isDestroyed;
    private boolean isAccessible;
    private boolean isBlocked;
    private boolean isGuarded;
    private boolean isAlarmed;
    private boolean isHidden;
    private boolean isRevealed;
    private boolean isOccupied;
    private boolean isContested;
    private boolean isCaptured;
    private boolean isReinforced;
    private boolean isVulnerable;
    private boolean isProtected;
    private boolean isExposed;
    private int securityLevel;
    private int guardLevel;
    private int alarmLevel;
    private int reinforcementLevel;
    private int vulnerabilityLevel;
    private int protectionLevel;
    private int exposureLevel;
    private int captureLevel;
    private int contestLevel;
    private int occupationLevel;
    private int defenseValue;
    private int securityValue;
    private int guardValue;
    private int alarmValue;
    private int reinforcementValue;
    private int vulnerabilityValue;
    private int protectionValue;
    private int exposureValue;
    private int captureValue;
    private int contestValue;
    private int occupationValue;
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
    private List<Unit> defendingUnits;
    private List<Unit> attackingUnits;
    private List<Unit> capturedBy;
    private List<Unit> contestingUnits;
    private List<Unit> occupyingUnits;
    
    public DefensePosition(String name, String description, Position position, int radius) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.radius = radius;
        this.isActive = true;
        this.isSecure = false;
        this.isCompromised = false;
        this.isDestroyed = false;
        this.isAccessible = true;
        this.isBlocked = false;
        this.isGuarded = false;
        this.isAlarmed = false;
        this.isHidden = false;
        this.isRevealed = false;
        this.isOccupied = false;
        this.isContested = false;
        this.isCaptured = false;
        this.isReinforced = false;
        this.isVulnerable = false;
        this.isProtected = false;
        this.isExposed = false;
        this.securityLevel = 0;
        this.guardLevel = 0;
        this.alarmLevel = 0;
        this.reinforcementLevel = 0;
        this.vulnerabilityLevel = 0;
        this.protectionLevel = 0;
        this.exposureLevel = 0;
        this.captureLevel = 0;
        this.contestLevel = 0;
        this.occupationLevel = 0;
        this.defenseValue = 100;
        this.securityValue = 50;
        this.guardValue = 75;
        this.alarmValue = 25;
        this.reinforcementValue = 80;
        this.vulnerabilityValue = -30;
        this.protectionValue = 60;
        this.exposureValue = -20;
        this.captureValue = -100;
        this.contestValue = -50;
        this.occupationValue = 40;
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
        this.defendingUnits = new ArrayList<>();
        this.attackingUnits = new ArrayList<>();
        this.capturedBy = new ArrayList<>();
        this.contestingUnits = new ArrayList<>();
        this.occupyingUnits = new ArrayList<>();
    }
    
    /**
     * Check if unit is in defense range
     */
    public boolean isInDefenseRange(Unit unit) {
        if (unit == null || unit.getPosition() == null) {
            return false;
        }
        
        int distance = position.getDistanceTo(unit.getPosition());
        return distance <= radius;
    }
    
    /**
     * Add defending unit
     */
    public boolean addDefendingUnit(Unit unit) {
        if (!isActive || isDestroyed || !isAccessible) {
            return false;
        }
        
        if (!isInDefenseRange(unit)) {
            return false;
        }
        
        if (!defendingUnits.contains(unit)) {
            defendingUnits.add(unit);
            isOccupied = true;
            occupationLevel++;
        }
        
        return true;
    }
    
    /**
     * Remove defending unit
     */
    public boolean removeDefendingUnit(Unit unit) {
        if (!defendingUnits.contains(unit)) {
            return false;
        }
        
        defendingUnits.remove(unit);
        
        if (defendingUnits.isEmpty()) {
            isOccupied = false;
            occupationLevel = 0;
        }
        
        return true;
    }
    
    /**
     * Add attacking unit
     */
    public boolean addAttackingUnit(Unit unit) {
        if (!isActive || isDestroyed) {
            return false;
        }
        
        if (!isInDefenseRange(unit)) {
            return false;
        }
        
        if (!attackingUnits.contains(unit)) {
            attackingUnits.add(unit);
            isContested = true;
            contestLevel++;
        }
        
        return true;
    }
    
    /**
     * Remove attacking unit
     */
    public boolean removeAttackingUnit(Unit unit) {
        if (!attackingUnits.contains(unit)) {
            return false;
        }
        
        attackingUnits.remove(unit);
        
        if (attackingUnits.isEmpty()) {
            isContested = false;
            contestLevel = 0;
        }
        
        return true;
    }
    
    /**
     * Capture the position
     */
    public boolean capture(Unit capturer) {
        if (isCaptured || isDestroyed) {
            return false;
        }
        
        if (!isInDefenseRange(capturer)) {
            return false;
        }
        
        isCaptured = true;
        captureLevel++;
        capturedBy.add(capturer);
        
        // Clear defending units
        defendingUnits.clear();
        isOccupied = false;
        occupationLevel = 0;
        
        return true;
    }
    
    /**
     * Liberate the position
     */
    public boolean liberate(Unit liberator) {
        if (!isCaptured) {
            return false;
        }
        
        isCaptured = false;
        captureLevel = 0;
        capturedBy.clear();
        
        // Add liberator as defender
        addDefendingUnit(liberator);
        
        return true;
    }
    
    /**
     * Secure the position
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
     * Compromise the position
     */
    public boolean compromise() {
        if (isCompromised || isDestroyed) {
            return false;
        }
        
        isCompromised = true;
        securityLevel--;
        return true;
    }
    
    /**
     * Guard the position
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
     * Remove guard from position
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
     * Alarm the position
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
     * Disarm the position
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
     * Reinforce the position
     */
    public boolean reinforce() {
        if (isReinforced || isDestroyed) {
            return false;
        }
        
        isReinforced = true;
        reinforcementLevel++;
        return true;
    }
    
    /**
     * Remove reinforcement from position
     */
    public boolean removeReinforcement() {
        if (!isReinforced) {
            return false;
        }
        
        isReinforced = false;
        reinforcementLevel = Math.max(0, reinforcementLevel - 1);
        return true;
    }
    
    /**
     * Make position vulnerable
     */
    public boolean makeVulnerable() {
        if (isVulnerable || isDestroyed) {
            return false;
        }
        
        isVulnerable = true;
        vulnerabilityLevel++;
        return true;
    }
    
    /**
     * Make position protected
     */
    public boolean makeProtected() {
        if (isProtected || isDestroyed) {
            return false;
        }
        
        isProtected = true;
        protectionLevel++;
        return true;
    }
    
    /**
     * Expose the position
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
     * Hide the position
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
     * Reveal the position
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
     * Block the position
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
     * Unblock the position
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
     * Destroy the position
     */
    public boolean destroy() {
        if (isDestroyed) {
            return false;
        }
        
        isDestroyed = true;
        isActive = false;
        isAccessible = false;
        
        // Clear all units
        defendingUnits.clear();
        attackingUnits.clear();
        capturedBy.clear();
        contestingUnits.clear();
        occupyingUnits.clear();
        
        return true;
    }
    
    /**
     * Process time-sensitive positions
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
     * Check if position is functional
     */
    public boolean isFunctional() {
        return isActive && !isDestroyed && isAccessible && !isBlocked;
    }
    
    /**
     * Check if position is defensible
     */
    public boolean isDefensible() {
        return isFunctional() && !isCaptured && !isContested;
    }
    
    /**
     * Check if position is secure
     */
    public boolean isSecure() {
        return isFunctional() && isSecure && !isCompromised && !isCaptured;
    }
    
    /**
     * Get position status
     */
    public String getPositionStatus() {
        if (isDestroyed) return "DESTROYED";
        if (!isAccessible) return "INACCESSIBLE";
        if (isBlocked) return "BLOCKED";
        if (isTimeExpired) return "TIME_EXPIRED";
        if (isTimeCritical) return "TIME_CRITICAL";
        if (isCaptured) return "CAPTURED";
        if (isContested) return "CONTESTED";
        if (isCompromised) return "COMPROMISED";
        if (isAlarmed) return "ALARMED";
        if (isGuarded) return "GUARDED";
        if (isReinforced) return "REINFORCED";
        if (isSecure) return "SECURE";
        if (isVulnerable) return "VULNERABLE";
        if (isProtected) return "PROTECTED";
        if (isExposed) return "EXPOSED";
        if (isOccupied) return "OCCUPIED";
        if (isHidden) return "HIDDEN";
        if (isRevealed) return "REVEALED";
        return "NORMAL";
    }
    
    /**
     * Get position value
     */
    public int getPositionValue() {
        int value = defenseValue;
        
        if (isSecure) {
            value += securityValue;
        }
        
        if (isGuarded) {
            value += guardValue;
        }
        
        if (isAlarmed) {
            value += alarmValue;
        }
        
        if (isReinforced) {
            value += reinforcementValue;
        }
        
        if (isVulnerable) {
            value += vulnerabilityValue;
        }
        
        if (isProtected) {
            value += protectionValue;
        }
        
        if (isExposed) {
            value += exposureValue;
        }
        
        if (isCaptured) {
            value += captureValue;
        }
        
        if (isContested) {
            value += contestValue;
        }
        
        if (isOccupied) {
            value += occupationValue;
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
     * Get position description
     */
    public String getPositionDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append("\n");
        desc.append("Description: ").append(description).append("\n");
        desc.append("Status: ").append(getPositionStatus()).append("\n");
        desc.append("Position: ").append(position).append("\n");
        desc.append("Radius: ").append(radius).append(" tiles\n");
        desc.append("Value: ").append(getPositionValue()).append("\n");
        
        if (isTimeSensitive) {
            desc.append("Time Limit: ").append(timeLimit).append(" turns\n");
            desc.append("Current Time: ").append(currentTime).append(" turns\n");
            desc.append("Time Remaining: ").append(Math.max(0, timeLimit - currentTime)).append(" turns\n");
        }
        
        if (isSecure) {
            desc.append("Security Level: ").append(securityLevel).append("\n");
        }
        
        if (isGuarded) {
            desc.append("Guard Level: ").append(guardLevel).append("\n");
        }
        
        if (isAlarmed) {
            desc.append("Alarm Level: ").append(alarmLevel).append("\n");
        }
        
        if (isReinforced) {
            desc.append("Reinforcement Level: ").append(reinforcementLevel).append("\n");
        }
        
        if (isVulnerable) {
            desc.append("Vulnerability Level: ").append(vulnerabilityLevel).append("\n");
        }
        
        if (isProtected) {
            desc.append("Protection Level: ").append(protectionLevel).append("\n");
        }
        
        if (isExposed) {
            desc.append("Exposure Level: ").append(exposureLevel).append("\n");
        }
        
        if (isCaptured) {
            desc.append("Capture Level: ").append(captureLevel).append("\n");
            desc.append("Captured by: ").append(capturedBy.size()).append(" units\n");
        }
        
        if (isContested) {
            desc.append("Contest Level: ").append(contestLevel).append("\n");
            desc.append("Attacking units: ").append(attackingUnits.size()).append("\n");
        }
        
        if (isOccupied) {
            desc.append("Occupation Level: ").append(occupationLevel).append("\n");
            desc.append("Defending units: ").append(defendingUnits.size()).append("\n");
        }
        
        if (isPrimary) desc.append("Primary Position\n");
        if (isSecondary) desc.append("Secondary Position\n");
        if (isOptional) desc.append("Optional Position\n");
        if (isRequired) desc.append("Required Position\n");
        if (isBonus) desc.append("Bonus Position\n");
        if (isPenalty) desc.append("Penalty Position\n");
        if (isTimeSensitive) desc.append("Time Sensitive\n");
        if (isTimeCritical) desc.append("Time Critical\n");
        if (isTimeExpired) desc.append("Time Expired\n");
        
        return desc.toString();
    }
} 
package com.aliensattack.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a hacking terminal for technical missions
 * Implements XCOM 2 hacking terminal mechanics
 */
@Getter
@Setter
public class HackingTerminal {
    private String name;
    private String description;
    private Position position;
    private int maxHealth;
    private int currentHealth;
    private boolean isDestroyed;
    private boolean isDamaged;
    private boolean isRepaired;
    private boolean isHacked;
    private boolean isCompromised;
    private boolean isSecure;
    private boolean isAccessible;
    private boolean isBlocked;
    private boolean isGuarded;
    private boolean isAlarmed;
    private boolean isHidden;
    private boolean isRevealed;
    private int hackingDifficulty;
    private int currentHackingProgress;
    private int hackingSuccessChance;
    private int hackingFailureChance;
    private int hackingReward;
    private int hackingPenalty;
    private int damageLevel;
    private int repairLevel;
    private int securityLevel;
    private int compromiseLevel;
    private int guardLevel;
    private int alarmLevel;
    private int revealLevel;
    private int hideLevel;
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
    private List<String> hackingOptions;
    private List<String> completedHacks;
    private List<String> failedHacks;
    private List<String> availableRewards;
    private List<String> appliedPenalties;
    
    public HackingTerminal(String name, String description, Position position, int maxHealth, int hackingDifficulty) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.hackingDifficulty = hackingDifficulty;
        this.isDestroyed = false;
        this.isDamaged = false;
        this.isRepaired = false;
        this.isHacked = false;
        this.isCompromised = false;
        this.isSecure = false;
        this.isAccessible = true;
        this.isBlocked = false;
        this.isGuarded = false;
        this.isAlarmed = false;
        this.isHidden = false;
        this.isRevealed = false;
        this.currentHackingProgress = 0;
        this.hackingSuccessChance = 100 - hackingDifficulty;
        this.hackingFailureChance = hackingDifficulty;
        this.hackingReward = 50;
        this.hackingPenalty = -25;
        this.damageLevel = 0;
        this.repairLevel = 0;
        this.securityLevel = 0;
        this.compromiseLevel = 0;
        this.guardLevel = 0;
        this.alarmLevel = 0;
        this.revealLevel = 0;
        this.hideLevel = 0;
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
        this.hackingOptions = new ArrayList<>();
        this.completedHacks = new ArrayList<>();
        this.failedHacks = new ArrayList<>();
        this.availableRewards = new ArrayList<>();
        this.appliedPenalties = new ArrayList<>();
        
        // Initialize default hacking options
        initializeHackingOptions();
    }
    
    /**
     * Initialize default hacking options
     */
    private void initializeHackingOptions() {
        hackingOptions.add("Disable Security");
        hackingOptions.add("Override Controls");
        hackingOptions.add("Extract Data");
        hackingOptions.add("Activate Systems");
        hackingOptions.add("Deactivate Systems");
        hackingOptions.add("Bypass Firewall");
        hackingOptions.add("Upload Virus");
        hackingOptions.add("Download Files");
        hackingOptions.add("Access Network");
        hackingOptions.add("Control Devices");
    }
    
    /**
     * Take damage to the terminal
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
     * Destroy the terminal
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
     * Repair the terminal
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
     * Hack the terminal
     */
    public boolean hack(String hackingOption) {
        if (isDestroyed || !isAccessible || isHacked) {
            return false;
        }
        
        if (!hackingOptions.contains(hackingOption)) {
            return false;
        }
        
        // Simulate hacking attempt
        int roll = (int)(Math.random() * 100);
        
        if (roll < hackingSuccessChance) {
            // Hack successful
            isHacked = true;
            completedHacks.add(hackingOption);
            currentHackingProgress += hackingReward;
            
            // Apply hack effects
            applyHackEffects(hackingOption);
            
            return true;
        } else {
            // Hack failed
            failedHacks.add(hackingOption);
            currentHackingProgress += hackingPenalty;
            
            // Apply failure effects
            applyFailureEffects(hackingOption);
            
            return false;
        }
    }
    
    /**
     * Apply hack effects
     */
    private void applyHackEffects(String hackingOption) {
        switch (hackingOption) {
            case "Disable Security" -> {
                isSecure = false;
                securityLevel = Math.max(0, securityLevel - 1);
            }
            case "Override Controls" -> {
                isCompromised = true;
                compromiseLevel++;
            }
            case "Extract Data" -> {
                availableRewards.add("Data Extracted");
            }
            case "Activate Systems" -> {
                isAccessible = true;
                isBlocked = false;
            }
            case "Deactivate Systems" -> {
                isAccessible = false;
                isBlocked = true;
            }
            case "Bypass Firewall" -> {
                hackingSuccessChance += 10;
                hackingFailureChance -= 10;
            }
            case "Upload Virus" -> {
                isDamaged = true;
                damageLevel++;
            }
            case "Download Files" -> {
                availableRewards.add("Files Downloaded");
            }
            case "Access Network" -> {
                isRevealed = true;
                revealLevel++;
            }
            case "Control Devices" -> {
                isGuarded = false;
                guardLevel = Math.max(0, guardLevel - 1);
            }
        }
    }
    
    /**
     * Apply failure effects
     */
    private void applyFailureEffects(String hackingOption) {
        switch (hackingOption) {
            case "Disable Security" -> {
                isAlarmed = true;
                alarmLevel++;
            }
            case "Override Controls" -> {
                isGuarded = true;
                guardLevel++;
            }
            case "Extract Data" -> {
                appliedPenalties.add("Data Corruption");
            }
            case "Activate Systems" -> {
                isBlocked = true;
                isAccessible = false;
            }
            case "Deactivate Systems" -> {
                isAlarmed = true;
                alarmLevel++;
            }
            case "Bypass Firewall" -> {
                hackingSuccessChance -= 5;
                hackingFailureChance += 5;
            }
            case "Upload Virus" -> {
                isDamaged = true;
                damageLevel++;
            }
            case "Download Files" -> {
                appliedPenalties.add("File Corruption");
            }
            case "Access Network" -> {
                isHidden = true;
                hideLevel++;
            }
            case "Control Devices" -> {
                isAlarmed = true;
                alarmLevel++;
            }
        }
    }
    
    /**
     * Secure the terminal
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
     * Compromise the terminal
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
     * Guard the terminal
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
     * Remove guard from terminal
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
     * Alarm the terminal
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
     * Disarm the terminal
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
     * Hide the terminal
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
     * Reveal the terminal
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
     * Block the terminal
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
     * Unblock the terminal
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
     * Process time-sensitive terminals
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
     * Check if terminal is functional
     */
    public boolean isFunctional() {
        return !isDestroyed && isAccessible && !isBlocked;
    }
    
    /**
     * Check if terminal is hackable
     */
    public boolean isHackable() {
        return isFunctional() && !isHacked && !isGuarded;
    }
    
    /**
     * Check if terminal is secure
     */
    public boolean isSecure() {
        return isFunctional() && isSecure && !isCompromised;
    }
    
    /**
     * Get terminal status
     */
    public String getTerminalStatus() {
        if (isDestroyed) return "DESTROYED";
        if (!isAccessible) return "INACCESSIBLE";
        if (isBlocked) return "BLOCKED";
        if (isTimeExpired) return "TIME_EXPIRED";
        if (isTimeCritical) return "TIME_CRITICAL";
        if (isHacked) return "HACKED";
        if (isCompromised) return "COMPROMISED";
        if (isAlarmed) return "ALARMED";
        if (isGuarded) return "GUARDED";
        if (isSecure) return "SECURE";
        if (isDamaged) return "DAMAGED";
        if (isHidden) return "HIDDEN";
        if (isRevealed) return "REVEALED";
        return "NORMAL";
    }
    
    /**
     * Get terminal value
     */
    public int getTerminalValue() {
        int value = currentHackingProgress;
        
        if (isHacked) {
            value += hackingReward;
        }
        
        if (isDamaged) {
            value += damageLevel * 10;
        }
        
        if (isRepaired) {
            value += repairLevel * 5;
        }
        
        if (isSecure) {
            value += securityLevel * 15;
        }
        
        if (isCompromised) {
            value += compromiseLevel * 20;
        }
        
        if (isGuarded) {
            value += guardLevel * 12;
        }
        
        if (isAlarmed) {
            value += alarmLevel * 8;
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
     * Get terminal description
     */
    public String getTerminalDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append("\n");
        desc.append("Description: ").append(description).append("\n");
        desc.append("Status: ").append(getTerminalStatus()).append("\n");
        desc.append("Position: ").append(position).append("\n");
        desc.append("Health: ").append(currentHealth).append("/").append(maxHealth).append("\n");
        desc.append("Hacking Difficulty: ").append(hackingDifficulty).append("\n");
        desc.append("Success Chance: ").append(hackingSuccessChance).append("%\n");
        desc.append("Failure Chance: ").append(hackingFailureChance).append("%\n");
        desc.append("Progress: ").append(currentHackingProgress).append("\n");
        desc.append("Value: ").append(getTerminalValue()).append("\n");
        
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
        
        if (isSecure) {
            desc.append("Security Level: ").append(securityLevel).append("\n");
        }
        
        if (isCompromised) {
            desc.append("Compromise Level: ").append(compromiseLevel).append("\n");
        }
        
        if (isGuarded) {
            desc.append("Guard Level: ").append(guardLevel).append("\n");
        }
        
        if (isAlarmed) {
            desc.append("Alarm Level: ").append(alarmLevel).append("\n");
        }
        
        if (!completedHacks.isEmpty()) {
            desc.append("Completed Hacks: ").append(String.join(", ", completedHacks)).append("\n");
        }
        
        if (!failedHacks.isEmpty()) {
            desc.append("Failed Hacks: ").append(String.join(", ", failedHacks)).append("\n");
        }
        
        if (!availableRewards.isEmpty()) {
            desc.append("Available Rewards: ").append(String.join(", ", availableRewards)).append("\n");
        }
        
        if (!appliedPenalties.isEmpty()) {
            desc.append("Applied Penalties: ").append(String.join(", ", appliedPenalties)).append("\n");
        }
        
        if (isPrimary) desc.append("Primary Terminal\n");
        if (isSecondary) desc.append("Secondary Terminal\n");
        if (isOptional) desc.append("Optional Terminal\n");
        if (isRequired) desc.append("Required Terminal\n");
        if (isBonus) desc.append("Bonus Terminal\n");
        if (isPenalty) desc.append("Penalty Terminal\n");
        if (isTimeSensitive) desc.append("Time Sensitive\n");
        if (isTimeCritical) desc.append("Time Critical\n");
        if (isTimeExpired) desc.append("Time Expired\n");
        
        return desc.toString();
    }
} 
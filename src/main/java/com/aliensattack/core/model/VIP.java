package com.aliensattack.core.model;

import com.aliensattack.core.enums.UnitType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a VIP (Very Important Person) for escort and extraction missions
 * Implements XCOM 2 VIP mechanics
 */
@Getter
@Setter
public class VIP extends Unit {
    private String vipName;
    private String vipTitle;
    private String vipDescription;
    private boolean isExtracted;
    private boolean isCaptured;
    private boolean isDead;
    private int extractionValue;
    private int capturePenalty;
    private int deathPenalty;
    private boolean isEscorted;
    private Unit escortUnit;
    private boolean isPanicked;
    private boolean isStunned;
    private boolean isHidden;
    private boolean isResistant;
    private boolean isVulnerable;
    private boolean isProtected;
    private boolean isExposed;
    private boolean isWounded;
    private boolean isHealing;
    private int healingRate;
    private int panicChance;
    private int stunChance;
    private int captureChance;
    private int escapeChance;
    private int resistanceLevel;
    private int vulnerabilityLevel;
    private int protectionLevel;
    private int exposureLevel;
    private int woundLevel;
    private int healingLevel;
    
    public VIP(String name, String title, String description, int maxHealth, int movementRange) {
        super(name, maxHealth, movementRange, 0, 0, UnitType.CIVILIAN);
        this.vipName = name;
        this.vipTitle = title;
        this.vipDescription = description;
        this.isExtracted = false;
        this.isCaptured = false;
        this.isDead = false;
        this.extractionValue = 100;
        this.capturePenalty = -50;
        this.deathPenalty = -100;
        this.isEscorted = false;
        this.escortUnit = null;
        this.isPanicked = false;
        this.isStunned = false;
        this.isHidden = false;
        this.isResistant = false;
        this.isVulnerable = false;
        this.isProtected = false;
        this.isExposed = false;
        this.isWounded = false;
        this.isHealing = false;
        this.healingRate = 1;
        this.panicChance = 25;
        this.stunChance = 15;
        this.captureChance = 10;
        this.escapeChance = 5;
        this.resistanceLevel = 0;
        this.vulnerabilityLevel = 0;
        this.protectionLevel = 0;
        this.exposureLevel = 0;
        this.woundLevel = 0;
        this.healingLevel = 0;
    }
    
    /**
     * Extract the VIP
     */
    public boolean extract() {
        if (isExtracted || isDead) {
            return false;
        }
        
        isExtracted = true;
        isEscorted = false;
        escortUnit = null;
        return true;
    }
    
    /**
     * Capture the VIP
     */
    public boolean capture() {
        if (isExtracted || isDead || isCaptured) {
            return false;
        }
        
        isCaptured = true;
        isEscorted = false;
        escortUnit = null;
        return true;
    }
    
    /**
     * Kill the VIP
     */
    public boolean kill() {
        if (isDead) {
            return false;
        }
        
        isDead = true;
        isExtracted = false;
        isCaptured = false;
        isEscorted = false;
        escortUnit = null;
        return true;
    }
    
    /**
     * Assign escort unit
     */
    public boolean assignEscort(Unit escort) {
        if (isExtracted || isDead || isCaptured) {
            return false;
        }
        
        if (escort == null || !escort.isAlive()) {
            return false;
        }
        
        isEscorted = true;
        escortUnit = escort;
        return true;
    }
    
    /**
     * Remove escort unit
     */
    public boolean removeEscort() {
        if (!isEscorted) {
            return false;
        }
        
        isEscorted = false;
        escortUnit = null;
        return true;
    }
    
    /**
     * Panic the VIP
     */
    public boolean panic() {
        if (isPanicked || isDead) {
            return false;
        }
        
        isPanicked = true;
        return true;
    }
    
    /**
     * Calm the VIP
     */
    public boolean calm() {
        if (!isPanicked) {
            return false;
        }
        
        isPanicked = false;
        return true;
    }
    
    /**
     * Stun the VIP
     */
    public boolean stun() {
        if (isStunned || isDead) {
            return false;
        }
        
        isStunned = true;
        return true;
    }
    
    /**
     * Wake the VIP
     */
    public boolean wake() {
        if (!isStunned) {
            return false;
        }
        
        isStunned = false;
        return true;
    }
    
    /**
     * Hide the VIP
     */
    public boolean hide() {
        if (isHidden || isDead) {
            return false;
        }
        
        isHidden = true;
        return true;
    }
    
    /**
     * Reveal the VIP
     */
    public boolean revealVIP() {
        if (!isHidden) {
            return false;
        }
        
        isHidden = false;
        return true;
    }
    
    /**
     * Make VIP resistant
     */
    public boolean makeResistant() {
        if (isResistant) {
            return false;
        }
        
        isResistant = true;
        resistanceLevel++;
        return true;
    }
    
    /**
     * Make VIP vulnerable
     */
    public boolean makeVulnerable() {
        if (isVulnerable) {
            return false;
        }
        
        isVulnerable = true;
        vulnerabilityLevel++;
        return true;
    }
    
    /**
     * Protect the VIP
     */
    public boolean protect() {
        if (isProtected) {
            return false;
        }
        
        isProtected = true;
        protectionLevel++;
        return true;
    }
    
    /**
     * Expose the VIP
     */
    public boolean expose() {
        if (isExposed) {
            return false;
        }
        
        isExposed = true;
        exposureLevel++;
        return true;
    }
    
    /**
     * Wound the VIP
     */
    public boolean wound() {
        if (isWounded || isDead) {
            return false;
        }
        
        isWounded = true;
        woundLevel++;
        return true;
    }
    
    /**
     * Start healing the VIP
     */
    public boolean startHealing() {
        if (isHealing || isDead) {
            return false;
        }
        
        isHealing = true;
        healingLevel++;
        return true;
    }
    
    /**
     * Stop healing the VIP
     */
    public boolean stopHealing() {
        if (!isHealing) {
            return false;
        }
        
        isHealing = false;
        return true;
    }
    
    /**
     * Process VIP healing
     */
    public void processHealing() {
        if (isHealing && isWounded) {
            woundLevel = Math.max(0, woundLevel - healingRate);
            if (woundLevel == 0) {
                isWounded = false;
                isHealing = false;
            }
        }
    }
    
    /**
     * Check if VIP can move
     */
    @Override
    public boolean canMove() {
        return super.canMove() && !isPanicked && !isStunned && !isDead;
    }
    
    /**
     * Check if VIP can be escorted
     */
    public boolean canBeEscorted() {
        return isAlive() && !isExtracted && !isCaptured && !isDead;
    }
    
    /**
     * Check if VIP can be extracted
     */
    public boolean canBeExtracted() {
        return isAlive() && !isExtracted && !isCaptured && !isDead;
    }
    
    /**
     * Check if VIP can be captured
     */
    public boolean canBeCaptured() {
        return isAlive() && !isExtracted && !isCaptured && !isDead;
    }
    
    /**
     * Check if VIP can be killed
     */
    public boolean canBeKilled() {
        return isAlive() && !isDead;
    }
    
    /**
     * Get VIP status
     */
    public String getVIPStatus() {
        if (isDead) return "DEAD";
        if (isExtracted) return "EXTRACTED";
        if (isCaptured) return "CAPTURED";
        if (isPanicked) return "PANICKED";
        if (isStunned) return "STUNNED";
        if (isHidden) return "HIDDEN";
        if (isWounded) return "WOUNDED";
        if (isHealing) return "HEALING";
        if (isEscorted) return "ESCORTED";
        return "NORMAL";
    }
    
    /**
     * Get VIP description
     */
    public String getVIPDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(vipName).append(" (").append(vipTitle).append(")\n");
        if (vipDescription != null && !vipDescription.isEmpty()) {
            desc.append("Description: ").append(vipDescription).append("\n");
        }
        desc.append("Status: ").append(getVIPStatus()).append("\n");
        desc.append("Health: ").append(getCurrentHealth()).append("/").append(getMaxHealth()).append("\n");
        desc.append("Position: ").append(getPosition()).append("\n");
        
        if (isEscorted && escortUnit != null) {
            desc.append("Escorted by: ").append(escortUnit.getName()).append("\n");
        }
        
        if (isWounded) {
            desc.append("Wound Level: ").append(woundLevel).append("\n");
        }
        
        if (isHealing) {
            desc.append("Healing Level: ").append(healingLevel).append("\n");
        }
        
        if (isResistant) {
            desc.append("Resistance Level: ").append(resistanceLevel).append("\n");
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
        
        desc.append("Extraction Value: ").append(extractionValue).append("\n");
        desc.append("Capture Penalty: ").append(capturePenalty).append("\n");
        desc.append("Death Penalty: ").append(deathPenalty).append("\n");
        
        return desc.toString();
    }
    
    /**
     * Get mission value
     */
    public int getMissionValue() {
        if (isDead) return deathPenalty;
        if (isCaptured) return capturePenalty;
        if (isExtracted) return extractionValue;
        return 0;
    }
} 
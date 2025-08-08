package com.aliensattack.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents an extraction point for VIP and escort missions
 * Implements XCOM 2 extraction point mechanics
 */
@Getter
@Setter
public class ExtractionPoint {
    private String name;
    private Position position;
    private int radius;
    private boolean isActive;
    private boolean isSecure;
    private boolean isCompromised;
    private boolean isDestroyed;
    private int securityLevel;
    private int extractionTime;
    private int currentExtractionTime;
    private boolean isExtracting;
    private List<Unit> unitsInExtraction;
    private List<VIP> vipsInExtraction;
    private int maxCapacity;
    private int currentCapacity;
    private boolean isPrimary;
    private boolean isSecondary;
    private boolean isEmergency;
    private boolean isHidden;
    private boolean isRevealed;
    private boolean isAccessible;
    private boolean isBlocked;
    private boolean isDamaged;
    private boolean isRepaired;
    private int damageLevel;
    private int repairLevel;
    private int securityBonus;
    private int extractionBonus;
    private int capacityBonus;
    private int timeBonus;
    private int damagePenalty;
    private int timePenalty;
    private int capacityPenalty;
    private int securityPenalty;
    
    public ExtractionPoint(String name, Position position, int radius) {
        this.name = name;
        this.position = position;
        this.radius = radius;
        this.isActive = true;
        this.isSecure = false;
        this.isCompromised = false;
        this.isDestroyed = false;
        this.securityLevel = 0;
        this.extractionTime = 3;
        this.currentExtractionTime = 0;
        this.isExtracting = false;
        this.unitsInExtraction = new ArrayList<>();
        this.vipsInExtraction = new ArrayList<>();
        this.maxCapacity = 10;
        this.currentCapacity = 0;
        this.isPrimary = false;
        this.isSecondary = false;
        this.isEmergency = false;
        this.isHidden = false;
        this.isRevealed = false;
        this.isAccessible = true;
        this.isBlocked = false;
        this.isDamaged = false;
        this.isRepaired = false;
        this.damageLevel = 0;
        this.repairLevel = 0;
        this.securityBonus = 0;
        this.extractionBonus = 0;
        this.capacityBonus = 0;
        this.timeBonus = 0;
        this.damagePenalty = 0;
        this.timePenalty = 0;
        this.capacityPenalty = 0;
        this.securityPenalty = 0;
    }
    
    /**
     * Check if unit is in extraction range
     */
    public boolean isInExtractionRange(Unit unit) {
        if (unit == null || unit.getPosition() == null) {
            return false;
        }
        
        int distance = position.getDistanceTo(unit.getPosition());
        return distance <= radius;
    }
    
    /**
     * Check if VIP is in extraction range
     */
    public boolean isVIPInExtractionRange(VIP vip) {
        if (vip == null || vip.getPosition() == null) {
            return false;
        }
        
        int distance = position.getDistanceTo(vip.getPosition());
        return distance <= radius;
    }
    
    /**
     * Start extraction for unit
     */
    public boolean startExtraction(Unit unit) {
        if (!isActive || isDestroyed || !isAccessible) {
            return false;
        }
        
        if (!isInExtractionRange(unit)) {
            return false;
        }
        
        if (currentCapacity >= maxCapacity) {
            return false;
        }
        
        if (!unitsInExtraction.contains(unit)) {
            unitsInExtraction.add(unit);
            currentCapacity++;
        }
        
        if (!isExtracting) {
            isExtracting = true;
            currentExtractionTime = 0;
        }
        
        return true;
    }
    
    /**
     * Start extraction for VIP
     */
    public boolean startVIPExtraction(VIP vip) {
        if (!isActive || isDestroyed || !isAccessible) {
            return false;
        }
        
        if (!isVIPInExtractionRange(vip)) {
            return false;
        }
        
        if (currentCapacity >= maxCapacity) {
            return false;
        }
        
        if (!vipsInExtraction.contains(vip)) {
            vipsInExtraction.add(vip);
            currentCapacity++;
        }
        
        if (!isExtracting) {
            isExtracting = true;
            currentExtractionTime = 0;
        }
        
        return true;
    }
    
    /**
     * Process extraction
     */
    public void processExtraction() {
        if (!isExtracting) {
            return;
        }
        
        currentExtractionTime++;
        
        if (currentExtractionTime >= getTotalExtractionTime()) {
            completeExtraction();
        }
    }
    
    /**
     * Complete extraction
     */
    private void completeExtraction() {
        // Extract all units
        for (Unit unit : unitsInExtraction) {
            // Mark unit as extracted (this would need to be implemented in Unit class)
            unit.setPosition(null); // Remove from field
        }
        
        // Extract all VIPs
        for (VIP vip : vipsInExtraction) {
            vip.extract();
        }
        
        // Clear extraction lists
        unitsInExtraction.clear();
        vipsInExtraction.clear();
        currentCapacity = 0;
        isExtracting = false;
        currentExtractionTime = 0;
    }
    
    /**
     * Cancel extraction
     */
    public boolean cancelExtraction() {
        if (!isExtracting) {
            return false;
        }
        
        unitsInExtraction.clear();
        vipsInExtraction.clear();
        currentCapacity = 0;
        isExtracting = false;
        currentExtractionTime = 0;
        return true;
    }
    
    /**
     * Secure the extraction point
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
     * Compromise the extraction point
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
     * Destroy the extraction point
     */
    public boolean destroy() {
        if (isDestroyed) {
            return false;
        }
        
        isDestroyed = true;
        isActive = false;
        isAccessible = false;
        cancelExtraction();
        return true;
    }
    
    /**
     * Repair the extraction point
     */
    public boolean repair() {
        if (!isDamaged || isDestroyed) {
            return false;
        }
        
        damageLevel = Math.max(0, damageLevel - 1);
        if (damageLevel == 0) {
            isDamaged = false;
            isRepaired = true;
        }
        return true;
    }
    
    /**
     * Damage the extraction point
     */
    public boolean damage() {
        if (isDestroyed) {
            return false;
        }
        
        damageLevel++;
        isDamaged = true;
        isRepaired = false;
        
        if (damageLevel >= 3) {
            destroy();
        }
        
        return true;
    }
    
    /**
     * Block the extraction point
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
     * Unblock the extraction point
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
     * Hide the extraction point
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
     * Reveal the extraction point
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
     * Get total extraction time
     */
    public int getTotalExtractionTime() {
        int totalTime = extractionTime;
        totalTime += timeBonus;
        totalTime -= timePenalty;
        totalTime = Math.max(1, totalTime); // Minimum 1 turn
        return totalTime;
    }
    
    /**
     * Get total capacity
     */
    public int getTotalCapacity() {
        int totalCapacity = maxCapacity;
        totalCapacity += capacityBonus;
        totalCapacity -= capacityPenalty;
        totalCapacity = Math.max(1, totalCapacity); // Minimum 1 unit
        return totalCapacity;
    }
    
    /**
     * Get total security level
     */
    public int getTotalSecurityLevel() {
        int totalSecurity = securityLevel;
        totalSecurity += securityBonus;
        totalSecurity -= securityPenalty;
        return totalSecurity;
    }
    
    /**
     * Check if extraction point is functional
     */
    public boolean isFunctional() {
        return isActive && !isDestroyed && isAccessible && !isBlocked;
    }
    
    /**
     * Check if extraction point is available
     */
    public boolean isAvailable() {
        return isFunctional() && currentCapacity < getTotalCapacity();
    }
    
    /**
     * Get extraction point status
     */
    public String getExtractionPointStatus() {
        if (isDestroyed) return "DESTROYED";
        if (!isActive) return "INACTIVE";
        if (isBlocked) return "BLOCKED";
        if (!isAccessible) return "INACCESSIBLE";
        if (isCompromised) return "COMPROMISED";
        if (isDamaged) return "DAMAGED";
        if (isSecure) return "SECURE";
        if (isExtracting) return "EXTRACTING";
        return "ACTIVE";
    }
    
    /**
     * Get extraction point description
     */
    public String getExtractionPointDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append("\n");
        desc.append("Status: ").append(getExtractionPointStatus()).append("\n");
        desc.append("Position: ").append(position).append("\n");
        desc.append("Radius: ").append(radius).append(" tiles\n");
        desc.append("Capacity: ").append(currentCapacity).append("/").append(getTotalCapacity()).append("\n");
        desc.append("Extraction Time: ").append(getTotalExtractionTime()).append(" turns\n");
        desc.append("Security Level: ").append(getTotalSecurityLevel()).append("\n");
        
        if (isExtracting) {
            desc.append("Extraction Progress: ").append(currentExtractionTime).append("/").append(getTotalExtractionTime()).append("\n");
            desc.append("Units in Extraction: ").append(unitsInExtraction.size()).append("\n");
            desc.append("VIPs in Extraction: ").append(vipsInExtraction.size()).append("\n");
        }
        
        if (isDamaged) {
            desc.append("Damage Level: ").append(damageLevel).append("/3\n");
        }
        
        if (isPrimary) desc.append("Primary Extraction Point\n");
        if (isSecondary) desc.append("Secondary Extraction Point\n");
        if (isEmergency) desc.append("Emergency Extraction Point\n");
        if (isHidden) desc.append("Hidden\n");
        if (isRevealed) desc.append("Revealed\n");
        
        return desc.toString();
    }
} 
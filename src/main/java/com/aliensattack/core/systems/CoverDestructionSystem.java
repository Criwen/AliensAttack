package com.aliensattack.core.systems;

import com.aliensattack.core.enums.CoverType;
import com.aliensattack.core.model.Position;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Cover Destruction System for environmental destruction.
 * Implements cover destruction, environmental damage, and destruction mechanics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoverDestructionSystem {
    
    private String coverId;
    private String coverName;
    private CoverType coverType;
    private int maxHealth;
    private int currentHealth;
    private boolean isDestroyed;
    private boolean isPartiallyDestroyed;
    private double partialCoverChance;
    private int height;
    private int maxHeight;
    private List<Position> adjacentPositions;
    private Map<String, Integer> damageHistory;
    private List<String> destructionEvents;
    private boolean isRepairable;
    private int repairCost;
    private int repairTime;
    private boolean isBeingRepaired;
    private int repairProgress;
    private Map<String, Integer> coverBonuses;
    private List<String> coverAbilities;
    private boolean isFlankable;
    private List<Position> flankingPositions;
    private Map<String, Integer> resistanceModifiers;
    private boolean isIndestructible;
    private int destructionThreshold;
    private List<String> destructionTriggers;
    
    public CoverDestructionSystem(String coverId, String coverName, CoverType coverType) {
        this.coverId = coverId;
        this.coverName = coverName;
        this.coverType = coverType;
        this.maxHealth = getMaxHealthForType(coverType);
        this.currentHealth = maxHealth;
        this.isDestroyed = false;
        this.isPartiallyDestroyed = false;
        this.partialCoverChance = 0.5;
        this.height = getHeightForType(coverType);
        this.maxHeight = height;
        this.adjacentPositions = new ArrayList<>();
        this.damageHistory = new HashMap<>();
        this.destructionEvents = new ArrayList<>();
        this.isRepairable = true;
        this.repairCost = 10;
        this.repairTime = 3;
        this.isBeingRepaired = false;
        this.repairProgress = 0;
        this.coverBonuses = new HashMap<>();
        this.coverAbilities = new ArrayList<>();
        this.isFlankable = true;
        this.flankingPositions = new ArrayList<>();
        this.resistanceModifiers = new HashMap<>();
        this.isIndestructible = false;
        this.destructionThreshold = 0;
        this.destructionTriggers = new ArrayList<>();
        
        initializeCover();
    }
    
    /**
     * Initialize cover with type-specific properties
     */
    private void initializeCover() {
        // Set cover bonuses based on type
        switch (coverType) {
            case NONE:
                coverBonuses.put("defense", 0);
                coverBonuses.put("dodge", 0);
                partialCoverChance = 1.0;
                break;
            case LIGHT:
                coverBonuses.put("defense", 20);
                coverBonuses.put("dodge", 5);
                partialCoverChance = 0.8;
                break;
            case HEAVY:
                coverBonuses.put("defense", 40);
                coverBonuses.put("dodge", 15);
                partialCoverChance = 0.3;
                break;
            case FULL:
                coverBonuses.put("defense", 60);
                coverBonuses.put("dodge", 25);
                partialCoverChance = 0.0;
                break;
            case FULL_COVER:
                coverBonuses.put("defense", 40);
                coverBonuses.put("dodge", 20);
                partialCoverChance = 0.0;
                break;
            case HALF_COVER:
                coverBonuses.put("defense", 20);
                coverBonuses.put("dodge", 10);
                partialCoverChance = 0.5;
                break;
            case LOW_COVER:
                coverBonuses.put("defense", 10);
                coverBonuses.put("dodge", 5);
                partialCoverChance = 0.8;
                break;
            case DEEP_COVER:
                coverBonuses.put("defense", 50);
                coverBonuses.put("dodge", 30);
                coverBonuses.put("stealth", 15);
                partialCoverChance = 0.0;
                break;
            case FLANKED:
                coverBonuses.put("defense", 0);
                coverBonuses.put("dodge", 0);
                partialCoverChance = 1.0;
                break;
        }
        
        // Set resistance modifiers
        resistanceModifiers.put("explosive", 50);
        resistanceModifiers.put("fire", 30);
        resistanceModifiers.put("acid", 20);
        resistanceModifiers.put("psionic", 10);
        
        // Add cover abilities
        coverAbilities.add("provide_cover");
        coverAbilities.add("block_line_of_sight");
        coverAbilities.add("reduce_damage");
    }
    
    /**
     * Take damage to cover
     */
    public boolean takeDamage(int damage, String damageType) {
        if (isDestroyed || isIndestructible) {
            return false;
        }
        
        // Apply resistance modifiers
        int resistance = resistanceModifiers.getOrDefault(damageType, 0);
        int actualDamage = Math.max(1, damage - resistance);
        
        // Record damage
        damageHistory.put(damageType, damageHistory.getOrDefault(damageType, 0) + actualDamage);
        
        // Apply damage
        currentHealth -= actualDamage;
        
        // Check for partial destruction
        if (currentHealth <= maxHealth * 0.5 && !isPartiallyDestroyed) {
            isPartiallyDestroyed = true;
            partialCoverChance = 0.8;
            destructionEvents.add("PARTIAL_DESTRUCTION");
        }
        
        // Check for complete destruction
        if (currentHealth <= 0) {
            isDestroyed = true;
            isPartiallyDestroyed = false;
            partialCoverChance = 1.0;
            destructionEvents.add("COMPLETE_DESTRUCTION");
            return true;
        }
        
        return false;
    }
    
    /**
     * Get cover bonus for specific attribute
     */
    public int getCoverBonus(String attribute) {
        if (isDestroyed) {
            return 0;
        }
        
        int baseBonus = coverBonuses.getOrDefault(attribute, 0);
        
        // Reduce bonus if partially destroyed
        if (isPartiallyDestroyed) {
            baseBonus = (int) (baseBonus * 0.5);
        }
        
        return baseBonus;
    }
    
    /**
     * Check if cover provides partial cover
     */
    public boolean providesPartialCover() {
        return isPartiallyDestroyed || partialCoverChance > 0.0;
    }
    
    /**
     * Get partial cover chance
     */
    public double getPartialCoverChance() {
        if (isDestroyed) {
            return 1.0;
        }
        
        if (isPartiallyDestroyed) {
            return partialCoverChance;
        }
        
        return 0.0;
    }
    
    /**
     * Check if unit is flanked from position
     */
    public boolean isFlankedFrom(Position attackerPosition) {
        if (!isFlankable) {
            return false;
        }
        
        // Check if attacker is in flanking position
        for (Position flankPos : flankingPositions) {
            if (attackerPosition.equals(flankPos)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Add adjacent position
     */
    public void addAdjacentPosition(Position position) {
        if (!adjacentPositions.contains(position)) {
            adjacentPositions.add(position);
        }
    }
    
    /**
     * Add flanking position
     */
    public void addFlankingPosition(Position position) {
        if (!flankingPositions.contains(position)) {
            flankingPositions.add(position);
        }
    }
    
    /**
     * Repair cover
     */
    public boolean repairCover(int repairAmount) {
        if (isDestroyed || !isRepairable || isBeingRepaired) {
            return false;
        }
        
        currentHealth = Math.min(currentHealth + repairAmount, maxHealth);
        
        // Check if no longer partially destroyed
        if (currentHealth > maxHealth * 0.5 && isPartiallyDestroyed) {
            isPartiallyDestroyed = false;
            partialCoverChance = getPartialCoverChanceForType(coverType);
        }
        
        return true;
    }
    
    /**
     * Start repair process
     */
    public boolean startRepair() {
        if (isDestroyed || !isRepairable || isBeingRepaired) {
            return false;
        }
        
        isBeingRepaired = true;
        repairProgress = 0;
        return true;
    }
    
    /**
     * Update repair progress
     */
    public void updateRepairProgress() {
        if (isBeingRepaired) {
            repairProgress++;
            
            if (repairProgress >= repairTime) {
                completeRepair();
            }
        }
    }
    
    /**
     * Complete repair
     */
    private void completeRepair() {
        isBeingRepaired = false;
        repairProgress = 0;
        
        // Restore health
        currentHealth = maxHealth;
        isPartiallyDestroyed = false;
        partialCoverChance = getPartialCoverChanceForType(coverType);
        
        destructionEvents.add("REPAIRED");
    }
    
    /**
     * Upgrade cover to new type
     */
    public boolean upgradeCover(CoverType newType) {
        if (isDestroyed) {
            return false;
        }
        
        coverType = newType;
        maxHealth = getMaxHealthForType(newType);
        currentHealth = Math.min(currentHealth, maxHealth);
        height = getHeightForType(newType);
        maxHeight = height;
        
        // Reinitialize bonuses
        coverBonuses.clear();
        initializeCover();
        
        return true;
    }
    
    /**
     * Get max health for cover type
     */
    private int getMaxHealthForType(CoverType type) {
        switch (type) {
            case FULL_COVER:
                return 100;
            case HALF_COVER:
                return 60;
            case LOW_COVER:
                return 30;
            case DEEP_COVER:
                return 150;
            case FLANKED:
                return 0;
            default:
                return 50;
        }
    }
    
    /**
     * Get height for cover type
     */
    private int getHeightForType(CoverType type) {
        switch (type) {
            case FULL_COVER:
                return 2;
            case HALF_COVER:
                return 1;
            case LOW_COVER:
                return 1;
            case DEEP_COVER:
                return 3;
            case FLANKED:
                return 0;
            default:
                return 1;
        }
    }
    
    /**
     * Get partial cover chance for type
     */
    private double getPartialCoverChanceForType(CoverType type) {
        switch (type) {
            case FULL_COVER:
                return 0.0;
            case HALF_COVER:
                return 0.5;
            case LOW_COVER:
                return 0.8;
            case DEEP_COVER:
                return 0.0;
            case FLANKED:
                return 1.0;
            default:
                return 0.5;
        }
    }
    
    /**
     * Check if cover can be moved to
     */
    public boolean canMoveToCover(Position unitPosition) {
        if (isDestroyed) {
            return false;
        }
        
        // Check if position is adjacent
        return adjacentPositions.contains(unitPosition);
    }
    
    /**
     * Get cover effectiveness percentage
     */
    public double getCoverEffectiveness() {
        if (isDestroyed) {
            return 0.0;
        }
        
        return (double) currentHealth / maxHealth;
    }
    
    /**
     * Get destruction percentage
     */
    public double getDestructionPercentage() {
        return 1.0 - getCoverEffectiveness();
    }
    
    /**
     * Check if cover is vulnerable to specific damage type
     */
    public boolean isVulnerableTo(String damageType) {
        int resistance = resistanceModifiers.getOrDefault(damageType, 0);
        return resistance < 50;
    }
    
    /**
     * Get resistance to damage type
     */
    public int getResistance(String damageType) {
        return resistanceModifiers.getOrDefault(damageType, 0);
    }
    
    /**
     * Add destruction trigger
     */
    public void addDestructionTrigger(String trigger) {
        if (!destructionTriggers.contains(trigger)) {
            destructionTriggers.add(trigger);
        }
    }
    
    /**
     * Check if destruction threshold is met
     */
    public boolean isDestructionThresholdMet() {
        return currentHealth <= destructionThreshold;
    }
    
    /**
     * Get cover status description
     */
    public String getCoverStatus() {
        if (isDestroyed) {
            return "DESTROYED";
        } else if (isPartiallyDestroyed) {
            return "PARTIALLY_DESTROYED";
        } else if (isBeingRepaired) {
            return "BEING_REPAIRED";
        } else {
            return "INTACT";
        }
    }
    
    /**
     * Get cover description
     */
    public String getCoverDescription() {
        return coverName + " (" + coverType + ") - " + getCoverStatus() + 
               " - Health: " + currentHealth + "/" + maxHealth + 
               " - Height: " + height;
    }
    
    /**
     * Get damage history
     */
    public Map<String, Integer> getDamageHistory() {
        return new HashMap<>(damageHistory);
    }
    
    /**
     * Get destruction events
     */
    public List<String> getDestructionEvents() {
        return new ArrayList<>(destructionEvents);
    }
    
    /**
     * Check if cover is repairable
     */
    public boolean isRepairable() {
        return isRepairable && !isDestroyed;
    }
    
    /**
     * Check if cover is being repaired
     */
    public boolean isBeingRepaired() {
        return isBeingRepaired;
    }
    
    /**
     * Get repair progress
     */
    public double getRepairProgress() {
        return repairTime > 0 ? (double) repairProgress / repairTime : 0.0;
    }
    
    /**
     * Get adjacent positions
     */
    public List<Position> getAdjacentPositions() {
        return new ArrayList<>(adjacentPositions);
    }
    
    /**
     * Get flanking positions
     */
    public List<Position> getFlankingPositions() {
        return new ArrayList<>(flankingPositions);
    }
    
    /**
     * Check if cover is flankable
     */
    public boolean isFlankable() {
        return isFlankable;
    }
    
    /**
     * Set flankable status
     */
    public void setFlankable(boolean flankable) {
        this.isFlankable = flankable;
    }
    
    /**
     * Get cover height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Get max height
     */
    public int getMaxHeight() {
        return maxHeight;
    }
}

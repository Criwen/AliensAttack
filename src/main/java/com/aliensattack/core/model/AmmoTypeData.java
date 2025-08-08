package com.aliensattack.core.model;

import com.aliensattack.core.enums.AmmoType;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents different types of ammunition
 * Implements XCOM 2 ammo types system
 */
@Getter
@Setter
public class AmmoTypeData {
    private String name;
    private AmmoType type;
    private int damageBonus;
    private int accuracyBonus;
    private int criticalBonus;
    private int rangeBonus;
    private int penetrationBonus;
    private int armorPiercingBonus;
    private int explosiveBonus;
    private int incendiaryBonus;
    private int acidBonus;
    private int electricBonus;
    private int radiationBonus;
    private int frostBonus;
    private int corrosionBonus;
    private int bluescreenBonus;
    private int psionicBonus;
    private int hackingBonus;
    private int roboticBonus;
    private int alienBonus;
    private int humanBonus;
    private int hybridBonus;
    private boolean isAdvanced;
    private boolean isExperimental;
    private boolean isAlien;
    private boolean isNanotech;
    private boolean isPsionic;
    private boolean isRobotic;
    private boolean isHybrid;
    private int maxUses;
    private int currentUses;
    private int cost;
    private int rarity;
    
    public AmmoTypeData(String name, AmmoType type, int damageBonus, int accuracyBonus) {
        this.name = name;
        this.type = type;
        this.damageBonus = damageBonus;
        this.accuracyBonus = accuracyBonus;
        this.criticalBonus = 0;
        this.rangeBonus = 0;
        this.penetrationBonus = 0;
        this.armorPiercingBonus = 0;
        this.explosiveBonus = 0;
        this.incendiaryBonus = 0;
        this.acidBonus = 0;
        this.electricBonus = 0;
        this.radiationBonus = 0;
        this.frostBonus = 0;
        this.corrosionBonus = 0;
        this.bluescreenBonus = 0;
        this.psionicBonus = 0;
        this.hackingBonus = 0;
        this.roboticBonus = 0;
        this.alienBonus = 0;
        this.humanBonus = 0;
        this.hybridBonus = 0;
        this.isAdvanced = false;
        this.isExperimental = false;
        this.isAlien = false;
        this.isNanotech = false;
        this.isPsionic = false;
        this.isRobotic = false;
        this.isHybrid = false;
        this.maxUses = 10;
        this.currentUses = maxUses;
        this.cost = 100;
        this.rarity = 1;
        
        // Set properties based on ammo type
        setPropertiesByAmmoType(type);
    }
    
    private void setPropertiesByAmmoType(AmmoType type) {
        switch (type) {
            case STANDARD:
                // Default values already set
                break;
            case ARMOR_PIERCING:
                armorPiercingBonus = 15;
                penetrationBonus = 10;
                cost = 150;
                rarity = 2;
                break;
            case EXPLOSIVE:
                explosiveBonus = 20;
                damageBonus += 10;
                cost = 200;
                rarity = 3;
                break;
            case INCENDIARY:
                incendiaryBonus = 15;
                damageBonus += 5;
                cost = 175;
                rarity = 2;
                break;
            case ACID:
                acidBonus = 25;
                damageBonus += 8;
                cost = 225;
                rarity = 3;
                break;
            case STUN:
                damageBonus += 5;
                cost = 150;
                rarity = 2;
                break;
            case POISON:
                damageBonus += 8;
                cost = 175;
                rarity = 2;
                break;
            case PLASMA:
                damageBonus += 15;
                cost = 300;
                rarity = 4;
                break;
            case LASER:
                damageBonus += 12;
                accuracyBonus += 10;
                cost = 250;
                rarity = 3;
                break;
            default:
                // Default values already set
                break;
        }
    }
    
    /**
     * Use ammo
     */
    public boolean use() {
        if (currentUses <= 0) {
            return false;
        }
        
        currentUses--;
        return true;
    }
    
    /**
     * Check if ammo can be used
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
     * Refill ammo
     */
    public void refill() {
        currentUses = maxUses;
    }
    
    /**
     * Refill ammo partially
     */
    public void refill(int uses) {
        currentUses = Math.min(currentUses + uses, maxUses);
    }
    
    /**
     * Get total damage bonus
     */
    public int getTotalDamageBonus() {
        return damageBonus + explosiveBonus + acidBonus + electricBonus + 
               radiationBonus + corrosionBonus + bluescreenBonus + psionicBonus + 
               roboticBonus + alienBonus + hybridBonus;
    }
    
    /**
     * Get total accuracy bonus
     */
    public int getTotalAccuracyBonus() {
        return accuracyBonus;
    }
    
    /**
     * Get total critical bonus
     */
    public int getTotalCriticalBonus() {
        return criticalBonus;
    }
    
    /**
     * Get total range bonus
     */
    public int getTotalRangeBonus() {
        return rangeBonus;
    }
    
    /**
     * Get total penetration bonus
     */
    public int getTotalPenetrationBonus() {
        return penetrationBonus + armorPiercingBonus;
    }
    
    /**
     * Check if ammo is empty
     */
    public boolean isEmpty() {
        return currentUses <= 0;
    }
    
    /**
     * Check if ammo is full
     */
    public boolean isFull() {
        return currentUses >= maxUses;
    }
    
    /**
     * Get efficiency rating
     */
    public int getEfficiencyRating() {
        int rating = 0;
        rating += getTotalDamageBonus();
        rating += getTotalAccuracyBonus();
        rating += getTotalCriticalBonus();
        rating += getTotalRangeBonus();
        rating += getTotalPenetrationBonus();
        
        if (isAdvanced) rating += 10;
        if (isExperimental) rating += 20;
        if (isAlien) rating += 30;
        if (isNanotech) rating += 25;
        if (isPsionic) rating += 35;
        if (isRobotic) rating += 15;
        if (isHybrid) rating += 40;
        
        return rating;
    }
    
    /**
     * Get ammo description
     */
    public String getDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(name).append(" (").append(type).append(")\n");
        desc.append("Uses: ").append(currentUses).append("/").append(maxUses).append("\n");
        desc.append("Cost: ").append(cost).append(" credits\n");
        desc.append("Rarity: ").append(rarity).append("/5\n");
        
        if (damageBonus > 0) desc.append("Damage: +").append(damageBonus).append("\n");
        if (accuracyBonus > 0) desc.append("Accuracy: +").append(accuracyBonus).append("\n");
        if (criticalBonus > 0) desc.append("Critical: +").append(criticalBonus).append("\n");
        if (rangeBonus > 0) desc.append("Range: +").append(rangeBonus).append("\n");
        if (penetrationBonus > 0) desc.append("Penetration: +").append(penetrationBonus).append("\n");
        if (armorPiercingBonus > 0) desc.append("Armor Piercing: +").append(armorPiercingBonus).append("\n");
        if (explosiveBonus > 0) desc.append("Explosive: +").append(explosiveBonus).append("\n");
        if (incendiaryBonus > 0) desc.append("Incendiary: +").append(incendiaryBonus).append("\n");
        if (acidBonus > 0) desc.append("Acid: +").append(acidBonus).append("\n");
        if (electricBonus > 0) desc.append("Electric: +").append(electricBonus).append("\n");
        if (radiationBonus > 0) desc.append("Radiation: +").append(radiationBonus).append("\n");
        if (frostBonus > 0) desc.append("Frost: +").append(frostBonus).append("\n");
        if (corrosionBonus > 0) desc.append("Corrosion: +").append(corrosionBonus).append("\n");
        if (bluescreenBonus > 0) desc.append("Bluescreen: +").append(bluescreenBonus).append("\n");
        if (psionicBonus > 0) desc.append("Psionic: +").append(psionicBonus).append("\n");
        if (hackingBonus > 0) desc.append("Hacking: +").append(hackingBonus).append("\n");
        if (roboticBonus > 0) desc.append("Robotic: +").append(roboticBonus).append("\n");
        if (alienBonus > 0) desc.append("Alien: +").append(alienBonus).append("\n");
        if (humanBonus > 0) desc.append("Human: +").append(humanBonus).append("\n");
        if (hybridBonus > 0) desc.append("Hybrid: +").append(hybridBonus).append("\n");
        
        if (isAdvanced) desc.append("Advanced\n");
        if (isExperimental) desc.append("Experimental\n");
        if (isAlien) desc.append("Alien\n");
        if (isNanotech) desc.append("Nanotech\n");
        if (isPsionic) desc.append("Psionic\n");
        if (isRobotic) desc.append("Robotic\n");
        if (isHybrid) desc.append("Hybrid\n");
        
        return desc.toString();
    }
} 
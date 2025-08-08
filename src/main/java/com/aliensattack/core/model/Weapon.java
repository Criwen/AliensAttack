package com.aliensattack.core.model;

import com.aliensattack.core.enums.WeaponType;
import com.aliensattack.core.enums.AmmoType;
import com.aliensattack.core.enums.StatusEffect;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a weapon with specific characteristics
 */
@Getter
@Setter
public class Weapon {
    private String name;
    private WeaponType type;
    private int baseDamage;
    private int criticalDamage;
    private int criticalChance; // Percentage
    private int accuracy; // Base accuracy percentage
    private int range;
    private boolean isAreaOfEffect;
    private int areaRadius;
    private int ammoCapacity;
    private int currentAmmo;
    private AmmoType ammoType;
    
    public Weapon(String name, WeaponType type, int baseDamage, int criticalDamage, 
                 int criticalChance, int accuracy, int range) {
        this.name = name;
        this.type = type;
        this.baseDamage = baseDamage;
        this.criticalDamage = criticalDamage;
        this.criticalChance = criticalChance;
        this.accuracy = accuracy;
        this.range = range;
        this.isAreaOfEffect = false;
        this.areaRadius = 0;
        this.ammoCapacity = 100;
        this.currentAmmo = ammoCapacity;
        this.ammoType = AmmoType.STANDARD;
    }
    
    public Weapon(String name, WeaponType type, int baseDamage, int criticalDamage, 
                 int criticalChance, int accuracy, int range, boolean isAreaOfEffect, int areaRadius) {
        this(name, type, baseDamage, criticalDamage, criticalChance, accuracy, range);
        this.isAreaOfEffect = isAreaOfEffect;
        this.areaRadius = areaRadius;
    }
    
    public boolean hasAmmo() {
        return currentAmmo > 0;
    }
    
    public void useAmmo() {
        if (currentAmmo > 0) {
            currentAmmo--;
        }
    }
    
    public void reload() {
        currentAmmo = ammoCapacity;
    }
    
    public double getAmmoPercentage() {
        return (double) currentAmmo / ammoCapacity;
    }
    
    public boolean isAreaOfEffect() {
        return isAreaOfEffect;
    }
    
    /**
     * Check if weapon is a grenade launcher
     */
    public boolean isGrenadeLauncher() {
        return type == WeaponType.GRENADE_LAUNCHER;
    }
    
    /**
     * Get grenade launcher range bonus
     */
    public int getGrenadeLauncherRangeBonus() {
        if (isGrenadeLauncher()) {
            return 8; // +8 range for grenade launcher
        }
        return 0;
    }
    
    /**
     * Get grenade launcher area radius
     */
    public int getGrenadeLauncherAreaRadius() {
        if (isGrenadeLauncher()) {
            return 3; // 3 tile radius for grenade launcher
        }
        return 0;
    }
    
    /**
     * Get total range including grenade launcher bonus
     */
    public int getTotalRange() {
        return range + getGrenadeLauncherRangeBonus();
    }
    
    /**
     * Get ammo type damage bonus
     */
    public int getAmmoTypeDamageBonus() {
        switch (ammoType) {
            case ARMOR_PIERCING:
                return 5; // +5 damage vs armor
            case INCENDIARY:
                return 3; // +3 fire damage
            case ACID:
                return 4; // +4 acid damage
            case EXPLOSIVE:
                return 8; // +8 explosive damage
            case PLASMA:
                return 6; // +6 plasma damage
            case LASER:
                return 2; // +2 laser damage
            default:
                return 0;
        }
    }
    
    /**
     * Get ammo type accuracy bonus
     */
    public int getAmmoTypeAccuracyBonus() {
        switch (ammoType) {
            case LASER:
                return 10; // +10% accuracy
            case ARMOR_PIERCING:
                return 5; // +5% accuracy
            default:
                return 0;
        }
    }
    
    /**
     * Get ammo type status effect
     */
    public StatusEffect getAmmoTypeStatusEffect() {
        switch (ammoType) {
            case INCENDIARY:
                return StatusEffect.BURNING;
            case ACID:
                return StatusEffect.ACID_BURN;
            case STUN:
                return StatusEffect.STUNNED;
            case POISON:
                return StatusEffect.POISONED;
            default:
                return StatusEffect.NONE;
        }
    }
    
    /**
     * Get total damage including ammo type bonus
     */
    public int getTotalDamage() {
        return baseDamage + getAmmoTypeDamageBonus();
    }
    
    /**
     * Get total accuracy including ammo type bonus
     */
    public int getTotalAccuracy() {
        return accuracy + getAmmoTypeAccuracyBonus();
    }
    
    // Additional getter methods for compatibility
    public int getAccuracy() {
        return accuracy;
    }
    
    public int getCriticalChance() {
        return criticalChance;
    }
    
    public int getAreaRadius() {
        return areaRadius;
    }
    
    // Additional setter methods for compatibility
    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
    
    /**
     * Check if weapon is Bluescreen
     */
    public boolean isBluescreen() {
        return type == WeaponType.BLUESCREEN;
    }
    
    /**
     * Get Bluescreen damage bonus vs robotic units
     */
    public int getBluescreenDamageBonus() {
        if (isBluescreen()) {
            return 15; // +15 damage vs robotic units
        }
        return 0;
    }
    
    /**
     * Get Bluescreen accuracy bonus vs robotic units
     */
    public int getBluescreenAccuracyBonus() {
        if (isBluescreen()) {
            return 20; // +20% accuracy vs robotic units
        }
        return 0;
    }
    
    /**
     * Get total Bluescreen damage
     */
    public int getTotalBluescreenDamage() {
        return baseDamage + getBluescreenDamageBonus();
    }
    
    /**
     * Get total Bluescreen accuracy
     */
    public int getTotalBluescreenAccuracy() {
        return accuracy + getBluescreenAccuracyBonus();
    }
    
    /**
     * Check if weapon can use Rapid Fire
     */
    public boolean canUseRapidFire() {
        return type != WeaponType.MELEE_WEAPON && type != WeaponType.GRENADE_LAUNCHER;
    }
    
    /**
     * Get Rapid Fire shot count
     */
    public int getRapidFireShotCount() {
        if (canUseRapidFire()) {
            return 2; // Two shots for rapid fire
        }
        return 1;
    }
    
    /**
     * Get Rapid Fire accuracy penalty
     */
    public int getRapidFireAccuracyPenalty() {
        if (canUseRapidFire()) {
            return 15; // -15% accuracy for rapid fire
        }
        return 0;
    }
    
    /**
     * Get Rapid Fire total accuracy
     */
    public int getRapidFireAccuracy() {
        return accuracy - getRapidFireAccuracyPenalty();
    }
    
    /**
     * Get Rapid Fire action point cost
     */
    public int getRapidFireActionPointCost() {
        if (canUseRapidFire()) {
            return 2; // Costs 2 action points
        }
        return 1;
    }
    
    /**
     * Get action point cost for this weapon
     * Pistols cost 0.5 AP, other weapons cost ALL AP
     */
    public double getActionPointCost() {
        if (type == WeaponType.PISTOL) {
            return 0.5; // Pistols cost half an action point
        }
        return Double.MAX_VALUE; // Other weapons cost ALL action points
    }
    
    /**
     * Check if this weapon is a pistol
     */
    public boolean isPistol() {
        return type == WeaponType.PISTOL;
    }
    
    /**
     * Check if this weapon is alien/plasma weapon
     */
    public boolean isAlienWeapon() {
        return type == WeaponType.PLASMA_WEAPON || type == WeaponType.PLASMA_RIFLE || 
               type == WeaponType.LASER_WEAPON || type == WeaponType.LASER_RIFLE;
    }
    
    /**
     * Check if this weapon is plasma weapon
     */
    public boolean isPlasmaWeapon() {
        return type == WeaponType.PLASMA_WEAPON || type == WeaponType.PLASMA_RIFLE;
    }
    
    /**
     * Check if this weapon is laser weapon
     */
    public boolean isLaserWeapon() {
        return type == WeaponType.LASER_WEAPON || type == WeaponType.LASER_RIFLE;
    }
    
    /**
     * Get alien weapon damage bonus
     */
    public int getAlienWeaponDamageBonus() {
        if (isPlasmaWeapon()) {
            return 8; // +8 damage for plasma weapons
        } else if (isLaserWeapon()) {
            return 5; // +5 damage for laser weapons
        }
        return 0;
    }
    
    /**
     * Get alien weapon accuracy bonus
     */
    public int getAlienWeaponAccuracyBonus() {
        if (isLaserWeapon()) {
            return 10; // +10% accuracy for laser weapons
        }
        return 0;
    }
    
    /**
     * Get total alien weapon damage
     */
    public int getTotalAlienWeaponDamage() {
        return baseDamage + getAlienWeaponDamageBonus();
    }
    
    /**
     * Get total alien weapon accuracy
     */
    public int getTotalAlienWeaponAccuracy() {
        return accuracy + getAlienWeaponAccuracyBonus();
    }
    
    // Additional method for compatibility
    public String getWeaponType() {
        return type.name();
    }
} 
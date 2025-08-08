package com.aliensattack.core.model;

import com.aliensattack.core.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * WeaponAttachment class representing weapon modifications in XCOM 2
 */
@Getter
@Setter
public class WeaponAttachment {
    
    private String attachmentId;
    private String attachmentName;
    private WeaponAttachmentType attachmentType;
    private Weapon weapon;
    private boolean isInstalled;
    private boolean isActive;
    private int accuracyBonus;
    private int damageBonus;
    private int criticalChanceBonus;
    private int criticalDamageBonus;
    private int rangeBonus;
    private int ammoCapacityBonus;
    private int reloadSpeedBonus;
    private int fireRateBonus;
    private int recoilReduction;
    private int noiseReduction;
    private int heatReduction;
    private int coolingBonus;
    private int durabilityBonus;
    private int maintenanceCost;
    private int installationCost;
    private int removalCost;
    private boolean isRemovable;
    private boolean isUpgradeable;
    private int upgradeLevel;
    private int maxUpgradeLevel;
    private List<String> compatibleWeaponTypes;
    private Map<String, Integer> attachmentEffects;
    private List<StatusEffect> statusEffects;
    private boolean isExperimental;
    private boolean isRare;
    private boolean isLegendary;
    private boolean isUnique;
    private String description;
    private String manufacturer;
    private int marketValue;
    private boolean isTradeable;
    private boolean isCraftable;
    private List<String> requiredMaterials;
    private int craftingCost;
    private int craftingTime;
    
    public WeaponAttachment(String attachmentId, String attachmentName, WeaponAttachmentType attachmentType) {
        this.attachmentId = attachmentId;
        this.attachmentName = attachmentName;
        this.attachmentType = attachmentType;
        this.weapon = null;
        this.isInstalled = false;
        this.isActive = false;
        this.accuracyBonus = 0;
        this.damageBonus = 0;
        this.criticalChanceBonus = 0;
        this.criticalDamageBonus = 0;
        this.rangeBonus = 0;
        this.ammoCapacityBonus = 0;
        this.reloadSpeedBonus = 0;
        this.fireRateBonus = 0;
        this.recoilReduction = 0;
        this.noiseReduction = 0;
        this.heatReduction = 0;
        this.coolingBonus = 0;
        this.durabilityBonus = 0;
        this.maintenanceCost = 0;
        this.installationCost = 0;
        this.removalCost = 0;
        this.isRemovable = true;
        this.isUpgradeable = false;
        this.upgradeLevel = 1;
        this.maxUpgradeLevel = 1;
        this.compatibleWeaponTypes = new ArrayList<>();
        this.attachmentEffects = new HashMap<>();
        this.statusEffects = new ArrayList<>();
        this.isExperimental = false;
        this.isRare = false;
        this.isLegendary = false;
        this.isUnique = false;
        this.description = "";
        this.manufacturer = "";
        this.marketValue = 0;
        this.isTradeable = true;
        this.isCraftable = false;
        this.requiredMaterials = new ArrayList<>();
        this.craftingCost = 0;
        this.craftingTime = 0;
        
        initializeAttachmentProperties();
    }
    
    /**
     * Initialize attachment properties based on type
     */
    private void initializeAttachmentProperties() {
        switch (attachmentType) {
            case SCOPE:
                accuracyBonus = 15;
                rangeBonus = 2;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "SNIPER", "ASSAULT_RIFLE"));
                description = "Improves accuracy and range";
                break;
            case EXTENDED_MAGAZINE:
                ammoCapacityBonus = 5;
                reloadSpeedBonus = -1;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Increases ammo capacity";
                break;
            case AUTO_LOADER:
                reloadSpeedBonus = 2;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Automatic reloading system";
                break;
            case REPEATER:
                criticalChanceBonus = 10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Chance for free shots";
                break;
            case HAIR_TRIGGER:
                fireRateBonus = 20;
                accuracyBonus = 5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Quick reaction shots";
                break;
            case STOCK:
                recoilReduction = 15;
                accuracyBonus = 5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Reduces recoil";
                break;
            case LASER_SIGHT:
                accuracyBonus = 10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Improves accuracy";
                break;
            case SUPPRESSOR:
                noiseReduction = 50;
                damageBonus = -2;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG"));
                description = "Reduces noise";
                break;
            case BAYONET:
                damageBonus = 3;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE"));
                description = "Melee attack capability";
                break;
            case GRENADE_LAUNCHER:
                damageBonus = 10;
                rangeBonus = 3;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE"));
                description = "Secondary grenade launcher";
                break;
            case UNDERBARREL_SHOTGUN:
                damageBonus = 8;
                rangeBonus = 1;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE"));
                description = "Secondary shotgun";
                break;
            case UNDERBARREL_RAILGUN:
                damageBonus = 15;
                rangeBonus = 4;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE"));
                description = "Secondary railgun";
                break;
            case HEAT_SINK:
                heatReduction = 20;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Reduces heat buildup";
                break;
            case COOLING_SYSTEM:
                coolingBonus = 25;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Improves cooling";
                break;
            case OVERCLOCK:
                damageBonus = 5;
                heatReduction = -10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Increases damage but adds heat";
                break;
            case TURBO_CHARGE:
                fireRateBonus = 30;
                heatReduction = -15;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Increases fire rate";
                break;
            case PRECISION_SIGHT:
                criticalChanceBonus = 15;
                criticalDamageBonus = 25;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Critical hit bonus";
                break;
            case TACTICAL_SIGHT:
                accuracyBonus = 10;
                rangeBonus = 1;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Tactical advantages";
                break;
            case REFLEX_SIGHT:
                accuracyBonus = 8;
                fireRateBonus = 10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Quick aiming";
                break;
            case HOLOGRAPHIC_SIGHT:
                accuracyBonus = 12;
                rangeBonus = 2;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Advanced targeting";
                break;
            case THERMAL_SIGHT:
                accuracyBonus = 15;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Thermal vision";
                break;
            case NIGHT_VISION:
                accuracyBonus = 10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Night vision capability";
                break;
            case INFRA_RED_SIGHT:
                accuracyBonus = 12;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Infrared targeting";
                break;
            case X_RAY_SIGHT:
                accuracyBonus = 20;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "X-ray vision";
                break;
            case SONAR_SIGHT:
                accuracyBonus = 15;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Sonar targeting";
                break;
            case RADAR_SIGHT:
                accuracyBonus = 18;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Radar targeting";
                break;
            case QUANTUM_SIGHT:
                accuracyBonus = 25;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Quantum targeting";
                break;
            case PSYCHIC_SIGHT:
                accuracyBonus = 30;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Psychic targeting";
                break;
            case DIMENSIONAL_SIGHT:
                accuracyBonus = 35;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Dimensional targeting";
                break;
            case TEMPORAL_SIGHT:
                accuracyBonus = 40;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Temporal targeting";
                break;
            case GRAVITATIONAL_SIGHT:
                accuracyBonus = 22;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Gravitational targeting";
                break;
            case MAGNETIC_SIGHT:
                accuracyBonus = 20;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Magnetic targeting";
                break;
            case ELECTROMAGNETIC_SIGHT:
                accuracyBonus = 24;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Electromagnetic targeting";
                break;
            case NUCLEAR_SIGHT:
                accuracyBonus = 28;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Nuclear targeting";
                break;
            case BIOLOGICAL_SIGHT:
                accuracyBonus = 26;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Biological targeting";
                break;
            case CHEMICAL_SIGHT:
                accuracyBonus = 23;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Chemical targeting";
                break;
            case TOXIC_SIGHT:
                accuracyBonus = 25;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Toxic targeting";
                break;
            case HAZARDOUS_SIGHT:
                accuracyBonus = 27;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Hazardous targeting";
                break;
            case SAFE_SIGHT:
                accuracyBonus = 10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Safe targeting";
                break;
            case NEUTRAL_SIGHT:
                accuracyBonus = 12;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Neutral targeting";
                break;
            case HOSTILE_SIGHT:
                accuracyBonus = 18;
                compatibleWeaponTypes.addAll(Arrays.asList("SNIPER", "RIFLE"));
                description = "Hostile targeting";
                break;
            case FRIENDLY_SIGHT:
                accuracyBonus = 8;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Friendly targeting";
                break;
            case UNKNOWN_SIGHT:
                accuracyBonus = 15;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Unknown targeting";
                break;
            case BARREL_EXTENSION:
                rangeBonus = 3;
                accuracyBonus = 5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Increases range";
                break;
            case COMPENSATOR:
                recoilReduction = 20;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Reduces spread";
                break;
            case MUZZLE_BRAKE:
                recoilReduction = 25;
                noiseReduction = -10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Reduces recoil";
                break;
            case FLASH_HIDER:
                noiseReduction = 10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Hides muzzle flash";
                break;
            case MUZZLE_BOOSTER:
                damageBonus = 3;
                rangeBonus = 1;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Increases velocity";
                break;
            case MUZZLE_BREAK:
                recoilReduction = 30;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Reduces recoil";
                break;
            case MUZZLE_WEIGHT:
                accuracyBonus = 8;
                fireRateBonus = -5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Stabilizes barrel";
                break;
            case MUZZLE_COOLER:
                heatReduction = 15;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Cools barrel";
                break;
            case MUZZLE_HEATER:
                damageBonus = 2;
                heatReduction = -5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Heats barrel";
                break;
            case MUZZLE_AMPLIFIER:
                noiseReduction = -20;
                damageBonus = 1;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Amplifies sound";
                break;
            case MUZZLE_SILENCER:
                noiseReduction = 60;
                damageBonus = -3;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG"));
                description = "Silences weapon";
                break;
            case MUZZLE_FLASH:
                damageBonus = 1;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Creates flash";
                break;
            case MUZZLE_SMOKE:
                accuracyBonus = 5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Creates smoke";
                break;
            case MUZZLE_FIRE:
                damageBonus = 2;
                heatReduction = -10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates fire";
                break;
            case MUZZLE_ICE:
                damageBonus = 1;
                heatReduction = 5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates ice";
                break;
            case MUZZLE_ELECTRIC:
                damageBonus = 3;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates electricity";
                break;
            case MUZZLE_POISON:
                damageBonus = 2;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Creates poison";
                break;
            case MUZZLE_ACID:
                damageBonus = 4;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates acid";
                break;
            case MUZZLE_PLASMA:
                damageBonus = 5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates plasma";
                break;
            case MUZZLE_VOID:
                damageBonus = 6;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates void";
                break;
            case MUZZLE_DIMENSIONAL:
                damageBonus = 7;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates dimensional effects";
                break;
            case MUZZLE_TEMPORAL:
                damageBonus = 8;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates temporal effects";
                break;
            case MUZZLE_QUANTUM:
                damageBonus = 9;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates quantum effects";
                break;
            case MUZZLE_GRAVITATIONAL:
                damageBonus = 4;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates gravitational effects";
                break;
            case MUZZLE_MAGNETIC:
                damageBonus = 3;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates magnetic effects";
                break;
            case MUZZLE_ELECTROMAGNETIC:
                damageBonus = 5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates electromagnetic effects";
                break;
            case MUZZLE_NUCLEAR:
                damageBonus = 10;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates nuclear effects";
                break;
            case MUZZLE_BIOLOGICAL:
                damageBonus = 3;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates biological effects";
                break;
            case MUZZLE_CHEMICAL:
                damageBonus = 4;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates chemical effects";
                break;
            case MUZZLE_TOXIC:
                damageBonus = 4;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates toxic effects";
                break;
            case MUZZLE_HAZARDOUS:
                damageBonus = 5;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates hazardous effects";
                break;
            case MUZZLE_SAFE:
                damageBonus = 1;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Creates safe effects";
                break;
            case MUZZLE_NEUTRAL:
                damageBonus = 1;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Creates neutral effects";
                break;
            case MUZZLE_HOSTILE:
                damageBonus = 3;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "ASSAULT_RIFLE", "SMG"));
                description = "Creates hostile effects";
                break;
            case MUZZLE_FRIENDLY:
                damageBonus = 1;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Creates friendly effects";
                break;
            case MUZZLE_UNKNOWN:
                damageBonus = 2;
                compatibleWeaponTypes.addAll(Arrays.asList("RIFLE", "PISTOL", "SMG", "ASSAULT_RIFLE"));
                description = "Creates unknown effects";
                break;
        }
        
        // Initialize attachment effects
        attachmentEffects.put("accuracy_bonus", accuracyBonus);
        attachmentEffects.put("damage_bonus", damageBonus);
        attachmentEffects.put("critical_chance_bonus", criticalChanceBonus);
        attachmentEffects.put("critical_damage_bonus", criticalDamageBonus);
        attachmentEffects.put("range_bonus", rangeBonus);
        attachmentEffects.put("ammo_capacity_bonus", ammoCapacityBonus);
        attachmentEffects.put("reload_speed_bonus", reloadSpeedBonus);
        attachmentEffects.put("fire_rate_bonus", fireRateBonus);
        attachmentEffects.put("recoil_reduction", recoilReduction);
        attachmentEffects.put("noise_reduction", noiseReduction);
        attachmentEffects.put("heat_reduction", heatReduction);
        attachmentEffects.put("cooling_bonus", coolingBonus);
        attachmentEffects.put("durability_bonus", durabilityBonus);
    }
    
    /**
     * Install attachment on weapon
     */
    public boolean install(Weapon targetWeapon) {
        if (isInstalled || targetWeapon == null) {
            return false;
        }
        
        // Check compatibility
        if (!isCompatibleWith(targetWeapon)) {
            return false;
        }
        
        // Check if weapon already has this type of attachment
        // Note: This would need to be implemented in the Weapon class
        // For now, we'll assume it's compatible
        
        weapon = targetWeapon;
        isInstalled = true;
        isActive = true;
        
        // Apply attachment effects to weapon
        applyEffectsToWeapon();
        
        return true;
    }
    
    /**
     * Remove attachment from weapon
     */
    public boolean remove() {
        if (!isInstalled || weapon == null) {
            return false;
        }
        
        // Remove attachment effects from weapon
        removeEffectsFromWeapon();
        
        weapon = null;
        isInstalled = false;
        isActive = false;
        
        return true;
    }
    
    /**
     * Check if attachment is compatible with weapon
     */
    public boolean isCompatibleWith(Weapon targetWeapon) {
        if (targetWeapon == null) {
            return false;
        }
        
        String weaponType = targetWeapon.getType().name();
        return compatibleWeaponTypes.contains(weaponType);
    }
    
    /**
     * Apply attachment effects to weapon
     */
    private void applyEffectsToWeapon() {
        if (weapon == null) {
            return;
        }
        
        // Apply accuracy bonus
        weapon.setAccuracy(weapon.getAccuracy() + accuracyBonus);
        
        // Apply damage bonus
        weapon.setBaseDamage(weapon.getBaseDamage() + damageBonus);
        
        // Apply critical chance bonus
        weapon.setCriticalChance(weapon.getCriticalChance() + criticalChanceBonus);
        
        // Apply critical damage bonus
        weapon.setCriticalDamage(weapon.getCriticalDamage() + criticalDamageBonus);
        
        // Apply range bonus
        weapon.setRange(weapon.getRange() + rangeBonus);
        
        // Apply ammo capacity bonus
        weapon.setAmmoCapacity(weapon.getAmmoCapacity() + ammoCapacityBonus);
        
        // Apply reload speed bonus
        // Note: reloadSpeed would need to be added to Weapon class
        
        // Apply fire rate bonus
        // Note: fireRate would need to be added to Weapon class
        
        // Apply recoil reduction
        // Note: recoil would need to be added to Weapon class
        
        // Apply noise reduction
        // Note: noiseLevel would need to be added to Weapon class
        
        // Apply heat reduction
        // Note: heatGeneration would need to be added to Weapon class
        
        // Apply cooling bonus
        // Note: coolingRate would need to be added to Weapon class
        
        // Apply durability bonus
        // Note: durability would need to be added to Weapon class
    }
    
    /**
     * Remove attachment effects from weapon
     */
    private void removeEffectsFromWeapon() {
        if (weapon == null) {
            return;
        }
        
        // Remove accuracy bonus
        weapon.setAccuracy(weapon.getAccuracy() - accuracyBonus);
        
        // Remove damage bonus
        weapon.setBaseDamage(weapon.getBaseDamage() - damageBonus);
        
        // Remove critical chance bonus
        weapon.setCriticalChance(weapon.getCriticalChance() - criticalChanceBonus);
        
        // Remove critical damage bonus
        weapon.setCriticalDamage(weapon.getCriticalDamage() - criticalDamageBonus);
        
        // Remove range bonus
        weapon.setRange(weapon.getRange() - rangeBonus);
        
        // Remove ammo capacity bonus
        weapon.setAmmoCapacity(weapon.getAmmoCapacity() - ammoCapacityBonus);
        
        // Remove reload speed bonus
        // Note: reloadSpeed would need to be added to Weapon class
        
        // Remove fire rate bonus
        // Note: fireRate would need to be added to Weapon class
        
        // Remove recoil reduction
        // Note: recoil would need to be added to Weapon class
        
        // Remove noise reduction
        // Note: noiseLevel would need to be added to Weapon class
        
        // Remove heat reduction
        // Note: heatGeneration would need to be added to Weapon class
        
        // Remove cooling bonus
        // Note: coolingRate would need to be added to Weapon class
        
        // Remove durability bonus
        // Note: durability would need to be added to Weapon class
    }
    
    /**
     * Upgrade attachment
     */
    public boolean upgrade() {
        if (!isUpgradeable || upgradeLevel >= maxUpgradeLevel) {
            return false;
        }
        
        upgradeLevel++;
        
        // Increase effects based on upgrade level
        accuracyBonus += 2;
        damageBonus += 1;
        criticalChanceBonus += 1;
        criticalDamageBonus += 5;
        rangeBonus += 1;
        ammoCapacityBonus += 1;
        reloadSpeedBonus += 1;
        fireRateBonus += 2;
        recoilReduction += 2;
        noiseReduction += 2;
        heatReduction += 2;
        coolingBonus += 2;
        durabilityBonus += 5;
        
        // Update attachment effects
        updateAttachmentEffects();
        
        // Reapply effects to weapon if installed
        if (isInstalled && weapon != null) {
            applyEffectsToWeapon();
        }
        
        return true;
    }
    
    /**
     * Update attachment effects
     */
    private void updateAttachmentEffects() {
        attachmentEffects.put("accuracy_bonus", accuracyBonus);
        attachmentEffects.put("damage_bonus", damageBonus);
        attachmentEffects.put("critical_chance_bonus", criticalChanceBonus);
        attachmentEffects.put("critical_damage_bonus", criticalDamageBonus);
        attachmentEffects.put("range_bonus", rangeBonus);
        attachmentEffects.put("ammo_capacity_bonus", ammoCapacityBonus);
        attachmentEffects.put("reload_speed_bonus", reloadSpeedBonus);
        attachmentEffects.put("fire_rate_bonus", fireRateBonus);
        attachmentEffects.put("recoil_reduction", recoilReduction);
        attachmentEffects.put("noise_reduction", noiseReduction);
        attachmentEffects.put("heat_reduction", heatReduction);
        attachmentEffects.put("cooling_bonus", coolingBonus);
        attachmentEffects.put("durability_bonus", durabilityBonus);
    }
    
    /**
     * Get attachment status
     */
    public String getAttachmentStatus() {
        return String.format("Attachment: %s, Type: %s, Installed: %s, Active: %s, Level: %d/%d", 
                           attachmentName, attachmentType, isInstalled, isActive, upgradeLevel, maxUpgradeLevel);
    }
    
    /**
     * Get attachment description
     */
    public String getAttachmentDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append(attachmentName).append(" (").append(attachmentType).append(")");
        
        if (isInstalled) desc.append(" [Installed]");
        if (isActive) desc.append(" [Active]");
        if (isUpgradeable) desc.append(" [Upgradeable]");
        if (isExperimental) desc.append(" [Experimental]");
        if (isRare) desc.append(" [Rare]");
        if (isLegendary) desc.append(" [Legendary]");
        if (isUnique) desc.append(" [Unique]");
        if (isCraftable) desc.append(" [Craftable]");
        if (isTradeable) desc.append(" [Tradeable]");
        
        desc.append("\n").append(description);
        
        return desc.toString();
    }
    
    /**
     * Get attachment effects summary
     */
    public String getEffectsSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Effects:\n");
        
        if (accuracyBonus != 0) summary.append("Accuracy: +").append(accuracyBonus).append("\n");
        if (damageBonus != 0) summary.append("Damage: +").append(damageBonus).append("\n");
        if (criticalChanceBonus != 0) summary.append("Critical Chance: +").append(criticalChanceBonus).append("%\n");
        if (criticalDamageBonus != 0) summary.append("Critical Damage: +").append(criticalDamageBonus).append("%\n");
        if (rangeBonus != 0) summary.append("Range: +").append(rangeBonus).append("\n");
        if (ammoCapacityBonus != 0) summary.append("Ammo Capacity: +").append(ammoCapacityBonus).append("\n");
        if (reloadSpeedBonus != 0) summary.append("Reload Speed: +").append(reloadSpeedBonus).append("\n");
        if (fireRateBonus != 0) summary.append("Fire Rate: +").append(fireRateBonus).append("%\n");
        if (recoilReduction != 0) summary.append("Recoil: -").append(recoilReduction).append("%\n");
        if (noiseReduction != 0) summary.append("Noise: -").append(noiseReduction).append("%\n");
        if (heatReduction != 0) summary.append("Heat: -").append(heatReduction).append("%\n");
        if (coolingBonus != 0) summary.append("Cooling: +").append(coolingBonus).append("%\n");
        if (durabilityBonus != 0) summary.append("Durability: +").append(durabilityBonus).append("\n");
        
        return summary.toString();
    }
}

package com.aliensattack.core.data;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Weapon Specialization System for weapon mastery.
 * Implements weapon proficiency, specialization bonuses, and mastery mechanics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeaponSpecialization {
    
    private String weaponId;
    private String weaponName;
    private WeaponType weaponType;
    private List<WeaponAttachment> attachments;
    private List<WeaponModification> modifications;
    private int weaponLevel;
    private int experiencePoints;
    private int maxExperiencePoints;
    private int durability;
    private int maxDurability;
    private boolean needsMaintenance;
    private Map<String, Integer> weaponStats;
    private List<String> unlockedAbilities;
    
    public WeaponSpecialization(String weaponId, String weaponName, WeaponType weaponType) {
        this.weaponId = weaponId;
        this.weaponName = weaponName;
        this.weaponType = weaponType;
        this.attachments = new ArrayList<>();
        this.modifications = new ArrayList<>();
        this.weaponLevel = 1;
        this.experiencePoints = 0;
        this.maxExperiencePoints = 100;
        this.durability = 100;
        this.maxDurability = 100;
        this.needsMaintenance = false;
        this.weaponStats = new HashMap<>();
        this.unlockedAbilities = new ArrayList<>();
        
        initializeWeaponStats();
    }
    
    /**
     * Initialize weapon stats based on type
     */
    private void initializeWeaponStats() {
        switch (weaponType) {
            case ASSAULT_RIFLE:
                weaponStats.put("damage", 25);
                weaponStats.put("accuracy", 75);
                weaponStats.put("range", 15);
                weaponStats.put("fire_rate", 3);
                break;
            case SHOTGUN:
                weaponStats.put("damage", 35);
                weaponStats.put("accuracy", 60);
                weaponStats.put("range", 8);
                weaponStats.put("fire_rate", 1);
                break;
            case SNIPER_RIFLE:
                weaponStats.put("damage", 45);
                weaponStats.put("accuracy", 90);
                weaponStats.put("range", 25);
                weaponStats.put("fire_rate", 1);
                break;
            case PISTOL:
                weaponStats.put("damage", 15);
                weaponStats.put("accuracy", 70);
                weaponStats.put("range", 10);
                weaponStats.put("fire_rate", 2);
                break;
            case HEAVY_WEAPON:
                weaponStats.put("damage", 50);
                weaponStats.put("accuracy", 65);
                weaponStats.put("range", 12);
                weaponStats.put("fire_rate", 1);
                break;
        }
    }
    
    /**
     * Add weapon attachment
     */
    public boolean addAttachment(WeaponAttachment attachment) {
        if (attachments.size() >= getMaxAttachments()) {
            return false; // Max attachments reached
        }
        
        if (isAttachmentCompatible(attachment)) {
            attachments.add(attachment);
            applyAttachmentEffects(attachment);
            return true;
        }
        
        return false;
    }
    
    /**
     * Remove weapon attachment
     */
    public boolean removeAttachment(String attachmentName) {
        return attachments.removeIf(attachment -> {
            if (attachment.getName().equals(attachmentName)) {
                removeAttachmentEffects(attachment);
                return true;
            }
            return false;
        });
    }
    
    /**
     * Check if attachment is compatible with weapon
     */
    private boolean isAttachmentCompatible(WeaponAttachment attachment) {
        return attachment.getCompatibleWeaponTypes().contains(weaponType);
    }
    
    /**
     * Apply attachment effects to weapon stats
     */
    private void applyAttachmentEffects(WeaponAttachment attachment) {
        for (Map.Entry<String, Integer> effect : attachment.getEffects().entrySet()) {
            String stat = effect.getKey();
            int bonus = effect.getValue();
            int currentValue = weaponStats.getOrDefault(stat, 0);
            weaponStats.put(stat, currentValue + bonus);
        }
    }
    
    /**
     * Remove attachment effects from weapon stats
     */
    private void removeAttachmentEffects(WeaponAttachment attachment) {
        for (Map.Entry<String, Integer> effect : attachment.getEffects().entrySet()) {
            String stat = effect.getKey();
            int penalty = effect.getValue();
            int currentValue = weaponStats.getOrDefault(stat, 0);
            weaponStats.put(stat, Math.max(0, currentValue - penalty));
        }
    }
    
    /**
     * Get maximum attachments for weapon type
     */
    private int getMaxAttachments() {
        switch (weaponType) {
            case ASSAULT_RIFLE:
                return 4;
            case SHOTGUN:
                return 3;
            case SNIPER_RIFLE:
                return 5;
            case PISTOL:
                return 2;
            case HEAVY_WEAPON:
                return 3;
            default:
                return 2;
        }
    }
    
    /**
     * Add weapon modification
     */
    public boolean addModification(WeaponModification modification) {
        if (modifications.size() >= getMaxModifications()) {
            return false; // Max modifications reached
        }
        
        modifications.add(modification);
        applyModificationEffects(modification);
        return true;
    }
    
    /**
     * Remove weapon modification
     */
    public boolean removeModification(String modificationName) {
        return modifications.removeIf(modification -> {
            if (modification.getName().equals(modificationName)) {
                removeModificationEffects(modification);
                return true;
            }
            return false;
        });
    }
    
    /**
     * Apply modification effects
     */
    private void applyModificationEffects(WeaponModification modification) {
        for (Map.Entry<String, Integer> effect : modification.getEffects().entrySet()) {
            String stat = effect.getKey();
            int bonus = effect.getValue();
            int currentValue = weaponStats.getOrDefault(stat, 0);
            weaponStats.put(stat, currentValue + bonus);
        }
    }
    
    /**
     * Remove modification effects
     */
    private void removeModificationEffects(WeaponModification modification) {
        for (Map.Entry<String, Integer> effect : modification.getEffects().entrySet()) {
            String stat = effect.getKey();
            int penalty = effect.getValue();
            int currentValue = weaponStats.getOrDefault(stat, 0);
            weaponStats.put(stat, Math.max(0, currentValue - penalty));
        }
    }
    
    /**
     * Get maximum modifications for weapon type
     */
    private int getMaxModifications() {
        return 3; // Standard max modifications
    }
    
    /**
     * Add experience points to weapon
     */
    public void addExperience(int points) {
        experiencePoints += points;
        
        // Check for level up
        while (experiencePoints >= maxExperiencePoints) {
            levelUp();
        }
    }
    
    /**
     * Level up weapon
     */
    private void levelUp() {
        weaponLevel++;
        experiencePoints -= maxExperiencePoints;
        maxExperiencePoints = weaponLevel * 100; // Increase XP requirement
        
        // Unlock new abilities
        unlockWeaponAbilities();
        
        // Improve weapon stats
        improveWeaponStats();
    }
    
    /**
     * Unlock weapon abilities based on level
     */
    private void unlockWeaponAbilities() {
        switch (weaponLevel) {
            case 2:
                unlockedAbilities.add("Rapid Fire");
                break;
            case 3:
                unlockedAbilities.add("Precision Shot");
                break;
            case 4:
                unlockedAbilities.add("Suppressive Fire");
                break;
            case 5:
                unlockedAbilities.add("Chain Shot");
                break;
        }
    }
    
    /**
     * Improve weapon stats on level up
     */
    private void improveWeaponStats() {
        for (Map.Entry<String, Integer> stat : weaponStats.entrySet()) {
            int currentValue = stat.getValue();
            int improvement = weaponLevel * 2; // +2 per level
            weaponStats.put(stat.getKey(), currentValue + improvement);
        }
    }
    
    /**
     * Use weapon (reduce durability)
     */
    public void useWeapon() {
        durability -= 1;
        
        if (durability <= maxDurability * 0.2) { // 20% durability threshold
            needsMaintenance = true;
        }
        
        if (durability <= 0) {
            durability = 0;
            needsMaintenance = true;
        }
    }
    
    /**
     * Maintain weapon (restore durability)
     */
    public void maintainWeapon() {
        durability = maxDurability;
        needsMaintenance = false;
    }
    
    /**
     * Get weapon effectiveness (based on durability)
     */
    public double getWeaponEffectiveness() {
        if (needsMaintenance) {
            return 0.5; // 50% effectiveness when maintenance needed
        }
        
        double durabilityPercentage = (double) durability / maxDurability;
        return Math.max(0.3, durabilityPercentage); // Minimum 30% effectiveness
    }
    
    /**
     * Get weapon summary
     */
    public String getWeaponSummary() {
        return String.format("%s (Level %d) - Durability: %d/%d, Attachments: %d, Modifications: %d",
            weaponName, weaponLevel, durability, maxDurability, attachments.size(), modifications.size());
    }
    
    /**
     * Weapon Type enum
     */
    public enum WeaponType {
        ASSAULT_RIFLE,
        SHOTGUN,
        SNIPER_RIFLE,
        PISTOL,
        HEAVY_WEAPON
    }
    
    /**
     * Weapon Attachment inner class
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeaponAttachment {
        private String name;
        private String description;
        private List<WeaponType> compatibleWeaponTypes;
        private Map<String, Integer> effects;
        private int attachmentSlot;
        
        public WeaponAttachment(String name, String description, List<WeaponType> compatibleTypes) {
            this.name = name;
            this.description = description;
            this.compatibleWeaponTypes = compatibleTypes;
            this.effects = new HashMap<>();
            this.attachmentSlot = 0;
        }
    }
    
    /**
     * Weapon Modification inner class
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeaponModification {
        private String name;
        private String description;
        private Map<String, Integer> effects;
        private int modificationCost;
        
        public WeaponModification(String name, String description) {
            this.name = name;
            this.description = description;
            this.effects = new HashMap<>();
            this.modificationCost = 0;
        }
    }
}

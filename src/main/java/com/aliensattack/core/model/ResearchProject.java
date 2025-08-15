package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

/**
 * Research Project Model - XCOM2 Strategic Layer
 * Represents a research project that can be conducted
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResearchProject {
    
    private String id;
    private String name;
    private int duration;
    private int cost;
    private int progress;
    private int startDay;
    private boolean isComplete;
    private Category category;
    private List<String> prerequisites;
    private List<String> rewards;
    private List<String> effects;
    private int scientistBonus;
    private boolean isUrgent;
    
    public ResearchProject(String id, String name, int duration, int cost) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.cost = cost;
        this.progress = 0;
        this.startDay = 0;
        this.isComplete = false;
        this.category = determineCategory(id);
        this.prerequisites = new ArrayList<>();
        this.rewards = new ArrayList<>();
        this.effects = new ArrayList<>();
        this.scientistBonus = 0;
        this.isUrgent = false;
        initializeProject();
    }
    
    public void incrementProgress() {
        if (!isComplete && progress < duration) {
            progress++;
            
            if (progress >= duration) {
                completeProject();
            }
        }
    }
    
    public void addScientistBonus(int bonus) {
        this.scientistBonus += bonus;
        // Recalculate completion time
        recalculateDuration();
    }
    
    public double getProgressPercentage() {
        if (duration == 0) return 100.0;
        return (double) progress / duration * 100.0;
    }
    
    public int getRemainingDays() {
        return Math.max(0, duration - progress);
    }
    
    public boolean canStart(List<String> completedResearch) {
        if (isComplete) return false;
        
        // Check prerequisites
        for (String prerequisite : prerequisites) {
            if (!completedResearch.contains(prerequisite)) {
                return false;
            }
        }
        
        return true;
    }
    
    public void start(int currentDay) {
        this.startDay = currentDay;
        this.progress = 0;
        this.isComplete = false;
    }
    
    private void completeProject() {
        isComplete = true;
        // Apply research effects
        applyResearchEffects();
    }
    
    private Category determineCategory(String id) {
        if (id.contains("WEAPON")) return Category.WEAPONS;
        if (id.contains("ARMOR")) return Category.ARMOR;
        if (id.contains("ALIEN")) return Category.ALIEN_TECH;
        if (id.contains("PSIONIC")) return Category.PSIONICS;
        if (id.contains("VEHICLE")) return Category.VEHICLES;
        return Category.GENERAL;
    }
    
    private void initializeProject() {
        switch (category) {
            case WEAPONS -> {
                effects.add("WEAPON_IMPROVEMENT");
                rewards.add("NEW_WEAPON_TECH");
            }
            case ARMOR -> {
                effects.add("ARMOR_IMPROVEMENT");
                rewards.add("NEW_ARMOR_TECH");
            }
            case ALIEN_TECH -> {
                effects.add("ALIEN_UNDERSTANDING");
                rewards.add("ALIEN_TECHNOLOGY");
            }
            case PSIONICS -> {
                effects.add("PSIONIC_DEVELOPMENT");
                rewards.add("PSIONIC_ABILITIES");
            }
            case VEHICLES -> {
                effects.add("VEHICLE_IMPROVEMENT");
                rewards.add("NEW_VEHICLE_TECH");
            }
            case GENERAL -> {
                effects.add("GENERAL_KNOWLEDGE");
                rewards.add("KNOWLEDGE_BONUS");
            }
        }
    }
    
    private void applyResearchEffects() {
        // Apply research-specific effects
        for (String effect : effects) {
            applyEffect(effect);
        }
    }
    
    private void applyEffect(String effect) {
        switch (effect) {
            case "WEAPON_IMPROVEMENT" -> applyWeaponImprovement();
            case "ARMOR_IMPROVEMENT" -> applyArmorImprovement();
            case "ALIEN_UNDERSTANDING" -> applyAlienUnderstanding();
            case "PSIONIC_DEVELOPMENT" -> applyPsionicDevelopment();
            case "VEHICLE_IMPROVEMENT" -> applyVehicleImprovement();
            case "GENERAL_KNOWLEDGE" -> applyGeneralKnowledge();
        }
    }
    
    private void applyWeaponImprovement() {
        // Apply weapon research effects
    }
    
    private void applyArmorImprovement() {
        // Apply armor research effects
    }
    
    private void applyAlienUnderstanding() {
        // Apply alien tech research effects
    }
    
    private void applyPsionicDevelopment() {
        // Apply psionic research effects
    }
    
    private void applyVehicleImprovement() {
        // Apply vehicle research effects
    }
    
    private void applyGeneralKnowledge() {
        // Apply general research effects
    }
    
    private void recalculateDuration() {
        if (scientistBonus > 0) {
            int bonusDays = scientistBonus / 10; // 10 bonus = 1 day reduction
            this.duration = Math.max(1, this.duration - bonusDays);
        }
    }
    
    public boolean isUrgentResearch() {
        return isUrgent;
    }
    
    public void setUrgent(boolean urgent) {
        this.isUrgent = urgent;
        if (urgent) {
            this.duration = Math.max(1, this.duration / 2); // Urgent research is faster
        }
    }
    
    public int getTotalCost() {
        return cost + (scientistBonus * 5); // Scientist bonus costs additional resources
    }
    
    /**
     * Research categories
     */
    public enum Category {
        WEAPONS("Weapons", "Combat weapon technology"),
        ARMOR("Armor", "Protective equipment"),
        ALIEN_TECH("Alien Technology", "Understanding alien technology"),
        PSIONICS("Psionics", "Mental and psychic abilities"),
        VEHICLES("Vehicles", "Transport and combat vehicles"),
        GENERAL("General", "General knowledge and technology");
        
        private final String displayName;
        private final String description;
        
        Category(String displayName, String description) {
            this.displayName = displayName;
            this.description = description;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getDescription() {
            return description;
        }
    }
}

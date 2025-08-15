package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

/**
 * Facility Model - XCOM2 Strategic Layer
 * Represents a facility that can be constructed in regions
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
    
    private String id;
    private String name;
    private int constructionCost;
    private int constructionTime;
    private Type type;
    private boolean isConstructed;
    private int constructionProgress;
    private double incomeBonus;
    private double researchBonus;
    private double constructionBonus;
    private List<String> effects;
    private int maintenanceCost;
    private boolean isOperational;
    
    public Facility(String id, String name, int constructionCost, int constructionTime) {
        this.id = id;
        this.name = name;
        this.constructionCost = constructionCost;
        this.constructionTime = constructionTime;
        this.type = determineType(id);
        this.isConstructed = false;
        this.constructionProgress = 0;
        this.effects = new ArrayList<>();
        this.isOperational = false;
        initializeBonuses();
    }
    
    public void updateConstruction() {
        if (!isConstructed && constructionProgress < constructionTime) {
            constructionProgress++;
            
            if (constructionProgress >= constructionTime) {
                completeConstruction();
            }
        }
    }
    
    public void update() {
        if (isConstructed && isOperational) {
            // Apply facility effects
            applyEffects();
        }
    }
    
    public boolean isConstructionComplete() {
        return constructionProgress >= constructionTime;
    }
    
    public double getConstructionProgressPercentage() {
        if (constructionTime == 0) return 100.0;
        return (double) constructionProgress / constructionTime * 100.0;
    }
    
    public int getRemainingConstructionTime() {
        return Math.max(0, constructionTime - constructionProgress);
    }
    
    private void completeConstruction() {
        isConstructed = true;
        isOperational = true;
        // log.info("Facility construction completed: {}", name); // log is not defined
    }
    
    private Type determineType(String id) {
        if (id.contains("COMMAND")) return Type.COMMAND;
        if (id.contains("WORKSHOP")) return Type.PRODUCTION;
        if (id.contains("LABORATORY")) return Type.RESEARCH;
        if (id.contains("DEFENSE")) return Type.DEFENSE;
        if (id.contains("COMMUNICATIONS")) return Type.COMMUNICATIONS;
        return Type.GENERAL;
    }
    
    private void initializeBonuses() {
        switch (type) {
            case COMMAND -> {
                incomeBonus = 50.0;
                effects.add("COMMAND_BONUS");
            }
            case PRODUCTION -> {
                constructionBonus = 40.0;
                effects.add("PRODUCTION_BONUS");
            }
            case RESEARCH -> {
                researchBonus = 30.0;
                effects.add("RESEARCH_BONUS");
            }
            case DEFENSE -> {
                effects.add("DEFENSE_BONUS");
            }
            case COMMUNICATIONS -> {
                incomeBonus = 20.0;
                effects.add("COMMUNICATIONS_BONUS");
            }
            case GENERAL -> {
                incomeBonus = 10.0;
            }
        }
    }
    
    private void applyEffects() {
        // Apply facility-specific effects
        for (String effect : effects) {
            applyEffect(effect);
        }
    }
    
    private void applyEffect(String effect) {
        switch (effect) {
            case "COMMAND_BONUS" -> applyCommandBonus();
            case "PRODUCTION_BONUS" -> applyProductionBonus();
            case "RESEARCH_BONUS" -> applyResearchBonus();
            case "DEFENSE_BONUS" -> applyDefenseBonus();
            case "COMMUNICATIONS_BONUS" -> applyCommunicationsBonus();
        }
    }
    
    private void applyCommandBonus() {
        // Apply command center bonuses
        // log.debug("Applying command bonus for facility: {}", name); // log is not defined
    }
    
    private void applyProductionBonus() {
        // Apply workshop bonuses
        // log.debug("Applying production bonus for facility: {}", name); // log is not defined
    }
    
    private void applyResearchBonus() {
        // Apply laboratory bonuses
        // log.debug("Applying research bonus for facility: {}", name); // log is not defined
    }
    
    private void applyDefenseBonus() {
        // Apply defense facility bonuses
        // log.debug("Applying defense bonus for facility: {}", name); // log is not defined
    }
    
    private void applyCommunicationsBonus() {
        // Apply communications bonuses
        // log.debug("Applying communications bonus for facility: {}", name); // log is not defined
    }
    
    public boolean canAffordConstruction(int availableSupplies) {
        return availableSupplies >= constructionCost;
    }
    
    public void startConstruction() {
        if (!isConstructed) {
            constructionProgress = 0;
            // log.info("Construction started for facility: {}", name); // log is not defined
        }
    }
    
    public void pauseConstruction() {
        if (!isConstructed) {
            // log.info("Construction paused for facility: {}", name); // log is not defined
        }
    }
    
    public void resumeConstruction() {
        if (!isConstructed) {
            // log.info("Construction resumed for facility: {}", name); // log is not defined
        }
    }
    
    /**
     * Facility types with different bonuses
     */
    public enum Type {
        COMMAND("Command", "Central command and control"),
        PRODUCTION("Production", "Manufacturing and workshops"),
        RESEARCH("Research", "Scientific research and development"),
        DEFENSE("Defense", "Defensive and security facilities"),
        COMMUNICATIONS("Communications", "Communication and networking"),
        GENERAL("General", "General purpose facilities");
        
        private final String displayName;
        private final String description;
        
        Type(String displayName, String description) {
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

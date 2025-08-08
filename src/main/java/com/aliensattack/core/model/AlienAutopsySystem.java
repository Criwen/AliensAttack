package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;

/**
 * Alien Autopsy System for XCOM 2 Tactical Combat
 * Provides research and intelligence gathering mechanics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienAutopsySystem {
    
    // Autopsy Types
    public enum AutopsyType {
        BASIC,          // Basic autopsy for common aliens
        ADVANCED,       // Advanced autopsy for special aliens
        COMPREHENSIVE,  // Comprehensive autopsy for unique aliens
        PSIONIC,        // Psionic autopsy for psionic aliens
        MECHANICAL,     // Mechanical autopsy for robotic aliens
        HYBRID,         // Hybrid autopsy for mixed aliens
        RULER,          // Ruler autopsy for alien rulers
        CHOSEN,         // Chosen autopsy for chosen enemies
        MUTATED,        // Mutated autopsy for evolved aliens
        CORRUPTED       // Corrupted autopsy for corrupted aliens
    }
    
    // Research Benefits
    public enum ResearchBenefit {
        WEAPON_TECH,        // New weapon technology
        ARMOR_TECH,         // New armor technology
        PSIONIC_TECH,       // New psionic technology
        MEDICAL_TECH,       // New medical technology
        INTELLIGENCE,       // Intelligence information
        TACTICAL_DATA,      // Tactical information
        VULNERABILITY_DATA, // Enemy vulnerability data
        ABILITY_DATA,       // Enemy ability information
        EVOLUTION_DATA,     // Alien evolution data
        STRATEGY_DATA       // Strategic information
    }
    
    // Intelligence Types
    public enum IntelligenceType {
        ALIEN_MOVEMENT,     // Alien movement patterns
        ALIEN_ABILITIES,    // Alien ability information
        ALIEN_WEAKNESSES,   // Alien vulnerability data
        ALIEN_STRENGTHS,    // Alien strength data
        ALIEN_TACTICS,      // Alien tactical information
        ALIEN_TECHNOLOGY,   // Alien technology data
        ALIEN_PSIONICS,     // Alien psionic data
        ALIEN_EVOLUTION,    // Alien evolution data
        ALIEN_STRATEGY,     // Alien strategic information
        ALIEN_INTELLIGENCE  // General alien intelligence
    }
    
    private String id;
    private String alienType;
    private AutopsyType autopsyType;
    private int researchPoints;
    private int intelligencePoints;
    private List<ResearchBenefit> unlockedBenefits;
    private List<IntelligenceType> gatheredIntelligence;
    private boolean isCompleted;
    private int completionTime;
    private int difficulty;
    private String researcher;
    private Date autopsyDate;
    
    public AlienAutopsySystem(String alienType, AutopsyType autopsyType) {
        this.id = "AUTOPSY_" + alienType + "_" + System.currentTimeMillis();
        this.alienType = alienType;
        this.autopsyType = autopsyType;
        this.researchPoints = 0;
        this.intelligencePoints = 0;
        this.unlockedBenefits = new ArrayList<>();
        this.gatheredIntelligence = new ArrayList<>();
        this.isCompleted = false;
        this.completionTime = calculateCompletionTime();
        this.difficulty = calculateDifficulty();
        this.researcher = "";
        this.autopsyDate = new Date();
    }
    
    // Autopsy Management Methods
    public void startAutopsy(String researcher) {
        this.researcher = researcher;
        System.out.println("Starting " + autopsyType + " autopsy on " + alienType + " by " + researcher);
    }
    
    public void addResearchPoints(int points) {
        researchPoints += points;
        checkForBenefits();
    }
    
    public void addIntelligencePoints(int points) {
        intelligencePoints += points;
        checkForIntelligence();
    }
    
    public void completeAutopsy() {
        isCompleted = true;
        System.out.println("Autopsy completed on " + alienType + " - Research: " + researchPoints + 
            ", Intelligence: " + intelligencePoints);
    }
    
    private int calculateCompletionTime() {
        switch (autopsyType) {
            case BASIC: return 2;
            case ADVANCED: return 4;
            case COMPREHENSIVE: return 6;
            case PSIONIC: return 5;
            case MECHANICAL: return 4;
            case HYBRID: return 5;
            case RULER: return 8;
            case CHOSEN: return 7;
            case MUTATED: return 6;
            case CORRUPTED: return 5;
            default: return 3;
        }
    }
    
    private int calculateDifficulty() {
        switch (autopsyType) {
            case BASIC: return 10;
            case ADVANCED: return 25;
            case COMPREHENSIVE: return 50;
            case PSIONIC: return 40;
            case MECHANICAL: return 35;
            case HYBRID: return 45;
            case RULER: return 80;
            case CHOSEN: return 70;
            case MUTATED: return 60;
            case CORRUPTED: return 55;
            default: return 20;
        }
    }
    
    // Research Benefits Methods
    public void checkForBenefits() {
        List<ResearchBenefit> newBenefits = new ArrayList<>();
        
        if (researchPoints >= 50 && !unlockedBenefits.contains(ResearchBenefit.INTELLIGENCE)) {
            newBenefits.add(ResearchBenefit.INTELLIGENCE);
        }
        
        if (researchPoints >= 100 && !unlockedBenefits.contains(ResearchBenefit.TACTICAL_DATA)) {
            newBenefits.add(ResearchBenefit.TACTICAL_DATA);
        }
        
        if (researchPoints >= 150 && !unlockedBenefits.contains(ResearchBenefit.VULNERABILITY_DATA)) {
            newBenefits.add(ResearchBenefit.VULNERABILITY_DATA);
        }
        
        if (researchPoints >= 200 && !unlockedBenefits.contains(ResearchBenefit.ABILITY_DATA)) {
            newBenefits.add(ResearchBenefit.ABILITY_DATA);
        }
        
        if (researchPoints >= 300 && !unlockedBenefits.contains(ResearchBenefit.WEAPON_TECH)) {
            newBenefits.add(ResearchBenefit.WEAPON_TECH);
        }
        
        if (researchPoints >= 400 && !unlockedBenefits.contains(ResearchBenefit.ARMOR_TECH)) {
            newBenefits.add(ResearchBenefit.ARMOR_TECH);
        }
        
        if (researchPoints >= 500 && !unlockedBenefits.contains(ResearchBenefit.PSIONIC_TECH)) {
            newBenefits.add(ResearchBenefit.PSIONIC_TECH);
        }
        
        if (researchPoints >= 600 && !unlockedBenefits.contains(ResearchBenefit.MEDICAL_TECH)) {
            newBenefits.add(ResearchBenefit.MEDICAL_TECH);
        }
        
        if (researchPoints >= 700 && !unlockedBenefits.contains(ResearchBenefit.EVOLUTION_DATA)) {
            newBenefits.add(ResearchBenefit.EVOLUTION_DATA);
        }
        
        if (researchPoints >= 800 && !unlockedBenefits.contains(ResearchBenefit.STRATEGY_DATA)) {
            newBenefits.add(ResearchBenefit.STRATEGY_DATA);
        }
        
        for (ResearchBenefit benefit : newBenefits) {
            unlockedBenefits.add(benefit);
            System.out.println("Unlocked research benefit: " + benefit + " from " + alienType + " autopsy");
        }
    }
    
    public void checkForIntelligence() {
        List<IntelligenceType> newIntelligence = new ArrayList<>();
        
        if (intelligencePoints >= 25 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_MOVEMENT)) {
            newIntelligence.add(IntelligenceType.ALIEN_MOVEMENT);
        }
        
        if (intelligencePoints >= 50 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_ABILITIES)) {
            newIntelligence.add(IntelligenceType.ALIEN_ABILITIES);
        }
        
        if (intelligencePoints >= 75 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_WEAKNESSES)) {
            newIntelligence.add(IntelligenceType.ALIEN_WEAKNESSES);
        }
        
        if (intelligencePoints >= 100 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_STRENGTHS)) {
            newIntelligence.add(IntelligenceType.ALIEN_STRENGTHS);
        }
        
        if (intelligencePoints >= 125 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_TACTICS)) {
            newIntelligence.add(IntelligenceType.ALIEN_TACTICS);
        }
        
        if (intelligencePoints >= 150 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_TECHNOLOGY)) {
            newIntelligence.add(IntelligenceType.ALIEN_TECHNOLOGY);
        }
        
        if (intelligencePoints >= 175 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_PSIONICS)) {
            newIntelligence.add(IntelligenceType.ALIEN_PSIONICS);
        }
        
        if (intelligencePoints >= 200 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_EVOLUTION)) {
            newIntelligence.add(IntelligenceType.ALIEN_EVOLUTION);
        }
        
        if (intelligencePoints >= 250 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_STRATEGY)) {
            newIntelligence.add(IntelligenceType.ALIEN_STRATEGY);
        }
        
        if (intelligencePoints >= 300 && !gatheredIntelligence.contains(IntelligenceType.ALIEN_INTELLIGENCE)) {
            newIntelligence.add(IntelligenceType.ALIEN_INTELLIGENCE);
        }
        
        for (IntelligenceType intelligence : newIntelligence) {
            gatheredIntelligence.add(intelligence);
            System.out.println("Gathered intelligence: " + intelligence + " from " + alienType + " autopsy");
        }
    }
    
    // Research Application Methods
    public void applyResearchBenefits() {
        for (ResearchBenefit benefit : unlockedBenefits) {
            applyResearchBenefit(benefit);
        }
    }
    
    private void applyResearchBenefit(ResearchBenefit benefit) {
        switch (benefit) {
            case WEAPON_TECH:
                applyWeaponTechnology();
                break;
            case ARMOR_TECH:
                applyArmorTechnology();
                break;
            case PSIONIC_TECH:
                applyPsionicTechnology();
                break;
            case MEDICAL_TECH:
                applyMedicalTechnology();
                break;
            case INTELLIGENCE:
                applyIntelligenceBenefit();
                break;
            case TACTICAL_DATA:
                applyTacticalData();
                break;
            case VULNERABILITY_DATA:
                applyVulnerabilityData();
                break;
            case ABILITY_DATA:
                applyAbilityData();
                break;
            case EVOLUTION_DATA:
                applyEvolutionData();
                break;
            case STRATEGY_DATA:
                applyStrategyData();
                break;
        }
    }
    
    private void applyWeaponTechnology() {
        System.out.println("Applied weapon technology from " + alienType + " autopsy");
        // Implementation would unlock new weapon types
    }
    
    private void applyArmorTechnology() {
        System.out.println("Applied armor technology from " + alienType + " autopsy");
        // Implementation would unlock new armor types
    }
    
    private void applyPsionicTechnology() {
        System.out.println("Applied psionic technology from " + alienType + " autopsy");
        // Implementation would unlock new psionic abilities
    }
    
    private void applyMedicalTechnology() {
        System.out.println("Applied medical technology from " + alienType + " autopsy");
        // Implementation would unlock new medical items
    }
    
    private void applyIntelligenceBenefit() {
        System.out.println("Applied intelligence benefit from " + alienType + " autopsy");
        // Implementation would provide intelligence bonuses
    }
    
    private void applyTacticalData() {
        System.out.println("Applied tactical data from " + alienType + " autopsy");
        // Implementation would provide tactical bonuses
    }
    
    private void applyVulnerabilityData() {
        System.out.println("Applied vulnerability data from " + alienType + " autopsy");
        // Implementation would provide damage bonuses against this alien type
    }
    
    private void applyAbilityData() {
        System.out.println("Applied ability data from " + alienType + " autopsy");
        // Implementation would provide ability information
    }
    
    private void applyEvolutionData() {
        System.out.println("Applied evolution data from " + alienType + " autopsy");
        // Implementation would provide evolution information
    }
    
    private void applyStrategyData() {
        System.out.println("Applied strategy data from " + alienType + " autopsy");
        // Implementation would provide strategic information
    }
    
    // Intelligence Application Methods
    public void applyIntelligenceGathering() {
        for (IntelligenceType intelligence : gatheredIntelligence) {
            applyIntelligenceType(intelligence);
        }
    }
    
    private void applyIntelligenceType(IntelligenceType intelligence) {
        switch (intelligence) {
            case ALIEN_MOVEMENT:
                applyAlienMovementIntel();
                break;
            case ALIEN_ABILITIES:
                applyAlienAbilitiesIntel();
                break;
            case ALIEN_WEAKNESSES:
                applyAlienWeaknessesIntel();
                break;
            case ALIEN_STRENGTHS:
                applyAlienStrengthsIntel();
                break;
            case ALIEN_TACTICS:
                applyAlienTacticsIntel();
                break;
            case ALIEN_TECHNOLOGY:
                applyAlienTechnologyIntel();
                break;
            case ALIEN_PSIONICS:
                applyAlienPsionicsIntel();
                break;
            case ALIEN_EVOLUTION:
                applyAlienEvolutionIntel();
                break;
            case ALIEN_STRATEGY:
                applyAlienStrategyIntel();
                break;
            case ALIEN_INTELLIGENCE:
                applyAlienIntelligenceIntel();
                break;
        }
    }
    
    private void applyAlienMovementIntel() {
        System.out.println("Applied alien movement intelligence from " + alienType + " autopsy");
        // Implementation would provide movement pattern information
    }
    
    private void applyAlienAbilitiesIntel() {
        System.out.println("Applied alien abilities intelligence from " + alienType + " autopsy");
        // Implementation would provide ability information
    }
    
    private void applyAlienWeaknessesIntel() {
        System.out.println("Applied alien weaknesses intelligence from " + alienType + " autopsy");
        // Implementation would provide weakness information
    }
    
    private void applyAlienStrengthsIntel() {
        System.out.println("Applied alien strengths intelligence from " + alienType + " autopsy");
        // Implementation would provide strength information
    }
    
    private void applyAlienTacticsIntel() {
        System.out.println("Applied alien tactics intelligence from " + alienType + " autopsy");
        // Implementation would provide tactical information
    }
    
    private void applyAlienTechnologyIntel() {
        System.out.println("Applied alien technology intelligence from " + alienType + " autopsy");
        // Implementation would provide technology information
    }
    
    private void applyAlienPsionicsIntel() {
        System.out.println("Applied alien psionics intelligence from " + alienType + " autopsy");
        // Implementation would provide psionic information
    }
    
    private void applyAlienEvolutionIntel() {
        System.out.println("Applied alien evolution intelligence from " + alienType + " autopsy");
        // Implementation would provide evolution information
    }
    
    private void applyAlienStrategyIntel() {
        System.out.println("Applied alien strategy intelligence from " + alienType + " autopsy");
        // Implementation would provide strategic information
    }
    
    private void applyAlienIntelligenceIntel() {
        System.out.println("Applied alien intelligence from " + alienType + " autopsy");
        // Implementation would provide general intelligence
    }
    
    // Autopsy Information Methods
    public String getAutopsyDescription() {
        return String.format("Autopsy Type: %s, Alien: %s, Research: %d, Intelligence: %d, Completed: %s", 
            autopsyType.name(), alienType, researchPoints, intelligencePoints, isCompleted ? "Yes" : "No");
    }
    
    public boolean isAutopsyCompleted() {
        return isCompleted;
    }
    
    public int getResearchProgress() {
        return (researchPoints * 100) / 800; // Max research points
    }
    
    public int getIntelligenceProgress() {
        return (intelligencePoints * 100) / 300; // Max intelligence points
    }
    
    public List<ResearchBenefit> getUnlockedBenefits() {
        return new ArrayList<>(unlockedBenefits);
    }
    
    public List<IntelligenceType> getGatheredIntelligence() {
        return new ArrayList<>(gatheredIntelligence);
    }
    
    public boolean hasBenefit(ResearchBenefit benefit) {
        return unlockedBenefits.contains(benefit);
    }
    
    public boolean hasIntelligence(IntelligenceType intelligence) {
        return gatheredIntelligence.contains(intelligence);
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public int getCompletionTime() {
        return completionTime;
    }
}

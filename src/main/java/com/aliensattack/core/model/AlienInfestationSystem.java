package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;

/**
 * Alien Infestation System for XCOM 2 Tactical Combat
 * Provides territory control mechanics with infestation levels and spread mechanics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienInfestationSystem {
    
    // Infestation Levels
    public enum InfestationLevel {
        NONE,           // No infestation
        LIGHT,          // Light infestation
        MODERATE,       // Moderate infestation
        HEAVY,          // Heavy infestation
        SEVERE,         // Severe infestation
        CRITICAL,       // Critical infestation
        OVERWHELMING    // Overwhelming infestation
    }
    
    // Infestation Types
    public enum InfestationType {
        BIOMASS,        // Organic biomass infestation
        TECHNOLOGICAL,  // Technological infestation
        PSIONIC,        // Psionic infestation
        CORRUPTION,     // Corruption infestation
        MUTATION,       // Mutation infestation
        ASSIMILATION,   // Assimilation infestation
        DOMINATION,     // Domination infestation
        CONVERSION,     // Conversion infestation
        INFECTION,      // Infection infestation
        PARASITIC       // Parasitic infestation
    }
    
    // Infestation Effects
    public enum InfestationEffect {
        HEALTH_DAMAGE,      // Damage to units
        MOVEMENT_PENALTY,   // Reduced movement
        ACCURACY_PENALTY,   // Reduced accuracy
        PSIONIC_INTERFERENCE, // Psionic interference
        EQUIPMENT_DEGRADATION, // Equipment damage
        MENTAL_DAMAGE,      // Mental damage
        CORRUPTION_SPREAD,  // Corruption spread
        MUTATION_RISK,      // Mutation risk
        ASSIMILATION_RISK,  // Assimilation risk
        DOMINATION_RISK     // Domination risk
    }
    
    // Territory Types
    public enum TerritoryType {
        URBAN,          // Urban area
        RURAL,          // Rural area
        INDUSTRIAL,     // Industrial area
        RESIDENTIAL,    // Residential area
        COMMERCIAL,     // Commercial area
        MILITARY,       // Military area
        GOVERNMENT,     // Government area
        TRANSPORTATION, // Transportation hub
        UTILITY,        // Utility facility
        WASTELAND       // Wasteland area
    }
    
    private String id;
    private String territoryName;
    private TerritoryType territoryType;
    private InfestationLevel infestationLevel;
    private InfestationType infestationType;
    private int infestationProgress;
    private int maxInfestationProgress;
    private List<InfestationEffect> activeEffects;
    private List<Unit> affectedUnits;
    private List<Unit> alienUnits;
    private int spreadRate;
    private int resistanceLevel;
    private boolean isContained;
    private boolean isCleansed;
    private int turnsInfested;
    private Map<String, Integer> infestationSources;
    
    public AlienInfestationSystem(String territoryName, TerritoryType territoryType) {
        this.id = "INFESTATION_" + territoryName + "_" + System.currentTimeMillis();
        this.territoryName = territoryName;
        this.territoryType = territoryType;
        this.infestationLevel = InfestationLevel.NONE;
        this.infestationType = determineInfestationType(territoryType);
        this.infestationProgress = 0;
        this.maxInfestationProgress = 1000;
        this.activeEffects = new ArrayList<>();
        this.affectedUnits = new ArrayList<>();
        this.alienUnits = new ArrayList<>();
        this.spreadRate = calculateSpreadRate();
        this.resistanceLevel = calculateResistanceLevel();
        this.isContained = false;
        this.isCleansed = false;
        this.turnsInfested = 0;
        this.infestationSources = new HashMap<>();
    }
    
    // Infestation Management Methods
    public void startInfestation(InfestationType type) {
        this.infestationType = type;
        this.infestationLevel = InfestationLevel.LIGHT;
        this.turnsInfested = 0;
        System.out.println("Infestation started in " + territoryName + " - Type: " + type);
    }
    
    public void spreadInfestation(int amount) {
        infestationProgress += amount;
        
        // Check for level increase
        checkForLevelIncrease();
        
        // Apply infestation effects
        applyInfestationEffects();
        
        // Update spread rate
        updateSpreadRate();
    }
    
    public void containInfestation() {
        isContained = true;
        spreadRate = Math.max(0, spreadRate - 50);
        System.out.println("Infestation contained in " + territoryName);
    }
    
    public void cleanseInfestation(int amount) {
        infestationProgress = Math.max(0, infestationProgress - amount);
        
        // Check for level decrease
        checkForLevelDecrease();
        
        // Update effects
        updateInfestationEffects();
        
        if (infestationProgress <= 0) {
            isCleansed = true;
            infestationLevel = InfestationLevel.NONE;
            System.out.println("Infestation cleansed in " + territoryName);
        }
    }
    
    private InfestationType determineInfestationType(TerritoryType territoryType) {
        switch (territoryType) {
            case URBAN:
                return InfestationType.BIOMASS;
            case INDUSTRIAL:
                return InfestationType.TECHNOLOGICAL;
            case RESIDENTIAL:
                return InfestationType.ASSIMILATION;
            case MILITARY:
                return InfestationType.DOMINATION;
            case GOVERNMENT:
                return InfestationType.CORRUPTION;
            case TRANSPORTATION:
                return InfestationType.INFECTION;
            case UTILITY:
                return InfestationType.PARASITIC;
            case WASTELAND:
                return InfestationType.MUTATION;
            default:
                return InfestationType.BIOMASS;
        }
    }
    
    private int calculateSpreadRate() {
        int baseRate = 10;
        
        // Territory-specific modifiers
        switch (territoryType) {
            case URBAN:
                baseRate += 20; // High population density
                break;
            case INDUSTRIAL:
                baseRate += 15; // Industrial infrastructure
                break;
            case RESIDENTIAL:
                baseRate += 25; // Vulnerable population
                break;
            case MILITARY:
                baseRate += 10; // Military resistance
                break;
            case GOVERNMENT:
                baseRate += 5; // Government control
                break;
            case RURAL:
                baseRate += 12; // Rural population
                break;
            case COMMERCIAL:
                baseRate += 18; // Commercial activity
                break;
            case TRANSPORTATION:
                baseRate += 22; // Transportation hub
                break;
            case UTILITY:
                baseRate += 8; // Utility facility
                break;
            case WASTELAND:
                baseRate += 30; // Wasteland vulnerability
                break;
        }
        
        return baseRate;
    }
    
    private int calculateResistanceLevel() {
        int baseResistance = 50;
        
        // Territory-specific resistance
        switch (territoryType) {
            case MILITARY:
                baseResistance += 30;
                break;
            case GOVERNMENT:
                baseResistance += 20;
                break;
            case UTILITY:
                baseResistance += 15;
                break;
            case RESIDENTIAL:
                baseResistance -= 10;
                break;
            case WASTELAND:
                baseResistance -= 20;
                break;
            case URBAN:
                baseResistance += 5;
                break;
            case RURAL:
                baseResistance += 10;
                break;
            case INDUSTRIAL:
                baseResistance += 8;
                break;
            case COMMERCIAL:
                baseResistance -= 5;
                break;
            case TRANSPORTATION:
                baseResistance -= 15;
                break;
        }
        
        return baseResistance;
    }
    
    public void checkForLevelIncrease() {
        InfestationLevel newLevel = calculateInfestationLevel();
        if (newLevel.ordinal() > infestationLevel.ordinal()) {
            infestationLevel = newLevel;
            System.out.println("Infestation level increased to " + infestationLevel + " in " + territoryName);
        }
    }
    
    public void checkForLevelDecrease() {
        InfestationLevel newLevel = calculateInfestationLevel();
        if (newLevel.ordinal() < infestationLevel.ordinal()) {
            infestationLevel = newLevel;
            System.out.println("Infestation level decreased to " + infestationLevel + " in " + territoryName);
        }
    }
    
    public InfestationLevel calculateInfestationLevel() {
        int percentage = (infestationProgress * 100) / maxInfestationProgress;
        
        if (percentage >= 90) {
            return InfestationLevel.OVERWHELMING;
        } else if (percentage >= 75) {
            return InfestationLevel.CRITICAL;
        } else if (percentage >= 60) {
            return InfestationLevel.SEVERE;
        } else if (percentage >= 45) {
            return InfestationLevel.HEAVY;
        } else if (percentage >= 30) {
            return InfestationLevel.MODERATE;
        } else if (percentage >= 15) {
            return InfestationLevel.LIGHT;
        } else {
            return InfestationLevel.NONE;
        }
    }
    
    public void applyInfestationEffects() {
        activeEffects.clear();
        
        switch (infestationType) {
            case BIOMASS:
                applyBiomassEffects();
                break;
            case TECHNOLOGICAL:
                applyTechnologicalEffects();
                break;
            case PSIONIC:
                applyPsionicEffects();
                break;
            case CORRUPTION:
                applyCorruptionEffects();
                break;
            case MUTATION:
                applyMutationEffects();
                break;
            case ASSIMILATION:
                applyAssimilationEffects();
                break;
            case DOMINATION:
                applyDominationEffects();
                break;
            case CONVERSION:
                applyConversionEffects();
                break;
            case INFECTION:
                applyInfectionEffects();
                break;
            case PARASITIC:
                applyParasiticEffects();
                break;
        }
    }
    
    private void applyBiomassEffects() {
        activeEffects.add(InfestationEffect.HEALTH_DAMAGE);
        activeEffects.add(InfestationEffect.MOVEMENT_PENALTY);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.MUTATION_RISK);
        }
    }
    
    private void applyTechnologicalEffects() {
        activeEffects.add(InfestationEffect.EQUIPMENT_DEGRADATION);
        activeEffects.add(InfestationEffect.ACCURACY_PENALTY);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.CORRUPTION_SPREAD);
        }
    }
    
    private void applyPsionicEffects() {
        activeEffects.add(InfestationEffect.PSIONIC_INTERFERENCE);
        activeEffects.add(InfestationEffect.MENTAL_DAMAGE);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.DOMINATION_RISK);
        }
    }
    
    private void applyCorruptionEffects() {
        activeEffects.add(InfestationEffect.CORRUPTION_SPREAD);
        activeEffects.add(InfestationEffect.MENTAL_DAMAGE);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.ASSIMILATION_RISK);
        }
    }
    
    private void applyMutationEffects() {
        activeEffects.add(InfestationEffect.MUTATION_RISK);
        activeEffects.add(InfestationEffect.HEALTH_DAMAGE);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.CORRUPTION_SPREAD);
        }
    }
    
    private void applyAssimilationEffects() {
        activeEffects.add(InfestationEffect.ASSIMILATION_RISK);
        activeEffects.add(InfestationEffect.MENTAL_DAMAGE);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.DOMINATION_RISK);
        }
    }
    
    private void applyDominationEffects() {
        activeEffects.add(InfestationEffect.DOMINATION_RISK);
        activeEffects.add(InfestationEffect.PSIONIC_INTERFERENCE);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.ASSIMILATION_RISK);
        }
    }
    
    private void applyConversionEffects() {
        activeEffects.add(InfestationEffect.ASSIMILATION_RISK);
        activeEffects.add(InfestationEffect.CORRUPTION_SPREAD);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.MUTATION_RISK);
        }
    }
    
    private void applyInfectionEffects() {
        activeEffects.add(InfestationEffect.HEALTH_DAMAGE);
        activeEffects.add(InfestationEffect.MUTATION_RISK);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.CORRUPTION_SPREAD);
        }
    }
    
    private void applyParasiticEffects() {
        activeEffects.add(InfestationEffect.HEALTH_DAMAGE);
        activeEffects.add(InfestationEffect.MUTATION_RISK);
        
        if (infestationLevel.ordinal() >= InfestationLevel.HEAVY.ordinal()) {
            activeEffects.add(InfestationEffect.ASSIMILATION_RISK);
        }
    }
    
    public void updateInfestationEffects() {
        // Update effects based on current level
        applyInfestationEffects();
        
        // Apply effects to affected units
        for (Unit unit : affectedUnits) {
            applyEffectsToUnit(unit);
        }
    }
    
    public void applyEffectsToUnit(Unit unit) {
        for (InfestationEffect effect : activeEffects) {
            applyEffectToUnit(unit, effect);
        }
    }
    
    private void applyEffectToUnit(Unit unit, InfestationEffect effect) {
        switch (effect) {
            case HEALTH_DAMAGE:
                applyHealthDamage(unit);
                break;
            case MOVEMENT_PENALTY:
                applyMovementPenalty(unit);
                break;
            case ACCURACY_PENALTY:
                applyAccuracyPenalty(unit);
                break;
            case PSIONIC_INTERFERENCE:
                applyPsionicInterference(unit);
                break;
            case EQUIPMENT_DEGRADATION:
                applyEquipmentDegradation(unit);
                break;
            case MENTAL_DAMAGE:
                applyMentalDamage(unit);
                break;
            case CORRUPTION_SPREAD:
                applyCorruptionSpread(unit);
                break;
            case MUTATION_RISK:
                applyMutationRisk(unit);
                break;
            case ASSIMILATION_RISK:
                applyAssimilationRisk(unit);
                break;
            case DOMINATION_RISK:
                applyDominationRisk(unit);
                break;
        }
    }
    
    private void applyHealthDamage(Unit unit) {
        int damage = infestationLevel.ordinal() * 5;
        unit.takeDamage(damage);
    }
    
    private void applyMovementPenalty(Unit unit) {
        int penalty = infestationLevel.ordinal() * 2;
        int newMovementRange = Math.max(1, unit.getMovementRange() - penalty);
        unit.setMovementRange(newMovementRange);
    }
    
    private void applyAccuracyPenalty(Unit unit) {
        int penalty = infestationLevel.ordinal() * 5;
        int newCriticalChance = Math.max(0, unit.getCriticalChance() - penalty);
        unit.setCriticalChance(newCriticalChance);
    }
    
    private void applyPsionicInterference(Unit unit) {
        int interference = infestationLevel.ordinal() * 10;
        int newPsiStrength = Math.max(0, unit.getPsiStrength() - interference);
        unit.setPsiStrength(newPsiStrength);
    }
    
    private void applyEquipmentDegradation(Unit unit) {
        // Simulate equipment degradation
        if (unit.getWeapon() != null) {
            // Implementation would degrade weapon
        }
    }
    
    private void applyMentalDamage(Unit unit) {
        // Apply mental damage
        // DONE: Calculate mental damage using infestation level and unit psi resistance (bypasses armor)
        int baseDamage = Math.max(1, infestationLevel.ordinal() * 4);
        int psiResistance = Math.max(0, unit.getPsiResistance()); // treat as percentage [0..100]
        int effectiveDamage = (int) Math.round(baseDamage * (1.0 - Math.min(100, psiResistance) / 100.0));
        if (effectiveDamage > 0) {
            unit.takeDamage(effectiveDamage);
        }
    }
    
    private void applyCorruptionSpread(Unit unit) {
        // Apply corruption spread
        // Implementation would spread corruption
    }
    
    private void applyMutationRisk(Unit unit) {
        // Apply mutation risk
        // Implementation would apply mutation risk
    }
    
    private void applyAssimilationRisk(Unit unit) {
        // Apply assimilation risk
        // Implementation would apply assimilation risk
    }
    
    private void applyDominationRisk(Unit unit) {
        // Apply domination risk
        // Implementation would apply domination risk
    }
    
    public void updateSpreadRate() {
        // Increase spread rate over time
        spreadRate += turnsInfested / 10;
        
        // Territory-specific modifiers
        switch (territoryType) {
            case URBAN:
                spreadRate += 5;
                break;
            case RESIDENTIAL:
                spreadRate += 10;
                break;
            case WASTELAND:
                spreadRate += 15;
                break;
            case RURAL:
                spreadRate += 8;
                break;
            case INDUSTRIAL:
                spreadRate += 12;
                break;
            case COMMERCIAL:
                spreadRate += 7;
                break;
            case MILITARY:
                spreadRate += 3;
                break;
            case GOVERNMENT:
                spreadRate += 2;
                break;
            case TRANSPORTATION:
                spreadRate += 9;
                break;
            case UTILITY:
                spreadRate += 6;
                break;
        }
    }
    
    public void addAlienUnit(Unit unit) {
        if (!alienUnits.contains(unit)) {
            alienUnits.add(unit);
            System.out.println("Alien unit " + unit.getName() + " added to " + territoryName);
        }
    }
    
    public void removeAlienUnit(Unit unit) {
        alienUnits.remove(unit);
        System.out.println("Alien unit " + unit.getName() + " removed from " + territoryName);
    }
    
    public void addAffectedUnit(Unit unit) {
        if (!affectedUnits.contains(unit)) {
            affectedUnits.add(unit);
            applyEffectsToUnit(unit);
        }
    }
    
    public void removeAffectedUnit(Unit unit) {
        affectedUnits.remove(unit);
        // Remove infestation effects from unit
    }
    
    // Infestation Information Methods
    public String getInfestationDescription() {
        return String.format("Territory: %s (%s), Infestation: %s %s, Progress: %d/%d, Spread Rate: %d", 
            territoryName, territoryType.name(), infestationLevel.name(), infestationType.name(), 
            infestationProgress, maxInfestationProgress, spreadRate);
    }
    
    public boolean isInfested() {
        return infestationLevel != InfestationLevel.NONE;
    }
    
    public boolean isContained() {
        return isContained;
    }
    
    public boolean isCleansed() {
        return isCleansed;
    }
    
    public int getInfestationPercentage() {
        return (infestationProgress * 100) / maxInfestationProgress;
    }
    
    public List<InfestationEffect> getActiveEffects() {
        return new ArrayList<>(activeEffects);
    }
    
    public List<Unit> getAffectedUnits() {
        return new ArrayList<>(affectedUnits);
    }
    
    public List<Unit> getAlienUnits() {
        return new ArrayList<>(alienUnits);
    }
    
    public boolean hasEffect(InfestationEffect effect) {
        return activeEffects.contains(effect);
    }
    
    public int getSpreadRate() {
        return spreadRate;
    }
    
    public int getResistanceLevel() {
        return resistanceLevel;
    }
    
    public int getTurnsInfested() {
        return turnsInfested;
    }
}

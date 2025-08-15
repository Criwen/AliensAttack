package com.aliensattack.core.systems;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.SoldierAbility;

import com.aliensattack.core.enums.ChosenStrength;
import com.aliensattack.core.enums.ChosenWeakness;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Chosen Adaptive Learning System - XCOM 2 Mechanic
 * Implements the system where chosen enemies learn from encounters and adapt to player tactics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChosenAdaptiveLearningSystem {
    
    private String chosenId;
    private String chosenName;
    private List<ChosenStrength> strengths;
    private List<ChosenWeakness> weaknesses;
    private Map<String, Integer> learnedTactics;
    private Map<String, Integer> tacticSuccessCount;
    private Map<String, Integer> tacticFailureCount;
    private List<String> playerPatterns;
    private Map<String, Integer> patternCounter;
    private int learningLevel;
    private int maxLearningLevel;
    private boolean isAdapting;
    private int adaptationProgress;
    private List<String> territoryControl;
    private Map<String, Integer> territoryInfluence;
    private int intelGathered;
    private int maxIntelCapacity;
    private List<String> knownPlayerAbilities;
    private Map<String, Integer> abilityCounter;
    
    public ChosenAdaptiveLearningSystem(String chosenId, String chosenName) {
        this.chosenId = chosenId;
        this.chosenName = chosenName;
        this.strengths = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
        this.learnedTactics = new HashMap<>();
        this.tacticSuccessCount = new HashMap<>();
        this.tacticFailureCount = new HashMap<>();
        this.playerPatterns = new ArrayList<>();
        this.patternCounter = new HashMap<>();
        this.learningLevel = 1;
        this.maxLearningLevel = 10;
        this.isAdapting = false;
        this.adaptationProgress = 0;
        this.territoryControl = new ArrayList<>();
        this.territoryInfluence = new HashMap<>();
        this.intelGathered = 0;
        this.maxIntelCapacity = 100;
        this.knownPlayerAbilities = new ArrayList<>();
        this.abilityCounter = new HashMap<>();
        
        initializeChosen();
    }
    
    /**
     * Initialize chosen with basic strengths and weaknesses
     */
    private void initializeChosen() {
        // Add random strengths
        strengths.addAll(Arrays.asList(
            ChosenStrength.PSYCHIC_MASTERY,
            ChosenStrength.ADAPTIVE_COMBAT,
            ChosenStrength.TERRITORY_CONTROL,
            ChosenStrength.INTEL_GATHERING
        ));
        
        // Add random weaknesses
        weaknesses.addAll(Arrays.asList(
            ChosenWeakness.EXPLOSIVE_VULNERABILITY,
            ChosenWeakness.PSYCHIC_SENSITIVITY
        ));
    }
    
    /**
     * Learn from player action
     */
    public void learnFromPlayerAction(String playerAction, Unit targetUnit, boolean wasSuccessful) {
        String tactic = playerAction + "_vs_" + targetUnit.getUnitType();
        
        // Record the tactic
        learnedTactics.put(tactic, learnedTactics.getOrDefault(tactic, 0) + 1);
        
        // Record success/failure
        if (wasSuccessful) {
            tacticSuccessCount.put(tactic, tacticSuccessCount.getOrDefault(tactic, 0) + 1);
        } else {
            tacticFailureCount.put(tactic, tacticFailureCount.getOrDefault(tactic, 0) + 1);
        }
        
        // Check for pattern recognition
        checkForPatterns(playerAction, targetUnit);
        
        // Update learning level
        updateLearningLevel();
        
        // Gather intel
        gatherIntel(playerAction, targetUnit);
    }
    
    /**
     * Check for recurring player patterns
     */
    private void checkForPatterns(String playerAction, Unit targetUnit) {
        String pattern = playerAction + "_pattern";
        
        if (!playerPatterns.contains(pattern)) {
            playerPatterns.add(pattern);
        }
        
        patternCounter.put(pattern, patternCounter.getOrDefault(pattern, 0) + 1);
        
        // If pattern is used frequently, start adapting
        if (patternCounter.get(pattern) >= 3) {
            startAdaptation(pattern);
        }
    }
    
    /**
     * Start adaptation to player pattern
     */
    private void startAdaptation(String pattern) {
        isAdapting = true;
        adaptationProgress = 0;
        
        // Add new strength based on pattern
        addAdaptiveStrength(pattern);
    }
    
    /**
     * Add adaptive strength based on learned pattern
     */
    private void addAdaptiveStrength(String pattern) {
        ChosenStrength newStrength = getStrengthFromPattern(pattern);
        if (newStrength != null && !strengths.contains(newStrength)) {
            strengths.add(newStrength);
        }
    }
    
    /**
     * Get strength type from pattern
     */
    private ChosenStrength getStrengthFromPattern(String pattern) {
        if (pattern.contains("attack")) {
            return ChosenStrength.ADAPTIVE_COMBAT;
        } else if (pattern.contains("move")) {
            return ChosenStrength.TERRITORY_CONTROL;
        } else if (pattern.contains("psionic")) {
            return ChosenStrength.PSYCHIC_MASTERY;
        } else if (pattern.contains("overwatch")) {
            return ChosenStrength.INTEL_GATHERING;
        }
        return null;
    }
    
    /**
     * Update learning level based on experience
     */
    private void updateLearningLevel() {
        int totalTactics = learnedTactics.size();
        int totalPatterns = playerPatterns.size();
        
        if (totalTactics >= 10 && learningLevel < maxLearningLevel) {
            learningLevel++;
        }
        
        if (totalPatterns >= 5 && learningLevel < maxLearningLevel) {
            learningLevel++;
        }
    }
    
    /**
     * Gather intel from player actions
     */
    private void gatherIntel(String playerAction, Unit targetUnit) {
        int intelGain = calculateIntelGain(playerAction, targetUnit);
        intelGathered = Math.min(intelGathered + intelGain, maxIntelCapacity);
        
        // Learn about player abilities
        learnPlayerAbilities(targetUnit);
    }
    
    /**
     * Calculate intel gain from action
     */
    private int calculateIntelGain(String playerAction, Unit targetUnit) {
        int baseIntel = 1;
        
        // Bonus intel for new tactics
        if (!learnedTactics.containsKey(playerAction + "_vs_" + targetUnit.getUnitType())) {
            baseIntel += 2;
        }
        
        // Bonus intel for high-value targets
        if (targetUnit.getSoldierClass() != null) {
            baseIntel += 1;
        }
        
        return baseIntel;
    }
    
    /**
     * Learn about player abilities
     */
    private void learnPlayerAbilities(Unit targetUnit) {
        List<SoldierAbility> abilities = targetUnit.getAbilities();
        for (SoldierAbility ability : abilities) {
            String abilityName = ability.getName();
            if (!knownPlayerAbilities.contains(abilityName)) {
                knownPlayerAbilities.add(abilityName);
            }
            abilityCounter.put(abilityName, abilityCounter.getOrDefault(abilityName, 0) + 1);
        }
    }
    
    /**
     * Adapt to player tactics
     */
    public void adaptToPlayerTactics(String playerTactic) {
        if (isAdapting) {
            adaptationProgress++;
            
            if (adaptationProgress >= 5) {
                completeAdaptation(playerTactic);
            }
        }
    }
    
    /**
     * Complete adaptation process
     */
    private void completeAdaptation(String playerTactic) {
        isAdapting = false;
        adaptationProgress = 0;
        
        // Add counter-tactic
        addCounterTactic(playerTactic);
        
        // Increase learning level
        if (learningLevel < maxLearningLevel) {
            learningLevel++;
        }
    }
    
    /**
     * Add counter-tactic for player tactic
     */
    private void addCounterTactic(String playerTactic) {
        String counterTactic = "counter_" + playerTactic;
        learnedTactics.put(counterTactic, 1);
    }
    
    /**
     * Get optimal counter-tactic for player action
     */
    public String getOptimalCounterTactic(String playerAction) {
        String counterTactic = "counter_" + playerAction;
        
        if (learnedTactics.containsKey(counterTactic)) {
            return counterTactic;
        }
        
        // Return most successful tactic
        return getMostSuccessfulTactic();
    }
    
    /**
     * Get most successful tactic
     */
    public String getMostSuccessfulTactic() {
        String bestTactic = null;
        double bestSuccessRate = 0.0;
        
        for (String tactic : learnedTactics.keySet()) {
            double successRate = getTacticSuccessRate(tactic);
            if (successRate > bestSuccessRate) {
                bestSuccessRate = successRate;
                bestTactic = tactic;
            }
        }
        
        return bestTactic;
    }
    
    /**
     * Get success rate for specific tactic
     */
    public double getTacticSuccessRate(String tactic) {
        int successes = tacticSuccessCount.getOrDefault(tactic, 0);
        int failures = tacticFailureCount.getOrDefault(tactic, 0);
        int total = successes + failures;
        
        return total > 0 ? (double) successes / total : 0.0;
    }
    
    /**
     * Add territory control
     */
    public void addTerritoryControl(String territory) {
        if (!territoryControl.contains(territory)) {
            territoryControl.add(territory);
            territoryInfluence.put(territory, 100);
        }
    }
    
    /**
     * Increase territory influence
     */
    public void increaseTerritoryInfluence(String territory, int amount) {
        int currentInfluence = territoryInfluence.getOrDefault(territory, 0);
        territoryInfluence.put(territory, Math.min(currentInfluence + amount, 100));
    }
    
    /**
     * Decrease territory influence
     */
    public void decreaseTerritoryInfluence(String territory, int amount) {
        int currentInfluence = territoryInfluence.getOrDefault(territory, 0);
        territoryInfluence.put(territory, Math.max(currentInfluence - amount, 0));
    }
    
    /**
     * Get territory influence level
     */
    public int getTerritoryInfluence(String territory) {
        return territoryInfluence.getOrDefault(territory, 0);
    }
    
    /**
     * Check if chosen controls territory
     */
    public boolean controlsTerritory(String territory) {
        return territoryControl.contains(territory) && 
               territoryInfluence.getOrDefault(territory, 0) >= 50;
    }
    
    /**
     * Get learning level
     */
    public int getLearningLevel() {
        return learningLevel;
    }
    
    /**
     * Check if chosen is adapting
     */
    public boolean isAdapting() {
        return isAdapting;
    }
    
    /**
     * Get adaptation progress
     */
    public int getAdaptationProgress() {
        return adaptationProgress;
    }
    
    /**
     * Get intel gathered
     */
    public int getIntelGathered() {
        return intelGathered;
    }
    
    /**
     * Get intel capacity percentage
     */
    public double getIntelCapacityPercentage() {
        return (double) intelGathered / maxIntelCapacity;
    }
    
    /**
     * Get known player abilities
     */
    public List<String> getKnownPlayerAbilities() {
        return new ArrayList<>(knownPlayerAbilities);
    }
    
    /**
     * Get most used player ability
     */
    public String getMostUsedPlayerAbility() {
        String mostUsed = null;
        int maxCount = 0;
        
        for (Map.Entry<String, Integer> entry : abilityCounter.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostUsed = entry.getKey();
            }
        }
        
        return mostUsed;
    }
    
    /**
     * Get strengths
     */
    public List<ChosenStrength> getStrengths() {
        return new ArrayList<>(strengths);
    }
    
    /**
     * Get weaknesses
     */
    public List<ChosenWeakness> getWeaknesses() {
        return new ArrayList<>(weaknesses);
    }
    
    /**
     * Add strength
     */
    public void addStrength(ChosenStrength strength) {
        if (!strengths.contains(strength)) {
            strengths.add(strength);
        }
    }
    
    /**
     * Remove strength
     */
    public void removeStrength(ChosenStrength strength) {
        strengths.remove(strength);
    }
    
    /**
     * Add weakness
     */
    public void addWeakness(ChosenWeakness weakness) {
        if (!weaknesses.contains(weakness)) {
            weaknesses.add(weakness);
        }
    }
    
    /**
     * Remove weakness
     */
    public void removeWeakness(ChosenWeakness weakness) {
        weaknesses.remove(weakness);
    }
}

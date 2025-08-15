package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.model.AlienEvolutionSystem.EvolutionPath;
import com.aliensattack.core.model.AlienEvolutionSystem.EvolutionMutation;
import com.aliensattack.core.model.AlienEvolutionSystem.EvolutionStage;

import java.util.*;

/**
 * Advanced Alien Evolution System for XCOM 2 Strategic Layer
 * Manages alien evolution, mutations, and strategic adaptation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienEvolution {
    
    private AlienEvolutionSystem evolutionSystem;
    private Map<String, EvolutionPath> evolutionPaths;
    private Map<String, EvolutionMutation> activeMutations;
    private List<AlienEvolutionSystem.AlienUnit.AlienType> evolvedAlienTypes;
    private EvolutionStage currentStage;
    private int evolutionPoints;
    private int mutationRate;
    private Random random;
    
    /**
     * Initialize the alien evolution system
     */
    public void initialize() {
        if (evolutionSystem == null) {
            evolutionSystem = new AlienEvolutionSystem();
        }
        if (evolutionPaths == null) {
            evolutionPaths = new HashMap<>();
        }
        if (activeMutations == null) {
            activeMutations = new HashMap<>();
        }
        if (evolvedAlienTypes == null) {
            evolvedAlienTypes = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        currentStage = EvolutionStage.BASIC;
        evolutionPoints = 100;
        mutationRate = 10;
        
        initializeEvolutionPaths();
        initializeActiveMutations();
    }
    
    /**
     * Initialize evolution paths
     */
    private void initializeEvolutionPaths() {
        // Create basic evolution paths
        EvolutionPath aggressivePath = EvolutionPath.builder()
                .pathId("AGGRESSIVE_EVOLUTION")
                .pathName("Aggressive Evolution Path")
                .pathType("COMBAT")
                .targetAlienType(AlienEvolutionSystem.AlienUnit.AlienType.MUTON)
                .evolutionCost(75)
                .evolutionTime(5)
                .currentProgress(0)
                .maxProgress(100)
                .successRate(0.8)
                .failureRate(0.2)
                .evolutionBonus("Increased combat power")
                .evolutionPenalty("Reduced intelligence")
                .isActive(false)
                .isCompleted(false)
                .isFailed(false)
                .evolutionStage(EvolutionStage.ADVANCED)
                .build();
        
        evolutionPaths.put(aggressivePath.getPathId(), aggressivePath);
        
        EvolutionPath psionicPath = EvolutionPath.builder()
                .pathId("PSIONIC_EVOLUTION")
                .pathName("Psionic Evolution Path")
                .pathType("PSIONIC")
                .targetAlienType(AlienEvolutionSystem.AlienUnit.AlienType.SECTOID)
                .evolutionCost(100)
                .evolutionTime(6)
                .currentProgress(0)
                .maxProgress(120)
                .successRate(0.75)
                .failureRate(0.25)
                .evolutionBonus("Enhanced psionic abilities")
                .evolutionPenalty("Reduced physical strength")
                .isActive(false)
                .isCompleted(false)
                .isFailed(false)
                .evolutionStage(EvolutionStage.ADVANCED)
                .build();
        
        evolutionPaths.put(psionicPath.getPathId(), psionicPath);
    }
    
    /**
     * Initialize active mutations
     */
    private void initializeActiveMutations() {
        // Create some active mutations
        EvolutionMutation combatMutation = EvolutionMutation.builder()
                .mutationId("COMBAT_MUTATION")
                .mutationName("Combat Enhancement")
                .mutationType("COMBAT")
                .mutationEffect("Increased damage output")
                .mutationDuration(10)
                .currentDuration(0)
                .isActive(true)
                .isPermanent(false)
                .build();
        
        activeMutations.put(combatMutation.getMutationId(), combatMutation);
    }
    
    /**
     * Start evolution path
     */
    public boolean startEvolutionPath(String pathId) {
        EvolutionPath path = evolutionPaths.get(pathId);
        if (path != null && !path.isActive() && evolutionPoints >= path.getEvolutionCost()) {
            path.setActive(true);
            evolutionPoints -= path.getEvolutionCost();
            return true;
        }
        return false;
    }
    
    /**
     * Update evolution progress
     */
    public void updateEvolutionProgress() {
        for (EvolutionPath path : evolutionPaths.values()) {
            if (path.isActive() && !path.isCompleted()) {
                int progress = path.getCurrentProgress() + (mutationRate / 2);
                path.setCurrentProgress(Math.min(progress, path.getMaxProgress()));
                
                if (path.getCurrentProgress() >= path.getMaxProgress()) {
                    completeEvolutionPath(path);
                }
            }
        }
        
        // Update active mutations
        updateActiveMutations();
    }
    
    /**
     * Complete evolution path
     */
    private void completeEvolutionPath(EvolutionPath path) {
        path.setCompleted(true);
        path.setActive(false);
        
        // Add to evolved alien types
        if (!evolvedAlienTypes.contains(path.getTargetAlienType())) {
            evolvedAlienTypes.add(path.getTargetAlienType());
        }
        
        // Unlock new mutations
        unlockMutations(path);
        
        // Add evolution points
        evolutionPoints += 50;
        
        // Update current stage
        updateEvolutionStage();
    }
    
    /**
     * Unlock mutations based on completed evolution
     */
    private void unlockMutations(EvolutionPath path) {
        // Create new mutation based on evolution
        EvolutionMutation newMutation = EvolutionMutation.builder()
                .mutationId(path.getPathId() + "_MUTATION")
                .mutationName(path.getPathName() + " Mutation")
                .mutationType(path.getPathType())
                .mutationEffect(path.getEvolutionBonus())
                .mutationDuration(15)
                .currentDuration(0)
                .isActive(true)
                .isPermanent(false)
                .build();
        
        activeMutations.put(newMutation.getMutationId(), newMutation);
    }
    
    /**
     * Update active mutations
     */
    private void updateActiveMutations() {
        Iterator<Map.Entry<String, EvolutionMutation>> iterator = activeMutations.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, EvolutionMutation> entry = iterator.next();
            EvolutionMutation mutation = entry.getValue();
            
            if (mutation.isActive() && !mutation.isPermanent()) {
                mutation.setCurrentDuration(mutation.getCurrentDuration() + 1);
                
                if (mutation.getCurrentDuration() >= mutation.getMutationDuration()) {
                    // Remove expired mutation
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * Update evolution stage
     */
    private void updateEvolutionStage() {
        int completedPaths = (int) evolutionPaths.values().stream()
                .filter(EvolutionPath::isCompleted)
                .count();
        
        if (completedPaths >= 3) {
            currentStage = EvolutionStage.ADVANCED;
        } else if (completedPaths >= 1) {
            currentStage = EvolutionStage.INTERMEDIATE;
        } else {
            currentStage = EvolutionStage.BASIC;
        }
    }
    
    /**
     * Get evolution status
     */
    public String getEvolutionStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Alien Evolution Status:\n");
        status.append("- Current Stage: ").append(currentStage).append("\n");
        status.append("- Evolution Points: ").append(evolutionPoints).append("\n");
        status.append("- Mutation Rate: ").append(mutationRate).append("\n");
        status.append("- Active Evolution Paths: ").append(evolutionPaths.values().stream().filter(EvolutionPath::isActive).count()).append("\n");
        status.append("- Completed Evolution Paths: ").append(evolutionPaths.values().stream().filter(EvolutionPath::isCompleted).count()).append("\n");
        status.append("- Evolved Alien Types: ").append(evolvedAlienTypes.size()).append("\n");
        status.append("- Active Mutations: ").append(activeMutations.size()).append("\n");
        
        return status.toString();
    }
}

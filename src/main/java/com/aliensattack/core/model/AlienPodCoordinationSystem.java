package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.AIBehaviorType;
import com.aliensattack.core.model.AIBehaviorTree.BehaviorNode;
import com.aliensattack.core.model.AIBehaviorTree.DecisionTree;

import java.util.*;

/**
 * Advanced Alien Pod Coordination System for XCOM 2 Tactical Combat
 * Manages alien pod behavior, coordination, and tactical decision making
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienPodCoordinationSystem {
    
    private AIBehaviorTree behaviorTree;
    private Map<String, DecisionTree> podDecisionTrees;
    private Map<String, BehaviorNode> podBehaviors;
    private List<AlienPod> coordinatedPods;
    private PodType currentPodType;
    private AIBehaviorTree.AIBehaviorType currentBehavior;
    private int coordinationLevel;
    private int tacticalAwareness;
    private Random random;
    
    /**
     * Initialize the pod coordination system
     */
    public void initialize() {
        if (behaviorTree == null) {
            behaviorTree = new AIBehaviorTree();
        }
        if (podDecisionTrees == null) {
            podDecisionTrees = new HashMap<>();
        }
        if (podBehaviors == null) {
            podBehaviors = new HashMap<>();
        }
        if (coordinatedPods == null) {
            coordinatedPods = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        coordinationLevel = 1;
        tacticalAwareness = 50;
        
        initializePodBehaviors();
        initializeDecisionTrees();
    }
    
    /**
     * Initialize pod behaviors
     */
    private void initializePodBehaviors() {
        // Create basic pod behaviors
        BehaviorNode aggressiveBehavior = BehaviorNode.builder()
                .nodeId("AGGRESSIVE_BEHAVIOR")
                .nodeName("Aggressive Pod Behavior")
                .nodeType("BEHAVIOR")
                .behaviorType(AIBehaviorTree.AIBehaviorType.AGGRESSIVE)
                .successRate(0.8)
                .failureRate(0.2)
                .energyCost(10)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Aggressive pod coordination")
                .build();
        
        podBehaviors.put(aggressiveBehavior.getNodeId(), aggressiveBehavior);
        
        BehaviorNode defensiveBehavior = BehaviorNode.builder()
                .nodeId("DEFENSIVE_BEHAVIOR")
                .nodeName("Defensive Pod Behavior")
                .nodeType("BEHAVIOR")
                .behaviorType(AIBehaviorTree.AIBehaviorType.DEFENSIVE)
                .successRate(0.9)
                .failureRate(0.1)
                .energyCost(5)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Defensive pod coordination")
                .build();
        
        podBehaviors.put(defensiveBehavior.getNodeId(), defensiveBehavior);
    }
    
    /**
     * Initialize decision trees
     */
    private void initializeDecisionTrees() {
        // Create decision tree for aggressive pods
        DecisionTree aggressiveTree = DecisionTree.builder()
                .treeId("AGGRESSIVE_TREE")
                .treeName("Aggressive Pod Decision Tree")
                .treeType("COMBAT")
                .rootNodeId("AGGRESSIVE_BEHAVIOR")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        podDecisionTrees.put(aggressiveTree.getTreeId(), aggressiveTree);
        
        // Create decision tree for defensive pods
        DecisionTree defensiveTree = DecisionTree.builder()
                .treeId("DEFENSIVE_TREE")
                .treeName("Defensive Pod Decision Tree")
                .treeType("SURVIVAL")
                .rootNodeId("DEFENSIVE_BEHAVIOR")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        podDecisionTrees.put(defensiveTree.getTreeId(), defensiveTree);
    }
    
    /**
     * Coordinate alien pod
     */
    public void coordinatePod(AlienPod pod) {
        if (!coordinatedPods.contains(pod)) {
            coordinatedPods.add(pod);
        }
        
        // Update pod behavior based on current situation
        updatePodBehavior(pod);
        
        // Apply coordination bonuses
        applyCoordinationBonuses(pod);
        
        // Update tactical awareness
        updateTacticalAwareness(pod);
    }
    
    /**
     * Update pod behavior
     */
    private void updatePodBehavior(AlienPod pod) {
        // Determine appropriate behavior based on pod type and situation
        AIBehaviorTree.AIBehaviorType newBehavior = determineOptimalBehavior(pod);
        
        if (newBehavior != currentBehavior) {
            currentBehavior = newBehavior;
            // pod.setBehaviorType(newBehavior); // Method not available
        }
    }
    
    /**
     * Determine optimal behavior for pod
     */
    private AIBehaviorTree.AIBehaviorType determineOptimalBehavior(AlienPod pod) {
        // Simple behavior determination logic
        if (pod.getPodType() == (Object)PodType.AGGRESSIVE) {
            return AIBehaviorTree.AIBehaviorType.AGGRESSIVE;
        } else if (pod.getPodType() == (Object)PodType.DEFENSIVE) {
            return AIBehaviorTree.AIBehaviorType.DEFENSIVE;
        } else {
            return AIBehaviorTree.AIBehaviorType.BALANCED;
        }
    }
    
    /**
     * Apply coordination bonuses
     */
    private void applyCoordinationBonuses(AlienPod pod) {
        // Apply coordination level bonuses
        int bonus = coordinationLevel * 5;
        
        // Apply to pod members - simplified since methods don't exist
        // for (Alien alien : pod.getAliens()) {
        //     alien.setCombatBonus(alien.getCombatBonus() + bonus);
        // }
    }
    
    /**
     * Update tactical awareness
     */
    private void updateTacticalAwareness(AlienPod pod) {
        // Increase tactical awareness based on coordination
        tacticalAwareness = Math.min(100, tacticalAwareness + coordinationLevel);
        
        // Apply to pod - simplified since method doesn't exist
        // pod.setTacticalAwareness(tacticalAwareness);
    }
    
    public enum PodType {
        AGGRESSIVE, DEFENSIVE, BALANCED, STEALTH
    }
    
    /**
     * Get coordination status
     */
    public String getCoordinationStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Pod Coordination Status:\n");
        status.append("- Coordinated Pods: ").append(coordinatedPods.size()).append("\n");
        status.append("- Current Behavior: ").append(currentBehavior).append("\n");
        status.append("- Coordination Level: ").append(coordinationLevel).append("\n");
        status.append("- Tactical Awareness: ").append(tacticalAwareness).append("\n");
        status.append("- Active Decision Trees: ").append(podDecisionTrees.values().stream().filter(DecisionTree::isActive).count()).append("\n");
        status.append("- Available Behaviors: ").append(podBehaviors.size()).append("\n");
        
        return status.toString();
    }
}

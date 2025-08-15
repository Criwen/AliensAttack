package com.aliensattack.core.ai;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.ai.AIBehaviorTree.BehaviorNode;
import com.aliensattack.core.ai.AIBehaviorTree.DecisionTree;
import com.aliensattack.core.ai.AIBehaviorTree.AIBehaviorType;

import java.util.*;

/**
 * Advanced Alien AI System for XCOM 2 Tactical Combat
 * Manages alien AI behavior, decision making, and tactical responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienAI {
    
    private AIBehaviorTree behaviorTree;
    private Map<String, BehaviorNode> aiBehaviors;
    private Map<String, DecisionTree> aiDecisions;
    private AlienType alienType;
    private AIBehaviorType currentBehavior;
    private int intelligenceLevel;
    private int tacticalAwareness;
    private int aggressionLevel;
    private Random random;
    
    /**
     * Initialize the alien AI
     */
    public void initialize() {
        if (behaviorTree == null) {
            behaviorTree = new AIBehaviorTree();
        }
        if (aiBehaviors == null) {
            aiBehaviors = new HashMap<>();
        }
        if (aiDecisions == null) {
            aiDecisions = new HashMap<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        intelligenceLevel = 50;
        tacticalAwareness = 60;
        aggressionLevel = 70;
        
        initializeAIBehaviors();
        initializeAIDecisions();
    }
    
    /**
     * Initialize AI behaviors
     */
    private void initializeAIBehaviors() {
        // Create AI behavior nodes
        BehaviorNode combatBehavior = BehaviorNode.builder()
                .nodeId("COMBAT_BEHAVIOR")
                .nodeName("Combat Behavior")
                .nodeType("COMBAT")
                .behaviorType(AIBehaviorType.AGGRESSIVE)
                .successRate(0.8)
                .failureRate(0.2)
                .energyCost(15)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Combat-focused behavior")
                .build();
        
        aiBehaviors.put(combatBehavior.getNodeId(), combatBehavior);
        
        BehaviorNode stealthBehavior = BehaviorNode.builder()
                .nodeId("STEALTH_BEHAVIOR")
                .nodeName("Stealth Behavior")
                .nodeType("STEALTH")
                .behaviorType(AIBehaviorType.DEFENSIVE)
                .successRate(0.9)
                .failureRate(0.1)
                .energyCost(10)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Stealth-focused behavior")
                .build();
        
        aiBehaviors.put(stealthBehavior.getNodeId(), stealthBehavior);
    }
    
    /**
     * Initialize AI decisions
     */
    private void initializeAIDecisions() {
        // Create decision tree for combat
        DecisionTree combatTree = DecisionTree.builder()
                .treeId("COMBAT_TREE")
                .treeName("Combat Decision Tree")
                .treeType("COMBAT")
                .rootNodeId("COMBAT_BEHAVIOR")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        aiDecisions.put(combatTree.getTreeId(), combatTree);
        
        // Create decision tree for stealth
        DecisionTree stealthTree = DecisionTree.builder()
                .treeId("STEALTH_TREE")
                .treeName("Stealth Decision Tree")
                .treeType("STEALTH")
                .rootNodeId("STEALTH_BEHAVIOR")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        aiDecisions.put(stealthTree.getTreeId(), stealthTree);
    }
    
    /**
     * Update AI behavior
     */
    public void updateBehavior() {
        // Determine optimal behavior based on current situation
        AIBehaviorType newBehavior = determineOptimalBehavior();
        
        if (newBehavior != currentBehavior) {
            currentBehavior = newBehavior;
            activateBehavior(newBehavior);
        }
        
        // Update tactical awareness
        updateTacticalAwareness();
    }
    
    /**
     * Determine optimal behavior
     */
    private AIBehaviorType determineOptimalBehavior() {
        // Simple behavior determination logic
        if (aggressionLevel > 80) {
            return AIBehaviorType.AGGRESSIVE;
        } else if (intelligenceLevel > 70) {
            return AIBehaviorType.BALANCED;
        } else {
            return AIBehaviorType.DEFENSIVE;
        }
    }
    
    /**
     * Activate behavior
     */
    private void activateBehavior(AIBehaviorType behaviorType) {
        // Deactivate all behaviors
        for (BehaviorNode behavior : aiBehaviors.values()) {
            behavior.setActive(false);
        }
        
        // Activate appropriate behavior
        for (BehaviorNode behavior : aiBehaviors.values()) {
            if (behavior.getBehaviorType() == behaviorType) {
                behavior.setActive(true);
                break;
            }
        }
    }
    
    /**
     * Update tactical awareness
     */
    private void updateTacticalAwareness() {
        // Increase tactical awareness based on intelligence
        tacticalAwareness = Math.min(100, tacticalAwareness + (intelligenceLevel / 10));
    }
    
    /**
     * Get AI status
     */
    public String getAIStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Alien AI Status:\n");
        status.append("- Alien Type: ").append(alienType).append("\n");
        status.append("- Current Behavior: ").append(currentBehavior).append("\n");
        status.append("- Intelligence Level: ").append(intelligenceLevel).append("\n");
        status.append("- Tactical Awareness: ").append(tacticalAwareness).append("\n");
        status.append("- Aggression Level: ").append(aggressionLevel).append("\n");
        status.append("- Active Behaviors: ").append(aiBehaviors.values().stream().filter(BehaviorNode::isActive).count()).append("\n");
        status.append("- Active Decision Trees: ").append(aiDecisions.values().stream().filter(DecisionTree::isActive).count()).append("\n");
        
        return status.toString();
    }
}



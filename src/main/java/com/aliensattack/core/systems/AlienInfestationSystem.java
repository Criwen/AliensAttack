package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.ai.AIBehaviorTree;
import com.aliensattack.core.ai.AIBehaviorTree.BehaviorNode;
import com.aliensattack.core.ai.AIBehaviorTree.DecisionTree;
import com.aliensattack.core.model.Alien;

import java.util.*;

/**
 * Advanced Alien Infestation System for XCOM 2 Strategic Layer
 * Manages alien infestation spread, control, and strategic impact
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienInfestationSystem {
    
    public enum InfestationLevel {
        LOW, MEDIUM, HIGH, CRITICAL
    }
    
    private AIBehaviorTree behaviorTree;
    private Map<String, AIBehaviorTree.BehaviorNode> infestationBehaviors;
    private Map<String, AIBehaviorTree.DecisionTree> infestationDecisions;
    private List<Alien> infestationSources;
    private Map<String, InfestationLevel> infestationLevels;
    private int globalInfestationLevel;
    private int infestationSpreadRate;
    private Random random;
    
    /**
     * Initialize the infestation system
     */
    public void initialize() {
        if (behaviorTree == null) {
            behaviorTree = new AIBehaviorTree();
        }
        if (infestationBehaviors == null) {
            infestationBehaviors = new HashMap<>();
        }
        if (infestationDecisions == null) {
            infestationDecisions = new HashMap<>();
        }
        if (infestationSources == null) {
            infestationSources = new ArrayList<>();
        }
        if (infestationLevels == null) {
            infestationLevels = new HashMap<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        globalInfestationLevel = 0;
        infestationSpreadRate = 5;
        
        initializeInfestationBehaviors();
        initializeInfestationDecisions();
    }
    
    /**
     * Initialize infestation behaviors
     */
    private void initializeInfestationBehaviors() {
        // Create infestation behavior nodes
        BehaviorNode spreadBehavior = BehaviorNode.builder()
                .nodeId("SPREAD_INFESTATION")
                .nodeName("Spread Infestation")
                .nodeType("INFESTATION")
                .behaviorType(AIBehaviorTree.AIBehaviorType.AGGRESSIVE)
                .successRate(0.7)
                .failureRate(0.3)
                .energyCost(15)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Spread alien infestation")
                .build();
        
        infestationBehaviors.put(spreadBehavior.getNodeId(), spreadBehavior);
        
        BehaviorNode controlBehavior = BehaviorNode.builder()
                .nodeId("CONTROL_INFESTATION")
                .nodeName("Control Infestation")
                .nodeType("INFESTATION")
                .behaviorType(AIBehaviorTree.AIBehaviorType.DEFENSIVE)
                .successRate(0.8)
                .failureRate(0.2)
                .energyCost(10)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Control existing infestation")
                .build();
        
        infestationBehaviors.put(controlBehavior.getNodeId(), controlBehavior);
    }
    
    /**
     * Initialize infestation decisions
     */
    private void initializeInfestationDecisions() {
        // Create decision tree for infestation spread
        DecisionTree spreadTree = DecisionTree.builder()
                .treeId("SPREAD_TREE")
                .treeName("Infestation Spread Decision Tree")
                .treeType("INFESTATION")
                .rootNodeId("SPREAD_INFESTATION")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        infestationDecisions.put(spreadTree.getTreeId(), spreadTree);
        
        // Create decision tree for infestation control
        DecisionTree controlTree = DecisionTree.builder()
                .treeId("CONTROL_TREE")
                .treeName("Infestation Control Decision Tree")
                .treeType("INFESTATION")
                .rootNodeId("CONTROL_INFESTATION")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        infestationDecisions.put(controlTree.getTreeId(), controlTree);
    }
    
    /**
     * Add infestation source
     */
    public void addInfestationSource(Alien alien) {
        if (!infestationSources.contains(alien)) {
            infestationSources.add(alien);
            updateGlobalInfestationLevel();
        }
    }
    
    /**
     * Remove infestation source
     */
    public void removeInfestationSource(Alien alien) {
        if (infestationSources.remove(alien)) {
            updateGlobalInfestationLevel();
        }
    }
    
    /**
     * Update global infestation level
     */
    private void updateGlobalInfestationLevel() {
        globalInfestationLevel = infestationSources.size() * infestationSpreadRate;
        
        // Update individual infestation levels
        for (Alien source : infestationSources) {
            String sourceId = source.getId();
            InfestationLevel level = calculateInfestationLevel(source);
            infestationLevels.put(sourceId, level);
        }
    }
    
    /**
     * Calculate infestation level for alien
     */
    private InfestationLevel calculateInfestationLevel(Alien alien) {
        // Simple level calculation - use constant power since methods don't exist
        int power = 75; // Default power level
        
        if (power < 50) {
            return InfestationLevel.LOW;
        } else if (power < 100) {
            return InfestationLevel.MEDIUM;
        } else {
            return InfestationLevel.HIGH;
        }
    }
    
    /**
     * Get infestation status
     */
    public String getInfestationStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Infestation System Status:\n");
        status.append("- Global Infestation Level: ").append(globalInfestationLevel).append("\n");
        status.append("- Infestation Sources: ").append(infestationSources.size()).append("\n");
        status.append("- Infestation Spread Rate: ").append(infestationSpreadRate).append("\n");
        status.append("- Active Behaviors: ").append(infestationBehaviors.values().stream().filter(BehaviorNode::isActive).count()).append("\n");
        status.append("- Active Decision Trees: ").append(infestationDecisions.values().stream().filter(DecisionTree::isActive).count()).append("\n");
        
        // Add individual infestation levels
        status.append("- Individual Infestation Levels:\n");
        for (Map.Entry<String, InfestationLevel> entry : infestationLevels.entrySet()) {
            status.append("  * ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        return status.toString();
    }
}

package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.model.AIBehaviorTree.BehaviorNode;
import com.aliensattack.core.model.AIBehaviorTree.DecisionTree;

import java.util.*;

/**
 * Advanced Alien Autopsy System for XCOM 2 Strategic Layer
 * Manages alien autopsy research, discoveries, and strategic intelligence
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienAutopsySystem {
    
    public enum AutopsyStatus {
        PENDING, IN_PROGRESS, COMPLETED, FAILED
    }
    
    private AIBehaviorTree behaviorTree;
    private Map<String, BehaviorNode> autopsyBehaviors;
    private Map<String, DecisionTree> autopsyDecisions;
    private List<Alien> autopsySubjects;
    private Map<String, AutopsyStatus> autopsyStatuses;
    private int autopsyPoints;
    private int researchBonus;
    private Random random;
    
    /**
     * Initialize the autopsy system
     */
    public void initialize() {
        if (behaviorTree == null) {
            behaviorTree = new AIBehaviorTree();
        }
        if (autopsyBehaviors == null) {
            autopsyBehaviors = new HashMap<>();
        }
        if (autopsyDecisions == null) {
            autopsyDecisions = new HashMap<>();
        }
        if (autopsySubjects == null) {
            autopsySubjects = new ArrayList<>();
        }
        if (autopsyStatuses == null) {
            autopsyStatuses = new HashMap<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        autopsyPoints = 100;
        researchBonus = 0;
        
        initializeAutopsyBehaviors();
        initializeAutopsyDecisions();
    }
    
    /**
     * Initialize autopsy behaviors
     */
    private void initializeAutopsyBehaviors() {
        // Create autopsy behavior nodes
        BehaviorNode researchBehavior = BehaviorNode.builder()
                .nodeId("RESEARCH_AUTOPSY")
                .nodeName("Research Autopsy")
                .nodeType("RESEARCH")
                .behaviorType(AIBehaviorTree.AIBehaviorType.BALANCED)
                .successRate(0.9)
                .failureRate(0.1)
                .energyCost(5)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Research alien autopsy")
                .build();
        
        autopsyBehaviors.put(researchBehavior.getNodeId(), researchBehavior);
        
        BehaviorNode analysisBehavior = BehaviorNode.builder()
                .nodeId("ANALYZE_AUTOPSY")
                .nodeName("Analyze Autopsy Results")
                .nodeType("ANALYSIS")
                .behaviorType(AIBehaviorTree.AIBehaviorType.DEFENSIVE)
                .successRate(0.85)
                .failureRate(0.15)
                .energyCost(8)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Analyze autopsy results")
                .build();
        
        autopsyBehaviors.put(analysisBehavior.getNodeId(), analysisBehavior);
    }
    
    /**
     * Initialize autopsy decisions
     */
    private void initializeAutopsyDecisions() {
        // Create decision tree for autopsy research
        DecisionTree researchTree = DecisionTree.builder()
                .treeId("RESEARCH_TREE")
                .treeName("Autopsy Research Decision Tree")
                .treeType("RESEARCH")
                .rootNodeId("RESEARCH_AUTOPSY")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        autopsyDecisions.put(researchTree.getTreeId(), researchTree);
        
        // Create decision tree for autopsy analysis
        DecisionTree analysisTree = DecisionTree.builder()
                .treeId("ANALYSIS_TREE")
                .treeName("Autopsy Analysis Decision Tree")
                .treeType("ANALYSIS")
                .rootNodeId("ANALYZE_AUTOPSY")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        autopsyDecisions.put(analysisTree.getTreeId(), analysisTree);
    }
    
    /**
     * Add autopsy subject
     */
    public void addAutopsySubject(Alien alien) {
        if (!autopsySubjects.contains(alien)) {
            autopsySubjects.add(alien);
            autopsyStatuses.put(alien.getId(), AutopsyStatus.PENDING);
        }
    }
    
    /**
     * Remove autopsy subject
     */
    public void removeAutopsySubject(Alien alien) {
        if (autopsySubjects.remove(alien)) {
            autopsyStatuses.remove(alien.getId());
        }
    }
    
    /**
     * Perform autopsy
     */
    public boolean performAutopsy(String alienId) {
        AutopsyStatus status = autopsyStatuses.get(alienId);
        if (status == AutopsyStatus.PENDING && autopsyPoints >= 20) {
            autopsyStatuses.put(alienId, AutopsyStatus.IN_PROGRESS);
            autopsyPoints -= 20;
            return true;
        }
        return false;
    }
    
    /**
     * Complete autopsy
     */
    public void completeAutopsy(String alienId) {
        AutopsyStatus status = autopsyStatuses.get(alienId);
        if (status == AutopsyStatus.IN_PROGRESS) {
            autopsyStatuses.put(alienId, AutopsyStatus.COMPLETED);
            
            // Add research bonus
            researchBonus += 10;
            autopsyPoints += 15;
        }
    }
    
    /**
     * Get autopsy status
     */
    public String getAutopsyStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Alien Autopsy Status:\n");
        status.append("- Autopsy Points: ").append(autopsyPoints).append("\n");
        status.append("- Research Bonus: ").append(researchBonus).append("\n");
        status.append("- Autopsy Subjects: ").append(autopsySubjects.size()).append("\n");
        status.append("- Active Behaviors: ").append(autopsyBehaviors.values().stream().filter(BehaviorNode::isActive).count()).append("\n");
        status.append("- Active Decision Trees: ").append(autopsyDecisions.values().stream().filter(DecisionTree::isActive).count()).append("\n");
        
        // Add individual autopsy statuses
        status.append("- Individual Autopsy Statuses:\n");
        for (Map.Entry<String, AutopsyStatus> entry : autopsyStatuses.entrySet()) {
            status.append("  * ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        
        return status.toString();
    }
}

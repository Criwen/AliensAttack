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
 * Advanced AI Action System for XCOM 2 Tactical Combat
 * Manages AI actions, decision making, and tactical responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIAction {
    
    private AIBehaviorTree behaviorTree;
    private Map<String, BehaviorNode> actionBehaviors;
    private Map<String, DecisionTree> actionDecisions;
    private String actionId;
    private String actionName;
    private AIBehaviorTree.AIBehaviorType behaviorType;
    private int actionCost;
    private int actionDuration;
    private boolean isActive;
    private boolean isSuccessful;
    private Random random;
    
    /**
     * Initialize the AI action
     */
    public void initialize() {
        if (behaviorTree == null) {
            behaviorTree = new AIBehaviorTree();
        }
        if (actionBehaviors == null) {
            actionBehaviors = new HashMap<>();
        }
        if (actionDecisions == null) {
            actionDecisions = new HashMap<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        isActive = false;
        isSuccessful = false;
        
        initializeActionBehaviors();
        initializeActionDecisions();
    }
    
    /**
     * Initialize action behaviors
     */
    private void initializeActionBehaviors() {
        // Create action behavior nodes
        BehaviorNode executeBehavior = BehaviorNode.builder()
                .nodeId("EXECUTE_ACTION")
                .nodeName("Execute Action")
                .nodeType("EXECUTION")
                .behaviorType(behaviorType)
                .successRate(0.8)
                .failureRate(0.2)
                .energyCost(actionCost)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Execute AI action")
                .build();
        
        actionBehaviors.put(executeBehavior.getNodeId(), executeBehavior);
        
        BehaviorNode planBehavior = BehaviorNode.builder()
                .nodeId("PLAN_ACTION")
                .nodeName("Plan Action")
                .nodeType("PLANNING")
                .behaviorType(AIBehaviorTree.AIBehaviorType.BALANCED)
                .successRate(0.9)
                .failureRate(0.1)
                .energyCost(actionCost / 2)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Plan AI action")
                .build();
        
        actionBehaviors.put(planBehavior.getNodeId(), planBehavior);
    }
    
    /**
     * Initialize action decisions
     */
    private void initializeActionDecisions() {
        // Create decision tree for action execution
        DecisionTree executionTree = DecisionTree.builder()
                .treeId("EXECUTION_TREE")
                .treeName("Action Execution Decision Tree")
                .treeType("EXECUTION")
                .rootNodeId("EXECUTE_ACTION")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        actionDecisions.put(executionTree.getTreeId(), executionTree);
        
        // Create decision tree for action planning
        DecisionTree planningTree = DecisionTree.builder()
                .treeId("PLANNING_TREE")
                .treeName("Action Planning Decision Tree")
                .treeType("PLANNING")
                .rootNodeId("PLAN_ACTION")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        actionDecisions.put(planningTree.getTreeId(), planningTree);
    }
    
    /**
     * Execute action
     */
    public boolean executeAction() {
        if (!isActive && actionCost > 0) {
            isActive = true;
            
            // Simulate action execution
            boolean success = random.nextDouble() < 0.8; // 80% success rate
            isSuccessful = success;
            
            // Deactivate after execution
            isActive = false;
            
            return success;
        }
        return false;
    }
    
    /**
     * Get action status
     */
    public String getActionStatus() {
        StringBuilder status = new StringBuilder();
        status.append("AI Action Status:\n");
        status.append("- Action ID: ").append(actionId).append("\n");
        status.append("- Action Name: ").append(actionName).append("\n");
        status.append("- Behavior Type: ").append(behaviorType).append("\n");
        status.append("- Action Cost: ").append(actionCost).append("\n");
        status.append("- Action Duration: ").append(actionDuration).append("\n");
        status.append("- Is Active: ").append(isActive).append("\n");
        status.append("- Is Successful: ").append(isSuccessful).append("\n");
        status.append("- Active Behaviors: ").append(actionBehaviors.values().stream().filter(BehaviorNode::isActive).count()).append("\n");
        status.append("- Active Decision Trees: ").append(actionDecisions.values().stream().filter(DecisionTree::isActive).count()).append("\n");
        
        return status.toString();
    }
}



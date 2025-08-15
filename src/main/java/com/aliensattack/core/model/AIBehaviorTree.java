package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;

/**
 * AI Behavior Tree for advanced alien AI decision making.
 * Implements behavior tree pattern for complex AI behaviors.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIBehaviorTree {
    
    // AI Behavior Types
    public enum BehaviorType {
        AGGRESSIVE,      // Direct attack approach
        DEFENSIVE,       // Defensive positioning
        FLANKING,        // Attempt to flank enemies
        SUPPORT,         // Support other units
        RETREAT,         // Retreat when outmatched
        AMBUSH,          // Set up ambushes
        RECONNAISSANCE,  // Gather intelligence
        COORDINATION,    // Coordinate with other units
        ADAPTIVE,        // Adapt to player tactics
        SPECIALIST       // Use special abilities
    }
    
    // AI Learning States
    public enum LearningState {
        NOVICE,          // Basic behavior patterns
        EXPERIENCED,     // Learned from encounters
        VETERAN,         // Highly adaptive
        MASTER,          // Expert level tactics
        LEGENDARY        // Ultimate AI behavior
    }
    
    // AI Decision Nodes
    public enum DecisionNode {
        DETECT_ENEMY,    // Enemy detection
        ASSESS_THREAT,   // Threat assessment
        CHOOSE_ACTION,   // Action selection
        COORDINATE,      // Unit coordination
        ADAPT_TACTIC,    // Tactic adaptation
        LEARN_PATTERN,   // Pattern learning
        EXECUTE_ABILITY, // Ability execution
        POSITION_UNIT,   // Unit positioning
        RETREAT_CHECK,   // Retreat evaluation
        REINFORCE_CALL   // Call reinforcements
    }
    
    public enum AIBehaviorType {
        AGGRESSIVE, DEFENSIVE, BALANCED, STEALTH, SUPPORT
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BehaviorNode {
        private String nodeId;
        private String nodeName;
        private String nodeType;
        private AIBehaviorType behaviorType;
        private double successRate;
        private double failureRate;
        private int energyCost;
        private int energyGained;
        private boolean isActive;
        private boolean isSuccessful;
        private String behaviorDescription;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DecisionTree {
        private String treeId;
        private String treeName;
        private String treeType;
        private String rootNodeId;
        private Map<String, BehaviorNode> decisionNodes;
        private boolean isActive;
    }
    
    private String id;
    private BehaviorType primaryBehavior;
    private BehaviorType secondaryBehavior;
    private LearningState learningState;
    private Map<String, Integer> tacticMemory;
    private List<String> learnedPatterns;
    private int adaptationLevel;
    private int coordinationLevel;
    private boolean isAggressive;
    private boolean isDefensive;
    private boolean isSupportive;
    private boolean canRetreat;
    private boolean canReinforce;
    
    // Behavior Tree Methods
    public BehaviorType decidePrimaryBehavior(Unit unit, List<Unit> enemies, List<Unit> allies) {
        if (unit.getCurrentHealth() < unit.getMaxHealth() * 0.3) {
            return BehaviorType.RETREAT;
        }
        
        if (enemies.size() > allies.size() * 2) {
            return BehaviorType.DEFENSIVE;
        }
        
        if (unit.getAbilities().stream().anyMatch(ability -> ability.getName().contains("FLANKING"))) {
            return BehaviorType.FLANKING;
        }
        
        if (unit.getAbilities().stream().anyMatch(ability -> ability.getName().contains("SUPPORT"))) {
            return BehaviorType.SUPPORT;
        }
        
        return BehaviorType.AGGRESSIVE;
    }
    
    public BehaviorType decideSecondaryBehavior(Unit unit, List<Unit> enemies, List<Unit> allies) {
        if (primaryBehavior == BehaviorType.AGGRESSIVE) {
            return BehaviorType.COORDINATION;
        }
        
        if (primaryBehavior == BehaviorType.DEFENSIVE) {
            return BehaviorType.SUPPORT;
        }
        
        if (primaryBehavior == BehaviorType.FLANKING) {
            return BehaviorType.AMBUSH;
        }
        
        return BehaviorType.ADAPTIVE;
    }
    
    public void learnFromEncounter(String tactic, boolean wasSuccessful) {
        if (tacticMemory == null) {
            tacticMemory = new HashMap<>();
        }
        
        int currentValue = tacticMemory.getOrDefault(tactic, 0);
        if (wasSuccessful) {
            tacticMemory.put(tactic, currentValue + 1);
        } else {
            tacticMemory.put(tactic, currentValue - 1);
        }
        
        // Update learning state based on experience
        updateLearningState();
    }
    
    public void updateLearningState() {
        int totalExperience = tacticMemory.values().stream().mapToInt(Integer::intValue).sum();
        
        if (totalExperience >= 50) {
            learningState = LearningState.LEGENDARY;
        } else if (totalExperience >= 30) {
            learningState = LearningState.MASTER;
        } else if (totalExperience >= 15) {
            learningState = LearningState.VETERAN;
        } else if (totalExperience >= 5) {
            learningState = LearningState.EXPERIENCED;
        } else {
            learningState = LearningState.NOVICE;
        }
    }
    
    public String chooseOptimalTactic(Unit unit, List<Unit> enemies, List<Unit> allies) {
        // Analyze current situation
        int enemyCount = enemies.size();
        int allyCount = allies.size();
        double healthRatio = (double) unit.getCurrentHealth() / unit.getMaxHealth();
        
        // Choose tactic based on situation and learned patterns
        if (healthRatio < 0.3) {
            return "RETREAT";
        }
        
        if (enemyCount > allyCount * 2) {
            return "DEFENSIVE_POSITIONING";
        }
        
        if (unit.getAbilities().stream().anyMatch(ability -> ability.getName().contains("FLANKING")) && canFlank(enemies)) {
            return "FLANKING_ATTACK";
        }
        
        if (unit.getAbilities().stream().anyMatch(ability -> ability.getName().contains("SUPPORT")) && allies.stream().anyMatch(a -> a.getCurrentHealth() < a.getMaxHealth() * 0.5)) {
            return "SUPPORT_ALLY";
        }
        
        // Use learned patterns
        String bestLearnedTactic = findBestLearnedTactic();
        if (bestLearnedTactic != null) {
            return bestLearnedTactic;
        }
        
        return "STANDARD_ATTACK";
    }
    
    private boolean canFlank(List<Unit> enemies) {
        // Check if flanking is possible
        return enemies.stream().anyMatch(e -> e.getPosition() != null);
    }
    
    private String findBestLearnedTactic() {
        if (tacticMemory == null || tacticMemory.isEmpty()) {
            return null;
        }
        
        return tacticMemory.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    
    public void adaptToPlayerTactics(String playerTactic) {
        if (learnedPatterns == null) {
            learnedPatterns = new ArrayList<>();
        }
        
        if (!learnedPatterns.contains(playerTactic)) {
            learnedPatterns.add(playerTactic);
        }
        
        adaptationLevel++;
    }
    
    public void coordinateWithAllies(List<Unit> allies) {
        coordinationLevel++;
        
        // Coordinate tactics with allies
        if (!allies.isEmpty() && learnedPatterns != null) {
            // Share learned patterns with allies (simplified coordination)
            // In a real implementation, this would share patterns with ally AI
            // For now, we just increment coordination level
        }
    }
    
    public boolean shouldRetreat(Unit unit, List<Unit> enemies, List<Unit> allies) {
        if (!canRetreat) {
            return false;
        }
        
        double healthRatio = (double) unit.getCurrentHealth() / unit.getMaxHealth();
        int enemyCount = enemies.size();
        int allyCount = allies.size();
        
        return healthRatio < 0.3 || (enemyCount > allyCount * 3);
    }
    
    public boolean shouldCallReinforcements(Unit unit, List<Unit> enemies, List<Unit> allies) {
        if (!canReinforce) {
            return false;
        }
        
        int enemyCount = enemies.size();
        int allyCount = allies.size();
        
        return enemyCount > allyCount * 2;
    }
    
    public void executeBehavior(Unit unit, List<Unit> enemies, List<Unit> allies) {
        // Decide primary and secondary behaviors
        primaryBehavior = decidePrimaryBehavior(unit, enemies, allies);
        secondaryBehavior = decideSecondaryBehavior(unit, enemies, allies);
        
        // Choose optimal tactic
        String tactic = chooseOptimalTactic(unit, enemies, allies);
        
        // Execute the chosen tactic
        executeTactic(unit, tactic, enemies, allies);
        
        // Learn from the encounter
        learnFromEncounter(tactic, true); // Assume success for now
    }
    
    private void executeTactic(Unit unit, String tactic, List<Unit> enemies, List<Unit> allies) {
        switch (tactic) {
            case "RETREAT":
                executeRetreat(unit);
                break;
            case "DEFENSIVE_POSITIONING":
                executeDefensivePositioning(unit);
                break;
            case "FLANKING_ATTACK":
                executeFlankingAttack(unit, enemies);
                break;
            case "SUPPORT_ALLY":
                executeSupportAlly(unit, allies);
                break;
            case "STANDARD_ATTACK":
                executeStandardAttack(unit, enemies);
                break;
            default:
                executeStandardAttack(unit, enemies);
                break;
        }
    }
    
    private void executeRetreat(Unit unit) {
        // Move unit away from enemies
        if (unit.getPosition() != null) {
            // Find safe retreat position
            Position retreatPosition = findSafeRetreatPosition(unit);
            if (retreatPosition != null) {
                unit.setPosition(retreatPosition);
            }
        }
    }
    
    private void executeDefensivePositioning(Unit unit) {
        // Find defensive position with cover
        Position defensivePosition = findDefensivePosition(unit);
        if (defensivePosition != null) {
            unit.setPosition(defensivePosition);
        }
    }
    
    private void executeFlankingAttack(Unit unit, List<Unit> enemies) {
        // Find flanking position
        Position flankingPosition = findFlankingPosition(unit, enemies);
        if (flankingPosition != null) {
            unit.setPosition(flankingPosition);
        }
    }
    
    private void executeSupportAlly(Unit unit, List<Unit> allies) {
        // Find ally that needs support
        Unit allyInNeed = findAllyInNeed(allies);
        if (allyInNeed != null) {
            // Move to support position
            Position supportPosition = findSupportPosition(unit, allyInNeed);
            if (supportPosition != null) {
                unit.setPosition(supportPosition);
            }
        }
    }
    
    private void executeStandardAttack(Unit unit, List<Unit> enemies) {
        // Find closest enemy and attack
        Unit closestEnemy = findClosestEnemy(unit, enemies);
        if (closestEnemy != null) {
            // Move towards enemy
            Position attackPosition = findAttackPosition(unit, closestEnemy);
            if (attackPosition != null) {
                unit.setPosition(attackPosition);
            }
        }
    }
    
    // Helper methods for position finding
    private Position findSafeRetreatPosition(Unit unit) {
        // Implementation for finding safe retreat position
        return new Position(0, 0, 0);
    }
    
    private Position findDefensivePosition(Unit unit) {
        // Implementation for finding defensive position
        return new Position(0, 0, 0);
    }
    
    private Position findFlankingPosition(Unit unit, List<Unit> enemies) {
        // Implementation for finding flanking position
        return new Position(0, 0, 0);
    }
    
    private Unit findAllyInNeed(List<Unit> allies) {
        return allies.stream()
                .filter(ally -> ally.getCurrentHealth() < ally.getMaxHealth() * 0.5)
                .findFirst()
                .orElse(null);
    }
    
    private Position findSupportPosition(Unit unit, Unit ally) {
        // Implementation for finding support position
        return new Position(0, 0, 0);
    }
    
    private Unit findClosestEnemy(Unit unit, List<Unit> enemies) {
        return enemies.stream()
                .min((e1, e2) -> {
                    double dist1 = calculateDistance(unit.getPosition(), e1.getPosition());
                    double dist2 = calculateDistance(unit.getPosition(), e2.getPosition());
                    return Double.compare(dist1, dist2);
                })
                .orElse(null);
    }
    
    private Position findAttackPosition(Unit unit, Unit enemy) {
        // Implementation for finding attack position
        return new Position(0, 0, 0);
    }
    
    private double calculateDistance(Position pos1, Position pos2) {
        if (pos1 == null || pos2 == null) {
            return Double.MAX_VALUE;
        }
        
        double dx = pos1.getX() - pos2.getX();
        double dy = pos1.getY() - pos2.getY();
        double dz = pos1.getHeight() - pos2.getHeight();
        
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}

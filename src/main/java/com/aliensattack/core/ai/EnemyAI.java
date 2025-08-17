package com.aliensattack.core.ai;

import com.aliensattack.core.ai.interfaces.IEnemyAI;
import com.aliensattack.core.model.Alien;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.interfaces.ICombatManagerExtended;
import com.aliensattack.core.config.GameConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

/**
 * Advanced AI implementation for enemy aliens in tactical combat
 * Merged with AlienAI features for enhanced behavior and decision making
 */
@Getter
@Setter
@Log4j2
public class EnemyAI implements IEnemyAI {
    
    private Alien alien;
    private ITacticalField tacticalField;
    private ICombatManagerExtended combatManager;
    private int difficultyLevel;
    private Random random;
    
    // Enhanced AI state from AlienAI
    private AIBehaviorTree behaviorTree;
    private Map<String, AIBehaviorTree.BehaviorNode> aiBehaviors;
    private Map<String, AIBehaviorTree.DecisionTree> aiDecisions;
    private AIBehaviorTree.AIBehaviorType currentBehavior;
    private int intelligenceLevel;
    private int tacticalAwareness;
    private int aggressionLevel;
    
    // Basic AI state
    private Position lastKnownPlayerPosition;
    private List<Position> exploredPositions;
    private int turnsSinceLastSighting;
    private boolean isAggressive;
    private boolean isDefensive;
    
    // Cover detection system
    private CoverDetectionSystem coverDetectionSystem;
    
    // AI Learning and Adaptation
    private Map<String, Integer> successfulTactics;
    private Map<String, Integer> failedTactics;
    private List<String> learnedPatterns;
    private int adaptationLevel;
    private double learningRate;
    private int memoryCapacity;
    
    // Performance Optimization
    private Map<String, Object> decisionCache;
    private long lastDecisionTime;
    private int maxCalculationTime;
    private int cacheSize;
    private int maxPathfindingIterations;
    
    // Squad coordination system
    private SquadCoordinationSystem squadCoordination;
    
    public EnemyAI() {
        this.random = new Random();
        this.exploredPositions = new ArrayList<>();
        this.difficultyLevel = GameConfig.getInt("ai.enemy.difficulty.level", 5);
        this.isAggressive = true;
        this.isDefensive = false;
        
        // Initialize enhanced AI components
        this.intelligenceLevel = GameConfig.getAIIntelligenceLevel();
        this.tacticalAwareness = GameConfig.getAITacticalAwareness();
        this.aggressionLevel = GameConfig.getAIAggressionLevel();
        
        // Initialize AI learning and adaptation
        this.successfulTactics = new HashMap<>();
        this.failedTactics = new HashMap<>();
        this.learnedPatterns = new ArrayList<>();
        this.adaptationLevel = 1;
        this.learningRate = GameConfig.getAIMemoryLearningRate();
        this.memoryCapacity = GameConfig.getAIMemoryMaxPositions();
        
        // Initialize performance optimization
        this.decisionCache = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
                return size() > cacheSize;
            }
        };
        this.lastDecisionTime = 0;
        this.maxCalculationTime = GameConfig.getAIPerformanceMaxCalculationTime();
        this.cacheSize = GameConfig.getAIPerformanceDecisionCacheSize();
        this.maxPathfindingIterations = GameConfig.getAIPerformancePathfindingMaxIterations();
        
        initializeEnhancedAI();
    }
    
    /**
     * Initialize enhanced AI components from AlienAI
     */
    private void initializeEnhancedAI() {
        if (behaviorTree == null) {
            behaviorTree = new AIBehaviorTree();
        }
        if (aiBehaviors == null) {
            aiBehaviors = new HashMap<>();
        }
        if (aiDecisions == null) {
            aiDecisions = new HashMap<>();
        }
        
        initializeAIBehaviors();
        initializeAIDecisions();
    }
    
    /**
     * Initialize AI behaviors
     */
    private void initializeAIBehaviors() {
        // Create AI behavior nodes
        AIBehaviorTree.BehaviorNode combatBehavior = AIBehaviorTree.BehaviorNode.builder()
                .nodeId("COMBAT_BEHAVIOR")
                .nodeName("Combat Behavior")
                .nodeType("COMBAT")
                .behaviorType(AIBehaviorTree.AIBehaviorType.AGGRESSIVE)
                .successRate(0.8)
                .failureRate(0.2)
                .energyCost(15)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Combat-focused behavior")
                .build();
        
        aiBehaviors.put(combatBehavior.getNodeId(), combatBehavior);
        
        AIBehaviorTree.BehaviorNode stealthBehavior = AIBehaviorTree.BehaviorNode.builder()
                .nodeId("STEALTH_BEHAVIOR")
                .nodeName("Stealth Behavior")
                .nodeType("STEALTH")
                .behaviorType(AIBehaviorTree.AIBehaviorType.DEFENSIVE)
                .successRate(0.9)
                .failureRate(0.1)
                .energyCost(10)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Stealth-focused behavior")
                .build();
        
        aiBehaviors.put(stealthBehavior.getNodeId(), stealthBehavior);
        
        AIBehaviorTree.BehaviorNode supportBehavior = AIBehaviorTree.BehaviorNode.builder()
                .nodeId("SUPPORT_BEHAVIOR")
                .nodeName("Support Behavior")
                .nodeType("SUPPORT")
                .behaviorType(AIBehaviorTree.AIBehaviorType.SUPPORT)
                .successRate(0.7)
                .failureRate(0.3)
                .energyCost(12)
                .energyGained(0)
                .isActive(false)
                .isSuccessful(false)
                .behaviorDescription("Support-focused behavior")
                .build();
        
        aiBehaviors.put(supportBehavior.getNodeId(), supportBehavior);
    }
    
    /**
     * Initialize AI decisions
     */
    private void initializeAIDecisions() {
        // Create decision tree for combat
        AIBehaviorTree.DecisionTree combatTree = AIBehaviorTree.DecisionTree.builder()
                .treeId("COMBAT_TREE")
                .treeName("Combat Decision Tree")
                .treeType("COMBAT")
                .rootNodeId("COMBAT_BEHAVIOR")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        aiDecisions.put(combatTree.getTreeId(), combatTree);
        
        // Create decision tree for stealth
        AIBehaviorTree.DecisionTree stealthTree = AIBehaviorTree.DecisionTree.builder()
                .treeId("STEALTH_TREE")
                .treeName("Stealth Decision Tree")
                .treeType("STEALTH")
                .rootNodeId("STEALTH_BEHAVIOR")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        aiDecisions.put(stealthTree.getTreeId(), stealthTree);
        
        // Create decision tree for support
        AIBehaviorTree.DecisionTree supportTree = AIBehaviorTree.DecisionTree.builder()
                .treeId("SUPPORT_TREE")
                .treeName("Support Decision Tree")
                .treeType("SUPPORT")
                .rootNodeId("SUPPORT_BEHAVIOR")
                .decisionNodes(new HashMap<>())
                .isActive(true)
                .build();
        
        aiDecisions.put(supportTree.getTreeId(), supportTree);
    }
    
    @Override
    public void initialize(Alien alien) {
        this.alien = alien;
        log.debug("Initializing AI for alien: {} (Type: {})", alien.getName(), alien.getAlienType());
        
        // Set AI behavior based on alien type
        setAIBehaviorByType(alien.getAlienType());
        
        // Update enhanced AI behavior
        updateBehavior();
    }
    
    @Override
    public void initializeWithUnit(com.aliensattack.core.model.Unit unit) {
        // For compatibility with any unit type
        if (unit.getUnitType() == com.aliensattack.core.enums.UnitType.ALIEN) {
            // Try to get alien-specific data if available
            try {
                // Check if we can access alien-specific methods
                if (canAccessAlienMethods(unit)) {
                    // Try to extract alien data and create proper alien
                    Alien alien = extractAlienData(unit);
                    if (alien != null) {
                        initialize(alien);
                        return;
                    }
                }
            } catch (Exception e) {
                log.warn("Failed to extract alien data from unit {}: {}", unit.getName(), e.getMessage());
            }
        }
        
        // Fallback: create a mock alien for AI initialization
        log.info("Initializing AI with unit {} (Type: {}) using fallback method", unit.getName(), unit.getUnitType());
        
        // Create a temporary alien reference for AI functionality
        // This allows the AI to work with any unit type while maintaining compatibility
        this.alien = createMockAlienFromUnit(unit);
        
        // Set default AI behavior
        setAIBehaviorByType(com.aliensattack.core.enums.AlienType.ADVENT_TROOPER);
        
        // Update enhanced AI behavior
        updateBehavior();
    }
    
    /**
     * Create a mock alien from unit data for AI compatibility
     */
    private Alien createMockAlienFromUnit(com.aliensattack.core.model.Unit unit) {
        // Create a mock alien with the unit's basic properties
        Alien mockAlien = new Alien(
            unit.getName(),
            unit.getMaxHealth(),
            unit.getMovementRange(),
            unit.getAttackRange(),
            unit.getAttackDamage(),
            com.aliensattack.core.enums.AlienType.ADVENT_TROOPER
        );
        
        // Copy position and other relevant properties
        mockAlien.setPosition(unit.getPosition());
        mockAlien.setCurrentHealth(unit.getCurrentHealth());
        mockAlien.setActionPoints(unit.getActionPoints());
        
        return mockAlien;
    }
    
    /**
     * Check if we can access alien-specific methods on the unit
     */
    private boolean canAccessAlienMethods(com.aliensattack.core.model.Unit unit) {
        try {
            // Try to access alien-specific methods using reflection or method calls
            // For now, just check if the unit has the basic required methods
            return unit.isAlive() && 
                   unit.getPosition() != null && 
                   unit.getMovementRange() > 0 &&
                   unit.getAttackRange() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Extract alien data from unit if possible
     */
    private Alien extractAlienData(com.aliensattack.core.model.Unit unit) {
        try {
            // Try to create an alien with the unit's data
            // This is a fallback for when we can't directly cast
            return createMockAlienFromUnit(unit);
        } catch (Exception e) {
            log.debug("Could not extract alien data from unit {}: {}", unit.getName(), e.getMessage());
            return null;
        }
    }
    
    @Override
    public void makeTurnDecision() {
        if (alien == null || !alien.isAlive()) {
            return;
        }
        
        log.debug("AI making turn decision for alien: {}", alien.getName());
        
        // Update AI state
        updateAIState();
        updateBehavior();
        
        // Check squad coordination if available
        if (squadCoordination != null) {
            SquadCoordinationSystem.Squad squad = squadCoordination.getSquadForUnit(alien);
            if (squad != null) {
                // Consider squad tactics in decision making
                SquadCoordinationSystem.SquadTactic activeTactic = squadCoordination.getActiveTactic(squad.getSquadId());
                if (activeTactic != null && activeTactic.canUse()) {
                    log.debug("AI considering squad tactic: {} for squad: {}", activeTactic.getName(), squad.getSquadId());
                    
                    // Execute squad tactic if appropriate
                    if (shouldExecuteSquadTactic(activeTactic, squad)) {
                        log.debug("AI executing squad tactic: {}", activeTactic.getName());
                        return;
                    }
                }
                
                // Get squad coordination data for tactical positioning
                SquadCoordinationSystem.SquadCoordinationData coordinationData = 
                    squadCoordination.getCoordinationData(squad.getSquadId());
                if (coordinationData != null) {
                    log.debug("AI using squad coordination data - Tactical advantage: {}, Coordination score: {}", 
                             coordinationData.getTacticalAdvantage(), coordinationData.getCoordinationScore());
                }
            }
        }
        
        // Make decision based on current situation and behavior tree
        if (shouldUseSpecialAbility()) {
            log.debug("AI decided to use special ability");
            return;
        }
        
        if (shouldAttack()) {
            log.debug("AI decided to attack");
            return;
        }
        
        if (shouldMove()) {
            log.debug("AI decided to move");
            return;
        }
        
        // Default: defensive stance
        log.debug("AI taking defensive stance");
    }
    
    /**
     * Determine if AI should execute a squad tactic
     */
    private boolean shouldExecuteSquadTactic(SquadCoordinationSystem.SquadTactic tactic, SquadCoordinationSystem.Squad squad) {
        // TODO: Implement sophisticated squad tactic execution logic
        // - Consider tactical situation
        // - Analyze enemy positions and threats
        // - Apply squad capabilities and formation
        
        // Basic logic: execute high priority tactics more often
        switch (tactic.getPriority()) {
            case HIGH:
                return random.nextDouble() < 0.8; // 80% chance for high priority
            case MEDIUM:
                return random.nextDouble() < 0.6; // 60% chance for medium priority
            case LOW:
                return random.nextDouble() < 0.4; // 40% chance for low priority
            default:
                return false;
        }
    }
    
    /**
     * Update AI behavior based on current situation
     */
    public void updateBehavior() {
        // Determine optimal behavior based on current situation
        AIBehaviorTree.AIBehaviorType newBehavior = determineOptimalBehavior();
        
        if (newBehavior != currentBehavior) {
            currentBehavior = newBehavior;
            activateBehavior(newBehavior);
        }
        
        // Update tactical awareness
        updateTacticalAwareness();
        
        // Apply learned adaptations
        applyLearnedAdaptations();
    }
    
    /**
     * Learn from successful action
     */
    public void learnFromSuccess(String tactic, String context) {
        String key = tactic + ":" + context;
        successfulTactics.put(key, successfulTactics.getOrDefault(key, 0) + 1);
        
        // Increase adaptation level
        adaptationLevel = Math.min(10, adaptationLevel + 1);
        
        // Learn pattern if successful enough times
        if (successfulTactics.get(key) >= 3) {
            String pattern = "SUCCESS:" + tactic + ":" + context;
            if (!learnedPatterns.contains(pattern)) {
                learnedPatterns.add(pattern);
                log.debug("AI learned successful pattern: {}", pattern);
            }
        }
        
        log.debug("AI learned from success: {} in context: {}", tactic, context);
    }
    
    /**
     * Learn from failed action
     */
    public void learnFromFailure(String tactic, String context) {
        String key = tactic + ":" + context;
        failedTactics.put(key, failedTactics.getOrDefault(key, 0) + 1);
        
        // Decrease adaptation level slightly
        adaptationLevel = Math.max(1, adaptationLevel - 1);
        
        log.debug("AI learned from failure: {} in context: {}", tactic, context);
    }
    
    /**
     * Apply learned adaptations to behavior
     */
    private void applyLearnedAdaptations() {
        if (adaptationLevel < 3) {
            return; // Too early to apply adaptations
        }
        
        // Adjust behavior based on learned patterns
        for (String pattern : learnedPatterns) {
            if (pattern.startsWith("SUCCESS:")) {
                String[] parts = pattern.split(":");
                if (parts.length >= 3) {
                    String tactic = parts[1];
                    String context = parts[2];
                    applyTacticAdaptation(tactic, context);
                }
            }
        }
    }
    
    /**
     * Apply specific tactic adaptation
     */
    private void applyTacticAdaptation(String tactic, String context) {
        switch (tactic) {
            case "ATTACK" -> {
                if (context.contains("FLANK")) {
                    // Increase flanking preference
                    aggressionLevel = Math.min(100, aggressionLevel + 5);
                }
            }
            case "MOVE" -> {
                if (context.contains("COVER")) {
                    // Increase cover preference
                    tacticalAwareness = Math.min(100, tacticalAwareness + 3);
                }
            }
            case "DEFEND" -> {
                if (context.contains("LOW_HEALTH")) {
                    // Increase defensive behavior when low health
                    isDefensive = true;
                    isAggressive = false;
                }
            }
        }
    }
    
    /**
     * Get success rate for a specific tactic
     */
    public double getTacticSuccessRate(String tactic, String context) {
        String key = tactic + ":" + context;
        int successes = successfulTactics.getOrDefault(key, 0);
        int failures = failedTactics.getOrDefault(key, 0);
        int total = successes + failures;
        
        if (total == 0) {
            return 0.5; // Default 50% success rate
        }
        
        return (double) successes / total;
    }
    
    /**
     * Check if AI should use a specific tactic based on learning
     */
    public boolean shouldUseTactic(String tactic, String context) {
        double successRate = getTacticSuccessRate(tactic, context);
        double threshold = 0.6; // 60% success rate threshold
        
        // Adjust threshold based on adaptation level
        threshold -= (adaptationLevel * 0.02); // More adaptive AI is more willing to try tactics
        
        return successRate >= threshold;
    }
    
    /**
     * Get AI learning statistics
     */
    public String getLearningStats() {
        StringBuilder stats = new StringBuilder();
        stats.append("AI Learning Statistics:\n");
        stats.append("- Adaptation Level: ").append(adaptationLevel).append("\n");
        stats.append("- Learning Rate: ").append(learningRate).append("\n");
        stats.append("- Learned Patterns: ").append(learnedPatterns.size()).append("\n");
        stats.append("- Successful Tactics: ").append(successfulTactics.size()).append("\n");
        stats.append("- Failed Tactics: ").append(failedTactics.size()).append("\n");
        
        return stats.toString();
    }
    
    /**
     * Check calculation time limit
     */
    private boolean isCalculationTimeExceeded() {
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - lastDecisionTime;
        return elapsed > maxCalculationTime;
    }
    
    /**
     * Cache decision result
     */
    private void cacheDecision(String key, Object result) {
        if (decisionCache.size() >= cacheSize) {
            // Remove oldest entry (handled by LinkedHashMap)
            decisionCache.remove(decisionCache.keySet().iterator().next());
        }
        decisionCache.put(key, result);
    }
    
    /**
     * Get cached decision result
     */
    private Object getCachedDecision(String key) {
        return decisionCache.get(key);
    }
    
    /**
     * Check if decision is cached
     */
    private boolean isDecisionCached(String key) {
        return decisionCache.containsKey(key);
    }
    
    /**
     * Clear decision cache
     */
    public void clearDecisionCache() {
        decisionCache.clear();
        log.debug("AI decision cache cleared");
    }
    
    /**
     * Get performance statistics
     */
    public String getPerformanceStats() {
        StringBuilder stats = new StringBuilder();
        stats.append("AI Performance Statistics:\n");
        stats.append("- Cache Size: ").append(decisionCache.size()).append("/").append(cacheSize).append("\n");
        stats.append("- Max Calculation Time: ").append(maxCalculationTime).append("ms\n");
        stats.append("- Max Pathfinding Iterations: ").append(maxPathfindingIterations).append("\n");
        stats.append("- Last Decision Time: ").append(lastDecisionTime).append("ms\n");
        
        return stats.toString();
    }
    
    /**
     * Optimized pathfinding with iteration limits
     */
    private List<Position> calculateOptimizedPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        int iterations = 0;
        
        // Simple A* inspired pathfinding with iteration limit
        PriorityQueue<PathNode> openSet = new PriorityQueue<>();
        Set<Position> closedSet = new HashSet<>();
        
        PathNode startNode = new PathNode(from, null, 0, calculateHeuristic(from, to));
        openSet.add(startNode);
        
        while (!openSet.isEmpty() && iterations < maxPathfindingIterations) {
            PathNode current = openSet.poll();
            iterations++;
            
            if (current.position.equals(to)) {
                // Reconstruct path
                return reconstructPath(current);
            }
            
            closedSet.add(current.position);
            
            // Check neighbors
            for (Position neighbor : getNeighbors(current.position)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }
                
                double newCost = current.gCost + 1;
                PathNode neighborNode = new PathNode(neighbor, current, newCost, calculateHeuristic(neighbor, to));
                
                if (!openSet.contains(neighborNode)) {
                    openSet.add(neighborNode);
                }
            }
        }
        
        // Fallback to simple path if optimization fails
        log.debug("Pathfinding optimization failed, using fallback for {} -> {}", from, to);
        return calculatePath(from, to);
    }
    
    /**
     * Path node for A* pathfinding
     */
    private static class PathNode implements Comparable<PathNode> {
        Position position;
        PathNode parent;
        double gCost;
        double hCost;
        
        PathNode(Position position, PathNode parent, double gCost, double hCost) {
            this.position = position;
            this.parent = parent;
            this.gCost = gCost;
            this.hCost = hCost;
        }
        
        double getFCost() {
            return gCost + hCost;
        }
        
        @Override
        public int compareTo(PathNode other) {
            return Double.compare(this.getFCost(), other.getFCost());
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            PathNode pathNode = (PathNode) obj;
            return Objects.equals(position, pathNode.position);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(position);
        }
    }
    
    /**
     * Calculate heuristic for A* pathfinding
     */
    private double calculateHeuristic(Position from, Position to) {
        // Manhattan distance
        return Math.abs(to.getX() - from.getX()) + Math.abs(to.getY() - from.getY());
    }
    
    /**
     * Get neighboring positions
     */
    private List<Position> getNeighbors(Position position) {
        List<Position> neighbors = new ArrayList<>();
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        
        for (int i = 0; i < 4; i++) {
            int newX = position.getX() + dx[i];
            int newY = position.getY() + dy[i];
            
            if (newX >= 0 && newX < tacticalField.getWidth() &&
                newY >= 0 && newY < tacticalField.getHeight()) {
                Position neighbor = new Position(newX, newY);
                if (isPositionAccessible(neighbor)) {
                    neighbors.add(neighbor);
                }
            }
        }
        
        return neighbors;
    }
    
    /**
     * Reconstruct path from A* pathfinding
     */
    private List<Position> reconstructPath(PathNode endNode) {
        List<Position> path = new ArrayList<>();
        PathNode current = endNode;
        
        while (current != null) {
            path.add(0, current.position);
            current = current.parent;
        }
        
        return path;
    }
    
    /**
     * Determine optimal behavior
     */
    private AIBehaviorTree.AIBehaviorType determineOptimalBehavior() {
        // Enhanced behavior determination logic
        if (aggressionLevel > 80 || isAggressive) {
            return AIBehaviorTree.AIBehaviorType.AGGRESSIVE;
        } else if (intelligenceLevel > 70) {
            return AIBehaviorTree.AIBehaviorType.BALANCED;
        } else if (isDefensive) {
            return AIBehaviorTree.AIBehaviorType.DEFENSIVE;
        } else {
            return AIBehaviorTree.AIBehaviorType.STEALTH;
        }
    }
    
    /**
     * Activate behavior
     */
    private void activateBehavior(AIBehaviorTree.AIBehaviorType behaviorType) {
        // Deactivate all behaviors
        for (AIBehaviorTree.BehaviorNode behavior : aiBehaviors.values()) {
            behavior.setActive(false);
        }
        
        // Activate appropriate behavior
        for (AIBehaviorTree.BehaviorNode behavior : aiBehaviors.values()) {
            if (behavior.getBehaviorType() == behaviorType) {
                behavior.setActive(true);
                break;
            }
        }
        
        log.debug("AI activated behavior: {}", behaviorType);
    }
    
    /**
     * Update tactical awareness
     */
    private void updateTacticalAwareness() {
        // Increase tactical awareness based on intelligence and experience
        int awarenessGain = intelligenceLevel / 10;
        if (turnsSinceLastSighting > 0) {
            awarenessGain += turnsSinceLastSighting / 2; // Learn from exploration
        }
        tacticalAwareness = Math.min(100, tacticalAwareness + awarenessGain);
    }
    
    /**
     * Get AI status
     */
    public String getAIStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Alien AI Status:\n");
        status.append("- Alien Type: ").append(alien != null ? alien.getAlienType() : "None").append("\n");
        status.append("- Current Behavior: ").append(currentBehavior).append("\n");
        status.append("- Intelligence Level: ").append(intelligenceLevel).append("\n");
        status.append("- Tactical Awareness: ").append(tacticalAwareness).append("\n");
        status.append("- Aggression Level: ").append(aggressionLevel).append("\n");
        status.append("- Difficulty Level: ").append(difficultyLevel).append("\n");
        status.append("- Adaptation Level: ").append(adaptationLevel).append("\n");
        status.append("- Active Behaviors: ").append(aiBehaviors.values().stream().filter(AIBehaviorTree.BehaviorNode::isActive).count()).append("\n");
        status.append("- Active Decision Trees: ").append(aiDecisions.values().stream().filter(AIBehaviorTree.DecisionTree::isActive).count()).append("\n");
        status.append("- Cover Detection: ").append(coverDetectionSystem != null ? "Enabled" : "Disabled").append("\n");
        status.append("- Decision Cache: ").append(decisionCache.size()).append("/").append(cacheSize).append("\n");
        
        return status.toString();
    }
    
    @Override
    public boolean executeAction() {
        if (alien == null || !alien.isAlive()) {
            return false;
        }
        
        try {
            // Execute the decided action
            if (shouldUseSpecialAbility()) {
                return executeSpecialAbility();
            } else if (shouldAttack()) {
                return executeAttack();
            } else if (shouldMove()) {
                return executeMove();
            } else {
                return executeDefend();
            }
        } catch (Exception e) {
            log.error("Error executing AI action for alien {}: {}", alien.getName(), e.getMessage());
            return false;
        }
    }
    
    @Override
    public Position calculateBestMovePosition() {
        if (alien == null || tacticalField == null) {
            return null;
        }
        
        Position currentPos = alien.getPosition();
        if (currentPos == null) {
            return null;
        }
        
        // Find best strategic position
        List<Position> validPositions = getValidMovePositions();
        if (validPositions.isEmpty()) {
            return currentPos; // Stay in place
        }
        
        // Score positions based on AI strategy
        Position bestPosition = validPositions.stream()
            .max(Comparator.comparingInt(this::scorePosition))
            .orElse(currentPos);
        
        log.debug("AI calculated best move position: {} -> {}", currentPos, bestPosition);
        return bestPosition;
    }
    
    @Override
    public List<Unit> findBestTargets() {
        if (alien == null || tacticalField == null) {
            return new ArrayList<>();
        }
        
        // Find all player units in range
        List<Unit> targets = tacticalField.getAllUnits().stream()
            .filter(unit -> unit.getUnitType() == com.aliensattack.core.enums.UnitType.SOLDIER)
            .filter(unit -> unit.isAlive())
            .filter(unit -> isInAttackRange(unit))
            .collect(Collectors.toList());
        
        // Sort by priority (closest, weakest, etc.)
        targets.sort((t1, t2) -> {
            int priority1 = calculateTargetPriority(t1);
            int priority2 = calculateTargetPriority(t2);
            return Integer.compare(priority2, priority1); // Higher priority first
        });
        
        log.debug("AI found {} potential targets", targets.size());
        return targets;
    }
    
    @Override
    public boolean shouldUseSpecialAbility() {
        if (alien == null || alien.getPsionicAbilities().isEmpty()) {
            return false;
        }
        
        // Check if we have enough action points and energy
        if (alien.getActionPoints() < 2) {
            return false;
        }
        
        // Check if there are good targets for psionic abilities
        List<Unit> targets = findBestTargets();
        if (targets.isEmpty()) {
            return false;
        }
        
        // Random chance based on difficulty and situation
        double chance = calculateSpecialAbilityChance();
        return random.nextDouble() < chance;
    }
    
    @Override
    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    
    @Override
    public void setTacticalField(ITacticalField field) {
        this.tacticalField = field;
        
        // Initialize cover detection system when tactical field is set
        if (field != null) {
            this.coverDetectionSystem = new CoverDetectionSystem(field);
            log.debug("Cover detection system initialized for AI: {}", alien != null ? alien.getName() : "Unknown");
        }
    }
    
    /**
     * Set cover detection system
     */
    public void setCoverDetectionSystem(CoverDetectionSystem coverDetectionSystem) {
        this.coverDetectionSystem = coverDetectionSystem;
    }
    
    /**
     * Set squad coordination system
     */
    public void setSquadCoordinationSystem(SquadCoordinationSystem squadCoordination) {
        this.squadCoordination = squadCoordination;
    }
    
    @Override
    public void setCombatManager(ICombatManagerExtended combatManager) {
        this.combatManager = combatManager;
    }
    
    // Private helper methods
    
    private void setAIBehaviorByType(AlienType alienType) {
        switch (alienType) {
            case ADVENT_MUTON -> {
                isAggressive = true;
                isDefensive = false;
                difficultyLevel = Math.min(difficultyLevel + 2, 10);
            }
            case ADVENT_PRIEST -> {
                isAggressive = false;
                isDefensive = true;
                difficultyLevel = Math.min(difficultyLevel + 3, 10);
            }
            case ADVENT_BERSERKER -> {
                isAggressive = true;
                isDefensive = false;
                difficultyLevel = Math.min(difficultyLevel + 1, 10);
            }
            case ADVENT_ARCHON -> {
                isAggressive = true;
                isDefensive = true;
                difficultyLevel = Math.min(difficultyLevel + 2, 10);
            }
            default -> {
                // Keep default behavior
            }
        }
    }
    
    private void updateAIState() {
        // Update last known player position
        List<Unit> visiblePlayers = findBestTargets();
        if (!visiblePlayers.isEmpty()) {
            lastKnownPlayerPosition = visiblePlayers.get(0).getPosition();
            turnsSinceLastSighting = 0;
        } else {
            turnsSinceLastSighting++;
        }
        
        // Update explored positions
        if (alien.getPosition() != null) {
            exploredPositions.add(alien.getPosition());
        }
    }
    
    private boolean shouldAttack() {
        if (alien.getActionPoints() < 1) {
            return false;
        }
        
        List<Unit> targets = findBestTargets();
        if (targets.isEmpty()) {
            return false;
        }
        
        // Aggressive AI attacks more often
        double attackChance = isAggressive ? 0.8 : 0.5;
        return random.nextDouble() < attackChance;
    }
    
    private boolean shouldMove() {
        if (alien.getActionPoints() < 1) {
            return false;
        }
        
        // Move if no targets in range or for tactical positioning
        List<Unit> targets = findBestTargets();
        if (targets.isEmpty()) {
            return true; // Move to find targets
        }
        
        // Move for better positioning
        Position bestPos = calculateBestMovePosition();
        if (bestPos != null && !bestPos.equals(alien.getPosition())) {
            return true;
        }
        
        return false;
    }
    
    private List<Position> getValidMovePositions() {
        if (alien == null || tacticalField == null) {
            return new ArrayList<>();
        }
        
        Position currentPos = alien.getPosition();
        if (currentPos == null) {
            return new ArrayList<>();
        }
        
        List<Position> validPositions = new ArrayList<>();
        int maxRange = alien.getMovementRange();
        
        for (int x = Math.max(0, currentPos.getX() - maxRange); 
             x <= Math.min(tacticalField.getWidth() - 1, currentPos.getX() + maxRange); x++) {
            for (int y = Math.max(0, currentPos.getY() - maxRange); 
                 y <= Math.min(tacticalField.getHeight() - 1, currentPos.getY() + maxRange); y++) {
                
                Position pos = new Position(x, y);
                int distance = Math.abs(x - currentPos.getX()) + Math.abs(y - currentPos.getY());
                
                if (distance <= maxRange && distance > 0 && isValidPosition(pos)) {
                    // Check if movement to this position is actually possible
                    if (canMoveToPosition(currentPos, pos)) {
                        validPositions.add(pos);
                    }
                }
            }
        }
        
        return validPositions;
    }
    
    /**
     * Check if movement to a position is possible
     */
    private boolean canMoveToPosition(Position from, Position to) {
        if (from.equals(to)) {
            return false;
        }
        
        // Check if we have enough action points
        if (alien.getActionPoints() < calculateMovementCost(from, to)) {
            return false;
        }
        
        // Check if there's a clear path
        if (!hasClearPath(from, to)) {
            return false;
        }
        
        // Check if the target position is accessible
        return isPositionAccessible(to);
    }
    
    /**
     * Calculate movement cost to a position
     */
    private int calculateMovementCost(Position from, Position to) {
        int baseCost = 1;
        
        // Calculate Manhattan distance
        int distance = Math.abs(to.getX() - from.getX()) + Math.abs(to.getY() - from.getY());
        
        // Add terrain cost if cover detection system is available
        if (coverDetectionSystem != null) {
            double coverValue = coverDetectionSystem.getCoverValueAt(to);
            if (coverValue > 0.5) {
                baseCost += 1; // Difficult terrain costs more
            }
        }
        
        // Height difference cost
        if (alien.getHeight() != 0) {
            baseCost += Math.abs(alien.getHeight());
        }
        
        return Math.max(baseCost, distance);
    }
    
    /**
     * Check if there's a clear path between two positions
     */
    private boolean hasClearPath(Position from, Position to) {
        // Simple line-of-sight check
        List<Position> path = calculatePath(from, to);
        
        for (Position pos : path) {
            if (!isPositionAccessible(pos)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Calculate path between two positions (simple line algorithm)
     */
    private List<Position> calculatePath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        
        int x0 = from.getX();
        int y0 = from.getY();
        int x1 = to.getX();
        int y1 = to.getY();
        
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        
        int x = x0;
        int y = y0;
        
        while (true) {
            path.add(new Position(x, y));
            
            if (x == x1 && y == y1) {
                break;
            }
            
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
        
        return path;
    }
    
    /**
     * Check if a position is accessible
     */
    private boolean isPositionAccessible(Position position) {
        if (tacticalField == null) {
            return false;
        }
        
        // Check bounds
        if (position.getX() < 0 || position.getX() >= tacticalField.getWidth() ||
            position.getY() < 0 || position.getY() >= tacticalField.getHeight()) {
            return false;
        }
        
        // Check if occupied by another unit
        Unit unitAtPosition = tacticalField.getUnitAt(position);
        if (unitAtPosition != null) {
            return false;
        }
        
        // Check if position is blocked by terrain (if cover detection available)
        if (coverDetectionSystem != null) {
            // For now, assume all positions are accessible
            // In the future, this could check for blocked terrain
            return true;
        }
        
        return true;
    }
    
    private boolean isValidPosition(Position position) {
        if (tacticalField == null) {
            return false;
        }
        
        // Check if position is within bounds
        if (position.getX() < 0 || position.getX() >= tacticalField.getWidth() ||
            position.getY() < 0 || position.getY() >= tacticalField.getHeight()) {
            return false;
        }
        
        // Check if position is occupied
        Unit unitAtPosition = tacticalField.getUnitAt(position);
        return unitAtPosition == null;
    }
    
    private int scorePosition(Position position) {
        int score = 0;
        
        // Higher score for positions closer to players
        if (lastKnownPlayerPosition != null) {
            int distanceToPlayer = Math.abs(position.getX() - lastKnownPlayerPosition.getX()) + 
                                  Math.abs(position.getY() - lastKnownPlayerPosition.getY());
            score += (20 - distanceToPlayer); // Closer is better
        }
        
        // Bonus for unexplored positions
        if (!exploredPositions.contains(position)) {
            score += 5;
        }
        
        // Bonus for defensive positions (near cover, etc.)
        if (hasCover(position)) {
            score += 3;
        }
        
        return score;
    }
    
    private boolean hasCover(Position position) {
        if (coverDetectionSystem != null) {
            return coverDetectionSystem.hasCover(position);
        }
        // Fallback: return false if cover detection system not available
        return false;
    }
    
    private boolean isInAttackRange(Unit target) {
        if (alien == null || target == null) {
            return false;
        }
        
        Position alienPos = alien.getPosition();
        Position targetPos = target.getPosition();
        
        if (alienPos == null || targetPos == null) {
            return false;
        }
        
        int distance = Math.abs(alienPos.getX() - targetPos.getX()) + 
                      Math.abs(alienPos.getY() - targetPos.getY());
        
        return distance <= alien.getAttackRange();
    }
    
    private int calculateTargetPriority(Unit target) {
        int priority = 0;
        
        // Closer targets get higher priority
        Position alienPos = alien.getPosition();
        Position targetPos = target.getPosition();
        
        if (alienPos != null && targetPos != null) {
            int distance = Math.abs(alienPos.getX() - targetPos.getX()) + 
                          Math.abs(alienPos.getY() - targetPos.getY());
            priority += (20 - distance); // Closer is better
        }
        
        // Weaker targets get higher priority
        priority += (100 - target.getCurrentHealth());
        
        // Targets with low action points get higher priority
        priority += (10 - target.getActionPoints());
        
        return priority;
    }
    
    private double calculateSpecialAbilityChance() {
        double baseChance = 0.3;
        
        // Increase chance based on difficulty
        baseChance += (difficultyLevel * 0.05);
        
        // Increase chance if we have good targets
        List<Unit> targets = findBestTargets();
        if (targets.size() >= 2) {
            baseChance += 0.2; // Multiple targets
        }
        
        // Decrease chance if we're low on health
        if (alien.getCurrentHealth() < alien.getMaxHealth() * 0.3) {
            baseChance -= 0.1; // Defensive mode
        }
        
        return Math.min(Math.max(baseChance, 0.1), 0.9); // Clamp between 0.1 and 0.9
    }
    
    private boolean executeSpecialAbility() {
        if (alien.getPsionicAbilities().isEmpty()) {
            return false;
        }
        
        // Select random psionic ability
        var ability = alien.getPsionicAbilities().get(random.nextInt(alien.getPsionicAbilities().size()));
        
        log.debug("AI executing special ability: {} for alien: {}", ability.getName(), alien.getName());
        
        // TODO: Implement actual ability execution
        // For now, just consume action points
        alien.spendActionPoints(2);
        
        return true;
    }
    
    private boolean executeAttack() {
        List<Unit> targets = findBestTargets();
        if (targets.isEmpty()) {
            return false;
        }
        
        Unit target = targets.get(0); // Attack highest priority target
        log.debug("AI attacking target: {} with alien: {}", target.getName(), alien.getName());
        
        // TODO: Implement actual attack logic
        // For now, just consume action points
        alien.spendActionPoints(1);
        
        return true;
    }
    
    private boolean executeMove() {
        Position targetPos = calculateBestMovePosition();
        if (targetPos == null || targetPos.equals(alien.getPosition())) {
            return false;
        }
        
        log.debug("AI moving alien: {} to position: {}", alien.getName(), targetPos);
        
        // TODO: Implement actual movement logic
        // For now, just consume action points
        alien.spendActionPoints(1);
        
        return true;
    }
    
    private boolean executeDefend() {
        log.debug("AI defending with alien: {}", alien.getName());
        
        // TODO: Implement defensive stance
        // For now, just consume action points
        alien.spendActionPoints(1);
        
        return true;
    }
}

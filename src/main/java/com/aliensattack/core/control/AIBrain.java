package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.actions.interfaces.IAction;
import com.aliensattack.core.model.GameContext;
import com.aliensattack.actions.ActionFactory;
import com.aliensattack.core.model.AIBehaviorTree;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

/**
 * AI brain implementation for computer-controlled units
 * Implements sophisticated decision-making using behavior trees and tactical analysis
 */
@Log4j2
public class AIBrain extends AbstractBrain {
    
    // AI-specific properties
    private final AIBehaviorTree behaviorTree;
    private final AIStrategy currentStrategy;
    private final int intelligenceLevel;
    private final double aggressionLevel;
    
    // Tactical memory
    private final List<String> knownEnemyPositions;
    private final List<String> tacticalAdvantages;
    private final Map<String, Integer> threatAssessments;
    
    // Decision making
    private long lastDecisionTime;
    private int consecutiveFailures;
    private boolean isLearning;
    
    public AIBrain(String brainId, int priority, int intelligenceLevel, double aggressionLevel) {
        super(brainId, BrainType.AI, priority);
        this.behaviorTree = new AIBehaviorTree();
        this.intelligenceLevel = Math.max(1, Math.min(10, intelligenceLevel)); // 1-10 scale
        this.aggressionLevel = Math.max(0.0, Math.min(1.0, aggressionLevel)); // 0.0-1.0 scale
        
        // Initialize with default strategy
        this.currentStrategy = determineInitialStrategy();
        
        // Initialize tactical memory
        this.knownEnemyPositions = new ArrayList<>();
        this.tacticalAdvantages = new ArrayList<>();
        this.threatAssessments = new HashMap<>();
        
        // Initialize decision making
        this.lastDecisionTime = 0;
        this.consecutiveFailures = 0;
        this.isLearning = true;
        
        log.info("AI Brain {} created with intelligence level {} and aggression {}", 
                brainId, intelligenceLevel, aggressionLevel);
    }
    
    @Override
    protected Optional<IAction> decideAction(GameContext context) {
        if (getControlledUnit() == null) {
            return Optional.empty();
        }
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Update tactical knowledge
            updateTacticalKnowledge(context);
            
            // Determine current strategy
            AIStrategy strategy = determineStrategy(context);
            
            // Select action based on strategy
            Optional<IAction> action = selectActionByStrategy(strategy, context);
            
            if (action.isPresent()) {
                log.debug("AI Brain {} selected action {} using strategy {}", 
                        getBrainId(), action.get().getActionType(), strategy);
                
                // Learn from decision
                if (isLearning) {
                    learnFromDecision(action.get(), context);
                }
            } else {
                log.warn("AI Brain {} failed to select action with strategy {}", 
                        getBrainId(), strategy);
                consecutiveFailures++;
            }
            
            lastDecisionTime = System.currentTimeMillis() - startTime;
            return action;
            
        } catch (Exception e) {
            log.error("AI Brain {} error during decision making", getBrainId(), e);
            consecutiveFailures++;
            return Optional.empty();
        }
    }
    
    @Override
    protected List<IAction> calculateAvailableActions() {
        if (getControlledUnit() == null) {
            return new ArrayList<>();
        }
        
        List<IAction> actions = new ArrayList<>();
        IUnit unit = getControlledUnit();
        
        // Add movement actions
        if (unit.canMove() && hasActionPoints(1.0)) {
            actions.add(ActionFactory.createMoveAction(unit, unit.getPosition()));
        }
        
        // Add attack actions
        if (unit.canAttack() && hasActionPoints(1.0)) {
            actions.add(ActionFactory.createAttackAction(unit, null));
        }
        
        // Add defensive actions
        if (hasActionPoints(1.0)) {
            actions.add(ActionFactory.createDefendAction(unit));
            actions.add(ActionFactory.createOverwatchAction(unit));
        }
        
        // Add utility actions
        if (hasActionPoints(0.5)) {
            actions.add(ActionFactory.createReloadAction(unit));
        }
        
        return actions;
    }
    
    @Override
    protected void onUpdate(GameContext context) {
        // Update behavior tree state
        behaviorTree.adaptToPlayerTactics("CURRENT_TACTIC");
        
        // Adapt strategy based on current situation
        adaptStrategy(context);
        
        // Learn from environment changes
        if (isLearning) {
            learnFromEnvironment(context);
        }
    }
    
    @Override
    protected void onNewTurn() {
        // Reset turn-specific state
        consecutiveFailures = 0;
        
        // Update learning based on performance
        updateLearningState();
        
        log.debug("AI Brain {} ready for new turn", getBrainId());
    }
    
    @Override
    public BrainState getState() {
        return getCurrentState();
    }
    
    // AI-specific methods
    
    /**
     * Get the current AI strategy
     */
    public AIStrategy getCurrentStrategy() {
        return currentStrategy;
    }
    
    /**
     * Get the intelligence level of this AI
     */
    public int getIntelligenceLevel() {
        return intelligenceLevel;
    }
    
    /**
     * Get the aggression level of this AI
     */
    public double getAggressionLevel() {
        return aggressionLevel;
    }
    
    /**
     * Enable or disable learning
     */
    public void setLearningEnabled(boolean enabled) {
        this.isLearning = enabled;
        log.info("Learning {} for AI Brain {}", enabled ? "enabled" : "disabled", getBrainId());
    }
    
    /**
     * Get the behavior tree
     */
    public AIBehaviorTree getBehaviorTree() {
        return behaviorTree;
    }
    
    // Private helper methods
    
    private AIStrategy determineInitialStrategy() {
        if (aggressionLevel > 0.7) {
            return AIStrategy.AGGRESSIVE;
        } else if (aggressionLevel < 0.3) {
            return AIStrategy.DEFENSIVE;
        } else {
            return AIStrategy.BALANCED;
        }
    }
    
    private AIStrategy determineStrategy(GameContext context) {
        IUnit unit = getControlledUnit();
        if (unit == null) {
            return currentStrategy;
        }
        
        // Health-based strategy adjustment
        double healthPercentage = (double) unit.getCurrentHealth() / unit.getMaxHealth();
        
        if (healthPercentage < 0.3) {
            return AIStrategy.SURVIVAL;
        } else if (healthPercentage < 0.6) {
            return AIStrategy.DEFENSIVE;
        }
        
        // Threat-based strategy adjustment
        int visibleThreats = context.getVisibleEnemies() != null ? context.getVisibleEnemies().size() : 0;
        
        if (visibleThreats > 3) {
            return AIStrategy.DEFENSIVE;
        } else if (visibleThreats == 0) {
            return AIStrategy.EXPLORATION;
        }
        
        // Resource-based strategy adjustment
        if (unit.getActionPoints() < 1.0) {
            return AIStrategy.CONSERVATIVE;
        }
        
        return currentStrategy;
    }
    
    private Optional<IAction> selectActionByStrategy(AIStrategy strategy, GameContext context) {
        List<IAction> availableActions = getAvailableActions();
        
        if (availableActions.isEmpty()) {
            return Optional.empty();
        }
        
        switch (strategy) {
            case AGGRESSIVE:
                return selectAggressiveAction(availableActions, context);
            case DEFENSIVE:
                return selectDefensiveAction(availableActions, context);
            case SURVIVAL:
                return selectSurvivalAction(availableActions, context);
            case EXPLORATION:
                return selectExplorationAction(availableActions, context);
            case CONSERVATIVE:
                return selectConservativeAction(availableActions, context);
            case BALANCED:
            default:
                return selectBalancedAction(availableActions, context);
        }
    }
    
    private Optional<IAction> selectAggressiveAction(List<IAction> actions, GameContext context) {
        // Prioritize attack actions
        Optional<IAction> attackAction = actions.stream()
                .filter(action -> action.getActionType().contains("ATTACK"))
                .findFirst();
        
        if (attackAction.isPresent()) {
            return attackAction;
        }
        
        // Then movement towards enemies
        Optional<IAction> moveAction = actions.stream()
                .filter(action -> action.getActionType().contains("MOVE"))
                .findFirst();
        
        return moveAction;
    }
    
    private Optional<IAction> selectDefensiveAction(List<IAction> actions, GameContext context) {
        // Prioritize defensive actions
        Optional<IAction> defensiveAction = actions.stream()
                .filter(action -> action.getActionType().contains("DEFEND") || 
                                action.getActionType().contains("OVERWATCH"))
                .findFirst();
        
        if (defensiveAction.isPresent()) {
            return defensiveAction;
        }
        
        // Then movement to cover
        Optional<IAction> moveAction = actions.stream()
                .filter(action -> action.getActionType().contains("MOVE"))
                .findFirst();
        
        return moveAction;
    }
    
    private Optional<IAction> selectSurvivalAction(List<IAction> actions, GameContext context) {
        // Prioritize defensive actions
        Optional<IAction> defensiveAction = actions.stream()
                .filter(action -> action.getActionType().contains("DEFEND"))
                .findFirst();
        
        if (defensiveAction.isPresent()) {
            return defensiveAction;
        }
        
        // Then movement away from threats
        Optional<IAction> moveAction = actions.stream()
                .filter(action -> action.getActionType().contains("MOVE"))
                .findFirst();
        
        return moveAction;
    }
    
    private Optional<IAction> selectExplorationAction(List<IAction> actions, GameContext context) {
        // Prioritize movement
        Optional<IAction> moveAction = actions.stream()
                .filter(action -> action.getActionType().contains("MOVE"))
                .findFirst();
        
        if (moveAction.isPresent()) {
            return moveAction;
        }
        
        // Then overwatch for safety
        Optional<IAction> overwatchAction = actions.stream()
                .filter(action -> action.getActionType().contains("OVERWATCH"))
                .findFirst();
        
        return overwatchAction;
    }
    
    private Optional<IAction> selectConservativeAction(List<IAction> actions, GameContext context) {
        // Prioritize low-cost actions
        Optional<IAction> lowCostAction = actions.stream()
                .filter(action -> action.getActionPointCost() <= 1)
                .findFirst();
        
        return lowCostAction;
    }
    
    private Optional<IAction> selectBalancedAction(List<IAction> actions, GameContext context) {
        // Use intelligence level to make balanced decisions
        if (intelligenceLevel > 7) {
            // High intelligence: analyze and choose best action
            return actions.stream()
                    .max(Comparator.comparingInt(action -> evaluateActionValue(action, context)));
        } else if (intelligenceLevel > 4) {
            // Medium intelligence: use some strategy
            if (Math.random() < 0.7) {
                return selectAggressiveAction(actions, context);
            } else {
                return selectDefensiveAction(actions, context);
            }
        } else {
            // Low intelligence: random choice
            int randomIndex = (int) (Math.random() * actions.size());
            return Optional.of(actions.get(randomIndex));
        }
    }
    
    private int evaluateActionValue(IAction action, GameContext context) {
        int value = 0;
        
        // Base value from action type
        if (action.getActionType().contains("ATTACK")) {
            value += 10;
        } else if (action.getActionType().contains("DEFEND")) {
            value += 8;
        } else if (action.getActionType().contains("MOVE")) {
            value += 6;
        } else if (action.getActionType().contains("OVERWATCH")) {
            value += 7;
        }
        
        // Adjust based on intelligence level
        value += intelligenceLevel;
        
        // Adjust based on aggression level
        if (action.getActionType().contains("ATTACK")) {
            value += (int) (aggressionLevel * 5);
        }
        
        return value;
    }
    
    private void updateTacticalKnowledge(GameContext context) {
        // Update known enemy positions
        if (context.getVisibleEnemies() != null) {
            context.getVisibleEnemies().forEach(enemy -> {
                String position = enemy.getPosition().toString();
                if (!knownEnemyPositions.contains(position)) {
                    knownEnemyPositions.add(position);
                }
            });
        }
        
        // Update threat assessments
        if (context.getThreatLevels() != null) {
            threatAssessments.putAll(context.getThreatLevels());
        }
    }
    
    private void adaptStrategy(GameContext context) {
        // Adapt strategy based on current situation
        if (consecutiveFailures > 3) {
            // Switch to more conservative strategy
            log.debug("AI Brain {} adapting strategy due to failures", getBrainId());
        }
    }
    
    private void learnFromDecision(IAction action, GameContext context) {
        // Simple learning: remember successful actions
        if (action.isSuccessful()) {
            log.debug("AI Brain {} learned from successful action {}", getBrainId(), action.getActionType());
        }
    }
    
    private void learnFromEnvironment(GameContext context) {
        // Learn from environmental changes
        if (context.getCurrentWeather() != GameContext.WeatherType.CLEAR) {
            log.debug("AI Brain {} adapting to weather conditions", getBrainId());
        }
    }
    
    private void updateLearningState() {
        // Adjust learning based on performance
        if (consecutiveFailures > 5) {
            isLearning = true; // Force learning when performing poorly
        } else if (consecutiveFailures < 2) {
            isLearning = false; // Reduce learning when performing well
        }
    }
    
    // AI Strategy enumeration
    public enum AIStrategy {
        AGGRESSIVE,      // Focus on attacking and advancing
        DEFENSIVE,       // Focus on defense and positioning
        SURVIVAL,        // Focus on staying alive
        EXPLORATION,     // Focus on discovering new areas
        CONSERVATIVE,    // Focus on resource conservation
        BALANCED         // Balanced approach
    }
}

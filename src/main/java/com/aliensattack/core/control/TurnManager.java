package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import com.aliensattack.core.model.GameContext;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Manages turn-based gameplay with automatic switching between player and enemy turns
 * Automatically activates enemy AI brains when it's their turn
 */
@Log4j2
public class TurnManager {
    
    private final BrainManager brainManager;
    private final GameContext gameContext;
    
    // Turn state
    private TurnPhase currentPhase;
    private int currentTurn;
    private boolean isPlayerTurn;
    
    // Turn timing
    private long turnStartTime;
    private long maxTurnDuration;
    private boolean enableTurnTimeLimit;
    
    // Turn statistics
    private int playerActionsThisTurn;
    private int enemyActionsThisTurn;
    private Map<String, Integer> unitActionsPerTurn;
    private int cumulativePlayerActions;
    private int cumulativeEnemyActions;
    
    public TurnManager(BrainManager brainManager, GameContext gameContext) {
        this.brainManager = brainManager;
        this.gameContext = gameContext;
        this.currentPhase = TurnPhase.PLAYER_TURN;
        this.currentTurn = 1;
        this.isPlayerTurn = true;
        this.turnStartTime = System.currentTimeMillis();
        this.maxTurnDuration = 30000; // 30 seconds default
        this.enableTurnTimeLimit = true;
        this.unitActionsPerTurn = new ConcurrentHashMap<>();
        this.cumulativePlayerActions = 0;
        this.cumulativeEnemyActions = 0;
        
        log.info("Turn Manager initialized");
    }
    
    /**
     * Start a new turn
     */
    public void startNewTurn() {
        currentTurn++;
        currentPhase = TurnPhase.PLAYER_TURN;
        isPlayerTurn = true;
        turnStartTime = System.currentTimeMillis();
        playerActionsThisTurn = 0;
        enemyActionsThisTurn = 0;
        unitActionsPerTurn.clear();
        
        // Reset all brains for new turn
        brainManager.resetAllBrainsForNewTurn();
        
        // Update game context
        gameContext.updateForNewTurn();
        
        log.info("🎯 Starting Turn {} - Player's Turn", currentTurn);
    }
    
    /**
     * End current player turn and start enemy turn
     */
    public void endPlayerTurn() {
        if (!isPlayerTurn) {
            log.warn("Cannot end player turn - it's not player's turn");
            return;
        }
        
        log.info("🏁 Player turn ended after {} actions", playerActionsThisTurn);
        
        // Switch to enemy turn
        currentPhase = TurnPhase.ENEMY_TURN;
        isPlayerTurn = false;
        turnStartTime = System.currentTimeMillis();
        
        // Activate all enemy AI brains
        activateEnemyAI();
        
        log.info("👾 Enemy turn started - AI brains activated");
    }
    
    /**
     * End current enemy turn and start player turn
     */
    public void endEnemyTurn() {
        if (isPlayerTurn) {
            log.warn("Cannot end enemy turn - it's not enemy's turn");
            return;
        }
        
        log.info("🏁 Enemy turn ended after {} actions", enemyActionsThisTurn);
        
        // Switch to player turn
        currentPhase = TurnPhase.PLAYER_TURN;
        isPlayerTurn = true;
        turnStartTime = System.currentTimeMillis();
        
        // Deactivate enemy AI brains
        deactivateEnemyAI();
        
        log.info("🎮 Player turn started - AI brains deactivated");
    }
    
    /**
     * Execute enemy AI turn automatically
     */
    public void executeEnemyTurn() {
        if (isPlayerTurn) {
            log.warn("Cannot execute enemy turn during player's turn");
            return;
        }
        
        log.info("🤖 Executing enemy AI turn...");
        
        // Get all active enemy AI brains
        List<IBrain> enemyBrains = getActiveEnemyBrains();
        
        if (enemyBrains.isEmpty()) {
            log.warn("No enemy AI brains found - ending enemy turn");
            endEnemyTurn();
            return;
        }
        
        // Execute each enemy brain in priority order
        for (IBrain brain : enemyBrains) {
            if (!brain.isReady()) {
                continue;
            }
            
            try {
                // Update brain with current game context
                brain.update(gameContext);
                
                // Select and execute action
                var action = brain.selectAction(gameContext);
                if (action.isPresent()) {
                    boolean success = brain.executeAction(action.get());
                    if (success) {
                        enemyActionsThisTurn++;
                        cumulativeEnemyActions++;
                        recordUnitAction(brain.getControlledUnit().getId());
                        log.debug("Enemy AI {} executed action: {}", 
                                brain.getBrainId(), action.get().getActionType());
                    }
                }
                
            } catch (Exception e) {
                log.error("Error executing enemy brain {}", brain.getBrainId(), e);
            }
        }
        
        log.info("🤖 Enemy AI turn completed - {} actions executed", enemyActionsThisTurn);
        
        // Check if enemy turn should end
        if (shouldEndEnemyTurn()) {
            endEnemyTurn();
        }
    }
    
    /**
     * Record an action for a unit
     */
    public void recordPlayerAction(String unitId) {
        playerActionsThisTurn++;
        cumulativePlayerActions++;
        recordUnitAction(unitId);
    }
    
    /**
     * Check if turn time limit exceeded
     */
    public boolean isTurnTimeExceeded() {
        if (!enableTurnTimeLimit) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        long turnDuration = currentTime - turnStartTime;
        
        return turnDuration > maxTurnDuration;
    }
    
    /**
     * Force end current turn due to time limit
     */
    public void forceEndTurn() {
        if (isPlayerTurn) {
            log.warn("⏰ Player turn time limit exceeded - forcing end");
            endPlayerTurn();
        } else {
            log.warn("⏰ Enemy turn time limit exceeded - forcing end");
            endEnemyTurn();
        }
    }
    
    /**
     * Get current turn information
     */
    public TurnInfo getCurrentTurnInfo() {
        long currentTime = System.currentTimeMillis();
        long turnDuration = currentTime - turnStartTime;
        long remainingTime = Math.max(0, maxTurnDuration - turnDuration);
        
        return TurnInfo.builder()
                .turnNumber(currentTurn)
                .phase(currentPhase)
                .isPlayerTurn(isPlayerTurn)
                .turnDuration(turnDuration)
                .remainingTime(remainingTime)
                .playerActions(playerActionsThisTurn)
                .enemyActions(enemyActionsThisTurn)
                .build();
    }
    
    /**
     * Activate all enemy AI brains
     */
    private void activateEnemyAI() {
        List<IBrain> enemyBrains = brainManager.getBrainsByType(IBrain.BrainType.AI);
        
        for (IBrain brain : enemyBrains) {
            if (brain.isControlling()) {
                // AI brain is already active and controlling a unit
                log.debug("Enemy AI brain {} already active", brain.getBrainId());
                continue;
            }
            
            // Find available enemy units for this brain
            var availableUnit = findAvailableEnemyUnit(brain);
            if (availableUnit.isPresent()) {
                boolean assigned = brainManager.assignBrainToUnit(brain.getBrainId(), availableUnit.get().getId());
                if (assigned) {
                    log.info("🎯 Activated enemy AI brain {} for unit {}", 
                            brain.getBrainId(), availableUnit.get().getId());
                }
            }
        }
    }
    
    /**
     * Deactivate all enemy AI brains
     */
    private void deactivateEnemyAI() {
        List<IBrain> enemyBrains = brainManager.getBrainsByType(IBrain.BrainType.AI);
        
        for (IBrain brain : enemyBrains) {
            if (brain.isControlling()) {
                brainManager.releaseUnitControl(brain.getBrainId());
                log.debug("Deactivated enemy AI brain {}", brain.getBrainId());
            }
        }
    }
    
    /**
     * Get all active enemy AI brains
     */
    private List<IBrain> getActiveEnemyBrains() {
        return brainManager.getBrainsByType(IBrain.BrainType.AI).stream()
                .filter(IBrain::isControlling)
                .collect(Collectors.toList());
    }
    
    /**
     * Find available enemy unit for AI brain
     */
    private java.util.Optional<com.aliensattack.core.interfaces.IUnit> findAvailableEnemyUnit(IBrain brain) {
        // Get all enemy units
        List<com.aliensattack.core.interfaces.IUnit> enemyUnits = gameContext.getEnemyUnits();
        
        // Find units not controlled by any brain
        return enemyUnits.stream()
                .filter(unit -> unit.isAlive() && unit.canTakeActions())
                .filter(unit -> !brainManager.getBrainControllingUnit(unit.getId()).isPresent())
                .findFirst();
    }
    
    /**
     * Check if enemy turn should end
     */
    private boolean shouldEndEnemyTurn() {
        // End turn if all enemy units have no action points
        List<com.aliensattack.core.interfaces.IUnit> enemyUnits = gameContext.getEnemyUnits();
        boolean allUnitsExhausted = enemyUnits.stream()
                .filter(unit -> unit.isAlive())
                .allMatch(unit -> unit.getActionPoints() <= 0);
        
        // End turn if time limit exceeded
        boolean timeExceeded = isTurnTimeExceeded();
        
        // End turn if no more actions possible
        boolean noActionsPossible = getActiveEnemyBrains().stream()
                .noneMatch(IBrain::isReady);
        
        return allUnitsExhausted || timeExceeded || noActionsPossible;
    }
    
    /**
     * Record action for a specific unit
     */
    private void recordUnitAction(String unitId) {
        unitActionsPerTurn.merge(unitId, 1, Integer::sum);
    }
    
    /**
     * Set turn time limit
     */
    public void setTurnTimeLimit(long milliseconds) {
        this.maxTurnDuration = Math.max(1000, milliseconds);
        log.info("Turn time limit set to {}ms", maxTurnDuration);
    }
    
    /**
     * Enable/disable turn time limit
     */
    public void setTurnTimeLimitEnabled(boolean enabled) {
        this.enableTurnTimeLimit = enabled;
        log.info("Turn time limit {}", enabled ? "enabled" : "disabled");
    }
    
    /**
     * Get turn statistics
     */
    public TurnStatistics getTurnStatistics() {
        int completedTurns = Math.max(1, currentTurn - 1);
        return TurnStatistics.builder()
                .totalTurns(currentTurn)
                .averagePlayerActionsPerTurn(cumulativePlayerActions > 0 ? 
                    (double) cumulativePlayerActions / completedTurns : 0.0)
                .averageEnemyActionsPerTurn(cumulativeEnemyActions > 0 ? 
                    (double) cumulativeEnemyActions / completedTurns : 0.0)
                .unitActionsThisTurn(new ConcurrentHashMap<>(unitActionsPerTurn))
                .build();
    }
    
    /**
     * Turn phases enumeration
     */
    public enum TurnPhase {
        PLAYER_TURN,    // Player's turn
        ENEMY_TURN,     // Enemy AI turn
        INTERRUPT,      // Interrupt phase (reactions, overwatch)
        TRANSITION      // Transition between turns
    }
    
    /**
     * Turn information data class
     */
    @lombok.Data
    @lombok.Builder
    public static class TurnInfo {
        private int turnNumber;
        private TurnPhase phase;
        private boolean isPlayerTurn;
        private long turnDuration;
        private long remainingTime;
        private int playerActions;
        private int enemyActions;
    }
    
    /**
     * Turn statistics data class
     */
    @lombok.Data
    @lombok.Builder
    public static class TurnStatistics {
        private int totalTurns;
        private double averagePlayerActionsPerTurn;
        private double averageEnemyActionsPerTurn;
        private Map<String, Integer> unitActionsThisTurn;
    }
} 

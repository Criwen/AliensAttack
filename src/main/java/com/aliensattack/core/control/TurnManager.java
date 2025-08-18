package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import com.aliensattack.core.model.GameContext;
import com.aliensattack.actions.interfaces.IAction;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.Optional;

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
            log.warn("Cannot execute enemy turn - it's player's turn");
            return;
        }
        
        log.info("🤖 Executing enemy AI turn");
        
        // Execute brains for the current game context
        brainManager.executeBrains(gameContext);
        
        // Count enemy actions from the execution
        enemyActionsThisTurn = brainManager.getBrainStatistics().get("activeBrains") != null ? 
            (Integer) brainManager.getBrainStatistics().get("activeBrains") : 0;
        
        cumulativeEnemyActions += enemyActionsThisTurn;
        log.info("🤖 Enemy turn completed with {} actions", enemyActionsThisTurn);
    }
    
    /**
     * Record a player action
     */
    public void recordPlayerAction(String unitId) {
        playerActionsThisTurn++;
        cumulativePlayerActions++;
        unitActionsPerTurn.merge(unitId, 1, Integer::sum);
        
        log.debug("📝 Player action recorded for unit {} (Total: {})", unitId, playerActionsThisTurn);
    }
    
    /**
     * Record an enemy action
     */
    public void recordEnemyAction(String unitId) {
        enemyActionsThisTurn++;
        cumulativeEnemyActions++;
        unitActionsPerTurn.merge(unitId, 1, Integer::sum);
        
        log.debug("📝 Enemy action recorded for unit {} (Total: {})", unitId, enemyActionsThisTurn);
    }
    
    /**
     * Check if turn time limit exceeded
     */
    public boolean isTurnTimeLimitExceeded() {
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
            log.warn("⏰ Forcing end of player turn due to time limit");
            endPlayerTurn();
        } else {
            log.warn("⏰ Forcing end of enemy turn due to time limit");
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
        boolean timeExceeded = isTurnTimeLimitExceeded();
        
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

    /**
     * Get current turn phase
     */
    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }
    
    /**
     * Check if it's currently player's turn
     */
    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }
    
    /**
     * Get current turn number
     */
    public int getCurrentTurn() {
        return currentTurn;
    }
    
    /**
     * Get turn start time
     */
    public long getTurnStartTime() {
        return turnStartTime;
    }
    
    /**
     * Get max turn duration
     */
    public long getMaxTurnDuration() {
        return maxTurnDuration;
    }
    
    /**
     * Check if turn time limit is enabled
     */
    public boolean isTurnTimeLimitEnabled() {
        return enableTurnTimeLimit;
    }
    
    /**
     * Get player actions this turn
     */
    public int getPlayerActionsThisTurn() {
        return playerActionsThisTurn;
    }
    
    /**
     * Get enemy actions this turn
     */
    public int getEnemyActionsThisTurn() {
        return enemyActionsThisTurn;
    }
    
    /**
     * Get unit actions per turn
     */
    public Map<String, Integer> getUnitActionsPerTurn() {
        return new ConcurrentHashMap<>(unitActionsPerTurn);
    }
    
    /**
     * Get cumulative player actions
     */
    public int getCumulativePlayerActions() {
        return cumulativePlayerActions;
    }
    
    /**
     * Get cumulative enemy actions
     */
    public int getCumulativeEnemyActions() {
        return cumulativeEnemyActions;
    }
    
    /**
     * Add unit to turn management
     */
    public void addUnit(com.aliensattack.core.interfaces.IUnit unit) {
        if (unit != null) {
            log.debug("Adding unit {} to turn management", unit.getId());
            // Note: This TurnManager doesn't maintain a unit list directly
            // Units are managed through the game context
        }
    }
    
    /**
     * Shutdown the turn manager and clean up resources
     */
    public void shutdown() {
        log.info("Shutting down Turn Manager...");
        
        try {
            // Deactivate all enemy AI brains
            deactivateEnemyAI();
            
            // Clear statistics
            unitActionsPerTurn.clear();
            playerActionsThisTurn = 0;
            enemyActionsThisTurn = 0;
            cumulativePlayerActions = 0;
            cumulativeEnemyActions = 0;
            
            log.info("Turn Manager shutdown completed");
            
        } catch (Exception e) {
            log.error("Error during Turn Manager shutdown", e);
        }
    }
} 

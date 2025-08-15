package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import com.aliensattack.core.model.GameContext;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Turn System
 * Tests turn switching, AI brain activation, and turn flow
 */
@Log4j2
class TurnSystemTest {
    
    private GameContext gameContext;
    private BrainManager brainManager;
    private TurnManager turnManager;
    
    @BeforeEach
    void setUp() {
        gameContext = GameContext.createDefault();
        brainManager = new BrainManager();
        turnManager = new TurnManager(brainManager, gameContext);
    }
    
    @Test
    @DisplayName("Should start with player turn")
    void shouldStartWithPlayerTurn() {
        // Given: TurnManager is initialized
        
        // When: Getting current turn info
        var turnInfo = turnManager.getCurrentTurnInfo();
        
        // Then: Should be player's turn
        assertTrue(turnInfo.isPlayerTurn());
        assertEquals(TurnManager.TurnPhase.PLAYER_TURN, turnInfo.getPhase());
        assertEquals(1, turnInfo.getTurnNumber());
    }
    
    @Test
    @DisplayName("Should switch to enemy turn when player turn ends")
    void shouldSwitchToEnemyTurnWhenPlayerTurnEnds() {
        // Given: Player turn is active
        
        // When: Ending player turn
        turnManager.endPlayerTurn();
        
        // Then: Should be enemy turn
        var turnInfo = turnManager.getCurrentTurnInfo();
        assertFalse(turnInfo.isPlayerTurn());
        assertEquals(TurnManager.TurnPhase.ENEMY_TURN, turnInfo.getPhase());
    }
    
    @Test
    @DisplayName("Should switch back to player turn when enemy turn ends")
    void shouldSwitchBackToPlayerTurnWhenEnemyTurnEnds() {
        // Given: Enemy turn is active
        turnManager.endPlayerTurn();
        
        // When: Ending enemy turn
        turnManager.endEnemyTurn();
        
        // Then: Should be player turn again
        var turnInfo = turnManager.getCurrentTurnInfo();
        assertTrue(turnInfo.isPlayerTurn());
        assertEquals(TurnManager.TurnPhase.PLAYER_TURN, turnInfo.getPhase());
    }
    
    @Test
    @DisplayName("Should increment turn number when starting new turn")
    void shouldIncrementTurnNumberWhenStartingNewTurn() {
        // Given: Initial turn is 1
        
        // When: Starting new turn
        turnManager.startNewTurn();
        
        // Then: Turn number should be incremented
        var turnInfo = turnManager.getCurrentTurnInfo();
        assertEquals(2, turnInfo.getTurnNumber());
    }
    
    @Test
    @DisplayName("Should record player actions correctly")
    void shouldRecordPlayerActionsCorrectly() {
        // Given: Player turn is active
        
        // When: Recording player actions
        turnManager.recordPlayerAction("SOLDIER_1");
        turnManager.recordPlayerAction("SOLDIER_2");
        
        // Then: Actions should be recorded
        var turnInfo = turnManager.getCurrentTurnInfo();
        assertEquals(2, turnInfo.getPlayerActions());
    }
    
    @Test
    @DisplayName("Should activate enemy AI brains during enemy turn")
    void shouldActivateEnemyAIBrainsDuringEnemyTurn() {
        // Given: AI brains are registered
        AIBrain enemyBrain = BrainFactory.createAIBrain(6, 0.7, 4);
        brainManager.registerBrain(enemyBrain);
        
        // When: Starting enemy turn
        turnManager.endPlayerTurn();
        
        // Then: AI brain should be active
        List<IBrain> aiBrains = brainManager.getBrainsByType(IBrain.BrainType.AI);
        assertFalse(aiBrains.isEmpty());
    }
    
    @Test
    @DisplayName("Should deactivate enemy AI brains when switching to player turn")
    void shouldDeactivateEnemyAIBrainsWhenSwitchingToPlayerTurn() {
        // Given: Enemy turn is active with AI brains
        AIBrain enemyBrain = BrainFactory.createAIBrain(6, 0.7, 4);
        brainManager.registerBrain(enemyBrain);
        turnManager.endPlayerTurn();
        
        // When: Ending enemy turn
        turnManager.endEnemyTurn();
        
        // Then: AI brains should be deactivated
        List<IBrain> aiBrains = brainManager.getBrainsByType(IBrain.BrainType.AI);
        for (IBrain brain : aiBrains) {
            assertFalse(brain.isControlling());
        }
    }
    
    @Test
    @DisplayName("Should handle turn time limits correctly")
    void shouldHandleTurnTimeLimitsCorrectly() {
        // Given: Turn time limit is set
        turnManager.setTurnTimeLimit(1000); // 1 second
        
        // When: Waiting for time to exceed
        try {
            Thread.sleep(1100); // Wait 1.1 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Then: Time limit should be exceeded
        assertTrue(turnManager.isTurnTimeExceeded());
    }
    
    @Test
    @DisplayName("Should provide correct turn statistics")
    void shouldProvideCorrectTurnStatistics() {
        // Given: Some actions have been performed
        turnManager.recordPlayerAction("SOLDIER_1");
        turnManager.recordPlayerAction("SOLDIER_2");
        
        // When: Getting turn statistics
        var stats = turnManager.getTurnStatistics();
        
        // Then: Statistics should be correct
        assertEquals(1, stats.getTotalTurns());
        assertEquals(2.0, stats.getAveragePlayerActionsPerTurn());
        assertEquals(0.0, stats.getAverageEnemyActionsPerTurn());
    }
    
    @Test
    @DisplayName("Should not allow ending player turn during enemy turn")
    void shouldNotAllowEndingPlayerTurnDuringEnemyTurn() {
        // Given: Enemy turn is active
        turnManager.endPlayerTurn();
        
        // When: Trying to end player turn during enemy turn
        turnManager.endPlayerTurn();
        
        // Then: Should still be enemy turn
        var turnInfo = turnManager.getCurrentTurnInfo();
        assertFalse(turnInfo.isPlayerTurn());
        assertEquals(TurnManager.TurnPhase.ENEMY_TURN, turnInfo.getPhase());
    }
    
    @Test
    @DisplayName("Should not allow ending enemy turn during player turn")
    void shouldNotAllowEndingEnemyTurnDuringPlayerTurn() {
        // Given: Player turn is active
        
        // When: Trying to end enemy turn during player turn
        turnManager.endEnemyTurn();
        
        // Then: Should still be player turn
        var turnInfo = turnManager.getCurrentTurnInfo();
        assertTrue(turnInfo.isPlayerTurn());
        assertEquals(TurnManager.TurnPhase.PLAYER_TURN, turnInfo.getPhase());
    }
}

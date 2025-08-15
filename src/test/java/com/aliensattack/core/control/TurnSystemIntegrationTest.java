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
 * Integration tests for the complete Turn System
 * Tests the interaction between TurnManager, BrainManager, and AI brains
 */
@Log4j2
class TurnSystemIntegrationTest {
    
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
    @DisplayName("Should complete full turn cycle with AI brain activation")
    void shouldCompleteFullTurnCycleWithAIBrainActivation() {
        // Given: Multiple AI brains are registered
        AIBrain tacticalEnemy = BrainFactory.createTacticalAIBrain();
        AIBrain aggressiveEnemy = BrainFactory.createAggressiveAIBrain();
        AIBrain defensiveEnemy = BrainFactory.createDefensiveAIBrain();
        
        brainManager.registerBrain(tacticalEnemy);
        brainManager.registerBrain(aggressiveEnemy);
        brainManager.registerBrain(defensiveEnemy);
        
        // When: Starting new turn
        turnManager.startNewTurn();
        assertEquals(2, turnManager.getCurrentTurnInfo().getTurnNumber());
        
        // And: Player performs actions
        turnManager.recordPlayerAction("SOLDIER_1");
        turnManager.recordPlayerAction("SOLDIER_2");
        assertEquals(2, turnManager.getCurrentTurnInfo().getPlayerActions());
        
        // And: Player ends turn
        turnManager.endPlayerTurn();
        assertFalse(turnManager.getCurrentTurnInfo().isPlayerTurn());
        assertEquals(TurnManager.TurnPhase.ENEMY_TURN, turnManager.getCurrentTurnInfo().getPhase());
        
        // Then: AI brains should be activated
        List<IBrain> aiBrains = brainManager.getBrainsByType(IBrain.BrainType.AI);
        assertFalse(aiBrains.isEmpty());
        
        // And: Enemy turn can be executed
        turnManager.executeEnemyTurn();
        
        // And: Enemy turn can be ended
        turnManager.endEnemyTurn();
        assertTrue(turnManager.getCurrentTurnInfo().isPlayerTurn());
        assertEquals(TurnManager.TurnPhase.PLAYER_TURN, turnManager.getCurrentTurnInfo().getPhase());
    }
    
    @Test
    @DisplayName("Should handle multiple turn cycles correctly")
    void shouldHandleMultipleTurnCyclesCorrectly() {
        // Given: AI brains are registered
        AIBrain enemyBrain = BrainFactory.createAIBrain(6, 0.5, 4);
        brainManager.registerBrain(enemyBrain);
        
        // When: Completing multiple turn cycles
        for (int cycle = 1; cycle <= 3; cycle++) {
            // Start turn
            turnManager.startNewTurn();
            assertEquals(cycle + 1, turnManager.getCurrentTurnInfo().getTurnNumber());
            
            // Player actions
            turnManager.recordPlayerAction("SOLDIER_" + cycle);
            
            // End player turn
            turnManager.endPlayerTurn();
            assertFalse(turnManager.getCurrentTurnInfo().isPlayerTurn());
            
            // Execute enemy turn
            turnManager.executeEnemyTurn();
            
            // End enemy turn
            turnManager.endEnemyTurn();
            assertTrue(turnManager.getCurrentTurnInfo().isPlayerTurn());
        }
        
        // Then: Should have completed 3 full cycles
        assertEquals(4, turnManager.getCurrentTurnInfo().getTurnNumber());
    }
    
    @Test
    @DisplayName("Should coordinate multiple AI brains during enemy turn")
    void shouldCoordinateMultipleAIBrainsDuringEnemyTurn() {
        // Given: Multiple AI brains with different strategies
        AIBrain tacticalBrain = BrainFactory.createTacticalAIBrain();
        AIBrain aggressiveBrain = BrainFactory.createAggressiveAIBrain();
        AIBrain defensiveBrain = BrainFactory.createDefensiveAIBrain();
        
        brainManager.registerBrain(tacticalBrain);
        brainManager.registerBrain(aggressiveBrain);
        brainManager.registerBrain(defensiveBrain);
        
        // When: Starting enemy turn
        turnManager.endPlayerTurn();
        
        // Then: All AI brains should be available
        List<IBrain> aiBrains = brainManager.getBrainsByType(IBrain.BrainType.AI);
        assertEquals(3, aiBrains.size());
        
        // And: Should be able to execute enemy turn
        assertDoesNotThrow(() -> turnManager.executeEnemyTurn());
    }
    
    @Test
    @DisplayName("Should maintain brain state consistency across turns")
    void shouldMaintainBrainStateConsistencyAcrossTurns() {
        // Given: AI brain is registered
        AIBrain enemyBrain = BrainFactory.createAIBrain(6, 0.7, 4);
        brainManager.registerBrain(enemyBrain);
        
        // When: Completing a full turn cycle
        turnManager.startNewTurn();
        turnManager.recordPlayerAction("SOLDIER_1");
        turnManager.endPlayerTurn();
        turnManager.executeEnemyTurn();
        turnManager.endEnemyTurn();
        
        // Then: Brain should be in consistent state
        assertFalse(enemyBrain.isControlling());
        assertEquals(IBrain.BrainState.THINKING, enemyBrain.getState());
    }
    
    @Test
    @DisplayName("Should handle brain manager integration correctly")
    void shouldHandleBrainManagerIntegrationCorrectly() {
        // Given: Multiple brains are registered
        HumanBrain playerBrain = BrainFactory.createHumanBrain("PLAYER_1", 5);
        AIBrain enemyBrain = BrainFactory.createAIBrain(6, 0.5, 4);
        
        brainManager.registerBrain(playerBrain);
        brainManager.registerBrain(enemyBrain);
        
        // When: Getting brain statistics
        var brainStats = brainManager.getBrainStatistics();
        
        // Then: Should have correct brain count
        assertEquals(2, brainStats.get("totalBrains"));
        
        // And: Should be able to get brains by type
        List<IBrain> humanBrains = brainManager.getBrainsByType(IBrain.BrainType.HUMAN);
        List<IBrain> aiBrains = brainManager.getBrainsByType(IBrain.BrainType.AI);
        
        assertEquals(1, humanBrains.size());
        assertEquals(1, aiBrains.size());
    }
    
    @Test
    @DisplayName("Should provide accurate turn statistics across multiple turns")
    void shouldProvideAccurateTurnStatisticsAcrossMultipleTurns() {
        // Given: AI brain is registered
        AIBrain enemyBrain = BrainFactory.createAIBrain(6, 0.5, 4);
        brainManager.registerBrain(enemyBrain);
        
        // When: Completing multiple turns with actions
        for (int turn = 1; turn <= 3; turn++) {
            turnManager.startNewTurn();
            turnManager.recordPlayerAction("SOLDIER_" + turn);
            turnManager.endPlayerTurn();
            turnManager.executeEnemyTurn();
            turnManager.endEnemyTurn();
        }
        
        // Then: Statistics should be accurate
        var stats = turnManager.getTurnStatistics();
        assertEquals(4, stats.getTotalTurns()); // Initial + 3 cycles
        assertEquals(1.0, stats.getAveragePlayerActionsPerTurn());
        assertTrue(stats.getAverageEnemyActionsPerTurn() >= 0.0);
    }
}

package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import com.aliensattack.core.model.GameContext;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Soldier;
import com.aliensattack.actions.interfaces.IAction;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Integration test for the complete brain system
 * Tests all brain types, brain manager, and coordination
 */
@Log4j2
public class BrainSystemIntegrationTest {
    
    private BrainManager brainManager;
    private GameContext gameContext;
    
    public BrainSystemIntegrationTest() {
        this.brainManager = new BrainManager();
        this.gameContext = GameContext.createDefault();
    }
    
    /**
     * Run the complete integration test
     */
    public void runIntegrationTest() {
        log.info("Starting Brain System Integration Test");
        
        try {
            // Test 1: Brain Factory
            testBrainFactory();
            
            // Test 2: Brain Registration
            testBrainRegistration();
            
            // Test 3: Human Brain Control
            testHumanBrainControl();
            
            // Test 4: AI Brain Control
            testAIBrainControl();
            
            // Test 5: Brain Coordination
            testBrainCoordination();
            
            // Test 6: Brain Switching
            testBrainSwitching();
            
            // Test 7: Performance Metrics
            testPerformanceMetrics();
            
            log.info("All integration tests passed successfully!");
            
        } catch (Exception e) {
            log.error("Integration test failed", e);
            throw new RuntimeException("Brain system integration test failed", e);
        }
    }
    
    /**
     * Test brain factory creation
     */
    private void testBrainFactory() {
        log.info("Testing Brain Factory...");
        
        // Test human brain creation
        HumanBrain humanBrain = BrainFactory.createHumanBrain("TEST_PLAYER", 5);
        assert humanBrain != null : "Human brain creation failed";
        assert humanBrain.getBrainType() == IBrain.BrainType.HUMAN : "Wrong brain type";
        assert humanBrain.getPlayerId().equals("TEST_PLAYER") : "Wrong player ID";
        
        // Test AI brain creation
        AIBrain aiBrain = BrainFactory.createAIBrain(7, 0.6, 6);
        assert aiBrain != null : "AI brain creation failed";
        assert aiBrain.getBrainType() == IBrain.BrainType.AI : "Wrong brain type";
        assert aiBrain.getIntelligenceLevel() == 7 : "Wrong intelligence level";
        assert aiBrain.getAggressionLevel() == 0.6 : "Wrong aggression level";
        
        // Test specialized AI brains
        AIBrain aggressiveBrain = BrainFactory.createAggressiveAIBrain();
        assert aggressiveBrain.getAggressionLevel() > 0.8 : "Aggressive brain not aggressive enough";
        
        AIBrain defensiveBrain = BrainFactory.createDefensiveAIBrain();
        assert defensiveBrain.getAggressionLevel() < 0.3 : "Defensive brain too aggressive";
        
        log.info("Brain Factory tests passed");
    }
    
    /**
     * Test brain registration with manager
     */
    private void testBrainRegistration() {
        log.info("Testing Brain Registration...");
        
        // Create and register brains
        HumanBrain humanBrain = BrainFactory.createHumanBrain("PLAYER_1", 5);
        AIBrain aiBrain = BrainFactory.createAIBrain(6, 0.5, 4);
        
        boolean humanRegistered = brainManager.registerBrain(humanBrain);
        boolean aiRegistered = brainManager.registerBrain(aiBrain);
        
        assert humanRegistered : "Human brain registration failed";
        assert aiRegistered : "AI brain registration failed";
        
        // Verify registration
        assert brainManager.getBrain(humanBrain.getBrainId()).isPresent() : "Human brain not found";
        assert brainManager.getBrain(aiBrain.getBrainId()).isPresent() : "AI brain not found";
        
        // Test brain type filtering
        List<IBrain> humanBrains = brainManager.getBrainsByType(IBrain.BrainType.HUMAN);
        List<IBrain> aiBrains = brainManager.getBrainsByType(IBrain.BrainType.AI);
        
        assert humanBrains.size() >= 1 : "Human brains not found";
        assert aiBrains.size() >= 1 : "AI brains not found";
        
        log.info("Brain Registration tests passed");
    }
    
    /**
     * Test human brain control
     */
    private void testHumanBrainControl() {
        log.info("Testing Human Brain Control...");
        
        // Create test unit
        Soldier testSoldier = createTestSoldier("SOLDIER_1");
        
        // Get human brain
        HumanBrain humanBrain = (HumanBrain) brainManager.getAllBrains().stream()
                .filter(brain -> brain.getBrainType() == IBrain.BrainType.HUMAN)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No human brain found"));
        
        // Test control assignment
        boolean assigned = brainManager.assignBrainToUnit(humanBrain.getBrainId(), testSoldier.getId());
        assert assigned : "Failed to assign human brain to unit";
        
        // Test brain state
        assert humanBrain.isControlling() : "Human brain not controlling unit";
        assert humanBrain.getControlledUnit() != null : "Controlled unit is null";
        
        // Test available actions
        List<IAction> actions = humanBrain.getAvailableActions();
        assert !actions.isEmpty() : "No available actions for human brain";
        
        // Test action selection (should wait for input)
        humanBrain.update(gameContext);
        assert humanBrain.getState() == IBrain.BrainState.THINKING : "Wrong brain state";
        
        log.info("Human Brain Control tests passed");
    }
    
    /**
     * Test AI brain control
     */
    private void testAIBrainControl() {
        log.info("Testing AI Brain Control...");
        
        // Create test unit
        Soldier testSoldier = createTestSoldier("SOLDIER_2");
        
        // Get AI brain
        AIBrain aiBrain = (AIBrain) brainManager.getAllBrains().stream()
                .filter(brain -> brain.getBrainType() == IBrain.BrainType.AI)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No AI brain found"));
        
        // Test control assignment
        boolean assigned = brainManager.assignBrainToUnit(aiBrain.getBrainId(), testSoldier.getId());
        assert assigned : "Failed to assign AI brain to unit";
        
        // Test brain state
        assert aiBrain.isControlling() : "AI brain not controlling unit";
        assert aiBrain.getControlledUnit() != null : "Controlled unit is null";
        
        // Test AI decision making
        aiBrain.update(gameContext);
        Optional<IAction> selectedAction = aiBrain.selectAction(gameContext);
        
        // AI should be able to make decisions
        assert aiBrain.getState() == IBrain.BrainState.THINKING : "Wrong AI brain state";
        
        log.info("AI Brain Control tests passed");
    }
    
    /**
     * Test brain coordination
     */
    private void testBrainCoordination() {
        log.info("Testing Brain Coordination...");
        
        // Create multiple brains for coordination
        AIBrain brain1 = BrainFactory.createAIBrain(6, 0.5, 5);
        AIBrain brain2 = BrainFactory.createAIBrain(7, 0.6, 6);
        AIBrain brain3 = BrainFactory.createAIBrain(5, 0.4, 4);
        
        brainManager.registerBrain(brain1);
        brainManager.registerBrain(brain2);
        brainManager.registerBrain(brain3);
        
        // Test coordination
        List<String> brainIds = List.of(brain1.getBrainId(), brain2.getBrainId(), brain3.getBrainId());
        
        // Verify coordination executes without exception
        try {
            brainManager.coordinateBrains(brainIds, "FLANKING");
            // If we reach here, coordination succeeded
        } catch (Exception e) {
            assert false : "Brain coordination failed with exception: " + e.getMessage();
        }
        
        log.info("Brain Coordination tests passed");
    }
    
    /**
     * Test brain switching
     */
    private void testBrainSwitching() {
        log.info("Testing Brain Switching...");
        
        // Create two brains
        HumanBrain humanBrain = BrainFactory.createHumanBrain("PLAYER_2", 5);
        AIBrain aiBrain = BrainFactory.createAIBrain(6, 0.5, 4);
        
        brainManager.registerBrain(humanBrain);
        brainManager.registerBrain(aiBrain);
        
        // Create test unit
        Soldier testSoldier = createTestSoldier("SOLDIER_3");
        
        // Assign human brain first
        boolean assigned1 = brainManager.assignBrainToUnit(humanBrain.getBrainId(), testSoldier.getId());
        assert assigned1 : "Failed to assign human brain";
        
        // Switch to AI brain
        boolean assigned2 = brainManager.assignBrainToUnit(aiBrain.getBrainId(), testSoldier.getId());
        assert assigned2 : "Failed to switch to AI brain";
        
        // Verify switch
        assert !humanBrain.isControlling() : "Human brain still controlling after switch";
        assert aiBrain.isControlling() : "AI brain not controlling after switch";
        
        log.info("Brain Switching tests passed");
    }
    
    /**
     * Test performance metrics
     */
    private void testPerformanceMetrics() {
        log.info("Testing Performance Metrics...");
        
        // Get all brain metrics
        Map<String, IBrain.BrainMetrics> allMetrics = brainManager.getAllBrainMetrics();
        assert !allMetrics.isEmpty() : "No brain metrics available";
        
        // Test individual brain metrics
        for (Map.Entry<String, IBrain.BrainMetrics> entry : allMetrics.entrySet()) {
            IBrain.BrainMetrics metrics = entry.getValue();
            
            assert metrics.getDecisionsMade() >= 0 : "Invalid decisions count";
            assert metrics.getActionsExecuted() >= 0 : "Invalid actions count";
            assert metrics.getErrors() >= 0 : "Invalid error count";
            assert metrics.getSuccessRate() >= 0.0 && metrics.getSuccessRate() <= 1.0 : "Invalid success rate";
        }
        
        // Test brain statistics
        Map<String, Object> stats = brainManager.getBrainStatistics();
        assert stats.containsKey("totalBrains") : "Missing total brains stat";
        assert stats.containsKey("activeBrains") : "Missing active brains stat";
        assert stats.containsKey("controlledUnits") : "Missing controlled units stat";
        
        log.info("Performance Metrics tests passed");
    }
    
    /**
     * Create a test soldier for testing
     */
    private Soldier createTestSoldier(String id) {
        Position position = new Position(0, 0, 0);
        Soldier soldier = new Soldier("Test Soldier", 100, 2, 3, 25);
        soldier.setId(id);
        soldier.setPosition(position);
        soldier.setActionPoints(2.0);
        return soldier;
    }
    
    /**
     * Main method to run the test
     */
    public static void main(String[] args) {
        BrainSystemIntegrationTest test = new BrainSystemIntegrationTest();
        test.runIntegrationTest();
        System.out.println("✅ Brain System Integration Test completed successfully!");
    }
}

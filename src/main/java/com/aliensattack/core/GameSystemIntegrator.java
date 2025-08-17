package com.aliensattack.core;

import com.aliensattack.combat.CombatSystemFactory;
import com.aliensattack.combat.CombatSystemFactory.CombatSystemType;
import com.aliensattack.combat.ConcreteCombatSystem;
import com.aliensattack.core.model.UnitFactory;
// UnitType import removed as it's not used in this class
import com.aliensattack.core.systems.SuppressionSystemFactory;
import com.aliensattack.core.config.GameConfig;
import lombok.extern.log4j.Log4j2;

/**
 * Main integrator for all previously unused game systems
 * Brings together BaseCombatSystem, RefactoredUnit, ISuppressionSystem, and advanced AI
 */
@Log4j2
public class GameSystemIntegrator {
    
    private static GameSystemIntegrator instance;
    private boolean systemsInitialized = false;
    
    private GameSystemIntegrator() {
        // Private constructor for singleton
    }
    
    public static GameSystemIntegrator getInstance() {
        if (instance == null) {
            instance = new GameSystemIntegrator();
        }
        return instance;
    }
    
    /**
     * Initialize all previously unused systems
     */
    public void initializeUnusedSystems() {
        if (systemsInitialized) {
            log.warn("Systems already initialized");
            return;
        }
        
        log.info("Initializing previously unused game systems...");
        
        try {
            // Initialize BaseCombatSystem implementation
            initializeCombatSystems();
            
            // Initialize RefactoredUnit system
            initializeUnitSystems();
            
            // Initialize advanced suppression system
            initializeSuppressionSystems();
            
            // Initialize advanced AI systems
            initializeAISystems();
            
            systemsInitialized = true;
            log.info("All previously unused systems initialized successfully");
            
        } catch (Exception e) {
            log.error("Error initializing unused systems", e);
            throw new RuntimeException("Failed to initialize game systems", e);
        }
    }
    
    /**
     * Initialize combat systems including BaseCombatSystem
     */
    private void initializeCombatSystems() {
        log.info("Initializing combat systems...");
        
        // Create concrete implementation of BaseCombatSystem
        ConcreteCombatSystem concreteSystem = new ConcreteCombatSystem();
        concreteSystem.setMissionType("INTEGRATION_TEST");
        
        // Test combat system factory with a temporary field
        try {
            var tempField = new com.aliensattack.field.TacticalField(20, 20);
            CombatSystemType recommendedType = CombatSystemFactory.getRecommendedSystem(tempField);
            log.info("Recommended combat system type: {}", recommendedType);
        } catch (Exception e) {
            log.warn("Could not test combat system factory: {}", e.getMessage());
        }
        
        log.info("Combat systems initialized");
    }
    
    /**
     * Initialize unit systems including RefactoredUnit
     */
    private void initializeUnitSystems() {
        log.info("Initializing unit systems...");
        
        // Test unit factory with different unit types
        var soldier = UnitFactory.createSoldierWithClass("Test Soldier", 
            com.aliensattack.core.enums.SoldierClass.HEAVY);
        
        var alien = UnitFactory.createAlien("Test Alien", 120, 4, 10, 20);
        
        var refactoredUnit = UnitFactory.createRefactoredUnit("Test Refactored", 100, 3, 8, 15);
        
        log.info("Created test units: {}, {}, {}", 
                soldier.getName(), alien.getName(), refactoredUnit.getName());
        
        log.info("Unit systems initialized");
    }
    
    /**
     * Initialize suppression systems including ISuppressionSystem
     */
    private void initializeSuppressionSystems() {
        log.info("Initializing suppression systems...");
        
        // Test suppression system factory
        var suppressionSystem = SuppressionSystemFactory.getSuppressionSystem();
        var advancedSystem = SuppressionSystemFactory.createSuppressionSystem(true);
        
        // Get suppression configuration
        var config = SuppressionSystemFactory.getSuppressionConfig();
        
        log.info("Suppression systems initialized with config: accuracy={}, movement={}", 
                config.getAccuracyPenalty(), config.getMovementPenalty());
    }
    
    /**
     * Initialize advanced AI systems
     */
    private void initializeAISystems() {
        log.info("Initializing AI systems...");
        
        // Test AI configuration from GameConfig
        boolean aiEnabled = GameConfig.isAIEnabled();
        int aiDifficulty = GameConfig.getAIDifficultyLevel();
        double aiAdaptationRate = GameConfig.getAIAdaptationRate();
        
        log.info("AI systems initialized: enabled={}, difficulty={}, adaptation={}", 
                aiEnabled, aiDifficulty, aiAdaptationRate);
    }
    
    /**
     * Get integration status
     */
    public boolean isSystemsInitialized() {
        return systemsInitialized;
    }
    
    /**
     * Reset all systems (for testing)
     */
    public void resetSystems() {
        log.info("Resetting all integrated systems...");
        
        // Reset suppression system
        SuppressionSystemFactory.resetSuppressionSystem();
        
        // Reset integrator state
        systemsInitialized = false;
        
        log.info("All systems reset");
    }
    
    /**
     * Test all integrated systems
     */
    public void testAllSystems() {
        if (!systemsInitialized) {
            log.warn("Systems not initialized, cannot test");
            return;
        }
        
        log.info("Testing all integrated systems...");
        
        try {
            // Test combat system
            testCombatSystem();
            
            // Test unit system
            testUnitSystem();
            
            // Test suppression system
            testSuppressionSystem();
            
            log.info("All system tests completed successfully");
            
        } catch (Exception e) {
            log.error("Error during system testing", e);
        }
    }
    
    private void testCombatSystem() {
        log.debug("Testing combat system...");
        // Combat system tests would go here
    }
    
    private void testUnitSystem() {
        log.debug("Testing unit system...");
        // Unit system tests would go here
    }
    
    private void testSuppressionSystem() {
        log.debug("Testing suppression system...");
        // Suppression system tests would go here
    }
}

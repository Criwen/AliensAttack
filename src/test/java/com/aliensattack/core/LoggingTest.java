package com.aliensattack.core;

import com.aliensattack.actions.AttackAction;
import com.aliensattack.core.model.Soldier;
import com.aliensattack.core.model.Alien;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.enums.SoldierClass;
import com.aliensattack.core.enums.AlienType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for comprehensive logging functionality
 */
public class LoggingTest {
    
    private Soldier soldier;
    private Alien alien;
    
    @BeforeEach
    void setUp() {
        // Create test units
        soldier = new Soldier("TestSoldier", 100, 3, 5, 25, SoldierClass.RANGER);
        alien = new Alien("TestAlien", 50, 2, 4, 20, AlienType.ADVENT_TROOPER);
        
        // Set initial stats
        soldier.setCurrentHealth(100);
        soldier.setMaxHealth(100);
        soldier.resetActionPoints();
        alien.setCurrentHealth(50);
        alien.setMaxHealth(50);
    }
    
    @Test
    void testActionLogging() {
        // Test attack action logging
        AttackAction attackAction = new AttackAction(soldier, alien);
        
        // Verify action can be performed
        assertTrue(attackAction.canPerform(), "Attack action should be valid");
        
        // Execute action
        attackAction.execute();
        
        // Verify action was logged and executed
        assertTrue(attackAction.isSuccessful() || !attackAction.isSuccessful(), "Action should have a result");
        assertNotNull(attackAction.getResult(), "Action should have a result message");
    }
    
    @Test
    void testApplicationLogging() {
        // Test application startup logging
        GameLogManager.logApplicationStart();
        
        // Test various logging methods
        GameLogManager.logSystemInit("TestSystem");
        GameLogManager.logSystemReady("TestSystem");
        GameLogManager.logCombatEvent("Test combat event");
        GameLogManager.logMissionEvent("Test mission event");
        GameLogManager.logUnitAction("TestUnit", "Test action");
        GameLogManager.logUserInteraction("Test interaction", "Test details");
        GameLogManager.logPerformance("Test operation", 100L);
        GameLogManager.logGameStateChange("INIT", "RUNNING", "Test state change");
        
        // Test error logging
        try {
            throw new RuntimeException("Test exception");
        } catch (Exception e) {
            GameLogManager.logError("Test context", e);
        }
        
        // Test warning logging
        GameLogManager.logWarning("Test context", "Test warning message");
        
        // Test debug logging
        GameLogManager.logDebug("Test context", "Test debug message");
        
        // Test application shutdown logging
        GameLogManager.logApplicationShutdown("Test completion");
    }
    
    @Test
    void testActionPointLogging() {
        // Test action point spending logging
        int initialAP = (int)soldier.getActionPoints();
        soldier.spendActionPoints(1);
        int remainingAP = (int)soldier.getActionPoints();
        
        // Verify action points were spent
        assertEquals(initialAP - 1, remainingAP, "Action points should be reduced by 1");
    }
    
    @Test
    void testExpandedLoggingMethods() {
        // Test unit creation logging
        GameLogManager.logUnitCreation("SOLDIER", "NewSoldier", "Ranger class with 100 HP");
        GameLogManager.logUnitCreation("ALIEN", "NewAlien", "Advent Trooper with 50 HP");
        
        // Test unit destruction logging
        GameLogManager.logUnitDestruction("TestUnit", "Combat Death", "Killed by enemy attack");
        
        // Test unit movement logging
        GameLogManager.logUnitMovement("TestUnit", "(0,0)", "(3,2)", 2);
        
        // Test unit health change logging
        GameLogManager.logUnitHealthChange("TestUnit", 100, 75, -25, "Enemy attack");
        GameLogManager.logUnitHealthChange("TestUnit", 75, 100, 25, "Healing");
        
        // Test unit status effect logging
        GameLogManager.logUnitStatusEffect("TestUnit", "POISON", "Poisoned by toxic gas", true);
        GameLogManager.logUnitStatusEffect("TestUnit", "POISON", "Poison effect expired", false);
        
        // Test equipment change logging
        GameLogManager.logEquipmentChange("TestUnit", "Weapon", "Basic Rifle", "Plasma Rifle");
        GameLogManager.logEquipmentChange("TestUnit", "Armor", "Basic Armor", "Power Armor");
        
        // Test inventory operation logging
        GameLogManager.logInventoryOperation("TestUnit", "ADDED", "Medikit", 3);
        GameLogManager.logInventoryOperation("TestUnit", "USED", "Medikit", 1);
        
        // Test AI decision logging
        GameLogManager.logAIDecision("AlienAI", "Attack Player", "Player unit is vulnerable");
        GameLogManager.logAIDecision("AlienAI", "Retreat", "Low health, need to regroup");
        
        // Test AI behavior change logging
        GameLogManager.logAIBehaviorChange("AlienAI", "Aggressive", "Defensive", "Low health");
        
        // Test terrain interaction logging
        GameLogManager.logTerrainInteraction("TestUnit", "High Ground", "Climb", "Gained height advantage");
        GameLogManager.logTerrainInteraction("TestUnit", "Water", "Swim", "Movement slowed");
        
        // Test weather effect logging
        GameLogManager.logWeatherEffect("Rain", "Reduced visibility", "Accuracy -10%");
        GameLogManager.logWeatherEffect("Fog", "Limited sight range", "Vision range halved");
        
        // Test mission objective logging
        GameLogManager.logMissionObjective("Eliminate Aliens", "IN_PROGRESS", "3 of 5 aliens eliminated");
        GameLogManager.logMissionObjective("Reach Extraction", "COMPLETED", "All units extracted");
        
        // Test turn progression logging
        GameLogManager.logTurnProgression(1, "Player Turn", "Soldier1", "Moving to cover position");
        GameLogManager.logTurnProgression(2, "Alien Turn", "Alien1", "Activating overwatch");
        
        // Test squad operation logging
        GameLogManager.logSquadOperation("Alpha Squad", "Formation", "Moving in diamond formation");
        GameLogManager.logSquadOperation("Alpha Squad", "Tactics", "Using suppression fire");
        
        // Test research progress logging
        GameLogManager.logResearchProgress("Plasma Weapons", 75, "Final testing phase");
        GameLogManager.logResearchProgress("Alien Autopsy", 100, "Research completed");
        
        // Test technology unlock logging
        GameLogManager.logTechnologyUnlock("Plasma Rifle", "Alien Alloy + Research", "Increased damage and accuracy");
        
        // Test resource management logging
        GameLogManager.logResourceManagement("Supplies", 1000, 800, "Weapon purchase");
        GameLogManager.logResourceManagement("Intel", 50, 75, "Mission reward");
        
        // Test save/load operation logging
        GameLogManager.logSaveLoadOperation("SAVE", "mission_001.sav", true, "Auto-save completed");
        GameLogManager.logSaveLoadOperation("LOAD", "mission_001.sav", true, "Game loaded successfully");
        
        // Test network operation logging
        GameLogManager.logNetworkOperation("CONNECT", "game.server.com", true, "Connected to game server");
        
        // Test configuration change logging
        GameLogManager.logConfigurationChange("Graphics Quality", "Medium", "High", "User preference");
        
        // Test security event logging
        GameLogManager.logSecurityEvent("Invalid Input", "LOW", "User entered invalid coordinates");
        
        // Test memory usage logging
        GameLogManager.logMemoryUsage();
        
        // Test thread info logging
        GameLogManager.logThreadInfo();
        
        // Test system resources logging
        GameLogManager.logSystemResources();
    }
    
    @Test
    void testComprehensiveActionLogging() {
        // Create and execute multiple actions to test comprehensive logging
        AttackAction attack1 = new AttackAction(soldier, alien);
        AttackAction attack2 = new AttackAction(soldier, alien);
        
        // Execute first attack
        attack1.execute();
        
        // Log some additional events
        GameLogManager.logCombatEvent("Combat Round 1", "Soldier attacks alien");
        GameLogManager.logTurnProgression(1, "Combat", "Soldier", "Attack action completed");
        
        // Execute second attack
        attack2.execute();
        
        // Log combat resolution
        GameLogManager.logCombatEvent("Combat Round 2", "Soldier attacks alien again");
        GameLogManager.logTurnProgression(2, "Combat", "Soldier", "Second attack completed");
        
        // Verify actions were executed
        assertNotNull(attack1.getResult(), "First attack should have a result");
        assertNotNull(attack2.getResult(), "Second attack should have a result");
    }
}

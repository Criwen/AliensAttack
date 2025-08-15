package com.aliensattack.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Mission Systems
 */
@DisplayName("Mission Systems Tests")
public class MissionSystemsTest {
    
    private MissionIntegrationSystem missionIntegrationSystem;
    private MindControlSystem mindControlSystem;
    private TeleportationSystem teleportationSystem;
    
    @BeforeEach
    void setUp() {
        missionIntegrationSystem = new MissionIntegrationSystem();
        mindControlSystem = new MindControlSystem();
        teleportationSystem = new TeleportationSystem();
        
        missionIntegrationSystem.initialize();
        mindControlSystem.initialize();
        teleportationSystem.initialize();
    }
    
    @Test
    @DisplayName("Should initialize mission integration system correctly")
    void shouldInitializeMissionIntegrationSystemCorrectly() {
        assertNotNull(missionIntegrationSystem);
        assertNotNull(missionIntegrationSystem.getMissionTypes());
        assertNotNull(missionIntegrationSystem.getIntegrationLevels());
        assertNotNull(missionIntegrationSystem.getActiveMissions());
        assertNotNull(missionIntegrationSystem.getMissionData());
    }
    
    @Test
    @DisplayName("Should initialize mind control system correctly")
    void shouldInitializeMindControlSystemCorrectly() {
        assertNotNull(mindControlSystem);
        assertNotNull(mindControlSystem.getControlTypes());
        assertNotNull(mindControlSystem.getControlledUnits());
        assertNotNull(mindControlSystem.getControlEffects());
    }
    
    @Test
    @DisplayName("Should initialize teleportation system correctly")
    void shouldInitializeTeleportationSystemCorrectly() {
        assertNotNull(teleportationSystem);
        assertNotNull(teleportationSystem.getTeleportationTypes());
        assertNotNull(teleportationSystem.getTeleportationTargets());
        assertNotNull(teleportationSystem.getTeleportationEffects());
    }
    
    @Test
    @DisplayName("Should add mission to integration system")
    void shouldAddMissionToIntegrationSystem() {
        // Create a mock mission
        Mission mission = new Mission();
        mission.setName("Test Mission");
        
        int initialCount = missionIntegrationSystem.getActiveMissions().size();
        missionIntegrationSystem.addMission(mission);
        
        assertEquals(initialCount + 1, missionIntegrationSystem.getActiveMissions().size());
        assertTrue(missionIntegrationSystem.getActiveMissions().contains(mission));
    }
    
    @Test
    @DisplayName("Should get mission integration system status")
    void shouldGetMissionIntegrationSystemStatus() {
        String status = missionIntegrationSystem.getMissionIntegrationStatus();
        
        assertNotNull(status);
        assertTrue(status.contains("Mission Integration Status"));
        assertTrue(status.contains("Integration Bonus"));
        assertTrue(status.contains("Active Missions"));
        assertTrue(status.contains("Mission Data"));
    }
}

package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.MissionType;

import java.util.*;
import java.util.ArrayList;

/**
 * Mission Integration System for XCOM 2 Strategic Layer
 * Integrates various mission systems and coordinates mission execution
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionIntegrationSystem {
    
    private Map<String, MissionType> missionTypes;
    private Map<String, String> integrationLevels;
    private List<Mission> activeMissions;
    private Map<String, Object> missionData;
    
    // Integrated systems
    private MindControlSystem mindControlSystem;
    private TeleportationSystem teleportationSystem;
    private MissionPressureSystem pressureSystem;
    private MissionFailureSuccessConditionsSystem failureSuccessSystem;
    
    private int integrationBonus;
    private int missionCount;
    private Random random;
    
    /**
     * Initialize the mission integration system
     */
    public void initialize() {
        if (missionTypes == null) {
            missionTypes = new HashMap<>();
        }
        if (integrationLevels == null) {
            integrationLevels = new HashMap<>();
        }
        if (activeMissions == null) {
            activeMissions = new ArrayList<>();
        }
        if (missionData == null) {
            missionData = new HashMap<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        integrationBonus = 0;
        missionCount = 0;
        
        initializeMissionTypes();
        initializeIntegrationLevels();
        initializeIntegratedSystems();
    }
    
    /**
     * Initialize mission types
     */
    private void initializeMissionTypes() {
        // Add mission types
        missionTypes.put("RECONNAISSANCE", MissionType.RECONNAISSANCE);
        missionTypes.put("EXTRACTION", MissionType.EXTRACTION);
        missionTypes.put("SABOTAGE", MissionType.SABOTAGE);
        missionTypes.put("DEFENSE", MissionType.DEFENSE);
        missionTypes.put("INVASION", MissionType.INVASION);
    }
    
    /**
     * Initialize integration levels
     */
    private void initializeIntegrationLevels() {
        // Add integration levels
        integrationLevels.put("BASIC", "BASIC");
        integrationLevels.put("INTERMEDIATE", "INTERMEDIATE");
        integrationLevels.put("ADVANCED", "ADVANCED");
        integrationLevels.put("EXPERT", "EXPERT");
    }
    
    /**
     * Initialize integrated systems
     */
    private void initializeIntegratedSystems() {
        // Initialize mind control system
        mindControlSystem = new MindControlSystem();
        mindControlSystem.initializeSystem("MISSION_INTEGRATION_MIND_CONTROL");
        
        // Initialize teleportation system
        teleportationSystem = new TeleportationSystem();
        // teleportationSystem.initialize(); // Method doesn't exist
        
        // Initialize pressure system
        pressureSystem = new MissionPressureSystem();
        // pressureSystem.initialize(); // Method doesn't exist
        
        // Initialize failure success system
        failureSuccessSystem = new MissionFailureSuccessConditionsSystem();
        // failureSuccessSystem.initialize(); // Method doesn't exist
    }
    
    /**
     * Add mission
     */
    public void addMission(Mission mission) {
        if (!activeMissions.contains(mission)) {
            activeMissions.add(mission);
            missionCount++;
            updateIntegrationBonus();
        }
    }
    
    /**
     * Remove mission
     */
    public void removeMission(Mission mission) {
        if (activeMissions.remove(mission)) {
            missionCount--;
            updateIntegrationBonus();
        }
    }
    
    /**
     * Update integration bonus
     */
    private void updateIntegrationBonus() {
        integrationBonus = missionCount * 3;
    }
    
    /**
     * Get mission integration status
     */
    public String getMissionIntegrationStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Mission Integration Status:\n");
        status.append("- Integration Bonus: ").append(integrationBonus).append("\n");
        status.append("- Mission Count: ").append(missionCount).append("\n");
        status.append("- Active Missions: ").append(activeMissions.size()).append("\n");
        status.append("- Mission Data: ").append(missionData.size()).append("\n");
        status.append("- Available Mission Types: ").append(missionTypes.size()).append("\n");
        status.append("- Available Integration Levels: ").append(integrationLevels.size()).append("\n");
        
        return status.toString();
    }
    
    /**
     * Integrate mind control with mission
     */
    public void integrateMindControlWithMission(String missionId, String targetUnitId) {
        // Create mind control modifier for mission pressure
        MissionPressureSystem.PressureModifier mindControlModifier = MissionPressureSystem.PressureModifier.builder()
            .modifierId("mind_control_" + missionId)
            .modifierName("Mind Control Pressure")
            .type(MissionPressureSystem.ModifierType.PSIONIC_INTERFERENCE)
            .value(15)
            .duration(5)
            .currentDuration(5)
            .source("mind_control")
            .isActive(true)
            .affectedSystems(new ArrayList<>())
            .description("Mind control pressure modifier")
            .build();
        
        // Apply mind control pressure
        pressureSystem.addPressureModifier(mindControlModifier);
        
        // Update mission data
        missionData.put("mind_control_" + missionId, mindControlModifier);
    }
    
    /**
     * Integrate teleportation with mission
     */
    public void integrateTeleportationWithMission(String missionId, String targetPosition) {
        // Create teleportation modifier for mission pressure
        MissionPressureSystem.PressureModifier teleportModifier = MissionPressureSystem.PressureModifier.builder()
            .modifierId("teleportation_" + missionId)
            .modifierName("Teleportation Pressure")
            .type(MissionPressureSystem.ModifierType.ENVIRONMENTAL_HAZARD)
            .value(10)
            .duration(3)
            .currentDuration(3)
            .source("teleportation")
            .isActive(true)
            .affectedSystems(new ArrayList<>())
            .description("Teleportation pressure modifier")
            .build();
        
        // Apply teleportation pressure
        pressureSystem.addPressureModifier(teleportModifier);
        
        // Update mission data
        missionData.put("teleportation_" + missionId, teleportModifier);
    }
    
    /**
     * Complete mission with result
     */
    public boolean completeMission(MissionFailureSuccessConditionsSystem.MissionResult result, int completionTurns) {
        // Check mission completion conditions
        boolean success = failureSuccessSystem.determineMissionResult();
        
        if (success) {
            // Create completion event
            MissionCompletionEvent completionEvent = MissionCompletionEvent.builder()
                .eventId("completion_" + System.currentTimeMillis())
                .result(result)
                .completionTurns(completionTurns)
                .type(result == MissionFailureSuccessConditionsSystem.MissionResult.COMPLETE_SUCCESS ?
                    "SUCCESS" : "PARTIAL_SUCCESS")
                .build();
            
            // Store completion data
            missionData.put("completion_event", completionEvent);
        }
        
        return success;
    }
}

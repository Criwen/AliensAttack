package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import static com.aliensattack.core.model.MissionFailureSuccessConditionsSystem.MissionResult;

import java.util.*;

/**
 * Mission Value Calculation System for XCOM 2 Strategic Layer
 * Calculates mission values, rewards, and strategic importance
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionValueCalculationSystem {
    
    private Map<String, Double> missionValues;
    private Map<String, Integer> missionRewards;
    private List<MissionValue> calculatedValues;
    private int calculationBonus;
    private int valueThreshold;
    private Random random;
    
    /**
     * Initialize the mission value calculation system
     */
    public void initialize() {
        if (missionValues == null) {
            missionValues = new HashMap<>();
        }
        if (missionRewards == null) {
            missionRewards = new HashMap<>();
        }
        if (calculatedValues == null) {
            calculatedValues = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        calculationBonus = 0;
        valueThreshold = 100;
        
        initializeMissionValues();
        initializeMissionRewards();
    }
    
    /**
     * Initialize mission values
     */
    private void initializeMissionValues() {
        // Add base mission values
        missionValues.put("RECONNAISSANCE", 50.0);
        missionValues.put("EXTRACTION", 75.0);
        missionValues.put("SABOTAGE", 100.0);
        missionValues.put("DEFENSE", 80.0);
        missionValues.put("INVASION", 120.0);
    }
    
    /**
     * Initialize mission rewards
     */
    private void initializeMissionRewards() {
        // Add base mission rewards
        missionRewards.put("RECONNAISSANCE", 100);
        missionRewards.put("EXTRACTION", 150);
        missionRewards.put("SABOTAGE", 200);
        missionRewards.put("DEFENSE", 175);
        missionRewards.put("INVASION", 250);
    }
    
    /**
     * Calculate mission value
     */
    public double calculateMissionValue(String missionType, MissionResult result) {
        double baseValue = missionValues.getOrDefault(missionType, 50.0);
        
        switch (result) {
            case COMPLETE_SUCCESS:
                return baseValue * 1.5;
            case PARTIAL_SUCCESS:
                return baseValue * 1.0;
            case PARTIAL_FAILURE:
                return baseValue * 0.5;
            case COMPLETE_FAILURE:
                return baseValue * 0.0;
            default:
                return baseValue;
        }
    }
    
    /**
     * Get mission value calculation status
     */
    public String getMissionValueCalculationStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Mission Value Calculation Status:\n");
        status.append("- Calculation Bonus: ").append(calculationBonus).append("\n");
        status.append("- Value Threshold: ").append(valueThreshold).append("\n");
        status.append("- Mission Values: ").append(missionValues.size()).append("\n");
        status.append("- Mission Rewards: ").append(missionRewards.size()).append("\n");
        status.append("- Calculated Values: ").append(calculatedValues.size()).append("\n");
        
        return status.toString();
    }
    
    // Nested class for mission values
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionValue {
        private String missionId;
        private String missionType;
        private double baseValue;
        private double calculatedValue;
        private String calculationDate;
    }
    
    // Mission result enum
    public enum MissionResult {
        COMPLETE_SUCCESS, PARTIAL_SUCCESS, PARTIAL_FAILURE, COMPLETE_FAILURE
    }
}

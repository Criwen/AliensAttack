package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.MissionType;
import com.aliensattack.core.model.MissionPlanningPreparationSystem.MissionStatus;
import com.aliensattack.core.model.MissionPlanningPreparationSystem.MissionPlan;
import com.aliensattack.core.model.MissionPlanningPreparationSystem.MissionObjective;
import com.aliensattack.core.model.MissionPlanningPreparationSystem.MissionRequirement;

import java.util.*;

/**
 * Advanced Mission Planning Manager for XCOM 2 Strategic Layer
 * Manages mission planning, preparation, and strategic decision making
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionPlanningManager {
    
    private MissionPlanningPreparationSystem planningSystem;
    private Map<String, MissionPlan> missionPlans;
    private Map<String, MissionObjective> missionObjectives;
    private Map<String, MissionRequirement> missionRequirements;
    private List<MissionType> availableMissionTypes;
    private MissionStatus currentMissionStatus;
    private int planningPoints;
    private int missionSuccessRate;
    private Random random;
    
    /**
     * Initialize the mission planning manager
     */
    public void initialize() {
        if (planningSystem == null) {
            planningSystem = MissionPlanningPreparationSystem.builder().build();
        }
        if (missionPlans == null) {
            missionPlans = new HashMap<>();
        }
        if (missionObjectives == null) {
            missionObjectives = new HashMap<>();
        }
        if (missionRequirements == null) {
            missionRequirements = new HashMap<>();
        }
        if (availableMissionTypes == null) {
            availableMissionTypes = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        currentMissionStatus = MissionStatus.PLANNING;
        planningPoints = 100;
        missionSuccessRate = 75;
        
        initializeMissionTypes();
        initializeMissionPlans();
    }
    
    /**
     * Initialize mission types
     */
    private void initializeMissionTypes() {
        availableMissionTypes.addAll(Arrays.asList(
            MissionType.RESCUE,
            MissionType.INFILTRATION,
            MissionType.DESTRUCTION,
            MissionType.INTELLIGENCE,
            MissionType.CAPTURE,
            MissionType.DEFENSE
        ));
    }
    
    /**
     * Initialize mission plans
     */
    private void initializeMissionPlans() {
        // Create initial mission plans
        MissionPlan rescuePlan = MissionPlan.builder()
                .planId("RESCUE_PLAN_1")
                .planName("Rescue Mission Plan")
                .missionType(MissionType.RESCUE)
                .planCost(50)
                .planDuration(3)
                .currentProgress(0)
                .maxProgress(100)
                .successRate(0.8)
                .failureRate(0.2)
                .planBonus("Improved rescue success rate")
                .planPenalty("Increased mission cost")
                .isActive(false)
                .isCompleted(false)
                .isFailed(false)
                .assignedPlanners(0)
                .maxPlanners(3)
                .planPriority(1)
                .build();
        
        missionPlans.put(rescuePlan.getPlanId(), rescuePlan);
    }
    
    /**
     * Start mission planning
     */
    public boolean startMissionPlanning(String planId) {
        MissionPlan plan = missionPlans.get(planId);
        if (plan != null && !plan.isActive() && planningPoints >= plan.getPlanCost()) {
            plan.setActive(true);
            planningPoints -= plan.getPlanCost();
            return true;
        }
        return false;
    }
    
    /**
     * Update mission planning progress
     */
    public void updateMissionPlanningProgress() {
        for (MissionPlan plan : missionPlans.values()) {
            if (plan.isActive() && !plan.isCompleted()) {
                int progress = plan.getCurrentProgress() + (plan.getAssignedPlanners() * 20);
                plan.setCurrentProgress(Math.min(progress, plan.getMaxProgress()));
                
                if (plan.getCurrentProgress() >= plan.getMaxProgress()) {
                    completeMissionPlanning(plan);
                }
            }
        }
    }
    
    /**
     * Complete mission planning
     */
    private void completeMissionPlanning(MissionPlan plan) {
        plan.setCompleted(true);
        plan.setActive(false);
        
        // Add planning points
        planningPoints += 25;
        
        // Update mission success rate
        updateMissionSuccessRate();
    }
    
    /**
     * Update mission success rate
     */
    private void updateMissionSuccessRate() {
        int completedPlans = (int) missionPlans.values().stream()
                .filter(MissionPlan::isCompleted)
                .count();
        
        int totalPlans = missionPlans.size();
        
        if (totalPlans > 0) {
            missionSuccessRate = Math.min(100, 75 + (completedPlans * 5));
        }
    }
    
    /**
     * Get mission planning status
     */
    public String getMissionPlanningStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Mission Planning Status:\n");
        status.append("- Current Mission Status: ").append(currentMissionStatus).append("\n");
        status.append("- Planning Points: ").append(planningPoints).append("\n");
        status.append("- Mission Success Rate: ").append(missionSuccessRate).append("%\n");
        status.append("- Available Mission Types: ").append(availableMissionTypes.size()).append("\n");
        status.append("- Active Plans: ").append(missionPlans.values().stream().filter(MissionPlan::isActive).count()).append("\n");
        status.append("- Completed Plans: ").append(missionPlans.values().stream().filter(MissionPlan::isCompleted).count()).append("\n");
        status.append("- Mission Objectives: ").append(missionObjectives.size()).append("\n");
        status.append("- Mission Requirements: ").append(missionRequirements.size()).append("\n");
        
        return status.toString();
    }
}

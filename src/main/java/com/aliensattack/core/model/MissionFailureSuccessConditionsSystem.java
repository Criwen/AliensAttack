package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.*;
import java.time.LocalDateTime;

/**
 * Mission Failure Success Conditions System for mission objectives.
 * Implements mission win/lose conditions and objective tracking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionFailureSuccessConditionsSystem {
    
    // Mission State Management
    private MissionState currentMissionState;
    private List<MissionObjective> activeObjectives;
    private List<MissionObjective> completedObjectives;
    private List<MissionObjective> failedObjectives;
    private LocalDateTime missionStartTime;
    private LocalDateTime missionEndTime;
    private int missionTimer;
    private boolean isEvacuationAvailable;
    private boolean isEvacuationTriggered;
    
    // Success/Failure Conditions
    private List<SuccessCondition> successConditions;
    private List<FailureCondition> failureConditions;
    private MissionResult currentResult;
    private List<MissionConsequence> consequences;
    
    // Squad Management
    private List<Unit> squadMembers;
    private List<Unit> evacuatedUnits;
    private List<Unit> lostUnits;
    private int maxSquadSize;
    private int minSquadSizeForSuccess;
    
    // Dynamic Objectives
    private Map<String, DynamicObjective> dynamicObjectives;
    private List<ObjectiveTrigger> objectiveTriggers;
    private boolean objectivesCanChange;
    
    // Mission Consequences
    private List<MissionConsequence> strategicConsequences;
    private List<MissionConsequence> resourceConsequences;
    private List<MissionConsequence> intelConsequences;
    
    public enum MissionState {
        PREPARING, IN_PROGRESS, SUCCESS, FAILURE, EVACUATING, ABORTED
    }
    
    public enum MissionResult {
        COMPLETE_SUCCESS, PARTIAL_SUCCESS, FAILURE, SQUAD_WIPE, TIMER_EXPIRED, OBJECTIVE_FAILED, STEALTH_SUCCESS
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionObjective {
        private String objectiveId;
        private String description;
        private ObjectiveType type;
        private boolean isRequired;
        private boolean isCompleted;
        private boolean isFailed;
        private int progress;
        private int requiredProgress;
        private LocalDateTime completionTime;
        private List<String> dependencies;
        private List<String> blockers;
    }
    
    public enum ObjectiveType {
        ELIMINATE_TARGET, SECURE_AREA, HACK_TERMINAL, EXTRACT_VIP, DEFEND_POSITION, 
        DESTROY_OBJECTIVE, STEALTH_COMPLETE, TIMED_OBJECTIVE, ESCORT_VIP, RECONNAISSANCE
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuccessCondition {
        private String conditionId;
        private String description;
        private SuccessType type;
        private boolean isMet;
        private int requiredValue;
        private int currentValue;
        private List<String> requiredObjectives;
    }
    
    public enum SuccessType {
        ALL_OBJECTIVES_COMPLETE, MINIMUM_OBJECTIVES_COMPLETE, SQUAD_SURVIVAL, 
        STEALTH_COMPLETE, TIME_BONUS, RESOURCE_BONUS, INTEL_BONUS
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FailureCondition {
        private String conditionId;
        private String description;
        private FailureType type;
        private boolean isTriggered;
        private int threshold;
        private int currentValue;
        private LocalDateTime triggerTime;
    }
    
    public enum FailureType {
        SQUAD_WIPE, TIMER_EXPIRED, OBJECTIVE_FAILED, VIP_LOST, AREA_LOST, 
        EXCESSIVE_CASUALTIES, STEALTH_BREACHED
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DynamicObjective {
        private String objectiveId;
        private String description;
        private DynamicType type;
        private List<ObjectiveTrigger> triggers;
        private List<String> modifiers;
        private boolean isActive;
        private LocalDateTime activationTime;
    }
    
    public enum DynamicType {
        EMERGENCY_EXTRACTION, REINFORCEMENT_ARRIVAL, OBJECTIVE_CHANGE, 
        NEW_THREAT_APPEARS, ENVIRONMENTAL_HAZARD
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ObjectiveTrigger {
        private String triggerId;
        private String description;
        private TriggerType type;
        private boolean isActivated;
        private int threshold;
        private int currentValue;
        private List<String> affectedObjectives;
    }
    
    public enum TriggerType {
        UNIT_DAMAGE, TIME_ELAPSED, OBJECTIVE_COMPLETED, ENEMY_SPOTTED, 
        ENVIRONMENTAL_CHANGE, SQUAD_POSITION
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionConsequence {
        private String consequenceId;
        private String description;
        private ConsequenceType type;
        private int severity;
        private List<String> affectedSystems;
        private LocalDateTime appliedTime;
    }
    
    public enum ConsequenceType {
        STRATEGIC_IMPACT, RESOURCE_LOSS, INTEL_LOSS, MORALE_IMPACT, 
        TECHNOLOGY_LOSS, RELATIONSHIP_DAMAGE
    }
    
    // Core Mission Management Methods
    
    public boolean initializeMission(String missionId, List<MissionObjective> objectives, 
                                   List<SuccessCondition> successConditions, 
                                   List<FailureCondition> failureConditions) {
        this.currentMissionState = MissionState.PREPARING;
        this.activeObjectives = new ArrayList<>(objectives);
        this.completedObjectives = new ArrayList<>();
        this.failedObjectives = new ArrayList<>();
        this.successConditions = new ArrayList<>(successConditions);
        this.failureConditions = new ArrayList<>(failureConditions);
        this.missionStartTime = LocalDateTime.now();
        this.consequences = new ArrayList<>();
        this.dynamicObjectives = new HashMap<>();
        this.objectiveTriggers = new ArrayList<>();
        
        return validateMissionSetup();
    }
    
    public boolean startMission() {
        if (this.currentMissionState != MissionState.PREPARING) {
            return false;
        }
        
        this.currentMissionState = MissionState.IN_PROGRESS;
        this.missionStartTime = LocalDateTime.now();
        
        // Activate initial objectives
        for (MissionObjective objective : activeObjectives) {
            if (canActivateObjective(objective)) {
                activateObjective(objective);
            }
        }
        
        return true;
    }
    
    public boolean updateMissionProgress() {
        if (this.currentMissionState != MissionState.IN_PROGRESS) {
            return false;
        }
        
        // Check for mission completion
        checkSuccessConditions();
        checkFailureConditions();
        updateDynamicObjectives();
        updateMissionTimer();
        
        // Determine mission result
        determineMissionResult();
        
        return true;
    }
    
    public boolean checkSuccessConditions() {
        boolean anySuccess = false;
        
        for (SuccessCondition condition : successConditions) {
            boolean isMet = evaluateSuccessCondition(condition);
            condition.setMet(isMet);
            
            if (isMet) {
                anySuccess = true;
            }
        }
        
        return anySuccess;
    }
    
    public boolean checkFailureConditions() {
        boolean anyFailure = false;
        
        for (FailureCondition condition : failureConditions) {
            boolean isTriggered = evaluateFailureCondition(condition);
            condition.setTriggered(isTriggered);
            
            if (isTriggered) {
                anyFailure = true;
                triggerFailureConsequences(condition);
            }
        }
        
        return anyFailure;
    }
    
    public boolean evaluateSuccessCondition(SuccessCondition condition) {
        switch (condition.getType()) {
            case ALL_OBJECTIVES_COMPLETE:
                return completedObjectives.size() == activeObjectives.size() + completedObjectives.size();
                
            case MINIMUM_OBJECTIVES_COMPLETE:
                return completedObjectives.size() >= condition.getRequiredValue();
                
            case SQUAD_SURVIVAL:
                return squadMembers.size() >= condition.getRequiredValue();
                
            case STEALTH_COMPLETE:
                return checkStealthSuccess();
                
            case TIME_BONUS:
                return getMissionTime() <= condition.getRequiredValue();
                
            case RESOURCE_BONUS:
                return calculateResourceBonus() >= condition.getRequiredValue();
                
            case INTEL_BONUS:
                return calculateIntelBonus() >= condition.getRequiredValue();
                
            default:
                return false;
        }
    }
    
    public boolean evaluateFailureCondition(FailureCondition condition) {
        switch (condition.getType()) {
            case SQUAD_WIPE:
                return squadMembers.isEmpty();
                
            case TIMER_EXPIRED:
                return missionTimer <= 0;
                
            case OBJECTIVE_FAILED:
                return failedObjectives.size() >= condition.getThreshold();
                
            case VIP_LOST:
                return checkVIPLost();
                
            case AREA_LOST:
                return checkAreaLost();
                
            case EXCESSIVE_CASUALTIES:
                return lostUnits.size() >= condition.getThreshold();
                
            case STEALTH_BREACHED:
                return checkStealthBreached();
                
            default:
                return false;
        }
    }
    
    public boolean activateObjective(MissionObjective objective) {
        if (objective.isCompleted() || objective.isFailed()) {
            return false;
        }
        
        // Check dependencies
        if (!checkObjectiveDependencies(objective)) {
            return false;
        }
        
        // Check blockers
        if (checkObjectiveBlockers(objective)) {
            return false;
        }
        
        // Objective is now active
        return true;
    }
    
    public boolean completeObjective(String objectiveId) {
        MissionObjective objective = findObjective(objectiveId);
        if (objective == null || objective.isCompleted() || objective.isFailed()) {
            return false;
        }
        
        objective.setCompleted(true);
        objective.setCompletionTime(LocalDateTime.now());
        completedObjectives.add(objective);
        activeObjectives.remove(objective);
        
        // Trigger dependent objectives
        triggerDependentObjectives(objective);
        
        return true;
    }
    
    public boolean failObjective(String objectiveId) {
        MissionObjective objective = findObjective(objectiveId);
        if (objective == null || objective.isCompleted() || objective.isFailed()) {
            return false;
        }
        
        objective.setFailed(true);
        failedObjectives.add(objective);
        activeObjectives.remove(objective);
        
        return true;
    }
    
    public boolean triggerEvacuation() {
        if (!isEvacuationAvailable || isEvacuationTriggered) {
            return false;
        }
        
        this.isEvacuationTriggered = true;
        this.currentMissionState = MissionState.EVACUATING;
        
        // Create evacuation objective
        MissionObjective evacuationObjective = MissionObjective.builder()
            .objectiveId("EVACUATION")
            .description("Evacuate all remaining squad members")
            .type(ObjectiveType.EXTRACT_VIP)
            .isRequired(true)
            .build();
        
        activeObjectives.add(evacuationObjective);
        
        return true;
    }
    
    public boolean evacuateUnit(Unit unit) {
        if (!isEvacuationTriggered || evacuatedUnits.contains(unit)) {
            return false;
        }
        
        evacuatedUnits.add(unit);
        squadMembers.remove(unit);
        
        // Check if all units evacuated
        if (squadMembers.isEmpty()) {
            completeMission(MissionResult.COMPLETE_SUCCESS);
        }
        
        return true;
    }
    
    public boolean loseUnit(Unit unit) {
        if (lostUnits.contains(unit)) {
            return false;
        }
        
        lostUnits.add(unit);
        squadMembers.remove(unit);
        
        // Check for squad wipe
        if (squadMembers.isEmpty()) {
            completeMission(MissionResult.SQUAD_WIPE);
        }
        
        return true;
    }
    
    public boolean addDynamicObjective(DynamicObjective dynamicObjective) {
        if (dynamicObjectives.containsKey(dynamicObjective.getObjectiveId())) {
            return false;
        }
        
        dynamicObjectives.put(dynamicObjective.getObjectiveId(), dynamicObjective);
        
        // Check if should activate immediately
        if (shouldActivateDynamicObjective(dynamicObjective)) {
            activateDynamicObjective(dynamicObjective);
        }
        
        return true;
    }
    
    public boolean activateDynamicObjective(DynamicObjective dynamicObjective) {
        if (!dynamicObjective.isActive()) {
            dynamicObjective.setActive(true);
            dynamicObjective.setActivationTime(LocalDateTime.now());
            
            // Convert to regular objective
            MissionObjective objective = MissionObjective.builder()
                .objectiveId(dynamicObjective.getObjectiveId())
                .description(dynamicObjective.getDescription())
                .type(ObjectiveType.TIMED_OBJECTIVE)
                .isRequired(false)
                .build();
            
            activeObjectives.add(objective);
            
            return true;
        }
        
        return false;
    }
    
    public boolean addObjectiveTrigger(ObjectiveTrigger trigger) {
        if (objectiveTriggers.contains(trigger)) {
            return false;
        }
        
        objectiveTriggers.add(trigger);
        
        // Check if trigger should activate
        if (shouldActivateTrigger(trigger)) {
            activateTrigger(trigger);
        }
        
        return true;
    }
    
    public boolean activateTrigger(ObjectiveTrigger trigger) {
        if (trigger.isActivated()) {
            return false;
        }
        
        trigger.setActivated(true);
        
        // Apply trigger effects to objectives
        for (String objectiveId : trigger.getAffectedObjectives()) {
            MissionObjective objective = findObjective(objectiveId);
            if (objective != null) {
                applyTriggerToObjective(trigger, objective);
            }
        }
        
        return true;
    }
    
    public boolean addMissionConsequence(MissionConsequence consequence) {
        if (consequences.contains(consequence)) {
            return false;
        }
        
        consequences.add(consequence);
        consequence.setAppliedTime(LocalDateTime.now());
        
        return true;
    }
    
    public boolean determineMissionResult() {
        // Check for immediate failure conditions
        if (checkFailureConditions()) {
            completeMission(MissionResult.FAILURE);
            return true;
        }
        
        // Check for success conditions
        if (checkSuccessConditions()) {
            completeMission(MissionResult.COMPLETE_SUCCESS);
            return true;
        }
        
        // Check for partial success
        if (completedObjectives.size() > 0 && failedObjectives.size() == 0) {
            completeMission(MissionResult.PARTIAL_SUCCESS);
            return true;
        }
        
        return false;
    }
    
    public boolean completeMission(MissionResult result) {
        this.currentResult = result;
        this.missionEndTime = LocalDateTime.now();
        
        switch (result) {
            case COMPLETE_SUCCESS:
            case PARTIAL_SUCCESS:
                this.currentMissionState = MissionState.SUCCESS;
                break;
            case SQUAD_WIPE:
            case TIMER_EXPIRED:
            case OBJECTIVE_FAILED:
            case FAILURE:
                this.currentMissionState = MissionState.FAILURE;
                break;
        }
        
        // Apply mission consequences
        applyMissionConsequences(result);
        
        return true;
    }
    
    // Helper Methods
    
    private boolean validateMissionSetup() {
        return activeObjectives != null && !activeObjectives.isEmpty() &&
               successConditions != null && !successConditions.isEmpty() &&
               failureConditions != null && !failureConditions.isEmpty();
    }
    
    private boolean canActivateObjective(MissionObjective objective) {
        return !objective.isCompleted() && !objective.isFailed() && 
               checkObjectiveDependencies(objective) && !checkObjectiveBlockers(objective);
    }
    
    private boolean checkObjectiveDependencies(MissionObjective objective) {
        if (objective.getDependencies() == null || objective.getDependencies().isEmpty()) {
            return true;
        }
        
        for (String dependencyId : objective.getDependencies()) {
            MissionObjective dependency = findObjective(dependencyId);
            if (dependency == null || !dependency.isCompleted()) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean checkObjectiveBlockers(MissionObjective objective) {
        if (objective.getBlockers() == null || objective.getBlockers().isEmpty()) {
            return false;
        }
        
        for (String blockerId : objective.getBlockers()) {
            MissionObjective blocker = findObjective(blockerId);
            if (blocker != null && (blocker.isCompleted() || blocker.isFailed())) {
                return true;
            }
        }
        
        return false;
    }
    
    private MissionObjective findObjective(String objectiveId) {
        // Search in active objectives
        for (MissionObjective objective : activeObjectives) {
            if (objective.getObjectiveId().equals(objectiveId)) {
                return objective;
            }
        }
        
        // Search in completed objectives
        for (MissionObjective objective : completedObjectives) {
            if (objective.getObjectiveId().equals(objectiveId)) {
                return objective;
            }
        }
        
        // Search in failed objectives
        for (MissionObjective objective : failedObjectives) {
            if (objective.getObjectiveId().equals(objectiveId)) {
                return objective;
            }
        }
        
        return null;
    }
    
    private void triggerDependentObjectives(MissionObjective completedObjective) {
        for (MissionObjective objective : activeObjectives) {
            if (objective.getDependencies() != null && 
                objective.getDependencies().contains(completedObjective.getObjectiveId())) {
                activateObjective(objective);
            }
        }
    }
    
    private void updateDynamicObjectives() {
        for (DynamicObjective dynamicObjective : dynamicObjectives.values()) {
            if (!dynamicObjective.isActive() && shouldActivateDynamicObjective(dynamicObjective)) {
                activateDynamicObjective(dynamicObjective);
            }
        }
    }
    
    private void updateMissionTimer() {
        if (missionTimer > 0) {
            missionTimer--;
        }
    }
    
    private boolean shouldActivateDynamicObjective(DynamicObjective dynamicObjective) {
        // Check trigger conditions
        for (ObjectiveTrigger trigger : dynamicObjective.getTriggers()) {
            if (shouldActivateTrigger(trigger)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean shouldActivateTrigger(ObjectiveTrigger trigger) {
        return trigger.getCurrentValue() >= trigger.getThreshold();
    }
    
    private void applyTriggerToObjective(ObjectiveTrigger trigger, MissionObjective objective) {
        // Apply trigger effects to objective
        // This could modify objective requirements, progress, or other properties
    }
    
    private void triggerFailureConsequences(FailureCondition condition) {
        MissionConsequence consequence = MissionConsequence.builder()
            .consequenceId("FAILURE_" + condition.getConditionId())
            .description("Mission failure: " + condition.getDescription())
            .type(ConsequenceType.STRATEGIC_IMPACT)
            .severity(calculateFailureSeverity(condition))
            .build();
        
        addMissionConsequence(consequence);
    }
    
    private void applyMissionConsequences(MissionResult result) {
        // Apply consequences based on mission result
        switch (result) {
            case COMPLETE_SUCCESS:
                applySuccessConsequences();
                break;
            case PARTIAL_SUCCESS:
                applyPartialSuccessConsequences();
                break;
            case SQUAD_WIPE:
            case FAILURE:
            case OBJECTIVE_FAILED:
            case TIMER_EXPIRED:
                applyFailureConsequences();
                break;
        }
    }
    
    private void applySuccessConsequences() {
        // Apply positive consequences for mission success
    }
    
    private void applyPartialSuccessConsequences() {
        // Apply mixed consequences for partial success
    }
    
    private void applyFailureConsequences() {
        // Apply negative consequences for mission failure
    }
    
    private boolean checkStealthSuccess() {
        // Check if mission was completed without being detected
        return true; // Placeholder
    }
    
    private boolean checkStealthBreached() {
        // Check if stealth was broken during mission
        return false; // Placeholder
    }
    
    private boolean checkVIPLost() {
        // Check if VIP was lost during mission
        return false; // Placeholder
    }
    
    private boolean checkAreaLost() {
        // Check if critical area was lost
        return false; // Placeholder
    }
    
    private int getMissionTime() {
        if (missionStartTime == null) {
            return 0;
        }
        return (int) java.time.Duration.between(missionStartTime, LocalDateTime.now()).toMinutes();
    }
    
    private int calculateResourceBonus() {
        // Calculate resource bonus based on mission performance
        return 0; // Placeholder
    }
    
    private int calculateIntelBonus() {
        // Calculate intel bonus based on mission performance
        return 0; // Placeholder
    }
    
    private int calculateFailureSeverity(FailureCondition condition) {
        // Calculate severity based on failure type and context
        return 1; // Placeholder
    }
    
    // Getters for mission state
    public MissionState getCurrentMissionState() {
        return currentMissionState;
    }
    
    public MissionResult getCurrentResult() {
        return currentResult;
    }
    
    public List<MissionObjective> getActiveObjectives() {
        return new ArrayList<>(activeObjectives);
    }
    
    public List<MissionObjective> getCompletedObjectives() {
        return new ArrayList<>(completedObjectives);
    }
    
    public List<MissionObjective> getFailedObjectives() {
        return new ArrayList<>(failedObjectives);
    }
    
    public int getMissionTimer() {
        return missionTimer;
    }
    
    public boolean isEvacuationAvailable() {
        return isEvacuationAvailable;
    }
    
    public boolean isEvacuationTriggered() {
        return isEvacuationTriggered;
    }
}

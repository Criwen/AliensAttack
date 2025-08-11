package com.aliensattack.core.model;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Mission Planning Manager - Centralized management of mission planning activities
 * Provides high-level interface for mission planning operations and coordination
 */
public class MissionPlanningManager {

    private AdvancedMissionPlanningPreparationSystem planningSystem;
    private Map<String, MissionPlan> missionPlans;
    private Map<String, MissionBriefing> missionBriefings;
    private Map<String, EquipmentLoadout> equipmentLoadouts;
    private Map<String, SoldierSelection> soldierSelections;
    private Map<String, MissionPreparationBonus> preparationBonuses;
    private Map<String, PreparationEvent> preparationEvents;
    private Map<String, Integer> preparationProgress;
    private Map<String, Boolean> preparationComplete;
    private int totalPlanningPoints;
    private int availablePlanningPoints;
    private LocalDateTime lastPlanningUpdate;

    public MissionPlanningManager() {
        this.planningSystem = AdvancedMissionPlanningPreparationSystem.builder().build();
        this.missionPlans = new HashMap<>();
        this.missionBriefings = new HashMap<>();
        this.equipmentLoadouts = new HashMap<>();
        this.soldierSelections = new HashMap<>();
        this.preparationBonuses = new HashMap<>();
        this.preparationEvents = new HashMap<>();
        this.preparationProgress = new HashMap<>();
        this.preparationComplete = new HashMap<>();
        this.totalPlanningPoints = 1500;
        this.availablePlanningPoints = 1500;
        this.lastPlanningUpdate = LocalDateTime.now();
    }

    /**
     * Create mission briefing
     */
    public boolean createMissionBriefing(String missionId, String missionName, String missionType) {
        if (!hasAvailablePlanningPoints(50)) {
            return false;
        }

        boolean success = planningSystem.createMissionBriefing(missionId, missionName, missionType);

        if (success) {
            // Create mission briefing tracking
            MissionBriefing briefing = new MissionBriefing(missionId, missionName, missionType);
            missionBriefings.put(briefing.getBriefingId(), briefing);

            // Deduct planning points
            deductPlanningPoints(50);

            // Create preparation event
            createPreparationEvent(missionId, "Mission briefing created", "BRIEFING", 1);
        }

        return success;
    }

    /**
     * Create mission plan
     */
    public boolean createMissionPlan(String missionId, String planName, String planType) {
        if (!hasAvailablePlanningPoints(75)) {
            return false;
        }

        boolean success = planningSystem.createMissionPlan(missionId, planName, planType);

        if (success) {
            // Create mission plan tracking
            MissionPlan plan = new MissionPlan(missionId, planName, planType);
            missionPlans.put(plan.getPlanId(), plan);

            // Deduct planning points
            deductPlanningPoints(75);

            // Create preparation event
            createPreparationEvent(missionId, "Mission plan created", "PLANNING", 2);
        }

        return success;
    }

    /**
     * Create equipment loadout
     */
    public boolean createEquipmentLoadout(String loadoutId, String name, String soldierId) {
        if (!hasAvailablePlanningPoints(25)) {
            return false;
        }

        boolean success = planningSystem.createEquipmentLoadout(loadoutId, name, soldierId);

        if (success) {
            // Create equipment loadout tracking
            EquipmentLoadout loadout = new EquipmentLoadout(loadoutId, name, soldierId);
            equipmentLoadouts.put(loadoutId, loadout);

            // Deduct planning points
            deductPlanningPoints(25);

            // Create preparation event
            createPreparationEvent(soldierId, "Equipment loadout created", "EQUIPMENT", 3);
        }

        return success;
    }

    /**
     * Create soldier selection
     */
    public boolean createSoldierSelection(String selectionId, String soldierId, String missionId) {
        if (!hasAvailablePlanningPoints(30)) {
            return false;
        }

        boolean success = planningSystem.selectSoldierForMission(missionId, soldierId, "SOLDIER");

        if (success) {
            // Create soldier selection tracking
            SoldierSelection selection = new SoldierSelection(selectionId, soldierId, missionId);
            soldierSelections.put(selectionId, selection);

            // Deduct planning points
            deductPlanningPoints(30);

            // Create preparation event
            createPreparationEvent(missionId, "Soldier selected: " + soldierId, "SELECTION", 4);
        }

        return success;
    }

    /**
     * Update preparation progress
     */
    public boolean updatePreparationProgress(String missionId, int progress) {
        boolean success = planningSystem.updatePreparationProgress(missionId, progress);

        if (success) {
            // Update preparation progress tracking
            int currentProgress = preparationProgress.getOrDefault(missionId, 0);
            preparationProgress.put(missionId, currentProgress + progress);

            // Check for completion
            if (currentProgress + progress >= 100) {
                preparationComplete.put(missionId, true);
            }

            // Update last planning update
            lastPlanningUpdate = LocalDateTime.now();

            // Create preparation event
            createPreparationEvent(missionId, "Preparation progress updated: " + progress, "PROGRESS", 5);
        }

        return success;
    }

    /**
     * Add preparation bonus
     */
    public boolean addPreparationBonus(String bonusId, String bonusName, String description,
                                    String bonusType, int duration) {
        if (!hasAvailablePlanningPoints(40)) {
            return false;
        }

        // Create preparation bonus tracking
        MissionPreparationBonus bonus = new MissionPreparationBonus(bonusId, bonusName, description, bonusType, duration);
        preparationBonuses.put(bonusId, bonus);

        // Deduct planning points
        deductPlanningPoints(40);

        // Create preparation event
        createPreparationEvent(bonusId, "Preparation bonus added: " + bonusName, "BONUS", 6);

        return true;
    }

    /**
     * Get mission briefing
     */
    public MissionBriefing getMissionBriefing(String briefingId) {
        return missionBriefings.get(briefingId);
    }

    /**
     * Get mission plan
     */
    public MissionPlan getMissionPlan(String planId) {
        return missionPlans.get(planId);
    }

    /**
     * Get equipment loadout
     */
    public EquipmentLoadout getEquipmentLoadout(String loadoutId) {
        return equipmentLoadouts.get(loadoutId);
    }

    /**
     * Get soldier selection
     */
    public SoldierSelection getSoldierSelection(String selectionId) {
        return soldierSelections.get(selectionId);
    }

    /**
     * Get preparation progress
     */
    public int getPreparationProgress(String missionId) {
        return preparationProgress.getOrDefault(missionId, 0);
    }

    /**
     * Check if preparation is complete
     */
    public boolean isPreparationComplete(String missionId) {
        return preparationComplete.getOrDefault(missionId, false);
    }

    /**
     * Get mission planning statistics
     */
    public MissionPlanningStatistics getMissionPlanningStatistics() {
        int totalPlans = missionPlans.size();
        int totalBriefings = missionBriefings.size();
        int totalLoadouts = equipmentLoadouts.size();
        int totalSelections = soldierSelections.size();
        int totalBonuses = preparationBonuses.size();
        int totalEvents = preparationEvents.size();
        int completedPreparations = (int) preparationComplete.values().stream().filter(Boolean::booleanValue).count();

        return new MissionPlanningStatistics(totalPlans, totalBriefings, totalLoadouts,
                                          totalSelections, totalBonuses, totalEvents,
                                          completedPreparations, totalPlanningPoints, availablePlanningPoints);
    }
    
    /**
     * Get last planning update time
     */
    public LocalDateTime getLastPlanningUpdate() {
        return lastPlanningUpdate;
    }
    
    /**
     * Check if planning system needs update
     */
    public boolean needsPlanningUpdate() {
        return lastPlanningUpdate.isBefore(LocalDateTime.now().minusHours(3));
    }

    // Helper methods

    private boolean hasAvailablePlanningPoints(int amount) {
        return availablePlanningPoints >= amount;
    }

    private void deductPlanningPoints(int amount) {
        availablePlanningPoints = Math.max(0, availablePlanningPoints - amount);
    }

    private void createPreparationEvent(String sourceId, String description, String eventType, int priority) {
        PreparationEvent event = new PreparationEvent(sourceId, description, eventType, priority);
        preparationEvents.put(event.getEventId(), event);
    }

    // Inner classes

    public static class MissionBriefing {
        private String briefingId;
        private String missionId;
        private String missionName;
        private String missionType;
        private LocalDateTime creationDate;
        private boolean isComplete;

        public MissionBriefing(String missionId, String missionName, String missionType) {
            this.briefingId = missionId + "_briefing";
            this.missionId = missionId;
            this.missionName = missionName;
            this.missionType = missionType;
            this.creationDate = LocalDateTime.now();
            this.isComplete = false;
        }

        // Getters
        public String getBriefingId() { return briefingId; }
        public String getMissionId() { return missionId; }
        public String getMissionName() { return missionName; }
        public String getMissionType() { return missionType; }
        public LocalDateTime getCreationDate() { return creationDate; }
        public boolean isComplete() { return isComplete; }
    }

    public static class MissionPlan {
        private String planId;
        private String missionId;
        private String planName;
        private String planType;
        private LocalDateTime creationDate;
        private boolean isApproved;

        public MissionPlan(String missionId, String planName, String planType) {
            this.planId = missionId + "_plan";
            this.missionId = missionId;
            this.planName = planName;
            this.planType = planType;
            this.creationDate = LocalDateTime.now();
            this.isApproved = false;
        }

        // Getters
        public String getPlanId() { return planId; }
        public String getMissionId() { return missionId; }
        public String getPlanName() { return planName; }
        public String getPlanType() { return planType; }
        public LocalDateTime getCreationDate() { return creationDate; }
        public boolean isApproved() { return isApproved; }
    }

    public static class EquipmentLoadout {
        private String loadoutId;
        private String name;
        private String soldierId;
        private LocalDateTime creationDate;
        private boolean isAssigned;

        public EquipmentLoadout(String loadoutId, String name, String soldierId) {
            this.loadoutId = loadoutId;
            this.name = name;
            this.soldierId = soldierId;
            this.creationDate = LocalDateTime.now();
            this.isAssigned = false;
        }

        // Getters
        public String getLoadoutId() { return loadoutId; }
        public String getName() { return name; }
        public String getSoldierId() { return soldierId; }
        public LocalDateTime getCreationDate() { return creationDate; }
        public boolean isAssigned() { return isAssigned; }
    }

    public static class SoldierSelection {
        private String selectionId;
        private String soldierId;
        private String missionId;
        private LocalDateTime selectionDate;
        private boolean isConfirmed;

        public SoldierSelection(String selectionId, String soldierId, String missionId) {
            this.selectionId = selectionId;
            this.soldierId = soldierId;
            this.missionId = missionId;
            this.selectionDate = LocalDateTime.now();
            this.isConfirmed = false;
        }

        // Getters
        public String getSelectionId() { return selectionId; }
        public String getSoldierId() { return soldierId; }
        public String getMissionId() { return missionId; }
        public LocalDateTime getSelectionDate() { return selectionDate; }
        public boolean isConfirmed() { return isConfirmed; }
    }

    public static class MissionPreparationBonus {
        private String bonusId;
        private String bonusName;
        private String description;
        private String bonusType;
        private int duration;
        private boolean isActive;
        private LocalDateTime activationDate;

        public MissionPreparationBonus(String bonusId, String bonusName, String description,
                                    String bonusType, int duration) {
            this.bonusId = bonusId;
            this.bonusName = bonusName;
            this.description = description;
            this.bonusType = bonusType;
            this.duration = duration;
            this.isActive = false;
            this.activationDate = null;
        }

        // Getters and setters
        public String getBonusId() { return bonusId; }
        public String getBonusName() { return bonusName; }
        public String getDescription() { return description; }
        public String getBonusType() { return bonusType; }
        public int getDuration() { return duration; }
        public boolean isActive() { return isActive; }
        public void setActive(boolean active) { isActive = active; }
        public LocalDateTime getActivationDate() { return activationDate; }
        public void setActivationDate(LocalDateTime activationDate) { this.activationDate = activationDate; }
    }

    public static class PreparationEvent {
        private String eventId;
        private String sourceId;
        private String description;
        private String eventType;
        private int priority;
        private LocalDateTime eventDate;

        public PreparationEvent(String sourceId, String description, String eventType, int priority) {
            this.eventId = UUID.randomUUID().toString();
            this.sourceId = sourceId;
            this.description = description;
            this.eventType = eventType;
            this.priority = priority;
            this.eventDate = LocalDateTime.now();
        }

        // Getters
        public String getEventId() { return eventId; }
        public String getSourceId() { return sourceId; }
        public String getDescription() { return description; }
        public String getEventType() { return eventType; }
        public int getPriority() { return priority; }
        public LocalDateTime getEventDate() { return eventDate; }
    }

    public static class MissionPlanningStatistics {
        private int totalPlans;
        private int totalBriefings;
        private int totalLoadouts;
        private int totalSelections;
        private int totalBonuses;
        private int totalEvents;
        private int completedPreparations;
        private int totalPlanningPoints;
        private int availablePlanningPoints;

        public MissionPlanningStatistics(int totalPlans, int totalBriefings, int totalLoadouts,
                                      int totalSelections, int totalBonuses, int totalEvents,
                                      int completedPreparations, int totalPlanningPoints, int availablePlanningPoints) {
            this.totalPlans = totalPlans;
            this.totalBriefings = totalBriefings;
            this.totalLoadouts = totalLoadouts;
            this.totalSelections = totalSelections;
            this.totalBonuses = totalBonuses;
            this.totalEvents = totalEvents;
            this.completedPreparations = completedPreparations;
            this.totalPlanningPoints = totalPlanningPoints;
            this.availablePlanningPoints = availablePlanningPoints;
        }

        // Getters
        public int getTotalPlans() { return totalPlans; }
        public int getTotalBriefings() { return totalBriefings; }
        public int getTotalLoadouts() { return totalLoadouts; }
        public int getTotalSelections() { return totalSelections; }
        public int getTotalBonuses() { return totalBonuses; }
        public int getTotalEvents() { return totalEvents; }
        public int getCompletedPreparations() { return completedPreparations; }
        public int getTotalPlanningPoints() { return totalPlanningPoints; }
        public int getAvailablePlanningPoints() { return availablePlanningPoints; }
        public double getPlanningPointsUsage() {
            return totalPlanningPoints > 0 ? (double) (totalPlanningPoints - availablePlanningPoints) / totalPlanningPoints : 0.0;
        }
        public double getCompletionRate() {
            return totalPlans > 0 ? (double) completedPreparations / totalPlans : 0.0;
        }
    }
}

package com.aliensattack.core.model;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Strategic Layer Manager - Centralized management of strategic layer activities
 * Provides high-level interface for strategic operations and coordination
 */
public class StrategicLayerManager {

    private AdvancedStrategicLayerIntegrationSystem strategicSystem;
    private Map<String, StrategicProject> strategicProjects;
    private Map<String, StrategicResource> strategicResources;
    private Map<String, StrategicDecision> strategicDecisions;
    private Map<String, BaseFacility> baseFacilities;
    private Map<String, GlobalThreat> globalThreats;
    private Map<String, AdvancedStrategicLayerIntegrationSystem.StrategicFeedback> strategicFeedback;
    private Map<String, IntelGathering> intelGathering;
    private int totalStrategicPoints;
    private int availableStrategicPoints;
    private LocalDateTime lastStrategicUpdate;

    public StrategicLayerManager() {
        this.strategicSystem = new AdvancedStrategicLayerIntegrationSystem();
        this.strategicProjects = new HashMap<>();
        this.strategicResources = new HashMap<>();
        this.strategicDecisions = new HashMap<>();
        this.baseFacilities = new HashMap<>();
        this.globalThreats = new HashMap<>();
        this.strategicFeedback = new HashMap<>();
        this.intelGathering = new HashMap<>();
        this.totalStrategicPoints = 2000;
        this.availableStrategicPoints = 2000;
        this.lastStrategicUpdate = LocalDateTime.now();
    }

    /**
     * Initialize strategic state
     */
    public boolean initializeStrategicState(String stateId, String name) {
        if (!hasAvailableStrategicPoints(100)) {
            return false;
        }

        boolean success = strategicSystem.initializeStrategicState(stateId, name);

        if (success) {
            // Deduct strategic points
            deductStrategicPoints(100);

            // Create strategic feedback
            createStrategicFeedback(stateId, "Strategic state initialized", 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.SUCCESS, 1);
        }

        return success;
    }

    /**
     * Update strategic state
     */
    public boolean updateStrategicState(String stateId) {
        boolean success = strategicSystem.updateStrategicState(stateId);

        if (success) {
            // Update last strategic update
            lastStrategicUpdate = LocalDateTime.now();

            // Check for strategic consequences
            checkStrategicConsequences(stateId);
        }

        return success;
    }

    /**
     * Add strategic resource
     */
    public boolean addStrategicResource(String resourceId, String name,
                                     AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType type,
                                     int maxAmount, int regenerationRate) {

        if (!hasAvailableStrategicPoints(50)) {
            return false;
        }

        boolean success = strategicSystem.addStrategicResource(resourceId, name, type, maxAmount, regenerationRate);

        if (success) {
            // Create strategic resource tracking
            StrategicResource resource = new StrategicResource(resourceId, name, type, maxAmount, regenerationRate);
            strategicResources.put(resourceId, resource);

            // Deduct strategic points
            deductStrategicPoints(50);

            // Create strategic feedback
            createStrategicFeedback(resourceId, "Strategic resource added", 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.SUCCESS, 2);
        }

        return success;
    }

    /**
     * Allocate strategic resource
     */
    public boolean allocateStrategicResource(String resourceId, String targetId, int amount,
                                          AdvancedStrategicLayerIntegrationSystem.StrategicResource.AllocationType type) {

        StrategicResource resource = strategicResources.get(resourceId);
        if (resource == null || resource.getCurrentAmount() < amount) {
            return false;
        }

        boolean success = strategicSystem.allocateStrategicResource(resourceId, targetId, amount, type);

        if (success) {
            // Update resource tracking
            resource.setCurrentAmount(resource.getCurrentAmount() - amount);

            // Create strategic feedback
            createStrategicFeedback(targetId, "Resource allocated: " + amount, 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.INFORMATION, 3);
        }

        return success;
    }

    /**
     * Process tactical impact
     */
    public boolean processTacticalImpact(String missionId, 
                                       AdvancedStrategicLayerIntegrationSystem.StrategicResource.ImpactType impactType,
                                       int magnitude, List<String> affectedSystems) {

        if (!hasAvailableStrategicPoints(magnitude / 10)) {
            return false;
        }

        boolean success = strategicSystem.processTacticalImpact(missionId, impactType, magnitude, affectedSystems);

        if (success) {
            // Deduct strategic points
            deductStrategicPoints(magnitude / 10);

            // Create strategic feedback
            createStrategicFeedback(missionId, "Tactical impact processed: " + impactType, 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.INFORMATION, 4);
        }

        return success;
    }

    /**
     * Add strategic consequence
     */
    public boolean addStrategicConsequence(String source, 
                                         AdvancedStrategicLayerIntegrationSystem.StrategicConsequence.ConsequenceType type,
                                         int magnitude, List<String> affectedSystems) {

        if (!hasAvailableStrategicPoints(magnitude / 5)) {
            return false;
        }

        boolean success = strategicSystem.addStrategicConsequence(source, type, magnitude, affectedSystems);

        if (success) {
            // Deduct strategic points
            deductStrategicPoints(magnitude / 5);

            // Create strategic feedback
            createStrategicFeedback(source, "Strategic consequence applied: " + type, 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.WARNING, 5);
        }

        return success;
    }

    /**
     * Make strategic decision
     */
    public boolean makeStrategicDecision(String description, 
                                       AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType type,
                                       int cost, List<String> consequences) {

        if (!hasAvailableStrategicPoints(cost)) {
            return false;
        }

        boolean success = strategicSystem.makeStrategicDecision(description, type, cost, consequences);

        if (success) {
            // Create strategic decision tracking
            StrategicDecision decision = new StrategicDecision(description, type, cost, consequences);
            strategicDecisions.put(decision.getDecisionId(), decision);

            // Deduct strategic points
            deductStrategicPoints(cost);

            // Create strategic feedback
            createStrategicFeedback("DECISION", "Strategic decision made: " + description, 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.SUCCESS, 6);
        }

        return success;
    }

    /**
     * Implement strategic decision
     */
    public boolean implementStrategicDecision(String decisionId) {
        StrategicDecision decision = strategicDecisions.get(decisionId);
        if (decision == null) {
            return false;
        }

        boolean success = strategicSystem.implementStrategicDecision(decisionId);

        if (success) {
            // Update decision tracking
            decision.setImplemented(true);

            // Create strategic feedback
            createStrategicFeedback(decisionId, "Strategic decision implemented", 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.SUCCESS, 7);
        }

        return success;
    }

    /**
     * Add base facility
     */
    public boolean addBaseFacility(String facilityId, String name,
                                 AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType type,
                                 int level, int capacity) {

        if (!hasAvailableStrategicPoints(level * 25)) {
            return false;
        }

        boolean success = strategicSystem.addBaseFacility(facilityId, name, type, level, capacity);

        if (success) {
            // Create base facility tracking
            BaseFacility facility = new BaseFacility(facilityId, name, type, level, capacity);
            baseFacilities.put(facilityId, facility);

            // Deduct strategic points
            deductStrategicPoints(level * 25);

            // Create strategic feedback
            createStrategicFeedback(facilityId, "Base facility added: " + name, 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.SUCCESS, 8);
        }

        return success;
    }

    /**
     * Add global threat
     */
    public boolean addGlobalThreat(String threatId, String name, int level, int progression) {

        if (!hasAvailableStrategicPoints(level * 10)) {
            return false;
        }

        boolean success = strategicSystem.addGlobalThreat(threatId, name, level, progression);

        if (success) {
            // Create global threat tracking
            GlobalThreat threat = new GlobalThreat(threatId, name, level, progression);
            globalThreats.put(threatId, threat);

            // Deduct strategic points
            deductStrategicPoints(level * 10);

            // Create strategic feedback
            createStrategicFeedback(threatId, "Global threat detected: " + name, 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.THREAT, 9);
        }

        return success;
    }

    /**
     * Gather intel
     */
    public boolean gatherIntel(String source, 
                             AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType type,
                             int value, List<String> applications) {

        if (!hasAvailableStrategicPoints(value / 2)) {
            return false;
        }

        boolean success = strategicSystem.gatherIntel(source, type, value, applications);

        if (success) {
            // Create intel gathering tracking
            IntelGathering intel = new IntelGathering(source, type, value, applications);
            intelGathering.put(intel.getIntelId(), intel);

            // Deduct strategic points
            deductStrategicPoints(value / 2);

            // Create strategic feedback
            createStrategicFeedback(source, "Intel gathered: " + type, 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.INFORMATION, 10);
        }

        return success;
    }

    /**
     * Analyze intel
     */
    public boolean analyzeIntel(String intelId) {
        IntelGathering intel = intelGathering.get(intelId);
        if (intel == null) {
            return false;
        }

        boolean success = strategicSystem.analyzeIntel(intelId);

        if (success) {
            // Update intel tracking
            intel.setAnalyzed(true);

            // Create strategic feedback
            createStrategicFeedback(intelId, "Intel analyzed", 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.SUCCESS, 11);
        }

        return success;
    }

    /**
     * Get strategic statistics
     */
    public StrategicStatistics getStrategicStatistics() {
        int totalProjects = strategicProjects.size();
        int totalResources = strategicResources.size();
        int totalDecisions = strategicDecisions.size();
        int totalFacilities = baseFacilities.size();
        int totalThreats = globalThreats.size();
        int totalIntel = intelGathering.size();

        return new StrategicStatistics(totalProjects, totalResources, totalDecisions,
                                    totalFacilities, totalThreats, totalIntel,
                                    totalStrategicPoints, availableStrategicPoints);
    }
    
    /**
     * Get strategic feedback for source
     */
    public AdvancedStrategicLayerIntegrationSystem.StrategicFeedback getStrategicFeedback(String source) {
        return strategicFeedback.get(source);
    }
    
    /**
     * Get all strategic feedback
     */
    public Map<String, AdvancedStrategicLayerIntegrationSystem.StrategicFeedback> getAllStrategicFeedback() {
        return new HashMap<>(strategicFeedback);
    }
    
    /**
     * Get last strategic update time
     */
    public LocalDateTime getLastStrategicUpdate() {
        return lastStrategicUpdate;
    }
    
    /**
     * Check if strategic layer needs update
     */
    public boolean needsStrategicUpdate() {
        return lastStrategicUpdate.isBefore(LocalDateTime.now().minusHours(4));
    }

    // Helper methods

    private boolean hasAvailableStrategicPoints(int amount) {
        return availableStrategicPoints >= amount;
    }

    private void deductStrategicPoints(int amount) {
        availableStrategicPoints = Math.max(0, availableStrategicPoints - amount);
    }

    private void checkStrategicConsequences(String stateId) {
        // Check for strategic consequences based on state
        AdvancedStrategicLayerIntegrationSystem.StrategicState state = strategicSystem.getStrategicState(stateId);
        if (state != null && state.getGlobalThreatLevel() > 7) {
            createStrategicFeedback(stateId, "Critical threat level detected", 
                AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType.WARNING, 12);
        }
    }

    private void createStrategicFeedback(String source, String message,
                                       AdvancedStrategicLayerIntegrationSystem.StrategicFeedback.FeedbackType type, int priority) {
        strategicSystem.addStrategicFeedback(source, type, message, priority);
    }

    // Inner classes

    public static class StrategicProject {
        private String projectId;
        private String name;
        private String description;
        private int progress;
        private int requiredProgress;
        private boolean isCompleted;
        private LocalDateTime startDate;
        private LocalDateTime lastUpdate;

        public StrategicProject(String projectId, String name, String description, int requiredProgress) {
            this.projectId = projectId;
            this.name = name;
            this.description = description;
            this.progress = 0;
            this.requiredProgress = requiredProgress;
            this.isCompleted = false;
            this.startDate = LocalDateTime.now();
            this.lastUpdate = LocalDateTime.now();
        }

        public void updateProgress(int increment) {
            this.progress += increment;
            this.lastUpdate = LocalDateTime.now();

            if (progress >= requiredProgress) {
                isCompleted = true;
            }
        }

        public double getProgressPercentage() {
            return requiredProgress > 0 ? (double) progress / requiredProgress : 0.0;
        }

        // Getters
        public String getProjectId() { return projectId; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public int getProgress() { return progress; }
        public int getRequiredProgress() { return requiredProgress; }
        public boolean isCompleted() { return isCompleted; }
        public LocalDateTime getStartDate() { return startDate; }
        public LocalDateTime getLastUpdate() { return lastUpdate; }
    }

    public static class StrategicResource {
        private String resourceId;
        private String name;
        private AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType type;
        private int currentAmount;
        private int maxAmount;
        private int regenerationRate;
        private LocalDateTime lastRegeneration;

        public StrategicResource(String resourceId, String name,
                              AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType type,
                              int maxAmount, int regenerationRate) {
            this.resourceId = resourceId;
            this.name = name;
            this.type = type;
            this.currentAmount = maxAmount;
            this.maxAmount = maxAmount;
            this.regenerationRate = regenerationRate;
            this.lastRegeneration = LocalDateTime.now();
        }

        // Getters and setters
        public String getResourceId() { return resourceId; }
        public String getName() { return name; }
        public AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType getType() { return type; }
        public int getCurrentAmount() { return currentAmount; }
        public void setCurrentAmount(int currentAmount) { this.currentAmount = currentAmount; }
        public int getMaxAmount() { return maxAmount; }
        public int getRegenerationRate() { return regenerationRate; }
        public LocalDateTime getLastRegeneration() { return lastRegeneration; }
    }

    public static class StrategicDecision {
        private String decisionId;
        private String description;
        private AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType type;
        private int cost;
        private List<String> consequences;
        private boolean isImplemented;
        private LocalDateTime decisionDate;

        public StrategicDecision(String description, AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType type,
                              int cost, List<String> consequences) {
            this.decisionId = UUID.randomUUID().toString();
            this.description = description;
            this.type = type;
            this.cost = cost;
            this.consequences = new ArrayList<>(consequences);
            this.isImplemented = false;
            this.decisionDate = LocalDateTime.now();
        }

        // Getters and setters
        public String getDecisionId() { return decisionId; }
        public String getDescription() { return description; }
        public AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType getType() { return type; }
        public int getCost() { return cost; }
        public List<String> getConsequences() { return new ArrayList<>(consequences); }
        public boolean isImplemented() { return isImplemented; }
        public void setImplemented(boolean implemented) { isImplemented = implemented; }
        public LocalDateTime getDecisionDate() { return decisionDate; }
    }

    public static class BaseFacility {
        private String facilityId;
        private String name;
        private AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType type;
        private int level;
        private int capacity;
        private int currentUsage;
        private boolean isOperational;
        private LocalDateTime constructionDate;

        public BaseFacility(String facilityId, String name,
                          AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType type,
                          int level, int capacity) {
            this.facilityId = facilityId;
            this.name = name;
            this.type = type;
            this.level = level;
            this.capacity = capacity;
            this.currentUsage = 0;
            this.isOperational = true;
            this.constructionDate = LocalDateTime.now();
        }

        // Getters
        public String getFacilityId() { return facilityId; }
        public String getName() { return name; }
        public AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType getType() { return type; }
        public int getLevel() { return level; }
        public int getCapacity() { return capacity; }
        public int getCurrentUsage() { return currentUsage; }
        public boolean isOperational() { return isOperational; }
        public LocalDateTime getConstructionDate() { return constructionDate; }
    }

    public static class GlobalThreat {
        private String threatId;
        private String name;
        private int level;
        private int progression;
        private boolean isActive;
        private LocalDateTime detectionDate;

        public GlobalThreat(String threatId, String name, int level, int progression) {
            this.threatId = threatId;
            this.name = name;
            this.level = level;
            this.progression = progression;
            this.isActive = true;
            this.detectionDate = LocalDateTime.now();
        }

        // Getters
        public String getThreatId() { return threatId; }
        public String getName() { return name; }
        public int getLevel() { return level; }
        public int getProgression() { return progression; }
        public boolean isActive() { return isActive; }
        public LocalDateTime getDetectionDate() { return detectionDate; }
    }

    public static class IntelGathering {
        private String intelId;
        private String source;
        private AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType type;
        private int value;
        private List<String> applications;
        private boolean isAnalyzed;
        private LocalDateTime gatheredDate;

        public IntelGathering(String source, AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType type,
                            int value, List<String> applications) {
            this.intelId = UUID.randomUUID().toString();
            this.source = source;
            this.type = type;
            this.value = value;
            this.applications = new ArrayList<>(applications);
            this.isAnalyzed = false;
            this.gatheredDate = LocalDateTime.now();
        }

        // Getters and setters
        public String getIntelId() { return intelId; }
        public String getSource() { return source; }
        public AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType getType() { return type; }
        public int getValue() { return value; }
        public List<String> getApplications() { return new ArrayList<>(applications); }
        public boolean isAnalyzed() { return isAnalyzed; }
        public void setAnalyzed(boolean analyzed) { isAnalyzed = analyzed; }
        public LocalDateTime getGatheredDate() { return gatheredDate; }
    }

    public static class StrategicStatistics {
        private int totalProjects;
        private int totalResources;
        private int totalDecisions;
        private int totalFacilities;
        private int totalThreats;
        private int totalIntel;
        private int totalStrategicPoints;
        private int availableStrategicPoints;

        public StrategicStatistics(int totalProjects, int totalResources, int totalDecisions,
                                int totalFacilities, int totalThreats, int totalIntel,
                                int totalStrategicPoints, int availableStrategicPoints) {
            this.totalProjects = totalProjects;
            this.totalResources = totalResources;
            this.totalDecisions = totalDecisions;
            this.totalFacilities = totalFacilities;
            this.totalThreats = totalThreats;
            this.totalIntel = totalIntel;
            this.totalStrategicPoints = totalStrategicPoints;
            this.availableStrategicPoints = availableStrategicPoints;
        }

        // Getters
        public int getTotalProjects() { return totalProjects; }
        public int getTotalResources() { return totalResources; }
        public int getTotalDecisions() { return totalDecisions; }
        public int getTotalFacilities() { return totalFacilities; }
        public int getTotalThreats() { return totalThreats; }
        public int getTotalIntel() { return totalIntel; }
        public int getTotalStrategicPoints() { return totalStrategicPoints; }
        public int getAvailableStrategicPoints() { return availableStrategicPoints; }
        public double getStrategicPointsUsage() {
            return totalStrategicPoints > 0 ? (double) (totalStrategicPoints - availableStrategicPoints) / totalStrategicPoints : 0.0;
        }
    }
}

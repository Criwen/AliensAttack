package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Strategic Layer Manager for XCOM 2 Strategic Layer
 * Manages strategic projects, global threats, and strategic decisions
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategicLayerManager {
    
    private String strategicSystem;
    private Map<String, String> strategicProjects;
    private Map<String, String> globalThreats;
    private Map<String, String> strategicStatistics;
    private List<String> strategicFeedbacks;
    private List<String> strategicResources;
    private List<String> strategicDecisions;
    private List<String> manufacturingFacilities;
    private List<String> intelGatherings;
    private int strategicPoints;
    private int globalThreatLevel;
    private Random random;
    
    /**
     * Initialize the strategic layer manager
     */
    public void initialize() {
        if (strategicSystem == null) {
            strategicSystem = new String();
        }
        if (strategicProjects == null) {
            strategicProjects = new HashMap<>();
        }
        if (globalThreats == null) {
            globalThreats = new HashMap<>();
        }
        if (strategicStatistics == null) {
            strategicStatistics = new HashMap<>();
        }
        if (strategicFeedbacks == null) {
            strategicFeedbacks = new ArrayList<>();
        }
        if (strategicResources == null) {
            strategicResources = new ArrayList<>();
        }
        if (strategicDecisions == null) {
            strategicDecisions = new ArrayList<>();
        }
        if (manufacturingFacilities == null) {
            manufacturingFacilities = new ArrayList<>();
        }
        if (intelGatherings == null) {
            intelGatherings = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        strategicPoints = 1000;
        globalThreatLevel = 25;
        
        initializeStrategicProjects();
        initializeGlobalThreats();
        initializeStrategicStatistics();
    }
    
    /**
     * Initialize strategic projects
     */
    private void initializeStrategicProjects() {
        // Create initial strategic projects
        String researchProject = "RESEARCH_PROJECT";
        
        strategicProjects.put(researchProject, researchProject);
    }
    
    /**
     * Initialize global threats
     */
    private void initializeGlobalThreats() {
        // Create initial global threats
        String alienThreat = "ALIEN_THREAT";
        
        globalThreats.put(alienThreat, alienThreat);
    }
    
    /**
     * Initialize strategic statistics
     */
    private void initializeStrategicStatistics() {
        // Create initial strategic statistics
        String performanceStats = "PERFORMANCE_STATS";
        
        strategicStatistics.put(performanceStats, performanceStats);
    }
    
    /**
     * Start strategic project
     */
    public boolean startStrategicProject(String projectId) {
        String project = strategicProjects.get(projectId);
        if (project != null && !project.equals("ACTIVE") && strategicPoints >= 500) { // Assuming project cost is 500 for now
            strategicProjects.put(projectId, "ACTIVE");
            strategicPoints -= 500;
            return true;
        }
        return false;
    }
    
    /**
     * Update strategic projects
     */
    public void updateStrategicProjects() {
        for (String projectId : strategicProjects.keySet()) {
            String project = strategicProjects.get(projectId);
            if (project.equals("ACTIVE") && !project.equals("COMPLETED")) {
                int progress = 0; // Placeholder for actual progress calculation
                strategicProjects.put(projectId, "PROGRESS:" + progress);
                
                if (progress >= 100) {
                    completeStrategicProject(projectId);
                }
            }
        }
    }
    
    /**
     * Complete strategic project
     */
    private void completeStrategicProject(String projectId) {
        strategicProjects.put(projectId, "COMPLETED");
        strategicProjects.put(projectId, "INACTIVE");
        
        // Add strategic points
        strategicPoints += 100;
        
        // Update global threat level
        updateGlobalThreatLevel();
    }
    
    /**
     * Update global threat level
     */
    private void updateGlobalThreatLevel() {
        // Simple threat level calculation
        int activeThreats = (int) globalThreats.values().stream()
                .filter(threat -> threat.equals("ACTIVE"))
                .count();
        
        globalThreatLevel = Math.min(100, 25 + (activeThreats * 15));
    }
    
    /**
     * Get strategic status
     */
    public String getStrategicStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Strategic Layer Status:\n");
        status.append("- Strategic Points: ").append(strategicPoints).append("\n");
        status.append("- Global Threat Level: ").append(globalThreatLevel).append("\n");
        status.append("- Active Projects: ").append(strategicProjects.values().stream().filter(project -> project.equals("ACTIVE")).count()).append("\n");
        status.append("- Completed Projects: ").append(strategicProjects.values().stream().filter(project -> project.equals("COMPLETED")).count()).append("\n");
        status.append("- Active Threats: ").append(globalThreats.values().stream().filter(threat -> threat.equals("ACTIVE")).count()).append("\n");
        status.append("- Strategic Statistics: ").append(strategicStatistics.size()).append("\n");
        
        return status.toString();
    }
}

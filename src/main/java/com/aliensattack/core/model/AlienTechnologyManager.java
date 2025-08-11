package com.aliensattack.core.model;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Alien Technology Manager - Centralized management of alien technology development
 * Provides high-level interface for technology analysis, development, and deployment
 */
public class AlienTechnologyManager {
    
    private AdvancedAlienResearchTechnologySystem technologySystem;
    private Map<String, TechnologyProject> technologyProjects;
    private Map<String, TechnologyAnalysis> technologyAnalyses;
    private Map<String, TechnologyDeployment> technologyDeployments;
    private List<TechnologyBreakthrough> technologyBreakthroughs;
    private Map<String, TechnologyReverseEngineering> reverseEngineeringProjects;
    private Map<String, TechnologyCapability> technologyCapabilities;
    private int totalTechnologyPoints;
    private int availableTechnologyPoints;
    
    public AlienTechnologyManager() {
        this.technologySystem = new AdvancedAlienResearchTechnologySystem();
        this.technologyProjects = new HashMap<>();
        this.technologyAnalyses = new HashMap<>();
        this.technologyDeployments = new HashMap<>();
        this.technologyBreakthroughs = new ArrayList<>();
        this.reverseEngineeringProjects = new HashMap<>();
        this.technologyCapabilities = new HashMap<>();
        this.totalTechnologyPoints = 1000;
        this.availableTechnologyPoints = 1000;
    }
    
    /**
     * Start a new technology project
     */
    public boolean startTechnologyProject(String projectId, String name,
                                       AdvancedAlienResearchTechnologySystem.TechnologyType type,
                                       int complexity, String teamId) {
        
        if (!hasAvailableTechnologyPoints(complexity)) {
            return false;
        }
        
        boolean success = technologySystem.startTechnologyProject(projectId, name, type, complexity);
        
        if (success) {
            // Create technology project tracking
            TechnologyProject project = new TechnologyProject(projectId, name, type, complexity, teamId);
            technologyProjects.put(projectId, project);
            
            // Allocate technology points
            deductTechnologyPoints(complexity * 10);
            
            // Create breakthrough milestone
            createTechnologyBreakthrough(projectId, "Technology Project Started");
        }
        
        return success;
    }
    
    /**
     * Update technology project progress
     */
    public boolean updateTechnologyProgress(String projectId, int progressIncrement, String engineer) {
        boolean success = technologySystem.updateProjectProgress(projectId, progressIncrement);
        
        if (success) {
            // Update project tracking
            TechnologyProject project = technologyProjects.get(projectId);
            if (project != null) {
                project.updateProgress(progressIncrement, engineer);
            }
            
            // Check for breakthroughs
            checkTechnologyBreakthroughs(projectId);
        }
        
        return success;
    }
    
    /**
     * Complete technology project
     */
    public boolean completeTechnologyProject(String projectId) {
        boolean success = technologySystem.completeTechnologyProject(projectId);
        
        if (success) {
            // Create completion breakthrough
            createTechnologyBreakthrough(projectId, "Technology Project Completed");
            
            // Award technology points
            awardTechnologyPoints(calculateCompletionBonus(projectId));
            
            // Process technology effects
            processTechnologyEffects(projectId);
        }
        
        return success;
    }
    
    /**
     * Discover alien technology
     */
    public boolean discoverAlienTechnology(String technologyId, String name,
                                         AdvancedAlienResearchTechnologySystem.TechnologyCategory category,
                                         AdvancedAlienResearchTechnologySystem.TechnologyLevel level) {
        
        boolean success = technologySystem.discoverAlienTechnology(technologyId, name, category, level);
        
        if (success) {
            // Create technology analysis
            TechnologyAnalysis analysis = new TechnologyAnalysis(technologyId, category, level);
            technologyAnalyses.put(technologyId, analysis);
            
            // Create discovery breakthrough
            createTechnologyBreakthrough(technologyId, "Alien Technology Discovered");
        }
        
        return success;
    }
    
    /**
     * Analyze alien technology
     */
    public boolean analyzeAlienTechnology(String technologyId, String analyst) {
        boolean success = technologySystem.analyzeAlienTechnology(technologyId);
        
        if (success) {
            // Update analysis
            TechnologyAnalysis analysis = technologyAnalyses.get(technologyId);
            if (analysis != null) {
                analysis.setAnalyzed(true);
                analysis.setAnalyst(analyst);
                analysis.setAnalysisDate(LocalDateTime.now());
            }
            
            // Create analysis breakthrough
            createTechnologyBreakthrough(technologyId, "Technology Analysis Completed");
            
            // Generate capabilities
            generateTechnologyCapabilities(technologyId);
        }
        
        return success;
    }
    
    /**
     * Start reverse engineering project
     */
    public boolean startReverseEngineering(String technologyId, String projectName, String teamId) {
        TechnologyReverseEngineering project = new TechnologyReverseEngineering(technologyId, projectName, teamId);
        reverseEngineeringProjects.put(technologyId, project);
        
        return true;
    }
    
    /**
     * Deploy technology
     */
    public boolean deployTechnology(String technologyId, String deploymentLocation, String deploymentTeam) {
        TechnologyDeployment deployment = new TechnologyDeployment(technologyId, deploymentLocation, deploymentTeam);
        technologyDeployments.put(technologyId, deployment);
        
        return true;
    }
    
    /**
     * Get technology analysis
     */
    public TechnologyAnalysis getTechnologyAnalysis(String technologyId) {
        return technologyAnalyses.get(technologyId);
    }
    
    /**
     * Get available technology projects
     */
    public List<TechnologyProject> getAvailableTechnologyProjects() {
        List<TechnologyProject> available = new ArrayList<>();
        
        for (TechnologyProject project : technologyProjects.values()) {
            if (!project.isCompleted()) {
                available.add(project);
            }
        }
        
        return available;
    }
    
    /**
     * Get completed technology projects
     */
    public List<TechnologyProject> getCompletedTechnologyProjects() {
        List<TechnologyProject> completed = new ArrayList<>();
        
        for (TechnologyProject project : technologyProjects.values()) {
            if (project.isCompleted()) {
                completed.add(project);
            }
        }
        
        return completed;
    }
    
    /**
     * Get technology statistics
     */
    public TechnologyStatistics getTechnologyStatistics() {
        int totalProjects = technologyProjects.size();
        int completedProjects = getCompletedTechnologyProjects().size();
        int inProgressProjects = totalProjects - completedProjects;
        int discoveredTechnologies = technologyAnalyses.size();
        int analyzedTechnologies = (int) technologyAnalyses.values().stream()
                .filter(TechnologyAnalysis::isAnalyzed).count();
        
        return new TechnologyStatistics(totalProjects, completedProjects, inProgressProjects,
                                     discoveredTechnologies, analyzedTechnologies,
                                     totalTechnologyPoints, availableTechnologyPoints);
    }
    
    // Helper methods
    
    private boolean hasAvailableTechnologyPoints(int complexity) {
        return availableTechnologyPoints >= complexity * 10;
    }
    
    private void deductTechnologyPoints(int amount) {
        availableTechnologyPoints = Math.max(0, availableTechnologyPoints - amount);
    }
    
    private void awardTechnologyPoints(int amount) {
        availableTechnologyPoints = Math.min(totalTechnologyPoints, availableTechnologyPoints + amount);
    }
    
    private int calculateCompletionBonus(String projectId) {
        TechnologyProject project = technologyProjects.get(projectId);
        if (project == null) {
            return 0;
        }
        
        return project.getComplexity() * 25;
    }
    
    private void checkTechnologyBreakthroughs(String projectId) {
        TechnologyProject project = technologyProjects.get(projectId);
        if (project == null) {
            return;
        }
        
        double progress = project.getProgressPercentage();
        
        if (progress >= 0.25 && progress < 0.5) {
            createTechnologyBreakthrough(projectId, "25% Technology Progress");
        } else if (progress >= 0.5 && progress < 0.75) {
            createTechnologyBreakthrough(projectId, "50% Technology Progress");
        } else if (progress >= 0.75 && progress < 1.0) {
            createTechnologyBreakthrough(projectId, "75% Technology Progress");
        }
    }
    
    private void createTechnologyBreakthrough(String technologyId, String description) {
        TechnologyBreakthrough breakthrough = new TechnologyBreakthrough(technologyId, description, LocalDateTime.now());
        technologyBreakthroughs.add(breakthrough);
    }
    
    private void generateTechnologyCapabilities(String technologyId) {
        // Generate technology capabilities based on analysis
        TechnologyCapability capability = new TechnologyCapability(technologyId, "Generated Capability", 50);
        technologyCapabilities.put(technologyId, capability);
    }
    
    private void processTechnologyEffects(String projectId) {
        // Process technology completion effects
    }
    
    // Inner classes
    
    public static class TechnologyProject {
        private String projectId;
        private String name;
        private AdvancedAlienResearchTechnologySystem.TechnologyType type;
        private int complexity;
        private int progress;
        private int requiredProgress;
        private String teamId;
        private List<String> engineers;
        private boolean completed;
        private LocalDateTime startDate;
        private LocalDateTime lastUpdate;
        
        public TechnologyProject(String projectId, String name, 
                               AdvancedAlienResearchTechnologySystem.TechnologyType type,
                               int complexity, String teamId) {
            this.projectId = projectId;
            this.name = name;
            this.type = type;
            this.complexity = complexity;
            this.progress = 0;
            this.requiredProgress = complexity * 200;
            this.teamId = teamId;
            this.engineers = new ArrayList<>();
            this.completed = false;
            this.startDate = LocalDateTime.now();
            this.lastUpdate = LocalDateTime.now();
        }
        
        public void updateProgress(int increment, String engineer) {
            this.progress += increment;
            if (!engineers.contains(engineer)) {
                engineers.add(engineer);
            }
            this.lastUpdate = LocalDateTime.now();
            
            if (progress >= requiredProgress) {
                completed = true;
            }
        }
        
        public double getProgressPercentage() {
            return requiredProgress > 0 ? (double) progress / requiredProgress : 0.0;
        }
        
        // Getters
        public String getProjectId() { return projectId; }
        public String getName() { return name; }
        public AdvancedAlienResearchTechnologySystem.TechnologyType getType() { return type; }
        public int getComplexity() { return complexity; }
        public int getProgress() { return progress; }
        public int getRequiredProgress() { return requiredProgress; }
        public String getTeamId() { return teamId; }
        public List<String> getEngineers() { return new ArrayList<>(engineers); }
        public boolean isCompleted() { return completed; }
        public LocalDateTime getStartDate() { return startDate; }
        public LocalDateTime getLastUpdate() { return lastUpdate; }
    }
    
    public static class TechnologyAnalysis {
        private String technologyId;
        private AdvancedAlienResearchTechnologySystem.TechnologyCategory category;
        private AdvancedAlienResearchTechnologySystem.TechnologyLevel level;
        private boolean analyzed;
        private String analyst;
        private LocalDateTime analysisDate;
        private int analysisQuality;
        
        public TechnologyAnalysis(String technologyId, 
                                AdvancedAlienResearchTechnologySystem.TechnologyCategory category,
                                AdvancedAlienResearchTechnologySystem.TechnologyLevel level) {
            this.technologyId = technologyId;
            this.category = category;
            this.level = level;
            this.analyzed = false;
            this.analysisQuality = 0;
        }
        
        // Getters and setters
        public String getTechnologyId() { return technologyId; }
        public AdvancedAlienResearchTechnologySystem.TechnologyCategory getCategory() { return category; }
        public AdvancedAlienResearchTechnologySystem.TechnologyLevel getLevel() { return level; }
        public boolean isAnalyzed() { return analyzed; }
        public void setAnalyzed(boolean analyzed) { this.analyzed = analyzed; }
        public String getAnalyst() { return analyst; }
        public void setAnalyst(String analyst) { this.analyst = analyst; }
        public LocalDateTime getAnalysisDate() { return analysisDate; }
        public void setAnalysisDate(LocalDateTime analysisDate) { this.analysisDate = analysisDate; }
        public int getAnalysisQuality() { return analysisQuality; }
        public void setAnalysisQuality(int analysisQuality) { this.analysisQuality = analysisQuality; }
    }
    
    public static class TechnologyDeployment {
        private String technologyId;
        private String deploymentLocation;
        private String deploymentTeam;
        private LocalDateTime deploymentDate;
        private boolean active;
        
        public TechnologyDeployment(String technologyId, String deploymentLocation, String deploymentTeam) {
            this.technologyId = technologyId;
            this.deploymentLocation = deploymentLocation;
            this.deploymentTeam = deploymentTeam;
            this.deploymentDate = LocalDateTime.now();
            this.active = true;
        }
        
        // Getters
        public String getTechnologyId() { return technologyId; }
        public String getDeploymentLocation() { return deploymentLocation; }
        public String getDeploymentTeam() { return deploymentTeam; }
        public LocalDateTime getDeploymentDate() { return deploymentDate; }
        public boolean isActive() { return active; }
    }
    
    public static class TechnologyBreakthrough {
        private String technologyId;
        private String description;
        private LocalDateTime timestamp;
        
        public TechnologyBreakthrough(String technologyId, String description, LocalDateTime timestamp) {
            this.technologyId = technologyId;
            this.description = description;
            this.timestamp = timestamp;
        }
        
        // Getters
        public String getTechnologyId() { return technologyId; }
        public String getDescription() { return description; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
    
    public static class TechnologyReverseEngineering {
        private String technologyId;
        private String projectName;
        private String teamId;
        private int progress;
        private boolean completed;
        
        public TechnologyReverseEngineering(String technologyId, String projectName, String teamId) {
            this.technologyId = technologyId;
            this.projectName = projectName;
            this.teamId = teamId;
            this.progress = 0;
            this.completed = false;
        }
        
        // Getters
        public String getTechnologyId() { return technologyId; }
        public String getProjectName() { return projectName; }
        public String getTeamId() { return teamId; }
        public int getProgress() { return progress; }
        public boolean isCompleted() { return completed; }
    }
    
    public static class TechnologyCapability {
        private String technologyId;
        private String capabilityName;
        private int power;
        
        public TechnologyCapability(String technologyId, String capabilityName, int power) {
            this.technologyId = technologyId;
            this.capabilityName = capabilityName;
            this.power = power;
        }
        
        // Getters
        public String getTechnologyId() { return technologyId; }
        public String getCapabilityName() { return capabilityName; }
        public int getPower() { return power; }
    }
    
    public static class TechnologyStatistics {
        private int totalProjects;
        private int completedProjects;
        private int inProgressProjects;
        private int discoveredTechnologies;
        private int analyzedTechnologies;
        private int totalTechnologyPoints;
        private int availableTechnologyPoints;
        
        public TechnologyStatistics(int totalProjects, int completedProjects, int inProgressProjects,
                                 int discoveredTechnologies, int analyzedTechnologies,
                                 int totalTechnologyPoints, int availableTechnologyPoints) {
            this.totalProjects = totalProjects;
            this.completedProjects = completedProjects;
            this.inProgressProjects = inProgressProjects;
            this.discoveredTechnologies = discoveredTechnologies;
            this.analyzedTechnologies = analyzedTechnologies;
            this.totalTechnologyPoints = totalTechnologyPoints;
            this.availableTechnologyPoints = availableTechnologyPoints;
        }
        
        // Getters
        public int getTotalProjects() { return totalProjects; }
        public int getCompletedProjects() { return completedProjects; }
        public int getInProgressProjects() { return inProgressProjects; }
        public int getDiscoveredTechnologies() { return discoveredTechnologies; }
        public int getAnalyzedTechnologies() { return analyzedTechnologies; }
        public int getTotalTechnologyPoints() { return totalTechnologyPoints; }
        public int getAvailableTechnologyPoints() { return availableTechnologyPoints; }
        public double getCompletionRate() { 
            return totalProjects > 0 ? (double) completedProjects / totalProjects : 0.0; 
        }
        public double getAnalysisRate() { 
            return discoveredTechnologies > 0 ? (double) analyzedTechnologies / discoveredTechnologies : 0.0; 
        }
    }
}

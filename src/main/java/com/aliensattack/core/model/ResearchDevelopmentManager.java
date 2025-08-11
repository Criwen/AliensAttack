package com.aliensattack.core.model;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Research and Development Manager - Centralized management of research and development activities
 * Provides high-level interface for R&D operations and coordination
 */
public class ResearchDevelopmentManager {

    private AlienResearchManager alienResearchManager;
    private AlienTechnologyManager alienTechnologyManager;
    private Map<String, ResearchProject> researchProjects;
    private Map<String, DevelopmentProject> developmentProjects;
    private Map<String, ResearchTeam> researchTeams;
    private Map<String, ResearchFacility> researchFacilities;
    private Map<String, ResearchBreakthrough> researchBreakthroughs;
    private Map<String, TechnologyPatent> technologyPatents;
    private Map<String, ResearchCollaboration> researchCollaborations;
    private int totalRDPoints;
    private int availableRDPoints;
    private LocalDateTime lastRDUpdate;

    public ResearchDevelopmentManager() {
        this.alienResearchManager = new AlienResearchManager();
        this.alienTechnologyManager = new AlienTechnologyManager();
        this.researchProjects = new HashMap<>();
        this.developmentProjects = new HashMap<>();
        this.researchTeams = new HashMap<>();
        this.researchFacilities = new HashMap<>();
        this.researchBreakthroughs = new HashMap<>();
        this.technologyPatents = new HashMap<>();
        this.researchCollaborations = new HashMap<>();
        this.totalRDPoints = 3000;
        this.availableRDPoints = 3000;
        this.lastRDUpdate = LocalDateTime.now();
    }

    /**
     * Start research project
     */
    public boolean startResearchProject(String projectId, String name, String category, String type, int difficulty) {
        if (!hasAvailableRDPoints(difficulty * 10)) {
            return false;
        }

        // Create research project tracking
        ResearchProject project = new ResearchProject(projectId, name, category, type, difficulty);
        researchProjects.put(projectId, project);

        // Start alien research if applicable
        if (category.contains("ALIEN")) {
            alienResearchManager.startResearchProject(projectId, name,
                AdvancedAlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY,
                AdvancedAlienResearchTechnologySystem.ResearchType.ADVANCED_RESEARCH,
                difficulty, "DEFAULT_TEAM");
        }

        // Deduct RD points
        deductRDPoints(difficulty * 10);

        // Create research breakthrough
        createResearchBreakthrough(projectId, "Research project started: " + name);

        return true;
    }

    /**
     * Start development project
     */
    public boolean startDevelopmentProject(String projectId, String name, String category, String type, int complexity) {
        if (!hasAvailableRDPoints(complexity * 15)) {
            return false;
        }

        // Create development project tracking
        DevelopmentProject project = new DevelopmentProject(projectId, name, category, type, complexity);
        developmentProjects.put(projectId, project);

        // Start alien technology project if applicable
        if (category.contains("ALIEN")) {
            alienTechnologyManager.startTechnologyProject(projectId, name,
                AdvancedAlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM,
                complexity, "DEFAULT_ENGINEER_TEAM");
        }

        // Deduct RD points
        deductRDPoints(complexity * 15);

        // Create research breakthrough
        createResearchBreakthrough(projectId, "Development project started: " + name);

        return true;
    }

    /**
     * Update research progress
     */
    public boolean updateResearchProgress(String projectId, int progressIncrement, String researcher) {
        ResearchProject project = researchProjects.get(projectId);
        if (project == null) {
            return false;
        }

        // Update project progress
        project.updateProgress(progressIncrement, researcher);

        // Update alien research if applicable
        if (project.getCategory().contains("ALIEN")) {
            alienResearchManager.updateResearchProgress(projectId, progressIncrement, researcher);
        }

        // Check for breakthroughs
        checkResearchBreakthroughs(projectId);

        // Update last RD update
        lastRDUpdate = LocalDateTime.now();

        return true;
    }

    /**
     * Update development progress
     */
    public boolean updateDevelopmentProgress(String projectId, int progressIncrement, String engineer) {
        DevelopmentProject project = developmentProjects.get(projectId);
        if (project == null) {
            return false;
        }

        // Update project progress
        project.updateProgress(progressIncrement, engineer);

        // Update alien technology if applicable
        if (project.getCategory().contains("ALIEN")) {
            alienTechnologyManager.updateTechnologyProgress(projectId, progressIncrement, engineer);
        }

        // Check for breakthroughs
        checkDevelopmentBreakthroughs(projectId);

        // Update last RD update
        lastRDUpdate = LocalDateTime.now();

        return true;
    }

    /**
     * Complete research project
     */
    public boolean completeResearchProject(String projectId) {
        ResearchProject project = researchProjects.get(projectId);
        if (project == null) {
            return false;
        }

        // Complete project
        project.setCompleted(true);

        // Complete alien research if applicable
        if (project.getCategory().contains("ALIEN")) {
            alienResearchManager.completeResearchProject(projectId);
        }

        // Award RD points
        awardRDPoints(project.getDifficulty() * 20);

        // Create research breakthrough
        createResearchBreakthrough(projectId, "Research project completed: " + project.getName());

        return true;
    }

    /**
     * Complete development project
     */
    public boolean completeDevelopmentProject(String projectId) {
        DevelopmentProject project = developmentProjects.get(projectId);
        if (project == null) {
            return false;
        }

        // Complete project
        project.setCompleted(true);

        // Complete alien technology if applicable
        if (project.getCategory().contains("ALIEN")) {
            alienTechnologyManager.completeTechnologyProject(projectId);
        }

        // Award RD points
        awardRDPoints(project.getComplexity() * 25);

        // Create technology patent
        createTechnologyPatent(projectId, project.getName());

        return true;
    }

    /**
     * Create research team
     */
    public boolean createResearchTeam(String teamId, String teamName, List<String> researchers) {
        if (researchTeams.containsKey(teamId)) {
            return false;
        }

        // Create research team
        ResearchTeam team = new ResearchTeam(teamId, teamName, researchers);
        researchTeams.put(teamId, team);

        // Create alien research team if applicable
        alienResearchManager.createResearchTeam(teamId, teamName, researchers);

        return true;
    }

    /**
     * Create research facility
     */
    public boolean createResearchFacility(String facilityId, String name, String facilityType, int level, int capacity) {
        if (!hasAvailableRDPoints(level * 50)) {
            return false;
        }

        // Create research facility
        ResearchFacility facility = new ResearchFacility(facilityId, name, facilityType, level, capacity);
        researchFacilities.put(facilityId, facility);

        // Deduct RD points
        deductRDPoints(level * 50);

        return true;
    }

    /**
     * Create research collaboration
     */
    public boolean createResearchCollaboration(String collaborationId, String projectId, String teamId, String facilityId) {
        if (!hasAvailableRDPoints(25)) {
            return false;
        }

        // Create research collaboration
        ResearchCollaboration collaboration = new ResearchCollaboration(collaborationId, projectId, teamId, facilityId);
        researchCollaborations.put(collaborationId, collaboration);

        // Deduct RD points
        deductRDPoints(25);

        return true;
    }

    /**
     * Get research project
     */
    public ResearchProject getResearchProject(String projectId) {
        return researchProjects.get(projectId);
    }

    /**
     * Get development project
     */
    public DevelopmentProject getDevelopmentProject(String projectId) {
        return developmentProjects.get(projectId);
    }

    /**
     * Get research team
     */
    public ResearchTeam getResearchTeam(String teamId) {
        return researchTeams.get(teamId);
    }

    /**
     * Get research facility
     */
    public ResearchFacility getResearchFacility(String facilityId) {
        return researchFacilities.get(facilityId);
    }

    /**
     * Get RD statistics
     */
    public RDStatistics getRDStatistics() {
        int totalResearchProjects = researchProjects.size();
        int totalDevelopmentProjects = developmentProjects.size();
        int totalTeams = researchTeams.size();
        int totalFacilities = researchFacilities.size();
        int totalBreakthroughs = researchBreakthroughs.size();
        int totalPatents = technologyPatents.size();
        int totalCollaborations = researchCollaborations.size();
        int completedResearchProjects = (int) researchProjects.values().stream().filter(ResearchProject::isCompleted).count();
        int completedDevelopmentProjects = (int) developmentProjects.values().stream().filter(DevelopmentProject::isCompleted).count();

        return new RDStatistics(totalResearchProjects, totalDevelopmentProjects, totalTeams, totalFacilities,
                              totalBreakthroughs, totalPatents, totalCollaborations,
                              completedResearchProjects, completedDevelopmentProjects,
                              totalRDPoints, availableRDPoints);
    }
    
    /**
     * Get last R&D update time
     */
    public LocalDateTime getLastRDUpdate() {
        return lastRDUpdate;
    }
    
    /**
     * Check if R&D system needs update
     */
    public boolean needsRDUpdate() {
        return lastRDUpdate.isBefore(LocalDateTime.now().minusHours(2));
    }

    // Helper methods

    private boolean hasAvailableRDPoints(int amount) {
        return availableRDPoints >= amount;
    }

    private void deductRDPoints(int amount) {
        availableRDPoints = Math.max(0, availableRDPoints - amount);
    }

    private void awardRDPoints(int amount) {
        availableRDPoints = Math.min(totalRDPoints, availableRDPoints + amount);
    }

    private void checkResearchBreakthroughs(String projectId) {
        ResearchProject project = researchProjects.get(projectId);
        if (project == null) {
            return;
        }

        double progress = project.getProgressPercentage();

        if (progress >= 0.25 && progress < 0.5) {
            createResearchBreakthrough(projectId, "25% Research Progress");
        } else if (progress >= 0.5 && progress < 0.75) {
            createResearchBreakthrough(projectId, "50% Research Progress");
        } else if (progress >= 0.75 && progress < 1.0) {
            createResearchBreakthrough(projectId, "75% Research Progress");
        }
    }

    private void checkDevelopmentBreakthroughs(String projectId) {
        DevelopmentProject project = developmentProjects.get(projectId);
        if (project == null) {
            return;
        }

        double progress = project.getProgressPercentage();

        if (progress >= 0.25 && progress < 0.5) {
            createResearchBreakthrough(projectId, "25% Development Progress");
        } else if (progress >= 0.5 && progress < 0.75) {
            createResearchBreakthrough(projectId, "50% Development Progress");
        } else if (progress >= 0.75 && progress < 1.0) {
            createResearchBreakthrough(projectId, "75% Development Progress");
        }
    }

    private void createResearchBreakthrough(String projectId, String description) {
        ResearchBreakthrough breakthrough = new ResearchBreakthrough(projectId, description);
        researchBreakthroughs.put(breakthrough.getBreakthroughId(), breakthrough);
    }

    private void createTechnologyPatent(String projectId, String technologyName) {
        TechnologyPatent patent = new TechnologyPatent(projectId, technologyName);
        technologyPatents.put(patent.getPatentId(), patent);
    }

    // Inner classes

    public static class ResearchProject {
        private String projectId;
        private String name;
        private String category;
        private String type;
        private int difficulty;
        private int progress;
        private int requiredProgress;
        private List<String> researchers;
        private boolean isCompleted;
        private LocalDateTime startDate;
        private LocalDateTime lastUpdate;

        public ResearchProject(String projectId, String name, String category, String type, int difficulty) {
            this.projectId = projectId;
            this.name = name;
            this.category = category;
            this.type = type;
            this.difficulty = difficulty;
            this.progress = 0;
            this.requiredProgress = difficulty * 100;
            this.researchers = new ArrayList<>();
            this.isCompleted = false;
            this.startDate = LocalDateTime.now();
            this.lastUpdate = LocalDateTime.now();
        }

        public void updateProgress(int increment, String researcher) {
            this.progress += increment;
            if (!researchers.contains(researcher)) {
                researchers.add(researcher);
            }
            this.lastUpdate = LocalDateTime.now();

            if (progress >= requiredProgress) {
                isCompleted = true;
            }
        }

        public double getProgressPercentage() {
            return requiredProgress > 0 ? (double) progress / requiredProgress : 0.0;
        }

        // Getters and setters
        public String getProjectId() { return projectId; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getType() { return type; }
        public int getDifficulty() { return difficulty; }
        public int getProgress() { return progress; }
        public int getRequiredProgress() { return requiredProgress; }
        public List<String> getResearchers() { return new ArrayList<>(researchers); }
        public boolean isCompleted() { return isCompleted; }
        public void setCompleted(boolean completed) { isCompleted = completed; }
        public LocalDateTime getStartDate() { return startDate; }
        public LocalDateTime getLastUpdate() { return lastUpdate; }
    }

    public static class DevelopmentProject {
        private String projectId;
        private String name;
        private String category;
        private String type;
        private int complexity;
        private int progress;
        private int requiredProgress;
        private List<String> engineers;
        private boolean isCompleted;
        private LocalDateTime startDate;
        private LocalDateTime lastUpdate;

        public DevelopmentProject(String projectId, String name, String category, String type, int complexity) {
            this.projectId = projectId;
            this.name = name;
            this.category = category;
            this.type = type;
            this.complexity = complexity;
            this.progress = 0;
            this.requiredProgress = complexity * 150;
            this.engineers = new ArrayList<>();
            this.isCompleted = false;
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
                isCompleted = true;
            }
        }

        public double getProgressPercentage() {
            return requiredProgress > 0 ? (double) progress / requiredProgress : 0.0;
        }

        // Getters and setters
        public String getProjectId() { return projectId; }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getType() { return type; }
        public int getComplexity() { return complexity; }
        public int getProgress() { return progress; }
        public int getRequiredProgress() { return requiredProgress; }
        public List<String> getEngineers() { return new ArrayList<>(engineers); }
        public boolean isCompleted() { return isCompleted; }
        public void setCompleted(boolean completed) { isCompleted = completed; }
        public LocalDateTime getStartDate() { return startDate; }
        public LocalDateTime getLastUpdate() { return lastUpdate; }
    }

    public static class ResearchTeam {
        private String teamId;
        private String teamName;
        private List<String> researchers;
        private List<String> assignedProjects;
        private int totalContribution;

        public ResearchTeam(String teamId, String teamName, List<String> researchers) {
            this.teamId = teamId;
            this.teamName = teamName;
            this.researchers = new ArrayList<>(researchers);
            this.assignedProjects = new ArrayList<>();
            this.totalContribution = 0;
        }

        // Getters
        public String getTeamId() { return teamId; }
        public String getTeamName() { return teamName; }
        public List<String> getResearchers() { return new ArrayList<>(researchers); }
        public List<String> getAssignedProjects() { return new ArrayList<>(assignedProjects); }
        public int getTotalContribution() { return totalContribution; }
    }

    public static class ResearchFacility {
        private String facilityId;
        private String name;
        private String facilityType;
        private int level;
        private int capacity;
        private int currentUsage;
        private boolean isOperational;
        private LocalDateTime constructionDate;

        public ResearchFacility(String facilityId, String name, String facilityType, int level, int capacity) {
            this.facilityId = facilityId;
            this.name = name;
            this.facilityType = facilityType;
            this.level = level;
            this.capacity = capacity;
            this.currentUsage = 0;
            this.isOperational = true;
            this.constructionDate = LocalDateTime.now();
        }

        // Getters
        public String getFacilityId() { return facilityId; }
        public String getName() { return name; }
        public String getFacilityType() { return facilityType; }
        public int getLevel() { return level; }
        public int getCapacity() { return capacity; }
        public int getCurrentUsage() { return currentUsage; }
        public boolean isOperational() { return isOperational; }
        public LocalDateTime getConstructionDate() { return constructionDate; }
    }

    public static class ResearchBreakthrough {
        private String breakthroughId;
        private String projectId;
        private String description;
        private LocalDateTime breakthroughDate;

        public ResearchBreakthrough(String projectId, String description) {
            this.breakthroughId = UUID.randomUUID().toString();
            this.projectId = projectId;
            this.description = description;
            this.breakthroughDate = LocalDateTime.now();
        }

        // Getters
        public String getBreakthroughId() { return breakthroughId; }
        public String getProjectId() { return projectId; }
        public String getDescription() { return description; }
        public LocalDateTime getBreakthroughDate() { return breakthroughDate; }
    }

    public static class TechnologyPatent {
        private String patentId;
        private String projectId;
        private String technologyName;
        private LocalDateTime patentDate;

        public TechnologyPatent(String projectId, String technologyName) {
            this.patentId = UUID.randomUUID().toString();
            this.projectId = projectId;
            this.technologyName = technologyName;
            this.patentDate = LocalDateTime.now();
        }

        // Getters
        public String getPatentId() { return patentId; }
        public String getProjectId() { return projectId; }
        public String getTechnologyName() { return technologyName; }
        public LocalDateTime getPatentDate() { return patentDate; }
    }

    public static class ResearchCollaboration {
        private String collaborationId;
        private String projectId;
        private String teamId;
        private String facilityId;
        private LocalDateTime startDate;
        private boolean isActive;

        public ResearchCollaboration(String collaborationId, String projectId, String teamId, String facilityId) {
            this.collaborationId = collaborationId;
            this.projectId = projectId;
            this.teamId = teamId;
            this.facilityId = facilityId;
            this.startDate = LocalDateTime.now();
            this.isActive = true;
        }

        // Getters
        public String getCollaborationId() { return collaborationId; }
        public String getProjectId() { return projectId; }
        public String getTeamId() { return teamId; }
        public String getFacilityId() { return facilityId; }
        public LocalDateTime getStartDate() { return startDate; }
        public boolean isActive() { return isActive; }
    }

    public static class RDStatistics {
        private int totalResearchProjects;
        private int totalDevelopmentProjects;
        private int totalTeams;
        private int totalFacilities;
        private int totalBreakthroughs;
        private int totalPatents;
        private int totalCollaborations;
        private int completedResearchProjects;
        private int completedDevelopmentProjects;
        private int totalRDPoints;
        private int availableRDPoints;

        public RDStatistics(int totalResearchProjects, int totalDevelopmentProjects, int totalTeams, int totalFacilities,
                          int totalBreakthroughs, int totalPatents, int totalCollaborations,
                          int completedResearchProjects, int completedDevelopmentProjects,
                          int totalRDPoints, int availableRDPoints) {
            this.totalResearchProjects = totalResearchProjects;
            this.totalDevelopmentProjects = totalDevelopmentProjects;
            this.totalTeams = totalTeams;
            this.totalFacilities = totalFacilities;
            this.totalBreakthroughs = totalBreakthroughs;
            this.totalPatents = totalPatents;
            this.totalCollaborations = totalCollaborations;
            this.completedResearchProjects = completedResearchProjects;
            this.completedDevelopmentProjects = completedDevelopmentProjects;
            this.totalRDPoints = totalRDPoints;
            this.availableRDPoints = availableRDPoints;
        }

        // Getters
        public int getTotalResearchProjects() { return totalResearchProjects; }
        public int getTotalDevelopmentProjects() { return totalDevelopmentProjects; }
        public int getTotalTeams() { return totalTeams; }
        public int getTotalFacilities() { return totalFacilities; }
        public int getTotalBreakthroughs() { return totalBreakthroughs; }
        public int getTotalPatents() { return totalPatents; }
        public int getTotalCollaborations() { return totalCollaborations; }
        public int getCompletedResearchProjects() { return completedResearchProjects; }
        public int getCompletedDevelopmentProjects() { return completedDevelopmentProjects; }
        public int getTotalRDPoints() { return totalRDPoints; }
        public int getAvailableRDPoints() { return availableRDPoints; }
        public double getRDPointsUsage() {
            return totalRDPoints > 0 ? (double) (totalRDPoints - availableRDPoints) / totalRDPoints : 0.0;
        }
        public double getResearchCompletionRate() {
            return totalResearchProjects > 0 ? (double) completedResearchProjects / totalResearchProjects : 0.0;
        }
        public double getDevelopmentCompletionRate() {
            return totalDevelopmentProjects > 0 ? (double) completedDevelopmentProjects / totalDevelopmentProjects : 0.0;
        }
    }
}

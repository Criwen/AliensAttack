package com.aliensattack.core.model;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Alien Research Manager - Centralized management of alien research activities
 * Provides high-level interface for research operations and coordination
 */
public class AlienResearchManager {
    
    private AdvancedAlienResearchTechnologySystem researchSystem;
    private Map<String, ResearchTeam> researchTeams;
    private Map<String, ResearchPriority> researchPriorities;
    private Map<String, ResearchBudget> researchBudgets;
    private List<ResearchMilestone> researchMilestones;
    private Map<String, ResearchCollaboration> researchCollaborations;
    private int totalResearchPoints;
    private int availableResearchPoints;
    private LocalDateTime lastResearchUpdate;
    
    public AlienResearchManager() {
        this.researchSystem = new AdvancedAlienResearchTechnologySystem();
        this.researchTeams = new HashMap<>();
        this.researchPriorities = new HashMap<>();
        this.researchBudgets = new HashMap<>();
        this.researchMilestones = new ArrayList<>();
        this.researchCollaborations = new HashMap<>();
        this.totalResearchPoints = 1000;
        this.availableResearchPoints = 1000;
        this.lastResearchUpdate = LocalDateTime.now();
    }
    
    /**
     * Start a new research project
     */
    public boolean startResearchProject(String researchId, String name, 
                                      AdvancedAlienResearchTechnologySystem.ResearchCategory category,
                                      AdvancedAlienResearchTechnologySystem.ResearchType type, 
                                      int difficulty, String teamId) {
        
        if (!hasAvailableResearchPoints(difficulty)) {
            return false;
        }
        
        boolean success = researchSystem.startResearch(researchId, name, category, type, difficulty);
        
        if (success) {
            // Assign research team
            assignResearchTeam(researchId, teamId);
            
            // Set research priority
            setResearchPriority(researchId, ResearchPriority.NORMAL);
            
            // Allocate budget
            allocateResearchBudget(researchId, calculateBudget(difficulty, type));
            
            // Deduct research points
            deductResearchPoints(difficulty);
            
            // Create milestone
            createResearchMilestone(researchId, "Research Started");
        }
        
        return success;
    }
    
    /**
     * Update research progress
     */
    public boolean updateResearchProgress(String researchId, int progressIncrement, String researcher) {
        boolean success = researchSystem.updateResearchProgress(researchId, progressIncrement);
        
        if (success) {
            // Update team contribution
            updateTeamContribution(researchId, researcher, progressIncrement);
            
            // Check for milestones
            checkResearchMilestones(researchId);
            
            // Update last research update
            lastResearchUpdate = LocalDateTime.now();
        }
        
        return success;
    }
    
    /**
     * Complete research project
     */
    public boolean completeResearchProject(String researchId) {
        boolean success = researchSystem.completeResearch(researchId);
        
        if (success) {
            // Create completion milestone
            createResearchMilestone(researchId, "Research Completed");
            
            // Award research points
            awardResearchPoints(calculateCompletionBonus(researchId));
            
            // Process research effects
            processResearchEffects(researchId);
        }
        
        return success;
    }
    
    /**
     * Create research team
     */
    public boolean createResearchTeam(String teamId, String teamName, List<String> researchers) {
        if (researchTeams.containsKey(teamId)) {
            return false;
        }
        
        ResearchTeam team = new ResearchTeam(teamId, teamName, researchers);
        researchTeams.put(teamId, team);
        
        return true;
    }
    
    /**
     * Assign research team to project
     */
    public boolean assignResearchTeam(String researchId, String teamId) {
        ResearchTeam team = researchTeams.get(teamId);
        if (team == null) {
            return false;
        }
        
        team.addAssignedProject(researchId);
        return true;
    }
    
    /**
     * Set research priority
     */
    public void setResearchPriority(String researchId, ResearchPriority priority) {
        researchPriorities.put(researchId, priority);
    }
    
    /**
     * Allocate research budget
     */
    public void allocateResearchBudget(String researchId, int budget) {
        researchBudgets.put(researchId, new ResearchBudget(researchId, budget));
    }
    
    /**
     * Create research milestone
     */
    public void createResearchMilestone(String researchId, String milestoneDescription) {
        ResearchMilestone milestone = new ResearchMilestone(researchId, milestoneDescription, LocalDateTime.now());
        researchMilestones.add(milestone);
    }
    
    /**
     * Get research progress
     */
    public double getResearchProgress(String researchId) {
        AdvancedAlienResearchTechnologySystem.ResearchProgress progress = researchSystem.getResearchProgress(researchId);
        return progress != null ? progress.getProgressPercentage() : 0.0;
    }
    
    /**
     * Get available research projects
     */
    public List<AdvancedAlienResearchTechnologySystem.AlienResearch> getAvailableResearchProjects() {
        List<AdvancedAlienResearchTechnologySystem.AlienResearch> available = new ArrayList<>();
        
        // Since we don't have direct access to all research, we'll need to track them differently
        // For now, return empty list - this would need to be implemented with proper tracking
        return available;
    }
    
    /**
     * Get completed research projects
     */
    public List<AdvancedAlienResearchTechnologySystem.AlienResearch> getCompletedResearchProjects() {
        List<AdvancedAlienResearchTechnologySystem.AlienResearch> completed = new ArrayList<>();
        
        // Since we don't have direct access to all research, we'll need to track them differently
        // For now, return empty list - this would need to be implemented with proper tracking
        return completed;
    }
    
    /**
     * Get research statistics
     */
    public ResearchStatistics getResearchStatistics() {
        // Since we don't have direct access to all research, we'll use default values
        // This would need to be implemented with proper tracking
        int totalProjects = 0;
        int completedProjects = 0;
        int inProgressProjects = 0;
        
        return new ResearchStatistics(totalProjects, completedProjects, inProgressProjects, 
                                   totalResearchPoints, availableResearchPoints);
    }
    
    /**
     * Get last research update time
     */
    public LocalDateTime getLastResearchUpdate() {
        return lastResearchUpdate;
    }
    
    /**
     * Check if research system needs update
     */
    public boolean needsResearchUpdate() {
        return lastResearchUpdate.isBefore(LocalDateTime.now().minusHours(1));
    }
    
    /**
     * Create research collaboration
     */
    public boolean createResearchCollaboration(String collaborationId, String researchId, List<String> collaboratingTeams) {
        if (researchCollaborations.containsKey(collaborationId)) {
            return false;
        }
        
        ResearchCollaboration collaboration = new ResearchCollaboration(collaborationId, researchId, collaboratingTeams);
        researchCollaborations.put(collaborationId, collaboration);
        return true;
    }
    
    /**
     * Get research collaboration
     */
    public ResearchCollaboration getResearchCollaboration(String collaborationId) {
        return researchCollaborations.get(collaborationId);
    }
    
    /**
     * Get active collaborations count
     */
    public int getActiveCollaborationsCount() {
        return (int) researchCollaborations.values().stream()
            .filter(ResearchCollaboration::isActive)
            .count();
    }
    
    // Helper methods
    
    private boolean hasAvailableResearchPoints(int difficulty) {
        return availableResearchPoints >= difficulty * 10;
    }
    
    private void deductResearchPoints(int amount) {
        availableResearchPoints = Math.max(0, availableResearchPoints - amount);
    }
    
    private void awardResearchPoints(int amount) {
        availableResearchPoints = Math.min(totalResearchPoints, availableResearchPoints + amount);
    }
    
    private int calculateBudget(int difficulty, AdvancedAlienResearchTechnologySystem.ResearchType type) {
        int baseBudget = difficulty * 100;
        
        switch (type) {
            case BASIC_RESEARCH:
                return baseBudget;
            case ADVANCED_RESEARCH:
                return baseBudget * 2;
            case EXPERIMENTAL_RESEARCH:
                return baseBudget * 3;
            case REVERSE_ENGINEERING:
                return baseBudget * 2;
            case ALIEN_AUTOPSY:
                return baseBudget * 1;
            case TECHNOLOGY_ANALYSIS:
                return baseBudget * 2;
            default:
                return baseBudget;
        }
    }
    
    private int calculateCompletionBonus(String researchId) {
        AdvancedAlienResearchTechnologySystem.AlienResearch research = researchSystem.getResearch(researchId);
        if (research == null) {
            return 0;
        }
        
        return research.getDifficulty() * 20;
    }
    
    private void updateTeamContribution(String researchId, String researcher, int contribution) {
        // Update team contribution tracking
    }
    
    private void checkResearchMilestones(String researchId) {
        double progress = getResearchProgress(researchId);
        
        if (progress >= 0.25 && progress < 0.5) {
            createResearchMilestone(researchId, "25% Complete");
        } else if (progress >= 0.5 && progress < 0.75) {
            createResearchMilestone(researchId, "50% Complete");
        } else if (progress >= 0.75 && progress < 1.0) {
            createResearchMilestone(researchId, "75% Complete");
        }
    }
    
    private void processResearchEffects(String researchId) {
        // Process research completion effects
    }
    
    // Inner classes
    
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
        
        public void addAssignedProject(String projectId) {
            if (!assignedProjects.contains(projectId)) {
                assignedProjects.add(projectId);
            }
        }
        
        // Getters
        public String getTeamId() { return teamId; }
        public String getTeamName() { return teamName; }
        public List<String> getResearchers() { return new ArrayList<>(researchers); }
        public List<String> getAssignedProjects() { return new ArrayList<>(assignedProjects); }
        public int getTotalContribution() { return totalContribution; }
    }
    
    public enum ResearchPriority {
        LOW, NORMAL, HIGH, CRITICAL
    }
    
    public static class ResearchBudget {
        private String researchId;
        private int allocatedBudget;
        private int spentBudget;
        
        public ResearchBudget(String researchId, int allocatedBudget) {
            this.researchId = researchId;
            this.allocatedBudget = allocatedBudget;
            this.spentBudget = 0;
        }
        
        // Getters
        public String getResearchId() { return researchId; }
        public int getAllocatedBudget() { return allocatedBudget; }
        public int getSpentBudget() { return spentBudget; }
        public int getRemainingBudget() { return allocatedBudget - spentBudget; }
    }
    
    public static class ResearchMilestone {
        private String researchId;
        private String description;
        private LocalDateTime timestamp;
        
        public ResearchMilestone(String researchId, String description, LocalDateTime timestamp) {
            this.researchId = researchId;
            this.description = description;
            this.timestamp = timestamp;
        }
        
        // Getters
        public String getResearchId() { return researchId; }
        public String getDescription() { return description; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
    
    public static class ResearchCollaboration {
        private String collaborationId;
        private String researchId;
        private List<String> collaboratingTeams;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private boolean isActive;
        
        public ResearchCollaboration(String collaborationId, String researchId, List<String> collaboratingTeams) {
            this.collaborationId = collaborationId;
            this.researchId = researchId;
            this.collaboratingTeams = new ArrayList<>(collaboratingTeams);
            this.startDate = LocalDateTime.now();
            this.isActive = true;
        }
        
        // Getters
        public String getCollaborationId() { return collaborationId; }
        public String getResearchId() { return researchId; }
        public List<String> getCollaboratingTeams() { return new ArrayList<>(collaboratingTeams); }
        public LocalDateTime getStartDate() { return startDate; }
        public LocalDateTime getEndDate() { return endDate; }
        public boolean isActive() { return isActive; }
        
        public void endCollaboration() {
            this.endDate = LocalDateTime.now();
            this.isActive = false;
        }
    }
    
    public static class ResearchStatistics {
        private int totalProjects;
        private int completedProjects;
        private int inProgressProjects;
        private int totalResearchPoints;
        private int availableResearchPoints;
        
        public ResearchStatistics(int totalProjects, int completedProjects, int inProgressProjects,
                                int totalResearchPoints, int availableResearchPoints) {
            this.totalProjects = totalProjects;
            this.completedProjects = completedProjects;
            this.inProgressProjects = inProgressProjects;
            this.totalResearchPoints = totalResearchPoints;
            this.availableResearchPoints = availableResearchPoints;
        }
        
        // Getters
        public int getTotalProjects() { return totalProjects; }
        public int getCompletedProjects() { return completedProjects; }
        public int getInProgressProjects() { return inProgressProjects; }
        public int getTotalResearchPoints() { return totalResearchPoints; }
        public int getAvailableResearchPoints() { return availableResearchPoints; }
        public double getCompletionRate() { 
            return totalProjects > 0 ? (double) completedProjects / totalProjects : 0.0; 
        }
    }
}

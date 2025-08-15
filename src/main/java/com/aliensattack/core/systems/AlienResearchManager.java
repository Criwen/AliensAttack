package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.systems.AlienResearchTechnologySystem.ResearchProject;
import com.aliensattack.core.systems.AlienResearchTechnologySystem.AlienTechnology;
import com.aliensattack.core.systems.AlienResearchTechnologySystem.ResearchStatus;

import java.util.*;

/**
 * Advanced Alien Research Manager for XCOM 2 Strategic Layer
 * Manages alien research projects, autopsy results, and strategic intelligence
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienResearchManager {
    
    private AlienResearchTechnologySystem researchSystem;
    private Map<String, ResearchProject> researchProjects;
    private Map<String, AlienTechnology> discoveredTechnologies;
    private List<String> researchedAlienTypes;
    private ResearchStatus overallResearchStatus;
    private int researchPoints;
    private int autopsyPoints;
    private Random random;
    
    /**
     * Initialize the alien research manager
     */
    public void initialize() {
        if (researchSystem == null) {
            researchSystem = new AlienResearchTechnologySystem();
        }
        if (researchProjects == null) {
            researchProjects = new HashMap<>();
        }
        if (discoveredTechnologies == null) {
            discoveredTechnologies = new HashMap<>();
        }
        if (researchedAlienTypes == null) {
            researchedAlienTypes = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        overallResearchStatus = ResearchStatus.NOT_STARTED;
        researchPoints = 100;
        autopsyPoints = 50;
        
        initializeResearchProjects();
        initializeDiscoveredTechnologies();
    }
    
    /**
     * Initialize research projects
     */
    private void initializeResearchProjects() {
        // Create initial research projects
        ResearchProject sectoidResearch = ResearchProject.builder()
                .projectId("SECTOID_RESEARCH")
                .projectName("Sectoid Autopsy Research")
                .projectType("AUTOPSY")
                .researchArea("ALIEN_BIOLOGY")
                .targetAlienType("SECTOID")
                .researchCost(50)
                .researchTime(3)
                .currentProgress(0)
                .maxProgress(100)
                .successRate(0.9)
                .failureRate(0.1)
                .criticalSuccessRate(0.05)
                .criticalFailureRate(0.02)
                .researchBonus("Improved psionic resistance")
                .researchPenalty("Reduced research efficiency")
                .isActive(false)
                .isCompleted(false)
                .isFailed(false)
                .assignedScientists(0)
                .maxScientists(2)
                .researchPriority(1)
                .build();
        
        researchProjects.put(sectoidResearch.getProjectId(), sectoidResearch);
        
        ResearchProject mutonResearch = ResearchProject.builder()
                .projectId("MUTON_RESEARCH")
                .projectName("Muton Autopsy Research")
                .projectType("AUTOPSY")
                .researchArea("ALIEN_BIOLOGY")
                .targetAlienType("MUTON")
                .researchCost(75)
                .researchTime(4)
                .currentProgress(0)
                .maxProgress(120)
                .successRate(0.85)
                .failureRate(0.15)
                .criticalSuccessRate(0.08)
                .criticalFailureRate(0.03)
                .researchBonus("Improved melee combat")
                .researchPenalty("Increased research time")
                .isActive(false)
                .isCompleted(false)
                .isFailed(false)
                .assignedScientists(0)
                .maxScientists(3)
                .researchPriority(2)
                .build();
        
        researchProjects.put(mutonResearch.getProjectId(), mutonResearch);
    }
    
    /**
     * Initialize discovered technologies
     */
    private void initializeDiscoveredTechnologies() {
        // Create basic discovered technologies
        AlienTechnology basicAlienBiology = AlienTechnology.builder()
                .technologyId("BASIC_ALIEN_BIOLOGY")
                .technologyName("Basic Alien Biology")
                .technologyType("BIOLOGY_TECH")
                .technologyLevel("BASIC")
                .technologyCost(25)
                .technologyBonus("Basic understanding of alien biology")
                .technologyPenalty("Limited research scope")
                .isDiscovered(true)
                .isImplemented(false)
                .discoveryDate(new Date().toString())
                .implementationDate("")
                .technologyDescription("Basic understanding of alien biological systems")
                .build();
        
        discoveredTechnologies.put(basicAlienBiology.getTechnologyId(), basicAlienBiology);
    }
    
    /**
     * Start research project
     */
    public boolean startResearchProject(String projectId) {
        ResearchProject project = researchProjects.get(projectId);
        if (project != null && !project.isActive() && researchPoints >= project.getResearchCost()) {
            project.setActive(true);
            researchPoints -= project.getResearchCost();
            return true;
        }
        return false;
    }
    
    /**
     * Update research progress
     */
    public void updateResearchProgress() {
        for (ResearchProject project : researchProjects.values()) {
            if (project.isActive() && !project.isCompleted()) {
                int progress = project.getCurrentProgress() + (project.getAssignedScientists() * 15);
                project.setCurrentProgress(Math.min(progress, project.getMaxProgress()));
                
                if (project.getCurrentProgress() >= project.getMaxProgress()) {
                    completeResearchProject(project);
                }
            }
        }
    }
    
    /**
     * Complete research project
     */
    private void completeResearchProject(ResearchProject project) {
        project.setCompleted(true);
        project.setActive(false);
        
        // Add to researched alien types
        if (!researchedAlienTypes.contains(project.getTargetAlienType())) {
            researchedAlienTypes.add(project.getTargetAlienType());
        }
        
        // Unlock new technologies
        unlockTechnologies(project);
        
        // Add research points
        researchPoints += 30;
        autopsyPoints += 15;
        
        // Update overall status
        updateOverallResearchStatus();
    }
    
    /**
     * Unlock technologies based on completed research
     */
    private void unlockTechnologies(ResearchProject project) {
        // Create new technology based on research
        AlienTechnology newTech = AlienTechnology.builder()
                .technologyId(project.getProjectId() + "_TECH")
                .technologyName(project.getProjectName() + " Technology")
                .technologyType("RESEARCH_TECH")
                .technologyLevel("BASIC")
                .technologyCost(project.getResearchCost() / 2)
                .technologyBonus(project.getResearchBonus())
                .technologyPenalty(project.getResearchPenalty())
                .isDiscovered(true)
                .isImplemented(false)
                .discoveryDate(new Date().toString())
                .implementationDate("")
                .technologyDescription("Technology discovered through " + project.getProjectName())
                .build();
        
        discoveredTechnologies.put(newTech.getTechnologyId(), newTech);
    }
    
    /**
     * Update overall research status
     */
    private void updateOverallResearchStatus() {
        int completedProjects = (int) researchProjects.values().stream()
                .filter(ResearchProject::isCompleted)
                .count();
        
        int totalProjects = researchProjects.size();
        
        if (completedProjects == 0) {
            overallResearchStatus = ResearchStatus.NOT_STARTED;
        } else if (completedProjects < totalProjects) {
            overallResearchStatus = ResearchStatus.IN_PROGRESS;
        } else {
            overallResearchStatus = ResearchStatus.COMPLETED;
        }
    }
    
    /**
     * Get research status
     */
    public String getResearchStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Alien Research Status:\n");
        status.append("- Overall Status: ").append(overallResearchStatus).append("\n");
        status.append("- Research Points: ").append(researchPoints).append("\n");
        status.append("- Autopsy Points: ").append(autopsyPoints).append("\n");
        status.append("- Active Projects: ").append(researchProjects.values().stream().filter(ResearchProject::isActive).count()).append("\n");
        status.append("- Completed Projects: ").append(researchProjects.values().stream().filter(ResearchProject::isCompleted).count()).append("\n");
        status.append("- Researched Alien Types: ").append(researchedAlienTypes.size()).append("\n");
        status.append("- Discovered Technologies: ").append(discoveredTechnologies.size()).append("\n");
        
        return status.toString();
    }
}

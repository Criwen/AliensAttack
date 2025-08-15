package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.model.AlienResearchTechnologySystem.ResearchProject;
import com.aliensattack.core.model.AlienResearchTechnologySystem.TechnologyNode;
import com.aliensattack.core.model.AlienResearchTechnologySystem.AlienTechnology;
import com.aliensattack.core.model.AlienResearchTechnologySystem.TechnologyLevel;
import com.aliensattack.core.model.AlienResearchTechnologySystem.ResearchStatus;

import java.util.*;

/**
 * Advanced Alien Technology Manager for XCOM 2 Strategic Layer
 * Manages alien research, technology development, and strategic decisions
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlienTechnologyManager {
    
    private AlienResearchTechnologySystem researchSystem;
    private Map<String, ResearchProject> activeProjects;
    private Map<String, TechnologyNode> technologyTree;
    private Map<String, AlienTechnology> discoveredTechnologies;
    private List<AlienType> researchedAlienTypes;
    private TechnologyLevel currentTechnologyLevel;
    private ResearchStatus overallResearchStatus;
    private int researchPoints;
    private int technologyPoints;
    private Random random;
    
    /**
     * Initialize the alien technology manager
     */
    public void initialize() {
        if (researchSystem == null) {
            researchSystem = new AlienResearchTechnologySystem();
        }
        if (activeProjects == null) {
            activeProjects = new HashMap<>();
        }
        if (technologyTree == null) {
            technologyTree = new HashMap<>();
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
        
        currentTechnologyLevel = TechnologyLevel.BASIC;
        overallResearchStatus = ResearchStatus.IN_PROGRESS;
        researchPoints = 100;
        technologyPoints = 50;
        
        initializeTechnologyTree();
        initializeResearchProjects();
        initializeDiscoveredTechnologies();
    }
    
    /**
     * Initialize technology tree
     */
    private void initializeTechnologyTree() {
        // Create basic technology nodes
        TechnologyNode basicWeapons = TechnologyNode.builder()
                .nodeId("BASIC_WEAPONS")
                .nodeName("Basic Alien Weapons")
                .nodeType("WEAPON_TECH")
                .technologyLevel("BASIC")
                .researchCost(50)
                .technologyCost(25)
                .prerequisites(new ArrayList<>())
                .unlockedTechnologies(Arrays.asList("PLASMA_RIFLE", "ALIEN_GRENADE"))
                .researchProgress(0)
                .maxResearchProgress(100)
                .isResearched(false)
                .isUnlocked(true)
                .researchTime(3)
                .technologyBonus("Basic alien weapon technology")
                .build();
        
        technologyTree.put(basicWeapons.getNodeId(), basicWeapons);
        
        TechnologyNode advancedWeapons = TechnologyNode.builder()
                .nodeId("ADVANCED_WEAPONS")
                .nodeName("Advanced Alien Weapons")
                .nodeType("WEAPON_TECH")
                .technologyLevel("ADVANCED")
                .researchCost(100)
                .technologyCost(50)
                .prerequisites(Arrays.asList("BASIC_WEAPONS"))
                .unlockedTechnologies(Arrays.asList("HEAVY_PLASMA", "PLASMA_CANNON"))
                .researchProgress(0)
                .maxResearchProgress(150)
                .isResearched(false)
                .isUnlocked(false)
                .researchTime(5)
                .technologyBonus("Advanced alien weapon technology")
                .build();
        
        technologyTree.put(advancedWeapons.getNodeId(), advancedWeapons);
    }
    
    /**
     * Initialize research projects
     */
    private void initializeResearchProjects() {
        // Create initial research projects
        ResearchProject plasmaResearch = ResearchProject.builder()
                .projectId("PLASMA_RESEARCH")
                .projectName("Plasma Technology Research")
                .projectType("WEAPON_TECH")
                .researchArea("ALIEN_WEAPONS")
                .targetAlienType("SECTOID")
                .researchCost(75)
                .researchTime(4)
                .currentProgress(0)
                .maxProgress(100)
                .successRate(0.8)
                .failureRate(0.2)
                .criticalSuccessRate(0.1)
                .criticalFailureRate(0.05)
                .researchBonus("Improved plasma weapon damage")
                .researchPenalty("Reduced research efficiency")
                .isActive(false)
                .isCompleted(false)
                .isFailed(false)
                .assignedScientists(0)
                .maxScientists(3)
                .researchPriority(1)
                .build();
        
        activeProjects.put(plasmaResearch.getProjectId(), plasmaResearch);
    }
    
    /**
     * Initialize discovered technologies
     */
    private void initializeDiscoveredTechnologies() {
        // Create basic discovered technologies
        AlienTechnology basicPlasma = AlienTechnology.builder()
                .technologyId("BASIC_PLASMA")
                .technologyName("Basic Plasma Technology")
                .technologyType("WEAPON_TECH")
                .technologyLevel("BASIC")
                .technologyCost(30)
                .technologyBonus("Basic plasma weapon damage")
                .technologyPenalty("Increased weapon heat")
                .isDiscovered(true)
                .isImplemented(false)
                .discoveryDate(new Date().toString())
                .implementationDate("")
                .technologyDescription("Basic understanding of alien plasma technology")
                .build();
        
        discoveredTechnologies.put(basicPlasma.getTechnologyId(), basicPlasma);
    }
    
    /**
     * Start research project
     */
    public boolean startResearchProject(String projectId) {
        ResearchProject project = activeProjects.get(projectId);
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
        for (ResearchProject project : activeProjects.values()) {
            if (project.isActive() && !project.isCompleted()) {
                int progress = project.getCurrentProgress() + (project.getAssignedScientists() * 10);
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
        
        // Unlock new technologies
        unlockTechnologies(project);
        
        // Add research points
        researchPoints += 25;
        
        // Update overall status
        updateOverallResearchStatus();
    }
    
    /**
     * Unlock technologies based on completed research
     */
    private void unlockTechnologies(ResearchProject project) {
        // Find and unlock related technology nodes
        for (TechnologyNode node : technologyTree.values()) {
            if (node.getPrerequisites().contains(project.getProjectId())) {
                node.setUnlocked(true);
            }
        }
    }
    
    /**
     * Update overall research status
     */
    private void updateOverallResearchStatus() {
        int completedProjects = (int) activeProjects.values().stream()
                .filter(ResearchProject::isCompleted)
                .count();
        
        int totalProjects = activeProjects.size();
        
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
        status.append("Alien Technology Research Status:\n");
        status.append("- Overall Status: ").append(overallResearchStatus).append("\n");
        status.append("- Technology Level: ").append(currentTechnologyLevel).append("\n");
        status.append("- Research Points: ").append(researchPoints).append("\n");
        status.append("- Technology Points: ").append(technologyPoints).append("\n");
        status.append("- Active Projects: ").append(activeProjects.values().stream().filter(ResearchProject::isActive).count()).append("\n");
        status.append("- Completed Projects: ").append(activeProjects.values().stream().filter(ResearchProject::isCompleted).count()).append("\n");
        status.append("- Discovered Technologies: ").append(discoveredTechnologies.size()).append("\n");
        
        return status.toString();
    }
}

package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.model.AlienResearchTechnologySystem.ResearchStatus;
import com.aliensattack.core.model.AlienResearchTechnologySystem.DevelopmentPhase;
import com.aliensattack.core.model.AlienResearchTechnologySystem.ResearchProject;
import com.aliensattack.core.model.AlienResearchTechnologySystem.DevelopmentProject;

import java.util.*;

/**
 * Research Development Manager for XCOM 2 Strategic Layer
 * Manages research projects and development phases
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResearchDevelopmentManager {
    
    private Map<String, ResearchStatus> researchStatuses;
    private Map<String, DevelopmentPhase> developmentPhases;
    private List<ResearchProject> researchProjects;
    private List<DevelopmentProject> developmentProjects;
    private int researchBonus;
    private int developmentPoints;
    private Random random;
    
    /**
     * Initialize the research development manager
     */
    public void initialize() {
        if (researchStatuses == null) {
            researchStatuses = new HashMap<>();
        }
        if (developmentPhases == null) {
            developmentPhases = new HashMap<>();
        }
        if (researchProjects == null) {
            researchProjects = new ArrayList<>();
        }
        if (developmentProjects == null) {
            developmentProjects = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        researchBonus = 0;
        developmentPoints = 100;
        
        initializeResearchStatuses();
        initializeDevelopmentPhases();
        initializeSampleProjects();
    }
    
    /**
     * Initialize research statuses
     */
    private void initializeResearchStatuses() {
        // Add research statuses
        researchStatuses.put("PLANNED", ResearchStatus.PLANNED);
        researchStatuses.put("IN_PROGRESS", ResearchStatus.IN_PROGRESS);
        researchStatuses.put("COMPLETED", ResearchStatus.COMPLETED);
        researchStatuses.put("FAILED", ResearchStatus.FAILED);
    }
    
    /**
     * Initialize development phases
     */
    private void initializeDevelopmentPhases() {
        // Add development phases
        developmentPhases.put("CONCEPT", DevelopmentPhase.CONCEPT);
        developmentPhases.put("DESIGN", DevelopmentPhase.DESIGN);
        developmentPhases.put("PROTOTYPE", DevelopmentPhase.PROTOTYPE);
        developmentPhases.put("TESTING", DevelopmentPhase.TESTING);
        developmentPhases.put("PRODUCTION", DevelopmentPhase.PRODUCTION);
    }
    
    /**
     * Initialize sample projects
     */
    private void initializeSampleProjects() {
        // Create sample research project
        ResearchProject weaponResearch = ResearchProject.builder()
            .projectId("WEAPON_RESEARCH_001")
            .projectName("Advanced Weapon Technology")
            .projectType("WEAPON_TECHNOLOGY")
            .researchArea("WEAPON_TECHNOLOGY")
            .targetAlienType("")
            .researchCost(1000)
            .researchTime(30)
            .currentProgress(0)
            .maxProgress(100)
            .successRate(0.8)
            .failureRate(0.2)
            .criticalSuccessRate(0.1)
            .criticalFailureRate(0.05)
            .researchBonus("")
            .researchPenalty("")
            .isActive(false)
            .isCompleted(false)
            .isFailed(false)
            .assignedScientists(0)
            .maxScientists(5)
            .researchPriority(1)
            .build();
        
        // Create sample development project
        DevelopmentProject weaponDevelopment = DevelopmentProject.builder()
            .projectId("WEAPON_DEV_001")
            .name("Advanced Weapon System")
            .type(AlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM)
            .phase(DevelopmentPhase.CONCEPT)
            .progress(0)
            .status(ResearchStatus.PLANNED)
            .build();
        
        researchProjects.add(weaponResearch);
        developmentProjects.add(weaponDevelopment);
    }
    
    /**
     * Get research development manager status
     */
    public String getResearchDevelopmentManagerStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Research Development Manager Status:\n");
        status.append("- Research Bonus: ").append(researchBonus).append("\n");
        status.append("- Development Points: ").append(developmentPoints).append("\n");
        status.append("- Research Projects: ").append(researchProjects.size()).append("\n");
        status.append("- Development Projects: ").append(developmentProjects.size()).append("\n");
        status.append("- Available Research Statuses: ").append(researchStatuses.size()).append("\n");
        status.append("- Available Development Phases: ").append(developmentPhases.size()).append("\n");
        
        return status.toString();
    }
}

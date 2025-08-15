package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.model.AlienResearchTechnologySystem.ResearchProject;
import com.aliensattack.core.model.AlienResearchTechnologySystem.AlienTechnology;

import java.util.*;

/**
 * Factory for creating alien research components
 */
public class AlienResearchFactory {
    
    /**
     * Create a research project
     */
    public static ResearchProject createResearchProject(String projectId, String projectName, 
                                                      String projectType, String researchArea, 
                                                      String targetAlienType, int researchCost, 
                                                      int researchTime) {
        return ResearchProject.builder()
                .projectId(projectId)
                .projectName(projectName)
                .projectType(projectType)
                .researchArea(researchArea)
                .targetAlienType(targetAlienType)
                .researchCost(researchCost)
                .researchTime(researchTime)
                .currentProgress(0)
                .maxProgress(100)
                .successRate(0.8)
                .failureRate(0.2)
                .criticalSuccessRate(0.1)
                .criticalFailureRate(0.05)
                .researchBonus("Research completed successfully")
                .researchPenalty("Research failed")
                .isActive(false)
                .isCompleted(false)
                .isFailed(false)
                .assignedScientists(0)
                .maxScientists(3)
                .researchPriority(1)
                .build();
    }
    
    /**
     * Create an alien technology
     */
    public static AlienTechnology createAlienTechnology(String technologyId, String technologyName,
                                                       String technologyType, int technologyCost,
                                                       String technologyBonus, String technologyPenalty) {
        return AlienTechnology.builder()
                .technologyId(technologyId)
                .technologyName(technologyName)
                .technologyType(technologyType)
                .technologyLevel("BASIC")
                .technologyCost(technologyCost)
                .technologyBonus(technologyBonus)
                .technologyPenalty(technologyPenalty)
                .isDiscovered(true)
                .isImplemented(false)
                .discoveryDate(new Date().toString())
                .implementationDate("")
                .technologyDescription("Technology created by factory")
                .build();
    }
}

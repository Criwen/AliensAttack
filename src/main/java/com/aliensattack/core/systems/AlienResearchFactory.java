package com.aliensattack.core.systems;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.systems.AlienResearchTechnologySystem.ResearchProject;
import com.aliensattack.core.systems.AlienResearchTechnologySystem.AlienTechnology;

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
    public static AlienTechnology createAlienTechnology(String technologyId, String name, 
                                                      String technologyType, String technologyLevel, 
                                                      int technologyCost, String technologyBonus, 
                                                      String technologyPenalty, boolean isDiscovered, 
                                                      boolean isImplemented, String discoveryDate, 
                                                      String implementationDate, String technologyDescription) {
        return AlienTechnology.builder()
                .technologyId(technologyId)
                .name(name)
                .technologyName(name)
                .technologyType(technologyType)
                .technologyLevel(technologyLevel)
                .technologyCost(technologyCost)
                .technologyBonus(technologyBonus)
                .technologyPenalty(technologyPenalty)
                .isDiscovered(isDiscovered)
                .isImplemented(isImplemented)
                .discoveryDate(discoveryDate)
                .implementationDate(implementationDate)
                .technologyDescription(technologyDescription)
                .build();
    }
    
    /**
     * Create basic research
     */
    public static ResearchProject createBasicResearch(String projectId, String name, 
                                                    AlienResearchTechnologySystem.ResearchCategory category) {
        return createResearchProject(projectId, name, "BASIC_RESEARCH", 
                                   category.name(), "GENERAL", 25, 30);
    }
    
    /**
     * Create advanced research
     */
    public static ResearchProject createAdvancedResearch(String projectId, String name, 
                                                        AlienResearchTechnologySystem.ResearchCategory category) {
        return createResearchProject(projectId, name, "ADVANCED_RESEARCH", 
                                   category.name(), "SPECIFIC", 50, 60);
    }
    
    /**
     * Create experimental research
     */
    public static ResearchProject createExperimentalResearch(String projectId, String name, 
                                                            AlienResearchTechnologySystem.ResearchCategory category) {
        return createResearchProject(projectId, name, "EXPERIMENTAL_RESEARCH", 
                                   category.name(), "EXPERIMENTAL", 100, 120);
    }
    
    /**
     * Create weapon research
     */
    public static ResearchProject createWeaponResearch(String projectId, String name) {
        return createResearchProject(projectId, name, "WEAPON_RESEARCH", 
                                   "WEAPON_TECHNOLOGY", "WEAPON", 75, 90);
    }
    
    /**
     * Validate research
     */
    public static boolean validateResearch(ResearchProject research) {
        return research != null && research.getProjectId() != null && 
               research.getProjectName() != null;
    }
    
    /**
     * Check if research is complete
     */
    public static boolean isResearchComplete(ResearchProject research) {
        return research != null && research.getCurrentProgress() >= research.getMaxProgress();
    }
    
    /**
     * Calculate research bonus
     */
    public static int calculateResearchBonus(ResearchProject research) {
        if (research == null) return 0;
        return research.getResearchPriority() * 10 + research.getAssignedScientists() * 5;
    }
    
    /**
     * Create random research
     */
    public static ResearchProject createRandomResearch(String projectId) {
        Random random = new Random();
        AlienResearchTechnologySystem.ResearchCategory[] categories = 
            AlienResearchTechnologySystem.ResearchCategory.values();
        AlienResearchTechnologySystem.ResearchType[] types = 
            AlienResearchTechnologySystem.ResearchType.values();
        
        return createResearchProject(projectId, "Random Research " + projectId, 
                                   types[random.nextInt(types.length)].name(),
                                   categories[random.nextInt(categories.length)].name(), 
                                   "RANDOM", random.nextInt(100) + 25, random.nextInt(120) + 30);
    }
    
    /**
     * Get available research categories
     */
    public static List<AlienResearchTechnologySystem.ResearchCategory> getAvailableResearchCategories() {
        return Arrays.asList(AlienResearchTechnologySystem.ResearchCategory.values());
    }
    
    /**
     * Get available research types
     */
    public static List<AlienResearchTechnologySystem.ResearchType> getAvailableResearchTypes() {
        return Arrays.asList(AlienResearchTechnologySystem.ResearchType.values());
    }
}

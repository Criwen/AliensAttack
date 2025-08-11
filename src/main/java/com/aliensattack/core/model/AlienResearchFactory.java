package com.aliensattack.core.model;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Alien Research Factory - Factory class for creating research components and projects
 * Provides standardized creation methods for research-related objects
 */
public class AlienResearchFactory {
    
    private static final Random random = new Random();
    
    /**
     * Create a basic research project
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createBasicResearch(
            String researchId, String name, 
            AdvancedAlienResearchTechnologySystem.ResearchCategory category) {
        
        return AdvancedAlienResearchTechnologySystem.AlienResearch.builder()
                .researchId(researchId)
                .name(name)
                .category(category)
                .type(AdvancedAlienResearchTechnologySystem.ResearchType.BASIC_RESEARCH)
                .difficulty(1)
                .progress(0)
                .requiredProgress(100)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(7))
                .isCompleted(false)
                .prerequisites(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create an advanced research project
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createAdvancedResearch(
            String researchId, String name, 
            AdvancedAlienResearchTechnologySystem.ResearchCategory category) {
        
        return AdvancedAlienResearchTechnologySystem.AlienResearch.builder()
                .researchId(researchId)
                .name(name)
                .category(category)
                .type(AdvancedAlienResearchTechnologySystem.ResearchType.ADVANCED_RESEARCH)
                .difficulty(3)
                .progress(0)
                .requiredProgress(300)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(21))
                .isCompleted(false)
                .prerequisites(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create an experimental research project
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createExperimentalResearch(
            String researchId, String name, 
            AdvancedAlienResearchTechnologySystem.ResearchCategory category) {
        
        return AdvancedAlienResearchTechnologySystem.AlienResearch.builder()
                .researchId(researchId)
                .name(name)
                .category(category)
                .type(AdvancedAlienResearchTechnologySystem.ResearchType.EXPERIMENTAL_RESEARCH)
                .difficulty(5)
                .progress(0)
                .requiredProgress(500)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(35))
                .isCompleted(false)
                .prerequisites(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a reverse engineering project
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createReverseEngineering(
            String researchId, String name, 
            AdvancedAlienResearchTechnologySystem.ResearchCategory category) {
        
        return AdvancedAlienResearchTechnologySystem.AlienResearch.builder()
                .researchId(researchId)
                .name(name)
                .category(category)
                .type(AdvancedAlienResearchTechnologySystem.ResearchType.REVERSE_ENGINEERING)
                .difficulty(4)
                .progress(0)
                .requiredProgress(400)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(28))
                .isCompleted(false)
                .prerequisites(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create an alien autopsy research
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createAlienAutopsy(
            String researchId, String name, 
            AdvancedAlienResearchTechnologySystem.ResearchCategory category) {
        
        return AdvancedAlienResearchTechnologySystem.AlienResearch.builder()
                .researchId(researchId)
                .name(name)
                .category(category)
                .type(AdvancedAlienResearchTechnologySystem.ResearchType.ALIEN_AUTOPSY)
                .difficulty(2)
                .progress(0)
                .requiredProgress(200)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(14))
                .isCompleted(false)
                .prerequisites(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a technology analysis research
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createTechnologyAnalysis(
            String researchId, String name, 
            AdvancedAlienResearchTechnologySystem.ResearchCategory category) {
        
        return AdvancedAlienResearchTechnologySystem.AlienResearch.builder()
                .researchId(researchId)
                .name(name)
                .category(category)
                .type(AdvancedAlienResearchTechnologySystem.ResearchType.TECHNOLOGY_ANALYSIS)
                .difficulty(3)
                .progress(0)
                .requiredProgress(300)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(21))
                .isCompleted(false)
                .prerequisites(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a research prerequisite
     */
    public static AdvancedAlienResearchTechnologySystem.ResearchPrerequisite createPrerequisite(
            String prerequisiteId, String researchId, 
            AdvancedAlienResearchTechnologySystem.PrerequisiteType type, String description) {
        
        return AdvancedAlienResearchTechnologySystem.ResearchPrerequisite.builder()
                .prerequisiteId(prerequisiteId)
                .researchId(researchId)
                .type(type)
                .isMet(false)
                .description(description)
                .build();
    }
    
    /**
     * Create a research effect
     */
    public static AdvancedAlienResearchTechnologySystem.ResearchEffect createEffect(
            String effectId, AdvancedAlienResearchTechnologySystem.EffectType type, 
            int magnitude, String description) {
        
        return AdvancedAlienResearchTechnologySystem.ResearchEffect.builder()
                .effectId(effectId)
                .type(type)
                .magnitude(magnitude)
                .description(description)
                .isApplied(false)
                .build();
    }
    
    /**
     * Create a research team
     */
    public static AlienResearchManager.ResearchTeam createResearchTeam(
            String teamId, String teamName, List<String> researchers) {
        
        return new AlienResearchManager.ResearchTeam(teamId, teamName, researchers);
    }
    
    /**
     * Create a research budget
     */
    public static AlienResearchManager.ResearchBudget createResearchBudget(
            String researchId, int allocatedBudget) {
        
        return new AlienResearchManager.ResearchBudget(researchId, allocatedBudget);
    }
    
    /**
     * Create a research milestone
     */
    public static AlienResearchManager.ResearchMilestone createResearchMilestone(
            String researchId, String description) {
        
        return new AlienResearchManager.ResearchMilestone(researchId, description, LocalDateTime.now());
    }
    
    /**
     * Create a random research project
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createRandomResearch(String researchId) {
        AdvancedAlienResearchTechnologySystem.ResearchCategory[] categories = 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.values();
        AdvancedAlienResearchTechnologySystem.ResearchType[] types = 
                AdvancedAlienResearchTechnologySystem.ResearchType.values();
        
        AdvancedAlienResearchTechnologySystem.ResearchCategory category = 
                categories[random.nextInt(categories.length)];
        AdvancedAlienResearchTechnologySystem.ResearchType type = 
                types[random.nextInt(types.length)];
        
        String name = "Random Research " + researchId;
        int difficulty = random.nextInt(5) + 1;
        
        return AdvancedAlienResearchTechnologySystem.AlienResearch.builder()
                .researchId(researchId)
                .name(name)
                .category(category)
                .type(type)
                .difficulty(difficulty)
                .progress(0)
                .requiredProgress(difficulty * 100)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(difficulty * 7))
                .isCompleted(false)
                .prerequisites(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a weapon technology research
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createWeaponTechnologyResearch(
            String researchId, String name) {
        
        return createAdvancedResearch(researchId, name, 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY);
    }
    
    /**
     * Create a defense technology research
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createDefenseTechnologyResearch(
            String researchId, String name) {
        
        return createAdvancedResearch(researchId, name, 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.DEFENSE_TECHNOLOGY);
    }
    
    /**
     * Create a psychic technology research
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createPsychicTechnologyResearch(
            String researchId, String name) {
        
        return createExperimentalResearch(researchId, name, 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.PSYCHIC_TECHNOLOGY);
    }
    
    /**
     * Create a biological technology research
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createBiologicalTechnologyResearch(
            String researchId, String name) {
        
        return createAdvancedResearch(researchId, name, 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.BIOLOGICAL_TECHNOLOGY);
    }
    
    /**
     * Create a medical technology research
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createMedicalTechnologyResearch(
            String researchId, String name) {
        
        return createBasicResearch(researchId, name, 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.MEDICAL_TECHNOLOGY);
    }
    
    /**
     * Create an experimental technology research
     */
    public static AdvancedAlienResearchTechnologySystem.AlienResearch createExperimentalTechnologyResearch(
            String researchId, String name) {
        
        return createExperimentalResearch(researchId, name, 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.EXPERIMENTAL_TECHNOLOGY);
    }
    
    /**
     * Get all research categories
     */
    public static List<AdvancedAlienResearchTechnologySystem.ResearchCategory> getAllResearchCategories() {
        return Arrays.asList(AdvancedAlienResearchTechnologySystem.ResearchCategory.values());
    }
    
    /**
     * Get all research types
     */
    public static List<AdvancedAlienResearchTechnologySystem.ResearchType> getAllResearchTypes() {
        return Arrays.asList(AdvancedAlienResearchTechnologySystem.ResearchType.values());
    }
    
    /**
     * Get all effect types
     */
    public static List<AdvancedAlienResearchTechnologySystem.EffectType> getAllEffectTypes() {
        return Arrays.asList(AdvancedAlienResearchTechnologySystem.EffectType.values());
    }
    
    /**
     * Get all prerequisite types
     */
    public static List<AdvancedAlienResearchTechnologySystem.PrerequisiteType> getAllPrerequisiteTypes() {
        return Arrays.asList(AdvancedAlienResearchTechnologySystem.PrerequisiteType.values());
    }
}

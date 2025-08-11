package com.aliensattack.core.model;

import java.util.*;
import java.time.LocalDateTime;

/**
 * Alien Technology Factory - Factory class for creating technology components and projects
 * Provides standardized creation methods for technology-related objects
 */
public class AlienTechnologyFactory {
    
    private static final Random random = new Random();
    
    /**
     * Create a weapon system technology project
     */
    public static AdvancedAlienResearchTechnologySystem.TechnologyProject createWeaponSystemProject(
            String projectId, String name, int complexity) {
        
        return AdvancedAlienResearchTechnologySystem.TechnologyProject.builder()
                .projectId(projectId)
                .name(name)
                .type(AdvancedAlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM)
                .complexity(complexity)
                .progress(0)
                .requiredProgress(complexity * 200)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(complexity * 14))
                .isCompleted(false)
                .dependencies(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a defense system technology project
     */
    public static AdvancedAlienResearchTechnologySystem.TechnologyProject createDefenseSystemProject(
            String projectId, String name, int complexity) {
        
        return AdvancedAlienResearchTechnologySystem.TechnologyProject.builder()
                .projectId(projectId)
                .name(name)
                .type(AdvancedAlienResearchTechnologySystem.TechnologyType.DEFENSE_SYSTEM)
                .complexity(complexity)
                .progress(0)
                .requiredProgress(complexity * 200)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(complexity * 14))
                .isCompleted(false)
                .dependencies(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a psychic amplifier technology project
     */
    public static AdvancedAlienResearchTechnologySystem.TechnologyProject createPsychicAmplifierProject(
            String projectId, String name, int complexity) {
        
        return AdvancedAlienResearchTechnologySystem.TechnologyProject.builder()
                .projectId(projectId)
                .name(name)
                .type(AdvancedAlienResearchTechnologySystem.TechnologyType.PSYCHIC_AMPLIFIER)
                .complexity(complexity)
                .progress(0)
                .requiredProgress(complexity * 400)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(complexity * 28))
                .isCompleted(false)
                .dependencies(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a medical device technology project
     */
    public static AdvancedAlienResearchTechnologySystem.TechnologyProject createMedicalDeviceProject(
            String projectId, String name, int complexity) {
        
        return AdvancedAlienResearchTechnologySystem.TechnologyProject.builder()
                .projectId(projectId)
                .name(name)
                .type(AdvancedAlienResearchTechnologySystem.TechnologyType.MEDICAL_DEVICE)
                .complexity(complexity)
                .progress(0)
                .requiredProgress(complexity * 200)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(complexity * 14))
                .isCompleted(false)
                .dependencies(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a stealth system technology project
     */
    public static AdvancedAlienResearchTechnologySystem.TechnologyProject createStealthSystemProject(
            String projectId, String name, int complexity) {
        
        return AdvancedAlienResearchTechnologySystem.TechnologyProject.builder()
                .projectId(projectId)
                .name(name)
                .type(AdvancedAlienResearchTechnologySystem.TechnologyType.STEALTH_SYSTEM)
                .complexity(complexity)
                .progress(0)
                .requiredProgress(complexity * 400)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(complexity * 28))
                .isCompleted(false)
                .dependencies(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create an experimental system technology project
     */
    public static AdvancedAlienResearchTechnologySystem.TechnologyProject createExperimentalSystemProject(
            String projectId, String name, int complexity) {
        
        return AdvancedAlienResearchTechnologySystem.TechnologyProject.builder()
                .projectId(projectId)
                .name(name)
                .type(AdvancedAlienResearchTechnologySystem.TechnologyType.EXPERIMENTAL_SYSTEM)
                .complexity(complexity)
                .progress(0)
                .requiredProgress(complexity * 600)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(complexity * 42))
                .isCompleted(false)
                .dependencies(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create an alien technology
     */
    public static AdvancedAlienResearchTechnologySystem.AlienTechnology createAlienTechnology(
            String technologyId, String name,
            AdvancedAlienResearchTechnologySystem.TechnologyCategory category,
            AdvancedAlienResearchTechnologySystem.TechnologyLevel level) {
        
        return AdvancedAlienResearchTechnologySystem.AlienTechnology.builder()
                .technologyId(technologyId)
                .name(name)
                .category(category)
                .level(level)
                .power(calculateTechnologyPower(level, category))
                .complexity(calculateTechnologyComplexity(level, category))
                .discoveryDate(LocalDateTime.now())
                .isUnderstood(false)
                .isReplicable(false)
                .capabilities(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a technology effect
     */
    public static AdvancedAlienResearchTechnologySystem.TechnologyEffect createTechnologyEffect(
            String effectId, AdvancedAlienResearchTechnologySystem.TechnologyEffectType type,
            int magnitude, String description) {
        
        return AdvancedAlienResearchTechnologySystem.TechnologyEffect.builder()
                .effectId(effectId)
                .type(type)
                .magnitude(magnitude)
                .description(description)
                .isActive(false)
                .build();
    }
    
    /**
     * Create a technology capability
     */
    public static AdvancedAlienResearchTechnologySystem.TechnologyCapability createTechnologyCapability(
            String capabilityId, String name,
            AdvancedAlienResearchTechnologySystem.CapabilityType type, int power) {
        
        return AdvancedAlienResearchTechnologySystem.TechnologyCapability.builder()
                .capabilityId(capabilityId)
                .name(name)
                .type(type)
                .power(power)
                .description("Technology capability")
                .isActive(true)
                .build();
    }
    
    /**
     * Create a technology project
     */
    public static AlienTechnologyManager.TechnologyProject createTechnologyProject(
            String projectId, String name,
            AdvancedAlienResearchTechnologySystem.TechnologyType type,
            int complexity, String teamId) {
        
        return new AlienTechnologyManager.TechnologyProject(projectId, name, type, complexity, teamId);
    }
    
    /**
     * Create a technology analysis
     */
    public static AlienTechnologyManager.TechnologyAnalysis createTechnologyAnalysis(
            String technologyId,
            AdvancedAlienResearchTechnologySystem.TechnologyCategory category,
            AdvancedAlienResearchTechnologySystem.TechnologyLevel level) {
        
        return new AlienTechnologyManager.TechnologyAnalysis(technologyId, category, level);
    }
    
    /**
     * Create a technology deployment
     */
    public static AlienTechnologyManager.TechnologyDeployment createTechnologyDeployment(
            String technologyId, String deploymentLocation, String deploymentTeam) {
        
        return new AlienTechnologyManager.TechnologyDeployment(technologyId, deploymentLocation, deploymentTeam);
    }
    
    /**
     * Create a technology breakthrough
     */
    public static AlienTechnologyManager.TechnologyBreakthrough createTechnologyBreakthrough(
            String technologyId, String description) {
        
        return new AlienTechnologyManager.TechnologyBreakthrough(technologyId, description, LocalDateTime.now());
    }
    
    /**
     * Create a random technology project
     */
    public static AdvancedAlienResearchTechnologySystem.TechnologyProject createRandomTechnologyProject(String projectId) {
        AdvancedAlienResearchTechnologySystem.TechnologyType[] types = 
                AdvancedAlienResearchTechnologySystem.TechnologyType.values();
        
        AdvancedAlienResearchTechnologySystem.TechnologyType type = 
                types[random.nextInt(types.length)];
        
        String name = "Random Technology " + projectId;
        int complexity = random.nextInt(5) + 1;
        
        return AdvancedAlienResearchTechnologySystem.TechnologyProject.builder()
                .projectId(projectId)
                .name(name)
                .type(type)
                .complexity(complexity)
                .progress(0)
                .requiredProgress(complexity * 200)
                .startDate(LocalDateTime.now())
                .expectedCompletionDate(LocalDateTime.now().plusDays(complexity * 14))
                .isCompleted(false)
                .dependencies(new ArrayList<>())
                .effects(new ArrayList<>())
                .build();
    }
    
    /**
     * Create a weapon alien technology
     */
    public static AdvancedAlienResearchTechnologySystem.AlienTechnology createWeaponAlienTechnology(
            String technologyId, String name, AdvancedAlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name, 
                AdvancedAlienResearchTechnologySystem.TechnologyCategory.WEAPON, level);
    }
    
    /**
     * Create a defense alien technology
     */
    public static AdvancedAlienResearchTechnologySystem.AlienTechnology createDefenseAlienTechnology(
            String technologyId, String name, AdvancedAlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name, 
                AdvancedAlienResearchTechnologySystem.TechnologyCategory.DEFENSE, level);
    }
    
    /**
     * Create a psychic alien technology
     */
    public static AdvancedAlienResearchTechnologySystem.AlienTechnology createPsychicAlienTechnology(
            String technologyId, String name, AdvancedAlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name, 
                AdvancedAlienResearchTechnologySystem.TechnologyCategory.PSYCHIC, level);
    }
    
    /**
     * Create a biological alien technology
     */
    public static AdvancedAlienResearchTechnologySystem.AlienTechnology createBiologicalAlienTechnology(
            String technologyId, String name, AdvancedAlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name, 
                AdvancedAlienResearchTechnologySystem.TechnologyCategory.BIOLOGICAL, level);
    }
    
    /**
     * Create an experimental alien technology
     */
    public static AdvancedAlienResearchTechnologySystem.AlienTechnology createExperimentalAlienTechnology(
            String technologyId, String name, AdvancedAlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name, 
                AdvancedAlienResearchTechnologySystem.TechnologyCategory.EXPERIMENTAL, level);
    }
    
    /**
     * Get all technology types
     */
    public static List<AdvancedAlienResearchTechnologySystem.TechnologyType> getAllTechnologyTypes() {
        return Arrays.asList(AdvancedAlienResearchTechnologySystem.TechnologyType.values());
    }
    
    /**
     * Get all technology categories
     */
    public static List<AdvancedAlienResearchTechnologySystem.TechnologyCategory> getAllTechnologyCategories() {
        return Arrays.asList(AdvancedAlienResearchTechnologySystem.TechnologyCategory.values());
    }
    
    /**
     * Get all technology levels
     */
    public static List<AdvancedAlienResearchTechnologySystem.TechnologyLevel> getAllTechnologyLevels() {
        return Arrays.asList(AdvancedAlienResearchTechnologySystem.TechnologyLevel.values());
    }
    
    /**
     * Get all technology effect types
     */
    public static List<AdvancedAlienResearchTechnologySystem.TechnologyEffectType> getAllTechnologyEffectTypes() {
        return Arrays.asList(AdvancedAlienResearchTechnologySystem.TechnologyEffectType.values());
    }
    
    /**
     * Get all capability types
     */
    public static List<AdvancedAlienResearchTechnologySystem.CapabilityType> getAllCapabilityTypes() {
        return Arrays.asList(AdvancedAlienResearchTechnologySystem.CapabilityType.values());
    }
    
    // Helper methods for calculating technology properties
    
    private static int calculateTechnologyPower(AdvancedAlienResearchTechnologySystem.TechnologyLevel level, 
                                              AdvancedAlienResearchTechnologySystem.TechnologyCategory category) {
        int basePower = level.getLevel() * 10;
        
        switch (category) {
            case WEAPON:
                return basePower * 2;
            case DEFENSE:
                return basePower * 1;
            case PSYCHIC:
                return basePower * 3;
            case BIOLOGICAL:
                return basePower * 2;
            case TRANSPORT:
                return basePower * 1;
            case COMMUNICATION:
                return basePower * 1;
            case STEALTH:
                return basePower * 2;
            case MEDICAL:
                return basePower * 1;
            case EXPERIMENTAL:
                return basePower * 4;
            default:
                return basePower;
        }
    }
    
    private static int calculateTechnologyComplexity(AdvancedAlienResearchTechnologySystem.TechnologyLevel level,
                                                   AdvancedAlienResearchTechnologySystem.TechnologyCategory category) {
        int baseComplexity = level.getLevel() * 5;
        
        switch (category) {
            case WEAPON:
                return baseComplexity * 1;
            case DEFENSE:
                return baseComplexity * 1;
            case PSYCHIC:
                return baseComplexity * 3;
            case BIOLOGICAL:
                return baseComplexity * 2;
            case TRANSPORT:
                return baseComplexity * 2;
            case COMMUNICATION:
                return baseComplexity * 1;
            case STEALTH:
                return baseComplexity * 2;
            case MEDICAL:
                return baseComplexity * 1;
            case EXPERIMENTAL:
                return baseComplexity * 4;
            default:
                return baseComplexity;
        }
    }
}

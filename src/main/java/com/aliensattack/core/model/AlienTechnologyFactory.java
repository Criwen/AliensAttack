package com.aliensattack.core.model;

import lombok.extern.log4j.Log4j2;
import java.util.*;

/**
 * Factory for creating alien technology components
 */
@Log4j2
public class AlienTechnologyFactory {
    
    /**
     * Create weapon system technology project
     */
    public static AlienResearchTechnologySystem.TechnologyProject createWeaponSystemProject(
            String projectId, String name, int complexity) {
        
        return AlienResearchTechnologySystem.TechnologyProject.builder()
            .projectId(projectId)
            .name(name)
            .type(AlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM)
            .category(AlienResearchTechnologySystem.TechnologyCategory.WEAPON)
            .level(AlienResearchTechnologySystem.TechnologyLevel.HUMAN_TECH)
            .complexity(complexity)
            .progress(0)
            .status(AlienResearchTechnologySystem.ProjectStatus.IN_PROGRESS)
            .engineerTeam("ENGINEER_TEAM_001")
            .build();
    }
    
    /**
     * Create defense system technology project
     */
    public static AlienResearchTechnologySystem.TechnologyProject createDefenseSystemProject(
            String projectId, String name, int complexity) {
        
        return AlienResearchTechnologySystem.TechnologyProject.builder()
            .projectId(projectId)
            .name(name)
            .type(AlienResearchTechnologySystem.TechnologyType.DEFENSE_SYSTEM)
            .category(AlienResearchTechnologySystem.TechnologyCategory.DEFENSE)
            .level(AlienResearchTechnologySystem.TechnologyLevel.HUMAN_TECH)
            .complexity(complexity)
            .progress(0)
            .status(AlienResearchTechnologySystem.ProjectStatus.IN_PROGRESS)
            .engineerTeam("ENGINEER_TEAM_001")
            .build();
    }
    
    /**
     * Create psychic amplifier technology project
     */
    public static AlienResearchTechnologySystem.TechnologyProject createPsychicAmplifierProject(
            String projectId, String name, int complexity) {
        
        return AlienResearchTechnologySystem.TechnologyProject.builder()
            .projectId(projectId)
            .name(name)
            .type(AlienResearchTechnologySystem.TechnologyType.PSYCHIC_AMPLIFIER)
            .category(AlienResearchTechnologySystem.TechnologyCategory.PSYCHIC)
            .level(AlienResearchTechnologySystem.TechnologyLevel.HUMAN_TECH)
            .complexity(complexity)
            .progress(0)
            .status(AlienResearchTechnologySystem.ProjectStatus.IN_PROGRESS)
            .engineerTeam("ENGINEER_TEAM_001")
            .build();
    }
    
    /**
     * Create medical device technology project
     */
    public static AlienResearchTechnologySystem.TechnologyProject createMedicalDeviceProject(
            String projectId, String name, int complexity) {
        
        return AlienResearchTechnologySystem.TechnologyProject.builder()
            .projectId(projectId)
            .name(name)
            .type(AlienResearchTechnologySystem.TechnologyType.MEDICAL_DEVICE)
            .category(AlienResearchTechnologySystem.TechnologyCategory.MEDICAL)
            .level(AlienResearchTechnologySystem.TechnologyLevel.HUMAN_TECH)
            .complexity(complexity)
            .progress(0)
            .status(AlienResearchTechnologySystem.ProjectStatus.IN_PROGRESS)
            .engineerTeam("ENGINEER_TEAM_001")
            .build();
    }
    
    /**
     * Create stealth system technology project
     */
    public static AlienResearchTechnologySystem.TechnologyProject createStealthSystemProject(
            String projectId, String name, int complexity) {
        
        return AlienResearchTechnologySystem.TechnologyProject.builder()
            .projectId(projectId)
            .name(name)
            .type(AlienResearchTechnologySystem.TechnologyType.STEALTH_SYSTEM)
            .category(AlienResearchTechnologySystem.TechnologyCategory.STEALTH)
            .level(AlienResearchTechnologySystem.TechnologyLevel.HUMAN_TECH)
            .complexity(complexity)
            .progress(0)
            .status(AlienResearchTechnologySystem.ProjectStatus.IN_PROGRESS)
            .engineerTeam("ENGINEER_TEAM_001")
            .build();
    }
    
    /**
     * Create experimental system technology project
     */
    public static AlienResearchTechnologySystem.TechnologyProject createExperimentalSystemProject(
            String projectId, String name, int complexity) {
        
        return AlienResearchTechnologySystem.TechnologyProject.builder()
            .projectId(projectId)
            .name(name)
            .type(AlienResearchTechnologySystem.TechnologyType.EXPERIMENTAL_SYSTEM)
            .category(AlienResearchTechnologySystem.TechnologyCategory.EXPERIMENTAL)
            .level(AlienResearchTechnologySystem.TechnologyLevel.HUMAN_TECH)
            .complexity(complexity)
            .progress(0)
            .status(AlienResearchTechnologySystem.ProjectStatus.IN_PROGRESS)
            .engineerTeam("ENGINEER_TEAM_001")
            .build();
    }
    
    /**
     * Create alien technology
     */
    public static AlienResearchTechnologySystem.AlienTechnology createAlienTechnology(
            String technologyId, String name,
            AlienResearchTechnologySystem.TechnologyCategory category,
            AlienResearchTechnologySystem.TechnologyLevel level) {
        
        return AlienResearchTechnologySystem.AlienTechnology.builder()
            .technologyId(technologyId)
            .name(name)
            .category(category)
            .level(level)
            .power(calculateTechnologyPower(level, category))
            .complexity(calculateTechnologyComplexity(level, category))
            .unlockRequirements(new ArrayList<>())
            .effects(new ArrayList<>())
            .capabilities(new ArrayList<>())
            .build();
    }
    
    /**
     * Create technology effect
     */
    public static AlienResearchTechnologySystem.TechnologyEffect createTechnologyEffect(
            String effectId, AlienResearchTechnologySystem.TechnologyEffectType type,
            int magnitude, String description) {
        
        return AlienResearchTechnologySystem.TechnologyEffect.builder()
            .effectId(effectId)
            .type(type)
            .magnitude(magnitude)
            .description(description)
            .duration(5)
            .build();
    }
    
    /**
     * Create technology capability
     */
    public static AlienResearchTechnologySystem.TechnologyCapability createTechnologyCapability(
            String capabilityId,
            AlienResearchTechnologySystem.CapabilityType type, int power) {
        
        return AlienResearchTechnologySystem.TechnologyCapability.builder()
            .capabilityId(capabilityId)
            .type(type)
            .power(power)
            .cooldown(3)
            .build();
    }
    
    /**
     * Create random technology project
     */
    public static AlienResearchTechnologySystem.TechnologyProject createRandomTechnologyProject(String projectId) {
        AlienResearchTechnologySystem.TechnologyType[] types = 
            AlienResearchTechnologySystem.TechnologyType.values();
        
        AlienResearchTechnologySystem.TechnologyType type = 
            types[new Random().nextInt(types.length)];
        
        return AlienResearchTechnologySystem.TechnologyProject.builder()
            .projectId(projectId)
            .name("Random " + type.name())
            .type(type)
            .category(AlienResearchTechnologySystem.TechnologyCategory.WEAPON)
            .level(AlienResearchTechnologySystem.TechnologyLevel.HUMAN_TECH)
            .complexity(3)
            .progress(0)
            .status(AlienResearchTechnologySystem.ProjectStatus.IN_PROGRESS)
            .engineerTeam("ENGINEER_TEAM_001")
            .build();
    }
    
    /**
     * Create weapon alien technology
     */
    public static AlienResearchTechnologySystem.AlienTechnology createWeaponAlienTechnology(
            String technologyId, String name, 
            AlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name,
            AlienResearchTechnologySystem.TechnologyCategory.WEAPON, level);
    }
    
    /**
     * Create defense alien technology
     */
    public static AlienResearchTechnologySystem.AlienTechnology createDefenseAlienTechnology(
            String technologyId, String name, 
            AlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name,
            AlienResearchTechnologySystem.TechnologyCategory.DEFENSE, level);
    }
    
    /**
     * Create psychic alien technology
     */
    public static AlienResearchTechnologySystem.AlienTechnology createPsychicAlienTechnology(
            String technologyId, String name, 
            AlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name,
            AlienResearchTechnologySystem.TechnologyCategory.PSYCHIC, level);
    }
    
    /**
     * Create biological alien technology
     */
    public static AlienResearchTechnologySystem.AlienTechnology createBiologicalAlienTechnology(
            String technologyId, String name, 
            AlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name,
            AlienResearchTechnologySystem.TechnologyCategory.BIOLOGICAL, level);
    }
    
    /**
     * Create experimental alien technology
     */
    public static AlienResearchTechnologySystem.AlienTechnology createExperimentalAlienTechnology(
            String technologyId, String name, 
            AlienResearchTechnologySystem.TechnologyLevel level) {
        
        return createAlienTechnology(technologyId, name,
            AlienResearchTechnologySystem.TechnologyCategory.EXPERIMENTAL, level);
    }
    
    /**
     * Get all technology types
     */
    public static List<AlienResearchTechnologySystem.TechnologyType> getAllTechnologyTypes() {
        return Arrays.asList(AlienResearchTechnologySystem.TechnologyType.values());
    }
    
    /**
     * Get all technology categories
     */
    public static List<AlienResearchTechnologySystem.TechnologyCategory> getAllTechnologyCategories() {
        return Arrays.asList(AlienResearchTechnologySystem.TechnologyCategory.values());
    }
    
    /**
     * Get all technology levels
     */
    public static List<AlienResearchTechnologySystem.TechnologyLevel> getAllTechnologyLevels() {
        return Arrays.asList(AlienResearchTechnologySystem.TechnologyLevel.values());
    }
    
    /**
     * Get all technology effect types
     */
    public static List<AlienResearchTechnologySystem.TechnologyEffectType> getAllTechnologyEffectTypes() {
        return Arrays.asList(AlienResearchTechnologySystem.TechnologyEffectType.values());
    }
    
    /**
     * Get all capability types
     */
    public static List<AlienResearchTechnologySystem.CapabilityType> getAllCapabilityTypes() {
        return Arrays.asList(AlienResearchTechnologySystem.CapabilityType.values());
    }
    
    /**
     * Calculate technology power based on level and category
     */
    private static int calculateTechnologyPower(AlienResearchTechnologySystem.TechnologyLevel level,
                                               AlienResearchTechnologySystem.TechnologyCategory category) {
        int basePower = 10;
        
        switch (level) {
            case HUMAN_TECH:
                basePower = 15;
                break;
            case ALIEN_TECH:
                basePower = 25;
                break;
            case BEYOND_HUMAN:
                basePower = 35;
                break;
            case EXPERIMENTAL:
                basePower = 45;
                break;
        }
        
        switch (category) {
            case WEAPON:
                basePower += 5;
                break;
            case DEFENSE:
                basePower += 3;
                break;
            case PSYCHIC:
                basePower += 7;
                break;
            case MEDICAL:
                basePower += 2;
                break;
            case STEALTH:
                basePower += 4;
                break;
            case EXPERIMENTAL:
                basePower += 10;
                break;
        }
        
        return basePower;
    }
    
    /**
     * Calculate technology complexity based on level and category
     */
    private static int calculateTechnologyComplexity(AlienResearchTechnologySystem.TechnologyLevel level,
                                                     AlienResearchTechnologySystem.TechnologyCategory category) {
        int baseComplexity = 1;
        
        switch (level) {
            case HUMAN_TECH:
                baseComplexity = 2;
                break;
            case ALIEN_TECH:
                baseComplexity = 4;
                break;
            case BEYOND_HUMAN:
                baseComplexity = 6;
                break;
            case EXPERIMENTAL:
                baseComplexity = 8;
                break;
        }
        
        switch (category) {
            case WEAPON:
                baseComplexity += 1;
                break;
            case DEFENSE:
                baseComplexity += 1;
                break;
            case PSYCHIC:
                baseComplexity += 2;
                break;
            case MEDICAL:
                baseComplexity += 1;
                break;
            case STEALTH:
                baseComplexity += 1;
                break;
            case EXPERIMENTAL:
                baseComplexity += 3;
                break;
        }
        
        return baseComplexity;
    }
}

package com.aliensattack.core.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

/**
 * Test class for Alien Evolution System
 */
@DisplayName("Alien Evolution Tests")
public class AlienEvolutionTest {
    
    private AlienEvolution alienEvolution;
    
    @BeforeEach
    void setUp() {
        alienEvolution = new AlienEvolution();
        alienEvolution.initialize();
    }
    
    @Test
    @DisplayName("Should initialize alien evolution system correctly")
    void shouldInitializeAlienEvolutionSystemCorrectly() {
        assertNotNull(alienEvolution);
        assertNotNull(alienEvolution.getAlienTypes());
        assertNotNull(alienEvolution.getEvolutionStages());
        assertNotNull(alienEvolution.getEvolvedAliens());
    }
    
    @Test
    @DisplayName("Should test alien research technology system")
    void testAlienResearchTechnologySystem() {
        // Test research creation
        AlienResearchTechnologySystem.ResearchCategory weaponCategory = 
            AlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY;
        AlienResearchTechnologySystem.ResearchType advancedResearchType = 
            AlienResearchTechnologySystem.ResearchType.ADVANCED_RESEARCH;
        
        // Test technology creation
        AlienResearchTechnologySystem.TechnologyType weaponSystem = 
            AlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM;
        
        // Test technology category and level
        AlienResearchTechnologySystem.TechnologyCategory weaponCategory2 = 
            AlienResearchTechnologySystem.TechnologyCategory.WEAPON;
        AlienResearchTechnologySystem.TechnologyLevel alienTechLevel = 
            AlienResearchTechnologySystem.TechnologyLevel.ALIEN_TECH;
        
        // Test research creation
        AlienResearchTechnologySystem.AlienResearch basicResearch = 
            AlienResearchFactory.createBasicResearch("BASIC_001", "Basic Weapon Research",
            AlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY);
        
        AlienResearchTechnologySystem.AlienResearch advancedResearch = 
            AlienResearchFactory.createAdvancedResearch("ADVANCED_001", "Advanced Defense Research",
            AlienResearchTechnologySystem.ResearchCategory.DEFENSE_TECHNOLOGY);
        System.out.println("Advanced research created: " + advancedResearch.getName());
        
        AlienResearchTechnologySystem.AlienResearch experimentalResearch = 
            AlienResearchFactory.createExperimentalResearch("EXPERIMENTAL_001", "Experimental Psychic Research",
            AlienResearchTechnologySystem.ResearchCategory.PSYCHIC_TECHNOLOGY);
        
        AlienResearchTechnologySystem.AlienResearch weaponResearch = 
            AlienResearchFactory.createWeaponResearch("WEAPON_001", "Weapon Technology Research");
        
        // Test research validation
        assertTrue(AlienResearchFactory.validateResearch(basicResearch));
        assertTrue(AlienResearchFactory.validateResearch(advancedResearch));
        assertTrue(AlienResearchFactory.validateResearch(experimentalResearch));
        assertTrue(AlienResearchFactory.validateResearch(weaponResearch));
        
        // Test research completion
        basicResearch.setProgress(100);
        assertTrue(AlienResearchFactory.isResearchComplete(basicResearch));
        
        // Test research bonus calculation
        int bonus = AlienResearchFactory.calculateResearchBonus(basicResearch);
        assertTrue(bonus > 0);
        
        // Test random research generation
        AlienResearchTechnologySystem.AlienResearch randomResearch = 
            AlienResearchFactory.createRandomResearch("RANDOM_001");
        assertNotNull(randomResearch);
        
        // Test research categories
        List<AlienResearchTechnologySystem.ResearchCategory> categories = 
            AlienResearchFactory.getAvailableResearchCategories();
        assertFalse(categories.isEmpty());
        
        // Test research types
        List<AlienResearchTechnologySystem.ResearchType> types = 
            AlienResearchFactory.getAvailableResearchTypes();
        assertFalse(types.isEmpty());
        
        // Test technology project creation
        AlienResearchTechnologySystem.TechnologyProject weaponProject = 
            AlienTechnologyFactory.createWeaponSystemProject("WEAPON_PROJ_001", "Advanced Weapon System", 3);
        
        AlienResearchTechnologySystem.TechnologyProject defenseProject = 
            AlienTechnologyFactory.createDefenseSystemProject("DEFENSE_PROJ_001", "Advanced Defense System", 3);
        
        AlienResearchTechnologySystem.TechnologyProject psychicProject = 
            AlienTechnologyFactory.createPsychicAmplifierProject("PSYCHIC_PROJ_001", "Psychic Amplifier", 3);
        
        // Test alien technology creation
        AlienResearchTechnologySystem.AlienTechnology alienTech = 
            AlienTechnologyFactory.createAlienTechnology("ALIEN_TECH_001", "Alien Defense Tech",
            AlienResearchTechnologySystem.TechnologyCategory.DEFENSE,
            AlienResearchTechnologySystem.TechnologyLevel.ALIEN_TECH);
        
        AlienResearchTechnologySystem.AlienTechnology weaponAlienTech = 
            AlienTechnologyFactory.createWeaponAlienTechnology("WEAPON_ALIEN_001", "Alien Weapon Tech",
            AlienResearchTechnologySystem.TechnologyLevel.BEYOND_HUMAN);
        
        // Test technology effects
        AlienResearchTechnologySystem.TechnologyEffect effect = 
            AlienTechnologyFactory.createTechnologyEffect("EFFECT_001", 
            AlienResearchTechnologySystem.TechnologyEffectType.DAMAGE_INCREASE, 25, "Damage boost");
        
        // Test technology capabilities
        AlienResearchTechnologySystem.TechnologyCapability capability = 
            AlienTechnologyFactory.createTechnologyCapability("CAPABILITY_001",
            AlienResearchTechnologySystem.CapabilityType.ACCURACY_BONUS, 30);
        
        // Test technology integration
        AlienResearchTechnologySystem.TechnologyType sensorSystem = 
            AlienResearchTechnologySystem.TechnologyType.SENSOR_SYSTEM;
        int integrationLevel = 3;
        String engineerTeam = "ENGINEER_TEAM_002";
        
        // Test technology validation
        assertTrue(AlienTechnologyFactory.validateTechnology(alienTech));
        assertTrue(AlienTechnologyFactory.validateTechnology(weaponAlienTech));
        
        // Test technology level validation
        assertTrue(AlienTechnologyFactory.isValidTechnologyLevel(
            AlienResearchTechnologySystem.TechnologyCategory.PSYCHIC,
            AlienResearchTechnologySystem.TechnologyLevel.EXPERIMENTAL));
        
        // Test technology bonus calculation
        int techBonus = AlienTechnologyFactory.calculateTechnologyBonus(alienTech);
        assertTrue(techBonus > 0);
        
        // Test random technology project generation
        AlienResearchTechnologySystem.TechnologyProject randomProject = 
            AlienTechnologyFactory.createRandomTechnologyProject("RANDOM_TECH_001");
        assertNotNull(randomProject);
        
        // Test technology types
        List<AlienResearchTechnologySystem.TechnologyType> techTypes = 
            AlienTechnologyFactory.getAvailableTechnologyTypes();
        assertFalse(techTypes.isEmpty());
        
        // Test technology categories
        List<AlienResearchTechnologySystem.TechnologyCategory> techCategories = 
            AlienTechnologyFactory.getAvailableTechnologyCategories();
        assertFalse(techCategories.isEmpty());
        
        // Test technology levels
        List<AlienResearchTechnologySystem.TechnologyLevel> techLevels = 
            AlienTechnologyFactory.getAvailableTechnologyLevels();
        assertFalse(techLevels.isEmpty());
    }
    
    @Test
    @DisplayName("Should test alien research technology system directly")
    void testAlienResearchTechnologySystemDirectly() {
        System.out.println("\n=== Testing AlienResearchTechnologySystem Directly ===");
        
        AlienResearchTechnologySystem system = new AlienResearchTechnologySystem();
        system.initialize();
        
        // Test research creation
        AlienResearchTechnologySystem.ResearchCategory weaponCategory = 
            AlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY;
        AlienResearchTechnologySystem.ResearchType advancedResearchType = 
            AlienResearchTechnologySystem.ResearchType.ADVANCED_RESEARCH;
        
        // Test technology creation
        AlienResearchTechnologySystem.TechnologyType weaponSystem = 
            AlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM;
        
        // Test technology category and level
        AlienResearchTechnologySystem.TechnologyCategory weaponCategory2 = 
            AlienResearchTechnologySystem.TechnologyCategory.WEAPON;
        AlienResearchTechnologySystem.TechnologyLevel alienTechLevel = 
            AlienResearchTechnologySystem.TechnologyLevel.ALIEN_TECH;
        
        // Test threat assessment
        AlienResearchTechnologySystem.ThreatType alienSpecialist = 
            AlienResearchTechnologySystem.ThreatType.ALIEN_SPECIALIST;
        int threatLevel = 100;
        int difficulty = 50;
        
        System.out.println("Weapon Category: " + weaponCategory);
        System.out.println("Advanced Research Type: " + advancedResearchType);
        System.out.println("Weapon System: " + weaponSystem);
        System.out.println("Weapon Category2: " + weaponCategory2);
        System.out.println("Alien Tech Level: " + alienTechLevel);
        System.out.println("Alien Specialist: " + alienSpecialist);
        System.out.println("Threat Level: " + threatLevel);
        System.out.println("Difficulty: " + difficulty);
        
        // Test system status
        String status = system.getAlienResearchTechnologyStatus();
        System.out.println("System Status: " + status);
        assertNotNull(status);
        assertTrue(status.contains("Alien Research Technology Status"));
    }
    
    @Test
    @DisplayName("Should test alien research factory")
    void testAlienResearchFactory() {
        System.out.println("\n=== Testing AlienResearchFactory ===");
        
        // Test research creation with different categories
        AlienResearchTechnologySystem.ResearchCategory weaponCategory = 
            AlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY;
        AlienResearchTechnologySystem.ResearchType advancedResearchType = 
            AlienResearchTechnologySystem.ResearchType.ADVANCED_RESEARCH;
        int complexity = 3;
        
        // Test technology creation
        AlienResearchTechnologySystem.TechnologyType weaponSystem = 
            AlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM;
        int techComplexity = 3;
        
        // Test technology category and level
        AlienResearchTechnologySystem.TechnologyCategory weaponCategory2 = 
            AlienResearchTechnologySystem.TechnologyCategory.WEAPON;
        AlienResearchTechnologySystem.TechnologyLevel alienTechLevel = 
            AlienResearchTechnologySystem.TechnologyLevel.ALIEN_TECH;
        
        System.out.println("Weapon Category: " + weaponCategory);
        System.out.println("Advanced Research Type: " + advancedResearchType);
        System.out.println("Complexity: " + complexity);
        System.out.println("Weapon System: " + weaponSystem);
        System.out.println("Tech Complexity: " + techComplexity);
        System.out.println("Weapon Category2: " + weaponCategory2);
        System.out.println("Alien Tech Level: " + alienTechLevel);
    }
}

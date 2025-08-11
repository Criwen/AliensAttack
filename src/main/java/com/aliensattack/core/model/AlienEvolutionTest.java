package com.aliensattack.core.model;

import java.util.*;

/**
 * Alien Evolution Test - Test class for Alien Evolution System
 * Tests the functionality of research and technology systems
 */
public class AlienEvolutionTest {
    
    public static void main(String[] args) {
        System.out.println("=== Alien Evolution System Test ===");
        
        // Test Alien Research Manager
        testAlienResearchManager();
        
        // Test Alien Technology Manager
        testAlienTechnologyManager();
        
        // Test Research Factory
        testAlienResearchFactory();
        
        // Test Technology Factory
        testAlienTechnologyFactory();
        
        // Test Advanced Alien Research Technology System
        testAdvancedAlienResearchTechnologySystem();
        
        // Test Integration
        testIntegration();
        
        System.out.println("=== All tests completed ===");
    }
    
    private static void testAlienResearchManager() {
        System.out.println("\n--- Testing Alien Research Manager ---");
        
        AlienResearchManager researchManager = new AlienResearchManager();
        
        // Create research team
        List<String> researchers = Arrays.asList("Dr. Smith", "Dr. Johnson", "Dr. Williams");
        boolean teamCreated = researchManager.createResearchTeam("TEAM_001", "Advanced Research Team", researchers);
        System.out.println("Research team created: " + teamCreated);
        
        // Start research project
        boolean projectStarted = researchManager.startResearchProject(
            "RESEARCH_001", 
            "Alien Weapon Technology", 
            AdvancedAlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY,
            AdvancedAlienResearchTechnologySystem.ResearchType.ADVANCED_RESEARCH,
            3, 
            "TEAM_001"
        );
        System.out.println("Research project started: " + projectStarted);
        
        // Update research progress
        boolean progressUpdated = researchManager.updateResearchProgress("RESEARCH_001", 50, "Dr. Smith");
        System.out.println("Research progress updated: " + progressUpdated);
        
        // Get research progress
        double progress = researchManager.getResearchProgress("RESEARCH_001");
        System.out.println("Research progress: " + (progress * 100) + "%");
        
        // Complete research project
        boolean projectCompleted = researchManager.completeResearchProject("RESEARCH_001");
        System.out.println("Research project completed: " + projectCompleted);
        
        // Get statistics
        AlienResearchManager.ResearchStatistics stats = researchManager.getResearchStatistics();
        System.out.println("Research statistics:");
        System.out.println("  Total projects: " + stats.getTotalProjects());
        System.out.println("  Completed projects: " + stats.getCompletedProjects());
        System.out.println("  In progress projects: " + stats.getInProgressProjects());
        System.out.println("  Completion rate: " + (stats.getCompletionRate() * 100) + "%");
        System.out.println("  Available research points: " + stats.getAvailableResearchPoints());
    }
    
    private static void testAlienTechnologyManager() {
        System.out.println("\n--- Testing Alien Technology Manager ---");
        
        AlienTechnologyManager technologyManager = new AlienTechnologyManager();
        
        // Start technology project
        boolean projectStarted = technologyManager.startTechnologyProject(
            "TECH_001", 
            "Advanced Weapon System", 
            AdvancedAlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM,
            3, 
            "ENGINEER_TEAM_001"
        );
        System.out.println("Technology project started: " + projectStarted);
        
        // Update technology progress
        boolean progressUpdated = technologyManager.updateTechnologyProgress("TECH_001", 100, "Engineer Smith");
        System.out.println("Technology progress updated: " + progressUpdated);
        
        // Complete technology project
        boolean projectCompleted = technologyManager.completeTechnologyProject("TECH_001");
        System.out.println("Technology project completed: " + projectCompleted);
        
        // Discover alien technology
        boolean technologyDiscovered = technologyManager.discoverAlienTechnology(
            "ALIEN_TECH_001", 
            "Alien Plasma Rifle", 
            AdvancedAlienResearchTechnologySystem.TechnologyCategory.WEAPON,
            AdvancedAlienResearchTechnologySystem.TechnologyLevel.ALIEN_TECH
        );
        System.out.println("Alien technology discovered: " + technologyDiscovered);
        
        // Analyze alien technology
        boolean technologyAnalyzed = technologyManager.analyzeAlienTechnology("ALIEN_TECH_001", "Dr. Analyst");
        System.out.println("Alien technology analyzed: " + technologyAnalyzed);
        
        // Deploy technology
        boolean technologyDeployed = technologyManager.deployTechnology("ALIEN_TECH_001", "Base Alpha", "Deployment Team 1");
        System.out.println("Technology deployed: " + technologyDeployed);
        
        // Get statistics
        AlienTechnologyManager.TechnologyStatistics stats = technologyManager.getTechnologyStatistics();
        System.out.println("Technology statistics:");
        System.out.println("  Total projects: " + stats.getTotalProjects());
        System.out.println("  Completed projects: " + stats.getCompletedProjects());
        System.out.println("  In progress projects: " + stats.getInProgressProjects());
        System.out.println("  Discovered technologies: " + stats.getDiscoveredTechnologies());
        System.out.println("  Analyzed technologies: " + stats.getAnalyzedTechnologies());
        System.out.println("  Completion rate: " + (stats.getCompletionRate() * 100) + "%");
        System.out.println("  Analysis rate: " + (stats.getAnalysisRate() * 100) + "%");
        System.out.println("  Available technology points: " + stats.getAvailableTechnologyPoints());
    }
    
    private static void testAlienResearchFactory() {
        System.out.println("\n--- Testing Alien Research Factory ---");
        
        // Create basic research
        AdvancedAlienResearchTechnologySystem.AlienResearch basicResearch = 
            AlienResearchFactory.createBasicResearch("BASIC_001", "Basic Weapon Research", 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY);
        System.out.println("Basic research created: " + basicResearch.getName());
        
        // Create advanced research
        AdvancedAlienResearchTechnologySystem.AlienResearch advancedResearch = 
            AlienResearchFactory.createAdvancedResearch("ADVANCED_001", "Advanced Defense Research", 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.DEFENSE_TECHNOLOGY);
        System.out.println("Advanced research created: " + advancedResearch.getName());
        
        // Create experimental research
        AdvancedAlienResearchTechnologySystem.AlienResearch experimentalResearch = 
            AlienResearchFactory.createExperimentalResearch("EXPERIMENTAL_001", "Experimental Psychic Research", 
                AdvancedAlienResearchTechnologySystem.ResearchCategory.PSYCHIC_TECHNOLOGY);
        System.out.println("Experimental research created: " + experimentalResearch.getName());
        
        // Create weapon technology research
        AdvancedAlienResearchTechnologySystem.AlienResearch weaponResearch = 
            AlienResearchFactory.createWeaponTechnologyResearch("WEAPON_001", "Plasma Weapon Research");
        System.out.println("Weapon research created: " + weaponResearch.getName());
        
        // Create research team
        List<String> researchers = Arrays.asList("Dr. Einstein", "Dr. Tesla", "Dr. Curie");
        AlienResearchManager.ResearchTeam team = 
            AlienResearchFactory.createResearchTeam("TEAM_002", "Elite Research Team", researchers);
        System.out.println("Research team created: " + team.getTeamName());
        
        // Create research budget
        AlienResearchManager.ResearchBudget budget = 
            AlienResearchFactory.createResearchBudget("RESEARCH_002", 1000);
        System.out.println("Research budget created: " + budget.getAllocatedBudget());
        
        // Create research milestone
        AlienResearchManager.ResearchMilestone milestone = 
            AlienResearchFactory.createResearchMilestone("RESEARCH_002", "Research milestone achieved");
        System.out.println("Research milestone created: " + milestone.getDescription());
        
        // Create random research
        AdvancedAlienResearchTechnologySystem.AlienResearch randomResearch = 
            AlienResearchFactory.createRandomResearch("RANDOM_001");
        System.out.println("Random research created: " + randomResearch.getName());
        
        // Get all categories
        List<AdvancedAlienResearchTechnologySystem.ResearchCategory> categories = 
            AlienResearchFactory.getAllResearchCategories();
        System.out.println("Research categories count: " + categories.size());
        
        // Get all types
        List<AdvancedAlienResearchTechnologySystem.ResearchType> types = 
            AlienResearchFactory.getAllResearchTypes();
        System.out.println("Research types count: " + types.size());
    }
    
    private static void testAlienTechnologyFactory() {
        System.out.println("\n--- Testing Alien Technology Factory ---");
        
        // Create weapon system project
        AdvancedAlienResearchTechnologySystem.TechnologyProject weaponProject = 
            AlienTechnologyFactory.createWeaponSystemProject("WEAPON_PROJ_001", "Plasma Cannon", 3);
        System.out.println("Weapon system project created: " + weaponProject.getName());
        
        // Create defense system project
        AdvancedAlienResearchTechnologySystem.TechnologyProject defenseProject = 
            AlienTechnologyFactory.createDefenseSystemProject("DEFENSE_PROJ_001", "Shield Generator", 2);
        System.out.println("Defense system project created: " + defenseProject.getName());
        
        // Create psychic amplifier project
        AdvancedAlienResearchTechnologySystem.TechnologyProject psychicProject = 
            AlienTechnologyFactory.createPsychicAmplifierProject("PSYCHIC_PROJ_001", "Mind Amplifier", 4);
        System.out.println("Psychic amplifier project created: " + psychicProject.getName());
        
        // Create alien technology
        AdvancedAlienResearchTechnologySystem.AlienTechnology alienTech = 
            AlienTechnologyFactory.createAlienTechnology("ALIEN_TECH_002", "Alien Shield", 
                AdvancedAlienResearchTechnologySystem.TechnologyCategory.DEFENSE,
                AdvancedAlienResearchTechnologySystem.TechnologyLevel.ALIEN_TECH);
        System.out.println("Alien technology created: " + alienTech.getName());
        
        // Create weapon alien technology
        AdvancedAlienResearchTechnologySystem.AlienTechnology weaponAlienTech = 
            AlienTechnologyFactory.createWeaponAlienTechnology("ALIEN_WEAPON_001", "Alien Plasma Rifle", 
                AdvancedAlienResearchTechnologySystem.TechnologyLevel.BEYOND_HUMAN);
        System.out.println("Weapon alien technology created: " + weaponAlienTech.getName());
        
        // Create technology effect
        AdvancedAlienResearchTechnologySystem.TechnologyEffect effect = 
            AlienTechnologyFactory.createTechnologyEffect("EFFECT_001", 
                AdvancedAlienResearchTechnologySystem.TechnologyEffectType.DAMAGE_INCREASE, 25, "Damage boost");
        System.out.println("Technology effect created: " + effect.getDescription());
        
        // Create technology capability
        AdvancedAlienResearchTechnologySystem.TechnologyCapability capability = 
            AlienTechnologyFactory.createTechnologyCapability("CAP_001", "Enhanced Accuracy", 
                AdvancedAlienResearchTechnologySystem.CapabilityType.ACCURACY_BONUS, 30);
        System.out.println("Technology capability created: " + capability.getName());
        
        // Create technology project
        AlienTechnologyManager.TechnologyProject techProject = 
            AlienTechnologyFactory.createTechnologyProject("TECH_PROJ_001", "Advanced Sensor System", 
                AdvancedAlienResearchTechnologySystem.TechnologyType.SENSOR_SYSTEM, 3, "ENGINEER_TEAM_002");
        System.out.println("Technology project created: " + techProject.getName());
        
        // Create technology analysis
        AlienTechnologyManager.TechnologyAnalysis analysis = 
            AlienTechnologyFactory.createTechnologyAnalysis("ALIEN_TECH_003", 
                AdvancedAlienResearchTechnologySystem.TechnologyCategory.PSYCHIC,
                AdvancedAlienResearchTechnologySystem.TechnologyLevel.EXPERIMENTAL);
        System.out.println("Technology analysis created for: " + analysis.getTechnologyId());
        
        // Create technology deployment
        AlienTechnologyManager.TechnologyDeployment deployment = 
            AlienTechnologyFactory.createTechnologyDeployment("ALIEN_TECH_004", "Base Beta", "Deployment Team 2");
        System.out.println("Technology deployment created: " + deployment.getDeploymentLocation());
        
        // Create technology breakthrough
        AlienTechnologyManager.TechnologyBreakthrough breakthrough = 
            AlienTechnologyFactory.createTechnologyBreakthrough("ALIEN_TECH_005", "Technology breakthrough achieved");
        System.out.println("Technology breakthrough created: " + breakthrough.getDescription());
        
        // Create random technology project
        AdvancedAlienResearchTechnologySystem.TechnologyProject randomProject = 
            AlienTechnologyFactory.createRandomTechnologyProject("RANDOM_TECH_001");
        System.out.println("Random technology project created: " + randomProject.getName());
        
        // Get all technology types
        List<AdvancedAlienResearchTechnologySystem.TechnologyType> techTypes = 
            AlienTechnologyFactory.getAllTechnologyTypes();
        System.out.println("Technology types count: " + techTypes.size());
        
        // Get all technology categories
        List<AdvancedAlienResearchTechnologySystem.TechnologyCategory> techCategories = 
            AlienTechnologyFactory.getAllTechnologyCategories();
        System.out.println("Technology categories count: " + techCategories.size());
        
        // Get all technology levels
        List<AdvancedAlienResearchTechnologySystem.TechnologyLevel> techLevels = 
            AlienTechnologyFactory.getAllTechnologyLevels();
        System.out.println("Technology levels count: " + techLevels.size());
    }
    
    private static void testAdvancedAlienResearchTechnologySystem() {
        System.out.println("\n--- Testing Advanced Alien Research Technology System ---");
        
        AdvancedAlienResearchTechnologySystem system = new AdvancedAlienResearchTechnologySystem();
        
        // Start research
        boolean researchStarted = system.startResearch("ADV_RESEARCH_001", "Advanced Alien Research", 
            AdvancedAlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY,
            AdvancedAlienResearchTechnologySystem.ResearchType.ADVANCED_RESEARCH, 3);
        System.out.println("Advanced research started: " + researchStarted);
        
        // Update research progress
        boolean progressUpdated = system.updateResearchProgress("ADV_RESEARCH_001", 150);
        System.out.println("Advanced research progress updated: " + progressUpdated);
        
        // Start technology project
        boolean techProjectStarted = system.startTechnologyProject("ADV_TECH_001", "Advanced Technology", 
            AdvancedAlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM, 3);
        System.out.println("Advanced technology project started: " + techProjectStarted);
        
        // Update technology progress
        boolean techProgressUpdated = system.updateProjectProgress("ADV_TECH_001", 200);
        System.out.println("Advanced technology progress updated: " + techProgressUpdated);
        
        // Discover alien technology
        boolean alienTechDiscovered = system.discoverAlienTechnology("ADV_ALIEN_TECH_001", "Advanced Alien Tech", 
            AdvancedAlienResearchTechnologySystem.TechnologyCategory.WEAPON,
            AdvancedAlienResearchTechnologySystem.TechnologyLevel.ALIEN_TECH);
        System.out.println("Advanced alien technology discovered: " + alienTechDiscovered);
        
        // Analyze alien technology
        boolean alienTechAnalyzed = system.analyzeAlienTechnology("ADV_ALIEN_TECH_001");
        System.out.println("Advanced alien technology analyzed: " + alienTechAnalyzed);
        
        // Create adaptive threat
        boolean threatCreated = system.createAdaptiveThreat("ADV_THREAT_001", "Advanced Threat", 
            AdvancedAlienResearchTechnologySystem.ThreatType.ALIEN_SPECIALIST, 100, 50);
        System.out.println("Advanced threat created: " + threatCreated);
        
        // Encounter threat
        boolean threatEncountered = system.encounterThreat("ADV_THREAT_001");
        System.out.println("Advanced threat encountered: " + threatEncountered);
        
        // Evolve threat
        boolean threatEvolved = system.evolveThreat("ADV_THREAT_001");
        System.out.println("Advanced threat evolved: " + threatEvolved);
        
        // Test countermeasure
        boolean countermeasureTested = system.testCountermeasure("ADV_THREAT_001", "COUNTER_001");
        System.out.println("Advanced countermeasure tested: " + countermeasureTested);
        
        // Check research completion
        boolean researchCompleted = system.isResearchCompleted("ADV_RESEARCH_001");
        System.out.println("Advanced research completed: " + researchCompleted);
        
        // Check technology understanding
        boolean techUnderstood = system.isTechnologyUnderstood("ADV_ALIEN_TECH_001");
        System.out.println("Advanced technology understood: " + techUnderstood);
        
        // Check technology replicability
        boolean techReplicable = system.isTechnologyReplicable("ADV_ALIEN_TECH_001");
        System.out.println("Advanced technology replicable: " + techReplicable);
        
        // Get threat power
        int threatPower = system.getThreatPower("ADV_THREAT_001");
        System.out.println("Advanced threat power: " + threatPower);
    }
    
    private static void testIntegration() {
        System.out.println("\n--- Testing Integration ---");
        
        // Create managers
        AlienResearchManager researchManager = new AlienResearchManager();
        AlienTechnologyManager technologyManager = new AlienTechnologyManager();
        
        // Create research team
        List<String> researchers = Arrays.asList("Dr. Integration", "Dr. Test", "Dr. System");
        researchManager.createResearchTeam("INTEGRATION_TEAM", "Integration Test Team", researchers);
        
        // Start integrated research project
        boolean researchStarted = researchManager.startResearchProject(
            "INTEGRATION_RESEARCH", 
            "Integrated Research Project", 
            AdvancedAlienResearchTechnologySystem.ResearchCategory.WEAPON_TECHNOLOGY,
            AdvancedAlienResearchTechnologySystem.ResearchType.ADVANCED_RESEARCH,
            2, 
            "INTEGRATION_TEAM"
        );
        System.out.println("Integrated research started: " + researchStarted);
        
        // Start integrated technology project
        boolean techStarted = technologyManager.startTechnologyProject(
            "INTEGRATION_TECH", 
            "Integrated Technology Project", 
            AdvancedAlienResearchTechnologySystem.TechnologyType.WEAPON_SYSTEM,
            2, 
            "INTEGRATION_ENGINEER_TEAM"
        );
        System.out.println("Integrated technology started: " + techStarted);
        
        // Update both projects
        researchManager.updateResearchProgress("INTEGRATION_RESEARCH", 100, "Dr. Integration");
        technologyManager.updateTechnologyProgress("INTEGRATION_TECH", 150, "Engineer Integration");
        
        // Complete both projects
        researchManager.completeResearchProject("INTEGRATION_RESEARCH");
        technologyManager.completeTechnologyProject("INTEGRATION_TECH");
        
        // Get statistics from both managers
        AlienResearchManager.ResearchStatistics researchStats = researchManager.getResearchStatistics();
        AlienTechnologyManager.TechnologyStatistics techStats = technologyManager.getTechnologyStatistics();
        
        System.out.println("Integration test results:");
        System.out.println("  Research completion rate: " + (researchStats.getCompletionRate() * 100) + "%");
        System.out.println("  Technology completion rate: " + (techStats.getCompletionRate() * 100) + "%");
        System.out.println("  Research points available: " + researchStats.getAvailableResearchPoints());
        System.out.println("  Technology points available: " + techStats.getAvailableTechnologyPoints());
        
        System.out.println("Integration test completed successfully!");
    }
}

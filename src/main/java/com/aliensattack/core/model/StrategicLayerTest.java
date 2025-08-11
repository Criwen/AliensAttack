package com.aliensattack.core.model;

import java.util.*;

/**
 * Strategic Layer Test - Test class for Strategic Layer System
 * Tests the functionality of strategic layer, mission planning, and R&D systems
 */
public class StrategicLayerTest {

    public static void main(String[] args) {
        System.out.println("=== Strategic Layer System Test ===");

        // Test Strategic Layer Manager
        testStrategicLayerManager();

        // Test Mission Planning Manager
        testMissionPlanningManager();

        // Test Research Development Manager
        testResearchDevelopmentManager();

        // Test Strategic Layer Factory
        testStrategicLayerFactory();

        // Test Integration
        testIntegration();

        System.out.println("=== All tests completed ===");
    }

    private static void testStrategicLayerManager() {
        System.out.println("\n--- Testing Strategic Layer Manager ---");

        StrategicLayerManager strategicManager = new StrategicLayerManager();

        // Initialize strategic state
        boolean stateInitialized = strategicManager.initializeStrategicState("STATE_001", "Global Strategic State");
        System.out.println("Strategic state initialized: " + stateInitialized);

        // Add strategic resource
        boolean resourceAdded = strategicManager.addStrategicResource("SUPPLIES_001", "Global Supplies",
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.SUPPLIES, 1000, 10);
        System.out.println("Strategic resource added: " + resourceAdded);

        // Allocate strategic resource
        boolean resourceAllocated = strategicManager.allocateStrategicResource("SUPPLIES_001", "MISSION_001", 100,
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.AllocationType.MILITARY);
        System.out.println("Strategic resource allocated: " + resourceAllocated);

        // Process tactical impact
        List<String> affectedSystems = Arrays.asList("COMBAT", "RESEARCH", "BASE");
        boolean impactProcessed = strategicManager.processTacticalImpact("MISSION_001",
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ImpactType.POSITIVE, 50, affectedSystems);
        System.out.println("Tactical impact processed: " + impactProcessed);

        // Add strategic consequence
        List<String> affectedSystems2 = Arrays.asList("THREAT_LEVEL", "RESOURCES");
        boolean consequenceAdded = strategicManager.addStrategicConsequence("MISSION_001",
            AdvancedStrategicLayerIntegrationSystem.StrategicConsequence.ConsequenceType.POSITIVE_OUTCOME, 25, affectedSystems2);
        System.out.println("Strategic consequence added: " + consequenceAdded);

        // Make strategic decision
        List<String> consequences = Arrays.asList("Increase threat level", "Reduce resources");
        boolean decisionMade = strategicManager.makeStrategicDecision("Launch offensive campaign",
            AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.LAUNCH_MISSION, 200, consequences);
        System.out.println("Strategic decision made: " + decisionMade);

        // Add base facility
        boolean facilityAdded = strategicManager.addBaseFacility("FACILITY_001", "Advanced Research Lab",
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.RESEARCH_LAB, 3, 50);
        System.out.println("Base facility added: " + facilityAdded);

        // Add global threat
        boolean threatAdded = strategicManager.addGlobalThreat("THREAT_001", "Alien Invasion", 5, 75);
        System.out.println("Global threat added: " + threatAdded);

        // Gather intel
        List<String> applications = Arrays.asList("Combat", "Research", "Strategy");
        boolean intelGathered = strategicManager.gatherIntel("MISSION_001",
            AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType.ALIEN_TECHNOLOGY, 100, applications);
        System.out.println("Intel gathered: " + intelGathered);

        // Get statistics
        StrategicLayerManager.StrategicStatistics stats = strategicManager.getStrategicStatistics();
        System.out.println("Strategic statistics:");
        System.out.println("  Total projects: " + stats.getTotalProjects());
        System.out.println("  Total resources: " + stats.getTotalResources());
        System.out.println("  Total decisions: " + stats.getTotalDecisions());
        System.out.println("  Total facilities: " + stats.getTotalFacilities());
        System.out.println("  Total threats: " + stats.getTotalThreats());
        System.out.println("  Total intel: " + stats.getTotalIntel());
        System.out.println("  Strategic points usage: " + (stats.getStrategicPointsUsage() * 100) + "%");
        System.out.println("  Available strategic points: " + stats.getAvailableStrategicPoints());
    }

    private static void testMissionPlanningManager() {
        System.out.println("\n--- Testing Mission Planning Manager ---");

        MissionPlanningManager planningManager = new MissionPlanningManager();

        // Create mission briefing
        boolean briefingCreated = planningManager.createMissionBriefing("MISSION_001", "Reconnaissance Mission", "Reconnaissance");
        System.out.println("Mission briefing created: " + briefingCreated);

        // Create mission plan
        boolean planCreated = planningManager.createMissionPlan("MISSION_001", "Stealth Approach", "Stealth");
        System.out.println("Mission plan created: " + planCreated);

        // Create equipment loadout
        boolean loadoutCreated = planningManager.createEquipmentLoadout("LOADOUT_001", "Stealth Loadout", "SOLDIER_001");
        System.out.println("Equipment loadout created: " + loadoutCreated);

        // Create soldier selection
        boolean soldierSelected = planningManager.createSoldierSelection("SELECTION_001", "SOLDIER_001", "MISSION_001");
        System.out.println("Soldier selected: " + soldierSelected);

        // Update preparation progress
        boolean progressUpdated = planningManager.updatePreparationProgress("MISSION_001", 25);
        System.out.println("Preparation progress updated: " + progressUpdated);

        // Add preparation bonus
        boolean bonusAdded = planningManager.addPreparationBonus("BONUS_001", "Stealth Training", 
            "Improved stealth capabilities", "STEALTH", 5);
        System.out.println("Preparation bonus added: " + bonusAdded);

        // Get preparation progress
        int progress = planningManager.getPreparationProgress("MISSION_001");
        System.out.println("Preparation progress: " + progress + "%");

        // Check if preparation is complete
        boolean isComplete = planningManager.isPreparationComplete("MISSION_001");
        System.out.println("Preparation complete: " + isComplete);

        // Get statistics
        MissionPlanningManager.MissionPlanningStatistics stats = planningManager.getMissionPlanningStatistics();
        System.out.println("Mission planning statistics:");
        System.out.println("  Total plans: " + stats.getTotalPlans());
        System.out.println("  Total briefings: " + stats.getTotalBriefings());
        System.out.println("  Total loadouts: " + stats.getTotalLoadouts());
        System.out.println("  Total selections: " + stats.getTotalSelections());
        System.out.println("  Total bonuses: " + stats.getTotalBonuses());
        System.out.println("  Total events: " + stats.getTotalEvents());
        System.out.println("  Completed preparations: " + stats.getCompletedPreparations());
        System.out.println("  Planning points usage: " + (stats.getPlanningPointsUsage() * 100) + "%");
        System.out.println("  Completion rate: " + (stats.getCompletionRate() * 100) + "%");
        System.out.println("  Available planning points: " + stats.getAvailablePlanningPoints());
    }

    private static void testResearchDevelopmentManager() {
        System.out.println("\n--- Testing Research Development Manager ---");

        ResearchDevelopmentManager rdManager = new ResearchDevelopmentManager();

        // Start research project
        boolean researchStarted = rdManager.startResearchProject("RESEARCH_001", "Alien Technology Analysis", 
            "ALIEN_TECHNOLOGY", "ADVANCED", 3);
        System.out.println("Research project started: " + researchStarted);

        // Start development project
        boolean developmentStarted = rdManager.startDevelopmentProject("DEV_001", "Plasma Weapon Development", 
            "ALIEN_WEAPON", "EXPERIMENTAL", 4);
        System.out.println("Development project started: " + developmentStarted);

        // Update research progress
        boolean researchProgressUpdated = rdManager.updateResearchProgress("RESEARCH_001", 50, "Dr. Smith");
        System.out.println("Research progress updated: " + researchProgressUpdated);

        // Update development progress
        boolean developmentProgressUpdated = rdManager.updateDevelopmentProgress("DEV_001", 75, "Engineer Johnson");
        System.out.println("Development progress updated: " + developmentProgressUpdated);

        // Complete research project
        boolean researchCompleted = rdManager.completeResearchProject("RESEARCH_001");
        System.out.println("Research project completed: " + researchCompleted);

        // Complete development project
        boolean developmentCompleted = rdManager.completeDevelopmentProject("DEV_001");
        System.out.println("Development project completed: " + developmentCompleted);

        // Create research team
        List<String> researchers = Arrays.asList("Dr. Einstein", "Dr. Tesla", "Dr. Curie");
        boolean teamCreated = rdManager.createResearchTeam("TEAM_001", "Elite Research Team", researchers);
        System.out.println("Research team created: " + teamCreated);

        // Create research facility
        boolean facilityCreated = rdManager.createResearchFacility("FACILITY_001", "Advanced Research Lab", 
            "RESEARCH", 3, 50);
        System.out.println("Research facility created: " + facilityCreated);

        // Create research collaboration
        boolean collaborationCreated = rdManager.createResearchCollaboration("COLLAB_001", "RESEARCH_002", 
            "TEAM_001", "FACILITY_001");
        System.out.println("Research collaboration created: " + collaborationCreated);

        // Get statistics
        ResearchDevelopmentManager.RDStatistics stats = rdManager.getRDStatistics();
        System.out.println("R&D statistics:");
        System.out.println("  Total research projects: " + stats.getTotalResearchProjects());
        System.out.println("  Total development projects: " + stats.getTotalDevelopmentProjects());
        System.out.println("  Total teams: " + stats.getTotalTeams());
        System.out.println("  Total facilities: " + stats.getTotalFacilities());
        System.out.println("  Total breakthroughs: " + stats.getTotalBreakthroughs());
        System.out.println("  Total patents: " + stats.getTotalPatents());
        System.out.println("  Total collaborations: " + stats.getTotalCollaborations());
        System.out.println("  Completed research projects: " + stats.getCompletedResearchProjects());
        System.out.println("  Completed development projects: " + stats.getCompletedDevelopmentProjects());
        System.out.println("  RD points usage: " + (stats.getRDPointsUsage() * 100) + "%");
        System.out.println("  Research completion rate: " + (stats.getResearchCompletionRate() * 100) + "%");
        System.out.println("  Development completion rate: " + (stats.getDevelopmentCompletionRate() * 100) + "%");
        System.out.println("  Available RD points: " + stats.getAvailableRDPoints());
    }

    private static void testStrategicLayerFactory() {
        System.out.println("\n--- Testing Strategic Layer Factory ---");

        // Create strategic project
        StrategicLayerManager.StrategicProject project = StrategicLayerFactory.createStrategicProject(
            "PROJECT_001", "Global Defense Initiative", "A comprehensive defense program", 500);
        System.out.println("Strategic project created: " + project.getName());

        // Create strategic resource
        StrategicLayerManager.StrategicResource resource = StrategicLayerFactory.createSuppliesResource(
            "SUPPLIES_001", "Global Supplies", 1000, 10);
        System.out.println("Strategic resource created: " + resource.getName());

        // Create strategic decision
        List<String> consequences = Arrays.asList("Increase threat level", "Reduce resources");
        StrategicLayerManager.StrategicDecision decision = StrategicLayerFactory.createLaunchMissionDecision(
            "Launch offensive campaign", 200, consequences);
        System.out.println("Strategic decision created: " + decision.getDescription());

        // Create base facility
        StrategicLayerManager.BaseFacility facility = StrategicLayerFactory.createResearchLab(
            "LAB_001", "Advanced Research Lab", 3, 50);
        System.out.println("Base facility created: " + facility.getName());

        // Create global threat
        StrategicLayerManager.GlobalThreat threat = StrategicLayerFactory.createGlobalThreat(
            "THREAT_001", "Alien Invasion", 5, 75);
        System.out.println("Global threat created: " + threat.getName());

        // Create intel gathering
        List<String> applications = Arrays.asList("Combat", "Research", "Strategy");
        StrategicLayerManager.IntelGathering intel = StrategicLayerFactory.createAlienTechnologyIntel(
            "MISSION_001", 100, applications);
        System.out.println("Intel gathering created: " + intel.getType());

        // Create random components
        StrategicLayerManager.StrategicProject randomProject = StrategicLayerFactory.createRandomStrategicProject("RANDOM_PROJECT_001");
        System.out.println("Random strategic project created: " + randomProject.getName());

        StrategicLayerManager.StrategicResource randomResource = StrategicLayerFactory.createRandomStrategicResource("RANDOM_RESOURCE_001");
        System.out.println("Random strategic resource created: " + randomResource.getName());

        StrategicLayerManager.BaseFacility randomFacility = StrategicLayerFactory.createRandomBaseFacility("RANDOM_FACILITY_001");
        System.out.println("Random base facility created: " + randomFacility.getName());

        StrategicLayerManager.GlobalThreat randomThreat = StrategicLayerFactory.createRandomGlobalThreat("RANDOM_THREAT_001");
        System.out.println("Random global threat created: " + randomThreat.getName());

        // Get all types
        List<AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType> resourceTypes = StrategicLayerFactory.getAllResourceTypes();
        System.out.println("Resource types count: " + resourceTypes.size());

        List<AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType> facilityTypes = StrategicLayerFactory.getAllFacilityTypes();
        System.out.println("Facility types count: " + facilityTypes.size());

        List<AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType> decisionTypes = StrategicLayerFactory.getAllDecisionTypes();
        System.out.println("Decision types count: " + decisionTypes.size());

        List<AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType> intelTypes = StrategicLayerFactory.getAllIntelTypes();
        System.out.println("Intel types count: " + intelTypes.size());
    }

    private static void testIntegration() {
        System.out.println("\n--- Testing Integration ---");

        // Create managers
        StrategicLayerManager strategicManager = new StrategicLayerManager();
        MissionPlanningManager planningManager = new MissionPlanningManager();
        ResearchDevelopmentManager rdManager = new ResearchDevelopmentManager();

        // Initialize strategic state
        strategicManager.initializeStrategicState("INTEGRATION_STATE", "Integration Test State");

        // Add strategic resource
        strategicManager.addStrategicResource("INTEGRATION_SUPPLIES", "Integration Supplies",
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.SUPPLIES, 500, 5);

        // Create mission briefing
        planningManager.createMissionBriefing("INTEGRATION_MISSION", "Integration Mission", "Test");

        // Start research project
        rdManager.startResearchProject("INTEGRATION_RESEARCH", "Integration Research", "TEST", "BASIC", 2);

        // Process tactical impact
        List<String> affectedSystems = Arrays.asList("TEST_SYSTEM");
        strategicManager.processTacticalImpact("INTEGRATION_MISSION",
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ImpactType.POSITIVE, 25, affectedSystems);

        // Update preparation progress
        planningManager.updatePreparationProgress("INTEGRATION_MISSION", 50);

        // Update research progress
        rdManager.updateResearchProgress("INTEGRATION_RESEARCH", 100, "Integration Researcher");

        // Get statistics from all managers
        StrategicLayerManager.StrategicStatistics strategicStats = strategicManager.getStrategicStatistics();
        MissionPlanningManager.MissionPlanningStatistics planningStats = planningManager.getMissionPlanningStatistics();
        ResearchDevelopmentManager.RDStatistics rdStats = rdManager.getRDStatistics();

        System.out.println("Integration test results:");
        System.out.println("  Strategic points usage: " + (strategicStats.getStrategicPointsUsage() * 100) + "%");
        System.out.println("  Planning points usage: " + (planningStats.getPlanningPointsUsage() * 100) + "%");
        System.out.println("  RD points usage: " + (rdStats.getRDPointsUsage() * 100) + "%");
        System.out.println("  Strategic resources: " + strategicStats.getTotalResources());
        System.out.println("  Mission plans: " + planningStats.getTotalPlans());
        System.out.println("  Research projects: " + rdStats.getTotalResearchProjects());

        System.out.println("Integration test completed successfully!");
    }
}

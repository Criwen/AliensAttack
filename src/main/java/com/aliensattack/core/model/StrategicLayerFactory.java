package com.aliensattack.core.model;

import java.util.*;


/**
 * Strategic Layer Factory - Factory class for creating strategic layer components
 * Provides standardized creation methods for strategic-related objects
 */
public class StrategicLayerFactory {

    private static final Random random = new Random();

    /**
     * Create a strategic project
     */
    public static StrategicLayerManager.StrategicProject createStrategicProject(
            String projectId, String name, String description, int requiredProgress) {

        return new StrategicLayerManager.StrategicProject(projectId, name, description, requiredProgress);
    }

    /**
     * Create a strategic resource
     */
    public static StrategicLayerManager.StrategicResource createStrategicResource(
            String resourceId, String name,
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType type,
            int maxAmount, int regenerationRate) {

        return new StrategicLayerManager.StrategicResource(resourceId, name, type, maxAmount, regenerationRate);
    }

    /**
     * Create a strategic decision
     */
    public static StrategicLayerManager.StrategicDecision createStrategicDecision(
            String description, AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType type,
            int cost, List<String> consequences) {

        return new StrategicLayerManager.StrategicDecision(description, type, cost, consequences);
    }

    /**
     * Create a base facility
     */
    public static StrategicLayerManager.BaseFacility createBaseFacility(
            String facilityId, String name,
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType type,
            int level, int capacity) {

        return new StrategicLayerManager.BaseFacility(facilityId, name, type, level, capacity);
    }

    /**
     * Create a global threat
     */
    public static StrategicLayerManager.GlobalThreat createGlobalThreat(
            String threatId, String name, int level, int progression) {

        return new StrategicLayerManager.GlobalThreat(threatId, name, level, progression);
    }

    /**
     * Create an intel gathering
     */
    public static StrategicLayerManager.IntelGathering createIntelGathering(
            String source, AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType type,
            int value, List<String> applications) {

        return new StrategicLayerManager.IntelGathering(source, type, value, applications);
    }

    /**
     * Create a supplies resource
     */
    public static StrategicLayerManager.StrategicResource createSuppliesResource(
            String resourceId, String name, int maxAmount, int regenerationRate) {

        return createStrategicResource(resourceId, name,
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.SUPPLIES, maxAmount, regenerationRate);
    }

    /**
     * Create an alloys resource
     */
    public static StrategicLayerManager.StrategicResource createAlloysResource(
            String resourceId, String name, int maxAmount, int regenerationRate) {

        return createStrategicResource(resourceId, name,
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.MATERIALS, maxAmount, regenerationRate);
    }

    /**
     * Create an elerium resource
     */
    public static StrategicLayerManager.StrategicResource createEleriumResource(
            String resourceId, String name, int maxAmount, int regenerationRate) {

        return createStrategicResource(resourceId, name,
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.ELECTRONICS, maxAmount, regenerationRate);
    }

    /**
     * Create an intel resource
     */
    public static StrategicLayerManager.StrategicResource createIntelResource(
            String resourceId, String name, int maxAmount, int regenerationRate) {

        return createStrategicResource(resourceId, name,
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.ELECTRONICS, maxAmount, regenerationRate);
    }

    /**
     * Create a scientists resource
     */
    public static StrategicLayerManager.StrategicResource createScientistsResource(
            String resourceId, String name, int maxAmount, int regenerationRate) {

        return createStrategicResource(resourceId, name,
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.TECHNICAL_SUPPLIES, maxAmount, regenerationRate);
    }

    /**
     * Create an engineers resource
     */
    public static StrategicLayerManager.StrategicResource createEngineersResource(
            String resourceId, String name, int maxAmount, int regenerationRate) {

        return createStrategicResource(resourceId, name,
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.TECHNICAL_SUPPLIES, maxAmount, regenerationRate);
    }

    /**
     * Create a command center facility
     */
    public static StrategicLayerManager.BaseFacility createCommandCenter(
            String facilityId, String name, int level, int capacity) {

        return createBaseFacility(facilityId, name,
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.COMMAND_CENTER, level, capacity);
    }

    /**
     * Create a research lab facility
     */
    public static StrategicLayerManager.BaseFacility createResearchLab(
            String facilityId, String name, int level, int capacity) {

        return createBaseFacility(facilityId, name,
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.RESEARCH_LAB, level, capacity);
    }

    /**
     * Create an engineering workshop facility
     */
    public static StrategicLayerManager.BaseFacility createEngineeringWorkshop(
            String facilityId, String name, int level, int capacity) {

        return createBaseFacility(facilityId, name,
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.ENGINEERING_WORKSHOP, level, capacity);
    }

    /**
     * Create a medical bay facility
     */
    public static StrategicLayerManager.BaseFacility createMedicalBay(
            String facilityId, String name, int level, int capacity) {

        return createBaseFacility(facilityId, name,
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.MEDICAL_BAY, level, capacity);
    }

    /**
     * Create a training facility
     */
    public static StrategicLayerManager.BaseFacility createTrainingFacility(
            String facilityId, String name, int level, int capacity) {

        return createBaseFacility(facilityId, name,
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.TRAINING_FACILITY, level, capacity);
    }

    /**
     * Create a defense turret facility
     */
    public static StrategicLayerManager.BaseFacility createDefenseTurret(
            String facilityId, String name, int level, int capacity) {

        return createBaseFacility(facilityId, name,
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.DEFENSE_TURRET, level, capacity);
    }

    /**
     * Create a power generator facility
     */
    public static StrategicLayerManager.BaseFacility createPowerGenerator(
            String facilityId, String name, int level, int capacity) {

        return createBaseFacility(facilityId, name,
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.POWER_GENERATOR, level, capacity);
    }

    /**
     * Create a storage depot facility
     */
    public static StrategicLayerManager.BaseFacility createStorageDepot(
            String facilityId, String name, int level, int capacity) {

        return createBaseFacility(facilityId, name,
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.STORAGE_DEPOT, level, capacity);
    }

    /**
     * Create a build facility decision
     */
    public static StrategicLayerManager.StrategicDecision createBuildFacilityDecision(
            String description, int cost, List<String> consequences) {

        return createStrategicDecision(description,
            AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.BUILD_FACILITY, cost, consequences);
    }

    /**
     * Create a research technology decision
     */
    public static StrategicLayerManager.StrategicDecision createResearchTechnologyDecision(
            String description, int cost, List<String> consequences) {

        return createStrategicDecision(description,
            AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.RESEARCH_TECHNOLOGY, cost, consequences);
    }

    /**
     * Create a train soldiers decision
     */
    public static StrategicLayerManager.StrategicDecision createTrainSoldiersDecision(
            String description, int cost, List<String> consequences) {

        return createStrategicDecision(description,
            AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.TRAIN_SOLDIERS, cost, consequences);
    }

    /**
     * Create a launch mission decision
     */
    public static StrategicLayerManager.StrategicDecision createLaunchMissionDecision(
            String description, int cost, List<String> consequences) {

        return createStrategicDecision(description,
            AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.LAUNCH_MISSION, cost, consequences);
    }

    /**
     * Create an upgrade base decision
     */
    public static StrategicLayerManager.StrategicDecision createUpgradeBaseDecision(
            String description, int cost, List<String> consequences) {

        return createStrategicDecision(description,
            AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.UPGRADE_BASE, cost, consequences);
    }

    /**
     * Create an allocate resources decision
     */
    public static StrategicLayerManager.StrategicDecision createAllocateResourcesDecision(
            String description, int cost, List<String> consequences) {

        return createStrategicDecision(description,
            AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.ALLOCATE_RESOURCES, cost, consequences);
    }

    /**
     * Create a form alliance decision
     */
    public static StrategicLayerManager.StrategicDecision createFormAllianceDecision(
            String description, int cost, List<String> consequences) {

        return createStrategicDecision(description,
            AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.FORM_ALLIANCE, cost, consequences);
    }

    /**
     * Create a declare war decision
     */
    public static StrategicLayerManager.StrategicDecision createDeclareWarDecision(
            String description, int cost, List<String> consequences) {

        return createStrategicDecision(description,
            AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.DECLARE_WAR, cost, consequences);
    }

    /**
     * Create alien technology intel
     */
    public static StrategicLayerManager.IntelGathering createAlienTechnologyIntel(
            String source, int value, List<String> applications) {

        return createIntelGathering(source,
            AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType.ALIEN_TECHNOLOGY, value, applications);
    }

    /**
     * Create alien strategy intel
     */
    public static StrategicLayerManager.IntelGathering createAlienStrategyIntel(
            String source, int value, List<String> applications) {

        return createIntelGathering(source,
            AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType.ALIEN_STRATEGY, value, applications);
    }

    /**
     * Create threat assessment intel
     */
    public static StrategicLayerManager.IntelGathering createThreatAssessmentIntel(
            String source, int value, List<String> applications) {

        return createIntelGathering(source,
            AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType.THREAT_ASSESSMENT, value, applications);
    }

    /**
     * Create resource location intel
     */
    public static StrategicLayerManager.IntelGathering createResourceLocationIntel(
            String source, int value, List<String> applications) {

        return createIntelGathering(source,
            AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType.RESOURCE_LOCATION, value, applications);
    }

    /**
     * Create base location intel
     */
    public static StrategicLayerManager.IntelGathering createBaseLocationIntel(
            String source, int value, List<String> applications) {

        return createIntelGathering(source,
            AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType.BASE_LOCATION, value, applications);
    }

    /**
     * Create weakness analysis intel
     */
    public static StrategicLayerManager.IntelGathering createWeaknessAnalysisIntel(
            String source, int value, List<String> applications) {

        return createIntelGathering(source,
            AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType.WEAKNESS_ANALYSIS, value, applications);
    }

    /**
     * Create strength analysis intel
     */
    public static StrategicLayerManager.IntelGathering createStrengthAnalysisIntel(
            String source, int value, List<String> applications) {

        return createIntelGathering(source,
            AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType.STRENGTH_ANALYSIS, value, applications);
    }

    /**
     * Create a random strategic project
     */
    public static StrategicLayerManager.StrategicProject createRandomStrategicProject(String projectId) {
        String[] names = {"Strategic Initiative", "Global Operation", "Defense Program", "Offensive Campaign"};
        String[] descriptions = {"A strategic initiative to improve global position", 
                               "A global operation to gather intelligence",
                               "A defense program to protect assets",
                               "An offensive campaign to weaken enemies"};

        String name = names[random.nextInt(names.length)];
        String description = descriptions[random.nextInt(descriptions.length)];
        int requiredProgress = random.nextInt(500) + 100;

        return createStrategicProject(projectId, name, description, requiredProgress);
    }

    /**
     * Create a random strategic resource
     */
    public static StrategicLayerManager.StrategicResource createRandomStrategicResource(String resourceId) {
        AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType[] types = 
            AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.values();
        
        AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType type = types[random.nextInt(types.length)];
        String name = type.name().toLowerCase() + "_resource";
        int maxAmount = random.nextInt(1000) + 100;
        int regenerationRate = random.nextInt(10) + 1;

        return createStrategicResource(resourceId, name, type, maxAmount, regenerationRate);
    }

    /**
     * Create a random base facility
     */
    public static StrategicLayerManager.BaseFacility createRandomBaseFacility(String facilityId) {
        AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType[] types = 
            AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.values();
        
        AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType type = types[random.nextInt(types.length)];
        String name = type.name().toLowerCase().replace("_", " ") + " facility";
        int level = random.nextInt(5) + 1;
        int capacity = random.nextInt(100) + 10;

        return createBaseFacility(facilityId, name, type, level, capacity);
    }

    /**
     * Create a random global threat
     */
    public static StrategicLayerManager.GlobalThreat createRandomGlobalThreat(String threatId) {
        String[] names = {"Alien Invasion", "Psychic Attack", "Biological Warfare", 
                         "Technological Surge", "Resource Scarcity", "Morale Crisis"};
        
        String name = names[random.nextInt(names.length)];
        int level = random.nextInt(10) + 1;
        int progression = random.nextInt(100);

        return createGlobalThreat(threatId, name, level, progression);
    }

    /**
     * Get all resource types
     */
    public static List<AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType> getAllResourceTypes() {
        return Arrays.asList(AdvancedStrategicLayerIntegrationSystem.StrategicResource.ResourceType.values());
    }

    /**
     * Get all facility types
     */
    public static List<AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType> getAllFacilityTypes() {
        return Arrays.asList(AdvancedStrategicLayerIntegrationSystem.ManufacturingFacility.FacilityType.values());
    }

    /**
     * Get all decision types
     */
    public static List<AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType> getAllDecisionTypes() {
        return Arrays.asList(AdvancedStrategicLayerIntegrationSystem.StrategicDecision.DecisionType.values());
    }

    /**
     * Get all intel types
     */
    public static List<AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType> getAllIntelTypes() {
        return Arrays.asList(AdvancedStrategicLayerIntegrationSystem.IntelReport.IntelType.values());
    }
}

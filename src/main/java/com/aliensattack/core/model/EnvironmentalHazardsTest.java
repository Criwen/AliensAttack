package com.aliensattack.core.model;

/**
 * Test class for Environmental Hazards System
 * Demonstrates the functionality of environmental hazards, weather effects, and chain reactions
 */
public class EnvironmentalHazardsTest {
    
    public static void main(String[] args) {
        System.out.println("=== Environmental Hazards System Test ===");
        
        // Test environmental hazards manager
        testEnvironmentalHazardsManager();
        
        // Test factory methods
        testEnvironmentalHazardsFactory();
        
        // Test integration with units
        testUnitIntegration();
        
        System.out.println("=== Environmental Hazards System Test Complete ===");
    }
    
    private static void testEnvironmentalHazardsManager() {
        System.out.println("\n--- Testing Environmental Hazards Manager ---");
        
        EnvironmentalHazardsManager manager = new EnvironmentalHazardsManager();
        
        // Create test positions
        Position hazardPos = new Position(10, 10);
        Position weatherPos = new Position(15, 15);
        Position reactionPos = new Position(20, 20);
        
        // Test creating environmental hazards
        boolean fireHazardCreated = manager.createEnvironmentalHazard(
            "test_fire_hazard", 
            EnvironmentalHazardsManager.HazardType.FIRE_HAZARD, 
            hazardPos, 5, 30, 10
        );
        System.out.println("Fire hazard created: " + fireHazardCreated);
        
        // Test creating weather effects
        boolean weatherEffectCreated = manager.createWeatherEffect(
            "test_visibility_weather", 
            EnvironmentalHazardsManager.WeatherEffectType.VISIBILITY_MODIFIER, 
            weatherPos, 8, 25, 15
        );
        System.out.println("Weather effect created: " + weatherEffectCreated);
        
        // Test creating chain reactions
        boolean chainReactionCreated = manager.createChainReaction(
            "test_explosive_chain", 
            EnvironmentalHazardsManager.ChainReactionType.EXPLOSIVE_CHAIN, 
            reactionPos, 6, 40, 8
        );
        System.out.println("Chain reaction created: " + chainReactionCreated);
        
        // Test processing effects
        manager.processEnvironmentalEffects();
        System.out.println("Environmental effects processed");
        
        // Test removing effects
        boolean fireHazardRemoved = manager.removeEnvironmentalHazard("test_fire_hazard");
        System.out.println("Fire hazard removed: " + fireHazardRemoved);
        
        boolean weatherEffectRemoved = manager.removeWeatherEffect("test_visibility_weather");
        System.out.println("Weather effect removed: " + weatherEffectRemoved);
        
        boolean chainReactionRemoved = manager.removeChainReaction("test_explosive_chain");
        System.out.println("Chain reaction removed: " + chainReactionRemoved);
    }
    
    private static void testEnvironmentalHazardsFactory() {
        System.out.println("\n--- Testing Environmental Hazards Factory ---");
        
        Position testPos = new Position(5, 5);
        
        // Test creating specific hazards
        EnvironmentalHazardsManager.EnvironmentalHazard fireHazard = 
            EnvironmentalHazardsFactory.createFireHazard(testPos, 5, 30, 10);
        System.out.println("Fire hazard created via factory: " + fireHazard.getHazardId());
        
        EnvironmentalHazardsManager.EnvironmentalHazard toxicHazard = 
            EnvironmentalHazardsFactory.createToxicHazard(testPos, 4, 25, 12);
        System.out.println("Toxic hazard created via factory: " + toxicHazard.getHazardId());
        
        EnvironmentalHazardsManager.EnvironmentalHazard electricalHazard = 
            EnvironmentalHazardsFactory.createElectricalHazard(testPos, 6, 35, 8);
        System.out.println("Electrical hazard created via factory: " + electricalHazard.getHazardId());
        
        EnvironmentalHazardsManager.EnvironmentalHazard radiationHazard = 
            EnvironmentalHazardsFactory.createRadiationHazard(testPos, 7, 40, 15);
        System.out.println("Radiation hazard created via factory: " + radiationHazard.getHazardId());
        
        EnvironmentalHazardsManager.EnvironmentalHazard acidHazard = 
            EnvironmentalHazardsFactory.createAcidHazard(testPos, 3, 20, 6);
        System.out.println("Acid hazard created via factory: " + acidHazard.getHazardId());
        
        EnvironmentalHazardsManager.EnvironmentalHazard plasmaHazard = 
            EnvironmentalHazardsFactory.createPlasmaHazard(testPos, 8, 45, 20);
        System.out.println("Plasma hazard created via factory: " + plasmaHazard.getHazardId());
        
        // Test creating weather effects
        EnvironmentalHazardsManager.WeatherEffect visibilityWeather = 
            EnvironmentalHazardsFactory.createVisibilityWeatherEffect(testPos, 6, 30, 12);
        System.out.println("Visibility weather created via factory: " + visibilityWeather.getWeatherId());
        
        EnvironmentalHazardsManager.WeatherEffect movementWeather = 
            EnvironmentalHazardsFactory.createMovementWeatherEffect(testPos, 5, 25, 10);
        System.out.println("Movement weather created via factory: " + movementWeather.getWeatherId());
        
        EnvironmentalHazardsManager.WeatherEffect accuracyWeather = 
            EnvironmentalHazardsFactory.createAccuracyWeatherEffect(testPos, 4, 20, 8);
        System.out.println("Accuracy weather created via factory: " + accuracyWeather.getWeatherId());
        
        EnvironmentalHazardsManager.WeatherEffect damageWeather = 
            EnvironmentalHazardsFactory.createDamageWeatherEffect(testPos, 7, 35, 15);
        System.out.println("Damage weather created via factory: " + damageWeather.getWeatherId());
        
        EnvironmentalHazardsManager.WeatherEffect armorWeather = 
            EnvironmentalHazardsFactory.createArmorWeatherEffect(testPos, 6, 30, 12);
        System.out.println("Armor weather created via factory: " + armorWeather.getWeatherId());
        
        EnvironmentalHazardsManager.WeatherEffect equipmentWeather = 
            EnvironmentalHazardsFactory.createEquipmentWeatherEffect(testPos, 5, 25, 10);
        System.out.println("Equipment weather created via factory: " + equipmentWeather.getWeatherId());
        
        EnvironmentalHazardsManager.WeatherEffect psionicWeather = 
            EnvironmentalHazardsFactory.createPsionicWeatherEffect(testPos, 8, 40, 18);
        System.out.println("Psionic weather created via factory: " + psionicWeather.getWeatherId());
        
        EnvironmentalHazardsManager.WeatherEffect mutationWeather = 
            EnvironmentalHazardsFactory.createMutationRiskWeatherEffect(testPos, 9, 50, 25);
        System.out.println("Mutation weather created via factory: " + mutationWeather.getWeatherId());
        
        // Test creating chain reactions
        EnvironmentalHazardsManager.ChainReaction explosiveChain = 
            EnvironmentalHazardsFactory.createExplosiveChainReaction(testPos, 8, 50, 12);
        System.out.println("Explosive chain created via factory: " + explosiveChain.getReactionId());
        
        EnvironmentalHazardsManager.ChainReaction fireSpread = 
            EnvironmentalHazardsFactory.createFireSpreadChainReaction(testPos, 6, 35, 10);
        System.out.println("Fire spread created via factory: " + fireSpread.getReactionId());
        
        EnvironmentalHazardsManager.ChainReaction electricalArc = 
            EnvironmentalHazardsFactory.createElectricalArcChainReaction(testPos, 7, 40, 15);
        System.out.println("Electrical arc created via factory: " + electricalArc.getReactionId());
        
        EnvironmentalHazardsManager.ChainReaction chemicalReaction = 
            EnvironmentalHazardsFactory.createChemicalReactionChainReaction(testPos, 5, 30, 8);
        System.out.println("Chemical reaction created via factory: " + chemicalReaction.getReactionId());
        
        EnvironmentalHazardsManager.ChainReaction radiationSpread = 
            EnvironmentalHazardsFactory.createRadiationSpreadChainReaction(testPos, 9, 45, 20);
        System.out.println("Radiation spread created via factory: " + radiationSpread.getReactionId());
        
        EnvironmentalHazardsManager.ChainReaction plasmaCascade = 
            EnvironmentalHazardsFactory.createPlasmaCascadeChainReaction(testPos, 10, 60, 30);
        System.out.println("Plasma cascade created via factory: " + plasmaCascade.getReactionId());
        
        // Test random creation methods
        EnvironmentalHazardsManager.EnvironmentalHazard randomHazard = 
            EnvironmentalHazardsFactory.createRandomHazard(testPos, 5, 30, 10);
        System.out.println("Random hazard created: " + randomHazard.getHazardType());
        
        EnvironmentalHazardsManager.WeatherEffect randomWeather = 
            EnvironmentalHazardsFactory.createRandomWeatherEffect(testPos, 6, 25, 12);
        System.out.println("Random weather created: " + randomWeather.getWeatherType());
        
        EnvironmentalHazardsManager.ChainReaction randomReaction = 
            EnvironmentalHazardsFactory.createRandomChainReaction(testPos, 7, 35, 15);
        System.out.println("Random chain reaction created: " + randomReaction.getReactionType());
    }
    
    private static void testUnitIntegration() {
        System.out.println("\n--- Testing Unit Integration ---");
        
        // Create test units
        Unit testUnit1 = new Unit("Test Soldier 1", 100, 4, 6, 25, com.aliensattack.core.enums.UnitType.SOLDIER);
        Unit testUnit2 = new Unit("Test Soldier 2", 100, 4, 6, 25, com.aliensattack.core.enums.UnitType.SOLDIER);
        
        // Set positions
        testUnit1.setPosition(new Position(10, 10));
        testUnit2.setPosition(new Position(15, 15));
        
        // Create environmental hazards manager
        EnvironmentalHazardsManager manager = new EnvironmentalHazardsManager();
        
        // Create hazards near units
        Position hazardPos = new Position(12, 12); // Near testUnit1
        manager.createEnvironmentalHazard("test_hazard", 
            EnvironmentalHazardsManager.HazardType.FIRE_HAZARD, hazardPos, 5, 30, 10);
        
        // Apply hazards to units
        manager.applyEnvironmentalHazardsToUnit(testUnit1);
        manager.applyEnvironmentalHazardsToUnit(testUnit2);
        
        System.out.println("Unit 1 health after hazard: " + testUnit1.getCurrentHealth());
        System.out.println("Unit 2 health after hazard: " + testUnit2.getCurrentHealth());
        
        // Create weather effects
        Position weatherPos = new Position(13, 13);
        manager.createWeatherEffect("test_weather", 
            EnvironmentalHazardsManager.WeatherEffectType.VISIBILITY_MODIFIER, weatherPos, 8, 25, 15);
        
        // Apply weather effects to units
        manager.applyWeatherEffectsToUnit(testUnit1);
        manager.applyWeatherEffectsToUnit(testUnit2);
        
        System.out.println("Unit 1 view range after weather: " + testUnit1.getViewRange());
        System.out.println("Unit 2 view range after weather: " + testUnit2.getViewRange());
        
        // Create chain reactions
        Position reactionPos = new Position(14, 14);
        manager.createChainReaction("test_reaction", 
            EnvironmentalHazardsManager.ChainReactionType.EXPLOSIVE_CHAIN, reactionPos, 6, 40, 8);
        
        // Apply chain reactions to units
        manager.applyChainReactionsToUnit(testUnit1);
        manager.applyChainReactionsToUnit(testUnit2);
        
        System.out.println("Unit 1 health after chain reaction: " + testUnit1.getCurrentHealth());
        System.out.println("Unit 2 health after chain reaction: " + testUnit2.getCurrentHealth());
        
        // Process all environmental effects
        manager.processEnvironmentalEffects();
        System.out.println("All environmental effects processed");
    }
}

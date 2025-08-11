package com.aliensattack.core.model;

import java.util.Random;

/**
 * Factory class for creating environmental hazards, weather effects, and chain reactions
 */
public class EnvironmentalHazardsFactory {
    
    private static final Random random = new Random();
    
    /**
     * Create a fire hazard
     */
    public static EnvironmentalHazardsManager.EnvironmentalHazard createFireHazard(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.EnvironmentalHazard.builder()
            .hazardId("fire_hazard_" + System.currentTimeMillis())
            .hazardType(EnvironmentalHazardsManager.HazardType.FIRE_HAZARD)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a toxic hazard
     */
    public static EnvironmentalHazardsManager.EnvironmentalHazard createToxicHazard(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.EnvironmentalHazard.builder()
            .hazardId("toxic_hazard_" + System.currentTimeMillis())
            .hazardType(EnvironmentalHazardsManager.HazardType.TOXIC_HAZARD)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create an electrical hazard
     */
    public static EnvironmentalHazardsManager.EnvironmentalHazard createElectricalHazard(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.EnvironmentalHazard.builder()
            .hazardId("electrical_hazard_" + System.currentTimeMillis())
            .hazardType(EnvironmentalHazardsManager.HazardType.ELECTRICAL_HAZARD)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a radiation hazard
     */
    public static EnvironmentalHazardsManager.EnvironmentalHazard createRadiationHazard(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.EnvironmentalHazard.builder()
            .hazardId("radiation_hazard_" + System.currentTimeMillis())
            .hazardType(EnvironmentalHazardsManager.HazardType.RADIATION_HAZARD)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create an acid hazard
     */
    public static EnvironmentalHazardsManager.EnvironmentalHazard createAcidHazard(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.EnvironmentalHazard.builder()
            .hazardId("acid_hazard_" + System.currentTimeMillis())
            .hazardType(EnvironmentalHazardsManager.HazardType.ACID_HAZARD)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a plasma hazard
     */
    public static EnvironmentalHazardsManager.EnvironmentalHazard createPlasmaHazard(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.EnvironmentalHazard.builder()
            .hazardId("plasma_hazard_" + System.currentTimeMillis())
            .hazardType(EnvironmentalHazardsManager.HazardType.PLASMA_HAZARD)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a visibility weather effect
     */
    public static EnvironmentalHazardsManager.WeatherEffect createVisibilityWeatherEffect(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.WeatherEffect.builder()
            .weatherId("visibility_weather_" + System.currentTimeMillis())
            .weatherType(EnvironmentalHazardsManager.WeatherEffectType.VISIBILITY_MODIFIER)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a movement weather effect
     */
    public static EnvironmentalHazardsManager.WeatherEffect createMovementWeatherEffect(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.WeatherEffect.builder()
            .weatherId("movement_weather_" + System.currentTimeMillis())
            .weatherType(EnvironmentalHazardsManager.WeatherEffectType.MOVEMENT_MODIFIER)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create an accuracy weather effect
     */
    public static EnvironmentalHazardsManager.WeatherEffect createAccuracyWeatherEffect(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.WeatherEffect.builder()
            .weatherId("accuracy_weather_" + System.currentTimeMillis())
            .weatherType(EnvironmentalHazardsManager.WeatherEffectType.ACCURACY_MODIFIER)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a damage weather effect
     */
    public static EnvironmentalHazardsManager.WeatherEffect createDamageWeatherEffect(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.WeatherEffect.builder()
            .weatherId("damage_weather_" + System.currentTimeMillis())
            .weatherType(EnvironmentalHazardsManager.WeatherEffectType.DAMAGE_MODIFIER)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create an armor weather effect
     */
    public static EnvironmentalHazardsManager.WeatherEffect createArmorWeatherEffect(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.WeatherEffect.builder()
            .weatherId("armor_weather_" + System.currentTimeMillis())
            .weatherType(EnvironmentalHazardsManager.WeatherEffectType.ARMOR_MODIFIER)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create an equipment weather effect
     */
    public static EnvironmentalHazardsManager.WeatherEffect createEquipmentWeatherEffect(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.WeatherEffect.builder()
            .weatherId("equipment_weather_" + System.currentTimeMillis())
            .weatherType(EnvironmentalHazardsManager.WeatherEffectType.EQUIPMENT_MODIFIER)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a psionic weather effect
     */
    public static EnvironmentalHazardsManager.WeatherEffect createPsionicWeatherEffect(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.WeatherEffect.builder()
            .weatherId("psionic_weather_" + System.currentTimeMillis())
            .weatherType(EnvironmentalHazardsManager.WeatherEffectType.PSIONIC_MODIFIER)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a mutation risk weather effect
     */
    public static EnvironmentalHazardsManager.WeatherEffect createMutationRiskWeatherEffect(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.WeatherEffect.builder()
            .weatherId("mutation_weather_" + System.currentTimeMillis())
            .weatherType(EnvironmentalHazardsManager.WeatherEffectType.MUTATION_RISK)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create an explosive chain reaction
     */
    public static EnvironmentalHazardsManager.ChainReaction createExplosiveChainReaction(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.ChainReaction.builder()
            .reactionId("explosive_chain_" + System.currentTimeMillis())
            .reactionType(EnvironmentalHazardsManager.ChainReactionType.EXPLOSIVE_CHAIN)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a fire spread chain reaction
     */
    public static EnvironmentalHazardsManager.ChainReaction createFireSpreadChainReaction(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.ChainReaction.builder()
            .reactionId("fire_spread_" + System.currentTimeMillis())
            .reactionType(EnvironmentalHazardsManager.ChainReactionType.FIRE_SPREAD)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create an electrical arc chain reaction
     */
    public static EnvironmentalHazardsManager.ChainReaction createElectricalArcChainReaction(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.ChainReaction.builder()
            .reactionId("electrical_arc_" + System.currentTimeMillis())
            .reactionType(EnvironmentalHazardsManager.ChainReactionType.ELECTRICAL_ARC)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a chemical reaction chain reaction
     */
    public static EnvironmentalHazardsManager.ChainReaction createChemicalReactionChainReaction(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.ChainReaction.builder()
            .reactionId("chemical_reaction_" + System.currentTimeMillis())
            .reactionType(EnvironmentalHazardsManager.ChainReactionType.CHEMICAL_REACTION)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a radiation spread chain reaction
     */
    public static EnvironmentalHazardsManager.ChainReaction createRadiationSpreadChainReaction(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.ChainReaction.builder()
            .reactionId("radiation_spread_" + System.currentTimeMillis())
            .reactionType(EnvironmentalHazardsManager.ChainReactionType.RADIATION_SPREAD)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a plasma cascade chain reaction
     */
    public static EnvironmentalHazardsManager.ChainReaction createPlasmaCascadeChainReaction(Position position, int radius, int intensity, int duration) {
        return EnvironmentalHazardsManager.ChainReaction.builder()
            .reactionId("plasma_cascade_" + System.currentTimeMillis())
            .reactionType(EnvironmentalHazardsManager.ChainReactionType.PLASMA_CASCADE)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a random environmental hazard
     */
    public static EnvironmentalHazardsManager.EnvironmentalHazard createRandomHazard(Position position, int radius, int intensity, int duration) {
        EnvironmentalHazardsManager.HazardType[] types = EnvironmentalHazardsManager.HazardType.values();
        EnvironmentalHazardsManager.HazardType randomType = types[random.nextInt(types.length)];
        
        return EnvironmentalHazardsManager.EnvironmentalHazard.builder()
            .hazardId("random_hazard_" + System.currentTimeMillis())
            .hazardType(randomType)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a random weather effect
     */
    public static EnvironmentalHazardsManager.WeatherEffect createRandomWeatherEffect(Position position, int radius, int intensity, int duration) {
        EnvironmentalHazardsManager.WeatherEffectType[] types = EnvironmentalHazardsManager.WeatherEffectType.values();
        EnvironmentalHazardsManager.WeatherEffectType randomType = types[random.nextInt(types.length)];
        
        return EnvironmentalHazardsManager.WeatherEffect.builder()
            .weatherId("random_weather_" + System.currentTimeMillis())
            .weatherType(randomType)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
    
    /**
     * Create a random chain reaction
     */
    public static EnvironmentalHazardsManager.ChainReaction createRandomChainReaction(Position position, int radius, int intensity, int duration) {
        EnvironmentalHazardsManager.ChainReactionType[] types = EnvironmentalHazardsManager.ChainReactionType.values();
        EnvironmentalHazardsManager.ChainReactionType randomType = types[random.nextInt(types.length)];
        
        return EnvironmentalHazardsManager.ChainReaction.builder()
            .reactionId("random_reaction_" + System.currentTimeMillis())
            .reactionType(randomType)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
    }
}

package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.StatusEffect;
import java.util.*;

/**
 * Comprehensive Environmental Hazards Manager
 * Implements 6 types of environmental hazards, weather effects, and chain reactions
 */
public class EnvironmentalHazardsManager {
    
    // 6 Types of Environmental Hazards
    public enum HazardType {
        FIRE_HAZARD,        // Fire damage and burning effects
        TOXIC_HAZARD,       // Poison gas and toxic damage
        ELECTRICAL_HAZARD,  // Electrical damage and stunning
        RADIATION_HAZARD,   // Radiation damage and mutations
        ACID_HAZARD,        // Acid damage and corrosion
        PLASMA_HAZARD       // Plasma damage and psionic interference
    }
    
    // Weather Effects Types
    public enum WeatherEffectType {
        VISIBILITY_MODIFIER,    // Changes to visibility
        MOVEMENT_MODIFIER,      // Changes to movement
        ACCURACY_MODIFIER,      // Changes to accuracy
        DAMAGE_MODIFIER,        // Changes to damage
        ARMOR_MODIFIER,         // Changes to armor effectiveness
        EQUIPMENT_MODIFIER,     // Changes to equipment reliability
        PSIONIC_MODIFIER,       // Changes to psionic abilities
        MUTATION_RISK           // Risk of mutations
    }
    
    // Chain Reaction Types
    public enum ChainReactionType {
        EXPLOSIVE_CHAIN,    // Explosive chain reactions
        FIRE_SPREAD,        // Fire spreading
        ELECTRICAL_ARC,     // Electrical arcing
        CHEMICAL_REACTION,  // Chemical reactions
        RADIATION_SPREAD,   // Radiation spreading
        PLASMA_CASCADE      // Plasma cascade effects
    }
    
    private Map<String, EnvironmentalHazard> activeHazards;
    private Map<String, WeatherEffect> activeWeatherEffects;
    private Map<String, ChainReaction> activeChainReactions;
    private Map<String, List<String>> hazardAffectedUnits;
    private Map<String, List<String>> weatherAffectedUnits;
    private Map<String, List<String>> reactionAffectedUnits;
    private Map<String, Integer> hazardIntensities;
    private Map<String, Integer> weatherIntensities;
    private Map<String, Integer> reactionIntensities;
    
    public EnvironmentalHazardsManager() {
        this.activeHazards = new HashMap<>();
        this.activeWeatherEffects = new HashMap<>();
        this.activeChainReactions = new HashMap<>();
        this.hazardAffectedUnits = new HashMap<>();
        this.weatherAffectedUnits = new HashMap<>();
        this.reactionAffectedUnits = new HashMap<>();
        this.hazardIntensities = new HashMap<>();
        this.weatherIntensities = new HashMap<>();
        this.reactionIntensities = new HashMap<>();
    }
    
    /**
     * Create and activate environmental hazard
     */
    public boolean createEnvironmentalHazard(String hazardId, HazardType type, Position position, 
                                           int radius, int intensity, int duration) {
        EnvironmentalHazard hazard = EnvironmentalHazard.builder()
            .hazardId(hazardId)
            .hazardType(type)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
        
        activeHazards.put(hazardId, hazard);
        hazardIntensities.put(hazardId, intensity);
        hazardAffectedUnits.put(hazardId, new ArrayList<>());
        
        return true;
    }
    
    /**
     * Create and activate weather effect
     */
    public boolean createWeatherEffect(String weatherId, WeatherEffectType type, 
                                     Position position, int radius, int intensity, int duration) {
        WeatherEffect weather = WeatherEffect.builder()
            .weatherId(weatherId)
            .weatherType(type)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
        
        activeWeatherEffects.put(weatherId, weather);
        weatherIntensities.put(weatherId, intensity);
        weatherAffectedUnits.put(weatherId, new ArrayList<>());
        
        return true;
    }
    
    /**
     * Create and activate chain reaction
     */
    public boolean createChainReaction(String reactionId, ChainReactionType type,
                                     Position position, int radius, int intensity, int duration) {
        ChainReaction reaction = ChainReaction.builder()
            .reactionId(reactionId)
            .reactionType(type)
            .position(position)
            .radius(radius)
            .intensity(intensity)
            .duration(duration)
            .isActive(true)
            .build();
        
        activeChainReactions.put(reactionId, reaction);
        reactionIntensities.put(reactionId, intensity);
        reactionAffectedUnits.put(reactionId, new ArrayList<>());
        
        return true;
    }
    
    /**
     * Apply environmental hazards to unit
     */
    public void applyEnvironmentalHazardsToUnit(Unit unit) {
        Position unitPosition = unit.getPosition();
        
        for (Map.Entry<String, EnvironmentalHazard> entry : activeHazards.entrySet()) {
            EnvironmentalHazard hazard = entry.getValue();
            String hazardId = entry.getKey();
            
            if (isUnitInRange(unitPosition, hazard.getPosition(), hazard.getRadius())) {
                applyHazardEffectToUnit(unit, hazard);
                
                // Track affected unit
                if (!hazardAffectedUnits.get(hazardId).contains(unit.getId())) {
                    hazardAffectedUnits.get(hazardId).add(unit.getId());
                }
            }
        }
    }
    
    /**
     * Apply weather effects to unit
     */
    public void applyWeatherEffectsToUnit(Unit unit) {
        Position unitPosition = unit.getPosition();
        
        for (Map.Entry<String, WeatherEffect> entry : activeWeatherEffects.entrySet()) {
            WeatherEffect weather = entry.getValue();
            String weatherId = entry.getKey();
            
            if (isUnitInRange(unitPosition, weather.getPosition(), weather.getRadius())) {
                applyWeatherEffectToUnit(unit, weather);
                
                // Track affected unit
                if (!weatherAffectedUnits.get(weatherId).contains(unit.getId())) {
                    weatherAffectedUnits.get(weatherId).add(unit.getId());
                }
            }
        }
    }
    
    /**
     * Apply chain reactions to unit
     */
    public void applyChainReactionsToUnit(Unit unit) {
        Position unitPosition = unit.getPosition();
        
        for (Map.Entry<String, ChainReaction> entry : activeChainReactions.entrySet()) {
            ChainReaction reaction = entry.getValue();
            String reactionId = entry.getKey();
            
            if (isUnitInRange(unitPosition, reaction.getPosition(), reaction.getRadius())) {
                applyChainReactionToUnit(unit, reaction);
                
                // Track affected unit
                if (!reactionAffectedUnits.get(reactionId).contains(unit.getId())) {
                    reactionAffectedUnits.get(reactionId).add(unit.getId());
                }
            }
        }
    }
    
    /**
     * Process all environmental effects
     */
    public void processEnvironmentalEffects() {
        // Process hazards
        for (EnvironmentalHazard hazard : activeHazards.values()) {
            if (hazard.isActive()) {
                processHazard(hazard);
            }
        }
        
        // Process weather effects
        for (WeatherEffect weather : activeWeatherEffects.values()) {
            if (weather.isActive()) {
                processWeatherEffect(weather);
            }
        }
        
        // Process chain reactions
        for (ChainReaction reaction : activeChainReactions.values()) {
            if (reaction.isActive()) {
                processChainReaction(reaction);
            }
        }
    }
    
    /**
     * Trigger chain reaction
     */
    public boolean triggerChainReaction(String reactionId, Unit triggeringUnit) {
        ChainReaction reaction = activeChainReactions.get(reactionId);
        if (reaction != null && reaction.isActive()) {
            // Apply initial reaction effect
            applyChainReactionToUnit(triggeringUnit, reaction);
            
            // Propagate chain reaction
            propagateChainReaction(reaction, triggeringUnit.getPosition());
            
            return true;
        }
        return false;
    }
    
    /**
     * Remove environmental hazard
     */
    public boolean removeEnvironmentalHazard(String hazardId) {
        EnvironmentalHazard hazard = activeHazards.remove(hazardId);
        if (hazard != null) {
            hazardIntensities.remove(hazardId);
            hazardAffectedUnits.remove(hazardId);
            return true;
        }
        return false;
    }
    
    /**
     * Remove weather effect
     */
    public boolean removeWeatherEffect(String weatherId) {
        WeatherEffect weather = activeWeatherEffects.remove(weatherId);
        if (weather != null) {
            weatherIntensities.remove(weatherId);
            weatherAffectedUnits.remove(weatherId);
            return true;
        }
        return false;
    }
    
    /**
     * Remove chain reaction
     */
    public boolean removeChainReaction(String reactionId) {
        ChainReaction reaction = activeChainReactions.remove(reactionId);
        if (reaction != null) {
            reactionIntensities.remove(reactionId);
            reactionAffectedUnits.remove(reactionId);
            return true;
        }
        return false;
    }
    
    // Private helper methods
    
    private void applyHazardEffectToUnit(Unit unit, EnvironmentalHazard hazard) {
        int intensity = hazard.getIntensity();
        
        switch (hazard.getHazardType()) {
            case FIRE_HAZARD:
                unit.takeDamage(intensity);
                unit.applyEnvironmentalHazard(StatusEffect.BURNING, 3, intensity);
                break;
            case TOXIC_HAZARD:
                unit.takeDamage(intensity / 2);
                unit.applyEnvironmentalHazard(StatusEffect.POISONED, 4, intensity);
                break;
            case ELECTRICAL_HAZARD:
                unit.takeDamage(intensity);
                unit.applyEnvironmentalHazard(StatusEffect.ELECTROCUTED, 2, intensity);
                break;
            case RADIATION_HAZARD:
                unit.takeDamage(intensity / 2);
                unit.applyEnvironmentalHazard(StatusEffect.RADIATION, 5, intensity);
                break;
            case ACID_HAZARD:
                unit.takeDamage(intensity);
                unit.applyEnvironmentalHazard(StatusEffect.ACID_BURN, 3, intensity);
                break;
            case PLASMA_HAZARD:
                unit.takeDamage(intensity);
                unit.applyEnvironmentalHazard(StatusEffect.MUTATION_RISK, 2, intensity);
                break;
        }
    }
    
    private void applyWeatherEffectToUnit(Unit unit, WeatherEffect weather) {
        int intensity = weather.getIntensity();
        
        switch (weather.getWeatherType()) {
            case VISIBILITY_MODIFIER:
                // Reduce visibility
                unit.setViewRange(Math.max(1, unit.getViewRange() - intensity / 10));
                break;
            case MOVEMENT_MODIFIER:
                // Reduce movement
                unit.setMovementRange(Math.max(1, unit.getMovementRange() - intensity / 10));
                break;
            case ACCURACY_MODIFIER:
                // Reduce accuracy
                unit.setAccuracy(Math.max(10, unit.getAccuracy() - intensity / 5));
                break;
            case DAMAGE_MODIFIER:
                // Modify damage output
                unit.setAttackDamage(Math.max(1, unit.getAttackDamage() - intensity / 10));
                break;
            case ARMOR_MODIFIER:
                // Reduce armor effectiveness - simplified for now
                unit.applyEnvironmentalHazard(StatusEffect.ARMOR_DEGRADATION, 2, intensity / 10);
                break;
            case EQUIPMENT_MODIFIER:
                // Apply equipment degradation
                unit.applyEnvironmentalHazard(StatusEffect.WEAPON_MALFUNCTION, 2, intensity / 5);
                break;
            case PSIONIC_MODIFIER:
                // Reduce psionic abilities
                unit.applyEnvironmentalHazard(StatusEffect.MUTATION_RISK, 2, intensity / 5);
                break;
            case MUTATION_RISK:
                // Apply mutation risk
                unit.applyEnvironmentalHazard(StatusEffect.MUTATION_RISK, 3, intensity / 10);
                break;
        }
    }
    
    private void applyChainReactionToUnit(Unit unit, ChainReaction reaction) {
        int intensity = reaction.getIntensity();
        
        switch (reaction.getReactionType()) {
            case EXPLOSIVE_CHAIN:
                unit.takeDamage(intensity * 2);
                unit.applyEnvironmentalHazard(StatusEffect.KNOCKED_BACK, 1, intensity);
                break;
            case FIRE_SPREAD:
                unit.takeDamage(intensity);
                unit.applyEnvironmentalHazard(StatusEffect.BURNING, 3, intensity);
                break;
            case ELECTRICAL_ARC:
                unit.takeDamage(intensity);
                unit.applyEnvironmentalHazard(StatusEffect.ELECTROCUTED, 2, intensity);
                break;
            case CHEMICAL_REACTION:
                unit.takeDamage(intensity / 2);
                unit.applyEnvironmentalHazard(StatusEffect.POISONED, 4, intensity);
                break;
            case RADIATION_SPREAD:
                unit.takeDamage(intensity / 2);
                unit.applyEnvironmentalHazard(StatusEffect.RADIATION, 5, intensity);
                break;
            case PLASMA_CASCADE:
                unit.takeDamage(intensity);
                unit.applyEnvironmentalHazard(StatusEffect.MUTATION_RISK, 2, intensity);
                break;
        }
    }
    
    private void processHazard(EnvironmentalHazard hazard) {
        if (hazard.getDuration() > 0) {
            hazard.setDuration(hazard.getDuration() - 1);
            
            if (hazard.getDuration() <= 0) {
                hazard.setActive(false);
            }
        }
    }
    
    private void processWeatherEffect(WeatherEffect weather) {
        if (weather.getDuration() > 0) {
            weather.setDuration(weather.getDuration() - 1);
            
            if (weather.getDuration() <= 0) {
                weather.setActive(false);
            }
        }
    }
    
    private void processChainReaction(ChainReaction reaction) {
        if (reaction.getDuration() > 0) {
            reaction.setDuration(reaction.getDuration() - 1);
            
            if (reaction.getDuration() <= 0) {
                reaction.setActive(false);
            }
        }
    }
    
    private void propagateChainReaction(ChainReaction reaction, Position triggerPosition) {
        // Simulate chain reaction propagation
        int propagationRadius = reaction.getRadius() * 2;
        
        // Find nearby units and apply effects
        // This would integrate with the unit management system
        // For now, we'll just apply the effect to the triggering unit
        // In a full implementation, this would check all units in range
        
        // Apply chain reaction effects to all units within propagation radius
        // This is a simplified implementation - in a full system, you would:
        // 1. Get all units from the game state
        // 2. Check which units are within propagationRadius of triggerPosition
        // 3. Apply chain reaction effects to those units
        
        // For now, we'll use the propagation radius to determine effect intensity
        int chainIntensity = reaction.getIntensity() + (propagationRadius / 10);
        
        // Update the reaction intensity based on propagation
        reaction.setIntensity(Math.min(100, chainIntensity));
    }
    
    private boolean isUnitInRange(Position unitPos, Position effectPos, int radius) {
        double distance = calculateDistance(unitPos, effectPos);
        return distance <= radius;
    }
    
    private double calculateDistance(Position pos1, Position pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        int dz = pos1.getHeight() - pos2.getHeight();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    // Data classes for environmental effects
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnvironmentalHazard {
        private String hazardId;
        private HazardType hazardType;
        private Position position;
        private int radius;
        private int intensity;
        private int duration;
        private boolean isActive;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherEffect {
        private String weatherId;
        private WeatherEffectType weatherType;
        private Position position;
        private int radius;
        private int intensity;
        private int duration;
        private boolean isActive;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChainReaction {
        private String reactionId;
        private ChainReactionType reactionType;
        private Position position;
        private int radius;
        private int intensity;
        private int duration;
        private boolean isActive;
    }
}

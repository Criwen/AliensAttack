package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.StatusEffect;
import java.util.*;

/**
 * Enhanced Weather Effects System for XCOM 2 Tactical Combat
 * Provides dynamic environmental conditions that affect combat
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherSystem {
    
    // Weather Types
    public enum WeatherType {
        CLEAR,          // No weather effects
        RAIN,           // Reduced visibility, movement penalties
        STORM,          // Heavy rain with lightning
        FOG,            // Reduced visibility, concealment bonus
        SNOW,           // Movement penalties, reduced accuracy
        SANDSTORM,      // Reduced visibility, damage over time
        ACID_RAIN,      // Damage over time, equipment degradation
        RADIATION,      // Health damage, mutation risk
        FIRE_STORM,     // High damage, reduced visibility
        ICE_STORM,      // Movement penalties, freezing effects
        ELECTRICAL_STORM, // Equipment damage, stunning effects
        PLASMA_STORM    // High damage, psionic interference
    }
    
    // Weather Intensity Levels
    public enum IntensityLevel {
        LIGHT,          // Minimal effects
        MODERATE,       // Noticeable effects
        HEAVY,          // Significant effects
        SEVERE,         // Major effects
        EXTREME         // Maximum effects
    }
    
    // Weather Effects
    public enum WeatherEffect {
        VISIBILITY_REDUCTION,    // Reduced line of sight
        MOVEMENT_PENALTY,        // Reduced movement range
        ACCURACY_PENALTY,        // Reduced attack accuracy
        DAMAGE_OVER_TIME,        // Continuous damage
        EQUIPMENT_DEGRADATION,   // Equipment damage
        CONCEALMENT_BONUS,       // Improved stealth
        PSIONIC_INTERFERENCE,    // Reduced psionic abilities
        MUTATION_RISK,           // Risk of mutations
        FREEZING_EFFECT,         // Movement and action penalties
        STUNNING_EFFECT,         // Temporary incapacitation
        ARMOR_DEGRADATION,       // Armor damage
        WEAPON_MALFUNCTION       // Weapon reliability issues
    }
    
    private String id;
    private WeatherType currentWeather;
    private IntensityLevel intensity;
    private int duration; // Turns remaining
    private Map<WeatherEffect, Integer> activeEffects;
    private List<Unit> affectedUnits;
    private boolean isDynamic; // Weather changes over time
    private int changeFrequency; // How often weather changes
    private Random random;
    

    
    // Weather Management Methods
    public void setWeather(WeatherType weather, IntensityLevel intensity, int duration) {
        this.currentWeather = weather;
        this.intensity = intensity;
        this.duration = duration;
        
        // Apply weather effects
        applyWeatherEffects();
    }
    
    public void updateWeather() {
        if (duration > 0) {
            duration--;
            
            if (duration <= 0) {
                // Weather expires, return to clear
                setWeather(WeatherType.CLEAR, IntensityLevel.LIGHT, 0);
            }
        }
        
        // Dynamic weather changes
        if (isDynamic && random.nextInt(100) < changeFrequency) {
            changeWeatherRandomly();
        }
    }
    
    public void changeWeatherRandomly() {
        WeatherType[] weatherTypes = WeatherType.values();
        IntensityLevel[] intensityLevels = IntensityLevel.values();
        
        WeatherType newWeather = weatherTypes[random.nextInt(weatherTypes.length)];
        IntensityLevel newIntensity = intensityLevels[random.nextInt(intensityLevels.length)];
        
        setWeather(newWeather, newIntensity, random.nextInt(10) + 5); // 5-15 turns
    }
    
    public void applyWeatherEffects() {
        activeEffects.clear();
        
        switch (currentWeather) {
            case RAIN:
                applyRainEffects();
                break;
            case STORM:
                applyStormEffects();
                break;
            case FOG:
                applyFogEffects();
                break;
            case SNOW:
                applySnowEffects();
                break;
            case SANDSTORM:
                applySandstormEffects();
                break;
            case ACID_RAIN:
                applyAcidRainEffects();
                break;
            case RADIATION:
                applyRadiationEffects();
                break;
            case FIRE_STORM:
                applyFireStormEffects();
                break;
            case ICE_STORM:
                applyIceStormEffects();
                break;
            case ELECTRICAL_STORM:
                applyElectricalStormEffects();
                break;
            case PLASMA_STORM:
                applyPlasmaStormEffects();
                break;
            default:
                // Clear weather - no effects
                break;
        }
    }
    
    private void applyRainEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.VISIBILITY_REDUCTION, effectValue);
        activeEffects.put(WeatherEffect.MOVEMENT_PENALTY, effectValue / 2);
        activeEffects.put(WeatherEffect.ACCURACY_PENALTY, effectValue / 3);
    }
    
    private void applyStormEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.VISIBILITY_REDUCTION, effectValue * 2);
        activeEffects.put(WeatherEffect.MOVEMENT_PENALTY, effectValue);
        activeEffects.put(WeatherEffect.ACCURACY_PENALTY, effectValue);
        activeEffects.put(WeatherEffect.DAMAGE_OVER_TIME, effectValue / 4);
    }
    
    private void applyFogEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.VISIBILITY_REDUCTION, effectValue * 3);
        activeEffects.put(WeatherEffect.CONCEALMENT_BONUS, effectValue);
    }
    
    private void applySnowEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.MOVEMENT_PENALTY, effectValue * 2);
        activeEffects.put(WeatherEffect.ACCURACY_PENALTY, effectValue);
        activeEffects.put(WeatherEffect.FREEZING_EFFECT, effectValue);
    }
    
    private void applySandstormEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.VISIBILITY_REDUCTION, effectValue * 2);
        activeEffects.put(WeatherEffect.DAMAGE_OVER_TIME, effectValue / 2);
        activeEffects.put(WeatherEffect.EQUIPMENT_DEGRADATION, effectValue / 4);
    }
    
    private void applyAcidRainEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.DAMAGE_OVER_TIME, effectValue);
        activeEffects.put(WeatherEffect.EQUIPMENT_DEGRADATION, effectValue);
        activeEffects.put(WeatherEffect.ARMOR_DEGRADATION, effectValue / 2);
    }
    
    private void applyRadiationEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.DAMAGE_OVER_TIME, effectValue);
        activeEffects.put(WeatherEffect.MUTATION_RISK, effectValue / 3);
        activeEffects.put(WeatherEffect.PSIONIC_INTERFERENCE, effectValue / 2);
    }
    
    private void applyFireStormEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.DAMAGE_OVER_TIME, effectValue * 2);
        activeEffects.put(WeatherEffect.VISIBILITY_REDUCTION, effectValue);
        activeEffects.put(WeatherEffect.EQUIPMENT_DEGRADATION, effectValue);
    }
    
    private void applyIceStormEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.MOVEMENT_PENALTY, effectValue * 2);
        activeEffects.put(WeatherEffect.FREEZING_EFFECT, effectValue);
        activeEffects.put(WeatherEffect.STUNNING_EFFECT, effectValue / 3);
    }
    
    private void applyElectricalStormEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.EQUIPMENT_DEGRADATION, effectValue);
        activeEffects.put(WeatherEffect.STUNNING_EFFECT, effectValue / 2);
        activeEffects.put(WeatherEffect.WEAPON_MALFUNCTION, effectValue / 3);
    }
    
    private void applyPlasmaStormEffects() {
        int effectValue = getIntensityValue();
        activeEffects.put(WeatherEffect.DAMAGE_OVER_TIME, effectValue * 2);
        activeEffects.put(WeatherEffect.PSIONIC_INTERFERENCE, effectValue);
        activeEffects.put(WeatherEffect.EQUIPMENT_DEGRADATION, effectValue);
    }
    
    private int getIntensityValue() {
        switch (intensity) {
            case LIGHT: return 10;
            case MODERATE: return 25;
            case HEAVY: return 50;
            case SEVERE: return 75;
            case EXTREME: return 100;
            default: return 10;
        }
    }
    
    // Unit Interaction Methods
    public void affectUnit(Unit unit) {
        if (!affectedUnits.contains(unit)) {
            affectedUnits.add(unit);
        }
        
        // Apply weather effects to unit
        applyWeatherEffectsToUnit(unit);
    }
    
    public void removeUnit(Unit unit) {
        affectedUnits.remove(unit);
        // Remove weather effects from unit
        removeWeatherEffectsFromUnit(unit);
    }
    
    public void applyWeatherEffectsToUnit(Unit unit) {
        for (Map.Entry<WeatherEffect, Integer> effect : activeEffects.entrySet()) {
            applyWeatherEffectToUnit(unit, effect.getKey(), effect.getValue());
        }
    }
    
    public void removeWeatherEffectsFromUnit(Unit unit) {
        // Remove all weather-related status effects
        unit.getStatusEffects().removeIf(effect -> 
            effect.getEffect().name().contains("WEATHER"));
    }
    
    private void applyWeatherEffectToUnit(Unit unit, WeatherEffect effect, int intensity) {
        switch (effect) {
            case VISIBILITY_REDUCTION:
                reduceUnitVisibility(unit, intensity);
                break;
            case MOVEMENT_PENALTY:
                applyMovementPenalty(unit, intensity);
                break;
            case ACCURACY_PENALTY:
                applyAccuracyPenalty(unit, intensity);
                break;
            case DAMAGE_OVER_TIME:
                applyDamageOverTime(unit, intensity);
                break;
            case EQUIPMENT_DEGRADATION:
                applyEquipmentDegradation(unit, intensity);
                break;
            case CONCEALMENT_BONUS:
                applyConcealmentBonus(unit, intensity);
                break;
            case PSIONIC_INTERFERENCE:
                applyPsionicInterference(unit, intensity);
                break;
            case MUTATION_RISK:
                applyMutationRisk(unit, intensity);
                break;
            case FREEZING_EFFECT:
                applyFreezingEffect(unit, intensity);
                break;
            case STUNNING_EFFECT:
                applyStunningEffect(unit, intensity);
                break;
            case ARMOR_DEGRADATION:
                applyArmorDegradation(unit, intensity);
                break;
            case WEAPON_MALFUNCTION:
                applyWeaponMalfunction(unit, intensity);
                break;
        }
    }
    
    private void reduceUnitVisibility(Unit unit, int intensity) {
        int viewRangeReduction = intensity / 10;
        int newViewRange = Math.max(1, unit.getViewRange() - viewRangeReduction);
        unit.setViewRange(newViewRange);
    }
    
    private void applyMovementPenalty(Unit unit, int intensity) {
        int movementReduction = intensity / 20;
        int newMovementRange = Math.max(1, unit.getMovementRange() - movementReduction);
        unit.setMovementRange(newMovementRange);
    }
    
    private void applyAccuracyPenalty(Unit unit, int intensity) {
        // Apply accuracy penalty through status effect
        StatusEffectData accuracyPenalty = new StatusEffectData(StatusEffect.STUNNED, 1, intensity / 10);
        unit.addStatusEffect(accuracyPenalty);
    }
    
    private void applyDamageOverTime(Unit unit, int intensity) {
        int damage = intensity / 20;
        unit.takeDamage(damage);
    }
    
    private void applyEquipmentDegradation(Unit unit, int intensity) {
        // Simulate equipment degradation
        if (unit.getWeapon() != null && random.nextInt(100) < intensity) {
            // Weapon malfunction chance
            StatusEffectData weaponMalfunction = new StatusEffectData(StatusEffect.STUNNED, 1, intensity / 10);
            unit.addStatusEffect(weaponMalfunction);
        }
    }
    
    private void applyConcealmentBonus(Unit unit, int intensity) {
        if (!unit.isConcealed()) {
            unit.setConcealed(true);
        }
    }
    
    private void applyPsionicInterference(Unit unit, int intensity) {
        int psiReduction = intensity / 10;
        int newPsiStrength = Math.max(0, unit.getPsiStrength() - psiReduction);
        unit.setPsiStrength(newPsiStrength);
    }
    
    private void applyMutationRisk(Unit unit, int intensity) {
        if (random.nextInt(100) < intensity) {
            // Apply mutation risk
            StatusEffectData mutationRisk = new StatusEffectData(StatusEffect.MUTATION_RISK, 3, intensity / 10);
            unit.addStatusEffect(mutationRisk);
        }
    }
    
    private void applyFreezingEffect(Unit unit, int intensity) {
        StatusEffectData freezingEffect = new StatusEffectData(StatusEffect.FREEZING, 1, intensity / 10);
        unit.addStatusEffect(freezingEffect);
    }
    
    private void applyStunningEffect(Unit unit, int intensity) {
        if (random.nextInt(100) < intensity) {
            StatusEffectData stunningEffect = new StatusEffectData(StatusEffect.STUNNED, 1, intensity / 10);
            unit.addStatusEffect(stunningEffect);
        }
    }
    
    private void applyArmorDegradation(Unit unit, int intensity) {
        if (unit.getArmor() != null && random.nextInt(100) < intensity) {
            // Armor degradation
            StatusEffectData armorDegradation = new StatusEffectData(StatusEffect.ARMOR_DEGRADATION, 1, intensity / 10);
            unit.addStatusEffect(armorDegradation);
        }
    }
    
    private void applyWeaponMalfunction(Unit unit, int intensity) {
        if (unit.getWeapon() != null && random.nextInt(100) < intensity) {
            StatusEffectData weaponMalfunction = new StatusEffectData(StatusEffect.WEAPON_MALFUNCTION, 1, intensity / 10);
            unit.addStatusEffect(weaponMalfunction);
        }
    }
    
    // Weather Information Methods
    public String getWeatherDescription() {
        return String.format("%s weather with %s intensity (Duration: %d turns)", 
            currentWeather.name(), intensity.name(), duration);
    }
    
    public boolean isWeatherActive() {
        return currentWeather != WeatherType.CLEAR && duration > 0;
    }
    
    public int getVisibilityModifier() {
        return activeEffects.getOrDefault(WeatherEffect.VISIBILITY_REDUCTION, 0);
    }
    
    public int getMovementModifier() {
        return activeEffects.getOrDefault(WeatherEffect.MOVEMENT_PENALTY, 0);
    }
    
    public int getAccuracyModifier() {
        return activeEffects.getOrDefault(WeatherEffect.ACCURACY_PENALTY, 0);
    }
    
    public int getDamageOverTime() {
        return activeEffects.getOrDefault(WeatherEffect.DAMAGE_OVER_TIME, 0);
    }
}

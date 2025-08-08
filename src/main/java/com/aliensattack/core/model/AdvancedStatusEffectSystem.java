package com.aliensattack.core.model;

import com.aliensattack.core.enums.StatusEffect;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Status Effect System implementing XCOM 2 status effect mechanics:
 * - Status effect stacking
 * - Status effect duration tracking
 * - Status effect interactions
 * - Status effect resistance and cures
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedStatusEffectSystem {
    
    private String unitId;
    private Map<StatusEffect, StatusEffectInstance> activeEffects;
    private Map<StatusEffect, Integer> resistances;
    private List<StatusEffect> immunities;
    private Map<String, Integer> cureAbilities;
    
    public AdvancedStatusEffectSystem(String unitId) {
        this.unitId = unitId;
        this.activeEffects = new HashMap<>();
        this.resistances = new HashMap<>();
        this.immunities = new ArrayList<>();
        this.cureAbilities = new HashMap<>();
    }
    
    /**
     * Apply status effect to unit
     */
    public boolean applyStatusEffect(StatusEffect effect, int duration, int intensity) {
        // Check for immunity
        if (immunities.contains(effect)) {
            return false;
        }
        
        // Check for resistance
        int resistance = resistances.getOrDefault(effect, 0);
        if (resistance > 0) {
            int resistanceRoll = new Random().nextInt(100) + 1;
            if (resistanceRoll <= resistance) {
                return false; // Effect resisted
            }
        }
        
        // Check if effect already exists
        if (activeEffects.containsKey(effect)) {
            // Stack the effect
            StatusEffectInstance existing = activeEffects.get(effect);
            existing.setIntensity(existing.getIntensity() + intensity);
            existing.setDuration(Math.max(existing.getDuration(), duration));
            return true;
        }
        
        // Apply new effect
        StatusEffectInstance newEffect = new StatusEffectInstance(effect, duration, intensity);
        activeEffects.put(effect, newEffect);
        return true;
    }
    
    /**
     * Remove status effect from unit
     */
    public boolean removeStatusEffect(StatusEffect effect) {
        return activeEffects.remove(effect) != null;
    }
    
    /**
     * Update status effect durations
     */
    public void updateStatusEffects() {
        Iterator<Map.Entry<StatusEffect, StatusEffectInstance>> iterator = activeEffects.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<StatusEffect, StatusEffectInstance> entry = iterator.next();
            StatusEffectInstance effect = entry.getValue();
            
            effect.setDuration(effect.getDuration() - 1);
            
            if (effect.getDuration() <= 0) {
                iterator.remove();
            }
        }
    }
    
    /**
     * Get all active status effects
     */
    public List<StatusEffectInstance> getActiveEffects() {
        return new ArrayList<>(activeEffects.values());
    }
    
    /**
     * Check if unit has specific status effect
     */
    public boolean hasStatusEffect(StatusEffect effect) {
        return activeEffects.containsKey(effect);
    }
    
    /**
     * Get status effect intensity
     */
    public int getEffectIntensity(StatusEffect effect) {
        StatusEffectInstance instance = activeEffects.get(effect);
        return instance != null ? instance.getIntensity() : 0;
    }
    
    /**
     * Get status effect duration
     */
    public int getEffectDuration(StatusEffect effect) {
        StatusEffectInstance instance = activeEffects.get(effect);
        return instance != null ? instance.getDuration() : 0;
    }
    
    /**
     * Add resistance to status effect
     */
    public void addResistance(StatusEffect effect, int resistanceValue) {
        int currentResistance = resistances.getOrDefault(effect, 0);
        resistances.put(effect, currentResistance + resistanceValue);
    }
    
    /**
     * Remove resistance to status effect
     */
    public void removeResistance(StatusEffect effect, int resistanceValue) {
        int currentResistance = resistances.getOrDefault(effect, 0);
        resistances.put(effect, Math.max(0, currentResistance - resistanceValue));
    }
    
    /**
     * Add immunity to status effect
     */
    public void addImmunity(StatusEffect effect) {
        if (!immunities.contains(effect)) {
            immunities.add(effect);
        }
    }
    
    /**
     * Remove immunity to status effect
     */
    public void removeImmunity(StatusEffect effect) {
        immunities.remove(effect);
    }
    
    /**
     * Add cure ability
     */
    public void addCureAbility(String ability, int cureChance) {
        cureAbilities.put(ability, cureChance);
    }
    
    /**
     * Use cure ability on status effect
     */
    public boolean useCureAbility(String ability, StatusEffect targetEffect) {
        Integer cureChance = cureAbilities.get(ability);
        if (cureChance == null || !hasStatusEffect(targetEffect)) {
            return false;
        }
        
        int roll = new Random().nextInt(100) + 1;
        if (roll <= cureChance) {
            return removeStatusEffect(targetEffect);
        }
        
        return false;
    }
    
    /**
     * Check for status effect interactions
     */
    public void checkStatusEffectInteractions() {
        // Example interactions
        if (hasStatusEffect(StatusEffect.STUNNED) && hasStatusEffect(StatusEffect.BURNING)) {
            // Burning removes stun
            removeStatusEffect(StatusEffect.STUNNED);
        }
        
        if (hasStatusEffect(StatusEffect.POISONED) && hasStatusEffect(StatusEffect.HEALING)) {
            // Healing reduces poison
            StatusEffectInstance poison = activeEffects.get(StatusEffect.POISONED);
            if (poison != null) {
                poison.setIntensity(Math.max(0, poison.getIntensity() - 1));
            }
        }
    }
    
    /**
     * Get total effect count
     */
    public int getTotalEffectCount() {
        return activeEffects.size();
    }
    
    /**
     * Get status effects summary
     */
    public String getStatusEffectsSummary() {
        if (activeEffects.isEmpty()) {
            return "No active status effects";
        }
        
        StringBuilder summary = new StringBuilder("Active effects: ");
        for (Map.Entry<StatusEffect, StatusEffectInstance> entry : activeEffects.entrySet()) {
            StatusEffectInstance effect = entry.getValue();
            summary.append(String.format("%s (Intensity: %d, Duration: %d), ", 
                entry.getKey(), effect.getIntensity(), effect.getDuration()));
        }
        
        return summary.toString().replaceAll(", $", "");
    }
    
    /**
     * Status Effect Instance inner class
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StatusEffectInstance {
        private StatusEffect effect;
        private int duration;
        private int intensity;
        private long appliedTime;
        
        public StatusEffectInstance(StatusEffect effect, int duration, int intensity) {
            this.effect = effect;
            this.duration = duration;
            this.intensity = intensity;
            this.appliedTime = System.currentTimeMillis();
        }
        
        /**
         * Get time since effect was applied
         */
        public long getTimeSinceApplied() {
            return System.currentTimeMillis() - appliedTime;
        }
        
        /**
         * Check if effect is about to expire
         */
        public boolean isExpiringSoon() {
            return duration <= 1;
        }
    }
}

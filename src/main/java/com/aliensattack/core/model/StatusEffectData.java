package com.aliensattack.core.model;

import com.aliensattack.core.enums.StatusEffect;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents status effect data with duration and intensity
 */
@Getter
@Setter
public class StatusEffectData {
    private StatusEffect effect;
    private int duration; // Number of turns remaining
    private int intensity; // Effect strength (damage per turn, etc.)
    private boolean isPermanent; // For effects that don't expire
    
    public StatusEffectData(StatusEffect effect, int duration, int intensity) {
        this.effect = effect;
        this.duration = duration;
        this.intensity = intensity;
        this.isPermanent = false;
    }
    
    public StatusEffectData(StatusEffect effect, int duration, int intensity, boolean isPermanent) {
        this(effect, duration, intensity);
        this.isPermanent = isPermanent;
    }
    
    public void decrementDuration() {
        if (!isPermanent && duration > 0) {
            duration--;
        }
    }
    
    public boolean isExpired() {
        return !isPermanent && duration <= 0;
    }
    
    public boolean isActive() {
        return !isExpired();
    }
    
    // Additional getter methods for compatibility
    public StatusEffect getEffect() {
        return effect;
    }
    
    public int getIntensity() {
        return intensity;
    }
} 
package com.aliensattack.core.model.components;

import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.model.StatusEffectData;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component for managing unit status effects
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusEffectManager {
    private List<StatusEffectData> statusEffects;
    private boolean isOverwatching;
    private boolean isFlanked;
    private boolean isSuppressed;
    private int suppressionTurns;
    private boolean isStabilized;
    private int medicalPriority;
    
    {
        this.statusEffects = new ArrayList<>();
        this.isOverwatching = false;
        this.isFlanked = false;
        this.isSuppressed = false;
        this.suppressionTurns = 0;
        this.isStabilized = false;
        this.medicalPriority = 0;
    }
    
    public void addStatusEffect(StatusEffectData effect) {
        if (effect != null) {
            statusEffects.add(effect);
        }
    }
    
    public void removeStatusEffect(StatusEffectData effect) {
        statusEffects.remove(effect);
    }
    
    public boolean hasStatusEffect(StatusEffect type) {
        return statusEffects.stream()
                .anyMatch(effect -> effect.getEffect() == type);
    }
    
    public List<StatusEffectData> getActiveEffects() {
        return statusEffects.stream()
                .filter(effect -> effect.getDuration() > 0)
                .collect(Collectors.toList());
    }
    
    public void updateEffects() {
        statusEffects.forEach(effect -> effect.setDuration(effect.getDuration() - 1));
        statusEffects.removeIf(effect -> effect.getDuration() <= 0);
    }
    
    public boolean isIncapacitated() {
        return hasStatusEffect(StatusEffect.STUNNED) || 
               hasStatusEffect(StatusEffect.GRAPPLED) ||
               hasStatusEffect(StatusEffect.BOUND);
    }
}

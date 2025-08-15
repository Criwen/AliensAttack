package com.aliensattack.core.model.components;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Component for managing unit medical status
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalStatus {
    private boolean isStabilized;
    private int medicalPriority;
    private boolean hasFallen;
    private int fallDamage;
    private int healingRate;
    private int maxHealingRate;
    
    {
        this.isStabilized = false;
        this.medicalPriority = 0;
        this.hasFallen = false;
        this.fallDamage = 0;
        this.healingRate = 0;
        this.maxHealingRate = 5;
    }
    
    public boolean needsMedicalAttention() {
        return !isStabilized || medicalPriority > 0;
    }
    
    public boolean isCriticallyInjured() {
        return medicalPriority >= 5;
    }
    
    public void stabilize() {
        this.isStabilized = true;
        this.medicalPriority = Math.max(0, this.medicalPriority - 1);
    }
    
    public void setMedicalPriority(int priority) {
        this.medicalPriority = Math.max(0, Math.min(10, priority));
    }
    
    public void processHealing() {
        if (isStabilized && healingRate > 0) {
            // Healing logic would be implemented here
            healingRate = Math.min(maxHealingRate, healingRate + 1);
        }
    }
}

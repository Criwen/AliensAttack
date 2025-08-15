package com.aliensattack.core.model.components;

import com.aliensattack.core.model.PsionicAbility;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Component for managing unit psionic abilities
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PsionicProfile {
    private List<PsionicAbility> psionicAbilities;
    private int psiStrength;
    private int psiResistance;
    private int psiEnergy;
    private int maxPsiEnergy;
    
    {
        this.psionicAbilities = new ArrayList<>();
        this.psiStrength = 0;
        this.psiResistance = 0;
        this.psiEnergy = 0;
        this.maxPsiEnergy = 100;
    }
    
    public boolean hasPsionicAbilities() {
        return !psionicAbilities.isEmpty();
    }
    
    public boolean canUsePsionicAbility(PsionicAbility ability) {
        return psiEnergy >= ability.getPsiCost();
    }
    
    public void consumePsiEnergy(int amount) {
        psiEnergy = Math.max(0, psiEnergy - amount);
    }
    
    public void restorePsiEnergy(int amount) {
        psiEnergy = Math.min(maxPsiEnergy, psiEnergy + amount);
    }
    
    public boolean isPsionicallyActive() {
        return psiStrength > 0;
    }
}

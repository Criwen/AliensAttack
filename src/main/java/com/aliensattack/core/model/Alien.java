package com.aliensattack.core.model;

import com.aliensattack.core.enums.UnitType;
import com.aliensattack.core.enums.AlienType;
import com.aliensattack.core.enums.PsionicType;
import com.aliensattack.core.model.PsionicAbility;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

/**
 * Alien unit - extraterrestrial enemies
 * Extends BaseUnit with alien-specific abilities and evolution
 */
@Getter
@Setter
public class Alien extends BaseUnit {
    
    private AlienType alienType;
    private List<PsionicAbility> psionicAbilities;
    private int evolutionLevel;
    private List<String> mutations;
    private int psiStrength;
    private int psiResistance;
    private boolean isEvolved;
    
    public Alien(String name, int maxHealth, int movementRange, int attackRange, int attackDamage, AlienType alienType) {
        super(name, maxHealth, movementRange, attackRange, attackDamage, UnitType.ALIEN);
        this.alienType = alienType;
        this.psionicAbilities = new ArrayList<>();
        this.evolutionLevel = 1;
        this.mutations = new ArrayList<>();
        this.psiStrength = calculateBasePsiStrength();
        this.psiResistance = calculateBasePsiResistance();
        this.isEvolved = false;
        
        initializeAlienAbilities();
    }
    
    /**
     * Initialize abilities based on alien type
     */
    private void initializeAlienAbilities() {
        switch (alienType) {
            case ADVENT_PRIEST -> {
                psionicAbilities.add(new PsionicAbility("Mind Control", PsionicType.MIND_CONTROL, 3, 8));
                psionicAbilities.add(new PsionicAbility("Psionic Blast", PsionicType.PSYCHIC_BLAST, 2, 6));
            }
            case ADVENT_MUTON -> {
                mutations.add("Enhanced Strength");
                mutations.add("Regeneration");
            }
            case ADVENT_BERSERKER -> {
                mutations.add("Poison Claws");
                mutations.add("Burrowing");
            }
            case ADVENT_ARCHON -> {
                mutations.add("Flight");
                mutations.add("Hover Attack");
            }
            case ADVENT_GATEKEEPER -> {
                psionicAbilities.add(new PsionicAbility("Psionic Gate", PsionicType.MIND_CONTROL, 4, 10));
                mutations.add("Dimensional Shift");
            }
            case ADVENT_CODEX -> {
                psionicAbilities.add(new PsionicAbility("Psionic Clone", PsionicType.PSYCHIC_BLAST, 2, 7));
                mutations.add("Clone Regeneration");
            }
            case ADVENT_AVATAR -> {
                psionicAbilities.add(new PsionicAbility("Psionic Mastery", PsionicType.MIND_CONTROL, 5, 12));
                mutations.add("Avatar Form");
            }
            default -> {
                // Basic abilities for other alien types
                if (alienType.name().contains("MEC") || alienType.name().contains("SECTOPOD")) {
                    mutations.add("Mechanical Armor");
                }
            }
        }
    }
    
    /**
     * Calculate base psionic strength
     */
    private int calculateBasePsiStrength() {
        return switch (alienType) {
            case ADVENT_PRIEST -> 80;
            case ADVENT_MUTON -> 40;
            case ADVENT_BERSERKER -> 20;
            case ADVENT_ARCHON -> 30;
            default -> 50;
        };
    }
    
    /**
     * Calculate base psionic resistance
     */
    private int calculateBasePsiResistance() {
        return switch (alienType) {
            case ADVENT_PRIEST -> 70;
            case ADVENT_MUTON -> 90;
            case ADVENT_BERSERKER -> 60;
            case ADVENT_ARCHON -> 50;
            default -> 70;
        };
    }
    
    /**
     * Evolve the alien
     */
    public boolean evolve() {
        if (isEvolved || evolutionLevel >= 3) {
            return false;
        }
        
        evolutionLevel++;
        isEvolved = true;
        
        // Increase stats
        maxHealth += 10;
        currentHealth = Math.min(currentHealth + 10, maxHealth);
        attackDamage += 5;
        psiStrength += 20;
        
        // Add new mutation
        addRandomMutation();
        
        return true;
    }
    
    /**
     * Add random mutation
     */
    private void addRandomMutation() {
        String[] possibleMutations = {
            "Enhanced Senses", "Acid Blood", "Teleportation", 
            "Mind Shield", "Rapid Healing", "Stealth Field"
        };
        
        String newMutation = possibleMutations[(int)(Math.random() * possibleMutations.length)];
        if (!mutations.contains(newMutation)) {
            mutations.add(newMutation);
        }
    }
    
    /**
     * Use psionic ability
     */
    public boolean usePsionicAbility(String abilityName) {
        PsionicAbility ability = psionicAbilities.stream()
                .filter(a -> a.getName().equals(abilityName))
                .findFirst()
                .orElse(null);
        
        if (ability == null || ability.getCooldown() > 0) {
            return false;
        }
        
        // Set cooldown
        ability.setCooldown(ability.getCooldown());
        
        return true;
    }
    
    /**
     * Process psionic ability cooldowns
     */
    public void processPsionicCooldowns() {
        psionicAbilities.forEach(ability -> {
            if (ability.getCooldown() > 0) {
                ability.setCooldown(ability.getCooldown() - 1);
            }
        });
    }
    
    /**
     * Get total attack damage including evolution bonuses
     */
    @Override
    public int getAttackDamage() {
        int baseDamage = super.getAttackDamage();
        int evolutionBonus = (evolutionLevel - 1) * 3;
        int mutationBonus = mutations.size() * 2;
        
        return baseDamage + evolutionBonus + mutationBonus;
    }
    
    /**
     * Get total psionic strength
     */
    public int getTotalPsiStrength() {
        int baseStrength = psiStrength;
        int evolutionBonus = (evolutionLevel - 1) * 10;
        
        return baseStrength + evolutionBonus;
    }
    
    /**
     * Check if alien can use psionic abilities
     */
    public boolean canUsePsionics() {
        return psiStrength > 0 && !hasStatusEffect(com.aliensattack.core.enums.StatusEffect.SUPPRESSED);
    }
    
    @Override
    public String toString() {
        return String.format("Alien[name='%s', type=%s, evolution=%d, mutations=%d, psi=%d]",
                getName(), alienType, evolutionLevel, mutations.size(), getTotalPsiStrength());
    }
    
    // Implementation of missing IUnit abstract methods
    @Override
    public int getCriticalDamageMultiplier() { return 2; }
    
    @Override
    public boolean isSuppressed() { return hasStatusEffect(com.aliensattack.core.enums.StatusEffect.SUPPRESSED); }
    
    @Override
    public int getHeight() { return 1; }
    
    @Override
    public void setSuppressed(boolean suppressed) {
        if (suppressed) {
            addStatusEffect(new com.aliensattack.core.model.StatusEffectData(com.aliensattack.core.enums.StatusEffect.SUPPRESSED, 2, 1));
        } else {
            removeStatusEffect(new com.aliensattack.core.model.StatusEffectData(com.aliensattack.core.enums.StatusEffect.SUPPRESSED, 0, 0));
        }
    }
    
    @Override
    public void applySuppression(int turns) {
        addStatusEffect(new com.aliensattack.core.model.StatusEffectData(com.aliensattack.core.enums.StatusEffect.SUPPRESSED, turns, 1));
    }
    
    @Override
    public int getTotalAttackRange() { return getAttackRange(); }
    
    @Override
    public boolean canPerformSpecialAbility() { return canUsePsionics(); }
    
    @Override
    public void setCriticalDamageMultiplier(int multiplier) { /* Not used in Alien */ }
    
    @Override
    public int getCriticalChance() { return 5; }
    
    @Override
    public int getTotalCriticalChance() { return getCriticalChance(); }
    
    @Override
    public int getSuppressionTurns() { 
        return getStatusEffects().stream()
            .filter(e -> e.getEffect() == com.aliensattack.core.enums.StatusEffect.SUPPRESSED)
            .findFirst()
            .map(com.aliensattack.core.model.StatusEffectData::getDuration)
            .orElse(0);
    }
    
    @Override
    public void setSuppressionTurns(int turns) {
        // Remove existing suppression and apply new duration
        getStatusEffects().removeIf(e -> e.getEffect() == com.aliensattack.core.enums.StatusEffect.SUPPRESSED);
        if (turns > 0) {
            addStatusEffect(new com.aliensattack.core.model.StatusEffectData(com.aliensattack.core.enums.StatusEffect.SUPPRESSED, turns, 1));
        }
    }
    
    @Override
    public void setHeight(int height) { /* Height not used for Aliens */ }
    
    @Override
    public void removeSuppression() {
        getStatusEffects().removeIf(e -> e.getEffect() == com.aliensattack.core.enums.StatusEffect.SUPPRESSED);
    }
    
    @Override
    public void setCriticalChance(int chance) { /* Critical chance not modifiable for Aliens */ }
}

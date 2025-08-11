package com.aliensattack.combat;

import com.aliensattack.core.model.PsionicAbility;
import com.aliensattack.core.enums.PsionicType;
import com.aliensattack.core.enums.PsionicSchool;

/**
 * Factory for creating psionic abilities
 */
public class PsionicAbilityFactory {
    
    /**
     * Creates mind control ability
     */
    public static PsionicAbility createMindControl() {
        PsionicAbility ability = new PsionicAbility("Mind Control", PsionicType.MIND_CONTROL, 8, 3);
        ability.setPreferredSchool(PsionicSchool.MIND_CONTROL);
        ability.setResistancePenetration(15);
        ability.setCooldown(4);
        ability.setDuration(3);
        ability.setIntensity(12);
        return ability;
    }
    
    /**
     * Creates mind scorch ability
     */
    public static PsionicAbility createMindScorch() {
        PsionicAbility ability = new PsionicAbility("Mind Scorch", PsionicType.MIND_SCORCH, 6, 2);
        ability.setPreferredSchool(PsionicSchool.PSYCHIC_WARFARE);
        ability.setResistancePenetration(10);
        ability.setCooldown(2);
        ability.setDuration(1);
        ability.setIntensity(8);
        return ability;
    }
    
    /**
     * Creates domination ability
     */
    public static PsionicAbility createDomination() {
        PsionicAbility ability = new PsionicAbility("Domination", PsionicType.DOMINATION, 10, 5);
        ability.setPreferredSchool(PsionicSchool.MIND_CONTROL);
        ability.setResistancePenetration(20);
        ability.setCooldown(6);
        ability.setDuration(5);
        ability.setIntensity(15);
        return ability;
    }
    
    /**
     * Creates psychic shield ability
     */
    public static PsionicAbility createPsychicShield() {
        PsionicAbility ability = new PsionicAbility("Psychic Shield", PsionicType.PSYCHIC_SHIELD, 0, 2);
        ability.setPreferredSchool(PsionicSchool.PSYCHIC_DEFENSE);
        ability.setResistancePenetration(0);
        ability.setCooldown(1);
        ability.setDuration(3);
        ability.setIntensity(8);
        return ability;
    }
    
    /**
     * Creates telepathy ability
     */
    public static PsionicAbility createTelepathy() {
        PsionicAbility ability = new PsionicAbility("Telepathy", PsionicType.TELEPATHY, 12, 1);
        ability.setPreferredSchool(PsionicSchool.TELEPATHY);
        ability.setResistancePenetration(5);
        ability.setCooldown(1);
        ability.setDuration(2);
        ability.setIntensity(6);
        return ability;
    }
    
    /**
     * Creates psychic blast ability
     */
    public static PsionicAbility createPsychicBlast() {
        PsionicAbility ability = new PsionicAbility("Psychic Blast", PsionicType.PSYCHIC_BLAST, 4, 3);
        ability.setPreferredSchool(PsionicSchool.PSYCHIC_WARFARE);
        ability.setResistancePenetration(8);
        ability.setCooldown(3);
        ability.setDuration(1);
        ability.setIntensity(10);
        ability.setAreaEffect(true, 2); // Area effect with 2 tile radius
        return ability;
    }
    
    /**
     * Creates mind merge ability
     */
    public static PsionicAbility createMindMerge() {
        PsionicAbility ability = new PsionicAbility("Mind Merge", PsionicType.MIND_MERGE, 6, 4);
        ability.setPreferredSchool(PsionicSchool.MIND_MERGE);
        ability.setResistancePenetration(0);
        ability.setCooldown(3);
        ability.setDuration(4);
        ability.setIntensity(12);
        return ability;
    }
    
    /**
     * Creates psychic barrier ability
     */
    public static PsionicAbility createPsychicBarrier() {
        PsionicAbility ability = new PsionicAbility("Psychic Barrier", PsionicType.PSYCHIC_BARRIER, 3, 2);
        ability.setPreferredSchool(PsionicSchool.PSYCHIC_DEFENSE);
        ability.setResistancePenetration(0);
        ability.setCooldown(2);
        ability.setDuration(5);
        ability.setIntensity(25);
        ability.setAreaEffect(true, 1); // Area effect with 1 tile radius
        return ability;
    }
    
    /**
     * Creates teleport ability
     */
    public static PsionicAbility createTeleport() {
        PsionicAbility teleport = new PsionicAbility("Teleport", PsionicType.TELEPORT, 15, 8);
        teleport.setPreferredSchool(PsionicSchool.TELEPORTATION);
        teleport.setResistancePenetration(25);
        teleport.setCooldown(5);
        teleport.setDuration(1);
        teleport.setIntensity(20);
        return teleport;
    }
    
    /**
     * Creates psychic dominance ability
     */
    public static PsionicAbility createPsychicDominance() {
        PsionicAbility dominance = new PsionicAbility("Psychic Dominance", PsionicType.PSYCHIC_DOMINANCE, 12, 6);
        dominance.setPreferredSchool(PsionicSchool.MIND_CONTROL);
        dominance.setResistancePenetration(18);
        dominance.setCooldown(4);
        dominance.setDuration(3);
        dominance.setIntensity(15);
        return dominance;
    }
    
    /**
     * Creates enhanced mind merge ability
     */
    public static PsionicAbility createEnhancedMindMerge() {
        PsionicAbility mindMerge = new PsionicAbility("Mind Merge", PsionicType.MIND_MERGE, 8, 5);
        mindMerge.setPreferredSchool(PsionicSchool.MIND_MERGE);
        mindMerge.setResistancePenetration(0);
        mindMerge.setCooldown(3);
        mindMerge.setDuration(4);
        mindMerge.setIntensity(12);
        return mindMerge;
    }
    
    /**
     * Creates enhanced psychic barrier ability
     */
    public static PsionicAbility createEnhancedPsychicBarrier() {
        PsionicAbility barrier = new PsionicAbility("Psychic Barrier", PsionicType.PSYCHIC_BARRIER, 5, 3);
        barrier.setPreferredSchool(PsionicSchool.PSYCHIC_DEFENSE);
        barrier.setResistancePenetration(0);
        barrier.setCooldown(2);
        barrier.setDuration(5);
        barrier.setIntensity(25);
        barrier.setAreaEffect(true, 2); // Area effect with 2 tile radius
        return barrier;
    }
    
    /**
     * Creates a psionic ability based on type
     */
    public static PsionicAbility createPsionicAbility(PsionicType type) {
        return switch (type) {
            case MIND_CONTROL -> createMindControl();
            case PSYCHIC_BLAST -> createPsychicBlast();
            case TELEPATHY -> createTelepathy();
            case PSYCHIC_SHIELD -> createPsychicShield();
            case TELEPORT -> createTeleport();
            case PSYCHIC_DOMINANCE -> createPsychicDominance();
            case MIND_MERGE -> createEnhancedMindMerge();
            case PSYCHIC_BARRIER -> createEnhancedPsychicBarrier();
            case MIND_SCORCH -> createMindScorch();
            case DOMINATION -> createDomination();
            default -> null;
        };
    }
    
    /**
     * Creates a custom psionic ability with all parameters
     */
    public static PsionicAbility createCustomPsionicAbility(String name, PsionicType type, int psiCost, 
                                                          int range, int cooldown, int duration, 
                                                          int intensity, PsionicSchool preferredSchool, 
                                                          int resistancePenetration, boolean isAreaEffect, 
                                                          int areaRadius) {
        PsionicAbility ability = new PsionicAbility(name, type, psiCost, range);
        ability.setCooldown(cooldown);
        ability.setDuration(duration);
        ability.setIntensity(intensity);
        ability.setPreferredSchool(preferredSchool);
        ability.setResistancePenetration(resistancePenetration);
        ability.setAreaEffect(isAreaEffect, areaRadius);
        return ability;
    }
} 
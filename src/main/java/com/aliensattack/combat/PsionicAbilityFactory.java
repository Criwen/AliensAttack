package com.aliensattack.combat;

import com.aliensattack.core.model.PsionicAbility;
import com.aliensattack.core.enums.PsionicType;

/**
 * Factory for creating psionic abilities
 */
public class PsionicAbilityFactory {
    
    /**
     * Creates mind control ability
     */
    public static PsionicAbility createMindControl() {
        return new PsionicAbility("Mind Control", PsionicType.MIND_CONTROL, 8, 3);
    }
    
    /**
     * Creates mind scorch ability
     */
    public static PsionicAbility createMindScorch() {
        return new PsionicAbility("Mind Scorch", PsionicType.MIND_SCORCH, 6, 2);
    }
    
    /**
     * Creates domination ability
     */
    public static PsionicAbility createDomination() {
        return new PsionicAbility("Domination", PsionicType.DOMINATION, 10, 5);
    }
    
    /**
     * Creates psychic shield ability
     */
    public static PsionicAbility createPsychicShield() {
        return new PsionicAbility("Psychic Shield", PsionicType.PSYCHIC_SHIELD, 0, 2);
    }
    
    /**
     * Creates telepathy ability
     */
    public static PsionicAbility createTelepathy() {
        return new PsionicAbility("Telepathy", PsionicType.TELEPATHY, 12, 1);
    }
    
    /**
     * Creates psychic blast ability
     */
    public static PsionicAbility createPsychicBlast() {
        return new PsionicAbility("Psychic Blast", PsionicType.PSYCHIC_BLAST, 4, 3);
    }
    
    /**
     * Creates mind merge ability
     */
    public static PsionicAbility createMindMerge() {
        return new PsionicAbility("Mind Merge", PsionicType.MIND_MERGE, 6, 4);
    }
    
    /**
     * Creates psychic barrier ability
     */
    public static PsionicAbility createPsychicBarrier() {
        return new PsionicAbility("Psychic Barrier", PsionicType.PSYCHIC_BARRIER, 3, 2);
    }
    
    /**
     * Creates psionic ability based on type
     */
    public static PsionicAbility createPsionicAbility(PsionicType type) {
        return switch (type) {
            case MIND_CONTROL -> createMindControl();
            case MIND_SCORCH -> createMindScorch();
            case DOMINATION -> createDomination();
            case PSYCHIC_SHIELD -> createPsychicShield();
            case TELEPATHY -> createTelepathy();
            case PSYCHIC_BLAST -> createPsychicBlast();
            case MIND_MERGE -> createMindMerge();
            case PSYCHIC_BARRIER -> createPsychicBarrier();
            case PSYCHIC_SCREAM -> createPsychicBlast(); // Use blast as base
            case MIND_SHIELD -> createPsychicShield(); // Use shield as base
            case TELEPORT -> createTelepathy(); // Use telepathy as base
            case PSYCHIC_DOMINANCE -> createMindControl(); // Use mind control as base
        };
    }
} 
package com.aliensattack.core.enums;

/**
 * Defines different types of status effects that can be applied to units
 */
public enum StatusEffectType {
    NONE,           // No status effect
    STUNNED,        // Unit cannot take actions for 1 turn
    BLEEDING,       // Unit takes damage over time
    POISONED,       // Unit takes damage and has reduced accuracy
    BURNING,        // Unit takes fire damage over time
    FROZEN,         // Unit cannot move but has increased defense
    MARKED,         // Unit takes increased damage from attacks
    OVERWATCH,      // Unit will reactively attack enemies that move
    SUPPRESSED,     // Unit is under suppression fire (reduced accuracy, cannot move)
    ACID_BURN,      // Unit takes acid damage over time
    ELECTROCUTED,   // Unit takes electrical damage and is stunned
    RADIATION,      // Unit takes radiation damage and has reduced stats
    CORROSION,      // Unit takes corrosion damage and armor degradation
    FROSTBITE,      // Unit takes cold damage and movement penalty
    GRAPPLED,       // Unit is grappled and cannot move
    BOUND,          // Unit is bound and takes constriction damage
    KNOCKED_DOWN,   // Unit is knocked down and has reduced accuracy
    KNOCKED_BACK,   // Unit is knocked back and repositioned
    GRENADED,       // Unit is affected by grenade explosion
    FLASHBANGED,    // Unit is disoriented and has reduced accuracy
    SMOKED,         // Unit is in smoke and has reduced visibility
    HIDDEN,         // Unit is hidden and has stealth bonuses
    REVEALED,       // Unit is revealed and loses stealth
    PANICKED,       // Unit is panicked and may flee
    HEALING,        // Unit is healing and regaining health
    PROTECTED,      // Unit has increased defense
    EXPOSED,        // Unit has reduced defense
    VULNERABLE,     // Unit takes increased damage
    RESISTANT,      // Unit takes reduced damage
    IMMUNE,         // Unit is immune to certain effects
    WOUNDED,        // Unit has reduced stats due to injury
    CAPTURED,       // Unit is captured and cannot act
    EXTRACTED,      // Unit has been extracted from mission
    DEAD,           // Unit is dead
    MIND_CONTROLLED, // Unit is under mind control
    PSYCHIC_DAMAGE  // Unit takes psychic damage
}

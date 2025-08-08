package com.aliensattack.core.enums;

/**
 * Defines different action types and their costs
 */
public enum ActionType {
    FREE_ACTION,     // Free action (0 action points)
    STANDARD_ACTION, // Standard action (1 action point)
    HEAVY_ACTION,    // Heavy action (2 action points)
    REACTION_ACTION, // Reaction action (triggered by events)
    MOVEMENT_ACTION, // Movement action (1 action point)
    ATTACK_ACTION,   // Attack action (1 action point)
    OVERWATCH_ACTION // Overwatch action (1 action point)
} 
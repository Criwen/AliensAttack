package com.aliensattack.core.enums;

/**
 * Defines different squad tactic types
 * Enhanced with comprehensive XCOM 2 tactical formations
 */
public enum SquadTacticType {
    // Original tactics
    COORDINATED_ATTACK,  // Multiple units attack same target
    FLANKING_MANEUVERS,  // Position for flanking bonuses
    COVERING_FIRE,       // Provide cover for moving allies
    TACTICAL_WITHDRAWAL, // Coordinated retreat
    AMBUSH_SETUP,        // Prepare ambush positions
    SUPPRESSION_NETWORK, // Multiple suppression sources
    MEDICAL_SUPPORT,     // Heal and stabilize allies
    RECONNAISSANCE_TEAM, // Scout and gather intel
    FORMATION,           // Standard formation bonuses
    SUPPRESSION,         // Suppression tactics
    ASSAULT,             // Aggressive assault tactics
    DEFENSIVE,           // Defensive positioning
    MOBILE,              // Mobile warfare tactics
    
    // Enhanced XCOM 2 tactics
    COORDINATED_ASSAULT,    // Coordinated assault with squad bonuses
    DEFENSIVE_FORMATION,    // Defensive formation with high defense
    STEALTH_OPERATION,      // Stealth operation with concealment
    PSIONIC_SYNERGY,        // Psionic synergy with psi bonuses
    RAPID_RESPONSE,         // Rapid response with movement bonuses
    TECHNICAL_SUPPORT,      // Technical support with hacking bonuses
    OVERWATCH_NETWORK,      // Overwatch network with ambush
    CONCEALED_AMBUSH,       // Concealed ambush with stealth
    CHAIN_REACTION,         // Chain reaction with explosive bonuses
    HEALING_CIRCLE,         // Healing circle with medical support
    
    // Formation types for SquadCohesionManager
    OFFENSIVE,              // Offensive formation
    FLANKING,               // Flanking formation
    SUPPORT                 // Support formation
} 
package com.aliensattack.core.exception;

/**
 * Defines different types of errors in XCOM 2 tactical combat system
 */
public enum ErrorType {
    
    // Combat related errors
    INVALID_ATTACK("Invalid attack attempt"),
    INSUFFICIENT_ACTION_POINTS("Not enough action points"),
    OUT_OF_RANGE("Target out of range"),
    INVALID_TARGET("Invalid target selection"),
    WEAPON_MALFUNCTION("Weapon malfunction"),
    
    // Unit related errors
    UNIT_NOT_FOUND("Unit not found"),
    UNIT_DEAD("Unit is dead"),
    UNIT_EXHAUSTED("Unit is exhausted"),
    INVALID_UNIT_STATE("Invalid unit state"),
    
    // Position and movement errors
    INVALID_POSITION("Invalid position"),
    POSITION_OCCUPIED("Position already occupied"),
    MOVEMENT_BLOCKED("Movement blocked"),
    OUT_OF_BOUNDS("Position out of bounds"),
    
    // Ability and skill errors
    ABILITY_NOT_AVAILABLE("Ability not available"),
    ABILITY_ON_COOLDOWN("Ability on cooldown"),
    INSUFFICIENT_RESOURCES("Insufficient resources"),
    INVALID_ABILITY_USE("Invalid ability usage"),
    
    // Equipment and weapon errors
    WEAPON_NOT_EQUIPPED("Weapon not equipped"),
    AMMO_DEPLETED("Ammo depleted"),
    EQUIPMENT_DAMAGED("Equipment damaged"),
    INCOMPATIBLE_EQUIPMENT("Incompatible equipment"),
    
    // UI and interface errors
    UI_ERROR("User interface error"),
    DISPLAY_ERROR("Display error"),
    INPUT_ERROR("Input error"),
    RENDERING_ERROR("Rendering error"),
    
    // System and technical errors
    SYSTEM_ERROR("System error"),
    MEMORY_ERROR("Memory error"),
    NETWORK_ERROR("Network error"),
    FILE_ERROR("File operation error"),
    CRITICAL("Critical system error"),
    
    // Game logic errors
    GAME_STATE_ERROR("Game state error"),
    RULE_VIOLATION("Rule violation"),
    INVALID_OPERATION("Invalid operation"),
    LOGIC_ERROR("Logic error"),
    
    // XCOM 2 specific mechanics errors
    CONCEALMENT_ERROR("Concealment system error"),
    FLANKING_ERROR("Flanking mechanics error"),
    SUPPRESSION_ERROR("Suppression fire error"),
    DESTRUCTIBLE_ENVIRONMENT_ERROR("Destructible environment error"),
    SQUAD_COHESION_ERROR("Squad cohesion error"),
    PSIONIC_ERROR("Psionic combat error"),
    ENVIRONMENTAL_HAZARD_ERROR("Environmental hazard error"),
    SQUAD_SIGHT_ERROR("Squad sight error"),
    HACKING_ERROR("Hacking/technical error"),
    CONCEALMENT_BREAK_ERROR("Concealment break error"),
    OVERWATCH_AMBUSH_ERROR("Overwatch ambush error"),
    HEIGHT_ADVANTAGE_ERROR("Height advantage error"),
    GRENADE_LAUNCHER_ERROR("Grenade launcher error"),
    MEDIKIT_ERROR("Medikit system error"),
    AMMO_TYPE_ERROR("Ammo type error"),
    BLADESTORM_ERROR("Bladestorm error"),
    BLUESCREEN_ERROR("Bluescreen protocol error"),
    VOLATILE_MIX_ERROR("Volatile mix error"),
    RAPID_FIRE_ERROR("Rapid fire error"),
    DEEP_COVER_ERROR("Deep cover error");
    
    private final String description;
    
    ErrorType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
} 
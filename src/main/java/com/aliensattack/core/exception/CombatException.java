package com.aliensattack.core.exception;

/**
 * Exception for combat-related errors in XCOM 2 tactical combat
 */
public class CombatException extends GameException {
    
    public CombatException(String message, ErrorType errorType, String operation) {
        super(message, errorType, "Combat", operation);
    }
    
    public CombatException(String message, ErrorType errorType, String operation, Throwable cause) {
        super(message, errorType, "Combat", operation, cause);
    }
    
    // Convenience constructors for common combat errors
    public static CombatException insufficientActionPoints(String operation) {
        return new CombatException("Insufficient action points for " + operation, 
            ErrorType.INSUFFICIENT_ACTION_POINTS, operation);
    }
    
    public static CombatException invalidAttack(String reason) {
        return new CombatException("Invalid attack: " + reason, 
            ErrorType.INVALID_ATTACK, "attack");
    }
    
    public static CombatException outOfRange(String operation, int range, int distance) {
        return new CombatException(String.format("%s out of range: required %d, actual %d", 
            operation, range, distance), ErrorType.OUT_OF_RANGE, operation);
    }
    
    public static CombatException weaponMalfunction(String weaponName) {
        return new CombatException("Weapon malfunction: " + weaponName, 
            ErrorType.WEAPON_MALFUNCTION, "weapon_use");
    }
    
    public static CombatException invalidTarget(String reason) {
        return new CombatException("Invalid target: " + reason, 
            ErrorType.INVALID_TARGET, "target_selection");
    }
} 
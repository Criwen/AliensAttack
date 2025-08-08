package com.aliensattack.core.exception;

/**
 * Exception for unit-related errors in XCOM 2 tactical combat
 */
public class UnitException extends GameException {
    
    public UnitException(String message, ErrorType errorType, String operation) {
        super(message, errorType, "Unit", operation);
    }
    
    public UnitException(String message, ErrorType errorType, String operation, Throwable cause) {
        super(message, errorType, "Unit", operation, cause);
    }
    
    // Convenience constructors for common unit errors
    public static UnitException unitNotFound(String unitName) {
        return new UnitException("Unit not found: " + unitName, 
            ErrorType.UNIT_NOT_FOUND, "unit_lookup");
    }
    
    public static UnitException unitDead(String unitName) {
        return new UnitException("Unit is dead: " + unitName, 
            ErrorType.UNIT_DEAD, "unit_action");
    }
    
    public static UnitException unitExhausted(String unitName) {
        return new UnitException("Unit is exhausted: " + unitName, 
            ErrorType.UNIT_EXHAUSTED, "unit_action");
    }
    
    public static UnitException invalidUnitState(String unitName, String state) {
        return new UnitException(String.format("Invalid unit state for %s: %s", unitName, state), 
            ErrorType.INVALID_UNIT_STATE, "state_check");
    }
    
    public static UnitException unitNotAlive(String unitName) {
        return new UnitException("Unit is not alive: " + unitName, 
            ErrorType.UNIT_DEAD, "health_check");
    }
    
    public static UnitException unitNotConcealed(String unitName) {
        return new UnitException("Unit is not concealed: " + unitName, 
            ErrorType.INVALID_UNIT_STATE, "concealment_check");
    }
    
    public static UnitException unitAlreadyConcealed(String unitName) {
        return new UnitException("Unit is already concealed: " + unitName, 
            ErrorType.INVALID_UNIT_STATE, "concealment_action");
    }
} 
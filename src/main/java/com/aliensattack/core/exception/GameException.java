package com.aliensattack.core.exception;

/**
 * Base exception class for XCOM 2 tactical combat system
 */
public class GameException extends Exception {
    
    private final ErrorType errorType;
    private final String component;
    private final String operation;
    
    public GameException(String message, ErrorType errorType, String component, String operation) {
        super(message);
        this.errorType = errorType;
        this.component = component;
        this.operation = operation;
    }
    
    public GameException(String message, ErrorType errorType, String component, String operation, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
        this.component = component;
        this.operation = operation;
    }
    
    public ErrorType getErrorType() {
        return errorType;
    }
    
    public String getComponent() {
        return component;
    }
    
    public String getOperation() {
        return operation;
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s: %s", 
            errorType, component, operation, getMessage());
    }
} 
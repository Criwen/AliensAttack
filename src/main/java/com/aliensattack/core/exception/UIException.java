package com.aliensattack.core.exception;

/**
 * Exception for UI-related errors in XCOM 2 tactical combat
 */
public class UIException extends GameException {
    
    public UIException(String message, ErrorType errorType, String operation) {
        super(message, errorType, "UI", operation);
    }
    
    public UIException(String message, ErrorType errorType, String operation, Throwable cause) {
        super(message, errorType, "UI", operation, cause);
    }
    
    // Convenience constructors for common UI errors
    public static UIException displayError(String component, String reason) {
        return new UIException(String.format("Display error in %s: %s", component, reason), 
            ErrorType.DISPLAY_ERROR, "display_update");
    }
    
    public static UIException inputError(String input, String reason) {
        return new UIException(String.format("Input error for %s: %s", input, reason), 
            ErrorType.INPUT_ERROR, "input_processing");
    }
    
    public static UIException renderingError(String component, String reason) {
        return new UIException(String.format("Rendering error in %s: %s", component, reason), 
            ErrorType.RENDERING_ERROR, "rendering");
    }
    
    public static UIException uiError(String component, String operation, String reason) {
        return new UIException(String.format("UI error in %s during %s: %s", component, operation, reason), 
            ErrorType.UI_ERROR, operation);
    }
    
    public static UIException invalidSelection(String selection, String reason) {
        return new UIException(String.format("Invalid selection %s: %s", selection, reason), 
            ErrorType.INPUT_ERROR, "selection");
    }
    
    public static UIException componentNotFound(String componentName) {
        return new UIException("UI component not found: " + componentName, 
            ErrorType.UI_ERROR, "component_lookup");
    }
} 
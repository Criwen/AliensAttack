package com.aliensattack.core.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * Comprehensive error handler for XCOM 2 tactical combat system
 */
@Getter
@Setter
public class ErrorHandler {
    
    private static final Logger logger = Logger.getLogger(ErrorHandler.class.getName());
    private static ErrorHandler instance;
    
    private List<GameException> errorHistory;
    private boolean logToFile;
    private String logFilePath;
    private boolean showUserNotifications;
    private ErrorSeverity minimumSeverity;
    
    public enum ErrorSeverity {
        INFO,       // Informational messages
        WARNING,    // Warnings that don't stop execution
        ERROR,      // Errors that affect functionality
        CRITICAL    // Critical errors that may crash the system
    }
    
    private ErrorHandler() {
        this.errorHistory = new ArrayList<>();
        this.logToFile = true;
        this.logFilePath = "xcom2_errors.log";
        this.showUserNotifications = true;
        this.minimumSeverity = ErrorSeverity.WARNING;
        
        setupLogger();
    }
    
    public static ErrorHandler getInstance() {
        if (instance == null) {
            instance = new ErrorHandler();
        }
        return instance;
    }
    
    private void setupLogger() {
        try {
            // Create file handler
            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            
            // Create console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);
            
            logger.setLevel(Level.ALL);
            
        } catch (IOException e) {
            System.err.println("Failed to setup error logging: " + e.getMessage());
        }
    }
    
    /**
     * Handle a game exception
     */
    public void handleException(GameException exception) {
        handleException(exception, ErrorSeverity.ERROR);
    }
    
    /**
     * Handle a game exception with specified severity
     */
    public void handleException(GameException exception, ErrorSeverity severity) {
        // Add to history
        errorHistory.add(exception);
        
        // Log the exception
        logException(exception, severity);
        
        // Show user notification if enabled
        if (showUserNotifications && severity.ordinal() >= minimumSeverity.ordinal()) {
            showUserNotification(exception, severity);
        }
        
        // Handle critical errors
        if (severity == ErrorSeverity.CRITICAL) {
            handleCriticalError(exception);
        }
    }
    
    /**
     * Handle a generic exception
     */
    public void handleException(Exception exception, String component, String operation) {
        GameException gameException = new GameException(
            exception.getMessage(), 
            ErrorType.SYSTEM_ERROR, 
            component, 
            operation, 
            exception
        );
        handleException(gameException, ErrorSeverity.ERROR);
    }
    
    /**
     * Log exception to file and console
     */
    private void logException(GameException exception, ErrorSeverity severity) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String logMessage = String.format("[%s] [%s] %s", 
            timestamp, severity, exception.toString());
        
        switch (severity) {
            case INFO:
                logger.info(logMessage);
                break;
            case WARNING:
                logger.warning(logMessage);
                break;
            case ERROR:
                logger.severe(logMessage);
                break;
            case CRITICAL:
                logger.severe("CRITICAL: " + logMessage);
                break;
        }
        
        // Log stack trace for errors and critical
        if (severity == ErrorSeverity.ERROR || severity == ErrorSeverity.CRITICAL) {
            logger.severe("Stack trace:");
            for (StackTraceElement element : exception.getStackTrace()) {
                logger.severe("\t" + element.toString());
            }
        }
    }
    
    /**
     * Show user notification
     */
    private void showUserNotification(GameException exception, ErrorSeverity severity) {
        String title = "XCOM 2 Error - " + severity.name();
        String message = String.format("Error: %s\nComponent: %s\nOperation: %s", 
            exception.getMessage(), 
            exception.getComponent(), 
            exception.getOperation());
        
        // In a real application, this would show a dialog
        System.err.println("=== USER NOTIFICATION ===");
        System.err.println(title);
        System.err.println(message);
        System.err.println("========================");
    }
    
    /**
     * Handle critical errors
     */
    private void handleCriticalError(GameException exception) {
        System.err.println("CRITICAL ERROR DETECTED!");
        System.err.println("Component: " + exception.getComponent());
        System.err.println("Operation: " + exception.getOperation());
        System.err.println("Error: " + exception.getMessage());
        
        // Save error report
        saveErrorReport(exception);
        
        // In a real application, you might want to:
        // - Save game state
        // - Show emergency dialog
        // - Attempt recovery
        // - Exit gracefully
    }
    
    /**
     * Save detailed error report
     */
    private void saveErrorReport(GameException exception) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("error_report.txt", true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            
            writer.println("=== XCOM 2 ERROR REPORT ===");
            writer.println("Timestamp: " + timestamp);
            writer.println("Error Type: " + exception.getErrorType());
            writer.println("Component: " + exception.getComponent());
            writer.println("Operation: " + exception.getOperation());
            writer.println("Message: " + exception.getMessage());
            writer.println("Stack Trace:");
            
            for (StackTraceElement element : exception.getStackTrace()) {
                writer.println("\t" + element.toString());
            }
            
            writer.println("===========================");
            writer.println();
            
        } catch (IOException e) {
            System.err.println("Failed to save error report: " + e.getMessage());
        }
    }
    
    /**
     * Get error statistics
     */
    public ErrorStatistics getErrorStatistics() {
        ErrorStatistics stats = new ErrorStatistics();
        
        for (GameException error : errorHistory) {
            stats.totalErrors++;
            
            // Count by component
            stats.errorsByComponent.merge(error.getComponent(), 1, Integer::sum);
            
            // Count by error type
            stats.errorsByType.merge(error.getErrorType().name(), 1, Integer::sum);
        }
        
        return stats;
    }
    
    /**
     * Clear error history
     */
    public void clearErrorHistory() {
        errorHistory.clear();
        logger.info("Error history cleared");
    }
    
    /**
     * Get recent errors
     */
    public List<GameException> getRecentErrors(int count) {
        int startIndex = Math.max(0, errorHistory.size() - count);
        return new ArrayList<>(errorHistory.subList(startIndex, errorHistory.size()));
    }
    
    /**
     * Check if system is in error state
     */
    public boolean isInErrorState() {
        return errorHistory.stream()
            .anyMatch(error -> error.getErrorType() == ErrorType.SYSTEM_ERROR ||
                             error.getErrorType() == ErrorType.MEMORY_ERROR ||
                             error.getErrorType() == ErrorType.CRITICAL);
    }
    
    /**
     * Error statistics class
     */
    public static class ErrorStatistics {
        public int totalErrors = 0;
        public java.util.Map<String, Integer> errorsByComponent = new java.util.HashMap<>();
        public java.util.Map<String, Integer> errorsByType = new java.util.HashMap<>();
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Error Statistics:\n");
            sb.append("Total Errors: ").append(totalErrors).append("\n");
            sb.append("By Component:\n");
            errorsByComponent.forEach((component, count) -> 
                sb.append("  ").append(component).append(": ").append(count).append("\n"));
            sb.append("By Type:\n");
            errorsByType.forEach((type, count) -> 
                sb.append("  ").append(type).append(": ").append(count).append("\n"));
            return sb.toString();
        }
    }
} 
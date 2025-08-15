package com.aliensattack.core;

import lombok.extern.log4j.Log4j2;

/**
 * Centralized logging utility for the AliensAttack application.
 * Provides consistent logging methods and configuration.
 */
@Log4j2
public class GameLogManager {
    
    private GameLogManager() {
        // Utility class - prevent instantiation
    }
    
    /**
     * Log application startup
     */
    public static void logApplicationStart() {
        log.info("=== AliensAttack Application Starting ===");
        log.info("Java Version: {}", System.getProperty("java.version"));
        log.info("OS: {}", System.getProperty("os.name"));
        log.info("Working Directory: {}", System.getProperty("user.dir"));
        log.info("Available Processors: {}", Runtime.getRuntime().availableProcessors());
        log.info("Max Memory: {} MB", Runtime.getRuntime().maxMemory() / (1024 * 1024));
        log.info("Total Memory: {} MB", Runtime.getRuntime().totalMemory() / (1024 * 1024));
    }
    
    /**
     * Log application shutdown with reason
     */
    public static void logApplicationShutdown(String reason) {
        log.info("=== AliensAttack Application Shutting Down ===");
        log.info("Shutdown Reason: {}", reason);
        log.info("Timestamp: {}", java.time.LocalDateTime.now());
        log.info("Final Memory Usage: {} MB", Runtime.getRuntime().totalMemory() / (1024 * 1024));
        log.info("Free Memory: {} MB", Runtime.getRuntime().freeMemory() / (1024 * 1024));
    }
    
    /**
     * Log application shutdown without specific reason
     */
    public static void logApplicationShutdown() {
        logApplicationShutdown("Normal termination");
    }
    
    /**
     * Log action start
     */
    public static void logActionStart(String actionType, String performerName, String targetInfo) {
        log.info("ACTION START: {} by {} targeting {}", actionType, performerName, targetInfo);
    }
    
    /**
     * Log action execution
     */
    public static void logActionExecution(String actionType, String performerName, String details) {
        log.info("ACTION EXECUTE: {} by {} - {}", actionType, performerName, details);
    }
    
    /**
     * Log action completion
     */
    public static void logActionComplete(String actionType, String performerName, boolean success, String result) {
        String status = success ? "SUCCESS" : "FAILED";
        log.info("ACTION COMPLETE: {} by {} - {}: {}", actionType, performerName, status, result);
    }
    
    /**
     * Log action validation
     */
    public static void logActionValidation(String actionType, String performerName, boolean canPerform, String reason) {
        String status = canPerform ? "VALID" : "INVALID";
        log.info("ACTION VALIDATION: {} by {} - {}: {}", actionType, performerName, status, reason);
    }
    
    /**
     * Log action point spending
     */
    public static void logActionPointSpending(String performerName, int cost, int remaining) {
        log.info("ACTION POINTS: {} spent {} points, {} remaining", performerName, cost, remaining);
    }
    
    /**
     * Log combat event
     */
    public static void logCombatEvent(String event, Object... params) {
        log.info("COMBAT: {} - {}", event, String.format(String.valueOf(event), params));
    }
    
    /**
     * Log mission event
     */
    public static void logMissionEvent(String event, Object... params) {
        log.info("MISSION: {} - {}", event, String.format(String.valueOf(event), params));
    }
    
    /**
     * Log unit action
     */
    public static void logUnitAction(String unitName, String action, Object... params) {
        log.info("UNIT [{}]: {} - {}", unitName, action, String.format(String.valueOf(action), params));
    }
    
    /**
     * Log system initialization
     */
    public static void logSystemInit(String systemName) {
        log.info("SYSTEM: Initializing {}", systemName);
    }
    
    /**
     * Log system ready
     */
    public static void logSystemReady(String systemName) {
        log.info("SYSTEM: {} ready", systemName);
    }
    
    /**
     * Log error with context
     */
    public static void logError(String context, Throwable error) {
        log.error("ERROR in {}: {}", context, error.getMessage(), error);
    }
    
    /**
     * Log warning with context
     */
    public static void logWarning(String context, String message) {
        log.warn("WARNING in {}: {}", context, message);
    }
    
    /**
     * Log debug information
     */
    public static void logDebug(String context, String message) {
        log.debug("DEBUG [{}]: {}", context, message);
    }
    
    /**
     * Log game state change
     */
    public static void logGameStateChange(String fromState, String toState, String reason) {
        log.info("GAME STATE: {} -> {} (Reason: {})", fromState, toState, reason);
    }
    
    /**
     * Log user interaction
     */
    public static void logUserInteraction(String interaction, String details) {
        log.info("USER INTERACTION: {} - {}", interaction, details);
    }
    
    /**
     * Log performance metrics
     */
    public static void logPerformance(String operation, long durationMs) {
        log.info("PERFORMANCE: {} completed in {}ms", operation, durationMs);
    }
    
    // ===== NEW EXPANDED LOGGING METHODS =====
    
    /**
     * Log unit creation
     */
    public static void logUnitCreation(String unitType, String unitName, String details) {
        log.info("UNIT CREATION: {} - {} - {}", unitType, unitName, details);
    }
    
    /**
     * Log unit destruction/death
     */
    public static void logUnitDestruction(String unitName, String reason, String details) {
        log.info("UNIT DESTRUCTION: {} - Reason: {} - {}", unitName, reason, details);
    }
    
    /**
     * Log unit movement
     */
    public static void logUnitMovement(String unitName, String fromPos, String toPos, int cost) {
        log.info("UNIT MOVEMENT: {} moved from {} to {} (Cost: {} AP)", unitName, fromPos, toPos, cost);
    }
    
    /**
     * Log unit health change
     */
    public static void logUnitHealthChange(String unitName, int oldHealth, int newHealth, int change, String reason) {
        String changeType = change > 0 ? "HEALED" : "DAMAGED";
        log.info("UNIT HEALTH: {} {} {} HP ({} -> {}) - Reason: {}", 
            unitName, changeType, Math.abs(change), oldHealth, newHealth, reason);
    }
    
    /**
     * Log unit status effect
     */
    public static void logUnitStatusEffect(String unitName, String effectType, String effect, boolean applied) {
        String action = applied ? "APPLIED" : "REMOVED";
        log.info("UNIT STATUS: {} {} {} - {}", unitName, action, effectType, effect);
    }
    
    /**
     * Log equipment change
     */
    public static void logEquipmentChange(String unitName, String equipmentType, String oldItem, String newItem) {
        log.info("EQUIPMENT CHANGE: {} {} changed from {} to {}", unitName, equipmentType, oldItem, newItem);
    }
    
    /**
     * Log inventory operation
     */
    public static void logInventoryOperation(String unitName, String operation, String item, int quantity) {
        log.info("INVENTORY: {} {} {} (Quantity: {})", unitName, operation, item, quantity);
    }
    
    /**
     * Log AI decision
     */
    public static void logAIDecision(String aiName, String decision, String reasoning) {
        log.info("AI DECISION: {} decided to {} - Reasoning: {}", aiName, decision, reasoning);
    }
    
    /**
     * Log AI behavior change
     */
    public static void logAIBehaviorChange(String aiName, String oldBehavior, String newBehavior, String trigger) {
        log.info("AI BEHAVIOR: {} changed from {} to {} - Trigger: {}", aiName, oldBehavior, newBehavior, trigger);
    }
    
    /**
     * Log terrain interaction
     */
    public static void logTerrainInteraction(String unitName, String terrainType, String interaction, String result) {
        log.info("TERRAIN: {} {} on {} - Result: {}", unitName, interaction, terrainType, result);
    }
    
    /**
     * Log weather effect
     */
    public static void logWeatherEffect(String weatherType, String effect, String impact) {
        log.info("WEATHER: {} - {} - Impact: {}", weatherType, effect, impact);
    }
    
    /**
     * Log mission objective
     */
    public static void logMissionObjective(String objectiveType, String status, String details) {
        log.info("MISSION OBJECTIVE: {} - {} - {}", objectiveType, status, details);
    }
    
    /**
     * Log turn progression
     */
    public static void logTurnProgression(int turnNumber, String phase, String activeUnit, String details) {
        log.info("TURN {} - {} - Active: {} - {}", turnNumber, phase, activeUnit, details);
    }
    
    /**
     * Log squad operation
     */
    public static void logSquadOperation(String squadName, String operation, String details) {
        log.info("SQUAD [{}]: {} - {}", squadName, operation, details);
    }
    
    /**
     * Log research progress
     */
    public static void logResearchProgress(String researchType, int progress, String details) {
        log.info("RESEARCH: {} - Progress: {}% - {}", researchType, progress, details);
    }
    
    /**
     * Log technology unlock
     */
    public static void logTechnologyUnlock(String technology, String requirements, String benefits) {
        log.info("TECHNOLOGY UNLOCKED: {} - Requirements: {} - Benefits: {}", technology, requirements, benefits);
    }
    
    /**
     * Log resource management
     */
    public static void logResourceManagement(String resourceType, int oldAmount, int newAmount, String operation) {
        int change = newAmount - oldAmount;
        String changeType = change > 0 ? "GAINED" : "SPENT";
        log.info("RESOURCE: {} {} {} {} ({} -> {})", 
            resourceType, changeType, Math.abs(change), resourceType, oldAmount, newAmount);
    }
    
    /**
     * Log save/load operation
     */
    public static void logSaveLoadOperation(String operation, String filename, boolean success, String details) {
        String status = success ? "SUCCESS" : "FAILED";
        log.info("SAVE/LOAD: {} {} - {} - {}", operation, filename, status, details);
    }
    
    /**
     * Log network operation
     */
    public static void logNetworkOperation(String operation, String endpoint, boolean success, String details) {
        String status = success ? "SUCCESS" : "FAILED";
        log.info("NETWORK: {} {} - {} - {}", operation, endpoint, status, details);
    }
    
    /**
     * Log configuration change
     */
    public static void logConfigurationChange(String configType, String oldValue, String newValue, String reason) {
        log.info("CONFIG: {} changed from {} to {} - Reason: {}", configType, oldValue, newValue, reason);
    }
    
    /**
     * Log security event
     */
    public static void logSecurityEvent(String eventType, String severity, String details) {
        log.warn("SECURITY: {} - {} - {}", eventType, severity, details);
    }
    
    /**
     * Log memory usage
     */
    public static void logMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long maxMemory = runtime.maxMemory();
        
        log.info("MEMORY USAGE: Used: {} MB, Free: {} MB, Total: {} MB, Max: {} MB", 
            usedMemory / (1024 * 1024), 
            freeMemory / (1024 * 1024), 
            totalMemory / (1024 * 1024), 
            maxMemory / (1024 * 1024));
    }
    
    /**
     * Log thread information
     */
    public static void logThreadInfo() {
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        while (rootGroup.getParent() != null) {
            rootGroup = rootGroup.getParent();
        }
        
        int activeThreads = rootGroup.activeCount();
        log.info("THREAD INFO: Active threads: {}", activeThreads);
    }
    
    /**
     * Log system resource usage
     */
    public static void logSystemResources() {
        logMemoryUsage();
        logThreadInfo();
        log.info("SYSTEM RESOURCES: Available processors: {}", Runtime.getRuntime().availableProcessors());
    }
}

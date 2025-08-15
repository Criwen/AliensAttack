package com.aliensattack;

import com.aliensattack.core.GameLogManager;
import com.aliensattack.core.patterns.GameEngine;
import com.aliensattack.core.config.GameConfig;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.swing.*;

import com.aliensattack.core.events.handlers.LoggingEventHandler;

/**
 * Main application class for Aliens Attack - XCOM 2 Tactical Combat
 */
public class AliensAttackApplication {
    private static final Logger log = LogManager.getLogger(AliensAttackApplication.class);
    
    public static void main(String[] args) {
        // Log application startup with enhanced system information
        GameLogManager.logApplicationStart();
        
        // Initialize configuration at startup
        GameConfig.initialize();
        
        // Subscribe logging handler to events
        LoggingEventHandler.register();
        
        // Log system resources at startup
        GameLogManager.logSystemResources();
        
        // Log configuration information
        GameLogManager.logConfigurationChange("Application", "Starting", "Running", "Application launch");
        
        // Add shutdown hook for graceful logging
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            GameLogManager.logApplicationShutdown("JVM shutdown signal received");
        }));
        
        // Launch the unified game window using Singleton pattern
        SwingUtilities.invokeLater(() -> {
            try {
                // Log system initialization
                GameLogManager.logSystemInit("Game Engine");
                
                // Get the singleton GameEngine instance
                GameEngine gameEngine = GameEngine.getInstance();
                
                // Log system ready
                GameLogManager.logSystemReady("Game Engine");
                
                // Start the game
                gameEngine.startGame();
                log.info("Game window launched");
                // Log game state change and user interaction
                GameLogManager.logGameStateChange("INIT", "RUNNING", "Game window launched successfully");
                GameLogManager.logUserInteraction("Application Launch", "Game window displayed to user");
                GameLogManager.logMissionObjective("Application Launch", "COMPLETED", "Startup sequence complete");
                GameLogManager.logPerformance("Application startup", System.currentTimeMillis());
                
            } catch (Exception e) {
                log.error("Error launching game window", e);
                GameLogManager.logError("Game Launch", e);
                GameLogManager.logApplicationShutdown("Error during game launch: " + e.getMessage());
                
                // Log security event for critical errors
                GameLogManager.logSecurityEvent("Critical Error", "HIGH", 
                    String.format("Game launch failed: %s", e.getMessage()));
            }
        });
    }
} 
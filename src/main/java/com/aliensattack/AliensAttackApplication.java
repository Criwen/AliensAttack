package com.aliensattack;

import com.aliensattack.core.GameLogManager;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.ui.GameWindow;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.swing.*;
import com.aliensattack.core.events.handlers.LoggingEventHandler;

/**
 * Main application class for Aliens Attack - XCOM 2 Tactical Combat
 */
public class AliensAttackApplication {
    private static final Logger log = LogManager.getLogger(AliensAttackApplication.class);
    private static volatile boolean shutdownRequested = false;
    
    public static void main(String[] args) {
        // Set up shutdown hook for graceful termination
        setupShutdownHook();
        
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
        
        // Launch the main game window directly
        SwingUtilities.invokeLater(() -> {
            try {
                // Log system initialization
                GameLogManager.logSystemInit("Game Window");
                
                // Show main game window directly
                launchMainGame();
                
                // Log system ready
                GameLogManager.logSystemReady("Game Window");
                
            } catch (Exception e) {
                log.error("Error launching game window", e);
                GameLogManager.logError("Game Window Launch", e);
                GameLogManager.logApplicationShutdown("Error during game window launch: " + e.getMessage());
                
                // Log security event for critical errors
                GameLogManager.logSecurityEvent("Critical Error", "HIGH", 
                    String.format("Game window launch failed: %s", e.getMessage()));
                
                // Exit gracefully on error
                System.exit(1);
            }
        });
    }
    
    private static void launchMainGame() {
        try {
            // Log system initialization
            GameLogManager.logSystemInit("Game Engine");
            
            // Create and show the main game window directly
            GameWindow gameWindow = new GameWindow();
            gameWindow.showWindow();
            
            // Log system ready
            GameLogManager.logSystemReady("Game Engine");
            
            log.info("Game window launched");
            
            // Log game state change and user interaction
            GameLogManager.logGameStateChange("INIT", "RUNNING", "Game window launched successfully");
            GameLogManager.logUserInteraction("Application Launch", "Game window displayed to user");
            GameLogManager.logMissionObjective("Application Launch", "COMPLETED", "Startup sequence complete");
            GameLogManager.logPerformance("Application startup", System.currentTimeMillis());
            
            // Keep the main thread alive
            gameWindow.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    log.info("Game window closed, exiting application");
                    System.exit(0);
                }
            });
            
        } catch (Exception e) {
            log.error("Error launching game window", e);
            GameLogManager.logError("Game Launch", e);
            GameLogManager.logApplicationShutdown("Error during game launch: " + e.getMessage());
            
            // Log security event for critical errors
            GameLogManager.logSecurityEvent("Critical Error", "HIGH", 
                String.format("Game launch failed: %s", e.getMessage()));
            
            // Exit gracefully on error
            System.exit(1);
        }
    }
    
    private static void setupShutdownHook() {
        // Handle Ctrl+C gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!shutdownRequested) {
                shutdownRequested = true;
                log.info("Shutdown signal received, cleaning up...");
                GameLogManager.logApplicationShutdown("JVM shutdown signal received");
                
                // Force exit after cleanup
                System.exit(0);
            }
        }));
        
        // Set up signal handlers for Windows
        try {
            // Handle Windows-specific signals
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                // Use reflection to access Windows-specific signal handling if available
                try {
                    Class<?> signalClass = Class.forName("sun.misc.Signal");
                    Object signal = signalClass.getConstructor(String.class).newInstance("INT");
                    Class<?> signalHandlerClass = Class.forName("sun.misc.SignalHandler");
                    Object handler = signalHandlerClass.getMethod("handle", signalClass).invoke(null, signal);
                    signalClass.getMethod("handle", signalClass, signalHandlerClass).invoke(null, signal, handler);
                } catch (Exception e) {
                    // Fallback: use basic signal handling
                    log.debug("Advanced signal handling not available, using basic shutdown");
                }
            }
        } catch (Exception e) {
            log.debug("Signal handling setup failed, using basic shutdown: " + e.getMessage());
        }
    }
} 
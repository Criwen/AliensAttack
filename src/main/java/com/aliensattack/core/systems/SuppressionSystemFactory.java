package com.aliensattack.core.systems;

import com.aliensattack.core.interfaces.ISuppressionSystem;
import com.aliensattack.core.config.GameConfig;
import lombok.extern.log4j.Log4j2;

/**
 * Factory for creating and managing suppression system implementations
 * Integrates the ISuppressionSystem interface into the main game flow
 */
@Log4j2
public class SuppressionSystemFactory {
    
    private static ISuppressionSystem instance;
    
    /**
     * Get or create suppression system instance
     */
    public static ISuppressionSystem getSuppressionSystem() {
        if (instance == null) {
            instance = new SuppressionSystem();
            log.info("Suppression system initialized");
        }
        return instance;
    }
    
    /**
     * Create suppression system with specific configuration
     */
    public static ISuppressionSystem createSuppressionSystem(boolean enableAdvancedFeatures) {
        if (enableAdvancedFeatures) {
            log.info("Creating advanced suppression system");
            return new AdvancedSuppressionSystem();
        } else {
            log.info("Creating basic suppression system");
            return new SuppressionSystem();
        }
    }
    
    /**
     * Reset suppression system instance
     */
    public static void resetSuppressionSystem() {
        instance = null;
        log.debug("Suppression system reset");
    }
    
    /**
     * Check if suppression system is enabled
     */
    public static boolean isSuppressionEnabled() {
        return GameConfig.getBoolean("combat.suppression.enabled", true);
    }
    
    /**
     * Get suppression system configuration
     */
    public static SuppressionConfig getSuppressionConfig() {
        return new SuppressionConfig(
            GameConfig.getInt("combat.suppression.accuracy.penalty", 30),
            GameConfig.getInt("combat.suppression.movement.penalty", 50),
            GameConfig.getInt("combat.suppression.duration", 2),
            GameConfig.getDouble("combat.suppression.break.chance", 0.3)
        );
    }
    
    /**
     * Configuration class for suppression system
     */
    public static class SuppressionConfig {
        private final int accuracyPenalty;
        private final int movementPenalty;
        private final int duration;
        private final double breakChance;
        
        public SuppressionConfig(int accuracyPenalty, int movementPenalty, int duration, double breakChance) {
            this.accuracyPenalty = accuracyPenalty;
            this.movementPenalty = movementPenalty;
            this.duration = duration;
            this.breakChance = breakChance;
        }
        
        public int getAccuracyPenalty() { return accuracyPenalty; }
        public int getMovementPenalty() { return movementPenalty; }
        public int getDuration() { return duration; }
        public double getBreakChance() { return breakChance; }
    }
}

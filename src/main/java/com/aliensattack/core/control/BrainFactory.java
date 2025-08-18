package com.aliensattack.core.control;

import com.aliensattack.core.interfaces.IBrain;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Factory for creating different types of brain systems
 * Implements Factory pattern for brain creation
 */
@Log4j2
public class BrainFactory {
    
    private static final String HUMAN_BRAIN_PREFIX = "HUMAN_";
    private static final String AI_BRAIN_PREFIX = "AI_";
    private static final String REMOTE_BRAIN_PREFIX = "REMOTE_";
    private static final String AUTONOMOUS_BRAIN_PREFIX = "AUTO_";
    
    /**
     * Create a human brain for player control
     */
    public static HumanBrain createHumanBrain(String playerId) {
        return createHumanBrain(playerId, 5); // Default priority 5
    }
    
    /**
     * Create a human brain with custom priority
     */
    public static HumanBrain createHumanBrain(String playerId, int priority) {
        String brainId = generateBrainId(HUMAN_BRAIN_PREFIX);
        HumanBrain brain = new HumanBrain(playerId, brainId, priority);
        log.info("Created human brain {} for player {}", brainId, playerId);
        return brain;
    }
    
    /**
     * Create a human brain with custom settings
     */
    public static HumanBrain createHumanBrain(String playerId, int priority, 
                                            List<String> preferredActions, 
                                            boolean enableAutoActions, 
                                            int reactionTime) {
        String brainId = generateBrainId(HUMAN_BRAIN_PREFIX);
        HumanBrain brain = new HumanBrain(playerId, brainId, priority, 
                                        preferredActions, enableAutoActions, reactionTime);
        log.info("Created human brain {} for player {} with custom settings", brainId, playerId);
        return brain;
    }
    
    /**
     * Create an AI brain with default settings
     */
    public static AIBrain createAIBrain() {
        return createAIBrain(5, 0.5); // Default priority 5, balanced aggression
    }
    
    /**
     * Create an AI brain with custom intelligence and aggression
     */
    public static AIBrain createAIBrain(int intelligenceLevel, double aggressionLevel) {
        return createAIBrain(intelligenceLevel, aggressionLevel, 5); // Default priority 5
    }
    
    /**
     * Create an AI brain with custom settings
     */
    public static AIBrain createAIBrain(int intelligenceLevel, double aggressionLevel, int priority) {
        String brainId = generateBrainId(AI_BRAIN_PREFIX);
        AIBrain brain = new AIBrain(brainId, priority, intelligenceLevel, aggressionLevel);
        log.info("Created AI brain {} with intelligence {} and aggression {}", 
                brainId, intelligenceLevel, aggressionLevel);
        return brain;
    }
    
    /**
     * Create an aggressive AI brain
     */
    public static AIBrain createAggressiveAIBrain() {
        return createAIBrain(7, 0.9, 6); // High intelligence, high aggression, high priority
    }
    
    /**
     * Create a defensive AI brain
     */
    public static AIBrain createDefensiveAIBrain() {
        return createAIBrain(6, 0.2, 4); // Medium intelligence, low aggression, medium priority
    }
    
    /**
     * Create a tactical AI brain
     */
    public static AIBrain createTacticalAIBrain() {
        return createAIBrain(9, 0.5, 7); // Very high intelligence, balanced aggression, high priority
    }
    
    /**
     * Create a basic AI brain for simple enemies
     */
    public static AIBrain createBasicAIBrain() {
        return createAIBrain(3, 0.6, 3); // Low intelligence, medium aggression, low priority
    }
    
    /**
     * Create a boss AI brain for challenging encounters
     */
    public static AIBrain createBossAIBrain() {
        return createAIBrain(10, 0.8, 10); // Maximum intelligence, high aggression, maximum priority
    }
    
    /**
     * Create a brain based on type string
     */
    public static IBrain createBrain(String brainType, String identifier, int priority) {
        return switch (brainType.toUpperCase()) {
            case "HUMAN" -> createHumanBrain(identifier, priority);
            case "AI" -> createAIBrain(5, 0.5, priority);
            case "AGGRESSIVE_AI" -> createAggressiveAIBrain();
            case "DEFENSIVE_AI" -> createDefensiveAIBrain();
            case "TACTICAL_AI" -> createTacticalAIBrain();
            case "BASIC_AI" -> createBasicAIBrain();
            case "BOSS_AI" -> createBossAIBrain();
            default -> {
                log.warn("Unknown brain type: {}, creating default AI brain", brainType);
                yield createAIBrain(5, 0.5, priority);
            }
        };
    }
    
    /**
     * Create multiple AI brains for a squad
     */
    public static List<AIBrain> createAISquad(int squadSize, int baseIntelligence, double baseAggression) {
        List<AIBrain> squad = new ArrayList<>();
        
        for (int i = 0; i < squadSize; i++) {
            // Vary intelligence and aggression slightly for squad diversity
            int intelligence = Math.max(1, Math.min(10, baseIntelligence + (int)(Math.random() * 3) - 1));
            double aggression = Math.max(0.0, Math.min(1.0, baseAggression + (Math.random() * 0.4) - 0.2));
            int priority = 5 + i; // Slightly different priorities
            
            AIBrain brain = createAIBrain(intelligence, aggression, priority);
            squad.add(brain);
        }
        
        log.info("Created AI squad with {} brains", squadSize);
        return squad;
    }
    
    /**
     * Create a mixed squad with different brain types
     */
    public static List<IBrain> createMixedSquad(int humanCount, int aiCount, int aggressiveCount) {
        List<IBrain> squad = new ArrayList<>();
        
        // Add human brains
        for (int i = 0; i < humanCount; i++) {
            String playerId = "PLAYER_" + (i + 1);
            HumanBrain brain = createHumanBrain(playerId, 5 + i);
            squad.add(brain);
        }
        
        // Add balanced AI brains
        for (int i = 0; i < aiCount; i++) {
            AIBrain brain = createAIBrain(6, 0.5, 4 + i);
            squad.add(brain);
        }
        
        // Add aggressive AI brains
        for (int i = 0; i < aggressiveCount; i++) {
            AIBrain brain = createAggressiveAIBrain();
            squad.add(brain);
        }
        
        log.info("Created mixed squad with {} humans, {} AI, {} aggressive AI", 
                humanCount, aiCount, aggressiveCount);
        return squad;
    }
    
    /**
     * Create a brain for a specific unit type
     */
    public static IBrain createBrainForUnitType(String unitType, String unitId, int priority) {
        return switch (unitType.toUpperCase()) {
            case "SOLDIER", "PLAYER_UNIT" -> createHumanBrain("PLAYER_" + unitId, priority);
            case "ALIEN" -> createAIBrain(7, 0.8, priority); // Use enhanced AI for Alien units
            case "ADVENT_TROOPER", "BASIC_ENEMY" -> createBasicAIBrain();
            case "ADVENT_OFFICER", "COMMANDER" -> createTacticalAIBrain();
            case "BERSERKER", "AGGRESSIVE_ENEMY" -> createAggressiveAIBrain();
            case "MUTON", "HEAVY_ENEMY" -> createDefensiveAIBrain();
            case "AVATAR", "BOSS_ENEMY" -> createBossAIBrain();
            case "ALIEN_RULER", "ELITE_ENEMY" -> createAIBrain(8, 0.7, 8);
            default -> {
                log.warn("Unknown unit type: {}, creating default AI brain", unitType);
                yield createAIBrain(5, 0.5, priority);
            }
        };
    }
    
    /**
     * Generate a unique brain ID
     */
    private static String generateBrainId(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * Validate brain configuration
     */
    public static boolean validateBrainConfiguration(int intelligenceLevel, double aggressionLevel, int priority) {
        if (intelligenceLevel < 1 || intelligenceLevel > 10) {
            log.warn("Invalid intelligence level: {} (must be 1-10)", intelligenceLevel);
            return false;
        }
        
        if (aggressionLevel < 0.0 || aggressionLevel > 1.0) {
            log.warn("Invalid aggression level: {} (must be 0.0-1.0)", aggressionLevel);
            return false;
        }
        
        if (priority < 1 || priority > 10) {
            log.warn("Invalid priority: {} (must be 1-10)", priority);
            return false;
        }
        
        return true;
    }
    
    /**
     * Get brain type description
     */
    public static String getBrainTypeDescription(IBrain.BrainType brainType) {
        return switch (brainType) {
            case HUMAN -> "Human player control with manual decision making";
            case AI -> "Artificial Intelligence with automated decision making";
            case REMOTE -> "Remote player control over network";
            case AUTONOMOUS -> "Autonomous behavior system";
        };
    }
    
    /**
     * Get recommended brain configuration for difficulty level
     */
    public static BrainConfiguration getRecommendedConfiguration(String difficulty) {
        return switch (difficulty.toUpperCase()) {
            case "EASY" -> new BrainConfiguration(3, 0.3, 3);
            case "NORMAL" -> new BrainConfiguration(5, 0.5, 5);
            case "HARD" -> new BrainConfiguration(7, 0.7, 7);
            case "LEGENDARY" -> new BrainConfiguration(10, 0.9, 10);
            default -> new BrainConfiguration(5, 0.5, 5);
        };
    }
    
    /**
     * Brain configuration data class
     */
    public static class BrainConfiguration {
        private final int intelligenceLevel;
        private final double aggressionLevel;
        private final int priority;
        
        public BrainConfiguration(int intelligenceLevel, double aggressionLevel, int priority) {
            this.intelligenceLevel = intelligenceLevel;
            this.aggressionLevel = aggressionLevel;
            this.priority = priority;
        }
        
        public int getIntelligenceLevel() { return intelligenceLevel; }
        public double getAggressionLevel() { return aggressionLevel; }
        public int getPriority() { return priority; }
    }
}

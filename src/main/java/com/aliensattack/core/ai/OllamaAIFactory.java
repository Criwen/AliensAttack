package com.aliensattack.core.ai;

import com.aliensattack.core.ai.ollama.OllamaAIService;
import com.aliensattack.core.model.Alien;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.interfaces.ICombatManagerExtended;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.ai.interfaces.IEnemyAI;
import lombok.extern.log4j.Log4j2;

/**
 * Factory for creating AI instances with optional Ollama enhancement
 * Now creates OllamaBasedEnemyAI for complete Ollama-powered decision making
 */
@Log4j2
public class OllamaAIFactory {
    private static final boolean OLLAMA_ENABLED = GameConfig.getBoolean("ollama.ai.enabled", true);
    private static OllamaAIService ollamaService;
    
    static {
        if (OLLAMA_ENABLED) {
            try {
                ollamaService = new OllamaAIService();
                log.info("Ollama AI service initialized in factory");
            } catch (Exception e) {
                log.warn("Failed to initialize Ollama AI service in factory: {}", e.getMessage());
                ollamaService = null;
            }
        }
    }
    
    /**
     * Create AI instance for an alien, with Ollama enhancement if available
     */
    public static IEnemyAI createAI(Alien alien, ITacticalField tacticalField, 
                                   ICombatManagerExtended combatManager) {
        if (OLLAMA_ENABLED && ollamaService != null && ollamaService.isAvailable()) {
            log.debug("Creating Ollama-based AI for alien: {}", alien.getAlienType());
            OllamaBasedEnemyAI ai = new OllamaBasedEnemyAI();
            ai.initialize(alien);
            ai.setTacticalField(tacticalField);
            ai.setCombatManager(combatManager);
            return ai;
        } else {
            log.debug("Creating standard AI for alien: {}", alien.getAlienType());
            EnemyAI ai = new EnemyAI();
            ai.initialize(alien);
            ai.setTacticalField(tacticalField);
            ai.setCombatManager(combatManager);
            return ai;
        }
    }
    
    /**
     * Create AI instance for any unit type
     */
    public static IEnemyAI createAI(com.aliensattack.core.model.Unit unit, ITacticalField tacticalField, 
                                   ICombatManagerExtended combatManager) {
        if (unit.getUnitType() == com.aliensattack.core.enums.UnitType.ALIEN) {
            try {
                if (OLLAMA_ENABLED && ollamaService != null && ollamaService.isAvailable()) {
                    log.debug("Creating Ollama-based AI for alien unit: {}", unit.getName());
                    OllamaBasedEnemyAI ai = new OllamaBasedEnemyAI();
                    ai.initializeWithUnit(unit);
                    ai.setTacticalField(tacticalField);
                    ai.setCombatManager(combatManager);
                    return ai;
                } else {
                    log.debug("Creating standard AI for alien unit: {} (Ollama not available)", unit.getName());
                    EnemyAI ai = new EnemyAI();
                    ai.initializeWithUnit(unit);
                    ai.setTacticalField(tacticalField);
                    ai.setCombatManager(combatManager);
                    return ai;
                }
            } catch (Exception e) {
                log.error("Error creating AI for alien unit {}: {}", unit.getName(), e.getMessage());
                // Fall back to standard AI for safety
                EnemyAI ai = new EnemyAI();
                ai.initializeWithUnit(unit);
                ai.setTacticalField(tacticalField);
                ai.setCombatManager(combatManager);
                return ai;
            }
        } else {
            log.debug("Creating standard AI for non-alien unit: {}", unit.getUnitType());
            EnemyAI ai = new EnemyAI();
            ai.initializeWithUnit(unit);
            ai.setTacticalField(tacticalField);
            ai.setCombatManager(combatManager);
            return ai;
        }
    }
    
    /**
     * Check if Ollama is available for AI enhancement
     */
    public static boolean isOllamaAvailable() {
        return OLLAMA_ENABLED && ollamaService != null && ollamaService.isAvailable();
    }
    
    /**
     * Get the Ollama AI service instance
     */
    public static OllamaAIService getOllamaService() {
        return ollamaService;
    }
    
    /**
     * Cleanup resources
     */
    public static void cleanup() {
        if (ollamaService != null) {
            ollamaService.cleanup();
        }
    }
}

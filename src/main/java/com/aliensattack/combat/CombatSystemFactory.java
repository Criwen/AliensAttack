package com.aliensattack.combat;

import com.aliensattack.combat.interfaces.ICombatManagerExtended;
import com.aliensattack.combat.interfaces.ICombatSystem;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.core.model.Mission;
import com.aliensattack.core.config.GameConfig;
import lombok.extern.log4j.Log4j2;

/**
 * Factory for creating and managing different combat system implementations
 * Integrates all combat managers and systems including previously unused components
 */
@Log4j2
public class CombatSystemFactory {
    
    public enum CombatSystemType {
        BASE,           // BaseCombatSystem implementation
        DEFAULT,        // DefaultCombatManager
        TACTICAL,       // TacticalCombatManager
        ALIEN_RULER,    // AlienRulerCombatManager
        ULTIMATE,       // UltimateCombatManager
        COMPREHENSIVE   // ComprehensiveCombatManager
    }
    
    /**
     * Create combat manager based on system type
     */
    public static ICombatManagerExtended createCombatManager(CombatSystemType type, ITacticalField field) {
        log.info("Creating combat manager of type: {}", type);
        
        try {
            switch (type) {
                case BASE:
                case DEFAULT:
                    return new DefaultCombatManager(createTacticalField(field));
                case TACTICAL:
                    return new TacticalCombatManager(field);
                case ALIEN_RULER:
                    return new AlienRulerCombatManager(field, new Mission("Alien Ruler", com.aliensattack.core.enums.MissionType.ELIMINATION, 20));
                case ULTIMATE:
                    return new UltimateCombatManager(createTacticalField(field), new Mission("Ultimate", com.aliensattack.core.enums.MissionType.ELIMINATION, 30));
                case COMPREHENSIVE:
                    return new ComprehensiveCombatManager(createTacticalField(field), new Mission("Comprehensive", com.aliensattack.core.enums.MissionType.ELIMINATION, 25));
                default:
                    log.warn("Unknown combat system type: {}, using DEFAULT", type);
                    return new DefaultCombatManager(createTacticalField(field));
            }
        } catch (Exception e) {
            log.error("Error creating combat manager of type: {}", type, e);
            return new DefaultCombatManager(createTacticalField(field));
        }
    }
    
    /**
     * Create combat system based on mission requirements
     */
    public static ICombatManagerExtended createCombatSystem(Mission mission, ITacticalField field) {
        log.info("Creating combat system for mission: {}", mission.getType());
        
        try {
            // Choose system based on mission type
            switch (mission.getType()) {
                case ELIMINATION:
                    return new AlienRulerCombatManager(field, mission);
                case DEFENSE:
                    return new TacticalCombatManager(field);
                case SABOTAGE:
                    return new DefaultCombatManager(createTacticalField(field));
                default:
                    return new DefaultCombatManager(createTacticalField(field));
            }
        } catch (Exception e) {
            log.error("Error creating combat system for mission: {}", mission.getType(), e);
            return new DefaultCombatManager(createTacticalField(field));
        }
    }
    
    /**
     * Helper method to create TacticalField from ITacticalField
     */
    private static com.aliensattack.field.TacticalField createTacticalField(ITacticalField field) {
        if (field instanceof com.aliensattack.field.TacticalField) {
            return (com.aliensattack.field.TacticalField) field;
        }
        // Create a new TacticalField with default dimensions if conversion not possible
        return new com.aliensattack.field.TacticalField(20, 20);
    }
    
    /**
     * Get recommended combat system for current game state
     */
    public static CombatSystemType getRecommendedSystem(ITacticalField field) {
        // Analyze field complexity and recommend appropriate system
        int fieldSize = field.getWidth() * field.getHeight();
        
        if (fieldSize > 100) {
            return CombatSystemType.COMPREHENSIVE;
        } else if (fieldSize > 50) {
            return CombatSystemType.TACTICAL;
        } else {
            return CombatSystemType.DEFAULT;
        }
    }
}

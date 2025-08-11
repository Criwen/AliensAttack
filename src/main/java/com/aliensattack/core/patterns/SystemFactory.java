package com.aliensattack.core.patterns;

import com.aliensattack.combat.*;
import com.aliensattack.combat.ICombatManager;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.field.OptimizedTacticalField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory pattern for creating game systems
 */
public class SystemFactory {
    private static final Logger log = LogManager.getLogger(SystemFactory.class);
    
    public enum CombatManagerType {
        OPTIMIZED, COMPREHENSIVE, ULTIMATE, FINAL
    }
    
    public enum FieldType {
        OPTIMIZED
    }
    
    public static ICombatManager createCombatManager(CombatManagerType type, ITacticalField field) {
        log.debug("Creating combat manager of type: {}", type);
        
        return switch (type) {
            case OPTIMIZED -> new OptimizedCombatManager((OptimizedTacticalField) field);
            case COMPREHENSIVE -> new ComprehensiveXCOM2CombatManager(field);
            case ULTIMATE -> new UltimateCombatManager((OptimizedTacticalField) field, null);
            case FINAL -> new FinalXCOM2CombatManager(field);
        };
    }
    
    public static ITacticalField createTacticalField(FieldType type, int width, int height) {
        log.debug("Creating tactical field of type: {} with dimensions {}x{}", type, width, height);
        
        return switch (type) {
            case OPTIMIZED -> new OptimizedTacticalField(width, height);
        };
    }
    
    public static ITacticalField createDefaultTacticalField() {
        return createTacticalField(FieldType.OPTIMIZED, 10, 10);
    }
    
    public static ICombatManager createDefaultCombatManager(ITacticalField field) {
        return createCombatManager(CombatManagerType.FINAL, field);
    }
}

package com.aliensattack.core.patterns;

import com.aliensattack.combat.*;
import com.aliensattack.combat.ICombatManager;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.field.TacticalField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory pattern for creating game systems
 */
public class SystemFactory {
    private static final Logger log = LogManager.getLogger(SystemFactory.class);
    
    public enum CombatManagerType {
        DEFAULT, BASE
    }
    
    public enum FieldType {
        STANDARD
    }
    
    public static ICombatManager createCombatManager(CombatManagerType type, ITacticalField field) {
        log.debug("Creating combat manager of type: {}", type);
        
        return switch (type) {
            case BASE -> new CombatManagerBase((TacticalField) field);
            case DEFAULT -> new DefaultCombatManager((TacticalField) field);
        };
    }
    
    public static ITacticalField createTacticalField(FieldType type, int width, int height) {
        log.debug("Creating tactical field of type: {} with dimensions {}x{}", type, width, height);
        return new TacticalField(width, height);
    }
    
    public static ITacticalField createDefaultTacticalField() {
        return createTacticalField(FieldType.STANDARD, 10, 10);
    }
    
    public static ICombatManager createDefaultCombatManager(ITacticalField field) {
        return createCombatManager(CombatManagerType.DEFAULT, field);
    }
}

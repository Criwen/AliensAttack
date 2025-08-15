package com.aliensattack.combat;

/**
 * Base combat manager name without "Optimized". Extends existing implementation
 * to avoid breaking changes while we migrate usages.
 */
public class CombatManagerBase extends DefaultCombatManager {
    public CombatManagerBase(com.aliensattack.field.TacticalField field) {
        super(field);
    }
}



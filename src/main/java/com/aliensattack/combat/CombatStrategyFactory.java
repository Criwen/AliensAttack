package com.aliensattack.combat;

import com.aliensattack.combat.interfaces.ICombatStrategy;
import com.aliensattack.combat.strategies.TacticalCombatStrategy;
import com.aliensattack.combat.strategies.PsionicCombatStrategy;
import com.aliensattack.combat.strategies.StealthCombatStrategy;
import com.aliensattack.combat.strategies.UrbanCombatStrategy;
import com.aliensattack.combat.strategies.AlienRulerCombatStrategy;
import lombok.extern.log4j.Log4j2;

/**
 * Factory for creating combat strategies
 */
@Log4j2
public class CombatStrategyFactory {
    
    public enum StrategyType {
        TACTICAL,
        PSIONIC,
        STEALTH,
        URBAN,
        ALIEN_RULER
    }
    
    /**
     * Create combat strategy based on type
     */
    public static ICombatStrategy createStrategy(StrategyType type) {
        log.info("Creating combat strategy: {}", type);
        
        return switch (type) {
            case TACTICAL -> new TacticalCombatStrategy();
            case PSIONIC -> new PsionicCombatStrategy();
            case STEALTH -> new StealthCombatStrategy();
            case URBAN -> new UrbanCombatStrategy();
            case ALIEN_RULER -> new AlienRulerCombatStrategy();
        };
    }
    
    /**
     * Create default tactical strategy
     */
    public static ICombatStrategy createDefaultStrategy() {
        return createStrategy(StrategyType.TACTICAL);
    }
}

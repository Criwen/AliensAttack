package com.aliensattack.combat.interfaces;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.field.ITacticalField;

import java.util.List;

/**
 * Interface for combat managers that coordinate and orchestrate combat operations
 * Extends ICombatSystem to provide both combat resolution and management capabilities
 */
public interface ICombatManager extends ICombatSystem {
    
    // Combat Management
    void initializeCombat(ITacticalField field);
    void startCombatRound();
    void endCombatRound();
    boolean isCombatRoundActive();
    
    // Unit Management
    void addUnitToCombat(IUnit unit);
    void removeUnitFromCombat(IUnit unit);
    List<IUnit> getActiveUnits();
    List<IUnit> getUnitsInCombat();
    
    // Turn Management
    void startUnitTurn(IUnit unit);
    void endUnitTurn(IUnit unit);
    IUnit getCurrentUnit();
    List<IUnit> getTurnOrder();
    boolean isUnitTurn(IUnit unit);
    
    // Combat Coordination
    void coordinateSquadActions(List<IUnit> squad);
    void processAlienAI();
    void handleReinforcements();
    void checkVictoryConditions();
    
    // Event Handling
    void handleCombatEvent(String eventType, Object... parameters);
    void notifyCombatStateChange();
    void registerCombatListener(Object listener);
    
    // Performance and Optimization
    void optimizeCombatCalculations();
    void clearCombatCache();
    long getCombatDuration();
}

package com.aliensattack.combat.interfaces;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;
import com.aliensattack.combat.CombatResult;

import java.util.List;

/**
 * Core interface for combat managers
 * Defines the contract for all combat system implementations
 */
public interface ICombatManager {
    
    /**
     * Initialize the combat system
     */
    void initialize();
    
    /**
     * Start a new combat turn
     */
    void startTurn();
    
    /**
     * End the current combat turn
     */
    void endTurn();
    
    /**
     * Process unit initiative order
     */
    void processInitiative();
    
    /**
     * Get units in initiative order
     */
    List<IUnit> getUnitsInInitiativeOrder();
    
    /**
     * Check if combat is finished
     */
    boolean isCombatFinished();
    
    /**
     * Get the current combat state
     */
    String getCombatState();
    
    /**
     * Get units at a specific position
     */
    List<IUnit> getUnitsAt(Position position);
    
    /**
     * Get units in range of a position
     */
    List<IUnit> getUnitsInRange(Position center, int range);
    
    /**
     * Check if a position is valid for movement
     */
    boolean isValidMovePosition(IUnit unit, Position position);
    
    /**
     * Check if a unit can see another unit
     */
    boolean canSeeUnit(IUnit observer, IUnit target);
    
    /**
     * Get the tactical field
     */
    Object getField(); // Using Object to avoid circular dependencies
}

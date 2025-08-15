package com.aliensattack.combat.interfaces;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.CombatResult;

import java.util.List;

/**
 * Extended combat manager interface that provides additional
 * combat mechanics beyond the basic combat system.
 */
public interface ICombatManagerExtended {

    // Basic Combat Methods
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
    ITacticalField getField();

    // Extended Combat Methods
    /**
     * Perform a tactical move with advanced positioning
     */
    boolean performTacticalMove(Unit unit, int newX, int newY);

    /**
     * Execute a coordinated attack with multiple units
     */
    CombatResult executeCoordinatedAttack(Unit[] attackers, Unit target);

    /**
     * Apply advanced cover mechanics
     */
    double calculateAdvancedCoverBonus(Unit unit);

    /**
     * Process environmental effects on combat
     */
    void processEnvironmentalEffects();

    /**
     * Get tactical field for advanced positioning
     */
    ITacticalField getTacticalField();
}



package com.aliensattack.combat.interfaces;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.combat.CombatResult;
import java.util.List;

/**
 * Strategy interface for different combat modes
 */
public interface ICombatStrategy {
    
    /**
     * Execute attack between units
     */
    CombatResult executeAttack(Unit attacker, Unit target);
    
    /**
     * Check if attack is valid
     */
    boolean canAttack(Unit attacker, Unit target);
    
    /**
     * Get valid targets for unit
     */
    List<Unit> getValidTargets(Unit attacker);
    
    /**
     * Check if unit can move to position
     */
    boolean canMoveTo(Unit unit, Position target);
    
    /**
     * Execute unit movement
     */
    boolean moveUnit(Unit unit, Position target);
    
    /**
     * Get valid movement positions
     */
    List<Position> getValidMovePositions(Unit unit);
    
    /**
     * Get strategy type
     */
    String getStrategyType();
}

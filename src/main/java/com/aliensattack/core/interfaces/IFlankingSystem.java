package com.aliensattack.core.interfaces;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.CombatAction;

import java.util.List;

/**
 * Interface for Flanking System - XCOM2 Tactical Combat
 * Defines contract for flanking mechanics and bonuses
 */
public interface IFlankingSystem {
    
    /**
     * Check if attacker is flanking target
     * @param attacker Attacking unit
     * @param target Target unit
     * @return true if attacker is flanking target
     */
    boolean isFlanking(Unit attacker, Unit target);
    
    /**
     * Calculate flanking bonus for attacker vs target
     * @param attacker Attacking unit
     * @param target Target unit
     * @return Flanking bonus multiplier
     */
    double calculateFlankingBonus(Unit attacker, Unit target);
    
    /**
     * Find best flanking position for attacker against target
     * @param attacker Attacking unit
     * @param target Target unit
     * @return Best flanking position or null if none available
     */
    Position findFlankingPosition(Unit attacker, Unit target);
    
    /**
     * Get all available flanking positions for target
     * @param target Target unit
     * @return List of flanking positions
     */
    List<Position> getFlankingPositions(Unit target);
    
    /**
     * Apply flanking effects to combat action
     * @param action Combat action to apply effects to
     */
    void applyFlankingEffects(CombatAction action);
}

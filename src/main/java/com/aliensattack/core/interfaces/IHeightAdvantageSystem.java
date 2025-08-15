package com.aliensattack.core.interfaces;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.enums.HeightLevel;
import com.aliensattack.core.systems.HeightAdvantageSystem.HeightModifier;

import java.util.List;

/**
 * Interface for Height Advantage System - XCOM2 Tactical Combat
 * Defines contract for elevation-based bonuses and penalties
 */
public interface IHeightAdvantageSystem {
    
    /**
     * Get height level for a position
     * @param position Position to check
     * @return Height level at position
     */
    HeightLevel getHeightLevel(Position position);
    
    /**
     * Calculate height bonus for attacker vs target
     * @param attacker Attacking unit
     * @param target Target unit
     * @return Height bonus multiplier
     */
    double calculateHeightBonus(Unit attacker, Unit target);
    
    /**
     * Calculate height penalty for attacker vs target
     * @param attacker Attacking unit
     * @param target Target unit
     * @return Height penalty multiplier
     */
    double calculateHeightPenalty(Unit attacker, Unit target);
    
    /**
     * Check line of sight considering height
     * @param from Starting position
     * @param to Target position
     * @return true if line of sight exists
     */
    boolean hasLineOfSight(Position from, Position to);
    
    /**
     * Get all height modifiers
     * @return List of height modifiers
     */
    List<HeightModifier> getHeightModifiers();
}

package com.aliensattack.core.interfaces;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.CombatAction;
import com.aliensattack.core.systems.ConcealmentSystem.ConcealmentEffect;
import com.aliensattack.core.enums.ConcealmentStatus;
import com.aliensattack.core.enums.ConcealmentBreakReason;

import java.util.List;

/**
 * Interface for Concealment System - XCOM2 Tactical Combat
 * Defines contract for stealth mechanics and concealment management
 */
public interface IConcealmentSystem {
    
    /**
     * Check if unit is currently concealed
     * @param unit Unit to check
     * @return true if unit is concealed
     */
    boolean isConcealed(Unit unit);
    
    /**
     * Break concealment for a unit with specific reason
     * @param unit Unit to break concealment for
     * @param reason Reason for breaking concealment
     */
    void breakConcealment(Unit unit, ConcealmentBreakReason reason);
    
    /**
     * Calculate detection chance for a unit by an enemy
     * @param unit Unit being detected
     * @param enemy Enemy trying to detect
     * @return Detection chance (0.0 to 1.0)
     */
    double calculateDetectionChance(Unit unit, Unit enemy);
    
    /**
     * Apply concealment bonuses to combat action
     * @param action Combat action to apply bonuses to
     */
    void applyConcealmentBonus(CombatAction action);
    
    /**
     * Get all active concealment effects
     * @return List of active concealment effects
     */
    List<ConcealmentEffect> getActiveEffects();
}

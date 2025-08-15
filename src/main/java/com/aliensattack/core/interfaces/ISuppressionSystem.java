package com.aliensattack.core.interfaces;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.systems.SuppressionSystem.SuppressionEffect;

import java.util.List;

/**
 * Interface for Suppression System - XCOM2 Tactical Combat
 * Defines contract for suppression mechanics and effects
 */
public interface ISuppressionSystem {
    
    /**
     * Apply suppression to target unit
     * @param target Unit to suppress
     * @param suppressor Unit applying suppression
     */
    void applySuppression(Unit target, Unit suppressor);
    
    /**
     * Check if unit is currently suppressed
     * @param unit Unit to check
     * @return true if unit is suppressed
     */
    boolean isSuppressed(Unit unit);
    
    /**
     * Get suppression effects for a unit
     * @param unit Unit to get effects for
     * @return List of suppression effects
     */
    List<SuppressionEffect> getSuppressionEffects(Unit unit);
    
    /**
     * Remove suppression from unit
     * @param unit Unit to remove suppression from
     */
    void removeSuppression(Unit unit);
    
    /**
     * Calculate chance of successful suppression
     * @param attacker Unit attempting suppression
     * @param target Unit to be suppressed
     * @return Suppression chance (0.0 to 1.0)
     */
    double calculateSuppressionChance(Unit attacker, Unit target);
}

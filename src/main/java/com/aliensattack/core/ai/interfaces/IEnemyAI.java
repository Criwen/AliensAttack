package com.aliensattack.core.ai.interfaces;

import com.aliensattack.core.model.Alien;
import com.aliensattack.core.model.Position;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.interfaces.ICombatManagerExtended;

import java.util.List;

/**
 * Interface for enemy AI behavior in tactical combat
 */
public interface IEnemyAI {
    
    /**
     * Initialize AI for a specific alien
     */
    void initialize(Alien alien);
    
    /**
     * Initialize AI with any unit type (for compatibility)
     */
    void initializeWithUnit(com.aliensattack.core.model.Unit unit);
    
    /**
     * Make AI decision for the current turn
     */
    void makeTurnDecision();
    
    /**
     * Execute AI action (move, attack, use ability)
     */
    boolean executeAction();
    
    /**
     * Calculate best move position
     */
    Position calculateBestMovePosition();
    
    /**
     * Find best target for attack
     */
    List<com.aliensattack.core.model.Unit> findBestTargets();
    
    /**
     * Check if AI should use special ability
     */
    boolean shouldUseSpecialAbility();
    
    /**
     * Get AI difficulty level
     */
    int getDifficultyLevel();
    
    /**
     * Set tactical field reference
     */
    void setTacticalField(ITacticalField field);
    
    /**
     * Set combat manager reference
     */
    void setCombatManager(ICombatManagerExtended combatManager);
}

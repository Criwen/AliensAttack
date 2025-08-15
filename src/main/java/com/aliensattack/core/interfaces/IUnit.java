package com.aliensattack.core.interfaces;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.enums.UnitType;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.data.StatusEffectData;

import java.util.List;

/**
 * Core interface for all units in the game
 * Defines the contract that all unit types must implement
 */
public interface IUnit {
    
    // Basic properties
    String getId();
    String getName();
    int getMaxHealth();
    int getCurrentHealth();
    UnitType getUnitType();
    Position getPosition();
    
    // Health and status
    boolean isAlive();
    boolean takeDamage(int damage);
    void heal(int amount);
    double getHealthPercentage();
    
    // Movement
    int getMovementRange();
    boolean canMove();
    void setPosition(int x, int y);
    void setPosition(Position position);
    
    // Combat
    int getAttackRange();
    int getAttackDamage();
    boolean canAttack();
    int getAccuracy();
    int getDefense();
    int getDodgeChance();
    
    // Action points
    double getActionPoints();
    boolean hasActionPoints();
    void spendActionPoint();
    void spendActionPoints(double amount);
    void resetActionPoints();
    boolean canTakeActions();
    
    // Status effects
    List<StatusEffectData> getStatusEffects();
    void addStatusEffect(StatusEffectData effect);
    void removeStatusEffect(StatusEffectData effect);
    boolean hasStatusEffect(StatusEffect effect);
    void processStatusEffects();
    void clearStatusEffects();
    
    // Overwatch
    boolean isOverwatching();
    void setOverwatching(boolean overwatching);
    int getOverwatchChance();
    void setOverwatchChance(int chance);
    
    // Height and elevation
    int getHeight();
    void setHeight(int height);
    
    // Critical hit methods
    int getCriticalChance();
    void setCriticalChance(int chance);
    int getCriticalDamageMultiplier();
    void setCriticalDamageMultiplier(int multiplier);
    int getTotalCriticalChance();
    
    // Attack range and special abilities
    int getTotalAttackRange();
    boolean canPerformSpecialAbility();
    
    // Suppression methods
    boolean isSuppressed();
    void setSuppressed(boolean suppressed);
    int getSuppressionTurns();
    void setSuppressionTurns(int turns);
    void applySuppression(int turns);
    void removeSuppression();
    
    // Utility methods
    boolean equals(Object obj);
    int hashCode();
    String toString();
}

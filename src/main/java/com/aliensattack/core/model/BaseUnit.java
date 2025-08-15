package com.aliensattack.core.model;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.enums.UnitType;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.data.StatusEffectData;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Base implementation of IUnit interface
 * Contains core functionality shared by all unit types
 */
@Getter
@Setter
public abstract class BaseUnit implements IUnit {
    
    // Core properties
    protected String id;
    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected int movementRange;
    protected int attackRange;
    protected int attackDamage;
    protected UnitType unitType;
    protected Position position;
    
    // Action points
    protected boolean hasActionPoints;
    protected double actionPoints;
    
    // Combat stats
    protected int accuracy;
    protected int defense;
    protected int dodgeChance;
    
    // Status effects
    protected List<StatusEffectData> statusEffects;
    
    // Overwatch
    protected boolean isOverwatching;
    protected int overwatchChance;
    
    public BaseUnit(String name, int maxHealth, int movementRange, int attackRange, int attackDamage, UnitType unitType) {
        this.id = "UNIT_" + System.currentTimeMillis() + "_" + name.replaceAll("\\s+", "_");
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.movementRange = movementRange;
        this.attackRange = attackRange;
        this.attackDamage = attackDamage;
        this.unitType = unitType;
        this.hasActionPoints = true;
        this.actionPoints = GameConfig.getDefaultActionPoints();
        this.position = new Position(0, 0);
        this.statusEffects = new ArrayList<>();
        this.isOverwatching = false;
        this.overwatchChance = GameConfig.getDefaultOverwatchChance();
        this.accuracy = GameConfig.getInt("unit.default.accuracy", 75);
        this.defense = GameConfig.getInt("unit.default.defense", 10);
        this.dodgeChance = GameConfig.getInt("unit.default.dodge", 15);
    }
    
    // Basic properties implementation
    @Override
    public String getId() { return id; }
    
    @Override
    public String getName() { return name; }
    
    @Override
    public int getMaxHealth() { return maxHealth; }
    
    @Override
    public int getCurrentHealth() { return currentHealth; }
    
    @Override
    public UnitType getUnitType() { return unitType; }
    
    @Override
    public Position getPosition() { return position; }
    
    // Health and status implementation
    @Override
    public boolean isAlive() {
        return currentHealth > 0;
    }
    
    @Override
    public boolean takeDamage(int damage) {
        if (damage <= 0) return false;
        
        currentHealth = Math.max(0, currentHealth - damage);
        return !isAlive();
    }
    
    @Override
    public void heal(int amount) {
        if (amount <= 0) return;
        currentHealth = Math.min(maxHealth, currentHealth + amount);
    }
    
    @Override
    public double getHealthPercentage() {
        return maxHealth > 0 ? (double) currentHealth / maxHealth : 0.0;
    }
    
    // Movement implementation
    @Override
    public int getMovementRange() { return movementRange; }
    
    @Override
    public boolean canMove() {
        return isAlive() && hasActionPoints && actionPoints > 0;
    }
    
    @Override
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }
    
    @Override
    public void setPosition(Position position) {
        this.position = position != null ? position : new Position(0, 0);
    }
    
    // Combat implementation
    @Override
    public int getAttackRange() { return attackRange; }
    
    @Override
    public int getAttackDamage() { return attackDamage; }
    
    @Override
    public boolean canAttack() {
        return isAlive() && hasActionPoints && actionPoints > 0;
    }
    
    @Override
    public int getAccuracy() { return accuracy; }
    
    @Override
    public int getDefense() { return defense; }
    
    @Override
    public int getDodgeChance() { return dodgeChance; }
    
    // Action points implementation
    @Override
    public double getActionPoints() { return actionPoints; }
    
    @Override
    public boolean hasActionPoints() { return hasActionPoints; }
    
    @Override
    public void spendActionPoint() {
        if (actionPoints > 0) {
            actionPoints = Math.max(0, actionPoints - 1);
        }
    }
    
    @Override
    public void spendActionPoints(double amount) {
        if (amount > 0) {
            actionPoints = Math.max(0, actionPoints - amount);
        }
    }
    
    @Override
    public void resetActionPoints() {
        actionPoints = GameConfig.getDefaultActionPoints();
    }
    
    @Override
    public boolean canTakeActions() {
        return isAlive() && hasActionPoints && actionPoints > 0;
    }
    
    // Status effects implementation
    @Override
    public List<StatusEffectData> getStatusEffects() { return statusEffects; }
    
    @Override
    public void addStatusEffect(StatusEffectData effect) {
        if (effect != null) {
            statusEffects.add(effect);
        }
    }
    
    @Override
    public void removeStatusEffect(StatusEffectData effect) {
        statusEffects.remove(effect);
    }
    
    @Override
    public boolean hasStatusEffect(StatusEffect effect) {
        return statusEffects.stream()
                .anyMatch(se -> se.getEffect() == effect);
    }
    
    @Override
    public void processStatusEffects() {
        statusEffects.removeIf(effect -> effect.getDuration() <= 0);
        statusEffects.forEach(effect -> effect.setDuration(effect.getDuration() - 1));
    }
    
    @Override
    public void clearStatusEffects() {
        statusEffects.clear();
    }
    
    // Overwatch implementation
    @Override
    public boolean isOverwatching() { return isOverwatching; }
    
    @Override
    public void setOverwatching(boolean overwatching) { this.isOverwatching = overwatching; }
    
    @Override
    public int getOverwatchChance() { return overwatchChance; }
    
    @Override
    public void setOverwatchChance(int chance) { this.overwatchChance = Math.max(0, Math.min(100, chance)); }
    
    // Utility methods implementation
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseUnit baseUnit = (BaseUnit) obj;
        return Objects.equals(id, baseUnit.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%s, name='%s', type=%s, health=%d/%d, position=%s]",
                getClass().getSimpleName(), id, name, unitType, currentHealth, maxHealth, position);
    }
}

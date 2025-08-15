package com.aliensattack.core.model;

import com.aliensattack.core.enums.UnitType;
import com.aliensattack.core.enums.SoldierClass;
import com.aliensattack.core.enums.VisibilityType;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.model.components.*;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.data.StatusEffectData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.ArrayList;

/**
 * Refactored Unit class using component-based architecture
 * Separates concerns into focused components for better maintainability
 */
@Getter
@Setter
@Log4j2
public class RefactoredUnit implements IUnit {
    
    // Core identification
    private String id;
    private String name;
    private UnitType unitType;
    private SoldierClass soldierClass;
    
    // Position and movement
    private Position position;
    private double actionPoints;
    
    // Component-based systems
    private CombatStats combatStats;
    private EquipmentManager equipment;
    private StatusEffectManager statusEffects;
    private PsionicProfile psionicProfile;
    private MedicalStatus medicalStatus;
    
    // Visibility and stealth
    private VisibilityType visibility;
    private boolean isConcealed;
    
    // Squad and tactics
    private SquadTactic activeSquadTactic;
    private List<SoldierAbility> abilities;
    
    // Evolution and mutations
    private int evolutionLevel;
    private List<String> mutations;
    
    public RefactoredUnit(String name, int maxHealth, int movementRange, int attackRange, int attackDamage, UnitType unitType) {
        this.id = "UNIT_" + System.currentTimeMillis() + "_" + name.replaceAll("\\s+", "_");
        this.name = name;
        this.unitType = unitType;
        this.actionPoints = 2.0; // Default action points
        
        // Initialize components
        this.combatStats = CombatStats.builder()
                .maxHealth(maxHealth)
                .currentHealth(maxHealth)
                .movementRange(movementRange)
                .attackRange(attackRange)
                .attackDamage(attackDamage)
                .viewRange(attackRange * 2)
                .height(0)
                .initiative(10)
                .criticalChance(5)
                .criticalDamageMultiplier(2)
                .overwatchChance(25)
                .build();
        
        this.equipment = new EquipmentManager();
        this.statusEffects = new StatusEffectManager();
        this.psionicProfile = new PsionicProfile();
        this.medicalStatus = new MedicalStatus();
        
        // Initialize other fields
        this.visibility = VisibilityType.CLEAR;
        this.isConcealed = false;
        this.activeSquadTactic = null;
        this.abilities = new ArrayList<>();
        this.evolutionLevel = 0;
        this.mutations = new ArrayList<>();
        
        log.info("RefactoredUnit created: {} (ID: {})", name, id);
    }
    
    // Combat stats delegation
    public int getMaxHealth() { return combatStats.getMaxHealth(); }
    public int getCurrentHealth() { return combatStats.getCurrentHealth(); }
    public void setCurrentHealth(int health) { combatStats.setCurrentHealth(health); }
    public int getMovementRange() { return combatStats.getMovementRange(); }
    public int getAttackRange() { return combatStats.getAttackRange(); }
    public int getAttackDamage() { return combatStats.getAttackDamage(); }
    public int getViewRange() { return combatStats.getViewRange(); }
    public int getHeight() { return combatStats.getHeight(); }
    public int getInitiative() { return combatStats.getInitiative(); }
    public int getCriticalChance() { return combatStats.getCriticalChance(); }
    public int getCriticalDamageMultiplier() { return combatStats.getCriticalDamageMultiplier(); }
    public int getOverwatchChance() { return combatStats.getOverwatchChance(); }
    
    // Equipment delegation
    public Weapon getWeapon() { return equipment.getWeapon(); }
    public void setWeapon(Weapon weapon) { equipment.setWeapon(weapon); }
    public Armor getArmor() { return equipment.getArmor(); }
    public void setArmor(Armor armor) { equipment.setArmor(armor); }
    
    // Status effects delegation
    public List<StatusEffectData> getStatusEffects() { return statusEffects.getStatusEffects(); }
    public boolean isOverwatching() { return statusEffects.isOverwatching(); }
    public void setOverwatching(boolean overwatching) { statusEffects.setOverwatching(overwatching); }
    public boolean isFlanked() { return statusEffects.isFlanked(); }
    public void setFlanked(boolean flanked) { statusEffects.setFlanked(flanked); }
    
    // Psionic delegation
    public List<PsionicAbility> getPsionicAbilities() { return psionicProfile.getPsionicAbilities(); }
    public int getPsiStrength() { return psionicProfile.getPsiStrength(); }
    public int getPsiResistance() { return psionicProfile.getPsiResistance(); }
    
    // Medical delegation
    public boolean isStabilized() { return medicalStatus.isStabilized(); }
    public boolean hasFallen() { return medicalStatus.isHasFallen(); }
    public int getFallDamage() { return medicalStatus.getFallDamage(); }
    
    // Utility methods
    public boolean isAlive() {
        return combatStats.isAlive();
    }
    
    public boolean canMove() {
        return combatStats.canMove(actionPoints);
    }
    
    public boolean canAttack() {
        return combatStats.canAttack(actionPoints);
    }
    
    public boolean hasActionPoints() {
        return combatStats.hasActionPoints(actionPoints);
    }
    
    public void setActionPoints(double points) {
        this.actionPoints = Math.max(0, points);
    }
    
    public double getActionPoints() {
        return actionPoints;
    }
    
    public void setPosition(Position position) {
        this.position = position;
        if (position != null) {
            this.combatStats.setHeight(position.getHeight());
        }
    }
    
    public Position getPosition() {
        return position;
    }
    
    // Component update methods
    public void updateComponents() {
        statusEffects.updateEffects();
        medicalStatus.processHealing();
        // Update other components as needed
    }
    
    // IUnit interface implementation
    @Override
    public boolean takeDamage(int damage) {
        int newHealth = Math.max(0, combatStats.getCurrentHealth() - damage);
        combatStats.setCurrentHealth(newHealth);
        return newHealth <= 0;
    }
    
    @Override
    public void heal(int amount) {
        int newHealth = Math.min(combatStats.getMaxHealth(), combatStats.getCurrentHealth() + amount);
        combatStats.setCurrentHealth(newHealth);
    }
    
    @Override
    public double getHealthPercentage() {
        return (double) combatStats.getCurrentHealth() / combatStats.getMaxHealth() * 100;
    }
    
    @Override
    public void setPosition(int x, int y) {
        setPosition(new Position(x, y));
    }
    
    @Override
    public int getAccuracy() {
        return 75; // Default accuracy
    }
    
    @Override
    public int getDefense() {
        return equipment.hasArmor() ? equipment.getArmor().getDamageReduction() : 0;
    }
    
    @Override
    public int getDodgeChance() {
        return 10; // Default dodge chance
    }
    
    @Override
    public void spendActionPoint() {
        setActionPoints(actionPoints - 1.0);
    }
    
    @Override
    public void spendActionPoints(double amount) {
        setActionPoints(actionPoints - amount);
    }
    
    @Override
    public void resetActionPoints() {
        setActionPoints(2.0); // Default action points
    }
    
    @Override
    public boolean canTakeActions() {
        return isAlive() && actionPoints > 0 && !statusEffects.isIncapacitated();
    }
    
    @Override
    public void addStatusEffect(StatusEffectData effect) {
        statusEffects.addStatusEffect(effect);
    }
    
    @Override
    public void removeStatusEffect(StatusEffectData effect) {
        statusEffects.removeStatusEffect(effect);
    }
    
    @Override
    public boolean hasStatusEffect(StatusEffect effect) {
        return statusEffects.hasStatusEffect(effect);
    }
    
    @Override
    public void processStatusEffects() {
        statusEffects.updateEffects();
    }
    
    @Override
    public void clearStatusEffects() {
        statusEffects.getStatusEffects().clear();
    }
    
    @Override
    public void setOverwatchChance(int chance) {
        combatStats.setOverwatchChance(chance);
    }
    
    @Override
    public void setCriticalChance(int chance) {
        combatStats.setCriticalChance(chance);
    }
    
    @Override
    public void setCriticalDamageMultiplier(int multiplier) {
        combatStats.setCriticalDamageMultiplier(multiplier);
    }
    
    @Override
    public void setHeight(int height) {
        combatStats.setHeight(height);
    }
    
    @Override
    public int getTotalCriticalChance() {
        return combatStats.getCriticalChance();
    }
    
    @Override
    public int getTotalAttackRange() {
        return combatStats.getAttackRange();
    }
    
    @Override
    public boolean canPerformSpecialAbility() {
        return hasActionPoints() && !statusEffects.isIncapacitated();
    }
    
    @Override
    public int getSuppressionTurns() {
        return statusEffects.getSuppressionTurns();
    }
    
    @Override
    public void setSuppressionTurns(int turns) {
        statusEffects.setSuppressionTurns(turns);
    }
    
    @Override
    public void setSuppressed(boolean suppressed) {
        statusEffects.setSuppressed(suppressed);
    }
    
    @Override
    public boolean isSuppressed() {
        return statusEffects.isSuppressed();
    }
    
    @Override
    public void applySuppression(int turns) {
        statusEffects.setSuppressed(true);
        statusEffects.setSuppressionTurns(turns);
    }
    
    @Override
    public void removeSuppression() {
        statusEffects.setSuppressed(false);
        statusEffects.setSuppressionTurns(0);
    }
    
    @Override
    public String toString() {
        return String.format("RefactoredUnit{id='%s', name='%s', health=%d/%d, actionPoints=%.1f}", 
                id, name, combatStats.getCurrentHealth(), combatStats.getMaxHealth(), actionPoints);
    }
}

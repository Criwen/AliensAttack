package com.aliensattack.core.model;

import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.enums.UnitType;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.enums.SoldierClass;
import com.aliensattack.core.enums.VisibilityType;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.data.StatusEffectData;

import java.util.List;
import java.util.ArrayList;

/**
 * Combat unit implementation that extends BaseUnit
 * Implements combat-specific functionality and behavior
 */
public class CombatUnit extends BaseUnit {
    
    // Add missing fields
    private boolean hasFallen;
    private int fallDamage;
    private boolean isConcealed;
    private VisibilityType visibility;
    private List<SoldierAbility> abilities;
    private List<PsionicAbility> psionicAbilities;
    private List<Explosive> explosives;
    private List<String> mutations;
    private List<ReactiveAbility> reactiveAbilities;
    private SquadTactic activeSquadTactic;
    private int evolutionLevel;
    private int psiStrength;
    private int psiResistance;
    private boolean isStabilized;
    private int medicalPriority;
    private boolean isInOverwatchAmbush;
    private boolean isSuppressed;
    private SoldierClass soldierClass;
    private Armor armor;
    private Weapon weapon;
    
    public CombatUnit(String name, int maxHealth, int movementRange, int attackRange, int attackDamage, UnitType unitType) {
        super(name, maxHealth, movementRange, attackRange, attackDamage, unitType);
        // Initialize collections
        this.abilities = new ArrayList<>();
        this.psionicAbilities = new ArrayList<>();
        this.explosives = new ArrayList<>();
        this.mutations = new ArrayList<>();
        this.reactiveAbilities = new ArrayList<>();
    }
    
    protected int getDefaultViewRange(UnitType unitType) {
        switch (unitType) {
            case SOLDIER: return 12;
            case ALIEN: return 10;
            case CIVILIAN: return 6;
            case VEHICLE: return 8;
            case ROBOTIC: return 10;
            default: return 10;
        }
    }
    
    public boolean canConceal() {
        return unitType == UnitType.SOLDIER && !hasFallen && isAlive();
    }
    
    // Status Effects
    public void addStatusEffect(StatusEffectData effect) {
        statusEffects.add(effect);
    }
    
    public void removeStatusEffect(StatusEffectData effect) {
        statusEffects.remove(effect);
    }
    
    public boolean hasStatusEffect(StatusEffect effect) {
        return statusEffects.stream().anyMatch(e -> e.getEffect() == effect);
    }
    
    public void clearStatusEffects() {
        statusEffects.clear();
    }
    
    public void processStatusEffects() {
        statusEffects.removeIf(effect -> effect.isExpired());
        statusEffects.forEach(StatusEffectData::decrementDuration);
    }
    
    // Soldier Class System
    public void addAbility(SoldierAbility ability) {
        abilities.add(ability);
    }
    
    public void removeAbility(SoldierAbility ability) {
        abilities.remove(ability);
    }
    
    public boolean hasAbility(String abilityName) {
        return abilities.stream().anyMatch(ability -> ability.getName().equals(abilityName));
    }
    
    public void processAbilityCooldowns() {
        abilities.forEach(SoldierAbility::processCooldown);
    }
    
    // Visibility and Stealth
    public void setVisibility(VisibilityType visibility) {
        this.visibility = visibility;
    }
    
    public void setConcealed(boolean concealed) {
        this.isConcealed = concealed;
        if (concealed) {
            this.visibility = VisibilityType.CONCEALED;
        } else {
            this.visibility = VisibilityType.CLEAR;
        }
    }
    
    public boolean conceal() {
        if (canConceal()) {
            setConcealed(true);
            return true;
        }
        return false;
    }
    
    public void reveal() {
        setConcealed(false);
        this.visibility = VisibilityType.CLEAR;
    }
    
    public boolean isDetectedBy(IUnit enemy) {
        if (!isConcealed) return true;
        
        int detectionRange = 10; // Default view range
        if (enemy.getPosition() != null && position != null) {
            double distance = calculateDistance(position, enemy.getPosition());
            return distance <= detectionRange;
        }
        return false;
    }
    
    public int getStealthAttackBonus() {
        if (!isConcealed) return 0;
        return 25; // 25% bonus for stealth attacks
    }
    
    // Height and Movement
    public boolean hasFallen() {
        return hasFallen;
    }
    
    public void setHasFallen(boolean fallen) {
        this.hasFallen = fallen;
    }
    
    public void setFallDamage(int damage) {
        this.fallDamage = damage;
    }
    
    public void applyFallDamage(int height) {
        int damage = Math.min(height * 2, 10); // Simplified fall damage
        takeDamage(damage);
        if (damage > 0) {
            setHasFallen(true);
            setFallDamage(damage);
        }
    }
    
    public boolean canMoveAfterFall() {
        return !hasFallen || fallDamage < maxHealth / 2;
    }
    
    // Medical System
    public void setStabilized(boolean stabilized) {
        this.isStabilized = stabilized;
    }
    
    public void setMedicalPriority(int priority) {
        this.medicalPriority = priority;
    }
    
    public void stabilize() {
        this.isStabilized = true;
        this.medicalPriority = 0;
        // Heal a small amount when stabilized
        heal(5);
    }
    
    public void healWithMedical(int amount) {
        heal(amount);
        if (isStabilized) {
            setStabilized(false);
        }
    }
    
    public boolean takeDamageWithArmor(int damage) {
        int actualDamage = damage;
        if (armor != null) {
            actualDamage = Math.max(1, damage - 2); // Simplified armor reduction
        }
        return takeDamage(actualDamage);
    }
    
    // Psionic System
    public void addPsionicAbility(PsionicAbility ability) {
        psionicAbilities.add(ability);
    }
    
    public void removePsionicAbility(PsionicAbility ability) {
        psionicAbilities.remove(ability);
    }
    
    public int getPsiStrength() { return psiStrength; }
    
    public void setPsiStrength(int strength) { this.psiStrength = strength; }
    
    public int getPsiResistance() { return psiResistance; }
    
    public void setPsiResistance(int resistance) { this.psiResistance = resistance; }
    
    // Explosives
    public void addExplosive(Explosive explosive) {
        explosives.add(explosive);
    }
    
    public void removeExplosive(Explosive explosive) {
        explosives.remove(explosive);
    }
    
    // Squad Tactics
    public void setActiveSquadTactic(SquadTactic tactic) {
        this.activeSquadTactic = tactic;
    }
    
    // Evolution and Mutations
    public void setEvolutionLevel(int level) {
        this.evolutionLevel = level;
    }
    
    public void addMutation(String mutation) {
        mutations.add(mutation);
    }
    
    public void removeMutation(String mutation) {
        mutations.remove(mutation);
    }
    
    public boolean hasMutation(String mutation) {
        return mutations.contains(mutation);
    }
    
    // Reactive Abilities
    public void addReactiveAbility(ReactiveAbility ability) {
        reactiveAbilities.add(ability);
    }
    
    public boolean hasBladestorm() {
        return reactiveAbilities.stream().anyMatch(ability -> 
            ability.getType().toString().contains("BLADESTORM"));
    }
    
    public int getBladestormDamage() {
        return hasBladestorm() ? 3 : 0;
    }
    
    public int getBladestormAccuracy() {
        return hasBladestorm() ? 75 : 0;
    }
    
    public boolean triggerBladestorm(IUnit target) {
        if (hasBladestorm() && canPerformBladestorm()) {
            // Trigger bladestorm attack
            return true;
        }
        return false;
    }
    
    public void processReactiveAbilityCooldowns() {
        reactiveAbilities.forEach(ReactiveAbility::processCooldown);
    }
    
    // Squad Sight
    public boolean canUseSquadSight() {
        return soldierClass == SoldierClass.SNIPER && !isConcealed;
    }
    
    public int getSquadSightRangeBonus() {
        return canUseSquadSight() ? 8 : 0;
    }
    
    public boolean isTargetVisibleThroughSquadSight(IUnit target, List<IUnit> allies) {
        if (!canUseSquadSight()) return false;
        
        // Check if any ally can see the target
        return allies.stream().anyMatch(ally -> 
            ally.getPosition() != null && target.getPosition() != null &&
            calculateDistance(ally.getPosition(), target.getPosition()) <= 10); // Default view range
    }
    
    // Attack Range
    public int getTotalAttackRange() {
        int totalRange = attackRange;
        if (weapon != null) {
            totalRange += 5; // Simplified weapon range
        }
        return totalRange;
    }
    
    // Concealment Management
    public boolean shouldBreakConcealment() {
        return isConcealed && (weapon != null || !explosives.isEmpty());
    }
    
    public void forceBreakConcealment() {
        setConcealed(false);
    }
    
    public boolean canMaintainConcealment(String actionType) {
        if (!isConcealed) return false;
        
        switch (actionType.toLowerCase()) {
            case "move":
            case "overwatch":
                return true;
            case "attack":
            case "grenade":
                return false;
            default:
                return true;
        }
    }
    
    public int getConcealmentDetectionRange() {
        return isConcealed ? 6 : 0;
    }
    
    // Overwatch Ambush
    public void setOverwatchAmbush(boolean ambush) {
        this.isInOverwatchAmbush = ambush;
    }
    
    public boolean isInOverwatchAmbush() {
        return isInOverwatchAmbush;
    }
    
    public int getOverwatchAmbushAccuracyBonus() {
        return isInOverwatchAmbush ? 15 : 0;
    }
    
    public int getOverwatchAmbushDamageBonus() {
        return isInOverwatchAmbush ? 2 : 0;
    }
    
    // Action Validation
    public boolean canPerformMove() {
        return canMove() && !hasFallen;
    }
    
    public boolean canPerformAttack() {
        return canAttack() && weapon != null;
    }
    
    public boolean canPerformOverwatch() {
        return hasActionPoints && !isSuppressed;
    }
    
    public boolean canPerformSuppression() {
        return hasActionPoints && weapon != null;
    }
    
    public boolean canPerformGrenade() {
        return hasActionPoints && !explosives.isEmpty();
    }
    
    public boolean canPerformMedikit() {
        return hasActionPoints && !isStabilized;
    }
    
    public boolean canPerformConceal() {
        return canConceal() && !isConcealed;
    }
    
    public boolean canPerformBladestorm() {
        return hasBladestorm() && !isSuppressed;
    }
    
    public boolean canPerformRapidFire() {
        return hasActionPoints && weapon != null;
    }
    
    public boolean canPerformBluescreen() {
        return hasActionPoints && weapon != null;
    }
    
    public boolean canPerformVolatileMix() {
        return hasActionPoints && !explosives.isEmpty();
    }
    
    public boolean canPerformReload() {
        return weapon != null;
    }
    
    public boolean canPerformDefend() {
        return hasActionPoints;
    }
    
    public boolean canPerformSpecialAbility() {
        return hasActionPoints && (!abilities.isEmpty() || !psionicAbilities.isEmpty());
    }
    
    // Environmental Hazards
    public void applyEnvironmentalHazard(StatusEffect hazard, int duration, int intensity) {
        StatusEffectData effect = new StatusEffectData(hazard, duration, intensity);
        addStatusEffect(effect);
    }
    
    public boolean isInEnvironmentalHazard() {
        return statusEffects.stream().anyMatch(effect -> 
            effect.getEffect().toString().contains("HAZARD"));
    }
    
    public int getEnvironmentalHazardDamage() {
        return statusEffects.stream()
            .filter(effect -> effect.getEffect().toString().contains("HAZARD"))
            .mapToInt(StatusEffectData::getIntensity)
            .sum();
    }
    
    // Utility Methods
    private double calculateDistance(Position pos1, Position pos2) {
        if (pos1 == null || pos2 == null) return Double.MAX_VALUE;
        
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    // Implementation of missing IUnit abstract methods
    @Override
    public int getCriticalDamageMultiplier() { return 2; }
    
    @Override
    public boolean isSuppressed() { return this.isSuppressed; }
    
    @Override
    public int getHeight() { return 1; }
    
    @Override
    public void setSuppressed(boolean suppressed) { this.isSuppressed = suppressed; }
    
    @Override
    public void applySuppression(int turns) { 
        this.isSuppressed = true;
        // Could add status effect here if needed
    }
    
    @Override
    public void setCriticalDamageMultiplier(int multiplier) { /* Not used in CombatUnit */ }
    
    @Override
    public int getCriticalChance() { return 5; }
    
    @Override
    public int getTotalCriticalChance() { return getCriticalChance(); }
    
    @Override
    public int getSuppressionTurns() { return isSuppressed ? 2 : 0; }
    
    @Override
    public void setSuppressionTurns(int turns) { 
        this.isSuppressed = turns > 0;
    }
    
    @Override
    public void setHeight(int height) { /* Height not used in CombatUnit */ }
    
    @Override
    public void removeSuppression() { this.isSuppressed = false; }
    
    @Override
    public void setCriticalChance(int chance) { /* Critical chance not modifiable in CombatUnit */ }
}

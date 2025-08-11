package com.aliensattack.core.model;

import com.aliensattack.core.enums.UnitType;
import com.aliensattack.core.enums.StatusEffect;
import com.aliensattack.core.enums.SoldierClass;
import com.aliensattack.core.enums.VisibilityType;
import com.aliensattack.core.enums.ReactiveAbilityType;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.core.interfaces.IUnit;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a combat unit on the tactical field
 */
@Getter
@Setter
public class Unit implements IUnit {
    private String id;
    private String name;
    private int maxHealth;
    private int currentHealth;
    private int movementRange;
    private int attackRange;
    private int attackDamage;
    private int viewRange; // View range
    private Position position;
    private UnitType unitType;
    private boolean hasActionPoints;
    private double actionPoints; // Changed to double to support fractional AP
    private Weapon weapon;
    private int height; // Height level (0 = ground, 1 = elevated, etc.)
    private List<StatusEffectData> statusEffects;
    private boolean isOverwatching;
    private int overwatchChance; // Percentage chance for overwatch shot
    private int criticalChance; // Base critical hit chance
    private int criticalDamageMultiplier; // Multiplier for critical damage
    private SoldierClass soldierClass; // Soldier class (for human soldiers)
    private List<SoldierAbility> abilities; // Soldier abilities
    private VisibilityType visibility; // Current visibility state
    private boolean isConcealed; // Stealth mode
    private boolean hasFallen; // Fallen from height
    private int fallDamage; // Damage from falling
    private Armor armor; // Unit's armor
    private int initiative; // Turn order priority
    private boolean isFlanked; // Is unit flanked
    private boolean isStabilized; // Medical stabilization
    private int medicalPriority; // Priority for medical attention
    private List<PsionicAbility> psionicAbilities; // Psionic abilities
    private int psiStrength; // Psionic power level
    private int psiResistance; // Resistance to psionic attacks
    private boolean isSuppressed; // Under suppression fire
    private int suppressionTurns; // Turns remaining under suppression
    private List<Explosive> explosives; // Carried explosives
    private SquadTactic activeSquadTactic; // Currently active squad tactic
    private int evolutionLevel; // Unit evolution level
    private List<String> mutations; // Active mutations
    private List<ReactiveAbility> reactiveAbilities; // Reactive abilities like Bladestorm
    
    public Unit(String name, int maxHealth, int movementRange, int attackRange, int attackDamage, UnitType unitType) {
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
        this.height = 0;
        this.statusEffects = new ArrayList<>();
        this.isOverwatching = false;
        this.overwatchChance = GameConfig.getDefaultOverwatchChance();
        this.criticalChance = GameConfig.getDefaultCriticalChance();
        this.criticalDamageMultiplier = GameConfig.getDefaultCriticalDamageMultiplier();
        this.soldierClass = null;
        this.abilities = new ArrayList<>();
        this.visibility = VisibilityType.CLEAR;
        this.isConcealed = false;
        this.hasFallen = false;
        this.fallDamage = 0;
        this.armor = null;
        this.initiative = GameConfig.getDefaultInitiative();
        this.isFlanked = false;
        this.isStabilized = false;
        this.medicalPriority = 0;
        this.psionicAbilities = new ArrayList<>();
        this.psiStrength = 0;
        this.psiResistance = 0;
        this.isSuppressed = false;
        this.suppressionTurns = 0;
        this.explosives = new ArrayList<>();
        this.activeSquadTactic = null;
        this.evolutionLevel = 0;
        this.mutations = new ArrayList<>();
        this.reactiveAbilities = new ArrayList<>();
        
        // Set view range based on unit type
        this.viewRange = getDefaultViewRange(unitType);
    }
    
    /**
     * Returns default view range for unit type
     */
    private int getDefaultViewRange(UnitType unitType) {
        return switch (unitType) {
            case SOLDIER -> GameConfig.getViewRange("soldier");
            case ALIEN -> GameConfig.getViewRange("alien");
            case ALIEN_RULER -> GameConfig.getViewRange("alien");
            case CIVILIAN -> GameConfig.getViewRange("civilian");
            case VEHICLE -> GameConfig.getViewRange("vehicle");
            case ROBOTIC -> GameConfig.getViewRange("robotic");
        };
    }
    
    public boolean canMove() {
        return hasActionPoints && actionPoints > 0;
    }
    
    public boolean canAttack() {
        return hasActionPoints && actionPoints > 0;
    }
    
    @Override
    public boolean hasActionPoints() {
        return hasActionPoints;
    }
    
    public void spendActionPoint() {
        if (actionPoints > 0) {
            actionPoints--;
        }
    }
    
    /**
     * Spend a specific amount of action points
     */
    public void spendActionPoints(double amount) {
        if (actionPoints >= amount) {
            actionPoints -= amount;
        }
    }
    
    public void resetActionPoints() {
        actionPoints = GameConfig.getDefaultActionPoints();
    }
    
    public boolean takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            return true; // Unit is dead
        }
        return false;
    }
    
    /**
     * Revive unit from death
     */
    public void revive() {
        if (!isAlive()) {
            currentHealth = maxHealth / 2; // Revive with half health
        }
    }
    
    /**
     * Check if unit has any status effects
     */
    public boolean hasStatusEffects() {
        return !statusEffects.isEmpty();
    }
    
    /**
     * Clear all status effects
     */
    public void clearStatusEffects() {
        statusEffects.clear();
    }
    
    /**
     * Heal unit
     */
    public void heal(int amount) {
        if (isAlive()) {
            currentHealth = Math.min(currentHealth + amount, maxHealth);
        }
    }
    
    public boolean isAlive() {
        return currentHealth > 0;
    }
    
    public double getHealthPercentage() {
        return (double) currentHealth / maxHealth;
    }
    
    public void setPosition(int x, int y) {
        this.position = new Position(x, y);
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    // Additional getter methods for compatibility
    public String getName() {
        return name;
    }
    
    public int getMovementRange() {
        return movementRange;
    }
    
    public int getAttackRange() {
        return attackRange;
    }
    
    public int getAttackDamage() {
        return attackDamage;
    }
    
    public UnitType getUnitType() {
        return unitType;
    }
    
    public double getActionPoints() {
        return actionPoints;
    }
    
    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }
    
    // Psionic methods
    public List<PsionicAbility> getPsionicAbilities() {
        return new ArrayList<>(psionicAbilities);
    }
    
    public void addPsionicAbility(PsionicAbility ability) {
        psionicAbilities.add(ability);
    }
    
    public void removePsionicAbility(PsionicAbility ability) {
        psionicAbilities.remove(ability);
    }
    
    public int getPsiStrength() {
        return psiStrength;
    }
    
    public void setPsiStrength(int psiStrength) {
        this.psiStrength = psiStrength;
    }
    
    public int getPsiResistance() {
        return psiResistance;
    }
    
    public void setPsiResistance(int psiResistance) {
        this.psiResistance = psiResistance;
    }
    
    // Suppression methods
    public boolean isSuppressed() {
        return isSuppressed;
    }
    
    public void setSuppressed(boolean suppressed) {
        isSuppressed = suppressed;
    }
    
    public int getSuppressionTurns() {
        return suppressionTurns;
    }
    
    public void setSuppressionTurns(int turns) {
        suppressionTurns = turns;
    }
    
    /**
     * Apply suppression to unit
     */
    public void applySuppression(int turns) {
        isSuppressed = true;
        suppressionTurns = turns;
        
        // Add suppression status effect
        StatusEffectData suppressionEffect = new StatusEffectData(
            StatusEffect.SUPPRESSED, 
            turns, 
            30  // 30% accuracy penalty
        );
        addStatusEffect(suppressionEffect);
    }
    
    /**
     * Remove suppression from unit
     */
    public void removeSuppression() {
        isSuppressed = false;
        suppressionTurns = 0;
        
        // Remove suppression status effect
        statusEffects.removeIf(effect -> effect.getEffect() == StatusEffect.SUPPRESSED);
    }
    
    /**
     * Check if unit can move while suppressed
     */
    public boolean canMoveWhileSuppressed() {
        return !isSuppressed || suppressionTurns <= 0;
    }
    
    /**
     * Get suppression accuracy penalty
     */
    public int getSuppressionAccuracyPenalty() {
        if (isSuppressed && suppressionTurns > 0) {
            return 30; // 30% accuracy penalty
        }
        return 0;
    }
    
    // Explosives methods
    public List<Explosive> getExplosives() {
        return new ArrayList<>(explosives);
    }
    
    public void addExplosive(Explosive explosive) {
        explosives.add(explosive);
    }
    
    public void removeExplosive(Explosive explosive) {
        explosives.remove(explosive);
    }
    
    // Squad tactic methods
    public SquadTactic getActiveSquadTactic() {
        return activeSquadTactic;
    }
    
    public void setActiveSquadTactic(SquadTactic tactic) {
        activeSquadTactic = tactic;
    }
    
    // Evolution methods
    public int getEvolutionLevel() {
        return evolutionLevel;
    }
    
    public void setEvolutionLevel(int level) {
        evolutionLevel = level;
    }
    
    public List<String> getMutations() {
        return new ArrayList<>(mutations);
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
    
    public int getViewRange() {
        return viewRange;
    }
    
    public void setViewRange(int viewRange) {
        this.viewRange = viewRange;
    }
    
    // Weapon methods
    public Weapon getWeapon() {
        return weapon;
    }
    
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
    
    // Height methods
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    // Status effect methods
    public List<StatusEffectData> getStatusEffects() {
        return new ArrayList<>(statusEffects);
    }
    
    public void addStatusEffect(StatusEffectData effect) {
        statusEffects.add(effect);
    }
    
    public void removeStatusEffect(StatusEffectData effect) {
        statusEffects.remove(effect);
    }
    
    public boolean hasStatusEffect(StatusEffect effect) {
        return statusEffects.stream()
            .anyMatch(effectData -> effectData.getEffect() == effect && effectData.isActive());
    }
    
    public void processStatusEffects() {
        statusEffects.removeIf(StatusEffectData::isExpired);
        statusEffects.forEach(StatusEffectData::decrementDuration);
    }
    
    // Overwatch methods
    public boolean isOverwatching() {
        return isOverwatching;
    }
    
    public void setOverwatching(boolean overwatching) {
        isOverwatching = overwatching;
    }
    
    public int getOverwatchChance() {
        return overwatchChance;
    }
    
    public void setOverwatchChance(int overwatchChance) {
        this.overwatchChance = overwatchChance;
    }
    
    // Critical hit methods
    public int getCriticalChance() {
        return criticalChance;
    }
    
    public void setCriticalChance(int criticalChance) {
        this.criticalChance = criticalChance;
    }
    
    public int getCriticalDamageMultiplier() {
        return criticalDamageMultiplier;
    }
    
    public void setCriticalDamageMultiplier(int multiplier) {
        this.criticalDamageMultiplier = multiplier;
    }
    
    // Check if unit can take actions (not stunned)
    public boolean canTakeActions() {
        return !hasStatusEffect(StatusEffect.STUNNED);
    }
    
    // Get total critical chance including weapon bonus
    public int getTotalCriticalChance() {
        int total = criticalChance;
        if (weapon != null) {
            total += weapon.getCriticalChance();
        }
        return total;
    }
    
    // Soldier class methods
    public SoldierClass getSoldierClass() {
        return soldierClass;
    }
    
    public void setSoldierClass(SoldierClass soldierClass) {
        this.soldierClass = soldierClass;
    }
    
    // Abilities methods
    public List<SoldierAbility> getAbilities() {
        return new ArrayList<>(abilities);
    }
    
    public void addAbility(SoldierAbility ability) {
        abilities.add(ability);
    }
    
    public void removeAbility(SoldierAbility ability) {
        abilities.remove(ability);
    }
    
    public boolean hasAbility(String abilityName) {
        return abilities.stream()
            .anyMatch(ability -> ability.getName().equals(abilityName));
    }
    
    public void processAbilityCooldowns() {
        abilities.forEach(SoldierAbility::processCooldown);
    }
    
    // Visibility methods
    public VisibilityType getVisibility() {
        return visibility;
    }
    
    public void setVisibility(VisibilityType visibility) {
        this.visibility = visibility;
    }
    
    public boolean isConcealed() {
        return isConcealed;
    }
    
    public void setConcealed(boolean concealed) {
        isConcealed = concealed;
        if (concealed) {
            visibility = VisibilityType.CONCEALED;
        } else {
            visibility = VisibilityType.CLEAR;
        }
    }
    
    // Fall damage methods
    public boolean hasFallen() {
        return hasFallen;
    }
    
    public void setHasFallen(boolean fallen) {
        hasFallen = fallen;
    }
    
    public int getFallDamage() {
        return fallDamage;
    }
    
    public void setFallDamage(int damage) {
        fallDamage = damage;
    }
    
    public void applyFallDamage(int height) {
        if (height > 1) {
            fallDamage = (height - 1) * GameConfig.getFallDamagePerLevel();
            takeDamage(fallDamage);
            hasFallen = true;
        }
    }
    
    // Check if unit can move (not fallen)
    public boolean canMoveAfterFall() {
        return !hasFallen;
    }
    
    // Armor methods
    public Armor getArmor() {
        return armor;
    }
    
    public void setArmor(Armor armor) {
        this.armor = armor;
    }
    
    public boolean hasArmor() {
        return armor != null && !armor.isDestroyed();
    }
    
    // Initiative methods
    public int getInitiative() {
        return initiative;
    }
    
    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }
    
    // Flanking methods
    public boolean isFlanked() {
        return isFlanked;
    }
    
    public void setFlanked(boolean flanked) {
        isFlanked = flanked;
    }
    
    // Medical methods
    public boolean isStabilized() {
        return isStabilized;
    }
    
    public void setStabilized(boolean stabilized) {
        isStabilized = stabilized;
    }
    
    public int getMedicalPriority() {
        return medicalPriority;
    }
    
    public void setMedicalPriority(int priority) {
        medicalPriority = priority;
    }
    
    // Override takeDamage to include armor
    public boolean takeDamageWithArmor(int damage) {
        int actualDamage = damage;
        
        // Apply armor protection
        if (hasArmor()) {
            actualDamage = armor.takeDamage(damage);
        }
        
        // Apply damage to unit
        currentHealth -= actualDamage;
        if (currentHealth <= 0) {
            currentHealth = 0;
            return true; // Unit is dead
        }
        return false;
    }
    
    // Medical stabilization
    public void stabilize() {
        if (!isStabilized && currentHealth <= maxHealth * 0.25) {
            isStabilized = true;
            // Prevent death for one turn
        }
    }
    
    // Heal unit with medical treatment
    public void healWithMedical(int amount) {
        if (isStabilized) {
            isStabilized = false; // Remove stabilization when healed
        }
        currentHealth = Math.min(currentHealth + amount, maxHealth);
    }
    
    // Additional getter methods for compatibility
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    /**
     * Check if unit can be concealed
     */
    public boolean canConceal() {
        return !isConcealed && !hasFallen && currentHealth > 0;
    }
    
    /**
     * Conceal the unit (stealth mode)
     */
    public boolean conceal() {
        if (canConceal()) {
            isConcealed = true;
            visibility = VisibilityType.CONCEALED;
            return true;
        }
        return false;
    }
    
    /**
     * Reveal the unit (break concealment)
     */
    public void reveal() {
        isConcealed = false;
        visibility = VisibilityType.REVEALED;
    }
    
    /**
     * Check if unit is detected by enemy
     */
    public boolean isDetectedBy(Unit enemy) {
        if (!isConcealed) {
            return true;
        }
        
        // Check if enemy can see this unit
        int distance = position.getDistanceTo(enemy.getPosition());
        return distance <= enemy.getViewRange();
    }
    
    /**
     * Get stealth bonus for attacks
     */
    public int getStealthAttackBonus() {
        if (isConcealed) {
            return 25; // +25% accuracy when concealed
        }
        return 0;
    }

    /**
     * Apply environmental hazard effect
     */
    public void applyEnvironmentalHazard(StatusEffect hazard, int duration, int intensity) {
        StatusEffectData hazardEffect = new StatusEffectData(hazard, duration, intensity);
        addStatusEffect(hazardEffect);
        
        // Apply immediate effects
        switch (hazard) {
            case NONE:
                // No effect
                break;
            case ACID_BURN:
            case BURNING:
            case CORROSION:
                takeDamage(intensity);
                break;
            case ELECTROCUTED:
                takeDamage(intensity);
                // Add stun effect
                StatusEffectData stunEffect = new StatusEffectData(StatusEffect.STUNNED, 1, 0);
                addStatusEffect(stunEffect);
                break;
            case RADIATION:
                takeDamage(intensity);
                // Reduce stats
                setInitiative(getInitiative() - 2);
                break;
            case FROSTBITE:
                takeDamage(intensity);
                // Reduce movement
                setMovementRange(getMovementRange() - 1);
                break;
            case STUNNED:
                // Stun effect already handled
                break;
            case BLEEDING:
            case POISONED:
                takeDamage(intensity);
                break;
            case FROZEN:
                // Freeze effect - cannot move but increased defense
                break;
            case MARKED:
                // Marked for increased damage
                break;
            case OVERWATCH:
                // Overwatch effect
                break;
            case SUPPRESSED:
                // Suppression effect
                break;
            case GRAPPLED:
            case BOUND:
                // Movement restriction
                break;
            case KNOCKED_DOWN:
            case KNOCKED_BACK:
                // Knockdown/back effects
                break;
            case GRENADED:
            case FLASHBANGED:
                // Explosive effects
                break;
            case SMOKED:
                // Smoke effect - reduced visibility
                break;
            case HIDDEN:
            case REVEALED:
                // Stealth effects
                break;
            case PANICKED:
                // Panic effect
                break;
            case HEALING:
                // Healing effect
                heal(intensity);
                break;
            case PROTECTED:
            case EXPOSED:
            case VULNERABLE:
            case RESISTANT:
            case IMMUNE:
                // Defense modifier effects
                break;
            case WOUNDED:
                // Wounded effect - reduced stats
                break;
            case CAPTURED:
            case EXTRACTED:
            case DEAD:
                // Special status effects
                break;
            case MUTATION_RISK:
                // Mutation risk effect
                break;
            case FREEZING:
                takeDamage(intensity);
                // Freezing effect
                break;
            case ARMOR_DEGRADATION:
                // Armor degradation effect
                break;
            case WEAPON_MALFUNCTION:
                // Weapon malfunction effect
                break;
            case CONTROLLED:
                // Mind control effect
                break;
            case MIND_MERGED:
                // Mind merge effect
                break;
            case DOMINATED:
                // Domination effect
                break;
            case PSYCHIC_SHIELD:
                // Psychic shield effect
                break;
            case MIND_SHIELD:
                // Mind shield effect
                break;
        }
    }
    
    /**
     * Check if unit is in environmental hazard
     */
    public boolean isInEnvironmentalHazard() {
        for (StatusEffectData effect : statusEffects) {
            switch (effect.getEffect()) {
                case NONE:
                    // No environmental hazard
                    break;
                case ACID_BURN:
                case BURNING:
                case BLEEDING:
                case ELECTROCUTED:
                case RADIATION:
                case CORROSION:
                case FROSTBITE:
                case POISONED:
                case FREEZING:
                    return true;
                case STUNNED:
                case FROZEN:
                case GRAPPLED:
                case BOUND:
                case KNOCKED_DOWN:
                case KNOCKED_BACK:
                case GRENADED:
                case FLASHBANGED:
                case SMOKED:
                case HIDDEN:
                case REVEALED:
                case PANICKED:
                case HEALING:
                case PROTECTED:
                case EXPOSED:
                case VULNERABLE:
                case RESISTANT:
                case IMMUNE:
                case WOUNDED:
                case CAPTURED:
                case EXTRACTED:
                case DEAD:
                case MUTATION_RISK:
                case ARMOR_DEGRADATION:
                case WEAPON_MALFUNCTION:
                case MARKED:
                case OVERWATCH:
                case SUPPRESSED:
                case CONTROLLED:
                case MIND_SHIELD:
                case DOMINATED:
                case PSYCHIC_SHIELD:
                case MIND_MERGED:
                    // Not an environmental hazard
                    break;
            }
        }
        return false;
    }
    
    /**
     * Get environmental hazard damage
     */
    public int getEnvironmentalHazardDamage() {
        int totalDamage = 0;
        for (StatusEffectData effect : statusEffects) {
            if (effect.isActive()) {
                switch (effect.getEffect()) {
                    case NONE:
                        // No damage
                        break;
                    case ACID_BURN:
                    case BURNING:
                    case BLEEDING:
                    case CORROSION:
                    case ELECTROCUTED:
                    case RADIATION:
                    case FROSTBITE:
                    case POISONED:
                    case FREEZING:
                        totalDamage += effect.getIntensity();
                        break;
                    case STUNNED:
                    case FROZEN:
                    case GRAPPLED:
                    case BOUND:
                    case KNOCKED_DOWN:
                    case KNOCKED_BACK:
                    case GRENADED:
                    case FLASHBANGED:
                    case SMOKED:
                    case HIDDEN:
                    case REVEALED:
                    case PANICKED:
                    case HEALING:
                    case PROTECTED:
                    case EXPOSED:
                    case VULNERABLE:
                    case RESISTANT:
                    case IMMUNE:
                    case WOUNDED:
                    case CAPTURED:
                    case EXTRACTED:
                    case DEAD:
                    case MUTATION_RISK:
                    case ARMOR_DEGRADATION:
                    case WEAPON_MALFUNCTION:
                    case MARKED:
                    case OVERWATCH:
                    case SUPPRESSED:
                    case CONTROLLED:
                    case MIND_SHIELD:
                    case DOMINATED:
                    case PSYCHIC_SHIELD:
                    case MIND_MERGED:
                        // Not an environmental hazard damage
                        break;
                }
            }
        }
        return totalDamage;
    }
    
    /**
     * Check if unit can use squad sight
     */
    public boolean canUseSquadSight() {
        // Only certain unit types can use squad sight
        return soldierClass == SoldierClass.SHARPSHOOTER || 
               soldierClass == SoldierClass.SPECIALIST;
    }
    
    /**
     * Get squad sight range bonus
     */
    public int getSquadSightRangeBonus() {
        if (canUseSquadSight()) {
            return 5; // +5 range for squad sight
        }
        return 0;
    }
    
    /**
     * Check if target is visible through squad sight
     */
    public boolean isTargetVisibleThroughSquadSight(Unit target, List<Unit> allies) {
        if (!canUseSquadSight()) {
            return false;
        }
        
        // Check if any ally can see the target
        for (Unit ally : allies) {
            if (ally.isAlive() && ally.getUnitType() == getUnitType()) {
                int distance = ally.getPosition().getDistanceTo(target.getPosition());
                if (distance <= ally.getViewRange()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Get total attack range including squad sight
     */
    public int getTotalAttackRange() {
        int baseRange = getAttackRange();
        if (getWeapon() != null) {
            baseRange = getWeapon().getRange();
        }
        return baseRange + getSquadSightRangeBonus();
    }

    /**
     * Check if unit should break concealment
     */
    public boolean shouldBreakConcealment() {
        if (!isConcealed) {
            return false;
        }
        
        // Break concealment if unit takes damage
        if (currentHealth < maxHealth) {
            return true;
        }
        
        // Break concealment if unit uses weapon
        if (weapon != null && weapon.getCurrentAmmo() < weapon.getAmmoCapacity()) {
            return true;
        }
        
        // Break concealment if unit is in environmental hazard
        if (isInEnvironmentalHazard()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Force break concealment
     */
    public void forceBreakConcealment() {
        if (isConcealed) {
            reveal();
        }
    }
    
    /**
     * Check if unit can maintain concealment after action
     */
    public boolean canMaintainConcealment(String actionType) {
        if (!isConcealed) {
            return false;
        }
        
        // Some actions don't break concealment
        switch (actionType.toLowerCase()) {
            case "move":
            case "overwatch":
                return true;
            case "attack":
            case "hack":
            case "use_ability":
                return false;
            default:
                return false;
        }
    }
    
    /**
     * Get concealment detection range
     */
    public int getConcealmentDetectionRange() {
        if (isConcealed) {
            return viewRange + 2; // Concealed units are harder to detect
        }
        return viewRange;
    }

    /**
     * Set overwatch ambush mode
     */
    public void setOverwatchAmbush(boolean ambush) {
        if (ambush) {
            // Add ambush status effect
            StatusEffectData ambushEffect = new StatusEffectData(StatusEffect.OVERWATCH, 1, 0);
            addStatusEffect(ambushEffect);
        }
    }
    
    /**
     * Check if unit is in overwatch ambush mode
     */
    public boolean isInOverwatchAmbush() {
        for (StatusEffectData effect : statusEffects) {
            if (effect.getEffect() == StatusEffect.OVERWATCH && effect.isActive()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get overwatch ambush accuracy bonus
     */
    public int getOverwatchAmbushAccuracyBonus() {
        if (isInOverwatchAmbush()) {
            return 15; // +15% accuracy for ambush
        }
        return 0;
    }
    
    /**
     * Get overwatch ambush damage bonus
     */
    public int getOverwatchAmbushDamageBonus() {
        if (isInOverwatchAmbush()) {
            return 5; // +5 damage for ambush
        }
        return 0;
    }
    
    /**
     * Check if unit has bladestorm ability
     */
    public boolean hasBladestorm() {
        for (ReactiveAbility ability : reactiveAbilities) {
            if (ability.getType() == ReactiveAbilityType.BLADESTORM && ability.canTrigger()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get bladestorm damage
     */
    public int getBladestormDamage() {
        for (ReactiveAbility ability : reactiveAbilities) {
            if (ability.getType() == ReactiveAbilityType.BLADESTORM) {
                return ability.getBladestormDamage();
            }
        }
        return 0;
    }
    
    /**
     * Get bladestorm accuracy
     */
    public int getBladestormAccuracy() {
        for (ReactiveAbility ability : reactiveAbilities) {
            if (ability.getType() == ReactiveAbilityType.BLADESTORM) {
                return ability.getBladestormAccuracy();
            }
        }
        return 0;
    }
    
    /**
     * Trigger bladestorm attack
     */
    public boolean triggerBladestorm(Unit target) {
        for (ReactiveAbility ability : reactiveAbilities) {
            if (ability.getType() == ReactiveAbilityType.BLADESTORM && ability.canTrigger()) {
                if (ability.trigger()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Process reactive ability cooldowns
     */
    public void processReactiveAbilityCooldowns() {
        reactiveAbilities.forEach(ReactiveAbility::processCooldown);
    }
    
    /**
     * Add reactive ability to unit
     */
    public void addReactiveAbility(ReactiveAbility ability) {
        reactiveAbilities.add(ability);
    }
    
    // Action availability methods
    public boolean canPerformMove() {
        return isAlive() && canMove() && actionPoints >= 1.0;
    }
    
    public boolean canPerformAttack() {
        return isAlive() && canAttack() && actionPoints >= 1.0 && weapon != null && weapon.hasAmmo();
    }
    
    public boolean canPerformOverwatch() {
        return isAlive() && actionPoints >= 1.0 && !isOverwatching();
    }
    
    public boolean canPerformSuppression() {
        return isAlive() && actionPoints >= 1.0 && weapon != null && weapon.hasAmmo();
    }
    
    public boolean canPerformGrenade() {
        return isAlive() && actionPoints >= 1.0 && explosives != null && !explosives.isEmpty();
    }
    
    public boolean canPerformMedikit() {
        return isAlive() && actionPoints >= 1.0 && hasAbility("Medikit");
    }
    
    public boolean canPerformConceal() {
        return isAlive() && canConceal() && !isConcealed();
    }
    
    public boolean canPerformBladestorm() {
        return isAlive() && hasBladestorm() && actionPoints >= 1.0;
    }
    
    public boolean canPerformRapidFire() {
        return isAlive() && weapon != null && weapon.canUseRapidFire() && actionPoints >= 1.0;
    }
    
    public boolean canPerformBluescreen() {
        return isAlive() && hasAbility("Bluescreen Protocol") && actionPoints >= 1.0;
    }
    
    public boolean canPerformVolatileMix() {
        return isAlive() && hasAbility("Volatile Mix") && actionPoints >= 1.0;
    }
    
    public boolean canPerformReload() {
        return isAlive() && weapon != null && !weapon.hasAmmo() && actionPoints >= 1.0;
    }
    
    public boolean canPerformDefend() {
        return isAlive() && actionPoints >= 1.0;
    }
    
    public boolean canPerformSpecialAbility() {
        return isAlive() && actionPoints >= 1.0 && (abilities != null && !abilities.isEmpty());
    }
    
    // Missing methods that are being called in compilation errors
    public int getAccuracy() {
        int baseAccuracy = 75; // Base accuracy
        if (weapon != null) {
            baseAccuracy += weapon.getAccuracy();
        }
        return baseAccuracy;
    }
    
    public int getDefense() {
        int baseDefense = 0;
        if (armor != null) {
            baseDefense += armor.getEffectiveDamageReduction();
        }
        return baseDefense;
    }
    
    public int getDodgeChance() {
        int baseDodge = 10; // Base dodge chance
        // Armor doesn't provide dodge bonus in this implementation
        return baseDodge;
    }
    
    public int getHealth() {
        return currentHealth;
    }
    
    // Additional setter methods for compatibility
    public void setAccuracy(int accuracy) {
        // This would typically modify weapon accuracy
        if (weapon != null) {
            // In a real implementation, this would set weapon accuracy
        }
    }
    
    public void setDefense(int defense) {
        // This would typically modify armor defense
        if (armor != null) {
            // In a real implementation, this would set armor defense
        }
    }
    
    public void setDodgeChance(int dodgeChance) {
        // This would typically modify unit dodge chance
        // In a real implementation, this would set dodge chance
    }
    
    public int getMovementPoints() {
        return movementRange;
    }
    
    public void setMovementPoints(int movementPoints) {
        this.movementRange = movementPoints;
    }
} 
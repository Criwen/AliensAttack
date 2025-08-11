package com.aliensattack.combat;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.field.ITacticalField;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Comprehensive combat manager implementing all XCOM 2 mechanics:
 * 1. Enhanced Destructible Environment
 * 2. Squad Cohesion System
 * 3. Height Advantage Mechanics
 * 4. Reactive Abilities System
 * 5. Advanced Terrain Interaction
 * 6. Chain Reaction System
 * 7. Deep Cover Mechanics
 * 8. Medikit System
 * 9. Ammo Types System
 * 10. Grenade Launcher System
 */
@Getter
@Setter
public class ComprehensiveXCOM2CombatManager extends FinalXCOM2CombatManager {
    
    private Random random;
    private List<EnvironmentObject> environmentObjects;
    private Map<String, SquadTactic> activeSquadTactics;
    private List<ReactiveAbility> globalReactiveAbilities;
    private Map<String, Medikit> medikits;
    private Map<String, AmmoTypeData> ammoTypes;
    private Map<String, Explosive> explosives;
    
    public ComprehensiveXCOM2CombatManager(ITacticalField field) {
        super(field);
        this.random = ThreadLocalRandom.current();
        this.environmentObjects = new ArrayList<>();
        this.activeSquadTactics = new HashMap<>();
        this.globalReactiveAbilities = new ArrayList<>();
        this.medikits = new HashMap<>();
        this.ammoTypes = new HashMap<>();
        this.explosives = new HashMap<>();
    }
    
    // =============================================================================
    // 1. ENHANCED DESTRUCTIBLE ENVIRONMENT
    // =============================================================================
    
    /**
     * Add environment object to the field
     */
    public void addEnvironmentObject(EnvironmentObject object) {
        environmentObjects.add(object);
    }
    
    /**
     * Damage environment object
     */
    public boolean damageEnvironmentObject(EnvironmentObject object, int damage) {
        if (object.takeDamage(damage)) {
            // Object was destroyed
            handleEnvironmentObjectDestruction(object);
            return true;
        }
        return false;
    }
    
    /**
     * Handle environment object destruction
     */
    private void handleEnvironmentObjectDestruction(EnvironmentObject object) {
        // Deal damage to nearby units
        int destructionDamage = object.getDestructionDamage();
        if (destructionDamage > 0) {
            List<IUnit> nearbyUnits = getUnitsInRange(object.getPosition(), 2);
            for (IUnit unit : nearbyUnits) {
                unit.takeDamage(destructionDamage);
            }
        }
        
        // Trigger explosion if object is explosive
        if (object.canExplode()) {
            triggerExplosion(object);
        }
        
        // Update field
        updateFieldAfterDestruction(object);
    }
    
    /**
     * Trigger explosion from environment object
     */
    private void triggerExplosion(EnvironmentObject object) {
        int explosionRadius = object.getExplosionRadius();
        int explosionDamage = object.getExplosionDamage();
        
        List<IUnit> unitsInExplosion = getUnitsInRange(object.getPosition(), explosionRadius);
        for (IUnit unit : unitsInExplosion) {
            unit.takeDamage(explosionDamage);
        }
        
        object.triggerExplosion();
    }
    
    /**
     * Update field after object destruction
     */
    private void updateFieldAfterDestruction(EnvironmentObject object) {
        // Update cover and line of sight
        if (object.providedCover()) {
            // Remove cover from this position
            // Note: Cover removal would need to be implemented in the field class
            // getField().removeCoverObject(object.getPosition());
        }
    }
    
    // =============================================================================
    // 2. SQUAD COHESION SYSTEM
    // =============================================================================
    
    /**
     * Activate squad tactic
     */
    public boolean activateSquadTactic(String tacticName, List<Unit> squadMembers) {
        SquadTactic tactic = activeSquadTactics.get(tacticName);
        if (tactic == null) {
            return false;
        }
        
        return tactic.activate(squadMembers);
    }
    
    /**
     * Deactivate squad tactic
     */
    public void deactivateSquadTactic(String tacticName) {
        SquadTactic tactic = activeSquadTactics.get(tacticName);
        if (tactic != null) {
            tactic.deactivate();
        }
    }
    
    /**
     * Get squad cohesion bonus for unit
     */
    public int getSquadCohesionBonus(Unit unit) {
        int totalBonus = 0;
        for (SquadTactic tactic : activeSquadTactics.values()) {
            if (tactic.isSquadMember(unit) && tactic.isActive()) {
                totalBonus += tactic.getTotalCohesionBonus();
            }
        }
        return totalBonus;
    }
    
    /**
     * Process squad tactic durations
     */
    public void processSquadTactics() {
        for (SquadTactic tactic : activeSquadTactics.values()) {
            tactic.processDuration();
        }
    }
    
    // =============================================================================
    // 3. HEIGHT ADVANTAGE MECHANICS
    // =============================================================================
    
    /**
     * Attack with height advantage
     */
    public CombatResult attackWithHeightAdvantage(Unit attacker, Unit target) {
        Position attackerPos = attacker.getPosition();
        
        if (!attackerPos.hasHeightAdvantage()) {
            return performAttack(attacker, target);
        }
        
        // Apply height advantage bonuses
        int heightAccuracyBonus = attackerPos.getHeightAccuracyBonus();
        int heightDamageBonus = attackerPos.getHeightDamageBonus();
        
        Weapon weapon = attacker.getWeapon();
        if (weapon != null) {
            weapon.setAccuracy(weapon.getAccuracy() + heightAccuracyBonus);
            weapon.setBaseDamage(weapon.getBaseDamage() + heightDamageBonus);
        }
        
        CombatResult result = performAttack(attacker, target);
        
        // Reset weapon stats
        if (weapon != null) {
            weapon.setAccuracy(weapon.getAccuracy() - heightAccuracyBonus);
            weapon.setBaseDamage(weapon.getBaseDamage() - heightDamageBonus);
        }
        
        return result;
    }
    
    /**
     * Check if unit has height advantage over target
     */
    public boolean hasHeightAdvantage(Unit attacker, Unit target) {
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        return attackerPos.isHigherThan(targetPos);
    }
    
    /**
     * Get height advantage bonus
     */
    public int getHeightAdvantageBonus(Unit unit) {
        Position pos = unit.getPosition();
        return pos.getTotalHeightAdvantageBonus();
    }
    
    // =============================================================================
    // 4. REACTIVE ABILITIES SYSTEM
    // =============================================================================
    
    /**
     * Add reactive ability to unit
     */
    public void addReactiveAbility(Unit unit, ReactiveAbility ability) {
        unit.addReactiveAbility(ability);
    }
    
    /**
     * Trigger reactive abilities for unit
     */
    public List<CombatResult> triggerReactiveAbilities(Unit unit, String triggerType) {
        List<CombatResult> results = new ArrayList<>();
        
        for (ReactiveAbility ability : unit.getReactiveAbilities()) {
            if (ability.canTrigger() && ability.requiresCondition(triggerType)) {
                if (ability.trigger()) {
                    // Perform reactive attack
                    CombatResult result = performReactiveAttack(unit, ability);
                    results.add(result);
                }
            }
        }
        
        return results;
    }
    
    /**
     * Perform reactive attack
     */
    private CombatResult performReactiveAttack(Unit attacker, ReactiveAbility ability) {
        // Find valid targets for reactive ability
        List<Unit> validTargets = getValidReactiveTargets(attacker, ability);
        
        if (validTargets.isEmpty()) {
            return new CombatResult(false, 0, "No valid targets for reactive ability");
        }
        
        // Select random target
        Unit target = validTargets.get(random.nextInt(validTargets.size()));
        
        // Perform attack with reactive ability bonuses
        int totalDamage = ability.getTotalDamage();
        int accuracy = ability.getAccuracy();
        
        return performAttackWithBonuses(attacker, target, accuracy, totalDamage);
    }
    
    /**
     * Get valid targets for reactive ability
     */
    private List<Unit> getValidReactiveTargets(Unit attacker, ReactiveAbility ability) {
        List<Unit> validTargets = new ArrayList<>();
        int range = ability.getTriggerRange();
        
        List<IUnit> unitsInRange = getUnitsInRange(attacker.getPosition(), range);
        for (IUnit unit : unitsInRange) {
            if (unit instanceof Unit && unit != attacker && isEnemy(attacker, (Unit) unit)) {
                validTargets.add((Unit) unit);
            }
        }
        
        return validTargets;
    }
    
    /**
     * Process reactive ability cooldowns
     */
    public void processReactiveAbilityCooldowns() {
        for (Unit unit : getAllUnits()) {
            for (ReactiveAbility ability : unit.getReactiveAbilities()) {
                ability.processCooldown();
            }
        }
    }
    
    // =============================================================================
    // 5. ADVANCED TERRAIN INTERACTION
    // =============================================================================
    
    /**
     * Check if position is hazardous
     */
    public boolean isPositionHazardous(Position position) {
        return position.isHazardous();
    }
    
    /**
     * Apply environmental hazards to unit
     */
    public void applyEnvironmentalHazards(Unit unit) {
        Position pos = unit.getPosition();
        if (pos.isHazardous()) {
            int hazardDamage = pos.getHazardDamage();
            unit.takeDamage(hazardDamage);
            
            // Apply status effects based on hazard type
            if (pos.isAcidLevel()) {
                unit.applyEnvironmentalHazard(StatusEffect.ACID_BURN, 3, hazardDamage);
            } else if (pos.isFireLevel()) {
                unit.applyEnvironmentalHazard(StatusEffect.BURNING, 2, hazardDamage);
            } else if (pos.isRadiationLevel()) {
                unit.applyEnvironmentalHazard(StatusEffect.RADIATION, 4, hazardDamage);
            } else if (pos.isFrostLevel()) {
                unit.applyEnvironmentalHazard(StatusEffect.FROSTBITE, 2, hazardDamage);
            } else if (pos.isElectrocutedLevel()) {
                unit.applyEnvironmentalHazard(StatusEffect.ELECTROCUTED, 1, hazardDamage);
            } else if (pos.isCorrosionLevel()) {
                unit.applyEnvironmentalHazard(StatusEffect.CORROSION, 3, hazardDamage);
            }
        }
    }
    
    /**
     * Get movement cost for position
     */
    public int getMovementCost(Position position) {
        return position.getMovementCost();
    }
    
    // =============================================================================
    // 6. CHAIN REACTION SYSTEM
    // =============================================================================
    
    /**
     * Trigger chain reaction
     */
    public List<CombatResult> triggerChainReaction(Position center, int radius) {
        List<CombatResult> results = new ArrayList<>();
        
        // Find all explosive objects in radius
        List<EnvironmentObject> explosivesInRange = new ArrayList<>();
        for (EnvironmentObject obj : environmentObjects) {
            if (obj.canExplode() && center.getDistanceTo(obj.getPosition()) <= radius) {
                explosivesInRange.add(obj);
            }
        }
        
        // Trigger explosions
        for (EnvironmentObject explosive : explosivesInRange) {
            triggerExplosion(explosive);
            
            // Deal damage to nearby units
                    List<IUnit> unitsInExplosion = getUnitsInRange(explosive.getPosition(), explosive.getExplosionRadius());
        for (IUnit unit : unitsInExplosion) {
            CombatResult result = new CombatResult(true, explosive.getExplosionDamage(), "Chain reaction explosion!");
            results.add(result);
            unit.takeDamage(explosive.getExplosionDamage());
        }
        }
        
        return results;
    }
    
    // =============================================================================
    // 7. DEEP COVER MECHANICS
    // =============================================================================
    
    /**
     * Attack unit in deep cover
     */
    public CombatResult attackDeepCoverUnit(Unit attacker, Unit target) {
        CoverObject cover = getField().getCoverObject(target.getPosition());
        
        if (cover != null && cover.providesDeepCover()) {
            // Apply deep cover penalties
            int deepCoverPenalty = cover.getDeepCoverDodgeBonus();
            int modifiedAccuracy = attacker.getWeapon().getAccuracy() - deepCoverPenalty;
            
            return performAttackWithBonuses(attacker, target, modifiedAccuracy, attacker.getWeapon().getBaseDamage());
        }
        
        return performAttack(attacker, target);
    }
    
    /**
     * Check if unit is in deep cover
     */
    public boolean isInDeepCover(Unit unit) {
        CoverObject cover = getField().getCoverObject(unit.getPosition());
        return cover != null && cover.providesDeepCover();
    }
    
    // =============================================================================
    // 8. MEDIKIT SYSTEM
    // =============================================================================
    
    /**
     * Use medikit on target
     */
    public CombatResult useMedikit(Unit medic, Unit target) {
        if (!medic.canPerformMedikit()) {
            return new CombatResult(false, 0, "Cannot use medikit");
        }
        
        Medikit medikit = medikits.get(medic.getName());
        if (medikit == null) {
            return new CombatResult(false, 0, "No medikit available");
        }
        
        if (!medikit.canUse()) {
            return new CombatResult(false, 0, "Medikit is empty");
        }
        
        // Use medikit
        int healingAmount = medikit.use();
        target.heal(healingAmount);
        medic.spendActionPoint();
        
        return new CombatResult(true, healingAmount, "Medikit used successfully");
    }
    
    /**
     * Add medikit to unit
     */
    public void addMedikit(Unit unit, Medikit medikit) {
        medikits.put(unit.getName(), medikit);
    }
    
    // =============================================================================
    // 9. AMMO TYPES SYSTEM
    // =============================================================================
    
    /**
     * Attack with specific ammo type
     */
    public CombatResult attackWithAmmoType(Unit attacker, Unit target, AmmoTypeData ammoType) {
        if (!attacker.canPerformAttack()) {
            return new CombatResult(false, 0, "Cannot attack");
        }
        
        Weapon weapon = attacker.getWeapon();
        if (weapon == null) {
            return new CombatResult(false, 0, "No weapon equipped");
        }
        
        // Apply ammo type bonuses
        int ammoDamageBonus = ammoType.getTotalDamageBonus();
        int ammoAccuracyBonus = ammoType.getTotalAccuracyBonus();
        int ammoCriticalBonus = ammoType.getTotalCriticalBonus();
        
        weapon.setBaseDamage(weapon.getBaseDamage() + ammoDamageBonus);
        weapon.setAccuracy(weapon.getAccuracy() + ammoAccuracyBonus);
        weapon.setCriticalChance(weapon.getCriticalChance() + ammoCriticalBonus);
        
        CombatResult result = performAttack(attacker, target);
        
        // Reset weapon stats
        weapon.setBaseDamage(weapon.getBaseDamage() - ammoDamageBonus);
        weapon.setAccuracy(weapon.getAccuracy() - ammoAccuracyBonus);
        weapon.setCriticalChance(weapon.getCriticalChance() - ammoCriticalBonus);
        
        return result;
    }
    
    // =============================================================================
    // 10. GRENADE LAUNCHER SYSTEM
    // =============================================================================
    
    /**
     * Attack with grenade launcher
     */
    public List<CombatResult> grenadeLauncherAttack(Unit attacker, Position targetPos) {
        if (!attacker.canPerformAttack()) {
            return new ArrayList<>();
        }
        
        Weapon weapon = attacker.getWeapon();
        if (weapon == null || !weapon.getWeaponType().equals("GRENADE_LAUNCHER")) {
            return new ArrayList<>();
        }
        
        List<CombatResult> results = new ArrayList<>();
        
        // Find all units in grenade radius
        int grenadeRadius = 3;
        List<IUnit> unitsInRadius = getUnitsInRange(targetPos, grenadeRadius);
        
        for (IUnit target : unitsInRadius) {
            if (target != attacker) {
                int grenadeDamage = weapon.getBaseDamage();
                CombatResult result = new CombatResult(true, grenadeDamage, "Grenade launcher hit!");
                results.add(result);
                target.takeDamage(grenadeDamage);
            }
        }
        
        attacker.spendActionPoint();
        weapon.useAmmo();
        
        return results;
    }
    
    // =============================================================================
    // ENHANCED TURN PROCESSING
    // =============================================================================
    
    @Override
    public void endTurn() {
        super.endTurn();
        
        // Process all new mechanics
        processSquadTactics();
        processReactiveAbilityCooldowns();
        processEnvironmentalHazards();
        processChainReactions();
    }
    
    /**
     * Process environmental hazards for all units
     */
    public void processEnvironmentalHazards() {
        for (Unit unit : getAllUnits()) {
            applyEnvironmentalHazards(unit);
        }
    }
    
    /**
     * Process chain reactions
     */
    private void processChainReactions() {
        // Check for chain reaction triggers
        for (EnvironmentObject obj : environmentObjects) {
            if (obj.canExplode() && obj.isDamaged()) {
                // Chance to trigger chain reaction
                if (random.nextInt(100) < 10) { // 10% chance
                    triggerChainReaction(obj.getPosition(), 2);
                }
            }
        }
    }
    
    // =============================================================================
    // HELPER METHODS
    // =============================================================================
    
    public List<IUnit> getUnitsInRange(Position position, int range) {
        List<IUnit> unitsInRange = new ArrayList<>();
        for (IUnit unit : units) {
            if (unit.getPosition().getDistanceTo(position) <= range) {
                unitsInRange.add(unit);
            }
        }
        return unitsInRange;
    }
    
    /**
     * Check if two units are enemies
     */
    private boolean isEnemy(Unit unit1, Unit unit2) {
        if (unit1.getUnitType() == UnitType.SOLDIER && unit2.getUnitType() == UnitType.ALIEN) return true;
        if (unit1.getUnitType() == UnitType.ALIEN && unit2.getUnitType() == UnitType.SOLDIER) return true;
        return false;
    }
    
    /**
     * Perform attack with custom bonuses
     */
    private CombatResult performAttackWithBonuses(Unit attacker, Unit target, int accuracy, int damage) {
        // Check hit
        int roll = random.nextInt(100) + 1;
        if (roll > accuracy) {
            return new CombatResult(false, 0, "Attack missed!");
        }
        
        // Apply damage
        boolean killed = target.takeDamageWithArmor(damage);
        
        String message = killed ? "Target killed!" : "Target hit!";
        return new CombatResult(true, damage, message);
    }
} 
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
 * Advanced combat manager implementing additional XCOM 2 mechanics:
 * 1. Psionic Combat System
 * 2. Environmental Hazards
 * 3. Squad Sight
 * 4. Hacking/Technical Abilities
 * 5. Concealment Breaks
 */
@Getter
@Setter
public class AdvancedXCOM2CombatManager extends XCOM2CombatManager {
    
    private Random random;
    
    public AdvancedXCOM2CombatManager(ITacticalField field) {
        super(field);
        this.random = ThreadLocalRandom.current();
    }
    
    // =============================================================================
    // INTERFACE IMPLEMENTATION METHODS
    // =============================================================================
    
    /**
     * Get all units in combat
     */
    public List<Unit> getAllUnits() {
        List<Unit> allUnits = new ArrayList<>();
        for (IUnit unit : units) {
            if (unit instanceof Unit) {
                allUnits.add((Unit) unit);
            }
        }
        return allUnits;
    }
    
    /**
     * Get player units
     */
    public List<Unit> getPlayerUnits() {
        return getAllUnits().stream()
            .filter(unit -> unit.getUnitType() == UnitType.SOLDIER)
            .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Get enemy units
     */
    public List<Unit> getEnemyUnits() {
        return getAllUnits().stream()
            .filter(unit -> unit.getUnitType() != UnitType.SOLDIER)
            .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Perform attack between units
     */
    public CombatResult performAttack(Unit attacker, Unit target) {
        if (!canAttack(attacker, target)) {
            return new CombatResult(false, 0, "Cannot attack target");
        }
        
        // Basic attack logic - this should be implemented based on your combat system
        int damage = attacker.getAttackDamage();
        target.takeDamage(damage);
        
        return new CombatResult(true, damage, "Attack successful");
    }
    
    /**
     * Check if unit can attack target
     */
    public boolean canAttack(Unit attacker, Unit target) {
        if (attacker == null || target == null) return false;
        if (!attacker.isAlive() || !target.isAlive()) return false;
        if (attacker.getActionPoints() < 1) return false;
        
        // Check if target is in range
        int distance = attacker.getPosition().getDistanceTo(target.getPosition());
        return distance <= attacker.getTotalAttackRange();
    }
    
    /**
     * Move unit to position
     */
    public boolean moveUnit(Unit unit, Position target) {
        if (unit == null || target == null) return false;
        if (!unit.isAlive()) return false;
        if (unit.getActionPoints() < 1) return false;
        
        // Basic movement logic
        unit.setPosition(target);
        unit.spendActionPoint();
        return true;
    }
    
    /**
     * Stealth attack
     */
    public CombatResult stealthAttack(Unit attacker, Unit target, Weapon weapon) {
        if (!attacker.isConcealed()) {
            return new CombatResult(false, 0, "Unit is not concealed");
        }
        
        // Perform stealth attack
        CombatResult result = performAttack(attacker, target);
        if (result.isSuccess()) {
            attacker.setConcealed(false); // Reveal concealment
        }
        
        return result;
    }
    
    // =============================================================================
    // 1. PSIONIC COMBAT SYSTEM
    // =============================================================================
    
    /**
     * Use psionic ability
     */
    public CombatResult usePsionicAbility(Unit caster, Unit target, PsionicAbility ability) {
        if (!caster.canTakeActions() || caster.getActionPoints() < 1) {
            return new CombatResult(false, 0, "Caster cannot take actions");
        }
        
        if (!ability.canUse()) {
            return new CombatResult(false, 0, "Ability is on cooldown");
        }
        
        // Check psi energy cost
        if (caster.getPsiStrength() < ability.getPsiCost()) {
            return new CombatResult(false, 0, "Insufficient psi energy");
        }
        
        // Check range
        int distance = caster.getPosition().getDistanceTo(target.getPosition());
        if (distance > ability.getRangeModifier()) {
            return new CombatResult(false, 0, "Target out of range");
        }
        
        // Use ability
        if (ability.useAbility()) {
            caster.spendActionPoint();
            
            // Apply psionic effect based on type
            return applyPsionicEffect(caster, target, ability);
        }
        
        return new CombatResult(false, 0, "Failed to use ability");
    }
    
    /**
     * Apply psionic effect based on ability type
     */
    private CombatResult applyPsionicEffect(Unit caster, Unit target, PsionicAbility ability) {
        int effect = ability.getPsionicEffect();
        
        switch (ability.getPsionicType()) {
            case MIND_CONTROL:
                return applyMindControl(caster, target, effect);
            case PSYCHIC_BLAST:
                return applyPsychicBlast(caster, target, effect);
            case PSYCHIC_SCREAM:
                return applyPsychicScream(caster, target, effect);
            case TELEPATHY:
                return applyTelepathy(caster, target, effect);
            case TELEPORT:
                return applyTeleport(caster, target, effect);
            case PSYCHIC_DOMINANCE:
                return applyPsychicDominance(caster, target, effect);
            case MIND_MERGE:
                return applyMindMerge(caster, target, effect);
            case PSYCHIC_BARRIER:
                return applyPsychicBarrier(caster, target, effect);
            case MIND_SCORCH:
                return applyMindScorch(caster, target, effect);
            case DOMINATION:
                return applyDomination(caster, target, effect);
            case PSYCHIC_SHIELD:
                return applyPsychicShield(caster, target, effect);
            case MIND_SHIELD:
                return applyMindShield(caster, target, effect);
            default:
                return new CombatResult(false, 0, "Unknown psionic ability");
        }
    }
    
    /**
     * Apply mind control effect
     */
    private CombatResult applyMindControl(Unit caster, Unit target, int effect) {
        // Check target's psi resistance
        int resistance = target.getPsiResistance();
        int successChance = effect - resistance;
        
        if (random.nextInt(100) < successChance) {
            // Mind control successful
            target.setStatusEffects(new ArrayList<>()); // Clear status effects
            StatusEffectData mindControlEffect = new StatusEffectData(
                StatusEffect.STUNNED, 3, 0
            );
            target.addStatusEffect(mindControlEffect);
            return new CombatResult(true, effect, "Mind control successful!");
        }
        
        return new CombatResult(false, 0, "Mind control resisted");
    }
    
    /**
     * Apply psychic blast effect
     */
    private CombatResult applyPsychicBlast(Unit caster, Unit target, int effect) {
        boolean killed = target.takeDamageWithArmor(effect);
        String message = killed ? "Target killed by psychic blast!" : "Target hit by psychic blast!";
        return new CombatResult(true, effect, message);
    }
    
    /**
     * Apply psychic scream effect
     */
    private CombatResult applyPsychicScream(Unit caster, Unit target, int effect) {
        StatusEffectData stunEffect = new StatusEffectData(StatusEffect.STUNNED, 2, 0);
        target.addStatusEffect(stunEffect);
        return new CombatResult(true, effect, "Target stunned by psychic scream!");
    }
    
    /**
     * Apply telepathy effect
     */
    private CombatResult applyTelepathy(Unit caster, Unit target, int effect) {
        // Telepathy reveals enemy information
        return new CombatResult(true, effect, "Enemy information revealed!");
    }
    
    /**
     * Apply teleport effect
     */
    private CombatResult applyTeleport(Unit caster, Unit target, int effect) {
        // Teleport caster to target position
        Position targetPos = target.getPosition();
        caster.setPosition(targetPos);
        return new CombatResult(true, effect, "Unit teleported to target position!");
    }
    
    /**
     * Apply psychic dominance effect for robotic control
     */
    private CombatResult applyPsychicDominance(Unit caster, Unit target, int effect) {
        // Check if target is robotic
        if (target.getUnitType() == UnitType.ROBOTIC) {
            // Apply robotic control
            StatusEffectData controlEffect = new StatusEffectData(StatusEffect.CONTROLLED, 3, 0);
            target.addStatusEffect(controlEffect);
            return new CombatResult(true, effect, "Robotic unit controlled!");
        }
        return new CombatResult(false, 0, "Target is not robotic");
    }
    
    /**
     * Apply mind merge effect
     */
    private CombatResult applyMindMerge(Unit caster, Unit target, int effect) {
        // Share consciousness between caster and target
        StatusEffectData mergeEffect = new StatusEffectData(StatusEffect.MIND_MERGED, 4, 0);
        caster.addStatusEffect(mergeEffect);
        target.addStatusEffect(mergeEffect);
        return new CombatResult(true, effect, "Minds merged - consciousness shared!");
    }
    
    /**
     * Apply psychic barrier effect
     */
    private CombatResult applyPsychicBarrier(Unit caster, Unit target, int effect) {
        // Create protective barrier
        StatusEffectData barrierEffect = new StatusEffectData(StatusEffect.PROTECTED, 5, effect);
        target.addStatusEffect(barrierEffect);
        return new CombatResult(true, effect, "Psychic barrier created!");
    }
    
    /**
     * Apply mind scorch effect
     */
    private CombatResult applyMindScorch(Unit caster, Unit target, int effect) {
        boolean killed = target.takeDamageWithArmor(effect);
        String message = killed ? "Target killed by mind scorch!" : "Target hit by mind scorch!";
        return new CombatResult(true, effect, message);
    }
    
    /**
     * Apply domination effect
     */
    private CombatResult applyDomination(Unit caster, Unit target, int effect) {
        // Check target's psi resistance
        int resistance = target.getPsiResistance();
        int successChance = effect - resistance;
        
        if (random.nextInt(100) < successChance) {
            StatusEffectData dominationEffect = new StatusEffectData(StatusEffect.DOMINATED, 3, 0);
            target.addStatusEffect(dominationEffect);
            return new CombatResult(true, effect, "Target dominated!");
        }
        
        return new CombatResult(false, 0, "Domination resisted");
    }
    
    /**
     * Apply psychic shield effect
     */
    private CombatResult applyPsychicShield(Unit caster, Unit target, int effect) {
        StatusEffectData shieldEffect = new StatusEffectData(StatusEffect.PSYCHIC_SHIELD, 3, effect);
        target.addStatusEffect(shieldEffect);
        return new CombatResult(true, effect, "Psychic shield applied!");
    }
    
    /**
     * Apply mind shield effect
     */
    private CombatResult applyMindShield(Unit caster, Unit target, int effect) {
        StatusEffectData mindShieldEffect = new StatusEffectData(StatusEffect.MIND_SHIELD, 3, effect);
        target.addStatusEffect(mindShieldEffect);
        return new CombatResult(true, effect, "Mind shield applied!");
    }
    
    // =============================================================================
    // 2. ENVIRONMENTAL HAZARDS
    // =============================================================================
    
    /**
     * Apply environmental hazard to unit
     */
    public CombatResult applyEnvironmentalHazard(Unit target, StatusEffect hazard, int duration, int intensity) {
        if (!target.isAlive()) {
            return new CombatResult(false, 0, "Target is not alive");
        }
        
        target.applyEnvironmentalHazard(hazard, duration, intensity);
        return new CombatResult(true, intensity, "Environmental hazard applied!");
    }
    
    /**
     * Create environmental hazard area
     */
    public List<CombatResult> createEnvironmentalHazardArea(Position center, int radius, 
                                                           StatusEffect hazard, int duration, int intensity) {
        List<CombatResult> results = new ArrayList<>();
        List<Unit> unitsInArea = getAllUnits().stream()
            .filter(unit -> unit.isAlive() && center.getDistanceTo(unit.getPosition()) <= radius)
            .collect(java.util.stream.Collectors.toList());
        
        for (Unit unit : unitsInArea) {
            if (unit.isAlive()) {
                CombatResult result = applyEnvironmentalHazard(unit, hazard, duration, intensity);
                results.add(result);
            }
        }
        
        return results;
    }
    
    /**
     * Process environmental hazard damage
     */
    public void processEnvironmentalHazards() {
        for (Unit unit : getAllUnits()) {
            if (unit.isAlive() && unit.isInEnvironmentalHazard()) {
                int damage = unit.getEnvironmentalHazardDamage();
                if (damage > 0) {
                    unit.takeDamage(damage);
                }
            }
        }
    }
    
    // =============================================================================
    // 3. SQUAD SIGHT
    // =============================================================================
    
    /**
     * Attack using squad sight
     */
    public CombatResult squadSightAttack(Unit attacker, Unit target, Weapon weapon) {
        if (!attacker.canUseSquadSight()) {
            return new CombatResult(false, 0, "Unit cannot use squad sight");
        }
        
        List<Unit> allies = getPlayerUnits();
        if (!attacker.isTargetVisibleThroughSquadSight(target, allies)) {
            return new CombatResult(false, 0, "Target not visible through squad sight");
        }
        
        // Apply squad sight range bonus
        int totalRange = attacker.getTotalAttackRange();
        int distance = attacker.getPosition().getDistanceTo(target.getPosition());
        
        if (distance > totalRange) {
            return new CombatResult(false, 0, "Target out of squad sight range");
        }
        
        // Perform attack with squad sight bonus
        return performAttack(attacker, target);
    }
    
    // =============================================================================
    // 4. HACKING/TECHNICAL ABILITIES
    // =============================================================================
    
    /**
     * Use hacking ability
     */
    public CombatResult useHackingAbility(Unit hacker, Unit target, SoldierAbility ability) {
        if (!ability.isHackingAbility()) {
            return new CombatResult(false, 0, "Not a hacking ability");
        }
        
        if (!hacker.canTakeActions() || hacker.getActionPoints() < ability.getActionPointCost()) {
            return new CombatResult(false, 0, "Hacker cannot take actions");
        }
        
        if (!ability.canUse()) {
            return new CombatResult(false, 0, "Ability is on cooldown");
        }
        
        // Check if target is hackable (robotic units)
        if (target.getUnitType() != UnitType.ALIEN) {
            return new CombatResult(false, 0, "Target is not hackable");
        }
        
        // Attempt hack
        int successChance = ability.getHackingSuccessChance();
        if (random.nextInt(100) < successChance) {
            ability.useAbility();
            hacker.spendActionPoint();
            
            // Apply hack effect
            StatusEffectData hackEffect = new StatusEffectData(StatusEffect.STUNNED, 2, 0);
            target.addStatusEffect(hackEffect);
            
            return new CombatResult(true, successChance, "Hack successful!");
        }
        
        return new CombatResult(false, 0, "Hack failed");
    }
    
    /**
     * Use technical ability
     */
    public CombatResult useTechnicalAbility(Unit technician, Unit target, SoldierAbility ability) {
        if (!ability.isTechnicalAbility()) {
            return new CombatResult(false, 0, "Not a technical ability");
        }
        
        if (!technician.canTakeActions() || technician.getActionPoints() < ability.getActionPointCost()) {
            return new CombatResult(false, 0, "Technician cannot take actions");
        }
        
        if (!ability.canUse()) {
            return new CombatResult(false, 0, "Ability is on cooldown");
        }
        
        ability.useAbility();
        technician.spendActionPoint();
        
        // Apply technical effect
        int effect = ability.getTechnicalEffectPower();
        boolean killed = target.takeDamageWithArmor(effect);
        
        String message = killed ? "Target destroyed by technical ability!" : "Target damaged by technical ability!";
        return new CombatResult(true, effect, message);
    }
    
    // =============================================================================
    // 5. CONCEALMENT BREAKS
    // =============================================================================
    
    /**
     * Check and process concealment breaks
     */
    public void processConcealmentBreaks() {
        for (Unit unit : getAllUnits()) {
            if (unit.isConcealed() && unit.shouldBreakConcealment()) {
                unit.forceBreakConcealment();
            }
        }
    }
    
    /**
     * Attack that may break concealment
     */
    public CombatResult concealedAttack(Unit attacker, Unit target, Weapon weapon) {
        if (!attacker.isConcealed()) {
            return new CombatResult(false, 0, "Unit is not concealed");
        }
        
        // Check if attack will break concealment
        if (!attacker.canMaintainConcealment("attack")) {
            attacker.forceBreakConcealment();
        }
        
        // Perform stealth attack
        return stealthAttack(attacker, target, weapon);
    }
    
    /**
     * Move that may break concealment
     */
    public boolean concealedMove(Unit unit, Position target) {
        if (!unit.isConcealed()) {
            return moveUnit(unit, target);
        }
        
        // Check if move will break concealment
        if (!unit.canMaintainConcealment("move")) {
            unit.forceBreakConcealment();
        }
        
        return moveUnit(unit, target);
    }
    
    // =============================================================================
    // ENHANCED TURN PROCESSING
    // =============================================================================
    
    @Override
    public void endTurn() {
        super.endTurn();
        
        // Process new mechanics
        processEnvironmentalHazards();
        processConcealmentBreaks();
        
        // Process ability cooldowns
        for (Unit unit : getAllUnits()) {
            unit.processAbilityCooldowns();
        }
    }
} 
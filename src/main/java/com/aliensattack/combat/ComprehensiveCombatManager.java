package com.aliensattack.combat;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Mission;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.TacticalField;
import com.aliensattack.core.interfaces.IUnit;

import lombok.Getter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Comprehensive combat manager with all XCOM 2 mechanics:
 * - Armor and protection system
 * - Action points and initiative
 * - Flanking and tactical positioning
 * - Medical system and stabilization
 * - Mission objectives and time limits
 */
public class ComprehensiveCombatManager extends CombatManagerExtended {
    private final Map<String, Unit> initiativeOrder;
    private final Mission currentMission;
    private final Random random;
    
    public ComprehensiveCombatManager(TacticalField field, Mission mission) {
        super(field);
        this.initiativeOrder = new ConcurrentHashMap<>();
        this.currentMission = mission;
        this.random = new Random();
    }
    
    /**
     * Attack with flanking consideration
     */
    public CombatResult attackWithFlanking(Unit attacker, Unit target, ShotType shotType) {
        // Check for flanking
        boolean isFlanking = checkFlanking(attacker, target);
        target.setFlanked(isFlanking);
        
        // Perform attack with flanking bonus
        CombatResult result = attackWithShotType(attacker, target, shotType);
        
        // Apply flanking bonus if successful
        if (result.isSuccess() && isFlanking) {
            int bonusDamage = (int)(result.getDamage() * 0.2);
            result.setDamage(result.getDamage() + bonusDamage);
            result.setMessage(result.getMessage() + " (Flanking bonus!)");
        }
        
        return result;
    }
    
    /**
     * Check if attacker is flanking target
     */
    private boolean checkFlanking(Unit attacker, Unit target) {
        // Simple flanking check - if target has cover, attacker might be flanking
        CoverType targetCover = ((TacticalField)getField()).getCoverTypeAt(
            target.getPosition().getX(), target.getPosition().getY());
        
        if (targetCover == CoverType.NONE) {
            return false; // No cover means no flanking
        }
        
        // Check if attacker is positioned to the side or behind target
        Position attackerPos = attacker.getPosition();
        Position targetPos = target.getPosition();
        
        // Simple flanking logic - if attacker is not directly in front
        int dx = Math.abs(attackerPos.getX() - targetPos.getX());
        int dy = Math.abs(attackerPos.getY() - targetPos.getY());
        
        return dx > 0 || dy > 0; // Not directly in front
    }
    
    /**
     * Stabilize wounded unit
     */
    public boolean stabilizeUnit(Unit medic, Unit target) {
        if (!medic.canTakeActions() || medic.getActionPoints() < 1) {
            return false;
        }
        
        if (target.isStabilized()) {
            return false; // Already stabilized
        }
        
        if (target.getCurrentHealth() > target.getMaxHealth() * 0.25) {
            return false; // Not wounded enough
        }
        
        target.stabilize();
        medic.spendActionPoint();
        
        return true;
    }
    
    /**
     * Heal unit with medical action
     */
    public boolean healUnit(Unit medic, Unit target, int healAmount) {
        if (!medic.canTakeActions() || medic.getActionPoints() < 1) {
            return false;
        }
        
        if (target.getCurrentHealth() >= target.getMaxHealth()) {
            return false; // Already at full health
        }
        
        target.healWithMedical(healAmount);
        medic.spendActionPoint();
        
        return true;
    }
    
    /**
     * Get units in initiative order
     */
    @Override
    public List<IUnit> getUnitsInInitiativeOrder() {
        List<IUnit> allUnits = new ArrayList<>();
        allUnits.addAll(getAllUnits());
        // Sort by initiative - cast to Unit to access getInitiative method
        allUnits.sort((u1, u2) -> {
            if (u1 instanceof Unit && u2 instanceof Unit) {
                return Integer.compare(((Unit) u2).getInitiative(), ((Unit) u1).getInitiative());
            }
            return 0;
        });
        return allUnits;
    }
    
    /**
     * Process initiative and turn order
     */
    public void processInitiative() {
        List<IUnit> unitsInOrder = getUnitsInInitiativeOrder();
        
        // Reset action points for all units
        for (IUnit unit : unitsInOrder) {
            if (unit.isAlive()) {
                unit.resetActionPoints();
            }
        }
    }
    
    /**
     * Check mission objectives
     */
    public void checkMissionObjectives() {
        if (currentMission == null) {
            return;
        }
        
        switch (currentMission.getType()) {
            case ASSASSINATION -> {
                // Check if assassination target is eliminated
                // This would need specific implementation based on target tracking
            }
            case EXTRACTION -> {
                // Check if VIP reached extraction point
                // This would need specific implementation based on VIP and extraction points
            }
            case DEFENSE -> {
                // Check if defense position is still held
                // This would need specific implementation based on defense positions
            }
            case SABOTAGE -> {
                // Check if sabotage objective is completed
                // This would need specific implementation based on objective tracking
            }
            case RECONNAISSANCE -> {
                // Check if reconnaissance objectives are completed
                // This would need specific implementation based on intel gathering
            }
            case ESCORT -> {
                // Check if VIP escort is successful
                // This would need specific implementation based on VIP status
            }
            case HACKING -> {
                // Check if hacking objectives are completed
                // This would need specific implementation based on terminal status
            }
            case DESTROY -> {
                // Check if destruction objectives are completed
                // This would need specific implementation based on facility status
            }
            case RESCUE -> {
                // Check if rescue objectives are completed
                // This would need specific implementation based on soldier status
            }
            case INFILTRATION -> {
                // Check if infiltration objectives are completed
                // This would need specific implementation based on stealth status
            }
            case RETALIATION -> {
                // Check if retaliation objectives are completed
                // This would need specific implementation based on retaliation targets
            }
            case GUERRILLA -> {
                // Check if guerrilla objectives are completed
                // This would need specific implementation based on guerrilla targets
            }
            case SUPPLY_RAID -> {
                // Check if supply raid objectives are completed
                // This would need specific implementation based on supply status
            }
            case TERROR -> {
                // Check if terror defense objectives are completed
                // This would need specific implementation based on civilian status
            }
            case COUNCIL -> {
                // Check if council mission objectives are completed
                // This would need specific implementation based on council requirements
            }
            case ELIMINATION -> {
                boolean allEnemiesDead = getEnemyUnits().stream().noneMatch(Unit::isAlive);
                if (allEnemiesDead) {
                    currentMission.completeObjective("Eliminate all enemy units");
                }
            }
            case TIMED_ASSAULT -> {
                // Check if timed assault objectives are completed
                // This would need specific implementation based on time limits
            }
        }
        
        // Check if all objectives are completed
        if (currentMission.areAllObjectivesCompleted()) {
            currentMission.setCompleted(true);
        }
    }
    
    /**
     * Advance mission turn
     */
    public void advanceMissionTurn() {
        if (currentMission != null) {
            currentMission.advanceTurn();
        }
    }
    
    /**
     * Get mission status
     */
    public String getMissionStatus() {
        if (currentMission == null) {
            return "No active mission";
        }
        return currentMission.getStatusDescription();
    }
    
    /**
     * Check if mission is completed
     */
    public boolean isMissionCompleted() {
        return currentMission != null && currentMission.isSuccessful();
    }
    
    /**
     * Check if mission is failed
     */
    public boolean isMissionFailed() {
        return currentMission != null && currentMission.isFailed();
    }
    
    /**
     * Get units that need medical attention
     */
    public List<Unit> getUnitsNeedingMedicalAttention() {
        return getAllUnits().stream()
            .filter(unit -> unit.isAlive() && 
                          (unit.getCurrentHealth() <= unit.getMaxHealth() * 0.25 || unit.isStabilized()))
            .sorted((u1, u2) -> Integer.compare(u2.getMedicalPriority(), u1.getMedicalPriority()))
            .toList();
    }
    
    /**
     * Repair armor
     */
    public boolean repairArmor(Unit engineer, Unit target, int repairAmount) {
        if (!engineer.canTakeActions() || engineer.getActionPoints() < 1) {
            return false;
        }
        
        if (target.getArmor() == null || target.getArmor().isDestroyed()) {
            return false; // No armor to repair
        }
        
        target.getArmor().repair(repairAmount);
        engineer.spendActionPoint();
        
        return true;
    }
    
    public void endTurnRound() {
        // Check mission objectives
        checkMissionObjectives();
        
        // Advance mission turn
        advanceMissionTurn();
        
        // Process medical stabilization
        for (Unit unit : getAllUnits()) {
            if (unit.isStabilized() && unit.getCurrentHealth() <= 0) {
                // Stabilized units don't die immediately
                unit.setCurrentHealth(1);
            }
        }
        
        super.endTurnCore();
    }
    
    @Override
    public void startTurn() {
        // Process initiative
        processInitiative();
        
        super.startTurn();
    }
} 
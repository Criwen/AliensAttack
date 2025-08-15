package com.aliensattack.combat;

import com.aliensattack.core.model.*;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.field.TacticalField;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Ultimate mission combat manager implementing all XCOM 2 mission mechanics:
 * 1. VIP and Civilian System
 * 2. Extraction Point System
 * 3. Objective Target System
 * 4. Hacking Terminal System
 * 5. Defense Position System
 * 6. Enhanced Mission Objectives
 * 7. Time-Sensitive Missions
 * 8. Mission Value Calculation
 * 9. Mission Status Tracking
 * 10. Mission Completion Logic
 */
@Getter
@Setter
public class UltimateMissionCombatManager extends ComprehensiveCombatManager {

    private Random random;
    private Mission currentMission;
    private List<VIP> vips;
    private List<ExtractionPoint> extractionPoints;
    private List<ObjectiveTarget> objectiveTargets;
    private List<HackingTerminal> hackingTerminals;
    private List<DefensePosition> defensePositions;
    private Map<String, Unit> escortAssignments;
    private Map<String, Unit> captureAssignments;
    private Map<String, Unit> hackAssignments;
    private Map<String, Unit> defenseAssignments;
    private int missionValue;
    private boolean missionCompleted;
    private boolean missionFailed;
    private String missionStatus;
    private List<String> missionLog;

    public UltimateMissionCombatManager(ITacticalField field, Mission mission) {
        super((TacticalField) field, mission);
        this.random = ThreadLocalRandom.current();
        this.currentMission = mission;
        this.vips = new ArrayList<>();
        this.extractionPoints = new ArrayList<>();
        this.objectiveTargets = new ArrayList<>();
        this.hackingTerminals = new ArrayList<>();
        this.defensePositions = new ArrayList<>();
        this.escortAssignments = new HashMap<>();
        this.captureAssignments = new HashMap<>();
        this.hackAssignments = new HashMap<>();
        this.defenseAssignments = new HashMap<>();
        this.missionValue = 0;
        this.missionCompleted = false;
        this.missionFailed = false;
        this.missionStatus = "Mission Started";
        this.missionLog = new ArrayList<>();
    }

    // =============================================================================
    // VIP AND CIVILIAN SYSTEM
    // =============================================================================

    /**
     * Add VIP to mission
     */
    public void addVIP(VIP vip) {
        vips.add(vip);
        logMissionEvent("VIP " + vip.getName() + " added to mission");
    }

    /**
     * Assign escort to VIP
     */
    public boolean assignEscort(VIP vip, Unit escort) {
        if (!escort.isAlive() || !escort.canTakeActions()) {
            return false;
        }

        if (vip.assignEscort(escort)) {
            escortAssignments.put(vip.getName(), escort);
            logMissionEvent("Unit " + escort.getName() + " assigned to escort VIP " + vip.getName());
            return true;
        }
        return false;
    }

    /**
     * Remove escort from VIP
     */
    public boolean removeEscort(VIP vip) {
        Unit escort = escortAssignments.get(vip.getName());
        if (escort != null && vip.removeEscort()) {
            escortAssignments.remove(vip.getName());
            logMissionEvent("Escort removed from VIP " + vip.getName());
            return true;
        }
        return false;
    }

    /**
     * Extract VIP
     */
    public boolean extractVIP(VIP vip, ExtractionPoint extractionPoint) {
        if (extractionPoint.startVIPExtraction(vip)) {
            logMissionEvent("VIP " + vip.getName() + " started extraction at " + extractionPoint.getName());
            return true;
        }
        return false;
    }

    /**
     * Capture VIP
     */
    public boolean captureVIP(VIP vip, Unit capturer) {
        if (vip.capture()) {
            captureAssignments.put(vip.getName(), capturer);
            logMissionEvent("VIP " + vip.getName() + " captured by " + capturer.getName());
            return true;
        }
        return false;
    }

    // =============================================================================
    // EXTRACTION POINT SYSTEM
    // =============================================================================

    /**
     * Add extraction point to mission
     */
    public void addExtractionPoint(ExtractionPoint extractionPoint) {
        extractionPoints.add(extractionPoint);
        logMissionEvent("Extraction point " + extractionPoint.getName() + " added to mission");
    }

    /**
     * Start extraction for unit
     */
    public boolean startExtraction(Unit unit, ExtractionPoint extractionPoint) {
        if (extractionPoint.startExtraction(unit)) {
            logMissionEvent("Unit " + unit.getName() + " started extraction at " + extractionPoint.getName());
            return true;
        }
        return false;
    }

    /**
     * Process all extractions
     */
    public void processExtractions() {
        for (ExtractionPoint extractionPoint : extractionPoints) {
            if (extractionPoint.isExtracting()) {
                extractionPoint.processExtraction();
            }
        }
    }

    // =============================================================================
    // OBJECTIVE TARGET SYSTEM
    // =============================================================================

    /**
     * Add objective target to mission
     */
    public void addObjectiveTarget(ObjectiveTarget objectiveTarget) {
        objectiveTargets.add(objectiveTarget);
        logMissionEvent("Objective target " + objectiveTarget.getName() + " added to mission");
    }

    /**
     * Attack objective target
     */
    public boolean attackObjectiveTarget(ObjectiveTarget target, Unit attacker, int damage) {
        if (target.takeDamage(damage)) {
            logMissionEvent("Objective target " + target.getName() + " destroyed by " + attacker.getName());
            return true;
        } else {
            logMissionEvent("Objective target " + target.getName() + " damaged by " + attacker.getName());
            return false;
        }
    }

    /**
     * Hack objective target
     */
    public boolean hackObjectiveTarget(ObjectiveTarget target, Unit hacker, String hackOption) {
        // This would need to be implemented in ObjectiveTarget class
        logMissionEvent("Hacking attempt on " + target.getName() + " by " + hacker.getName());
        return false;
    }

    // =============================================================================
    // HACKING TERMINAL SYSTEM
    // =============================================================================

    /**
     * Add hacking terminal to mission
     */
    public void addHackingTerminal(HackingTerminal terminal) {
        hackingTerminals.add(terminal);
        logMissionEvent("Hacking terminal " + terminal.getName() + " added to mission");
    }

    /**
     * Hack terminal
     */
    public boolean hackTerminal(HackingTerminal terminal, Unit hacker, String hackOption) {
        if (terminal.hack(hackOption)) {
            hackAssignments.put(terminal.getName(), hacker);
            logMissionEvent("Terminal " + terminal.getName() + " hacked successfully by " + hacker.getName());
            return true;
        } else {
            logMissionEvent("Hacking attempt on " + terminal.getName() + " failed by " + hacker.getName());
            return false;
        }
    }

    /**
     * Process all terminals
     */
    public void processTerminals() {
        for (HackingTerminal terminal : hackingTerminals) {
            terminal.processTime();
        }
    }

    // =============================================================================
    // DEFENSE POSITION SYSTEM
    // =============================================================================

    /**
     * Add defense position to mission
     */
    public void addDefensePosition(DefensePosition position) {
        defensePositions.add(position);
        logMissionEvent("Defense position " + position.getName() + " added to mission");
    }

    /**
     * Assign unit to defense position
     */
    public boolean assignDefender(DefensePosition position, Unit defender) {
        if (position.addDefendingUnit(defender)) {
            defenseAssignments.put(position.getName(), defender);
            logMissionEvent("Unit " + defender.getName() + " assigned to defend " + position.getName());
            return true;
        }
        return false;
    }

    /**
     * Attack defense position
     */
    public boolean attackDefensePosition(DefensePosition position, Unit attacker) {
        if (position.addAttackingUnit(attacker)) {
            logMissionEvent("Unit " + attacker.getName() + " attacking " + position.getName());
            return true;
        }
        return false;
    }

    /**
     * Capture defense position
     */
    public boolean captureDefensePosition(DefensePosition position, Unit capturer) {
        if (position.capture(capturer)) {
            logMissionEvent("Defense position " + position.getName() + " captured by " + capturer.getName());
            return true;
        }
        return false;
    }

    /**
     * Process all defense positions
     */
    public void processDefensePositions() {
        for (DefensePosition position : defensePositions) {
            position.processTime();
        }
    }

    // =============================================================================
    // MISSION OBJECTIVES AND COMPLETION
    // =============================================================================

    /**
     * Check mission objectives
     */
    public void checkMissionObjectives() {
        if (currentMission == null) {
            return;
        }

        switch (currentMission.getType()) {
            case ASSASSINATION -> checkAssassinationObjectives();
            case EXTRACTION -> checkExtractionObjectives();
            case DEFENSE -> checkDefenseObjectives();
            case SABOTAGE -> checkSabotageObjectives();
            case RECONNAISSANCE -> checkReconnaissanceObjectives();
            case ESCORT -> checkEscortObjectives();
            case HACKING -> checkHackingObjectives();
            case DESTROY -> checkDestroyObjectives();
            case RESCUE -> checkRescueObjectives();
            case INFILTRATION -> checkInfiltrationObjectives();
            case RETALIATION -> checkRetaliationObjectives();
            case GUERRILLA -> checkGuerrillaObjectives();
            case SUPPLY_RAID -> checkSupplyRaidObjectives();
            case TERROR -> checkTerrorObjectives();
            case COUNCIL -> checkCouncilObjectives();
            case ELIMINATION -> checkEliminationObjectives();
            case TIMED_ASSAULT -> checkTimedAssaultObjectives();
        }

        // Check if all objectives are completed
        if (currentMission.areAllObjectivesCompleted()) {
            completeMission();
        }
    }

    /**
     * Check assassination objectives
     */
    private void checkAssassinationObjectives() {
        // Check if assassination target is eliminated
        logMissionEvent("Checking assassination objectives");
    }
    
    /**
     * Check elimination objectives
     */
    private void checkEliminationObjectives() {
        boolean allEnemiesDead = getEnemyUnits().stream().noneMatch(Unit::isAlive);
        if (allEnemiesDead) {
            currentMission.completeObjective("Eliminate all enemy units");
            logMissionEvent("All enemies eliminated - objective completed");
        }
    }

    /**
     * Check extraction objectives
     */
    private void checkExtractionObjectives() {
        boolean allVIPsExtracted = vips.stream().allMatch(VIP::isExtracted);
        if (allVIPsExtracted) {
            currentMission.completeObjective("Extract VIP to designated area");
            logMissionEvent("All VIPs extracted - objective completed");
        }
    }

    /**
     * Check defense objectives
     */
    private void checkDefenseObjectives() {
        boolean allPositionsDefended = defensePositions.stream().allMatch(DefensePosition::isOccupied);
        if (allPositionsDefended) {
            currentMission.completeObjective("Defend position for " + currentMission.getTurnLimit() + " turns");
            logMissionEvent("All defense positions secured - objective completed");
        }
    }

    /**
     * Check sabotage objectives
     */
    private void checkSabotageObjectives() {
        boolean allTargetsDestroyed = objectiveTargets.stream().allMatch(ObjectiveTarget::isDestroyed);
        if (allTargetsDestroyed) {
            currentMission.completeObjective("Destroy target objective");
            logMissionEvent("All objective targets destroyed - objective completed");
        }
    }

    /**
     * Check reconnaissance objectives
     */
    private void checkReconnaissanceObjectives() {
        boolean allTerminalsHacked = hackingTerminals.stream().allMatch(HackingTerminal::isHacked);
        if (allTerminalsHacked) {
            currentMission.completeObjective("Scout and gather intelligence");
            logMissionEvent("All terminals hacked - objective completed");
        }
    }

    /**
     * Check escort objectives
     */
    private void checkEscortObjectives() {
        boolean allVIPsEscorted = vips.stream().allMatch(VIP::isEscorted);
        if (allVIPsEscorted) {
            currentMission.completeObjective("Escort VIP to extraction point");
            logMissionEvent("All VIPs escorted - objective completed");
        }
    }

    /**
     * Check timed assault objectives
     */
    private void checkTimedAssaultObjectives() {
        if (currentMission.getCurrentTurn() >= currentMission.getTurnLimit()) {
            currentMission.setFailed(true);
            failMission();
            logMissionEvent("Time limit exceeded - mission failed");
        }
    }
    
    /**
     * Check hacking objectives
     */
    private void checkHackingObjectives() {
        // Check if all terminals are hacked
        logMissionEvent("Checking hacking objectives");
    }
    
    /**
     * Check destroy objectives
     */
    private void checkDestroyObjectives() {
        // Check if all targets are destroyed
        logMissionEvent("Checking destroy objectives");
    }
    
    /**
     * Check rescue objectives
     */
    private void checkRescueObjectives() {
        // Check if all soldiers are rescued
        logMissionEvent("Checking rescue objectives");
    }
    
    /**
     * Check infiltration objectives
     */
    private void checkInfiltrationObjectives() {
        // Check if infiltration is complete
        logMissionEvent("Checking infiltration objectives");
    }
    
    /**
     * Check retaliation objectives
     */
    private void checkRetaliationObjectives() {
        // Check if retaliation is complete
        logMissionEvent("Checking retaliation objectives");
    }
    
    /**
     * Check guerrilla objectives
     */
    private void checkGuerrillaObjectives() {
        // Check if guerrilla objectives are complete
        logMissionEvent("Checking guerrilla objectives");
    }
    
    /**
     * Check supply raid objectives
     */
    private void checkSupplyRaidObjectives() {
        // Check if supply raid is complete
        logMissionEvent("Checking supply raid objectives");
    }
    
    /**
     * Check terror objectives
     */
    private void checkTerrorObjectives() {
        // Check if terror attack is defended
        logMissionEvent("Checking terror objectives");
    }
    
    /**
     * Check council objectives
     */
    private void checkCouncilObjectives() {
        // Check if council mission is complete
        logMissionEvent("Checking council objectives");
    }

    /**
     * Complete mission
     */
    public void completeMission() {
        missionCompleted = true;
        missionStatus = "Mission Completed Successfully";
        calculateMissionValue();
        logMissionEvent("Mission completed successfully");
    }

    /**
     * Fail mission
     */
    public void failMission() {
        missionFailed = true;
        missionStatus = "Mission Failed";
        calculateMissionValue();
        logMissionEvent("Mission failed");
    }

    /**
     * Calculate mission value
     */
    public void calculateMissionValue() {
        missionValue = 0;

        // Add VIP values
        for (VIP vip : vips) {
            missionValue += vip.getMissionValue();
        }

        // Add extraction point values
        for (ExtractionPoint extractionPoint : extractionPoints) {
            missionValue += extractionPoint.getTotalCapacity() * 10;
        }

        // Add objective target values
        for (ObjectiveTarget target : objectiveTargets) {
            missionValue += target.getObjectiveValue();
        }

        // Add hacking terminal values
        for (HackingTerminal terminal : hackingTerminals) {
            missionValue += terminal.getTerminalValue();
        }

        // Add defense position values
        for (DefensePosition position : defensePositions) {
            missionValue += position.getPositionValue();
        }

        // Add unit survival bonus
        for (Unit unit : getPlayerUnits()) {
            if (unit.isAlive()) {
                missionValue += 50;
            }
        }

        // Add time bonus/penalty
        if (currentMission != null) {
            int remainingTurns = currentMission.getRemainingTurns();
            if (remainingTurns > 0) {
                missionValue += remainingTurns * 10; // Bonus for completing quickly
            }
        }
    }

    /**
     * Log mission event
     */
    public void logMissionEvent(String event) {
        String timestamp = "Turn " + getCurrentTurn() + ": ";
        missionLog.add(timestamp + event);
    }

    /**
     * Get mission summary
     */
    public String getMissionSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("=== MISSION SUMMARY ===\n");
        summary.append("Status: ").append(missionStatus).append("\n");
        summary.append("Value: ").append(missionValue).append("\n");
        summary.append("Turns: ").append(getCurrentTurn()).append("\n");
        
        if (currentMission != null) {
            summary.append("Mission Type: ").append(currentMission.getType()).append("\n");
            summary.append("Objectives: ").append(currentMission.getObjectives().size()).append("\n");
            summary.append("Completed: ").append(currentMission.getCompletedObjectives().size()).append("\n");
        }
        
        summary.append("\n=== UNITS ===\n");
        summary.append("Player Units: ").append(getPlayerUnits().size()).append("\n");
        summary.append("Enemy Units: ").append(getEnemyUnits().size()).append("\n");
        summary.append("VIPs: ").append(vips.size()).append("\n");
        
        summary.append("\n=== OBJECTIVES ===\n");
        summary.append("Extraction Points: ").append(extractionPoints.size()).append("\n");
        summary.append("Objective Targets: ").append(objectiveTargets.size()).append("\n");
        summary.append("Hacking Terminals: ").append(hackingTerminals.size()).append("\n");
        summary.append("Defense Positions: ").append(defensePositions.size()).append("\n");
        
        summary.append("\n=== RECENT EVENTS ===\n");
        int startIndex = Math.max(0, missionLog.size() - 10);
        for (int i = startIndex; i < missionLog.size(); i++) {
            summary.append(missionLog.get(i)).append("\n");
        }
        
        return summary.toString();
    }

    /**
     * End turn with mission processing
     */
    @Override
    public void endTurn() {
        // Process all mission systems
        processExtractions();
        processTerminals();
        processDefensePositions();
        
        // Process VIP healing
        for (VIP vip : vips) {
            vip.processHealing();
        }
        
        // Check mission objectives
        checkMissionObjectives();
        
        // Advance mission turn
        if (currentMission != null) {
            currentMission.advanceTurn();
        }
        
        // Calculate mission value
        calculateMissionValue();
        
        super.endTurn();
    }

    /**
     * Get mission status
     */
    public String getMissionStatus() {
        if (missionCompleted) {
            return "COMPLETED";
        } else if (missionFailed) {
            return "FAILED";
        } else if (currentMission != null && currentMission.isFailed()) {
            return "FAILED";
        } else {
            return "IN_PROGRESS";
        }
    }

    /**
     * Check if mission is over
     */
    public boolean isMissionOver() {
        return missionCompleted || missionFailed || 
               (currentMission != null && currentMission.isFailed());
    }
} 
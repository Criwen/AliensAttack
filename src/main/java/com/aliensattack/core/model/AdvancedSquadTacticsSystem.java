package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Squad Tactics System - XCOM 2 Tactical Combat
 * Implements sophisticated squad-level coordination and formation mechanics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedSquadTacticsSystem {
    
    private String squadId;
    private List<Unit> squadMembers;
    private SquadFormation currentFormation;
    private Unit squadLeader;
    private Map<String, SquadManeuver> availableManeuvers;
    private Map<String, Double> squadSynergyBonuses;
    private List<SquadCommunication> activeCommunications;
    private Map<String, Integer> formationBonuses;
    private boolean isCoordinated;
    private int coordinationLevel;
    private Map<String, SquadTactic> activeTactics;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadFormation {
        private String formationName;
        private FormationType formationType;
        private Map<String, Integer> positionBonuses;
        private List<String> requiredPositions;
        private int formationCost;
        private boolean isActive;
        
        public enum FormationType {
            DEFENSIVE,      // Defensive formation with cover bonuses
            OFFENSIVE,      // Offensive formation with attack bonuses
            FLANKING,       // Flanking formation for side attacks
            SUPPORT,        // Support formation for healing/buffing
            RECONNAISSANCE, // Reconnaissance formation for scouting
            AMBUSH,         // Ambush formation for surprise attacks
            RETREAT,        // Retreat formation for safe withdrawal
            COORDINATED     // Coordinated formation for complex tactics
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadManeuver {
        private String maneuverName;
        private ManeuverType maneuverType;
        private int actionPointCost;
        private List<String> requiredUnits;
        private Map<String, Integer> maneuverBonuses;
        private int cooldown;
        private boolean isAvailable;
        
        public enum ManeuverType {
            COORDINATED_ATTACK,    // Multiple units attack same target
            COVERING_FIRE,         // One unit covers another's movement
            FLANKING_MANEUVER,     // Coordinated flanking attack
            SUPPORT_POSITIONING,   // Reposition for support abilities
            TACTICAL_WITHDRAWAL,   // Coordinated retreat
            AMBUSH_SETUP,          // Set up ambush positions
            DEFENSIVE_POSITIONING, // Reposition for defense
            RECONNAISSANCE_SWEEP   // Coordinated scouting
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadCommunication {
        private String communicationId;
        private CommunicationType communicationType;
        private Unit sender;
        private Unit receiver;
        private String message;
        private int duration;
        private Map<String, Integer> communicationBonuses;
        
        public enum CommunicationType {
            TARGET_SHARING,        // Share target information
            POSITION_REPORTING,    // Report enemy positions
            TACTIC_COORDINATION,   // Coordinate tactical actions
            SUPPORT_REQUEST,       // Request support from allies
            WARNING_ALERT,         // Alert about threats
            SUCCESS_REPORTING      // Report successful actions
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadTactic {
        private String tacticName;
        private TacticType tacticType;
        private List<String> requiredAbilities;
        private Map<String, Integer> tacticBonuses;
        private int duration;
        private boolean isActive;
        
        public enum TacticType {
            OVERWATCH_NET,         // Multiple overwatch positions
            FLANKING_ATTACK,       // Coordinated flanking
            SUPPRESSION_FIRE,      // Coordinated suppression
            MEDICAL_SUPPORT,       // Coordinated healing
            RECONNAISSANCE,        // Coordinated scouting
            AMBUSH_TACTIC,         // Coordinated ambush
            DEFENSIVE_STANCE,      // Coordinated defense
            BREACH_AND_CLEAR       // Coordinated room clearing
        }
    }
    

    
    /**
     * Add squad member
     */
    public void addSquadMember(Unit member) {
        squadMembers.add(member);
        updateSquadSynergy();
    }
    
    /**
     * Set squad formation
     */
    public boolean setFormation(SquadFormation formation) {
        if (canUseFormation(formation)) {
            this.currentFormation = formation;
            applyFormationBonuses();
            return true;
        }
        return false;
    }
    
    /**
     * Execute squad maneuver
     */
    public boolean executeManeuver(String maneuverName, List<Unit> participants) {
        SquadManeuver maneuver = availableManeuvers.get(maneuverName);
        if (maneuver != null && maneuver.isAvailable() && canExecuteManeuver(maneuver, participants)) {
            applyManeuverBonuses(maneuver, participants);
            maneuver.setAvailable(false);
            return true;
        }
        return false;
    }
    
    /**
     * Establish squad communication
     */
    public boolean establishCommunication(Unit sender, Unit receiver, SquadCommunication.CommunicationType type, String message) {
        SquadCommunication communication = SquadCommunication.builder()
                .communicationId(UUID.randomUUID().toString())
                .communicationType(type)
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .duration(3)
                .communicationBonuses(new HashMap<>())
                .build();
        
        activeCommunications.add(communication);
        applyCommunicationBonuses(communication);
        return true;
    }
    
    /**
     * Activate squad tactic
     */
    public boolean activateTactic(String tacticName) {
        SquadTactic tactic = activeTactics.get(tacticName);
        if (tactic != null && canActivateTactic(tactic)) {
            tactic.setActive(true);
            applyTacticBonuses(tactic);
            return true;
        }
        return false;
    }
    
    /**
     * Update squad synergy bonuses
     */
    public void updateSquadSynergy() {
        squadSynergyBonuses.clear();
        
        for (Unit member : squadMembers) {
            for (Unit otherMember : squadMembers) {
                if (!member.equals(otherMember)) {
                    calculateSynergyBonus(member, otherMember);
                }
            }
        }
    }
    
    /**
     * Calculate synergy bonus between two units
     */
    private void calculateSynergyBonus(Unit unit1, Unit unit2) {
        double distance = calculateDistance(unit1.getPosition(), unit2.getPosition());
        
        if (distance <= 3.0) { // Close proximity bonus
            squadSynergyBonuses.put("accuracy", squadSynergyBonuses.getOrDefault("accuracy", 0.0) + 5.0);
            squadSynergyBonuses.put("defense", squadSynergyBonuses.getOrDefault("defense", 0.0) + 3.0);
        }
        
        if (distance <= 1.5) { // Adjacent bonus
            squadSynergyBonuses.put("critical_chance", squadSynergyBonuses.getOrDefault("critical_chance", 0.0) + 2.0);
            squadSynergyBonuses.put("dodge", squadSynergyBonuses.getOrDefault("dodge", 0.0) + 2.0);
        }
    }
    
    /**
     * Apply formation bonuses
     */
    private void applyFormationBonuses() {
        if (currentFormation != null && currentFormation.isActive()) {
            formationBonuses.putAll(currentFormation.getPositionBonuses());
        }
    }
    
    /**
     * Apply maneuver bonuses
     */
    private void applyManeuverBonuses(SquadManeuver maneuver, List<Unit> participants) {
        for (Unit participant : participants) {
            for (Map.Entry<String, Integer> bonus : maneuver.getManeuverBonuses().entrySet()) {
                // Apply bonus to participant
                applyBonusToUnit(participant, bonus.getKey(), bonus.getValue());
            }
        }
    }
    
    /**
     * Apply communication bonuses
     */
    private void applyCommunicationBonuses(SquadCommunication communication) {
        communication.getCommunicationBonuses().put("coordination", 10);
        communication.getCommunicationBonuses().put("awareness", 15);
    }
    
    /**
     * Apply tactic bonuses
     */
    private void applyTacticBonuses(SquadTactic tactic) {
        for (Map.Entry<String, Integer> bonus : tactic.getTacticBonuses().entrySet()) {
            // Apply bonus to all squad members
            for (Unit member : squadMembers) {
                applyBonusToUnit(member, bonus.getKey(), bonus.getValue());
            }
        }
    }
    
    /**
     * Apply bonus to unit
     */
    private void applyBonusToUnit(Unit unit, String bonusType, int bonusValue) {
        switch (bonusType) {
            case "accuracy":
                unit.setAccuracy(unit.getAccuracy() + bonusValue);
                break;
            case "defense":
                unit.setDefense(unit.getDefense() + bonusValue);
                break;
            case "critical_chance":
                unit.setCriticalChance(unit.getCriticalChance() + bonusValue);
                break;
            case "dodge":
                unit.setDodgeChance(unit.getDodgeChance() + bonusValue);
                break;
        }
    }
    
    /**
     * Check if formation can be used
     */
    private boolean canUseFormation(SquadFormation formation) {
        return squadMembers.size() >= formation.getRequiredPositions().size();
    }
    
    /**
     * Check if maneuver can be executed
     */
    private boolean canExecuteManeuver(SquadManeuver maneuver, List<Unit> participants) {
        return participants.size() >= maneuver.getRequiredUnits().size();
    }
    
    /**
     * Check if tactic can be activated
     */
    private boolean canActivateTactic(SquadTactic tactic) {
        return squadMembers.stream().anyMatch(unit -> 
            unit.getAbilities().stream().anyMatch(ability -> 
                tactic.getRequiredAbilities().contains(ability.getName())
            )
        );
    }
    
    /**
     * Calculate distance between positions
     */
    private double calculateDistance(Position pos1, Position pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    /**
     * Get squad coordination level
     */
    public int getCoordinationLevel() {
        return coordinationLevel;
    }
    
    /**
     * Increase coordination level
     */
    public void increaseCoordinationLevel() {
        if (coordinationLevel < 5) {
            coordinationLevel++;
            updateSquadSynergy();
        }
    }
    
    /**
     * Check if squad is coordinated
     */
    public boolean isCoordinated() {
        return isCoordinated && coordinationLevel >= 2;
    }
    
    /**
     * Get active formation
     */
    public SquadFormation getCurrentFormation() {
        return currentFormation;
    }
    
    /**
     * Get squad leader
     */
    public Unit getSquadLeader() {
        return squadLeader;
    }
    
    /**
     * Set squad leader
     */
    public void setSquadLeader(Unit leader) {
        this.squadLeader = leader;
        updateSquadSynergy();
    }
}

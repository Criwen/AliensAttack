package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Squad Tactics System - XCOM 2 Tactical Combat
 * Implements squad bonding, coordination, and tactical bonuses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedSquadTacticsSystem {
    
    private String tacticsId;
    private Map<String, Squad> squads;
    private Map<String, SquadTactic> squadTactics;
    private Map<String, SquadBond> squadBonds;
    private Map<String, SquadCoordination> squadCoordinations;
    private Map<String, SquadMorale> squadMorales;
    private Map<String, SquadExperience> squadExperiences;
    private Map<String, List<String>> squadMembers;
    private Map<String, Map<String, Integer>> squadBonuses;
    private Map<String, List<String>> activeTactics;
    private Map<String, Integer> squadLevels;
    private Map<String, Boolean> squadStates;
    private int totalSquads;
    private int maxSquadSize;
    private boolean isTacticsActive;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadFormation {
        private String formationId;
        private String formationName;
        private FormationType formationType;
        private Map<String, Integer> formationEffects;
        private List<String> formationRequirements;
        private int formationCost;
        private boolean isActive;
        private String description;
        private int cooldown;
        private int currentCooldown;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> formationBonuses;
        private List<String> formationAbilities;
        private String formationMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum FormationType {
            DEFENSIVE,      // Defensive formation
            OFFENSIVE,      // Offensive formation
            FLANKING,       // Flanking formation
            SUPPORT,        // Support formation
            COVER,          // Cover formation
            OVERWATCH,      // Overwatch formation
            MEDICAL,        // Medical formation
            TECHNICAL       // Technical formation
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadManeuver {
        private String maneuverId;
        private String maneuverName;
        private ManeuverType maneuverType;
        private Map<String, Integer> maneuverEffects;
        private List<String> maneuverRequirements;
        private int maneuverCost;
        private boolean isActive;
        private String description;
        private int cooldown;
        private int currentCooldown;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> maneuverBonuses;
        private List<String> maneuverAbilities;
        private String maneuverMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum ManeuverType {
            COORDINATED_ATTACK,    // Coordinated attack maneuver
            COVERING_FIRE,         // Covering fire maneuver
            FLANKING_MANEUVER,     // Flanking maneuver
            SUPPRESSION_FIRE,      // Suppression fire maneuver
            OVERWATCH_AMBUSH,      // Overwatch ambush maneuver
            TACTICAL_WITHDRAWAL,   // Tactical withdrawal maneuver
            MEDICAL_EVACUATION,    // Medical evacuation maneuver
            TECHNICAL_SPECIALIST   // Technical specialist maneuver
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Squad {
        private String squadId;
        private String squadName;
        private List<String> members;
        private SquadTacticType primaryTactic;
        private SquadTacticType secondaryTactic;
        private int squadLevel;
        private int experience;
        private int morale;
        private int cohesion;
        private boolean isActive;
        private String leaderId;
        private Map<String, Integer> squadBonuses;
        private List<String> squadAbilities;
        private int maxSize;
        private int currentSize;
        
        public enum SquadTacticType {
            OVERWATCH_AMBUSH,    // Overwatch and ambush tactics
            FLANKING_MANEUVER,   // Flanking and positioning
            SUPPRESSION_COVER,   // Suppression and cover tactics
            PSYCHIC_COORDINATION, // Psychic coordination
            STEALTH_INFILTRATION, // Stealth and infiltration
            HEAVY_ASSAULT,       // Heavy weapons assault
            MEDICAL_SUPPORT,     // Medical and support tactics
            TECHNICAL_SPECIALIST  // Technical specialist tactics
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadTactic {
        private String tacticId;
        private String tacticName;
        private Squad.SquadTacticType tacticType;
        private Map<String, Integer> tacticEffects;
        private List<String> tacticRequirements;
        private int tacticCost;
        private boolean isActive;
        private String description;
        private int cooldown;
        private int currentCooldown;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> tacticBonuses;
        private List<String> tacticAbilities;
        private String tacticMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadBond {
        private String bondId;
        private String unit1Id;
        private String unit2Id;
        private int bondLevel;
        private int bondExperience;
        private Map<String, Integer> bondBonuses;
        private List<String> bondAbilities;
        private boolean isActive;
        private String bondType;
        private int bondDuration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> bondEffects;
        private List<String> bondRequirements;
        private int bondCost;
        private boolean isPermanent;
        private String permanentCondition;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadCoordination {
        private String coordinationId;
        private String squadId;
        private CoordinationType coordinationType;
        private Map<String, Integer> coordinationEffects;
        private List<String> participatingUnits;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> coordinationBonuses;
        private List<String> coordinationAbilities;
        private String coordinationMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
        
        public enum CoordinationType {
            COMBINED_ATTACK,     // Combined attack coordination
            DEFENSIVE_FORMATION,  // Defensive formation coordination
            SUPPORT_ACTION,       // Support action coordination
            TACTICAL_MOVEMENT,    // Tactical movement coordination
            COVER_COORDINATION,   // Cover coordination
            OVERWATCH_NETWORK,    // Overwatch network coordination
            MEDICAL_COORDINATION, // Medical coordination
            TECHNICAL_COORDINATION // Technical coordination
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadMorale {
        private String moraleId;
        private String squadId;
        private int moraleLevel;
        private int maxMoraleLevel;
        private Map<String, Integer> moraleEffects;
        private List<String> moraleEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> moraleBonuses;
        private List<String> moraleAbilities;
        private String moraleMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SquadExperience {
        private String experienceId;
        private String squadId;
        private int experienceLevel;
        private int maxExperienceLevel;
        private int experiencePoints;
        private int experienceToNext;
        private Map<String, Integer> experienceEffects;
        private List<String> experienceEvents;
        private boolean isActive;
        private int duration;
        private int currentDuration;
        private String activationCondition;
        private double successRate;
        private String failureEffect;
        private Map<String, Integer> experienceBonuses;
        private List<String> experienceAbilities;
        private String experienceMethod;
        private int energyCost;
        private boolean isAutomatic;
        private String triggerCondition;
        private Map<String, Integer> damageModifiers;
        private List<String> resistanceTypes;
        private boolean isPermanent;
        private String permanentCondition;
    }
    

    
    /**
     * Initialize the squad tactics system
     */
    public void initializeSystem() {
        // ToDo: Реализовать продвинутые тактики отряда
        // - Squad Bonding System
        // - Advanced Squad Tactics
        // - Squad Cohesion Bonuses
        // - Squad coordination mechanics
        // - Squad morale system
        // - Squad experience sharing
    }
    
    /**
     * Create a new squad
     */
    public boolean createSquad(String squadId, String squadName, String leaderId) {
        if (squads.containsKey(squadId)) {
            return false; // Squad already exists
        }
        
        Squad squad = Squad.builder()
            .squadId(squadId)
            .squadName(squadName)
            .members(new ArrayList<>())
            .primaryTactic(Squad.SquadTacticType.OVERWATCH_AMBUSH)
            .secondaryTactic(Squad.SquadTacticType.FLANKING_MANEUVER)
            .squadLevel(1)
            .experience(0)
            .morale(100)
            .cohesion(50)
            .isActive(true)
            .leaderId(leaderId)
            .squadBonuses(new HashMap<>())
            .squadAbilities(new ArrayList<>())
            .maxSize(maxSquadSize)
            .currentSize(0)
            .build();
        
        squads.put(squadId, squad);
        squadMembers.put(squadId, new ArrayList<>());
        squadBonuses.put(squadId, new HashMap<>());
        squadLevels.put(squadId, 1);
        squadStates.put(squadId, true);
        totalSquads++;
        
        return true;
    }
    
    /**
     * Add member to squad
     */
    public boolean addSquadMember(String squadId, String unitId) {
        Squad squad = squads.get(squadId);
        if (squad == null || squad.getCurrentSize() >= squad.getMaxSize()) {
            return false;
        }
        
        squad.getMembers().add(unitId);
        squad.setCurrentSize(squad.getCurrentSize() + 1);
        squadMembers.get(squadId).add(unitId);
        
        // Update squad cohesion
        updateSquadCohesion(squadId);
        
        return true;
    }
    
    /**
     * Remove member from squad
     */
    public boolean removeSquadMember(String squadId, String unitId) {
        Squad squad = squads.get(squadId);
        if (squad == null) {
            return false;
        }
        
        boolean removed = squad.getMembers().remove(unitId);
        if (removed) {
            squad.setCurrentSize(squad.getCurrentSize() - 1);
            squadMembers.get(squadId).remove(unitId);
            
            // Update squad cohesion
            updateSquadCohesion(squadId);
        }
        
        return removed;
    }
    
    /**
     * Create squad bond between two units
     */
    public boolean createSquadBond(String unit1Id, String unit2Id, String bondType) {
        String bondId = "BOND_" + unit1Id + "_" + unit2Id;
        
        if (squadBonds.containsKey(bondId)) {
            return false; // Bond already exists
        }
        
        SquadBond bond = SquadBond.builder()
            .bondId(bondId)
            .unit1Id(unit1Id)
            .unit2Id(unit2Id)
            .bondLevel(1)
            .bondExperience(0)
            .bondBonuses(new HashMap<>())
            .bondAbilities(new ArrayList<>())
            .isActive(true)
            .bondType(bondType)
            .bondDuration(0)
            .currentDuration(0)
            .activationCondition("Both units alive")
            .successRate(0.9)
            .failureEffect("No bond effect")
            .bondEffects(new HashMap<>())
            .bondRequirements(new ArrayList<>())
            .bondCost(0)
            .isPermanent(true)
            .permanentCondition("Both units alive")
            .build();
        
        squadBonds.put(bondId, bond);
        
        // Apply bond bonuses
        applyBondBonuses(bond);
        
        return true;
    }
    
    /**
     * Apply squad tactic
     */
    public boolean applySquadTactic(String squadId, Squad.SquadTacticType tacticType) {
        Squad squad = squads.get(squadId);
        if (squad == null) {
            return false;
        }
        
        // Create tactic
        SquadTactic tactic = SquadTactic.builder()
            .tacticId("TACTIC_" + squadId + "_" + tacticType.name())
            .tacticName(tacticType.name())
            .tacticType(tacticType)
            .tacticEffects(new HashMap<>())
            .tacticRequirements(new ArrayList<>())
            .tacticCost(1)
            .isActive(true)
            .description("Squad tactic: " + tacticType.name())
            .cooldown(3)
            .currentCooldown(0)
            .activationCondition("Squad has action points")
            .successRate(0.8)
            .failureEffect("No tactic effect")
            .tacticBonuses(new HashMap<>())
            .tacticAbilities(new ArrayList<>())
            .tacticMethod("Automatic")
            .energyCost(0)
            .isAutomatic(true)
            .triggerCondition("Squad action")
            .damageModifiers(new HashMap<>())
            .resistanceTypes(new ArrayList<>())
            .isPermanent(false)
            .permanentCondition("")
            .build();
        
        // Apply tactic effects based on type
        applyTacticEffects(squad, tactic);
        
        squadTactics.put(tactic.getTacticId(), tactic);
        activeTactics.put(squadId, new ArrayList<>());
        activeTactics.get(squadId).add(tactic.getTacticId());
        
        return true;
    }
    
    /**
     * Update squad cohesion
     */
    private void updateSquadCohesion(String squadId) {
        Squad squad = squads.get(squadId);
        if (squad == null) {
            return;
        }
        
        int memberCount = squad.getMembers().size();
        if (memberCount < 2) {
            squad.setCohesion(0);
            return;
        }
        
        // Calculate cohesion based on member count and bonds
        int baseCohesion = Math.min(100, memberCount * 20);
        int bondBonus = calculateBondBonus(squadId);
        int finalCohesion = Math.min(100, baseCohesion + bondBonus);
        
        squad.setCohesion(finalCohesion);
    }
    
    /**
     * Calculate bond bonus for squad
     */
    private int calculateBondBonus(String squadId) {
        List<String> members = squadMembers.get(squadId);
        if (members == null) {
            return 0;
        }
        
        int bondBonus = 0;
        for (SquadBond bond : squadBonds.values()) {
            if (members.contains(bond.getUnit1Id()) && members.contains(bond.getUnit2Id())) {
                bondBonus += bond.getBondLevel() * 5;
            }
        }
        
        return bondBonus;
    }
    
    /**
     * Apply bond bonuses
     */
    private void applyBondBonuses(SquadBond bond) {
        // Apply basic bond bonuses
        bond.getBondBonuses().put("accuracy", 5);
        bond.getBondBonuses().put("defense", 3);
        bond.getBondBonuses().put("morale", 10);
        
        // Add bond abilities
        bond.getBondAbilities().add("COORDINATED_ATTACK");
        bond.getBondAbilities().add("COVER_FIRE");
        bond.getBondAbilities().add("MORALE_BOOST");
    }
    
    /**
     * Apply tactic effects
     */
    private void applyTacticEffects(Squad squad, SquadTactic tactic) {
        switch (tactic.getTacticType()) {
            case OVERWATCH_AMBUSH:
                tactic.getTacticEffects().put("overwatch_bonus", 20);
                tactic.getTacticEffects().put("ambush_damage", 15);
                break;
            case FLANKING_MANEUVER:
                tactic.getTacticEffects().put("flanking_bonus", 25);
                tactic.getTacticEffects().put("movement_bonus", 10);
                break;
            case SUPPRESSION_COVER:
                tactic.getTacticEffects().put("suppression_bonus", 15);
                tactic.getTacticEffects().put("cover_bonus", 20);
                break;
            case PSYCHIC_COORDINATION:
                tactic.getTacticEffects().put("psychic_bonus", 30);
                tactic.getTacticEffects().put("coordination_bonus", 15);
                break;
            case STEALTH_INFILTRATION:
                tactic.getTacticEffects().put("stealth_bonus", 25);
                tactic.getTacticEffects().put("infiltration_bonus", 20);
                break;
            case HEAVY_ASSAULT:
                tactic.getTacticEffects().put("heavy_damage", 30);
                tactic.getTacticEffects().put("armor_penetration", 15);
                break;
            case MEDICAL_SUPPORT:
                tactic.getTacticEffects().put("healing_bonus", 25);
                tactic.getTacticEffects().put("support_bonus", 20);
                break;
            case TECHNICAL_SPECIALIST:
                tactic.getTacticEffects().put("technical_bonus", 30);
                tactic.getTacticEffects().put("hacking_bonus", 25);
                break;
        }
    }
    
    /**
     * Get squad by ID
     */
    public Squad getSquad(String squadId) {
        return squads.get(squadId);
    }
    
    /**
     * Get squad members
     */
    public List<String> getSquadMembers(String squadId) {
        return squadMembers.getOrDefault(squadId, new ArrayList<>());
    }
    
    /**
     * Get squad bonuses
     */
    public Map<String, Integer> getSquadBonuses(String squadId) {
        return squadBonuses.getOrDefault(squadId, new HashMap<>());
    }
    
    /**
     * Get active tactics for squad
     */
    public List<String> getActiveTactics(String squadId) {
        return activeTactics.getOrDefault(squadId, new ArrayList<>());
    }
    
    /**
     * Get squad level
     */
    public int getSquadLevel(String squadId) {
        return squadLevels.getOrDefault(squadId, 1);
    }
    
    /**
     * Check if squad is active
     */
    public boolean isSquadActive(String squadId) {
        return squadStates.getOrDefault(squadId, false);
    }
    
    /**
     * Get total squads
     */
    public int getTotalSquads() {
        return totalSquads;
    }
}

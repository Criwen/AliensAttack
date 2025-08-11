package com.aliensattack.core.model;

import java.util.*;

/**
 * Squad Cohesion Manager - XCOM 2 Tactical Combat
 * Manages squad bonding, cohesion bonuses, and squad-wide coordination effects
 */
public class SquadCohesionManager {
    
    // Cohesion Levels
    public enum CohesionLevel {
        NONE,           // No cohesion
        BASIC,          // Basic squad coordination
        TRAINED,        // Trained squad coordination
        VETERAN,        // Veteran squad coordination
        ELITE,          // Elite squad coordination
        LEGENDARY       // Legendary squad coordination
    }
    
    // Cohesion Bonus Types
    public enum CohesionBonusType {
        ACCURACY_BONUS,         // Accuracy bonus for squad members
        DAMAGE_BONUS,           // Damage bonus for squad members
        DEFENSE_BONUS,          // Defense bonus for squad members
        MOVEMENT_BONUS,         // Movement bonus for squad members
        OVERWATCH_BONUS,        // Overwatch bonus for squad members
        CRITICAL_BONUS,         // Critical hit bonus for squad members
        DODGE_BONUS,            // Dodge bonus for squad members
        PSI_BONUS,              // Psionic bonus for squad members
        HACK_BONUS,             // Hacking bonus for squad members
        HEALING_BONUS,          // Healing bonus for squad members
        SQUAD_SIGHT_BONUS,      // Squad sight range bonus
        CONCEALMENT_BONUS,      // Concealment bonus for squad members
        CHAIN_REACTION_BONUS,   // Chain reaction chance bonus
        SUPPRESSION_BONUS,      // Suppression radius bonus
        SUPPORT_RANGE_BONUS     // Support ability range bonus
    }
    
    private String managerId;
    private Map<String, AdvancedSquadBonding> activeBonds;
    private Map<String, SquadTactic> activeTactics;
    private Map<String, AdvancedSquadTacticsSystem> squadSystems;
    private Map<String, CohesionLevel> squadCohesionLevels;
    private Map<String, Map<CohesionBonusType, Integer>> cohesionBonuses;
    private Map<String, List<Unit>> squadMembers;
    private Map<String, Unit> squadLeaders;
    private Map<String, Integer> squadExperience;
    private Random random;
    
    public SquadCohesionManager() {
        this.managerId = "SQUAD_COHESION_" + System.currentTimeMillis();
        this.activeBonds = new HashMap<>();
        this.activeTactics = new HashMap<>();
        this.squadSystems = new HashMap<>();
        this.squadCohesionLevels = new HashMap<>();
        this.cohesionBonuses = new HashMap<>();
        this.squadMembers = new HashMap<>();
        this.squadLeaders = new HashMap<>();
        this.squadExperience = new HashMap<>();
        this.random = new Random();
    }
    
    /**
     * Register a squad with the cohesion manager
     */
    public boolean registerSquad(String squadId, List<Unit> members, Unit leader) {
        if (members == null || members.isEmpty()) {
            return false;
        }
        
        squadMembers.put(squadId, new ArrayList<>(members));
        squadLeaders.put(squadId, leader);
        squadCohesionLevels.put(squadId, CohesionLevel.BASIC);
        cohesionBonuses.put(squadId, new HashMap<>());
        squadExperience.put(squadId, 0);
        
        // Initialize squad tactics system
        AdvancedSquadTacticsSystem tacticsSystem = new AdvancedSquadTacticsSystem();
        
        squadSystems.put(squadId, tacticsSystem);
        
        // Initialize bonds between squad members
        initializeSquadBonds(squadId, members);
        
        return true;
    }
    
    /**
     * Initialize bonds between squad members
     */
    private void initializeSquadBonds(String squadId, List<Unit> members) {
        for (int i = 0; i < members.size(); i++) {
            for (int j = i + 1; j < members.size(); j++) {
                Unit unit1 = members.get(i);
                Unit unit2 = members.get(j);
                
                AdvancedSquadBonding bond = new AdvancedSquadBonding(unit1, unit2);
                activeBonds.put(bond.getId(), bond);
            }
        }
    }
    
    /**
     * Add cohesion experience to squad
     */
    public void addSquadExperience(String squadId, int experience) {
        if (!squadExperience.containsKey(squadId)) {
            return;
        }
        
        int currentExp = squadExperience.get(squadId);
        squadExperience.put(squadId, currentExp + experience);
        
        // Check for cohesion level up
        checkCohesionLevelUp(squadId);
        
        // Update cohesion bonuses
        updateCohesionBonuses(squadId);
    }
    
    /**
     * Check for cohesion level up
     */
    private void checkCohesionLevelUp(String squadId) {
        int experience = squadExperience.get(squadId);
        CohesionLevel currentLevel = squadCohesionLevels.get(squadId);
        CohesionLevel newLevel = calculateCohesionLevel(experience);
        
        if (newLevel != currentLevel) {
            squadCohesionLevels.put(squadId, newLevel);
            System.out.println("Squad " + squadId + " cohesion level increased to " + newLevel);
        }
    }
    
    /**
     * Calculate cohesion level based on experience
     */
    private CohesionLevel calculateCohesionLevel(int experience) {
        if (experience >= 1000) {
            return CohesionLevel.LEGENDARY;
        } else if (experience >= 750) {
            return CohesionLevel.ELITE;
        } else if (experience >= 500) {
            return CohesionLevel.VETERAN;
        } else if (experience >= 250) {
            return CohesionLevel.TRAINED;
        } else if (experience >= 100) {
            return CohesionLevel.BASIC;
        } else {
            return CohesionLevel.NONE;
        }
    }
    
    /**
     * Update cohesion bonuses for squad
     */
    private void updateCohesionBonuses(String squadId) {
        CohesionLevel level = squadCohesionLevels.get(squadId);
        Map<CohesionBonusType, Integer> bonuses = cohesionBonuses.get(squadId);
        
        // Clear existing bonuses
        bonuses.clear();
        
        // Apply level-based bonuses
        switch (level) {
            case BASIC:
                bonuses.put(CohesionBonusType.ACCURACY_BONUS, 5);
                bonuses.put(CohesionBonusType.DEFENSE_BONUS, 3);
                break;
            case TRAINED:
                bonuses.put(CohesionBonusType.ACCURACY_BONUS, 10);
                bonuses.put(CohesionBonusType.DEFENSE_BONUS, 5);
                bonuses.put(CohesionBonusType.MOVEMENT_BONUS, 1);
                break;
            case VETERAN:
                bonuses.put(CohesionBonusType.ACCURACY_BONUS, 15);
                bonuses.put(CohesionBonusType.DEFENSE_BONUS, 8);
                bonuses.put(CohesionBonusType.MOVEMENT_BONUS, 2);
                bonuses.put(CohesionBonusType.OVERWATCH_BONUS, 10);
                break;
            case ELITE:
                bonuses.put(CohesionBonusType.ACCURACY_BONUS, 20);
                bonuses.put(CohesionBonusType.DEFENSE_BONUS, 12);
                bonuses.put(CohesionBonusType.MOVEMENT_BONUS, 3);
                bonuses.put(CohesionBonusType.OVERWATCH_BONUS, 15);
                bonuses.put(CohesionBonusType.CRITICAL_BONUS, 10);
                break;
            case LEGENDARY:
                bonuses.put(CohesionBonusType.ACCURACY_BONUS, 25);
                bonuses.put(CohesionBonusType.DEFENSE_BONUS, 15);
                bonuses.put(CohesionBonusType.MOVEMENT_BONUS, 4);
                bonuses.put(CohesionBonusType.OVERWATCH_BONUS, 20);
                bonuses.put(CohesionBonusType.CRITICAL_BONUS, 15);
                bonuses.put(CohesionBonusType.SQUAD_SIGHT_BONUS, 2);
                break;
            default:
                break;
        }
    }
    
    /**
     * Get cohesion bonus for unit
     */
    public int getCohesionBonus(String squadId, Unit unit, CohesionBonusType bonusType) {
        if (!squadMembers.containsKey(squadId) || !squadMembers.get(squadId).contains(unit)) {
            return 0;
        }
        
        Map<CohesionBonusType, Integer> bonuses = cohesionBonuses.get(squadId);
        if (bonuses == null) {
            return 0;
        }
        
        int baseBonus = bonuses.getOrDefault(bonusType, 0);
        
        // Add bond bonuses
        int bondBonus = getBondBonus(unit, bonusType);
        
        // Add tactic bonuses
        int tacticBonus = getTacticBonus(squadId, unit, bonusType);
        
        return baseBonus + bondBonus + tacticBonus;
    }
    
    /**
     * Get bond bonus for unit
     */
    private int getBondBonus(Unit unit, CohesionBonusType bonusType) {
        int totalBonus = 0;
        
        for (AdvancedSquadBonding bond : activeBonds.values()) {
            if (bond.isUnitInBond(unit) && bond.isBondActive()) {
                // Add randomization to bond bonuses (10% variation)
                int baseBonus = 0;
                switch (bonusType) {
                    case ACCURACY_BONUS:
                        baseBonus = bond.getBondLevel().ordinal() * 2;
                        break;
                    case DEFENSE_BONUS:
                        baseBonus = bond.getBondLevel().ordinal() * 2;
                        break;
                    case CRITICAL_BONUS:
                        baseBonus = bond.getBondLevel().ordinal();
                        break;
                    case DODGE_BONUS:
                        baseBonus = bond.getBondLevel().ordinal();
                        break;
                    default:
                        break;
                }
                
                // Apply random variation
                int variation = (int) (baseBonus * 0.1);
                int randomBonus = baseBonus + random.nextInt(variation * 2 + 1) - variation;
                totalBonus += Math.max(0, randomBonus);
            }
        }
        
        return totalBonus;
    }
    
    /**
     * Get tactic bonus for unit
     */
    private int getTacticBonus(String squadId, Unit unit, CohesionBonusType bonusType) {
        SquadTactic tactic = activeTactics.get(squadId);
        if (tactic == null || !tactic.isActive() || !tactic.isSquadMember(unit)) {
            return 0;
        }
        
        switch (bonusType) {
            case ACCURACY_BONUS:
                return tactic.getAccuracyBonus();
            case DAMAGE_BONUS:
                return tactic.getDamageBonus();
            case DEFENSE_BONUS:
                return tactic.getDefenseBonus();
            case MOVEMENT_BONUS:
                return tactic.getMovementBonus();
            case OVERWATCH_BONUS:
                return tactic.getOverwatchBonus();
            case CRITICAL_BONUS:
                return tactic.getCriticalBonus();
            case DODGE_BONUS:
                return tactic.getDodgeBonus();
            case PSI_BONUS:
                return tactic.getPsiBonus();
            case HACK_BONUS:
                return tactic.getHackBonus();
            case SQUAD_SIGHT_BONUS:
                return tactic.getSquadSightRangeBonus();
            default:
                return 0;
        }
    }
    
    /**
     * Activate squad tactic
     */
    public boolean activateSquadTactic(String squadId, SquadTactic tactic) {
        if (!squadMembers.containsKey(squadId)) {
            return false;
        }
        
        List<Unit> members = squadMembers.get(squadId);
        if (tactic.activate(members)) {
            activeTactics.put(squadId, tactic);
            return true;
        }
        
        return false;
    }
    
    /**
     * Deactivate squad tactic
     */
    public void deactivateSquadTactic(String squadId) {
        SquadTactic tactic = activeTactics.get(squadId);
        if (tactic != null) {
            tactic.deactivate();
            activeTactics.remove(squadId);
        }
    }
    
    /**
     * Process all squad systems
     */
    public void processSquadSystems() {
        // Process bonds
        for (AdvancedSquadBonding bond : activeBonds.values()) {
            bond.updateBond();
        }
        
        // Process tactics
        for (SquadTactic tactic : activeTactics.values()) {
            tactic.processDuration();
        }
        
        // Process squad tactics systems
        for (AdvancedSquadTacticsSystem system : squadSystems.values()) {
            // Update coordination level - methods not available in this class
        }
    }
    
    /**
     * Get squad cohesion level
     */
    public CohesionLevel getSquadCohesionLevel(String squadId) {
        return squadCohesionLevels.getOrDefault(squadId, CohesionLevel.NONE);
    }
    
    /**
     * Get squad experience
     */
    public int getSquadExperience(String squadId) {
        return squadExperience.getOrDefault(squadId, 0);
    }
    
    /**
     * Get active bonds for squad
     */
    public List<AdvancedSquadBonding> getSquadBonds(String squadId) {
        List<AdvancedSquadBonding> squadBonds = new ArrayList<>();
        List<Unit> members = squadMembers.get(squadId);
        
        if (members == null) {
            return squadBonds;
        }
        
        for (AdvancedSquadBonding bond : activeBonds.values()) {
            if (members.contains(bond.getUnit1()) || members.contains(bond.getUnit2())) {
                squadBonds.add(bond);
            }
        }
        
        return squadBonds;
    }
    
    /**
     * Get squad leader
     */
    public Unit getSquadLeader(String squadId) {
        return squadLeaders.get(squadId);
    }
    
    /**
     * Get squad members
     */
    public List<Unit> getSquadMembers(String squadId) {
        return squadMembers.getOrDefault(squadId, new ArrayList<>());
    }
    
    /**
     * Check if unit is in squad
     */
    public boolean isUnitInSquad(String squadId, Unit unit) {
        List<Unit> members = squadMembers.get(squadId);
        return members != null && members.contains(unit);
    }
    
    /**
     * Get total cohesion bonus for unit
     */
    public int getTotalCohesionBonus(String squadId, Unit unit) {
        int totalBonus = 0;
        
        for (CohesionBonusType bonusType : CohesionBonusType.values()) {
            totalBonus += getCohesionBonus(squadId, unit, bonusType);
        }
        
        return totalBonus;
    }
    
    /**
     * Get manager ID
     */
    public String getManagerId() {
        return managerId;
    }
    
    /**
     * Check if manager is active
     */
    public boolean isManagerActive() {
        return managerId != null && !managerId.isEmpty();
    }
}

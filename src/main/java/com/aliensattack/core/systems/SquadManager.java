package com.aliensattack.core.systems;

import com.aliensattack.core.enums.MissionType;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Mission;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Squad Manager - Dynamic squad composition and deployment
 * Implements XCOM 2 Squad Size Management System
 */
@Getter
@Setter
public class SquadManager {
    
    private String squadId;
    private List<Unit> squadMembers;
    private int maxSquadSize;
    private int currentSquadSize;
    private Map<String, Unit> deployedUnits;
    private Map<String, Unit> reserveUnits;
    private List<String> squadHistory;
    private Map<String, Integer> unitExperience;
    private Map<String, String> unitSpecializations;
    private int totalMissions;
    private int successfulMissions;
    private Random random;
    
    public SquadManager() {
        this.squadId = "SQUAD_" + System.currentTimeMillis();
        this.squadMembers = new ArrayList<>();
        this.maxSquadSize = 6; // Standard XCOM 2 squad size
        this.currentSquadSize = 0;
        this.deployedUnits = new HashMap<>();
        this.reserveUnits = new HashMap<>();
        this.squadHistory = new ArrayList<>();
        this.unitExperience = new HashMap<>();
        this.unitSpecializations = new HashMap<>();
        this.totalMissions = 0;
        this.successfulMissions = 0;
        this.random = new Random();
    }
    
    /**
     * Add unit to squad
     */
    public boolean addUnitToSquad(Unit unit) {
        if (currentSquadSize >= maxSquadSize) {
            return false; // Squad is full
        }
        
        if (squadMembers.contains(unit)) {
            return false; // Unit already in squad
        }
        
        squadMembers.add(unit);
        currentSquadSize++;
        unitExperience.put(unit.getId(), 0);
        
        logSquadEvent("Unit " + unit.getName() + " added to squad");
        return true;
    }
    
    /**
     * Remove unit from squad
     */
    public boolean removeUnitFromSquad(Unit unit) {
        if (!squadMembers.contains(unit)) {
            return false;
        }
        
        squadMembers.remove(unit);
        currentSquadSize--;
        
        logSquadEvent("Unit " + unit.getName() + " removed from squad");
        return true;
    }
    
    /**
     * Deploy unit to mission
     */
    public boolean deployUnit(Unit unit) {
        if (!squadMembers.contains(unit)) {
            return false;
        }
        
        if (deployedUnits.size() >= maxSquadSize) {
            return false; // Max deployment reached
        }
        
        deployedUnits.put(unit.getId(), unit);
        reserveUnits.remove(unit.getId());
        
        logSquadEvent("Unit " + unit.getName() + " deployed to mission");
        return true;
    }
    
    /**
     * Return unit to reserve
     */
    public boolean returnToReserve(Unit unit) {
        if (!deployedUnits.containsKey(unit.getId())) {
            return false;
        }
        
        deployedUnits.remove(unit.getId());
        reserveUnits.put(unit.getId(), unit);
        
        logSquadEvent("Unit " + unit.getName() + " returned to reserve");
        return true;
    }
    
    /**
     * Get optimal squad composition for mission
     */
    public List<Unit> getOptimalSquad(Mission mission) {
        List<Unit> optimalSquad = new ArrayList<>();
        
        // Analyze mission requirements
        MissionType missionType = mission.getType();
        int requiredSize = getRequiredSquadSize(missionType);
        
        // Select units based on mission type
        switch (missionType) {
            case ASSASSINATION:
                optimalSquad = selectAssassinationSquad(requiredSize);
                break;
            case EXTRACTION:
                optimalSquad = selectExtractionSquad(requiredSize);
                break;
            case DEFENSE:
                optimalSquad = selectDefenseSquad(requiredSize);
                break;
            case SABOTAGE:
                optimalSquad = selectSabotageSquad(requiredSize);
                break;
            case RECONNAISSANCE:
                optimalSquad = selectReconnaissanceSquad(requiredSize);
                break;
            default:
                optimalSquad = selectStandardSquad(requiredSize);
        }
        
        return optimalSquad;
    }
    
    private List<Unit> selectAssassinationSquad(int size) {
        List<Unit> squad = new ArrayList<>();
        
        // Prioritize high-damage units
        List<Unit> availableUnits = new ArrayList<>(squadMembers);
        availableUnits.sort((u1, u2) -> Integer.compare(u2.getAttackDamage(), u1.getAttackDamage()));
        
        for (int i = 0; i < Math.min(size, availableUnits.size()); i++) {
            squad.add(availableUnits.get(i));
        }
        
        return squad;
    }
    
    private List<Unit> selectExtractionSquad(int size) {
        List<Unit> squad = new ArrayList<>();
        
        // Prioritize mobile units
        List<Unit> availableUnits = new ArrayList<>(squadMembers);
        availableUnits.sort((u1, u2) -> Integer.compare(u2.getMovementRange(), u1.getMovementRange()));
        
        for (int i = 0; i < Math.min(size, availableUnits.size()); i++) {
            squad.add(availableUnits.get(i));
        }
        
        return squad;
    }
    
    private List<Unit> selectDefenseSquad(int size) {
        List<Unit> squad = new ArrayList<>();
        
        // Prioritize units with armor for defense
        List<Unit> availableUnits = new ArrayList<>(squadMembers);
        availableUnits.sort((u1, u2) -> {
            boolean u1HasArmor = u1.getArmor() != null;
            boolean u2HasArmor = u2.getArmor() != null;
            return Boolean.compare(u2HasArmor, u1HasArmor);
        });
        
        for (int i = 0; i < Math.min(size, availableUnits.size()); i++) {
            squad.add(availableUnits.get(i));
        }
        
        return squad;
    }
    
    private List<Unit> selectSabotageSquad(int size) {
        List<Unit> squad = new ArrayList<>();
        
        // Prioritize technical units
        List<Unit> availableUnits = new ArrayList<>(squadMembers);
        availableUnits.sort((u1, u2) -> {
            boolean u1Technical = unitSpecializations.getOrDefault(u1.getId(), "").contains("TECHNICAL");
            boolean u2Technical = unitSpecializations.getOrDefault(u2.getId(), "").contains("TECHNICAL");
            return Boolean.compare(u2Technical, u1Technical);
        });
        
        for (int i = 0; i < Math.min(size, availableUnits.size()); i++) {
            squad.add(availableUnits.get(i));
        }
        
        return squad;
    }
    
    private List<Unit> selectReconnaissanceSquad(int size) {
        List<Unit> squad = new ArrayList<>();
        
        // Prioritize stealth units
        List<Unit> availableUnits = new ArrayList<>(squadMembers);
        availableUnits.sort((u1, u2) -> {
            boolean u1Stealth = unitSpecializations.getOrDefault(u1.getId(), "").contains("STEALTH");
            boolean u2Stealth = unitSpecializations.getOrDefault(u2.getId(), "").contains("STEALTH");
            return Boolean.compare(u2Stealth, u1Stealth);
        });
        
        for (int i = 0; i < Math.min(size, availableUnits.size()); i++) {
            squad.add(availableUnits.get(i));
        }
        
        return squad;
    }
    
    private List<Unit> selectStandardSquad(int size) {
        List<Unit> squad = new ArrayList<>();
        
        // Balanced squad composition
        List<Unit> availableUnits = new ArrayList<>(squadMembers);
        availableUnits.sort((u1, u2) -> Integer.compare(u2.getAbilities().size(), u1.getAbilities().size()));
        
        for (int i = 0; i < Math.min(size, availableUnits.size()); i++) {
            squad.add(availableUnits.get(i));
        }
        
        return squad;
    }
    
    private int getRequiredSquadSize(MissionType missionType) {
        switch (missionType) {
            case ASSASSINATION:
                return 4; // Smaller squad for stealth
            case EXTRACTION:
                return 6; // Full squad for extraction
            case DEFENSE:
                return 5; // Balanced defense
            case SABOTAGE:
                return 4; // Technical specialists
            case RECONNAISSANCE:
                return 3; // Small stealth squad
            default:
                return 4; // Default size
        }
    }
    
    /**
     * Award experience to unit
     */
    public void awardExperience(Unit unit, int experience) {
        int currentExp = unitExperience.getOrDefault(unit.getId(), 0);
        unitExperience.put(unit.getId(), currentExp + experience);
        
        // Check for level up
        if (currentExp + experience >= 100) {
            unitLevelUp(unit);
        }
    }
    
    /**
     * Handle unit level up
     */
    private void unitLevelUp(Unit unit) {
        // Increase unit stats
        unit.setAttackDamage(unit.getAttackDamage() + 5);
        // Note: Defense is handled by armor system
        unit.setMovementRange(unit.getMovementRange() + 1);
        
        // Assign specialization if none
        if (!unitSpecializations.containsKey(unit.getId())) {
            assignSpecialization(unit);
        }
        
        logSquadEvent("Unit " + unit.getName() + " leveled up!");
    }
    
    /**
     * Assign specialization to unit
     */
    private void assignSpecialization(Unit unit) {
        String[] specializations = {"ASSAULT", "HEAVY", "SNIPER", "SUPPORT", "SPECIALIST", "PSIONIC"};
        String specialization = specializations[random.nextInt(specializations.length)];
        unitSpecializations.put(unit.getId(), specialization);
        
        logSquadEvent("Unit " + unit.getName() + " specialized as " + specialization);
    }
    
    /**
     * Complete mission and update squad
     */
    public void completeMission(boolean success) {
        totalMissions++;
        if (success) {
            successfulMissions++;
        }
        
        // Award experience to deployed units
        for (Unit unit : deployedUnits.values()) {
            int experience = success ? 25 : 10;
            awardExperience(unit, experience);
        }
        
        // Return all units to reserve
        List<Unit> deployedList = new ArrayList<>(deployedUnits.values());
        for (Unit unit : deployedList) {
            returnToReserve(unit);
        }
        
        logSquadEvent("Mission completed: " + (success ? "SUCCESS" : "FAILURE"));
    }
    
    /**
     * Get squad statistics
     */
    public String getSquadStatistics() {
        return String.format("Squad %s: %d/%d units, %d missions (%d successful), %.1f%% success rate",
                squadId, currentSquadSize, maxSquadSize, totalMissions, successfulMissions,
                totalMissions > 0 ? (successfulMissions * 100.0 / totalMissions) : 0.0);
    }
    
    private void logSquadEvent(String event) {
        squadHistory.add(System.currentTimeMillis() + ": " + event);
    }
}

package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.aliensattack.core.enums.SquadTacticType;
import com.aliensattack.core.model.SquadBonding.BondAbility;
import com.aliensattack.core.model.SquadTacticsSystem.SquadFormation;

import java.util.*;

/**
 * Advanced Squad Cohesion Manager for XCOM 2 Tactical Combat
 * Manages squad bonding, tactics, and coordination bonuses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SquadCohesionManager {
    
    private Map<String, SquadBonding> activeBonds;
    private Map<String, SquadTacticsSystem> squadSystems;
    private List<Unit> squadMembers;
    private int cohesionLevel;
    private int bondingPoints;
    private int tacticalBonus;
    private Random random;
    
    /**
     * Initialize the squad cohesion manager
     */
    public void initialize() {
        if (activeBonds == null) {
            activeBonds = new HashMap<>();
        }
        if (squadSystems == null) {
            squadSystems = new HashMap<>();
        }
        if (squadMembers == null) {
            squadMembers = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        cohesionLevel = 1;
        bondingPoints = 100;
        tacticalBonus = 0;
        
        initializeSquadSystems();
    }
    
    /**
     * Initialize squad systems
     */
    private void initializeSquadSystems() {
        // Create squad tactics systems for different squads
        SquadTacticsSystem alphaSquad = new SquadTacticsSystem();
        squadSystems.put("Squad_Alpha", alphaSquad);
        
        SquadTacticsSystem betaSquad = new SquadTacticsSystem();
        squadSystems.put("Squad_Beta", betaSquad);
        
        SquadTacticsSystem gammaSquad = new SquadTacticsSystem();
        squadSystems.put("Squad_Gamma", gammaSquad);
    }
    
    /**
     * Create squad bond
     */
    public boolean createSquadBond(Unit unit1, Unit unit2) {
        if (unit1 != null && unit2 != null && bondingPoints >= 25) {
            SquadBonding bond = new SquadBonding(unit1, unit2);
            String bondId = "BOND_" + unit1.getId() + "_" + unit2.getId();
            
            activeBonds.put(bondId, bond);
            bondingPoints -= 25;
            
            // Update cohesion level
            updateCohesionLevel();
            
            return true;
        }
        return false;
    }
    
    /**
     * Remove squad bond
     */
    public boolean removeSquadBond(String bondId) {
        if (activeBonds.remove(bondId) != null) {
            bondingPoints += 15;
            updateCohesionLevel();
            return true;
        }
        return false;
    }
    
    /**
     * Update cohesion level
     */
    private void updateCohesionLevel() {
        cohesionLevel = Math.min(10, activeBonds.size() + 1);
        tacticalBonus = cohesionLevel * 5;
    }
    
    /**
     * Get squad bonds
     */
    public List<SquadBonding> getSquadBonds(String squadId) {
        List<SquadBonding> squadBonds = new ArrayList<>();
        
        for (SquadBonding bond : activeBonds.values()) {
            if (bond.getUnit1().getSquadId().equals(squadId) || 
                bond.getUnit2().getSquadId().equals(squadId)) {
                squadBonds.add(bond);
            }
        }
        
        return squadBonds;
    }
    
    /**
     * Get squad formation
     */
    public SquadFormation getSquadFormation(String squadId, SquadTacticType tacticType) {
        SquadTacticsSystem squadSystem = squadSystems.get(squadId);
        if (squadSystem != null) {
            switch (tacticType) {
                case OFFENSIVE:
                    return squadSystem.createOffensiveFormation();
                case DEFENSIVE:
                    return squadSystem.createDefensiveFormation();
                case FLANKING:
                    return squadSystem.createFlankingFormation();
                case SUPPORT:
                    return squadSystem.createSupportFormation();
                default:
                    return squadSystem.createBalancedFormation();
            }
        }
        return null;
    }
    
    /**
     * Get cohesion status
     */
    public String getCohesionStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Squad Cohesion Status:\n");
        status.append("- Cohesion Level: ").append(cohesionLevel).append("\n");
        status.append("- Bonding Points: ").append(bondingPoints).append("\n");
        status.append("- Tactical Bonus: ").append(tacticalBonus).append("\n");
        status.append("- Active Bonds: ").append(activeBonds.size()).append("\n");
        status.append("- Squad Members: ").append(squadMembers.size()).append("\n");
        status.append("- Squad Systems: ").append(squadSystems.size()).append("\n");
        
        // Add bond details
        status.append("- Bond Details:\n");
        for (Map.Entry<String, SquadBonding> entry : activeBonds.entrySet()) {
            SquadBonding bond = entry.getValue();
            status.append("  * ").append(entry.getKey()).append(": ")
                  .append(bond.getUnit1().getUnitName()).append(" <-> ")
                  .append(bond.getUnit2().getUnitName()).append("\n");
        }
        
        return status.toString();
    }
}

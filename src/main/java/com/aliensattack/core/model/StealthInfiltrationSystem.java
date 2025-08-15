package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

/**
 * Advanced Stealth Infiltration System for XCOM 2 Tactical Combat
 * Manages stealth mechanics, infiltration, and covert operations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StealthInfiltrationSystem {
    
    private Map<String, String> stealthLevels;
    private Map<String, String> infiltrationTypes;
    private List<Unit> stealthUnits;
    private int stealthBonus;
    private int infiltrationSuccess;
    private Random random;
    
    /**
     * Initialize the stealth infiltration system
     */
    public void initialize() {
        if (stealthLevels == null) {
            stealthLevels = new HashMap<>();
        }
        if (infiltrationTypes == null) {
            infiltrationTypes = new HashMap<>();
        }
        if (stealthUnits == null) {
            stealthUnits = new ArrayList<>();
        }
        if (random == null) {
            random = new Random();
        }
        
        stealthBonus = 0;
        infiltrationSuccess = 75;
        
        initializeStealthLevels();
        initializeInfiltrationTypes();
    }
    
    /**
     * Initialize stealth levels
     */
    private void initializeStealthLevels() {
        // Add stealth levels
        stealthLevels.put("VISIBLE", "VISIBLE");
        stealthLevels.put("CONCEALED", "CONCEALED");
        stealthLevels.put("STEALTH", "STEALTH");
        stealthLevels.put("INVISIBLE", "INVISIBLE");
    }
    
    /**
     * Initialize infiltration types
     */
    private void initializeInfiltrationTypes() {
        // Add infiltration types
        infiltrationTypes.put("COVERT", "COVERT");
        infiltrationTypes.put("AGGRESSIVE", "AGGRESSIVE");
        infiltrationTypes.put("SUPPORT", "SUPPORT");
    }
    
    /**
     * Add stealth unit
     */
    public void addStealthUnit(Unit unit) {
        if (!stealthUnits.contains(unit)) {
            stealthUnits.add(unit);
            updateStealthBonus();
        }
    }
    
    /**
     * Remove stealth unit
     */
    public void removeStealthUnit(Unit unit) {
        if (stealthUnits.remove(unit)) {
            updateStealthBonus();
        }
    }
    
    /**
     * Update stealth bonus
     */
    private void updateStealthBonus() {
        stealthBonus = stealthUnits.size() * 5;
    }
    
    /**
     * Get stealth status
     */
    public String getStealthStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Stealth Infiltration Status:\n");
        status.append("- Stealth Bonus: ").append(stealthBonus).append("\n");
        status.append("- Infiltration Success: ").append(infiltrationSuccess).append("%\n");
        status.append("- Stealth Units: ").append(stealthUnits.size()).append("\n");
        status.append("- Available Stealth Levels: ").append(stealthLevels.size()).append("\n");
        status.append("- Available Infiltration Types: ").append(infiltrationTypes.size()).append("\n");
        
        return status.toString();
    }
}


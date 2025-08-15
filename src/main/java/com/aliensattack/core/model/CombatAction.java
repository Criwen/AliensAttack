package com.aliensattack.core.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

/**
 * Combat Action - XCOM2 Tactical Combat
 * Represents a combat action with all relevant properties
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombatAction {
    
    private String id;
    private Unit attacker;
    private Unit target;
    private String actionType;
    private int damage;
    private int accuracy;
    private double criticalChance;
    private boolean isCritical;
    private String message;
    private long timestamp;
    private List<String> effects;
    
    // Constructor removed - using Lombok @NoArgsConstructor instead
    
    // Additional methods for compatibility
    public String getActionId() {
        return id;
    }
    
    public int getActionCost() {
        return 1; // Default action cost
    }
    
    public List<String> getEffects() {
        if (effects == null) {
            effects = new ArrayList<>();
        }
        return effects;
    }
    
    public void addEffect(String effect) {
        if (effects == null) {
            effects = new ArrayList<>();
        }
        effects.add(effect);
    }
}

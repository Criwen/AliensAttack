package com.aliensattack.combat;

import lombok.Data;

/**
 * Represents the result of a combat action
 */
@Data
public class CombatResult {
    private boolean success;
    private int damage;
    private String message;
    
    public CombatResult(boolean success, int damage, String message) {
        this.success = success;
        this.damage = damage;
        this.message = message;
    }
    
    // Additional getter methods for compatibility
    public boolean isSuccess() {
        return success;
    }
} 
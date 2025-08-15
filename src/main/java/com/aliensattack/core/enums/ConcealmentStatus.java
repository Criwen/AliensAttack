package com.aliensattack.core.enums;

/**
 * Concealment Status Enum - XCOM2 Tactical Combat
 * Defines different states of unit concealment
 */
public enum ConcealmentStatus {
    
    VISIBLE("Visible", 0, 0.0),
    CONCEALED("Concealed", 1, 1.0),
    STEALTH("Stealth", 2, 1.5),
    INVISIBLE("Invisible", 3, 2.0),
    BROKEN("Broken", -1, 0.0);
    
    private final String displayName;
    private final int level;
    private final double strength;
    
    ConcealmentStatus(String displayName, int level, double strength) {
        this.displayName = displayName;
        this.level = level;
        this.strength = strength;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getLevel() {
        return level;
    }
    
    public double getStrength() {
        return strength;
    }
    
    public boolean isConcealed() {
        return this == CONCEALED || this == STEALTH || this == INVISIBLE;
    }
    
    public boolean isBroken() {
        return this == BROKEN;
    }
    
    public boolean isVisible() {
        return this == VISIBLE || this == BROKEN;
    }
}

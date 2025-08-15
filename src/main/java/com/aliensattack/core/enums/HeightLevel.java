package com.aliensattack.core.enums;

/**
 * Height Level Enum - XCOM2 Tactical Combat
 * Defines different elevation levels for tactical positioning
 */
public enum HeightLevel {
    
    GROUND("Ground", 0, 0.0, 0),
    LOW("Low", 1, 1.0, 1000),
    MEDIUM("Medium", 2, 1.5, 2000),
    HIGH("High", 3, 2.0, 3000);
    
    private final String displayName;
    private final int level;
    private final double strength;
    private final int duration;
    
    HeightLevel(String displayName, int level, double strength, int duration) {
        this.displayName = displayName;
        this.level = level;
        this.strength = strength;
        this.duration = duration;
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
    
    public int getDuration() {
        return duration;
    }
    
    public boolean isHigherThan(HeightLevel other) {
        return this.level > other.level;
    }
    
    public boolean isLowerThan(HeightLevel other) {
        return this.level < other.level;
    }
    
    public int getHeightDifference(HeightLevel other) {
        return this.level - other.level;
    }
    
    public static HeightLevel fromLevel(int level) {
        return switch (level) {
            case 0 -> GROUND;
            case 1 -> LOW;
            case 2 -> MEDIUM;
            case 3 -> HIGH;
            default -> GROUND;
        };
    }
}

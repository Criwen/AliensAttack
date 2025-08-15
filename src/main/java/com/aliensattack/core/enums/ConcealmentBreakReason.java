package com.aliensattack.core.enums;

/**
 * Concealment Break Reason Enum - XCOM2 Tactical Combat
 * Defines different reasons why concealment might be broken
 */
public enum ConcealmentBreakReason {
    
    ATTACK("Attack", "Unit attacked while concealed"),
    DETECTED("Detected", "Unit was detected by enemy"),
    MOVEMENT("Movement", "Unit moved to visible position"),
    ABILITY_USE("Ability Use", "Unit used ability while concealed"),
    ENVIRONMENTAL("Environmental", "Environmental factors broke concealment"),
    TEAMMATE_ACTION("Teammate Action", "Teammate action revealed unit"),
    SOUND("Sound", "Sound made by unit broke concealment"),
    LIGHT("Light", "Light exposure broke concealment"),
    ENEMY_ABILITY("Enemy Ability", "Enemy ability revealed unit"),
    MISSION_OBJECTIVE("Mission Objective", "Mission objective forced reveal");
    
    private final String code;
    private final String description;
    
    ConcealmentBreakReason(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isCombatRelated() {
        return this == ATTACK || this == ENEMY_ABILITY;
    }
    
    public boolean isEnvironmental() {
        return this == ENVIRONMENTAL || this == SOUND || this == LIGHT;
    }
    
    public boolean isPlayerAction() {
        return this == ATTACK || this == MOVEMENT || this == ABILITY_USE;
    }
    
    public boolean isPassive() {
        return this == DETECTED || this == ENVIRONMENTAL || this == SOUND || this == LIGHT;
    }
}

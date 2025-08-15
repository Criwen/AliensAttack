package com.aliensattack.core.enums;

/**
 * Action Type Enum - XCOM2 Tactical Combat
 * Defines different types of actions units can perform
 */
public enum ActionType {
    MOVE("Move", "Unit movement"),
    ATTACK("Attack", "Weapon attack"),
    OVERWATCH("Overwatch", "Reaction shot"),
    RELOAD("Reload", "Weapon reload"),
    HEAL("Heal", "Medical action"),
    GRENADE("Grenade", "Explosive use"),
    SPECIAL_ABILITY("Special Ability", "Unique unit ability"),
    DEFEND("Defend", "Defensive stance"),
    DASH("Dash", "Fast movement"),
    HACK("Hack", "Technical action");
    
    private final String displayName;
    private final String description;
    
    ActionType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
}

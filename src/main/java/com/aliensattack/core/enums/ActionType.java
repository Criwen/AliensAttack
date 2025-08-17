package com.aliensattack.core.enums;

/**
 * Action Type Enum - XCOM2 Tactical Combat
 * Defines different types of actions units can perform
 */
public enum ActionType {
    MOVE("Move", "Unit movement", 1),
    ATTACK("Attack", "Weapon attack", 1),
    OVERWATCH("Overwatch", "Reaction shot", 1),
    RELOAD("Reload", "Weapon reload", 1),
    HEAL("Heal", "Medical action", 1),
    GRENADE("Grenade", "Explosive use", 1),
    SPECIAL_ABILITY("Special Ability", "Unique unit ability", 2),
    DEFEND("Defend", "Defensive stance", 1),
    DASH("Dash", "Fast movement", 2),
    HACK("Hack", "Technical action", 1);
    
    private final String displayName;
    private final String description;
    private final int actionPointCost;
    
    ActionType(String displayName, String description, int actionPointCost) {
        this.displayName = displayName;
        this.description = description;
        this.actionPointCost = actionPointCost;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getActionPointCost() {
        return actionPointCost;
    }
}

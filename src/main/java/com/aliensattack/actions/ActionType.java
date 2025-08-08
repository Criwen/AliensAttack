package com.aliensattack.actions;

/**
 * Типы действий для юнитов в тактическом бою
 */
public enum ActionType {
    MOVE("Движение", 1),
    ATTACK("Атака", 1),
    OVERWATCH("Наблюдение", 1),
    RELOAD("Перезарядка", 1),
    HEAL("Лечение", 1),
    GRENADE("Граната", 1),
    SPECIAL_ABILITY("Спец. способность", 2),
    DEFEND("Защита", 1),
    DASH("Рывок", 2);
    
    private final String displayName;
    private final int actionPointCost;
    
    ActionType(String displayName, int actionPointCost) {
        this.displayName = displayName;
        this.actionPointCost = actionPointCost;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getActionPointCost() {
        return actionPointCost;
    }
}
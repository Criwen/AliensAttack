package com.aliensattack.actions;

import com.aliensattack.actions.interfaces.IAction;
import com.aliensattack.core.interfaces.IUnit;
import com.aliensattack.core.model.Position;

/**
 * Factory for creating different types of actions
 * Implements Factory pattern for action creation
 */
public class ActionFactory {
    
    /**
     * Create a move action
     */
    public static IAction createMoveAction(IUnit performer, Position targetPosition) {
        return new MoveAction(performer, targetPosition);
    }
    
    /**
     * Create an attack action
     */
    public static IAction createAttackAction(IUnit performer, IUnit target) {
        return new AttackAction(performer, target);
    }
    
    /**
     * Create an overwatch action
     */
    public static IAction createOverwatchAction(IUnit performer) {
        return new OverwatchAction(performer);
    }
    
    /**
     * Create a reload action
     */
    public static IAction createReloadAction(IUnit performer) {
        return new ReloadAction(performer);
    }
    
    /**
     * Create a heal action
     */
    public static IAction createHealAction(IUnit performer, IUnit target) {
        return new HealAction(performer, target);
    }
    
    /**
     * Create a grenade action
     */
    public static IAction createGrenadeAction(IUnit performer, Position targetPosition) {
        return new GrenadeAction(performer, targetPosition);
    }
    
    /**
     * Create a defend action
     */
    public static IAction createDefendAction(IUnit performer) {
        return new DefendAction(performer);
    }
    
    /**
     * Create a dash action
     */
    public static IAction createDashAction(IUnit performer, Position targetPosition) {
        return new DashAction(performer, targetPosition);
    }
    
    /**
     * Create action based on action type string
     */
    public static IAction createAction(String actionType, IUnit performer, Object target) {
        return switch (actionType.toUpperCase()) {
            case "MOVE" -> {
                if (target instanceof Position) {
                    yield createMoveAction(performer, (Position) target);
                }
                yield null;
            }
            case "ATTACK" -> {
                if (target instanceof IUnit) {
                    yield createAttackAction(performer, (IUnit) target);
                }
                yield null;
            }
            case "OVERWATCH" -> createOverwatchAction(performer);
            case "RELOAD" -> createReloadAction(performer);
            case "HEAL" -> {
                if (target instanceof IUnit) {
                    yield createHealAction(performer, (IUnit) target);
                }
                yield null;
            }
            case "GRENADE" -> {
                if (target instanceof Position) {
                    yield createGrenadeAction(performer, (Position) target);
                }
                yield null;
            }
            case "DEFEND" -> createDefendAction(performer);
            case "DASH" -> {
                if (target instanceof Position) {
                    yield createDashAction(performer, (Position) target);
                }
                yield null;
            }
            default -> null;
        };
    }
}

package com.aliensattack.core.exception;

import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Weapon;

import java.util.function.Supplier;

/**
 * Utility class for safe operations with comprehensive error handling
 */
public class SafeOperations {
    
    private static final ErrorHandler errorHandler = ErrorHandler.getInstance();
    
    /**
     * Execute operation safely with error handling
     */
    public static <T> T executeSafely(Supplier<T> operation, String component, String operationName, T defaultValue) {
        try {
            return operation.get();
        } catch (Exception e) {
            errorHandler.handleException(e, component, operationName);
            return defaultValue;
        }
    }
    
    /**
     * Execute void operation safely
     */
    public static void executeSafely(Runnable operation, String component, String operationName) {
        try {
            operation.run();
        } catch (Exception e) {
            errorHandler.handleException(e, component, operationName);
        }
    }
    
    /**
     * Safe unit operations
     */
    public static class UnitOperations {
        
        /**
         * Safely get unit health
         */
        public static int getHealth(Unit unit) {
            return executeSafely(
                () -> unit.getCurrentHealth(),
                "Unit",
                "get_health",
                0
            );
        }
        
        /**
         * Safely check if unit is alive
         */
        public static boolean isAlive(Unit unit) {
            return executeSafely(
                () -> unit.isAlive(),
                "Unit",
                "check_alive",
                false
            );
        }
        
        /**
         * Safely get unit position
         */
        public static Position getPosition(Unit unit) {
            return executeSafely(
                () -> unit.getPosition(),
                "Unit",
                "get_position",
                new Position(0, 0)
            );
        }
        
        /**
         * Safely spend action points
         */
        public static boolean spendActionPoints(Unit unit, int points) {
            return executeSafely(
                () -> {
                    if (unit.getActionPoints() >= points) {
                        unit.spendActionPoint();
                        return true;
                    }
                    return false;
                },
                "Unit",
                "spend_action_points",
                false
            );
        }
        
        /**
         * Safely attack with unit
         */
        public static boolean attack(Unit attacker, Unit target) {
            return executeSafely(
                () -> {
                    if (!attacker.isAlive() || !target.isAlive() || attacker.getActionPoints() < 1) {
                        return false;
                    }
                    attacker.spendActionPoint();
                    return true;
                },
                "Combat",
                "attack",
                false
            );
        }
        
        /**
         * Safely move unit
         */
        public static boolean moveUnit(Unit unit, Position newPosition) {
            return executeSafely(
                () -> {
                    if (!unit.isAlive() || unit.getActionPoints() < 1 || 
                        newPosition.getX() < 0 || newPosition.getY() < 0) {
                        return false;
                    }
                    unit.setPosition(newPosition);
                    unit.spendActionPoint();
                    return true;
                },
                "Unit",
                "move",
                false
            );
        }
        
        /**
         * Safely use weapon
         */
        public static boolean useWeapon(Unit unit, Weapon weapon) {
            return executeSafely(
                () -> {
                    if (weapon == null || weapon.getCurrentAmmo() <= 0) {
                        return false;
                    }
                    weapon.useAmmo();
                    return true;
                },
                "Unit",
                "use_weapon",
                false
            );
        }
    }
    
    /**
     * Safe UI operations
     */
    public static class UIOperations {
        
        /**
         * Safely update UI component
         */
        public static void updateComponent(Runnable updateOperation, String componentName) {
            executeSafely(
                updateOperation,
                "UI",
                "update_" + componentName
            );
        }
        
        /**
         * Safely handle user input
         */
        public static <T> T handleInput(Supplier<T> inputOperation, String inputType, T defaultValue) {
            return executeSafely(
                inputOperation,
                "UI",
                "handle_input_" + inputType,
                defaultValue
            );
        }
        
        /**
         * Safely render component
         */
        public static void renderComponent(Runnable renderOperation, String componentName) {
            executeSafely(
                renderOperation,
                "UI",
                "render_" + componentName
            );
        }
    }
    
    /**
     * Safe combat operations
     */
    public static class CombatOperations {
        
        /**
         * Safely perform attack
         */
        public static boolean performAttack(Unit attacker, Unit target) {
            return executeSafely(
                () -> {
                    if (!attacker.isAlive() || !target.isAlive() || attacker.getActionPoints() < 1) {
                        return false;
                    }
                    
                    // Check range
                    int distance = attacker.getPosition().getDistanceTo(target.getPosition());
                    if (distance > attacker.getWeapon().getRange()) {
                        return false;
                    }
                    
                    attacker.spendActionPoint();
                    return true;
                },
                "Combat",
                "perform_attack",
                false
            );
        }
        
        /**
         * Safely use ability
         */
        public static boolean useAbility(Unit unit, String abilityName) {
            return executeSafely(
                () -> {
                    if (!unit.isAlive() || unit.getActionPoints() < 1) {
                        return false;
                    }
                    unit.spendActionPoint();
                    return true;
                },
                "Combat",
                "use_ability_" + abilityName,
                false
            );
        }
    }
    
    /**
     * Safe validation operations
     */
    public static class ValidationOperations {
        
        /**
         * Validate unit state
         */
        public static boolean validateUnit(Unit unit, String operation) {
            return executeSafely(
                () -> unit != null && unit.isAlive(),
                "Validation",
                "validate_unit_" + operation,
                false
            );
        }
        
        /**
         * Validate position
         */
        public static boolean validatePosition(Position position) {
            return executeSafely(
                () -> position != null && position.getX() >= 0 && position.getY() >= 0,
                "Validation",
                "validate_position",
                false
            );
        }
        
        /**
         * Validate weapon
         */
        public static boolean validateWeapon(Weapon weapon) {
            return executeSafely(
                () -> weapon != null && weapon.getCurrentAmmo() > 0,
                "Validation",
                "validate_weapon",
                false
            );
        }
    }
} 
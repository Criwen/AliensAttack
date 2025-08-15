package com.aliensattack.actions;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.combat.ICombatManager;
import com.aliensattack.core.GameLogManager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import java.util.*;

/**
 * Менеджер действий для управления действиями юнитов в тактическом бою
 */
@Getter
@Log4j2
public class ActionManager {
    private final ITacticalField field;
    private final ICombatManager combatManager;
    private final List<UnitAction> actionHistory;
    private final Map<Unit, List<ActionType>> availableActions;
    private Unit selectedUnit;
    private ActionType selectedAction;
    private Position selectedTargetPosition; // Новая переменная для выбранной позиции
    
    public ActionManager(ITacticalField field, ICombatManager combatManager) {
        this.field = field;
        this.combatManager = combatManager;
        this.actionHistory = new ArrayList<>();
        this.availableActions = new HashMap<>();
        this.selectedTargetPosition = null;
        
        // Log manager initialization
        GameLogManager.logSystemInit("Action Manager");
        log.info("ActionManager initialized with field size: {}x{}", field.getWidth(), field.getHeight());
        
        initializeAvailableActions();
        GameLogManager.logSystemReady("Action Manager");
    }
    
    private void initializeAvailableActions() {
        // Log initialization start
        GameLogManager.logActionExecution("ActionManager", "System", "Initializing available actions for all units");
        
        // Инициализируем действия для всех существующих юнитов
        for (Unit unit : field.getAllUnits()) {
            initializeActionsForUnit(unit);
        }
        
        // Log initialization completion
        GameLogManager.logActionExecution("ActionManager", "System", 
            String.format("Available actions initialized for %d units", field.getAllUnits().size()));
    }
    
    public void initializeActionsForUnit(Unit unit) {
        // Log unit action initialization
        GameLogManager.logActionExecution("ActionManager", "System", 
            String.format("Initializing actions for unit: %s (Type: %s)", unit.getName(), unit.getUnitType()));
        
        // Действия для солдат
        List<ActionType> soldierActions = Arrays.asList(
            ActionType.MOVE,
            ActionType.ATTACK,
            ActionType.OVERWATCH,
            ActionType.RELOAD,
            ActionType.HEAL,
            ActionType.GRENADE,
            ActionType.SPECIAL_ABILITY,
            ActionType.DEFEND,
            ActionType.DASH
        );
        
        // Действия для пришельцев
        List<ActionType> alienActions = Arrays.asList(
            ActionType.MOVE,
            ActionType.ATTACK,
            ActionType.SPECIAL_ABILITY,
            ActionType.DEFEND,
            ActionType.DASH
        );
        
        // Действия для гражданских
        List<ActionType> civilianActions = Arrays.asList(
            ActionType.MOVE,
            ActionType.HEAL,
            ActionType.DEFEND,
            ActionType.DASH
        );
        
        // Действия для техники
        List<ActionType> vehicleActions = Arrays.asList(
            ActionType.MOVE,
            ActionType.ATTACK,
            ActionType.SPECIAL_ABILITY,
            ActionType.DEFEND
        );
        
        // Сохраняем в карте в зависимости от типа юнита
        List<ActionType> actionsToAssign = null;
        switch (unit.getUnitType()) {
            case SOLDIER -> {
                actionsToAssign = new ArrayList<>(soldierActions);
                GameLogManager.logUnitAction(unit.getName(), "Action Assignment", 
                    String.format("Assigned %d soldier actions", soldierActions.size()));
            }
            case ALIEN -> {
                actionsToAssign = new ArrayList<>(alienActions);
                GameLogManager.logUnitAction(unit.getName(), "Action Assignment", 
                    String.format("Assigned %d alien actions", alienActions.size()));
            }
            case ALIEN_RULER -> {
                actionsToAssign = new ArrayList<>(alienActions);
                GameLogManager.logUnitAction(unit.getName(), "Action Assignment", 
                    String.format("Assigned %d alien ruler actions", alienActions.size()));
            }
            case CIVILIAN -> {
                actionsToAssign = new ArrayList<>(civilianActions);
                GameLogManager.logUnitAction(unit.getName(), "Action Assignment", 
                    String.format("Assigned %d civilian actions", civilianActions.size()));
            }
            case VEHICLE -> {
                actionsToAssign = new ArrayList<>(vehicleActions);
                GameLogManager.logUnitAction(unit.getName(), "Action Assignment", 
                    String.format("Assigned %d vehicle actions", vehicleActions.size()));
            }
            case ROBOTIC -> {
                actionsToAssign = new ArrayList<>(vehicleActions); // Robotic units use vehicle actions
                GameLogManager.logUnitAction(unit.getName(), "Action Assignment", 
                    String.format("Assigned %d robotic actions", vehicleActions.size()));
            }
        }
        
        if (actionsToAssign != null) {
            availableActions.put(unit, actionsToAssign);
            GameLogManager.logDebug("ActionManager", String.format("Actions assigned to %s: %s", 
                unit.getName(), actionsToAssign));
        }
    }
    
    public void selectUnit(Unit unit) {
        if (unit != null && unit.isAlive() && unit.canMove()) {
            this.selectedUnit = unit;
            this.selectedAction = null;
            this.selectedTargetPosition = null; // Сбрасываем выбранную позицию
            
            // Log unit selection
            GameLogManager.logUserInteraction("Unit Selection", 
                String.format("Selected unit: %s", unit.getName()));
            GameLogManager.logUnitAction(unit.getName(), "Selection", 
                String.format("Unit selected - AP: %.1f, Type: %s, Position: %s", 
                    unit.getActionPoints(), unit.getUnitType(), unit.getPosition()));
            
            log.info("Unit selected: {} (AP: {}, Type: {})", unit.getName(), unit.getActionPoints(), unit.getUnitType());
            System.out.println("Выбран юнит: " + unit.getName() + 
                " (AP: " + unit.getActionPoints() + ", Тип: " + unit.getUnitType() + ")");
        } else {
            // Log failed unit selection
            String reason = unit == null ? "Unit is null" : 
                          (!unit.isAlive() ? "Unit is not alive" : "Unit cannot move");
            GameLogManager.logUserInteraction("Unit Selection", 
                String.format("Failed to select unit: %s", reason));
            
            System.out.println("Не удалось выбрать юнит: " + reason);
        }
    }
    
    public void selectAction(ActionType actionType) {
        if (selectedUnit != null && isActionAvailable(selectedUnit, actionType)) {
            this.selectedAction = actionType;
            this.selectedTargetPosition = null; // Сбрасываем выбранную позицию
            log.info("Action selected: {} for unit: {} (cost: {} AP)", 
                    actionType.getDisplayName(), selectedUnit.getName(), actionType.getActionPointCost());
            System.out.println("DEBUG: selectAction called - selectedAction set to: " + actionType);
            System.out.println("Выбрано действие: " + actionType.getDisplayName() + 
                             " (стоимость: " + actionType.getActionPointCost() + " AP)");
        } else {
            log.warn("Cannot select action: {} for unit: {} (available: {})", 
                    actionType, selectedUnit != null ? selectedUnit.getName() : "null",
                    selectedUnit != null ? isActionAvailable(selectedUnit, actionType) : "false");
            System.out.println("DEBUG: selectAction failed - selectedUnit=" + (selectedUnit != null ? selectedUnit.getName() : "null") + 
                             ", actionType=" + actionType + ", isActionAvailable=" + (selectedUnit != null ? isActionAvailable(selectedUnit, actionType) : "false"));
        }
    }
    
    public void selectTargetPosition(Position position) {
        if (selectedUnit != null && selectedAction != null) {
            this.selectedTargetPosition = position;
            System.out.println("DEBUG: selectTargetPosition called - selectedTargetPosition set to: " + position);
            System.out.println("Выбрана целевая позиция: (" + position.getX() + ", " + position.getY() + ")");
        } else {
            System.out.println("DEBUG: selectTargetPosition failed - selectedUnit=" + (selectedUnit != null ? selectedUnit.getName() : "null") + 
                             ", selectedAction=" + selectedAction);
        }
    }
    
    public boolean isActionAvailable(Unit unit, ActionType actionType) {
        System.out.println("DEBUG: isActionAvailable called for unit=" + (unit != null ? unit.getName() : "null") + 
                          ", actionType=" + actionType);
        System.out.println("DEBUG: unit.isAlive()=" + (unit != null ? unit.isAlive() : "null"));
        System.out.println("DEBUG: unit.getActionPoints()=" + (unit != null ? unit.getActionPoints() : "null"));
        System.out.println("DEBUG: actionType.getActionPointCost()=" + actionType.getActionPointCost());
        
        if (unit == null || !unit.isAlive() || unit.getActionPoints() < actionType.getActionPointCost()) {
            System.out.println("DEBUG: isActionAvailable failed - basic checks failed");
            return false;
        }
        
        List<ActionType> unitActions = availableActions.get(unit);
        System.out.println("DEBUG: unitActions=" + unitActions);
        boolean containsAction = unitActions != null && unitActions.contains(actionType);
        System.out.println("DEBUG: containsAction=" + containsAction);
        return containsAction;
    }
    
    public List<ActionType> getAvailableActionsForUnit(Unit unit) {
        return availableActions.getOrDefault(unit, new ArrayList<>());
    }
    
    public UnitAction executeAction(Position targetPosition) {
        System.out.println("DEBUG: executeAction(Position) called with targetPosition=" + targetPosition);
        return executeAction(null, targetPosition);
    }
    
    public UnitAction executeAction(Unit target) {
        return executeAction(target, null);
    }
    
    public UnitAction executeAction(Unit target, Position targetPosition) {
        log.info("Executing action: {} for unit: {} -> target: {}, position: {}", 
                selectedAction, selectedUnit != null ? selectedUnit.getName() : "null",
                target != null ? target.getName() : "null", targetPosition);
        
        System.out.println("DEBUG: === executeAction ENTRY ===");
        System.out.println("DEBUG: executeAction called with target=" + (target != null ? target.getName() : "null") + 
                          ", targetPosition=" + targetPosition);
        
        if (selectedUnit == null || selectedAction == null) {
            log.error("Cannot execute action - missing unit or action: unit={}, action={}", 
                    selectedUnit != null ? selectedUnit.getName() : "null", selectedAction);
            System.out.println("DEBUG: executeAction failed - selectedUnit=" + (selectedUnit != null ? selectedUnit.getName() : "null") + 
                             ", selectedAction=" + selectedAction);
            return null;
        }
        
        // Для движения используем выбранную позицию, если она есть
        if (selectedAction == ActionType.MOVE && selectedTargetPosition != null) {
            targetPosition = selectedTargetPosition;
        }
        
        UnitAction action;
        if (target != null) {
            action = new UnitAction(selectedAction, selectedUnit, target);
        } else if (targetPosition != null) {
            action = new UnitAction(selectedAction, selectedUnit, targetPosition);
        } else {
            action = new UnitAction(selectedAction, selectedUnit);
        }
        
        // Для движения сначала обновляем тактическое поле, затем выполняем действие
        if (selectedAction == ActionType.MOVE && targetPosition != null) {
            System.out.println("DEBUG: Updating tactical field before movement");
            System.out.println("DEBUG: Unit: " + selectedUnit.getName() + ", Target: " + targetPosition);
            
            // Перемещаем через боевой менеджер, чтобы соблюдать правила боя
            boolean moveSuccess = combatManager.moveUnit(selectedUnit, targetPosition);
            if (!moveSuccess) {
                String msg = "Movement failed: moveUnit returned false (cell may be occupied or invalid)";
                log.error("Movement failed for unit: {} to position: ({}, {})", 
                        selectedUnit.getName(), targetPosition.getX(), targetPosition.getY());
                System.out.println("DEBUG: " + msg);
                System.out.println("LOG: " + msg);
                return null; // Возвращаем null если не удалось обновить тактическое поле
            } else {
                log.info("Movement successful for unit: {} to position: ({}, {})", 
                        selectedUnit.getName(), targetPosition.getX(), targetPosition.getY());
                System.out.println("DEBUG: Tactical field updated successfully");
            }
        }
        
        action.execute();
        
        actionHistory.add(action);
        
        log.info("Action executed successfully: {} for unit: {} (remaining AP: {})", 
                selectedAction, selectedUnit.getName(), selectedUnit.getActionPoints());
        System.out.println("Выполнено действие: " + action);
        
        // Сбрасываем выбор после выполнения
        if (selectedUnit.getActionPoints() <= 0) {
            log.info("Unit {} has no action points remaining, clearing selection", selectedUnit.getName());
            selectedUnit = null;
            selectedAction = null;
            selectedTargetPosition = null;
        } else {
            log.debug("Unit {} has {} action points remaining, keeping unit selected", 
                    selectedUnit.getName(), selectedUnit.getActionPoints());
            selectedAction = null; // Оставляем юнита выбранным для следующего действия
            selectedTargetPosition = null;
        }
        
        return action;
    }
    
    public List<Position> getValidMovePositions(Unit unit) {
        List<Position> validPositions = new ArrayList<>();
        Position currentPos = unit.getPosition();
        int maxRange = unit.getMovementRange();
        
        // Ограничиваем диапазон доступными AP
        int availableAP = (int) unit.getActionPoints();
        int effectiveRange = Math.min(maxRange, availableAP);
        
        System.out.println("DEBUG: getValidMovePositions - unit=" + unit.getName() + 
                          ", maxRange=" + maxRange + ", availableAP=" + availableAP + 
                          ", effectiveRange=" + effectiveRange);
        
        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                Position pos = new Position(x, y);
                int distance = Math.abs(currentPos.getX() - x) + Math.abs(currentPos.getY() - y);
                
                if (distance <= effectiveRange && distance > 0) {
                    // Проверяем, что позиция свободна
                    if (field.getUnitAt(x, y) == null) {
                        validPositions.add(pos);
                    }
                }
            }
        }
        
        System.out.println("DEBUG: getValidMovePositions returned " + validPositions.size() + " positions");
        return validPositions;
    }
    
    public int calculateMoveActionCost(Unit unit, Position targetPosition) {
        if (unit == null || targetPosition == null) {
            return ActionType.MOVE.getActionPointCost();
        }
        
        Position currentPos = unit.getPosition();
        int distance = Math.abs(currentPos.getX() - targetPosition.getX()) + 
                      Math.abs(currentPos.getY() - targetPosition.getY());
        
        // Базовая стоимость движения
        int baseCost = ActionType.MOVE.getActionPointCost();
        
        // Дополнительная стоимость за расстояние
        if (distance > 1) {
            baseCost += (distance - 1); // +1 AP за каждую дополнительную клетку
        }
        
        return Math.min(baseCost, (int)unit.getActionPoints()); // Не превышаем доступные AP
    }
    
    /**
     * Calculate action point cost for Overwatch and Suppression
     * These actions cost ALL remaining AP but minimum 1 AP
     */
    public int calculateOverwatchSuppressionCost(Unit unit) {
        if (unit == null) {
            return 1;
        }
        
        double currentAP = unit.getActionPoints();
        if (currentAP < 1.0) {
            return 0; // Недостаточно AP
        }
        
        return (int)currentAP; // Тратим все оставшиеся AP
    }
    
    public boolean canMoveToPosition(Unit unit, Position targetPosition) {
        if (unit == null || targetPosition == null) {
            return false;
        }
        
        // Проверяем, что позиция в пределах досягаемости
        Position currentPos = unit.getPosition();
        int distance = Math.abs(currentPos.getX() - targetPosition.getX()) + 
                      Math.abs(currentPos.getY() - targetPosition.getY());
        
        if (distance > unit.getMovementRange()) {
            return false;
        }
        
        // Проверяем, что позиция свободна
        if (field.getUnitAt(targetPosition.getX(), targetPosition.getY()) != null) {
            return false;
        }
        
        // Проверяем, что у юнита достаточно очков действия
        int moveCost = calculateMoveActionCost(unit, targetPosition);
        return unit.getActionPoints() >= moveCost;
    }
    
    public List<Unit> getValidTargets(Unit attacker) {
        if (attacker == null || !attacker.isAlive()) {
            return new ArrayList<>();
        }
        
        List<Unit> validTargets = new ArrayList<>();
        Position attackerPos = attacker.getPosition();
        int attackRange = attacker.getAttackRange();
        
        // Получаем видимых врагов
        List<Unit> visibleEnemies = field.getVisibleEnemies(attacker);
        
        for (Unit unit : visibleEnemies) {
            Position targetPos = unit.getPosition();
            
            // Проверяем расстояние атаки
            int distance = Math.abs(attackerPos.getX() - targetPos.getX()) + 
                          Math.abs(attackerPos.getY() - targetPos.getY());
            
            if (distance <= attackRange) {
                validTargets.add(unit);
            }
        }
        
        return validTargets;
    }
    

    
    public void clearSelection() {
        selectedUnit = null;
        selectedAction = null;
        selectedTargetPosition = null;
    }
    
    public void endTurn() {
        log.info("Ending turn - clearing selection and restoring action points");
        
        // Сбрасываем выбор
        clearSelection();
        
        // Восстанавливаем очки действия всем юнитам
        int unitsRestored = 0;
        for (Unit unit : field.getAllUnits()) {
            if (unit.isAlive()) {
                unit.resetActionPoints();
                unitsRestored++;
            }
        }
        
        log.info("Turn ended - {} units had action points restored", unitsRestored);
        System.out.println("Ход завершен. Очки действия восстановлены.");
    }
    
    public String getActionSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ИСТОРИЯ ДЕЙСТВИЙ ===\n");
        
        if (actionHistory.isEmpty()) {
            sb.append("Действий пока не было\n");
        } else {
            for (int i = Math.max(0, actionHistory.size() - 10); i < actionHistory.size(); i++) {
                sb.append((i + 1)).append(". ").append(actionHistory.get(i)).append("\n");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Проверяет нужно ли автоматически завершить ход
     * @return true если ход должен быть завершен автоматически
     */
    public boolean shouldAutoEndTurn() {
        return combatManager.allPlayerUnitsOutOfActionPoints();
    }
}
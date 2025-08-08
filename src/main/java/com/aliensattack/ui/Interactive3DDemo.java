package com.aliensattack.ui;

import com.aliensattack.core.model.Position;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.CoverObject;
import com.aliensattack.core.enums.CoverType;
import com.aliensattack.core.enums.UnitType;
import com.aliensattack.field.OptimizedTacticalField;
import com.aliensattack.combat.OptimizedCombatManager;
import com.aliensattack.actions.ActionManager;
import com.aliensattack.actions.ActionType;
import com.aliensattack.actions.UnitAction;
import com.aliensattack.visualization.Combat3DVisualizer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Интерактивная 3D демо с системой действий для юнитов
 */
public class Interactive3DDemo extends Application {
    
    private OptimizedTacticalField field;
    private OptimizedCombatManager combatManager;
    private ActionManager actionManager;
    private Combat3DVisualizer visualizer;
    
    // UI элементы
    private ListView<Unit> unitList;
    private TextArea logArea;
    private Label statusLabel;
    private Button endTurnButton;
    
    // Панель действий с кнопками
    private GridPane actionPanel;
    private Map<ActionType, Button> actionButtons;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        setupGameLogic();
        setupUI(primaryStage);
        launch3DVisualization();
        updateUI();
    }
    
    private void setupGameLogic() {
        // Создаем тактическое поле
        field = new OptimizedTacticalField(64, 64);
        combatManager = new OptimizedCombatManager(field);
        actionManager = new ActionManager(field, combatManager);
        
        // Создаем юнитов
        Unit soldier1 = new Unit("Сержант Иванов", 100, 3, 4, 25, UnitType.SOLDIER);
        Unit soldier2 = new Unit("Рядовой Петров", 80, 3, 3, 20, UnitType.SOLDIER);
        Unit soldier3 = new Unit("Снайпер Сидоров", 70, 2, 6, 35, UnitType.SOLDIER);
        
        Unit alien1 = new Unit("Сектоид", 60, 3, 3, 20, UnitType.ALIEN);
        Unit alien2 = new Unit("Адвент Солдат", 80, 2, 4, 25, UnitType.ALIEN);
        Unit alien3 = new Unit("Командир Пришельцев", 100, 2, 5, 30, UnitType.ALIEN);
        
        // Размещаем юнитов
        field.addUnit(soldier1, 10, 10);
        field.addUnit(soldier2, 12, 10);
        field.addUnit(soldier3, 14, 10);
        
        field.addUnit(alien1, 50, 50);
        field.addUnit(alien2, 52, 50);
        field.addUnit(alien3, 54, 50);
        
        // Добавляем укрытия
        CoverObject wall1 = new CoverObject(new Position(30, 30), CoverType.FULL_COVER, 100);
        CoverObject wall2 = new CoverObject(new Position(35, 35), CoverType.HALF_COVER, 50);
        CoverObject wall3 = new CoverObject(new Position(40, 30), CoverType.LOW_COVER, 30);
        
        field.addCoverObject(wall1, 30, 30);
        field.addCoverObject(wall2, 35, 35);
        field.addCoverObject(wall3, 40, 30);
        
        // Добавляем юнитов в боевой менеджер
        combatManager.addPlayerUnit(soldier1);
        combatManager.addPlayerUnit(soldier2);
        combatManager.addPlayerUnit(soldier3);
        
        combatManager.addEnemyUnit(alien1);
        combatManager.addEnemyUnit(alien2);
        combatManager.addEnemyUnit(alien3);
        
        // Инициализируем действия для всех юнитов
        actionManager.initializeActionsForUnit(soldier1);
        actionManager.initializeActionsForUnit(soldier2);
        actionManager.initializeActionsForUnit(soldier3);
        actionManager.initializeActionsForUnit(alien1);
        actionManager.initializeActionsForUnit(alien2);
        actionManager.initializeActionsForUnit(alien3);
    }
    
    private void setupUI(Stage primaryStage) {
        primaryStage.setTitle("XCOM 2 Style Tactical Combat - Интерактивная демо");
        
        // Основной контейнер
        BorderPane root = new BorderPane();
        root.setFocusTraversable(true); // Позволяем получать фокус
        
        // Левая панель - список юнитов
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(250);
        
        Label unitLabel = new Label("Выберите юнита (TAB - следующий):");
        unitLabel.setStyle("-fx-font-weight: bold;");
        
        unitList = new ListView<>();
        unitList.setPrefHeight(150);
        unitList.setCellFactory(listView -> new UnitListCell());
        unitList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actionManager.selectUnit(newVal);
                updateActionList();
                updateStatus();
                
                // Центрируем камеру на выбранном солдате
                if (visualizer != null) {
                    visualizer.centerCameraOnUnit(newVal);
                }
                
                // Логируем информацию о видимости
                logVisibilityInfo(newVal);
            }
        });
        
        leftPanel.getChildren().addAll(unitLabel, unitList);
        
        // Центральная панель - действия
        VBox centerPanel = new VBox(10);
        centerPanel.setPadding(new Insets(10));
        centerPanel.setPrefWidth(350);
        
        Label actionLabel = new Label("Панель действий (нажмите на кнопку для выполнения):");
        actionLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        // Создаем панель с кнопками действий
        createActionPanel();
        
        HBox buttonPanel = new HBox(10);
        endTurnButton = new Button("Завершить ход");
        endTurnButton.setOnAction(e -> endTurn());
        endTurnButton.setStyle("-fx-font-size: 12px; -fx-padding: 8px 16px;");
        
        Button nextSoldierButton = new Button("Следующий солдат (TAB)");
        nextSoldierButton.setOnAction(e -> selectNextSoldier());
        nextSoldierButton.setStyle("-fx-font-size: 12px; -fx-padding: 8px 16px;");
        
        buttonPanel.getChildren().addAll(endTurnButton, nextSoldierButton);
        
        centerPanel.getChildren().addAll(actionLabel, actionPanel, buttonPanel);
        
        // Правая панель - лог и статус
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setPrefWidth(300);
        
        statusLabel = new Label("Выберите юнита для начала");
        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: blue;");
        
        Label logLabel = new Label("Лог действий:");
        logLabel.setStyle("-fx-font-weight: bold;");
        
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(300);
        logArea.setText("Добро пожаловать в тактический бой!\nВыберите юнита и действие для начала.\n\n");
        
        Button clearLogButton = new Button("Очистить лог");
        clearLogButton.setOnAction(e -> logArea.clear());
        
        rightPanel.getChildren().addAll(statusLabel, logLabel, logArea, clearLogButton);
        
        // Собираем интерфейс
        root.setLeft(leftPanel);
        root.setCenter(centerPanel);
        root.setRight(rightPanel);
        
        Scene scene = new Scene(root, 1000, 650);
        
        // Добавляем обработчик клавиш
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume(); // Предотвращаем стандартное поведение TAB
                selectNextSoldier();
                System.out.println("TAB pressed - switching soldier"); // Отладка
            }
        });
        
        // Дублируем обработчик на корневом элементе для надежности
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                selectNextSoldier();
                System.out.println("TAB pressed on root - switching soldier"); // Отладка
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Запрашиваем фокус для Scene чтобы получать клавиатурные события
        Platform.runLater(() -> {
            scene.getRoot().requestFocus();
        });
    }
    
    private void createActionPanel() {
        actionPanel = new GridPane();
        actionPanel.setHgap(8);
        actionPanel.setVgap(8);
        actionPanel.setPadding(new Insets(10));
        actionPanel.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-radius: 5px;");
        
        actionButtons = new HashMap<>();
        
        // Создаем кнопки для каждого типа действия
        ActionType[] actions = ActionType.values();
        int col = 0, row = 0;
        
        for (ActionType actionType : actions) {
            Button actionButton = new Button();
            actionButton.setText(actionType.getDisplayName());
            actionButton.setPrefSize(110, 40);
            actionButton.setStyle(getActionButtonStyle(actionType));
            
            // Добавляем tooltip с описанием действия
            Tooltip tooltip = new Tooltip(getActionDescription(actionType));
            tooltip.setStyle("-fx-font-size: 12px;");
            actionButton.setTooltip(tooltip);
            
            // Добавляем эффект наведения
            actionButton.setOnMouseEntered(e -> {
                if (!actionButton.isDisabled()) {
                    actionButton.setStyle(getActionButtonStyle(actionType) + " -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 4, 0, 2, 2);");
                }
            });
            
            actionButton.setOnMouseExited(e -> {
                if (!actionButton.isDisabled()) {
                    actionButton.setStyle(getActionButtonStyle(actionType));
                }
            });
            
            // Добавляем обработчик нажатия
            actionButton.setOnAction(e -> executeAction(actionType));
            
            actionButtons.put(actionType, actionButton);
            actionPanel.add(actionButton, col, row);
            
            col++;
            if (col >= 3) { // 3 колонки
                col = 0;
                row++;
            }
        }
    }
    
    private String getActionButtonStyle(ActionType actionType) {
        String baseStyle = "-fx-font-size: 11px; -fx-padding: 5px; -fx-border-radius: 3px; ";
        
        return switch (actionType) {
            case MOVE -> baseStyle + "-fx-background-color: #e3f2fd; -fx-border-color: #2196f3;";
            case ATTACK -> baseStyle + "-fx-background-color: #ffebee; -fx-border-color: #f44336;";
            case OVERWATCH -> baseStyle + "-fx-background-color: #fff3e0; -fx-border-color: #ff9800;";
            case RELOAD -> baseStyle + "-fx-background-color: #f3e5f5; -fx-border-color: #9c27b0;";
            case HEAL -> baseStyle + "-fx-background-color: #e8f5e8; -fx-border-color: #4caf50;";
            case GRENADE -> baseStyle + "-fx-background-color: #fce4ec; -fx-border-color: #e91e63;";
            case SPECIAL_ABILITY -> baseStyle + "-fx-background-color: #e1f5fe; -fx-border-color: #00bcd4;";
            case DEFEND -> baseStyle + "-fx-background-color: #f1f8e9; -fx-border-color: #8bc34a;";
            case DASH -> baseStyle + "-fx-background-color: #fff8e1; -fx-border-color: #ffc107;";
        };
    }
    
    private String getActionDescription(ActionType actionType) {
        return switch (actionType) {
            case MOVE -> "Переместить солдата на новую позицию (1 AP)";
            case ATTACK -> "Атаковать выбранного противника (1 AP)";
            case OVERWATCH -> "Установить режим наблюдения - стрелять по врагам при движении (1 AP)";
            case RELOAD -> "Перезарядить оружие и восстановить боеприпасы (1 AP)";
            case HEAL -> "Использовать аптечку для восстановления здоровья (1 AP)";
            case GRENADE -> "Бросить гранату по выбранной области (1 AP)";
            case SPECIAL_ABILITY -> "Использовать уникальную способность солдата (2 AP)";
            case DEFEND -> "Принять оборонительную позицию для снижения урона (1 AP)";
            case DASH -> "Быстрое движение на большое расстояние (2 AP)";
        };
    }
    
    private void executeAction(ActionType actionType) {
        Unit selectedUnit = actionManager.getSelectedUnit();
        if (selectedUnit == null) {
            addToLog("Выберите солдата для выполнения действия");
            return;
        }
        
        if (!actionManager.isActionAvailable(selectedUnit, actionType)) {
            addToLog("Действие " + actionType.getDisplayName() + " недоступно для " + selectedUnit.getName());
            return;
        }
        
        // Выбираем действие и выполняем его
        actionManager.selectAction(actionType);
        addToLog("🎯 Выполняется действие: " + actionType.getDisplayName() + " для " + selectedUnit.getName());
        executeSelectedAction();
    }
    
    private void launch3DVisualization() {
        // Запускаем 3D визуализацию в отдельном окне
        Platform.runLater(() -> {
            try {
                visualizer = new Combat3DVisualizer(field);
                visualizer.start(new Stage());
                System.out.println("3D Visualizer успешно инициализирован");
            } catch (Exception e) {
                System.err.println("Ошибка при запуске 3D визуализации:");
                e.printStackTrace();
            }
        });
    }
    
    private void updateUI() {
        updateUnitList();
        updateActionList();
        updateStatus();
    }
    
    private void updateUnitList() {
        unitList.getItems().clear();
        List<Unit> playerUnits = combatManager.getPlayerUnitsOptimized();
        unitList.getItems().addAll(playerUnits);
        
        // Автоматически выбираем первого солдате если ничего не выбрано
        if (!playerUnits.isEmpty() && unitList.getSelectionModel().getSelectedItem() == null) {
            unitList.getSelectionModel().select(0);
            
            // Центрируем камеру на первом солдате
            Platform.runLater(() -> {
                if (visualizer != null && !playerUnits.isEmpty()) {
                    visualizer.centerCameraOnUnit(playerUnits.get(0));
                    addToLog("Камера центрирована на " + playerUnits.get(0).getName());
                }
            });
        }
    }
    
    private void updateActionList() {
        Unit selectedUnit = actionManager.getSelectedUnit();
        
        // Обновляем состояние всех кнопок действий
        for (Map.Entry<ActionType, Button> entry : actionButtons.entrySet()) {
            ActionType actionType = entry.getKey();
            Button button = entry.getValue();
            
            boolean isAvailable = selectedUnit != null && 
                                 actionManager.isActionAvailable(selectedUnit, actionType);
            
            button.setDisable(!isAvailable);
            
            // Обновляем текст кнопки с информацией о стоимости
            String buttonText = actionType.getDisplayName() + " (" + actionType.getActionPointCost() + " AP)";
            button.setText(buttonText);
            
            // Обновляем стиль кнопки в зависимости от доступности
            if (isAvailable) {
                button.setStyle(getActionButtonStyle(actionType));
            } else {
                button.setStyle(getActionButtonStyle(actionType) + " -fx-opacity: 0.4;");
            }
        }
    }
    
    private void updateStatus() {
        Unit selectedUnit = actionManager.getSelectedUnit();
        
        // Проверяем автоматическое завершение хода
        if (actionManager.shouldAutoEndTurn()) {
            statusLabel.setText("Ход будет завершен автоматически - у всех солдат закончились очки действий");
            statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: orange;");
            return;
        }
        
        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: blue;"); // Восстанавливаем стандартный стиль
        
        if (selectedUnit == null) {
            statusLabel.setText("Выберите солдата для выполнения действий");
        } else {
            int availableActions = actionManager.getAvailableActionsForUnit(selectedUnit).size();
            int visibleEnemies = field.getVisibleEnemies(selectedUnit).size();
            int attackTargets = actionManager.getValidTargets(selectedUnit).size();
            
            statusLabel.setText(String.format("Выбран: %s (AP: %d/%d) | Действий: %d | Видимость: %d | Видимых врагов: %d | Целей для атаки: %d", 
                selectedUnit.getName(), 
                selectedUnit.getActionPoints(), 
                2,
                availableActions,
                selectedUnit.getViewRange(),
                visibleEnemies,
                attackTargets));
        }
    }
    
    private void executeSelectedAction() {
        Unit selectedUnit = actionManager.getSelectedUnit();
        ActionType selectedAction = actionManager.getSelectedAction();
        
        if (selectedUnit == null || selectedAction == null) {
            addToLog("Ошибка: Не выбран юнит или действие");
            return;
        }
        
        // Простая реализация для демо
        UnitAction action = null;
        
        switch (selectedAction) {
            case MOVE, DASH -> {
                // Для демо - перемещаем на случайную доступную позицию
                List<Position> validPositions = actionManager.getValidMovePositions(selectedUnit);
                if (!validPositions.isEmpty()) {
                    Position randomPos = validPositions.get((int)(Math.random() * validPositions.size()));
                    action = actionManager.executeAction(randomPos);
                    
                    // Обновляем 3D визуализацию
                    if (visualizer != null) {
                        visualizer.updateUnitPosition(selectedUnit, randomPos);
                    }
                }
            }
            case ATTACK -> {
                // Для демо - атакуем случайную доступную цель
                List<Unit> validTargets = actionManager.getValidTargets(selectedUnit);
                if (!validTargets.isEmpty()) {
                    Unit randomTarget = validTargets.get((int)(Math.random() * validTargets.size()));
                    action = actionManager.executeAction(randomTarget);
                    
                    // Показываем анимацию атаки в 3D
                    if (visualizer != null) {
                        visualizer.showAttackAnimation(selectedUnit, randomTarget);
                        visualizer.updateUnitHealth(randomTarget);
                    }
                }
            }
            default -> {
                // Для остальных действий - просто выполняем
                action = actionManager.executeAction((Unit)null, (Position)null);
            }
        }
        
        if (action != null) {
            addToLog(action.toString());
            
            // Проверяем нужно ли автоматически завершить ход
            if (actionManager.shouldAutoEndTurn()) {
                Platform.runLater(() -> {
                    addToLog("=== АВТОМАТИЧЕСКОЕ ЗАВЕРШЕНИЕ ХОДА ===");
                    addToLog("У всех солдат закончились очки действий");
                    endTurn();
                });
                return;
            }
        } else {
            addToLog("Не удалось выполнить действие: " + selectedAction.getDisplayName());
        }
        
        updateUI();
    }
    
    private void endTurn() {
        actionManager.endTurn();
        addToLog("=== ХОД ЗАВЕРШЕН ===");
        addToLog("Очки действия восстановлены для всех юнитов");
        updateUI();
    }
    
    private void addToLog(String message) {
        Platform.runLater(() -> {
            logArea.appendText(message + "\n");
            logArea.setScrollTop(Double.MAX_VALUE);
        });
    }
    
    /**
     * Выбирает следующего солдата в списке (циклически)
     */
    private void selectNextSoldier() {
        System.out.println("selectNextSoldier() called"); // Отладка
        List<Unit> playerUnits = combatManager.getPlayerUnitsOptimized();
        if (playerUnits.isEmpty()) {
            System.out.println("No player units found!"); // Отладка
            return;
        }
        
        // Проверяем автоматическое завершение хода
        if (actionManager.shouldAutoEndTurn()) {
            addToLog("=== АВТОМАТИЧЕСКОЕ ЗАВЕРШЕНИЕ ХОДА ===");
            addToLog("TAB: У всех солдат закончились очки действий - завершаем ход");
            endTurn();
            return;
        }
        
        Unit currentSelected = actionManager.getSelectedUnit();
        int currentIndex = -1;
        
        // Находим текущий выбранный юнит в списке
        if (currentSelected != null) {
            for (int i = 0; i < playerUnits.size(); i++) {
                if (playerUnits.get(i) == currentSelected) {
                    currentIndex = i;
                    break;
                }
            }
        }
        
        // Переходим к следующему солдату (циклически)
        int nextIndex = (currentIndex + 1) % playerUnits.size();
        Unit nextUnit = playerUnits.get(nextIndex);
        
        // Выбираем в списке UI
        unitList.getSelectionModel().select(nextUnit);
        
        // Фокусируемся на выбранном элементе для лучшей видимости
        unitList.scrollTo(nextUnit);
        
        // Центрируем камеру на новом солдате
        if (visualizer != null) {
            visualizer.centerCameraOnUnit(nextUnit);
        }
        
        addToLog("TAB: Переключение на " + nextUnit.getName());
        
        // Логируем информацию о видимости
        logVisibilityInfo(nextUnit);
    }
    
    /**
     * Логирует информацию о видимости для выбранного юнита
     */
    private void logVisibilityInfo(Unit unit) {
        if (unit == null) return;
        
        List<Unit> visibleEnemies = field.getVisibleEnemies(unit);
        List<Unit> validTargets = actionManager.getValidTargets(unit);
        
        addToLog("👁️ " + unit.getName() + " - Диапазон видимости: " + unit.getViewRange() + " клеток");
        
        if (!visibleEnemies.isEmpty()) {
            StringBuilder enemies = new StringBuilder("🔍 Видимые враги: ");
            for (Unit enemy : visibleEnemies) {
                enemies.append(enemy.getName()).append(" ");
            }
            addToLog(enemies.toString());
        } else {
            addToLog("🔍 Враги не обнаружены");
        }
        
        if (!validTargets.isEmpty()) {
            StringBuilder targets = new StringBuilder("🎯 Цели для атаки: ");
            for (Unit target : validTargets) {
                targets.append(target.getName()).append(" ");
            }
            addToLog(targets.toString());
        } else {
            addToLog("🎯 Нет целей в пределах дальности атаки");
        }
    }
    
    // Кастомная ячейка для отображения юнитов
    private static class UnitListCell extends ListCell<Unit> {
        @Override
        protected void updateItem(Unit unit, boolean empty) {
            super.updateItem(unit, empty);
            
            if (empty || unit == null) {
                setText(null);
                setStyle("");
            } else {
                setText(String.format("%s (%s) - HP: %d/%d, AP: %d, Видимость: %d", 
                    unit.getName(),
                    unit.getUnitType(),
                    unit.getCurrentHealth(),
                    unit.getMaxHealth(),
                    unit.getActionPoints(),
                    unit.getViewRange()));
                
                // Цветовая индикация
                if (!unit.isAlive()) {
                    setStyle("-fx-text-fill: red;");
                } else if (unit.getActionPoints() <= 0) {
                    setStyle("-fx-text-fill: gray;");
                } else if (unit.getUnitType() == UnitType.SOLDIER) {
                    setStyle("-fx-text-fill: blue;");
                } else if (unit.getUnitType() == UnitType.ALIEN) {
                    setStyle("-fx-text-fill: darkred;");
                } else {
                    setStyle("-fx-text-fill: green;");
                }
            }
        }
    }
}
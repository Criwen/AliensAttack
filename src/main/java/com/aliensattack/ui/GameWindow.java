package com.aliensattack.ui;

import com.aliensattack.combat.ShootingSystem;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.combat.DefaultCombatManager;
import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.field.TacticalField;
import com.aliensattack.actions.ActionManager;
import com.aliensattack.actions.ActionType;
import com.aliensattack.actions.UnitAction;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Unified game window displaying all XCOM 2 mechanics and actions
 */
@Getter
@Setter
public class GameWindow extends JFrame {
    private static final Logger log = LogManager.getLogger(GameWindow.class);
    
    private ITacticalField tacticalField;
    private DefaultCombatManager combatManager;
    private ShootingSystem shootingSystem;
    private ActionManager actionManager; // Добавляем ActionManager
    
    // UI Components
    private JTextArea gameLog;
    private JPanel tacticalMapPanel;
    private JPanel unitInfoPanel;
    private JPanel actionPanel;
    private JPanel mechanicsPanel;
    private JPanel shootingPanel;
    
    // Views
    private com.aliensattack.ui.panels.TacticalMapView tacticalMapView;
    private com.aliensattack.ui.panels.UnitInfoPanelView unitInfoPanelView;
    private com.aliensattack.ui.panels.ActionPanelView actionPanelView;
    
    // Game State
    private List<Unit> units;
    private Unit selectedUnit;
    private int currentTurn;
    private boolean isWaitingForTargetPosition; // Флаг ожидания выбора позиции
    private List<Position> highlightedPositions; // Подсвеченные позиции для движения
    private boolean isHighlightingMovePositions; // Флаг подсветки позиций движения
    private boolean isWaitingForGrenadeTarget; // ожидание выбора точки броска гранаты
    private Position grenadePreviewCenter; // центр предпросмотра AOE
    private int grenadePreviewRadius; // радиус предпросмотра AOE
    
    public GameWindow() {
        initializeWindow();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        initializeGame();
    }
    
    private void initializeWindow() {
        setTitle("Aliens Attack - XCOM 2 Tactical Combat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    private void initializeComponents() {
        // Game log for displaying all actions
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gameLog.setBackground(Color.BLACK);
        gameLog.setForeground(Color.GREEN);
        
        // Tactical map panel
        tacticalMapPanel = new JPanel();
        tacticalMapPanel.setBorder(BorderFactory.createTitledBorder("Tactical Map"));
        tacticalMapPanel.setPreferredSize(new Dimension(600, 400));
        
        // Unit information panel
        unitInfoPanel = new JPanel();
        unitInfoPanel.setBorder(BorderFactory.createTitledBorder("Unit Information"));
        unitInfoPanel.setLayout(new BoxLayout(unitInfoPanel, BoxLayout.Y_AXIS));
        
        // Action panel
        actionPanel = new JPanel();
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        actionPanel.setLayout(new GridLayout(0, 2, 5, 5));
        
        // Mechanics panel
        mechanicsPanel = new JPanel();
        mechanicsPanel.setBorder(BorderFactory.createTitledBorder("XCOM 2 Mechanics"));
        mechanicsPanel.setLayout(new BoxLayout(mechanicsPanel, BoxLayout.Y_AXIS));
        
        // Shooting panel
        shootingPanel = new JPanel();
        shootingPanel.setBorder(BorderFactory.createTitledBorder("Система Стрельбы"));
        shootingPanel.setLayout(new BoxLayout(shootingPanel, BoxLayout.Y_AXIS));

        // Views initialization
        tacticalMapView = new com.aliensattack.ui.panels.TacticalMapView(tacticalMapPanel, 10, 10);
        unitInfoPanelView = new com.aliensattack.ui.panels.UnitInfoPanelView(unitInfoPanel);
        actionPanelView = new com.aliensattack.ui.panels.ActionPanelView(actionPanel, null);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Main content area
        JPanel mainContent = new JPanel(new BorderLayout());
        
        // Left panel - Tactical map and unit info
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(tacticalMapPanel, BorderLayout.CENTER);
        leftPanel.add(unitInfoPanel, BorderLayout.SOUTH);
        
        // Right panel - Actions, mechanics and shooting
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(actionPanel, BorderLayout.CENTER);
        
        // Bottom panel for mechanics and shooting
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(mechanicsPanel, BorderLayout.WEST);
        bottomPanel.add(shootingPanel, BorderLayout.EAST);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        mainContent.add(leftPanel, BorderLayout.CENTER);
        mainContent.add(rightPanel, BorderLayout.EAST);
        
        // Bottom panel - Game log
        JScrollPane logScrollPane = new JScrollPane(gameLog);
        logScrollPane.setPreferredSize(new Dimension(1400, 200));
        
        add(mainContent, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        // Add window listener for cleanup
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cleanup();
            }
        });
    }
    
    private void initializeGame() {
        // Initialize tactical field
        tacticalField = new TacticalField(10, 10);
        
        // Initialize combat manager
        combatManager = new DefaultCombatManager((TacticalField) tacticalField);
        
        // Initialize action manager
        actionManager = new ActionManager(tacticalField, combatManager);
        
        // Initialize shooting system
        shootingSystem = new ShootingSystem(tacticalField, combatManager);
        
        // Initialize turn and units list
        currentTurn = 1;
        units = new ArrayList<>();
        highlightedPositions = new ArrayList<>();
        isHighlightingMovePositions = false;
        isWaitingForGrenadeTarget = false;
        grenadePreviewCenter = null;
        grenadePreviewRadius = 0;
        
        // Create sample units
        createSampleUnits();
        
        // Update UI
        updateTacticalMap();
        updateUnitInfo();
        updateActionPanel();
        updateMechanicsPanel();
        updateShootingPanel();
        
        logMessage("=== ALIENS ATTACK - XCOM 2 TACTICAL COMBAT ===");
        logMessage("Turn " + currentTurn + " started");
        logMessage("Select a unit and choose an action");
    }
    
    private void createSampleUnits() {
        // Create sample soldiers
        Unit soldier1 = new Unit("Ranger", 100, 3, 4, 15, UnitType.SOLDIER);
        soldier1.setSoldierClass(SoldierClass.RANGER);
        soldier1.setWeapon(new Weapon("Assault Rifle", WeaponType.RIFLE, 8, 12, 10, 75, 30));
        soldier1.setPosition(new Position(2, 2));
        
        // Добавляем гранаты и медикаменты
        soldier1.addExplosive(new Explosive("Frag Grenade", ExplosiveType.GRENADE, 25, 3, 0));
        soldier1.addExplosive(new Explosive("Flashbang", ExplosiveType.FLASHBANG, 0, 2, 0));
        soldier1.addAbility(new SoldierAbility("Medikit", "Medical treatment device", 1, 0)); // Медикамент
        
        // Создаем второго солдата с пистолетом для демонстрации
        Unit soldier4 = new Unit("Pistol Soldier", 80, 2, 3, 10, UnitType.SOLDIER);
        soldier4.setSoldierClass(SoldierClass.RANGER);
        Weapon pistol = new Weapon("Pistol", WeaponType.PISTOL, 4, 6, 5, 65, 8);
        soldier4.setWeapon(pistol);
        soldier4.setPosition(new Position(3, 3));
        
        // Добавляем гранаты и медикаменты
        soldier4.addExplosive(new Explosive("Smoke Grenade", ExplosiveType.SMOKE_GRENADE, 0, 2, 0));
        soldier4.addAbility(new SoldierAbility("Medikit", "Medical treatment device", 1, 0));
        
        Unit soldier2 = new Unit("Sniper", 80, 2, 6, 20, UnitType.SOLDIER);
        soldier2.setSoldierClass(SoldierClass.SHARPSHOOTER);
        soldier2.setWeapon(new Weapon("Sniper Rifle", WeaponType.SNIPER_RIFLE, 12, 15, 8, 85, 5));
        soldier2.setPosition(new Position(5, 5, 2)); // Height advantage
        
        // Добавляем гранаты и медикаменты
        soldier2.addExplosive(new Explosive("Acid Grenade", ExplosiveType.ACID_GRENADE, 20, 2, 0));
        soldier2.addAbility(new SoldierAbility("Medikit", "Medical treatment device", 1, 0));
        
        Unit soldier3 = new Unit("Grenadier", 120, 2, 3, 12, UnitType.SOLDIER);
        soldier3.setSoldierClass(SoldierClass.HEAVY);
        soldier3.setWeapon(new Weapon("Grenade Launcher", WeaponType.GRENADE_LAUNCHER, 15, 20, 10, 70, 12));
        soldier3.setPosition(new Position(1, 1));
        
        // Добавляем гранаты и медикаменты (Grenadier имеет больше гранат)
        soldier3.addExplosive(new Explosive("Frag Grenade", ExplosiveType.GRENADE, 25, 3, 0));
        soldier3.addExplosive(new Explosive("Fire Grenade", ExplosiveType.FIRE_GRENADE, 30, 2, 0));
        soldier3.addExplosive(new Explosive("Poison Grenade", ExplosiveType.POISON_GRENADE, 15, 2, 0));
        soldier3.addAbility(new SoldierAbility("Medikit", "Medical treatment device", 1, 0));
        
        Unit alien1 = new Unit("Sectoid", 60, 3, 3, 10, UnitType.ALIEN);
        alien1.setWeapon(new Weapon("Plasma Rifle", WeaponType.PLASMA_RIFLE, 12, 18, 15, 80, 25));
        alien1.setPosition(new Position(8, 8));
        
        // Создаем второго врага с лазерным оружием
        Unit alien2 = new Unit("Advent Trooper", 70, 2, 4, 12, UnitType.ALIEN);
        alien2.setWeapon(new Weapon("Laser Rifle", WeaponType.LASER_RIFLE, 10, 16, 12, 85, 20));
        alien2.setPosition(new Position(7, 7));
        
        // Add units to tactical field
        tacticalField.addUnit(soldier1);
        tacticalField.addUnit(soldier2);
        tacticalField.addUnit(soldier3);
        tacticalField.addUnit(soldier4);
        tacticalField.addUnit(alien1);
        tacticalField.addUnit(alien2);
        
        // Инициализируем действия для всех юнитов после их добавления в поле
        actionManager.initializeActionsForUnit(soldier1);
        actionManager.initializeActionsForUnit(soldier2);
        actionManager.initializeActionsForUnit(soldier3);
        actionManager.initializeActionsForUnit(soldier4);
        actionManager.initializeActionsForUnit(alien1);
        actionManager.initializeActionsForUnit(alien2);
        
        units.add(soldier1);
        units.add(soldier2);
        units.add(soldier3);
        units.add(soldier4);
        units.add(alien1);
        units.add(alien2);
        
        selectedUnit = soldier1;
    }
    
    private void updateTacticalMap() {
        log.debug("Updating tactical map");
        tacticalMapView.setOnTileClick((x, y) -> handleTileClick(x, y));
        tacticalMapView.render(highlightedPositions, isHighlightingMovePositions,
                isWaitingForGrenadeTarget, grenadePreviewCenter, grenadePreviewRadius, units);
        checkAutoEndTurn();
    }
    
    // createTileButton moved to TacticalMapView
    
    private Unit getUnitAtPosition(int x, int y) {
        // Используем тактическое поле для получения юнита
        Unit unit = tacticalField.getUnitAt(x, y);
        if (unit != null) {
            log.debug("Found unit {} at ({}, {})", unit.getName(), x, y);
        }
        return unit;
    }
    
    private void handleTileClick(int x, int y) {
        try {
            logMessage("=== КЛИК ПО ПОЗИЦИИ (" + x + ", " + y + ") ===");
            logMessage("Режим движения: " + (isWaitingForTargetPosition ? "ДА" : "НЕТ"));
            logMessage("Режим гранаты: " + (isWaitingForGrenadeTarget ? "ДА" : "НЕТ"));
            logMessage("Выбранный юнит: " + (selectedUnit != null ? selectedUnit.getName() : "НЕТ"));
            logMessage("Количество подсвеченных позиций: " + highlightedPositions.size());
            
            log.debug("Click at ({}, {}) - move mode: {}, grenade mode: {}, highlighted count: {}", 
                     x, y, isWaitingForTargetPosition, isWaitingForGrenadeTarget, highlightedPositions.size());
            
            // Дополнительная отладочная информация для режима гранаты
            if (isWaitingForGrenadeTarget) {
                log.debug("Grenade mode active - selectedUnit: {}, explosives: {}", 
                         selectedUnit != null ? selectedUnit.getName() : "null",
                         selectedUnit != null && selectedUnit.getExplosives() != null ? selectedUnit.getExplosives().size() : 0);
                log.debug("Highlighted positions: {}", highlightedPositions);
            }
            
            Unit unitAtPosition = getUnitAtPosition(x, y);
            Position clickedPosition = new Position(x, y);
            
            if (unitAtPosition != null) {
                // Выбираем юнита
                try {
                    selectedUnit = unitAtPosition;
                    actionManager.selectUnit(selectedUnit);
                    isWaitingForTargetPosition = false;
                    
                    // Очищаем подсветку при выборе нового юнита, но сохраняем режим гранаты если он активен
                    boolean wasWaitingForGrenade = isWaitingForGrenadeTarget;
                    clearHighlighting();
                    if (wasWaitingForGrenade) {
                        isWaitingForGrenadeTarget = true;
                        highlightGrenadePositions();
                    }
                    
                    updateUnitInfo();
                    logMessage("Selected unit: " + unitAtPosition.getName());
                } catch (Exception e) {
                    System.err.println("Error selecting unit: " + e.getMessage());
                    e.printStackTrace();
                    logMessage("Error selecting unit: " + e.getMessage());
                }
            } else if (isWaitingForTargetPosition && selectedUnit != null) {
                // Выбираем целевую позицию для движения
                if (highlightedPositions.contains(clickedPosition)) {
                    log.debug("Valid move position clicked: {}", clickedPosition);
                    
                    // Устанавливаем действие MOVE в ActionManager ПЕРЕД выполнением
                    log.debug("Selecting MOVE action");
                    actionManager.selectAction(ActionType.MOVE);
                    log.debug("Setting target position: {}", clickedPosition);
                    actionManager.selectTargetPosition(clickedPosition);
                    log.debug("Target position set");
                    int moveCost = actionManager.calculateMoveActionCost(selectedUnit, clickedPosition);
                    logMessage("Target position selected: (" + x + ", " + y + ") - Cost: " + moveCost + " AP");
                    
                    // Выполняем движение
                    UnitAction moveAction = actionManager.executeAction(clickedPosition);
                    log.debug("executeAction returned: {}", (moveAction != null ? "UnitAction" : "null"));
                    if (moveAction != null) {
                        log.debug("moveAction.isSuccessful(): {}", moveAction.isSuccessful());
                        log.debug("moveAction.getResult(): {}", moveAction.getResult());
                    }
                    
                    if (moveAction != null && moveAction.isSuccessful()) {
                        logMessage(moveAction.getResult());
                        log.debug("Movement successful, updating UI");
                        updateTacticalMap();
                        updateUnitInfo();
                        updateActionPanel(); // Обновляем панель действий после траты AP
                        
                        // Если у юнита остались AP, снова подсвечиваем доступные позиции для движения
                        if (selectedUnit.getActionPoints() > 0) {
                            log.debug("AP remaining: {}, highlighting new positions", selectedUnit.getActionPoints());
                            highlightMovePositions();
                        } else {
                            clearHighlighting(); // Очищаем подсветку только если AP закончились
                            // Автоматически выбираем следующего солдата с AP
                            onMovementCompleted();
                        }
                    } else {
                        String errorMsg = moveAction != null ? moveAction.getResult() : "ActionManager.executeAction() returned null";
                        logMessage("Movement failed: " + errorMsg);
                        log.warn("Movement failed - {}", errorMsg);
                    }
                    
                    isWaitingForTargetPosition = false;
                } else {
                    logMessage("Cannot move to position (" + x + ", " + y + ") - Position not highlighted");
                }
            } else if (isWaitingForGrenadeTarget && selectedUnit != null) {
                // Бросок гранаты по выбранной позиции
                logMessage("=== РЕЖИМ ГРАНАТЫ АКТИВЕН ===");
                logMessage("Клик по позиции (" + x + ", " + y + ")");
                
                Position unitPos = selectedUnit.getPosition();
                Position targetPosition = new Position(x, y, unitPos.getHeight());
                log.debug("Grenade target selected at: {} for unit: {} (unit height: {})", targetPosition, selectedUnit.getName(), unitPos.getHeight());
                
                // Проверяем, что клик был по подсвеченной позиции
                log.debug("Checking if target position {} is in highlighted positions: {}", targetPosition, highlightedPositions);
                log.debug("Highlighted positions count: {}", highlightedPositions.size());
                
                // Упрощенная проверка - ищем позицию по координатам
                boolean foundInHighlighted = false;
                for (Position pos : highlightedPositions) {
                    log.debug("Checking position: {} against target ({}, {})", pos, x, y);
                    if (pos.getX() == x && pos.getY() == y) {
                        foundInHighlighted = true;
                        log.debug("Found matching position: {} at ({}, {})", pos, x, y);
                        break;
                    }
                }
                
                if (!foundInHighlighted) {
                    logMessage("Клик не по подсвеченной позиции для броска гранаты!");
                    log.debug("Target position ({}, {}) not found in highlighted positions", x, y);
                    log.debug("Highlighted positions: {}", highlightedPositions);
                    return;
                }
                
                // Гранаты можно бросать в любую позицию в радиусе, включая позиции с врагами
                log.debug("Grenade target validation passed - grenades can target enemy positions");
                
                logMessage("Обрабатываем бросок гранаты в позицию (" + x + ", " + y + ")");
                
                List<Explosive> explosives = selectedUnit.getExplosives();
                if (explosives != null && !explosives.isEmpty()) {
                    Explosive explosive = explosives.get(0);
                    
                    // Проверяем, что позиция в радиусе броска (используем Manhattan distance для консистентности)
                    int distance = Math.abs(unitPos.getX() - x) + Math.abs(unitPos.getY() - y);
                    logMessage("Расстояние до цели: " + distance + ", радиус гранаты: " + explosive.getRadius());
                    
                    if (distance > explosive.getRadius()) {
                        logMessage("Цель слишком далеко! Радиус гранаты: " + explosive.getRadius() + ", расстояние: " + distance);
                        return;
                    }
                    
                    // Бросаем гранату
                    logMessage(selectedUnit.getName() + " бросает " + explosive.getName() + " в позицию (" + x + ", " + y + ")");
                    
                    // Наносим урон по области вокруг выбранной позиции
                    int grenadeRadius = explosive.getRadius();
                    int damage = explosive.getDamage();
                    List<CombatResult> results = new ArrayList<>();
                    
                    // Проверяем всех юнитов в радиусе гранаты
                    for (Unit unit : units) {
                        if (unit.isAlive() && unit.getUnitType() == UnitType.ALIEN) {
                            Position enemyPos = unit.getPosition();
                            int enemyDistance = Math.abs(enemyPos.getX() - x) + Math.abs(enemyPos.getY() - y);
                            
                            if (enemyDistance <= grenadeRadius) {
                                // Враг в радиусе гранаты - наносим урон
                                boolean killed = unit.takeDamage(damage);
                                String message = killed ? 
                                    unit.getName() + " уничтожен гранатой!" : 
                                    unit.getName() + " получил " + damage + " урона от гранаты!";
                                
                                results.add(new CombatResult(true, damage, message));
                                logMessage(message);
                            }
                        }
                    }
                    
                    // Если никого не задели, все равно считаем бросок успешным
                    if (results.isEmpty()) {
                        results.add(new CombatResult(true, 0, "Граната взорвалась, но никого не задела"));
                        logMessage("Граната взорвалась, но никого не задела");
                    }
                    
                    // Тратим очки действия и удаляем гранату
                    selectedUnit.spendActionPoint();
                    selectedUnit.removeExplosive(explosive);
                    logMessage(selectedUnit.getName() + " потратил 1 AP на бросок гранаты");
                    logMessage("Граната " + explosive.getName() + " удалена из инвентаря");
                    
                    // Очищаем состояние
                    isWaitingForGrenadeTarget = false;
                    grenadePreviewCenter = null;
                    grenadePreviewRadius = 0;
                    clearHighlighting();
                    
                    // Обновляем UI
                    updateTacticalMap();
                    updateUnitInfo();
                    updateActionPanel();
                    
                    // Автоматически выбираем следующего солдата с AP только если у текущего не осталось AP
                    if (selectedUnit.getActionPoints() <= 0) {
                        onMovementCompleted();
                    }
                } else {
                    log.warn("No explosives available for {}", selectedUnit.getName());
                    logMessage("Нет доступных гранат у " + selectedUnit.getName());
                }
            } else {
                logMessage("Empty tile clicked at (" + x + ", " + y + ")");
            }
        } catch (Exception e) {
            System.err.println("Error in handleTileClick: " + e.getMessage());
            e.printStackTrace();
            logMessage("Error handling tile click: " + e.getMessage());
        }
    }
    
    private void highlightMovePositions() {
        if (selectedUnit != null && selectedUnit.isAlive() && selectedUnit.canMove()) {
            // Don't clear if we're in grenade mode
            if (!isWaitingForGrenadeTarget) {
                highlightedPositions.clear();
            }
            isHighlightingMovePositions = true;
            
            // Получаем все доступные позиции для движения
            List<Position> validPositions = actionManager.getValidMovePositions(selectedUnit);
            log.debug("Found {} valid positions for {}", validPositions.size(), selectedUnit.getName());
            
            // Фильтруем позиции по доступным очкам действия
            for (Position pos : validPositions) {
                int moveCost = actionManager.calculateMoveActionCost(selectedUnit, pos);
                if (selectedUnit.getActionPoints() >= moveCost) {
                    highlightedPositions.add(pos);
                    log.trace("Added position {} (cost: {} AP)", pos, moveCost);
                }
            }
            
            updateTacticalMap();
            logMessage("Highlighted " + highlightedPositions.size() + " valid move positions for " + selectedUnit.getName());
            
            // Проверяем автоматическое завершение хода после подсветки позиций для движения
            checkAutoEndTurn();
        }
    }
    
    private void clearHighlighting() {
        try {
            log.debug("Clearing highlighting - current state: move={}, grenade={}", isHighlightingMovePositions, isWaitingForGrenadeTarget);
            
            // Очищаем все подсветки
            highlightedPositions.clear();
            isHighlightingMovePositions = false;
            isWaitingForGrenadeTarget = false;
            grenadePreviewCenter = null;
            grenadePreviewRadius = 0;
            updateTacticalMap();
            
            // Проверяем автоматическое завершение хода после очистки подсветки
            checkAutoEndTurn();
        } catch (Exception e) {
            System.err.println("Error clearing highlighting: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Highlight valid grenade throw positions
     */
    private void highlightGrenadePositions() {
        logMessage("=== ПОДСВЕТКА ПОЗИЦИЙ ДЛЯ ГРАНАТЫ ===");
        
        if (selectedUnit != null && selectedUnit.isAlive()) {
            List<Explosive> explosives = selectedUnit.getExplosives();
            if (explosives != null && !explosives.isEmpty()) {
                Explosive explosive = explosives.get(0);
                int grenadeRange = explosive.getRadius();
                log.debug("Using explosive: {} with radius: {}", explosive.getName(), grenadeRange);
                logMessage("Граната: " + explosive.getName() + " (радиус: " + grenadeRange + ")");
                
                // Очищаем предыдущие подсвеченные позиции
                highlightedPositions.clear();
                log.debug("Cleared previous highlighted positions");
                logMessage("Очищены предыдущие позиции");
                
                // Подсвечиваем все позиции в радиусе броска гранаты, включая позиции с врагами
                Position unitPos = selectedUnit.getPosition();
                log.debug("Unit position: {} (x={}, y={})", unitPos, unitPos.getX(), unitPos.getY());
                logMessage("Позиция юнита: (" + unitPos.getX() + ", " + unitPos.getY() + ")");
                
                for (int dx = -grenadeRange; dx <= grenadeRange; dx++) {
                    for (int dy = -grenadeRange; dy <= grenadeRange; dy++) {
                        if (Math.abs(dx) + Math.abs(dy) <= grenadeRange) {
                            int newX = unitPos.getX() + dx;
                            int newY = unitPos.getY() + dy;
                            
                            // Исключаем позицию самого солдата
                            if (newX == unitPos.getX() && newY == unitPos.getY()) {
                                log.trace("Skipping unit's own position: ({}, {})", newX, newY);
                                continue;
                            }
                            
                            if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10) {
                                // Create position with same height as unit for consistency
                                Position pos = new Position(newX, newY, unitPos.getHeight());
                                
                                // Добавляем все позиции в радиусе - гранаты могут бросаться в врагов
                                highlightedPositions.add(pos);
                                log.debug("Added grenade position: {} (x={}, y={}, height={})", pos, newX, newY, unitPos.getHeight());
                            } else {
                                log.trace("Position ({}, {}) out of bounds, skipping", newX, newY);
                            }
                        }
                    }
                }
                
                log.debug("Total highlighted positions: {}", highlightedPositions.size());
                logMessage("Всего подсвечено позиций: " + highlightedPositions.size());
                updateTacticalMap();
                logMessage("Подсвечены " + highlightedPositions.size() + " позиций для броска гранаты (радиус: " + grenadeRange + ")");
                log.debug("Подсвеченные позиции: {}", highlightedPositions);
                
                // Проверяем автоматическое завершение хода после подсветки позиций для гранаты
                checkAutoEndTurn();
            } else {
                log.warn("No explosives found for unit: {}", selectedUnit.getName());
                logMessage("ОШИБКА: У юнита нет гранат!");
            }
        } else {
            log.warn("Cannot highlight grenade positions - unit is null or not alive");
            logMessage("ОШИБКА: Юнит недоступен!");
        }
    }
    
    private void updateUnitInfo() {
        unitInfoPanelView.render(selectedUnit);
            checkAutoEndTurn();
    }
    
    // addUnitInfoLabel moved to UnitInfoPanelView
    
    private void updateActionPanel() {
        try {
            actionPanel.removeAll();
            
            if (selectedUnit != null) {
                try {
                    // Basic actions - always available if unit is alive
                    addActionButtonIfAvailable("Move", e -> handleMoveAction(), selectedUnit.canPerformMove());
                    addActionButtonIfAvailable("Attack", e -> handleAttackAction(), selectedUnit.canPerformAttack());
                    addActionButtonIfAvailable("Overwatch", e -> handleOverwatchAction(), selectedUnit.canPerformOverwatch());
                    
                    // End Turn button - only available when no soldiers have action points
                    boolean canEndTurn = !hasSoldiersWithActionPoints();
                    addActionButtonIfAvailable("End Turn", e -> handleEndTurnAction(), canEndTurn);
                    
                    // Special abilities - check if unit has them
                    addActionButtonIfAvailable("Conceal", e -> handleConcealAction(), selectedUnit.canPerformConceal());
                    addActionButtonIfAvailable("Suppress", e -> handleSuppressAction(), selectedUnit.canPerformSuppression());
                    addActionButtonIfAvailable("Grenade", e -> handleGrenadeAction(), selectedUnit.canPerformGrenade());
                    addActionButtonIfAvailable("Medikit", e -> handleMedikitAction(), selectedUnit.canPerformMedikit());
                    addActionButtonIfAvailable("Bladestorm", e -> handleBladestormAction(), selectedUnit.canPerformBladestorm());
                    addActionButtonIfAvailable("Rapid Fire", e -> handleRapidFireAction(), selectedUnit.canPerformRapidFire());
                    addActionButtonIfAvailable("Bluescreen", e -> handleBluescreenAction(), selectedUnit.canPerformBluescreen());
                    addActionButtonIfAvailable("Volatile Mix", e -> handleVolatileMixAction(), selectedUnit.canPerformVolatileMix());
                    addActionButtonIfAvailable("Reload", e -> handleReloadAction(), selectedUnit.canPerformReload());
                    addActionButtonIfAvailable("Defend", e -> handleDefendAction(), selectedUnit.canPerformDefend());
                    addActionButtonIfAvailable("Special Ability", e -> handleSpecialAbilityAction(), selectedUnit.canPerformSpecialAbility());
                } catch (Exception e) {
                    System.err.println("Error updating action panel: " + e.getMessage());
                    e.printStackTrace();
                    addActionButton("Error: Could not load actions", event -> logMessage("Action panel error occurred"));
                }
            }
            
            actionPanel.revalidate();
            actionPanel.repaint();
            
            // Проверяем автоматическое завершение хода после обновления панели
            checkAutoEndTurn();
        } catch (Exception e) {
            System.err.println("Critical error in updateActionPanel: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void addActionButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        actionPanel.add(button);
    }
    
    private void addActionButtonIfAvailable(String text, ActionListener listener, boolean isAvailable) {
        if (isAvailable) {
            JButton button = new JButton(text);
            button.addActionListener(listener);
            button.setEnabled(true);
            actionPanel.add(button);
        } else {
            // Добавляем неактивную кнопку для визуального отображения
            JButton button = new JButton(text + " (N/A)");
            button.setEnabled(false);
            button.setBackground(Color.LIGHT_GRAY);
            actionPanel.add(button);
        }
    }
    
    /**
     * Check if any soldiers have action points remaining
     */
    private boolean hasSoldiersWithActionPoints() {
        for (Unit unit : units) {
            if (unit.getUnitType() == UnitType.SOLDIER && unit.isAlive() && unit.getActionPoints() > 0) {
                return true;
            }
        }
        return false;
    }
    
    private void updateMechanicsPanel() {
        mechanicsPanel.removeAll();
        
        addMechanicsLabel("=== XCOM 2 MECHANICS STATUS ===");
        addMechanicsLabel("Concealment System: Active");
        addMechanicsLabel("Flanking Mechanics: Active");
        addMechanicsLabel("Suppression Fire: Active");
        addMechanicsLabel("Destructible Environment: Active");
        addMechanicsLabel("Squad Cohesion: Active");
        addMechanicsLabel("Psionic Combat: Active");
        addMechanicsLabel("Environmental Hazards: Active");
        addMechanicsLabel("Squad Sight: Active");
        addMechanicsLabel("Hacking/Technical: Active");
        addMechanicsLabel("Concealment Breaks: Active");
        addMechanicsLabel("Overwatch Ambush: Active");
        addMechanicsLabel("Height Advantage: Active");
        addMechanicsLabel("Grenade Launcher: Active");
        addMechanicsLabel("Medikit System: Active");
        addMechanicsLabel("Ammo Types: Active");
        addMechanicsLabel("Bladestorm: Active");
        addMechanicsLabel("Bluescreen Protocol: Active");
        addMechanicsLabel("Volatile Mix: Active");
        addMechanicsLabel("Rapid Fire: Active");
        addMechanicsLabel("Deep Cover: Active");
        
        mechanicsPanel.revalidate();
        mechanicsPanel.repaint();
        
        // Проверяем автоматическое завершение хода после обновления панели механик
        checkAutoEndTurn();
    }
    
    private void addMechanicsLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 10));
        label.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
        mechanicsPanel.add(label);
    }
    
    private void updateShootingPanel() {
        shootingPanel.removeAll();
        
        // Status label
        JLabel statusLabel = new JLabel(shootingSystem.getShootingStatus());
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        shootingPanel.add(statusLabel);
        
        // Weapon selection
        if (shootingSystem.getSelectedShooter() != null) {
            List<Weapon> availableWeapons = shootingSystem.getAvailableWeapons();
            if (!availableWeapons.isEmpty()) {
                JLabel weaponLabel = new JLabel("Доступное оружие:");
                weaponLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 5));
                shootingPanel.add(weaponLabel);
                
                for (Weapon weapon : availableWeapons) {
                    JButton weaponButton = new JButton(weapon.getName() + " (" + weapon.getCurrentAmmo() + "/" + weapon.getAmmoCapacity() + ")");
                    weaponButton.addActionListener(e -> {
                        if (shootingSystem.selectWeapon(weapon)) {
                            logMessage("Выбрано оружие: " + weapon.getName());
                            updateShootingPanel();
                        }
                    });
                    weaponButton.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                    shootingPanel.add(weaponButton);
                }
            }
            
            // Target selection
            List<Unit> visibleEnemies = shootingSystem.getVisibleEnemies();
            if (!visibleEnemies.isEmpty()) {
                JLabel targetLabel = new JLabel("Видимые враги:");
                targetLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 5));
                shootingPanel.add(targetLabel);
                
                for (Unit enemy : visibleEnemies) {
                    JButton targetButton = new JButton(enemy.getName() + " (HP: " + enemy.getCurrentHealth() + ")");
                    targetButton.addActionListener(e -> {
                        if (shootingSystem.selectTarget(enemy)) {
                            logMessage("Выбрана цель: " + enemy.getName());
                            updateShootingPanel();
                        }
                    });
                    targetButton.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                    shootingPanel.add(targetButton);
                }
            }
            
            // Fire button
            if (shootingSystem.canExecuteShot()) {
                JButton fireButton = new JButton("ОГОНЬ!");
                fireButton.setBackground(Color.RED);
                fireButton.setForeground(Color.WHITE);
                fireButton.setFont(new Font("Arial", Font.BOLD, 12));
                                 fireButton.addActionListener(e -> {
                     ShootingSystem.ShootingResult result = shootingSystem.executeShot();
                     logMessage("Результат выстрела: " + result.getMessage());
                     if (result.isHit()) {
                         logMessage("Нанесен урон: " + result.getDamage());
                     }
                     
                     // Обновляем информацию о юните после выстрела
                     updateUnitInfo();
                     updateShootingPanel();
                     
                     // Если у юнита закончились очки действия, очищаем подсветку
                     if (selectedUnit != null && selectedUnit.getActionPoints() <= 0) {
                         clearHighlighting();
                         
                         // Автоматически выбираем следующего солдата с AP или завершаем ход
                         onMovementCompleted();
                     }
                 });
                fireButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                shootingPanel.add(fireButton);
            }
        }
        
        shootingPanel.revalidate();
        shootingPanel.repaint();
        
        // Проверяем автоматическое завершение хода после обновления панели стрельбы
        checkAutoEndTurn();
    }
    
    // Action handlers
    private void handleMoveAction() {
        if (selectedUnit != null) {
            actionManager.selectAction(ActionType.MOVE);
            isWaitingForTargetPosition = true;
            // Подсвечиваем доступные позиции для движения
            highlightMovePositions();
            logMessage("Select target position for " + selectedUnit.getName() + " to move to");
            logMessage("Available AP: " + selectedUnit.getActionPoints());
            
            // Проверяем автоматическое завершение хода после выбора действия движения
            checkAutoEndTurn();
        }
    }
    
    /**
     * Called after movement is completed to auto-select next soldier
     */
    public void onMovementCompleted() {
        // Проверяем автоматическое завершение хода
        if (!hasSoldiersWithActionPoints()) {
            logMessage("=== АВТОМАТИЧЕСКОЕ ЗАВЕРШЕНИЕ ХОДА ===");
            logMessage("У всех солдат закончились очки действий");
            handleEndTurnAction();
            return;
        }
        
        // Если у текущего юнита нет AP, выбираем следующего
        if (selectedUnit != null && selectedUnit.getActionPoints() <= 0) {
            autoSelectNextSoldier();
        }
    }
    
    /**
     * Auto-select next soldier with action points
     */
    private void autoSelectNextSoldier() {
        if (selectedUnit != null && selectedUnit.getActionPoints() <= 0) {
            // Find next soldier with action points
            for (Unit unit : units) {
                if (unit.getUnitType() == UnitType.SOLDIER && unit.isAlive() && unit.getActionPoints() > 0) {
                    selectedUnit = unit;
                    logMessage("Автоматически выбран: " + unit.getName() + " (AP: " + unit.getActionPoints() + ")");
                    
                    // Очищаем режим гранаты при смене юнита
                    isWaitingForGrenadeTarget = false;
                    grenadePreviewCenter = null;
                    grenadePreviewRadius = 0;
                    
                    updateUnitInfo();
                    updateActionPanel();
                    updateTacticalMap();
                    return;
                }
            }
            
            // Если не нашли солдата с AP - автоматически завершаем ход
            logMessage("=== АВТОМАТИЧЕСКОЕ ЗАВЕРШЕНИЕ ХОДА ===");
            logMessage("У всех солдат закончились очки действий");
            handleEndTurnAction();
        }
    }
    
    private void handleAttackAction() {
        if (selectedUnit != null) {
            clearHighlighting(); // Очищаем подсветку при выборе атаки
            
            // Проверяем, может ли юнит атаковать
            if (selectedUnit.getActionPoints() < 1.0) {
                logMessage("Недостаточно очков действия для атаки. Нужно минимум 1 AP");
                return;
            }
            
            // Select shooter in shooting system
            if (shootingSystem.selectShooter(selectedUnit)) {
                logMessage("Выбран стрелок: " + selectedUnit.getName() + " (AP: " + selectedUnit.getActionPoints() + ")");
                updateShootingPanel();
            } else {
                logMessage("Нельзя выбрать этого солдата для стрельбы");
            }
        }
    }
    
    private void handleOverwatchAction() {
        if (selectedUnit != null) {
            if (selectedUnit.getActionPoints() < 1) {
                logMessage("Недостаточно очков действия для Overwatch. Нужно минимум 1 AP");
                return;
            }
            
            // Тратим все оставшиеся очки действия, но не менее 1 AP
            double allAP = selectedUnit.getActionPoints();
            selectedUnit.spendActionPoints(allAP);
            
            logMessage(selectedUnit.getName() + " goes on overwatch (AP spent: ALL)");
            selectedUnit.setOverwatching(true);
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
            
            // Автоматически выбираем следующего солдата с AP или завершаем ход
            onMovementCompleted();
        }
    }
    
    private void handleEndTurnAction() {
        // Проверяем, есть ли у солдат очки действий
        boolean hasUnitsWithActions = false;
        for (Unit unit : units) {
            if (unit.getUnitType() == UnitType.SOLDIER && unit.isAlive() && unit.getActionPoints() > 0) {
                hasUnitsWithActions = true;
                break;
            }
        }
        
        if (hasUnitsWithActions) {
            logMessage("⚠️ Нельзя завершить ход! У некоторых солдат остались очки действий.");
            return;
        }
        
        currentTurn++;
        logMessage("=== TURN " + currentTurn + " ===");
        logMessage("Ход завершен - все солдаты потратили очки действий");
        
        // Reset action points for all units
        for (Unit unit : units) {
            unit.resetActionPoints();
        }
        
        updateUnitInfo();
        updateActionPanel(); // Обновляем панель действий после сброса AP
    }
    
    private void handleConcealAction() {
        if (selectedUnit != null && selectedUnit.canConceal()) {
            selectedUnit.conceal();
            logMessage(selectedUnit.getName() + " enters concealment");
            updateUnitInfo();
            
            // Проверяем автоматическое завершение хода после использования способности маскировки
            checkAutoEndTurn();
        }
    }
    
    private void handleSuppressAction() {
        if (selectedUnit != null) {
            if (selectedUnit.getActionPoints() < 1) {
                logMessage("Недостаточно очков действия для Suppression. Нужно минимум 1 AP");
                return;
            }
            
            // Тратим все оставшиеся очки действия, но не менее 1 AP
            double allAP = selectedUnit.getActionPoints();
            selectedUnit.spendActionPoints(allAP);
            
            logMessage(selectedUnit.getName() + " uses suppression fire (AP spent: ALL)");
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
            
            // Автоматически выбираем следующего солдата с AP или завершаем ход
            onMovementCompleted();
        }
    }
    
    private void handleGrenadeAction() {
        if (selectedUnit != null) {
            if (selectedUnit.getActionPoints() < 1) {
                logMessage("Недостаточно очков действия для Grenade. Нужно: 1 AP");
                return;
            }

            // Проверяем, есть ли у юнита гранаты
            List<Explosive> explosives = selectedUnit.getExplosives();
            if (explosives == null || explosives.isEmpty()) {
                logMessage("У " + selectedUnit.getName() + " нет гранат!");
                return;
            }

            logMessage("=== АКТИВАЦИЯ РЕЖИМА ГРАНАТЫ ===");
            logMessage("Юнит: " + selectedUnit.getName());
            logMessage("Гранаты: " + explosives.size() + " шт.");
            log.debug("Grenade action triggered for unit: {} with {} explosives", selectedUnit.getName(), explosives.size());
            log.debug("Unit position: {}", selectedUnit.getPosition());

            // Очищаем предыдущую подсветку и входим в режим гранаты
            clearHighlighting();
            isWaitingForGrenadeTarget = true;
            grenadePreviewCenter = null;
            grenadePreviewRadius = 0;
            
            log.debug("Grenade mode flags set: isWaitingForGrenadeTarget={}", isWaitingForGrenadeTarget);
            logMessage("Режим гранаты активирован!");
            
            actionManager.selectAction(ActionType.GRENADE);
            logMessage("Выберите клетку для броска гранаты. Красная подсветка покажет доступные позиции.");
            log.debug("Grenade mode activated for unit: {}", selectedUnit.getName());
            
            // Подсвечиваем доступные позиции для броска гранаты
            highlightGrenadePositions();
            updateTacticalMap();
            
            log.debug("Grenade mode setup completed. Highlighted positions count: {}", highlightedPositions.size());
            logMessage("Подсветка завершена. Позиций: " + highlightedPositions.size());
            
            // Проверяем автоматическое завершение хода после активации режима гранаты
            checkAutoEndTurn();
        }
    }
    
    private void handleMedikitAction() {
        if (selectedUnit != null) {
            if (selectedUnit.getActionPoints() < 1) {
                logMessage("Недостаточно очков действия для Medikit. Нужно: 1 AP");
                return;
            }
            
            logMessage(selectedUnit.getName() + " uses medikit (AP spent: 1)");
            selectedUnit.spendActionPoint();
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
            
            // Автоматически выбираем следующего солдата с AP или завершаем ход
            onMovementCompleted();
        }
    }
    
    private void handleBladestormAction() {
        if (selectedUnit != null) {
            logMessage(selectedUnit.getName() + " activates Bladestorm");
            ReactiveAbility bladestorm = new ReactiveAbility("Bladestorm", ReactiveAbilityType.BLADESTORM, 1);
            selectedUnit.addReactiveAbility(bladestorm);
            updateUnitInfo();
            
            // Проверяем автоматическое завершение хода после активации способности Bladestorm
            checkAutoEndTurn();
        }
    }
    
    private void handleRapidFireAction() {
        if (selectedUnit != null && selectedUnit.getWeapon() != null && selectedUnit.getWeapon().canUseRapidFire()) {
            logMessage(selectedUnit.getName() + " uses Rapid Fire");
            selectedUnit.spendActionPoint();
            
            // Автоматически выбираем следующего солдата с AP или завершаем ход
            onMovementCompleted();
        }
    }
    
    private void handleBluescreenAction() {
        if (selectedUnit != null) {
            logMessage(selectedUnit.getName() + " uses Bluescreen attack");
            selectedUnit.spendActionPoint();
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
            
            // Автоматически выбираем следующего солдата с AP или завершаем ход
            onMovementCompleted();
        }
    }
    
    private void handleVolatileMixAction() {
        if (selectedUnit != null) {
            logMessage(selectedUnit.getName() + " detonates Volatile Mix");
            selectedUnit.spendActionPoint();
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
            
            // Автоматически выбираем следующего солдата с AP или завершаем ход
            onMovementCompleted();
        }
    }
    
    private void handleReloadAction() {
        if (selectedUnit != null) {
            if (selectedUnit.getActionPoints() < 1) {
                logMessage("Недостаточно очков действия для Reload. Нужно: 1 AP");
                return;
            }
            
            logMessage(selectedUnit.getName() + " reloads weapon (AP spent: 1)");
            selectedUnit.spendActionPoint();
            if (selectedUnit.getWeapon() != null) {
                selectedUnit.getWeapon().reload();
            }
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий после перезарядки
            
            // Автоматически выбираем следующего солдата с AP или завершаем ход
            onMovementCompleted();
        }
    }
    
    private void handleDefendAction() {
        if (selectedUnit != null) {
            if (selectedUnit.getActionPoints() < 1) {
                logMessage("Недостаточно очков действия для Defend. Нужно: 1 AP");
                return;
            }
            
            logMessage(selectedUnit.getName() + " takes defensive position (AP spent: 1)");
            selectedUnit.spendActionPoint();
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
            
            // Автоматически выбираем следующего солдата с AP или завершаем ход
            onMovementCompleted();
        }
    }
    
    private void handleSpecialAbilityAction() {
        if (selectedUnit != null) {
            if (selectedUnit.getActionPoints() < 1) {
                logMessage("Недостаточно очков действия для Special Ability. Нужно: 1 AP");
                return;
            }
            
            logMessage(selectedUnit.getName() + " uses special ability (AP spent: 1)");
            selectedUnit.spendActionPoint();
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
            
            // Автоматически выбираем следующего солдата с AP или завершаем ход
            onMovementCompleted();
        }
    }
    
    private void logMessage(String message) {
        gameLog.append("[" + getCurrentTime() + "] " + message + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }
    
    private String getCurrentTime() {
        return String.format("%02d:%02d:%02d", 
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            Calendar.getInstance().get(Calendar.SECOND));
    }
    
    private void cleanup() {
        logMessage("Shutting down XCOM 2 Tactical Combat System...");
        // Cleanup resources
    }
    
    public void showWindow() {
        setVisible(true);
        logMessage("XCOM 2 Tactical Combat System loaded successfully!");
    }

    /**
     * Проверяет нужно ли автоматически завершить ход
     */
    private void checkAutoEndTurn() {
        if (selectedUnit != null && selectedUnit.getActionPoints() <= 0) {
            // Проверяем, есть ли у других солдат очки действий
            if (!hasSoldiersWithActionPoints()) {
                logMessage("=== АВТОМАТИЧЕСКОЕ ЗАВЕРШЕНИЕ ХОДА ===");
                logMessage("У всех солдат закончились очки действий");
                handleEndTurnAction();
                return; // Не выбираем следующего солдата если ход завершен
            } else {
                // Автоматически выбираем следующего солдата с AP
                autoSelectNextSoldier();
            }
        }
    }
    
    // checkAutoEndTurnAndUpdateUI removed as unused
} 
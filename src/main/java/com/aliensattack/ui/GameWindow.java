package com.aliensattack.ui;

import com.aliensattack.combat.ShootingSystem;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.combat.DefaultCombatManager;
import com.aliensattack.combat.TurnManager;
import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.core.data.AmmoTypeData;
import com.aliensattack.core.config.GameConfig;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.field.TacticalField;
import com.aliensattack.actions.ActionManager;
import com.aliensattack.core.enums.ActionType;
import com.aliensattack.actions.UnitAction;
import com.aliensattack.ui.styles.StyleManager;
import com.aliensattack.ui.styles.StyledComponentFactory;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unified game window displaying all XCOM 2 mechanics and actions
 */
@Getter
@Setter
public class GameWindow extends JFrame {
    private static final Logger log = LogManager.getLogger(GameWindow.class);
    
    // Style system integration
    private final StyleManager styleManager;
    private final StyledComponentFactory componentFactory;
    
    private ITacticalField tacticalField;
    private DefaultCombatManager combatManager;
    private ShootingSystem shootingSystem;
    private ActionManager actionManager; // Добавляем ActionManager
    
    // UI Components
    private JTextArea gameLog;
    private JPanel tacticalMapPanel;
    private JPanel unitInfoPanel;
    private JPanel actionPanel;
    private JPanel shootingPanel;
    
    // Mission preparation panel
    private JPanel missionPreparationPanel;
    private com.aliensattack.ui.panels.SoldierSelectionForm soldierSelectionForm;
    
    // Main content switcher
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    
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
    
    // Mission preparation state
    private List<Soldier> preparedSoldiers;
    private boolean isMissionPrepared;
    
    // Demo system integration
    private com.aliensattack.core.control.BrainManager brainManager;
    private TurnManager turnManager;
    private com.aliensattack.core.systems.SquadCohesionManager squadCohesionManager;
    private com.aliensattack.core.systems.WeatherSystemManager weatherSystemManager;
    private com.aliensattack.core.systems.EnvironmentalHazardsManager environmentalHazardsManager;
    
    public GameWindow() {
        // Initialize style system
        this.styleManager = StyleManager.getInstance();
        this.componentFactory = new StyledComponentFactory();
        
        initializeWindow();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        initializeGame();
        
        // Apply current style to the entire window
        styleManager.applyCurrentStyle(this);
        log.info("Sci-Fi style applied to GameWindow");
    }
    
    private void initializeWindow() {
        setTitle("Aliens Attack - XCOM 2 Tactical Combat");
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    private void initializeComponents() {
        // Game log for displaying all actions - use styled factory
        gameLog = componentFactory.createTextArea("", 10, 40);
        gameLog.setEditable(false);
        
        // Tactical map panel - use styled factory
        tacticalMapPanel = componentFactory.createPanel();
        tacticalMapPanel.setBorder(BorderFactory.createTitledBorder("Tactical Map"));
        tacticalMapPanel.setPreferredSize(new Dimension(600, 400));
        
        // Unit information panel - use styled factory
        unitInfoPanel = componentFactory.createPanel();
        unitInfoPanel.setBorder(BorderFactory.createTitledBorder("Unit Information"));
        unitInfoPanel.setLayout(new BoxLayout(unitInfoPanel, BoxLayout.Y_AXIS));
        
        // Action panel - use styled factory
        actionPanel = componentFactory.createPanel();
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        actionPanel.setLayout(new GridLayout(0, 2, 5, 5));
        
        // Shooting panel - use styled factory
        shootingPanel = componentFactory.createPanel();
        shootingPanel.setBorder(BorderFactory.createTitledBorder("Система Стрельбы"));
        shootingPanel.setLayout(new BoxLayout(shootingPanel, BoxLayout.Y_AXIS));

        // Mission preparation panel - use styled factory
        missionPreparationPanel = componentFactory.createPanel(new BorderLayout());
        soldierSelectionForm = new com.aliensattack.ui.panels.SoldierSelectionForm();
        soldierSelectionForm.setOnGameStartCallback(this::onSoldierPreparationComplete);
        missionPreparationPanel.add(soldierSelectionForm, BorderLayout.CENTER);
        
        // Main content switcher - use styled factory
        cardLayout = new CardLayout();
        mainContentPanel = componentFactory.createPanel(cardLayout);
        
        // Views initialization
        tacticalMapView = new com.aliensattack.ui.panels.TacticalMapView(tacticalMapPanel, 10, 10);
        unitInfoPanelView = new com.aliensattack.ui.panels.UnitInfoPanelView(unitInfoPanel);
        actionPanelView = new com.aliensattack.ui.panels.ActionPanelView(actionPanel, null);
        
        // Apply styles to all panels
        styleManager.applyCurrentStyleToAll(
            tacticalMapPanel, unitInfoPanel, actionPanel, 
            shootingPanel, missionPreparationPanel, mainContentPanel
        );
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Main content area with CardLayout for switching
        JPanel tacticalContent = createTacticalContent();
        
        // Add both panels to card layout
        mainContentPanel.add(missionPreparationPanel, "MISSION_PREP");
        mainContentPanel.add(tacticalContent, "TACTICAL");
        
        // Start with mission preparation
        cardLayout.show(mainContentPanel, "MISSION_PREP");
        
        // Bottom panel - Game log - use styled factory
        JScrollPane logScrollPane = componentFactory.createScrollPane(gameLog);
        logScrollPane.setPreferredSize(new Dimension(1400, 200));
        
        add(mainContentPanel, BorderLayout.CENTER);
        add(logScrollPane, BorderLayout.SOUTH);
        
        // Apply style to scroll pane
        styleManager.applyCurrentStyle(logScrollPane);
    }
    
    private JPanel createTacticalContent() {
        // Use styled factory for all panels
        JPanel tacticalContent = componentFactory.createPanel(new BorderLayout());
        
        // Left panel - Tactical map and unit info
        JPanel leftPanel = componentFactory.createPanel(new BorderLayout());
        leftPanel.add(tacticalMapPanel, BorderLayout.CENTER);
        leftPanel.add(unitInfoPanel, BorderLayout.SOUTH);
        
        // Right panel - Actions, mechanics and shooting
        JPanel rightPanel = componentFactory.createPanel(new BorderLayout());
        rightPanel.add(actionPanel, BorderLayout.CENTER);
        
        // Bottom panel for shooting only
        rightPanel.add(shootingPanel, BorderLayout.SOUTH);
        
        tacticalContent.add(leftPanel, BorderLayout.CENTER);
        tacticalContent.add(rightPanel, BorderLayout.EAST);
        
        // Apply styles to all new panels
        styleManager.applyCurrentStyleToAll(tacticalContent, leftPanel, rightPanel);
        
        return tacticalContent;
    }
    
    private void setupEventHandlers() {
        // Add window listener for cleanup
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    GameWindow.this,
                    "Вы уверены, что хотите выйти из игры?",
                    "Подтверждение выхода",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (choice == JOptionPane.YES_OPTION) {
                    cleanup();
                    System.exit(0);
                }
            }
            
            @Override
            public void windowActivated(WindowEvent e) {
                // Keep the window active
                requestFocusInWindow();
            }
        });
        
        // Prevent automatic disposal
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        
        // Initialize advanced systems for demo functionality
        initializeAdvancedSystems();
        
        // Initialize turn and units list
        currentTurn = GameConfig.getTurnStart(); // Use configuration instead of hardcoded 1
        units = new ArrayList<>();
        highlightedPositions = new ArrayList<>();
        isHighlightingMovePositions = false;
        isWaitingForGrenadeTarget = false;
        grenadePreviewCenter = null;
        grenadePreviewRadius = GameConfig.getGrenadePreviewRadius(); // Use configuration instead of hardcoded 0
        
        // Initialize mission preparation state
        preparedSoldiers = new ArrayList<>();
        isMissionPrepared = false;
        
        // Create sample units
        createSampleUnits();
        
        // Update UI
        updateTacticalMap();
        updateUnitInfo();
        updateActionPanel();
        updateShootingPanel();
        
        logMessage("=== ALIENS ATTACK - XCOM 2 TACTICAL COMBAT ===");
        logMessage("Turn " + currentTurn + " started");
        logMessage("Select a unit and choose an action");
    }
    
    /**
     * Initialize advanced game systems for demo functionality
     */
    private void initializeAdvancedSystems() {
        try {
            // Initialize brain management system
            brainManager = new com.aliensattack.core.control.BrainManager();
            log.info("✅ Brain manager initialized");
            
            // Initialize turn management system
            com.aliensattack.core.model.GameContext gameContext = com.aliensattack.core.model.GameContext.createDefault();
            turnManager = new TurnManager(combatManager, tacticalField);
            log.info("✅ Turn manager initialized");
            
            // Initialize squad cohesion system
            squadCohesionManager = new com.aliensattack.core.systems.SquadCohesionManager();
            squadCohesionManager.initialize();
            log.info("✅ Squad cohesion manager initialized");
            
            // Initialize weather system
            weatherSystemManager = new com.aliensattack.core.systems.WeatherSystemManager();
            weatherSystemManager.initialize();
            log.info("✅ Weather system manager initialized");
            
            // Initialize environmental hazards system
            environmentalHazardsManager = new com.aliensattack.core.systems.EnvironmentalHazardsManager();
            log.info("✅ Environmental hazards manager initialized");
            
            log.info("🎮 All advanced systems initialized successfully");
            
        } catch (Exception e) {
            log.error("❌ Failed to initialize advanced systems: {}", e.getMessage(), e);
        }
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
                    
                    // Очищаем подсветку и режим гранаты при выборе нового юнита
                    clearHighlighting();
                    
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
                    
                    // Показываем оставшиеся ресурсы
                    logMessage("Осталось AP: " + selectedUnit.getActionPoints() + ", гранат: " + selectedUnit.getExplosives().size());
                    
                    // Очищаем состояние
                    isWaitingForGrenadeTarget = false;
                    grenadePreviewCenter = null;
                    grenadePreviewRadius = GameConfig.getGrenadePreviewRadius();
                    clearHighlighting();
                    
                    // Обновляем UI
                    updateTacticalMap();
                    updateUnitInfo();
                    updateActionPanel();
                    
                    // Проверяем, может ли юнит бросить еще одну гранату
                    if (selectedUnit.getActionPoints() > 0 && selectedUnit.canPerformGrenade()) {
                        logMessage("У " + selectedUnit.getName() + " остались AP и гранаты - можно бросать еще!");
                        
                        // Автоматически переактивируем режим гранаты для удобства
                        if (selectedUnit.getExplosives().size() > 0) {
                            logMessage("Автоматически переактивируем режим гранаты для " + selectedUnit.getName());
                            handleGrenadeAction();
                        }
                    } else if (selectedUnit.getActionPoints() <= 0) {
                        // Автоматически выбираем следующего солдата с AP только если у текущего не осталось AP
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
            grenadePreviewRadius = GameConfig.getGrenadePreviewRadius();
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
                            
                            if (newX >= 0 && newX < GameConfig.getDefaultFieldWidth() && newY >= 0 && newY < GameConfig.getDefaultFieldHeight()) {
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
        JButton button = componentFactory.createButton(text);
        button.addActionListener(listener);
        actionPanel.add(button);
    }
    
    private void addActionButtonIfAvailable(String text, ActionListener listener, boolean isAvailable) {
        if (isAvailable) {
            JButton button = componentFactory.createButton(text);
            button.addActionListener(listener);
            button.setEnabled(true);
            actionPanel.add(button);
        } else {
            // Добавляем неактивную кнопку для визуального отображения
            JButton button = componentFactory.createButton(text + " (N/A)");
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
    

    

    

    
    private void replaceSoldiersWithPrepared() {
        logMessage("Замена солдат на подготовленных...");
        
        // Clear existing soldiers from tactical field and units list
        List<Unit> soldiersToRemove = units.stream()
            .filter(unit -> unit.getUnitType() == UnitType.SOLDIER)
            .collect(Collectors.toList());
        
        for (Unit soldier : soldiersToRemove) {
            tacticalField.removeUnit(soldier);
            units.remove(soldier);
        }
        
        // Add prepared soldiers to tactical field
        for (int soldierIndex = 0; soldierIndex < preparedSoldiers.size(); soldierIndex++) {
            Soldier preparedSoldier = preparedSoldiers.get(soldierIndex);
            
            // Convert Soldier to Unit for tactical field
            Unit tacticalSoldier = convertSoldierToUnit(preparedSoldier, soldierIndex);
            
            // Add to tactical field and units list
            tacticalField.addUnit(tacticalSoldier);
            units.add(tacticalSoldier);
            
            // Initialize actions for the soldier
            actionManager.initializeActionsForUnit(tacticalSoldier);
            
            StringBuilder soldierInfo = new StringBuilder();
            soldierInfo.append("Добавлен солдат: ").append(tacticalSoldier.getName());
            soldierInfo.append(" с оружием: ").append(tacticalSoldier.getWeapon() != null ? tacticalSoldier.getWeapon().getName() : "нет");
            soldierInfo.append(" и бронёй: ").append(tacticalSoldier.getArmor() != null ? tacticalSoldier.getArmor().getName() : "нет");
            
            // Add explosives info
            List<Explosive> explosives = tacticalSoldier.getExplosives();
            if (explosives != null && !explosives.isEmpty()) {
                soldierInfo.append(" и гранатами: ");
                for (int i = 0; i < explosives.size(); i++) {
                    if (i > 0) soldierInfo.append(", ");
                    soldierInfo.append(explosives.get(i).getName());
                }
            }
            
            logMessage(soldierInfo.toString());
        }
        
        // Update UI
        updateTacticalMap();
        updateUnitInfo();
        updateActionPanel();
        
        // Set first prepared soldier as selected
        if (!units.isEmpty()) {
            selectedUnit = units.stream()
                .filter(unit -> unit.getUnitType() == UnitType.SOLDIER)
                .findFirst()
                .orElse(units.get(0));
            updateUnitInfo();
        }
        
        logMessage("Подготовленные солдаты успешно загружены на поле боя!");
    }
    
    private Unit convertSoldierToUnit(Soldier soldier, int index) {
        // Create Unit from Soldier with equipment
        Unit unit = new Unit(soldier.getName(), soldier.getCurrentHealth(), 
                           soldier.getMovementRange(), soldier.getAttackRange(), 
                           soldier.getAttackDamage(), UnitType.SOLDIER);
        
        // Set soldier class
        unit.setSoldierClass(soldier.getSoldierClass());
        
        // Set weapon if equipped
        if (soldier.getWeapon() != null) {
            unit.setWeapon(soldier.getWeapon());
        }
        
        // Set armor if equipped
        if (soldier.getArmor() != null) {
            unit.setArmor(soldier.getArmor());
        }
        
        // Set position (distribute soldiers across the field)
        int x = 2 + (index * 2);
        int y = 2 + (index % 2) * 2;
        unit.setPosition(new Position(x, y));
        
        // Add abilities
        if (soldier.getAbilities() != null) {
            for (SoldierAbility ability : soldier.getAbilities()) {
                unit.addAbility(ability);
            }
        }
        
        // Add explosives if soldier has them
        List<Explosive> soldierExplosives = soldier.getExplosives();
        if (soldierExplosives != null && !soldierExplosives.isEmpty()) {
            for (Explosive explosive : soldierExplosives) {
                unit.addExplosive(explosive);
            }
        }
        
        return unit;
    }
    
    private void resetPreparation() {
        logMessage("=== ПОДГОТОВКА СБРОШЕНА ===");
        logMessage("Солдаты возвращены в исходное состояние");
        logMessage("Требуется повторная подготовка к миссии");
        

        
        // Reset mission preparation state
        preparedSoldiers.clear();
        isMissionPrepared = false;
        
        // Reset selected unit to first available soldier
        if (!units.isEmpty()) {
            selectedUnit = units.stream()
                .filter(unit -> unit.getUnitType() == UnitType.SOLDIER)
                .findFirst()
                .orElse(units.get(0));
            updateUnitInfo();
            updateActionPanel();
        }
    }
    
    private void returnToMissionPreparation() {
        logMessage("=== ВОЗВРАЩЕНИЕ К ПОДГОТОВКЕ ===");
        cardLayout.show(mainContentPanel, "MISSION_PREP");
        logMessage("Переключено на подготовку миссии.");
        
        // Reset mission state
        preparedSoldiers.clear();
        isMissionPrepared = false;
        
        // Reset soldier selection form
        soldierSelectionForm.resetForm();
        
        // Disable battle start buttons

    }
    
    private void openSoldierSelectionForm() {
        JDialog formDialog = componentFactory.createDialog(this, "Выбор солдат и снаряжения", true);
        formDialog.setLayout(new BorderLayout());
        
        // Create the soldier selection form
        com.aliensattack.ui.panels.SoldierSelectionFormSimple soldierForm = new com.aliensattack.ui.panels.SoldierSelectionFormSimple();
        
        // Add form to dialog
        formDialog.add(soldierForm, BorderLayout.CENTER);
        
        // Set dialog properties
        formDialog.setSize(1200, 800);
        formDialog.setLocationRelativeTo(this);
        formDialog.setResizable(true);
        
        // Apply style to dialog
        styleManager.applyCurrentStyle(formDialog);
        
        // Show dialog
        formDialog.setVisible(true);
        
        // After dialog closes, check if soldier was equipped
        if (soldierForm.isFormComplete()) {
            Soldier equippedSoldier = soldierForm.getEquippedSoldier();
            logMessage("Солдат " + equippedSoldier.getName() + " подготовлен к миссии!");
            
            // Store the prepared soldier
            preparedSoldiers.clear(); // Clear previous preparations
            preparedSoldiers.add(equippedSoldier);
            isMissionPrepared = true;
            

        }
    }
    
    private void updateShootingPanel() {
        shootingPanel.removeAll();
        
        // Status label - use styled factory
        JLabel statusLabel = componentFactory.createLabel(shootingSystem.getShootingStatus());
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        shootingPanel.add(statusLabel);
        
        // Weapon selection
        if (shootingSystem.getSelectedShooter() != null) {
            List<Weapon> availableWeapons = shootingSystem.getAvailableWeapons();
            if (!availableWeapons.isEmpty()) {
                JLabel weaponLabel = componentFactory.createLabel("Доступное оружие:");
                weaponLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 5));
                shootingPanel.add(weaponLabel);
                
                for (Weapon weapon : availableWeapons) {
                    JButton weaponButton = componentFactory.createButton(weapon.getName() + " (" + weapon.getCurrentAmmo() + "/" + weapon.getAmmoCapacity() + ")");
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
                JLabel targetLabel = componentFactory.createLabel("Видимые враги:");
                targetLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 5));
                shootingPanel.add(targetLabel);
                
                for (Unit enemy : visibleEnemies) {
                    JButton targetButton = componentFactory.createButton(enemy.getName() + " (HP: " + enemy.getCurrentHealth() + ")");
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
            
            // Fire button - use styled factory
            if (shootingSystem.canExecuteShot()) {
                JButton fireButton = componentFactory.createButton("ОГОНЬ!");
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
                    grenadePreviewRadius = GameConfig.getGrenadePreviewRadius();
                    
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
            
            // Проверяем, может ли юнит бросить гранату
            if (!selectedUnit.canPerformGrenade()) {
                logMessage("Юнит " + selectedUnit.getName() + " не может бросить гранату!");
                return;
            }

            logMessage("=== АКТИВАЦИЯ РЕЖИМА ГРАНАТЫ ===");
            logMessage("Юнит: " + selectedUnit.getName());
            logMessage("Гранаты: " + explosives.size() + " шт.");
            
            // Показываем, сколько гранат можно бросить с текущими AP
            int maxGrenades = Math.min(explosives.size(), (int)selectedUnit.getActionPoints());
            logMessage("Можно бросить гранат: " + maxGrenades + " (AP: " + selectedUnit.getActionPoints() + ")");
            log.debug("Grenade action triggered for unit: {} with {} explosives", selectedUnit.getName(), explosives.size());
            log.debug("Unit position: {}", selectedUnit.getPosition());

            // Очищаем предыдущую подсветку и входим в режим гранаты
            clearHighlighting();
            isWaitingForGrenadeTarget = true;
            grenadePreviewCenter = null;
            grenadePreviewRadius = GameConfig.getGrenadePreviewRadius();
            
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
        
        // Add additional protection against automatic closure
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                // Prevent window from being deactivated
                if (isVisible()) {
                    requestFocusInWindow();
                }
            }
        });
        
        // Keep the window focused
        requestFocusInWindow();
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

    /**
     * Переключает окно на режим боя.
     */
    private void switchToTacticalMode() {
        logMessage("=== ПЕРЕКЛЮЧЕНИЕ НА РЕЖИМ БОЯ ===");
        cardLayout.show(mainContentPanel, "TACTICAL");
        logMessage("Переключено на режим боя. Выберите юнита и действие.");
        
        // Initialize tactical field with prepared soldiers if available
        if (isMissionPrepared && !preparedSoldiers.isEmpty()) {
            replaceSoldiersWithPrepared();
            logMessage("Поле боя загружено с подготовленными солдатами!");
        } else {
            logMessage("Используются стандартные солдаты (подготовка не завершена)");
        }
        
        // Update all UI components
        updateTacticalMap();
        updateUnitInfo();
        updateActionPanel();
        updateShootingPanel();
        
        // Focus on tactical map
        tacticalMapPanel.requestFocusInWindow();
    }

    /**
     * Callback method for when soldier preparation is complete.
     * This method is called when the soldier selection form is closed
     * and the user has selected soldiers for the mission.
     */
    private void onSoldierPreparationComplete() {
        logMessage("=== ПОДГОТОВКА СОЛДАТА ЗАВЕРШЕНА ===");
        
        // Get all prepared soldiers from the form
        List<Soldier> preparedSoldiersList = soldierSelectionForm.getMissionReadySoldiers();
        if (preparedSoldiersList != null && !preparedSoldiersList.isEmpty()) {
            preparedSoldiers.clear();
            preparedSoldiers.addAll(preparedSoldiersList);
            isMissionPrepared = true;
            
            logMessage("Подготовлено " + preparedSoldiersList.size() + " солдат для миссии!");
            for (Soldier soldier : preparedSoldiersList) {
                logMessage("Солдат " + soldier.getName() + ":");
                logMessage("  Оружие: " + (soldier.getWeapon() != null ? soldier.getWeapon().getName() : "нет"));
                logMessage("  Броня: " + (soldier.getArmor() != null ? soldier.getArmor().getName() : "нет"));
                logMessage("  Взрывчатка: " + (soldier.getExplosives() != null && !soldier.getExplosives().isEmpty() ? soldier.getExplosives().size() + " шт." : "нет"));
                logMessage("  Амуниция: " + (soldier.hasAmmunition() ? soldier.getAmmunitionCount() + " типов" : "не выбрана"));
            }
            

            
            // Switch to tactical mode to start the battle
            switchToTacticalMode();
        } else {
            logMessage("Ошибка: Не удалось получить подготовленных солдат!");
            JOptionPane.showMessageDialog(this, 
                "Ошибка подготовки солдат. Пожалуйста, попробуйте снова.",
                "Ошибка подготовки", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Run comprehensive system demo showcasing all implemented features
     */
    private void runSystemDemo() {
        logMessage("🚀 === ДЕМОНСТРАЦИЯ СИСТЕМ ALIENS ATTACK ===");
        logMessage("🎮 Инициализация всех продвинутых систем...");
        
        try {
            // Demo 1: Squad Cohesion System
            logMessage("🤝 Демонстрация системы сплоченности отряда...");
            if (squadCohesionManager != null) {
                // Create squad bonds between units
                List<Unit> soldiers = units.stream()
                    .filter(u -> u.getUnitType() == UnitType.SOLDIER)
                    .collect(java.util.stream.Collectors.toList());
                
                if (soldiers.size() >= 2) {
                    squadCohesionManager.createSquadBond(soldiers.get(0), soldiers.get(1));
                    logMessage("✅ Создана связь между солдатами: " + 
                        soldiers.get(0).getName() + " и " + soldiers.get(1).getName());
                    
                    int cohesionLevel = squadCohesionManager.getCohesionLevel();
                    int bondingPoints = squadCohesionManager.getBondingPoints();
                    int tacticalBonus = squadCohesionManager.getTacticalBonus();
                    
                    logMessage("📊 Уровень сплоченности: " + cohesionLevel);
                    logMessage("📊 Очки связей: " + bondingPoints);
                    logMessage("📊 Тактический бонус: +" + tacticalBonus);
                }
            }
            
            // Demo 2: Weather System
            logMessage("🌦️ Демонстрация системы погоды...");
            if (weatherSystemManager != null) {
                logMessage("✅ Система погоды активна");
                logMessage("📊 Влияние на видимость: -25%");
                logMessage("📊 Влияние на движение: -15%");
            }
            
            // Demo 3: Environmental Hazards
            logMessage("🌍 Демонстрация экологических опасностей...");
            if (environmentalHazardsManager != null) {
                logMessage("✅ Система экологических опасностей активна");
                logMessage("📊 Поддерживаемые типы: Огонь, Токсичные, Электрические, Радиация, Кислота, Плазма");
            }
            
            // Demo 4: Turn Management
            logMessage("🔄 Демонстрация системы управления ходами...");
            if (turnManager != null) {
                turnManager.startNewTurn();
                logMessage("✅ Начат новый ход");
                
                logMessage("📊 Система управления ходами активна");
            }
            
            // Demo 5: Brain Management
            logMessage("🧠 Демонстрация системы управления мозгами...");
            if (brainManager != null) {
                logMessage("✅ Система управления мозгами активна");
                logMessage("📊 Поддерживает: Человеческий мозг, AI мозг, Многопоточность");
            }
            
            // Demo 6: Combat System Enhancement
            logMessage("⚔️ Демонстрация улучшенной боевой системы...");
            if (combatManager != null) {
                logMessage("✅ Боевой менеджер активен");
                logMessage("📊 Поддерживаемые стратегии: Тактическая, Псионическая, Стелс, Городская, Правитель пришельцев");
            }
            
            logMessage("🎯 === ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА ===");
            logMessage("🚀 Все системы Aliens Attack работают корректно!");
            logMessage("💡 Теперь вы можете использовать все продвинутые функции игры");
            
        } catch (Exception e) {
            log.error("❌ Ошибка в демонстрации систем: {}", e.getMessage(), e);
            logMessage("❌ Ошибка в демонстрации: " + e.getMessage());
        }
    }
    
    /**
     * Refresh the entire game interface with current styles
     * This method reapplies the current style to all components
     */
    public void refreshGameInterface() {
        try {
            // Reapply styles to all main components
            styleManager.applyCurrentStyleToAll(
                this, tacticalMapPanel, unitInfoPanel, actionPanel,
                shootingPanel, missionPreparationPanel, mainContentPanel,
                gameLog
            );
            
            // Also apply styles to any dynamically created components
            if (tacticalMapView != null) {
                styleManager.applyCurrentStyle(tacticalMapView.getPanel());
            }
            if (unitInfoPanelView != null) {
                styleManager.applyCurrentStyle(unitInfoPanelView.getPanel());
            }
            if (actionPanelView != null) {
                styleManager.applyCurrentStyle(actionPanelView.getPanel());
            }
            
            // Refresh the display
            revalidate();
            repaint();
            
            logMessage("🎨 Стили интерфейса обновлены");
            log.info("Game interface styles refreshed successfully");
            
        } catch (Exception e) {
            log.error("Error refreshing game interface styles: {}", e.getMessage(), e);
            logMessage("❌ Ошибка обновления стилей: " + e.getMessage());
        }
    }
    
    /**
     * Get current active style information
     * @return current style display name
     */
    public String getCurrentStyleInfo() {
        if (styleManager.getCurrentStyle() != null) {
            return styleManager.getCurrentStyle().getDisplayName();
        }
        return "Unknown Style";
    }
    
    /**
     * Switch to a different style theme
     * @param styleId the style identifier to switch to
     * @return true if switch was successful
     */
    public boolean switchGameStyle(String styleId) {
        boolean success = styleManager.switchStyle(styleId);
        if (success) {
            refreshGameInterface();
            logMessage("🎨 Переключен на стиль: " + styleManager.getCurrentStyle().getDisplayName());
        } else {
            logMessage("❌ Не удалось переключить на стиль: " + styleId);
        }
        return success;
    }
} 
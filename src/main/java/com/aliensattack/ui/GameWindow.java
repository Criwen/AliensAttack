package com.aliensattack.ui;

import com.aliensattack.combat.ShootingSystem;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.combat.OptimizedCombatManager;
import com.aliensattack.combat.interfaces.ICombatManager;
import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.field.OptimizedTacticalField;
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
    private OptimizedCombatManager combatManager;
    private ShootingSystem shootingSystem;
    private ActionManager actionManager; // Добавляем ActionManager
    
    // UI Components
    private JTextArea gameLog;
    private JPanel tacticalMapPanel;
    private JPanel unitInfoPanel;
    private JPanel actionPanel;
    private JPanel mechanicsPanel;
    private JPanel shootingPanel;
    
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
        tacticalField = new OptimizedTacticalField(10, 10);
        
        // Initialize combat manager
        combatManager = new OptimizedCombatManager((OptimizedTacticalField) tacticalField);
        
        // Initialize action manager - используем OptimizedCombatManager вместо FinalXCOM2CombatManager
        OptimizedCombatManager optimizedCombatManager = new OptimizedCombatManager((OptimizedTacticalField) tacticalField);
        actionManager = new ActionManager((OptimizedTacticalField) tacticalField, optimizedCombatManager);
        
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
        tacticalMapPanel.removeAll();
        tacticalMapPanel.setLayout(new GridLayout(10, 10, 2, 2));
        
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                JButton tileButton = createTileButton(x, y);
                tacticalMapPanel.add(tileButton);
            }
        }
        
        tacticalMapPanel.revalidate();
        tacticalMapPanel.repaint();
        log.debug("Tactical map updated");
    }
    
    private JButton createTileButton(int x, int y) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        button.setFont(new Font("Arial", Font.BOLD, 10));
        
        // Check if unit is at this position
        Unit unitAtPosition = getUnitAtPosition(x, y);
        if (unitAtPosition != null) {
            if (unitAtPosition.getUnitType() == UnitType.SOLDIER) {
                button.setBackground(Color.BLUE);
                button.setText(unitAtPosition.getName().substring(0, 1));
            } else {
                button.setBackground(Color.RED);
                button.setText("A");
            }
            button.setForeground(Color.WHITE);
        } else {
            // Проверяем, является ли эта позиция подсвеченной для движения
            Position currentPos = new Position(x, y);
            if (isHighlightingMovePositions && highlightedPositions.contains(currentPos)) {
                button.setBackground(Color.GREEN);
                button.setText("M");
                button.setForeground(Color.WHITE);
            } else if (isWaitingForGrenadeTarget && grenadePreviewCenter != null) {
                // Подсветка AOE для гранаты: рисуем красный круг радиуса
                int dx = Math.abs(grenadePreviewCenter.getX() - x);
                int dy = Math.abs(grenadePreviewCenter.getY() - y);
                int manhattan = dx + dy;
                if (manhattan <= grenadePreviewRadius) {
                    button.setBackground(new Color(255, 100, 100));
                    button.setText("G");
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(Color.LIGHT_GRAY);
                    button.setText("");
                }
            } else {
                button.setBackground(Color.LIGHT_GRAY);
                button.setText("");
            }
        }
        
        button.addActionListener(e -> handleTileClick(x, y));

        // Перестраиваем предпросмотр AOE при наведении мыши
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isWaitingForGrenadeTarget && selectedUnit != null) {
                    grenadePreviewCenter = new Position(x, y);
                    // Возьмем радиус первой доступной гранаты
                    List<Explosive> explosives = selectedUnit.getExplosives();
                    grenadePreviewRadius = (explosives != null && !explosives.isEmpty()) ? Math.max(0, explosives.get(0).getRadius()) : 0;
                    updateTacticalMap();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isWaitingForGrenadeTarget) {
                    // оставляем центр, чтобы пользователь видел текущий предпросмотр
                }
            }
        });
        
        return button;
    }
    
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
            Unit unitAtPosition = getUnitAtPosition(x, y);
            Position clickedPosition = new Position(x, y);
            
            if (unitAtPosition != null) {
                // Выбираем юнита
                try {
                    selectedUnit = unitAtPosition;
                    actionManager.selectUnit(selectedUnit);
                    isWaitingForTargetPosition = false;
                    
                    // Очищаем подсветку при выборе нового юнита
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
                List<Explosive> explosives = selectedUnit.getExplosives();
                if (explosives != null && !explosives.isEmpty()) {
                    Explosive explosive = explosives.get(0);
                    List<CombatResult> results = combatManager.throwGrenade(selectedUnit, explosive, clickedPosition);
                    log.info("{} throws '{}' to {} with radius {}", selectedUnit.getName(), explosive.getName(), clickedPosition, explosive.getRadius());
                    for (CombatResult r : results) {
                        if (r.getDamage() > 0) {
                            log.debug("Grenade result: {} dmg, msg='{}'", r.getDamage(), r.getMessage());
                        } else {
                            log.trace("Grenade peripheral effect: {}", r.getMessage());
                        }
                        logMessage(r.getMessage() + " (" + r.getDamage() + " dmg)");
                    }
                    isWaitingForGrenadeTarget = false;
                    grenadePreviewCenter = null;
                    grenadePreviewRadius = 0;
                    updateTacticalMap();
                    updateUnitInfo();
                    updateActionPanel();
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
            highlightedPositions.clear();
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
        }
    }
    
    private void clearHighlighting() {
        try {
            highlightedPositions.clear();
            isHighlightingMovePositions = false;
            updateTacticalMap();
        } catch (Exception e) {
            System.err.println("Error clearing highlighting: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void updateUnitInfo() {
        try {
            unitInfoPanel.removeAll();
            
            if (selectedUnit != null) {
                try {
                    addUnitInfoLabel("Name: " + selectedUnit.getName());
                    addUnitInfoLabel("Health: " + selectedUnit.getCurrentHealth() + "/" + selectedUnit.getMaxHealth());
                    
                    Position position = selectedUnit.getPosition();
                    if (position != null) {
                        addUnitInfoLabel("Position: (" + position.getX() + ", " + position.getY() + ")");
                    } else {
                        addUnitInfoLabel("Position: Unknown");
                    }
                    
                    addUnitInfoLabel("Unit Type: " + selectedUnit.getUnitType());
                    addUnitInfoLabel("Action Points: " + selectedUnit.getActionPoints());
                    
                    if (selectedUnit.getWeapon() != null) {
                        try {
                            Weapon weapon = selectedUnit.getWeapon();
                            addUnitInfoLabel("Weapon: " + weapon.getName());
                            
                            // Показываем специальные свойства инопланетного оружия
                            if (weapon.isAlienWeapon()) {
                                addUnitInfoLabel("Type: ALIEN WEAPON");
                                addUnitInfoLabel("Damage: " + weapon.getTotalAlienWeaponDamage() + " (Base: " + weapon.getBaseDamage() + " + " + weapon.getAlienWeaponDamageBonus() + ")");
                                addUnitInfoLabel("Accuracy: " + weapon.getTotalAlienWeaponAccuracy() + "% (Base: " + weapon.getAccuracy() + "% + " + weapon.getAlienWeaponAccuracyBonus() + "%)");
                                
                                if (weapon.isPlasmaWeapon()) {
                                    addUnitInfoLabel("Special: Plasma Technology");
                                } else if (weapon.isLaserWeapon()) {
                                    addUnitInfoLabel("Special: Laser Technology");
                                }
                            } else {
                                addUnitInfoLabel("Damage: " + weapon.getBaseDamage());
                                addUnitInfoLabel("Accuracy: " + weapon.getAccuracy() + "%");
                            }
                            
                            addUnitInfoLabel("Ammo: " + weapon.getCurrentAmmo() + "/" + weapon.getAmmoCapacity());
                        } catch (Exception e) {
                            System.err.println("Error displaying weapon info: " + e.getMessage());
                            addUnitInfoLabel("Weapon: Error displaying weapon info");
                        }
                    }
                    
                    // Показываем гранаты
                    try {
                        List<Explosive> explosives = selectedUnit.getExplosives();
                        if (explosives != null && !explosives.isEmpty()) {
                            addUnitInfoLabel("--- GRENADES ---");
                            for (Explosive explosive : explosives) {
                                addUnitInfoLabel(explosive.getName() + " (" + explosive.getDamage() + " dmg, " + explosive.getRadius() + " radius)");
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error displaying explosives info: " + e.getMessage());
                    }
                    
                    // Показываем способности (включая медикаменты)
                    try {
                        List<SoldierAbility> abilities = selectedUnit.getAbilities();
                        if (abilities != null && !abilities.isEmpty()) {
                            addUnitInfoLabel("--- ABILITIES ---");
                            for (SoldierAbility ability : abilities) {
                                addUnitInfoLabel(ability.getName() + " (AP: " + ability.getActionPointCost() + ")");
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error displaying abilities info: " + e.getMessage());
                    }
                    
                    if (selectedUnit.getSoldierClass() != null) {
                        addUnitInfoLabel("Class: " + selectedUnit.getSoldierClass());
                    }
                    
                    // Show special abilities
                    try {
                        if (selectedUnit.hasBladestorm()) {
                            addUnitInfoLabel("Bladestorm: Active");
                        }
                        
                        if (selectedUnit.isConcealed()) {
                            addUnitInfoLabel("Status: Concealed");
                        }
                        
                        if (selectedUnit.isSuppressed()) {
                            addUnitInfoLabel("Status: Suppressed");
                        }
                    } catch (Exception e) {
                        System.err.println("Error displaying special abilities: " + e.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("Error updating unit info: " + e.getMessage());
                    e.printStackTrace();
                    addUnitInfoLabel("Error: Could not display unit information");
                }
            }
            
            unitInfoPanel.revalidate();
            unitInfoPanel.repaint();
        } catch (Exception e) {
            System.err.println("Critical error in updateUnitInfo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void addUnitInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        unitInfoPanel.add(label);
    }
    
    private void updateActionPanel() {
        try {
            actionPanel.removeAll();
            
            if (selectedUnit != null) {
                try {
                    // Basic actions - always available if unit is alive
                    addActionButtonIfAvailable("Move", e -> handleMoveAction(), selectedUnit.canPerformMove());
                    addActionButtonIfAvailable("Attack", e -> handleAttackAction(), selectedUnit.canPerformAttack());
                    addActionButtonIfAvailable("Overwatch", e -> handleOverwatchAction(), selectedUnit.canPerformOverwatch());
                    addActionButtonIfAvailable("End Turn", e -> handleEndTurnAction(), true); // Always available
                    
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
                     }
                 });
                fireButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                shootingPanel.add(fireButton);
            }
        }
        
        shootingPanel.revalidate();
        shootingPanel.repaint();
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
        }
    }
    
    private void handleEndTurnAction() {
        currentTurn++;
        logMessage("=== TURN " + currentTurn + " ===");
        
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
        }
    }
    
    private void handleGrenadeAction() {
        if (selectedUnit != null) {
            if (selectedUnit.getActionPoints() < 1) {
                logMessage("Недостаточно очков действия для Grenade. Нужно: 1 AP");
                return;
            }

            // Входим в режим выбора точки броска гранаты и показываем предпросмотр AOE
            isWaitingForGrenadeTarget = true;
            grenadePreviewCenter = null;
            grenadePreviewRadius = 0;
            actionManager.selectAction(ActionType.GRENADE);
            logMessage("Выберите клетку для броска гранаты. Подсветка покажет область поражения.");
            updateTacticalMap();
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
        }
    }
    
    private void handleBladestormAction() {
        if (selectedUnit != null) {
            logMessage(selectedUnit.getName() + " activates Bladestorm");
            ReactiveAbility bladestorm = new ReactiveAbility("Bladestorm", ReactiveAbilityType.BLADESTORM, 1);
            selectedUnit.addReactiveAbility(bladestorm);
            updateUnitInfo();
        }
    }
    
    private void handleRapidFireAction() {
        if (selectedUnit != null && selectedUnit.getWeapon() != null && selectedUnit.getWeapon().canUseRapidFire()) {
            logMessage(selectedUnit.getName() + " uses Rapid Fire");
            selectedUnit.spendActionPoint();
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
        }
    }
    
    private void handleBluescreenAction() {
        if (selectedUnit != null) {
            logMessage(selectedUnit.getName() + " uses Bluescreen attack");
            selectedUnit.spendActionPoint();
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
        }
    }
    
    private void handleVolatileMixAction() {
        if (selectedUnit != null) {
            logMessage(selectedUnit.getName() + " detonates Volatile Mix");
            selectedUnit.spendActionPoint();
            updateUnitInfo();
            updateActionPanel(); // Обновляем панель действий
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
} 
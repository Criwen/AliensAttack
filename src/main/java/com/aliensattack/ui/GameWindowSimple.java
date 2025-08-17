package com.aliensattack.ui;

import com.aliensattack.combat.ShootingSystem;
import com.aliensattack.combat.CombatResult;
import com.aliensattack.combat.DefaultCombatManager;
import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.field.ITacticalField;
import com.aliensattack.field.TacticalField;
import com.aliensattack.actions.ActionManager;
import com.aliensattack.core.enums.ActionType;
import com.aliensattack.actions.UnitAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simplified GameWindow without Lombok for testing mission preparation system
 */
public class GameWindowSimple extends JFrame {
    
    private ITacticalField tacticalField;
    private DefaultCombatManager combatManager;
    private ShootingSystem shootingSystem;
    private ActionManager actionManager;
    
    // UI Components
    private JTextArea gameLog;
    private JPanel tacticalMapPanel;
    private JPanel unitInfoPanel;
    private JPanel actionPanel;
    private JPanel mechanicsPanel;
    private JPanel shootingPanel;
    private JPanel missionControlPanel;
    
    // Views
    private com.aliensattack.ui.panels.TacticalMapView tacticalMapView;
    private com.aliensattack.ui.panels.UnitInfoPanelView unitInfoPanelView;
    private com.aliensattack.ui.panels.ActionPanelView actionPanelView;
    
    // Game State
    private List<Unit> units;
    private Unit selectedUnit;
    private int currentTurn;
    private boolean isWaitingForTargetPosition;
    private List<Position> highlightedPositions;
    private boolean isHighlightingMovePositions;
    private boolean isWaitingForGrenadeTarget;
    private Position grenadePreviewCenter;
    private int grenadePreviewRadius;
    
    // Mission preparation state
    private List<Soldier> preparedSoldiers;
    private boolean isMissionPrepared;
    
    public GameWindowSimple() {
        initializeWindow();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        initializeGame();
    }
    
    private void initializeWindow() {
        setTitle("Aliens Attack - XCOM 2 Tactical Combat (Simple)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    private void initializeComponents() {
        // Game log
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

        // Mission control panel
        missionControlPanel = new JPanel();
        missionControlPanel.setBorder(BorderFactory.createTitledBorder("Управление Миссией"));
        missionControlPanel.setLayout(new BoxLayout(missionControlPanel, BoxLayout.Y_AXIS));

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
        
        // Bottom panel for mechanics, shooting and mission control
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(mechanicsPanel, BorderLayout.WEST);
        bottomPanel.add(shootingPanel, BorderLayout.CENTER);
        bottomPanel.add(missionControlPanel, BorderLayout.EAST);
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
        
        // Initialize mission preparation state
        preparedSoldiers = new ArrayList<>();
        isMissionPrepared = false;
        
        // Create sample units
        createSampleUnits();
        
        // Update UI
        updateTacticalMap();
        updateUnitInfo();
        updateActionPanel();
        updateMechanicsPanel();
        updateShootingPanel();
        updateMissionControlPanel();
        
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
        
        // Add units to tactical field
        tacticalField.addUnit(soldier1);
        
        // Initialize actions for units
        actionManager.initializeActionsForUnit(soldier1);
        
        units.add(soldier1);
        selectedUnit = soldier1;
    }
    
    private void updateTacticalMap() {
        tacticalMapView.setOnTileClick((x, y) -> handleTileClick(x, y));
        tacticalMapView.render(highlightedPositions, isHighlightingMovePositions,
                isWaitingForGrenadeTarget, grenadePreviewCenter, grenadePreviewRadius, units);
    }
    
    private void handleTileClick(int x, int y) {
        logMessage("Клик по тайлу: (" + x + ", " + y + ")");
    }
    
    private void updateUnitInfo() {
        // Simple unit info update
        unitInfoPanel.removeAll();
        if (selectedUnit != null) {
            JLabel label = new JLabel("Выбран: " + selectedUnit.getName());
            unitInfoPanel.add(label);
        }
        unitInfoPanel.revalidate();
        unitInfoPanel.repaint();
    }
    
    private void updateActionPanel() {
        // Simple action panel update
        actionPanel.removeAll();
        JButton moveButton = new JButton("Движение");
        JButton attackButton = new JButton("Атака");
        actionPanel.add(moveButton);
        actionPanel.add(attackButton);
        actionPanel.revalidate();
        actionPanel.repaint();
    }
    
    private void updateMechanicsPanel() {
        mechanicsPanel.removeAll();
        JLabel label = new JLabel("Механики XCOM 2");
        mechanicsPanel.add(label);
        mechanicsPanel.revalidate();
        mechanicsPanel.repaint();
    }
    
    private void updateShootingPanel() {
        shootingPanel.removeAll();
        JLabel statusLabel = new JLabel("Система стрельбы готова");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        shootingPanel.add(statusLabel);
        shootingPanel.revalidate();
        shootingPanel.repaint();
    }
    
    private void updateMissionControlPanel() {
        missionControlPanel.removeAll();
        
        // Mission preparation button
        JButton prepareMissionButton = new JButton("Подготовка к миссии");
        prepareMissionButton.setFont(new Font("Arial", Font.BOLD, 12));
        prepareMissionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        prepareMissionButton.addActionListener(e -> openSoldierSelectionForm());
        
        // Start battle button (initially disabled)
        JButton startBattleButton = new JButton("Начать бой");
        startBattleButton.setFont(new Font("Arial", Font.BOLD, 12));
        startBattleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBattleButton.setEnabled(false);
        startBattleButton.addActionListener(e -> startBattle());
        
        // Reset preparation button (initially disabled)
        JButton resetPreparationButton = new JButton("Сбросить подготовку");
        resetPreparationButton.setFont(new Font("Arial", Font.PLAIN, 11));
        resetPreparationButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetPreparationButton.setEnabled(false);
        resetPreparationButton.addActionListener(e -> resetPreparation());
        
        // Mission status label
        JLabel missionStatusLabel = new JLabel("Статус: Требуется подготовка");
        missionStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        missionStatusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Add components
        missionControlPanel.add(Box.createVerticalStrut(10));
        missionControlPanel.add(prepareMissionButton);
        missionControlPanel.add(Box.createVerticalStrut(10));
        missionControlPanel.add(startBattleButton);
        missionControlPanel.add(Box.createVerticalStrut(5));
        missionControlPanel.add(resetPreparationButton);
        missionControlPanel.add(Box.createVerticalStrut(10));
        missionControlPanel.add(missionStatusLabel);
        missionControlPanel.add(Box.createVerticalGlue());
        
        // Store references for later updates
        missionControlPanel.putClientProperty("startBattleButton", startBattleButton);
        missionControlPanel.putClientProperty("resetPreparationButton", resetPreparationButton);
        missionControlPanel.putClientProperty("missionStatusLabel", missionStatusLabel);
        
        missionControlPanel.revalidate();
        missionControlPanel.repaint();
    }
    
    private void enableBattleStart() {
        // Enable start battle button and update status
        JButton startBattleButton = (JButton) missionControlPanel.getClientProperty("startBattleButton");
        JButton resetPreparationButton = (JButton) missionControlPanel.getClientProperty("resetPreparationButton");
        JLabel missionStatusLabel = (JLabel) missionControlPanel.getClientProperty("missionStatusLabel");
        
        if (startBattleButton != null) {
            startBattleButton.setEnabled(true);
        }
        
        if (resetPreparationButton != null) {
            resetPreparationButton.setEnabled(true);
        }
        
        if (missionStatusLabel != null) {
            missionStatusLabel.setText("Статус: Готов к бою");
            missionStatusLabel.setForeground(new Color(0, 128, 0)); // Green color
        }
        
        logMessage("Миссия готова! Можно начинать бой.");
        logMessage("Подготовлен солдат: " + preparedSoldiers.get(0).getName() + 
                  " с оружием: " + (preparedSoldiers.get(0).getWeapon() != null ? preparedSoldiers.get(0).getWeapon().getName() : "нет") +
                  " и бронёй: " + (preparedSoldiers.get(0).getArmor() != null ? preparedSoldiers.get(0).getArmor().getName() : "нет"));
    }
    
    private void startBattle() {
        logMessage("=== БОЙ НАЧАТ! ===");
        
        if (isMissionPrepared && !preparedSoldiers.isEmpty()) {
            // Replace default soldiers with prepared ones
            replaceSoldiersWithPrepared();
            logMessage("Поле боя загружено с подготовленными солдатами!");
        } else {
            logMessage("Используются стандартные солдаты (подготовка не завершена)");
        }
        
        logMessage("Тактическая карта активирована");
        logMessage("Выберите юнита и действие для начала боя");
        
        // Update mission status
        JLabel missionStatusLabel = (JLabel) missionControlPanel.getClientProperty("missionStatusLabel");
        if (missionStatusLabel != null) {
            missionStatusLabel.setText("Статус: Бой в процессе");
            missionStatusLabel.setForeground(new Color(255, 140, 0)); // Orange color
        }
        
        // Focus on tactical map
        tacticalMapPanel.requestFocusInWindow();
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
        for (int i = 0; i < preparedSoldiers.size(); i++) {
            Soldier preparedSoldier = preparedSoldiers.get(i);
            
            // Convert Soldier to Unit for tactical field
            Unit tacticalSoldier = convertSoldierToUnit(preparedSoldier, i);
            
            // Add to tactical field and units list
            tacticalField.addUnit(tacticalSoldier);
            units.add(tacticalSoldier);
            
            // Initialize actions for the soldier
            actionManager.initializeActionsForUnit(tacticalSoldier);
            
            logMessage("Добавлен солдат: " + tacticalSoldier.getName() + 
                      " с оружием: " + (tacticalSoldier.getWeapon() != null ? tacticalSoldier.getWeapon().getName() : "нет") +
                      " и бронёй: " + (tacticalSoldier.getArmor() != null ? tacticalSoldier.getArmor().getName() : "нет"));
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
        
        return unit;
    }
    
    private void resetPreparation() {
        logMessage("=== ПОДГОТОВКА СБРОШЕНА ===");
        logMessage("Солдаты возвращены в исходное состояние");
        logMessage("Требуется повторная подготовка к миссии");
        
        // Reset mission status
        JButton startBattleButton = (JButton) missionControlPanel.getClientProperty("startBattleButton");
        JButton resetPreparationButton = (JButton) missionControlPanel.getClientProperty("resetPreparationButton");
        JLabel missionStatusLabel = (JLabel) missionControlPanel.getClientProperty("missionStatusLabel");
        
        if (startBattleButton != null) {
            startBattleButton.setEnabled(false);
        }
        
        if (resetPreparationButton != null) {
            resetPreparationButton.setEnabled(false);
        }
        
        if (missionStatusLabel != null) {
            missionStatusLabel.setText("Статус: Требуется подготовка");
            missionStatusLabel.setForeground(Color.BLACK); // Default color
        }
        
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
    
    private void openSoldierSelectionForm() {
        JDialog formDialog = new JDialog(this, "Выбор солдат и снаряжения", true);
        formDialog.setLayout(new BorderLayout());
        
        // Create the soldier selection form
        com.aliensattack.ui.panels.SoldierSelectionFormSimple soldierForm = new com.aliensattack.ui.panels.SoldierSelectionFormSimple();
        
        // Add form to dialog
        formDialog.add(soldierForm, BorderLayout.CENTER);
        
        // Set dialog properties
        formDialog.setSize(1200, 800);
        formDialog.setLocationRelativeTo(this);
        formDialog.setResizable(true);
        
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
            
            enableBattleStart(); // Enable battle start button
        }
    }
    
    private void cleanup() {
        logMessage("Очистка ресурсов...");
        dispose();
    }
    
    private void logMessage(String message) {
        gameLog.append(message + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
        System.out.println(message);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindowSimple gameWindow = new GameWindowSimple();
            gameWindow.setVisible(true);
        });
    }
}

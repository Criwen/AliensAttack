package com.aliensattack.ui.panels;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import com.aliensattack.core.data.AmmoTypeData;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;




/**
 * Soldier selection and equipment form for mission preparation
 */
@Getter
@Setter
public class SoldierSelectionForm extends JPanel {
    
    private final List<Soldier> availableSoldiers;
    private final List<Weapon> availableWeapons;
    private final List<Armor> availableArmor;
    private final List<Explosive> availableExplosives;
    
    private JList<Soldier> soldierList;
    private JList<Weapon> weaponList;
    private JList<Armor> armorList;
    private JList<Explosive> explosiveList;
    private JList<AmmoTypeData> ammoList;
    
    private JTextArea soldierInfo;
    private JTextArea equipmentInfo;
    private JTextArea inventoryInfo;
    private JButton startGameButton; // Button to start the game
    
    private List<Soldier> selectedSoldiers;
    private Weapon selectedWeapon;
    private Armor selectedArmor;
    private List<Explosive> selectedExplosives;
    private List<AmmoTypeData> selectedAmmo;
    
    // Callback for when game should start
    private Runnable onGameStartCallback;
    

    
    public SoldierSelectionForm() {
        this.availableSoldiers = new ArrayList<>();
        this.availableWeapons = new ArrayList<>();
        this.availableArmor = new ArrayList<>();
        this.availableExplosives = new ArrayList<>();
        this.selectedSoldiers = new ArrayList<>();
        this.selectedExplosives = new ArrayList<>();
        this.selectedAmmo = new ArrayList<>();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Выбор солдат и оснащения"));
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadSampleData();
        
        // Apply LLM-generated styles after component initialization
        applyLLMStyles();
    }
    
    private void initializeComponents() {
        // Soldier selection - MULTIPLE SELECTION
        soldierList = new JList<>();
        soldierList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        soldierList.setCellRenderer(new SoldierListCellRenderer());
        
        // Equipment lists
        weaponList = new JList<>();
        weaponList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        weaponList.setCellRenderer(new EquipmentListCellRenderer());
        
        armorList = new JList<>();
        armorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        armorList.setCellRenderer(new EquipmentListCellRenderer());
        
        explosiveList = new JList<>();
        explosiveList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        explosiveList.setCellRenderer(new EquipmentListCellRenderer());
        
        ammoList = new JList<>();
        ammoList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        ammoList.setCellRenderer(new AmmoListCellRenderer());
        
        // Info panels
        soldierInfo = new JTextArea();
        soldierInfo.setEditable(false);
        soldierInfo.setLineWrap(true);
        soldierInfo.setWrapStyleWord(true);
        
        equipmentInfo = new JTextArea();
        equipmentInfo.setEditable(false);
        equipmentInfo.setLineWrap(true);
        equipmentInfo.setWrapStyleWord(true);
        
        inventoryInfo = new JTextArea();
        inventoryInfo.setEditable(false);
        inventoryInfo.setLineWrap(true);
        inventoryInfo.setWrapStyleWord(true);
        
        // Main action button
        startGameButton = new JButton("Начать миссию");
        startGameButton.setEnabled(false);
    }
    
    private void setupLayout() {
        // Main container with proper spacing
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Left panel - Soldier selection and info
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Доступные солдаты"));
        leftPanel.setPreferredSize(new Dimension(280, 400));
        
        JScrollPane soldierScrollPane = new JScrollPane(soldierList);
        soldierScrollPane.setPreferredSize(new Dimension(260, 180));
        leftPanel.add(soldierScrollPane, BorderLayout.CENTER);
        
        JScrollPane soldierInfoScrollPane = new JScrollPane(soldierInfo);
        soldierInfoScrollPane.setPreferredSize(new Dimension(260, 150));
        leftPanel.add(soldierInfoScrollPane, BorderLayout.SOUTH);
        
        // Center panel - Equipment selection (more compact)
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Выбор снаряжения"));
        centerPanel.setPreferredSize(new Dimension(500, 300));
        
        // Weapons
        JPanel weaponPanel = new JPanel(new BorderLayout());
        weaponPanel.setBorder(BorderFactory.createTitledBorder("Оружие"));
        JScrollPane weaponScrollPane = new JScrollPane(weaponList);
        weaponScrollPane.setPreferredSize(new Dimension(220, 100));
        weaponPanel.add(weaponScrollPane, BorderLayout.CENTER);
        centerPanel.add(weaponPanel);
        
        // Armor
        JPanel armorPanel = new JPanel(new BorderLayout());
        armorPanel.setBorder(BorderFactory.createTitledBorder("Броня"));
        JScrollPane armorScrollPane = new JScrollPane(armorList);
        armorScrollPane.setPreferredSize(new Dimension(220, 100));
        armorPanel.add(armorScrollPane, BorderLayout.CENTER);
        centerPanel.add(armorPanel);
        
        // Explosives
        JPanel explosivePanel = new JPanel(new BorderLayout());
        explosivePanel.setBorder(BorderFactory.createTitledBorder("Взрывчатка"));
        JScrollPane explosiveScrollPane = new JScrollPane(explosiveList);
        explosiveScrollPane.setPreferredSize(new Dimension(220, 100));
        explosivePanel.add(explosiveScrollPane, BorderLayout.CENTER);
        centerPanel.add(explosivePanel);
        
        // Ammunition panel
        JPanel ammoPanel = new JPanel(new BorderLayout());
        ammoPanel.setBorder(BorderFactory.createTitledBorder("Амуниция"));
        JScrollPane ammoScrollPane = new JScrollPane(ammoList);
        ammoScrollPane.setPreferredSize(new Dimension(220, 100));
        ammoPanel.add(ammoScrollPane, BorderLayout.CENTER);
        centerPanel.add(ammoPanel);
        
        // Right panel - Equipment info and controls
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Информация"));
        rightPanel.setPreferredSize(new Dimension(300, 400));
        
        // Create tabbed pane for equipment and inventory
        JTabbedPane infoTabs = new JTabbedPane();
        
        // Equipment tab
        JScrollPane equipmentInfoScrollPane = new JScrollPane(equipmentInfo);
        equipmentInfoScrollPane.setPreferredSize(new Dimension(280, 200));
        infoTabs.addTab("Снаряжение", equipmentInfoScrollPane);
        
        // Inventory tab
        InventoryPanel inventoryPanel = new InventoryPanel();
        infoTabs.addTab("Инвентарь", inventoryPanel);
        
        rightPanel.add(infoTabs, BorderLayout.CENTER);
        
        // Button panel - compact 4x2 grid
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Действия"));
        
        // First row
        JButton equipWeaponButton = new JButton("Экипировать оружие");
        JButton equipArmorButton = new JButton("Экипировать броню");
        
        // Second row
        JButton unequipWeaponButton = new JButton("Снять оружие");
        JButton unequipArmorButton = new JButton("Снять броню");
        
        // Third row
        JButton equipExplosiveButton = new JButton("Добавить взрывчатку");
        JButton clearExplosivesButton = new JButton("Очистить взрывчатку");
        
        // Fourth row
        JButton equipAllButton = new JButton("Экипировать всё");
        
        // Add buttons to panel
        buttonPanel.add(equipWeaponButton);
        buttonPanel.add(equipArmorButton);
        buttonPanel.add(unequipWeaponButton);
        buttonPanel.add(unequipArmorButton);
        buttonPanel.add(equipExplosiveButton);
        buttonPanel.add(clearExplosivesButton);
        buttonPanel.add(equipAllButton);
        buttonPanel.add(this.startGameButton);
        
        // Add action listeners
        equipWeaponButton.addActionListener(e -> handleQuickEquipWeapon());
        equipArmorButton.addActionListener(e -> handleQuickEquipArmor());
        unequipWeaponButton.addActionListener(e -> handleUnequipWeapon());
        unequipArmorButton.addActionListener(e -> handleUnequipArmor());
        equipExplosiveButton.addActionListener(e -> handleQuickEquipExplosive());
        clearExplosivesButton.addActionListener(e -> handleClearExplosives());
        equipAllButton.addActionListener(e -> handleEquipAll());
        
        // Add buttons directly to the right panel
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Main layout with proper constraints
        JPanel topPanel = new JPanel(new BorderLayout(15, 0));
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Soldier selection - MULTIPLE SELECTION
        soldierList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                List<Soldier> selected = soldierList.getSelectedValuesList();
                selectedSoldiers.clear();
                selectedSoldiers.addAll(selected);
                updateSoldierInfo();
                updateInventoryInfo();
                updateStartGameButton();
            }
        });
        
        // Weapon selection
        weaponList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Weapon weapon = weaponList.getSelectedValue();
                if (weapon != null) {
                                    selectedWeapon = weapon;
                updateEquipmentInfo();
                updateInventoryInfo();
                updateStartGameButton();
                }
            }
        });
        
        // Armor selection
        armorList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Armor armor = armorList.getSelectedValue();
                if (armor != null) {
                                    selectedArmor = armor;
                updateEquipmentInfo();
                updateInventoryInfo();
                updateStartGameButton();
                }
            }
        });
        
        // Explosive selection
        explosiveList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                List<Explosive> selected = explosiveList.getSelectedValuesList();
                selectedExplosives.clear();
                selectedExplosives.addAll(selected);
                updateEquipmentInfo();
                updateInventoryInfo();
                updateStartGameButton();
            }
        });
        
        // Button
        startGameButton.addActionListener(e -> handleStartGame());
        
        // Ammunition selection
        ammoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                List<AmmoTypeData> selected = ammoList.getSelectedValuesList();
                selectedAmmo.clear();
                selectedAmmo.addAll(selected);
                updateInventoryInfo();
            }
        });
    }
    
    private void loadSampleData() {
        // Load sample soldiers
        availableSoldiers.addAll(createSampleSoldiers());
        soldierList.setListData(availableSoldiers.toArray(new Soldier[0]));
        
        // Load sample weapons
        availableWeapons.addAll(createSampleWeapons());
        weaponList.setListData(availableWeapons.toArray(new Weapon[0]));
        
        // Load sample armor
        availableArmor.addAll(createSampleArmor());
        armorList.setListData(availableArmor.toArray(new Armor[0]));
        
        // Load sample explosives
        availableExplosives.addAll(createSampleExplosives());
        explosiveList.setListData(availableExplosives.toArray(new Explosive[0]));
        
        // Load sample ammunition
        List<AmmoTypeData> availableAmmo = createSampleAmmo();
        ammoList.setListData(availableAmmo.toArray(new AmmoTypeData[0]));
    }
    
    private List<Soldier> createSampleSoldiers() {
        List<Soldier> soldiers = new ArrayList<>();
        
        Soldier ranger = new Soldier("Сержант Иванов", 8, 12, 4, 4, SoldierClass.RANGER);
        ranger.setExperience(150);
        ranger.setRank(3);
        soldiers.add(ranger);
        
        Soldier specialist = new Soldier("Капрал Петров", 6, 10, 6, 3, SoldierClass.SPECIALIST);
        specialist.setExperience(100);
        specialist.setRank(2);
        soldiers.add(specialist);
        
        Soldier heavy = new Soldier("Рядовой Сидоров", 10, 8, 8, 5, SoldierClass.HEAVY);
        heavy.setExperience(200);
        heavy.setRank(4);
        soldiers.add(heavy);
        
        Soldier sharpshooter = new Soldier("Снайпер Козлов", 5, 8, 12, 6, SoldierClass.SHARPSHOOTER);
        sharpshooter.setExperience(180);
        sharpshooter.setRank(3);
        soldiers.add(sharpshooter);
        
        return soldiers;
    }
    
    private List<Weapon> createSampleWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        
        weapons.add(new Weapon("Автомат АК-12", WeaponType.RIFLE, 4, 6, 15, 75, 4));
        weapons.add(new Weapon("Снайперская винтовка СВД", WeaponType.SNIPER_RIFLE, 6, 9, 25, 85, 12));
        weapons.add(new Weapon("Пулемет ПКМ", WeaponType.HEAVY_WEAPON, 5, 7, 10, 70, 6));
        weapons.add(new Weapon("Дробовик Сайга", WeaponType.SHOTGUN, 7, 10, 20, 65, 2));
        weapons.add(new Weapon("Гранатомет РПГ-7", WeaponType.GRENADE_LAUNCHER, 8, 12, 15, 60, 8, true, 3));
        
        return weapons;
    }
    
    private List<Armor> createSampleArmor() {
        List<Armor> armor = new ArrayList<>();
        
        armor.add(new Armor("Легкая броня", ArmorType.LIGHT_ARMOR, 1, 2));
        armor.add(new Armor("Средняя броня", ArmorType.MEDIUM_ARMOR, 2, 4));
        armor.add(new Armor("Тяжелая броня", ArmorType.HEAVY_ARMOR, 3, 6));
        armor.add(new Armor("Пластинчатая броня", ArmorType.PLATED_ARMOR, 4, 6));
        
        return armor;
    }
    
    private List<Explosive> createSampleExplosives() {
        List<Explosive> explosives = new ArrayList<>();
        
        explosives.add(new Explosive("Ф-1", ExplosiveType.GRENADE, 6, 3, 0));
        explosives.add(new Explosive("РГД-5", ExplosiveType.GRENADE, 5, 2, 0));
        explosives.add(new Explosive("Дымовая граната", ExplosiveType.SMOKE_GRENADE, 0, 2, 0));
        explosives.add(new Explosive("Световая граната", ExplosiveType.FLASHBANG, 0, 2, 0));
        
        return explosives;
    }
    
    private List<AmmoTypeData> createSampleAmmo() {
        List<AmmoTypeData> ammo = new ArrayList<>();
        
        ammo.add(new AmmoTypeData("Стандартные патроны", AmmoType.STANDARD, 0, 0));
        ammo.add(new AmmoTypeData("Бронебойные патроны", AmmoType.ARMOR_PIERCING, 5, 0));
        ammo.add(new AmmoTypeData("Взрывные патроны", AmmoType.EXPLOSIVE, 10, -5));
        ammo.add(new AmmoTypeData("Зажигательные патроны", AmmoType.INCENDIARY, 8, 0));
        ammo.add(new AmmoTypeData("Кислотные патроны", AmmoType.ACID, 12, -3));
        ammo.add(new AmmoTypeData("Оглушающие патроны", AmmoType.STUN, 2, 5));
        ammo.add(new AmmoTypeData("Отравляющие патроны", AmmoType.POISON, 6, 0));
        ammo.add(new AmmoTypeData("Плазменные патроны", AmmoType.PLASMA, 15, 3));
        ammo.add(new AmmoTypeData("Лазерные патроны", AmmoType.LASER, 8, 8));
        
        return ammo;
    }
    
    private void updateSoldierInfo() {
        if (selectedSoldiers == null || selectedSoldiers.isEmpty()) {
            soldierInfo.setText("Выберите солдат для просмотра информации");
            return;
        }
        
        StringBuilder info = new StringBuilder();
        info.append("=== ВЫБРАННЫЕ СОЛДАТЫ ===\n\n");
        
        for (int i = 0; i < selectedSoldiers.size(); i++) {
            Soldier soldier = selectedSoldiers.get(i);
            info.append("Солдат ").append(i + 1).append(":\n");
            info.append("Имя: ").append(soldier.getName()).append("\n");
            info.append("Класс: ").append(soldier.getSoldierClass()).append("\n");
            info.append("Уровень: ").append(soldier.getRank()).append("\n");
            info.append("Опыт: ").append(soldier.getExperience()).append("\n");
            info.append("Здоровье: ").append(soldier.getCurrentHealth()).append("/").append(soldier.getMaxHealth()).append("\n");
            info.append("Движение: ").append(soldier.getMovementRange()).append("\n");
            info.append("Атака: ").append(soldier.getAttackRange()).append(" (дальность)\n");
            info.append("Урон: ").append(soldier.getAttackDamage()).append("\n");
            
            if (soldier.getAbilities() != null && !soldier.getAbilities().isEmpty()) {
                info.append("Способности:\n");
                for (SoldierAbility ability : soldier.getAbilities()) {
                    info.append("- ").append(ability.getName()).append("\n");
                }
            }
            
            if (i < selectedSoldiers.size() - 1) {
                info.append("\n---\n\n");
            }
        }
        
        soldierInfo.setText(info.toString());
    }
    
    private void updateEquipmentInfo() {
        StringBuilder info = new StringBuilder();
        
        if (selectedWeapon != null) {
            info.append("Оружие: ").append(selectedWeapon.getName()).append("\n");
            info.append("Тип: ").append(selectedWeapon.getType()).append("\n");
            info.append("Урон: ").append(selectedWeapon.getBaseDamage()).append("\n");
            info.append("Точность: ").append(selectedWeapon.getAccuracy()).append("%\n");
            info.append("Дальность: ").append(selectedWeapon.getRange()).append("\n");
            info.append("Патроны: ").append(selectedWeapon.getCurrentAmmo()).append("/").append(selectedWeapon.getAmmoCapacity()).append("\n\n");
        }
        
        if (selectedArmor != null) {
            info.append("Броня: ").append(selectedArmor.getName()).append("\n");
            info.append("Тип: ").append(selectedArmor.getType()).append("\n");
            info.append("Защита: ").append(selectedArmor.getDamageReduction()).append("\n");
            info.append("Здоровье: ").append(selectedArmor.getCurrentHealth()).append("/").append(selectedArmor.getMaxHealth()).append("\n\n");
        }
        
        if (!selectedExplosives.isEmpty()) {
            info.append("Взрывчатка:\n");
            for (Explosive explosive : selectedExplosives) {
                info.append("- ").append(explosive.getName()).append(" (урон: ").append(explosive.getDamage()).append(", радиус: ").append(explosive.getRadius()).append(")\n");
            }
        }
        
        equipmentInfo.setText(info.toString());
    }
    
    private void updateInventoryInfo() {
        if (selectedSoldiers == null || selectedSoldiers.isEmpty()) {
            inventoryInfo.setText("Выберите солдат для просмотра инвентаря");
            return;
        }
        
        StringBuilder info = new StringBuilder();
        info.append("=== СНАРЯЖЕНИЕ ДЛЯ ВЫБРАННЫХ СОЛДАТ ===\n\n");
        
        // Show selected equipment
        if (selectedWeapon != null) {
            info.append("Оружие: ").append(selectedWeapon.getName()).append("\n");
            info.append("  Урон: ").append(selectedWeapon.getBaseDamage()).append("\n");
            info.append("  Точность: ").append(selectedWeapon.getAccuracy()).append("%\n\n");
        } else {
            info.append("Оружие: не выбрано\n\n");
        }
        
        if (selectedArmor != null) {
            info.append("Броня: ").append(selectedArmor.getName()).append("\n");
            info.append("  Защита: ").append(selectedArmor.getDamageReduction()).append("\n\n");
        } else {
            info.append("Броня: не выбрана\n\n");
        }
        
        if (selectedExplosives != null && !selectedExplosives.isEmpty()) {
            info.append("Взрывчатка:\n");
            for (Explosive explosive : selectedExplosives) {
                info.append("- ").append(explosive.getName()).append(" (урон: ").append(explosive.getDamage()).append(")\n");
            }
            info.append("\n");
        }
        
        if (selectedAmmo != null && !selectedAmmo.isEmpty()) {
            info.append("Амуниция:\n");
            for (AmmoTypeData ammo : selectedAmmo) {
                info.append("- ").append(ammo.getName()).append(" (тип: ").append(ammo.getType()).append(")\n");
            }
            info.append("\n");
        }
        
        info.append("=== КОЛИЧЕСТВО ВЫБРАННЫХ СОЛДАТ: ").append(selectedSoldiers.size()).append(" ===\n");
        
        inventoryInfo.setText(info.toString());
    }
    

    
    private void updateStartGameButton() {
        boolean canStartGame = !selectedSoldiers.isEmpty() && selectedWeapon != null && selectedArmor != null;
        startGameButton.setEnabled(canStartGame);
    }
    


    private void handleStartGame() {
        if (!selectedSoldiers.isEmpty() && selectedWeapon != null && selectedArmor != null) {
            // Equip all selected soldiers before starting
            for (Soldier soldier : selectedSoldiers) {
                soldier.equipWeapon(selectedWeapon);
                soldier.equipArmor(selectedArmor);
                
                // Add explosives if selected
                if (selectedExplosives != null && !selectedExplosives.isEmpty()) {
                    for (Explosive explosive : selectedExplosives) {
                        // Create a copy of the explosive for each soldier
                        Explosive explosiveCopy = new Explosive(explosive.getName(), explosive.getType(), explosive.getDamage(), explosive.getRadius(), 0);
                        soldier.addExplosive(explosiveCopy);
                    }
                }
                
                // Add ammunition to soldier's inventory
                if (selectedAmmo != null && !selectedAmmo.isEmpty()) {
                    soldier.addAmmunition(selectedAmmo);
                }
            }
            
            // Show brief success message and immediately start the game
            String message = "Миссия начинается!\n";
            message += "Экипировано " + selectedSoldiers.size() + " солдат";
            
            JOptionPane.showMessageDialog(this, message, "Миссия запущена", JOptionPane.INFORMATION_MESSAGE);
            
            // Call the callback to start the game immediately
            if (onGameStartCallback != null) {
                onGameStartCallback.run();
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, выберите солдат, оружие и броню перед началом миссии.",
                "Неполная подготовка", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleQuickEquipWeapon() {
        if (!selectedSoldiers.isEmpty() && selectedWeapon != null) {
            // Replace current weapon for all selected soldiers
            StringBuilder message = new StringBuilder("Оружие заменено для " + selectedSoldiers.size() + " солдат!\n\n");
            
            for (Soldier soldier : selectedSoldiers) {
                Weapon oldWeapon = soldier.getWeapon();
                soldier.equipWeapon(selectedWeapon);
                
                if (oldWeapon != null) {
                    message.append(soldier.getName()).append(": снято ").append(oldWeapon.getName()).append("\n");
                }
                message.append(soldier.getName()).append(": экипировано ").append(selectedWeapon.getName()).append("\n");
            }
            
            JOptionPane.showMessageDialog(this, message.toString(), "Оружие заменено", JOptionPane.INFORMATION_MESSAGE);
            updateInventoryInfo();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, выберите солдат и оружие для экипировки.",
                "Неполная подготовка", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleQuickEquipArmor() {
        if (!selectedSoldiers.isEmpty() && selectedArmor != null) {
            // Replace current armor for all selected soldiers
            StringBuilder message = new StringBuilder("Броня заменена для " + selectedSoldiers.size() + " солдат!\n\n");
            
            for (Soldier soldier : selectedSoldiers) {
                Armor oldArmor = soldier.getArmor();
                soldier.equipArmor(selectedArmor);
                
                if (oldArmor != null) {
                    message.append(soldier.getName()).append(": снято ").append(oldArmor.getName()).append("\n");
                }
                message.append(soldier.getName()).append(": экипировано ").append(selectedArmor.getName()).append("\n");
            }
            
            JOptionPane.showMessageDialog(this, message.toString(), "Броня заменена", JOptionPane.INFORMATION_MESSAGE);
            updateInventoryInfo();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, выберите солдат и броню для экипировки.",
                "Неполная подготовка", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleQuickEquipExplosive() {
        if (!selectedSoldiers.isEmpty() && !selectedExplosives.isEmpty()) {
            // Add explosives to all selected soldiers
            StringBuilder message = new StringBuilder();
            message.append("Взрывчатка добавлена ").append(selectedSoldiers.size()).append(" солдатам!\n\n");
            message.append("Добавлено:\n");
            
            for (Explosive explosive : selectedExplosives) {
                message.append("- ").append(explosive.getName()).append(" (урон: ").append(explosive.getDamage()).append(")\n");
            }
            
            message.append("\nСолдаты:\n");
            for (Soldier soldier : selectedSoldiers) {
                message.append("- ").append(soldier.getName()).append("\n");
            }
            
            JOptionPane.showMessageDialog(this, message.toString(), "Взрывчатка добавлена", JOptionPane.INFORMATION_MESSAGE);
            updateInventoryInfo();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, выберите солдат и взрывчатку для добавления в инвентарь.",
                "Неполная подготовка", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleUnequipWeapon() {
        if (!selectedSoldiers.isEmpty()) {
            StringBuilder message = new StringBuilder("Оружие снято с " + selectedSoldiers.size() + " солдат!\n\n");
            
            for (Soldier soldier : selectedSoldiers) {
                if (soldier.getWeapon() != null) {
                    Weapon oldWeapon = soldier.getWeapon();
                    soldier.setWeapon(null);
                    message.append(soldier.getName()).append(": снято ").append(oldWeapon.getName()).append("\n");
                } else {
                    message.append(soldier.getName()).append(": нет оружия\n");
                }
            }
            
            JOptionPane.showMessageDialog(this, message.toString(), "Оружие снято", JOptionPane.INFORMATION_MESSAGE);
            updateInventoryInfo();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, выберите солдат для снятия оружия.",
                "Нет солдат", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleUnequipArmor() {
        if (!selectedSoldiers.isEmpty()) {
            StringBuilder message = new StringBuilder("Броня снята с " + selectedSoldiers.size() + " солдат!\n\n");
            
            for (Soldier soldier : selectedSoldiers) {
                if (soldier.getArmor() != null) {
                    Armor oldArmor = soldier.getArmor();
                    soldier.setArmor(null);
                    message.append(soldier.getName()).append(": снята ").append(oldArmor.getName()).append("\n");
                } else {
                    message.append(soldier.getName()).append(": нет брони\n");
                }
            }
            
            JOptionPane.showMessageDialog(this, message.toString(), "Броня снята", JOptionPane.INFORMATION_MESSAGE);
            updateInventoryInfo();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, выберите солдат для снятия брони.",
                "Нет солдат", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleClearExplosives() {
        if (!selectedExplosives.isEmpty()) {
            int count = selectedExplosives.size();
            selectedExplosives.clear();
            
            JOptionPane.showMessageDialog(this, 
                "Взрывчатка очищена (" + count + " шт. удалено)",
                "Взрывчатка очищена", 
                JOptionPane.INFORMATION_MESSAGE);
            updateInventoryInfo();
            updateEquipmentInfo();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Нет выбранной взрывчатки для очистки.",
                "Нет взрывчатки", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleEquipAmmo() {
        if (!selectedSoldiers.isEmpty() && !selectedAmmo.isEmpty()) {
            // Actually equip the ammunition to all selected soldiers
            StringBuilder message = new StringBuilder();
            message.append("Амуниция экипирована ").append(selectedSoldiers.size()).append(" солдатам!\n\n");
            message.append("Добавлено:\n");
            
            for (AmmoTypeData ammo : selectedAmmo) {
                message.append("- ").append(ammo.getName()).append(" (").append(ammo.getDamageBonus()).append(" бонус урона)\n");
            }
            
            message.append("\nСолдаты:\n");
            for (Soldier soldier : selectedSoldiers) {
                soldier.addAmmunition(selectedAmmo);
                message.append("- ").append(soldier.getName()).append("\n");
            }
            
            message.append("\nАмуниция сохранена в инвентаре солдат и будет доступна на поле боя.");
            
            JOptionPane.showMessageDialog(this, message.toString(), "Амуниция экипирована", JOptionPane.INFORMATION_MESSAGE);
            updateInventoryInfo();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, выберите солдат и амуницию для экипировки.",
                "Неполная подготовка", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleClearAmmo() {
        if (!selectedAmmo.isEmpty()) {
            int count = selectedAmmo.size();
            selectedAmmo.clear();
            
            // Also clear ammunition from all selected soldiers' inventory
            if (!selectedSoldiers.isEmpty()) {
                for (Soldier soldier : selectedSoldiers) {
                    soldier.clearAmmunition();
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "Амуниция очищена (" + count + " типов удалено)\nАмуниция также удалена из инвентаря " + selectedSoldiers.size() + " солдат.",
                "Амуниция очищена", 
                JOptionPane.INFORMATION_MESSAGE);
            updateInventoryInfo();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Нет выбранной амуниции для очистки.",
                "Нет амуниции", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void handleEquipAll() {
        if (!selectedSoldiers.isEmpty()) {
            boolean equipped = false;
            StringBuilder message = new StringBuilder();
            message.append("Экипировка ").append(selectedSoldiers.size()).append(" солдат:\n\n");
            
            for (Soldier soldier : selectedSoldiers) {
                message.append("Солдат: ").append(soldier.getName()).append("\n");
                
                if (selectedWeapon != null) {
                    soldier.equipWeapon(selectedWeapon);
                    message.append("✓ Оружие: ").append(selectedWeapon.getName()).append("\n");
                    equipped = true;
                }
                
                if (selectedArmor != null) {
                    soldier.equipArmor(selectedArmor);
                    message.append("✓ Броня: ").append(selectedArmor.getName()).append("\n");
                    equipped = true;
                }
                
                if (!selectedExplosives.isEmpty()) {
                    for (Explosive explosive : selectedExplosives) {
                        // Create a copy of the explosive for each soldier
                        Explosive explosiveCopy = new Explosive(explosive.getName(), explosive.getType(), explosive.getDamage(), explosive.getRadius(), 0);
                        soldier.addExplosive(explosiveCopy);
                    }
                    message.append("✓ Взрывчатка: ").append(selectedExplosives.size()).append(" шт.\n");
                    equipped = true;
                }
                
                if (!selectedAmmo.isEmpty()) {
                    soldier.addAmmunition(selectedAmmo);
                    message.append("✓ Амуниция: ").append(selectedAmmo.size()).append(" типов\n");
                    equipped = true;
                }
                
                message.append("\n");
            }
            
            if (equipped) {
                message.append("Все солдаты полностью экипированы!");
                JOptionPane.showMessageDialog(this, message.toString(), "Экипировка завершена", JOptionPane.INFORMATION_MESSAGE);
                updateInventoryInfo();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Пожалуйста, выберите снаряжение для экипировки.",
                    "Нет снаряжения", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Пожалуйста, выберите солдат для экипировки.",
                "Нет солдат", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Custom cell renderer for soldier list
     */
    private static class SoldierListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                   boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Soldier soldier) {
                setText(soldier.getName() + " (" + soldier.getSoldierClass() + " - Ур." + soldier.getRank() + ")");
            }
            
            return this;
        }
    }
    
    /**
     * Custom cell renderer for equipment list
     */
    private static class EquipmentListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                   boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Weapon weapon) {
                setText(weapon.getName() + " (" + weapon.getBaseDamage() + " урон, " + weapon.getAccuracy() + "% точность)");
            } else if (value instanceof Armor armor) {
                setText(armor.getName() + " (" + armor.getDamageReduction() + " защита)");
            } else if (value instanceof Explosive explosive) {
                setText(explosive.getName() + " (" + explosive.getDamage() + " урон, " + explosive.getRadius() + " радиус)");
            }
            
            return this;
        }
    }
    
    /**
     * Custom cell renderer for ammunition list
     */
    private static class AmmoListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                   boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof AmmoTypeData ammo) {
                setText(ammo.getName() + " (" + ammo.getDamageBonus() + " бонус урона, " + ammo.getCurrentUses() + " шт.)");
            }
            
            return this;
        }
    }
    
    /**
     * Get the equipped soldiers
     */
    public List<Soldier> getEquippedSoldiers() {
        return new ArrayList<>(selectedSoldiers);
    }
    
    /**
     * Get the first equipped soldier
     */
    public Soldier getFirstEquippedSoldier() {
        return selectedSoldiers.isEmpty() ? null : selectedSoldiers.get(0);
    }
    
    /**
     * Get the fully equipped soldiers for the mission
     */
    public List<Soldier> getMissionReadySoldiers() {
        if (!selectedSoldiers.isEmpty() && selectedWeapon != null && selectedArmor != null) {
            List<Soldier> missionSoldiers = new ArrayList<>();
            
            for (Soldier selectedSoldier : selectedSoldiers) {
                // Create a copy to avoid modifying the original
                Soldier missionSoldier = new Soldier(
                    selectedSoldier.getName(),
                    selectedSoldier.getMaxHealth(),
                    selectedSoldier.getMovementRange(),
                    selectedSoldier.getAttackRange(),
                    selectedSoldier.getAttackDamage(),
                    selectedSoldier.getSoldierClass()
                );
                
                // Copy other properties
                missionSoldier.setExperience(selectedSoldier.getExperience());
                missionSoldier.setRank(selectedSoldier.getRank());
                
                // Equip the soldier
                missionSoldier.equipWeapon(selectedWeapon);
                missionSoldier.equipArmor(selectedArmor);
                
                // Add explosives if selected
                if (selectedExplosives != null && !selectedExplosives.isEmpty()) {
                    for (Explosive explosive : selectedExplosives) {
                        // Create a copy of the explosive for each soldier
                        Explosive explosiveCopy = new Explosive(explosive.getName(), explosive.getType(), explosive.getDamage(), explosive.getRadius(), 0);
                        missionSoldier.addExplosive(explosiveCopy);
                    }
                }
                
                // Add ammunition if selected
                if (selectedAmmo != null && !selectedAmmo.isEmpty()) {
                    missionSoldier.addAmmunition(selectedAmmo);
                }
                
                missionSoldiers.add(missionSoldier);
            }
            
            return missionSoldiers;
        }
        return new ArrayList<>();
    }
    
    /**
     * Get the first fully equipped soldier for the mission
     */
    public Soldier getFirstMissionReadySoldier() {
        List<Soldier> readySoldiers = getMissionReadySoldiers();
        return readySoldiers.isEmpty() ? null : readySoldiers.get(0);
    }
    
    /**
     * Check if form is complete
     */
    public boolean isFormComplete() {
        return !selectedSoldiers.isEmpty() && selectedWeapon != null && selectedArmor != null;
    }
    
    /**
     * Get selected equipment summary for mission
     */
    public String getEquipmentSummary() {
        if (!isFormComplete()) {
            return "Оборудование не выбрано полностью";
        }
        
        StringBuilder summary = new StringBuilder();
        summary.append("Солдаты: ").append(selectedSoldiers.size()).append(" чел.\n");
        for (Soldier soldier : selectedSoldiers) {
            summary.append("- ").append(soldier.getName()).append(" (").append(soldier.getSoldierClass()).append(")\n");
        }
        summary.append("Оружие: ").append(selectedWeapon.getName()).append("\n");
        summary.append("Броня: ").append(selectedArmor.getName()).append("\n");
        
        if (selectedExplosives != null && !selectedExplosives.isEmpty()) {
            summary.append("Взрывчатка: ").append(selectedExplosives.size()).append(" шт.\n");
        } else {
            summary.append("Взрывчатка: не выбрана\n");
        }
        
        if (selectedAmmo != null && !selectedAmmo.isEmpty()) {
            summary.append("Амуниция: ").append(selectedAmmo.size()).append(" типов\n");
        } else {
            summary.append("Амуниция: не выбрана\n");
        }
        
        return summary.toString();
    }

    /**
     * Set the callback for when the game should start.
     */
    public void setOnGameStartCallback(Runnable callback) {
        this.onGameStartCallback = callback;
    }
    
    /**
     * Reset the form to initial state
     */
    public void resetForm() {
        // Clear selections
        soldierList.clearSelection();
        weaponList.clearSelection();
        armorList.clearSelection();
        explosiveList.clearSelection();
        ammoList.clearSelection();
        
        // Reset selected items
        selectedSoldiers.clear();
        selectedWeapon = null;
        selectedArmor = null;
        selectedExplosives.clear();
        selectedAmmo.clear();
        
        // Clear info panels
        soldierInfo.setText("");
        equipmentInfo.setText("");
        inventoryInfo.setText("");
        
        // Update button
        updateStartGameButton();
    }

    private void applyLLMStyles() {
        // Basic styling can be added here if needed
        // For now, this method is kept for future enhancements
    }
}

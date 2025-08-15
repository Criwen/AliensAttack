package com.aliensattack.ui.panels;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Simplified Soldier selection and equipment form for mission preparation
 * (without Lombok for testing purposes)
 */
public class SoldierSelectionFormSimple extends JPanel {
    
    private final List<Soldier> availableSoldiers;
    private final List<Weapon> availableWeapons;
    private final List<Armor> availableArmor;
    private final List<Explosive> availableExplosives;
    
    private JList<Soldier> soldierList;
    private JList<Weapon> weaponList;
    private JList<Armor> armorList;
    private JList<Explosive> explosiveList;
    
    private JTextArea soldierInfo;
    private JTextArea equipmentInfo;
    private JButton confirmButton;
    private JButton cancelButton;
    
    private Soldier selectedSoldier;
    private Weapon selectedWeapon;
    private Armor selectedArmor;
    private List<Explosive> selectedExplosives;
    
    public SoldierSelectionFormSimple() {
        this.availableSoldiers = new ArrayList<>();
        this.availableWeapons = new ArrayList<>();
        this.availableArmor = new ArrayList<>();
        this.availableExplosives = new ArrayList<>();
        this.selectedExplosives = new ArrayList<>();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Выбор солдат и оснащения"));
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadSampleData();
    }
    
    private void initializeComponents() {
        // Soldier selection
        soldierList = new JList<>();
        soldierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        
        // Info panels
        soldierInfo = new JTextArea();
        soldierInfo.setEditable(false);
        soldierInfo.setLineWrap(true);
        soldierInfo.setWrapStyleWord(true);
        
        equipmentInfo = new JTextArea();
        equipmentInfo.setEditable(false);
        equipmentInfo.setLineWrap(true);
        equipmentInfo.setWrapStyleWord(true);
        
        // Buttons
        confirmButton = new JButton("Подтвердить выбор");
        confirmButton.setEnabled(false);
        
        cancelButton = new JButton("Отмена");
    }
    
    private void setupLayout() {
        // Left panel - Soldier selection
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Доступные солдаты"));
        
        JScrollPane soldierScrollPane = new JScrollPane(soldierList);
        soldierScrollPane.setPreferredSize(new Dimension(300, 200));
        leftPanel.add(soldierScrollPane, BorderLayout.CENTER);
        
        JScrollPane soldierInfoScrollPane = new JScrollPane(soldierInfo);
        soldierInfoScrollPane.setPreferredSize(new Dimension(300, 150));
        leftPanel.add(soldierInfoScrollPane, BorderLayout.SOUTH);
        
        // Center panel - Equipment selection
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Выбор снаряжения"));
        
        // Weapons
        JPanel weaponPanel = new JPanel(new BorderLayout());
        weaponPanel.setBorder(BorderFactory.createTitledBorder("Оружие"));
        JScrollPane weaponScrollPane = new JScrollPane(weaponList);
        weaponScrollPane.setPreferredSize(new Dimension(250, 120));
        weaponPanel.add(weaponScrollPane, BorderLayout.CENTER);
        centerPanel.add(weaponPanel);
        
        // Armor
        JPanel armorPanel = new JPanel(new BorderLayout());
        armorPanel.setBorder(BorderFactory.createTitledBorder("Броня"));
        JScrollPane armorScrollPane = new JScrollPane(armorList);
        armorScrollPane.setPreferredSize(new Dimension(250, 120));
        armorPanel.add(armorScrollPane, BorderLayout.CENTER);
        centerPanel.add(armorPanel);
        
        // Explosives
        JPanel explosivePanel = new JPanel(new BorderLayout());
        explosivePanel.setBorder(BorderFactory.createTitledBorder("Взрывчатка"));
        JScrollPane explosiveScrollPane = new JScrollPane(explosiveList);
        explosiveScrollPane.setPreferredSize(new Dimension(250, 120));
        explosivePanel.add(explosiveScrollPane, BorderLayout.CENTER);
        centerPanel.add(explosivePanel);
        
        // Right panel - Equipment info and controls
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Информация"));
        
        JScrollPane equipmentInfoScrollPane = new JScrollPane(equipmentInfo);
        equipmentInfoScrollPane.setPreferredSize(new Dimension(300, 300));
        rightPanel.add(equipmentInfoScrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Main layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Soldier selection
        soldierList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Soldier soldier = soldierList.getSelectedValue();
                if (soldier != null) {
                    selectedSoldier = soldier;
                    updateSoldierInfo(soldier);
                    updateConfirmButton();
                }
            }
        });
        
        // Weapon selection
        weaponList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Weapon weapon = weaponList.getSelectedValue();
                if (weapon != null) {
                    selectedWeapon = weapon;
                    updateEquipmentInfo();
                    updateConfirmButton();
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
                    updateConfirmButton();
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
                updateConfirmButton();
            }
        });
        
        // Buttons
        confirmButton.addActionListener(e -> handleConfirm());
        cancelButton.addActionListener(e -> handleCancel());
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
    
    private void updateSoldierInfo(Soldier soldier) {
        StringBuilder info = new StringBuilder();
        info.append("Имя: ").append(soldier.getName()).append("\n");
        info.append("Класс: ").append(soldier.getSoldierClass()).append("\n");
        info.append("Уровень: ").append(soldier.getRank()).append("\n");
        info.append("Опыт: ").append(soldier.getExperience()).append("\n");
        info.append("Здоровье: ").append(soldier.getCurrentHealth()).append("/").append(soldier.getMaxHealth()).append("\n");
        info.append("Движение: ").append(soldier.getMovementRange()).append("\n");
        info.append("Атака: ").append(soldier.getAttackRange()).append(" (дальность)\n");
        info.append("Урон: ").append(soldier.getAttackDamage()).append("\n");
        
        if (soldier.getAbilities() != null && !soldier.getAbilities().isEmpty()) {
            info.append("\nСпособности:\n");
            for (SoldierAbility ability : soldier.getAbilities()) {
                info.append("- ").append(ability.getName()).append("\n");
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
    
    private void updateConfirmButton() {
        boolean canConfirm = selectedSoldier != null && selectedWeapon != null && selectedArmor != null;
        confirmButton.setEnabled(canConfirm);
    }
    
    private void handleConfirm() {
        if (selectedSoldier != null && selectedWeapon != null && selectedArmor != null) {
            // Equip the soldier
            selectedSoldier.equipWeapon(selectedWeapon);
            selectedSoldier.equipArmor(selectedArmor);
            
            // Add explosives - using EquipmentManager if available, otherwise store for later
            if (selectedExplosives != null && !selectedExplosives.isEmpty()) {
                // Store selected explosives for the soldier
                // Note: In a full implementation, this would use EquipmentManager
                String explosiveInfo = "Выбрано " + selectedExplosives.size() + " взрывчатки для " + selectedSoldier.getName();
                equipmentInfo.setText(equipmentInfo.getText() + "\n\n" + explosiveInfo);
            }
            
            JOptionPane.showMessageDialog(this, 
                "Солдат " + selectedSoldier.getName() + " экипирован для операции!",
                "Подтверждение", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void handleCancel() {
        // Reset selections
        soldierList.clearSelection();
        weaponList.clearSelection();
        armorList.clearSelection();
        explosiveList.clearSelection();
        
        selectedSoldier = null;
        selectedWeapon = null;
        selectedArmor = null;
        selectedExplosives.clear();
        
        soldierInfo.setText("");
        equipmentInfo.setText("");
        updateConfirmButton();
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
     * Get the equipped soldier
     */
    public Soldier getEquippedSoldier() {
        return selectedSoldier;
    }
    
    /**
     * Check if form is complete
     */
    public boolean isFormComplete() {
        return selectedSoldier != null && selectedWeapon != null && selectedArmor != null;
    }
}

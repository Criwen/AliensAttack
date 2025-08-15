package com.aliensattack.ui.panels;

import com.aliensattack.core.systems.InventorySystem;
import com.aliensattack.core.systems.InventorySystem.InventoryItem;
import com.aliensattack.core.systems.InventorySystem.InventoryStats;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Панель инвентаря для отображения доступного снаряжения
 */
@Log4j2
public class InventoryPanel extends JPanel {
    
    private final InventorySystem inventorySystem;
    
    // UI компоненты
    private JTabbedPane categoryTabs;
    private JTable itemsTable;
    private JTextArea itemDetails;
    private JTextField searchField;
    private JLabel statsLabel;
    
    // Модель таблицы
    private DefaultTableModel tableModel;
    
    public InventoryPanel() {
        this.inventorySystem = new InventorySystem();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Инвентарь - Доступное снаряжение"));
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        refreshInventory();
    }
    
    private void initializeComponents() {
        // Создаем модель таблицы
        String[] columnNames = {"Название", "Категория", "Количество", "Местоположение", "Стоимость", "Вес"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Таблица только для чтения
            }
        };
        
        // Таблица предметов
        itemsTable = new JTable(tableModel);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsTable.getTableHeader().setReorderingAllowed(false);
        
        // Панель деталей предмета
        itemDetails = new JTextArea();
        itemDetails.setEditable(false);
        itemDetails.setLineWrap(true);
        itemDetails.setWrapStyleWord(true);
        itemDetails.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Поле поиска
        searchField = new JTextField(20);
        searchField.setToolTipText("Введите название предмета для поиска");
        
        // Метка статистики
        statsLabel = new JLabel("Загрузка статистики...");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        // Вкладки категорий
        categoryTabs = new JTabbedPane();
        setupCategoryTabs();
    }
    
    private void setupCategoryTabs() {
        // Вкладка "Все предметы"
        JPanel allItemsPanel = new JPanel(new BorderLayout());
        allItemsPanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);
        categoryTabs.addTab("Все предметы", allItemsPanel);
        
        // Вкладка "Оружие"
        JPanel weaponsPanel = createCategoryPanel("WEAPON");
        categoryTabs.addTab("Оружие", weaponsPanel);
        
        // Вкладка "Броня"
        JPanel armorPanel = createCategoryPanel("ARMOR");
        categoryTabs.addTab("Броня", armorPanel);
        
        // Вкладка "Взрывчатка"
        JPanel explosivesPanel = createCategoryPanel("EXPLOSIVE");
        categoryTabs.addTab("Взрывчатка", explosivesPanel);
        
        // Вкладка "Медицина"
        JPanel medicalPanel = createCategoryPanel("MEDICAL");
        categoryTabs.addTab("Медицина", medicalPanel);
        
        // Вкладка "Техника"
        JPanel technicalPanel = createCategoryPanel("TECHNICAL");
        categoryTabs.addTab("Техника", technicalPanel);
        
        // Вкладка "Амуниция"
        JPanel ammoPanel = createCategoryPanel("AMMO");
        categoryTabs.addTab("Амуниция", ammoPanel);
    }
    
    private JPanel createCategoryPanel(String category) {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Создаем таблицу для категории
        String[] columnNames = {"Название", "Количество", "Местоположение", "Стоимость", "Вес"};
        DefaultTableModel categoryModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable categoryTable = new JTable(categoryModel);
        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryTable.getTableHeader().setReorderingAllowed(false);
        
        // Заполняем данными
        List<InventoryItem> items = inventorySystem.getItemsByCategory(category);
        for (InventoryItem item : items) {
            categoryModel.addRow(new Object[]{
                item.getItemName(),
                item.getQuantity(),
                item.getLocation(),
                item.getCost(),
                String.format("%.1f", item.getWeight())
            });
        }
        
        panel.add(new JScrollPane(categoryTable), BorderLayout.CENTER);
        
        // Добавляем обработчик выбора
        categoryTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = categoryTable.getSelectedRow();
                if (selectedRow >= 0) {
                    InventoryItem selectedItem = items.get(selectedRow);
                    displayItemDetails(selectedItem);
                }
            }
        });
        
        return panel;
    }
    
    private void setupLayout() {
        // Верхняя панель с поиском и статистикой
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // Панель поиска
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Поиск:"));
        searchPanel.add(searchField);
        topPanel.add(searchPanel, BorderLayout.WEST);
        
        // Панель статистики
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statsPanel.add(statsLabel);
        topPanel.add(statsPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Центральная панель с вкладками и деталями
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(categoryTabs);
        splitPane.setRightComponent(createDetailsPanel());
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.7);
        
        add(splitPane, BorderLayout.CENTER);
    }
    
    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Детали предмета"));
        detailsPanel.setPreferredSize(new Dimension(300, 400));
        
        // Панель деталей
        JScrollPane detailsScrollPane = new JScrollPane(itemDetails);
        detailsScrollPane.setPreferredSize(new Dimension(280, 350));
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);
        
        // Панель действий
        JPanel actionsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Действия"));
        
        JButton equipButton = new JButton("Экипировать");
        JButton moveButton = new JButton("Переместить");
        JButton infoButton = new JButton("Информация");
        JButton refreshButton = new JButton("Обновить");
        
        actionsPanel.add(equipButton);
        actionsPanel.add(moveButton);
        actionsPanel.add(infoButton);
        actionsPanel.add(refreshButton);
        
        detailsPanel.add(actionsPanel, BorderLayout.SOUTH);
        
        return detailsPanel;
    }
    
    private void setupEventHandlers() {
        // Обработчик поиска
        searchField.addActionListener(e -> performSearch());
        
        // Обработчик выбора в основной таблице
        itemsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = itemsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String itemName = (String) itemsTable.getValueAt(selectedRow, 0);
                    InventoryItem selectedItem = findItemByName(itemName);
                    if (selectedItem != null) {
                        displayItemDetails(selectedItem);
                    }
                }
            }
        });
    }
    
    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            refreshInventory();
            return;
        }
        
        List<InventoryItem> searchResults = inventorySystem.searchItemsByName(searchTerm);
        updateItemsTable(searchResults);
        
        if (searchResults.isEmpty()) {
            itemDetails.setText("Поиск не дал результатов для: " + searchTerm);
        }
    }
    
    private void refreshInventory() {
        List<InventoryItem> allItems = inventorySystem.getAllAvailableItems();
        updateItemsTable(allItems);
        updateStats();
    }
    
    private void updateItemsTable(List<InventoryItem> items) {
        tableModel.setRowCount(0);
        
        for (InventoryItem item : items) {
            tableModel.addRow(new Object[]{
                item.getItemName(),
                item.getCategory(),
                item.getQuantity(),
                item.getLocation(),
                item.getCost(),
                String.format("%.1f", item.getWeight())
            });
        }
    }
    
    private void updateStats() {
        InventoryStats stats = inventorySystem.getInventoryStats();
        
        StringBuilder statsText = new StringBuilder();
        statsText.append("Всего предметов: ").append(stats.getTotalItems());
        statsText.append(" | Общая стоимость: ").append(String.format("%.0f", stats.getTotalValue()));
        statsText.append(" | Категории: ");
        
        for (Map.Entry<String, Long> entry : stats.getCategoryCounts().entrySet()) {
            statsText.append(entry.getKey()).append("(").append(entry.getValue()).append(") ");
        }
        
        statsLabel.setText(statsText.toString());
    }
    
    private void displayItemDetails(InventoryItem item) {
        if (item == null) {
            itemDetails.setText("Выберите предмет для просмотра деталей");
            return;
        }
        
        StringBuilder details = new StringBuilder();
        details.append("=== ").append(item.getItemName()).append(" ===\n\n");
        details.append("ID: ").append(item.getItemId()).append("\n");
        details.append("Категория: ").append(item.getCategory()).append("\n");
        details.append("Подкатегория: ").append(item.getSubcategory()).append("\n");
        details.append("Количество: ").append(item.getQuantity()).append("\n");
        details.append("Местоположение: ").append(item.getLocation()).append("\n");
        details.append("Редкость: ").append(item.getRarity()).append("\n");
        details.append("Стоимость: ").append(item.getCost()).append(" кредитов\n");
        details.append("Вес: ").append(String.format("%.1f", item.getWeight())).append(" кг\n");
        details.append("Описание: ").append(item.getDescription()).append("\n\n");
        
        if (item.getStats() != null && !item.getStats().isEmpty()) {
            details.append("=== ХАРАКТЕРИСТИКИ ===\n");
            for (Map.Entry<String, Integer> stat : item.getStats().entrySet()) {
                details.append(stat.getKey()).append(": ").append(stat.getValue()).append("\n");
            }
        }
        
        if (item.isEquipped()) {
            details.append("\nСтатус: Экипирован");
            if (item.getAssignedTo() != null) {
                details.append(" (").append(item.getAssignedTo()).append(")");
            }
        } else {
            details.append("\nСтатус: Доступен");
        }
        
        itemDetails.setText(details.toString());
    }
    
    private InventoryItem findItemByName(String itemName) {
        return inventorySystem.getAllAvailableItems().stream()
            .filter(item -> item.getItemName().equals(itemName))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Получить систему инвентаря
     */
    public InventorySystem getInventorySystem() {
        return inventorySystem;
    }
    
    /**
     * Обновить отображение инвентаря
     */
    public void refresh() {
        refreshInventory();
    }
}

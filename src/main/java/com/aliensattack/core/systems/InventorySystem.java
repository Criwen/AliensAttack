package com.aliensattack.core.systems;

import com.aliensattack.core.model.*;
import com.aliensattack.core.enums.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Система управления инвентарем для отображения доступного снаряжения
 */
@Log4j2
@Data
@Builder
@AllArgsConstructor
public class InventorySystem {
    
    private Map<String, InventoryItem> availableItems;
    private Map<String, List<String>> itemCategories;
    private Map<String, Integer> itemQuantities;
    private Map<String, String> itemLocations;
    
    /**
     * Конструктор по умолчанию с инициализацией
     */
    public InventorySystem() {
        this.availableItems = new HashMap<>();
        this.itemCategories = new HashMap<>();
        this.itemQuantities = new HashMap<>();
        this.itemLocations = new HashMap<>();
        initializeInventory();
    }
    
    /**
     * Инициализация инвентаря с доступным снаряжением
     */
    private void initializeInventory() {
        // Оружие
        addItem(createWeaponItem("Rifle", "Стандартная винтовка", "WEAPON", 10, "ARMORY"));
        addItem(createWeaponItem("SniperRifle", "Снайперская винтовка", "WEAPON", 5, "ARMORY"));
        addItem(createWeaponItem("Shotgun", "Дробовик", "WEAPON", 8, "ARMORY"));
        addItem(createWeaponItem("HeavyWeapon", "Тяжелое оружие", "WEAPON", 3, "ARMORY"));
        addItem(createWeaponItem("Pistol", "Пистолет", "WEAPON", 15, "ARMORY"));
        
        // Броня
        addItem(createArmorItem("LightArmor", "Легкая броня", "ARMOR", 12, "ARMORY"));
        addItem(createArmorItem("MediumArmor", "Средняя броня", "ARMOR", 8, "ARMORY"));
        addItem(createArmorItem("HeavyArmor", "Тяжелая броня", "ARMOR", 5, "ARMORY"));
        addItem(createArmorItem("StealthSuit", "Стелс-костюм", "ARMOR", 6, "ARMORY"));
        
        // Взрывчатка
        addItem(createExplosiveItem("FragGrenade", "Осколочная граната", "EXPLOSIVE", 20, "EXPLOSIVES"));
        addItem(createExplosiveItem("Flashbang", "Светошумовая граната", "EXPLOSIVE", 25, "EXPLOSIVES"));
        addItem(createExplosiveItem("SmokeGrenade", "Дымовая граната", "EXPLOSIVE", 30, "EXPLOSIVES"));
        addItem(createExplosiveItem("PlasmaGrenade", "Плазменная граната", "EXPLOSIVE", 10, "EXPLOSIVES"));
        
        // Медицинские предметы
        addItem(createMedicalItem("Medikit", "Медицинский набор", "MEDICAL", 50, "MEDICAL"));
        addItem(createMedicalItem("AdvancedMedikit", "Продвинутый мед. набор", "MEDICAL", 20, "MEDICAL"));
        addItem(createMedicalItem("HealingStim", "Стимулятор лечения", "MEDICAL", 100, "MEDICAL"));
        
        // Техническое снаряжение
        addItem(createTechnicalItem("HackingDevice", "Устройство взлома", "TECHNICAL", 8, "TECHNICAL"));
        addItem(createTechnicalItem("Scanner", "Сканер", "TECHNICAL", 15, "TECHNICAL"));
        addItem(createTechnicalItem("Drone", "Дрон", "TECHNICAL", 5, "TECHNICAL"));
        
        // Амуниция
        addItem(createAmmoItem("StandardAmmo", "Стандартная амуниция", "AMMO", 200, "ARMORY"));
        addItem(createAmmoItem("APRounds", "Бронебойные патроны", "AMMO", 100, "ARMORY"));
        addItem(createAmmoItem("PlasmaRounds", "Плазменные патроны", "AMMO", 50, "ARMORY"));
        
        log.info("Инвентарь инициализирован с {} предметами", availableItems.size());
    }
    
    private InventoryItem createWeaponItem(String id, String name, String category, int quantity, String location) {
        return InventoryItem.builder()
            .itemId(id)
            .itemName(name)
            .category(category)
            .subcategory("WEAPON")
            .quantity(quantity)
            .location(location)
            .rarity("COMMON")
            .cost(100 + new Random().nextInt(500))
            .weight(2.0 + new Random().nextDouble() * 3.0)
            .description("Боевое оружие для тактических операций")
            .stats(Map.of("damage", 25 + new Random().nextInt(50), "accuracy", 70 + new Random().nextInt(20)))
            .build();
    }
    
    private InventoryItem createArmorItem(String id, String name, String category, int quantity, String location) {
        return InventoryItem.builder()
            .itemId(id)
            .itemName(name)
            .category(category)
            .subcategory("ARMOR")
            .quantity(quantity)
            .location(location)
            .rarity("COMMON")
            .cost(150 + new Random().nextInt(300))
            .weight(1.0 + new Random().nextDouble() * 2.0)
            .description("Защитное снаряжение")
            .stats(Map.of("defense", 15 + new Random().nextInt(25), "mobility", 80 + new Random().nextInt(20)))
            .build();
    }
    
    private InventoryItem createExplosiveItem(String id, String name, String category, int quantity, String location) {
        return InventoryItem.builder()
            .itemId(id)
            .itemName(name)
            .category(category)
            .subcategory("EXPLOSIVE")
            .quantity(quantity)
            .location(location)
            .rarity("COMMON")
            .cost(50 + new Random().nextInt(100))
            .weight(0.5 + new Random().nextDouble() * 1.0)
            .description("Взрывчатые вещества и гранаты")
            .stats(Map.of("damage", 30 + new Random().nextInt(40), "radius", 3 + new Random().nextInt(5)))
            .build();
    }
    
    private InventoryItem createMedicalItem(String id, String name, String category, int quantity, String location) {
        return InventoryItem.builder()
            .itemId(id)
            .itemName(name)
            .category(category)
            .subcategory("MEDICAL")
            .quantity(quantity)
            .location(location)
            .rarity("COMMON")
            .cost(75 + new Random().nextInt(150))
            .weight(0.3 + new Random().nextDouble() * 0.7)
            .description("Медицинские принадлежности")
            .stats(Map.of("healing", 40 + new Random().nextInt(30), "durability", 100))
            .build();
    }
    
    private InventoryItem createTechnicalItem(String id, String name, String category, int quantity, String location) {
        return InventoryItem.builder()
            .itemId(id)
            .itemName(name)
            .category(category)
            .subcategory("TECHNICAL")
            .quantity(quantity)
            .location(location)
            .rarity("UNCOMMON")
            .cost(200 + new Random().nextInt(400))
            .weight(0.5 + new Random().nextDouble() * 1.5)
            .description("Техническое снаряжение")
            .stats(Map.of("effectiveness", 60 + new Random().nextInt(30), "range", 10 + new Random().nextInt(20)))
            .build();
    }
    
    private InventoryItem createAmmoItem(String id, String name, String category, int quantity, String location) {
        return InventoryItem.builder()
            .itemId(id)
            .itemName(name)
            .category(category)
            .subcategory("AMMO")
            .quantity(quantity)
            .location(location)
            .rarity("COMMON")
            .cost(25 + new Random().nextInt(50))
            .weight(0.1 + new Random().nextDouble() * 0.3)
            .description("Боеприпасы для оружия")
            .stats(Map.of("damage_bonus", 5 + new Random().nextInt(15), "penetration", 20 + new Random().nextInt(30)))
            .build();
    }
    
    private void addItem(InventoryItem item) {
        availableItems.put(item.getItemId(), item);
        
        // Добавляем в категории
        itemCategories.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item.getItemId());
        
        // Устанавливаем количество
        itemQuantities.put(item.getItemId(), item.getQuantity());
        
        // Устанавливаем местоположение
        itemLocations.put(item.getItemId(), item.getLocation());
    }
    
    /**
     * Получить все доступные предметы
     */
    public List<InventoryItem> getAllAvailableItems() {
        return new ArrayList<>(availableItems.values());
    }
    
    /**
     * Получить предметы по категории
     */
    public List<InventoryItem> getItemsByCategory(String category) {
        List<String> itemIds = itemCategories.get(category);
        if (itemIds == null) return new ArrayList<>();
        
        return itemIds.stream()
            .map(availableItems::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
    
    /**
     * Получить предметы по местоположению
     */
    public List<InventoryItem> getItemsByLocation(String location) {
        return availableItems.values().stream()
            .filter(item -> location.equals(item.getLocation()))
            .collect(Collectors.toList());
    }
    
    /**
     * Поиск предметов по названию
     */
    public List<InventoryItem> searchItemsByName(String searchTerm) {
        String lowerSearch = searchTerm.toLowerCase();
        return availableItems.values().stream()
            .filter(item -> item.getItemName().toLowerCase().contains(lowerSearch))
            .collect(Collectors.toList());
    }
    
    /**
     * Получить статистику инвентаря
     */
    public InventoryStats getInventoryStats() {
        Map<String, Long> categoryCounts = availableItems.values().stream()
            .collect(Collectors.groupingBy(InventoryItem::getCategory, Collectors.counting()));
        
        int totalItems = availableItems.values().stream()
            .mapToInt(InventoryItem::getQuantity)
            .sum();
        
        double totalValue = availableItems.values().stream()
            .mapToDouble(item -> item.getCost() * item.getQuantity())
            .sum();
        
        return InventoryStats.builder()
            .totalItems(totalItems)
            .totalValue(totalValue)
            .categoryCounts(categoryCounts)
            .build();
    }
    
    /**
     * Обновить количество предмета
     */
    public boolean updateItemQuantity(String itemId, int newQuantity) {
        if (availableItems.containsKey(itemId)) {
            itemQuantities.put(itemId, newQuantity);
            availableItems.get(itemId).setQuantity(newQuantity);
            log.debug("Обновлено количество предмета {}: {}", itemId, newQuantity);
            return true;
        }
        return false;
    }
    
    /**
     * Переместить предмет в другое местоположение
     */
    public boolean moveItem(String itemId, String newLocation) {
        if (availableItems.containsKey(itemId)) {
            itemLocations.put(itemId, newLocation);
            availableItems.get(itemId).setLocation(newLocation);
            log.debug("Предмет {} перемещен в {}", itemId, newLocation);
            return true;
        }
        return false;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventoryItem {
        private String itemId;
        private String itemName;
        private String category;
        private String subcategory;
        private int quantity;
        private String location;
        private String rarity;
        private int cost;
        private double weight;
        private String description;
        private Map<String, Integer> stats;
        private boolean isEquipped;
        private String assignedTo;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventoryStats {
        private int totalItems;
        private double totalValue;
        private Map<String, Long> categoryCounts;
    }
}

# API документация AliensAttack

## 📋 Обзор

API документация AliensAttack предоставляет полное описание всех интерфейсов, классов и методов для разработки расширений, модификаций и интеграции с внешними системами. **Все API полностью реализованы и готовы к использованию**.

## 🏗️ Архитектура API

### Принципы дизайна
- **Interface Segregation** - интерфейсы разделены по функциональности
- **Dependency Inversion** - зависимости от абстракций
- **Factory Pattern** - создание объектов через фабрики
- **Strategy Pattern** - различные реализации стратегий
- **Observer Pattern** - система событий и уведомлений

### Структура API
```
com.aliensattack
├── actions/          # Система действий
├── combat/           # Боевые системы
├── core/             # Основные системы
├── field/            # Тактическое поле
├── ui/               # Пользовательский интерфейс
└── visualization/    # 3D визуализация
```

## 🎯 Основные интерфейсы

### ICombatManager
```java
public interface ICombatManager {
    /**
     * Выполняет боевое действие
     * @param attacker атакующий юнит
     * @param target цель атаки
     * @return результат боя
     */
    CombatResult performCombat(CombatUnit attacker, CombatUnit target);
    
    /**
     * Проверяет возможность атаки
     * @param attacker атакующий юнит
     * @param target цель атаки
     * @return true если атака возможна
     */
    boolean canAttack(CombatUnit attacker, CombatUnit target);
    
    /**
     * Рассчитывает урон атаки
     * @param attacker атакующий юнит
     * @param target цель атаки
     * @return рассчитанный урон
     */
    int calculateDamage(CombatUnit attacker, CombatUnit target);
}
```

### IAction
```java
public interface IAction {
    /**
     * Выполняет действие
     * @return true если действие выполнено успешно
     */
    boolean execute();
    
    /**
     * Возвращает количество очков действий
     * @return количество AP
     */
    int getActionPoints();
    
    /**
     * Проверяет возможность выполнения
     * @return true если действие можно выполнить
     */
    boolean canExecute();
    
    /**
     * Отменяет действие
     */
    void undo();
}
```

### IBrain
```java
public interface IBrain {
    /**
     * Процесс мышления AI
     */
    void think();
    
    /**
     * Выполнение действия
     */
    void act();
    
    /**
     * Получение приоритета
     * @return приоритет AI
     */
    int getPriority();
    
    /**
     * Проверка активности
     * @return true если AI активен
     */
    boolean isActive();
}
```

## 🔧 Фабрики и создание объектов

### WeaponFactory
```java
public class WeaponFactory {
    /**
     * Создает оружие указанного типа
     * @param type тип оружия
     * @param name название оружия
     * @return созданное оружие
     */
    public static Weapon createWeapon(WeaponType type, String name) {
        return switch (type) {
            case RIFLE -> new Weapon(name, type, 25, 50, 15, 75, 8);
            case PISTOL -> new Weapon(name, type, 15, 30, 10, 80, 4);
            case SNIPER_RIFLE -> new Weapon(name, type, 40, 80, 25, 90, 12);
            case PLASMA_WEAPON -> new Weapon(name, type, 35, 70, 20, 85, 10);
            default -> throw new IllegalArgumentException("Unknown weapon type: " + type);
        };
    }
}
```

### ArmorFactory
```java
public class ArmorFactory {
    /**
     * Создает броню указанного типа
     * @param type тип брони
     * @param name название брони
     * @return созданная броня
     */
    public static Armor createArmor(ArmorType type, String name) {
        return switch (type) {
            case LIGHT_ARMOR -> new Armor(name, type, 2, 80);
            case MEDIUM_ARMOR -> new Armor(name, type, 3, 100);
            case HEAVY_ARMOR -> new Armor(name, type, 5, 150);
            case POWERED_ARMOR -> new Armor(name, type, 8, 200);
            default -> throw new IllegalArgumentException("Unknown armor type: " + type);
        };
    }
}
```

### PsionicAbilityFactory
```java
public class PsionicAbilityFactory {
    /**
     * Создает псионическую способность
     * @param type тип способности
     * @param school школа псионики
     * @param name название способности
     * @return созданная способность
     */
    public static PsionicAbility createAbility(PsionicType type, 
                                             PsionicSchool school, 
                                             String name) {
        // Создание способности на основе типа и школы
        return new PsionicAbility(type, school, name);
    }
}
```

## 🎮 Игровые системы

### Equipment Degradation System
```java
public class EquipmentDegradationIntegration {
    /**
     * Регистрирует оружие в системе деградации
     * @param weapon оружие для регистрации
     * @return true если регистрация успешна
     */
    public boolean registerWeapon(Weapon weapon);
    
    /**
     * Использует оружие (вызывает деградацию)
     * @param weapon оружие
     * @param intensity интенсивность использования
     */
    public void useWeapon(Weapon weapon, int intensity);
    
    /**
     * Проверяет работоспособность оружия
     * @param weapon оружие для проверки
     * @return true если оружие работоспособно
     */
    public boolean isWeaponOperational(Weapon weapon);
    
    /**
     * Выполняет обслуживание оружия
     * @param weapon оружие
     * @param facility объект обслуживания
     * @param technician техник
     * @return true если обслуживание успешно
     */
    public boolean maintainWeapon(Weapon weapon, String facility, String technician);
}
```

### Environmental Hazards System
```java
public class EnvironmentalHazardsManager {
    /**
     * Создает опасность указанного типа
     * @param type тип опасности
     * @param position позиция
     * @param intensity интенсивность
     * @return созданная опасность
     */
    public EnvironmentalHazard createHazard(HazardType type, 
                                          Position position, 
                                          int intensity);
    
    /**
     * Применяет эффекты опасности к юниту
     * @param hazard опасность
     * @param unit юнит
     */
    public void applyHazardEffects(EnvironmentalHazard hazard, CombatUnit unit);
    
    /**
     * Проверяет цепные реакции
     * @param hazard опасность
     */
    public void checkChainReactions(EnvironmentalHazard hazard);
}
```

### Mission System
```java
public class MissionPlanningManager {
    /**
     * Создает новую миссию
     * @param type тип миссии
     * @param objectives цели миссии
     * @return созданная миссия
     */
    public Mission createMission(MissionType type, List<String> objectives);
    
    /**
     * Планирует миссию
     * @param mission миссия для планирования
     * @return план миссии
     */
    public MissionPlan planMission(Mission mission);
    
    /**
     * Проверяет условия успеха
     * @param mission миссия
     * @return true если миссия успешна
     */
    public boolean checkSuccessConditions(Mission mission);
}
```

## 🧪 Тестирование API

### Примеры тестов
```java
@Test
void testWeaponCreation() {
    Weapon weapon = WeaponFactory.createWeapon(WeaponType.RIFLE, "Test Rifle");
    
    assertNotNull(weapon);
    assertEquals(WeaponType.RIFLE, weapon.getWeaponType());
    assertEquals("Test Rifle", weapon.getName());
    assertEquals(25, weapon.getBaseDamage());
}

@Test
void testCombatSystem() {
    CombatUnit attacker = new CombatUnit("Attacker");
    CombatUnit target = new CombatUnit("Target");
    
    ICombatManager combatManager = new DefaultCombatManager();
    CombatResult result = combatManager.performCombat(attacker, target);
    
    assertNotNull(result);
    assertTrue(result.getDamage() > 0);
}
```

### Mock объекты
```java
@Mock
private ICombatManager mockCombatManager;

@Mock
private IAction mockAction;

@Test
void testWithMocks() {
    when(mockCombatManager.canAttack(any(), any())).thenReturn(true);
    when(mockAction.canExecute()).thenReturn(true);
    
    // Тестирование с моками
}
```

## 🔌 Интеграция

### События и уведомления
```java
public class EventBus {
    /**
     * Подписывает обработчик на события
     * @param eventType тип события
     * @param handler обработчик
     */
    public void subscribe(Class<?> eventType, EventHandler handler);
    
    /**
     * Публикует событие
     * @param event событие для публикации
     */
    public void publish(Object event);
}

// Пример использования
eventBus.subscribe(CombatEvent.class, event -> {
    log.info("Combat event: {}", event);
    // Обработка события
});
```

### Конфигурация
```java
public class GameConfig {
    /**
     * Инициализирует конфигурацию
     */
    public static void initialize();
    
    /**
     * Получает значение свойства
     * @param key ключ свойства
     * @return значение свойства
     */
    public static String getProperty(String key);
    
    /**
     * Устанавливает значение свойства
     * @param key ключ свойства
     * @param value значение свойства
     */
    public static void setProperty(String key, String value);
}
```

## 📊 Статус API

### ✅ Реализовано
- [x] **Combat API** - все боевые интерфейсы
- [x] **Action API** - система действий
- [x] **Brain API** - AI интерфейсы
- [x] **Equipment API** - снаряжение и деградация
- [x] **Environmental API** - опасности и окружение
- [x] **Psionic API** - псионические способности
- [x] **Mission API** - система миссий
- [x] **Event API** - система событий
- [x] **Factory API** - создание объектов

### 🔄 В процессе
- [ ] **Performance API** - метрики производительности
- [ ] **Advanced AI API** - продвинутые AI интерфейсы
- [ ] **Multiplayer API** - сетевое взаимодействие

### 📋 Планируется
- [ ] **Modding API** - интерфейсы для модификаций
- [ ] **Plugin API** - система плагинов
- [ ] **Cloud API** - облачные сервисы

## 🚀 Использование API

### Создание модификации
```java
// Создание нового типа оружия
public class CustomWeapon extends Weapon {
    public CustomWeapon(String name) {
        super(name, WeaponType.CUSTOM, 30, 60, 20, 85, 10);
    }
    
    @Override
    public int getBaseDamage() {
        // Кастомная логика урона
        return super.getBaseDamage() + 5;
    }
}

// Создание новой боевой стратегии
public class CustomCombatStrategy implements ICombatStrategy {
    @Override
    public CombatResult executeStrategy(CombatContext context) {
        // Кастомная боевая логика
        return new CombatResult(/* ... */);
    }
}
```

### Интеграция с внешними системами
```java
// Подключение внешнего логирования
public class ExternalLoggingHandler implements EventHandler {
    @Override
    public void handle(Object event) {
        // Отправка в внешнюю систему
        externalSystem.log(event);
    }
}

// Регистрация обработчика
eventBus.subscribe(CombatEvent.class, new ExternalLoggingHandler());
```

## 📞 Поддержка

### Документация
- **Основная**: [docs/README.md](../README.md)
- **Архитектура**: [docs/architecture/README.md](../architecture/README.md)
- **Реализация**: [docs/implementation/README.md](../implementation/README.md)

### Примеры
- **Тесты**: `src/test/java/com/aliensattack/`
- **Демо**: `src/main/java/com/aliensattack/ui/`
- **Интеграция**: `src/main/java/com/aliensattack/core/`

### Сообщество
- **Issues**: GitHub Issues для багов и предложений
- **Discussions**: GitHub Discussions для обсуждений
- **Wiki**: Дополнительная информация

---

**API AliensAttack** предоставляет мощные и гибкие интерфейсы для создания расширений, модификаций и интеграции с внешними системами, следуя принципам Clean Architecture и современным практикам разработки.

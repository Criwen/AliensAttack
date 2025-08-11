# API документация AliensAttack

## 📋 Содержание

- [Обзор API](#обзор-api)
- [Принципы API](#принципы-api)
- [Структура API](#структура-api)
- [Основные интерфейсы](#основные-интерфейсы)
- [Примеры использования](#примеры-использования)
- [Обработка ошибок](#обработка-ошибок)
- [Версионирование](#версионирование)

## 🔌 Обзор API

API AliensAttack предоставляет программный интерфейс для взаимодействия с тактической боевой системой. API построен на принципах чистой архитектуры и обеспечивает гибкость, расширяемость и простоту использования.

### 🎯 Цели API

- **Простота использования** - интуитивно понятные интерфейсы
- **Гибкость** - поддержка различных сценариев использования
- **Расширяемость** - возможность добавления новых функций
- **Производительность** - оптимизированные операции
- **Надежность** - надежная обработка ошибок

### 🏗️ Архитектура API

```
┌─────────────────────────────────────┐
│           Public API                │ ← Внешний интерфейс
├─────────────────────────────────────┤
│           Internal API              │ ← Внутренние интерфейсы
├─────────────────────────────────────┤
│           Core API                  │ ← Базовые интерфейсы
└─────────────────────────────────────┘
```

## 🧩 Принципы API

### Принцип единственной ответственности
Каждый интерфейс отвечает за одну конкретную область:

```java
// Боевая система
public interface ICombatManager {
    CombatResult executeCombat(Unit attacker, Unit target);
}

// Система видимости
public interface IVisibilitySystem {
    boolean hasLineOfSight(Position from, Position to);
}

// Система действий
public interface IActionManager {
    boolean canExecuteAction(Unit unit, ActionType action);
    void executeAction(Unit unit, ActionType action, Position target);
}
```

### Принцип открытости/закрытости
API открыт для расширения, но закрыт для модификации:

```java
// Базовый интерфейс
public interface ICombatManager {
    CombatResult executeCombat(Unit attacker, Unit target);
}

// Расширение без изменения базового интерфейса
public interface IAdvancedCombatManager extends ICombatManager {
    CombatResult executeCombatWithCover(Unit attacker, Unit target, CoverType cover);
    CombatResult executeCombatWithTerrain(Unit attacker, Unit target, TerrainType terrain);
}
```

### Принцип подстановки Лисков
Все реализации интерфейса взаимозаменяемы:

```java
// Базовый тип
public interface ICombatManager {
    CombatResult executeCombat(Unit attacker, Unit target);
}

// Реализации взаимозаменяемы
public class BasicCombatManager implements ICombatManager { }
public class AdvancedCombatManager implements ICombatManager { }
public class XCOM2CombatManager implements ICombatManager { }
```

### Принцип разделения интерфейсов
Клиенты не зависят от неиспользуемых методов:

```java
// Специализированные интерфейсы
public interface ICombatOperations {
    CombatResult executeCombat(Unit attacker, Unit target);
}

public interface IVisibilityOperations {
    boolean hasLineOfSight(Position from, Position to);
    Set<Position> getVisiblePositions(Position from);
}

public interface IActionOperations {
    boolean canExecuteAction(Unit unit, ActionType action);
    void executeAction(Unit unit, ActionType action, Position target);
}
```

### Принцип инверсии зависимостей
Зависимости от абстракций, а не от конкретных классов:

```java
// Внедрение зависимостей
public class GameEngine {
    private final ICombatManager combatManager;
    private final IVisibilitySystem visibilitySystem;
    private final IActionManager actionManager;
    
    public GameEngine(ICombatManager combatManager, 
                     IVisibilitySystem visibilitySystem,
                     IActionManager actionManager) {
        this.combatManager = combatManager;
        this.visibilitySystem = visibilitySystem;
        this.actionManager = actionManager;
    }
}
```

## 🏢 Структура API

### Пакеты API

#### Core API (`com.aliensattack.core`)
- **Модели данных** - базовые сущности
- **Перечисления** - константы и типы
- **Исключения** - обработка ошибок
- **Конфигурация** - настройки системы

#### Combat API (`com.aliensattack.combat`)
- **Интерфейсы боя** - ICombatManager
- **Результаты боя** - CombatResult
- **Системы стрельбы** - ShootingSystem
- **Фабрики оружия** - WeaponFactory

#### Field API (`com.aliensattack.field`)
- **Тактическое поле** - ITacticalField
- **Клетки поля** - Tile
- **Позиции** - Position
- **Система видимости** - IVisibilitySystem

#### Action API (`com.aliensattack.actions`)
- **Управление действиями** - IActionManager
- **Типы действий** - ActionType
- **Действия юнитов** - UnitAction
- **Валидация действий** - ActionValidator

#### Visualization API (`com.aliensattack.visualization`)
- **3D визуализация** - Combat3DVisualizer
- **Рендеринг** - Renderer
- **Камера** - Camera
- **Эффекты** - VisualEffect

#### UI API (`com.aliensattack.ui`)
- **Главное окно** - GameWindow
- **Панели** - ActionPanel, InfoPanel
- **Контроллеры** - UIController
- **События UI** - UIEvent

### Иерархия интерфейсов

```
ICombatManager
├── BasicCombatManager
├── AdvancedCombatManager
└── XCOM2CombatManager

IVisibilitySystem
├── BasicVisibilitySystem
└── AdvancedVisibilitySystem

IActionManager
├── BasicActionManager
└── AdvancedActionManager

ITacticalField
├── BasicTacticalField
└── OptimizedTacticalField
```

## 🔧 Основные интерфейсы

### ICombatManager
**Ответственность**: Управление боевыми операциями

```java
public interface ICombatManager {
    /**
     * Выполняет боевое действие между двумя юнитами
     * @param attacker атакующий юнит
     * @param target цель атаки
     * @return результат боевого действия
     * @throws CombatException если боевое действие не может быть выполнено
     */
    CombatResult executeCombat(Unit attacker, Unit target) throws CombatException;
    
    /**
     * Проверяет возможность атаки
     * @param attacker атакующий юнит
     * @param target цель атаки
     * @return true если атака возможна
     */
    boolean canAttack(Unit attacker, Unit target);
    
    /**
     * Рассчитывает урон атаки
     * @param attacker атакующий юнит
     * @param target цель атаки
     * @return рассчитанный урон
     */
    int calculateDamage(Unit attacker, Unit target);
}
```

### IVisibilitySystem
**Ответственность**: Управление системой видимости

```java
public interface IVisibilitySystem {
    /**
     * Проверяет линию видимости между двумя позициями
     * @param from начальная позиция
     * @param to конечная позиция
     * @return true если есть прямая видимость
     */
    boolean hasLineOfSight(Position from, Position to);
    
    /**
     * Получает все видимые позиции с заданной точки
     * @param from позиция наблюдателя
     * @return множество видимых позиций
     */
    Set<Position> getVisiblePositions(Position from);
    
    /**
     * Проверяет видимость юнита
     * @param observer наблюдающий юнит
     * @param target целевой юнит
     * @return true если цель видна
     */
    boolean canSeeUnit(Unit observer, Unit target);
}
```

### IActionManager
**Ответственность**: Управление действиями юнитов

```java
public interface IActionManager {
    /**
     * Проверяет возможность выполнения действия
     * @param unit юнит
     * @param action тип действия
     * @return true если действие может быть выполнено
     */
    boolean canExecuteAction(Unit unit, ActionType action);
    
    /**
     * Выполняет действие юнита
     * @param unit юнит
     * @param action тип действия
     * @param target целевая позиция
     * @throws ActionException если действие не может быть выполнено
     */
    void executeAction(Unit unit, ActionType action, Position target) throws ActionException;
    
    /**
     * Получает доступные действия для юнита
     * @param unit юнит
     * @return список доступных действий
     */
    List<ActionType> getAvailableActions(Unit unit);
    
    /**
     * Получает стоимость действия в очках действий
     * @param action тип действия
     * @return стоимость в AP
     */
    int getActionCost(ActionType action);
}
```

### ITacticalField
**Ответственность**: Управление тактическим полем

```java
public interface ITacticalField {
    /**
     * Получает клетку по позиции
     * @param position позиция
     * @return клетка поля
     * @throws FieldException если позиция недопустима
     */
    Tile getTile(Position position) throws FieldException;
    
    /**
     * Проверяет валидность позиции
     * @param position позиция для проверки
     * @return true если позиция валидна
     */
    boolean isValidPosition(Position position);
    
    /**
     * Получает размер поля
     * @return размер поля (ширина x высота)
     */
    Dimension getSize();
    
    /**
     * Обновляет состояние поля
     * @param position позиция
     * @param tile новая клетка
     */
    void updateTile(Position position, Tile tile);
}
```

## 💡 Примеры использования

### Базовое использование боевой системы

```java
// Создание менеджера боя
ICombatManager combatManager = new BasicCombatManager();

// Создание юнитов
Unit soldier = new Unit(UnitType.SOLDIER, new Position(5, 5));
Unit alien = new Unit(UnitType.ALIEN, new Position(6, 6));

// Выполнение атаки
try {
    CombatResult result = combatManager.executeCombat(soldier, alien);
    System.out.println("Атака выполнена: " + result.isHit());
    System.out.println("Урон: " + result.getDamage());
} catch (CombatException e) {
    System.err.println("Ошибка боя: " + e.getMessage());
}
```

### Использование системы видимости

```java
// Создание системы видимости
IVisibilitySystem visibilitySystem = new BasicVisibilitySystem();

// Проверка линии видимости
Position observer = new Position(3, 3);
Position target = new Position(7, 7);

boolean canSee = visibilitySystem.hasLineOfSight(observer, target);
System.out.println("Видимость: " + canSee);

// Получение всех видимых позиций
Set<Position> visiblePositions = visibilitySystem.getVisiblePositions(observer);
System.out.println("Видимых позиций: " + visiblePositions.size());
```

### Управление действиями

```java
// Создание менеджера действий
IActionManager actionManager = new BasicActionManager();

// Создание юнита
Unit unit = new Unit(UnitType.SOLDIER, new Position(5, 5));
unit.setActionPoints(2);

// Проверка доступных действий
List<ActionType> availableActions = actionManager.getAvailableActions(unit);
System.out.println("Доступные действия: " + availableActions);

// Выполнение действия
try {
    actionManager.executeAction(unit, ActionType.MOVEMENT, new Position(6, 5));
    System.out.println("Действие выполнено");
} catch (ActionException e) {
    System.err.println("Ошибка действия: " + e.getMessage());
}
```

### Работа с тактическим полем

```java
// Создание тактического поля
ITacticalField field = new BasicTacticalField(64, 64);

// Получение клетки
Position pos = new Position(10, 10);
Tile tile = field.getTile(pos);
System.out.println("Тип местности: " + tile.getTerrainType());

// Проверка валидности позиции
boolean isValid = field.isValidPosition(new Position(100, 100));
System.out.println("Позиция валидна: " + isValid);

// Обновление клетки
Tile newTile = new Tile(pos, TerrainType.URBAN, CoverType.FULL);
field.updateTile(pos, newTile);
```

## ⚠️ Обработка ошибок

### Иерархия исключений

```java
// Базовое исключение
public abstract class GameException extends Exception {
    private final ErrorType errorType;
    
    public GameException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}

// Специализированные исключения
public class CombatException extends GameException {
    public CombatException(String message) {
        super(message, ErrorType.COMBAT_ERROR);
    }
}

public class ActionException extends GameException {
    public ActionException(String message) {
        super(message, ErrorType.ACTION_ERROR);
    }
}

public class FieldException extends GameException {
    public FieldException(String message) {
        super(message, ErrorType.FIELD_ERROR);
    }
}
```

### Обработка исключений

```java
// Пример обработки исключений
try {
    CombatResult result = combatManager.executeCombat(attacker, target);
    // Обработка успешного результата
} catch (CombatException e) {
    // Обработка ошибки боя
    logger.error("Ошибка боя: {}", e.getMessage());
    showErrorMessage("Невозможно выполнить атаку");
} catch (GameException e) {
    // Обработка общих игровых ошибок
    logger.error("Игровая ошибка: {}", e.getMessage());
    showErrorMessage("Произошла ошибка в игре");
} catch (Exception e) {
    // Обработка неожиданных ошибок
    logger.error("Неожиданная ошибка: {}", e.getMessage());
    showErrorMessage("Произошла непредвиденная ошибка");
}
```

### Валидация входных данных

```java
// Пример валидации
public class InputValidator {
    public static void validatePosition(Position position, ITacticalField field) 
            throws FieldException {
        if (position == null) {
            throw new FieldException("Позиция не может быть null");
        }
        
        if (!field.isValidPosition(position)) {
            throw new FieldException("Позиция находится вне поля");
        }
    }
    
    public static void validateUnit(Unit unit) throws GameException {
        if (unit == null) {
            throw new GameException("Юнит не может быть null", ErrorType.UNIT_ERROR);
        }
        
        if (unit.getHealth() <= 0) {
            throw new GameException("Юнит мертв", ErrorType.UNIT_ERROR);
        }
    }
}
```

## 🔄 Версионирование

### Семантическое версионирование

API использует семантическое версионирование (SemVer):

- **MAJOR.MINOR.PATCH**
- **MAJOR** - несовместимые изменения
- **MINOR** - новые функции (обратная совместимость)
- **PATCH** - исправления ошибок

### Примеры версий

```
1.0.0 - Первая стабильная версия
1.1.0 - Добавлены новые типы действий
1.1.1 - Исправлена ошибка в расчете урона
2.0.0 - Изменен интерфейс ICombatManager
```

### Обратная совместимость

```java
// Старый API (v1.0.0)
public interface ICombatManager {
    CombatResult executeCombat(Unit attacker, Unit target);
}

// Новый API (v1.1.0) - обратная совместимость
public interface ICombatManager {
    CombatResult executeCombat(Unit attacker, Unit target);
    CombatResult executeCombatWithCover(Unit attacker, Unit target, CoverType cover);
}
```

### Миграция API

```java
// Пример миграции с v1.0.0 на v2.0.0
public class ApiMigration {
    public static ICombatManager migrateCombatManager(ICombatManager oldManager) {
        // Создание нового менеджера с сохранением функциональности
        return new V2CombatManager(oldManager);
    }
}
```

## 📚 Документация API

### Javadoc

Все публичные методы API документированы с помощью Javadoc:

```java
/**
 * Выполняет боевое действие между двумя юнитами.
 * 
 * @param attacker атакующий юнит, не может быть null
 * @param target цель атаки, не может быть null
 * @return результат боевого действия
 * @throws CombatException если боевое действие не может быть выполнено
 * @throws IllegalArgumentException если attacker или target равны null
 * 
 * @since 1.0.0
 * @see CombatResult
 * @see Unit
 */
CombatResult executeCombat(Unit attacker, Unit target) throws CombatException;
```

### Примеры кода

Каждый интерфейс API сопровождается примерами использования:

```java
// Пример использования ICombatManager
public class CombatExample {
    public static void main(String[] args) {
        ICombatManager combatManager = new BasicCombatManager();
        
        // Создание юнитов
        Unit soldier = new Unit(UnitType.SOLDIER, new Position(5, 5));
        Unit alien = new Unit(UnitType.ALIEN, new Position(6, 6));
        
        // Выполнение атаки
        try {
            CombatResult result = combatManager.executeCombat(soldier, alien);
            System.out.println("Результат атаки: " + result);
        } catch (CombatException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
```

### Тесты API

Все интерфейсы API покрыты тестами:

```java
@Test
public void testCombatManagerExecuteCombat() {
    ICombatManager combatManager = new BasicCombatManager();
    Unit attacker = new Unit(UnitType.SOLDIER, new Position(5, 5));
    Unit target = new Unit(UnitType.ALIEN, new Position(6, 6));
    
    CombatResult result = combatManager.executeCombat(attacker, target);
    
    assertNotNull(result);
    assertTrue(result.getDamage() >= 0);
}
```

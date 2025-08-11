# Компоненты системы AliensAttack

## 📋 Содержание

- [Обзор компонентов](#обзор-компонентов)
- [Core Components](#core-components)
- [Combat System](#combat-system)
- [Field System](#field-system)
- [Action System](#action-system)
- [Visualization System](#visualization-system)
- [UI Components](#ui-components)
- [Configuration System](#configuration-system)

## 🧩 Обзор компонентов

Система AliensAttack состоит из взаимосвязанных компонентов, каждый из которых отвечает за определенную функциональность. Компоненты организованы по принципу слабой связанности и высокой когезии.

### 🎯 Принципы организации компонентов

- **Единая ответственность** - каждый компонент имеет одну четкую задачу
- **Слабая связанность** - минимальные зависимости между компонентами
- **Высокая когезия** - связанная функциональность группируется вместе
- **Интерфейсная абстракция** - взаимодействие через интерфейсы
- **Конфигурируемость** - настройка поведения через конфигурацию

## 🔧 Core Components

### GameEngine
**Ответственность**: Главный координатор игровой системы

#### Основные функции:
- Управление жизненным циклом игры
- Координация между компонентами
- Обработка игровых событий
- Управление состоянием игры

#### Ключевые методы:
```java
public class GameEngine {
    public void initialize();
    public void startGame();
    public void pauseGame();
    public void resumeGame();
    public void endGame();
    public void processEvent(GameEvent event);
}
```

### GameState
**Ответственность**: Хранение и управление состоянием игры

#### Состояния:
- **INITIALIZING** - инициализация
- **PLAYING** - активная игра
- **PAUSED** - приостановлено
- **GAME_OVER** - игра завершена
- **VICTORY** - победа
- **DEFEAT** - поражение

#### Ключевые методы:
```java
public class GameState {
    public void setState(GameStateType state);
    public GameStateType getCurrentState();
    public boolean isGameActive();
    public void updateState();
}
```

### GameConfig
**Ответственность**: Управление конфигурацией системы

#### Настройки:
- Параметры поля (размер, типы местности)
- Настройки боевой системы
- Конфигурация UI
- Параметры производительности

#### Ключевые методы:
```java
public class GameConfig {
    public static GameConfig getInstance();
    public void loadConfiguration(String configFile);
    public <T> T getSetting(String key, Class<T> type);
    public void setSetting(String key, Object value);
}
```

## ⚔️ Combat System

### ICombatManager
**Ответственность**: Интерфейс боевой системы

#### Основные операции:
- Выполнение атак
- Расчет урона
- Проверка попаданий
- Обработка боевых эффектов

#### Реализации:
- `BasicCombatManager` - базовая реализация
- `AdvancedCombatManager` - продвинутая система
- `XCOM2CombatManager` - система в стиле XCOM2

### CombatResult
**Ответственность**: Результат боевого действия

#### Поля:
```java
public class CombatResult {
    private boolean hit;
    private int damage;
    private boolean critical;
    private List<StatusEffect> effects;
    private String message;
}
```

### ShootingSystem
**Ответственность**: Система стрельбы и расчета попаданий

#### Алгоритмы:
- **Line of Sight** - проверка видимости цели
- **Hit Calculation** - расчет попадания
- **Damage Calculation** - расчет урона
- **Critical Hit** - критические попадания

## 🗺️ Field System

### ITacticalField
**Ответственность**: Интерфейс тактического поля

#### Основные операции:
- Получение клетки по координатам
- Проверка доступности позиции
- Поиск пути
- Обновление состояния поля

#### Реализации:
- `BasicTacticalField` - базовая реализация
- `OptimizedTacticalField` - оптимизированная версия

### Tile
**Ответственность**: Представление клетки поля

#### Свойства:
```java
public class Tile {
    private Position position;
    private TerrainType terrainType;
    private CoverType coverType;
    private boolean isOccupied;
    private Unit occupyingUnit;
}
```

### Position
**Ответственность**: Представление позиции на поле

#### Операции:
```java
public class Position {
    public int getX();
    public int getY();
    public double distanceTo(Position other);
    public Position add(Position offset);
    public boolean isValid(int maxX, int maxY);
}
```

## 🎮 Action System

### ActionManager
**Ответственность**: Управление действиями юнитов

#### Основные функции:
- Создание действий
- Валидация действий
- Выполнение действий
- Отмена действий

#### Ключевые методы:
```java
public class ActionManager {
    public boolean canExecuteAction(Unit unit, ActionType action);
    public void executeAction(Unit unit, ActionType action, Position target);
    public List<ActionType> getAvailableActions(Unit unit);
    public int getActionCost(ActionType action);
}
```

### ActionType
**Ответственность**: Типы действий в игре

#### Доступные действия:
- **MOVEMENT** - движение (1 AP)
- **ATTACK** - атака (1 AP)
- **OVERWATCH** - наблюдение (1 AP)
- **RELOAD** - перезарядка (1 AP)
- **HEAL** - лечение (1 AP)
- **GRENADE** - граната (1 AP)
- **SPECIAL_ABILITY** - спецспособность (2 AP)
- **DEFEND** - защита (1 AP)
- **DASH** - рывок (2 AP)

### UnitAction
**Ответственность**: Конкретное действие юнита

#### Структура:
```java
public class UnitAction {
    private Unit unit;
    private ActionType actionType;
    private Position target;
    private int actionPoints;
    private boolean executed;
}
```

## 🎨 Visualization System

### Combat3DVisualizer
**Ответственность**: 3D визуализация боевой сцены

#### Возможности:
- Рендеринг 3D сцены
- Анимации действий
- Эффекты частиц
- Освещение и тени

#### Ключевые методы:
```java
public class Combat3DVisualizer {
    public void initializeScene();
    public void renderFrame();
    public void animateAction(UnitAction action);
    public void updateCamera(Position target);
    public void addEffect(VisualEffect effect);
}
```

### GameWindow
**Ответственность**: Главное окно приложения

#### Компоненты:
- Панель действий
- Информационная панель
- 3D область отображения
- Меню и настройки

#### Ключевые методы:
```java
public class GameWindow {
    public void initializeUI();
    public void updateActionPanel(Unit unit);
    public void showMessage(String message);
    public void updateGameState(GameState state);
}
```

### Interactive3DDemo
**Ответственность**: Интерактивная 3D демонстрация

#### Функции:
- Интерактивное управление камерой
- Выбор юнитов
- Выполнение действий
- Демонстрация возможностей

## 🖥️ UI Components

### ActionPanel
**Ответственность**: Панель действий юнита

#### Элементы:
- Кнопки действий с цветовой кодировкой
- Индикатор очков действий
- Информация о выбранном юните
- Подсказки и описания

#### Цветовая схема:
- 🔵 **Движение** - синий
- 🔴 **Атака** - красный
- 🟠 **Наблюдение** - оранжевый
- 🟣 **Перезарядка** - фиолетовый
- 🟢 **Лечение** - зеленый
- 🌸 **Граната** - розовый
- 🟦 **Спец.способность** - голубой
- 🟢 **Защита** - светло-зеленый
- 🟡 **Рывок** - желтый

### InfoPanel
**Ответственность**: Информационная панель

#### Отображаемая информация:
- Статистика юнита
- Состояние здоровья
- Информация о местности
- Цели миссии
- Время и ходы

### CameraController
**Ответственность**: Управление 3D камерой

#### Функции:
- Автоматическое центрирование на юните
- Ручное управление камерой
- Зум и поворот
- Следование за действиями

## ⚙️ Configuration System

### ConfigurationManager
**Ответственность**: Управление конфигурацией

#### Типы конфигурации:
- **Game Properties** - основные настройки игры
- **Combat Properties** - параметры боевой системы
- **Unit Properties** - характеристики юнитов
- **Weapon Properties** - параметры оружия
- **Armor Properties** - характеристики брони

#### Методы загрузки:
```java
public class ConfigurationManager {
    public void loadGameConfig();
    public void loadCombatConfig();
    public void loadUnitConfig();
    public void loadWeaponConfig();
    public void loadArmorConfig();
}
```

### PropertyLoader
**Ответственность**: Загрузка свойств из файлов

#### Поддерживаемые форматы:
- **Properties files** (.properties)
- **JSON files** (.json)
- **XML files** (.xml)
- **YAML files** (.yaml)

#### Ключевые методы:
```java
public class PropertyLoader {
    public Properties loadProperties(String filename);
    public <T> T loadObject(String filename, Class<T> type);
    public void saveProperties(String filename, Properties props);
}
```

## 🔄 Взаимодействие компонентов

### Поток выполнения типичного действия:

1. **UI** получает пользовательский ввод
2. **ActionManager** валидирует действие
3. **CombatManager** выполняет боевую логику
4. **GameState** обновляет состояние
5. **Visualizer** обновляет отображение
6. **UI** обновляет интерфейс

### События и уведомления:

```java
// Пример взаимодействия через события
GameEvent event = new GameEvent(EventType.UNIT_ACTION_EXECUTED, action);
GameEngine.getInstance().notifyObservers(event);
```

### Зависимости между компонентами:

- **UI** → **ActionManager** (через интерфейсы)
- **ActionManager** → **CombatManager** (прямые вызовы)
- **CombatManager** → **GameState** (обновление состояния)
- **Visualizer** → **GameState** (получение данных)
- **UI** → **Visualizer** (обновление отображения)

## 📈 Производительность компонентов

### Оптимизации:
- **Кэширование** результатов вычислений
- **Ленивая загрузка** ресурсов
- **Пул объектов** для часто создаваемых объектов
- **Асинхронная обработка** тяжелых операций

### Мониторинг:
- **Метрики производительности** каждого компонента
- **Профилирование** критических путей
- **Логирование** операций для отладки
- **Алерты** при превышении пороговых значений

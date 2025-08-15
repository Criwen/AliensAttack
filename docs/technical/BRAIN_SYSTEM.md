# Brain System - Система управления персонажами

## Обзор

Система управления персонажами (Brain System) предоставляет абстракцию для контроля над персонажами в игре AliensAttack. Система поддерживает различные типы управления: человек, ИИ, удаленное управление и автономное поведение.

## Архитектура

### Основные компоненты

1. **IBrain** - Интерфейс для всех типов управления
2. **AbstractBrain** - Абстрактный базовый класс с общей функциональностью
3. **HumanBrain** - Реализация для управления человеком
4. **AIBrain** - Реализация для ИИ
5. **BrainManager** - Центральный менеджер всех brain систем
6. **BrainFactory** - Фабрика для создания различных типов brain

### Диаграмма классов

```
IBrain (interface)
    ↑
AbstractBrain (abstract)
    ↑
HumanBrain ──── AIBrain
    ↑              ↑
BrainManager ← BrainFactory
```

## Типы Brain

### 1. HumanBrain (Управление человеком)

**Особенности:**
- Ожидает ввода от игрока
- Поддерживает авто-действия при таймауте
- Настраиваемые предпочтения действий
- Конфигурируемое время реакции

**Использование:**
```java
HumanBrain playerBrain = BrainFactory.createHumanBrain("PLAYER_1", 8);
playerBrain.setAutoActionsEnabled(true);
playerBrain.setReactionTime(2000); // 2 секунды
```

### 2. AIBrain (Искусственный интеллект)

**Особенности:**
- Автоматическое принятие решений
- Настраиваемые уровни интеллекта (1-10) и агрессии (0.0-1.0)
- Адаптивные стратегии поведения
- Система обучения на основе опыта
- Интеграция с AdvancedAIBehaviorTree

**Стратегии:**
- **AGGRESSIVE** - Фокус на атаке и продвижении
- **DEFENSIVE** - Фокус на защите и позиционировании
- **SURVIVAL** - Фокус на выживании
- **EXPLORATION** - Фокус на исследовании
- **CONSERVATIVE** - Фокус на экономии ресурсов
- **BALANCED** - Сбалансированный подход

**Использование:**
```java
AIBrain enemyBrain = BrainFactory.createAIBrain(7, 0.8, 6);
AIBrain bossBrain = BrainFactory.createBossAIBrain();
AIBrain tacticalBrain = BrainFactory.createTacticalAIBrain();
```

## BrainManager

Центральный менеджер для координации всех brain систем.

**Основные функции:**
- Регистрация и управление brain
- Назначение brain к юнитам
- Координация выполнения brain
- Управление приоритетами
- Статистика и метрики

**Использование:**
```java
BrainManager manager = new BrainManager();

// Регистрация brain
manager.registerBrain(playerBrain);
manager.registerBrain(enemyBrain);

// Назначение к юнитам
manager.assignBrainToUnit("BRAIN_ID", "UNIT_ID");

// Выполнение
manager.executeBrains(gameContext);
```

## BrainFactory

Фабрика для создания различных типов brain с предустановленными конфигурациями.

**Готовые конфигурации:**
- `createBasicAIBrain()` - Базовый ИИ для простых врагов
- `createAggressiveAIBrain()` - Агрессивный ИИ
- `createDefensiveAIBrain()` - Защитный ИИ
- `createTacticalAIBrain()` - Тактический ИИ
- `createBossAIBrain()` - ИИ для боссов

**Создание отрядов:**
```java
// Создание отряда ИИ
List<AIBrain> squad = BrainFactory.createAISquad(5, 6, 0.6);

// Смешанный отряд
List<IBrain> mixedSquad = BrainFactory.createMixedSquad(2, 3, 1);
```

## Интеграция с игровой системой

### GameContext

Каждый brain получает `GameContext` с информацией о текущем состоянии игры:

- Текущий ход и фаза
- Информация о поле и юнитах
- Боевая информация
- Экологические условия
- Миссионные цели

### Система действий

Brain взаимодействуют с системой действий через:

- `getAvailableActions()` - Получение доступных действий
- `selectAction(context)` - Выбор действия
- `executeAction(action)` - Выполнение действия

## Конфигурация и настройка

### Настройка HumanBrain

```java
List<String> preferredActions = Arrays.asList("ATTACK", "MOVE");
HumanBrain brain = BrainFactory.createHumanBrain("PLAYER_1", 8, 
    preferredActions, true, 1500);
```

### Настройка AIBrain

```java
AIBrain brain = new AIBrain("CUSTOM_AI", 9, 8, 0.7);
brain.setLearningEnabled(true);
```

### Настройка BrainManager

```java
BrainManager manager = new BrainManager(
    15,    // Максимум одновременных brain
    10000, // Таймаут выполнения (10 секунд)
    true   // Разрешить переключение brain
);
```

## Метрики и производительность

### Brain Metrics

Каждый brain отслеживает:

- Количество принятых решений
- Количество выполненных действий
- Среднее время принятия решений
- Процент успешности
- Количество ошибок

### Статистика BrainManager

- Общее количество brain
- Активные brain
- Контролируемые юниты
- Распределение по типам

## Примеры использования

### Базовый пример

```java
// Создание системы
BrainManager manager = new BrainManager();
BrainFactory factory = new BrainFactory();

// Создание brain
HumanBrain playerBrain = factory.createHumanBrain("PLAYER_1");
AIBrain enemyBrain = factory.createAIBrain(6, 0.7);

// Регистрация
manager.registerBrain(playerBrain);
manager.registerBrain(enemyBrain);

// Назначение к юнитам
manager.assignBrainToUnit(playerBrain.getBrainId(), "UNIT_PLAYER");
manager.assignBrainToUnit(enemyBrain.getBrainId(), "UNIT_ENEMY");

// Выполнение
GameContext context = GameContext.createDefault();
manager.executeBrains(context);
```

### Создание отряда

```java
// Создание отряда ИИ
List<AIBrain> enemySquad = BrainFactory.createAISquad(5, 6, 0.6);

// Регистрация всех brain
enemySquad.forEach(manager::registerBrain);

// Назначение к юнитам
for (int i = 0; i < enemySquad.size(); i++) {
    String brainId = enemySquad.get(i).getBrainId();
    String unitId = "ENEMY_UNIT_" + (i + 1);
    manager.assignBrainToUnit(brainId, unitId);
}
```

### Координация brain

```java
// Координация для тактических действий
List<String> brainIds = Arrays.asList("BRAIN_1", "BRAIN_2", "BRAIN_3");
manager.coordinateBrains(brainIds, "FLANKING");
manager.coordinateBrains(brainIds, "DEFENSIVE");
```

## Расширение системы

### Создание нового типа Brain

1. Создать класс, наследующий от `AbstractBrain`
2. Реализовать абстрактные методы
3. Добавить специфичную логику
4. Зарегистрировать в `BrainFactory`

### Пример кастомного Brain

```java
public class RemoteBrain extends AbstractBrain {
    
    public RemoteBrain(String brainId, int priority) {
        super(brainId, BrainType.REMOTE, priority);
    }
    
    @Override
    protected Optional<IAction> decideAction(GameContext context) {
        // Логика для удаленного управления
        return Optional.empty();
    }
    
    @Override
    protected List<IAction> calculateAvailableActions() {
        // Расчет доступных действий
        return new ArrayList<>();
    }
    
    @Override
    protected void onUpdate(GameContext context) {
        // Обновление состояния
    }
    
    @Override
    protected void onNewTurn() {
        // Сброс для нового хода
    }
}
```

## Лучшие практики

### Производительность

1. **Ограничение одновременных brain** - Настройте `maxConcurrentBrains` в BrainManager
2. **Таймауты выполнения** - Установите разумные таймауты для предотвращения зависания
3. **Кэширование действий** - Переиспользуйте доступные действия между обновлениями

### Безопасность

1. **Валидация входных данных** - Проверяйте все параметры при создании brain
2. **Обработка ошибок** - Всегда обрабатывайте исключения в brain
3. **Логирование** - Используйте логирование для отладки и мониторинга

### Расширяемость

1. **Интерфейсы** - Программируйте к интерфейсам, а не к реализациям
2. **Фабрики** - Используйте фабрики для создания объектов
3. **Конфигурация** - Выносите настройки в конфигурационные файлы

## Отладка и мониторинг

### Логирование

Система использует Log4j2 для логирования:

```java
// Включение детального логирования
log.debug("Brain {} selected action {}", brainId, actionType);
log.info("Brain {} took control of unit {}", brainId, unitId);
log.warn("Brain {} failed to execute action {}", brainId, actionType);
log.error("Brain {} error during execution", brainId, exception);
```

### Метрики

Отслеживание производительности brain:

```java
// Получение метрик
IBrain.BrainMetrics metrics = brain.getMetrics();
log.info("Decisions: {}, Success Rate: {:.2f}%", 
    metrics.getDecisionsMade(), 
    metrics.getSuccessRate() * 100);

// Статистика менеджера
Map<String, Object> stats = manager.getBrainStatistics();
log.info("Total brains: {}, Active: {}", 
    stats.get("totalBrains"), 
    stats.get("activeBrains"));
```

## Заключение

Система управления персонажами предоставляет гибкую и расширяемую архитектуру для контроля над персонажами в игре. Она поддерживает как ручное управление человеком, так и автоматическое управление ИИ, с возможностью координации и переключения между различными типами управления.

Система спроектирована с учетом принципов SOLID и использует современные паттерны проектирования, что делает её легко расширяемой и поддерживаемой.

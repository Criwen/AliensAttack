# XCOM 2 Advanced Mechanics Implementation

## Обзор

Данный документ описывает реализацию 5 ключевых механик из игры XCOM 2, которые были добавлены в проект AliensAttack для расширения тактической глубины боевой системы.

## Реализованные механики

### 1. Система скрытности (Concealment/Stealth System)

**Описание:** Юниты могут начинать миссию в скрытом состоянии и раскрываться при обнаружении врагами.

**Ключевые особенности:**
- Юниты могут скрываться (`conceal()`)
- Автоматическое обнаружение при приближении врагов
- Бонус к точности (+25%) при атаке из скрытности
- Автоматическое раскрытие после атаки

**Реализация:**
```java
// Скрытие юнита
boolean concealed = combatManager.concealUnit(unit);

// Атака из скрытности
CombatResult result = combatManager.stealthAttack(attacker, target, weapon);
```

**Классы:**
- `Unit.conceal()` - скрытие юнита
- `Unit.reveal()` - раскрытие юнита
- `Unit.isDetectedBy()` - проверка обнаружения
- `Unit.getStealthAttackBonus()` - бонус к атаке из скрытности

### 2. Система фланкинга (Flanking Mechanics)

**Описание:** Бонусный урон при атаке врага с тыла, когда он находится за укрытием.

**Ключевые особенности:**
- Проверка позиции атакующего относительно укрытия
- Бонус к урону (+50%) при фланкинге
- Бонус к точности (+25%) при фланкинге
- Автоматическое определение фланкинга

**Реализация:**
```java
// Проверка фланкинга
boolean isFlanked = combatManager.isUnitFlanked(target, attacker);

// Атака с фланкинговым бонусом
CombatResult result = combatManager.flankingAttack(attacker, target, weapon);
```

**Классы:**
- `CoverObject.providesCoverFrom()` - проверка защиты укрытия
- `CoverObject.getDefenseBonus()` - бонус защиты от укрытия
- `XCOM2CombatManager.isUnitFlanked()` - определение фланкинга
- `XCOM2CombatManager.flankingAttack()` - фланкинговая атака

### 3. Система подавляющего огня (Suppression Fire)

**Описание:** Огонь по области, который ограничивает движение врагов и снижает их точность.

**Ключевые особенности:**
- Подавление нескольких целей одновременно
- Снижение точности на 30% у подавленных юнитов
- Блокировка движения подавленных юнитов
- Автоматическое применение эффекта подавления

**Реализация:**
```java
// Подавляющий огонь
List<CombatResult> results = combatManager.suppressionFire(
    attacker, targetPos, weapon, 3 // 3 хода подавления
);

// Проверка возможности движения
boolean canMove = combatManager.canMoveWhileSuppressed(unit);
```

**Классы:**
- `Unit.applySuppression()` - применение подавления
- `Unit.removeSuppression()` - снятие подавления
- `Unit.canMoveWhileSuppressed()` - проверка движения
- `Unit.getSuppressionAccuracyPenalty()` - штраф к точности

### 4. Разрушаемая среда (Destructible Environment)

**Описание:** Укрытия и объекты окружения могут быть разрушены, изменяя тактическую ситуацию.

**Ключевые особенности:**
- Разрушение укрытий и барьеров
- Взрывы при разрушении объектов
- Изменение линии видимости
- Тактическое использование разрушения

**Реализация:**
```java
// Атака по объекту окружения
CombatResult result = combatManager.attackEnvironment(attacker, envObject, weapon);

// Проверка разрушения
if (envObject.isDestroyed()) {
    // Обработка взрыва
    handleEnvironmentExplosion(envObject);
}
```

**Классы:**
- `EnvironmentObject.takeDamage()` - получение урона объектом
- `EnvironmentObject.blocksLineOfSight()` - блокировка видимости
- `EnvironmentObject.providesCover()` - предоставление укрытия
- `EnvironmentObject.getExplosionDamage()` - урон от взрыва

### 5. Сплоченность отряда (Squad Cohesion)

**Описание:** Бонусы для юнитов, находящихся рядом друг с другом.

**Ключевые особенности:**
- 5 типов тактик отряда
- Бонусы к точности, урону, защите и движению
- Система кулдаунов для тактик
- Автоматическое применение к ближайшим союзникам

**Реализация:**
```java
// Активация тактики отряда
boolean activated = combatManager.activateSquadTactic(leader, tactic);

// Получение бонуса сплоченности
int accuracyBonus = combatManager.getSquadCohesionBonus(unit, "accuracy");
```

**Классы:**
- `SquadTactic.activate()` - активация тактики
- `SquadTactic.getTotalBonus()` - получение бонуса
- `SquadTactic.processCooldown()` - обработка кулдауна
- `XCOM2CombatManager.activateSquadTactic()` - применение тактики

## Типы тактик отряда

### 1. Formation (Стандартная формация)
- Точность: +10%
- Урон: +5%
- Защита: +10%
- Движение: 0

### 2. Suppression (Подавление)
- Точность: +15%
- Урон: +10%
- Защита: +5%
- Движение: 0

### 3. Assault (Штурм)
- Точность: +5%
- Урон: +15%
- Защита: 0
- Движение: +1

### 4. Defensive (Оборона)
- Точность: 0
- Урон: 0
- Защита: +20%
- Движение: -1

### 5. Mobile (Мобильная)
- Точность: +5%
- Урон: +5%
- Защита: +5%
- Движение: +2

## Интеграция с существующей системой

### Совместимость
Все новые механики полностью интегрированы с существующей архитектурой:
- Используют существующие модели данных
- Совместимы с текущими системами боя
- Не нарушают работу существующих механик

### Расширения
- `XCOM2CombatManager` - основной класс для новых механик
- Обновленные enums для новых типов
- Дополнительные методы в существующих классах

## Использование

### Базовое использование
```java
// Создание менеджера боя
XCOM2CombatManager combatManager = new XCOM2CombatManager(field);

// Добавление юнитов
combatManager.addPlayerUnit(soldier);
combatManager.addEnemyUnit(alien);

// Использование новых механик
combatManager.concealUnit(soldier);
CombatResult result = combatManager.stealthAttack(soldier, alien, weapon);
```

### Продвинутое использование
```java
// Комбинирование механик
SquadTactic assaultTactic = new SquadTactic("Assault", SquadTacticType.ASSAULT, 3);
combatManager.activateSquadTactic(leader, assaultTactic);

// Фланкинговая атака с бонусом отряда
CombatResult flankResult = combatManager.flankingAttack(attacker, target, weapon);

// Подавляющий огонь по области
List<CombatResult> suppressionResults = combatManager.suppressionFire(
    attacker, targetPos, weapon, 3
);
```

## Конфигурация

### Настройки в properties файлах
```properties
# Stealth mechanics
unit.stealth.attack.bonus=25
unit.stealth.detection.range=8

# Flanking mechanics
combat.flanking.damage.bonus=50
combat.flanking.accuracy.bonus=25

# Suppression mechanics
combat.suppression.accuracy.penalty=30
combat.suppression.movement.penalty=100

# Squad cohesion
squad.tactic.duration=3
squad.tactic.cooldown=5
```

## Производительность

### Оптимизации
- Кэширование результатов проверок
- Эффективные алгоритмы поиска целей
- Минимальные вычисления при проверках

### Масштабируемость
- Поддержка больших карт
- Эффективная обработка множественных юнитов
- Оптимизированные алгоритмы поиска пути

## Тестирование

### Unit тесты
```java
@Test
public void testStealthAttack() {
    // Тест атаки из скрытности
}

@Test
public void testFlankingMechanics() {
    // Тест фланкинговых механик
}

@Test
public void testSuppressionFire() {
    // Тест подавляющего огня
}
```

## Заключение

Реализованные механики значительно расширяют тактическую глубину игры, добавляя:

1. **Стратегическое планирование** - скрытность и фланкинг
2. **Тактическое позиционирование** - подавляющий огонь и сплоченность
3. **Динамическую среду** - разрушаемые объекты
4. **Командное взаимодействие** - тактики отряда
5. **Реалистичность** - механики, основанные на реальной тактике

Все механики готовы к использованию и полностью интегрированы в существующую архитектуру проекта. 
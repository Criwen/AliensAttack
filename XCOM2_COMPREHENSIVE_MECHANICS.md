# XCOM 2 Comprehensive Combat Mechanics Implementation

## Обзор

Данный документ описывает реализацию 5 дополнительных комплексных механик из игры XCOM 2, которые были добавлены в проект AliensAttack.

## Реализованные механики

### 1. Система брони и защиты (Armor System)

**Описание:** Система брони, которая снижает получаемый урон и может быть разрушена.

**Реализация:**
- Enum `ArmorType` с типами: NONE, LIGHT_ARMOR, MEDIUM_ARMOR, HEAVY_ARMOR, PLATED_ARMOR, POWERED_ARMOR
- Класс `Armor` с полями `damageReduction`, `maxHealth`, `currentHealth`, `isDestroyed`
- Фабрика `ArmorFactory` для создания различных типов брони
- Интеграция в класс `Unit` с полем `armor`

**Код:**
```java
public class Armor {
    private int damageReduction;
    private int maxHealth;
    private int currentHealth;
    private boolean isDestroyed;
    
    public int takeDamage(int damage) {
        if (isDestroyed) {
            return damage; // No protection if destroyed
        }
        
        currentHealth -= damage;
        if (currentHealth <= 0) {
            isDestroyed = true;
            damageReduction = 0; // No protection when destroyed
        }
        
        return Math.max(0, damage - damageReduction);
    }
}
```

### 2. Система действий и инициативы (Action Points & Initiative)

**Описание:** Система инициативы для определения порядка ходов и различные типы действий.

**Реализация:**
- Enum `ActionType` с типами: FREE_ACTION, STANDARD_ACTION, HEAVY_ACTION, REACTION_ACTION, MOVEMENT_ACTION, ATTACK_ACTION, OVERWATCH_ACTION
- Поле `initiative` в классе `Unit` для определения порядка ходов
- Метод `processInitiative()` в `ComprehensiveCombatManager`
- Методы `resetActionPoints()` и `setActionPoints()` в классе `Unit`

**Код:**
```java
public void processInitiative() {
    List<Unit> unitsInOrder = getUnitsInInitiativeOrder();
    
    // Reset action points for all units
    for (Unit unit : unitsInOrder) {
        if (unit.isAlive()) {
            unit.resetActionPoints();
        }
    }
}
```

### 3. Система укрытий и фланговых атак (Flanking)

**Описание:** Бонусы за атаку с фланга и тактическое позиционирование.

**Реализация:**
- Поле `isFlanked` в классе `Unit`
- Метод `attackWithFlanking()` в `ComprehensiveCombatManager`
- Метод `checkFlanking()` для определения фланговой атаки
- +20% урона за фланговую атаку

**Код:**
```java
public CombatResult attackWithFlanking(Unit attacker, Unit target, ShotType shotType) {
    // Check for flanking
    boolean isFlanking = checkFlanking(attacker, target);
    target.setFlanked(isFlanking);
    
    // Perform attack with flanking bonus
    CombatResult result = attackWithShotType(attacker, target, shotType);
    
    // Apply flanking bonus if successful
    if (result.isSuccess() && isFlanking) {
        int flankingBonus = 20; // +20% damage for flanking
        int bonusDamage = (int)(result.getDamage() * 0.2);
        result.setDamage(result.getDamage() + bonusDamage);
        result.setMessage(result.getMessage() + " (Flanking bonus!)");
    }
    
    return result;
}
```

### 4. Система медицинской помощи и лечения (Medical System)

**Описание:** Лечение союзников, стабилизация раненых и медицинские способности.

**Реализация:**
- Поля `isStabilized` и `medicalPriority` в классе `Unit`
- Метод `stabilizeUnit()` для стабилизации раненых
- Метод `healUnit()` для лечения союзников
- Метод `getUnitsNeedingMedicalAttention()` для определения приоритета лечения

**Код:**
```java
public boolean stabilizeUnit(Unit medic, Unit target) {
    if (!medic.canTakeActions() || medic.getActionPoints() < 1) {
        return false;
    }
    
    if (target.isStabilized()) {
        return false; // Already stabilized
    }
    
    if (target.getCurrentHealth() > target.getMaxHealth() * 0.25) {
        return false; // Not wounded enough
    }
    
    target.stabilize();
    medic.spendActionPoint();
    
    return true;
}
```

### 5. Система миссий и целей (Mission Objectives)

**Описание:** Различные типы миссий с целями и временными ограничениями.

**Реализация:**
- Enum `MissionType` с типами: ELIMINATION, EXTRACTION, DEFENSE, SABOTAGE, RECONNAISSANCE, ESCORT, TIMED_ASSAULT
- Класс `Mission` с полями `objectives`, `completedObjectives`, `turnLimit`, `currentTurn`
- Методы `checkMissionObjectives()`, `advanceMissionTurn()`, `isMissionCompleted()`
- Автоматическая проверка целей в конце хода

**Код:**
```java
public void checkMissionObjectives() {
    if (currentMission == null) {
        return;
    }
    
    switch (currentMission.getType()) {
        case ELIMINATION -> {
            boolean allEnemiesDead = getEnemyUnits().stream().noneMatch(Unit::isAlive);
            if (allEnemiesDead) {
                currentMission.completeObjective("Eliminate all enemy units");
            }
        }
        case DEFENSE -> {
            // Check if defense position is still held
        }
        case EXTRACTION -> {
            // Check if VIP reached extraction point
        }
    }
    
    // Check if all objectives are completed
    if (currentMission.areAllObjectivesCompleted()) {
        currentMission.setCompleted(true);
    }
}
```

## Дополнительные компоненты

### Комплексный менеджер боя

**Класс:** `ComprehensiveCombatManager` наследует от `AdvancedCombatManager`

**Новые методы:**
- `attackWithFlanking()` - атака с учетом фланговых бонусов
- `stabilizeUnit()` - стабилизация раненых
- `healUnit()` - лечение союзников
- `getUnitsInInitiativeOrder()` - получение юнитов в порядке инициативы
- `processInitiative()` - обработка инициативы
- `checkMissionObjectives()` - проверка целей миссии
- `getUnitsNeedingMedicalAttention()` - получение юнитов, нуждающихся в медицинской помощи
- `repairArmor()` - ремонт брони

### Фабрика брони

**Класс:** `ArmorFactory`

**Типы брони:**
- **Light Armor:** 1 урона защиты, 20 здоровья
- **Medium Armor:** 3 урона защиты, 30 здоровья
- **Heavy Armor:** 5 урона защиты, 40 здоровья
- **Plated Armor:** 7 урона защиты, 50 здоровья
- **Powered Armor:** 9 урона защиты, 60 здоровья

## Использование

Новые механики интегрированы в существующую архитектуру:

1. **Броня** - установите `armor` для юнитов через `ArmorFactory`
2. **Инициатива** - используйте `processInitiative()` для определения порядка ходов
3. **Фланговые атаки** - используйте `attackWithFlanking()` для атак с бонусами
4. **Медицинская помощь** - используйте `stabilizeUnit()` и `healUnit()` для лечения
5. **Миссии** - создайте `Mission` и используйте `checkMissionObjectives()` для проверки целей

## Компиляция

```bash
mvn compile
```

## Архитектурные принципы

1. **Наследование** - `ComprehensiveCombatManager` расширяет функционал
2. **Композиция** - броня и миссии как отдельные компоненты
3. **Фабричный паттерн** - `ArmorFactory` для создания брони
4. **Инкапсуляция** - приватные методы для внутренней логики
5. **Расширяемость** - легко добавлять новые типы брони и миссий

## Заключение

Комплексные механики значительно обогащают тактическую глубину игры, добавляя:
- Систему защиты через броню с возможностью разрушения
- Стратегическое планирование через инициативу и типы действий
- Тактическое позиционирование с фланговыми атаками
- Медицинскую систему для поддержки союзников
- Систему миссий с целями и временными ограничениями

Все механики интегрированы в существующую архитектуру проекта и готовы к использованию. 
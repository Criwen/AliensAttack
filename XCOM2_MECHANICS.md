# XCOM 2 Combat Mechanics Implementation

## Обзор

Данный документ описывает реализацию 5 ключевых механик из игры XCOM 2, которые были добавлены в проект AliensAttack.

## Реализованные механики

### 1. Система критических ударов (Critical Hits)

**Описание:** Механика критических попаданий с увеличенным уроном.

**Реализация:**
- Класс `Weapon` содержит поля `criticalChance` и `criticalDamage`
- Метод `checkCriticalHit()` в `EnhancedCombatManager` проверяет критическое попадание
- Критический урон = базовый урон × множитель (по умолчанию 2x)

**Код:**
```java
private boolean checkCriticalHit(Unit attacker) {
    int totalCritChance = attacker.getTotalCriticalChance();
    int roll = random.nextInt(100) + 1;
    return roll <= totalCritChance;
}
```

### 2. Система Overwatch

**Описание:** Реактивные выстрелы при движении врагов.

**Реализация:**
- Поле `isOverwatching` в классе `Unit`
- Метод `setOverwatch()` устанавливает режим overwatch
- Метод `processOverwatch()` обрабатывает реактивные выстрелы
- Шанс попадания в overwatch режиме (по умолчанию 70%)

**Код:**
```java
public boolean setOverwatch(Unit unit) {
    if (!unit.canAttack() || !unit.canTakeActions()) {
        return false;
    }
    unit.setOverwatching(true);
    unit.spendActionPoint();
    return true;
}
```

### 3. Система высоты (Height Advantages)

**Описание:** Бонусы к точности за высоту позиции.

**Реализация:**
- Поле `height` в классе `Unit` (0 = земля, 1 = возвышение)
- Метод `calculateHeightBonus()` вычисляет бонус к точности
- +10% к точности за каждый уровень высоты

**Код:**
```java
private int calculateHeightBonus(Unit attacker, Unit target) {
    int heightDiff = attacker.getHeight() - target.getHeight();
    if (heightDiff > 0) {
        return heightDiff * 10; // +10% per height level
    }
    return 0;
}
```

### 4. Система статус-эффектов (Status Effects)

**Описание:** Временные эффекты, влияющие на характеристики юнитов.

**Реализация:**
- Enum `StatusEffect` с типами эффектов
- Класс `StatusEffectData` для хранения данных об эффектах
- Эффекты: STUNNED, BLEEDING, POISONED, BURNING, FROZEN, MARKED, OVERWATCH
- Автоматическое применение урона от эффектов

**Код:**
```java
public void processAllStatusEffects() {
    for (Unit unit : getAllUnits()) {
        if (unit.isAlive()) {
            unit.processStatusEffects();
            
            for (StatusEffectData effect : unit.getStatusEffects()) {
                if (effect.isActive()) {
                    switch (effect.getEffect()) {
                        case BLEEDING:
                        case BURNING:
                        case POISONED:
                            unit.takeDamage(effect.getIntensity());
                            break;
                    }
                }
            }
        }
    }
}
```

### 5. Система гранат и взрывчатки (Area of Effect Attacks)

**Описание:** Атаки по области с уроном по нескольким целям.

**Реализация:**
- Поле `isAreaOfEffect` и `areaRadius` в классе `Weapon`
- Метод `areaOfEffectAttack()` в `EnhancedCombatManager`
- Поддержка гранат и ракетных установок

**Код:**
```java
public List<CombatResult> areaOfEffectAttack(Unit attacker, Position targetPos, Weapon weapon) {
    if (!weapon.isAreaOfEffect()) {
        return List.of(new CombatResult(false, 0, "Weapon is not area of effect"));
    }
    
    List<CombatResult> results = new ArrayList<>();
    int areaRadius = weapon.getAreaRadius();
    
    List<Unit> unitsInArea = getField().getUnitsInRangeOptimized(targetPos, areaRadius);
    
    for (Unit target : unitsInArea) {
        if (target.isAlive()) {
            int damage = weapon.getBaseDamage();
            boolean killed = target.takeDamage(damage);
            
            String message = killed ? "Target killed by AoE!" : "Target hit by AoE!";
            results.add(new CombatResult(true, damage, message));
        }
    }
    
    attacker.spendActionPoint();
    weapon.useAmmo();
    
    return results;
}
```

## Дополнительные компоненты

### Система оружия

**Классы:**
- `Weapon` - базовый класс оружия
- `WeaponType` - типы оружия (RIFLE, SHOTGUN, SNIPER_RIFLE, etc.)
- `WeaponFactory` - фабрика для создания оружия

**Характеристики оружия:**
- `baseDamage` - базовый урон
- `criticalDamage` - критический урон
- `criticalChance` - шанс критического попадания
- `accuracy` - точность
- `range` - дальность
- `isAreaOfEffect` - является ли оружием по области
- `areaRadius` - радиус области поражения

### Расширенный менеджер боя

**Класс:** `EnhancedCombatManager` наследует от `OptimizedCombatManager`

**Новые методы:**
- `attackWithCritical()` - атака с критическими попаданиями
- `areaOfEffectAttack()` - атака по области
- `setOverwatch()` - установка режима overwatch
- `processOverwatch()` - обработка overwatch выстрелов
- `applyStatusEffects()` - применение статус-эффектов
- `processAllStatusEffects()` - обработка всех статус-эффектов

## Использование

Новые механики интегрированы в существующую архитектуру и готовы к использованию:

1. **Критические удары** - используйте `attackWithCritical()` вместо обычной атаки
2. **Статус-эффекты** - применяются автоматически при атаках
3. **Высота** - учитывается автоматически при расчете точности
4. **Overwatch** - используйте `setOverwatch()` для реактивных выстрелов
5. **Атаки по области** - используйте `areaOfEffectAttack()` для гранат и ракет

## Компиляция

```bash
mvn compile
```

## Архитектурные принципы

1. **Наследование** - `EnhancedCombatManager` расширяет базовый функционал
2. **Композиция** - оружие и статус-эффекты как отдельные компоненты
3. **Фабричный паттерн** - `WeaponFactory` для создания оружия
4. **Инкапсуляция** - приватные методы для внутренней логики
5. **Расширяемость** - легко добавлять новые типы оружия и эффектов

## Заключение

Реализованные механики значительно обогащают тактическую глубину игры, добавляя:
- Стратегическое планирование с учетом критических ударов
- Позиционирование с учетом высоты и overwatch
- Тактическое использование статус-эффектов
- Разнообразие оружия и атак по области

Все механики интегрированы в существующую архитектуру проекта и готовы к использованию. 
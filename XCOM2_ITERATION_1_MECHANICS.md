# XCOM 2 Iteration 1 Mechanics Implementation

## Обзор

Данный документ описывает реализацию 5 дополнительных механик из игры XCOM 2 в первой итерации рекурсивного анализа и реализации.

## Реализованные механики (Итерация 1)

### 1. **Overwatch Ambush** - Координированные атаки в режиме ожидания

**Описание:** Система координированных атак в режиме ожидания с несколькими юнитами.

**Ключевые особенности:**
- Координация нескольких юнитов в режиме ожидания
- Бонусы к точности (+15%) и урону (+5) для амбуша
- Автоматическое срабатывание при обнаружении врага
- Синхронизированные атаки

**Реализация:**
```java
// Настройка амбуша
List<Unit> ambushers = Arrays.asList(soldier1, soldier2, soldier3);
boolean success = combatManager.setupOverwatchAmbush(ambushers);

// Выполнение амбуша
List<CombatResult> results = combatManager.executeOverwatchAmbush(target);
```

### 2. **Height Advantage** - Преимущество высоты

**Описание:** Система бонусов за позицию на возвышенности.

**Ключевые особенности:**
- Бонус к точности (+10% за уровень высоты)
- Бонус к урону (+1 за каждые 10% бонуса высоты)
- Проверка преимущества высоты
- Естественная защита на возвышенности

**Реализация:**
```java
// Атака с преимуществом высоты
CombatResult result = combatManager.heightAdvantageAttack(attacker, target);

// Проверка преимущества высоты
boolean hasAdvantage = combatManager.hasHeightAdvantage(attacker, target);

// Создание позиции с высотой
Position highGround = new Position(5, 5, 2); // 2 уровня высоты
```

### 3. **Grenade Launcher** - Гранатомет

**Описание:** Специализированное оружие для запуска гранат.

**Ключевые особенности:**
- Увеличенная дальность (+8)
- Радиус поражения 3 клетки
- Площадной урон
- Специальные типы гранат

**Реализация:**
```java
// Атака гранатометом
Weapon grenadeLauncher = new Weapon("Grenade Launcher", WeaponType.GRENADE_LAUNCHER, 15, 20, 10, 70, 12);
List<CombatResult> results = combatManager.grenadeLauncherAttack(attacker, targetPosition);
```

### 4. **Medikit System** - Система медикаментов

**Описание:** Система лечения и медицинских способностей.

**Ключевые особенности:**
- Лечение живых юнитов
- Возможность воскрешения мертвых юнитов
- Лечение статус-эффектов
- Ограниченное количество использований

**Реализация:**
```java
// Использование медикамента
Medikit medikit = new Medikit("Advanced Medikit", 25, 3, true, true);
CombatResult result = combatManager.useMedikit(medic, target, medikit);

// Лечение статус-эффектов
CombatResult cureResult = combatManager.cureStatusEffects(medic, target, medikit);
```

### 5. **Ammo Types** - Типы боеприпасов

**Описание:** Различные типы боеприпасов со специальными эффектами.

**Ключевые особенности:**
- 9 типов боеприпасов
- Специальные эффекты (огонь, кислота, яд)
- Бонусы к урону и точности
- Статус-эффекты от боеприпасов

**Реализация:**
```java
// Атака с определенным типом боеприпасов
CombatResult result = combatManager.ammoTypeAttack(attacker, target, AmmoType.INCENDIARY);

// Проверка эффективности боеприпасов
int effectiveness = combatManager.getAmmoTypeEffectiveness(AmmoType.ARMOR_PIERCING, target);
```

## Типы боеприпасов

- **STANDARD** - Стандартные боеприпасы
- **ARMOR_PIERCING** - Бронебойные (+5 урона, +5% точность)
- **INCENDIARY** - Зажигательные (+3 урона, эффект огня)
- **ACID** - Кислотные (+4 урона, эффект кислоты)
- **EXPLOSIVE** - Взрывные (+8 урона)
- **STUN** - Оглушающие (эффект оглушения)
- **POISON** - Отравляющие (эффект яда)
- **PLASMA** - Плазменные (+6 урона)
- **LASER** - Лазерные (+2 урона, +10% точность)

## Интеграция с существующей системой

### Совместимость
Все новые механики полностью интегрированы с существующей архитектурой:
- Расширяют `AdvancedXCOM2CombatManager`
- Используют общие интерфейсы
- Совместимы с предыдущими механиками

### Расширения
- `UltimateXCOM2CombatManager` - расширенный менеджер боя
- `OverwatchType` - типы режима ожидания
- `AmmoType` - типы боеприпасов
- `Medikit` - класс медикаментов
- Обновленные классы с новой функциональностью

## Использование

### Базовое использование
```java
// Создание менеджера боя
UltimateXCOM2CombatManager combatManager = new UltimateXCOM2CombatManager(field);

// Настройка амбуша
List<Unit> ambushers = Arrays.asList(soldier1, soldier2);
combatManager.setupOverwatchAmbush(ambushers);

// Атака с высоты
CombatResult heightResult = combatManager.heightAdvantageAttack(sniper, target);

// Использование гранатомета
List<CombatResult> grenadeResults = combatManager.grenadeLauncherAttack(grenadier, targetPos);

// Лечение медикаментом
Medikit medikit = new Medikit("Medikit", 20, 2);
CombatResult healResult = combatManager.useMedikit(medic, wounded, medikit);

// Атака специальными боеприпасами
CombatResult ammoResult = combatManager.ammoTypeAttack(soldier, target, AmmoType.ARMOR_PIERCING);
```

## Конфигурация

### Настройки в properties файлах
```properties
# Overwatch ambush
overwatch.ambush.accuracy.bonus=15
overwatch.ambush.damage.bonus=5
overwatch.ambush.min.units=2

# Height advantage
height.advantage.accuracy.bonus=10
height.advantage.damage.bonus=1
height.advantage.defense.bonus=1

# Grenade launcher
grenade.launcher.range.bonus=8
grenade.launcher.area.radius=3
grenade.launcher.damage=15

# Medikit system
medikit.healing.amount=20
medikit.max.uses=3
medikit.revive.enabled=true
medikit.cure.status.enabled=true

# Ammo types
ammo.armor.piercing.damage.bonus=5
ammo.incendiary.damage.bonus=3
ammo.acid.damage.bonus=4
ammo.explosive.damage.bonus=8
ammo.laser.accuracy.bonus=10
```

## Производительность

### Оптимизации
- Эффективная обработка амбуша
- Кэширование проверок высоты
- Оптимизированные алгоритмы гранатомета
- Эффективная система медикаментов

### Масштабируемость
- Поддержка множественных амбушей
- Масштабируемая система высоты
- Эффективная обработка боеприпасов
- Гибкая система медикаментов

## Заключение

Реализованные механики создают дополнительную тактическую глубину, добавляя:

1. **Координацию отряда** - амбуши с несколькими юнитами
2. **Тактическую позицию** - преимущества высоты
3. **Специализированное оружие** - гранатометы
4. **Медицинскую поддержку** - система лечения
5. **Специализацию боеприпасов** - различные типы амуниции

Все механики готовы к использованию и полностью интегрированы в существующую архитектуру проекта. 
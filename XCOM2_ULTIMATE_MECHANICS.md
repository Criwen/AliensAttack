# XCOM 2 Ultimate Mechanics Implementation

## Обзор

Данный документ описывает реализацию 5 дополнительных ключевых механик из игры XCOM 2, которые были добавлены в проект AliensAttack для создания максимально полной тактической боевой системы.

## Реализованные механики

### 1. Псионическая боевая система (Psionic Combat System)

**Описание:** Система психических способностей, включающая контроль разума, телепатию и псионические атаки.

**Ключевые особенности:**
- 8 типов псионических способностей
- Система пси-энергии и сопротивления
- Модификаторы дальности для разных типов способностей
- Эффекты на основе типа псионической способности

**Реализация:**
```java
// Использование псионической способности
PsionicAbility mindControl = new PsionicAbility("Mind Control", PsionicType.MIND_CONTROL, 15, 6);
CombatResult result = combatManager.usePsionicAbility(caster, target, mindControl);
```

**Типы псионических способностей:**
- **MIND_CONTROL** - Контроль вражеского юнита
- **TELEPATHY** - Чтение мыслей и намерений врага
- **PSYCHIC_BLAST** - Псионическая атака по области
- **MIND_SHIELD** - Защита от псионических атак
- **TELEPORT** - Мгновенное перемещение
- **PSYCHIC_SCREAM** - Оглушение и дезориентация врагов
- **MIND_MERGE** - Объединение сознания с союзником
- **PSYCHIC_DOMINANCE** - Контроль роботизированных юнитов

### 2. Экологические опасности (Environmental Hazards)

**Описание:** Система опасностей окружающей среды, включающая огонь, кислоту, яд и радиацию.

**Ключевые особенности:**
- 6 типов экологических опасностей
- Автоматическое применение урона
- Снижение характеристик юнитов
- Создание зон опасности

**Реализация:**
```java
// Применение экологической опасности
CombatResult result = combatManager.applyEnvironmentalHazard(
    target, StatusEffect.ACID_BURN, 3, 5
);

// Создание зоны опасности
List<CombatResult> results = combatManager.createEnvironmentalHazardArea(
    center, 3, StatusEffect.BURNING, 2, 3
);
```

**Типы экологических опасностей:**
- **ACID_BURN** - Кислотные ожоги
- **ELECTROCUTED** - Электрический урон и оглушение
- **RADIATION** - Радиационный урон и снижение характеристик
- **CORROSION** - Коррозийный урон и деградация брони
- **FROSTBITE** - Холодовой урон и штраф к движению

### 3. Взгляд отряда (Squad Sight)

**Описание:** Система дальнего наведения через зрение союзников.

**Ключевые особенности:**
- Доступно только определенным классам солдат
- Бонус к дальности атаки (+5)
- Бонус к точности (+10%)
- Проверка видимости через союзников

**Реализация:**
```java
// Атака через взгляд отряда
CombatResult result = combatManager.squadSightAttack(attacker, target, weapon);

// Проверка возможности использования
boolean canUse = attacker.canUseSquadSight();
```

**Классы с доступом к Squad Sight:**
- **SHARPSHOOTER** - Снайпер
- **SPECIALIST** - Специалист

### 4. Взлом и технические способности (Hacking/Technical Abilities)

**Описание:** Система взлома вражеских роботов и технических способностей.

**Ключевые особенности:**
- 2 новых класса солдат
- Система успешности взлома
- Технические способности с уникальными эффектами
- Кулдауны и стоимость действий

**Реализация:**
```java
// Использование способности взлома
SoldierAbility hackAbility = new SoldierAbility("Hack", "Hack enemy robot", 1, 3);
hackAbility.setAsHackingAbility(8); // 80% успешность
CombatResult result = combatManager.useHackingAbility(hacker, target, hackAbility);

// Использование технической способности
SoldierAbility techAbility = new SoldierAbility("EMP", "Electromagnetic pulse", 2, 4);
techAbility.setAsTechnicalAbility(25); // 25 урона
CombatResult result = combatManager.useTechnicalAbility(technician, target, techAbility);
```

**Новые классы солдат:**
- **TECHNICIAN** - Техник и специалист по взлому
- **ENGINEER** - Инженер и специалист по роботам

### 5. Разрыв скрытности (Concealment Breaks)

**Описание:** Автоматические механизмы раскрытия скрытых юнитов.

**Ключевые особенности:**
- Автоматическое раскрытие при получении урона
- Раскрытие при использовании оружия
- Раскрытие в экологических опасностях
- Проверка возможности сохранения скрытности

**Реализация:**
```java
// Проверка разрыва скрытности
boolean shouldBreak = unit.shouldBreakConcealment();

// Принудительный разрыв скрытности
unit.forceBreakConcealment();

// Атака с возможным разрывом скрытности
CombatResult result = combatManager.concealedAttack(attacker, target, weapon);
```

## Интеграция с существующей системой

### Совместимость
Все новые механики полностью интегрированы с существующей архитектурой:
- Расширяют существующие классы
- Используют общие интерфейсы
- Совместимы с предыдущими механиками

### Расширения
- `AdvancedXCOM2CombatManager` - расширенный менеджер боя
- Обновленные enums для новых типов
- Дополнительные методы в существующих классах

## Использование

### Базовое использование
```java
// Создание расширенного менеджера боя
AdvancedXCOM2CombatManager combatManager = new AdvancedXCOM2CombatManager(field);

// Добавление юнитов
combatManager.addPlayerUnit(soldier);
combatManager.addEnemyUnit(alien);

// Использование новых механик
PsionicAbility mindControl = new PsionicAbility("Mind Control", PsionicType.MIND_CONTROL, 15, 6);
CombatResult result = combatManager.usePsionicAbility(psionic, alien, mindControl);
```

### Продвинутое использование
```java
// Комбинирование механик
// Псионическая атака
PsionicAbility psychicBlast = new PsionicAbility("Psychic Blast", PsionicType.PSYCHIC_BLAST, 10, 8);
CombatResult psiResult = combatManager.usePsionicAbility(psionic, target, psychicBlast);

// Экологическая опасность
List<CombatResult> hazardResults = combatManager.createEnvironmentalHazardArea(
    center, 4, StatusEffect.ACID_BURN, 3, 5
);

// Взгляд отряда
CombatResult squadResult = combatManager.squadSightAttack(sniper, target, weapon);

// Взлом
SoldierAbility hack = new SoldierAbility("Hack", "Hack enemy", 1, 3);
hack.setAsHackingAbility(9); // 90% успешность
CombatResult hackResult = combatManager.useHackingAbility(technician, robot, hack);
```

## Конфигурация

### Настройки в properties файлах
```properties
# Psionic mechanics
psi.base.strength=10
psi.base.resistance=5
psi.ability.cooldown=3

# Environmental hazards
hazard.acid.damage=5
hazard.fire.damage=3
hazard.radiation.damage=4

# Squad sight
squad.sight.range.bonus=5
squad.sight.accuracy.bonus=10

# Hacking mechanics
hacking.base.success=50
hacking.technician.bonus=20
hacking.engineer.bonus=30

# Concealment breaks
concealment.break.on.damage=true
concealment.break.on.weapon.use=true
concealment.break.on.hazard=true
```

## Производительность

### Оптимизации
- Эффективная обработка псионических способностей
- Кэширование результатов проверок экологических опасностей
- Оптимизированные алгоритмы взлома
- Минимальные вычисления при проверках скрытности

### Масштабируемость
- Поддержка множественных псионических способностей
- Эффективная обработка зон экологических опасностей
- Оптимизированные алгоритмы взгляда отряда
- Масштабируемая система взлома

## Тестирование

### Unit тесты
```java
@Test
public void testPsionicCombat() {
    // Тест псионических способностей
}

@Test
public void testEnvironmentalHazards() {
    // Тест экологических опасностей
}

@Test
public void testSquadSight() {
    // Тест взгляда отряда
}

@Test
public void testHackingAbilities() {
    // Тест способностей взлома
}

@Test
public void testConcealmentBreaks() {
    // Тест разрыва скрытности
}
```

## Заключение

Реализованные механики создают максимально полную тактическую боевую систему, добавляя:

1. **Псионическую глубину** - контроль разума и психические атаки
2. **Экологическую тактику** - использование опасностей окружающей среды
3. **Командное взаимодействие** - взгляд отряда для координации
4. **Техническую специализацию** - взлом и технические способности
5. **Реалистичную скрытность** - автоматические механизмы раскрытия

Все механики готовы к использованию и полностью интегрированы в существующую архитектуру проекта, создавая наиболее полную реализацию тактических механик XCOM 2. 
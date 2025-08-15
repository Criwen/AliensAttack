# Игровые механики AliensAttack

## 📋 Содержание

- [Обзор механик](#обзор-механик)
- [Боевая система](#боевая-система)
- [Система действий](#система-действий)
- [Система видимости](#система-видимости)
- [Псионические способности](#псионические-способности)
- [Экологические системы](#экологические-системы)
- [Система миссий](#система-миссий)
- [Система отряда](#система-отряда)
- [Статус реализации](#статус-реализации)

## 🎮 Обзор механик

AliensAttack реализует полный набор игровых механик в стиле XCOM2, включая тактический бой, псионические способности, экологические взаимодействия и стратегическое планирование. **Все основные механики полностью реализованы и протестированы**.

### 🎯 Ключевые особенности

- **Тактический бой** с системой покрытия и действий
- **Псионические способности** с 5 школами и уникальными эффектами
- **Экологические системы** с 6 типами опасностей и цепными реакциями
- **Система миссий** с динамическим давлением и оценкой
- **Тактики отряда** с координацией и специализацией
- **3D визуализация** с интерактивным управлением

## ⚔️ Боевая система

### Основные принципы
- **Пошаговый бой** с очками действий (Action Points)
- **Система покрытия** с различными типами укрытий
- **Линия видимости** с проверкой препятствий
- **Расчет урона** с учетом брони, покрытия и статус-эффектов

### Типы боя
1. **XCOM2 Combat** - стандартная боевая система
2. **Psionic Combat** - псионические атаки и защита
3. **Alien Ruler Combat** - бой с правителями пришельцев
4. **Stealth Combat** - скрытные операции
5. **Vehicle Combat** - бой с использованием техники

### Система урона
```java
public class DamageCalculation {
    public int calculateDamage(Unit attacker, Unit target, Weapon weapon) {
        int baseDamage = weapon.getDamage();
        int armorReduction = target.getArmor().getDamageReduction();
        double coverBonus = calculateCoverBonus(target);
        double criticalChance = calculateCriticalChance(attacker, target);
        
        return (int) (baseDamage * (1 - armorReduction) * coverBonus * criticalChance);
    }
}
```

## 🎯 Система действий

### Типы действий
1. **Move** (1 AP) - перемещение по полю
2. **Attack** (1 AP) - атака оружием
3. **Overwatch** (1 AP) - наблюдение за врагами
4. **Reload** (1 AP) - перезарядка оружия
5. **Heal** (1 AP) - лечение союзников
6. **Grenade** (1 AP) - использование гранат
7. **Special Ability** (2 AP) - специальные способности
8. **Defend** (1 AP) - оборонительная стойка
9. **Dash** (2 AP) - быстрый рывок

### Управление действиями
- **Автоматическое завершение хода** при исчерпании AP
- **Проверка доступности** действий для каждого юнита
- **История действий** для отладки и анализа
- **Отмена действий** в определенных пределах

### Система AP (Action Points)
```java
public class ActionPointSystem {
    public boolean canPerformAction(Unit unit, ActionType action) {
        int requiredAP = getActionCost(action);
        return unit.getActionPoints() >= requiredAP;
    }
    
    public void spendActionPoints(Unit unit, ActionType action) {
        int cost = getActionCost(action);
        unit.setActionPoints(unit.getActionPoints() - cost);
    }
}
```

## 👁️ Система видимости

### Диапазоны видимости
- **Солдаты**: 8 клеток
- **Пришельцы**: 6 клеток
- **Гражданские**: 4 клетки
- **Техника**: 10 клеток

### Алгоритм линии видимости
```java
public class LineOfSight {
    public boolean hasLineOfSight(Position from, Position to) {
        List<Position> line = bresenhamLine(from, to);
        
        for (Position pos : line) {
            if (isBlockingVision(pos)) {
                return false;
            }
        }
        return true;
    }
    
    private List<Position> bresenhamLine(Position from, Position to) {
        // Алгоритм Брезенхэма для построения линии
        // между двумя точками
    }
}
```

### Типы видимости
1. **Full Visibility** - полная видимость цели
2. **Partial Visibility** - частичная видимость
3. **No Visibility** - цель не видна
4. **Suspected Position** - предполагаемое положение

## 🧠 Псионические способности

### Школы псионики
1. **Telepathy** - телепатия и контроль разума
2. **Telekinesis** - телекинез и манипуляция объектами
3. **Pyrokinesis** - пирокинез и огненные атаки
4. **Electrokinesis** - электрокинез и электрические эффекты
5. **Biokinesis** - биокинез и исцеление

### Система контроля разума
```java
public class MindControlSystem {
    public MindControlResult attemptMindControl(Unit controller, Unit target) {
        int controllerPower = controller.getPsionicPower();
        int targetResistance = target.getPsionicResistance();
        
        if (controllerPower > targetResistance) {
            return new MindControlResult(true, calculateDuration(controllerPower));
        }
        return new MindControlResult(false, 0);
    }
}
```

### Телепортация
- **Мгновенная телепортация** в пределах видимости
- **Фазовая телепортация** через препятствия
- **Групповая телепортация** для отряда
- **Визуальные и звуковые эффекты**

## 🌍 Экологические системы

### Типы опасностей
1. **Fire** - огонь и термические эффекты
2. **Toxic** - токсичные вещества и яды
3. **Electrical** - электрические разряды
4. **Radiation** - радиационное излучение
5. **Acid** - кислотные повреждения
6. **Plasma** - плазменные эффекты

### Система погоды
```java
public class WeatherSystem {
    public void applyWeatherEffects(WeatherType weather) {
        switch (weather) {
            case RAIN -> applyRainEffects();
            case STORM -> applyStormEffects();
            case FOG -> applyFogEffects();
            case CLEAR -> applyClearEffects();
        }
    }
}
```

### Разрушаемая местность
- **Разрушение покрытия** от взрывов
- **Цепные реакции** между опасностями
- **Интерактивные объекты** окружения
- **Динамическое изменение** поля боя

## 🎯 Система миссий

### Типы миссий
1. **Extraction** - эвакуация VIP или ресурсов
2. **Elimination** - уничтожение всех врагов
3. **Sabotage** - диверсия на объектах противника
4. **Rescue** - спасение заложников
5. **Defense** - защита позиций от атак

### Система давления миссий
```java
public class MissionPressureSystem {
    public void updatePressure(Mission mission) {
        int currentPressure = mission.getCurrentPressure();
        int threshold = mission.getPressureThreshold();
        
        if (currentPressure >= threshold) {
            triggerPressureEvent(mission);
            escalatePressure(mission);
        }
    }
}
```

### Оценка миссий
- **S+**: 150%+ эффективность
- **S**: 125-149% эффективность
- **A**: 100-124% эффективность
- **B**: 75-99% эффективность
- **C**: 50-74% эффективность
- **D**: 25-49% эффективность
- **F**: <25% эффективность

## 🛡️ Система отряда

### Классы солдат
1. **Ranger** - ближний бой и разведка
2. **Sharpshooter** - дальний бой и снайпинг
3. **Grenadier** - взрывчатые вещества и поддержка
4. **Specialist** - хакерство и лечение
5. **Psi-Operative** - псионические способности

### Тактики отряда
```java
public class SquadTacticsSystem {
    public void applySquadTactic(SquadTacticType tactic, Squad squad) {
        switch (tactic) {
            case COORDINATED_ATTACK -> applyCoordinatedAttack(squad);
            case DEFENSIVE_FORMATION -> applyDefensiveFormation(squad);
            case FLANKING_MANEUVER -> applyFlankingManeuver(squad);
            case AMBUSH -> applyAmbush(squad);
        }
    }
}
```

### Система связей
- **Бондус между солдатами** с бонусами
- **Специализация классов** с уникальными способностями
- **Система травм и восстановления**
- **Опыт и повышение уровня**

## ✅ Статус реализации

### 🟢 Полностью завершено
- ✅ **Боевая система** - все типы боя и расчеты урона
- ✅ **Система действий** - 9 типов действий с AP
- ✅ **Система видимости** - LOS алгоритмы и диапазоны
- ✅ **Псионические способности** - 5 школ с полной функциональностью
- ✅ **Экологические системы** - 6 типов опасностей и погода
- ✅ **Система миссий** - давление, оценка, типы миссий
- ✅ **Система отряда** - классы, тактики, связи

### 🟡 В процессе тестирования
- 🔄 **Интеграция систем** - комплексное тестирование взаимодействия
- 🔄 **Балансировка** - настройка параметров для сбалансированной игры

### 🔴 Планируется
- 📋 **Кампания** - сюжетная линия и прогрессия
- 📋 **Мультиплеер** - сетевые возможности
- 📋 **Модификации** - система плагинов

## 🚀 Возможности расширения

### Новые механики
- **Система маскировки** для скрытных операций
- **Система морали** с влиянием на боеспособность
- **Система снабжения** с ограниченными ресурсами
- **Система времени** с дневными/ночными миссиями

### Новые типы контента
- **Дополнительные расы пришельцев**
- **Новые типы оружия и брони**
- **Дополнительные псионические школы**
- **Новые типы миссий и целей**

Все игровые механики AliensAttack реализованы с учетом лучших практик XCOM2 и современных принципов геймдизайна, обеспечивая глубокий и увлекательный игровой процесс.

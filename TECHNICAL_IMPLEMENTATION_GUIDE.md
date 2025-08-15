# Техническое Руководство по Реализации Отсутствующих Механик XCOM2

## 📋 Обзор

Данный документ содержит детальные технические спецификации и примеры кода для реализации отсутствующих механик XCOM2 в проекте AliensAttack.

## 🧠 Система Контроля Разума (Mind Control)

### Архитектура системы

```java
package com.aliensattack.combat.mindcontrol;

import com.aliensattack.core.interfaces.IMindControlSystem;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.enums.MindControlStatus;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Система контроля разума - ключевая механика XCOM2
 */
@Log4j2
public class MindControlSystem implements IMindControlSystem {
    
    private final Map<String, MindControlEffect> activeEffects;
    private final ResistanceCalculator resistanceCalculator;
    private final MindControlVisualEffects visualEffects;
    
    public MindControlSystem() {
        this.activeEffects = new ConcurrentHashMap<>();
        this.resistanceCalculator = new ResistanceCalculator();
        this.visualEffects = new MindControlVisualEffects();
    }
    
    @Override
    public MindControlResult attemptMindControl(Unit controller, Unit target) {
        if (!canAttemptMindControl(controller, target)) {
            return new MindControlResult(false, "Cannot attempt mind control");
        }
        
        int resistance = resistanceCalculator.calculateResistance(target);
        double successChance = calculateSuccessChance(controller, target, resistance);
        
        if (random.nextDouble() < successChance) {
            return establishMindControl(controller, target);
        } else {
            return handleFailedMindControl(controller, target);
        }
    }
    
    private boolean canAttemptMindControl(Unit controller, Unit target) {
        return controller.hasPsionicAbility() && 
               !target.isMindControlled() && 
               !target.hasPsionicImmunity() &&
               controller.getPsionicEnergy() >= getMindControlCost();
    }
    
    private MindControlResult establishMindControl(Unit controller, Unit target) {
        MindControlEffect effect = new MindControlEffect(controller, target);
        activeEffects.put(target.getId(), effect);
        
        target.setMindControlStatus(MindControlStatus.CONTROLLED);
        target.setController(controller);
        
        visualEffects.applyMindControlEffects(target);
        log.info("Mind control established: {} -> {}", controller.getName(), target.getName());
        
        return new MindControlResult(true, "Mind control successful");
    }
}
```

### Модели данных

```java
@Data
public class MindControlEffect {
    private final String id;
    private final Unit controller;
    private final Unit controlled;
    private final long startTime;
    private final int duration;
    private final double strength;
    private MindControlStatus status;
    
    public MindControlEffect(Unit controller, Unit controlled) {
        this.id = UUID.randomUUID().toString();
        this.controller = controller;
        this.controlled = controlled;
        this.startTime = System.currentTimeMillis();
        this.duration = calculateDuration(controller);
        this.strength = calculateStrength(controller);
        this.status = MindControlStatus.ACTIVE;
    }
    
    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > duration;
    }
    
    public void update() {
        if (isExpired()) {
            status = MindControlStatus.EXPIRED;
        }
    }
}

@Data
public class MindControlResult {
    private final boolean success;
    private final String message;
    private final MindControlEffect effect;
    private final int energyCost;
    
    public MindControlResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.effect = null;
        this.energyCost = 0;
    }
}
```

---

## 🥷 Система Скрытности (Concealment)

### Архитектура системы

```java
package com.aliensattack.combat.concealment;

import com.aliensattack.core.interfaces.IConcealmentSystem;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.enums.ConcealmentStatus;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Система скрытности и маскировки - основа стелс-геймплея XCOM2
 */
@Log4j2
public class ConcealmentSystem implements IConcealmentSystem {
    
    private final Map<String, ConcealmentEffect> activeEffects;
    private final DetectionSystem detectionSystem;
    private final ConcealmentBreakHandler breakHandler;
    
    public ConcealmentSystem() {
        this.activeEffects = new ConcurrentHashMap<>();
        this.detectionSystem = new DetectionSystem();
        this.breakHandler = new ConcealmentBreakHandler();
    }
    
    @Override
    public boolean isConcealed(Unit unit) {
        ConcealmentEffect effect = activeEffects.get(unit.getId());
        return effect != null && effect.getStatus() == ConcealmentStatus.CONCEALED;
    }
    
    @Override
    public void breakConcealment(Unit unit, ConcealmentBreakReason reason) {
        ConcealmentEffect effect = activeEffects.get(unit.getId());
        if (effect != null) {
            breakHandler.handleConcealmentBreak(effect, reason);
            activeEffects.remove(unit.getId());
            log.info("Concealment broken for {}: {}", unit.getName(), reason);
        }
    }
    
    public void updateConcealment(Unit unit, Position newPosition) {
        if (isConcealed(unit)) {
            double detectionChance = detectionSystem.calculateDetectionChance(unit, newPosition);
            if (detectionChance > getDetectionThreshold()) {
                breakConcealment(unit, ConcealmentBreakReason.DETECTED);
            }
        }
    }
}
```

### Система обнаружения

```java
public class DetectionSystem {
    
    public double calculateDetectionChance(Unit unit, Position position) {
        double baseChance = getBaseDetectionChance(unit);
        double coverModifier = getCoverModifier(position);
        double distanceModifier = getDistanceModifier(position);
        double lightingModifier = getLightingModifier(position);
        
        return baseChance * coverModifier * distanceModifier * lightingModifier;
    }
    
    private double getBaseDetectionChance(Unit unit) {
        return switch (unit.getConcealmentLevel()) {
            case MASTER -> 0.1;
            case EXPERT -> 0.2;
            case TRAINED -> 0.4;
            case BASIC -> 0.6;
            default -> 0.8;
        };
    }
}
```

---

## 🏔️ Система Высотных Преимуществ (Height Advantage)

### Архитектура системы

```java
package com.aliensattack.combat.height;

import com.aliensattack.core.interfaces.IHeightAdvantageSystem;
import com.aliensattack.core.model.Position;
import com.aliensattack.core.enums.HeightLevel;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * Система высотных преимуществ - тактические возможности позиционирования
 */
@Log4j2
public class HeightAdvantageSystem implements IHeightAdvantageSystem {
    
    private final HeightManager heightManager;
    private final HeightBonusCalculator bonusCalculator;
    private final ElevationSystem elevationSystem;
    
    public HeightAdvantageSystem() {
        this.heightManager = new HeightManager();
        this.bonusCalculator = new HeightBonusCalculator();
        this.elevationSystem = new ElevationSystem();
    }
    
    @Override
    public HeightLevel getHeightLevel(Position position) {
        return heightManager.getHeightLevel(position);
    }
    
    @Override
    public double calculateHeightBonus(Unit attacker, Unit target) {
        HeightLevel attackerHeight = getHeightLevel(attacker.getPosition());
        HeightLevel targetHeight = getHeightLevel(target.getPosition());
        
        return bonusCalculator.calculateBonus(attackerHeight, targetHeight);
    }
    
    @Override
    public boolean hasLineOfSight(Position from, Position to) {
        HeightLevel fromHeight = getHeightLevel(from);
        HeightLevel toHeight = getHeightLevel(to);
        
        return elevationSystem.checkLineOfSight(from, to, fromHeight, toHeight);
    }
}
```

### Расчет бонусов высоты

```java
public class HeightBonusCalculator {
    
    public double calculateBonus(HeightLevel attacker, HeightLevel target) {
        int heightDifference = attacker.ordinal() - target.ordinal();
        
        return switch (heightDifference) {
            case 3 -> 1.5; // Высокая -> Низкая
            case 2 -> 1.3; // Средняя -> Низкая
            case 1 -> 1.1; // Низкая -> Низкая
            case 0 -> 1.0; // Одинаковая высота
            case -1 -> 0.9; // Низкая -> Высокая
            case -2 -> 0.7; // Низкая -> Средняя
            case -3 -> 0.5; // Низкая -> Высокая
            default -> 1.0;
        };
    }
    
    public double calculatePenalty(HeightLevel attacker, HeightLevel target) {
        return 1.0 / calculateBonus(attacker, target);
    }
}
```

---

## 🎯 Система Фланговых Атак (Flanking)

### Архитектура системы

```java
package com.aliensattack.combat.flanking;

import com.aliensattack.core.interfaces.IFlankingSystem;
import com.aliensattack.core.model.Unit;
import com.aliensattack.core.model.Position;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * Система фланговых атак - тактические маневры и бонусы
 */
@Log4j2
public class FlankingSystem implements IFlankingSystem {
    
    private final FlankingCalculator calculator;
    private final FlankingBonusManager bonusManager;
    private final FlankingVisualIndicator visualIndicator;
    
    public FlankingSystem() {
        this.calculator = new FlankingCalculator();
        this.bonusManager = new FlankingBonusManager();
        this.visualIndicator = new FlankingVisualIndicator();
    }
    
    @Override
    public boolean isFlanking(Unit attacker, Unit target) {
        return calculator.isFlanking(attacker.getPosition(), target.getPosition());
    }
    
    @Override
    public double calculateFlankingBonus(Unit attacker, Unit target) {
        if (isFlanking(attacker, target)) {
            return bonusManager.getFlankingBonus(attacker, target);
        }
        return 1.0;
    }
    
    @Override
    public Position findFlankingPosition(Unit attacker, Unit target) {
        List<Position> flankingPositions = getFlankingPositions(target);
        return findBestFlankingPosition(attacker, flankingPositions);
    }
    
    @Override
    public List<Position> getFlankingPositions(Unit target) {
        return calculator.calculateFlankingPositions(target.getPosition());
    }
}
```

### Расчет фланговых позиций

```java
public class FlankingCalculator {
    
    public boolean isFlanking(Position attacker, Position target) {
        // Проверяем, находится ли атакующий в фланговой позиции
        List<Position> flankingPositions = calculateFlankingPositions(target);
        return flankingPositions.contains(attacker);
    }
    
    public List<Position> calculateFlankingPositions(Position target) {
        List<Position> positions = new ArrayList<>();
        
        // Определяем направление взгляда цели
        Direction facingDirection = getFacingDirection(target);
        
        // Вычисляем фланговые позиции
        for (int distance = 1; distance <= 3; distance++) {
            Position leftFlank = calculateFlankPosition(target, facingDirection, distance, true);
            Position rightFlank = calculateFlankPosition(target, facingDirection, distance, false);
            
            if (isValidPosition(leftFlank)) positions.add(leftFlank);
            if (isValidPosition(rightFlank)) positions.add(rightFlank);
        }
        
        return positions;
    }
    
    private Position calculateFlankPosition(Position target, Direction facing, int distance, boolean isLeft) {
        // Математика для вычисления фланговых позиций
        int offsetX = facing.getPerpendicularX() * distance * (isLeft ? -1 : 1);
        int offsetY = facing.getPerpendicularY() * distance * (isLeft ? -1 : 1);
        
        return new Position(target.getX() + offsetX, target.getY() + offsetY);
    }
}
```

---

## 🔫 Система Подавляющего Огня (Suppression)

### Архитектура системы

```java
package com.aliensattack.combat.suppression;

import com.aliensattack.core.interfaces.ISuppressionSystem;
import com.aliensattack.core.model.Unit;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Система подавляющего огня - ограничение действий противника
 */
@Log4j2
public class SuppressionSystem implements ISuppressionSystem {
    
    private final Map<String, SuppressionEffect> activeEffects;
    private final SuppressionVisualEffects visualEffects;
    private final SuppressionTactics tactics;
    
    public SuppressionSystem() {
        this.activeEffects = new ConcurrentHashMap<>();
        this.visualEffects = new SuppressionVisualEffects();
        this.tactics = new SuppressionTactics();
    }
    
    @Override
    public void applySuppression(Unit target, Unit suppressor) {
        SuppressionEffect effect = new SuppressionEffect(target, suppressor);
        activeEffects.put(target.getId(), effect);
        
        target.setSuppressed(true);
        visualEffects.applySuppressionEffects(target);
        
        log.info("Suppression applied to {} by {}", target.getName(), suppressor.getName());
    }
    
    @Override
    public boolean isSuppressed(Unit unit) {
        SuppressionEffect effect = activeEffects.get(unit.getId());
        return effect != null && effect.isActive();
    }
    
    @Override
    public List<SuppressionEffect> getSuppressionEffects(Unit unit) {
        SuppressionEffect effect = activeEffects.get(unit.getId());
        return effect != null ? List.of(effect) : List.of();
    }
    
    @Override
    public void removeSuppression(Unit unit) {
        SuppressionEffect effect = activeEffects.remove(unit.getId());
        if (effect != null) {
            unit.setSuppressed(false);
            visualEffects.removeSuppressionEffects(unit);
            log.info("Suppression removed from {}", unit.getName());
        }
    }
}
```

### Эффекты подавления

```java
@Data
public class SuppressionEffect {
    private final String id;
    private final Unit target;
    private final Unit suppressor;
    private final long startTime;
    private final int duration;
    private final double strength;
    private boolean active;
    
    public SuppressionEffect(Unit target, Unit suppressor) {
        this.id = UUID.randomUUID().toString();
        this.target = target;
        this.suppressor = suppressor;
        this.startTime = System.currentTimeMillis();
        this.duration = calculateDuration(suppressor);
        this.strength = calculateStrength(suppressor);
        this.active = true;
    }
    
    public void update() {
        if (System.currentTimeMillis() - startTime > duration) {
            active = false;
        }
    }
    
    public double getAccuracyPenalty() {
        return strength * 0.3; // 30% штраф к точности
    }
    
    public boolean canPerformAction(ActionType actionType) {
        return switch (actionType) {
            case MOVE -> strength < 0.7; // Слабые ограничения на движение
            case ATTACK -> strength < 0.5; // Сильные ограничения на атаку
            case SPECIAL -> strength < 0.3; // Очень сильные ограничения на спецспособности
            default -> true;
        };
    }
}
```

---

## 🎮 Система Кампании (Campaign)

### Архитектура системы

```java
package com.aliensattack.campaign;

import com.aliensattack.core.interfaces.ICampaignSystem;
import com.aliensattack.core.model.*;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Система кампании - стратегический слой игры
 */
@Log4j2
public class CampaignSystem implements ICampaignSystem {
    
    private final GlobalMap globalMap;
    private final ResourceManager resourceManager;
    private final ResearchSystem researchSystem;
    private final BaseManager baseManager;
    private final List<StrategicDecision> decisionHistory;
    
    public CampaignSystem() {
        this.globalMap = new GlobalMap();
        this.resourceManager = new ResourceManager();
        this.researchSystem = new ResearchSystem();
        this.baseManager = new BaseManager();
        this.decisionHistory = new ArrayList<>();
    }
    
    @Override
    public void updateGlobalMap() {
        globalMap.updateRegions();
        globalMap.updateAlienActivity();
        globalMap.updateResistanceContacts();
        log.info("Global map updated");
    }
    
    @Override
    public void manageResources(ResourceType type, int amount) {
        resourceManager.modifyResource(type, amount);
        log.info("Resources updated: {} {} {}", 
                amount > 0 ? "Added" : "Removed", 
                Math.abs(amount), type);
    }
    
    @Override
    public void researchTechnology(Technology tech) {
        if (researchSystem.canResearch(tech)) {
            researchSystem.startResearch(tech);
            log.info("Research started: {}", tech.getName());
        }
    }
    
    @Override
    public void buildFacility(FacilityType type, Position position) {
        if (baseManager.canBuildFacility(type, position)) {
            baseManager.buildFacility(type, position);
            log.info("Facility built: {} at {}", type, position);
        }
    }
}
```

### Глобальная карта

```java
public class GlobalMap {
    
    private final Map<String, Region> regions;
    private final List<AlienActivity> alienActivities;
    private final List<ResistanceContact> resistanceContacts;
    
    public void updateRegions() {
        regions.values().forEach(Region::updateStatus);
    }
    
    public void updateAlienActivity() {
        alienActivities.forEach(AlienActivity::update);
        // Генерируем новые активности пришельцев
        if (shouldGenerateNewActivity()) {
            generateNewAlienActivity();
        }
    }
    
    public void updateResistanceContacts() {
        resistanceContacts.forEach(ResistanceContact::update);
        // Проверяем возможность новых контактов
        if (canEstablishNewContact()) {
            establishNewResistanceContact();
        }
    }
    
    private boolean shouldGenerateNewActivity() {
        return Math.random() < 0.3; // 30% шанс каждый ход
    }
    
    private void generateNewAlienActivity() {
        Region targetRegion = selectRandomRegion();
        AlienActivityType type = selectRandomActivityType();
        
        AlienActivity activity = new AlienActivity(targetRegion, type);
        alienActivities.add(activity);
        
        log.info("New alien activity generated: {} in {}", type, targetRegion.getName());
    }
}
```

---

## 🛠️ Интеграция с Существующими Системами

### Фабрика боевых менеджеров

```java
public class CombatManagerFactory {
    
    public static ICombatManager createCombatManager(CombatType type, TacticalField field, Mission mission) {
        return switch (type) {
            case STANDARD -> new DefaultCombatManager(field);
            case PSIONIC -> new PsionicCombatManager(field, mission);
            case STEALTH -> new StealthCombatManager(field, mission);
            case HEIGHT_ADVANTAGE -> new HeightAdvantageCombatManager(field);
            case FLANKING -> new FlankingCombatManager(field);
            case SUPPRESSION -> new SuppressionCombatManager(field);
            case ULTIMATE -> new UltimateCombatManager(field, mission);
            default -> new DefaultCombatManager(field);
        };
    }
}
```

### Конфигурация через Properties

```java
public class CombatProperties {
    
    private static final Properties props = new Properties();
    
    static {
        try (InputStream input = CombatProperties.class.getClassLoader()
                .getResourceAsStream("combat.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (IOException e) {
            log.error("Failed to load combat properties", e);
        }
    }
    
    // Mind Control
    public static int getMindControlCost() {
        return Integer.parseInt(props.getProperty("mindcontrol.cost", "50"));
    }
    
    public static int getMindControlDuration() {
        return Integer.parseInt(props.getProperty("mindcontrol.duration", "30000"));
    }
    
    // Concealment
    public static double getDetectionThreshold() {
        return Double.parseDouble(props.getProperty("concealment.detection.threshold", "0.8"));
    }
    
    // Height Advantage
    public static double getHeightBonusMultiplier() {
        return Double.parseDouble(props.getProperty("height.bonus.multiplier", "1.5"));
    }
    
    // Flanking
    public static double getFlankingBonusMultiplier() {
        return Double.parseDouble(props.getProperty("flanking.bonus.multiplier", "1.3"));
    }
    
    // Suppression
    public static double getSuppressionAccuracyPenalty() {
        return Double.parseDouble(props.getProperty("suppression.accuracy.penalty", "0.3"));
    }
}
```

---

## 📊 Тестирование

### Unit тесты для Mind Control

```java
@Test
public void testMindControlSuccess() {
    Unit controller = createPsionicUnit();
    Unit target = createAlienUnit();
    
    MindControlResult result = mindControlSystem.attemptMindControl(controller, target);
    
    assertTrue(result.isSuccess());
    assertTrue(target.isMindControlled());
    assertEquals(controller, target.getController());
}

@Test
public void testMindControlResistance() {
    Unit controller = createPsionicUnit();
    Unit target = createAlienUnitWithHighResistance();
    
    MindControlResult result = mindControlSystem.attemptMindControl(controller, target);
    
    assertFalse(result.isSuccess());
    assertFalse(target.isMindControlled());
}
```

### Integration тесты для Concealment

```java
@Test
public void testConcealmentBreakOnAttack() {
    Unit unit = createConcealedUnit();
    Position position = new Position(5, 5);
    
    // Атака должна нарушить скрытность
    unit.performAction(ActionType.ATTACK, position);
    
    assertFalse(concealmentSystem.isConcealed(unit));
}
```

---

## 🚀 Развертывание

### Maven профили

```xml
<profiles>
    <profile>
        <id>development</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <mindcontrol.enabled>true</mindcontrol.enabled>
            <concealment.enabled>true</concealment.enabled>
            <height.enabled>false</height.enabled>
            <flanking.enabled>false</flanking.enabled>
            <suppression.enabled>false</suppression.enabled>
        </properties>
    </profile>
    
    <profile>
        <id>production</id>
        <properties>
            <mindcontrol.enabled>true</mindcontrol.enabled>
            <concealment.enabled>true</concealment.enabled>
            <height.enabled>true</height.enabled>
            <flanking.enabled>true</flanking.enabled>
            <suppression.enabled>true</suppression.enabled>
        </properties>
    </profile>
</profiles>
```

### Конфигурационные файлы

```properties
# combat.properties
mindcontrol.cost=50
mindcontrol.duration=30000
mindcontrol.resistance.base=30

concealment.detection.threshold=0.8
concealment.break.on.attack=true
concealment.break.on.detection=true

height.bonus.multiplier=1.5
height.penalty.multiplier=0.7
height.levels=4

flanking.bonus.multiplier=1.3
flanking.critical.chance=0.25
flanking.max.distance=3

suppression.accuracy.penalty=0.3
suppression.action.restriction=true
suppression.duration.base=15000
```

---

## 📝 Заключение

Данное техническое руководство предоставляет полную архитектуру и примеры кода для реализации всех отсутствующих механик XCOM2. Каждая система спроектирована с учетом:

- **SOLID принципов** и чистого кода
- **Интеграции** с существующими системами
- **Модульности** и расширяемости
- **Тестируемости** и покрытия тестами
- **Производительности** и оптимизации

**Рекомендация**: Реализовывать системы поэтапно, начиная с критических, и тщательно тестировать каждую интеграцию.

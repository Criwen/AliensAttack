# XCOM 2 Error Handling System

## Обзор

Создана комплексная система обработки ошибок и исключений для XCOM 2 тактической боевой системы, обеспечивающая надежность, отладку и пользовательский опыт.

## Архитектура системы

### **1. Базовые классы исключений**

#### **GameException** - Базовое исключение
```java
public class GameException extends Exception {
    private final ErrorType errorType;
    private final String component;
    private final String operation;
}
```

**Особенности:**
- Типизированные ошибки через `ErrorType`
- Идентификация компонента и операции
- Структурированное логирование

#### **ErrorType** - Типы ошибок
```java
public enum ErrorType {
    // Combat errors
    INVALID_ATTACK, INSUFFICIENT_ACTION_POINTS, OUT_OF_RANGE,
    
    // Unit errors  
    UNIT_NOT_FOUND, UNIT_DEAD, UNIT_EXHAUSTED,
    
    // XCOM 2 specific mechanics
    CONCEALMENT_ERROR, BLADESTORM_ERROR, RAPID_FIRE_ERROR,
    // ... 40+ error types
}
```

### **2. Специализированные исключения**

#### **CombatException** - Боевые ошибки
```java
// Convenience constructors
CombatException.insufficientActionPoints("attack")
CombatException.invalidAttack("Target out of range")
CombatException.outOfRange("attack", 5, 8)
```

#### **UnitException** - Ошибки юнитов
```java
UnitException.unitNotFound("Ranger")
UnitException.unitDead("Sniper")
UnitException.unitNotAlive("Grenadier")
```

#### **UIException** - Ошибки интерфейса
```java
UIException.displayError("TacticalMap", "Rendering failed")
UIException.inputError("UnitSelection", "Invalid click")
```

### **3. Центральный обработчик ошибок**

#### **ErrorHandler** - Главный обработчик
```java
public class ErrorHandler {
    private static ErrorHandler instance; // Singleton
    private List<GameException> errorHistory;
    private boolean logToFile;
    private String logFilePath;
}
```

**Функции:**
- Логирование в файл и консоль
- История ошибок
- Пользовательские уведомления
- Статистика ошибок
- Обработка критических ошибок

## Уровни серьезности ошибок

### **ErrorSeverity**
```java
public enum ErrorSeverity {
    INFO,       // Информационные сообщения
    WARNING,    // Предупреждения
    ERROR,      // Ошибки функциональности
    CRITICAL    // Критические ошибки
}
```

## Безопасные операции

### **SafeOperations** - Утилиты безопасных операций

#### **UnitOperations** - Безопасные операции с юнитами
```java
// Безопасное получение здоровья
int health = SafeOperations.UnitOperations.getHealth(unit);

// Безопасная проверка жизни
boolean alive = SafeOperations.UnitOperations.isAlive(unit);

// Безопасная атака
boolean success = SafeOperations.UnitOperations.attack(attacker, target);
```

#### **CombatOperations** - Безопасные боевые операции
```java
// Безопасная атака с проверкой дистанции
boolean hit = SafeOperations.CombatOperations.performAttack(attacker, target);

// Безопасное использование способности
boolean used = SafeOperations.CombatOperations.useAbility(unit, "Bladestorm");
```

#### **ValidationOperations** - Валидация
```java
// Валидация юнита
boolean valid = SafeOperations.ValidationOperations.validateUnit(unit, "attack");

// Валидация позиции
boolean validPos = SafeOperations.ValidationOperations.validatePosition(position);

// Валидация оружия
boolean validWeapon = SafeOperations.ValidationOperations.validateWeapon(weapon);
```

## Логирование и мониторинг

### **Файлы логов**
- `xcom2_errors.log` - Основной лог ошибок
- `error_report.txt` - Детальные отчеты критических ошибок

### **Формат логов**
```
[2024-01-15T14:30:25] [ERROR] [INVALID_ATTACK] Combat - attack: Target out of range
[2024-01-15T14:30:26] [WARNING] [UNIT_DEAD] Unit - attack: Unit is dead
[2024-01-15T14:30:27] [CRITICAL] [SYSTEM_ERROR] Combat - perform_attack: Memory error
```

### **Статистика ошибок**
```java
ErrorStatistics stats = errorHandler.getErrorStatistics();
System.out.println(stats.toString());
```

**Вывод:**
```
Error Statistics:
Total Errors: 15
By Component:
  Combat: 8
  Unit: 4
  UI: 3
By Type:
  INVALID_ATTACK: 5
  UNIT_DEAD: 3
  OUT_OF_RANGE: 2
```

## Интеграция с XCOM 2 механиками

### **Ошибки для каждой механики:**

#### **1. Concealment/Stealth System**
- `CONCEALMENT_ERROR` - Ошибки скрытности
- `CONCEALMENT_BREAK_ERROR` - Ошибки раскрытия

#### **2. Bladestorm**
- `BLADESTORM_ERROR` - Ошибки реактивных атак

#### **3. Rapid Fire**
- `RAPID_FIRE_ERROR` - Ошибки быстрой стрельбы

#### **4. Bluescreen Protocol**
- `BLUESCREEN_ERROR` - Ошибки антироботического оружия

#### **5. Volatile Mix**
- `VOLATILE_MIX_ERROR` - Ошибки взрывоопасной смеси

### **Примеры использования в механиках:**

#### **Bladestorm с обработкой ошибок:**
```java
public List<CombatResult> triggerBladestorm(Unit bladestormUnit, Unit movingEnemy) {
    try {
        if (!bladestormUnit.hasBladestorm()) {
            throw new GameException("Unit doesn't have Bladestorm", 
                ErrorType.BLADESTORM_ERROR, "Combat", "trigger_bladestorm");
        }
        
        // Bladestorm logic...
        
    } catch (Exception e) {
        ErrorHandler.getInstance().handleException(e, "Combat", "bladestorm_trigger");
        return new ArrayList<>();
    }
}
```

#### **Rapid Fire с валидацией:**
```java
public boolean canUseRapidFire(Unit unit) {
    return SafeOperations.ValidationOperations.validateUnit(unit, "rapid_fire") &&
           SafeOperations.ValidationOperations.validateWeapon(unit.getWeapon()) &&
           unit.getActionPoints() >= 2;
}
```

## Пользовательский интерфейс

### **Уведомления пользователя**
```java
// Автоматические уведомления
=== USER NOTIFICATION ===
XCOM 2 Error - ERROR
Error: Target out of range
Component: Combat
Operation: attack
========================
```

### **Интеграция с GameWindow**
```java
// В GameWindow.java
private void handleAttackAction() {
    if (selectedUnit != null) {
        boolean success = SafeOperations.CombatOperations.performAttack(selectedUnit, target);
        if (success) {
            logMessage(selectedUnit.getName() + " attacks successfully");
        } else {
            logMessage("Attack failed - check error log for details");
        }
    }
}
```

## Конфигурация

### **Настройки ErrorHandler**
```java
ErrorHandler handler = ErrorHandler.getInstance();
handler.setLogToFile(true);
handler.setLogFilePath("custom_errors.log");
handler.setShowUserNotifications(true);
handler.setMinimumSeverity(ErrorSeverity.WARNING);
```

### **Уровни логирования**
- `INFO` - Информационные сообщения
- `WARNING` - Предупреждения (по умолчанию)
- `ERROR` - Ошибки функциональности
- `CRITICAL` - Критические ошибки

## Мониторинг и отладка

### **Проверка состояния системы**
```java
// Проверка критических ошибок
if (errorHandler.isInErrorState()) {
    System.err.println("System is in error state!");
}

// Получение последних ошибок
List<GameException> recentErrors = errorHandler.getRecentErrors(10);
```

### **Очистка истории**
```java
// Очистка истории ошибок
errorHandler.clearErrorHistory();
```

## Преимущества системы

### **✅ Надежность**
- Все операции обернуты в try-catch
- Автоматическое логирование
- Graceful degradation

### **✅ Отладка**
- Детальные логи с временными метками
- Структурированная информация об ошибках
- Статистика по компонентам

### **✅ Пользовательский опыт**
- Понятные сообщения об ошибках
- Неблокирующие операции
- Информативные уведомления

### **✅ Масштабируемость**
- Легкое добавление новых типов ошибок
- Модульная архитектура
- Расширяемая статистика

### **✅ Интеграция**
- Полная совместимость с XCOM 2 механиками
- Безопасные операции для всех компонентов
- Автоматическая обработка исключений

## Заключение

Созданная система обработки ошибок обеспечивает:

🎯 **Полную надежность** XCOM 2 тактической боевой системы
🎯 **Профессиональное логирование** всех ошибок и исключений  
🎯 **Отличный пользовательский опыт** с информативными уведомлениями
🎯 **Эффективную отладку** с детальной статистикой
🎯 **Масштабируемость** для будущих расширений

Система готова к использованию в продакшене и демонстрирует современные принципы обработки ошибок в игровых приложениях. 
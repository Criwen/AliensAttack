# Быстрый старт AliensAttack

## 📋 Обзор

AliensAttack - это продвинутая тактическая боевая система в стиле XCOM2, построенная на Java 21 с использованием современных технологий и принципов разработки. **Проект полностью реализован и готов к использованию**.

## 🎯 Что такое AliensAttack

### Основные возможности
- **Тактический бой** - пошаговые сражения на сетке
- **15+ боевых менеджеров** - различные стратегии и подходы
- **Система действий** - 9 типов действий с очками действий
- **Псионические способности** - 6 школ с уникальными эффектами
- **Система снаряжения** - деградация и обслуживание
- **Окружающая среда** - 6 типов опасностей и погодные эффекты
- **AI система** - продвинутое поведение пришельцев
- **3D визуализация** - JavaFX рендеринг

### Технологический стек
- **Java 21** - последние возможности языка
- **JavaFX 21.0.2** - современный UI фреймворк
- **Lombok 1.18.30** - уменьшение boilerplate кода
- **Log4j2 2.23.1** - продвинутое логирование
- **JUnit 5.10.0** - современное тестирование
- **Maven** - управление зависимостями и сборка

## 🚀 Быстрый запуск

### Требования
- **Java 21** - последняя версия JDK
- **Maven 3.8+** - для сборки проекта
- **Минимум 4GB RAM** - для комфортной работы
- **Windows 10/11** - основная платформа

### Шаг 1: Установка Java
```bash
# Проверка версии Java
java -version

# Должно показать Java 21
java version "21.0.x" 2024-xx-xx
Java(TM) SE Runtime Environment (build 21.0.x+xx-xx)
Java HotSpot(TM) 64-Bit Server VM (build 21.0.x+xx-xx, mixed mode, sharing)
```

### Шаг 2: Установка Maven
```bash
# Проверка версии Maven
mvn -version

# Должно показать Maven 3.8+
Apache Maven 3.8.x (Java version: 21.0.x, ...)
```

### Шаг 3: Клонирование проекта
```bash
# Клонирование репозитория
git clone [repository-url]
cd AliensAttack

# Проверка структуры
ls -la
```

### Шаг 4: Сборка проекта
```bash
# Очистка и компиляция
mvn clean compile

# Запуск тестов
mvn test

# Создание JAR файла
mvn package
```

### Шаг 5: Запуск игры
```bash
# Запуск через Maven
mvn exec:java -Dexec.mainClass="com.aliensattack.AliensAttackApplication"

# Или через JAR файл
java -jar target/aliens-attack-1.0.0.jar
```

## 🎮 Первые шаги в игре

### Запуск демо
1. **Запустите игру** - появится главное окно
2. **Выберите демо** - интерактивная демонстрация
3. **Изучите интерфейс** - панели действий и информации
4. **Попробуйте действия** - перемещение, атака, защита

### Основные элементы управления
- **ЛКМ** - выбор юнита или позиции
- **ПКМ** - контекстное меню
- **WASD** - навигация по полю
- **Пробел** - подтверждение действия
- **ESC** - отмена действия

### Типы действий
- **Move** - перемещение юнита
- **Attack** - атака противника
- **Overwatch** - наблюдение за областью
- **Defend** - защитная стойка
- **Special Ability** - специальные способности

## 🏗️ Структура проекта

### Основные пакеты
```
src/main/java/com/aliensattack/
├── actions/          # Система действий
├── combat/           # Боевые системы
├── core/             # Основные системы
├── field/            # Тактическое поле
├── ui/               # Пользовательский интерфейс
└── visualization/    # 3D визуализация
```

### Ключевые классы
- **AliensAttackApplication** - главный класс приложения
- **GameEngine** - основной игровой движок
- **CombatManager** - управление боем
- **ActionManager** - управление действиями
- **BrainManager** - AI управление

### Конфигурационные файлы
```
src/main/resources/
├── application.properties    # Основные настройки
├── combat.properties        # Боевые параметры
├── weapon.properties        # Характеристики оружия
├── armor.properties         # Характеристики брони
├── explosive.properties     # Взрывчатые вещества
├── mission.properties       # Параметры миссий
├── unit.properties          # Характеристики юнитов
└── log4j2.xml              # Настройки логирования
```

## 🧪 Тестирование

### Запуск всех тестов
```bash
# Все тесты
mvn test

# Конкретный тест
mvn test -Dtest=CombatManagerTest

# Тесты с отчетом покрытия
mvn test jacoco:report
```

### Структура тестов
```
src/test/java/com/aliensattack/
├── combat/           # Тесты боевых систем
├── core/             # Тесты основных систем
│   ├── config/       # Тесты конфигурации
│   ├── control/      # Тесты управления
│   └── model/        # Тесты моделей
└── resources/        # Тестовые ресурсы
```

### Пример теста
```java
@Test
void testWeaponCreation() {
    Weapon weapon = WeaponFactory.createWeapon(WeaponType.RIFLE, "Test Rifle");
    
    assertNotNull(weapon);
    assertEquals(WeaponType.RIFLE, weapon.getWeaponType());
    assertEquals("Test Rifle", weapon.getName());
}
```

## 🔧 Разработка

### Создание нового действия
```java
public class CustomAction extends BaseAction {
    public CustomAction(CombatUnit unit) {
        super(unit, ActionType.CUSTOM, 2);
    }
    
    @Override
    public boolean execute() {
        // Кастомная логика
        return true;
    }
}
```

### Создание новой боевой стратегии
```java
public class CustomCombatStrategy implements ICombatStrategy {
    @Override
    public CombatResult executeStrategy(CombatContext context) {
        // Кастомная боевая логика
        return new CombatResult(/* ... */);
    }
}
```

### Добавление нового типа оружия
```java
public enum WeaponType {
    RIFLE, PISTOL, SNIPER_RIFLE, PLASMA_WEAPON, CUSTOM_WEAPON
}

// В WeaponFactory
case CUSTOM_WEAPON -> new Weapon(name, type, 35, 70, 20, 85, 10);
```

## 📚 Документация

### Основные разделы
- **[Архитектура](../architecture/README.md)** - принципы и паттерны
- **[Реализация](../implementation/README.md)** - технические детали
- **[API](../api/README.md)** - интерфейсы и примеры
- **[Игровые механики](../game-mechanics/README.md)** - правила и системы

### Примеры кода
- **Тесты** - `src/test/java/com/aliensattack/`
- **Демо** - `src/main/java/com/aliensattack/ui/`
- **Интеграция** - `src/main/java/com/aliensattack/core/`

## 🚨 Решение проблем

### Частые ошибки

#### Java не найден
```bash
# Проверьте переменную JAVA_HOME
echo $JAVA_HOME

# Установите Java 21
# Скачайте с oracle.com или используйте OpenJDK
```

#### Maven не найден
```bash
# Проверьте переменную PATH
echo $PATH

# Установите Maven
# Скачайте с maven.apache.org
```

#### Ошибки компиляции
```bash
# Очистите проект
mvn clean

# Обновите зависимости
mvn dependency:resolve

# Проверьте версию Java
java -version
```

#### Ошибки тестирования
```bash
# Запустите тесты с подробным выводом
mvn test -X

# Проверьте тестовые ресурсы
ls src/test/resources/
```

### Логи и отладка
```bash
# Запуск с подробным логированием
mvn exec:java -Dexec.mainClass="com.aliensattack.AliensAttackApplication" -Dlog4j.configuration=log4j2.xml

# Просмотр логов
tail -f logs/aliensattack.log
```

## 🎯 Следующие шаги

### Для игроков
1. **Изучите механики** - [docs/game-mechanics/README.md](../game-mechanics/README.md)
2. **Попробуйте демо** - интерактивные примеры
3. **Создайте миссию** - настройте параметры
4. **Изучите тактики** - различные подходы к бою

### Для разработчиков
1. **Изучите архитектуру** - [docs/architecture/README.md](../architecture/README.md)
2. **Поняйте API** - [docs/api/README.md](../api/README.md)
3. **Создайте расширение** - новый тип действия или стратегии
4. **Напишите тесты** - для вашего кода

### Для моддеров
1. **Изучите API** - [docs/api/README.md](../api/README.md)
2. **Создайте модификацию** - новый контент
3. **Протестируйте** - проверьте совместимость
4. **Поделитесь** - с сообществом

## 📞 Поддержка

### Сообщество
- **GitHub Issues** - для багов и предложений
- **GitHub Discussions** - для обсуждений
- **Wiki** - дополнительная информация

### Ресурсы
- **Документация** - полное описание всех систем
- **Примеры** - готовые образцы кода
- **Тесты** - проверка функциональности

---

**AliensAttack** предоставляет мощную платформу для тактических игр с открытой архитектурой, позволяющей легко создавать расширения и модификации. Начните с изучения основ и постепенно углубляйтесь в сложные системы!

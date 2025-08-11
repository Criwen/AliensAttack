# Установка AliensAttack

## 📋 Содержание

- [Обзор установки](#обзор-установки)
- [Системные требования](#системные-требования)
- [Установка зависимостей](#установка-зависимостей)
- [Установка AliensAttack](#установка-aliensattack)
- [Настройка окружения](#настройка-окружения)
- [Проверка установки](#проверка-установки)
- [Устранение проблем](#устранение-проблем)

## 🔧 Обзор установки

AliensAttack - это Java-приложение, которое требует установки Java Development Kit (JDK) и Apache Maven для сборки и запуска. Данное руководство поможет вам установить все необходимые компоненты на различных операционных системах.

### 🎯 Варианты установки

- **Полная установка** - для разработки и модификации
- **Минимальная установка** - только для запуска игры
- **Docker-установка** - для изолированного окружения

## 💻 Системные требования

### Минимальные требования

#### Аппаратное обеспечение
- **Процессор**: Intel Core i3-6100 / AMD FX-6300
- **Оперативная память**: 4 GB RAM
- **Видеокарта**: Intel HD 530 / AMD Radeon R7 240
- **Свободное место**: 2 GB на диске
- **Сеть**: Интернет-соединение для загрузки

#### Программное обеспечение
- **Операционная система**: Windows 10 (64-bit), macOS 10.14+, Ubuntu 18.04+
- **Java**: OpenJDK 21 или Oracle JDK 21
- **Maven**: Apache Maven 3.6+
- **Git**: Git 2.30+ (для разработки)

### Рекомендуемые требования

#### Аппаратное обеспечение
- **Процессор**: Intel Core i5-8400 / AMD Ryzen 5 2600
- **Оперативная память**: 8 GB RAM
- **Видеокарта**: NVIDIA GTX 1060 / AMD RX 580
- **Свободное место**: 5 GB на SSD
- **Сеть**: Высокоскоростное интернет-соединение

#### Программное обеспечение
- **Операционная система**: Windows 11, macOS 12+, Ubuntu 20.04+
- **Java**: OpenJDK 21 LTS
- **Maven**: Apache Maven 3.8+
- **IDE**: IntelliJ IDEA 2023.1+ или Eclipse 2023-03+

## 📦 Установка зависимостей

### Установка Java 21

#### Windows
```bash
# Скачайте OpenJDK 21 с официального сайта
# https://adoptium.net/temurin/releases/

# Или используйте Chocolatey
choco install temurin21

# Или используйте Scoop
scoop install temurin21-jdk
```

#### macOS
```bash
# Используя Homebrew
brew install --cask temurin

# Или скачайте DMG-файл с официального сайта
# https://adoptium.net/temurin/releases/
```

#### Linux (Ubuntu/Debian)
```bash
# Добавление репозитория
wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo apt-key add -
echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | sudo tee /etc/apt/sources.list.d/adoptium.list

# Установка Java
sudo apt update
sudo apt install temurin-21-jdk
```

#### Linux (CentOS/RHEL/Fedora)
```bash
# CentOS/RHEL 8+
sudo dnf install java-21-openjdk-devel

# Fedora
sudo dnf install java-21-openjdk-devel

# Или используя SDKMAN
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 21.0.1-tem
```

### Установка Maven

#### Windows
```bash
# Скачайте Maven с официального сайта
# https://maven.apache.org/download.cgi

# Или используйте Chocolatey
choco install maven

# Или используйте Scoop
scoop install maven
```

#### macOS
```bash
# Используя Homebrew
brew install maven

# Или используя SDKMAN
sdk install maven
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt install maven

# CentOS/RHEL/Fedora
sudo dnf install maven

# Или используя SDKMAN
sdk install maven
```

### Установка Git

#### Windows
```bash
# Скачайте Git с официального сайта
# https://git-scm.com/download/win

# Или используйте Chocolatey
choco install git

# Или используйте Scoop
scoop install git
```

#### macOS
```bash
# Git предустановлен в macOS
# Или используйте Homebrew для обновления
brew install git
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt install git

# CentOS/RHEL/Fedora
sudo dnf install git
```

## 🚀 Установка AliensAttack

### Способ 1: Клонирование репозитория

#### Клонирование
```bash
# Клонирование основного репозитория
git clone https://github.com/your-username/AliensAttack.git
cd AliensAttack

# Или клонирование с определенной веткой
git clone -b main https://github.com/your-username/AliensAttack.git
cd AliensAttack
```

#### Проверка клонирования
```bash
# Проверка содержимого
ls -la

# Должны быть файлы:
# - pom.xml (Maven проект)
# - src/ (исходный код)
# - docs/ (документация)
# - README.md (описание проекта)
```

### Способ 2: Скачивание архива

#### Скачивание
1. **Перейдите на GitHub** репозитория AliensAttack
2. **Нажмите "Code"** → "Download ZIP"
3. **Распакуйте архив** в удобную папку
4. **Откройте терминал** в папке с проектом

#### Проверка распаковки
```bash
# Проверка содержимого
ls -la

# Должны быть те же файлы, что и при клонировании
```

### Способ 3: Docker-установка

#### Создание Dockerfile
```dockerfile
# Dockerfile
FROM openjdk:21-jdk-slim

WORKDIR /app

# Установка Maven
RUN apt-get update && apt-get install -y maven

# Копирование проекта
COPY . .

# Сборка проекта
RUN mvn clean package -DskipTests

# Экспорт порта
EXPOSE 8080

# Запуск приложения
CMD ["java", "-jar", "target/aliensattack-1.0.0.jar"]
```

#### Сборка и запуск
```bash
# Сборка образа
docker build -t aliensattack .

# Запуск контейнера
docker run -p 8080:8080 -v $(pwd)/config:/app/config aliensattack
```

## ⚙️ Настройка окружения

### Настройка переменных окружения

#### Windows
```cmd
# Установка JAVA_HOME
setx JAVA_HOME "C:\Program Files\Java\jdk-21"

# Установка MAVEN_HOME
setx MAVEN_HOME "C:\Program Files\Apache\maven"

# Добавление в PATH
setx PATH "%PATH%;%JAVA_HOME%\bin;%MAVEN_HOME%\bin"
```

#### macOS/Linux
```bash
# Добавление в ~/.bashrc или ~/.zshrc
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export MAVEN_HOME=/usr/share/maven
export PATH=$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin

# Применение изменений
source ~/.bashrc
# или
source ~/.zshrc
```

### Проверка переменных окружения
```bash
# Проверка JAVA_HOME
echo $JAVA_HOME
# Windows: echo %JAVA_HOME%

# Проверка MAVEN_HOME
echo $MAVEN_HOME
# Windows: echo %MAVEN_HOME%

# Проверка PATH
echo $PATH
# Windows: echo %PATH%
```

### Настройка IDE

#### IntelliJ IDEA
1. **Откройте IDEA**
2. **File** → **Open**
3. **Выберите папку** AliensAttack
4. **Import Maven project**
5. **Дождитесь загрузки** зависимостей

#### Eclipse
1. **Откройте Eclipse**
2. **File** → **Import**
3. **Maven** → **Existing Maven Projects**
4. **Выберите папку** AliensAttack
5. **Finish**

#### VS Code
1. **Откройте VS Code**
2. **File** → **Open Folder**
3. **Выберите папку** AliensAttack
4. **Установите расширения**:
   - Extension Pack for Java
   - Maven for Java
   - Java Test Runner

## ✅ Проверка установки

### Проверка Java
```bash
# Проверка версии Java
java -version

# Ожидаемый вывод:
# openjdk version "21.0.1" 2023-10-17
# OpenJDK Runtime Environment Temurin-21.0.1+12 (build 21.0.1+12)
# OpenJDK 64-Bit Server VM Temurin-21.0.1+12 (build 21.0.1+12, mixed mode, sharing)

# Проверка компилятора
javac -version

# Ожидаемый вывод:
# javac 21.0.1
```

### Проверка Maven
```bash
# Проверка версии Maven
mvn -version

# Ожидаемый вывод:
# Apache Maven 3.9.5 (57804ffe001d7215b5e7bcb531aa583f60caa2c2; 2023-07-05T20:31:25+02:00)
# Maven home: /usr/share/maven
# Java version: 21.0.1, vendor: Eclipse Adoptium, runtime: /usr/lib/jvm/java-21-openjdk
# Default locale: en_US, platform encoding: UTF-8
# OS name: "linux", version: "5.15.0-91-generic", arch: "amd64", family: "unix"
```

### Проверка Git
```bash
# Проверка версии Git
git --version

# Ожидаемый вывод:
# git version 2.39.2
```

### Проверка AliensAttack
```bash
# Переход в папку проекта
cd AliensAttack

# Проверка структуры проекта
ls -la

# Компиляция проекта
mvn clean compile

# Ожидаемый вывод:
# [INFO] BUILD SUCCESS
# [INFO] Total time: XX.XXX s
# [INFO] Finished at: 2024-01-15TXX:XX:XX+XX:XX
```

## 🚨 Устранение проблем

### Проблемы с Java

#### Java не найден
```bash
# Проверка установки
which java
# Windows: where java

# Если не найден, переустановите Java
# Убедитесь, что JAVA_HOME установлен корректно
```

#### Неправильная версия Java
```bash
# Проверка всех установленных версий Java
# Windows
dir "C:\Program Files\Java"

# macOS/Linux
ls /usr/lib/jvm/
ls /Library/Java/JavaVirtualMachines/

# Установка правильной версии
# Следуйте инструкциям по установке Java 21
```

### Проблемы с Maven

#### Maven не найден
```bash
# Проверка установки
which mvn
# Windows: where mvn

# Если не найден, переустановите Maven
# Убедитесь, что MAVEN_HOME установлен корректно
```

#### Ошибки зависимостей
```bash
# Очистка локального репозитория Maven
mvn dependency:purge-local-repository

# Обновление зависимостей
mvn dependency:resolve -U

# Принудительное обновление
mvn clean install -U
```

### Проблемы с проектом

#### Ошибки компиляции
```bash
# Очистка проекта
mvn clean

# Проверка зависимостей
mvn dependency:tree

# Компиляция с подробным выводом
mvn compile -X
```

#### Проблемы с JavaFX
```bash
# Проверка модулей JavaFX
java --list-modules | grep javafx

# Если модули отсутствуют, установите OpenJFX
# Ubuntu/Debian
sudo apt install openjfx

# macOS
brew install openjfx

# Windows - JavaFX включен в JDK
```

### Проблемы с правами доступа

#### Linux/macOS
```bash
# Установка прав на выполнение
chmod +x mvnw

# Проверка прав
ls -la mvnw

# Запуск с sudo (если необходимо)
sudo mvn clean compile
```

#### Windows
```bash
# Запуск командной строки от имени администратора
# Или проверка прав доступа к папке проекта
```

## 🔧 Дополнительная настройка

### Настройка производительности

#### Оптимизация JVM
```bash
# Запуск с оптимизированными параметрами
java -Xmx2g -Xms1g -XX:+UseG1GC -jar target/aliensattack-1.0.0.jar

# Параметры:
# -Xmx2g: максимальная память 2GB
# -Xms1g: начальная память 1GB
# -XX:+UseG1GC: использование G1 сборщика мусора
```

#### Настройка Maven
```xml
<!-- ~/.m2/settings.xml -->
<settings>
  <profiles>
    <profile>
      <id>performance</id>
      <properties>
        <maven.compiler.fork>true</maven.compiler.fork>
        <maven.compiler.meminitial>512m</maven.compiler.meminitial>
        <maven.compiler.maxmem>1024m</maven.compiler.maxmem>
      </properties>
    </profile>
  </profiles>
</settings>
```

### Настройка сети

#### Прокси-сервер
```bash
# Настройка Maven для прокси
# ~/.m2/settings.xml
<settings>
  <proxies>
    <proxy>
      <id>proxy</id>
      <active>true</active>
      <protocol>http</protocol>
      <host>proxy.company.com</host>
      <port>8080</port>
    </proxy>
  </proxies>
</settings>
```

#### Firewall
```bash
# Разрешение доступа для Java
# Windows: Добавьте java.exe в исключения брандмауэра
# Linux: sudo ufw allow out 8080
# macOS: Системные настройки → Безопасность → Брандмауэр
```

## 📚 Следующие шаги

### После успешной установки

1. **Запустите игру** - `mvn exec:java`
2. **Изучите документацию** - `docs/` папка
3. **Запустите тесты** - `mvn test`
4. **Изучите код** - `src/main/java/` папка

### Разработка

1. **Откройте проект в IDE**
2. **Изучите структуру** кода
3. **Запустите отладку** для понимания работы
4. **Создайте свою модификацию**

### Сообщество

1. **Присоединитесь к обсуждениям** на GitHub
2. **Сообщите об ошибках** через Issues
3. **Предложите улучшения** через Discussions
4. **Поделитесь опытом** с другими пользователями

## 🎉 Готово!

Теперь у вас установлен AliensAttack со всеми необходимыми зависимостями! Вы можете:

- **Запустить игру** и начать играть
- **Изучить код** для понимания архитектуры
- **Создавать модификации** для расширения функциональности
- **Участвовать в разработке** проекта

Если у вас возникли проблемы, обратитесь к разделу "Устранение проблем" или создайте Issue на GitHub.

**Удачной игры и разработки!** 🎮🚀

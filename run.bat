@echo off
echo ========================================
echo    AliensAttack Project Launcher
echo ========================================
echo.

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java not found. Please ensure Java 21 is installed and in PATH.
    echo Expected Java path: C:\Program Files\Java\jdk-21
    pause
    exit /b 1
)

REM Check if Maven is available
mvn -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Maven not found. Please ensure Maven is installed and in PATH.
    pause
    exit /b 1
)

echo Compiling project...
call mvn clean compile -q
if errorlevel 1 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo.
echo Starting game...
echo.

REM Run the application in a new window to avoid "Terminate batch job" prompt
start "AliensAttack Game" cmd /c "mvn exec:java -Dexec.mainClass=com.aliensattack.AliensAttackApplication -q"

echo Game launched in new window.
echo You can close this window.
pause

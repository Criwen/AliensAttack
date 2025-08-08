@echo off
echo Compiling AliensAttack project...
"C:\TEMP\jdk-21.0.2\bin\javac.exe" -cp "src/main/java" -d target/classes src/main/java/com/aliensattack/ui/Interactive3DDemo.java
if %errorlevel% equ 0 (
    echo Compilation successful!
) else (
    echo Compilation failed with error code %errorlevel%
)
pause 
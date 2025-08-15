@echo off
echo Stopping AliensAttack Project...
echo.

REM Find and terminate Java processes running AliensAttack
tasklist /FI "IMAGENAME eq java.exe" /FO CSV | findstr /I "aliensattack" >nul
if not errorlevel 1 (
    echo Found running AliensAttack process, terminating...
    taskkill /F /IM java.exe /FI "WINDOWTITLE eq AliensAttack*" >nul 2>&1
    taskkill /F /IM java.exe /FI "WINDOWTITLE eq *AliensAttack*" >nul 2>&1
    echo Process stopped.
) else (
    echo No running AliensAttack process found.
)

REM Also stop any Maven processes
tasklist /FI "IMAGENAME eq mvn.cmd" /FO CSV | findstr /I "mvn" >nul
if not errorlevel 1 (
    echo Stopping Maven processes...
    taskkill /F /IM mvn.cmd >nul 2>&1
)

echo Done.
pause


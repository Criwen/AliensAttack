Write-Host "Compiling AliensAttack project..." -ForegroundColor Green

$javacPath = "C:\TEMP\jdk-21.0.2\bin\javac.exe"
$sourcePath = "src/main/java"
$outputPath = "target/classes"
$mainClass = "src/main/java/com/aliensattack/ui/Interactive3DDemo.java"

if (Test-Path $javacPath) {
    Write-Host "Found Java compiler at: $javacPath" -ForegroundColor Yellow
    
    # Create output directory if it doesn't exist
    if (!(Test-Path $outputPath)) {
        New-Item -ItemType Directory -Path $outputPath -Force | Out-Null
        Write-Host "Created output directory: $outputPath" -ForegroundColor Yellow
    }
    
    # Compile the project
    $arguments = @("-cp", $sourcePath, "-d", $outputPath, $mainClass)
    Write-Host "Running: $javacPath $($arguments -join ' ')" -ForegroundColor Cyan
    
    & $javacPath $arguments
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Compilation successful!" -ForegroundColor Green
    } else {
        Write-Host "Compilation failed with error code: $LASTEXITCODE" -ForegroundColor Red
    }
} else {
    Write-Host "Java compiler not found at: $javacPath" -ForegroundColor Red
    Write-Host "Please check the JDK installation path." -ForegroundColor Red
}

Write-Host "Press any key to continue..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown") 
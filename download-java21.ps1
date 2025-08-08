Write-Host "Downloading Java 21..." -ForegroundColor Green

$java21Url = "https://download.java.net/java/GA/jdk21.0.2/13d5b2a4be90462f896e6f96bcf36db2/13/GPL/openjdk-21.0.2_windows-x64_bin.zip"
$downloadPath = "C:\TEMP\jdk-21.0.2.zip"
$extractPath = "C:\TEMP"

# Create directory if it doesn't exist
if (!(Test-Path "C:\TEMP")) {
    New-Item -ItemType Directory -Path "C:\TEMP" -Force | Out-Null
}

Write-Host "Downloading from: $java21Url" -ForegroundColor Yellow
Write-Host "This may take a few minutes..." -ForegroundColor Yellow

try {
    # Download Java 21
    Invoke-WebRequest -Uri $java21Url -OutFile $downloadPath -UseBasicParsing
    
    Write-Host "Download completed!" -ForegroundColor Green
    Write-Host "Extracting to: $extractPath" -ForegroundColor Yellow
    
    # Extract the zip file
    Expand-Archive -Path $downloadPath -DestinationPath $extractPath -Force
    
    # Rename the extracted folder to jdk-21.0.2
    $extractedFolder = Get-ChildItem -Path $extractPath -Directory | Where-Object { $_.Name -like "*jdk*" } | Select-Object -First 1
    if ($extractedFolder) {
        Rename-Item -Path $extractedFolder.FullName -NewName "jdk-21.0.2" -Force
        Write-Host "Java 21 installed successfully at: C:\TEMP\jdk-21.0.2" -ForegroundColor Green
    }
    
    # Clean up the zip file
    Remove-Item -Path $downloadPath -Force
    
} catch {
    Write-Host "Error downloading Java 21: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Please download Java 21 manually from: https://jdk.java.net/21/" -ForegroundColor Yellow
}

Write-Host "Press any key to continue..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown") 
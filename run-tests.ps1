Write-Host "Running AliensAttack Tests..." -ForegroundColor Green
Write-Host ""

Write-Host "Compiling and running tests..." -ForegroundColor Yellow
mvn clean test

Write-Host ""
Write-Host "Tests completed!" -ForegroundColor Green
Read-Host "Press Enter to continue" 
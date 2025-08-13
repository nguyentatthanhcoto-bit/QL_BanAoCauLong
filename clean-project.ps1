# Clean Project Script
# Removes all compiled .class files and build artifacts

Write-Host "🧹 Cleaning project..." -ForegroundColor Yellow

# Count files before cleaning
$classFiles = Get-ChildItem -Path . -Recurse -Filter "*.class" -ErrorAction SilentlyContinue
$classCount = $classFiles.Count

Write-Host "Found $classCount .class files to remove" -ForegroundColor Cyan

# Remove all .class files
if ($classCount -gt 0) {
    $classFiles | Remove-Item -Force
    Write-Host "✅ Removed $classCount .class files" -ForegroundColor Green
} else {
    Write-Host "✅ No .class files found" -ForegroundColor Green
}

# Remove target directory if exists
if (Test-Path "target") {
    Remove-Item -Path "target" -Recurse -Force
    Write-Host "✅ Removed target directory" -ForegroundColor Green
} else {
    Write-Host "✅ No target directory found" -ForegroundColor Green
}

# Remove build directory if exists
if (Test-Path "build") {
    Remove-Item -Path "build" -Recurse -Force
    Write-Host "✅ Removed build directory" -ForegroundColor Green
} else {
    Write-Host "✅ No build directory found" -ForegroundColor Green
}

# Remove out directory if exists
if (Test-Path "out") {
    Remove-Item -Path "out" -Recurse -Force
    Write-Host "✅ Removed out directory" -ForegroundColor Green
} else {
    Write-Host "✅ No out directory found" -ForegroundColor Green
}

# Remove .DS_Store files (macOS)
$dsStoreFiles = Get-ChildItem -Path . -Recurse -Name ".DS_Store" -ErrorAction SilentlyContinue
if ($dsStoreFiles.Count -gt 0) {
    Get-ChildItem -Path . -Recurse -Name ".DS_Store" | Remove-Item -Force
    Write-Host "✅ Removed $($dsStoreFiles.Count) .DS_Store files" -ForegroundColor Green
}

# Remove Thumbs.db files (Windows)
$thumbsFiles = Get-ChildItem -Path . -Recurse -Name "Thumbs.db" -ErrorAction SilentlyContinue
if ($thumbsFiles.Count -gt 0) {
    Get-ChildItem -Path . -Recurse -Name "Thumbs.db" | Remove-Item -Force
    Write-Host "✅ Removed $($thumbsFiles.Count) Thumbs.db files" -ForegroundColor Green
}

# Remove temporary files
$tempFiles = Get-ChildItem -Path . -Recurse -Filter "*.tmp" -ErrorAction SilentlyContinue
if ($tempFiles.Count -gt 0) {
    $tempFiles | Remove-Item -Force
    Write-Host "✅ Removed $($tempFiles.Count) temporary files" -ForegroundColor Green
}

Write-Host "🎉 Project cleaned successfully!" -ForegroundColor Green
Write-Host ""
Write-Host "📋 Summary:" -ForegroundColor Yellow
Write-Host "  - Removed all .class files" -ForegroundColor White
Write-Host "  - Removed build directories (target, build, out)" -ForegroundColor White
Write-Host "  - Removed system files (.DS_Store, Thumbs.db)" -ForegroundColor White
Write-Host "  - Removed temporary files" -ForegroundColor White
Write-Host ""
Write-Host "💡 Usage: .\clean-project.ps1" -ForegroundColor Cyan

@echo off
echo 🧹 Cleaning project...

REM Remove all .class files
echo Removing .class files...
for /r %%i in (*.class) do (
    del "%%i" 2>nul
)

REM Remove target directory
if exist "target" (
    echo Removing target directory...
    rmdir /s /q "target"
    echo ✅ Removed target directory
) else (
    echo ✅ No target directory found
)

REM Remove build directory
if exist "build" (
    echo Removing build directory...
    rmdir /s /q "build"
    echo ✅ Removed build directory
) else (
    echo ✅ No build directory found
)

REM Remove out directory
if exist "out" (
    echo Removing out directory...
    rmdir /s /q "out"
    echo ✅ Removed out directory
) else (
    echo ✅ No out directory found
)

REM Remove Thumbs.db files
echo Removing Thumbs.db files...
for /r %%i in (Thumbs.db) do (
    del "%%i" 2>nul
)

REM Remove temporary files
echo Removing temporary files...
for /r %%i in (*.tmp) do (
    del "%%i" 2>nul
)

echo.
echo 🎉 Project cleaned successfully!
echo.
echo 📋 Summary:
echo   - Removed all .class files
echo   - Removed build directories (target, build, out)
echo   - Removed system files (Thumbs.db)
echo   - Removed temporary files
echo.
echo 💡 Usage: clean-project.bat
pause

@echo off
echo ========================================
echo        CLEANING PROJECT FILES
echo ========================================

echo.
echo [1/3] Removing .class files from source directories...
for /r src %%f in (*.class) do (
    echo Removing: %%f
    del "%%f"
)

echo.
echo [2/3] Cleaning target directory...
if exist "target\classes" (
    echo Cleaning target\classes...
    rmdir /S /Q "target\classes" 2>nul
    mkdir "target\classes"
)

echo.
echo [3/3] Removing temporary files...
for /r . %%f in (*.tmp, *.log, *~) do (
    if exist "%%f" (
        echo Removing: %%f
        del "%%f" 2>nul
    )
)

echo.
echo ========================================
echo         CLEANING COMPLETED!
echo ========================================
echo.
echo All .class files have been removed from source directories.
echo Use build.bat to compile the project properly.
echo.
pause

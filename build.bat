@echo off
echo ========================================
echo           SHOP PROJECT BUILD
echo ========================================

REM Create target directories if they don't exist
if not exist "target\classes" mkdir "target\classes"

echo.
echo [1/4] Cleaning previous build...
if exist "target\classes\*" del /Q /S "target\classes\*" >nul 2>&1

echo [2/4] Compiling model and utility classes...
javac -d target/classes -cp "target/classes;src/main/java" ^
    src/main/java/com/mycompany/shop/model/*.java ^
    src/main/java/com/mycompany/shop/dao/*.java ^
    src/main/java/com/mycompany/shop/util/*.java

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to compile model/dao/util classes
    pause
    exit /b 1
)

echo [3/4] Compiling screen classes...
javac -d target/classes -cp "target/classes;src/main/java" ^
    src/main/java/screen/*.java

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Failed to compile screen classes
    pause
    exit /b 1
)

echo [4/4] Compiling test classes...
javac -d target/classes -cp "target/classes;src/main/java" ^
    src/main/java/com/mycompany/shop/test/*.java

if %ERRORLEVEL% NEQ 0 (
    echo WARNING: Failed to compile test classes (this is optional)
)

echo.
echo ========================================
echo           BUILD SUCCESSFUL!
echo ========================================
echo.
echo Compiled classes are in: target\classes\
echo.
echo To run the application:
echo   java -cp "target/classes" screen.Login
echo.
pause

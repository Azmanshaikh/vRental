@echo off
REM Compile and run Vehicle Rental Portal (Windows)
REM Place sqlite-jdbc jar in lib\ folder before running

set JAR=lib\sqlite-jdbc-3.53.1.0.jar

if not exist "%JAR%" (
    echo ERROR: SQLite JDBC driver not found at %JAR%
    echo Download from https://github.com/xerial/sqlite-jdbc/releases
    echo and place in lib\ folder. See lib\README.txt
    pause
    exit /b 1
)

if not exist out mkdir out

echo Compiling...
javac -encoding UTF-8 -cp "%JAR%;src" -d out src\*.java
if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

echo Starting Vehicle Rental Portal...
java -cp "out;%JAR%" Main

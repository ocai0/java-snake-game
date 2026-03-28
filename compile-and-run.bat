@echo off
CLS
if not exist bin mkdir bin
del /q bin\*.class
echo Compilando o jogo...
javac -d bin src/*.java
if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Deu ruim o trem aqui, viu? Oia esse codigo ai.
    exit /b %errorlevel%
)
CLS
echo Iniciando Jogo...
cls
java -cp bin Main
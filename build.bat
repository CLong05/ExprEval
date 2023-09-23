@echo off
cd src
javac -d ..\bin -classpath ..\bin decimal\*.java
javac -d ..\bin -classpath ..\bin token\*.java
javac -d ..\bin -classpath ..\bin expr\*.java
javac -d ..\bin -classpath ..\bin parser\*.java
cd ..
pause
@echo on

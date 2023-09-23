@echo off
cd src
javadoc -private -author -version -d ..\doc -classpath ..\bin decimal\*.java
javadoc -private -author -version -d ..\doc -classpath ..\bin token\*.java
javadoc -private -author -version -d ..\doc -classpath ..\bin expr\*.java
javadoc -private -author -version -d ..\doc -classpath ..\bin parser\*.java
cd ..
pause
@echo on

@echo off
cd bin
java test.ExprEvalTest ..\testcases\custom.xml > ..\testcases\report.txt
cd ..
type testcases\report.txt
pause
del testcases\report.txt
@echo on
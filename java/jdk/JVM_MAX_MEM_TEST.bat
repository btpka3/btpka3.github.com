@echo off
rem REF: http://www.dostips.com/DtTutoFunctions.php
rem REF: http://www.caucho.com/resin-3.0/performance/jvm-tuning.xtp
setLocal
set searchResult=0
call:SEARCH 1 10240

:END
echo.
echo #################################################
echo # the max memory avaiable is : %searchResult% M
echo #################################################
pause
endLocal
goto:eof

:SEARCH
rem        %~1 min value
rem        %~2 max value
set /A minValue=%~1
set /A maxValue=%~2
set /A midValue=(%minValue%+%maxValue%)/2

if "%minValue%" EQU "%midValue%" (
  set searchResult=%minValue%
  goto:eof
)
echo ------------------------------------------testing %midValue%M
java -Xmx%midValue%M -version > NUL
if "0" EQU "%ERRORLEVEL%" (
  set minValue=%midValue%
) else (
  set maxValue=%midValue%
)
call:SEARCH %minValue% %maxValue%

goto:eof

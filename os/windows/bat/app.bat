@ECHO OFF
setLocal enableDelayedExpansion

SET WORK_DIR=%~dp0
CD %WORK_DIR%

SET XXX_DIR=
SET RUNTIME_WORK_DIR=
SET MAIN_CLASS=me.test.SampleMain
SET JAVA_OPTS=-XX:PermSize=64M -XX:MaxPermSize=128M -Xms128M -Xmx256M -Xss512K

FOR %%I IN (".\lib\*.jar") do (
  SET CLASSPATH=!CLASSPATH!;%%~fI
)
SET CLASSPATH=%XXX_DIR%;%CLASSPATH%
SET CLASSPATH=.;.\conf;.\another.jar;%CLASSPATH%

java %JAVA_OPTS% %MAIN_CLASS%

PAUSE
endLocal
@ECHO ON

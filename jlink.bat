@echo off

if "%JAVA_HOME%" == "" goto set_java_home
goto java_home_ok
:set_java_home
set JAVA_HOME=c:\jdk9
:java_home_ok

echo --- DELETING IMAGES ---
del /s /q jdk-minimal
rmdir /s /q jdk-minimal
del /s /q jdk-monitor
rmdir /s /q jdk-monitor

echo -
echo --- DEPENDENCIES ON PLATFORM MODULES ---
jdeps -summary -recursive --module-path mods --module monitor --add-modules monitor.observer.alpha,monitor.observer.beta | grep java.

echo -
echo --- CREATE JAVA.BASE IMAGE ---
jlink.exe --output jdk-minimal --module-path %JAVA_HOME%/jmods --add-modules java.base

echo -
echo --- LIST JAVA.BASE IMAGE MODULES ---
jdk-minimal\bin\java --list-modules

echo -
echo --- CREATE MONITOR IMAGE ---
jlink.exe --output jdk-monitor --module-path %JAVA_HOME%/jmods;mods --add-modules monitor,monitor.observer.alpha,monitor.observer.beta --launcher monitor=monitor

echo -
echo --- LIST MONITOR IMAGE MODULES ---
jdk-monitor\bin\java --list-modules

rem echo -
rem echo --- LAUNCH MONITOR IMAGE MODULES ---
rem jdk-monitor\bin\monitor

echo -
echo --- LAUNCH MONITOR IMAGE MODULES WITH EXTERNAL OBSERVER ---
rem without monitor.observer.zero, you can not observe service 0-patient
jdk-monitor\bin\java --module-path mods/monitor.observer.zero.jar --module monitor

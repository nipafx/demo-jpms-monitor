@echo off
echo ""
echo "--- LAUNCH ---"

if "%1" == "mvn" goto maven
echo " > run monitor"
echo ""
rem the classpath is needed for Spark's dependencies
java --module-path mods --class-path "libs/*" --module monitor
goto end

:maven
echo " > run monitor from Maven build"
echo ""
rem This version runs the application when built with Maven.
rem the classpath is needed for Spark's dependencies
java --module-path mods-mvn --class-path "libs/*" --module monitor/monitor.Main

:end

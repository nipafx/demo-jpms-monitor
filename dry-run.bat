@echo off
echo ""
echo "--- LAUNCH ---"

if "%1" == "mvn" goto maven
echo " > dry-run monitor"
echo ""
rem the classpath is needed for Spark's dependencies
java --module-path mods --class-path "libs/*" --dry-run --module monitor
goto end

:maven
echo " > dry-run monitor from Maven build"
echo ""
rem This version runs the application when built with Maven.
rem the classpath is needed for Spark's dependencies
java --module-path mods-mvn --class-path "libs/*" --dry-run --module monitor/monitor.Main

:end

@echo off
echo "--- COMPILATION & PACKAGING ---"

echo " > creating clean directories"
del /s /q classes
rmdir /s /q classes
mkdir classes
del /s /q mods
rmdir /s /q mods
mkdir mods

echo " > multi-compiling modules"
rem spark is required as an automatic module, so copy it to mods
copy libs\spark-core-*.jar mods\
javac --module-path mods --module-source-path "./*/src/main/java" -d classes --module monitor

echo " > packaging modules"
jar --create --file mods/monitor.observer.jar -C classes/monitor.observer .
jar --create --file mods/monitor.observer.alpha.jar -C classes/monitor.observer.alpha .
jar --create --file mods/monitor.observer.beta.jar -C classes/monitor.observer.beta .
jar --create --file mods/monitor.statistics.jar -C classes/monitor.statistics .
jar --create --file mods/monitor.persistence.jar -C classes/monitor.persistence .
jar --create --file mods/monitor.rest.jar -C classes/monitor.rest .
jar --create --file mods/monitor.jar --main-class monitor.Main -C classes/monitor .

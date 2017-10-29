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
rem since the monitor services are not required (directly or indirectly)
rem from the root module monitor, they are added manually
javac --module-path mods --module-source-path "./*/src/main/java" --add-modules=monitor.observer.alpha,monitor.observer.beta -d classes --module monitor

echo " > packaging modules"
jar --create --file mods/monitor.observer.jar -C classes/monitor.observer .
jar --create --file mods/monitor.observer.alpha.jar -C classes/monitor.observer.alpha .
jar --create --file mods/monitor.observer.beta.jar -C classes/monitor.observer.beta .
jar --create --file mods/monitor.statistics.jar -C classes/monitor.statistics .
jar --create --file mods/monitor.persistence.jar -C classes/monitor.persistence .
jar --create --file mods/monitor.rest.jar -C classes/monitor.rest .
jar --create --file mods/monitor.jar --main-class monitor.Main -C classes/monitor .

rem monitor.observer.zero is not a module, so it can't be added
rem to thmulti-module compiler - do it separately instead
echo " > building monitor.observer.zero (plain JAR)"
set JARS=
for %%f in (mods\*.jar) do call set JARS=%%JARS%%;"%%f"
dir /S /B monitor.observer.zero\src\*.java > sources.txt
javac --class-path %JARS% -d classes/monitor.observer.zero @sources.txt
xcopy monitor.observer.zero\src\main\resources classes\monitor.observer.zero /e
del sources.txt
jar --create --file mods/monitor.observer.zero.jar -C classes/monitor.observer.zero .

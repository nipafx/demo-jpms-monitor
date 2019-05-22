#!/bin/bash
set -e

#Java executable for standard Linux environment
export JAVAC=javac
export JAR=jar
#Java executable for MinGW environment
#export JAVAC=/c/jdk9/bin/javac.exe
#export JAR=/c/jdk9/bin/jar.exe

echo "--- COMPILATION & PACKAGING ---"

echo " > creating clean directories"
rm -rf classes
mkdir classes
rm -rf mods
mkdir mods

echo " > multi-compiling modules"
# spark is required as an automatic module, so copy it to mods
cp libs/spark-core-* mods/spark.core.jar
# since the monitor services are not required (directly or indirectly)
# from the root module monitor, they are added manually
$JAVAC \
	--module-path mods \
	--module-source-path "./*/src/main/java" \
	--add-modules=monitor.observer.alpha,monitor.observer.beta \
	-d classes \
	--module monitor

echo " > packaging modules"
$JAR --create \
	--file mods/monitor.observer.jar \
	-C classes/monitor.observer .
$JAR --create \
	--file mods/monitor.observer.alpha.jar \
	-C classes/monitor.observer.alpha .
$JAR --create \
	--file mods/monitor.observer.beta.jar \
	-C classes/monitor.observer.beta .
$JAR --create \
	--file mods/monitor.statistics.jar \
	-C classes/monitor.statistics .
$JAR --create \
	--file mods/monitor.persistence.jar \
	-C classes/monitor.persistence .
$JAR --create \
	--file mods/monitor.rest.jar \
	-C classes/monitor.rest .
$JAR --create \
	--file mods/monitor.jar \
	--main-class monitor.Main \
	-C classes/monitor .

# monitor.observer.zero is not a module, so it can't be included
# in the multi-module compilation - do it separately instead
echo " > building monitor.observer.zero (plain JAR)"
$JAVAC \
	--class-path 'mods/*' \
	-d classes/monitor.observer.zero \
	$(find monitor.observer.zero -name '*.java')
cp -r monitor.observer.zero/src/main/resources/META-INF classes/monitor.observer.zero
$JAR --create \
	--file mods/monitor.observer.zero.jar \
	-C classes/monitor.observer.zero .

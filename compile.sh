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

echo " > creating monitor.observer"
$JAVAC \
	-d classes/monitor.observer \
	$(find monitor.observer -name '*.java')
$JAR --create \
	--file mods/monitor.observer.jar \
	-C classes/monitor.observer .

echo " > creating monitor.observer.alpha"
$JAVAC \
	--module-path mods \
	-d classes/monitor.observer.alpha \
	$(find monitor.observer.alpha -name '*.java')
$JAR --create \
	--file mods/monitor.observer.alpha.jar \
	-C classes/monitor.observer.alpha .


echo " > creating monitor.observer.beta"
$JAVAC \
	--module-path mods \
	-d classes/monitor.observer.beta \
	$(find monitor.observer.beta -name '*.java')
$JAR --create \
	--file mods/monitor.observer.beta-1.0.jar \
	--module-version 1.0 \
	-C classes/monitor.observer.beta .
$JAR --create \
	--file mods/monitor.observer.beta-2.0.jar \
	--module-version 2.0 \
	-C classes/monitor.observer.beta .


echo " > creating monitor.statistics"
$JAVAC \
	--module-path mods \
	-d classes/monitor.statistics \
	$(find monitor.statistics -name '*.java')
$JAR --create \
	--file mods/monitor.statistics.jar \
	-C classes/monitor.statistics .

echo " > creating monitor.persistence"
$JAVAC \
	--module-path mods \
	-d classes/monitor.persistence \
	$(find monitor.persistence -name '*.java')
$JAR --create \
	--file mods/monitor.persistence.jar \
	-C classes/monitor.persistence .

echo " > creating monitor.rest"
# spark is required as an automatic module, so copy it to mods
cp libs/spark-core-* mods/spark.core.jar
$JAVAC \
	--module-path mods \
	-d classes/monitor.rest \
	$(find monitor.rest -name '*.java')
$JAR --create \
	--file mods/monitor.rest.jar \
	-C classes/monitor.rest .

echo " > creating monitor"
$JAVAC \
	--module-path mods \
	-d classes/monitor \
	$(find monitor -name '*.java')
$JAR --create \
	--file mods/monitor.jar \
	--main-class monitor.Main \
	-C classes/monitor .

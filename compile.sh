#!/bin/bash
set -e

echo "--- COMPILATION & PACKAGING ---"

#Java executable for standard Linux environment
export JAVAC_EXE=javac
export JAR_EXE=jar
#Java executable for MinGW environment
#export JAVAC_EXE=/c/jdk9/bin/javac.exe
#export JAR_EXE=/c/jdk9/bin/jar.exe

echo " > creating clean directories"
rm -rf classes
mkdir classes
rm -rf mods
mkdir mods

echo " > creating monitor.observer"
$JAVAC_EXE \
	-d classes/monitor.observer \
	$(find monitor.observer -name '*.java')
$JAR_EXE --create \
	--file mods/monitor.observer.jar \
	-C classes/monitor.observer .

echo " > creating monitor.observer.alpha"
$JAVAC_EXE \
	--module-path mods \
	-d classes/monitor.observer.alpha \
	$(find monitor.observer.alpha -name '*.java')
$JAR_EXE --create \
	--file mods/monitor.observer.alpha.jar \
	-C classes/monitor.observer.alpha .


echo " > creating monitor.observer.beta"
$JAVAC_EXE \
	--module-path mods \
	-d classes/monitor.observer.beta \
	$(find monitor.observer.beta -name '*.java')
$JAR_EXE --create \
	--file mods/monitor.observer.beta.jar \
	-C classes/monitor.observer.beta .


echo " > creating monitor.statistics"
$JAVAC_EXE \
	--module-path mods \
	-d classes/monitor.statistics \
	$(find monitor.statistics -name '*.java')
$JAR_EXE --create \
	--file mods/monitor.statistics.jar \
	-C classes/monitor.statistics .

echo " > creating monitor.persistence"
$JAVAC_EXE \
	--module-path mods \
	-d classes/monitor.persistence \
	$(find monitor.persistence -name '*.java')
$JAR_EXE --create \
	--file mods/monitor.persistence.jar \
	-C classes/monitor.persistence .

echo " > creating monitor.rest"
# spark is required as an automatic module, so copy it to mods
cp libs/spark-core-* mods/spark.core.jar
$JAVAC_EXE \
	--module-path mods \
	-d classes/monitor.rest \
	$(find monitor.rest -name '*.java')
$JAR_EXE --create \
	--file mods/monitor.rest.jar \
	-C classes/monitor.rest .

echo " > creating monitor"
$JAVAC_EXE \
	--module-path mods \
	-d classes/monitor \
	$(find monitor -name '*.java')
$JAR_EXE --create \
	--file mods/monitor.jar \
	--main-class monitor.Main \
	-C classes/monitor .

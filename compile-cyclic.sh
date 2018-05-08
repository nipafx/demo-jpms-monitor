#!/bin/bash
set -e

echo " > recreating monitor.persistence with dependency on monitor.rest"
mv monitor.persistence/src/main/java/module-info.java monitor.persistence/src/main/java/module-info.ok
mv monitor.persistence/src/main/java/module-info.cyclic monitor.persistence/src/main/java/module-info.java
$JAVAC \
	--module-path mods \
	-d classes/monitor.persistence \
	$(find monitor.persistence -name '*.java')
$JAR --create \
	--file mods-cyclic/monitor.persistence.jar \
	-C classes/monitor.persistence .
mv monitor.persistence/src/main/java/module-info.java monitor.persistence/src/main/java/module-info.cyclic
mv monitor.persistence/src/main/java/module-info.ok monitor.persistence/src/main/java/module-info.java

echo " > recreating monitor.rest with dependency on monitor.persistence"
mv monitor.rest/src/main/java/module-info.java monitor.rest/src/main/java/module-info.ok
mv monitor.rest/src/main/java/module-info.cyclic monitor.rest/src/main/java/module-info.java
$JAVAC \
	--module-path mods \
	-d classes/monitor.rest \
	$(find monitor.rest -name '*.java')
$JAR --create \
	--file mods-cyclic/monitor.rest.jar \
	-C classes/monitor.rest .
mv monitor.rest/src/main/java/module-info.java monitor.rest/src/main/java/module-info.cyclic
mv monitor.rest/src/main/java/module-info.ok monitor.rest/src/main/java/module-info.java

echo " > replacing original versions of monitor.persistence and monitor.rest with new versions"
mv mods-cyclic/* mods

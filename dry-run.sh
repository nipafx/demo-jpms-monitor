#!/bin/bash

echo ""
echo "--- LAUNCH ---"

#Java executable for standard Linux environment
export JAVA_EXE=java
#Java executable for MinGW environment
#export JAVA_EXE=/c/jdk9/bin/java.exe

echo " > dry-run monitor"
echo ""

if [ "$1" == "mvn" ]
then
# the classpath is needed for Spark's dependencies
	$JAVA_EXE \
		--module-path mods-mvn \
		--class-path "libs/*" \
		--dry-run \
		--module monitor
else
# the classpath is needed for Spark's dependencies
	$JAVA_EXE \
		--module-path mods \
		--class-path "libs/*" \
		--dry-run \
		--module monitor
fi

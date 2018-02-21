#!/bin/bash

#Java executable for standard Linux environment
export JAVA=java
#Java executable for MinGW environment
#export JAVA=/c/jdk9/bin/java.exe

echo ""
echo "--- LAUNCH ---"

echo " > run monitor"
echo ""

# (because spark.core is an automatic module and actually required,
#  monitor.observer.zero would be resolved as well and does not need
#  to be added explicitly; it is still done for clarity)
if [ "$1" == "mvn" ]
then
# the classpath is needed for Spark's dependencies
	$JAVA \
		--module-path mods-mvn \
		--class-path "libs/*" \
		--add-modules monitor.observer.zero \
		--module monitor
else
# the classpath is needed for Spark's dependencies
	$JAVA \
		--module-path mods \
		--class-path "libs/*" \
		--add-modules monitor.observer.zero \
		--module monitor
fi

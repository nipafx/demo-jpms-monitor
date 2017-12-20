#!/bin/bash

echo ""
echo "--- LAUNCH ---"

echo " > run monitor"
echo ""

if [ "$1" == "mvn" ]
then
# the classpath is needed for Spark's dependencies
	java \
		--module-path mods-mvn \
		--class-path "libs/*" \
		--module monitor
else
# the classpath is needed for Spark's dependencies
	java \
		--module-path mods \
		--class-path "libs/*" \
		--module monitor
fi

#!/bin/bash

echo ""
echo "--- LAUNCH ---"

echo " > run monitor"
echo ""

# the multi-module build created exploded modules in `classes`
# the `mods` folder contains Spark, which was copied there during compilation
# the classpath is needed for Spark's dependencies
java9 \
	--module-path classes:mods \
	--class-path "libs/*" \
	--module monitor/monitor.Main

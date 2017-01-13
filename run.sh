#!/bin/bash

echo ""
echo "--- LAUNCH ---"

echo " > run monitor"
echo ""

# the classpath is needed for Spark's dependencies
java9 \
	--module-path mods \
	--class-path "libs/*" \
	--module monitor

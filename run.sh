#!/bin/bash

echo ""
echo "--- LAUNCH ---"

echo " > run monitor"
echo ""

# the classpath is needed for Spark's dependencies
java9 \
	-p mods \
	-cp "libs/*" \
	-m monitor

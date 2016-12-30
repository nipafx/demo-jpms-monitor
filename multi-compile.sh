#!/bin/bash

echo "--- COMPILATION & PACKAGING ---"

echo " > creating clean directories"
rm -rf multi-src
mkdir multi-src
mkdir multi-src/monitor
mkdir multi-src/monitor.observer
mkdir multi-src/monitor.observer.alpha
mkdir multi-src/monitor.observer.beta
mkdir multi-src/monitor.persistence
mkdir multi-src/monitor.rest
mkdir multi-src/monitor.statistics

echo " > copying source files"
cp -r monitor/src/main/java/* multi-src/monitor
cp -r monitor.observer/src/main/java/* multi-src/monitor.observer
cp -r monitor.observer.alpha/src/main/java/* multi-src/monitor.observer.alpha
cp -r monitor.observer.beta/src/main/java/* multi-src/monitor.observer.beta
cp -r monitor.persistence/src/main/java/* multi-src/monitor.persistence
# cp -r monitor.rest/src/main/java/* multi-src/monitor.rest
cp -r monitor.statistics/src/main/java/* multi-src/monitor.statistics

echo " > compiling multiple modules"
javac9 \
	--module-source-path multi-src/ \
	-d classes \
	$(find multi-src -name '*.java')


#!/bin/bash
set -e

#Java executable for standard Linux environment
export JDEPS=jdeps
export JLINK=jlink
export JMODS=/opt/jdk-9/jmods
#Java executable for MinGW environment
#export JDEPS=/c/jdk9/bin/jdeps.exe
#export JLINK=/c/jdk9/bin/jlink.exe
#export JMODS=/c/jdk9/jmods

# The CREATE MONITOR IMAGE step isn't likely to work in MinGW unless using a
# native JDK for Linux/MinGW that uses the colon (not the semicolon) as the
# file separator. With a Windows JDK, the colon in a class/module path is an
# illegal character. In a Unix shell, the semicolon is considered a command
# terminator when unquoted. Quoting it bypasses this "command terminator"
# thing, but only one path entry is taken into consideration.

echo "--- DELETING IMAGES ---"
rm -rf jdk-base
rm -rf jdk-monitor

echo ""
echo "--- DEPENDENCIES ON PLATFORM MODULES ---"
$JDEPS -summary -recursive \
	--module-path mods \
	--module monitor \
	--add-modules monitor.observer.alpha,monitor.observer.beta \
| grep java.

echo ""
echo "--- CREATE JAVA.BASE IMAGE ---"
$JLINK \
	--output jdk-base \
	--module-path $JMODS \
	--add-modules java.base

echo ""
echo "--- LIST JAVA.BASE IMAGE MODULES ---"
jdk-base/bin/java --list-modules

echo ""
echo "--- CREATE MONITOR IMAGE ---"
$JLINK \
	--output jdk-monitor \
	--module-path $JMODS:mods \
	--add-modules monitor,monitor.observer.alpha,monitor.observer.beta \
	--launcher monitor=monitor

echo ""
echo "--- LIST MONITOR IMAGE MODULES ---"
jdk-monitor/bin/java --list-modules

echo ""
echo "--- LAUNCH MONITOR IMAGE MODULES ---"
jdk-monitor/bin/monitor

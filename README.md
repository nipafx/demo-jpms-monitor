> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Split Packages at Compile Time

The modules _monitor_ and _monitor.statistics_ contain the same package `monitor.statistics` - they are said to _split_ it.
The module system forbids that and consequently compilation fails for a module that sees this package split, which in this case is only _monitor_:

```
monitor/src/main/java/monitor/statistics/SimpleStatistician.java:1:
	error: package exists in another module: monitor.statistics
package monitor.statistics;
^
1 error
```

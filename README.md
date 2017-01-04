> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Compile-time Access to Non-exported Package

`Main` uses `monitor.observer.dis.DisconnectedServiceObserver`, but the module owning that package does not export it.
The result is a compile time error:

```
monitor/src/main/java/monitor/Main.java:6: error: package monitor.observer.dis is not visible
import monitor.observer.dis.DisconnectedServiceObserver;
                       ^
  (package monitor.observer.dis is declared in module monitor.observer, which does not export it)
1 error
```

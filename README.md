> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Qualified Exports

The observer modules share common utilities, which can be found in [_monitor.observer_/`ObserverUtil`](monitor.observer/src/main/java/monitor/observer/utils/ObserverUtil.java)
To make sure they are only available to these modules, _monitor.observer_ uses a _qualified export_:

```java
module monitor.observer {
	exports monitor.observer;
	exports monitor.observer.utils
		to monitor.observer.alpha, monitor.observer.beta;
}
```

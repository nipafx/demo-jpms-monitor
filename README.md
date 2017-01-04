> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Reflection Over Internals

`Main` uses reflection to create an instance of `DisconnectedServiceObserver`:

```java
private static Object createDisconnected() throws Exception {
	Constructor<?> constructor = Class
		.forName("monitor.observer.DisconnectedServiceObserver")
		.getDeclaredConstructor();
	constructor.setAccessible(true);
	return constructor.newInstance();
}
```

The thing is, while `DisconnectedServiceObserver` is in an exported package it is not public and hence inaccessible.
Accordingly, this results in an exception at run time:

```
Exception in thread "main" java.lang.reflect.InaccessibleObjectException:
Unable to make monitor.observer.DisconnectedServiceObserver() accessible:
module monitor.observer does not "opens monitor.observer" to module monitor
    at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:337)
    at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:281)
    at java.base/java.lang.reflect.Constructor.checkCanSetAccessible(Constructor.java:192)
    at java.base/java.lang.reflect.Constructor.setAccessible(Constructor.java:185)
    at monitor/monitor.Main.createDisconnected(Main.java:50)
    at monitor/monitor.Main.main(Main.java:25)
```

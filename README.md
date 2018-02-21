> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Implied Readability

This branch shows how _implied readability_ allows the _monitor_ and _monitor.peek_ modules to use _monitor.observer.alpha_ without requiring _monitor.observer_ even though that module's API is needed as well.
The feat is achieved by using `requires transitive` to establish the dependency from _monitor.observer.alpha_ to _monitor.observer_.

```java
module monitor.observer.alpha {
		requires transitive monitor.observer;
		exports monitor.observer.alpha;
}
```

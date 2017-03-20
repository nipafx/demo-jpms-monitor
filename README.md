> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Optional Dependencies

The module _monitor.stats_ has an optional dependency on _stats.fancy_.
It's module descriptor uses `requires static` for that:

```java
module monitor.statistics {
	requires monitor.observer;
	requires static stats.fancy;
	exports monitor.statistics;
}
```

To check whether the module is present, it uses the reflection API (see `Statistician`):

```java
private boolean isModulePresent(String moduleName) {
	return this.getClass()
			.getModule()
			.getLayer()
			.findModule(moduleName)
			.isPresent();
}
```

Even though the module is on the module path, it is not resolved and thus not part of the module graphs unless:

* it is required (non-optionally) by some other module
* it is explicitly added to the graph during resolution

The first is not the case here.
The second can be achieved by launching the app with `--add-modules`:

```bash
# add the optional dependency stats.fancy
java \
	--module-path mods \
	--class-path "libs/*" \
	--add-modules stats.fancy \
	--module monitor
```

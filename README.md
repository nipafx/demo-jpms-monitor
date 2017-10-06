> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Duplicate Modules

Here, we create two artifacts claiming to contain _monitor.observer.beta_ and place them into the same folder:

```
jar --create \
	--file mods/monitor.observer.beta-1.0.jar \
	--module-version 1.0 \
	-C classes/monitor.observer.beta .
jar --create \
	--file mods/monitor.observer.beta-2.0.jar \
	--module-version 2.0 \
	-C classes/monitor.observer.beta .
```

(Only in `compile.sh`, not with Maven.)

The next module-related command, compiling _monitor.statistics_, immediately fails:

```
error: duplicate module on application module path
  module in monitor.observer.beta
1 error
```

This happens even though _monitor.statistics_ does not even use the duplicated module.

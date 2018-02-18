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

(Only in `compile.sh` or `compile.bat`, not with Maven.)

The next module-related command, compiling _monitor.statistics_, immediately fails:

```
error: duplicate module on application module path
  module in monitor.observer.beta
1 error
```

When using `multi-compile.sh` or `multi-compile.bat`, the project builds successfully.
However, when running it (using `run.sh` or `run.bat`) or just validating the module tree
(using `dry-run.sh` or `dry-run.bat`), we get the following error:

```
Error occurred during initialization of boot layer
java.lang.module.FindException: Two versions of module monitor.observer.beta found in mods (monitor.observer.beta-2.0.jar and monitor.observer.beta-1.0.jar)
```

This happens even though _monitor.statistics_ does not even use the duplicated module.

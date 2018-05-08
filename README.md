> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Cyclic Dependencies at Compile Time

The modules in this branch create a simple cyclic dependency:

* _monitor.persistence_ depends on _monitor.statistics_ (as usual)
* _monitor.statistics_ depends on _monitor.persistence_ (this is new)

When compiling modules one by one, there is no way to observe the cycle because one dependency is always missing.
Multi-module compilation gets around that and results in the following error:

```
TODO: Does it, though?
```

(Only in `multi-compile.sh`, not with Maven.)

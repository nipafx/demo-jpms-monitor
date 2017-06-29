> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Missing Transitive Dependency

During compilation transitive dependencies are allowed to be missing unless they are exposed with the `transitive` keyword.
Three modules are involved (only the dependencies are shown):

```
module monitor.rest {
	requires monitor.statistics;
}

module monitor.statistics {
	requires transitive monitor.observer.alpha;
}

module monitor.observer.alpha { }
```

The modules are compiled in the order _alpha_, _statistics_, _rest_ and just before _rest_ gets compiled, _alpha_ is removed from the `mods` folder (only in `compile.sh`).
Because _rest_ depends on _statistics_, which **exposes** _alpha_, the compiler gives an error:

```
error: module not found: monitor.observer.alpha
1 error
```

Interestingly enough, though, if the `transitive` keyword is removed, compilations works just fine because all the modules _rest_ can read are observable.

> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Cyclic Dependencies at Run Time

Getting a dependency cycle past the compiler requires a little bit of effort:

* build all modules as usual (i.e. without any cycle)
* add dependency from _monitor.persistence_ to _monitor.rest_ and rebuild it against the usual modules
* add dependency from _monitor.rest_ to _monitor.persistence_ and rebuild it against the usual modules
* replace usual versions of _monitor.persistence_ and _monitor.rest_ with new ones

Because each half of the cycle was built against a version of the depended module that did not have the dependency back, the compiler never saw the cycle.
Only when both versions come together is there actually a cycle.

(Only in `compile.sh`, not with Maven.)

The runtime catches it:

```
Error occurred during initialization of boot layer
java.lang.module.ResolutionException:
Cycle detected: monitor.persistence -> monitor.rest -> monitor.persistence
```

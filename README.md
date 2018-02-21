> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Split Packages at Launch Time

This package split only fails at launch time.

```
Error occurred during initialization of boot layer
java.lang.LayerInstantiationException: Package monitor in both module monitor and module monitor.statistics
```

During compilation the split only becomes relevant when _monitor_ is compiled against _monitor.statistics_ (both contain `monitor`), but because _monitor.statistics_ does not _export_ `monitor`, the compiler is content.

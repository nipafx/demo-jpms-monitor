> :arrow_upper_right: This README only covers one section of the demo.
> The [master branch](../../tree/master) contains more information.

# Layers

The starting point for this version is the _features-services_ branch, which the reader should refer to for details about the JPMS support for service-based architectures.
In this version, each service is instantiated in its own layer, as can be seen in the `Main` class.

```java
private static void registerNewService(String serviceName, Path... modulePaths) {
  ModuleLayer layer = createLayer(modulePaths);
  Stream<ServiceObserverFactory> observerFactories = ServiceLoader
      .load(layer, ServiceObserverFactory.class).stream()
      .map(Provider::get);
  Optional<ServiceObserver> observer = observerFactories
      .flatMap(factory -> factory.createIfMatchingService(serviceName).stream())
      .findFirst();
  observer.ifPresentOrElse(
          monitor::addServiceObserver,
          () -> System.out.println(
              "WARNING: Failed to create service for " + serviceName)
      );
}

private static ModuleLayer createLayer(Path[] modulePaths) {
  Configuration configuration = createConfiguration(modulePaths);
  ClassLoader thisLoader = getThisLoader();
  return getThisLayer()
      .defineModulesWithOneLoader(configuration, thisLoader);
}
```

The building and executing procedures don't change.

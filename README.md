# ServiceMonitor

An example application for my book [_The Java Module System_](https://www.manning.com/books/the-java-module-system?a_aid=nipa&a_bid=869915cb).
The _Service Monitor_ is an application that observes a hypothetical network of microservices by

* contacting individual services (not really, it just makes up data)
* collecting and aggregating diagnostic data into statistics
* persisting statistics
* making statistics available via REST

It is split into a number of modules that focus on specific concerns.
Each module has its own directory that contains the known folder structure, e.g. `src/main/java`.


## Branches

The master branch uses basic features, except where it has to use automatic and unnamed modules for the non-modularized dependencies (Spark, Hibernate).
Other branches explore individual features of the module system:

* [services](../../tree/feature-services) aka `provides ... with` and `uses`
* [implied readability](../../tree/feature-implied-readability) aka `requires transitive`
* [optional dependencies](../../tree/feature-optional-dependencies) aka `requires static`
* [qualified exports](../../tree/feature-qualified-exports) aka `exports to`
* [image creation with `jlink`](../../tree/feature-jlink)
* [layers](../../tree/feature-layers)

The [branch `features-combined`](../../tree/features-combined) combines many of those into a final version of the application.

Then there are some branches that explore how things can break:

* [duplicate modules](../../tree/break-duplicate-modules-even-if-unrequired)
* [missing transitive dependency](../../tree/break-missing-transitive-dependency)
* [reflection over internals](../../tree/break-reflection-over-internals)
* split package, on [compilation](../../tree/break-split-package-compilation) and [launch](../../tree/break-split-package-launch)


## Setup

This demo was developed against JDK 9.0.4.
The command line scripts for shell and batch use the default commands `javac`, `jar`, and `java`.
If these commands don't refer to the Java 9 version on your system, you can enter the appropriate paths at the top of each script.

This is a multi-module Maven project, which your IDE should be able to import. If you're using Maven directly, make sure you have 3.5.0 or higher.


## Build and Execution

This being a Maven project means you can build and run with with Maven, but to really see how the module system works, I recommend to use the scripts.

### Maven

To build and run with Maven execute the following in the project's root:

```
mvn clean install
mvn exec:exec -pl .
```

Unfortunately, Jetty doesn't come up due to a `NoClassDefFoundError` (of `org.eclipse.jetty.http.pathmap.PathSpec`).
Need to inspect that...

### Scripts

The root directory contains a number of Windows batch and Linux shell scripts:

* `compile`: compiles the modules one by one
* `multi-compile`: compiles all modules at once
* `dry-run`: launches the application with `--dry-run`, which aborts before calling the main method
* `run`: launches the application

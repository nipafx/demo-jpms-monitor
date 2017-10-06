# ServiceMonitor

An example application for my book [_The Java Module System_](https://www.manning.com/books/the-java-module-system?a_aid=nipa&a_bid=869915cb).
The _Service Monitor_ is an application that observes a hypothetical network of microservices by

* contacting individual services
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

Then there are some branches that explore how things can break:

* [duplicate modules](../../tree/break-duplicate-modules-even-if-unrequired) (not properly documented)
* split package, on [compilation](../../tree/break-split-package-compilation) and [launch](../../tree/break-split-package-launch) (not properly documented)
* [missing transitive dependency](../../tree/break-missing-transitive-dependency) (not properly documented)


## Setup

This demo was developed against JDK 9+181 (first official version of [Java 9](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html)).
For it to work, the Java 9 variants of `javac`, `jar`, and `java` must be available on the command line via `javac9`, `jar9`, and `java9`, e.g. by symlinking ([Linux, MacOS](https://stackoverflow.com/q/1951742/2525313), [Windows](https://www.howtogeek.com/howto/16226/complete-guide-to-symbolic-links-symlinks-on-windows-or-linux/)) them.

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

The root directory contains a number of Linux shell scripts:

* `compile.sh`: compiles the modules one by one
* `multi-compile.sh`: compiles all modules at once
* `dry-run.sh`: launches the application with `--dry-run`, which aborts before calling the main method
* `run.sh`: launches the application

Adapting them for Windows should be straight forwards except for where `$(find ...)` is used, which you might have to replace with a list of the actual files.

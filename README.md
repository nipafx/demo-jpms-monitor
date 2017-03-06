# ServiceMonitor

An example application for [_Java 9 Module System](https://git.manning.com/agileauthor/parlog).
The _Service Monitor_ is an application that observes a hypothetical network of microservices by

* contacting individual services
* collecting and aggregating diagnostic data into statistics
* persisting statistics
* making statistics available via REST

It is split into a number of modules that focus on specific concerns.
Each module has its own directory that contains the known folder structure, e.g. `src/main/java`.


## Branches

The master branch uses basic features, except where it has to use automatic and unnamed modules for the non-modularized dependencies (Spark, Hibernate).
Other branches explore individual features of the module system.


## Setup

This demo was developed against build 158 of the [Jigsaw early access prototype](https://jdk9.java.net/jigsaw/).
For it to work the Java 9 variants of `javac`, `jar`, and `java` must be available on the command line via `javac9`, `jar9`, and `java9`, e.g. by symlinking them.

The root directory contains a number of shell scripts:

* `compile.sh`: compiles the modules one by one
* `multi-compile.sh`: compiles all modules at once
* `dry-run`: launches the application with `--dry-run`, which aborts before calling the main method
* `run`: launches the application

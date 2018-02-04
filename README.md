# ServiceMonitor

The example application from my book [_The Java Module System_](https://www.manning.com/books/the-java-module-system?a_aid=nipa&a_bid=869915cb).
The _Service Monitor_ is an application that observes a hypothetical network of microservices by

* contacting individual services (not really, it just makes up data)
* collecting and aggregating diagnostic data into statistics
* persisting statistics
* making statistics available via REST

It is split into a number of modules that focus on specific concerns.
Each module has its own directory that contains the known folder structure, e.g. `src/main/java`.


## Book References and Module Names

The project is closely related to the book.
The bookmark emoji 🔖 marks references to chapters and sections (for example, [🔖2] for chapter 2 and [🔖2.2] for section 2.2).

The module names in this demo are dangerously short to make them easier on the eyes in scripts, snippets, diagrams, and so forth.
**Do not use such short names in real life!**
Instead, name modules like packages, i.e. pick a domain that belongs to the project and reverse it, so you end up with unique names [🔖3.1.3].


## Branches

The master branch uses basic features [🔖2.2], except where it has to use automatic and unnamed modules for the non-modularized dependencies (Spark, Hibernate).
Other branches explore individual features of the module system:

* [resource encapsulation](../../tree/feature-resources) [🔖5.2]
* [services](../../tree/feature-services) aka `provides ... with` and `uses` [🔖10]
* [implied readability](../../tree/feature-implied-readability) aka `requires transitive` [🔖11.1]
* [optional dependencies](../../tree/feature-optional-dependencies) aka `requires static` [🔖11.2]
* [qualified exports](../../tree/feature-qualified-exports) aka `exports to` [🔖11.3]
* [image creation with `jlink`](../../tree/feature-jlink) [🔖14]
* [layers](../../tree/feature-layers) [🔖12.4]

The [branch `features-combined`](../../tree/features-combined) combines many of those into a final version of the application [🔖15.1].

Then there are some branches that explore how things can break:

* [missing transitive dependency](../../tree/break-missing-transitive-dependency) [🔖3.2.2]
* [duplicate modules](../../tree/break-duplicate-modules-even-if-unrequired) [🔖3.2.2]
* cyclic dependencies, on [compilation](../../tree/break-cyclic-dependency-compile-time) and [launch](../../tree/break-cyclic-dependency-run-time) [🔖3.2.2]
* split package, on [compilation](../../tree/break-split-package-compilation) and [launch](../../tree/break-split-package-launch) [🔖3.2.2]
* [package not exported](../..tree/break-package-not-exported-compile-time) [🔖3.3.3]
* [reflection over internals](../../tree/break-reflection-over-internals) [🔖3.3.3]


## Setup

This demo was developed against JDK 9.0.4.
The command line scripts for shell and batch use the default commands `javac`, `jar`, and `java`.
If these commands don't refer to the Java 9 version on your system, you can enter the appropriate paths at the top of each script.

### Java

Don't worry if your system still runs Java 8 and you don't want to make Java 9 or later your default.
Just pick a recent Java version (see [the archives for JDK 9](http://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase9-3934878.html)) and unpack it.
Then you can add it to your IDE (how-to for [IntelliJ](https://www.jetbrains.com/help/idea/configuring-build-jdk.html) and [Eclipse](https://stackoverflow.com/q/13635563/2525313)) and [configure Maven with `.mavenrc](https://blog.codefx.org/tools/maven-on-java-9/#The-mavenrc-File) to use a specific JDK.

### IDEs and Build Tools

This is a multi-module Maven project, which your IDE should be able to import.
You should use at least Intellij IDEA 2017.2, Eclipse Oxygen.1a, or NetBeans 9. 
If you're using Maven directly, make sure you have 3.5.0 or higher.



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

It is also not possible to demonstrate all details with Maven.
Some branches only show the expected behavior when script is executed (the branch's README will point that out.)

### Scripts

The root directory contains a number of Windows batch and Linux shell scripts:

* `compile`: compiles the modules one by one
* `multi-compile`: compiles all modules at once
* `dry-run`: launches the application with `--dry-run`, which aborts before calling the main method
* `run`: launches the application

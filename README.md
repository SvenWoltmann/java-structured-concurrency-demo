# Java Structured Concurrency Demo

This is the demo code for the HappyCoders.eu article [Structured Concurrency with Java](https://www.happycoders.eu/java/structured-concurrency-structuredtaskscope/). 

It shows how to run three tasks in parallel using the old `ExecutorService` and the new `StructuredTaskScope`.

## How to Compile

You need Java 21, available for download [here](https://jdk.java.net/21/).

Neither Maven nor IntelliJ support Java 21 at this time (June 2023).

Therefore, you have to compile the application manually:

```
javac --enable-preview --source 21 -d target/classes src/main/java/eu/happycoders/demo/util/*.java src/main/java/eu/happycoders/demo/model/*.java src/main/java/eu/happycoders/demo/*.java 
```

And then run the three demos like this:

```
java -cp target/classes eu.happycoders.demo.ExecutorServiceDemo
java -cp target/classes --enable-preview eu.happycoders.demo.StructuredConcurrencyDemo
java -cp target/classes --enable-preview eu.happycoders.demo.ShutdownOnFailureDemo
java -cp target/classes --enable-preview eu.happycoders.demo.ShutdownOnSuccessDemo
java -cp target/classes --enable-preview eu.happycoders.demo.StructuredConcurrencyWithScopedValueDemo
```

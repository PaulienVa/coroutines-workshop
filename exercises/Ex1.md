# Basic of coroutines

## Let's start cooking!
... but first the basics.

Kotlin coroutines are light-weight threads on the JVM. Using coroutines, concurrent programming is made easy.

<todo add some extra documentation on coroutines and differences with Java>


### First coroutine.

To start to get some feeling on what a coroutine is, let's have a look at our [Cooker](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/kitchen/Cooker.kt).
In the function `boil(pan)` a coroutine is started using `runBlocking {}`. `runBlocking` blocks the current thread, executes the code in the block and releases the thread once all the jobs inside the coroutine are completed.
Using `launch {}` a new coroutine inside the coroutine is started and ran in the background.

Run this piece of code using the `main` function in the [Exercise1Runner](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/runners/Exercise1Runner.kt).

Have a look at the print statements to understand the order in which the code is executed.

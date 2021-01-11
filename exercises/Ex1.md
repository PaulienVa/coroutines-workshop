# Basic of coroutines - Let's start cooking!
... but first the basics.

Kotlin coroutines are light-weight threads on the JVM. Using coroutines, concurrent programming is made easy.

<todo add some extra documentation on coroutines and differences with Java>

## Scope

## First coroutine.

To start to get some feeling on what a coroutine is, let's have a look at our [Cooker](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/kitchen/Cooker.kt).
In the function `boil(pan)` a coroutine is started using `runBlocking {}`. `runBlocking` blocks the current thread, executes the code in the block and releases the thread once all the jobs inside the coroutine are completed.
Using `launch {}` a new coroutine inside the coroutine is started and ran in the background.

Run this piece of code using the `main` function in the [Exercise1Runner](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/runners/Exercise1Runner.kt).

Have a look at the print statements to understand the order in which the code is executed.

Now the last line of logging in the `runBlocking {}` block, is not the last one showed. The only way to get this done is to wait until the child coroutine is finished. 

To do so, assign the `launch {}` block to a variable called `job`. 

/!\ it is important this variable is assigned into the `runBlocking` as the coroutines is executed.

This variable `job` is of type `Job`.
By calling the function `join()`, parent coroutine will wait until the job coroutine is completed. 

Now run the exercise again to see the order of the logging. It should look like this:

```text
[Thread: id: 1, name: main, priority: 5] [IN RUN_BLOCKING]: Starting to prepare the boiling of the water
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Starting to boil a pan of water
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Increasing pan's temperature to 20
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Increasing pan's temperature to 30
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Increasing pan's temperature to 40
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Increasing pan's temperature to 50
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Increasing pan's temperature to 60
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Increasing pan's temperature to 70
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Increasing pan's temperature to 80
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Increasing pan's temperature to 90
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Increasing pan's temperature to 100
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Finished boiling a pan of water
[Thread: id: 1, name: main, priority: 5] [IN RUN_BLOCKING]: Finished boiling a pan of water
```

## Cancel the boiling

We started the boiling a bit to early, so let's cancel the boiling.
Before calling the `join()` function on the job, call the `cancel()` function. Now check the logging, there should be no logging of the `launch` block.

Now add a delay of 2ms before increasing the temperature. Before cancelling the coroutines, add a delay of 10ms. 
Those delays do not really add functionality, but it creates some insight on how and when the coroutines are cancelled.
The logging should now show 4 lines about increasing the temperature.

The two lines of cancelling and join can be combined into one call:

`job.cancelAndJoin()`

Now, every suspending function is cancellable.

## Suspending functions

Now let's have a look at suspend functions. They are at the center of coroutines. Those functions have the particularity that they can be started, paused and resumed at any moment.
They have the same syntax as regular functions, but they are prefixed with the keyword `suspend`:

```kotlin
suspend fun doSomething(val something: Int) {
  // some implementation
}
```

Important to know is that they can only be called from a coroutine or another suspend function.

Now let's see what happens when we extract the lines of code increasing the temperature until the boiling point (the `while` block). Using the refactor function from IntelliJ the `suspend` keyword will be visible in the newly created function.
This is due to the `delay()` function that is used in the `while` block. This function is suspending the functionality. Removing the call to the `delay` function will make the `suspend` keyword unnecessary.

## Composing



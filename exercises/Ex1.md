# Basic of coroutines - Let's start cooking!
... but first the basics.

Kotlin coroutines are light-weight threads on the JVM. Using coroutines, concurrent programming is made easy.

## First coroutine.

To start to get some feeling on what a coroutine is, let's have a look at our [Stove](../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/kitchen/Stove.kt).
In the function `boil(pan)` a coroutine is started using `runBlocking {}`. `runBlocking` blocks the current thread, executes the code in the block and releases the thread once all the jobs inside the coroutine are completed.
Using `launch {}` a new coroutine inside the coroutine is started and ran in the background.

Run this piece of code using the `main` function in the [Exercise1Runner](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/runners/Exercise1Runner.kt).

Have a look at the print statements to understand the order in which the code is executed.

Now the last line of logging in the `runBlocking {}` block, is not the last one showed. The only way to get this done is to wait until the child coroutine is finished. 

To do so, assign the `launch {}` block to a variable called `job`. 
Be careful! It is important this variable is assigned into the `runBlocking` as the coroutines is executed.

This variable `job` is of type `Job`.
By calling the function `join()`, parent coroutine will wait until the job coroutine is completed. 

Now run the exercise again to see the order of the logging. It should look like this:

```text
[Thread: id: 1, name: main, priority: 5] [IN RUN_BLOCKING]: Finished boiling a pan of WATER
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Starting to boil a pan of WATER
[Thread: id: 1, name: main, priority: 5] [STOVE: IN LAUNCH]: Increasing WATER's temperature to 20
[Thread: id: 1, name: main, priority: 5] [STOVE: IN LAUNCH]: Increasing WATER's temperature to 30
[Thread: id: 1, name: main, priority: 5] [STOVE: IN LAUNCH]: Increasing WATER's temperature to 40
[Thread: id: 1, name: main, priority: 5] [STOVE: IN LAUNCH]: Increasing WATER's temperature to 50
[Thread: id: 1, name: main, priority: 5] [STOVE: IN LAUNCH]: Increasing WATER's temperature to 60
[Thread: id: 1, name: main, priority: 5] [STOVE: IN LAUNCH]: Increasing WATER's temperature to 70
[Thread: id: 1, name: main, priority: 5] [STOVE: IN LAUNCH]: Increasing WATER's temperature to 80
[Thread: id: 1, name: main, priority: 5] [STOVE: IN LAUNCH]: Increasing WATER's temperature to 90
[Thread: id: 1, name: main, priority: 5] [STOVE: IN LAUNCH]: Increasing WATER's temperature to 100
[Thread: id: 1, name: main, priority: 5] [IN LAUNCH]: Finished boiling a pan of WATER
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

Now increase the delay on line 17 to 300 ms. -> replace this with a checkout of a branch (with the recursive function)

We are going to see how it works to compose several coroutines in one. 

The assignment is to cook two pans at the same time: one of water and one of olive oil. When both are finished, a signal will be displayed.

To do so, follow the following steps:
 1. make the `boil` function in the `Cooker` object, a suspend function
 2. make it return a Boolean with value true (at the last line of the function)
 2. replace the `runBlocking` by `coroutineScope`
 
The last step has to be taken, as `runBlocking` will block the current thread until all the code has completed. However, in this part of the exercise, we want to start both pans boiling at the same time, so both coroutines should be able to start.
 
Now the `Exercise1Runner` won't compile anymore, the boil function is now a suspend function without a coroutine scope, this won't work. We need to introduce a coroutine scope.
Use a runBlocking block again. The coroutines will be terminated, once this block is terminated.

For now, we will use the `GlobalScope.launch` method, just to be using something different.

In [Exercise1Runner](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/runners/Exercise1Runner.kt), the `run` function should look like this:

```kotlin
  runBlocking {
    Cooker.boil(Pan(1, Liquid.WATER, 10))
  }
```

Now, wrap the `Cooker.boil` statement in a `async` block and add another async block for cookie olive oil (there is an `OLIVE_OIL` enum present in the `Liquid` enum).
The results of both `async` blocks to variables: `val waterPan` and `val oliveOilPan`.
Now add the following statement:

```kotlin
if (waterPan.await() && oliveOilPan.await()) {
    println("\n")
    printlnCW("[RUNNER]: Both pans are finished boiling \n")
}
```

Now have a look at the logging produced. It is visible that the cooking process starts simultaniously and that the olive oil pan, which takes more time to boil continues even though the water is already finished.
The signal that both pans are finished boiling is only displayed when both coroutines are completed.

## Timeouts

One last thing about coroutines. It looks a bit scary to work with delays, waiting for one another, so one might want to secure this whole process by determining a timeout.
For that in `Exercise1Runner`, wrap the following statements in a `withTimeout` block, with a timeout of 10ms, to see what happens:
```kotlin
withTimeout(10){
    val waterPan = async { Cooker.boil(Pan(1, Liquid.WATER, 10)) }
    val oliveOilPan = async { Cooker.boil(Pan(1, Liquid.OLIVE_OIL, 10)) }
    
    if (waterPan.await() && oliveOilPan.await()) {
        println("\n")
        printlnCW("[RUNNER]: Both pans are finished boiling \n")
    }
}
```

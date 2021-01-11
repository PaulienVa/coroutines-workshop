# Flow 

## What is it?

As seen in the [first exercise](./Ex1.md), a function such as `async`, also a `Deferred` function returns a single value. A `suspend` function can return
a collection, when needed, however, the `suspend` function will only return/complete, when all values are collected. Now there is a third option, `Flow`s representing
an asynchronous stream, computing it's values asynchronously.
For more info read the [official documentation](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)


## Building and collecting a flow
Let's have a look at what this looks like. Open the [Cook.kt](../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/kitchen/Cook.kt) 

# Flow 

## What is it?

As seen in the [first exercise](./Ex1.md), a function such as `async`, also a `Deferred` function returns a single value. A `suspend` function can return
a collection, when needed, however, the `suspend` function will only return/complete, when all values are collected. Now there is a third option, `Flow`s representing
an asynchronous stream, computing it's values asynchronously.
For more info read the [official documentation](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/index.html)


## Building and collecting a flow
Let's have a look at what this looks like in practice. Open the file [Cook.kt](../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/kitchen/Cook.kt) 

a. Have a look at the first `private` `suspend` called `collectIngredients`. It already contains a `flow{}` builder block, to build up the Flow of ingredients.
The flow of ingredients should emit each ingredient of the recipe. For each ingredient the following print statement should be there as well:
```kotlin
printlnCW("[COOK]: collecting ingredient $it")
```

b. Now the cook is able to collect the ingredients from a recipe, so let's mix them in a bowl. For that the `private` `suspend` function `mixInBowl` is ready to mix!
At least, if it is implemented. To mix the ingredients collected by the cook, "collect" the values emitted by the flow of ingredients and them to the bowl, using the appropriate function of [`Bowl`](./../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/kitchen/utensils/Utensils.kt)
During the collection of the ingredients, add, per collected ingredient, the following print statement.

```kotlin
printlnCW("[COOK]: putting $it in a bowl for preparation of $recipeName")
```

Now run the main function in the [Exercise2Runner.kt](../src/main/kotlin/nl/openvalue/paulienvanalst/kotlin/coroutines/workshop/runners/Exercise2Runner.kt).

Have a look at the logging and see how ingredients a collected and directly put in the bowl.



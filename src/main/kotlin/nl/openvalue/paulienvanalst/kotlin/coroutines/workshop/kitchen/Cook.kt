package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Bowl
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.references.Recipe
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer.printlnCW

object Cook {

    suspend fun mixIngredientsInBowl(recipe: Recipe) {
        val pancakeIngredients = gatherIngredients(recipe.ingredients)
        val bowl = mixInBowl(pancakeIngredients, recipe.name)
        printlnCW("[COOK]: Everything mixed in the bowl: ${bowl.content} ")
    }

    private suspend fun gatherIngredients(ingredients: List<String>): Flow<String> =
        flow { // flow builder
            ingredients.forEach {
                printlnCW("[COOK]: collecting ingredient $it")
                delay(1000)
                emit(it) //emit value to flow
            }
        }

    private suspend fun mixInBowl(
        pancakeIngredients: Flow<String>,
        recipeName: String
    ): Bowl {
        var bowl = Bowl.empty()
        withTimeout(900) {
            pancakeIngredients.collect { // collecting the emitted  values
                printlnCW("[COOK]: putting $it in a bowl for preparation of $recipeName")
                bowl = bowl.addContent(it)
                printlnCW("[COOK]: Ingredients in bowl: ${bowl.content}")
            }
        }
        return bowl
    }

    suspend fun selectFastRecipes(recipes: List<Recipe>) {
        return recipes.asFlow()
            .filter {
                it.duration <= 15
            }
            .collect { printlnCW("[COOK]: Fast recipe found: ${it.name}") }
    }
}

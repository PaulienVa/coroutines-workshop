package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Bowl
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.references.Recipe
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer.printlnCW

object Cook {

    suspend fun mixIngredientsInBowl(recipe: Recipe) {
        val pancakeIngredients = gatherIngredients(recipe.ingredients)
        val bowl = mixInBowl(pancakeIngredients, recipe.name)
        printlnCW("[COOK]: Everything mixed in the bowl: ${bowl.content} ")
    }

    suspend fun gatherIngredients(ingredients: List<String>): Flow<String> =
        flow { // flow builder
        }

    private suspend fun mixInBowl(
        pancakeIngredients: Flow<String>,
        recipeName: String
    ): Bowl {
        var bowl = Bowl.empty()
        return bowl
    }

    suspend fun selectFastRecipes(recipes: List<Recipe>) {
        return recipes.asFlow()
            .collect { printlnCW("[COOK]: Fast recipe found: ${it.name}") }
    }
}

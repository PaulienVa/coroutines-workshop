package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.runners

import kotlinx.coroutines.runBlocking
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.Cook
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.references.Recipe
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer.printlnCW

object Exercise2Runner {
    fun run() {
        printlnCW("[RUNNER]: Running Exercise 2 ...\n")
        runBlocking {
            Cook.mixIngredientsInBowl(pancakes)
            Cook.selectFastRecipes(listOf(pancakes, scrambledEggs, fondue))

        }
        println("\n")
        printlnCW("[RUNNER]: Finishing exercise 2\n")
    }
}

fun main() {
    Exercise2Runner.run()
}

private val pancakes = Recipe(
    "pancakes",
    20,
    listOf("2 eggs", "250gr flower", "500mL milk", "bit of salt"),
    "Mix everything and bake pancakes"
)
private val scrambledEggs = Recipe(
    "scrambled eggs",
    10,
    listOf("4 eggs", "2 tbsp milk", "bit of salt"),
    "Mix everything and bake and fold the eggs over"
)
private val fondue = Recipe(
    "fondue",
    45,
    listOf("cheese", "wine", "kirsch", "garlic"),
    "Cook the wine, add the cheese, add the kirsch and garlic"
)

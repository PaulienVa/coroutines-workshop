package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.runners

import kotlinx.coroutines.runBlocking
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.Cook
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.references.Recipe
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer.printlnCW

object Exercise2Runner {
    fun run() {
        printlnCW("[RUNNER]: Running Exercise 2 ...\n")
        runBlocking {
            printlnCW("[RUNNER]: Cooking is starting to cook the recipe of ${pancakes.name}")
            pancakes.ingredients.forEach {
                printlnCW("[RUNNER - sync]: $it")
            }

            println("\n");
            Cook.mixIngredientsInBowl(pancakes)

        }
        println("\n")
        printlnCW("[RUNNER]: Finishing exercise 2\n")
    }
}

fun main() {
    Exercise2Runner.run()
}


private val pancakes = Recipe("pancakes", listOf("2 eggs", "250gr flower", "500mL milk", "bit of salt"), "Mix everything and bake pancakes")

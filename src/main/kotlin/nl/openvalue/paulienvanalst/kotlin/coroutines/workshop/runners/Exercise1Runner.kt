package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.runners

import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.Cooker
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Pan

object Exercise1Runner {
    fun run() {
        println("\n Running Exercise 1...\n")
        Cooker.boil(Pan(1, "water", 10))
    }
}

fun main(args: Array<String>) {
    Exercise1Runner.run()
}


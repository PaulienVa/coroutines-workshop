package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.runners

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.Cooker
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Liquid
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Pan
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer.printlnCW

object Exercise1Runner {
    fun run() {
        printlnCW("[RUNNER]: Running Exercise 1...\n")
        runBlocking {
            val waterPan = async { Cooker.boil(Pan(1, Liquid.WATER, 10)) }
            val oliveOilPan = async { Cooker.boil(Pan(1, Liquid.OLIVE_OIL, 10)) }

            if (waterPan.await() && oliveOilPan.await()) {
                println("\n")
                printlnCW("[RUNNER]: Both pans are finished boiling \n")
            }
            printlnCW("[RUNNER]: Finishing exercise 1\n")
        }

    }
}

fun main(args: Array<String>) {
    Exercise1Runner.run()
}


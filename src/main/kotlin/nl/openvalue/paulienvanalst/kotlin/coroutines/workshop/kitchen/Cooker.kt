package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen

import kotlinx.coroutines.*

import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Pan
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer.printlnCW

object Cooker {
    suspend fun boil(pan: Pan) : Boolean {
        coroutineScope { //start main coroutine
            val boiler = launch { // launches a new coroutine in the background but continue
                println("\n")
                printlnCW("[IN LAUNCH]: Starting to boil a pan of ${pan.liquid}")
                increaseUntilBoilingPoint(pan)
                printlnCW("[IN LAUNCH]: Finished boiling a pan of ${pan.liquid}")
                println("\n")
            }
            printlnCW("[IN RUN_BLOCKING]: Starting to prepare the boiling of the water")
            delay(100)
            boiler.cancelAndJoin()
            printlnCW("[IN RUN_BLOCKING]: Finished boiling a pan of ${pan.liquid}")
        }
        return true
    }

    private suspend fun increaseUntilBoilingPoint(pan: Pan) {
        if (!pan.isBoiling) {
            delay(2)
            printlnCW("[IN LAUNCH]: Increasing ${pan.liquid}'s temperature to ${pan.temperature + 10}")
            increaseUntilBoilingPoint(pan.increaseTemperature())
        }
    }
}

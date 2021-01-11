package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen

import kotlinx.coroutines.*

import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Pan
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer.printlnCW

object Cooker {
    fun boil(pan: Pan) {
        runBlocking { //start main coroutine
            val boiler = launch { // launches a new coroutine in the background but continue
                printlnCW("[IN LAUNCH]: Starting to boil a pan of ${pan.liquid}")
                increaseUntilBoilingPoint(pan)
                printlnCW("[IN LAUNCH]: Finished boiling a pan of ${pan.liquid}")
            }
            printlnCW("[IN RUN_BLOCKING]: Starting to prepare the boiling of the water")
            delay(10)
            boiler.cancelAndJoin()
            printlnCW("[IN RUN_BLOCKING]: Finished boiling a pan of ${pan.liquid}")
        }
    }

    private suspend fun increaseUntilBoilingPoint(pan: Pan) {
        while (pan.temperature < 100) {
            delay(2)
            pan.increaseTemperature()
            printlnCW("[IN LAUNCH 1]: Increasing pan's temperature to ${pan.temperature}")
        }
    }
}

package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen

import kotlinx.coroutines.*
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Pan
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer.printlnCW

object Stove {
    fun boil(pan: Pan) = runBlocking {
        printlnCW("Starting to prepare the boiling of the water")
        launch {
            printlnCW("[IN LAUNCH]: Starting to boil a pan of ${pan.liquid}")
            increaseUntilBoilingPoint(pan)
            printlnCW("[IN LAUNCH]: Finished boiling a pan of ${pan.liquid}")
        }
        printlnCW("[IN RUN_BLOCKING]: Finished boiling a pan of ${pan.liquid}")
    }

    private suspend fun increaseUntilBoilingPoint(pan: Pan) {
        if (!pan.isBoiling) {
            delay(2)
            printlnCW("[STOVE: IN LAUNCH]: Increasing ${pan.liquid}'s temperature to ${pan.temperature + 10}")
            increaseUntilBoilingPoint(pan.increaseTemperature())
        }
    }
}

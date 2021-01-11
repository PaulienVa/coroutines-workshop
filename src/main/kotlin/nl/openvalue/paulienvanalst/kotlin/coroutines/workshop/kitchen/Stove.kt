package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Pan
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer

object Stove {
    suspend fun boil(pan: Pan) : Boolean {
        coroutineScope { //start main coroutine
            val boiler = launch { // launches a new coroutine in the background but continue
                println("\n")
                Printer.printlnCW("[STOVE: IN LAUNCH]: Starting to boil a pan of ${pan.liquid}")
                increaseUntilBoilingPoint(pan)
                Printer.printlnCW("[STOVE: IN LAUNCH]: Finished boiling a pan of ${pan.liquid}")
                println("\n")
            }
            Printer.printlnCW("[STOVE: IN RUN_BLOCKING]: Starting to prepare the boiling of the water")
            delay(100)
            boiler.cancelAndJoin()
            Printer.printlnCW("[STOVE: IN RUN_BLOCKING]: Finished boiling a pan of ${pan.liquid}")
        }
        return true
    }



    private suspend fun increaseUntilBoilingPoint(pan: Pan) {
        if (!pan.isBoiling) {
            delay(2)
            Printer.printlnCW("[STOVE: IN LAUNCH]: Increasing ${pan.liquid}'s temperature to ${pan.temperature + 10}")
            increaseUntilBoilingPoint(pan.increaseTemperature())
        }
    }
}

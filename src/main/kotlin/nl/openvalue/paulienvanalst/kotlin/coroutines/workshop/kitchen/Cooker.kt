package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen

import kotlinx.coroutines.*

import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils.Pan
import nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils.Printer.printlnCW

object Cooker {
    fun boil(pan: Pan) {
        runBlocking { //start main coroutine
            val job = launch { // launches a new coroutine in the background but continue
                printlnCW("[IN LAUNCH 1]: Starting to boil a pan of ${pan.liquid}")
                while (pan.temperature < 100) {
                    pan.increaseTemperature()
                    printlnCW("[IN LAUNCH 1]: Increasing pan's temperature to ${pan.temperature}")
                }
                printlnCW("[IN LAUNCH 1]: Finished boiling a pan of ${pan.liquid}")
            }
            printlnCW("[IN RUN_BLOCKING]: Starting to prepare the boiling of the water")
            job.join()
            printlnCW("[IN RUN_BLOCKING]: Finished boiling a pan of ${pan.liquid}")
        }
    }
}

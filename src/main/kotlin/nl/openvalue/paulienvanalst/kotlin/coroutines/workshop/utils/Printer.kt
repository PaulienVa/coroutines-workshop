package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.utils

object Printer {
    fun printlnCW(message: String) {
        println("[Thread: id: ${Thread.currentThread().id}, name: ${Thread.currentThread().name}, priority: ${Thread.currentThread().priority}] $message")
    }
}

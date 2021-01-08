package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.runners

object WorkshopRunner {

    fun run() {
        println("Which exercise do you want to execute? (1/2/3)")

        when(readLine()){
            "1" -> Exercise1Runner.run()
            "2" -> Exercise2Runner.run()
            "3" -> Exercise3Runner.run()
            else -> println("No valid exercise... exiting ...")
        }

    }
}

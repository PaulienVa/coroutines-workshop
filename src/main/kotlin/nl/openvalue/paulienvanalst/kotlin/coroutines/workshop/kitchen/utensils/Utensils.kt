package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils

data class Pan(val content: Int, val liquid: String, var temperature: Int) {
    fun increaseTemperature() {
        temperature += 10
    }
}

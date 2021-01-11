package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils

data class Pan(val content: Int, val liquid: Liquid, val temperature: Int) {
    fun increaseTemperature() : Pan = this.copy(temperature = temperature + 10)

    val isBoiling = this.temperature >= liquid.boilingPoint
}

enum class Liquid(val boilingPoint: Int) {
    WATER(100),
    OLIVE_OIL(180)
}

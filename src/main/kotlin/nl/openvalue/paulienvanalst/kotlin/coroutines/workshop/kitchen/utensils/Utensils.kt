package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.kitchen.utensils

data class Pan(val content: Int, val liquid: Liquid, val temperature: Int) {
    fun increaseTemperature(): Pan = this.copy(temperature = temperature + 10)

    val isBoiling = this.temperature >= liquid.boilingPoint
}

data class Bowl(val content: String) {
    companion object {
        fun empty(): Bowl {
            return Bowl("")
        }
    }

    fun addContent(ingredient: String) = this.copy(content = "$content $ingredient")
}

enum class Liquid(val boilingPoint: Int) {
    WATER(100),
    OLIVE_OIL(180)
}

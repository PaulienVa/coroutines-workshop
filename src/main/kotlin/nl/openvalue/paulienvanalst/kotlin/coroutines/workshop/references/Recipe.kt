package nl.openvalue.paulienvanalst.kotlin.coroutines.workshop.references

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(val name: String, val duration: Int, val ingredients: List<String>, val preparation: String)

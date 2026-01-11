package com.joyagogames.almacenrepartoscorrecto.domain.entities

data class Article(
    val id: Int,
    val name: String,
    val units: Int,
    val weightPerUnit: Double
) {
    val totalWeight = units * weightPerUnit
}

package com.joyagogames.almacenrepartoscorrecto.domain.entities

data class Box(
    val number: Int,
    val articles: List<Article> = emptyList()
) {
    val totalWeight = articles.sumOf { it.totalWeight }
}


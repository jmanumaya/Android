package com.joyagogames.spacialstationcorrecta.domain.entities


import com.joyagogames.spacialstationcorrecta.data.models.SupplyItem

data class Module(
    val id: Int,
    val supplies: MutableList<SupplyItem> = mutableListOf()
) {
    fun pesoTotal(): Int = supplies.sumOf { it.pesoTotal }
}

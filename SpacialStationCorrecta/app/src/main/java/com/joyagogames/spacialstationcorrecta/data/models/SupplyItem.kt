package com.joyagogames.spacialstationcorrecta.data.models

data class SupplyItem(
    val tipo: SupplyType,
    val unidades: Int
) {
    val pesoTotal: Int = tipo.peso * unidades
}
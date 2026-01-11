package com.joyagogames.elgarajecorrecto.data.models

data class ParkingSpot(
    val number: Int,
    var plate: String? = null
) {
    val isFree: Boolean
        get() = plate == null
}
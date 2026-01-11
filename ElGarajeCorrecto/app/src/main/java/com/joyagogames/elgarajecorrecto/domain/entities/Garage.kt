package com.joyagogames.elgarajecorrecto.domain.entities

import com.joyagogames.elgarajecorrecto.data.models.ParkingSpot

data class Garage(
    val spots: MutableList<ParkingSpot>
)

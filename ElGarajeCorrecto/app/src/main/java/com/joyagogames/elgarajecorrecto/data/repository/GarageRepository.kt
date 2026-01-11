package com.joyagogames.elgarajecorrecto.data.repository

import com.joyagogames.elgarajecorrecto.data.models.ParkingSpot
import com.joyagogames.elgarajecorrecto.domain.entities.Garage

class GarageRepository {

    private var totalSpots = 10
    private var garage = Garage(
        MutableList(totalSpots) { ParkingSpot(it + 1) }
    )

    fun getGarage(): Garage = garage

    fun setTotalSpots(newTotal: Int) {
        totalSpots = newTotal
        garage = Garage(MutableList(totalSpots) { ParkingSpot(it + 1) })
    }

    fun occupySpot(number: Int, plate: String) {
        garage.spots.first { it.number == number }.plate = plate
    }

    fun freeSpot(number: Int) {
        garage.spots.first { it.number == number }.plate = null
    }
}

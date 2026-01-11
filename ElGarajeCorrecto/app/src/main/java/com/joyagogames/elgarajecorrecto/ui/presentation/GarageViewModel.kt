package com.joyagogames.elgarajecorrecto.ui.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.joyagogames.elgarajecorrecto.data.repository.GarageRepository
import com.joyagogames.elgarajecorrecto.data.models.ParkingSpot

class GarageViewModel : ViewModel() {

    private val repository = GarageRepository()

    var spots by mutableStateOf(repository.getGarage().spots.toList())
        private set

    var selectedSpot by mutableStateOf<ParkingSpot?>(null)

    fun selectSpot(spot: ParkingSpot) {
        selectedSpot = spot
    }

    fun addVehicle(spotNumber: Int, plate: String) {
        repository.occupySpot(spotNumber, plate)
        refresh()
    }

    fun freeSpot(spotNumber: Int) {
        repository.freeSpot(spotNumber)
        refresh()
    }

    fun changeTotalSpots(total: Int) {
        repository.setTotalSpots(total)
        refresh()
    }

    private fun refresh() {
        spots = repository.getGarage().spots.toList()
    }
}
package com.example.appelhostalbien.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appelhostalbien.data.repository.BookingsRepository
import com.example.appelhostalbien.data.repository.RoomsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OwnerViewModel(
    private val roomsRepository: RoomsRepository,
    private val bookingsRepository: BookingsRepository
) : ViewModel() {
    val allRooms = roomsRepository.allRooms.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    val allBookings = bookingsRepository.getAllBookings().stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    fun addRoom(number: String, type: String, price: Double) {
        viewModelScope.launch {
            roomsRepository.addRoom(number, type, price)
        }
    }

    fun makeRoomAvailable(roomId: Long) {
        viewModelScope.launch {
            roomsRepository.updateRoomAvailability(roomId, true)
        }
    }
}

package com.example.appelhostal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appelhostal.data.entities.BookingWithDetails
import com.example.appelhostal.data.entities.RoomEntity
import com.example.appelhostal.data.repository.BookingsRepository
import com.example.appelhostal.data.repository.RoomsRepository
import com.example.appelhostal.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class RoomWithOccupant(
    val room: RoomEntity,
    val currentBooking: BookingWithDetails?
)

class OwnerViewModel(
    private val roomsRepository: RoomsRepository,
    private val bookingsRepository: BookingsRepository
) : ViewModel() {
    
    val allRooms: StateFlow<List<RoomEntity>> = roomsRepository.getAllRooms()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    val allBookings: StateFlow<List<BookingWithDetails>> = bookingsRepository.getAllBookings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    private val _roomsWithOccupants = MutableStateFlow<List<RoomWithOccupant>>(emptyList())
    val roomsWithOccupants: StateFlow<List<RoomWithOccupant>> = _roomsWithOccupants.asStateFlow()
    
    private val _addRoomState = MutableStateFlow<UiState<Long>>(UiState.Idle)
    val addRoomState: StateFlow<UiState<Long>> = _addRoomState.asStateFlow()
    
    private val _releaseRoomState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val releaseRoomState: StateFlow<UiState<Unit>> = _releaseRoomState.asStateFlow()
    
    init {
        loadRoomsWithOccupants()
    }
    
    fun loadRoomsWithOccupants() {
        viewModelScope.launch {
            roomsRepository.getAllRooms().collect { rooms ->
                val roomsWithOccupants = rooms.map { room ->
                    val activeBooking = bookingsRepository.getActiveBookingForRoom(room.id)
                    RoomWithOccupant(room, activeBooking)
                }
                _roomsWithOccupants.value = roomsWithOccupants
            }
        }
    }
    
    fun addRoom(
        name: String,
        description: String,
        capacity: Int,
        pricePerNight: Double,
        imageUrl: String
    ) {
        viewModelScope.launch {
            _addRoomState.value = UiState.Loading
            val result = roomsRepository.addRoom(name, description, capacity, pricePerNight, imageUrl)
            result.fold(
                onSuccess = { id ->
                    _addRoomState.value = UiState.Success(id)
                    loadRoomsWithOccupants()
                },
                onFailure = { error ->
                    _addRoomState.value = UiState.Error(error.message ?: "Error al añadir habitación")
                }
            )
        }
    }
    
    fun releaseRoom(roomId: Long, bookingId: Long) {
        viewModelScope.launch {
            _releaseRoomState.value = UiState.Loading
            try {
                bookingsRepository.finishBooking(bookingId)
                roomsRepository.updateRoomAvailability(roomId, true)
                _releaseRoomState.value = UiState.Success(Unit)
                loadRoomsWithOccupants()
            } catch (e: Exception) {
                _releaseRoomState.value = UiState.Error(e.message ?: "Error al liberar habitación")
            }
        }
    }
    
    fun resetAddRoomState() {
        _addRoomState.value = UiState.Idle
    }
    
    fun resetReleaseRoomState() {
        _releaseRoomState.value = UiState.Idle
    }
}

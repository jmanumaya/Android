package com.example.appelhostal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appelhostal.data.entities.RoomEntity
import com.example.appelhostal.data.repository.RoomsRepository
import com.example.appelhostal.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoomsViewModel(private val roomsRepository: RoomsRepository) : ViewModel() {
    
    private val _roomsState = MutableStateFlow<UiState<List<RoomEntity>>>(UiState.Loading)
    val roomsState: StateFlow<UiState<List<RoomEntity>>> = _roomsState.asStateFlow()
    
    private val _selectedRoom = MutableStateFlow<RoomEntity?>(null)
    val selectedRoom: StateFlow<RoomEntity?> = _selectedRoom.asStateFlow()
    
    init {
        loadRooms()
    }
    
    private fun loadRooms() {
        viewModelScope.launch {
            _roomsState.value = UiState.Loading
            try {
                // Seed rooms if empty
                roomsRepository.seedRoomsIfEmpty()
                
                // Collect rooms
                roomsRepository.getAvailableRooms().collect { rooms ->
                    _roomsState.value = UiState.Success(rooms)
                }
            } catch (e: Exception) {
                _roomsState.value = UiState.Error(e.message ?: "Error al cargar habitaciones")
            }
        }
    }
    
    fun selectRoom(roomId: Long) {
        viewModelScope.launch {
            val room = roomsRepository.getRoomById(roomId)
            _selectedRoom.value = room
        }
    }
    
    fun clearSelectedRoom() {
        _selectedRoom.value = null
    }
    
    fun refreshRooms() {
        loadRooms()
    }
    
    fun updateRoomAvailability(roomId: Long, isAvailable: Boolean) {
        viewModelScope.launch {
            roomsRepository.updateRoomAvailability(roomId, isAvailable)
        }
    }
}

package com.example.appelhostal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appelhostal.data.entities.RoomEntity
import com.example.appelhostal.data.repository.RoomsRepository
import com.example.appelhostal.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
                // Asegurar que las habitaciones estén inicializadas
                roomsRepository.seedRoomsIfEmpty()

                // Recoger habitaciones disponibles
                roomsRepository.getAvailableRooms()
                    .catch { e ->
                        android.util.Log.e("RoomsViewModel", "Error loading rooms", e)
                        _roomsState.value = UiState.Error(e.message ?: "Error al cargar habitaciones")
                    }
                    .collect { rooms ->
                        android.util.Log.d("RoomsViewModel", "Loaded ${rooms.size} rooms")
                        _roomsState.value = UiState.Success(rooms)
                    }
            } catch (e: Exception) {
                android.util.Log.e("RoomsViewModel", "Error in loadRooms", e)
                _roomsState.value = UiState.Error(e.message ?: "Error al cargar habitaciones")
            }
        }
    }

    fun selectRoom(roomId: Long) {
        viewModelScope.launch {
            try {
                val room = roomsRepository.getRoomById(roomId)
                _selectedRoom.value = room
                android.util.Log.d("RoomsViewModel", "Selected room: ${room?.name}")
            } catch (e: Exception) {
                android.util.Log.e("RoomsViewModel", "Error selecting room", e)
            }
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
            try {
                roomsRepository.updateRoomAvailability(roomId, isAvailable)
                android.util.Log.d("RoomsViewModel", "Updated room $roomId availability to $isAvailable")
            } catch (e: Exception) {
                android.util.Log.e("RoomsViewModel", "Error updating room availability", e)
            }
        }
    }
}
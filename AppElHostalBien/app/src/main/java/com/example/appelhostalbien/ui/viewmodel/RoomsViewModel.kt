package com.example.appelhostalbien.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appelhostalbien.data.repository.RoomsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoomsViewModel(private val repository: RoomsRepository) : ViewModel() {
    val availableRooms = repository.availableRooms.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun getRoom(id: Long, onResult: (com.example.appelhostalbien.data.entities.RoomEntity?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getRoomById(id))
        }
    }
}

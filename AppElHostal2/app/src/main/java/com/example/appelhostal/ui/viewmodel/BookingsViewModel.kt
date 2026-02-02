package com.example.appelhostal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appelhostal.data.entities.BookingWithRoom
import com.example.appelhostal.data.repository.BookingsRepository
import com.example.appelhostal.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingsViewModel(private val bookingsRepository: BookingsRepository) : ViewModel() {
    
    private val _bookingsState = MutableStateFlow<UiState<List<BookingWithRoom>>>(UiState.Idle)
    val bookingsState: StateFlow<UiState<List<BookingWithRoom>>> = _bookingsState.asStateFlow()
    
    private val _createBookingState = MutableStateFlow<UiState<Long>>(UiState.Idle)
    val createBookingState: StateFlow<UiState<Long>> = _createBookingState.asStateFlow()
    
    private val _cancelBookingState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val cancelBookingState: StateFlow<UiState<Unit>> = _cancelBookingState.asStateFlow()
    
    fun loadBookings(clientId: Long) {
        viewModelScope.launch {
            _bookingsState.value = UiState.Loading
            try {
                bookingsRepository.getClientBookings(clientId).collect { bookings ->
                    _bookingsState.value = UiState.Success(bookings)
                }
            } catch (e: Exception) {
                _bookingsState.value = UiState.Error(e.message ?: "Error al cargar reservas")
            }
        }
    }
    
    fun createBooking(clientId: Long, roomId: Long, totalPrice: Double) {
        viewModelScope.launch {
            _createBookingState.value = UiState.Loading
            val result = bookingsRepository.createBooking(
                clientId = clientId,
                roomId = roomId,
                totalPrice = totalPrice
            )
            result.fold(
                onSuccess = { bookingId ->
                    _createBookingState.value = UiState.Success(bookingId)
                },
                onFailure = { error ->
                    _createBookingState.value = UiState.Error(error.message ?: "Error al crear reserva")
                }
            )
        }
    }
    
    fun cancelBooking(bookingId: Long) {
        viewModelScope.launch {
            _cancelBookingState.value = UiState.Loading
            val result = bookingsRepository.cancelBooking(bookingId)
            result.fold(
                onSuccess = {
                    _cancelBookingState.value = UiState.Success(Unit)
                },
                onFailure = { error ->
                    _cancelBookingState.value = UiState.Error(error.message ?: "Error al cancelar reserva")
                }
            )
        }
    }
    
    fun resetCreateBookingState() {
        _createBookingState.value = UiState.Idle
    }
    
    fun resetCancelBookingState() {
        _cancelBookingState.value = UiState.Idle
    }
}


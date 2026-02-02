package com.example.appelhostalbien.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appelhostalbien.data.repository.AuthRepository
import com.example.appelhostalbien.data.repository.BookingsRepository
import com.example.appelhostalbien.data.entities.BookingWithRoom
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookingsViewModel(
    private val repository: BookingsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val userBookings: StateFlow<List<BookingWithRoom>> = authRepository.currentUser
        .flatMapLatest { user ->
            if (user != null) {
                repository.getBookingsForClient(user.id)
            } else {
                flowOf(emptyList())
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun createBooking(roomId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = authRepository.currentUser.value
            if (user != null) {
                repository.createBooking(user.id, roomId)
                onSuccess()
            }
        }
    }

    fun cancelBooking(bookingId: Long) {
        viewModelScope.launch {
            repository.cancelBooking(bookingId)
        }
    }
}

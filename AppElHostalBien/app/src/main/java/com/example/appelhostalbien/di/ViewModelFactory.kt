package com.example.appelhostalbien.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appelhostalbien.data.repository.AuthRepository
import com.example.appelhostalbien.data.repository.BookingsRepository
import com.example.appelhostalbien.data.repository.RoomsRepository
import com.example.appelhostalbien.ui.viewmodel.AuthViewModel
import com.example.appelhostalbien.ui.viewmodel.BookingsViewModel
import com.example.appelhostalbien.ui.viewmodel.OwnerViewModel
import com.example.appelhostalbien.ui.viewmodel.RoomsViewModel

class ViewModelFactory(
    private val authRepository: AuthRepository,
    private val roomsRepository: RoomsRepository,
    private val bookingsRepository: BookingsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RoomsViewModel::class.java) -> {
                RoomsViewModel(roomsRepository) as T
            }
            modelClass.isAssignableFrom(BookingsViewModel::class.java) -> {
                BookingsViewModel(bookingsRepository, authRepository) as T
            }
            modelClass.isAssignableFrom(OwnerViewModel::class.java) -> {
                OwnerViewModel(roomsRepository, bookingsRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

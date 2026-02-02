package com.example.appelhostal.di

import com.example.appelhostal.data.db.AppDatabase
import com.example.appelhostal.data.repository.AuthRepository
import com.example.appelhostal.data.repository.BookingsRepository
import com.example.appelhostal.data.repository.RoomsRepository

class AppContainer(database: AppDatabase) {
    val authRepository: AuthRepository by lazy {
        AuthRepository(database.clientDao())
    }
    
    val roomsRepository: RoomsRepository by lazy {
        RoomsRepository(database.roomDao())
    }
    
    val bookingsRepository: BookingsRepository by lazy {
        BookingsRepository(database.bookingDao())
    }
}

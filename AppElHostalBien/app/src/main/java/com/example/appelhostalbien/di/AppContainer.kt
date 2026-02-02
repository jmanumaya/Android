package com.example.appelhostalbien.di

import com.example.appelhostalbien.data.db.AppDatabase
import com.example.appelhostalbien.data.repository.AuthRepository
import com.example.appelhostalbien.data.repository.BookingsRepository
import com.example.appelhostalbien.data.repository.RoomsRepository

class AppContainer(private val database: AppDatabase) {
    val authRepository: AuthRepository by lazy {
        AuthRepository(database.clientDao())
    }

    val roomsRepository: RoomsRepository by lazy {
        RoomsRepository(database.roomDao())
    }

    val bookingsRepository: BookingsRepository by lazy {
        BookingsRepository(database.bookingDao(), database.roomDao())
    }
}

package com.example.appelhostalbien.data.db

import androidx.room.*
import com.example.appelhostalbien.data.entities.BookingEntity
import com.example.appelhostalbien.data.entities.BookingWithClientAndRoom
import com.example.appelhostalbien.data.entities.BookingWithRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Transaction
    @Query("SELECT * FROM bookings WHERE clientId = :clientId")
    fun getBookingsByClient(clientId: Long): Flow<List<BookingWithRoom>>

    @Transaction
    @Query("SELECT * FROM bookings")
    fun getAllBookings(): Flow<List<BookingWithClientAndRoom>>

    @Insert
    suspend fun insertBooking(booking: BookingEntity)

    @Update
    suspend fun updateBooking(booking: BookingEntity)

    @Query("SELECT * FROM bookings WHERE id = :id")
    suspend fun getBookingById(id: Long): BookingEntity?
}

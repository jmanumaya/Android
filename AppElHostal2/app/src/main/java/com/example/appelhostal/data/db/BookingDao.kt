package com.example.appelhostal.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.appelhostal.data.entities.BookingEntity
import com.example.appelhostal.data.entities.BookingWithDetails
import com.example.appelhostal.data.entities.BookingWithRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    
    @Transaction
    @Query("SELECT * FROM bookings WHERE clientId = :clientId ORDER BY id DESC")
    fun getBookingsByClient(clientId: Long): Flow<List<BookingWithRoom>>
    
    @Transaction
    @Query("SELECT * FROM bookings ORDER BY id DESC")
    fun getAllBookings(): Flow<List<BookingWithDetails>>
    
    @Transaction
    @Query("SELECT * FROM bookings WHERE roomId = :roomId AND status = 'ACTIVE' LIMIT 1")
    suspend fun getActiveBookingForRoom(roomId: Long): BookingWithDetails?
    
    @Insert
    suspend fun insertBooking(booking: BookingEntity): Long
    
    @Query("UPDATE bookings SET status = :status WHERE id = :bookingId")
    suspend fun updateBookingStatus(bookingId: Long, status: String)
    
    @Transaction
    @Query("SELECT * FROM bookings WHERE id = :bookingId LIMIT 1")
    suspend fun getBookingWithRoom(bookingId: Long): BookingWithRoom?
}


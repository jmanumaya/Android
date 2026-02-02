package com.example.appelhostal.data.repository

import com.example.appelhostal.data.db.BookingDao
import com.example.appelhostal.data.entities.BookingEntity
import com.example.appelhostal.data.entities.BookingWithDetails
import com.example.appelhostal.data.entities.BookingWithRoom
import kotlinx.coroutines.flow.Flow

class BookingsRepository(private val bookingDao: BookingDao) {
    
    fun getClientBookings(clientId: Long): Flow<List<BookingWithRoom>> {
        return bookingDao.getBookingsByClient(clientId)
    }
    
    fun getAllBookings(): Flow<List<BookingWithDetails>> {
        return bookingDao.getAllBookings()
    }
    
    suspend fun getActiveBookingForRoom(roomId: Long): BookingWithDetails? {
        return bookingDao.getActiveBookingForRoom(roomId)
    }
    
    suspend fun createBooking(
        clientId: Long,
        roomId: Long,
        totalPrice: Double
    ): Result<Long> {
        return try {
            val booking = BookingEntity(
                clientId = clientId,
                roomId = roomId,
                totalPrice = totalPrice,
                status = "ACTIVE"
            )
            val id = bookingDao.insertBooking(booking)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun cancelBooking(bookingId: Long): Result<Unit> {
        return try {
            val booking = bookingDao.getBookingWithRoom(bookingId)
            if (booking == null) {
                Result.failure(Exception("Reserva no encontrada"))
            } else if (booking.booking.status == "CANCELLED") {
                Result.failure(Exception("La reserva ya está cancelada"))
            } else {
                bookingDao.updateBookingStatus(bookingId, "CANCELLED")
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun finishBooking(bookingId: Long): Result<Unit> {
        return try {
            bookingDao.updateBookingStatus(bookingId, "CANCELLED")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getBookingWithRoom(bookingId: Long): BookingWithRoom? {
        return bookingDao.getBookingWithRoom(bookingId)
    }
}



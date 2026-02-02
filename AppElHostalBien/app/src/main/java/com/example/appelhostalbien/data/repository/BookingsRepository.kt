package com.example.appelhostalbien.data.repository

import com.example.appelhostalbien.data.db.BookingDao
import com.example.appelhostalbien.data.db.RoomDao
import com.example.appelhostalbien.data.entities.BookingEntity
import com.example.appelhostalbien.data.entities.BookingWithClientAndRoom
import com.example.appelhostalbien.data.entities.BookingWithRoom
import kotlinx.coroutines.flow.Flow

class BookingsRepository(
    private val bookingDao: BookingDao,
    private val roomDao: RoomDao
) {
    fun getBookingsForClient(clientId: Long): Flow<List<BookingWithRoom>> =
        bookingDao.getBookingsByClient(clientId)

    fun getAllBookings(): Flow<List<BookingWithClientAndRoom>> =
        bookingDao.getAllBookings()

    suspend fun createBooking(clientId: Long, roomId: Long) {
        val room = roomDao.getRoomById(roomId)
        if (room != null && room.isAvailable) {
            bookingDao.insertBooking(BookingEntity(clientId = clientId, roomId = roomId))
            roomDao.updateRoom(room.copy(isAvailable = false))
        }
    }

    suspend fun cancelBooking(bookingId: Long) {
        val booking = bookingDao.getBookingById(bookingId)
        if (booking != null && booking.isActive) {
            bookingDao.updateBooking(booking.copy(isActive = false))
            val room = roomDao.getRoomById(booking.roomId)
            room?.let {
                roomDao.updateRoom(it.copy(isAvailable = true))
            }
        }
    }
}

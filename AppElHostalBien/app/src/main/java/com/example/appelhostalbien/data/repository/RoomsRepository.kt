package com.example.appelhostalbien.data.repository

import com.example.appelhostalbien.data.db.RoomDao
import com.example.appelhostalbien.data.entities.RoomEntity
import kotlinx.coroutines.flow.Flow

class RoomsRepository(private val roomDao: RoomDao) {
    val allRooms: Flow<List<RoomEntity>> = roomDao.getAllRooms()
    val availableRooms: Flow<List<RoomEntity>> = roomDao.getAvailableRooms()

    suspend fun getRoomById(id: Long) = roomDao.getRoomById(id)

    suspend fun addRoom(number: String, type: String, price: Double) {
        roomDao.insertRoom(RoomEntity(number = number, type = type, price = price))
    }

    suspend fun updateRoomAvailability(roomId: Long, available: Boolean) {
        val room = roomDao.getRoomById(roomId)
        room?.let {
            roomDao.updateRoom(it.copy(isAvailable = available))
        }
    }

    suspend fun seedRoomsIfEmpty() {
        if (roomDao.getRoomCount() == 0) {
            val initialRooms = listOf(
                RoomEntity(number = "101", type = "Individual", price = 30.0),
                RoomEntity(number = "102", type = "Doble", price = 50.0),
                RoomEntity(number = "201", type = "Suite", price = 100.0),
                RoomEntity(number = "202", type = "Individual", price = 35.0)
            )
            initialRooms.forEach { roomDao.insertRoom(it) }
        }
    }
}

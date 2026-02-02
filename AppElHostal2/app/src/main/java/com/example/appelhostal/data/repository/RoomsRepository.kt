package com.example.appelhostal.data.repository

import com.example.appelhostal.data.db.RoomDao
import com.example.appelhostal.data.entities.RoomEntity
import kotlinx.coroutines.flow.Flow

class RoomsRepository(private val roomDao: RoomDao) {
    
    fun getAvailableRooms(): Flow<List<RoomEntity>> {
        return roomDao.getAllAvailableRooms()
    }
    
    fun getAllRooms(): Flow<List<RoomEntity>> {
        return roomDao.getAllRooms()
    }
    
    suspend fun getRoomById(id: Long): RoomEntity? {
        return roomDao.getRoomById(id)
    }
    
    suspend fun addRoom(
        name: String,
        description: String,
        capacity: Int,
        pricePerNight: Double,
        imageUrl: String
    ): Result<Long> {
        return try {
            val maxId = roomDao.getMaxRoomId()
            val room = RoomEntity(
                id = maxId + 1,
                name = name,
                description = description,
                capacity = capacity,
                pricePerNight = pricePerNight,
                imageUrl = imageUrl,
                isAvailable = true
            )
            val id = roomDao.insertRoom(room)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun seedRoomsIfEmpty() {
        val count = roomDao.getRoomsCount()
        if (count == 0) {
            val initialRooms = listOf(
                RoomEntity(
                    id = 1,
                    name = "Habitación Individual",
                    description = "Acogedora habitación individual con cama de 90cm, baño privado, TV y WiFi gratuito.",
                    capacity = 1,
                    pricePerNight = 45.0,
                    imageUrl = "single_room",
                    isAvailable = true
                ),
                RoomEntity(
                    id = 2,
                    name = "Habitación Doble",
                    description = "Espaciosa habitación doble con cama de matrimonio, baño privado, minibar, TV y WiFi gratuito.",
                    capacity = 2,
                    pricePerNight = 75.0,
                    imageUrl = "double_room",
                    isAvailable = true
                ),
                RoomEntity(
                    id = 3,
                    name = "Habitación Familiar",
                    description = "Amplia habitación familiar con una cama de matrimonio y dos camas individuales, baño privado, TV y WiFi.",
                    capacity = 4,
                    pricePerNight = 120.0,
                    imageUrl = "family_room",
                    isAvailable = true
                ),
                RoomEntity(
                    id = 4,
                    name = "Suite Premium",
                    description = "Lujosa suite con sala de estar, jacuzzi, cama king size, minibar premium, TV 55\" y WiFi de alta velocidad.",
                    capacity = 2,
                    pricePerNight = 200.0,
                    imageUrl = "suite_room",
                    isAvailable = true
                ),
                RoomEntity(
                    id = 5,
                    name = "Habitación Triple",
                    description = "Habitación con tres camas individuales, ideal para grupos de amigos. Incluye baño privado, TV y WiFi.",
                    capacity = 3,
                    pricePerNight = 95.0,
                    imageUrl = "triple_room",
                    isAvailable = true
                )
            )
            roomDao.insertRooms(initialRooms)
        }
    }
    
    suspend fun updateRoomAvailability(roomId: Long, isAvailable: Boolean) {
        roomDao.updateRoomAvailability(roomId, isAvailable)
    }
}


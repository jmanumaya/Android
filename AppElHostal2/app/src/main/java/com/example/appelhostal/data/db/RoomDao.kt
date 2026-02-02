package com.example.appelhostal.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appelhostal.data.entities.RoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    
    @Query("SELECT * FROM rooms WHERE isAvailable = 1")
    fun getAllAvailableRooms(): Flow<List<RoomEntity>>
    
    @Query("SELECT * FROM rooms ORDER BY id ASC")
    fun getAllRooms(): Flow<List<RoomEntity>>
    
    @Query("SELECT * FROM rooms WHERE id = :id LIMIT 1")
    suspend fun getRoomById(id: Long): RoomEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRooms(rooms: List<RoomEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: RoomEntity): Long
    
    @Query("SELECT COUNT(*) FROM rooms")
    suspend fun getRoomsCount(): Int
    
    @Query("SELECT COALESCE(MAX(id), 0) FROM rooms")
    suspend fun getMaxRoomId(): Long
    
    @Query("UPDATE rooms SET isAvailable = :isAvailable WHERE id = :roomId")
    suspend fun updateRoomAvailability(roomId: Long, isAvailable: Boolean)
}

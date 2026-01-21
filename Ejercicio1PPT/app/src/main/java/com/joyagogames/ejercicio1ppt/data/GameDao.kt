package com.joyagogames.ejercicio1ppt.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game_entity")
    fun getAllGames(): Flow<List<GameEntity>>

    @Insert
    suspend fun addGame(game: GameEntity)
}
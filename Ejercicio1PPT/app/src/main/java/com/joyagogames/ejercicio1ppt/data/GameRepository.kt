package com.joyagogames.ejercicio1ppt.data

import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {

    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    suspend fun addGames(game: GameEntity) {
        gameDao.addGame(game)
    }
}
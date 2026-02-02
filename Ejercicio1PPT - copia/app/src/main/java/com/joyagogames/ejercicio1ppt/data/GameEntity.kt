// com.joyagogames.ejercicio1ppt.data.GameEntity.kt
package com.joyagogames.ejercicio1ppt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.joyagogames.ejercicio1ppt.domain.entities.GameHistory // Importamos el modelo de dominio

@Entity(tableName = "game_entity")
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val rondas: Int,
    var player: String,
    var ia: String,
    var resultado: String,
    var puntosPlayer: Int,
    var puntosIA: Int,
)

fun GameEntity.toDomain() = GameHistory(
    id = id,
    player = player,
    ia = ia,
    resultado = resultado,
    puntosPlayer = puntosPlayer,
    puntosIA = puntosIA
)
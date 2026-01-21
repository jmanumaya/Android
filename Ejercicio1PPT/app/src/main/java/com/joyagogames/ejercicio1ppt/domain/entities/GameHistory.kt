package com.joyagogames.ejercicio1ppt.domain.entities

data class GameHistory(
    val id: Int,
    val player: String,
    val ia: String,
    val resultado: String,
    val puntosPlayer: Int,
    val puntosIA: Int
)
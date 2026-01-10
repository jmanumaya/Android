package com.joyagogames.ejercicio1ppt.domain.entities

import com.joyagogames.ejercicio1ppt.data.models.Jugada
import com.joyagogames.ejercicio1ppt.data.models.Resultado

data class Game(
    var rondaActual: Int,
    val rondas: Int,
    var juegoPlayer: Jugada? = null,
    var juegoIA: Jugada? = null,
    var resultado: Resultado,
    var puntosPlayer: Int,
    var puntosIA: Int,
    var finalizar: Boolean,
)


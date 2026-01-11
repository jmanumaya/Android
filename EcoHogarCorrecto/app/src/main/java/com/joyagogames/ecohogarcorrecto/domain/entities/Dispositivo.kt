package com.joyagogames.ecohogarcorrecto.domain.entities

import com.joyagogames.ecohogarcorrecto.data.models.TipoDispositivo

data class Dispositivo(
    val id: Int,
    var nombre: String,
    var tipo: TipoDispositivo,
    var encendido: Boolean
)

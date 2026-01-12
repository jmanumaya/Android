package com.example.examenoffline.domain.entities

import com.example.examenoffline.data.models.Especie
import com.example.examenoffline.data.models.TipoEstado

data class Mascota(
    val id: Int,
    val name: String,
    val especie: Especie,
    var edad: Int,
    var estado: TipoEstado,
    var duenno: String,
)

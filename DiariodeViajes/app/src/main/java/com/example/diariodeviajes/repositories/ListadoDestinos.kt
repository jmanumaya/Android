package com.example.diariodeviajes.repositories

import com.example.diariodeviajes.R
import com.example.diariodeviajes.entities.Destino

object ListadoDestinos {

    val destinos = mutableListOf<Destino>(
        Destino(
            1,
            "Santiago Bernabéu",
            "España",
            "Famoso estadio del Real Madrid, ubicado en el corazón de Madrid.",
            R.drawable.santiagobernabeu
        ),
        Destino(
            2,
            "Coliseo Romano",
            "Italia",
            "Antiguo anfiteatro en Roma, símbolo del Imperio Romano.",
            R.drawable.santiagobernabeu
        ),
        Destino(
            3,
            "Torre Eiffel",
            "Francia",
            "Icono de París y una de las estructuras más reconocidas del mundo.",
            R.drawable.torreeiffel
        ),
        Destino(
            4,
            "Big Ben",
            "Reino Unido",
            "Histórico reloj londinense junto al Parlamento británico.",
            R.drawable.big
        ),
        Destino(
            5,
            "Estadio Maracaná",
            "Brasil",
            "Uno de los estadios más grandes y emblemáticos del fútbol mundial.",
            R.drawable.santiagobernabeu
        ),
        Destino(
            6,
            "Monte Fuji",
            "Japón",
            "Majestuoso volcán sagrado y símbolo nacional del Japón.",
            R.drawable.montefuji
        )
    )


    fun getAllDestinos() : List<Destino>{
        return destinos.toList()
    }

    fun getDestinoById(id: Int) : Destino? {
        return destinos.find { it.id == id }
    }

    fun insert(destino:Destino)
    {
        destinos.add(destino)
    }

}
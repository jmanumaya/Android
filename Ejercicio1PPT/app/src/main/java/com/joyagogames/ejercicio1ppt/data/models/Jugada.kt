package com.joyagogames.ejercicio1ppt.data.models

import com.joyagogames.ejercicio1ppt.R

enum class Jugada(val imageRes: Int) {
    PIEDRA(R.drawable.piedra),
    PAPEL(R.drawable.papel),
    TIJERA(R.drawable.tijera);

    fun ganaA(otra: Jugada?): Boolean {
        return (this == PIEDRA && otra == TIJERA) ||
                (this == PAPEL && otra == PIEDRA) ||
                (this == TIJERA && otra == PAPEL)
    }
}
package com.example.simulacroexamen1.ui.presentation

import androidx.lifecycle.ViewModel
import com.example.simulacroexamen1.domain.entities.Player
import kotlin.random.Random

class PlayerViewModel : ViewModel() {

    var player = Player("Jose Manuel", 10, -1)
    var numeroGanador = -1
    var apuestaActual = 0
    var gana = false

    fun eligeNum(num: Int){
        player = player.copy(num = num)
    }

    fun newApuesta(apuesta: Int){
        apuestaActual = apuesta
        compruebaApuesta()
    }

    fun compruebaApuesta(){
        numeroGanador = Random.nextInt(1, 10)

        if (player.num == numeroGanador){
            gana = true
            player = player.copy("Jose Manuel", player.saldo * 2, player.num)
        } else{
            gana = false
            player = player.copy("Jose Manuel", player.saldo - apuestaActual, player.num)
        }
    }

    fun reiniciaJuego(){
        player = player.copy(saldo = 10, num = -1)
    }
}
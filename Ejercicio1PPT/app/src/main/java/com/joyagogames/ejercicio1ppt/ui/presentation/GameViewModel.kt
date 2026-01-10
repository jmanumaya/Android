package com.joyagogames.ejercicio1ppt.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.joyagogames.ejercicio1ppt.data.models.Jugada
import com.joyagogames.ejercicio1ppt.data.models.Resultado
import com.joyagogames.ejercicio1ppt.domain.entities.Game
import com.joyagogames.ejercicio1ppt.domain.entities.Player
import kotlin.random.Random

class GameViewModel : ViewModel() {

    val posiblesRondas = listOf(3, 5)

    val namesIA = listOf("ROBO-CRACK", "ROBOTICO-PRO", "DAVID-COMPOSE")

    var playerJugada by mutableStateOf<Jugada?>(null)
        private set

    var aiJugada by mutableStateOf<Jugada?>(null)
        private set

    val player = Player(1, "")
    val ia = Player(2, "")

    var game = Game(
        0,
        posiblesRondas[Random.nextInt(posiblesRondas.size)],
        null,
        null,
        Resultado.SIN_RESULTADO,
        0,
        0,
        false
    )


    fun chooseNameIA():String{
        return namesIA[Random.nextInt(namesIA.size)]
    }

    fun setName(name: String){
        player.name = name
        ia.name = chooseNameIA()
    }

    fun setJugada(jugada: Jugada){
        val iaRandom = Jugada.values().random()

        playerJugada = jugada
        aiJugada = iaRandom

        game.juegoPlayer = jugada
        game.juegoIA = iaRandom

        comprobarGanador()
        if(game.resultado != Resultado.EMPATE){
            game.rondaActual++;
        }
        if(game.rondaActual == game.rondas){
            game.finalizar = true
        }
    }

    fun comprobarGanador() {
        val player = game.juegoPlayer ?: return
        val ia = game.juegoIA ?: return

        game.resultado = when {
            player == ia -> Resultado.EMPATE
            player.ganaA(ia) -> {
                game.puntosPlayer++
                Resultado.VICTORIA}
            else -> {
                game.puntosIA++
                Resultado.DERROTA}
        }
    }

    fun resetGame(){
        game = Game(
            0,
            posiblesRondas[Random.nextInt(posiblesRondas.size)],
            null,
            null,
            Resultado.SIN_RESULTADO,
            0,
            0,
            false
        )
        playerJugada = null
        aiJugada = null
    }

    fun newGame(){
        resetGame()
        ia.name = chooseNameIA()
    }
}
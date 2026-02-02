package com.joyagogames.ejercicio1ppt.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joyagogames.ejercicio1ppt.data.GameEntity // La entidad de Room
import com.joyagogames.ejercicio1ppt.data.GameRepository
import com.joyagogames.ejercicio1ppt.data.models.Jugada
import com.joyagogames.ejercicio1ppt.data.models.Resultado
import com.joyagogames.ejercicio1ppt.domain.entities.GameHistory
import com.joyagogames.ejercicio1ppt.domain.entities.Game as GameDomain // Alias para evitar conflicto
import com.joyagogames.ejercicio1ppt.domain.entities.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random
import com.joyagogames.ejercicio1ppt.data.toDomain

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    val posiblesRondas = listOf(3, 5)
    val namesIA = listOf("ROBO-CRACK", "ROBOTICO-PRO", "DAVID-COMPOSE")

    var playerJugada by mutableStateOf<Jugada?>(null)
        private set

    var aiJugada by mutableStateOf<Jugada?>(null)
        private set

    val player = Player(1, "")
    val ia = Player(2, "")

    val listaGames: StateFlow<List<GameHistory>> = repository.getAllGames()
        .map { entities: List<GameEntity> ->
            entities.map { it.toDomain() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var game = GameDomain(
        0,
        posiblesRondas[Random.nextInt(posiblesRondas.size)],
        null,
        null,
        Resultado.SIN_RESULTADO,
        0,
        0,
        false
    )

    private fun guardarPartidaEnHistorial() {
        val resultadoFinal = if (game.puntosPlayer > game.puntosIA) "VICTORIA"
        else if (game.puntosIA > game.puntosPlayer) "DERROTA"
        else "EMPATE"

        viewModelScope.launch {
            val entity = GameEntity(
                rondas = game.rondas,
                player = player.name,
                ia = ia.name,
                resultado = resultadoFinal, // Usamos el valor calculado aquí arriba
                puntosPlayer = game.puntosPlayer,
                puntosIA = game.puntosIA
            )
            repository.addGames(entity)
        }
    }

    fun chooseNameIA(): String = namesIA[Random.nextInt(namesIA.size)]

    fun setName(name: String) {
        player.name = name
        ia.name = chooseNameIA()
    }

    fun setJugada(jugada: Jugada) {
        if (game.finalizar) return

        val iaRandom = Jugada.values().random()
        playerJugada = jugada
        aiJugada = iaRandom

        game.juegoPlayer = jugada
        game.juegoIA = iaRandom

        comprobarGanador()

        if (game.resultado != Resultado.EMPATE) {
            game.rondaActual++
        }

        if (game.rondaActual >= game.rondas) {
            game.finalizar = true
            guardarPartidaEnHistorial()
        }
    }

    fun comprobarGanador() {
        val p = game.juegoPlayer ?: return
        val i = game.juegoIA ?: return

        game.resultado = when {
            p == i -> Resultado.EMPATE
            p.ganaA(i) -> {
                game.puntosPlayer++
                Resultado.VICTORIA
            }
            else -> {
                game.puntosIA++
                Resultado.DERROTA
            }
        }
    }

    fun resetGame() {
        game = GameDomain(
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

    fun newGame() {
        resetGame()
        ia.name = chooseNameIA()
    }
}
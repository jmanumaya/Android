package com.joyagogames.simulacroexamengpt.ui.presentation

import androidx.lifecycle.ViewModel

data class Pregunta(
    val enunciado: String,
    val opciones: List<String>,
    val correcta: Int
)

class QuizViewModel : ViewModel() {

    var nombreJugador: String = ""
    var numPreguntas: Int = 3

    private val preguntasBase = listOf(
        Pregunta(
            "¿Cuál es la capital de Francia?",
            listOf("Londres", "Madrid", "París", "Berlín"),
            2
        ),
        Pregunta(
            "¿Cuántos planetas hay en el sistema solar?",
            listOf("7", "8", "9", "10"),
            1
        ),
        Pregunta(
            "¿En qué año comenzó la Segunda Guerra Mundial?",
            listOf("1939", "1945", "1914", "1920"),
            0
        ),
        Pregunta(
            "¿Cuál es el río más largo del mundo?",
            listOf("Amazonas", "Nilo", "Yangtsé", "Danubio"),
            0
        ),
        Pregunta(
            "¿Cuál es el metal más abundante en la corteza terrestre?",
            listOf("Hierro", "Aluminio", "Cobre", "Zinc"),
            1
        )
    )

    lateinit var preguntas: List<Pregunta>
    private val respuestasUsuario = mutableListOf<Int>()

    var indicePregunta = 0
        private set

    fun iniciarQuiz() {
        preguntas = preguntasBase.shuffled().take(numPreguntas)
        respuestasUsuario.clear()
        indicePregunta = 0
    }

    fun responder(indice: Int) {
        respuestasUsuario.add(indice)
    }

    fun avanzarPregunta(): Boolean {
        return if (indicePregunta < preguntas.size - 1) {
            indicePregunta++
            true
        } else false
    }

    fun obtenerResultado(): List<Triple<Pregunta, Int, Int?>> {
        return preguntas.mapIndexed { index, pregunta ->
            Triple(pregunta, pregunta.correcta, respuestasUsuario.getOrNull(index))
        }
    }

    fun reiniciarTodo() {
        nombreJugador = ""
        numPreguntas = 3
        respuestasUsuario.clear()
        indicePregunta = 0
    }
}

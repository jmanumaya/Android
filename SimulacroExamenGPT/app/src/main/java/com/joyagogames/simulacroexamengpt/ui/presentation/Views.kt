package com.joyagogames.simulacroexamengpt.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MainQuiz(navController: NavController, vm: QuizViewModel) {

    var nombre by remember { mutableStateOf("") }
    var preguntasSeleccionadas by remember { mutableStateOf(3) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Configuración del Quiz", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del jugador") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Text("Número de preguntas")

        listOf(3, 5, 10).forEach { n ->
            Row {
                RadioButton(
                    selected = preguntasSeleccionadas == n,
                    onClick = { preguntasSeleccionadas = n }
                )
                Text(text = "$n preguntas", modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank()) {
                    vm.nombreJugador = nombre
                    vm.numPreguntas = preguntasSeleccionadas
                    vm.iniciarQuiz()
                    navController.navigate("preguntasQuiz")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Comenzar Quiz")
        }
    }
}

@Composable
fun Preguntas(navController: NavController, vm: QuizViewModel) {

    val preguntaActual = vm.preguntas[vm.indicePregunta]

    var seleccion by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text("Jugador: ${vm.nombreJugador}")
        Text("Pregunta ${vm.indicePregunta + 1} de ${vm.preguntas.size}")

        Spacer(Modifier.height(24.dp))

        Text(text = preguntaActual.enunciado, style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(24.dp))

        preguntaActual.opciones.forEachIndexed { index, opcion ->
            Button(
                onClick = { seleccion = index },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (seleccion == index) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(opcion)
            }
        }

        Spacer(Modifier.height(32.dp))

        if (seleccion != null) {
            Button(
                onClick = {
                    vm.responder(seleccion!!)
                    val avanzar = vm.avanzarPregunta()

                    if (!avanzar) {
                        navController.navigate("resultados")
                    } else {
                        navController.navigate("preguntasQuiz") {
                            popUpTo("preguntasQuiz") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (vm.indicePregunta == vm.preguntas.size - 1) "Finalizar" else "Siguiente")
            }
        }
    }
}

@Composable
fun Resultados(navController: NavController, vm: QuizViewModel) {

    val resultados = vm.obtenerResultado()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text("Resultados de ${vm.nombreJugador}", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        val aciertos = resultados.count { it.second == it.third }
        Text("Has acertado $aciertos de ${resultados.size} preguntas")

        Spacer(Modifier.height(24.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(resultados) { (pregunta, correcta, usuario) ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(pregunta.enunciado)

                    Text("Correcta: ${pregunta.opciones[correcta]}")

                    Text(
                        "Tu respuesta: ${usuario?.let { pregunta.opciones[it] } ?: "Sin responder"}",
                        color = if (usuario == correcta) Color.Green else Color.Red
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("mainQuiz") {
                    popUpTo("mainQuiz") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver a jugar")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                vm.reiniciarTodo()
                navController.navigate("mainQuiz") {
                    popUpTo("mainQuiz") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Reiniciar")
        }
    }
}
package com.example.simulacroexamen1.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EligeNumero(navController: NavController, playerViewModel: PlayerViewModel){

    val player = playerViewModel.player

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Jugador: " + player.nombre,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            val numeros = listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9)
            )

            Text("Elige tu numero de la apuesta",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            numeros.forEach { fila ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    fila.forEach { num ->
                        OutlinedButton(
                            onClick = {
                                playerViewModel.eligeNum(num)
                                navController.navigate("apuesta")
                            },
                            modifier = Modifier
                                .width(100.dp)
                                .height(90.dp)
                                .padding(4.dp)
                                .padding(4.dp)
                        ) {
                            Text(num.toString(),
                                fontSize = 26.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Apuesta(navController: NavController, playerViewModel: PlayerViewModel) {

    var apuesta by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val apuestaInt = apuesta.toIntOrNull() ?: -1
    val player = playerViewModel.player
    val saldoInsuficiente = apuestaInt > player.saldo
    val apuestaInvalida = apuestaInt <= 0 || saldoInsuficiente

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Jugador: " + player.nombre,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Saldo: " + player.saldo,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Numero: " + player.num,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = apuesta,
            onValueChange = { newValue ->
                apuesta = newValue
                showError = false
            },
            label = { Text("Cantidad de Apuesta") },
            placeholder = { Text("cantidad a apostar") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = showError || apuestaInvalida,
            modifier = Modifier.fillMaxWidth()
        )

        if (showError || apuestaInvalida) {
            Text(
                text = "La apuesta debe ser mayor que 0 y menor que " + player.saldo,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        if (showError || saldoInsuficiente) {
            Text(
                text = "No tienes suficiente saldo",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (!apuestaInvalida && !saldoInsuficiente) {
                    playerViewModel.newApuesta(apuestaInt)
                    navController.navigate("resultadoApuesta")
                } else {
                    showError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Apostar")
        }

        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Cancelar")
        }
    }
}

@Composable
fun Comprobacion(navController: NavController, playerViewModel: PlayerViewModel) {

    val player = playerViewModel.player
    var numeroGanador = playerViewModel.numeroGanador
    var resultado = playerViewModel.gana

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "El Número Ganador es...",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            numeroGanador.toString(),
            fontSize = 24.sp,
        )
        Text(
            text = if (resultado) "Apuesta Ganada" else "Apuesta Perdida",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = if (resultado) Color.Green else Color.Red
        )
        Text(
            "Nuevo Saldo = " + player.saldo,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )

        if (playerViewModel.player.saldo > 0) {

            Button(
                onClick = {
                    navController.navigate("eligeNum")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Seguir Jugando")
            }
        } else {
            Text(
                text = "Te has quedado sin saldo y no puedes seguir jugando",
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Red
            )
        }

        OutlinedButton(
            onClick = {
                playerViewModel.reiniciaJuego()
                navController.navigate("eligeNum") },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Empezar de Nuevo")
        }
    }
}

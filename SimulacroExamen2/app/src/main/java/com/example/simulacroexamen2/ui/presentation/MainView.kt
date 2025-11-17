package com.example.simulacroexamen2.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NumeroPersonas(navController : NavController, gastosViewModel: GastosViewModel){

    var personas by remember { mutableIntStateOf(0) }
    var totalPagar by remember { mutableIntStateOf(0) }
    var showError by remember { mutableStateOf(false) }

    val personasValidas = personas > 0
    val totalPagarValido = totalPagar > 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = if (personas == 0) "" else personas.toString(),
            onValueChange = { newValue ->
                personas = newValue.toIntOrNull() ?: 0
                showError = false
            },
            label = { Text("Personas") },
            placeholder = { Text("Número de personas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = showError && !personasValidas,
            modifier = Modifier.fillMaxWidth()
        )

        if (showError && !personasValidas) {
            Text(
                text = "Debe haber al menos 1 persona",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = if (totalPagar == 0) "" else totalPagar.toString(),
            onValueChange = { newValue ->
                totalPagar = newValue.toIntOrNull() ?: 0
                showError = false
            },
            label = { Text("Total a pagar") },
            placeholder = { Text("Introduce el total en €") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = showError && !totalPagarValido,
            modifier = Modifier.fillMaxWidth()
        )

        if (showError && !totalPagarValido) {
            Text(
                text = "El total debe ser mayor que 0",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (personasValidas && totalPagarValido) {
                    gastosViewModel.insertNumeroPersonasTotalPagar(personas, totalPagar)
                    navController.navigate("nombres")
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Calcular")
        }
    }
}

@Composable
fun NombresPersonas(navController: NavController, gastosViewModel: GastosViewModel) {

    val totalPersonas = gastosViewModel.numeroPersonas
    var nombres by remember { mutableStateOf(List(totalPersonas) { "" }) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "Introduce el nombre de cada persona",
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(nombres.size) { index ->
                val nombre = nombres[index]
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { newValue ->
                        nombres = nombres.toMutableList().also {
                            it[index] = newValue
                        }
                        showError = false
                    },
                    label = { Text("Persona ${index + 1}") },
                    placeholder = { Text("Nombre") },
                    isError = showError && nombre.isBlank(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = {
                        val todoValido = nombres.all { it.isNotBlank() }

                        if (!todoValido) {
                            showError = true
                        } else {
                            gastosViewModel.setNombres(nombres)
                            navController.navigate("resultadoPorPersona")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(50.dp)
                ) {
                    Text("Continuar")
                }
            }
        }
    }
}

@Composable
fun ResultadoTotalPorPersona(navController: NavController, gastosViewModel: GastosViewModel) {

    val lista = gastosViewModel.listPersonas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Resumen de pagos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(lista.size) { index ->
                val persona = lista[index]
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = persona.nombre,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "" + persona.paga,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("main")
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Volver al inicio")
        }
    }
}
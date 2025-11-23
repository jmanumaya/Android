package com.tu.paquete // <-- cámbialo por el tuyo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentesDeApoyo() {

    // Estados principales
    var text by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }
    var radio by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // 🔹 TEXT
        Text(text = "Ejemplo de Text")

        Spacer(Modifier.height(12.dp))

        // 🔹 TEXTFIELD
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Introduzca texto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // 🔹 BUTTON
        Button(
            onClick = { /* Acción */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pulsar")
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 CHECKBOX
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked, onCheckedChange = { checked = it })
            Text("Aceptar condición")
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 RADIO BUTTON
        Text("Selecciona una opción:")
        listOf("Opción A", "Opción B", "Opción C").forEachIndexed { index, cadena ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = radio == index,
                    onClick = { radio = index }
                )
                Text(cadena)
            }
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 SWITCH
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(checked = checked, onCheckedChange = { checked = it })
            Text("Interruptor")
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 SLIDER
        Text("Valor del slider: $sliderValue")
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it }
        )

        Spacer(Modifier.height(12.dp))

        // 🔹 ICONO
        Icon(Icons.Default.Favorite, contentDescription = "Icono ejemplo")

        Spacer(Modifier.height(12.dp))

        // 🔹 IMAGEN (ejemplo)
        // Image(
        //     painter = painterResource(id = R.drawable.tuImagen),
        //     contentDescription = "Imagen"
        // )

        Spacer(Modifier.height(12.dp))

        // 🔹 CARD
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Esto es una Card")
                Text("Sirve para agrupar contenido con estilo.")
            }
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 DROPDOWN MENU
        Box {
            Button(onClick = { expanded = true }) {
                Text("Abrir menú")
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("Elemento 1") },
                    onClick = { expanded = false }
                )
                DropdownMenuItem(
                    text = { Text("Elemento 2") },
                    onClick = { expanded = false }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 PROGRESS INDICATOR
        CircularProgressIndicator()

        Spacer(Modifier.height(12.dp))

        // 🔹 LAZY COLUMN
        Text("Listado con LazyColumn:")
        LazyColumn(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        ) {
            items(5) { index ->
                Text("Elemento $index", modifier = Modifier.padding(8.dp))
            }
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 ROW
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Fila 1")
            Text("Fila 2")
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 COLUMN
        Column {
            Text("Columna 1")
            Text("Columna 2")
        }

        Spacer(Modifier.height(12.dp))

        // 🔹 SCAFFOLD
        Text("Ejemplo de Scaffold (plantilla):")
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Título") })
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text("Contenido dentro de Scaffold")
            }
        }

        Spacer(Modifier.height(12.dp))
    }
}

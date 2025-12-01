package com.example.practicatareasbd.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practicatareasbd.MainActivity
import com.example.practicatareasbd.data.Task

@Composable
fun MainView(navController: NavController, tareasViewModel: TareasViewModel) {

    var titulo by remember { mutableStateOf("") }
    val tareas by tareasViewModel.listadoTareas.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título de la tarea") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (titulo.isNotBlank()) {
                    tareasViewModel.addTarea(titulo)
                    titulo = ""
                }
            }) {
                Text("Añadir")
            }
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn {
            items(tareas) { tarea ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = tarea.terminada,
                        onCheckedChange = { tareasViewModel.toggleTarea(tarea.id) }
                    )
                    Text(
                        text = tarea.titulo,
                        textDecoration = if (tarea.terminada) TextDecoration.LineThrough else TextDecoration.None
                    )
                }
            }
        }
    }
}

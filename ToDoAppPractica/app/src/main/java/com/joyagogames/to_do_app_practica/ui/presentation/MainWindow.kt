package com.joyagogames.to_do_app_practica.ui.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.joyagogames.to_do_app_practica.domain.entities.Task
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.OutlinedButton


@Composable
fun TaskListScreenWithFAB(navController: NavController, taskViewModel: TaskViewModel) {
    val tasks = taskViewModel.tasks

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("agregar") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
            }
        }
    ) { padding ->
        TaskListView(
            tasks = tasks,
            navController = navController,
            taskViewModel = taskViewModel,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun TaskListView(
    tasks: List<Task>,
    navController: NavController,
    taskViewModel: TaskViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tasks) { task ->
            TaskCard(task, taskViewModel, navController)
        }
    }
}

@Composable
fun TaskCard(task: Task, taskViewModel: TaskViewModel, navController: NavController) {
    var isChecked by remember { mutableStateOf(task.finished) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(task.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        taskViewModel.updateTask(task, finished = it)
                    }
                )
                Text(if (isChecked) "Completada" else "Pendiente")
            }

            Row {
                Button(onClick = { navController.navigate("ver/${task.id}") }) {
                    Text("Ver")
                }
                Button(onClick = { taskViewModel.deleteTask(task) }) {
                    Text("Eliminar")
                }
            }
        }
    }
}

@Composable
fun TaskDetailView(task: Task, taskViewModel: TaskViewModel, navController: NavController) {
    var description by remember { mutableStateOf(task.description) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = task.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Estado de completado (solo lectura)
        Text(
            text = if (task.finished) "✓ Completada" else "○ Pendiente",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = if (task.finished) Color.Green else Color.Gray
        )

        // Campo editable para la descripción
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            maxLines = 8
        )

        // Botón para guardar cambios
        Button(
            onClick = {
                taskViewModel.updateTask(task, description = description)
                navController.popBackStack() // vuelve a la lista
            },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Guardar Cambios")
        }

        // Botón para volver sin guardar
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Volver")
        }
    }
}

@Composable
fun AddTaskView(taskViewModel: TaskViewModel, navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Nueva Tarea",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                showError = false
            },
            label = { Text("Título") },
            placeholder = { Text("Escribe el título de la tarea") },
            isError = showError && title.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        if (showError && title.isBlank()) {
            Text(
                text = "El título es obligatorio",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            placeholder = { Text("Describe los detalles de la tarea") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            minLines = 5,
            maxLines = 10
        )

        Spacer(modifier = Modifier.weight(1f))

        // Guardar tarea
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    taskViewModel.addTask(title.trim(), description.trim())
                    navController.popBackStack()
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Guardar Tarea")
        }

        // Cancelar
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Cancelar")
        }
    }
}

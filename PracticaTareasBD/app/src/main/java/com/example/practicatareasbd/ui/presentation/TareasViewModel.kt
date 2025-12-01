package com.example.practicatareasbd.ui.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicatareasbd.MainActivity
import com.example.practicatareasbd.data.ListadoTareasRepository
import com.example.practicatareasbd.data.Task
import com.example.practicatareasbd.domain.Tarea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TareasViewModel : ViewModel() {

    // Usamos un MutableStateFlow para la lista
    private val _listadoTareas = MutableStateFlow<List<Task>>(emptyList())
    val listadoTareas: StateFlow<List<Task>> = _listadoTareas.asStateFlow()

    init {
        // Al iniciarse el ViewModel, cargamos las tareas automáticamente
        obtenerTareas()
    }

    fun obtenerTareas() {
        viewModelScope.launch {
            val tareas = MainActivity.database.taskDao().getAllTasks()
            _listadoTareas.value = tareas
        }
    }

    fun addTarea(titulo: String) {
        viewModelScope.launch {
            val nuevaTarea = Task(titulo = titulo)
            MainActivity.database.taskDao().addTask(nuevaTarea)
            obtenerTareas()
        }
    }

    fun toggleTarea(id: Int) {
        viewModelScope.launch {
            val tareaActual = _listadoTareas.value.find { it.id == id }
            tareaActual?.let {
                val tareaActualizada = it.copy(terminada = !it.terminada)
                MainActivity.database.taskDao().updateTarea(tareaActualizada)
                obtenerTareas()
            }
        }
    }
}

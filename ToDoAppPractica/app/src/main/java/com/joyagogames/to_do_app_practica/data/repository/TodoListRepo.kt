package com.joyagogames.to_do_app_practica.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.joyagogames.to_do_app_practica.domain.entities.Task

object TodoListRepo {
    private val todoList = mutableStateListOf(
        Task(1, "Realizar Tarea de Kotlin", "La tarea consiste en realizar una app aplicando Jetpack Compose"),
        Task(2, "Realizar Tarea de Java", "La tarea consiste en realizar una app aplicando Spring"),
        Task(3, "Realizar Tarea de PHP", "La tarea consiste en realizar una app aplicando Laravel")
    )

    fun getAllTasks() = todoList

    fun getTaskById(id: Int) = todoList.first { it.id == id }

    fun addTask(task: Task) {
        todoList.add(task)
    }

    fun updateTask(updatedTask: Task) {
        val index = todoList.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            todoList[index] = updatedTask
        }
    }

    fun deleteTask(taskId: Int) {
        todoList.removeIf { it.id == taskId }
    }
}

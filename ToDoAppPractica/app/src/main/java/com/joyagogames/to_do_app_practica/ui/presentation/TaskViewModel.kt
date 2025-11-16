package com.joyagogames.to_do_app_practica.ui.presentation

import androidx.lifecycle.ViewModel
import com.joyagogames.to_do_app_practica.data.repository.TodoListRepo
import com.joyagogames.to_do_app_practica.domain.entities.Task

class TaskViewModel : ViewModel() {

    val tasks = TodoListRepo.getAllTasks()

    fun addTask(title: String, description: String) {
        val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
        val task = Task(newId, title, description)
        TodoListRepo.addTask(task)
    }

    fun updateTask(task: Task, finished: Boolean? = null, description: String? = null) {
        val updatedTask = task.copy(
            finished = finished ?: task.finished,
            description = description ?: task.description
        )
        TodoListRepo.updateTask(updatedTask)
    }

    fun deleteTask(task: Task) {
        TodoListRepo.deleteTask(task.id)
    }

    fun getTaskById(id: Int): Task = TodoListRepo.getTaskById(id)
}

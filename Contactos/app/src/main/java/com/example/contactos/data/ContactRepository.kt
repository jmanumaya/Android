package com.example.contactos.data

object ContactRepository {

    private var listContactos = mutableListOf(
        Contacto(1, "Hacer Deberes", false)
    )

    fun getAllTasks() = listTareas.toList()

    fun addTask(tarea: Tarea) {
        listTareas.add(tarea)
    }

    fun toggleTask(id: Int) {
        val index = listTareas.indexOfFirst { it.id == id }
        if (index != -1) {
            val tarea = listTareas[index]
            listTareas[index] = tarea.copy(terminada = !tarea.terminada)
        }
    }

}
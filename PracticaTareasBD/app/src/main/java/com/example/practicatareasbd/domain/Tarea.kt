package com.example.practicatareasbd.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Tarea(
    val id: Int,
    val titulo: String,
    val terminada: Boolean = false
)

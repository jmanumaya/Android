package com.example.practicatareasbd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_entity")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val apellidos: String,
    val telefono: String,
    val imagenReferencia: String
)
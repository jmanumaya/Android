package com.example.practicatareasbd.domain

data class Contact(
    val id: Int = 0,
    val nombre: String,
    val apellidos: String,
    val telefono: String,
    val imagenReferencia: String
)
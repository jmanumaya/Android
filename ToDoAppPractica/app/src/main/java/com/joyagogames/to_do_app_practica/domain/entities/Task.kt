package com.joyagogames.to_do_app_practica.domain.entities

data class Task(
    val id: Int,
    val title: String,
    val description: String = "",
    val finished: Boolean = false
)

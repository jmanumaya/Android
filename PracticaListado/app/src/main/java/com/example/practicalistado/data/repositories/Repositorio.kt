package com.example.practicalistado.data.repositories

import com.example.practicalistado.domain.entities.Contacto

object Repositorio {

    val lista = mutableListOf(
        Contacto(1, "Juan García", "611123456", "hombre"),
        Contacto(2, "María López", "678456123", "mujer"),
        Contacto(3, "Raúl Cimas", "644789456", "hombre"),
        Contacto(4, "Pedro Montes", "693882147", "hombre"),
        Contacto(5, "Ana Morantes", "622314785", "mujer"),
        Contacto(6, "Lucía Fernández", "655987321", "mujer"),
        Contacto(7, "Carlos Romero", "699852741", "hombre"),
        Contacto(8, "Sofía Navarro", "670112233", "mujer"),
        Contacto(9, "Miguel Torres", "688998877", "hombre"),
        Contacto(10, "Elena Ruiz", "633445566", "mujer"),
        Contacto(11, "Javier Martín", "677889900", "hombre"),
        Contacto(12, "Laura Sánchez", "644221133", "mujer"),
        Contacto(13, "Diego Ortega", "611778899", "hombre"),
        Contacto(14, "Isabel Ramos", "600445522", "mujer"),
        Contacto(15, "Fernando Gil", "690334455", "hombre")
    )

    fun getAllContacts(): List<Contacto> {
        return lista
    }

    fun insertContact(contacto:Contacto) {
        lista.add(contacto)
    }
}
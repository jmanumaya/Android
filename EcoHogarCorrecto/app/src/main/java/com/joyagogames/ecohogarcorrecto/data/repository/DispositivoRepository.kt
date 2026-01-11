package com.joyagogames.ecohogarcorrecto.data.repository

import com.joyagogames.ecohogarcorrecto.data.models.TipoDispositivo
import com.joyagogames.ecohogarcorrecto.domain.entities.Dispositivo

class DispositivoRepository {

    private val dispositivos = mutableListOf(
        Dispositivo(1, "Luz Salón", TipoDispositivo.LUZ, true),
        Dispositivo(2, "Sensor Puerta", TipoDispositivo.SENSOR, false),
        Dispositivo(3, "Riego Jardín", TipoDispositivo.RIEGO, true),
        Dispositivo(4, "Luz Cocina", TipoDispositivo.LUZ, false)
    )

    private var nextId = 5

    fun getDispositivos(): List<Dispositivo> = dispositivos.toList()

    fun getDispositivoById(id: Int): Dispositivo? = dispositivos.find { it.id == id }

    fun addDispositivo(nombre: String, tipo: TipoDispositivo, encendido: Boolean) {
        val nuevoDispositivo = Dispositivo(nextId++, nombre, tipo, encendido)
        dispositivos.add(nuevoDispositivo)
    }

    fun updateDispositivo(id: Int, encendido: Boolean) {
        dispositivos.find { it.id == id }?.encendido = encendido
    }

    fun apagarTodos() {
        dispositivos.forEach { it.encendido = false }
    }
}
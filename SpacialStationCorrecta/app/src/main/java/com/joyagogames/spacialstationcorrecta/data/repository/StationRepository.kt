package com.joyagogames.spacialstationcorrecta.data.repository

import com.joyagogames.spacialstationcorrecta.domain.entities.Module

class StationRepository {

    private val modules = MutableList(8) { Module(it + 1) }

    fun getModules(): List<Module> = modules

    fun getModule(id: Int): Module = modules.first { it.id == id }

    fun clearModule(id: Int) {
        getModule(id).supplies.clear()
    }
}
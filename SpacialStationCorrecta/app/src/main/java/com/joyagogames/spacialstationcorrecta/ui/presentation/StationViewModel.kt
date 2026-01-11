package com.joyagogames.spacialstationcorrecta.ui.presentation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.joyagogames.spacialstationcorrecta.data.models.SupplyItem
import com.joyagogames.spacialstationcorrecta.data.models.SupplyType
import com.joyagogames.spacialstationcorrecta.data.repository.StationRepository
import com.joyagogames.spacialstationcorrecta.domain.entities.Module

class StationViewModel : ViewModel() {

    private val repository = StationRepository()

    var modules by mutableStateOf(repository.getModules())
        private set

    var selectedModule by mutableStateOf<Module?>(null)

    fun addSupply(moduleId: Int, item: SupplyItem): Boolean {
        val module = repository.getModule(moduleId)
        val nuevoPeso = module.pesoTotal() + item.pesoTotal

        if (nuevoPeso > 500) return false

        module.supplies.add(item)
        refresh()
        return true
    }

    fun selectModule(id: Int) {
        selectedModule = repository.getModule(id)
    }

    fun sendModule(id: Int) {
        repository.clearModule(id)
        refresh()
    }

    private fun refresh() {
        modules = repository.getModules().toList()
    }
}
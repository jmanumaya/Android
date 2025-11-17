package com.example.simulacroexamen2.ui.presentation

import androidx.lifecycle.ViewModel
import com.example.simulacroexamen2.domain.entities.Persona

class GastosViewModel : ViewModel() {

    var listPersonas = mutableListOf<Persona>()
    private var id = 0

    var numeroPersonas = 0
    var totalPagar = 0
    var porPersona = 0.0

    fun insertNumeroPersonasTotalPagar(num: Int, total: Int) {
        numeroPersonas = num
        totalPagar = total
    }

    fun setNombres(nombres: List<String>) {
        listPersonas.clear()
        id = 0

        for (name in nombres) {
            id++
            listPersonas.add(Persona(id, name, 0.0))
        }
        calculaPagar()
    }

    fun calculaPagar() {
        porPersona = (totalPagar.toDouble() / numeroPersonas)

        listPersonas = listPersonas.map { persona ->
            persona.copy(paga = porPersona)
        }.toMutableList()
    }
}
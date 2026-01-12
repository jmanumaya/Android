package com.example.examenoffline.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.examenoffline.data.repository.ListadoRepository
import com.example.examenoffline.domain.entities.Mascota
import com.example.examenoffline.data.models.Especie


class MascotasViewModel : ViewModel() {

    private val repository = ListadoRepository()

    var mascotas by mutableStateOf(repository.getMascotas())
        private set

    fun updateMascotas(){
        mascotas = repository.getMascotas()
    }

    fun registrarAcogida(mascotaId: Int?, nombre: String){
        repository.asignarAcogida(mascotaId, nombre)
        updateMascotas()
    }

    fun adoptar(mascotaId: Int?, nombre: String){
        repository.asignarAdopción(mascotaId, nombre)
        updateMascotas()
    }

    fun addMascota(nombre: String, especie: Especie, edad: Int){
        repository.newMascota(nombre, especie, edad)
        updateMascotas()
    }
}
package com.example.examenoffline.data.repository

import android.nfc.Tag
import android.util.Log
import com.example.examenoffline.domain.entities.Mascota
import com.example.examenoffline.data.models.Especie
import com.example.examenoffline.data.models.TipoEstado
import org.jetbrains.annotations.Debug

class ListadoRepository {

    private var mascotas = mutableListOf(
        Mascota(1, "Mini", Especie.GATO, 5, TipoEstado.BUSCANDO_CASA, ""),
        Mascota(2, "Mila", Especie.PERRO, 14, TipoEstado.BUSCANDO_CASA, ""),
        Mascota(3, "Mayita", Especie.OTROS, 20, TipoEstado.BUSCANDO_CASA, ""),
        Mascota(4, "Manza", Especie.PERRO, 51, TipoEstado.BUSCANDO_CASA, ""),
    )

    private var nextId = 5

    fun asignarAcogida(id: Int?, nameDuenno: String){
        Log.d(":::TAG","${id}")
        Log.d(":::TAG","${nameDuenno}")
        for (a in mascotas){
            if (a.id == id){
                a.duenno = nameDuenno
                a.estado = TipoEstado.EN_ACOGIDA
                Log.d(":::TAG","${a}")
            }
        }

    }

    fun asignarAdopción(id: Int?, nameDuenno: String){
        for (a in mascotas){
            if (a.id == id){
                a.duenno = nameDuenno
                a.estado = TipoEstado.ADOPTADO
            }
        }
    }

    fun finalizarAdopción(id: Int?){
        for (a in mascotas){
            if (a.id == id){
                a.duenno = ""
                a.estado = TipoEstado.BUSCANDO_CASA
            }
        }
    }

    fun getMascotas(): List<Mascota> = mascotas.toList()

    fun newMascota(nombre: String, especie: Especie, edad: Int){
        mascotas.add(Mascota(nextId, nombre, especie, edad, TipoEstado.BUSCANDO_CASA, ""))
        nextId++;
    }
}
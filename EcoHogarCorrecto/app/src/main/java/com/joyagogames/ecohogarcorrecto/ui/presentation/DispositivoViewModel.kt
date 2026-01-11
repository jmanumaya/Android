package com.joyagogames.ecohogarcorrecto.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.joyagogames.ecohogarcorrecto.data.repository.DispositivoRepository
import com.joyagogames.ecohogarcorrecto.data.models.FiltroDispositivo
import com.joyagogames.ecohogarcorrecto.data.models.TipoDispositivo
import com.joyagogames.ecohogarcorrecto.domain.entities.Dispositivo

class DispositivoViewModel : ViewModel() {

    private val repository = DispositivoRepository()

    var filtroActual by mutableStateOf(FiltroDispositivo.TODO)
        private set

    var dispositivosFiltrados by mutableStateOf(repository.getDispositivos())
        private set

    fun getDispositivos(): List<Dispositivo> {
        return when (filtroActual) {
            FiltroDispositivo.TODO -> repository.getDispositivos()
            FiltroDispositivo.LUZ -> repository.getDispositivos().filter { it.tipo == TipoDispositivo.LUZ }
            FiltroDispositivo.SENSOR -> repository.getDispositivos().filter { it.tipo == TipoDispositivo.SENSOR }
            FiltroDispositivo.RIEGO -> repository.getDispositivos().filter { it.tipo == TipoDispositivo.RIEGO }
            FiltroDispositivo.OTRO -> repository.getDispositivos().filter { it.tipo == TipoDispositivo.OTRO }
        }
    }

    fun setFiltro(filtro: FiltroDispositivo) {
        filtroActual = filtro
        actualizarLista()
    }

    fun agregarDispositivo(nombre: String, tipo: TipoDispositivo, encendido: Boolean) {
        repository.addDispositivo(nombre, tipo, encendido)
        actualizarLista()
    }

    fun getDispositivoById(id: Int): Dispositivo? {
        return repository.getDispositivoById(id)
    }

    fun toggleDispositivo(id: Int, encendido: Boolean) {
        repository.updateDispositivo(id, encendido)
        actualizarLista()
    }

    fun modoAhorro() {
        repository.apagarTodos()
        filtroActual = FiltroDispositivo.TODO
        actualizarLista()
    }

    private fun actualizarLista() {
        dispositivosFiltrados = getDispositivos()
    }
}
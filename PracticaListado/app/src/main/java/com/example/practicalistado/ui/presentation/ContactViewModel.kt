package com.example.practicalistado.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicalistado.MainActivity
import com.example.practicalistado.data.repositories.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel: ViewModel() {

    // Cambiamos List<Task> por List<Contacto>
    private val _listadoContactos = MutableStateFlow<List<Contact>>(emptyList())
    val listadoContactos: StateFlow<List<Contact>> = _listadoContactos.asStateFlow()

    init {
        obtenerContactos()
    }

    // Renombrado de obtenerTareas a obtenerContactos
    fun obtenerContactos() {
        viewModelScope.launch {
            // Asumiendo que tu DAO se llama contactDao() y el método getAllContacts()
            val contactos = MainActivity.database.contactDao().getAllContacts()
            _listadoContactos.value = contactos
        }
    }

    // Adaptado para recibir el objeto Contacto que creaste en el formulario
    fun addContact(nuevoContacto: Contact) {
        viewModelScope.launch {
            MainActivity.database.contactDao().addContact(nuevoContacto)
            obtenerContactos() // Recargamos la lista
        }
    }
}
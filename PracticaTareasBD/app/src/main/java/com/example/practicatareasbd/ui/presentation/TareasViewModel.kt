package com.example.practicatareasbd.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicatareasbd.data.Contact
import com.example.practicatareasbd.data.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactosViewModel : ViewModel() {

    private val _listaContactos = MutableStateFlow<List<Contact>>(emptyList())
    val listaContactos: StateFlow<List<Contact>> = _listaContactos.asStateFlow()

    init {
        obtenerContactos()
    }

    fun obtenerContactos() {
        viewModelScope.launch {
            val contactos = ContactRepository.getAllContacts()
            _listaContactos.value = contactos
        }
    }

    fun agregarContacto(nombre: String, apellidos: String, telefono: String, imagenRef: String) {
        viewModelScope.launch {
            val nuevoContacto = Contact(
                nombre = nombre,
                apellidos = apellidos,
                telefono = telefono,
                imagenReferencia = imagenRef
            )
            ContactRepository.addContact(nuevoContacto)

            obtenerContactos()
        }
    }
}
package com.example.appelhostalbien.data.repository

import com.example.appelhostalbien.data.db.ClientDao
import com.example.appelhostalbien.data.entities.ClientEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepository(private val clientDao: ClientDao) {
    private val _currentUser = MutableStateFlow<ClientEntity?>(null)
    val currentUser: StateFlow<ClientEntity?> = _currentUser.asStateFlow()

    suspend fun login(email: String, password: String): Boolean {
        val client = clientDao.getClientByEmail(email)
        return if (client != null && client.password == password) {
            _currentUser.value = client
            true
        } else {
            false
        }
    }

    suspend fun register(name: String, email: String, password: String): Boolean {
        return try {
            val newClient = ClientEntity(name = name, email = email, password = password)
            val id = clientDao.insertClient(newClient)
            _currentUser.value = newClient.copy(id = id)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun logout() {
        _currentUser.value = null
    }
}

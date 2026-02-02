package com.example.appelhostal.data.repository

import com.example.appelhostal.data.db.ClientDao
import com.example.appelhostal.data.entities.ClientEntity

class AuthRepository(private val clientDao: ClientDao) {
    
    suspend fun login(email: String, password: String): ClientEntity? {
        return clientDao.login(email, password)
    }
    
    suspend fun register(name: String, email: String, password: String): Result<ClientEntity> {
        return try {
            val existingClient = clientDao.getClientByEmail(email)
            if (existingClient != null) {
                Result.failure(Exception("El email ya está registrado"))
            } else {
                val client = ClientEntity(
                    name = name,
                    email = email,
                    password = password
                )
                val id = clientDao.insertClient(client)
                Result.success(client.copy(id = id))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getClient(id: Long): ClientEntity? {
        return clientDao.getClientById(id)
    }
}

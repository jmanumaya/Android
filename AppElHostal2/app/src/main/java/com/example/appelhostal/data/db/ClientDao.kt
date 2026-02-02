package com.example.appelhostal.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appelhostal.data.entities.ClientEntity

@Dao
interface ClientDao {
    
    @Query("SELECT * FROM clients WHERE email = :email LIMIT 1")
    suspend fun getClientByEmail(email: String): ClientEntity?
    
    @Query("SELECT * FROM clients WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): ClientEntity?
    
    @Query("SELECT * FROM clients WHERE id = :id LIMIT 1")
    suspend fun getClientById(id: Long): ClientEntity?
    
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertClient(client: ClientEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOwner(owner: ClientEntity): Long
}

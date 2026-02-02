package com.example.appelhostalbien.data.db

import androidx.room.*
import com.example.appelhostalbien.data.entities.ClientEntity

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients WHERE email = :email LIMIT 1")
    suspend fun getClientByEmail(email: String): ClientEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertClient(client: ClientEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwner(client: ClientEntity)

    @Query("SELECT * FROM clients WHERE id = :id")
    suspend fun getClientById(id: Long): ClientEntity?
}

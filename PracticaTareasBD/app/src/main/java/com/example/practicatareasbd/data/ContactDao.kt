package com.example.practicatareasbd.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_entity")
    suspend fun getAllContacts(): List<Contact>

    @Insert
    suspend fun addContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)
}
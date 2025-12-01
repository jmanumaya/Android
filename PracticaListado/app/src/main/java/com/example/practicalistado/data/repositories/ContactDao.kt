package com.example.practicalistado.data.repositories

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface ContactDao {

    @Query("SELECT * FROM contact_entity")
    suspend fun getAllContacts(): List< Contact>

    @Insert
    suspend fun addContact(contact : Contact): Long

    @Query("SELECT * FROM contact_entity where id like :id")
    suspend fun getContactById(id: Int): Contact

    @Update
    suspend fun updateContact(contact: Contact): Int

    @Delete
    suspend fun deleteContact(contact: Contact): Int
}
package com.example.practicatareasbd.data

import com.example.practicatareasbd.MainActivity

object ContactRepository {

    suspend fun getAllContacts(): List<Contact> {
        return MainActivity.database.contactDao().getAllContacts()
    }

    suspend fun addContact(contact: Contact) {
        MainActivity.database.contactDao().addContact(contact)
    }
}
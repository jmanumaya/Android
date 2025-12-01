package com.example.practicalistado.data.repositories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_entity")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name:String,
    val phoneNumber: String,
    val genre:String = "",
)

package com.example.appelhostalbien.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val number: String,
    val type: String,
    val price: Double,
    val isAvailable: Boolean = true
)

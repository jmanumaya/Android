package com.example.appelhostal.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val capacity: Int,
    val pricePerNight: Double,
    val imageUrl: String,
    val isAvailable: Boolean = true
)

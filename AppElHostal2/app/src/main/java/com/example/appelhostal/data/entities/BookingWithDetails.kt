package com.example.appelhostal.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class BookingWithDetails(
    @Embedded val booking: BookingEntity,
    @Relation(
        parentColumn = "roomId",
        entityColumn = "id"
    )
    val room: RoomEntity,
    @Relation(
        parentColumn = "clientId",
        entityColumn = "id"
    )
    val client: ClientEntity
)

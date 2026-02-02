package com.example.appelhostal.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ClientWithBookings(
    @Embedded val client: ClientEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "clientId"
    )
    val bookings: List<BookingEntity>
)

data class BookingWithRoom(
    @Embedded val booking: BookingEntity,
    @Relation(
        parentColumn = "roomId",
        entityColumn = "id"
    )
    val room: RoomEntity
)

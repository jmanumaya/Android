package com.example.appelhostalbien.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appelhostalbien.data.entities.BookingWithRoom

@Composable
fun BookingCard(bookingWithRoom: BookingWithRoom, onCancel: () -> Unit) {
    val booking = bookingWithRoom.booking
    val room = bookingWithRoom.room

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Reserva #${booking.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Habitación ${room.number} (${room.type})")
            Spacer(modifier = Modifier.height(8.dp))
            if (booking.isActive) {
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Cancelar Reserva")
                }
            } else {
                Text(text = "Cancelada/Finalizada", color = Color.Gray)
            }
        }
    }
}

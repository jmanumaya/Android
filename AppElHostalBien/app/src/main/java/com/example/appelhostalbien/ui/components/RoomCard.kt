package com.example.appelhostalbien.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appelhostalbien.data.entities.RoomEntity

@Composable
fun RoomCard(room: RoomEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Habitación ${room.number}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Tipo: ${room.type}")
            Text(text = "Precio: ${room.price}€")
            Text(
                text = if (room.isAvailable) "Disponible" else "Ocupada",
                color = if (room.isAvailable) Color(0xFF4CAF50) else Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

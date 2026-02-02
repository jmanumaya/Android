package com.example.appelhostalbien.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appelhostalbien.ui.viewmodel.OwnerViewModel

@Composable
fun BookingHistoryScreen(navController: NavController, ownerViewModel: OwnerViewModel) {
    val bookings by ownerViewModel.allBookings.collectAsState()

    Scaffold(

    ) { padding ->
    Text("Historial de Reservas")
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(bookings) { item ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Reserva #${item.booking.id}", style = MaterialTheme.typography.titleMedium)
                        Text("Cliente: ${item.client.name} (${item.client.email})")
                        Text("Habitación: ${item.room.number} (${item.room.type})")
                        Text("Estado: ${if (item.booking.isActive) "ACTIVA" else "CANCELADA/FINALIZADA"}")
                    }
                }
            }
        }
    }
}

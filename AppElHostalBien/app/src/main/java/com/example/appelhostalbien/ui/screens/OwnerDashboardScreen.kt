package com.example.appelhostalbien.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appelhostalbien.ui.viewmodel.AuthViewModel
import com.example.appelhostalbien.ui.viewmodel.OwnerViewModel

@Composable
fun OwnerDashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    ownerViewModel: OwnerViewModel
) {
    val rooms by ownerViewModel.allRooms.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_room") }) {
                Text("+")
            }
        }
    ) { padding ->
        Text("Panel del Dueño")
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Row(modifier = Modifier.padding(16.dp)) {
                Button(onClick = { navController.navigate("booking_history") }) {
                    Text("Ver Historial de Reservas")
                }
            }
            
            Text("Estado de Habitaciones:", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
            
            LazyColumn {
                items(rooms) { room ->
                    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Habitación ${room.number}")
                                Text(if (room.isAvailable) "DISPONIBLE" else "OCUPADA")
                            }
                            if (!room.isAvailable) {
                                Button(onClick = { ownerViewModel.makeRoomAvailable(room.id) }) {
                                    Text("Liberar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.example.appelhostalbien.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appelhostalbien.data.entities.RoomEntity
import com.example.appelhostalbien.ui.viewmodel.AuthViewModel
import com.example.appelhostalbien.ui.viewmodel.BookingsViewModel
import com.example.appelhostalbien.ui.viewmodel.RoomsViewModel

@Composable
fun RoomDetailScreen(
    navController: NavController,
    roomId: Long,
    authViewModel: AuthViewModel,
    roomsViewModel: RoomsViewModel,
    bookingsViewModel: BookingsViewModel
) {
    var room by remember { mutableStateOf<RoomEntity?>(null) }
    val user by authViewModel.currentUser.collectAsState()

    LaunchedEffect(roomId) {
        roomsViewModel.getRoom(roomId) { room = it }
    }

    Scaffold(

    ) { padding ->
        Text("Detalle de Habitación")
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            room?.let { r ->
                Text(text = "Habitación ${r.number}", style = MaterialTheme.typography.headlineMedium)
                Text(text = "Tipo: ${r.type}")
                Text(text = "Precio: ${r.price}€ noche")
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = {
                        if (user == null) {
                            navController.navigate("login")
                        } else {
                            bookingsViewModel.createBooking(r.id) {
                                navController.navigate("bookings")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (user == null) "Logueate para reservar" else "Reservar Ahora")
                }
            } ?: Text("Cargando...")
        }
    }
}

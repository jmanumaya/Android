package com.example.appelhostalbien.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appelhostalbien.ui.components.HeaderUserBar
import com.example.appelhostalbien.ui.components.RoomCard
import com.example.appelhostalbien.ui.viewmodel.AuthViewModel
import com.example.appelhostalbien.ui.viewmodel.RoomsViewModel

@Composable
fun RoomsScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    roomsViewModel: RoomsViewModel
) {
    val user by authViewModel.currentUser.collectAsState()
    val rooms by roomsViewModel.availableRooms.collectAsState()

    Scaffold(
        topBar = {
            HeaderUserBar(
                user = user,
                onLogin = { navController.navigate("login") },
                onRegister = { navController.navigate("register") },
                onLogout = { authViewModel.logout() },
                onBookings = { navController.navigate("bookings") },
                onOwnerDashboard = { navController.navigate("owner_dashboard") }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (rooms.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "NO VACANCY",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(rooms) { room ->
                        RoomCard(room = room) {
                            navController.navigate("room/${room.id}")
                        }
                    }
                }
            }
        }
    }
}

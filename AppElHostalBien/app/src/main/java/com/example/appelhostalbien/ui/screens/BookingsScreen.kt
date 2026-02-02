package com.example.appelhostalbien.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appelhostalbien.ui.components.BookingCard
import com.example.appelhostalbien.ui.viewmodel.AuthViewModel
import com.example.appelhostalbien.ui.viewmodel.BookingsViewModel

@Composable
fun BookingsScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    bookingsViewModel: BookingsViewModel
) {
    val bookings by bookingsViewModel.userBookings.collectAsState()

    Scaffold(

    ) { padding ->
        Text("Mis Reservas")
        if (bookings.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No tienes reservas activas")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(bookings) { booking ->
                    BookingCard(bookingWithRoom = booking) {
                        bookingsViewModel.cancelBooking(booking.booking.id)
                    }
                }
            }
        }
    }
}

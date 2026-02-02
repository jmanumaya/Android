package com.example.appelhostal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appelhostal.ui.components.BookingCard
import com.example.appelhostal.ui.viewmodel.AuthViewModel
import com.example.appelhostal.ui.viewmodel.BookingsViewModel
import com.example.appelhostal.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingsScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    bookingsViewModel: BookingsViewModel
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val bookingsState by bookingsViewModel.bookingsState.collectAsState()
    val cancelBookingState by bookingsViewModel.cancelBookingState.collectAsState()
    
    val snackbarHostState = remember { SnackbarHostState() }
    var showCancelDialog by remember { mutableStateOf(false) }
    var bookingToCancel by remember { mutableLongStateOf(0L) }
    
    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            bookingsViewModel.loadBookings(user.id)
        }
    }
    
    LaunchedEffect(cancelBookingState) {
        when (val state = cancelBookingState) {
            is UiState.Success -> {
                snackbarHostState.showSnackbar("Reserva cancelada correctamente")
                bookingsViewModel.resetCancelBookingState()
            }
            is UiState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                bookingsViewModel.resetCancelBookingState()
            }
            else -> {}
        }
    }
    
    if (currentUser == null) {
        LaunchedEffect(Unit) {
            navController.navigate("login") {
                popUpTo("rooms") { inclusive = false }
            }
        }
        return
    }
    
    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancelar Reserva") },
            text = { Text("¿Estás seguro de que quieres cancelar esta reserva? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        bookingsViewModel.cancelBooking(bookingToCancel)
                        showCancelDialog = false
                    }
                ) {
                    Text("Cancelar Reserva", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("Volver")
                }
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Reservas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = bookingsState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                is UiState.Success -> {
                    val bookings = state.data
                    if (bookings.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "📋",
                                style = MaterialTheme.typography.displayLarge
                            )
                            Text(
                                text = "Sin Reservas",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Aún no tienes ninguna reserva. ¡Explora nuestras habitaciones!",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(bookings) { bookingWithRoom ->
                                BookingCard(
                                    bookingWithRoom = bookingWithRoom,
                                    onCancelClick = {
                                        bookingToCancel = bookingWithRoom.booking.id
                                        showCancelDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
                
                is UiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                else -> {}
            }
        }
    }
}

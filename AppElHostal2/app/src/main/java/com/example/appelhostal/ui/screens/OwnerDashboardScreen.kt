package com.example.appelhostal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appelhostal.ui.viewmodel.AuthViewModel
import com.example.appelhostal.ui.viewmodel.OwnerViewModel
import com.example.appelhostal.ui.viewmodel.RoomWithOccupant
import com.example.appelhostal.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerDashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    ownerViewModel: OwnerViewModel
) {
    val roomsWithOccupants by ownerViewModel.roomsWithOccupants.collectAsState()
    val releaseRoomState by ownerViewModel.releaseRoomState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    
    var showReleaseDialog by remember { mutableStateOf(false) }
    var selectedRoom by remember { mutableStateOf<RoomWithOccupant?>(null) }
    
    val occupiedRooms = roomsWithOccupants.count { it.currentBooking != null && !it.room.isAvailable }
    val availableRooms = roomsWithOccupants.count { it.room.isAvailable }
    
    LaunchedEffect(releaseRoomState) {
        if (releaseRoomState is UiState.Success) {
            showReleaseDialog = false
            ownerViewModel.resetReleaseRoomState()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administración", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = {
                        authViewModel.logout()
                        navController.navigate("rooms") { popUpTo(0) { inclusive = true } }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_room") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir habitación")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Summary Cards
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SummaryCard(Modifier.weight(1f), "Disponibles", availableRooms.toString(), Color(0xFF4CAF50))
                    SummaryCard(Modifier.weight(1f), "Ocupadas", occupiedRooms.toString(), Color(0xFFFF5722))
                    SummaryCard(Modifier.weight(1f), "Total", roomsWithOccupants.size.toString(), Color(0xFF2196F3))
                }
            }
            
            // History Button
            item {
                OutlinedButton(onClick = { navController.navigate("booking_history") }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Info, null, Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Ver Historial de Reservas")
                }
            }
            
            // Section Header
            item { Text("Estado de Habitaciones", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
            
            // Room Cards
            items(roomsWithOccupants) { roomWithOccupant ->
                RoomStatusCard(
                    roomWithOccupant = roomWithOccupant,
                    onReleaseClick = {
                        selectedRoom = roomWithOccupant
                        showReleaseDialog = true
                    }
                )
            }
        }
    }
    
    // Release Confirmation Dialog
    if (showReleaseDialog && selectedRoom != null) {
        AlertDialog(
            onDismissRequest = { showReleaseDialog = false },
            title = { Text("Liberar Habitación") },
            text = { Text("¿Liberar \"${selectedRoom?.room?.name}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        selectedRoom?.let { room ->
                            room.currentBooking?.booking?.id?.let { bookingId ->
                                ownerViewModel.releaseRoom(room.room.id, bookingId)
                            }
                        }
                    },
                    enabled = releaseRoomState !is UiState.Loading
                ) {
                    if (releaseRoomState is UiState.Loading) {
                        CircularProgressIndicator(Modifier.size(16.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Liberar")
                    }
                }
            },
            dismissButton = { TextButton(onClick = { showReleaseDialog = false }) { Text("Cancelar") } }
        )
    }
}

@Composable
private fun SummaryCard(modifier: Modifier, title: String, value: String, color: Color) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f))) {
        Column(Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = color)
            Text(title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }
    }
}

@Composable
private fun RoomStatusCard(roomWithOccupant: RoomWithOccupant, onReleaseClick: () -> Unit) {
    val room = roomWithOccupant.room
    val booking = roomWithOccupant.currentBooking
    val isOccupied = booking != null && !room.isAvailable
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (isOccupied) Color(0xFFFFEBEE) else Color(0xFFE8F5E9))
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(room.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Capacidad: ${room.capacity} | ${room.pricePerNight}€", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
                Surface(shape = RoundedCornerShape(16.dp), color = if (isOccupied) Color(0xFFFF5722) else Color(0xFF4CAF50)) {
                    Text(
                        text = if (isOccupied) "OCUPADA" else "DISPONIBLE",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            if (isOccupied && booking != null) {
                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))
                
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, null, Modifier.size(20.dp), MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text(booking.client.name, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                    Button(onClick = onReleaseClick, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                        Text("Liberar")
                    }
                }
            }
        }
    }
}

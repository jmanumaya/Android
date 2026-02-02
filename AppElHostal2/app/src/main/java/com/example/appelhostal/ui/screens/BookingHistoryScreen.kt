package com.example.appelhostal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appelhostal.data.entities.BookingWithDetails
import com.example.appelhostal.ui.viewmodel.OwnerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(
    navController: NavController,
    ownerViewModel: OwnerViewModel
) {
    val allBookings by ownerViewModel.allBookings.collectAsState()

    
    // Group bookings by status
    val activeBookings = allBookings.filter { it.booking.status == "ACTIVE" }
    val finishedBookings = allBookings.filter { it.booking.status == "FINISHED" }
    val cancelledBookings = allBookings.filter { it.booking.status == "CANCELLED" }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Reservas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (allBookings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No hay reservas registradas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Summary
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StatusChip(
                            modifier = Modifier.weight(1f),
                            label = "Activas",
                            count = activeBookings.size,
                            color = Color(0xFF2196F3)
                        )
                        StatusChip(
                            modifier = Modifier.weight(1f),
                            label = "Finalizadas",
                            count = finishedBookings.size,
                            color = Color(0xFF4CAF50)
                        )
                        StatusChip(
                            modifier = Modifier.weight(1f),
                            label = "Canceladas",
                            count = cancelledBookings.size,
                            color = Color(0xFFFF5722)
                        )
                    }
                }
                
                // Active Bookings Section
                if (activeBookings.isNotEmpty()) {
                    item {
                        Text(
                            text = "Reservas Activas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    items(activeBookings) { booking ->
                        BookingHistoryCard(booking = booking)
                    }
                }
                
                // Finished Bookings Section
                if (finishedBookings.isNotEmpty()) {
                    item {
                        Text(
                            text = "Reservas Finalizadas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    items(finishedBookings) { booking ->
                        BookingHistoryCard(booking = booking)
                    }
                }
                
                // Cancelled Bookings Section
                if (cancelledBookings.isNotEmpty()) {
                    item {
                        Text(
                            text = "Reservas Canceladas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    items(cancelledBookings) { booking ->
                        BookingHistoryCard(booking = booking)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusChip(
    modifier: Modifier = Modifier,
    label: String,
    count: Int,
    color: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun BookingHistoryCard(
    booking: BookingWithDetails
) {
    val statusColor = when (booking.booking.status) {
        "ACTIVE" -> Color(0xFF2196F3)
        "FINISHED" -> Color(0xFF4CAF50)
        "CANCELLED" -> Color(0xFFFF5722)
        else -> Color.Gray
    }
    
    val statusText = when (booking.booking.status) {
        "ACTIVE" -> "Activa"
        "FINISHED" -> "Finalizada"
        "CANCELLED" -> "Cancelada"
        else -> booking.booking.status
    }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = booking.room.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = statusColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = statusText,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${booking.client.name} (${booking.client.email})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            

            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Total: ${booking.booking.totalPrice}€",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

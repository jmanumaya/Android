package com.example.appelhostalbien.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appelhostalbien.data.entities.ClientEntity

@Composable
fun HeaderUserBar(
    user: ClientEntity?,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onLogout: () -> Unit,
    onBookings: () -> Unit,
    onOwnerDashboard: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (user == null) {
                Text("JetPack Stay Rooms")
                Row {
                    TextButton(onClick = onLogin) { Text("Login") }
                    TextButton(onClick = onRegister) { Text("Registro") }
                }
            } else {
                Column {
                    Text("Hola, ${user.name}")
                    if (user.isOwner) {
                        Text("Modo Administrador", style = MaterialTheme.typography.labelSmall)
                    }
                }
                Row {
                    if (user.isOwner) {
                        TextButton(onClick = onOwnerDashboard) { Text("Panel") }
                    } else {
                        TextButton(onClick = onBookings) { Text("Mis Reservas") }
                    }
                    TextButton(onClick = onLogout) { Text("Logout") }
                }
            }
        }
    }
}

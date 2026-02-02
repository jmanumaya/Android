package com.example.appelhostal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HeaderUserBar(
    isLoggedIn: Boolean,
    userName: String?,
    onLoginClick: () -> Unit,
    onBookingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoggedIn && userName != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(userName.first().uppercase(), color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(userName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            }
            Row {
                Button(onClick = onBookingsClick) { Text("Reservas") }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(onClick = onLogoutClick) { Text("Salir") }
            }
        } else {
            Column {
                Text("El Hostal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Tu hogar lejos de casa", style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = onLoginClick) { Text("Login") }
        }
    }
}

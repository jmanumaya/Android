package com.example.practicatareasbd.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// --- HELPER PARA IMÁGENES ---
// Mapa de imágenes disponibles para seleccionar
val availableImages = mapOf(
    "face" to Icons.Default.Face,
    "person" to Icons.Default.Person,
    "star" to Icons.Default.Star,
    "account" to Icons.Default.AccountCircle,
    "call" to Icons.Default.Call
)

fun getIconByName(name: String): ImageVector {
    return availableImages[name] ?: Icons.Default.Person
}

// --- PANTALLA 1: LISTA DE CONTACTOS ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(navController: NavController, viewModel: ContactosViewModel) {
    val contactos by viewModel.listaContactos.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("form") }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Contacto")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Mis Contactos", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(contactos) { contacto ->
                    ContactItem(contacto)
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: com.example.practicatareasbd.data.Contact) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto (Icono)
            Icon(
                imageVector = getIconByName(contact.imagenReferencia),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Datos
            Column {
                Text(
                    text = "${contact.nombre} ${contact.apellidos}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(text = contact.telefono, color = Color.Gray)
            }
        }
    }
}

// --- PANTALLA 2: FORMULARIO ---
@Composable
fun FormularioScreen(navController: NavController, viewModel: ContactosViewModel) {
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var selectedImageKey by remember { mutableStateOf("face") } // Por defecto

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nuevo Contacto", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        // Selector de Imagen
        Text("Selecciona una foto:")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(availableImages.toList()) { (key, icon) ->
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(
                            width = if (selectedImageKey == key) 3.dp else 0.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .clickable { selectedImageKey = key },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Campos de texto
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && telefono.isNotBlank()) {
                    viewModel.agregarContacto(nombre, apellidos, telefono, selectedImageKey)
                    navController.popBackStack() // Volver a la lista
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Contacto")
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Cancelar")
        }
    }
}
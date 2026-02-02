package com.example.appelhostal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appelhostal.ui.viewmodel.OwnerViewModel
import com.example.appelhostal.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(
    navController: NavController,
    ownerViewModel: OwnerViewModel
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var pricePerNight by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("new_room") }
    
    val addRoomState by ownerViewModel.addRoomState.collectAsState()
    
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    LaunchedEffect(addRoomState) {
        when (addRoomState) {
            is UiState.Success -> {
                ownerViewModel.resetAddRoomState()
                navController.popBackStack()
            }
            is UiState.Error -> {
                errorMessage = (addRoomState as UiState.Error).message
                showError = true
            }
            else -> {}
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Habitación") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Nueva Habitación",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Rellena los datos para añadir una nueva habitación al hostal.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la habitación") },
                placeholder = { Text("Ej: Habitación Doble Superior") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                placeholder = { Text("Describe las características de la habitación...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = capacity,
                    onValueChange = { capacity = it.filter { char -> char.isDigit() } },
                    label = { Text("Capacidad") },
                    placeholder = { Text("2") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                
                OutlinedTextField(
                    value = pricePerNight,
                    onValueChange = { pricePerNight = it.filter { char -> char.isDigit() || char == '.' } },
                    label = { Text("Precio/noche (€)") },
                    placeholder = { Text("75.00") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
            
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Identificador de imagen") },
                placeholder = { Text("new_room") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            if (showError) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = {
                    val capacityInt = capacity.toIntOrNull()
                    val priceDouble = pricePerNight.toDoubleOrNull()
                    
                    when {
                        name.isBlank() -> {
                            errorMessage = "El nombre es obligatorio"
                            showError = true
                        }
                        description.isBlank() -> {
                            errorMessage = "La descripción es obligatoria"
                            showError = true
                        }
                        capacityInt == null || capacityInt < 1 -> {
                            errorMessage = "La capacidad debe ser al menos 1"
                            showError = true
                        }
                        priceDouble == null || priceDouble <= 0 -> {
                            errorMessage = "El precio debe ser mayor que 0"
                            showError = true
                        }
                        else -> {
                            showError = false
                            ownerViewModel.addRoom(
                                name = name,
                                description = description,
                                capacity = capacityInt,
                                pricePerNight = priceDouble,
                                imageUrl = imageUrl.ifBlank { "new_room" }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = addRoomState !is UiState.Loading
            ) {
                if (addRoomState is UiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = "Añadir Habitación",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

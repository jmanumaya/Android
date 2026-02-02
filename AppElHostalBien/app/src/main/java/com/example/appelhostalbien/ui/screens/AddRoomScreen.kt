package com.example.appelhostalbien.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appelhostalbien.ui.viewmodel.OwnerViewModel

@Composable
fun AddRoomScreen(navController: NavController, ownerViewModel: OwnerViewModel) {
    var number by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Scaffold(
    ) { padding ->

        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Añadir Habitación")
            TextField(value = number, onValueChange = { number = it }, label = { Text("Número de Habitación") })
            TextField(value = type, onValueChange = { type = it }, label = { Text("Tipo (Individual, Doble, etc)") })
            TextField(value = price, onValueChange = { price = it }, label = { Text("Precio por noche") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    ownerViewModel.addRoom(number, type, price.toDoubleOrNull() ?: 0.0)
                    navController.popBackStack()
                }
            ) {
                Text("Guardar Habitación")
            }
        }
    }
}

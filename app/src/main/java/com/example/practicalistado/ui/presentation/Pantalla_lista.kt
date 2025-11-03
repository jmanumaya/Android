package com.example.practicalistado.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.practicalistado.R
import com.example.practicalistado.Rutas
import com.example.practicalistado.data.repositories.Repositorio
import com.example.practicalistado.domain.entities.Contacto

// ------------------------------------------------------------
// Fila individual de contacto
// ------------------------------------------------------------
@Composable
fun ContactRow(contacto: Contacto) {
    var mostrarDatos by remember { mutableStateOf(false) }

    val imagenGenero = when (contacto.genre.lowercase()) {
        "hombre" -> R.drawable.male
        "mujer" -> R.drawable.female
        else -> R.drawable.ic_launcher_foreground
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = imagenGenero),
                contentDescription = "Foto contacto",
                modifier = Modifier
                    .height(80.dp)
                    .clickable { mostrarDatos = !mostrarDatos }
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Column {
                if (mostrarDatos) {
                    Text(
                        text = contacto.name,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = contacto.phoneNumber,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                    Text(
                        text = contacto.genre,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                } else {
                    val iniciales = contacto.name
                        .split(" ")
                        .mapNotNull { it.firstOrNull()?.toString()?.uppercase() }
                        .joinToString("")
                    Text(
                        text = iniciales,
                        fontSize = 40.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

// ------------------------------------------------------------
// Pantalla principal de contactos
// ------------------------------------------------------------
@Composable
fun ContactsScreen(navController: NavController
) {
    val lista = Repositorio.getAllContacts()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Rutas.NUEVO) }
            ) {
                Text("+")
            }
        }
    ) { innerPadding ->

        if (lista.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No hay contactos guardados.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(lista) { itemContacto ->
                    ContactRow(contacto = itemContacto)
                }
            }
        }
    }
}

// ------------------------------------------------------------
// Pantalla del formulario para añadir un contacto
// ------------------------------------------------------------
@Composable
fun FormNewContact(navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Nuevo contacto",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Género",
            style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val opcionesGenero = listOf("hombre", "mujer", "otro")
            opcionesGenero.forEach { opcion ->
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = genre == opcion,
                        onClick = { genre = opcion }
                    )
                    Text(opcion)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && phoneNumber.isNotBlank() && genre.isNotBlank()) {
                    val nuevo = Contacto(
                        id = (0..9999).random(),
                        name = name,
                        phoneNumber = phoneNumber,
                        genre = genre
                    )
                    Repositorio.insertContact(nuevo)
                    navController.navigate(Rutas.LISTA)
                    name = ""
                    phoneNumber = ""
                    genre = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Añadir contacto")
        }
    }
}

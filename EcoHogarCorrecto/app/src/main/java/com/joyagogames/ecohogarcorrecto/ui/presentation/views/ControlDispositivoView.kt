package com.joyagogames.ecohogarcorrecto.ui.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.joyagogames.ecohogarcorrecto.ui.presentation.DispositivoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlDispositivoView(
    navController: NavController,
    viewModel: DispositivoViewModel,
    dispositivoId: Int?
) {

    val dispositivo = dispositivoId?.let { viewModel.getDispositivoById(it) }

    if (dispositivo == null) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
        return
    }

    var encendido by remember { mutableStateOf(dispositivo.encendido) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Control de Dispositivo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = dispositivo.nombre,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = dispositivo.tipo.displayName,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Divider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (encendido) "Encendido" else "Apagado",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Switch(
                            checked = encendido,
                            onCheckedChange = {
                                encendido = it
                                viewModel.toggleDispositivo(dispositivo.id, it)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}
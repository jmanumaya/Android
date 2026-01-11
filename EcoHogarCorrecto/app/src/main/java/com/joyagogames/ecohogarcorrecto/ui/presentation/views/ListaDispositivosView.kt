package com.joyagogames.ecohogarcorrecto.ui.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.joyagogames.ecohogarcorrecto.data.models.FiltroDispositivo
import com.joyagogames.ecohogarcorrecto.domain.entities.Dispositivo
import com.joyagogames.ecohogarcorrecto.ui.presentation.DispositivoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaDispositivosView(navController: NavController, viewModel: DispositivoViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("EcoHogar", fontWeight = FontWeight.Bold)
                        Text("Jose Manuel Maya Hidalgo", fontSize = 14.sp, color = Color.Gray)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("nuevoDispositivo") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar dispositivo")
            }
        },
        bottomBar = {
            BottomMenuBar(viewModel)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.dispositivosFiltrados) { dispositivo ->
                DispositivoCard(dispositivo) {
                    navController.navigate("controlDispositivo/${dispositivo.id}")
                }
            }
        }
    }
}

@Composable
fun DispositivoCard(dispositivo: Dispositivo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (dispositivo.encendido) Color(0xFFE8F5E9) else Color(0xFFF5F5F5)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dispositivo.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dispositivo.tipo.displayName,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        color = if (dispositivo.encendido) Color(0xFF4CAF50) else Color.Gray,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun BottomMenuBar(viewModel: DispositivoViewModel) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Todo") },
            selected = viewModel.filtroActual == FiltroDispositivo.TODO,
            onClick = { viewModel.setFiltro(FiltroDispositivo.TODO) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Lock, contentDescription = null) },
            label = { Text("Luces") },
            selected = viewModel.filtroActual == FiltroDispositivo.LUZ,
            onClick = { viewModel.setFiltro(FiltroDispositivo.LUZ) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Sensores") },
            selected = viewModel.filtroActual == FiltroDispositivo.SENSOR,
            onClick = { viewModel.setFiltro(FiltroDispositivo.SENSOR) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = null) },
            label = { Text("Riego") },
            selected = viewModel.filtroActual == FiltroDispositivo.RIEGO,
            onClick = { viewModel.setFiltro(FiltroDispositivo.RIEGO) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
            label = { Text("Otro") },
            selected = viewModel.filtroActual == FiltroDispositivo.OTRO,
            onClick = { viewModel.setFiltro(FiltroDispositivo.OTRO) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Warning, contentDescription = null) },
            label = { Text("Ahorro") },
            selected = false,
            onClick = { viewModel.modoAhorro() }
        )
    }
}
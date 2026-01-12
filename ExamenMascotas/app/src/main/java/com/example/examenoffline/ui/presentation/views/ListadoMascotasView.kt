package com.example.examenoffline.ui.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.examenoffline.data.models.TipoEstado
import com.example.examenoffline.domain.entities.Mascota
import com.example.examenoffline.ui.presentation.MascotasViewModel

@Composable
fun ListadoMascotasView(navController: NavController, viewModel: MascotasViewModel) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("nuevaMascota") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+",
                    fontSize = 24.sp
                )
            }
        },
    )
    { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.mascotas) { mascota ->
                MascotaCard(mascota, navController)
            }
        }
    }
}

@Composable
fun MascotaCard(mascota: Mascota, navController: NavController) {

    var expandir by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clickable {
                expandir = !expandir
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (mascota.estado) {
                TipoEstado.BUSCANDO_CASA -> Color.White
                TipoEstado.EN_ACOGIDA -> Color.Blue
                else -> Color.Green
            }
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
                    text = mascota.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                if (expandir) {

                    when (mascota.estado) {
                        TipoEstado.BUSCANDO_CASA -> {
                            Button(
                                onClick = {
                                    navController.navigate("registroAcogida/${mascota.id}")
                                }
                            ) {
                                Text("Registrar Acogida")
                            }
                        }
                        TipoEstado.EN_ACOGIDA -> {
                            Text(
                                text = mascota.duenno,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                onClick = {
                                    navController.navigate("finalizarAdopcion/${mascota.id}")
                                }
                            ) {
                                Text("Finalizar Adopción")
                            }
                        }
                        else -> {
                            Text(
                                text = mascota.duenno,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
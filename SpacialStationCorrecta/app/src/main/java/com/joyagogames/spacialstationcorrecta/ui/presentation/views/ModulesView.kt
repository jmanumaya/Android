package com.joyagogames.spacialstationcorrecta.ui.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joyagogames.spacialstationcorrecta.ui.presentation.StationViewModel

@Composable
fun ModulesView(navController: NavController, vm: StationViewModel) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add") }) {
                Text("+")
            }
        }
    ) {
        Column(Modifier.padding(it)) {
            vm.modules.forEach { module ->
                val peso = module.pesoTotal()
                val color = when {
                    peso < 200 -> Color.Green
                    peso <= 400 -> Color.Yellow
                    else -> Color.Red
                }

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            vm.selectModule(module.id)
                            navController.navigate("detail")
                        },
                    colors = CardDefaults.cardColors(containerColor = color)
                ) {
                    Text("Módulo ${module.id}")
                    Text("Suministros: ${module.supplies.size}")
                    Text("Peso: $peso kg")
                }
            }
        }
    }
}

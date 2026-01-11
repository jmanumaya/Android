package com.joyagogames.almacenrepartoscorrecto.ui.presentation.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joyagogames.almacenrepartoscorrecto.ui.presentation.WarehouseViewModel

@Composable
fun BoxesView(navController: NavController, vm: WarehouseViewModel) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("create")
            }) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { padding ->

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            contentPadding = padding
        ) {
            items(vm.boxes) { box ->

                val color = when {
                    box.totalWeight < 8 -> Color.Green
                    box.totalWeight <= 16 -> Color.Yellow
                    else -> Color.Red
                }

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("detail/${box.number}")
                        },
                    colors = CardDefaults.cardColors(containerColor = color)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Caja ${box.number}")
                        Text("Objetos: ${box.articles.size}")
                        Text("Peso: ${"%.2f".format(box.totalWeight)} kg")
                    }
                }
            }
        }
    }
}

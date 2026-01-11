package com.joyagogames.almacenrepartoscorrecto.ui.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joyagogames.almacenrepartoscorrecto.ui.presentation.WarehouseViewModel

@Composable
fun BoxDetailView(
    navController: NavController,
    vm: WarehouseViewModel,
    boxNumber: Int
) {
    val box = vm.boxes.first { it.number == boxNumber }

    Column(Modifier.padding(16.dp)) {

        Text("Caja $boxNumber")
        Text("Peso total: ${"%.2f".format(box.totalWeight)} kg")

        Spacer(Modifier.height(12.dp))

        LazyColumn {
            items(box.articles) { article ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${article.name} - ${article.units} x ${article.weightPerUnit}kg")

                    IconButton(onClick = {
                        vm.deleteArticle(boxNumber, article.id)
                    }) {
                        Icon(Icons.Default.Delete, null)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { vm.clearBox(boxNumber) }) {
            Text("Vaciar caja")
        }

        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}

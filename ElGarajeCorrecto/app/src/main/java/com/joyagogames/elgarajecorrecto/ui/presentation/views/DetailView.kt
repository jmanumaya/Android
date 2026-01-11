package com.joyagogames.elgarajecorrecto.ui.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joyagogames.elgarajecorrecto.ui.presentation.GarageViewModel

@Composable
fun DetailView(navController: NavController, vm: GarageViewModel) {

    val spot = vm.selectedSpot ?: return

    Column(Modifier.padding(20.dp)) {

        Text("Plaza ${spot.number}")
        Text("Matrícula: ${spot.plate}")

        Button(onClick = {
            vm.freeSpot(spot.number)
            navController.navigate("list")
        }) {
            Text("Liberar plaza")
        }

        Button(onClick = { navController.navigate("list") }) {
            Text("Cancelar")
        }
    }
}

package com.joyagogames.elgarajecorrecto.ui.presentation.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joyagogames.elgarajecorrecto.ui.presentation.GarageViewModel

@Composable
fun EntryView(navController: NavController, vm: GarageViewModel) {

    val context = LocalContext.current
    val spot = vm.selectedSpot ?: return

    var plate by remember { mutableStateOf("") }

    Column(Modifier.padding(20.dp)) {

        Text("Plaza ${spot.number}")
        OutlinedTextField(value = plate, onValueChange = { plate = it }, label = { Text("Matrícula") })

        Row {
            Button(onClick = {
                if (plate.isBlank()) {
                    Toast.makeText(context, "Matrícula vacía", Toast.LENGTH_SHORT).show()
                } else {
                    vm.addVehicle(spot.number, plate)
                    navController.navigate("list")
                }
            }) {
                Text("Guardar")
            }

            Button(onClick = { navController.navigate("list") }) {
                Text("Cancelar")
            }
        }
    }
}

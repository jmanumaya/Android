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
fun SettingsView(navController: NavController, vm: GarageViewModel) {

    val context = LocalContext.current
    var total by remember { mutableStateOf("") }

    Column(Modifier.padding(20.dp)) {

        Text("Número total de plazas")
        OutlinedTextField(value = total, onValueChange = { total = it })

        Row {
            Button(onClick = {
                val number = total.toIntOrNull()
                if (number == null || number < 2) {
                    Toast.makeText(context, "Mínimo 2 plazas", Toast.LENGTH_SHORT).show()
                } else {
                    vm.changeTotalSpots(number)
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

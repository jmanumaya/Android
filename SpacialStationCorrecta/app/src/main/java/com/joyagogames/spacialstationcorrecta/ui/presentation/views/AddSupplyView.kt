package com.joyagogames.spacialstationcorrecta.ui.presentation.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import com.joyagogames.spacialstationcorrecta.data.models.SupplyItem
import com.joyagogames.spacialstationcorrecta.data.models.SupplyType
import com.joyagogames.spacialstationcorrecta.ui.presentation.StationViewModel

@Composable
fun AddSupplyView(navController: NavController, vm: StationViewModel) {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var unidades by remember { mutableStateOf("") }
    var modulo by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf<SupplyType?>(null) }

    Column(Modifier.padding(20.dp)) {

        Text("Tu nombre")
        OutlinedTextField(value = name, onValueChange = { name = it })

        Text("Selecciona suministro")
        SupplyType.values().forEach {
            Row {
                RadioButton(
                    selected = tipo == it,
                    onClick = { tipo = it }
                )
                Text(it.name)
            }
        }

        OutlinedTextField(value = unidades, onValueChange = { unidades = it }, label = { Text("Unidades") })
        OutlinedTextField(value = modulo, onValueChange = { modulo = it }, label = { Text("Módulo (1-8)") })

        Row {
            Button(onClick = {
                if (name.isBlank() || unidades.isBlank() || modulo.isBlank() || tipo == null) {
                    Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val item = SupplyItem(tipo!!, unidades.toInt())
                val ok = vm.addSupply(modulo.toInt(), item)

                if (!ok) {
                    Toast.makeText(context, "No cabe en el módulo", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate("modules")
                }
            }) {
                Text("Guardar")
            }

            Button(onClick = { navController.navigate("modules") }) {
                Text("Cancelar")
            }
        }
    }
}
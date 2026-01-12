package com.example.examenoffline.ui.presentation.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.examenoffline.ui.presentation.MascotasViewModel
import com.example.examenoffline.ui.theme.PurpleGrey40
import com.example.examenoffline.data.models.Especie

@Composable
fun NuevaMascotaView(navController: NavController, viewModel: MascotasViewModel) {

    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf<Especie>(Especie.OTROS) }
    var edad by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val toast = Toast.makeText(LocalContext.current, "Debes rellenar los campos", Toast.LENGTH_SHORT)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Registro De Nueva Mascota",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Introduce el nombre de la nueva mascota",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { newValue ->
                nombre = newValue
                showError = false
            },
            placeholder = { Text("Nombre de la animal:") },
            isError = showError,
            modifier = Modifier
                .width(200.dp)
                .padding(bottom = 5.dp)
        )

        Text(
            "Introduce la especie",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text("Selecciona especie")
        Especie.values().forEach {
            Row {
                RadioButton(
                    selected = tipo == it,
                    onClick = { tipo = it }
                )
                Text(it.name)
            }
        }

        Text(
            "Introduce la edad del animal",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = edad,
            onValueChange = { newValue ->
                edad = newValue
                showError = false
            },
            placeholder = { Text("ejem -> 4:") },
            isError = showError,
            modifier = Modifier
                .width(200.dp)
                .padding(bottom = 5.dp)
        )

        Row {
            Button(onClick = {
                if (nombre.isBlank() || edad.isBlank()) {
                    toast.show()
                    return@Button
                }

                val edad = edad.toInt();
                viewModel.addMascota(nombre, tipo, edad)
                navController.navigate("listadoMascotas")
            }) {
                Text("Guardar")
            }

            Button(
                onClick = {
                    nombre = ""
                    tipo = Especie.OTROS
                    edad = ""
                    navController.navigate("listadoMascotas")
                },
                modifier = Modifier
                    .padding(top = 5.dp)
                    .width(150.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleGrey40
                )
            ) {
                Text(
                    "Cancelar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default
                )
            }
        }
    }
}
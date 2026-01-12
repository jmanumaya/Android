package com.example.examenoffline.ui.presentation.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.examenoffline.ui.theme.DarkGreen
import com.example.examenoffline.ui.theme.PurpleGrey40

@Composable
fun RegistroAcogidasView(navController: NavController, viewModel: MascotasViewModel, mascotaId: Int?) {

    var nombre by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val toast = Toast.makeText(LocalContext.current, "Debes rellenar los campos", Toast.LENGTH_SHORT)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Registro De Acogida",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Introduce el nombre de la persona que se va a hacer cargo del animal",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { newValue ->
                nombre = newValue
                showError = false
            },
            placeholder = { Text("Nombre de la persona:") },
            isError = showError,
            modifier = Modifier
                .width(200.dp)
                .padding(bottom = 5.dp)
        )

        if (nombre == "") {
            showError = true
            Text(
                text = "Debes rellenar el nombre de la persona.",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                fontFamily = FontFamily.Default
            )
        }

        Button(
            onClick = {
                if (!showError) {
                    viewModel.registrarAcogida(mascotaId, nombre)
                    navController.navigate("listadoMascotas")
                } else {
                    showError = true;
                    toast.show()
                }
            },
            modifier = Modifier
                .padding(top = 5.dp)
                .width(150.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkGreen
            )
        ) {
            Text("Confirmar",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default)
        }

        Button(
            onClick = {
                nombre = ""
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
            Text("Volver",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default)
        }
    }
}
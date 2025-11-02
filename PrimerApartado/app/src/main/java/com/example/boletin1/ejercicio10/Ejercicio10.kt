package com.example.boletin1.ejercicio10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boletin1.ejercicio10.ui.theme.Boletin1Theme

class Ejercicio10Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Boletin1Theme {
                CitaDelDia()
            }
        }
    }
}

@Composable
fun CitaDelDia() {
    // Lista de citas
    val citas = listOf(
        "La vida es lo que pasa mientras estás ocupado haciendo otros planes." to "John Lennon",
        "El único modo de hacer un gran trabajo es amar lo que haces." to "Steve Jobs",
        "No cuentes los días, haz que los días cuenten." to "Muhammad Ali",
        "El éxito es la suma de pequeños esfuerzos repetidos día tras día." to "Robert Collier",
        "Si puedes soñarlo, puedes lograrlo." to "Walt Disney"
    )

    // Estado con la cita actual
    var citaActual by remember { mutableStateOf(citas.random()) }

    // Cambiar color según cita (simplemente para variar)
    val colores = listOf(Color(0xFFFFE082), Color(0xFF80DEEA), Color(0xFFC5E1A5), Color(0xFFFFAB91), Color(0xFFD1C4E9))
    val colorFondo = remember(citaActual) { colores[citas.indexOf(citaActual) % colores.size] }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "\"${citaActual.first}\"",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "- ${citaActual.second} -",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { citaActual = citas.random() }) {
                Text("Cambiar cita")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCitaDelDia() {
    Boletin1Theme {
        CitaDelDia()
    }
}

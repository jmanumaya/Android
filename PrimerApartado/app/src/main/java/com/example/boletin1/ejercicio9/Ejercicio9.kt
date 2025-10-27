package com.example.boletin1.ejercicio9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.boletin1.ejercicio9.ui.theme.Boletin1Theme
import com.example.boletin1.R

class Ejercicio9Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Boletin1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MiniGaleria(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MiniGaleria(modifier: Modifier = Modifier) {
    val imagenes = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_foreground,
    )

    var indiceActual by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imagenes[indiceActual]),
            contentDescription = "Imagen de galería",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                indiceActual = if (indiceActual - 1 < 0) imagenes.size - 1 else indiceActual - 1
            }) {
                Text("Anterior")
            }

            Button(onClick = {
                indiceActual = (indiceActual + 1) % imagenes.size
            }) {
                Text("Siguiente")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MiniGaleriaPreview() {
    Boletin1Theme {
        MiniGaleria()
    }
}

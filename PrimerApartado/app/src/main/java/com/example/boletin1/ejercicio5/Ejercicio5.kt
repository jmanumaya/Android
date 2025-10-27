package com.example.boletin1.ejercicio5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boletin1.ui.theme.Boletin1Theme

class Ejercicio5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Boletin1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaTarjetaAvanzada(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaTarjetaAvanzada(modifier: Modifier = Modifier) {
    var showDescription by remember { mutableStateOf(false) }

    val name = "Jose Manuel"
    val profesion = "Programador"
    val email = "jm.maya@iesnervion.es"
    val descripcion = "Apasionado de la tecnología y el desarrollo de aplicaciones. Siempre aprendiendo y mejorando mis habilidades."

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(), // opcional
                horizontalAlignment = Alignment.CenterHorizontally, // centra todo dentro
            ) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Imagen de perfil",
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(name, fontSize = 20.sp, color = Color.White)
                Text(profesion, fontSize = 16.sp, color = Color.White)
                Text(email, color = Color.White)

                Spacer(modifier = Modifier.height(12.dp))

                Button(onClick = { showDescription = !showDescription }) {
                    Text(if (showDescription) "Ver menos" else "Ver más")
                }

                AnimatedVisibility(visible = showDescription) {
                    Column(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(descripcion, color = Color.White, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Boletin1Theme {
        PantallaTarjetaAvanzada()
    }
}

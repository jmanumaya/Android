package com.example.boletin1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.boletin1.ejercicio1.Ejercicio1Activity
import com.example.boletin1.ui.theme.Boletin1Theme
import com.example.ejercicio2.Ejercicio2Activity
import com.example.boletin1.ejercicio3.Ejercicio3Activity
import com.example.boletin1.ejercicio4.Ejercicio4Activity
import com.example.boletin1.ejercicio5.Ejercicio5Activity
import com.example.boletin1.ejercicio6.Ejercicio6Activity
import com.example.boletin1.ejercicio7.Ejercicio7Activity
import com.example.boletin1.ejercicio8.Ejercicio8Activity
import com.example.boletin1.ejercicio9.Ejercicio9Activity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Boletin1Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MenuPrincipal(
                        modifier = Modifier.padding(innerPadding),
                        onOpenEjercicio1 = {
                            startActivity(Intent(this, Ejercicio1Activity::class.java))
                        },
                        onOpenEjercicio2 = {
                            startActivity(Intent(this, Ejercicio2Activity::class.java))
                        },
                        onOpenEjercicio3 = {
                            startActivity(Intent(this, Ejercicio3Activity::class.java))
                        },
                        onOpenEjercicio4 = {
                            startActivity(Intent(this, Ejercicio4Activity::class.java))
                        },
                        onOpenEjercicio5 = {
                            startActivity(Intent(this, Ejercicio5Activity::class.java))
                        },
                        onOpenEjercicio6 = {
                            startActivity(Intent(this, Ejercicio6Activity::class.java))
                        },
                        onOpenEjercicio7 = {
                            startActivity(Intent(this, Ejercicio7Activity::class.java))
                        },
                        onOpenEjercicio8 = {
                            startActivity(Intent(this, Ejercicio8Activity::class.java))
                        },
                        onOpenEjercicio9 = {
                            startActivity(Intent(this, Ejercicio9Activity::class.java))
                        }

                    )
                }
            }
        }
    }
}

@Composable
fun MenuPrincipal(
    modifier: Modifier = Modifier,
    onOpenEjercicio1: () -> Unit,
    onOpenEjercicio2: () -> Unit,
    onOpenEjercicio3: () -> Unit,
    onOpenEjercicio4: () -> Unit,
    onOpenEjercicio5: () -> Unit,
    onOpenEjercicio6: () -> Unit,
    onOpenEjercicio7: () -> Unit,
    onOpenEjercicio8: () -> Unit,
    onOpenEjercicio9: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Boletín 1 - Menú principal")
        Button(onClick = onOpenEjercicio1) {
            Text("Ejercicio 1")
        }
        Button(onClick = onOpenEjercicio2) {
            Text("Ejercicio 2")
        }
        Button(onClick = onOpenEjercicio3) {
            Text("Ejercicio 3")
        }
        Button(onClick = onOpenEjercicio4) {
            Text("Ejercicio 4")
        }
        Button(onClick = onOpenEjercicio5) {
            Text("Ejercicio 5")
        }
        Button(onClick = onOpenEjercicio6) {
            Text("Ejercicio 6")
        }
        Button(onClick = onOpenEjercicio7) {
            Text("Ejercicio 7")
        }
        Button(onClick = onOpenEjercicio8) {
            Text("Ejercicio 8")
        }
        Button(onClick = onOpenEjercicio9) {
            Text("Ejercicio 9")
        }
    }
}

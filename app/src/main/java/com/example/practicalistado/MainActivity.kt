package com.example.practicalistado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practicalistado.ui.presentation.ContactsScreen
import com.example.practicalistado.ui.presentation.FormNewContact
import com.example.practicalistado.ui.theme.PracticaListadoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaListadoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Rutas.LISTA
                ) {
                    composable(Rutas.LISTA) {
                        ContactsScreen(navController)
                    }

                    composable(Rutas.NUEVO) {
                        FormNewContact(navController)
                    }
                }
            }
        }
    }
}
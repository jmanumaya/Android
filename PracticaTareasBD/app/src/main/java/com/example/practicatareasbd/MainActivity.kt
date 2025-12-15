package com.example.practicatareasbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.practicatareasbd.data.ContactDataBase
import com.example.practicatareasbd.ui.presentation.ContactListScreen
import com.example.practicatareasbd.ui.presentation.FormularioScreen
import com.example.practicatareasbd.ui.presentation.ContactosViewModel
import com.example.practicatareasbd.ui.theme.PracticaTareasBDTheme

class MainActivity : ComponentActivity() {

    companion object {
        // Inicialización estática simple (como en tu ejemplo original)
        lateinit var database: ContactDataBase
    }

    private val contactosViewModel: ContactosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Construimos la BD de contactos
        database = Room.databaseBuilder(
            applicationContext,
            ContactDataBase::class.java,
            "contactos-db"
        ).build()

        setContent {
            PracticaTareasBDTheme {
                NavigationHost(contactosViewModel)
            }
        }
    }
}

@Composable
fun NavigationHost(viewModel: ContactosViewModel) {
    val navController = rememberNavController()

    // Definimos las rutas: "list" y "form"
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ContactListScreen(navController, viewModel)
        }
        composable("form") {
            FormularioScreen(navController, viewModel)
        }
    }
}
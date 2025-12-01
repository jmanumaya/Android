package com.example.practicatareasbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.practicatareasbd.data.TaskDataBase
import com.example.practicatareasbd.ui.presentation.MainView
import com.example.practicatareasbd.ui.presentation.TareasViewModel
import com.example.practicatareasbd.ui.theme.PracticaTareasBDTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var database: TaskDataBase
    }

    private val tareasViewModel: TareasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = Room.databaseBuilder(
            applicationContext,			            // Contexto de la aplicación
            TaskDataBase::class.java,		// Clase de la base de datos
            "tareas-db"				        // Nombre de la base de datos
        ).build()					                // Se construye
        setContent {
            PracticaTareasBDTheme {
                MainNav(tareasViewModel)
            }
        }
    }
}




@Composable
fun MainNav(tareasViewModel: TareasViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainView(navController, tareasViewModel)
        }
    }
}
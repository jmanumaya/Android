package com.example.practicalistado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.room.Room
import com.example.practicalistado.data.repositories.ContactDataBase
import com.example.practicalistado.ui.presentation.ContactsScreen
import com.example.practicalistado.ui.presentation.FormNewContact
import com.example.practicalistado.ui.theme.PracticaListadoTheme
import kotlin.getValue
import com.example.practicalistado.ui.presentation.ContactViewModel

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: ContactDataBase
    }

    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = Room.databaseBuilder(
            applicationContext,			            // Contexto de la aplicación
            ContactDataBase::class.java,		// Clase de la base de datos
            "contacts-db"		        // Nombre de la base de datos
        ).build()					                // Se construye
        setContent {
            PracticaListadoTheme {
                MainNav(contactViewModel)
            }
        }
    }
}

@Composable
fun MainNav(contactViewModel: ContactViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            ContactsScreen(navController, contactViewModel)
        }
        composable("form") {
            FormNewContact(navController, contactViewModel)
        }

    }
}
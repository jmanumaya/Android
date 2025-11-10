package com.example.diariodeviajes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.diariodeviajes.repositories.ListadoDestinos
import com.example.diariodeviajes.ui.DetallesDestino
import com.example.diariodeviajes.ui.ListDestinos
import com.example.diariodeviajes.ui.theme.DiarioDeViajesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiarioDeViajesTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "lista"
                ) {
                    composable("lista") {
                        ListDestinos(destinos = ListadoDestinos.getAllDestinos(), navController)
                    }

                    composable(
                        route = "detalles/{idDestino}",
                        arguments = listOf(navArgument("idDestino") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val idDestino = backStackEntry.arguments?.getInt("idDestino") ?: 0
                        val destino = ListadoDestinos.getDestinoById(idDestino)
                        if (destino != null) {
                            DetallesDestino(destino)
                        }
                    }
                }
            }
        }
    }
}
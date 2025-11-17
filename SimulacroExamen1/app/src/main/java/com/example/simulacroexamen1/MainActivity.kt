package com.example.simulacroexamen1

import android.os.Bundle
import android.view.View
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
import androidx.lifecycle.ViewModel
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simulacroexamen1.ui.presentation.Apuesta
import com.example.simulacroexamen1.ui.presentation.Comprobacion
import com.example.simulacroexamen1.ui.presentation.EligeNumero
import com.example.simulacroexamen1.ui.theme.SimulacroExamen1Theme
import com.example.simulacroexamen1.ui.presentation.PlayerViewModel

class MainActivity : ComponentActivity() {

    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimulacroExamen1Theme {
                MainNav(playerViewModel)
            }
        }
    }
}

@Composable
fun MainNav(playerViewModel: PlayerViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "eligeNum") {
        composable("eligeNum") {
            EligeNumero(navController, playerViewModel)
        }
        composable("apuesta") { backStackEntry ->
            Apuesta(navController, playerViewModel)
        }
        composable("resultadoApuesta") {
            Comprobacion(navController, playerViewModel)
        }
    }
}

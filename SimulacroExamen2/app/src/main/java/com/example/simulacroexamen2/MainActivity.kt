package com.example.simulacroexamen2

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
import com.example.simulacroexamen2.ui.theme.SimulacroExamen2Theme
import kotlin.getValue
import com.example.simulacroexamen2.ui.presentation.GastosViewModel
import com.example.simulacroexamen2.ui.presentation.NombresPersonas
import com.example.simulacroexamen2.ui.presentation.NumeroPersonas
import com.example.simulacroexamen2.ui.presentation.ResultadoTotalPorPersona

class MainActivity : ComponentActivity() {

    private val gastosViewModel: GastosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimulacroExamen2Theme {
                MainNav(gastosViewModel)
            }
        }
    }
}

@Composable
fun MainNav(playerViewModel: GastosViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            NumeroPersonas(navController, playerViewModel)
        }
        composable("nombres") { backStackEntry ->
            NombresPersonas(navController, playerViewModel)
        }
        composable("resultadoPorPersona") {
            ResultadoTotalPorPersona(navController, playerViewModel)
        }
    }
}
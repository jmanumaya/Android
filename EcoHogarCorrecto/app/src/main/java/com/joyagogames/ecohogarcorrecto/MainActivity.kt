package com.joyagogames.ecohogarcorrecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joyagogames.ecohogarcorrecto.ui.presentation.DispositivoViewModel
import com.joyagogames.ecohogarcorrecto.ui.presentation.views.ControlDispositivoView
import com.joyagogames.ecohogarcorrecto.ui.presentation.views.ListaDispositivosView
import com.joyagogames.ecohogarcorrecto.ui.presentation.views.NuevoDispositivoView
import com.joyagogames.ecohogarcorrecto.ui.theme.EcoHogarCorrectoTheme

class MainActivity : ComponentActivity() {

    private val dispositivoViewModel: DispositivoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoHogarCorrectoTheme {
                MainNav(dispositivoViewModel)
            }
        }
    }
}

@Composable
fun MainNav(dispositivoViewModel: DispositivoViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "listaDispositivos") {
        composable("listaDispositivos") {
            ListaDispositivosView(navController, dispositivoViewModel)
        }
        composable("nuevoDispositivo") {
            NuevoDispositivoView(navController, dispositivoViewModel)
        }
        composable("controlDispositivo/{dispositivoId}") { backStackEntry ->
            val dispositivoId = backStackEntry.arguments?.getString("dispositivoId")?.toIntOrNull()
            ControlDispositivoView(navController, dispositivoViewModel, dispositivoId)
        }
    }
}
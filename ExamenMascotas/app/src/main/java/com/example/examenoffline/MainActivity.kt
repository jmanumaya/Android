package com.example.examenoffline

// Librerias cargadas. NO BORRAR.
import android.os.Bundle
import android.util.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.compose.*
import com.example.examenoffline.ui.presentation.MascotasViewModel
import com.example.examenoffline.ui.presentation.views.FinalizarAdopcionView
import com.example.examenoffline.ui.presentation.views.ListadoMascotasView
import com.example.examenoffline.ui.presentation.views.NuevaMascotaView
import com.example.examenoffline.ui.presentation.views.RegistroAcogidasView
import com.example.examenoffline.ui.theme.ExamenOfflineTheme

// Fin de las librerías cargadas. NO BORRAR.

class MainActivity : ComponentActivity() {

    private val mascotasViewModel: MascotasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExamenOfflineTheme {
                MainNav(mascotasViewModel)
            }
        }
    }
}

@Composable
fun MainNav(mascotasViewModel: MascotasViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "listadoMascotas") {
        composable("listadoMascotas") {
            ListadoMascotasView(navController, mascotasViewModel)
        }
        composable("registroAcogida/{mascotaId}") { backStackEntry ->
            val mascotaId = backStackEntry.arguments?.getString("mascotaId")?.toIntOrNull()
            Log.d(":::TAGDIS","${mascotaId}")
            RegistroAcogidasView(navController, mascotasViewModel, mascotaId)
        }
        composable("finalizarAdopcion/{mascotaId}") { backStackEntry ->
            val mascotaId = backStackEntry.arguments?.getString("mascotaId")?.toIntOrNull()
            FinalizarAdopcionView(navController, mascotasViewModel, mascotaId)
        }
        composable("nuevaMascota") {
            NuevaMascotaView(navController, mascotasViewModel)
        }
    }
}

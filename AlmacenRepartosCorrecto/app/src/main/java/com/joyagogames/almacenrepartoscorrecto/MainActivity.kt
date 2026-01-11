package com.joyagogames.almacenrepartoscorrecto

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joyagogames.almacenrepartoscorrecto.ui.presentation.WarehouseViewModel
import com.joyagogames.almacenrepartoscorrecto.ui.presentation.views.BoxDetailView
import com.joyagogames.almacenrepartoscorrecto.ui.presentation.views.BoxesView
import com.joyagogames.almacenrepartoscorrecto.ui.presentation.views.CreateArticleView
import com.joyagogames.almacenrepartoscorrecto.ui.theme.AlmacenRepartosCorrectoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val vm: WarehouseViewModel = viewModel()

            NavHost(
                navController = navController,
                startDestination = "boxes"
            ) {
                composable("boxes") {
                    BoxesView(navController, vm)
                }

                composable("create") {
                    CreateArticleView(navController, vm)
                }

                composable("detail/{box}") { backStack ->
                    val boxNumber = backStack.arguments?.getString("box")!!.toInt()
                    BoxDetailView(navController, vm, boxNumber)
                }
            }
        }
    }
}

package com.joyagogames.spacialstationcorrecta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.joyagogames.spacialstationcorrecta.ui.presentation.StationViewModel
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
import com.joyagogames.spacialstationcorrecta.ui.presentation.views.AddSupplyView
import com.joyagogames.spacialstationcorrecta.ui.presentation.views.ModuleDetailView
import com.joyagogames.spacialstationcorrecta.ui.presentation.views.ModulesView
import com.joyagogames.spacialstationcorrecta.ui.theme.SpacialStationCorrectaTheme

class MainActivity : ComponentActivity() {

    private val viewModel: StationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "modules") {
                composable("add") { AddSupplyView(navController, viewModel) }
                composable("modules") { ModulesView(navController, viewModel) }
                composable("detail") { ModuleDetailView(navController, viewModel) }
            }
        }
    }
}
package com.joyagogames.elgarajecorrecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.joyagogames.elgarajecorrecto.ui.presentation.GarageViewModel
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
import com.joyagogames.elgarajecorrecto.ui.presentation.views.DetailView
import com.joyagogames.elgarajecorrecto.ui.presentation.views.EntryView
import com.joyagogames.elgarajecorrecto.ui.presentation.views.ListView
import com.joyagogames.elgarajecorrecto.ui.presentation.views.SettingsView
import com.joyagogames.elgarajecorrecto.ui.theme.ElGarajeCorrectoTheme

class MainActivity : ComponentActivity() {

    private val viewModel: GarageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "list") {
                composable("list") { ListView(navController, viewModel, this@MainActivity) }
                composable("entry") { EntryView(navController, viewModel) }
                composable("detail") { DetailView(navController, viewModel) }
                composable("settings") { SettingsView(navController, viewModel) }
            }
        }
    }
}

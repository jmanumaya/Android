package com.joyagogames.ejercicio1ppt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joyagogames.ejercicio1ppt.ui.presentation.GameViewModel
import com.joyagogames.ejercicio1ppt.ui.presentation.views.FinnishGameView
import com.joyagogames.ejercicio1ppt.ui.presentation.views.MainGame
import com.joyagogames.ejercicio1ppt.ui.presentation.views.WelcomeView
import com.joyagogames.ejercicio1ppt.ui.theme.Ejercicio1PPTTheme

class MainActivity : ComponentActivity() {

    private val gameViewModel : GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio1PPTTheme {
                MainNav(gameViewModel)
            }
        }
    }
}

@Composable
fun MainNav(gameViewModel: GameViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcomeView") {
        composable("welcomeView") {
            WelcomeView(navController, gameViewModel)
        }
        composable("mainGame") { backStackEntry ->
            MainGame(navController, gameViewModel)
        }
        composable("finnishGameView") { backStackEntry ->
            FinnishGameView(navController, gameViewModel)
        }
    }
}
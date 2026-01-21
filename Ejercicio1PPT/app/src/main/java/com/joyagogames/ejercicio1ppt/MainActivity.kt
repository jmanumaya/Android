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
import androidx.room.Room
import com.joyagogames.ejercicio1ppt.data.GameDataBase
import com.joyagogames.ejercicio1ppt.ui.presentation.GameViewModel
import com.joyagogames.ejercicio1ppt.ui.presentation.views.FinnishGameView
import com.joyagogames.ejercicio1ppt.ui.presentation.views.HistoryView
import com.joyagogames.ejercicio1ppt.ui.presentation.views.MainGame
import com.joyagogames.ejercicio1ppt.ui.presentation.views.WelcomeView
import com.joyagogames.ejercicio1ppt.ui.theme.Ejercicio1PPTTheme
import com.joyagogames.ejercicio1ppt.data.GameRepository
import com.joyagogames.ejercicio1ppt.ui.presentation.GameViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            GameDataBase::class.java,
            "games-db"
        ).build()
    }

    private val repository by lazy { GameRepository(database.gameDao()) }

    private val gameViewModel: GameViewModel by viewModels {
        GameViewModelFactory(repository)
    }

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
        composable("mainGame") {
            MainGame(navController, gameViewModel)
        }
        composable("finnishGameView") {
            FinnishGameView(navController, gameViewModel)
        }
        composable("historyView") {
            HistoryView(navController, gameViewModel)
        }
    }
}
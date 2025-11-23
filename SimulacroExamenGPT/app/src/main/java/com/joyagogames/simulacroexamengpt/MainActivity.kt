package com.joyagogames.simulacroexamengpt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joyagogames.simulacroexamengpt.ui.presentation.MainQuiz
import com.joyagogames.simulacroexamengpt.ui.presentation.Preguntas
import com.joyagogames.simulacroexamengpt.ui.presentation.QuizViewModel
import com.joyagogames.simulacroexamengpt.ui.presentation.Resultados
import com.joyagogames.simulacroexamengpt.ui.theme.SimulacroExamenGPTTheme

class MainActivity : ComponentActivity() {

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimulacroExamenGPTTheme {
                MainNav(quizViewModel)
            }
        }
    }
}

@Composable
fun MainNav(vm: QuizViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainQuiz") {
        composable("mainQuiz") {
            MainQuiz(navController, vm)
        }
        composable("preguntasQuiz") {
            Preguntas(navController, vm)
        }
        composable("resultados") {
            Resultados(navController, vm)
        }
    }
}

package com.joyagogames.to_do_app_practica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joyagogames.to_do_app_practica.ui.presentation.AddTaskView
import com.joyagogames.to_do_app_practica.ui.presentation.TaskDetailView
import com.joyagogames.to_do_app_practica.ui.presentation.TaskListScreenWithFAB
import com.joyagogames.to_do_app_practica.ui.theme.ToDoAppPracticaTheme
import com.joyagogames.to_do_app_practica.ui.presentation.TaskViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppPracticaTheme {
                MainNav(taskViewModel)
            }
        }
    }
}

@Composable
fun MainNav(taskViewModel: TaskViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") {
            TaskListScreenWithFAB(navController, taskViewModel)
        }
        composable("ver/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            val task = taskViewModel.getTaskById(id)
            TaskDetailView(task, taskViewModel, navController)
        }
        composable("agregar") {
            AddTaskView(taskViewModel, navController)
        }
    }
}

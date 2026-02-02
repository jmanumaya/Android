package com.example.appelhostal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appelhostal.ui.screens.BookingsScreen
import com.example.appelhostal.ui.screens.LoginScreen
import com.example.appelhostal.ui.screens.RegisterScreen
import com.example.appelhostal.ui.screens.RoomDetailScreen
import com.example.appelhostal.ui.screens.RoomsScreen
import com.example.appelhostal.ui.viewmodel.AuthViewModel
import com.example.appelhostal.ui.viewmodel.BookingsViewModel
import com.example.appelhostal.ui.viewmodel.RoomsViewModel

@Composable
fun MainNav(
    authViewModel: AuthViewModel,
    roomsViewModel: RoomsViewModel,
    bookingsViewModel: BookingsViewModel
) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Routes.Rooms.route) {
        composable(Routes.Rooms.route) {
            RoomsScreen(
                navController = navController,
                authViewModel = authViewModel,
                roomsViewModel = roomsViewModel
            )
        }
        
        composable(
            route = Routes.RoomDetail.route,
            arguments = listOf(navArgument("roomId") { type = NavType.LongType })
        ) { backStackEntry ->
            val roomId = backStackEntry.arguments?.getLong("roomId") ?: 0L
            RoomDetailScreen(
                navController = navController,
                roomId = roomId,
                authViewModel = authViewModel,
                roomsViewModel = roomsViewModel,
                bookingsViewModel = bookingsViewModel
            )
        }
        
        composable(Routes.Login.route) {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        
        composable(Routes.Register.route) {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }
        
        composable(Routes.Bookings.route) {
            BookingsScreen(
                navController = navController,
                authViewModel = authViewModel,
                bookingsViewModel = bookingsViewModel
            )
        }
    }
}

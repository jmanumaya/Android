package com.example.appelhostalbien

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.appelhostalbien.data.db.AppDatabase
import com.example.appelhostalbien.data.entities.ClientEntity
import com.example.appelhostalbien.di.AppContainer
import com.example.appelhostalbien.di.ViewModelFactory
import com.example.appelhostalbien.ui.screens.AddRoomScreen
import com.example.appelhostalbien.ui.screens.BookingHistoryScreen
import com.example.appelhostalbien.ui.screens.BookingsScreen
import com.example.appelhostalbien.ui.screens.LoginScreen
import com.example.appelhostalbien.ui.screens.OwnerDashboardScreen
import com.example.appelhostalbien.ui.screens.RegisterScreen
import com.example.appelhostalbien.ui.screens.RoomDetailScreen
import com.example.appelhostalbien.ui.screens.RoomsScreen
import com.example.appelhostalbien.ui.theme.AppElHostalBienTheme
import com.example.appelhostalbien.ui.viewmodel.AuthViewModel
import com.example.appelhostalbien.ui.viewmodel.BookingsViewModel
import com.example.appelhostalbien.ui.viewmodel.OwnerViewModel
import com.example.appelhostalbien.ui.viewmodel.RoomsViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "hostal-db"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

    private val container by lazy { AppContainer(database) }

    private val viewModelFactory by lazy {
        ViewModelFactory(
            container.authRepository,
            container.roomsRepository,
            container.bookingsRepository
        )
    }

    private val authViewModel: AuthViewModel by viewModels { viewModelFactory }
    private val roomsViewModel: RoomsViewModel by viewModels { viewModelFactory }
    private val bookingsViewModel: BookingsViewModel by viewModels { viewModelFactory }
    private val ownerViewModel: OwnerViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            try {
                seedOwnerAccount()
                container.roomsRepository.seedRoomsIfEmpty()
            } catch (e: Exception) {
                android.util.Log.e("MainActivity", "Error initializing data", e)
            }
        }

        setContent {
            AppElHostalBienTheme {
                MainNav(
                    authViewModel = authViewModel,
                    roomsViewModel = roomsViewModel,
                    bookingsViewModel = bookingsViewModel,
                    ownerViewModel = ownerViewModel
                )
            }
        }
    }

    private suspend fun seedOwnerAccount() {
        try {
            val ownerEmail = "admin@hostal.com"
            val existingOwner = database.clientDao().getClientByEmail(ownerEmail)
            if (existingOwner == null) {
                val owner = ClientEntity(
                    id = 0,
                    email = ownerEmail,
                    password = "admin123",
                    name = "Administrador",
                    isOwner = true
                )
                database.clientDao().insertOwner(owner)
                android.util.Log.d("MainActivity", "Owner account created successfully")
            } else {
                android.util.Log.d("MainActivity", "Owner account already exists")
            }
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "Error seeding owner account", e)
        }
    }
}

@Composable
fun MainNav(
    authViewModel: AuthViewModel,
    roomsViewModel: RoomsViewModel,
    bookingsViewModel: BookingsViewModel,
    ownerViewModel: OwnerViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "rooms"
    ) {
        composable("rooms") {
            RoomsScreen(
                navController = navController,
                authViewModel = authViewModel,
                roomsViewModel = roomsViewModel
            )
        }

        composable(
            route = "room/{roomId}",
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

        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("register") {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("bookings") {
            BookingsScreen(
                navController = navController,
                authViewModel = authViewModel,
                bookingsViewModel = bookingsViewModel
            )
        }

        // Owner screens
        composable("owner_dashboard") {
            OwnerDashboardScreen(
                navController = navController,
                authViewModel = authViewModel,
                ownerViewModel = ownerViewModel
            )
        }

        composable("add_room") {
            AddRoomScreen(
                navController = navController,
                ownerViewModel = ownerViewModel
            )
        }

        composable("booking_history") {
            BookingHistoryScreen(
                navController = navController,
                ownerViewModel = ownerViewModel
            )
        }
    }
}
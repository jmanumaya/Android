package com.example.appelhostal.navigation

sealed class Routes(val route: String) {
    object Rooms : Routes("rooms")
    object RoomDetail : Routes("room/{roomId}") {
        fun createRoute(roomId: Long) = "room/$roomId"
    }
    object Login : Routes("login")
    object Register : Routes("register")
    object Bookings : Routes("bookings")
}

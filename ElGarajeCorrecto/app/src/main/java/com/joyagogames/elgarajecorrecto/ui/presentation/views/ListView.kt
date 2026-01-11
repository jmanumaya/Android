package com.joyagogames.elgarajecorrecto.ui.presentation.views

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joyagogames.elgarajecorrecto.ui.presentation.GarageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListView(navController: NavController, vm: GarageViewModel, activity: Activity) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("José Manuel - Garaje") },
                actions = {
                    DropdownMenuDemo(navController, activity)
                }
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(120.dp),
            contentPadding = it
        ) {
            items(vm.spots) { spot ->
                val color = if (spot.isFree) Color.Green else Color.Red

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            vm.selectSpot(spot)
                            navController.navigate(if (spot.isFree) "entry" else "detail")
                        },
                    colors = CardDefaults.cardColors(containerColor = color)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Plaza ${spot.number}")
                        if (!spot.isFree) Text(spot.plate!!)
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuDemo(navController: NavController, activity: Activity) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Ajustes") }, onClick = {
                navController.navigate("settings")
            })
            DropdownMenuItem(text = { Text("Salir") }, onClick = {
                activity.finish()
            })
        }
    }
}


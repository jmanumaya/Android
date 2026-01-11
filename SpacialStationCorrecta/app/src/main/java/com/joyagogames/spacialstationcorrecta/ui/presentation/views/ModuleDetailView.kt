package com.joyagogames.spacialstationcorrecta.ui.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joyagogames.spacialstationcorrecta.ui.presentation.StationViewModel

@Composable
fun ModuleDetailView(navController: NavController, vm: StationViewModel) {

    val module = vm.selectedModule ?: return

    Column(Modifier.padding(20.dp)) {

        Text("Módulo ${module.id}")
        Text("Peso total: ${module.pesoTotal()} kg")

        LazyColumn {
            items(module.supplies) {
                Text("${it.tipo.name} → ${it.pesoTotal} kg")
            }
        }

        Button(onClick = {
            vm.sendModule(module.id)
            navController.navigate("modules")
        }) {
            Text("Enviar módulo")
        }

        Button(onClick = { navController.navigate("modules") }) {
            Text("Volver")
        }
    }
}

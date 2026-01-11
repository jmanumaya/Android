package com.joyagogames.almacenrepartoscorrecto.ui.presentation.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joyagogames.almacenrepartoscorrecto.domain.entities.Article
import com.joyagogames.almacenrepartoscorrecto.ui.presentation.WarehouseViewModel
import kotlin.random.Random

@Composable
fun CreateArticleView(navController: NavController, vm: WarehouseViewModel) {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var units by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var box by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {

        Text("José Manuel - Nuevo artículo")

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = units, onValueChange = { units = it }, label = { Text("Unidades") })
        OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Peso por unidad") })
        OutlinedTextField(value = box, onValueChange = { box = it }, label = { Text("Caja (1-6)") })

        Button(onClick = {
            if (name.isBlank() || units.isBlank() || weight.isBlank() || box.isBlank()) {
                Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
                return@Button
            }

            val article = Article(
                id = Random.nextInt(),
                name = name,
                units = units.toInt(),
                weightPerUnit = weight.toDouble()
            )

            val success = vm.addArticle(box.toInt(), article)

            if (!success) {
                Toast.makeText(context, "La caja supera los 21kg", Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate("boxes")
            }
        }) {
            Text("Guardar")
        }

        Button(onClick = {
            navController.navigate("boxes")
        }) {
            Text("Cancelar")
        }
    }
}

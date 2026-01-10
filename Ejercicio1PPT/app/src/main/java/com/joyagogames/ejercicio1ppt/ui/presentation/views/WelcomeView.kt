package com.joyagogames.ejercicio1ppt.ui.presentation.views

import android.widget.Toast
import com.joyagogames.ejercicio1ppt.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.joyagogames.ejercicio1ppt.ui.presentation.GameViewModel
import com.joyagogames.ejercicio1ppt.ui.theme.DarkGreen
import com.joyagogames.ejercicio1ppt.ui.theme.TextPrimary
import com.joyagogames.ejercicio1ppt.ui.theme.WarningOrange

@Composable
fun WelcomeView(navController: NavController, gameViewModel: GameViewModel){

    var name by remember { mutableStateOf(gameViewModel.player.name)}
    var showError by remember { mutableStateOf(false) }

    val toast = Toast.makeText(LocalContext.current, "Debes rellenar los campos", Toast.LENGTH_SHORT)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_app),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(5.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "¡PIEDRA, PAPEL, TIJERAS!",
                fontSize = 36.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
            )

            Text(
                "Introduce tu nombre para continuar",
                fontSize = 20.sp,
                color = WarningOrange,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .shadow(10.dp, shape = RoundedCornerShape(12.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                    .size(250.dp, 180.dp)
                    .border(4.dp, color = Color.White, shape = RoundedCornerShape(12.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        name = newValue
                        showError = false
                    },
                    placeholder = { Text("Tu Nombre:") },
                    isError = showError,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(bottom = 5.dp)
                )

                if (name == "") {
                    showError = true
                    Text(
                        text = "Debes rellenar tu nombre.",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Default
                    )
                }

                Button(
                    onClick = {
                        if (!showError) {
                            gameViewModel.setName(name)
                            navController.navigate("mainGame")
                        } else {
                            showError = true;
                            toast.show()
                        }
                    },
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .width(150.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen
                    )
                ) {
                    Text("¡JUGAR!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Default)
                }
            }
        }
    }
}
package com.joyagogames.ejercicio1ppt.ui.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.joyagogames.ejercicio1ppt.R
import com.joyagogames.ejercicio1ppt.data.models.Jugada
import com.joyagogames.ejercicio1ppt.ui.presentation.GameViewModel
import com.joyagogames.ejercicio1ppt.ui.theme.DarkGreen
import com.joyagogames.ejercicio1ppt.ui.theme.Pink40
import com.joyagogames.ejercicio1ppt.ui.theme.TextPrimary

@Composable
fun FinnishGameView(navController: NavController, gameViewModel: GameViewModel){

    val haGanado = gameViewModel.game.puntosPlayer > gameViewModel.game.puntosIA

    val resultado = if (haGanado) "¡VICTORIA!" else "¡DERROTA!"

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

            Image(
                painter = if (haGanado) {
                    painterResource(id = R.drawable.copa)
                } else {
                    painterResource(id = R.drawable.derrota)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .padding(5.dp)
            )

            Text(
                text = resultado,
                fontSize = 36.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
            )

            Text(
                text = if (haGanado) {
                    "${gameViewModel.player.name.uppercase()} HA GANADO LA PARTIDA"
                } else {
                    "${gameViewModel.ia.name.uppercase()} HA GANADO LA PARTIDA"
                },
                fontSize = 24.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
            )

            Text(
                text = "${gameViewModel.player.name}: ${gameViewModel.game.puntosPlayer} - ${gameViewModel.ia.name}: ${gameViewModel.game.puntosIA}",
                fontSize = 22.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Button(
                    onClick = {
                        gameViewModel.resetGame();
                        navController.navigate("mainGame")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGreen
                    )
                ) {
                    Text("Volver a jugar")
                }
                Button(
                    onClick = {
                        gameViewModel.newGame();
                        navController.navigate("welcomeView")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Pink40
                    )
                ) {
                    Text("Salir")
                }
            }
        }
    }
}

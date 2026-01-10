package com.joyagogames.ejercicio1ppt.ui.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.joyagogames.ejercicio1ppt.R
import com.joyagogames.ejercicio1ppt.data.models.Jugada
import com.joyagogames.ejercicio1ppt.data.models.Resultado
import com.joyagogames.ejercicio1ppt.ui.presentation.GameViewModel
import com.joyagogames.ejercicio1ppt.ui.theme.ErrorRed
import com.joyagogames.ejercicio1ppt.ui.theme.TextPrimary
import com.joyagogames.ejercicio1ppt.ui.theme.WarningOrange

@Composable
fun MainGame(navController: NavController, gameViewModel: GameViewModel){

    val textoResultado = when (gameViewModel.game.resultado) {
        Resultado.VICTORIA -> "¡Has ganado!"
        Resultado.DERROTA -> "¡Has perdido!"
        Resultado.EMPATE -> "Empate"
        Resultado.SIN_RESULTADO -> "¿?"
    }

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(TextPrimary, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("Tú: ${gameViewModel.player.name}", color = Color.Black)
                }

                Box(
                    modifier = Modifier
                        .background(TextPrimary, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("IA: ${gameViewModel.ia.name}", color = Color.Black)
                }
            }
            Text(
                "¡PIEDRA, PAPEL, TIJERAS!",
                fontSize = 36.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
            )

            GameResultCard(
                playerImage = gameViewModel.playerJugada?.imageRes ?: R.drawable.dado,
                aiImage = gameViewModel.aiJugada?.imageRes ?: R.drawable.dado,
                resultText = textoResultado,
                roundText = "Ronda: ${gameViewModel.game.rondaActual}/${gameViewModel.game.rondas}"
            )

            Text(
                "ELIGE TU JUGADA",
                fontSize = 20.sp,
                color = WarningOrange,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Default,
                textAlign = TextAlign.Center
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                PlayOption(R.drawable.piedra) {
                    gameViewModel.setJugada(Jugada.PIEDRA)
                }

                PlayOption(R.drawable.papel) {
                    gameViewModel.setJugada(Jugada.PAPEL)
                }

                PlayOption(R.drawable.tijera) {
                    gameViewModel.setJugada(Jugada.TIJERA)
                }
                PlayOption(R.drawable.dado) {
                    val randomJugada = Jugada.values().random()
                    gameViewModel.setJugada(randomJugada)
                }
            }
        }
    }
    if(gameViewModel.game.finalizar){
        navController.navigate("finnishGameView")
    }
}

@Composable
fun GameResultCard(
    playerImage: Int,
    aiImage: Int,
    resultText: String,
    roundText: String
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(2.dp, Color.LightGray, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Jugador
            PlayerCard("¡Tú!", playerImage, WarningOrange, Modifier.weight(1f))

            // Resultado central
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = resultText,
                    color = ErrorRed,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = roundText,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            // IA
            PlayerCard("¡IA!", aiImage, WarningOrange, Modifier.weight(1f))
        }
    }
}

@Composable
fun PlayerCard(
    label: String,
    imageRes: Int,
    labelColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .border(4.dp, Color.White, RoundedCornerShape(12.dp))
        )
        Text(
            text = label,
            color = labelColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun PlayOption(
    imageRes: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(70.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

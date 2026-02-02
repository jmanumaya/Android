package com.joyagogames.ejercicio1ppt.ui.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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
import com.joyagogames.ejercicio1ppt.data.GameEntity
import com.joyagogames.ejercicio1ppt.domain.entities.GameHistory
import com.joyagogames.ejercicio1ppt.ui.presentation.GameViewModel
import com.joyagogames.ejercicio1ppt.ui.theme.DarkGreen
import com.joyagogames.ejercicio1ppt.ui.theme.TextPrimary
import com.joyagogames.ejercicio1ppt.ui.theme.WarningOrange

@Composable
fun HistoryView(navController: NavController, gameViewModel: GameViewModel) {

    val listaGames by gameViewModel.listaGames.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_app),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().blur(5.dp)
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "HISTORIAL DE PARTIDAS",
                fontSize = 30.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 40.dp, bottom = 20.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(listaGames) { game ->
                    GameHistoryItem(game)
                }
            }

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                modifier = Modifier.padding(bottom = 20.dp).width(200.dp)
            ) {
                Text("VOLVER", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun GameHistoryItem(game: GameHistory) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(2.dp, WarningOrange, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${game.player} vs ${game.ia}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 18.sp
                )
                Text(
                    text = "Resultado: ${game.resultado}",
                    color = if (game.resultado == "VICTORIA") DarkGreen else Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = "${game.puntosPlayer} - ${game.puntosIA}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = WarningOrange
            )
        }
    }
}
package com.ayozera.turnpoints.activities

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.ui.theme.Fondo
import com.ayozera.turnpoints.ui.theme.LetraOscura
import com.ayozera.turnpoints.ui.theme.Rojo
import com.ayozera.turnpoints.ui.theme.jugador9
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


@Composable
fun ShowInformation(navController: NavHostController, game: String) {

    val match = game.lowercase()
    val context = LocalContext.current
    val fileContent = readFile("gamesInformation/$match.txt", context)
    val resourceId = context.resources.getIdentifier(match, "drawable", context.packageName)

    Column(modifier = Modifier.background(color = Fondo)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        ) {

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Box(
                    modifier = Modifier
                        .background(color = jugador9)
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        modifier = Modifier.align(Alignment.Center),
                        tint = Color.White
                    )
                }
            }

            Text(
                text = match.uppercase(),
                fontSize = 32.sp,
                fontFamily = FontFamily.Cursive,
                color = LetraOscura,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Box() {
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = "juego de $match",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp)
                    .padding(16.dp)
                    .border(border = BorderStroke(width = 1.dp, color = Rojo))
            )
        }
        Column(
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .verticalScroll(rememberScrollState(), enabled = true)
        ) {
            Text(text = "Estadísticas")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = fileContent)
        }
    }
}


fun readFile(game: String, context: Context): String {
    var fileContent = ""

    try {
        val assetManager = context.assets
        val inputStream = assetManager.open(game)
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.forEachLine { line ->
            if (line.isNotBlank()) {
                fileContent += line + "\n"
            }
            println(fileContent)
        }
        reader.close()

    } catch (e: IOException) {
        e.printStackTrace()
    }

    return fileContent
}


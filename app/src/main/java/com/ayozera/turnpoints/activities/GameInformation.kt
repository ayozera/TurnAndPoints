package com.ayozera.turnpoints.activities

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.ui.theme.Fondo
import com.ayozera.turnpoints.ui.theme.LetraClara
import com.ayozera.turnpoints.ui.theme.LetraOscura
import com.ayozera.turnpoints.ui.theme.Rojo
import com.ayozera.turnpoints.ui.theme.jugador9
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@Composable
fun ShowInformation(navController: NavHostController, game: String) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val match = game.lowercase()

    if (isPortrait) {
        ShowInformationInPortraitMode(navController, match)
    } else {
        ShowInformationInLandscapeMode(navController, match)
    }
}

@Composable
fun ShowInformationInPortraitMode(
    navController: NavHostController,
    match: String
) {
    Column(modifier = Modifier.background(color = Fondo)) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LetraClara)
                .padding(16.dp)
        ) {
            ArrowBackButton(navController)
            GameTitle(match)
        }
        GameImage(match)
        GameInformation(match)
    }
}


@Composable
fun ShowInformationInLandscapeMode(
    navController: NavHostController,
    game: String
) {
    Row(modifier = Modifier.background(color = Fondo)) {
        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp)
            ) {
                ArrowBackButton(navController)
                GameTitle(game)
            }
            GameImage(game)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            GameInformation(game)
        }
    }
}

@Composable
fun ArrowBackButton(navController: NavHostController) {
    Box() {
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
    }
}

@Composable
fun GameTitle(game: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = game.uppercase(),
            fontSize = 32.sp,
            fontFamily = FontFamily.Cursive,
            color = LetraOscura,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun GameImage(game: String) {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(game, "drawable", context.packageName)
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = "imagen del juego $game",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp)
                .padding(16.dp)
                .border(border = BorderStroke(width = 1.dp, color = Rojo))
        )
    }
}

@Composable
fun GameInformation(game: String) {
    val context = LocalContext.current
    val fileContent = readFile("gamesInformation/$game.txt", context)
    Column(
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .verticalScroll(rememberScrollState(), enabled = true)
    ) {
        Text(
            text = fileContent,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = LetraOscura,
            fontWeight = FontWeight.Bold,
        )
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


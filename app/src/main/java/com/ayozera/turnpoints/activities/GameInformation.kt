package com.ayozera.turnpoints.activities

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.R
import com.ayozera.turnpoints.ui.theme.Rojo
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


@Composable
fun showInformation(navController: NavHostController) {
    val fileContent = readFile("gamesInformation/monopoly.txt", LocalContext.current)


    Box(
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Nombre del juego",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Box() {
        Image(
            painter = painterResource(id = R.drawable.monopoly),
            contentDescription = "Tablero del juego de monopoly",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(16.dp)
                .border(border = BorderStroke(width = 1.dp, color = Rojo))
        )
    }
    Column(
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Estadísticas")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = fileContent)
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
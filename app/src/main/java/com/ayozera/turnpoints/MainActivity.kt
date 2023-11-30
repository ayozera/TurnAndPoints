package com.ayozera.turnpoints

import android.os.Build
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.activities.PantallaNueva
import com.ayozera.turnpoints.navegacion.NavigationGraph
import com.ayozera.turnpoints.navegacion.Routs
import com.ayozera.turnpoints.ui.theme.TurnpointsTheme
import com.ayozera.turnpoints.ui.theme.Rojo
import com.ayozera.turnpoints.ui.theme.Fondo


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TurnpointsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph()
                }
            }
        }
    }
}

@Composable
fun showMainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(color = Fondo)
    ) {
        Text(text = "Bienvenido a la app para saber...")
        searchBar()
        playerCard()
        Button(
            onClick = { navController.navigate(Routs.NuevaPartida.rout) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            ),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Mostrar pantalla Nueva",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchBar() {
    val games = listOf(
        "Ajedrez",
        "Damas",
        "Backgammon",
        "Monopoly",
        "Scrabble",
        "Clue (o Cluedo)",
        "Risk",
        "Catan",
        "Carcassonne",
        "Ticket to Ride",
        "Dominion",
        "Pandemic",
        "Agricola",
        "Puerto Rico",
        "Twilight Struggle",
        "Power Grid",
        "Terra Mystica",
        "7 Wonders",
        "Splendor",
        "Codenames",
        "Azul",
        "Gloomhaven",
        "Wingspan",
        "Root",
        "Brass: Birmingham"
    )

    val searchBarPlaceHolder by remember { mutableStateOf("¿De qué fue el juego?") }
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    var filteredGames by remember { mutableStateOf(games) }

    SearchBar(
        query = query,
        onQueryChange = { newQuery ->
            query = newQuery
            filteredGames = games.filter { it.contains(newQuery, ignoreCase = true) }
        },
        onSearch = {isActive = false},
        active = isActive,
        onActiveChange = {isActive = !isActive},
        placeholder = { Text(searchBarPlaceHolder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Icono para buscar"
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                query = ""
                filteredGames = games
            }) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Icono para borrar lo escrito"
                )
            }

        },
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape
    ) {
        LazyColumn {
            items(filteredGames) {  juego ->
                TextButton(onClick = { query = juego }) {
                    Text(juego)
                }
            }
        }

    }

}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun playerCard() {

    Card(
        modifier = Modifier
            .background(color = Fondo)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = Rojo)
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar10),
                contentDescription = "avatar10",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = "Nombre del jugador",
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "VS Equipo contra el que jugó, Fecha",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "puntos", style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
            }
        }
    }

}




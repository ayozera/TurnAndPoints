package com.ayozera.turnpoints

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.models.DataUp
import com.ayozera.turnpoints.models.Match
import com.ayozera.turnpoints.navigation.NavigationGraph
import com.ayozera.turnpoints.navigation.Routs
import com.ayozera.turnpoints.ui.theme.TurnpointsTheme
import com.ayozera.turnpoints.ui.theme.Fondo
import com.ayozera.turnpoints.ui.theme.FondoSearchBar
import com.ayozera.turnpoints.ui.theme.LetraOscura
import com.ayozera.turnpoints.ui.theme.letrasSearchBar
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TurnpointsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph()
                }
            }
        }
    }
}

@Composable
fun showMainScreen(navController: NavHostController) {

    val showCheckbox = remember { mutableStateOf(false) }
    var filtroJuegos by remember { mutableStateOf("") }
    var delete by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Fondo)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,

        ) {
        Column {
            Text(
                text = "Bienvenido a Turn & points",
                fontSize = 32.sp,
                fontFamily = FontFamily.Cursive,
                color = LetraOscura,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            searchBar() {
                filtroJuegos = it
            }
            playerCard(
                showCheckbox.value,
                filtroJuegos,
                delete,
                { onClick -> navController.navigate(Routs.GameInformation.rout + "/$onClick") },
                { onDeleteClick -> delete = onDeleteClick }
            )
            Button(
                onClick = { navController.navigate(Routs.NuevaPartida.rout) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Mostrar pantalla Nueva", style = TextStyle(
                        fontSize = 20.sp, color = Color.White
                    )
                )
            }
        }
        buttonsAddAndDelete(showCheckbox) { onDeleteClick ->
            delete = onDeleteClick
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchBar(onSearchSelected: (String) -> Unit) {

    val games = DataUp.gameLoader(LocalContext.current)

    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    var filteredGames by remember { mutableStateOf(games) }

    SearchBar(
        query = query,
        onQueryChange = { newQuery ->
            query = newQuery
            filteredGames = games.filter { it.contains(newQuery, ignoreCase = true) }
        },
        onSearch = { isActive = false },
        active = isActive,
        onActiveChange = { isActive = !isActive },
        placeholder = { Text("¿De qué fue el juego?") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search, contentDescription = "Icono para buscar"
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
        LazyColumn(
            modifier = Modifier.fillMaxWidth()

        ) {
            items(filteredGames) { juego ->
                TextButton(
                    onClick = {
                        query = juego
                        onSearchSelected(juego)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .background(color = FondoSearchBar)
                        .border(
                            width = .5.dp,
                            color = Color.White,
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Icono de estrella",
                            modifier = Modifier.size(24.dp),
                            tint = letrasSearchBar
                        )

                        Text(
                            text = juego,
                            fontSize = 26.sp,
                            color = letrasSearchBar,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun playerCard(
    showCheckbox: Boolean,
    filtroJuegos: String,
    delete: Boolean,
    onClick: (String) -> Unit,
    onDeleteClick: (Boolean) -> Unit
) {
    var matches = DataUp.matchLoader(LocalContext.current)
    val players = DataUp.playerLoader(LocalContext.current)

    matches.forEach { match ->
        if (match.game.contains(filtroJuegos)) {
            var avatar = ""
            var colorFondo = Color.Red
            var isChecked by remember { mutableStateOf(false) }

            players.forEach {
                if (match.player == it.name) {
                    avatar = it.avatar
                    colorFondo = it.color
                }
            }

            Column(
                modifier = Modifier.background(color = Fondo),
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .background(color = Fondo)
                        .padding(16.dp)
                        .clickable(onClick = { onClick(match.game) })
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = colorFondo)
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val imageResourceId = LocalContext.current.resources.getIdentifier(
                            avatar,
                            "drawable",
                            LocalContext.current.packageName
                        )

                        Image(
                            painter = painterResource(id = imageResourceId),
                            contentDescription = "avatar",
                            modifier = Modifier
                                .size(75.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = match.player,
                                style = TextStyle(
                                    textDecoration = TextDecoration.Underline,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "VS ${match.opponent}, ${match.day}-${match.month}-${match.year}",
                                style = TextStyle(fontSize = 14.sp, color = Color.White)
                            )
                            Text(
                                text = "${match.game}: ${match.score} puntos",
                                style = TextStyle(fontSize = 14.sp, color = Color.White)
                            )
                        }

                        if (showCheckbox) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = {
                                    isChecked = it
                                    matches.remove(match)
                                    onDeleteClick(isChecked)

                                },
                                enabled = true,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.White,
                                    uncheckedColor = Color.White,
                                    checkmarkColor = Color.Red
                                )
                            )
                        }
                    }
                }
            }
        }

    }
    if(delete){
        overwrite(LocalContext.current, matches)
    }
}

fun overwrite(context: Context, matches: ArrayList<Match>){
    val file = File(context.filesDir, "matches.txt")
    val writer = BufferedWriter(FileWriter(file))
    matches.forEach {
        writer.write(it.player)
        writer.newLine()
        writer.write(it.game)
        writer.newLine()
        writer.write(it.type)
        writer.newLine()
        writer.write(it.opponent)
        writer.newLine()
        writer.write(it.score.toString())
        writer.newLine()
        writer.write(it.day.toString())
        writer.newLine()
        writer.write(it.month.toString())
        writer.newLine()
        writer.write(it.year.toString())
        writer.newLine()
    }
    writer.close()
}

@Composable
fun buttonsAddAndDelete(showCheckbox: MutableState<Boolean>, onDeleteClick: (Boolean) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExtendedFloatingActionButton(
            onClick = { /*TODO*/ }, containerColor = FondoSearchBar

        ) {
            Text(
                text = "Agregar",
                modifier = Modifier.padding(end = 15.dp),
                style = TextStyle(
                    fontSize = 20.sp, color = LetraOscura
                )
            )
            Icon(
                imageVector = Icons.Filled.AddCircle, contentDescription = "Icono para agregar"
            )
        }

        ExtendedFloatingActionButton(
            onClick = {
                showCheckbox.value = !showCheckbox.value
                onDeleteClick(!showCheckbox.value)
            },
            containerColor = FondoSearchBar,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Eliminar",
                modifier = Modifier.padding(end = 15.dp),
                style = TextStyle(
                    fontSize = 20.sp, color = LetraOscura
                )
            )
            Icon(
                imageVector = Icons.Filled.Delete, contentDescription = "Icono para eliminar"
            )
        }
    }
}





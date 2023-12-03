package com.ayozera.turnpoints.activities

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.ayozera.turnpoints.models.Player
import com.ayozera.turnpoints.navigation.Routs
import com.ayozera.turnpoints.ui.theme.Fondo
import com.ayozera.turnpoints.ui.theme.FondoSearchBar
import com.ayozera.turnpoints.ui.theme.LetraClara
import com.ayozera.turnpoints.ui.theme.LetraOscura
import com.ayozera.turnpoints.ui.theme.PurpleGrey40
import com.ayozera.turnpoints.ui.theme.letrasSearchBar

@Composable
fun ShowMainScreen(navController: NavHostController) {

    val showCheckbox = rememberSaveable { mutableStateOf(false) }
    var filtroJuegos = rememberSaveable { mutableStateOf("") }
    val delete = rememberSaveable { mutableStateOf(false) }
    var matches = DataUp.matchLoader(LocalContext.current)
    val players = DataUp.playerLoader(LocalContext.current)
    var matchesDeleted = rememberSaveable { mutableStateOf(ArrayList<Match>()) }

    if (delete.value) {
        matchesDeleted.value.forEach {
            matches.remove(it)
        }
        DataUp.overwrite(LocalContext.current, matches)
    }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        MainScreenPortraitMode(
            showCheckbox,
            filtroJuegos,
            delete,
            matches,
            players,
            navController,
            matchesDeleted
        )
    } else {
        MainScreenLandscapeMode(
            showCheckbox,
            filtroJuegos,
            delete,
            matches,
            players,
            navController,
            matchesDeleted
        )
    }
}

@Composable
fun MainScreenPortraitMode(
    showCheckbox: MutableState<Boolean>,
    filtroJuegos: MutableState<String>,
    delete: MutableState<Boolean>,
    matches: ArrayList<Match>,
    players: List<Player>,
    navController: NavHostController,
    matchesDeleted: MutableState<ArrayList<Match>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Fondo),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Column (Modifier.fillMaxWidth()
                .background(color = LetraClara)){
            WelcomeText()

            SearchBar() {
                filtroJuegos.value = it
            }
            }
            ShowCardsColumn(
                matches,
                players,
                showCheckbox,
                filtroJuegos.value,
                navController,
                matchesDeleted
            )
        }
        ButtonsAddAndDelete(navController, showCheckbox) { onDeleteClick ->
            delete.value = onDeleteClick
        }
    }
}


@Composable
fun MainScreenLandscapeMode(
    showCheckbox: MutableState<Boolean>,
    filtroJuegos: MutableState<String>,
    delete: MutableState<Boolean>,
    matches: ArrayList<Match>,
    players: List<Player>,
    navController: NavHostController,
    matchesDeleted: MutableState<ArrayList<Match>>
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Fondo),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,

            ) {
            Row (Modifier.fillMaxWidth()
                .background(color = LetraClara)){

                WelcomeText()
            }
            SearchBar() {
                filtroJuegos.value = it
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.3f))
            ButtonsAddAndDelete(navController, showCheckbox) { onDeleteClick ->
                delete.value = onDeleteClick
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            ShowCardsColumn(
                matches,
                players,
                showCheckbox,
                filtroJuegos.value,
                navController,
                matchesDeleted
            )
        }
    }
}

@Composable
fun WelcomeText() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Bienvenido a Turn & points",
            fontSize = 32.sp,
            fontFamily = FontFamily.Cursive,
            color = LetraOscura,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Composable
fun ShowCardsColumn(
    matches: ArrayList<Match>,
    players: List<Player>,
    showCheckbox: MutableState<Boolean>,
    filtroJuegos: String,
    navController: NavHostController,
    matchesDeleted: MutableState<ArrayList<Match>>
) {
    LazyColumn {
        items(matches) { match ->
            PlayerCard(
                showCheckbox.value,
                filtroJuegos,
                match,
                players,
                { onClick -> navController.navigate(Routs.GameInformation.rout + "/$onClick") },
                { onChekedClick ->
                    run {
                        var found = false
                        matchesDeleted.value.forEach {
                            if (it == onChekedClick) {
                                found = true
                            }
                        }
                        if (!found) {
                            matchesDeleted.value.add(onChekedClick)
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onSearchSelected: (String) -> Unit) {

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
                onSearchSelected("")
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
                        isActive = false
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

@Composable
fun PlayerCard(
    showCheckbox: Boolean,
    filtroJuegos: String,
    match: Match,
    players: List<Player>,
    onClick: (String) -> Unit,
    onChekedClick: (Match) -> Unit
) {

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
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(color = Fondo)
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
                            .size(100.dp)
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
                                fontSize = 24.sp,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "VS ${match.opponent}, ${match.day}-${match.month}-${match.year}",
                            style = TextStyle(fontSize = 18.sp, color = Color.White)
                        )
                        Text(
                            text = "${match.game}: ${match.score} puntos",
                            style = TextStyle(fontSize = 18.sp, color = Color.White)
                        )
                    }

                    if (showCheckbox) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                isChecked = it
                                onChekedClick(match)

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

@Composable
fun ButtonsAddAndDelete(
    navController: NavHostController,
    showCheckbox: MutableState<Boolean>,
    onDeleteClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExtendedFloatingActionButton(
            onClick = {
                if (!showCheckbox.value) {
                    navController.navigate(Routs.NewMatch.rout)
                }
            }, containerColor = if (!showCheckbox.value) FondoSearchBar else PurpleGrey40
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

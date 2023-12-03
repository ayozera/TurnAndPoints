package com.ayozera.turnpoints.activities

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.models.DataUp
import com.ayozera.turnpoints.models.DataUp.Companion.gameLoader
import com.ayozera.turnpoints.models.DataUp.Companion.playerLoader
import com.ayozera.turnpoints.models.GameType
import com.ayozera.turnpoints.models.Match
import com.ayozera.turnpoints.models.Player
import com.ayozera.turnpoints.ui.theme.Fondo
import com.ayozera.turnpoints.ui.theme.FondoSearchBar
import com.ayozera.turnpoints.ui.theme.LetraClara
import com.ayozera.turnpoints.ui.theme.LetraOscura
import com.ayozera.turnpoints.ui.theme.jugador9
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewMatch(navController: NavHostController) {


    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT


    if (isPortrait) {
        NewMatchInPortraitMode(navController)
    } else {
        NewMatchInLandscapeMode(navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewMatchInPortraitMode(navController: NavHostController) {
    val context = LocalContext.current
    val players = playerLoader(context)
    val games = gameLoader(context)
    val gameTypes = listOf(
        GameType.BOARD,
        GameType.CARDS,
        GameType.ROLE_PLAYING,
        GameType.STRATEGY,
        GameType.TRIVIA
    )
    var player = ""
    var game = ""
    var type = GameType.BOARD
    var score = 0
    var opponent = ""
    var day = 0
    var month = 0
    var year = 0
    var openDialog by remember { mutableStateOf(false) }
    var openDialogError by remember { mutableStateOf(false) }
    val delay = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(color = Fondo)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LetraClara)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArrowBackNM(navController)
            ShowTitle()
        }

        Spacer(modifier = Modifier.size(30.dp))
        PlayerSelection(players) { onPlayerSelected ->
            player = onPlayerSelected
        }
        Spacer(modifier = Modifier.size(30.dp))
        GameSelection(games) { onGameSelected ->
            game = onGameSelected
        }
        Spacer(modifier = Modifier.size(30.dp))
        TypeSelection(gameTypes) { onTypeSelected ->
            type = onTypeSelected
        }
        Spacer(modifier = Modifier.size(30.dp))
        ScoreSelection() { onScoreSelection ->
            score = onScoreSelection
        }
        Spacer(modifier = Modifier.size(30.dp))
        OpponentSelection(players) { onOpponentSelected ->
            opponent = onOpponentSelected
        }
        Spacer(modifier = Modifier.size(30.dp))
        DatePickerScreen() { onDateSelected ->
            day = onDateSelected.dayOfMonth
            month = onDateSelected.monthValue
            year = onDateSelected.year
        }
        Spacer(modifier = Modifier.size(30.dp))
        ButtonSave() {
            if (player.isNotBlank() && game.isNotBlank() && opponent.isNotBlank() && year != 0) {
                players.forEach { actual ->
                    if (actual.name == player)
                        DataUp.writer(
                            Match(
                                actual.name,
                                game,
                                type.name,
                                opponent,
                                score,
                                day,
                                month,
                                year
                            ), context
                        )
                }

                delay.launch(Dispatchers.Main) {
                    openDialog = true
                    delay(3000)
                    navController.popBackStack()
                }
            } else {
                openDialogError = true
            }
        }
        Spacer(modifier = Modifier.size(60.dp))

        if (openDialog) {
            AlertDialog() {
                openDialog = false
            }
        }

        if (openDialogError) {
            AlertDialogError() {
                openDialogError = false
            }
        }
    }
}

@Composable
fun ShowTitle() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nueva Partida".uppercase(),
            fontSize = 28.sp,
            fontFamily = FontFamily.Cursive,
            color = LetraOscura,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ArrowBackNM(navController: NavHostController) {

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewMatchInLandscapeMode(navController: NavHostController) {
    val context = LocalContext.current
    val players = playerLoader(context)
    val games = gameLoader(context)
    val gameTypes = listOf(
        GameType.BOARD,
        GameType.CARDS,
        GameType.ROLE_PLAYING,
        GameType.STRATEGY,
        GameType.TRIVIA
    )
    var player = ""
    var game = ""
    var type = GameType.BOARD
    var score = 0
    var opponent = ""
    var day = 0
    var month = 0
    var year = 0
    var openDialog by remember { mutableStateOf(false) }
    var openDialogError by remember { mutableStateOf(false) }
    val delay = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(color = Fondo)
            .verticalScroll(rememberScrollState())
            .padding(46.dp,0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        ) {
            ArrowBackNM(navController)
            ShowTitle()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .weight(1f)
            ) {
                PlayerSelection(players) { onPlayerSelected ->
                    player = onPlayerSelected
                }
                Spacer(modifier = Modifier.height(8.dp))
                OpponentSelection(players) { onOpponentSelected ->
                    opponent = onOpponentSelected
                }
                Spacer(modifier = Modifier.height(8.dp))
                GameSelection(games) { onGameSelected ->
                    game = onGameSelected
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                TypeSelection(gameTypes) { onTypeSelected ->
                    type = onTypeSelected
                }
            }
        }

        ScoreSelection() { onScoreSelection ->
            score = onScoreSelection
        }

        DatePickerScreen() { onDateSelected ->
            day = onDateSelected.dayOfMonth
            month = onDateSelected.monthValue
            year = onDateSelected.year

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonSave() {
                if (player.isNotBlank() && game.isNotBlank() && opponent.isNotBlank() && year != 0) {
                    players.forEach { actual ->
                        if (actual.name == player)
                            DataUp.writer(
                                Match(
                                    actual.name,
                                    game,
                                    type.name,
                                    opponent,
                                    score,
                                    day,
                                    month,
                                    year
                                ), context
                            )
                    }

                    delay.launch(Dispatchers.Main) {
                        openDialog = true
                        delay(3000)
                        navController.popBackStack()
                    }
                } else {
                    openDialogError = true
                }
            }
        }

        if (openDialog) {
            AlertDialog() {
                openDialog = false
            }
        }

        if (openDialogError) {
            AlertDialogError() {
                openDialogError = false
            }
        }
    }
}

@Composable
fun PlayerSelection(players: List<Player>, onPlayerSelection: (String) -> Unit) {
    var expandedPlayer by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf("") }
    Column() {
        Text(
            text = "Introduce el nombre del jugador",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = LetraOscura,
            fontWeight = FontWeight.Bold,
        )
        TextField(value = selectedPlayer,
            onValueChange = {},
            label = { Text("Seleccione un jugador") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedPlayer = true }) {
                    Icon(
                        Icons.Default.ArrowDropDown, contentDescription = "abrir lista de jugadores"
                    )
                }
                DropdownMenu(expanded = expandedPlayer,
                    onDismissRequest = { expandedPlayer = false }) {
                    players.forEach { player ->
                        DropdownMenuItem(text = { Text(text = player.name) }, onClick = {
                            selectedPlayer = player.name
                            expandedPlayer = false
                            onPlayerSelection(player.name)
                        })
                    }
                }
            }
        )
    }

}

@Composable
fun GameSelection(games: List<String>, onGameSelection: (String) -> Unit) {
    var expandedGame by remember { mutableStateOf(false) }
    var selectedGame by remember { mutableStateOf("") }
    Column {

        Text(
            text = "El nombre del juego",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = LetraOscura,
            fontWeight = FontWeight.Bold,
        )
        TextField(value = selectedGame,
            onValueChange = {},
            label = { Text("¿Cuál fue el juego?") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedGame = true }) {
                    Icon(
                        Icons.Default.ArrowDropDown, contentDescription = "abrir lista de jugadores"
                    )
                }
                DropdownMenu(expanded = expandedGame, onDismissRequest = { expandedGame = false }) {
                    games.forEach { game ->
                        DropdownMenuItem(text = { Text(text = game) }, onClick = {
                            selectedGame = game
                            expandedGame = false
                            onGameSelection(game)
                        })
                    }
                }
            }
        )
    }
}

@Composable
fun TypeSelection(gameTypes: List<GameType>, onTypeSelection: (GameType) -> Unit) {
    var selectedGameType by remember { mutableStateOf("") }
    Column {
        Text(
            text = "Selecciona el tipo de juego",
            modifier = Modifier.padding(63.dp, 0.dp, 0.dp, 0.dp),
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = LetraOscura,
            fontWeight = FontWeight.Bold,
        )
        gameTypes.forEach { gameType ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(selected = (gameType.name == selectedGameType),
                        onClick = { selectedGameType = gameType.name })
                    .padding(horizontal = 50.dp),
                verticalAlignment = CenterVertically
            ) {
                RadioButton(selected = (gameType.name == selectedGameType),
                    onClick = {
                        selectedGameType = gameType.name
                        onTypeSelection(gameType)
                    })
                Text(
                    text = gameType.name,
                    style = MaterialTheme.typography.bodyMedium.merge(),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ScoreSelection(onScoreSelection: (Int) -> Unit) {
    var score by remember { mutableStateOf(0f) }
    Column(
        modifier = Modifier.padding(63.dp, 0.dp, 63.dp, 0.dp)
    ) {

        Text(
            text = "Número de puntos en la partida",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = LetraOscura,
            fontWeight = FontWeight.Bold,
        )
        Slider(
            value = score,
            onValueChange = {
                score = it
                onScoreSelection(score.toInt())
            },
            valueRange = 0f..150f,
            steps = 150,
        )
        Text(
            text = "Puntuación: ${score.toInt()}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun OpponentSelection(players: List<Player>, onPlayerSelection: (String) -> Unit) {
    var expandedPlayer2 by remember { mutableStateOf(false) }
    var selectedPlayer2 by remember { mutableStateOf("") }
    Column {
        Text(
            text = "Jugador contra quien jugó",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = LetraOscura,
            fontWeight = FontWeight.Bold,
        )
        TextField(value = selectedPlayer2,
            onValueChange = {},
            label = { Text("Seleccione un jugador") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedPlayer2 = true }) {
                    Icon(
                        Icons.Default.ArrowDropDown, contentDescription = "abrir lista de jugadores"
                    )
                }
                DropdownMenu(expanded = expandedPlayer2,
                    onDismissRequest = { expandedPlayer2 = false }) {
                    players.forEach { player ->
                        DropdownMenuItem(text = { Text(text = player.name) },
                            onClick = {
                                selectedPlayer2 = player.name
                                expandedPlayer2 = false
                                onPlayerSelection(player.name)
                            }
                        )
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerScreen(onDateSelection: (LocalDate) -> Unit) {

    val dateTime = LocalDateTime.now()

    val datePickerState = remember {
        DatePickerState(
            yearRange = (2023..2024),
            initialSelectedDateMillis = null,
            initialDisplayMode = DisplayMode.Picker,
            initialDisplayedMonthMillis = null
        )
    }
    DatePicker(state = datePickerState)

    val date = datePickerState.selectedDateMillis
    date?.let {
        onDateSelection(Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate())
    }
}

@Composable
fun ButtonSave(onSave: () -> Unit) {
    Button(onClick = { onSave() },
        colors = ButtonDefaults.buttonColors(
            containerColor = FondoSearchBar)) {
        Text(text = "Guardar Partida", color = LetraOscura)
    }
}

@Composable
fun AlertDialog(onDismissClick: () -> Unit) {
    MaterialTheme {
        Column {
            AlertDialog(
                onDismissRequest = {
                    onDismissClick()
                },
                title = {
                    Text(text = "Partida guardada")
                },
                text = {
                    Text("Partida añadida correctamente")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onDismissClick()
                        }) {
                        Text("Entendido")
                    }
                }
            )
        }
    }
}

@Composable
fun AlertDialogError(onDismissClick: () -> Unit) {
    MaterialTheme {
        Column {
            AlertDialog(
                onDismissRequest = {
                    onDismissClick()
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(
                        "Se deben llenar todos los campos",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        color = LetraOscura,
                        fontWeight = FontWeight.Bold,
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onDismissClick()
                        }) {
                        Text("Entendido")
                    }
                }
            )
        }
    }
}


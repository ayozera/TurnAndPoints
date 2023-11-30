package com.ayozera.turnpoints.activities

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.modelo.DataUp
import com.ayozera.turnpoints.modelo.Match
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


//@Preview(showBackground = true, showSystemUi = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PantallaNueva(navController: NavHostController) {
    val players = listOf("Player 1", "Player 2", "Player 3")
    val games = listOf("Juego 1", "Juego 2", "Juego 3")
    val gameTypes = listOf("Type 1", "Type 2", "Type 3")
    var player = ""
    var game = ""
    var type = ""
    var score = 0
    var opponent = ""
    var day = 0
    var month = 0
    var year = 0
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
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
        OponentSelection(players) { onOpponentSelected ->
            opponent = onOpponentSelected
        }
        Spacer(modifier = Modifier.size(30.dp))
        datePickerScreen() { onDateSelected ->
            day = onDateSelected.dayOfMonth
            month = onDateSelected.monthValue
            year = onDateSelected.year
        }
        Spacer(modifier = Modifier.size(30.dp))
        ButtonSave() {
            if (player.isNotBlank() && game.isNotBlank() && type.isNotBlank() && opponent.isNotBlank() && year != 0) {
                DataUp.writer(
                    Match(
                        player,
                        game,
                        type,
                        opponent,
                        score,
                        day,
                        month,
                        year
                    ), context
                )
            }
        }
        Spacer(modifier = Modifier.size(60.dp))
    }
}

@Composable
fun PlayerSelection(players: List<String>, onPlayerSelection: (String) -> Unit) {
    var expandedPlayer by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf(players.first()) }
    Column() {

        Text(text = "Introduce el nombre del jugador")
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
                        DropdownMenuItem(text = { Text(text = player) }, onClick = {
                            selectedPlayer = player
                            expandedPlayer = false
                            onPlayerSelection(player)
                        })
                    }
                }
            })
    }
}

@Composable
fun GameSelection(games: List<String>, onGameSelection: (String) -> Unit) {
    var expandedGame by remember { mutableStateOf(false) }
    var selectedGame by remember { mutableStateOf(games.first()) }
    Column {

        Text(text = "El nombre del juego")
        TextField(value = selectedGame,
            onValueChange = {},
            label = { Text("Seleccione un jugador") },
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
            })
    }
}

@Composable
fun TypeSelection(gameTypes: List<String>, onTypeSelection: (String) -> Unit) {
    var selectedGameType by remember { mutableStateOf(gameTypes.first()) }
    Column {

        Text(
            text = "Selecciona uno o más tipos",
            modifier = Modifier.padding(63.dp, 0.dp, 0.dp, 0.dp)
        )
        gameTypes.forEach { gameType ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(selected = (gameType == selectedGameType),
                        onClick = { selectedGameType = gameType })
                    .padding(horizontal = 50.dp),
                verticalAlignment = CenterVertically
            ) {
                RadioButton(selected = (gameType == selectedGameType),
                    onClick = {
                        selectedGameType = gameType
                        onTypeSelection(gameType)
                    })
                Text(
                    text = gameType,
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

        Text(text = "Número de puntos en la partida")
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
fun OponentSelection(players: List<String>, onPlayerSelection: (String) -> Unit) {
    var expandedPlayer2 by remember { mutableStateOf(false) }
    var selectedPlayer2 by remember { mutableStateOf(players.first()) }
    Column {
        Text(text = "Jugador contra quien jugó")
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
                        DropdownMenuItem(text = { Text(text = player) },
                            onClick = {
                                selectedPlayer2 = player
                                expandedPlayer2 = false
                                onPlayerSelection(player)
                            }
                        )
                    }
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun datePickerScreen(onDateSelection : (LocalDate) -> Unit) {

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
    Button(onClick = { onSave() }) {
        Text(text = "Guardar Partida", color = Color.White)
    }
}

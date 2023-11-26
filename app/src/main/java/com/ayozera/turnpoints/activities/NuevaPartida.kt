package com.ayozera.turnpoints.activities

import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate

//@Preview(showBackground = true, showSystemUi = true)
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaNueva(navController : NavHostController) {
    val players = listOf("Player 1", "Player 2", "Player 3")
    var expandedPlayer by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf(players.first()) }

    Column {
        Text(text = "Introduce el nombre del jugador")
        TextField(
            value = selectedPlayer,
            onValueChange = {},
            label = { Text("Seleccione un jugador") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expandedPlayer = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "abrir lista de jugadores")
                }
            }
        )

        DropdownMenu(
            expanded = expandedPlayer,
            onDismissRequest = { expandedPlayer = false }
        ) {
            players.forEach { player ->
                DropdownMenuItem(
                    text = { Text(text = player) },
                    onClick = {
                        selectedPlayer = player
                        expandedPlayer = false
                    }
                )
            }
        }
        Text(text = "El nombre del juego")
        val games = listOf("Juego 1", "Juego 2", "Juego 3")
        var expandedGame by remember { mutableStateOf(false) }
        var selectedGame by remember { mutableStateOf(games.first()) }

        Column {
            Text(text = "Introduce el nombre del jugador")
            TextField(
                value = selectedGame,
                onValueChange = {},
                label = { Text("Seleccione un jugador") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expandedGame = true }) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "abrir lista de jugadores"
                        )
                    }
                }
            )

            DropdownMenu(
                expanded = expandedGame,
                onDismissRequest = { expandedGame = false }
            ) {
                games.forEach { game ->
                    DropdownMenuItem(
                        text = { Text(text = game) },
                        onClick = {
                            selectedGame = game
                            expandedGame = false
                        }
                    )
                }
            }
            val gameTypes = listOf("Type 1", "Type 2", "Type 3")
            var selectedGameType by remember { mutableStateOf(gameTypes.first()) }
            Text(text = "Selecciona uno o más tipos")
            gameTypes.forEach { gameType ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (gameType == selectedGameType),
                            onClick = { selectedGameType = gameType }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (gameType == selectedGameType),
                        onClick = { selectedGameType = gameType }
                    )
                    Text(
                        text = gameType,
                        style = MaterialTheme.typography.bodyMedium.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            var score by remember { mutableStateOf(0f) }
            Text(text = "Número de puntos que metió en la partida")
            Slider(
                value = score,
                onValueChange = { score = it },
                valueRange = 0f..100f,
                steps = 10,
                modifier = Modifier.padding(16.dp)
            )

            var expandedPlayer2 by remember { mutableStateOf(false) }
            var selectedPlayer2 by remember { mutableStateOf(players.first()) }
            Text(text = "Jugador contra quien jugó")
            TextField(
                value = selectedPlayer2,
                onValueChange = {},
                label = { Text("Seleccione un jugador") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expandedPlayer2 = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "abrir lista de jugadores")
                    }
                }
            )

            DropdownMenu(
                expanded = expandedPlayer2,
                onDismissRequest = { expandedPlayer2 = false }
            ) {
                players.forEach { player ->
                    DropdownMenuItem(
                        text = { Text(text = player) },
                        onClick = {
                            selectedPlayer2 = player
                            expandedPlayer2 = false
                        }
                    )
                }
            }
            //val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
            var selectedDate by remember { mutableStateOf(LocalDate.now()) }

        /*    DatePicker(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )*/
           /* DatePicker(
                state = datePickerState,
                onDateChanged = { date ->
                    // Handle date change
                }
            )*/
        }
    }
}


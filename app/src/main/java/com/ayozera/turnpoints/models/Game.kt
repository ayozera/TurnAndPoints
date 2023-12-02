package com.ayozera.turnpoints.models

import java.io.File

data class Game(
    val name: String,
    val numberOfPlayers: Integer,
    val type: GameType,
    val image: String,
    val description: File
)





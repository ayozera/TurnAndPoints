package com.ayozera.turnpoints.models

data class Match(
    val player: Player,
    val game: Game,
    val type: GameType,
    val opponent: Player,
    val score: Int,
    val day: Int,
    val month: Int,
    val year: Int
)
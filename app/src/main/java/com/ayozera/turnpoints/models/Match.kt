package com.ayozera.turnpoints.models

data class Match(
    val player: Player,
    val game: String,
    val type: GameType,
    val opponent: String,
    val score: Int,
    val day: Int,
    val month: Int,
    val year: Int
)
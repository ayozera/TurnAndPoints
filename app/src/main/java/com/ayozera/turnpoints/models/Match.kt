package com.ayozera.turnpoints.models

data class Match(
    val player: Player,
    val game: String,
    val type: String,
    val opponent: String,
    val score: Int,
    val day: Int,
    val month: Int,
    val year: Int
)
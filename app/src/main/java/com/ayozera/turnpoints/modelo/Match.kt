package com.ayozera.turnpoints.modelo

data class Match(
    val player: String,
    val game: String,
    val type: String,
    val opponent: String,
    val score: Int
)
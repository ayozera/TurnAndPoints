package com.ayozera.turnpoints.models

import java.util.Date

data class Match (

    val player: Player,
    val rival: Player,
    val date: Date,
    val score: Number,
    val game: Game

)

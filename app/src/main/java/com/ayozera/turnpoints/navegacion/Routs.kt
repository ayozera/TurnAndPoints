package com.ayozera.turnpoints.navegacion

sealed class Routs(val rout : String) {
    object Jugadores : Routs("jugadores")
    object NuevaPartida : Routs("nuevaPartida")
    object Juego : Routs("juego")
}
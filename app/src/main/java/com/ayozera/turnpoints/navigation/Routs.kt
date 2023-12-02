package com.ayozera.turnpoints.navigation

sealed class Routs(val rout : String) {
    object Jugadores : Routs("jugadores")
    object NuevaPartida : Routs("nuevaPartida")
    object Juego : Routs("juego")

    object GameInformation : Routs("gameInformation")
}
package com.ayozera.turnpoints.navigation

sealed class Routs(val rout : String) {
    object MainScreen : Routs("mainScreen")
    object NewMatch : Routs("newMatch")
    object Welcome : Routs("welcome")
    object Login : Routs("login")
    object GameInformation : Routs("gameInformation")
}
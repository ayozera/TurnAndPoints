package com.ayozera.turnpoints.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayozera.turnpoints.activities.PantallaNueva

@Composable
fun NavigationGraph() {
    val navController : NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routs.Jugadores.rout) {

        composable(Routs.Jugadores.rout){

        }

        composable(Routs.NuevaPartida.rout){
            PantallaNueva(navController = navController)
        }

        composable(Routs.Juego.rout){

        }
    }
}


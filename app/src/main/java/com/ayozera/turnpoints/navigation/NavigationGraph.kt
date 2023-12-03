package com.ayozera.turnpoints.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ayozera.turnpoints.activities.PantallaNueva
import com.ayozera.turnpoints.activities.ShowInformation
import com.ayozera.turnpoints.activities.ShowMainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {
    val navController : NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routs.Jugadores.rout) {

        composable(Routs.Jugadores.rout){
            ShowMainScreen(navController = navController)
        }

        composable(Routs.NuevaPartida.rout){
            PantallaNueva(navController = navController)
        }

        composable(Routs.GameInformation.rout + "/{game}", arguments = listOf(navArgument("game") {
            type = NavType.StringType })){ backStackEntry ->
            val game = backStackEntry.arguments?.getString("game")
            ShowInformation(navController = navController, game!!)
        }
    }
}


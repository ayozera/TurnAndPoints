package com.ayozera.turnpoints.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ayozera.turnpoints.activities.PantallaNueva
import com.ayozera.turnpoints.activities.showInformation
import com.ayozera.turnpoints.models.Match
import com.ayozera.turnpoints.showMainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {
    val navController : NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routs.Jugadores.rout) {

        composable(Routs.Jugadores.rout){
            showMainScreen(navController = navController)
        }

        composable(Routs.NuevaPartida.rout){
            PantallaNueva(navController = navController)
        }

        composable(Routs.GameInformation.rout + "/{game}", arguments = listOf(navArgument("game") {
            type = NavType.StringType })){ backStackEntry ->
            val game = backStackEntry.arguments?.getString("game")
            showInformation(navController = navController, game)
        }
    }
}


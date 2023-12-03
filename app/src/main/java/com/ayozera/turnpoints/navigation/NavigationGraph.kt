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
import com.ayozera.turnpoints.activities.Login
import com.ayozera.turnpoints.activities.NewMatch
import com.ayozera.turnpoints.activities.ShowInformation
import com.ayozera.turnpoints.activities.ShowMainScreen
import com.ayozera.turnpoints.activities.Welcome

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {
    val navController : NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routs.Welcome.rout) {

        composable(Routs.MainScreen.rout){
            ShowMainScreen(navController = navController)
        }

        composable(Routs.NewMatch.rout){
            NewMatch(navController = navController)
        }

        composable(Routs.Welcome.rout){
            Welcome(navController = navController)
        }
        
        composable(Routs.Login.rout){
            Login(navController = navController)
        }

        composable(Routs.GameInformation.rout + "/{game}", arguments = listOf(navArgument("game") {
            type = NavType.StringType })){ backStackEntry ->
            val game = backStackEntry.arguments?.getString("game")
            ShowInformation(navController = navController, game!!)
        }
    }
}


package com.ayozera.turnpoints.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.R
import com.ayozera.turnpoints.navigation.Routs
import com.ayozera.turnpoints.ui.theme.Fondo
import com.ayozera.turnpoints.ui.theme.LetraClara
import com.ayozera.turnpoints.ui.theme.LetraOscura

@Composable
fun Welcome(navController: NavHostController) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
            .background(color = Fondo)
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.10f)
                .background(LetraOscura)
                .fillMaxWidth()
        ) {
            Text(
                text = "Turn & Points",
                color = LetraClara,
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(0.90f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dados_removebg_preview),
                contentDescription = "Logo con unos dados"
            )
            Text(text = "Bienvenid@", color = LetraClara)
            Spacer(modifier = Modifier.padding(50.dp))
            Button(onClick = { navController.navigate(Routs.Login.rout) }) {
                Text(text = "Iniciar Sesión")
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Button(onClick = { navController.navigate(Routs.Signup.rout) }) {
                Text(text = "Crear Cuenta")
            }
        }
    }
}
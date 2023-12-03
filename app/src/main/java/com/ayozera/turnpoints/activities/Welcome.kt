package com.ayozera.turnpoints.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.R
import com.ayozera.turnpoints.navigation.Routs
import com.ayozera.turnpoints.ui.theme.LetraOscura

@Composable
fun Welcome (navController: NavHostController) {
    Text(
        text = "Turn & Points",
        modifier = Modifier
            .background(LetraOscura)
            .padding(30.dp)
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Image(painter = painterResource(id = R.drawable.dados_removebg_preview),
            contentDescription = "Logo con unos dados")
        Text(text = "Bienvenid@")
        Button(onClick = { navController.navigate(Routs.Login.rout) }) {
            Text(text = "Iniciar Sesión")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Crear Cuenta")
        }
    }
}
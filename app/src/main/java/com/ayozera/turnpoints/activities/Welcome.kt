package com.ayozera.turnpoints.activities

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.R
import com.ayozera.turnpoints.navigation.Routs
import com.ayozera.turnpoints.ui.theme.Fondo
import com.ayozera.turnpoints.ui.theme.LetraClara
import com.ayozera.turnpoints.ui.theme.LetraOscura

@Composable
fun Welcome(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        WelcomeInPortraitMode(navController)
    } else {
        WelcomeInLandscapeMode(navController)
    }
}


@Composable
fun WelcomeInPortraitMode(navController: NavHostController) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Fondo)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.10f)
                .background(LetraOscura)
                .fillMaxWidth()
        ) {
            Title()
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(1f)
        ) {
            Logo()
            Text(
                text = "Bienvenid@",
                color = LetraClara,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.padding(30.dp))

            ButtonsWelcome(navController::navigate, Routs.Login.rout, "Iniciar Sesión")
            Spacer(modifier = Modifier.padding(20.dp))
            ButtonsWelcome(navController::navigate, Routs.Signup.rout, "Crear Cuenta")
        }
    }
}


@Composable
fun WelcomeInLandscapeMode(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Fondo),
        horizontalAlignment = CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LetraOscura)
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Title()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Bienvenid@",
                color = LetraClara,
                fontSize = 32.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    //.weight(1f)
                    .fillMaxWidth(0.4f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Logo()
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ButtonsWelcome(navController::navigate, Routs.Login.rout, "Iniciar Sesión")
                Spacer(modifier = Modifier.padding(16.dp))
                ButtonsWelcome(navController::navigate, Routs.Signup.rout, "Crear Cuenta")
            }
        }
    }
}


@Composable
fun Title() {
    Text(
        text = "Turn & Points",
        color = Color.White,
        fontSize = 32.sp,
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Bold

    )
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.dados_removebg_preview),
        contentDescription = "Logo",
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
    )
}

@Composable
fun ButtonsWelcome(navigate: (String) -> Unit, route: String, text: String) {
    Button(onClick = { navigate(route) }) {
        Text(text = text)
    }
}



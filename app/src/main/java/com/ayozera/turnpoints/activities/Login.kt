package com.ayozera.turnpoints.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ayozera.turnpoints.R
import com.ayozera.turnpoints.models.DataUp
import com.ayozera.turnpoints.navigation.Routs
import com.ayozera.turnpoints.ui.theme.Fondo
import com.ayozera.turnpoints.ui.theme.LetraClara
import com.ayozera.turnpoints.ui.theme.LetraOscura
import com.ayozera.turnpoints.ui.theme.jugador9

@Composable
fun Login(navController: NavHostController) {

    var textUser by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    val keys = DataUp.credentialLoader(LocalContext.current)
    var openDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .background(color = Fondo),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.10f)
                .background(LetraClara)
                .fillMaxWidth()
        ) {
            ArrowBackWelcome(navController)
            Text(
                text = "Turn & Points",
                modifier = Modifier.padding(start = 50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.dados_removebg_preview),
                contentDescription = "Logo con unos dados"
            )
        }
        Column (
            modifier = Modifier.fillMaxHeight(0.9f)
                .background(color = Fondo),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Introduzca nombre de usuario")
            TextField(
                value = textUser,
                onValueChange = { textUser = it },
                shape = RoundedCornerShape(30.dp)
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = "Introduzca su contraseña")
            TextField(
                value = textPassword,
                onValueChange = { textPassword = it },
                shape = RoundedCornerShape(30.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.padding(50.dp))
            Button(onClick = {
                var access = false
                keys.forEach {
                    if (it.user == textUser && it.password == textPassword) {
                        access = true
                    }
                }
                if (access) {
                    navController.navigate(Routs.MainScreen.rout)
                } else {
                    openDialog = true
                }

            }) {
                Text(text = "Iniciar Sesión")
            }
        }

        if (openDialog) {
            AccessDialogError {
                openDialog = false
            }
        }
    }
}

@Composable
fun ArrowBackWelcome(navController: NavHostController) {

    Box() {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.CenterStart)
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = jugador9)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun AccessDialogError(onDismissClick: () -> Unit) {
    MaterialTheme {
        Column {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = {
                    onDismissClick()
                },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text("Asegúrese de que el usuario y la contraseña son correctas")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onDismissClick()
                        }) {
                        Text("Entendido")
                    }
                },
            )
        }
    }
}
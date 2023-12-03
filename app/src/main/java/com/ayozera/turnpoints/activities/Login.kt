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
import androidx.compose.material3.Divider
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
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.10f)
                .background(LetraClara)
                .fillMaxWidth()
        ) {
            Text(
                text = "Turn & Points",
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
            TextField(value = textUser, onValueChange = { textUser = it })
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = "Introduzca su contraseña")
            TextField(
                value = textPassword,
                onValueChange = { textPassword = it },
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
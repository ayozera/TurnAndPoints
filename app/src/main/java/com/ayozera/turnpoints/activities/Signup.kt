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
import com.ayozera.turnpoints.models.Credential
import com.ayozera.turnpoints.models.DataUp
import com.ayozera.turnpoints.models.DataUp.Companion.writeCredential
import com.ayozera.turnpoints.navigation.Routs
import com.ayozera.turnpoints.ui.theme.Fondo
import com.ayozera.turnpoints.ui.theme.LetraClara
import com.ayozera.turnpoints.ui.theme.LetraOscura

@Composable
fun Signup(navController: NavHostController) {
    var textUser by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var textPassword2 by remember { mutableStateOf("") }
    val keys = DataUp.credentialLoader(LocalContext.current)
    var openDialogExits by remember { mutableStateOf(false) }
    var openDialogPasswords by remember { mutableStateOf(false) }

    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
            .background(color = Fondo)
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
                color = LetraOscura
            )
            Image(
                painter = painterResource(id = R.drawable.dados_removebg_preview),
                contentDescription = "Logo con unos dados"
            )
        }


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(0.90f)
        ) {

            Text(text = "Introduzca nombre de usuario")
            TextField(value = textUser, onValueChange = { textUser = it })
            Spacer(modifier = Modifier.padding(15.dp))
            Text(text = "Introduzca su contraseña")
            TextField(
                value = textPassword,
                onValueChange = { textPassword = it },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Text(text = "Repita su contraseña")
            TextField(
                value = textPassword2,
                onValueChange = { textPassword2 = it },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.padding(50.dp))
            Button(onClick = {
                var alreadyExits = false
                keys.forEach {
                    if (it.user == textUser) {
                        alreadyExits = true
                    }
                }
                if (!alreadyExits) {
                    if (textPassword == textPassword2) {
                        writeCredential(Credential(textUser, textPassword), context)
                        navController.navigate(Routs.MainScreen.rout)
                    } else {
                        openDialogPasswords = true
                    }
                } else {
                    openDialogExits = true
                }

            }) {
                Text(text = "Crear Cuenta")
            }
        }

        if (openDialogExits) {
            UserAlreadyExitsDialog {
                openDialogExits = false
            }
        }
        if (openDialogPasswords) {
            SignupDialogError {
                openDialogPasswords = false
            }
        }
    }
}

@Composable
fun SignupDialogError(onDismissClick: () -> Unit) {
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
                    Text("Las contraseñas no coinciden")
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

@Composable
fun UserAlreadyExitsDialog(onDismissClick: () -> Unit) {
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
                    Text("El nombre de usuario ya está en uso")
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
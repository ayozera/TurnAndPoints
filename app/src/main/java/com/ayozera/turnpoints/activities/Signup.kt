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
import androidx.compose.ui.graphics.Color
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
import com.ayozera.turnpoints.ui.theme.jugador9

@Composable
fun Signup(navController: NavHostController) {
    var textUser by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var textPassword2 by remember { mutableStateOf("") }
    val keys = DataUp.credentialLoader(LocalContext.current)
    var openDialogExits by remember { mutableStateOf(false) }
    var openDialogPasswords by remember { mutableStateOf(false) }
    var openDialogGuest by remember { mutableStateOf(false) }

    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
            .background(color = Fondo)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight(0.10f)
                .background(LetraClara)
                .fillMaxWidth()
        ) {
            ArrowBack(navController)
            Text(
                text = "Turn & Points",
                color = LetraOscura,
                modifier = Modifier.padding(start = 50.dp)
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
            TextField(
                value = textUser,
                onValueChange = { textUser = it },
                shape = RoundedCornerShape(30.dp),
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Text(text = "Introduzca su contraseña")
            TextField(
                value = textPassword,
                onValueChange = { textPassword = it },
                shape = RoundedCornerShape(30.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Text(text = "Repita su contraseña")
            TextField(
                value = textPassword2,
                onValueChange = { textPassword2 = it },
                shape = RoundedCornerShape(30.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.padding(50.dp))
            Button(onClick = {
                var alreadyExits = false
                if (textUser.isNotBlank()){
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
                } else {
                    openDialogGuest = true
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
        if (openDialogGuest) {
            GuestDialog {
                openDialogGuest = false
                navController.navigate(Routs.MainScreen.rout)
            }
        }
    }
}

@Composable
fun ArrowBack(navController: NavHostController) {
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

@Composable
fun GuestDialog(onDismissClick: () -> Unit) {
    MaterialTheme {
        Column {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = {
                    onDismissClick()
                },
                title = {
                    Text(text = "Bienvenido")
                },
                text = {
                    Text("Está accediendo como invitado")
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
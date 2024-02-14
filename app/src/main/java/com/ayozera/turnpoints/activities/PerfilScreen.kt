package com.ayozera.turnpoints.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ayozera.turnpoints.R
import com.ayozera.turnpoints.ui.theme.Fondo
import java.lang.reflect.Modifier

@Preview
@Composable
fun PantallaPerfil() {

    Column (){

        Row (){
            Image(
                painter = painterResource(R.drawable.imagen_perfil),
                contentDescription = "Imagen de perfil"
            )
            Text(text = "Nombre de usuario")
        }
        Text(text = "Juego Favorito")
        Text (text = "Aquí va el juego")


    }
}
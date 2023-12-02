package com.ayozera.turnpoints.models

import androidx.compose.ui.graphics.Color
import java.security.SecureRandom
import kotlin.random.Random

class Util {

    companion object {


        fun randomColor(): Color {
            val random = Random(System.currentTimeMillis())
            val colors = ArrayList<Color>()
            colors.add(Color(0xFF156C99))
            colors.add(Color(0xFF962C2C))
            colors.add(Color(0xFFC2E233))
            colors.add(Color(0xFFC11AE6))
            colors.add(Color(0xFF45C437))
            colors.add(Color(0xFFFF9800))
            colors.add(Color(0xFF3F51B5))
            colors.add(Color(0xFF8BC34A))
            colors.add(Color(0xFF009688))
            colors.add(Color(0xFFE91E63))
            colors.add(Color(0xFF673AB7))
            colors.add(Color(0xFFFF5722))

            return colors[random.nextInt(12)]
        }
    }
}
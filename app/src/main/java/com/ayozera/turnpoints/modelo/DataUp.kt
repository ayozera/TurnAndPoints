package com.ayozera.turnpoints.modelo

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class DataUp {
    fun firstloader(context: Context) {
        val assetManager = context.assets
        val inputStream = assetManager.open("matches.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val writer : FileOutputStream =
            context.openFileOutput("matches.txt", Context.MODE_APPEND)
        reader.forEachLine { line ->
            writer.write("$line\n".toByteArray())
        }
        reader.close()
        writer.close()
    }
    fun loader(context : Context): ArrayList<Match> {
        val questionsList = ArrayList<Match>()
        val file = File(context.filesDir, "matches.txt")
        try {
            if (!file.exists()) {
                firstloader(context)
            }
            val fileInput = FileInputStream(file)
            val reader = BufferedReader(InputStreamReader(fileInput))
            var counter = -1
            var player = ""
            var game = ""
            var type = ""
            var opponent = ""
            var score = 0
            reader.forEachLine { line ->
                if (line.isNotBlank()) {
                    counter++
                    when (counter) {
                        0 -> player = line
                        1 -> game = line
                        2 -> type = line
                        3 -> opponent = line
                        4 -> {
                            score = line.toInt()
                            counter = -1
                            questionsList.add(Match(player, game, type, opponent,score))
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return questionsList
    }
    companion object {
        fun writer(match: Match, context: Context) {
            val content = "\n${match.player}\n${match.game}\n${match.type}\n" +
                    "${match.opponent}\n${match.score}\n"
            val writer: FileOutputStream =
                context.openFileOutput("matches.txt", Context.MODE_APPEND)
            writer.write("$content".toByteArray())
            writer.close()
        }
    }
}
package com.ayozera.turnpoints.models

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class DataUp {
    companion object {
        fun gameFirstLoader(context: Context) {
            val assetManager = context.assets
            val inputStream = assetManager.open("games.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val writer : FileOutputStream =
                context.openFileOutput("games.txt", Context.MODE_APPEND)
            reader.forEachLine { line ->
                writer.write("$line\n".toByteArray())
            }
            reader.close()
            writer.close()
        }
        fun gameLoader(context : Context): ArrayList<String> {
            val gamesList = ArrayList<String>()
            val file = File(context.filesDir, "games.txt")
            try {
                if (!file.exists()) {
                    gameFirstLoader(context)
                }
                val fileInput = FileInputStream(file)
                val reader = BufferedReader(InputStreamReader(fileInput))

                reader.forEachLine { line ->
                    if (line.isNotBlank()) {
                        gamesList.add(line)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return gamesList
        }

        fun matchFirstloader(context: Context) {
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
        fun matchLoader(context : Context): ArrayList<Match> {
            val matchesList = ArrayList<Match>()
            val file = File(context.filesDir, "matches.txt")
            try {
                if (!file.exists()) {
                    matchFirstloader(context)
                }
                val fileInput = FileInputStream(file)
                val reader = BufferedReader(InputStreamReader(fileInput))
                var counter = -1
                var player = ""
                var game = ""
                var type = ""
                var opponent = ""
                var score = 0
                var day = 0
                var month = 0
                var year = 0
                reader.forEachLine { line ->
                    if (line.isNotBlank()) {
                        counter++
                        when (counter) {
                            0 -> player = line
                            1 -> game = line
                            2 -> type = line
                            3 -> opponent = line
                            4 -> score = line.toInt()
                            5 -> day = line.toInt()
                            6 -> month = line.toInt()
                            7 -> {
                                year = line.toInt()
                                counter = -1
                                matchesList.add(Match(player, game, type, opponent, score, day, month, year))
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return matchesList
        }
        fun writer(match: Match, context: Context) {
            val content = "\n${match.player}\n${match.game}\n${match.type}\n" +
                    "${match.opponent}\n${match.score}\n${match.day}\n${match.month}\n${match.year}\n"
            val writer: FileOutputStream =
                context.openFileOutput("matches.txt", Context.MODE_APPEND)
            writer.write("$content".toByteArray())
            writer.close()
        }
    }
}
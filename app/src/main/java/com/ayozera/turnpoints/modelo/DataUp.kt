package com.ayozera.turnpoints.modelo

import android.content.Context
import java.io.FileOutputStream

class DataUp {
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
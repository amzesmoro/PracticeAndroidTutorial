package com.raywenderlich.android.datadrop.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raywenderlich.android.datadrop.app.DataDropApplication
import java.io.*
import java.lang.StringBuilder

object FileRepository : DropRepository {

    private val gson: Gson
      get() {
          val builder = GsonBuilder()
          builder.registerTypeAdapter(Drop::class.java, DropTypeAdapter())
          return builder.create()
      }

    private fun getContext() = DataDropApplication.getAppContext()

    override fun addDrop(drop: Drop) {
        val string = gson.toJson(drop)
        try {
            val dropStream = dropOutputStream(drop)
            dropStream.write(string.toByteArray())
            dropStream.close()
        } catch (e: IOException) {
            Log.e("FileRepository", "Error saving drop")
        }
    }

    @Throws(IOException::class)
    override fun getDrops(): List<Drop> {
        val drops = mutableListOf<Drop>()

        try {
            val fileList = dropsDirectory().list()
            fileList.map { convertStreamToString(dropInputStream(it)) }.mapTo(drops) {
                gson.fromJson(it, Drop::class.java)
            }
        } catch (e: IOException) {
            Log.e("FileRepository", "Error reading drops")
        }
        return drops
    }

    override fun clearDrop(drop: Drop) {
        TODO("Not yet implemented")
    }

    override fun clearAllDrops() {
        TODO("Not yet implemented")
    }

    private fun dropsDirectory() = getContext().getDir("drops", Context.MODE_PRIVATE)

    private fun dropFile(filename: String) = File(dropsDirectory(), filename)

    private fun dropFilename(drop: Drop) = drop.id + ".drop"

    private fun dropOutputStream(drop: Drop): FileOutputStream {
        return FileOutputStream(dropFile(dropFilename(drop)))
    }

    private fun dropInputStream(filename: String): FileInputStream {
        return FileInputStream(dropFile(filename))
    }

    @Throws(IOException::class)
    private fun convertStreamToString(inputStream: InputStream): String {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            sb.append(line).append("\n")
            line = reader.readLine()
        }
        reader.close()
        return sb.toString()
    }
}
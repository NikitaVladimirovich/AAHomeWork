package com.aacademy.homework.data.api

import android.content.Context
import com.aacademy.homework.data.api.model.JsonMovie
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val jsonFormat = Json { ignoreUnknownKeys = true }

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    jsonFormat.decodeFromString(data)
}

suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    jsonFormat.decodeFromString(data)
}

suspend fun loadJsonMovies(context: Context): List<JsonMovie> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "data.json")
    jsonFormat.decodeFromString(data)
}

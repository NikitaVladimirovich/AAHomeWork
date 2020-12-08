package com.aacademy.homework.data.api

import android.content.Context
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val jsonFormat = Json { ignoreUnknownKeys = true }

@Serializable
private class JsonGenre(val id: Int, val name: String)

@Serializable
private class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String
)

@Serializable
private class JsonMovie(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val actors: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    val overview: String,
    val adult: Boolean,
    @SerialName("vote_count")
    val voteCount: Int
)

private suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "genres.json")
    parseGenres(data)
}

internal fun parseGenres(data: String): List<Genre> {
    val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
    return jsonGenres.map { Genre(id = it.id, name = it.name) }
}

private fun readAssetFileToString(context: Context, fileName: String): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

private suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "people.json")
    parseActors(data)
}

internal fun parseActors(data: String): List<Actor> {
    val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
    return jsonActors.map { Actor(id = it.id, name = it.name, picture = it.profilePicture) }
}

internal suspend fun loadMoviesPreviews(context: Context): List<MoviePreviewWithGenres> =
    withContext(Dispatchers.IO) {
        val genresMap = loadGenres(context)
        val data = readAssetFileToString(context, "data.json")
        parseMoviesPreviews(data, genresMap)
    }

internal fun parseMoviesPreviews(
    data: String,
    genres: List<Genre>
): List<MoviePreviewWithGenres> {
    val genresMap = genres.associateBy { it.id }
    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        MoviePreviewWithGenres(
            MoviePreview(
                id = jsonMovie.id,
                title = jsonMovie.title,
                poster = jsonMovie.posterPicture,
                backdrop = jsonMovie.backdropPicture,
                rating = jsonMovie.ratings,
                ageLimit = 0,
                runtime = jsonMovie.runtime,
                reviews = jsonMovie.voteCount
            ),
            genres = jsonMovie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            }
        )
    }
}

internal suspend fun loadMovieDetail(id: Int, context: Context): MovieDetailWithActors = withContext(Dispatchers.IO) {
    val actorsMap = loadActors(context)
    val data = readAssetFileToString(context, "data.json")
    parseMovieDetail(id, data, actorsMap)
}

internal fun parseMovieDetail(
    id: Int,
    data: String,
    actors: List<Actor>
): MovieDetailWithActors {
    val actorsMap = actors.associateBy { it.id }
    val jsonMovie = jsonFormat.decodeFromString<List<JsonMovie>>(data).first { it.id == id }

    return MovieDetailWithActors(
        MovieDetail(
            id = jsonMovie.id,
            overview = jsonMovie.overview,
        ),
        actors = jsonMovie.actors.map {
            actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
        }
    )
}

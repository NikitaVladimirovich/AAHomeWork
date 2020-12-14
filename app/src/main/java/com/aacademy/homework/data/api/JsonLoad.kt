package com.aacademy.homework.data.api

import com.aacademy.homework.MyApp
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
private class JsonMovie(
    val id: Long,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Long>,
    @SerialName("actors")
    val actorsIds: List<Long>,
    @SerialName("vote_average")
    val ratings: Float,
    val overview: String,
    val adult: Boolean,
    @SerialName("vote_count")
    val voteCount: Int
)

private fun readAssetFileToString(fileName: String): String {
    val stream = MyApp.INSTANCE.assets.open(fileName)
    return stream.bufferedReader().readText()
}

private suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString("genres.json")
    jsonFormat.decodeFromString(data)
}

private suspend fun loadActors(): List<Actor> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString("people.json")
    jsonFormat.decodeFromString(data)
}

internal suspend fun loadMoviesPreviews(): List<MoviePreviewWithGenres> = withContext(Dispatchers.IO) {
    val genresMap = loadGenres()
    val data = readAssetFileToString("data.json")
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
                ageLimit = if (jsonMovie.adult) 16 else 13,
                runtime = jsonMovie.runtime,
                reviews = jsonMovie.voteCount
            ),
            genres = jsonMovie.genreIds.map {
                genresMap[it] ?: throw IllegalArgumentException("Genre not found")
            }
        )
    }
}

internal suspend fun loadMovieDetail(id: Long): MovieDetailWithActors = withContext(Dispatchers.IO) {
    val actorsMap = loadActors()
    val data = readAssetFileToString("data.json")
    parseMovieDetail(id, data, actorsMap)
}

internal fun parseMovieDetail(
    id: Long,
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
        actors = jsonMovie.actorsIds.map {
            actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
        }
    )
}

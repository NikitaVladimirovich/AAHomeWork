package com.aacademy.homework.data

import com.aacademy.homework.data.api.ApiSource
import com.aacademy.homework.data.api.model.JsonMovie
import com.aacademy.homework.data.local.LocalSource
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.Movie
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class DataRepositoryImpl @Inject constructor(private val apiSource: ApiSource, private val localSource: LocalSource) :
    DataRepository {

    override suspend fun getAllPreviews(): List<Movie> =
        localSource.getAllMoviePreviews()
            .let {
                if (it.isNotEmpty()) return it else loadAllPreviews()
            }

    override suspend fun loadAllPreviews(): List<Movie> {
        var jsonMovies = listOf<JsonMovie>()
        var genres = mapOf<Long, Genre>()
        coroutineScope {
            launch {
                jsonMovies = apiSource.getPopularMovies().results
            }
            launch {
                genres = apiSource.getGenres().genres.associateBy { genre -> genre.id }
            }
        }
        val result = jsonMovies.map { jsonMovie ->
            Movie(
                id = jsonMovie.id,
                title = jsonMovie.title,
                poster = jsonMovie.posterPicture.orEmpty(),
                backdrop = jsonMovie.backdropPicture.orEmpty(),
                rating = jsonMovie.ratings,
                ageLimit = if (jsonMovie.adult) 16 else 13,
                runtime = 0,
                reviews = jsonMovie.voteCount,
                overview = jsonMovie.overview,
                genres = jsonMovie.genreIds.map { id ->
                    genres[id] ?: throw IllegalArgumentException("Genre not found")
                }
            )
        }
        localSource.cacheMoviePreviews(result)
        return result
    }

    override suspend fun getCast(id: Long): List<Actor> {
        var actors = emptyList<Actor>()
        coroutineScope {
            launch {
                actors = apiSource.getActors(id).cast
            }
        }
        return actors
    }

    override suspend fun setMovieLiked(id: Long, isLiked: Boolean) {
        localSource.setMovieLiked(id, isLiked)
    }
}

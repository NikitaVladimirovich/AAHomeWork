package com.aacademy.homework.data

import com.aacademy.homework.data.api.ApiSource
import com.aacademy.homework.data.api.model.JsonMovie
import com.aacademy.homework.data.local.LocalSource
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Genre
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MoviePreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class DataRepositoryImpl @Inject constructor(private val apiSource: ApiSource, private val localSource: LocalSource) :
    DataRepository {

    override suspend fun getAllPreviews(): List<MoviePreview> =
        localSource.getAllMoviePreviews()
            .let {
                if (it.isNotEmpty()) return it else loadAllPreviews()
            }

    override suspend fun loadAllPreviews(): List<MoviePreview> {
        delay(2000)
        var jsonMovies = listOf<JsonMovie>()
        var genres = mapOf<Long, Genre>()
        coroutineScope {
            launch {
                jsonMovies = apiSource.getMovies()
            }
            launch {
                genres = apiSource.getGenres().associateBy { genre -> genre.id }
            }
        }
        val result = jsonMovies.map { jsonMovie ->
            MoviePreview(
                id = jsonMovie.id,
                title = jsonMovie.title,
                poster = jsonMovie.posterPicture,
                backdrop = jsonMovie.backdropPicture,
                rating = jsonMovie.ratings,
                ageLimit = if (jsonMovie.adult) 16 else 13,
                runtime = jsonMovie.runtime,
                reviews = jsonMovie.voteCount,
                genres = jsonMovie.genreIds.map { id ->
                    genres[id] ?: throw IllegalArgumentException("Genre not found")
                }
            )
        }
        localSource.cacheMoviePreviews(result)
        return result
    }

    override suspend fun getMovieDetail(id: Long): MovieDetail = localSource.getMovieDetail(id)
        .let {
            if (it.isNotEmpty()) it.first() else {
                delay(2000)
                lateinit var jsonMovie: JsonMovie
                var actors = mapOf<Long, Actor>()
                coroutineScope {
                    launch {
                        jsonMovie = apiSource.getMovies().first { movie -> movie.id == id }
                    }
                    launch {
                        actors = apiSource.getActors().associateBy { actor -> actor.id }
                    }
                }
                val result = MovieDetail(
                    id = jsonMovie.id,
                    overview = jsonMovie.overview,
                    actors = jsonMovie.actorsIds.map { id ->
                        actors[id] ?: throw IllegalArgumentException("Actor not found")
                    }
                )
                localSource.cacheMovieDetail(result)
                return result
            }
        }

    override suspend fun setMovieLiked(id: Long, isLiked: Boolean) {
        localSource.setMovieLiked(id, isLiked)
    }
}

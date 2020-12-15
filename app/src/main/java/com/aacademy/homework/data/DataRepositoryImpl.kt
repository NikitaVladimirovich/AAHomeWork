package com.aacademy.homework.data

import com.aacademy.homework.data.api.ApiSource
import com.aacademy.homework.data.local.LocalSource
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class DataRepositoryImpl @Inject constructor(private val apiSource: ApiSource, private val localSource: LocalSource) :
    DataRepository {

    override suspend fun getAllPreviews(): List<MoviePreviewWithGenres> =
        localSource.getAllMoviePreviews()
            .let {
                if (it.isNotEmpty()) return it else {
                    val jsonMovies = apiSource.getMovies()
                    val genres = apiSource.getGenres().associateBy { genre -> genre.id }
                    val result = jsonMovies.map { jsonMovie ->
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
                            genres = jsonMovie.genreIds.map { id ->
                                genres[id] ?: throw IllegalArgumentException("Genre not found")
                            }
                        )
                    }
                    localSource.cacheMoviePreviewsWithGenres(result)
                    return result
                }
            }

    override suspend fun getMovieDetail(id: Long): MovieDetailWithActors = localSource.getMovieDetail(id)
        .let {
            if (it.isNotEmpty()) it.first() else {
                val jsonMovie = apiSource.getMovies().first { movie -> movie.id == id }
                val actors = apiSource.getActors().associateBy { actor -> actor.id }
                val result = MovieDetailWithActors(
                    MovieDetail(
                        id = jsonMovie.id,
                        overview = jsonMovie.overview,
                    ),
                    actors = jsonMovie.actorsIds.map { id ->
                        actors[id] ?: throw IllegalArgumentException("Actor not found")
                    }
                )
                localSource.cacheMovieDetailWithActors(result)
                return result
            }
        }

    override suspend fun setMovieLiked(id: Long, isLiked: Boolean) {
        localSource.setMovieLiked(id, isLiked)
    }
}

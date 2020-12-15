package com.aacademy.homework.data

import com.aacademy.homework.data.api.ApiSourceImpl
import com.aacademy.homework.data.local.LocalSourceImpl
import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreview
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object DataRepositoryImpl : DataRepository {

    override suspend fun getAllPreviews(): List<MoviePreviewWithGenres> =
        LocalSourceImpl.getAllMoviePreviews()
            .let {
                if (it.isNotEmpty()) return it else {
                    val jsonMovies = ApiSourceImpl.getMovies()
                    val genres = ApiSourceImpl.getGenres().associateBy { it.id }
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
                            genres = jsonMovie.genreIds.map {
                                genres[it] ?: throw IllegalArgumentException("Genre not found")
                            }
                        )
                    }
                    LocalSourceImpl.cacheMoviePreviewsWithGenres(result)
                    return result
                }
            }

    override suspend fun getMovieDetail(id: Long): MovieDetailWithActors = LocalSourceImpl.getMovieDetail(id)
        .let {
            if (it.isNotEmpty()) it.first() else {
                val jsonMovie = ApiSourceImpl.getMovies().first { it.id == id }
                val actors = ApiSourceImpl.getActors().associateBy { it.id }
                val result = MovieDetailWithActors(
                    MovieDetail(
                        id = jsonMovie.id,
                        overview = jsonMovie.overview,
                    ),
                    actors = jsonMovie.actorsIds.map {
                        actors[it] ?: throw IllegalArgumentException("Actor not found")
                    }
                )
                LocalSourceImpl.cacheMovieDetailWithActors(result)
                return result
            }
        }
}

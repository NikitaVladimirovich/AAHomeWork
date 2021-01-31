package com.aacademy.homework.data.local

import com.aacademy.homework.data.local.dao.AppDatabase
import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Movie
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(private val database: AppDatabase) : LocalSource {

    override suspend fun getPopularMovies(query: String): List<Movie> =
        database.movieDao().getMovies("%$query%")

    override suspend fun setMovieLiked(movieId: Long, isLiked: Boolean) =
        database.movieDao().setMovieLiked(movieId, isLiked)

    override suspend fun cachePopularMovies(movies: List<Movie>) {
        database.movieDao().update(movies)
    }

    override suspend fun getActors(movieId: Long): List<Actor> = database.actorDao().getActors(movieId)

    override suspend fun cacheActors(actors: List<Actor>) {
        database.actorDao().clearActors()
        database.actorDao().insert(actors)
    }
}

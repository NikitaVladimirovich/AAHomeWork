package com.aacademy.data.local

import com.aacademy.data.local.dao.AppDatabase
import com.aacademy.data.model.ActorEntity
import com.aacademy.data.model.MovieEntity
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(private val database: AppDatabase) : LocalSource {

    override suspend fun getPopularMovies(query: String): List<MovieEntity> =
        database.movieDao().getMovies("%$query%")

    override suspend fun setMovieLiked(movieId: Long, isLiked: Boolean) =
        database.movieDao().setMovieLiked(movieId, isLiked)

    override suspend fun cachePopularMovies(movieEntities: List<MovieEntity>) {
        database.movieDao().update(movieEntities)
    }

    override suspend fun getActors(movieId: Long): List<ActorEntity> = database.actorDao().getActors(movieId)

    override suspend fun cacheActors(movieId: Long, actorEntities: List<ActorEntity>) {
        database.actorDao().clearActors(movieId)
        database.actorDao().insert(actorEntities)
    }
}

package com.aacademy.data.local

import com.aacademy.data.model.ActorEntity
import com.aacademy.data.model.MovieEntity

interface LocalSource {

    suspend fun getPopularMovies(query: String = ""): List<MovieEntity>

    suspend fun setMovieLiked(movieId: Long, isLiked: Boolean)

    suspend fun cachePopularMovies(movieEntities: List<MovieEntity>)

    suspend fun getActors(movieId: Long): List<ActorEntity>

    suspend fun cacheActors(movieId: Long, actorEntities: List<ActorEntity>)
}

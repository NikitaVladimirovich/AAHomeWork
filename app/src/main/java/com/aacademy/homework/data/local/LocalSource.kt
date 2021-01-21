package com.aacademy.homework.data.local

import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Movie

interface LocalSource {

    suspend fun getPopularMovies(): List<Movie>

    suspend fun setMovieLiked(movieId: Long, isLiked: Boolean)

    suspend fun cachePopularMovies(movies: List<Movie>)

    suspend fun getActors(movieId: Long): List<Actor>

    suspend fun cacheActors(actors: List<Actor>)
}

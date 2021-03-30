package com.aacademy.domain.repositories

import com.aacademy.domain.entities.Actor
import com.aacademy.domain.entities.Movie

interface DataRepository {

    suspend fun getMoviesFromAPI(query: String, page: Int): Pair<Int, List<Movie>>

    suspend fun getMoviesFromDB(query: String): List<Movie>

    suspend fun getCastFromAPI(movieId: Long): List<Actor>

    suspend fun getCastFromDB(movieId: Long): List<Actor>

    suspend fun setMovieLiked(movieId: Long, isLiked: Boolean)
}

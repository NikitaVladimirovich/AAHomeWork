package com.aacademy.homework.data

import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Movie

interface DataRepository {

    suspend fun getMoviesFromAPI(query: String = "", page: Int = 1): Pair<Int, List<Movie>>

    suspend fun getMoviesFromDB(query: String = ""): List<Movie>

    suspend fun getCastFromAPI(movieId: Long): List<Actor>

    suspend fun getCastFromDB(movieId: Long): List<Actor>

    suspend fun setMovieLiked(movieId: Long, isLiked: Boolean)
}

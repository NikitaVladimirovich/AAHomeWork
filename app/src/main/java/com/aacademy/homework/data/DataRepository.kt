package com.aacademy.homework.data

import com.aacademy.homework.data.model.Actor
import com.aacademy.homework.data.model.Movie

interface DataRepository {

    suspend fun getMovies(query: String? = null, page: Int = 1): Pair<Int, List<Movie>>

    suspend fun getCast(id: Long): List<Actor>

    suspend fun setMovieLiked(id: Long, isLiked: Boolean)
}

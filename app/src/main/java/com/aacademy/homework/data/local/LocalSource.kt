package com.aacademy.homework.data.local

import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MoviePreview

interface LocalSource {

    suspend fun getAllMoviePreviews(): List<MoviePreview>

    suspend fun getMovieDetail(id: Long): List<MovieDetail>

    suspend fun setMovieLiked(id: Long, isLiked: Boolean)

    suspend fun cacheMoviePreviews(moviePreviews: List<MoviePreview>)

    suspend fun cacheMovieDetail(movieDetail: MovieDetail)
}

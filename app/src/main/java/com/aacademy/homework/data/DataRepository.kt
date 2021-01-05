package com.aacademy.homework.data

import com.aacademy.homework.data.model.MovieDetail
import com.aacademy.homework.data.model.MoviePreview

interface DataRepository {

    suspend fun getAllPreviews(): List<MoviePreview>

    suspend fun loadAllPreviews(): List<MoviePreview>

    suspend fun getMovieDetail(id: Long): MovieDetail

    suspend fun setMovieLiked(id: Long, isLiked: Boolean)
}

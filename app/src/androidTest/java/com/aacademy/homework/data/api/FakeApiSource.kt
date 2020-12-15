package com.aacademy.homework.data.api

import com.aacademy.homework.data.local.LocalSourceImpl
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

object FakeApiSource {

    suspend fun getMovieDetail(id: Long): MovieDetailWithActors {
        return loadMovieDetail(id)
            .let {
                LocalSourceImpl.cacheMovieDetailWithActors(it)
                it
            }
    }

    suspend fun getAllMoviePreviews(): List<MoviePreviewWithGenres> {
        return loadMoviesPreviews()
            .let {
                LocalSourceImpl.cacheMoviePreviewsWithGenres(it)
                it
            }
    }
}

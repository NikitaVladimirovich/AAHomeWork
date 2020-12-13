package com.aacademy.homework.data.api

import com.aacademy.homework.data.local.LocalSource
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

object FakeApiSource {

    suspend fun getMovieDetail(id: Long): MovieDetailWithActors {
        Thread.sleep(2000)
        return loadMovieDetail(id)
            .let {
                LocalSource.cacheMovieDetailWithActors(it)
                it
            }
    }

    suspend fun getAllMoviePreviews(): List<MoviePreviewWithGenres> {
        Thread.sleep(2000)
        return loadMoviesPreviews()
            .let {
                LocalSource.cacheMoviePreviewsWithTags(it)
                it
            }
    }
}

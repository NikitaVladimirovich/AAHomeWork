package com.aacademy.homework.data.api

import com.aacademy.homework.data.local.FakeLocalRepository
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

object FakeApiRepository {

    suspend fun getMovieDetail(id: Long): MovieDetailWithActors {
        Thread.sleep(2000)
        return loadMovieDetail(id)
            .let {
                FakeLocalRepository.cacheMovieDetailWithActors(it)
                it
            }
    }

    suspend fun getAllMoviePreviews(): List<MoviePreviewWithGenres> {
        Thread.sleep(2000)
        return loadMoviesPreviews()
            .let {
                FakeLocalRepository.cacheMoviePreviewsWithTags(it)
                it
            }
    }
}

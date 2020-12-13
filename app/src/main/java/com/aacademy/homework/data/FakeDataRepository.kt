package com.aacademy.homework.data

import com.aacademy.homework.data.api.FakeApiSource
import com.aacademy.homework.data.local.LocalSource
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

object FakeDataRepository {

    suspend fun getAllPreviews(): List<MoviePreviewWithGenres> {
        return LocalSource.getAllMoviePreviews()
            .let {
                if (it.isEmpty()) FakeApiSource.getAllMoviePreviews() else it
            }
    }

    suspend fun getMovieDetail(id: Long): MovieDetailWithActors = LocalSource.getMovieDetail(id)
        .let {
            if (it.isEmpty()) FakeApiSource.getMovieDetail(id) else it.first()
        }
}

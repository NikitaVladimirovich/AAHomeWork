package com.aacademy.homework.data

import com.aacademy.homework.data.api.FakeApiRepository
import com.aacademy.homework.data.local.FakeLocalRepository
import com.aacademy.homework.data.model.MovieDetailWithActors
import com.aacademy.homework.data.model.MoviePreviewWithGenres

object FakeDataRepository {

    suspend fun getAllPreviews(): List<MoviePreviewWithGenres> {
        return FakeLocalRepository.getAllMoviePreviews()
            .let {
                if (it.isEmpty()) FakeApiRepository.getAllMoviePreviews() else it
            }
    }

    suspend fun getMovieDetail(id: Int): MovieDetailWithActors = FakeLocalRepository.getMovieDetail(id)
        .let {
            if (it.isEmpty()) FakeApiRepository.getMovieDetail(id) else it.first()
        }
}
package com.aacademy.homework.data

import com.aacademy.homework.data.api.FakeApiRepository
import com.aacademy.homework.data.local.FakeLocalRepository
import com.aacademy.homework.data.model.MoviePreviewWithTags
import io.reactivex.rxjava3.core.Single

object FakeDataRepository {

    fun getAllPreviews(): Single<List<MoviePreviewWithTags>> {
        return FakeLocalRepository.getAllMoviePreviews()
            .flatMap {
                if (it.isEmpty()) {
                    FakeApiRepository.getAllMoviePreviews()
                } else {
                    Single.just(it)
                }
            }
    }
}
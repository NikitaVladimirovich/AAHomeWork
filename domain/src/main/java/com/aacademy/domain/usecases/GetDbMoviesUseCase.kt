package com.aacademy.domain.usecases

import com.aacademy.domain.repositories.DataRepository

class GetDbMoviesUseCase(private val dataRepository: DataRepository) {

    suspend operator fun invoke(query: String = "") = dataRepository.getMoviesFromDB(query)
}

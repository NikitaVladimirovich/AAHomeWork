package com.aacademy.domain.usecases

import com.aacademy.domain.repositories.DataRepository

class GetApiMoviesUseCase(private val dataRepository: DataRepository) {

    suspend operator fun invoke(query: String = "", page: Int = 1) = dataRepository.getMoviesFromAPI(query, page)
}

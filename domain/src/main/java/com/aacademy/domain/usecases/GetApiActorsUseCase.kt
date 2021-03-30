package com.aacademy.domain.usecases

import com.aacademy.domain.repositories.DataRepository

class GetApiActorsUseCase(private val dataRepository: DataRepository) {

    suspend operator fun invoke(movieId: Long) = dataRepository.getCastFromAPI(movieId)
}

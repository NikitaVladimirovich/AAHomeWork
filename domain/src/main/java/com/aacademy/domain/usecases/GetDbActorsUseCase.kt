package com.aacademy.domain.usecases

import com.aacademy.domain.repositories.DataRepository

class GetDbActorsUseCase(private val dataRepository: DataRepository) {

    suspend operator fun invoke(movieId: Long) = dataRepository.getCastFromDB(movieId)
}

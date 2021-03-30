package com.aacademy.domain.usecases

import com.aacademy.domain.repositories.DataRepository

class LikeMovieUseCase(private val dataRepository: DataRepository) {

    suspend operator fun invoke(movieId: Long, isLiked: Boolean) = dataRepository.setMovieLiked(movieId, isLiked)
}

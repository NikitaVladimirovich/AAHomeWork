package com.aacademy.homework.di

import com.aacademy.domain.repositories.DataRepository
import com.aacademy.domain.usecases.GetApiActorsUseCase
import com.aacademy.domain.usecases.GetApiMoviesUseCase
import com.aacademy.domain.usecases.GetDbActorsUseCase
import com.aacademy.domain.usecases.GetDbMoviesUseCase
import com.aacademy.domain.usecases.LikeMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetApiMoviesUseCase(
        dataRepository: DataRepository
    ): GetApiMoviesUseCase {
        return GetApiMoviesUseCase(dataRepository)
    }

    @Singleton
    @Provides
    fun provideGetDbMoviesUseCase(
        dataRepository: DataRepository
    ): GetDbMoviesUseCase {
        return GetDbMoviesUseCase(dataRepository)
    }

    @Singleton
    @Provides
    fun provideGetApiActorsUseCase(
        dataRepository: DataRepository
    ): GetApiActorsUseCase {
        return GetApiActorsUseCase(dataRepository)
    }

    @Singleton
    @Provides
    fun provideGetDbActorsUseCase(
        dataRepository: DataRepository
    ): GetDbActorsUseCase {
        return GetDbActorsUseCase(dataRepository)
    }

    @Singleton
    @Provides
    fun provideLikeMovieUseCase(
        dataRepository: DataRepository
    ): LikeMovieUseCase {
        return LikeMovieUseCase(dataRepository)
    }
}

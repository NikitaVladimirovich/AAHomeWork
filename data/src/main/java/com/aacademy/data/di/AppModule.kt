package com.aacademy.data.di

import com.aacademy.data.DataRepositoryImpl
import com.aacademy.data.api.ApiSource
import com.aacademy.data.api.ApiSourceImpl
import com.aacademy.data.local.LocalSource
import com.aacademy.data.local.LocalSourceImpl
import com.aacademy.domain.repositories.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository

    @Binds
    abstract fun bindApiSource(apiSourceImpl: ApiSourceImpl): ApiSource

    @Binds
    abstract fun bindLocalSource(localSourceImpl: LocalSourceImpl): LocalSource
}

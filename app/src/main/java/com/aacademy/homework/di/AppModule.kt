package com.aacademy.homework.di

import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.DataRepositoryImpl
import com.aacademy.homework.data.api.ApiSource
import com.aacademy.homework.data.api.ApiSourceImpl
import com.aacademy.homework.data.local.LocalSource
import com.aacademy.homework.data.local.LocalSourceImpl
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

package com.aacademy.homework.di

import com.aacademy.homework.data.api.ApiSource
import com.aacademy.homework.data.api.FakeApiSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
abstract class FakeApiModule {

    @Binds
    abstract fun bindApiSource(apiSourceImpl: FakeApiSourceImpl): ApiSource
}

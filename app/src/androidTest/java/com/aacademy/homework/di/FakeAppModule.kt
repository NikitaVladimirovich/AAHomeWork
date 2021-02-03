package com.aacademy.homework.di

import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.data.DataRepositoryImpl
import com.aacademy.homework.data.api.ApiSource
import com.aacademy.homework.data.api.FakeApiSourceImpl
import com.aacademy.homework.data.local.LocalSource
import com.aacademy.homework.data.local.LocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
abstract class FakeAppModule {

    @Binds
    abstract fun bindDataRepository(dataRepositoryImpl: DataRepositoryImpl): DataRepository

    @Binds
    abstract fun bindApiSource(apiSourceImpl: FakeApiSourceImpl): ApiSource

    @Binds
    abstract fun bindLocalSource(localSourceImpl: LocalSourceImpl): LocalSource
}

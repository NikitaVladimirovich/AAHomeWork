package com.aacademy.homework.di

import com.aacademy.homework.data.preferences.MyPreference
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface GlideModuleEntryPoint {

    fun myPreference(): MyPreference
}


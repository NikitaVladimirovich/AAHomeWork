package com.aacademy.homework.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.aacademy.homework.data.preferences.MyPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    fun provideMyPreferences(prefs: SharedPreferences): MyPreference = MyPreference(prefs)

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext applicationContext: Context
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
}

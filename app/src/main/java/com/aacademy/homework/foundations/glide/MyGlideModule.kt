package com.aacademy.homework.foundations.glide

import android.content.Context
import com.aacademy.homework.di.GlideModuleEntryPoint
import com.aacademy.homework.foundations.glide.CustomImageLoader.Factory
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import dagger.hilt.android.EntryPointAccessors
import java.io.InputStream

@GlideModule
class MyGlideModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val myPreferences =
            EntryPointAccessors.fromApplication(context, GlideModuleEntryPoint::class.java).myPreference()
        registry.append(String::class.java, InputStream::class.java, Factory(myPreferences))
    }
}

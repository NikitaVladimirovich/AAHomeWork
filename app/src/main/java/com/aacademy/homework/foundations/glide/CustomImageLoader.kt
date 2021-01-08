package com.aacademy.homework.foundations.glide

import com.aacademy.homework.data.preferences.MyPreference
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream

class CustomImageLoader(urlLoader: ModelLoader<GlideUrl?, InputStream?>?, private val baseUrl: MyPreference) :
    BaseGlideUrlLoader<String>(urlLoader) {

    override fun handles(model: String): Boolean {
        return model.isNotEmpty()
    }

    override fun getUrl(model: String, width: Int, height: Int, options: Options): String {
        return baseUrl.imageUrl + model
    }

    class Factory(private val baseUrl: MyPreference) : ModelLoaderFactory<String, InputStream> {

        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return CustomImageLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java), baseUrl)
        }

        override fun teardown() {}
    }
}

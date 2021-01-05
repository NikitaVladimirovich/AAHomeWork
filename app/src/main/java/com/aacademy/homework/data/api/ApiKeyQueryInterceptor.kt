package com.aacademy.homework.data.api

import com.aacademy.homework.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyQueryInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .url(
                    chain.request().url.newBuilder()
                        .addQueryParameter(API_KEY_PARAMETER, BuildConfig.API_KEY)
                        .build()
                ).build()
        )
    }

    private companion object {

        private const val API_KEY_PARAMETER = "api_key"
    }
}

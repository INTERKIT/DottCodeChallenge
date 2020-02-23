package com.example.dottchallenge.common.network

import com.example.dottchallenge.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object HttpClientBuilder {
    private const val TIMEOUT_VAL: Long = 20

    fun create(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(TIMEOUT_VAL, TimeUnit.SECONDS)
            .build()
    }
}
package com.example.dottchallenge.common.di

import com.example.dottchallenge.common.network.ApiConfig
import com.example.dottchallenge.common.network.HttpClientBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    fun create() = module {
        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HttpClientBuilder.create())
                .build()
        }
    }
}
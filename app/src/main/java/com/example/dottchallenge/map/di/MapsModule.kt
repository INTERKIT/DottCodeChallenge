package com.example.dottchallenge.map.di

import com.example.dottchallenge.common.network.MapsApi
import com.example.dottchallenge.details.VenueViewModel
import com.example.dottchallenge.map.domain.MapsInteractor
import com.example.dottchallenge.map.repository.MapsInMemoryRepository
import com.example.dottchallenge.map.repository.MapsLocalRepository
import com.example.dottchallenge.map.repository.MapsRemoteRepository
import com.example.dottchallenge.map.repository.MapsRemoteRepositoryImpl
import com.example.dottchallenge.map.ui.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

object MapsModule {

    fun create() = module {
        single {
            val api = get<Retrofit>().create(MapsApi::class.java)
            MapsRemoteRepositoryImpl(api)
        } bind MapsRemoteRepository::class

        single { MapsInMemoryRepository() } bind MapsLocalRepository::class
        factory { MapsInteractor(get(), get()) }

        viewModel { MapsViewModel(get()) }
        viewModel { VenueViewModel(get()) }
    }
}
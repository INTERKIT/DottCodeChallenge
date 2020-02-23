package com.example.dottchallenge.common.network

import com.example.dottchallenge.map.model.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApi {

    @GET("venues/search")
    fun search(
        @Query("ll") location: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") apiVersion: String
    ): Single<SearchResponse>
}
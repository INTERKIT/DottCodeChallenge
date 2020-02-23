package com.example.dottchallenge.map.repository

import com.example.dottchallenge.common.network.ApiConfig.CLIENT_ID
import com.example.dottchallenge.common.network.ApiConfig.CLIENT_KEY
import com.example.dottchallenge.common.network.MapsApi
import com.example.dottchallenge.common.utils.DateUtils
import com.example.dottchallenge.map.model.Venue
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

interface MapsRemoteRepository {
    fun search(location: LatLng): Single<List<Venue>>
}

class MapsRemoteRepositoryImpl(
    private val api: MapsApi
) : MapsRemoteRepository {

    override fun search(
        location: LatLng
    ): Single<List<Venue>> {
        val result = "${location.latitude},${location.longitude}"
        return api.search(result, CLIENT_ID, CLIENT_KEY, DateUtils.getToday()).map { it.response.venues }
    }
}
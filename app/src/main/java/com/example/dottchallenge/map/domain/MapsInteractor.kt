package com.example.dottchallenge.map.domain

import com.example.dottchallenge.common.utils.toLatLng
import com.example.dottchallenge.map.model.Venue
import com.example.dottchallenge.map.repository.MapsLocalRepository
import com.example.dottchallenge.map.repository.MapsRemoteRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

class MapsInteractor(
    private val remoteRepository: MapsRemoteRepository,
    private val localRepository: MapsLocalRepository
) {

    fun getVenuesFlowable(): Flowable<List<Venue>> =
        localRepository.getVenuesFlowable()

    fun findById(venueId: String): Maybe<Venue> =
        localRepository.getVenuesFlowable()
            .firstElement()
            .map { venues ->
                venues.find { it.id == venueId }
            }

    fun search(location: LatLng, bounds: LatLngBounds?): Completable =
        remoteRepository.search(location)
            .flatMapCompletable {
                val result = filterVenues(it, bounds)
                localRepository.setVenues(result)
            }

    private fun filterVenues(venues: List<Venue>, bounds: LatLngBounds?): List<Venue> {
        if (bounds == null) return venues

        return venues.filter {
            val location = it.location.toLatLng()
            bounds.contains(location)
        }
    }
}
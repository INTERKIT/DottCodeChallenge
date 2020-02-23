package com.example.dottchallenge.map.repository

import com.example.dottchallenge.map.model.Venue
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import kotlin.properties.Delegates

interface MapsLocalRepository {
    fun setVenues(venues: List<Venue>): Completable
    fun getVenuesFlowable(): Flowable<List<Venue>>
}

class MapsInMemoryRepository : MapsLocalRepository {

    private var venuesList: List<Venue> by Delegates.observable(emptyList()) { _, oldValue, newValue ->
        val result = (oldValue + newValue).distinctBy { it.id }
        venuesBehaviorProcessor.offer(result)
    }

    private val venuesBehaviorProcessor = BehaviorProcessor.createDefault<List<Venue>>(emptyList())

    override fun setVenues(venues: List<Venue>) = Completable.fromAction {
        venuesList = venues
    }

    override fun getVenuesFlowable(): Flowable<List<Venue>> = venuesBehaviorProcessor
}
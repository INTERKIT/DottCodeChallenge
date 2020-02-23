package com.example.dottchallenge

import com.example.dottchallenge.map.model.PlaceLocation
import com.example.dottchallenge.map.model.Venue

object VenuesData {

    private val location = PlaceLocation("address", 2.0, 3.0, "city", "state", "12345")

    val venues: List<Venue> = mutableListOf(
        Venue("1", "Venue1", location, "someurl"),
        Venue("2", "Venue2", location, "someurl2"),
        Venue("3", "Venue3", location, "someurl3")
    )
}

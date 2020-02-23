package com.example.dottchallenge.map.model

import com.google.gson.annotations.SerializedName

data class PlaceLocation(
    @SerializedName("address")
    val address: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("postalCode")
    val postalCode: String
)
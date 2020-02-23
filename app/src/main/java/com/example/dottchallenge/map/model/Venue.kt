package com.example.dottchallenge.map.model

import com.google.gson.annotations.SerializedName

data class Venue(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("location")
    val location: PlaceLocation,
    @SerializedName("url")
    val url: String?
)
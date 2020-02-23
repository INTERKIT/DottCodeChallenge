package com.example.dottchallenge.map.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("response")
    val response: Response
)

data class Response(
    @SerializedName("venues")
    val venues: List<Venue>
)